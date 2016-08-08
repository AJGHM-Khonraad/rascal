package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.rascalmpl.interpreter.types.NonTerminalType;
import org.rascalmpl.interpreter.types.OverloadedFunctionType;
import org.rascalmpl.interpreter.types.RascalTypeFactory;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.serialize.util.MapLastWritten;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.serialize.util.TrackLastWritten;
import org.rascalmpl.value.IBool;
import org.rascalmpl.value.IConstructor;
import org.rascalmpl.value.IDateTime;
import org.rascalmpl.value.IInteger;
import org.rascalmpl.value.IList;
import org.rascalmpl.value.IMap;
import org.rascalmpl.value.INode;
import org.rascalmpl.value.IReal;
import org.rascalmpl.value.ISet;
import org.rascalmpl.value.ISourceLocation;
import org.rascalmpl.value.IString;
import org.rascalmpl.value.ITuple;
import org.rascalmpl.value.IValue;
import org.rascalmpl.value.IValueFactory;
import org.rascalmpl.value.type.Type;
import org.rascalmpl.value.type.TypeFactory;
import org.rascalmpl.value.type.TypeStore;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.RascalValueFactory;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;
        	
/**
 * RSFIValueWriter is a binary serializer for IValues and Types. The main public functions is:
 * - writeValue
 */
	        
public class RSFIValueWriter {
    
    public enum CompressionRate {
        None(0,0,0, 0),
        //TypesOnly(10,0,0),
        //ValuesOnly(0,10,10),
        Fast(10,10,10, 1),
        Normal(50,100,50,3),
        Extreme(50,250,100, 6)
        ;

        private final int uriWindow;
        private final int typeWindow;
        private final int valueWindow;
        private int xzMode;

        CompressionRate(int typeWindow, int valueWindow, int uriWindow, int xzMode) {
            this.typeWindow = typeWindow;
            this.valueWindow = valueWindow;
            this.uriWindow = uriWindow;
            this.xzMode = xzMode;
        }
        
    }
    
	protected static final byte[] header = { 'R', 'V', 1,0,0 };
	static final class CompressionHeader {
	    public static final byte NONE = 0;
	    public static final byte GZIP = 1;
	    public static final byte XZ = 2;
	}
	
	private static <T> TrackLastWritten<T> getWindow(int size) {
	    if (size == 0) {
	        return new TrackLastWritten<T>() {
	            public int howLongAgo(T obj) {
	                return -1;
	            }
	            public void write(T obj) {};
	        };
	    }
	    return new MapLastWritten<>(size * 1024); 
	}
	
	public static void write(OutputStream out, IValue value, CompressionRate compression, boolean shouldClose) throws IOException {
        out.write(header);
    	out.write(compression.typeWindow);
    	out.write(compression.valueWindow);
    	out.write(compression.uriWindow);
    	out.write(compression.xzMode == 0 ? CompressionHeader.NONE : CompressionHeader.XZ);
    	if (compression.xzMode > 0) {
    	    out = new XZOutputStream(out, new LZMA2Options(compression.xzMode));
    	}
    	RSFWriter writer =  new RSFWriter(out);
    	try {
    	    TrackLastWritten<Type> typeCache = getWindow(compression.typeWindow);
    	    TrackLastWritten<IValue> valueCache = getWindow(compression.valueWindow);
    	    TrackLastWritten<ISourceLocation> uriCache = getWindow(compression.uriWindow);
    	    write(writer, value, typeCache, valueCache, uriCache);
    	}
    	finally {
    	    writer.flush();
    	    if (shouldClose) {
    	        writer.close();
    	    }
    	    else {
                if (compression.xzMode > 0) {
                    ((XZOutputStream)out).finish();
                }
    	    }
    	}
	}
	
	private static void writeNames(final RSFWriter writer, int fieldId, String[] names) throws IOException{
		writer.writeField(fieldId, names.length);
		for(int i = 0; i < names.length; i++){
		    writer.writeField(fieldId, names[i]);
		}
	}
	
