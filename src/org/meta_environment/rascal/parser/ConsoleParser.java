package org.meta_environment.rascal.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.meta_environment.rascal.ast.Import.Default;
import org.meta_environment.rascal.interpreter.load.ModuleLoader;

public class ConsoleParser extends ModuleParser {

	private Set<String> sdfImports = new HashSet<String>();
	private ModuleLoader loader = null;
	private final SdfImportExtractor importExtractor = new SdfImportExtractor();
	
	public ConsoleParser(ModuleLoader loader) {
		setLoader(loader);
	}

	public ConsoleParser() {
		super();
	}

	@Override
	public void setLoader(ModuleLoader loader) {
		this.loader = loader;
	}
	
	public IConstructor parseCommand(String command) throws IOException {
		String table = getOrConstructParseTable(META_LANGUAGE_KEY, sdfImports, getSdfSearchPath());
		return parseFromString(table, "-", command);
	}
	
	public void addSdfImportForImportDefault(Default x) {
		sdfImports.addAll(importExtractor.extractImports(x, getSdfSearchPath()));
	}

	private List<String> getSdfSearchPath() {
		return loader.getSdfSearchPath();
	}

	
	
}
