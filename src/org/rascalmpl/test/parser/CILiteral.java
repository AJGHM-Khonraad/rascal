/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.test.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.rascalmpl.parser.gtd.SGTDBF;
import org.rascalmpl.parser.gtd.stack.AbstractStackNode;
import org.rascalmpl.parser.gtd.stack.CaseInsensitiveLiteralStackNode;
import org.rascalmpl.parser.gtd.stack.NonTerminalStackNode;
import org.rascalmpl.parser.uptr.NodeToUPTR;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

/*
S ::= ci(bla)

NOTE: ci(*) means whatever * represents is Case Insensitive.
*/
public class CILiteral extends SGTDBF implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_A = VF.constructor(Factory.Symbol_Sort, VF.string("A"));
	private final static IConstructor SYMBOL_bla = VF.constructor(Factory.Symbol_CiLit, VF.string("bla"));
	private final static IConstructor SYMBOL_char_b = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(98))));
	private final static IConstructor SYMBOL_char_l = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(108))));
	private final static IConstructor SYMBOL_char_a = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(97))));
	
	private final static IConstructor PROD_S_A = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_A), VF.set());
	private final static IConstructor PROD_bla_bla = VF.constructor(Factory.Production_Default,  SYMBOL_bla, VF.list(SYMBOL_char_b, SYMBOL_char_l, SYMBOL_char_a), VF.set());
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode LITERAL_bla0 = new CaseInsensitiveLiteralStackNode(0, 0, PROD_bla_bla, new char[]{'b','l','a'});
	
	private final static AbstractStackNode[] S_EXPECT_1 = new AbstractStackNode[1];
	static{
		S_EXPECT_1[0] = LITERAL_bla0;
		S_EXPECT_1[0].setProduction(S_EXPECT_1);
		S_EXPECT_1[0].setParentProduction(PROD_S_A);
	}
	
	public CILiteral(){
		super();
	}
	
	public AbstractStackNode[] S(){
		return new AbstractStackNode[]{S_EXPECT_1[0]};
	}
	
	public IConstructor executeParser(){
		return (IConstructor) parse(NONTERMINAL_START_S, null, "Bla".toCharArray(), new NodeToUPTR());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "appl(prod(sort(\"S\"),[sort(\"A\")],{}),[appl(prod(cilit(\"bla\"),[\\char-class([single(98)]),\\char-class([single(108)]),\\char-class([single(97)])],{}),[char(66),char(108),char(97)])])";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new ByteArrayInputStream(expectedInput.getBytes()));
	}

	public static void main(String[] args){
		CILiteral cil = new CILiteral();
		IConstructor result = cil.executeParser();
		System.out.println(result);
		
		System.out.println("S(Bla) <- good");
	}
}