	private static void write(final RSFWriter writer, final Type type, final TrackLastWritten<Type> typeCache, final TrackLastWritten<IValue> valueCache, final TrackLastWritten<ISourceLocation> uriCache) throws IOException {
	    final PrePostTypeIterator iter = new PrePostTypeIterator(type);
	    
	    while(iter.hasNext()){
	        final TypeIteratorKind kind = iter.next();
	        final Type currentType = iter.getItem();
	        final boolean atBeginning = iter.atBeginning();
	        int lastSeen;
	        if (atBeginning && kind.isCompound() && (lastSeen = typeCache.howLongAgo(currentType)) != -1) {
	            writeSingleValueMessage(writer, RSF.PreviousType.ID, RSF.PreviousType.HOW_LONG_AGO, lastSeen);
	            iter.skipItem();
	        }
	        else {
	            switch(kind){
	                case BOOL: {
	                    writer.writeEmptyMessage(RSF.BoolType.ID);
	                    break;
	                }
	                case DATETIME: {
	                    writer.writeEmptyMessage(RSF.DateTimeType.ID);
	                    break;
	                }
	                case INT: {
	                    writer.writeEmptyMessage(RSF.IntegerType.ID);
	                    break;
	                }
	                case NODE: {
	                    writer.writeEmptyMessage(RSF.NodeType.ID);
	                    break;
	                }
	                case NUMBER: {
	                    writer.writeEmptyMessage(RSF.NumberType.ID);
	                    break;
	                }
	                case RATIONAL: {
	                    writer.writeEmptyMessage(RSF.RationalType.ID);
	                    break;
	                }
	                case REAL: {
	                    writer.writeEmptyMessage(RSF.RealType.ID);
	                    break;
	                }
	                case LOC: {
	                    writer.writeEmptyMessage(RSF.SourceLocationType.ID);
	                    break;
	                }
	                case STR: {
	                    writer.writeEmptyMessage(RSF.StringType.ID);
	                    break;
	                }
	                case VALUE: {
	                    writer.writeEmptyMessage(RSF.ValueType.ID);
	                    break;
	                }
	                case VOID: {
	                    writer.writeEmptyMessage(RSF.VoidType.ID);
	                    break;
	                }

	                // Composite types

	                case ADT: {
	                    if (!atBeginning) {
	                        writeSingleValueMessage(writer, RSF.ADTType.ID, RSF.ADTType.NAME, currentType.getName());
	                    }
	                    break;

	                }
	                case ALIAS: {
	                    if (!atBeginning) {
	                        writeSingleValueMessage(writer, RSF.AliasType.ID, RSF.AliasType.NAME, currentType.getName());
	                    }
	                    break;
	                }
	                case CONSTRUCTOR : {
	                    if (!atBeginning) {
	                        writeSingleValueMessage(writer, RSF.ConstructorType.ID, RSF.ConstructorType.NAME, currentType.getName());
	                    }
	                    break;
	                }
	                case FUNCTION: {
	                    if (!atBeginning) {
	                        writer.writeEmptyMessage(RSF.ConstructorType.ID);
	                    }
	                    break;
	                }

	                case REIFIED: {
	                    if (!atBeginning) {
	                        writer.writeEmptyMessage(RSF.ReifiedType.ID);
	                    }
	                    break;
	                }

	                case OVERLOADED: {
	                    if (!atBeginning) {
	                        writeSingleValueMessage(writer, RSF.OverloadedType.ID, RSF.OverloadedType.SIZE, ((OverloadedFunctionType) currentType).getAlternatives().size());
	                    }
	                    break;
	                }

	                case NONTERMINAL: {
	                    if (!atBeginning) {
	                        // first prefix with the Constructor 
	                        write(writer, ((NonTerminalType)currentType).getSymbol(), typeCache, valueCache, uriCache);
	                        writer.writeEmptyMessage(RSF.NonTerminalType.ID);
	                    }
	                    break;
	                }

	                case LIST: {
	                    if (!atBeginning) {
	                        writer.writeEmptyMessage(RSF.ListType.ID);
	                    }
	                    break;
	                }
	                
	                case MAP: {
	                    if (!atBeginning) {
	                        writer.writeEmptyMessage(RSF.MapType.ID);
	                    }
	                    break;
	                }
	                case PARAMETER: {
	                    if (!atBeginning) {
	                        writeSingleValueMessage(writer, RSF.ParameterType.ID, RSF.ParameterType.NAME,currentType.getName());
	                    }
	                    break;
	                }

	                case SET: {
	                    if (!atBeginning) {
	                        writer.writeEmptyMessage(RSF.SetType.ID);
	                    }
	                    break;
	                }
	                case TUPLE: {
	                    if (!atBeginning) {
	                        writer.startMessage(RSF.TupleType.ID);
	                        writer.writeField(RSF.TupleType.ARITY, currentType.getArity());
	                        String[] fieldNames = currentType.getFieldNames();
	                        if(fieldNames != null){
	                            writeNames(writer, RSF.TupleType.NAMES, fieldNames);
	                        }
	                        writer.endMessage();
	                    }
	                    break;
	                }
	            }
	            if (!atBeginning && kind.isCompound()) {
	                typeCache.write(currentType);
	            }
	        }
	    }
	}
	
