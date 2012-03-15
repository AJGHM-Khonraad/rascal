package org.rascalmpl.library.lang.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.IWriter;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.TypeReifier;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.values.ValueFactoryFactory;

public class IO {
	private static final TypeFactory types = TypeFactory.getInstance();
	
	private final IValueFactory values;
	private int separator;  	// The separator to be used between fields
	private boolean header;		// Does the file start with a line defining field names?

	private TypeReifier tr;
	
	public IO(IValueFactory values){
		super();
		
		this.values = values;
		this.tr = new TypeReifier(values);
		separator = ',';
		header = true;
	}
	
	/**
	 * @param options	map with options may contain:
	 * 					- "header" (Possible values "true" or "false", as string!)
	 * 					- "separator" (Possible value a single character string)
	 * 					When map is null, the defaults are reset.
	 */
	private void setOptions(IMap options){
		
		IString separatorKey = values.string("separator");
		IString headerKey = values.string("header");
		
		IString iseparator = null;
		IString iheader = null;
		
		if(options != null){
			iseparator = (IString)options.get(separatorKey);
			iheader = (IString)options.get(headerKey);
		}
			
		separator = (iseparator == null) ? ',' : iseparator.getValue().charAt(0);
		header = (iheader == null) ? true : iheader.toString().equals("true");
	}
	
	/*
	 * Read a CSV file
	 */
	
	
	public IValue readCSV(IValue resultType, ISourceLocation loc, IEvaluatorContext ctx){
		return readCSV(resultType, loc, null, ctx);
	}

	public IValue readCSV(ISourceLocation loc, IEvaluatorContext ctx){
		return read(null, loc, null, ctx);
	}

	public IValue readCSV(ISourceLocation loc, IMap options, IEvaluatorContext ctx){
		return read(null, loc, options, ctx);
	}
	
	public IValue readCSV(IValue result, ISourceLocation loc, IMap options, IEvaluatorContext ctx){
		return read(tr.valueToType((IConstructor) result, new TypeStore()), loc, options, ctx);
	}


	//////
	
