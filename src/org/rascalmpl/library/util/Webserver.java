package org.rascalmpl.library.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.TypeReifier;
import org.rascalmpl.interpreter.control_exceptions.Throw;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.result.ICallableValue;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.lang.json.io.JsonValueReader;
import org.rascalmpl.library.lang.json.io.JsonValueWriter;
import org.rascalmpl.uri.URIResolverRegistry;
import org.rascalmpl.uri.URIUtil;
import org.rascalmpl.value.IBool;
import org.rascalmpl.value.IConstructor;
import org.rascalmpl.value.IMap;
import org.rascalmpl.value.IMapWriter;
import org.rascalmpl.value.ISourceLocation;
import org.rascalmpl.value.IString;
import org.rascalmpl.value.IValue;
import org.rascalmpl.value.IValueFactory;
import org.rascalmpl.value.IWithKeywordParameters;
import org.rascalmpl.value.exceptions.FactTypeUseException;
import org.rascalmpl.value.type.Type;
import org.rascalmpl.value.type.TypeFactory;
import org.rascalmpl.value.type.TypeStore;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class Webserver {
  private final IValueFactory vf;
  private final Map<ISourceLocation, NanoHTTPD> servers;
  private final Map<IConstructor,Status> statusValues = new HashMap<>();
  private Type requestType;
  private Type post;
  private Type get;
  private Type head;
  private Type delete;
  private Type put;
  
  
  public Webserver(IValueFactory vf) {
    this.vf = vf;
    this.servers = new HashMap<>();
  }

  public void serve(ISourceLocation url, IValue type, final IValue callback, final IEvaluatorContext ctx) {
    URI uri = url.getURI();
    initMethodAndStatusValues(ctx);
    final TypeStore store = new TypeStore();
    final Type topType = new TypeReifier(vf).valueToType((IConstructor) type, store);
    final Type requestTypeInstance = requestType.instantiate(Collections.singletonMap(requestType.getTypeParameters().getFieldType(0), topType));

    int port = uri.getPort() != -1 ? uri.getPort() : 80;
    String host = uri.getHost() != null ? uri.getHost() : "localhost";
    host = host.equals("localhost") ? "127.0.0.1" : host; // NanoHttp tries to resolve localhost, which isn't what we want!
    final ICallableValue callee = (ICallableValue) callback; 
    
    NanoHTTPD server = new NanoHTTPD(host, port) {
      
      @Override
      public Response serve(String uri, Method method, Map<String, String> headers, Map<String, String> parms,
          Map<String, String> files) {
        try {
          IConstructor request = makeRequest(vf.sourceLocation(URIUtil.assumeCorrect("request", "", uri)), method, headers, parms, files);
          
          synchronized (callee.getEval()) {
            callee.getEval().__setInterrupt(false);
            return translateResponse(method, callee.call(new Type[] {requestTypeInstance}, new IValue[] { request }, null).getValue());  
          }
        }
        catch (Throw rascalException) {
          ctx.getStdErr().println(rascalException.getMessage());
          return new Response(Status.INTERNAL_ERROR, "text/plain", rascalException.getMessage());
        }
        catch (Throwable unexpected) {
          ctx.getStdErr().println(unexpected.getMessage());
          unexpected.printStackTrace(ctx.getStdErr());
          return new Response(Status.INTERNAL_ERROR, "text/plain", unexpected.getMessage());
        }
      }

      private IConstructor makeRequest(ISourceLocation loc, Method method, Map<String, String> headers,
          Map<String, String> parms, Map<String, String> files) throws FactTypeUseException, IOException {
        Map<String,IValue> kws = new HashMap<>();
        kws.put("params", makeMap(parms));
        kws.put("uploads", makeMap(files));
        kws.put("headers", makeMap(headers));
        
        switch (method) {
          case HEAD:
            return vf.constructor(head, loc);
          case DELETE:
            return vf.constructor(delete, loc);
          case GET:
            return vf.constructor(get, loc);
          case PUT:
            return vf.constructor(put, loc, getContent(parms));
          case POST:
            return vf.constructor(post, loc, getContent(parms));
          default:
              throw new IOException("Unhandled request " + method);
        }
      }

      private IValue getContent(Map<String, String> parms) throws IOException {
        if (topType.isString()) {
          return vf.string(parms.get("content"));
        }
        else {
          return new JsonValueReader(vf, store).read(new JsonReader(new StringReader(parms.get("content"))), topType);
        }
      }

      private Response translateResponse(Method method, IValue value) throws IOException {
        IConstructor cons = (IConstructor) value;
        initMethodAndStatusValues(ctx);
        
        switch (cons.getName()) {
          case "fileResponse":
            return translateFileResponse(method, cons);
          case "jsonResponse":
            return translateJsonResponse(method, cons);
          case "response":
            return translateTextResponse(method, cons);
          default:
            throw new IOException("Unknown response kind: " + value);
        }
      }
      
      private Response translateJsonResponse(Method method, IConstructor cons) {
        IMap header = (IMap) cons.get("header");
        IValue data = cons.get("val");
        Status status = translateStatus((IConstructor) cons.get("status"));
        IWithKeywordParameters<? extends IConstructor> kws = cons.asWithKeywordParameters();
        
        IValue dtf = kws.getParameter("dateTimeFormat");
        IValue ics = kws.getParameter("implicitConstructors");
        IValue ipn = kws.getParameter("implicitNodes");
        IValue dai = kws.getParameter("dateTimeAsInt");
        
        JsonValueWriter writer = new JsonValueWriter()
            .setCalendarFormat(dtf != null ? ((IString) dtf).getValue() : "yyyy-MM-dd\'T\'HH:mm:ss\'Z\'")
            .setImplicitConstructors(ics != null ? ((IBool) ics).getValue() : true)
            .setImplicitNodes(ipn != null ? ((IBool) ipn).getValue() : true)
            .setDatesAsInt(dai != null ? ((IBool) dai).getValue() : true);

        try {
          final ByteArrayOutputStream baos = new ByteArrayOutputStream();
          
          JsonWriter out = new JsonWriter(new OutputStreamWriter(baos, Charset.forName("UTF8")));
          
          writer.write(out, data);
          out.flush();
          out.close();
          
          Response response = new Response(status, "application/json", new ByteArrayInputStream(baos.toByteArray()));
          addHeaders(response, header);
          return response;
        }
        catch (IOException e) {
          // this should not happen in theory
          throw new RuntimeException("Could not create piped inputstream");
        }
      }

      private Response translateFileResponse(Method method, IConstructor cons) {
        ISourceLocation l = (ISourceLocation) cons.get("file");
        IString mimeType = (IString) cons.get("mimeType");
        IMap header = (IMap) cons.get("header");
        
        Response response;
        try {
          response = new Response(Status.OK, mimeType.getValue(),URIResolverRegistry.getInstance().getInputStream(l));
          addHeaders(response, header);
          return response;
        } catch (IOException e) {
          e.printStackTrace(ctx.getStdErr());
          return new Response(Status.NOT_FOUND, "text/plain", l + " not found.\n" + e);
        } 
      }

      private Response translateTextResponse(Method method, IConstructor cons) {
        IString mimeType = (IString) cons.get("mimeType");
        IMap header = (IMap) cons.get("header");
        IString data = (IString) cons.get("content");
        Status status = translateStatus((IConstructor) cons.get("status"));
        
        if (method != Method.HEAD) {
          switch (status) {
          case BAD_REQUEST:
          case UNAUTHORIZED:
          case NOT_FOUND:
          case FORBIDDEN:
          case RANGE_NOT_SATISFIABLE:
          case INTERNAL_ERROR:
            if (data.length() == 0) {
              data = vf.string(status.getDescription());
            }
          default:
            break;
          }
        }
        Response response = new Response(status, mimeType.getValue(), data.getValue());
        addHeaders(response, header);
        return response;
      }

      private void addHeaders(Response response, IMap header) {
        // TODO add first class support for cache control on the Rascal side. For
        // now we prevent any form of client-side caching with this.. hopefully.
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");
        
        for (IValue key : header) {
          response.addHeader(((IString) key).getValue(), ((IString) header.get(key)).getValue());
        }
      }

      private Status translateStatus(IConstructor cons) {
        initMethodAndStatusValues(ctx);
        return statusValues.get(cons);
      }

      private IMap makeMap(Map<String, String> headers) {
        IMapWriter writer = vf.mapWriter();
        for (Entry<String, String> entry : headers.entrySet()) {
          writer.put(vf.string(entry.getKey()), vf.string(entry.getValue()));
        }
        return writer.done();
      }
    };
   
    try {
      server.start();
      servers.put(url, server);
    } catch (IOException e) {
      throw RuntimeExceptionFactory.io(vf.string(e.getMessage()), null, null);
    }
  }
  
  public void shutdown(ISourceLocation server) {
    NanoHTTPD nano = servers.get(server);
    if (nano != null) {
      //if (nano.isAlive()) {
        nano.stop();
        servers.remove(server);
      //}
    }
    else {
      throw RuntimeExceptionFactory.illegalArgument(server, null, null, "could not shutdown");
    }
  }
  
  @Override
  protected void finalize() throws Throwable {
    for (NanoHTTPD server : servers.values()) {
      if (server != null && server.wasStarted()) {
        server.stop();
      }
    }
  }

  private void initMethodAndStatusValues(final IEvaluatorContext ctx) {
    if (statusValues.isEmpty() || requestType == null) {
      Environment env = ctx.getHeap().getModule("util::Webserver");
      TypeFactory tf = TypeFactory.getInstance();
      Type statusType = env.getAbstractDataType("Status");
                        
      statusValues.put(vf.constructor(env.getConstructor(statusType, "ok", tf.voidType())), Status.OK);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "created", tf.voidType())), Status.CREATED);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "accepted", tf.voidType())), Status.ACCEPTED);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "noContent", tf.voidType())), Status.NO_CONTENT);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "partialContent", tf.voidType())), Status.PARTIAL_CONTENT);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "redirect", tf.voidType())), Status.REDIRECT);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "notModified", tf.voidType())), Status.NOT_MODIFIED);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "badRequest", tf.voidType())), Status.BAD_REQUEST);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "unauthorized", tf.voidType())), Status.UNAUTHORIZED);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "forbidden", tf.voidType())), Status.FORBIDDEN);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "notFound", tf.voidType())), Status.NOT_FOUND);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "rangeNotSatisfiable", tf.voidType())), Status.RANGE_NOT_SATISFIABLE);
      statusValues.put(vf.constructor(env.getConstructor(statusType, "internalError", tf.voidType())), Status.INTERNAL_ERROR);
      
      requestType = env.getAbstractDataType("Request");
      Type param = requestType.getTypeParameters().getFieldType(0);
      
      get = env.getConstructor(requestType, "get", tf.tupleType(tf.sourceLocationType()));
      put = env.getConstructor(requestType, "put",  tf.tupleType(tf.sourceLocationType(), param));
      post = env.getConstructor(requestType, "post",  tf.tupleType(tf.sourceLocationType(), param));
      delete = env.getConstructor(requestType, "delete",  tf.tupleType(tf.sourceLocationType()));
      head = env.getConstructor(requestType, "head",  tf.tupleType(tf.sourceLocationType()));
    }
  }
}