	private static void writeSingleValueMessage(final RSFWriter writer, int messageID, int fieldId, long fieldValue) throws IOException {
	    writer.startMessage(messageID);
	    writer.writeField(fieldId, fieldValue);
	    writer.endMessage();
	}
	
	private static void writeSingleValueMessage(final RSFWriter writer, int messageID, int fieldId, String fieldValue) throws IOException {
	    writer.startMessage(messageID);
	    writer.writeField(fieldId, fieldValue);
	    writer.endMessage();
	}

	private static final IInteger MININT =ValueFactoryFactory.getValueFactory().integer(Integer.MIN_VALUE);
	private static final IInteger MAXINT =ValueFactoryFactory.getValueFactory().integer(Integer.MAX_VALUE);
	
	private static void write(final RSFWriter writer, final IValue value, final TrackLastWritten<Type> typeCache, final TrackLastWritten<IValue> valueCache, final TrackLastWritten<ISourceLocation> uriCache) throws IOException {
		PrePostIValueIterator iter = new PrePostIValueIterator(value);
		
		while(iter.hasNext()){
			final ValueIteratorKind kind = iter.next();
			final IValue currentValue = iter.getItem();
			final boolean atBeginning = iter.atBeginning();
			int lastSeen;
			if (atBeginning && kind.isCompound() && (lastSeen = valueCache.howLongAgo(currentValue)) > -1) {
			    writer.startMessage(RSF.PreviousValue.ID);
			    writer.writeField(RSF.PreviousValue.HOW_FAR_BACK, lastSeen);
			    writer.endMessage();
			    iter.skipItem();
			}
			else {
                switch(kind){
                case BOOL: {
                    assert atBeginning;
                    writeSingleValueMessage(writer, RSF.BoolValue.ID, RSF.BoolValue.VALUE, ((IBool)currentValue).getValue() ? 1: 0);
                    break;
                }

                case CONSTRUCTOR: {
                    if (!atBeginning) {
                        IConstructor cons = (IConstructor)currentValue;
                        write(writer, cons.getUninstantiatedConstructorType(), typeCache, valueCache, uriCache);

                        writer.startMessage(RSF.ConstructorValue.ID);
                        writer.writeField(RSF.ConstructorValue.ARITY, cons.arity());
                        if(cons.mayHaveKeywordParameters()){
                            if(cons.asWithKeywordParameters().hasParameters()){
                                writer.writeField(RSF.ConstructorValue.KWPARAMS, cons.asWithKeywordParameters().getParameters().size());
                            }
                        } else {
                            if(cons.asAnnotatable().hasAnnotations()){
                                writer.writeField(RSF.ConstructorValue.ANNOS, cons.asAnnotatable().getAnnotations().size());
                            }
                        }
                        writer.endMessage();
                    }
                    break;
                }
                
                case DATETIME: {
                    assert atBeginning;
                    
                    IDateTime dateTime = (IDateTime)currentValue;
                    writer.startMessage(RSF.DateTimeValue.ID);

                    if (!dateTime.isTime()) {
                        writer.writeField(RSF.DateTimeValue.YEAR, dateTime.getYear());
                        writer.writeField(RSF.DateTimeValue.MONTH, dateTime.getMonthOfYear());
                        writer.writeField(RSF.DateTimeValue.DAY, dateTime.getDayOfMonth());
                    }

                    if (!dateTime.isDate()) {
                        writer.writeField(RSF.DateTimeValue.HOUR, dateTime.getHourOfDay());
                        writer.writeField(RSF.DateTimeValue.MINUTE, dateTime.getMinuteOfHour());
                        writer.writeField(RSF.DateTimeValue.SECOND, dateTime.getSecondOfMinute());
                        writer.writeField(RSF.DateTimeValue.MILLISECOND, dateTime.getMillisecondsOfSecond());

                        writer.writeField(RSF.DateTimeValue.TZ_HOUR, dateTime.getTimezoneOffsetHours());
                        writer.writeField(RSF.DateTimeValue.TZ_MINUTE, dateTime.getTimezoneOffsetMinutes());
                    }
                    writer.endMessage();
                    break;
                }

                case INT: {
                    assert atBeginning;
                    writer.startMessage(RSF.IntegerValue.ID);
                    IInteger ii = (IInteger)currentValue;
                    if(ii. greaterEqual(MININT).getValue() && ii.lessEqual(MAXINT).getValue()){
                        writer.writeField(RSF.IntegerValue.INTVALUE, ii.intValue());
                    } 
                    else {
                        writer.writeField(RSF.IntegerValue.BIGVALUE, ii.getTwosComplementRepresentation());
                    }
                    writer.endMessage();
                    break;
                }
                
                case LIST: {
                    if (!atBeginning) {
                        writeSingleValueMessage(writer, RSF.ListValue.ID, RSF.ListValue.SIZE, ((IList)currentValue).length());
                    }
                    break;
                }
                
                case MAP: {
                    if (!atBeginning) {
                        writeSingleValueMessage(writer, RSF.MapValue.ID, RSF.MapValue.SIZE, ((IMap)currentValue).size());
                    }
                    break;
                }
                case SET: {
                    if (!atBeginning) {
                        writeSingleValueMessage(writer, RSF.SetValue.ID, RSF.SetValue.SIZE, ((ISet)currentValue).size());
                    }
                    break;
                }

                case NODE: {
                    if (!atBeginning) {
                        INode node = (INode)currentValue;
                        writer.startMessage(RSF.NodeValue.ID);
                        writer.writeField(RSF.NodeValue.NAME,  node.getName());
                        writer.writeField(RSF.NodeValue.ARITY, node.arity());
                        if(node.mayHaveKeywordParameters()){
                            if(node.asWithKeywordParameters().hasParameters()){
                                writer.writeField(RSF.NodeValue.KWPARAMS, node.asWithKeywordParameters().getParameters().size());
                            }
                        } else {
                            if(node.asAnnotatable().hasAnnotations()){
                                writer.writeField(RSF.NodeValue.ANNOS, node.asAnnotatable().getAnnotations().size());
                            }
                        }
                        writer.endMessage();
                    }
                    break;
                }
                        
                case RATIONAL: {
                    if (!atBeginning) {
                        writer.writeEmptyMessage(RSF.RationalValue.ID);
                    }
                    break;
                }
                    
                case REAL: {
                    assert atBeginning;
                    writer.startMessage(RSF.RealValue.ID);
                    writer.writeField(RSF.RealValue.CONTENT, ((IReal)currentValue).unscaled().getTwosComplementRepresentation());
                    writer.writeField(RSF.RealValue.SCALE, ((IReal)currentValue).scale());
                    writer.endMessage();
                    break;
                }
                
                case LOC: {
                    assert atBeginning;
                    
                    writer.startMessage(RSF.SourceLocationValue.ID);
                    ISourceLocation loc = (ISourceLocation)currentValue;
                    ISourceLocation uriPart = loc.top();
                    int alreadyWritten = uriCache.howLongAgo(uriPart);
                    if (alreadyWritten == -1) {
                        writer.writeField(RSF.SourceLocationValue.SCHEME, uriPart.getScheme());
                        if (uriPart.hasAuthority()) {
                            writer.writeField(RSF.SourceLocationValue.AUTHORITY, uriPart.getAuthority());
                        }
                        if (uriPart.hasPath()) {
                            writer.writeField(RSF.SourceLocationValue.PATH, uriPart.getPath());
                        }
                        if (uriPart.hasQuery()) {
                            writer.writeField(RSF.SourceLocationValue.QUERY,  uriPart.getQuery());
                        }
                        if (uriPart.hasFragment()) {
                            writer.writeField(RSF.SourceLocationValue.FRAGMENT,  uriPart.getFragment());
                        }
                        uriCache.write(uriPart);
                    }
                    else {
                        writer.writeField(RSF.SourceLocationValue.PREVIOUS_URI, alreadyWritten);
                    }

                    if(loc.hasOffsetLength()){
                        writer.writeField(RSF.SourceLocationValue.OFFSET, loc.getOffset());
                        writer.writeField(RSF.SourceLocationValue.LENGTH, loc.getLength());
                    } 
                    if(loc.hasLineColumn()){
                        writer.writeField(RSF.SourceLocationValue.BEGINLINE, loc.getBeginLine());
                        writer.writeField(RSF.SourceLocationValue.ENDLINE, loc.getEndLine());
                        writer.writeField(RSF.SourceLocationValue.BEGINCOLUMN, loc.getBeginColumn());
                        writer.writeField(RSF.SourceLocationValue.ENDCOLUMN, loc.getEndColumn());
                    }
                    writer.endMessage();
                    break;
                }
                    
                case STR: {
                    assert iter.atBeginning();
                    writeSingleValueMessage(writer, RSF.StringValue.ID, RSF.StringValue.CONTENT, ((IString)currentValue).getValue());
                    break;
                }

                case TUPLE: {
                    if (!atBeginning) {
                        writeSingleValueMessage(writer, RSF.TupleValue.ID, RSF.TupleValue.SIZE, ((ITuple)currentValue).arity());
                    }
                    break;
                }
                
                default:
                     throw new RuntimeException("writeValue: unexpected kind of value " + kind);
                }
            }
			if (!atBeginning && kind.isCompound()) {
			    valueCache.write(currentValue);
			}
		}
	}
	