	private IValue read(Type resultType, ISourceLocation loc, IMap options, IEvaluatorContext ctx) {
		setOptions(options);
		InputStream in = null;
		try {
			in = ctx.getResolverRegistry().getInputStream(loc.getURI());
			List<Record> records = loadRecords(in);
			if (resultType == null) {
				resultType = inferType(records, ctx);
				ctx.getStdOut().println("readCSV inferred the relation type: " + resultType);
				ctx.getStdOut().flush();
			}
			else if (header) {
				records.remove(0);
			}
			return buildCollection(resultType, records, ctx);
		}
		catch (IOException e){
			throw RuntimeExceptionFactory.io(values.string(e.getMessage()), ctx.getCurrentAST(), ctx.getStackTrace());
		}
		finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e){
					throw RuntimeExceptionFactory.io(values.string(e.getMessage()), ctx.getCurrentAST(), ctx.getStackTrace());
				}
			}
		}
	}

	private List<Record> loadRecords(InputStream in) throws IOException {
		FieldReader reader = new FieldReader(in, separator);
		List<Record> records = new ArrayList<Record>();
		while (reader.hasRecord()) {
			// TODO: Record should not read from reader.
			records.add(new Record(reader));
		}
		return records;
	}
	

	private Type inferType(List<Record> records, IEvaluatorContext ctx) throws IOException {
		String[] labels = null;
		if (header) {
			labels = extractLabels(records.remove(0)); 
		}
		Type[] fieldTypes = null;
		for (Record ri: records) {
			List<Type> ftypes = ri.getFieldTypes();
			if (fieldTypes == null) {
				fieldTypes = new Type[ftypes.size()];
				Arrays.fill(fieldTypes, types.voidType());
			}
			else if (ftypes.size() != fieldTypes.length) {
				// We assume all records in the CSV file to have the same arity.
				throw RuntimeExceptionFactory.illegalArgument(
						//"Inconsistent tuple in CSV expected " + fieldTypes.length + " but was " + ftypes.size(),
						ctx.getCurrentAST(), ctx.getStackTrace());
				
			}
			for (int i = 0; i < ftypes.size(); i++){
				fieldTypes[i] = fieldTypes[i].lub(ftypes.get(i));
				if(fieldTypes[i].isValueType()) {
					fieldTypes[i] = types.stringType();
				}
			}
		}
		if (labels == null) {
			labels = makeUpLabels(fieldTypes.length);
		}
		return types.setType(types.tupleType(fieldTypes, labels));
	}

	
	private String[] extractLabels(Record record)  {
		String[] labels = new String[record.getFieldTypes().size()];
		int i = 0;
		for (String label: record) {
			label = normalizeLabel(label, i);
			labels[i] = label;
			i++;
		}
		return labels;
	}
	
	private String[] makeUpLabels(int n) {
		String labels[] = new String[n];
		for (int i = 0; i < n; i++) {
			labels[i] = "field" + i;
		}
		return labels;
	}

	
	public IValue buildCollection(Type type, List<Record> records, IEvaluatorContext ctx) {
		IWriter writer;
		if (type.isListType()) {
			writer = values.listWriter(type.getElementType());
		}
		else if (type.isRelationType()) {
			writer = values.relationWriter(type.getElementType());
		}
		else {
			throw RuntimeExceptionFactory.illegalTypeArgument(
					"Invalid result type for CSV reading: " + type, 
					ctx.getCurrentAST(), ctx.getStackTrace());
		}
		
		// reverse traversal so that the order in case of ListWriter is correct
		// (IWriter only supports inserts at the front).
		Type eltType = type.getElementType();
		for (int i = records.size() - 1; i >= 0; i--) {
			Record record = records.get(i);
			checkRecord(eltType, record, ctx);
			writer.insert(record.getTuple(eltType));
		}
		return writer.done();
	}

	private void checkRecord(Type eltType, Record record, IEvaluatorContext ctx) {
		if (record.getType().isSubtypeOf(eltType)) {
			return;
		}
		if (eltType.isTupleType()) {
			int expectedArity = eltType.getArity();
			List<Type> fieldTypes = record.getFieldTypes();
			int actualArity = fieldTypes.size();
			if (expectedArity == actualArity) {
				return;
			}
			throw RuntimeExceptionFactory.illegalTypeArgument(
					"Arities of actual type and requested type are different (" + actualArity + " vs " + expectedArity + ")", 
					ctx.getCurrentAST(), ctx.getStackTrace());
		}
		throw RuntimeExceptionFactory.illegalTypeArgument(
				"Invalid tuple " + record + " for requested field " + eltType, 
				ctx.getCurrentAST(), ctx.getStackTrace());
	}
	
	
	/*
	 * Write a CSV file.
	 */
	public void writeCSV(IValue rel, ISourceLocation loc, IMap options, IEvaluatorContext ctx){
		setOptions(options);
		write(rel, loc, ctx);
	}
	
	public void writeCSV(IValue rel, ISourceLocation loc, IEvaluatorContext ctx){
		setOptions(null);
		write(rel, loc, ctx);
	}
	
	public void write(IValue rel, ISourceLocation loc, IEvaluatorContext ctx){

		OutputStream out = null;
		
		Type paramType = ctx.getCurrentEnvt().getTypeBindings().get(types.parameterType("T"));
		
		if(!paramType.isRelationType()){
			throw RuntimeExceptionFactory.illegalTypeArgument("A relation type is required instead of " + paramType,ctx.getCurrentAST(), 
					ctx.getStackTrace());
		}
		
		try{
			out = ctx.getResolverRegistry().getOutputStream(loc.getURI(), false);
			IRelation irel = (IRelation) rel;
			
			int nfields = irel.arity();
			if(header){
				for(int i = 0; i < nfields; i++){
					if(i > 0)
						out.write(separator);
					String label = paramType.getFieldName(i);
					if(label == null || label.isEmpty())
						label = "field" + i;
					writeString(out, label);
				}
				out.write('\n');
			}
			
			for(IValue v : irel){
				ITuple tup = (ITuple) v;
				int sep = 0;
				for(IValue w : tup){
					if(sep == 0)
						sep = separator;
					else
						out.write(sep);
					if(w.getType().isStringType()){
						String s = ((IString)w).getValue();
						if(s.contains("\n") || s.contains("\"")){
							s = s.replaceAll("\"", "\"\"");
							out.write('"');
							writeString(out,s);
							out.write('"');
						} else
							writeString(out, s);
					} else {
						writeString(out, w.toString());
					}
				}
				out.write('\n');
			}
			out.flush();
			out.close();
		}
		catch(IOException e){
			throw RuntimeExceptionFactory.io(values.string(e.getMessage()), null, null);
		}finally{
			if(out != null){
				try{
					out.close();
				}catch(IOException ioex){
					throw RuntimeExceptionFactory.io(values.string(ioex.getMessage()), null, null);
				}
			}
		}
	}
	
	/**
	 * Normalize a label in the header for use in the relation type.
	 * @param label	The string found in the header
	 * @param pos	Position in the header
	 * @return		The label (with non-fieldname characters removed) or "field<pos>" when empty
	 */
	private String normalizeLabel(String label, int pos){
		label = label.replaceAll("[^a-zA-Z0-9]+", "");
		if(label.isEmpty())
			return "field" + pos;
		return label;
	}
	
	/**
	 * Write a string to the output stream.
	 * @param out	The output stream.
	 * @param txt	The string to be written.
	 * @throws IOException
	 */
	private void writeString(OutputStream out, String txt) throws IOException{
		for(char c : txt.toCharArray()){
			out.write((byte)c);
		}
	}
}

/**
 * Auxiliary class to read fields from an input stream.
 *
 */
class FieldReader {
	int lastChar = ';';
	int separator = ';';
	InputStream in;
	boolean startOfLine = true;
	
