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
S ::= D | Da
D ::= C
C ::= Baa | Ba
B ::= A
A ::= a
*/
public class SplitAndMerge2 extends SGTDBF implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_A = VF.constructor(Factory.Symbol_Sort, VF.string("A"));
	private final static IConstructor SYMBOL_B = VF.constructor(Factory.Symbol_Sort, VF.string("B"));
	private final static IConstructor SYMBOL_C = VF.constructor(Factory.Symbol_Sort, VF.string("C"));
	private final static IConstructor SYMBOL_D = VF.constructor(Factory.Symbol_Sort, VF.string("D"));
	private final static IConstructor SYMBOL_a = VF.constructor(Factory.Symbol_Lit, VF.string("a"));
	private final static IConstructor SYMBOL_aa = VF.constructor(Factory.Symbol_Lit, VF.string("aa"));
	private final static IConstructor SYMBOL_char_a = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(97))));
	
	private final static IConstructor PROD_S_D = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_D), VF.set());
	private final static IConstructor PROD_S_Da = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_D, SYMBOL_a), VF.set());
	private final static IConstructor PROD_D_C = VF.constructor(Factory.Production_Default,  SYMBOL_D, VF.list(SYMBOL_C), VF.set());
	private final static IConstructor PROD_C_Ba = VF.constructor(Factory.Production_Default,  SYMBOL_C, VF.list(SYMBOL_B, SYMBOL_aa), VF.set());
	private final static IConstructor PROD_C_Baa = VF.constructor(Factory.Production_Default,  SYMBOL_C, VF.list(SYMBOL_B, SYMBOL_a), VF.set());
	private final static IConstructor PROD_a_a = VF.constructor(Factory.Production_Default,  SYMBOL_a, VF.list(SYMBOL_char_a), VF.set());
	private final static IConstructor PROD_aa_aa = VF.constructor(Factory.Production_Default,  SYMBOL_aa, VF.list(SYMBOL_char_a, SYMBOL_char_a), VF.set());
	private final static IConstructor PROD_B_A = VF.constructor(Factory.Production_Default,  SYMBOL_B, VF.list(SYMBOL_A), VF.set());
	private final static IConstructor PROD_A_a = VF.constructor(Factory.Production_Default,  SYMBOL_A, VF.list(SYMBOL_a), VF.set());
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_B2 = new NonTerminalStackNode(2, 0, "B");
	private final static AbstractStackNode NONTERMINAL_C3 = new NonTerminalStackNode(3, 0, "C");
	private final static AbstractStackNode NONTERMINAL_D4 = new NonTerminalStackNode(4, 0, "D");
	private final static AbstractStackNode NONTERMINAL_D5 = new NonTerminalStackNode(5, 0, "D");
	private final static AbstractStackNode LITERAL_a6 = new LiteralStackNode(6, 1, PROD_a_a, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a7 = new LiteralStackNode(7, 0, PROD_a_a, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a8 = new LiteralStackNode(8, 1, PROD_a_a, new char[]{'a'});
	private final static AbstractStackNode LITERAL_aa9 = new LiteralStackNode(9, 1, PROD_aa_aa, new char[]{'a','a'});
	
	public SplitAndMerge2(){
		super();
	}
	
	public void S(){
		expect(PROD_S_D, NONTERMINAL_D4);
		
		expect(PROD_S_Da, NONTERMINAL_D5, LITERAL_a6);
	}
	
	public void A(){
		expect(PROD_A_a, LITERAL_a7);
	}
	
	public void B(){
		expect(PROD_B_A, NONTERMINAL_A0);
	}
	
	public void C(){
		expect(PROD_C_Ba, NONTERMINAL_B1, LITERAL_a8);
		
		expect(PROD_C_Baa, NONTERMINAL_B2, LITERAL_aa9);
	}
	
	public void D(){
		expect(PROD_D_C, NONTERMINAL_C3);
	}
	
	public IConstructor executeParser(){
		return parse(NONTERMINAL_START_S, null, "aaa".toCharArray(), new NodeToUPTR());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "amb({appl(prod(sort(\"S\"),[sort(\"D\"),lit(\"a\")],{}),[appl(prod(sort(\"D\"),[sort(\"C\")],{}),[appl(prod(sort(\"C\"),[sort(\"B\"),lit(\"aa\")],{}),[appl(prod(sort(\"B\"),[sort(\"A\")],{}),[appl(prod(sort(\"A\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])])]),appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])])]),appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])]),appl(prod(sort(\"S\"),[sort(\"D\")],{}),[appl(prod(sort(\"D\"),[sort(\"C\")],{}),[appl(prod(sort(\"C\"),[sort(\"B\"),lit(\"a\")],{}),[appl(prod(sort(\"B\"),[sort(\"A\")],{}),[appl(prod(sort(\"A\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])])]),appl(prod(lit(\"aa\"),[\\char-class([single(97)]),\\char-class([single(97)])],{}),[char(97),char(97)])])])])})";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new ByteArrayInputStream(expectedInput.getBytes()));
	}

	public static void main(String[] args){
		SplitAndMerge2 ms2 = new SplitAndMerge2();
		IConstructor result = ms2.parse(NONTERMINAL_START_S, null, "aaa".toCharArray(), new NodeToUPTR());
		System.out.println(result);
		
		System.out.println("[S(D(C(B(A(a)),aa))),S(D(C(B(A(a)),a)),a)] <- good");
	}
}
