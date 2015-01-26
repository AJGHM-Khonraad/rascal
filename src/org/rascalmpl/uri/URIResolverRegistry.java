/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
 *******************************************************************************/
package org.rascalmpl.uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.rascalmpl.unicode.UnicodeInputStreamReader;

public class URIResolverRegistry {
	private final Map<String,ISourceLocationInput> inputResolvers = new HashMap<>();
	private final Map<String,ISourceLocationOutput> outputResolvers = new HashMap<>();
	private final Map<String, Map<String,ILogicalSourceLocationResolver>> logicalResolvers = new HashMap<>();
	private static class InstanceHolder {
		static URIResolverRegistry sInstance = new URIResolverRegistry();
	}
	
	private URIResolverRegistry() { }

	public static URIResolverRegistry getInstance() {
		return InstanceHolder.sInstance;
	}
	
	public void registerLogical(ILogicalSourceLocationResolver resolver) {
		synchronized (logicalResolvers) {
			Map<String, ILogicalSourceLocationResolver> map = logicalResolvers.get(resolver.scheme());
			if (map == null) {
				map = new HashMap<>();
				logicalResolvers.put(resolver.scheme(), map);
			}
			map.put(resolver.authority(), resolver);
		}
	}
	
	public ISourceLocation logicalToPhysical(ISourceLocation loc) throws IOException {
		ISourceLocation result = physicalLocation(loc);
		if (result == null) {
			throw new FileNotFoundException(loc.toString());
		}
		return result;
	}
	
	private ISourceLocation physicalLocation(ISourceLocation loc) {
		synchronized (logicalResolvers) {
			while (logicalResolvers.containsKey(loc.getScheme())) {
				Map<String, ILogicalSourceLocationResolver> map = logicalResolvers.get(loc.getScheme());
				String auth = loc.hasAuthority() ? loc.getAuthority() : "";
				ILogicalSourceLocationResolver resolver = map.get(auth);
				ILogicalSourceLocationResolver noAuth = map.get("");
				ISourceLocation prev = loc;
				
				if (resolver != null) {
					loc = resolver.resolve(loc);
				}
				
				if (noAuth != null && (loc == null || prev.equals(loc))) {
				    loc = noAuth.resolve(loc);
				}
				
				if (loc == null || prev.equals(loc)) {
					return null;
				}
			}
		}
		
		return loc;
	}
	
	public void registerInput(ISourceLocationInput resolver) {
		synchronized (inputResolvers) {
			inputResolvers.put(resolver.scheme(), resolver);
		}
	}

	public void registerOutput(ISourceLocationOutput resolver) {
		synchronized (outputResolvers) {
			outputResolvers.put(resolver.scheme(), resolver);
		}
	}

	private static final Pattern splitScheme = Pattern.compile("^([^\\+]*)\\+");

	private ISourceLocationInput getInputResolver(String scheme) {
		synchronized (inputResolvers) {
			ISourceLocationInput result = inputResolvers.get(scheme);
			if (result == null) {
				Matcher m = splitScheme.matcher(scheme);
				if (m.find()) {
					String subScheme = m.group(1);
					return inputResolvers.get(subScheme);
				}
			}
			return result;
		}
	}

	private ISourceLocationOutput getOutputResolver(String scheme) {
		synchronized (outputResolvers) {
			ISourceLocationOutput result = outputResolvers.get(scheme);
			if (result == null) {
				Matcher m = splitScheme.matcher(scheme);
				if (m.find()) {
					String subScheme = m.group(1);
					return outputResolvers.get(subScheme);
				}
			}
			return result;
		}
	}

	public boolean supportsInputScheme(String scheme) {
		return getInputResolver(scheme) != null;
	}

	public boolean supportsOutputScheme(String scheme) {
		return getOutputResolver(scheme) != null;
	}

	public void registerInputOutput(ISourceLocationInputOutput resolver) {
		registerInput(resolver);
		registerOutput(resolver);
	}

	public boolean supportsHost(ISourceLocation uri) {
		uri = physicalLocation(uri);
		ISourceLocationInput resolver = getInputResolver(uri.getScheme());
		if (resolver == null) {
			ISourceLocationOutput resolverOther = getOutputResolver(uri.getScheme());
			if (resolverOther == null) {
				return false;
			}
			return resolverOther.supportsHost();
		}
		return resolver.supportsHost();
	}