	FieldReader(InputStream in, int sep) throws IOException{
		this.in = in;
		this.separator = sep;
		startOfLine = true;
		lastChar = in.read();
	}
	
	private boolean isEOL(int c) {
		return c == '\n' || c == '\r';
	}
	
	/**
	 * @return true if the current record has another field left to be read.
	 * @throws IOException
	 */
	boolean hasField() throws IOException{
		if(startOfLine)
			return true;
		if(lastChar == separator){
			lastChar = in.read();
			return true; //lastChar != -1;
		}
		return false;
	}
	
	/**
	 * @return true if the current stream has another record to be read.
	 * @throws IOException
	 */
	boolean hasRecord() throws IOException{
		if(startOfLine)
			return true;
		while(isEOL(lastChar))
			lastChar = in.read();
		startOfLine = true;
		return lastChar != -1;
	}
	
	/**
	 * @return The next field from the input stream.
	 * @throws IOException
	 */
	String getField() throws IOException{
		startOfLine = false;
		StringWriter sw = new StringWriter();
		if(lastChar == '"'){
			lastChar = in.read();
			while (lastChar != -1){
				if(lastChar == '"'){
					lastChar = in.read();
					if(lastChar == '"'){
						sw.append('"');
						lastChar = in.read();
					} else
						break;
				} else {
					sw.append((char)lastChar);
					lastChar = in.read();
				}
			}
			assert lastChar == separator || isEOL(lastChar) || lastChar == -1;
			return sw.toString();
		}
		while ((lastChar != -1) && (lastChar != separator) && !isEOL(lastChar)){
			sw.append((char)lastChar);
			lastChar = in.read();
		}
		return sw.toString();
	}
}

/**
 * Auxiliary class to read and represent records in the file.
 *
 */
class Record implements Iterable<String> {
	private static final TypeFactory types = TypeFactory.getInstance();
	private static final IValueFactory values = ValueFactoryFactory.getValueFactory();
	ArrayList<IValue> rfields = new ArrayList<IValue>();
	ArrayList<Type> fieldTypes = new ArrayList<Type>();
	
	/**
	 * Create a record by reader all its fields using reader
	 * @param reader to be used.
	 * @throws IOException
	 */
	Record(FieldReader reader) throws IOException{
		
		while(reader.hasField()) {
			String field = reader.getField();
			//System.err.print("field = " + field);
			
			if(field.isEmpty()){
				rfields.add(null);
				fieldTypes.add(types.voidType());
				//System.err.println(" void");
			} else
			if(field.matches("^[+-]?[0-9]+$")){
				rfields.add(values.integer(field));
				fieldTypes.add(types.integerType());
				//System.err.println(" int");
			} else
			if(field.matches("[+-]?[0-9]+\\.[0-9]*")){
				rfields.add(values.real(field));
				fieldTypes.add(types.realType());
				//System.err.println(" real");
			} else
			if(field.equals("true") || field.equals("false")){
				rfields.add(values.bool(field.equals("true")));
				fieldTypes.add(types.boolType());
				//System.err.println(" bool");
			} else {
				rfields.add(values.string(field));
				fieldTypes.add(types.stringType());
				//System.err.println(" str");
			}
		}
	}
	
	
	
	/**
	 * @return the type of this record.
	 */
	Type getType(){
		Type[] typeArray = new Type[rfields.size()];
		for (int i = 0; i < rfields.size(); i++) {
			if (rfields.get(i) == null) {
				typeArray[i] = types.voidType();
			}
			else {
				typeArray[i] = rfields.get(i).getType();
			}
		}
		return types.tupleType(typeArray);
	}
	
	/**
	 * @return a list of the types of the fields of this record.
	 */
	ArrayList<Type> getFieldTypes(){
		return fieldTypes;
	}
	
	/**
	 * @param fieldTypes as inferred for the whole relation.
	 * @return The tuple value for this record
	 */
	ITuple getTuple(Type fieldTypes){
		IValue  fieldValues[] = new IValue[rfields.size()];
		for(int i = 0; i < rfields.size(); i++){
			if(rfields.get(i) == null){
				if(fieldTypes.getFieldType(i).isBoolType())
					rfields.set(i, values.bool(false));
				else if(fieldTypes.getFieldType(i).isIntegerType())
					rfields.set(i, values.integer(0));
				else if(fieldTypes.getFieldType(i).isRealType())
					rfields.set(i, values.real(0));
				else
					rfields.set(i, values.string(""));
			}
			if(fieldTypes.getFieldType(i).isStringType() && !rfields.get(i).getType().isStringType())
				rfields.set(i, values.string(rfields.get(i).toString()));
			fieldValues[i] = rfields.get(i);
		}
		return values.tuple(fieldValues);
	}



	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			private final Iterator<IValue> iter = rfields.iterator();
			
			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public String next() {
				IValue x = iter.next();
				if (x == null) {
					return "";
				}
				return x + "";
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
}
