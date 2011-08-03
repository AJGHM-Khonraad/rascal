/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

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
import org.rascalmpl.parser.gtd.stack.ListStackNode;
import org.rascalmpl.parser.gtd.stack.LiteralStackNode;
import org.rascalmpl.parser.gtd.stack.NonTerminalStackNode;
import org.rascalmpl.parser.uptr.NodeToUPTR;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

/*
* S ::= A*B*A*
* A ::= a
* B ::= b
*/
public class ListOverlap extends SGTDBF implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_A = VF.constructor(Factory.Symbol_Sort, VF.string("A"));
	private final static IConstructor SYMBOL_B = VF.constructor(Factory.Symbol_Sort, VF.string("B"));
	private final static IConstructor SYMBOL_STAR_LIST_A = VF.constructor(Factory.Symbol_IterStar, SYMBOL_A);
	private final static IConstructor SYMBOL_STAR_LIST_B = VF.constructor(Factory.Symbol_IterStar, SYMBOL_B);
	private final static IConstructor SYMBOL_b = VF.constructor(Factory.Symbol_Lit, VF.string("b"));
	private final static IConstructor SYMBOL_a = VF.constructor(Factory.Symbol_Lit, VF.string("a"));
	private final static IConstructor SYMBOL_char_a = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(97))));
	private final static IConstructor SYMBOL_char_b = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(98))));
	
	private final static IConstructor PROD_S_STARLISTASTARLISTBSTARLISTA = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_STAR_LIST_A, SYMBOL_STAR_LIST_B, SYMBOL_STAR_LIST_A), VF.set());
	private final static IConstructor PROD_STARLISTA = VF.constructor(Factory.Production_Regular, SYMBOL_STAR_LIST_A);
	private final static IConstructor PROD_STARLISTB = VF.constructor(Factory.Production_Regular, SYMBOL_STAR_LIST_B);
	private final static IConstructor PROD_A_a = VF.constructor(Factory.Production_Default,  SYMBOL_A, VF.list(SYMBOL_a), VF.set());
	private final static IConstructor PROD_B_b = VF.constructor(Factory.Production_Default,  SYMBOL_B, VF.list(SYMBOL_b), VF.set());
	private final static IConstructor PROD_a_a = VF.constructor(Factory.Production_Default,  SYMBOL_a, VF.list(SYMBOL_char_a), VF.set());
	private final static IConstructor PROD_b_b = VF.constructor(Factory.Production_Default,  SYMBOL_b, VF.list(SYMBOL_char_b), VF.set());
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_A2 = new NonTerminalStackNode(2, 0, "A");
	
	private final static AbstractStackNode[] S_EXPECT_1 = new AbstractStackNode[3];
	static{
		S_EXPECT_1[0] = new ListStackNode(3, 0, PROD_STARLISTA, NONTERMINAL_A0, false);
		S_EXPECT_1[0].setProduction(S_EXPECT_1);
		S_EXPECT_1[1] = new ListStackNode(4, 1, PROD_STARLISTB, NONTERMINAL_B1, false);
		S_EXPECT_1[1].setProduction(S_EXPECT_1);
		S_EXPECT_1[2] = new ListStackNode(5, 2, PROD_STARLISTA, NONTERMINAL_A2, false);
		S_EXPECT_1[2].setProduction(S_EXPECT_1);
		S_EXPECT_1[2].setParentProduction(PROD_S_STARLISTASTARLISTBSTARLISTA);
	}
	
	private final static AbstractStackNode[] A_EXPECT_1 = new AbstractStackNode[1];
	static{
		A_EXPECT_1[0] = new LiteralStackNode(6, 0, PROD_a_a, new char[]{'a'});
		A_EXPECT_1[0].setProduction(A_EXPECT_1);
		A_EXPECT_1[0].setParentProduction(PROD_A_a);
	}
	
	private final static AbstractStackNode[] B_EXPECT_1 = new AbstractStackNode[1];
	static{
		B_EXPECT_1[0] = new LiteralStackNode(7, 0, PROD_b_b, new char[]{'b'});
		B_EXPECT_1[0].setProduction(B_EXPECT_1);
		B_EXPECT_1[0].setParentProduction(PROD_B_b);
	}
	
	public ListOverlap(){
		super();
	}
	
	public void S(){
		expect(S_EXPECT_1[0]);
	}
	
	public void A(){
		expect(A_EXPECT_1[0]);
	}
	
	public void B(){
		expect(B_EXPECT_1[0]);
	}
	
	public IConstructor executeParser(){
		return (IConstructor) parse(NONTERMINAL_START_S, null, "aab".toCharArray(), new NodeToUPTR());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "appl(prod(sort(\"S\"),[\\iter-star(sort(\"A\")),\\iter-star(sort(\"B\")),\\iter-star(sort(\"A\"))],{}),[appl(regular(\\iter-star(sort(\"A\"))),[appl(prod(sort(\"A\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])]),appl(prod(sort(\"A\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])])]),appl(regular(\\iter-star(sort(\"B\"))),[appl(prod(sort(\"B\"),[lit(\"b\")],{}),[appl(prod(lit(\"b\"),[\\char-class([single(98)])],{}),[char(98)])])]),appl(regular(\\iter-star(sort(\"A\"))),[])])";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new ByteArrayInputStream(expectedInput.getBytes()));
	}
	
	public static void main(String[] args){
		ListOverlap lo = new ListOverlap();
		IConstructor result = lo.executeParser();
		System.out.println(result);
		
		System.out.println("S(A*(A(a),A(a)),B*(B(b)),A*()) <- good");
	}
}
