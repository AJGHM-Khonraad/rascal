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
import org.rascalmpl.parser.gtd.stack.LiteralStackNode;
import org.rascalmpl.parser.gtd.stack.NonTerminalStackNode;
import org.rascalmpl.parser.uptr.NodeToUPTR;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

/*
S ::= A | B
A ::= B | a
B ::= A | a
*/
public class UselessSelfLoop extends SGTDBF implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_A = VF.constructor(Factory.Symbol_Sort, VF.string("A"));
	private final static IConstructor SYMBOL_B = VF.constructor(Factory.Symbol_Sort, VF.string("B"));
	private final static IConstructor SYMBOL_a = VF.constructor(Factory.Symbol_Lit, VF.string("a"));
	private final static IConstructor SYMBOL_char_a = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(97))));
	
	private final static IConstructor PROD_S_A = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_A), VF.set());
	private final static IConstructor PROD_S_B = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_B), VF.set());
	private final static IConstructor PROD_A_B = VF.constructor(Factory.Production_Default,  SYMBOL_A, VF.list(SYMBOL_B), VF.set());
	private final static IConstructor PROD_A_a = VF.constructor(Factory.Production_Default,  SYMBOL_A, VF.list(SYMBOL_a), VF.set());
	private final static IConstructor PROD_B_A = VF.constructor(Factory.Production_Default,  SYMBOL_B, VF.list(SYMBOL_A), VF.set());
	private final static IConstructor PROD_B_a = VF.constructor(Factory.Production_Default,  SYMBOL_B, VF.list(SYMBOL_a), VF.set());
	private final static IConstructor PROD_a_a = VF.constructor(Factory.Production_Default,  SYMBOL_a, VF.list(SYMBOL_char_a), VF.set());
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B3 = new NonTerminalStackNode(3, 0, "B");
	private final static AbstractStackNode LITERAL_a4 = new LiteralStackNode(4, 0, PROD_a_a, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a5 = new LiteralStackNode(5, 0, PROD_a_a, new char[]{'a'});
	
	private final static AbstractStackNode[] S_EXPECT_1 = new AbstractStackNode[1];
	static{
		S_EXPECT_1[0] = NONTERMINAL_A0;
		S_EXPECT_1[0].setProduction(S_EXPECT_1);
		S_EXPECT_1[0].setParentProduction(PROD_S_A);
	}
	
	private final static AbstractStackNode[] S_EXPECT_2 = new AbstractStackNode[1];
	static{
		S_EXPECT_2[0] = NONTERMINAL_B1;
		S_EXPECT_2[0].setProduction(S_EXPECT_2);
		S_EXPECT_2[0].setParentProduction(PROD_S_B);
	}
	
	private final static AbstractStackNode[] A_EXPECT_1 = new AbstractStackNode[1];
	static{
		A_EXPECT_1[0] = NONTERMINAL_B3;
		A_EXPECT_1[0].setProduction(A_EXPECT_1);
		A_EXPECT_1[0].setParentProduction(PROD_A_B);
	}
	
	private final static AbstractStackNode[] A_EXPECT_2 = new AbstractStackNode[1];
	static{
		A_EXPECT_2[0] = LITERAL_a4;
		A_EXPECT_2[0].setProduction(A_EXPECT_2);
		A_EXPECT_2[0].setParentProduction(PROD_A_a);
	}
	
	private final static AbstractStackNode[] B_EXPECT_1 = new AbstractStackNode[1];
	static{
		B_EXPECT_1[0] = NONTERMINAL_A2;
		B_EXPECT_1[0].setProduction(B_EXPECT_1);
		B_EXPECT_1[0].setParentProduction(PROD_B_A);
	}
	
	private final static AbstractStackNode[] B_EXPECT_2 = new AbstractStackNode[1];
	static{
		B_EXPECT_2[0] = LITERAL_a5;
		B_EXPECT_2[0].setProduction(B_EXPECT_2);
		B_EXPECT_2[0].setParentProduction(PROD_B_a);
	}
	
	public UselessSelfLoop(){
		super();
	}
	
	public void S(){
		expect(S_EXPECT_1[0]);
		expect(S_EXPECT_2[0]);
	}
	
	public void A(){
		expect(A_EXPECT_1[0]);
		expect(A_EXPECT_2[0]);
	}
	
	public void B(){
		expect(B_EXPECT_1[0]);
		expect(B_EXPECT_2[0]);
	}
	
	public IConstructor executeParser(){
		return parse(NONTERMINAL_START_S, null, "a".toCharArray(), new NodeToUPTR());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "amb({appl(prod(sort(\"S\"),[sort(\"B\")],{}),[amb({appl(prod(sort(\"B\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])]),appl(prod(sort(\"B\"),[sort(\"A\")],{}),[amb({appl(prod(sort(\"A\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])]),appl(prod(sort(\"A\"),[sort(\"B\")],{}),[cycle(sort(\"B\"),2)])})])})]),appl(prod(sort(\"S\"),[sort(\"A\")],{}),[amb({appl(prod(sort(\"A\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])]),appl(prod(sort(\"A\"),[sort(\"B\")],{}),[amb({appl(prod(sort(\"B\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])]),appl(prod(sort(\"B\"),[sort(\"A\")],{}),[cycle(sort(\"A\"),2)])})])})])})";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new ByteArrayInputStream(expectedInput.getBytes()));
	}

	public static void main(String[] args){
		UselessSelfLoop usl = new UselessSelfLoop();
		IConstructor result = usl.parse(NONTERMINAL_START_S, null, "a".toCharArray(), new NodeToUPTR());
		System.out.println(result);
		
		System.out.println("[S([A([B(cycle(A,2)),B(a)]),A(a)]),S([B([A(cycle(B,2)),A(a)]),B(a)])] <- good");
	}
}