  // Test code
    


    public static void main(String[] args) throws Exception {
    	TypeFactory tf = TypeFactory.getInstance();
    	RascalTypeFactory rtf = RascalTypeFactory.getInstance();
    	IValueFactory vf = ValueFactoryFactory.getValueFactory();
    	TypeStore ts = RascalValueFactory.getStore();
    	 try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
    		 Type ct = tf.constructor(ts, tf.abstractDataType(ts, "D"), "f", tf.integerType(), tf.stringType());
    		 IConstructor nd = vf.constructor(ct, vf.integer(42));
    		 nd = nd.asWithKeywordParameters().setParameter("a", vf.integer(1));
    		 nd = nd.asWithKeywordParameters().setParameter("b", vf.string("xyz"));
    		 
    		 Type param = tf.parameterType("T");
    		 
    		 Type maybe = tf.abstractDataType(ts, "Maybe");
    		 
    		 Type none = tf.constructor(ts, maybe, "none");
    		 
    		 Type Bool = tf.abstractDataType(ts, "Bool");
    		 Type btrue = tf.constructor(ts, Bool, "btrue");
    		 Type bfalse = tf.constructor(ts, Bool, "bfalse");
    		 Type band = tf.constructor(ts, Bool, "band", Bool, Bool);
    		 Type bor = tf.constructor(ts, Bool, "bor", Bool, Bool);
    		 
    		 IValue trueval = vf.constructor(btrue);
    		 IValue falseval = vf.constructor(bfalse);
    		 
    		 IValue andval = vf.constructor(band, trueval, falseval);
    		 
    		 
    		 Type t = rtf.functionType(tf.integerType(), tf.tupleType(tf.stringType(), tf.boolType()), tf.voidType());
    		 IValue v = andval;
    		 System.out.println(v);
    		 RSFIValueWriter.write(out, v, CompressionRate.Normal, true);
    		 try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                 System.out.println(RSFIValueReader.read(in, vf, ts));
             }
    		 
    	 }
    }
}
