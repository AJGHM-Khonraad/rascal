package org.meta_environment.rascal.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.exceptions.FactParseError;
import org.eclipse.imp.pdb.facts.io.ATermReader;
import org.meta_environment.ValueFactoryFactory;
import org.meta_environment.errors.SummaryAdapter;
import org.meta_environment.rascal.interpreter.Configuration;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.rascal.interpreter.staticErrors.SyntaxError;
import org.meta_environment.uptr.Factory;
import org.meta_environment.uptr.ParsetreeAdapter;

import sglr.SGLRInvoker;

public class ModuleParser {
	protected static final String META_LANGUAGE_KEY = "meta";
	private final IValueFactory valueFactory = ValueFactoryFactory.getValueFactory();
	private final SdfImportExtractor importExtractor = new SdfImportExtractor();

	public Set<String> getSdfImports(List<String> sdfSearchPath, String fileName, InputStream source) throws IOException {
		try {
			IConstructor tree= parseFromStream(Configuration.getHeaderParsetableProperty(), fileName, source);

			if (tree.getConstructorType() == Factory.ParseTree_Summary) {
				throw new SyntaxError(fileName, new SummaryAdapter(tree).getInitialSubject().getLocation());
			}
			return importExtractor.extractImports(tree, sdfSearchPath);
		}
		catch (FactParseError p) {
			throw new ImplementationError("unexpected error: " + p.getMessage());
		}
	}

	public IConstructor parseCommand(Set<String> sdfImports, List<String> sdfSearchPath, String fileName, String command) throws IOException {
		String table = getOrConstructParseTable(META_LANGUAGE_KEY, sdfImports, sdfSearchPath);
		return parseFromString(table, fileName, command);
	}


	public IConstructor parseModule(List<String> sdfSearchPath, Set<String> sdfImports, String fileName, InputStream source) throws IOException {
		String table = getOrConstructParseTable(META_LANGUAGE_KEY, sdfImports, sdfSearchPath);
		try {
			return parseFromStream(table, fileName, source);
		} catch (FactParseError e) {
			throw new ImplementationError("parse tree format error", e);
		} 
	}

	public IConstructor parseObjectLanguageFile(List<String> sdfSearchPath, Set<String> sdfImports, String fileName) throws IOException {
		String table = getOrConstructParseTable(META_LANGUAGE_KEY, sdfImports, sdfSearchPath);
		try {
			return parseFromFile(table, fileName);
		} catch (FactParseError e) {
			throw new ImplementationError("parse tree format error", e);
		} 
	}
	
	protected String getOrConstructParseTable(String key, Set<String> sdfImports, List<String> sdfSearchPath) throws IOException {
		if (sdfImports.isEmpty()) {
			return Configuration.getDefaultParsetableProperty();
		}

		String table = getTable(key, sdfImports, sdfSearchPath);

		if (table == null) {
			return constructUserDefinedSyntaxTable(key, sdfImports, sdfSearchPath);
		}

		return table;
	}

	private IConstructor parseFromStream(String table, String fileName, InputStream source) throws FactParseError, IOException {
		SGLRInvoker sglrInvoker = SGLRInvoker.getInstance();
		byte[] result = sglrInvoker.parseFromStream(source, table);

		return bytesToParseTree(fileName, result);
	}

	private IConstructor bytesToParseTree(String fileName, byte[] result)
			throws IOException {
		ATermReader reader = new ATermReader();
		ByteArrayInputStream bais = new ByteArrayInputStream(result);
		IConstructor tree = (IConstructor) reader.read(valueFactory,  Factory.getStore(),Factory.ParseTree, bais);
		return new ParsetreeAdapter(tree).addPositionInformation(fileName);
	}
	
	protected IConstructor parseFromFile(String table, String fileName) throws FactParseError, IOException {
		byte[] result = SGLRInvoker.getInstance().parseFromFile(new File(fileName), table);
		return bytesToParseTree(fileName, result);
	}

	protected IConstructor parseFromString(String table, String fileName, String source) throws FactParseError, IOException {
		SGLRInvoker sglrInvoker = SGLRInvoker.getInstance();
		byte[] result = sglrInvoker.parseFromString(source, table);

		return bytesToParseTree(fileName, result);
	}

	protected String constructUserDefinedSyntaxTable(String key, Set<String> sdfImports, List<String> sdfSearchPath) throws IOException {
		String tablefileName = getTableLocation(key, sdfImports, sdfSearchPath);

		Process p = Runtime.getRuntime().exec(new String[] {
				Configuration.getRascal2TableCommandProperty(),
				"-s", getImportParameter(sdfImports),
				"-p", getSdfSearchPath(sdfSearchPath),
				"-o", tablefileName
		}, new String[0], new File(Configuration.getRascal2TableBinDirProperty()));
		
		try{
			p.waitFor();
		}catch(InterruptedException irex){
			throw new ImplementationError("Interrupted while waiting for the generation of the parse table.");
		}finally{
			p.destroy();
		}

		return tablefileName;
	}

	private String joinAsPath(Collection<?> list) {
		StringBuilder tmp = new StringBuilder();
		boolean first = true;
		for (Object object: list) {
			if (!first) {
				tmp.append(':');
			}
			tmp.append(object);
			first = false;
		}
		return tmp.toString();
	}

	protected String getSdfSearchPath(List<String> sdfSearchPath) {
		return joinAsPath(sdfSearchPath);
	}

	protected String getImportParameter(Set<String> sdfImports) {
		return joinAsPath(sdfImports);
	}

	protected String getTable(String key, Set<String> imports, List<String> sdfSearchPath) throws IOException {
		String filename = getTableLocation(key, imports, sdfSearchPath);

		if (!new File(filename).canRead()) {
			return null;
		}

		return filename;
	}

	protected String getTableLocation(String key, Set<String> sdfImports, List<String> sdfSearchPath) throws IOException {
		List<String> sorted = new ArrayList<String>(sdfImports);
		Collections.sort(sorted);
		InputStream in = null;

		Process p = Runtime.getRuntime().exec(new String[] {  
				Configuration.getRascal2TableCommandProperty(),
				"-c",
				"-s", joinAsPath(sorted),
				"-p", joinAsPath(sdfSearchPath)
		}, new String[0], new File(Configuration.getRascal2TableBinDirProperty()));
		
		try{
			p.waitFor();
			
			if (p.exitValue() != 0) {
				throw new ImplementationError("Could not collect syntax for some reason");
			}

			in = p.getInputStream();

			byte[] result = new byte[32];
			in.read(result);

			return new File(Configuration.getTableCacheDirectoryProperty(), parseTableFileName(key, result)).getAbsolutePath();
		}catch(InterruptedException e){
			throw new IOException("could not compute table location: " + e.getMessage());
		}finally{
			if(p != null){
				p.destroy();
			}
		}
	}

	private String parseTableFileName(String key, byte[] result){
		return new String(result) + "-" + key + ".tbl";
	}

}