/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Bas Basten - Bas.Basten@cwi.nl (CWI)
 *   * Jouke Stoel - Jouke.Stoel@cwi.nl (CWI)
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
 *   * Anastasia Izmaylova - A.Izmaylova@cwi.nl - CWI
*******************************************************************************/
package org.rascalmpl.library.experiments.m3.internal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.interpreter.control_exceptions.Throw;
import org.rascalmpl.interpreter.utils.RuntimeExceptionFactory;
import org.rascalmpl.library.experiments.m3.internal.JavaToRascalConverter;

public class JDT {
    private final IValueFactory VF;
    List<String> classPathEntries;
    List<String> sourcePathEntries;
	
    public JDT(IValueFactory vf) {
    	this.VF = vf;
	}
    
    public void setEnvironmentOptions(ISet classPaths, ISet sourcePaths) {
    	this.classPathEntries = new ArrayList<String>();
    	this.sourcePathEntries = new ArrayList<String>();
    	
    	for (IValue path: classPaths) {
    		classPathEntries.add(((ISourceLocation)path).getURI().getPath());
    	}
    	
    	for (IValue path: sourcePaths) {
    		sourcePathEntries.add(((ISourceLocation)path).getURI().getPath());
    	}
    }
    
	/*
	 * Creates Rascal ASTs for Java source files
	 */
	public IValue createAstFromFile(ISourceLocation loc, IBool collectBindings, IString projectName, IEvaluatorContext eval) {
		CompilationUnit cu = this.getCompilationUnit(loc, collectBindings.getValue());
		JavaToRascalConverter converter = new JavaToRascalConverter(eval.getHeap().getModule("experiments::m3::AST").getStore(),
												collectBindings.getValue());
		converter.set(cu);
		converter.set(loc);
		cu.accept(converter);
		return converter.getValue();
	}
	
	private CompilationUnit getCompilationUnit(ISourceLocation loc, boolean resolveBindings) {
		
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setUnitName(loc.getURI().getPath());
		parser.setResolveBindings(resolveBindings);
		parser.setSource(getFileContents(loc));
		parser.setBindingsRecovery(resolveBindings);
		parser.setStatementsRecovery(resolveBindings);
		
//		Hashtable options = new Hashtable();
//		options.put(JavaCore.COMPILER_SOURCE, "1.7");
//		options.put(JavaCore.COMPILER_COMPLIANCE, "1.7");
//		
//		parser.setCompilerOptions(options);
		
		parser.setEnvironment(classPathEntries.toArray(new String[classPathEntries.size()]), 
				  sourcePathEntries.toArray(new String[sourcePathEntries.size()]),
				  null, true);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		int i;
		IProblem[] problems = cu.getProblems();
		for (i = 0; i < problems.length; i++) {
			if (problems[i].isError()) {
				int offset = problems[i].getSourceStart();
				int length = problems[i].getSourceEnd() - offset;
				int sl = problems[i].getSourceLineNumber();
				ISourceLocation pos = VF.sourceLocation(loc.getURI(), offset, length, sl, sl, 0, 0);
				// Continue with partial ast on error
				System.err.println(VF.string("Error(s) in compilation unit: " + problems[i].getMessage()) + "\n" + pos);
			}
		}
		
		return cu;
	}
	
	private char[] getFileContents(ISourceLocation loc) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(loc.getURI().getPath()));
			
			String line = "";
			while((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			System.err.println(e.toString());
			throw new Throw(VF.string("Cannot read file: " + loc), (ISourceLocation) null, null);
		}
		return sb.toString().toCharArray();
	}
}
