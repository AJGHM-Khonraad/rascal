package org.meta_environment.rascal.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.meta_environment.rascal.ast.Import.Default;
import org.meta_environment.rascal.interpreter.env.ModuleEnvironment;
import org.meta_environment.rascal.interpreter.load.ModuleLoader;

public class ConsoleParser extends ModuleParser {

	private Set<String> sdfImports = new HashSet<String>();
	private ModuleLoader loader = null;
	private final SdfImportExtractor importExtractor = new SdfImportExtractor();
	private final ModuleEnvironment shell;
	
	public ConsoleParser(ModuleLoader loader, ModuleEnvironment shell) {
		setLoader(loader);
		this.shell = shell;
	}

	public ConsoleParser(ModuleEnvironment shell) {
		super();
		this.shell = shell;
	}

	@Override
	public void setLoader(ModuleLoader loader) {
		this.loader = loader;
	}
	
	public IConstructor parseCommand(String command) throws IOException {
		generateModuleParser(getSdfSearchPath(), sdfImports, shell);
		TableInfo table = lookupTable(META_LANGUAGE_KEY, sdfImports, getSdfSearchPath());
		return parseFromString(table.getTableName(), "-", command, false);
	}
	
	public void addSdfImportForImportDefault(Default x) {
		sdfImports.addAll(importExtractor.extractImports(x, getSdfSearchPath()));
	}

	private List<String> getSdfSearchPath() {
		return loader.getSdfSearchPath();
	}

	
	
}