	public boolean exists(ISourceLocation uri) {
		uri = physicalLocation(uri);
		ISourceLocationInput resolver = getInputResolver(uri.getScheme());

		if (resolver == null) {
			return false;
		}

		return resolver.exists(uri);
	}

	public boolean isDirectory(ISourceLocation uri) {
		uri = physicalLocation(uri);
		ISourceLocationInput resolver = getInputResolver(uri.getScheme());

		if (resolver == null) {
			return false;
		}
		return resolver.isDirectory(uri);
	}

	public void mkDirectory(ISourceLocation uri) throws IOException {
		uri = physicalLocation(uri);
		ISourceLocationOutput resolver = getOutputResolver(uri.getScheme());

		if (resolver == null) {
			throw new UnsupportedSchemeException(uri.getScheme());
		}

		mkParentDir(uri);

		resolver.mkDirectory(uri);
	}

	public void remove(ISourceLocation uri) throws IOException {
		uri = physicalLocation(uri);
		ISourceLocationOutput out = getOutputResolver(uri.getScheme());

		if (out == null) {
			throw new UnsupportedSchemeException(uri.getScheme());
		}

		if (isDirectory(uri)) { 
			for (ISourceLocation element : list(uri)) {
				remove(element);
			} 
		}

		out.remove(uri);
	}

	public boolean isFile(ISourceLocation uri) {
		uri = physicalLocation(uri);
		ISourceLocationInput resolver = getInputResolver(uri.getScheme());

		if (resolver == null) {
			return false;
		}
		return resolver.isFile(uri);
	}

	public long lastModified(ISourceLocation uri) throws IOException {
		uri = physicalLocation(uri);
		ISourceLocationInput resolver = getInputResolver(uri.getScheme());

		if (resolver == null) {
			throw new UnsupportedSchemeException(uri.getScheme());
		}
		return resolver.lastModified(uri);
	}

	public ISourceLocation[] list(ISourceLocation uri) throws IOException {
		uri = physicalLocation(uri);
		ISourceLocationInput resolver = getInputResolver(uri.getScheme());

		if (resolver == null) {
			throw new UnsupportedSchemeException(uri.getScheme());
		}
		return resolver.list(uri);
	}


	public Reader getCharacterReader(ISourceLocation uri) throws IOException {
		uri = physicalLocation(uri);
		return getCharacterReader(uri, getCharset(uri));
	}

	public Reader getCharacterReader(ISourceLocation uri, String encoding) throws IOException {
		uri = physicalLocation(uri);
		return getCharacterReader(uri, Charset.forName(encoding));
	}

	public Reader getCharacterReader(ISourceLocation uri, Charset encoding) throws IOException {
		uri = physicalLocation(uri);
		return new UnicodeInputStreamReader(getInputStream(uri), encoding);

	}
	
	public InputStream getInputStream(ISourceLocation uri) throws IOException {
		uri = physicalLocation(uri);
		ISourceLocationInput resolver = getInputResolver(uri.getScheme());

		if (resolver == null) {
			throw new UnsupportedSchemeException(uri.getScheme());
		}

		return resolver.getInputStream(uri);
	}

	public Charset getCharset(ISourceLocation uri) throws IOException {
		uri = physicalLocation(uri);
		ISourceLocationInput resolver = getInputResolver(uri.getScheme());

		if (resolver == null) {
			throw new UnsupportedSchemeException(uri.getScheme());
		}

		return resolver.getCharset(uri);
	}

	public OutputStream getOutputStream(ISourceLocation uri, boolean append) throws IOException {
		uri = physicalLocation(uri);
		ISourceLocationOutput resolver = getOutputResolver(uri.getScheme());

		if (resolver == null) {
			throw new UnsupportedSchemeException(uri.getScheme());
		}

		if (uri.getPath() != null && uri.getPath().startsWith("/..")) {
			throw new IllegalArgumentException("Can not navigate beyond the root of a URI: " + uri);
		}

		mkParentDir(uri);

		return resolver.getOutputStream(uri, append);
	}

	private void mkParentDir(ISourceLocation uri) throws IOException {
		uri = physicalLocation(uri);
		ISourceLocation parentURI = URIUtil.getParentLocation(uri);

		if (parentURI != null && !exists(parentURI)) {
			mkDirectory(parentURI);
		}
	}
}
