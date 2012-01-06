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
S ::= A | E
A ::= B
B ::= C
C ::= D
D ::= E | a
E ::= F
F ::= G
G ::= a
*/
public class Ambiguous6 extends SGTDBF implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_A = VF.constructor(Factory.Symbol_Sort, VF.string("A"));
	private final static IConstructor SYMBOL_B = VF.constructor(Factory.Symbol_Sort, VF.string("B"));
	private final static IConstructor SYMBOL_C = VF.constructor(Factory.Symbol_Sort, VF.string("C"));
	private final static IConstructor SYMBOL_D = VF.constructor(Factory.Symbol_Sort, VF.string("D"));
	private final static IConstructor SYMBOL_E = VF.constructor(Factory.Symbol_Sort, VF.string("E"));
	private final static IConstructor SYMBOL_F = VF.constructor(Factory.Symbol_Sort, VF.string("F"));
	private final static IConstructor SYMBOL_G = VF.constructor(Factory.Symbol_Sort, VF.string("G"));
	private final static IConstructor SYMBOL_a = VF.constructor(Factory.Symbol_Lit, VF.string("a"));
	private final static IConstructor SYMBOL_char_a = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(97))));
	
	private final static IConstructor PROD_S_A = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_A), VF.set());
	private final static IConstructor PROD_S_E = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_E), VF.set());
	private final static IConstructor PROD_A_B = VF.constructor(Factory.Production_Default,  SYMBOL_A, VF.list(SYMBOL_B), VF.set());
	private final static IConstructor PROD_B_C = VF.constructor(Factory.Production_Default,  SYMBOL_B, VF.list(SYMBOL_C), VF.set());
	private final static IConstructor PROD_C_D = VF.constructor(Factory.Production_Default,  SYMBOL_C, VF.list(SYMBOL_D), VF.set());
	private final static IConstructor PROD_D_E = VF.constructor(Factory.Production_Default,  SYMBOL_D, VF.list(SYMBOL_E), VF.set());
	private final static IConstructor PROD_D_a = VF.constructor(Factory.Production_Default,  SYMBOL_D, VF.list(SYMBOL_a), VF.set());
	private final static IConstructor PROD_E_F = VF.constructor(Factory.Production_Default,  SYMBOL_E, VF.list(SYMBOL_F), VF.set());
	private final static IConstructor PROD_F_G = VF.constructor(Factory.Production_Default,  SYMBOL_F, VF.list(SYMBOL_G), VF.set());
	private final static IConstructor PROD_G_a = VF.constructor(Factory.Production_Default,  SYMBOL_G, VF.list(SYMBOL_a), VF.set());
	private final static IConstructor PROD_a_a = VF.constructor(Factory.Production_Default,  SYMBOL_a, VF.list(SYMBOL_char_a), VF.set());
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode NONTERMINAL_C2 = new NonTerminalStackNode(2, 0, "C");
	private final static AbstractStackNode NONTERMINAL_D3 = new NonTerminalStackNode(3, 0, "D");
	private final static AbstractStackNode NONTERMINAL_E4 = new NonTerminalStackNode(4, 0, "E");
	private final static AbstractStackNode NONTERMINAL_E5 = new NonTerminalStackNode(5, 0, "E");
	private final static AbstractStackNode NONTERMINAL_F6 = new NonTerminalStackNode(6, 0, "F");
	private final static AbstractStackNode NONTERMINAL_G7 = new NonTerminalStackNode(7, 0, "G");
	private final static AbstractStackNode LITERAL_a8 = new LiteralStackNode(8, 0, PROD_a_a, new int[]{'a'});
	private final static AbstractStackNode LITERAL_a9 = new LiteralStackNode(9, 0, PROD_a_a, new int[]{'a'});
	
	private final static AbstractStackNode[] S_EXPECT_1 = new AbstractStackNode[1];
	static{
		S_EXPECT_1[0] = NONTERMINAL_A0;
		S_EXPECT_1[0].setProduction(S_EXPECT_1);
		S_EXPECT_1[0].setParentProduction(PROD_S_A);
	}
	
	private final static AbstractStackNode[] S_EXPECT_2 = new AbstractStackNode[1];
	static{
		S_EXPECT_2[0] = NONTERMINAL_E4;
		S_EXPECT_2[0].setProduction(S_EXPECT_2);
		S_EXPECT_2[0].setParentProduction(PROD_S_E);
	}
	
	private final static AbstractStackNode[] A_EXPECT_1 = new AbstractStackNode[1];
	static{
		A_EXPECT_1[0] = NONTERMINAL_B1;
		A_EXPECT_1[0].setProduction(A_EXPECT_1);
		A_EXPECT_1[0].setParentProduction(PROD_A_B);
	}
	
	private final static AbstractStackNode[] B_EXPECT_1 = new AbstractStackNode[1];
	static{
		B_EXPECT_1[0] = NONTERMINAL_C2;
		B_EXPECT_1[0].setProduction(B_EXPECT_1);
		B_EXPECT_1[0].setParentProduction(PROD_B_C);
	}
	
	private final static AbstractStackNode[] C_EXPECT_1 = new AbstractStackNode[1];
	static{
		C_EXPECT_1[0] = NONTERMINAL_D3;
		C_EXPECT_1[0].setProduction(C_EXPECT_1);
		C_EXPECT_1[0].setParentProduction(PROD_C_D);
	}
	
	private final static AbstractStackNode[] D_EXPECT_1 = new AbstractStackNode[1];
	static{
		D_EXPECT_1[0] = NONTERMINAL_E5;
		D_EXPECT_1[0].setProduction(D_EXPECT_1);
		D_EXPECT_1[0].setParentProduction(PROD_D_E);
	}
	
	private final static AbstractStackNode[] D_EXPECT_2 = new AbstractStackNode[1];
	static{
		D_EXPECT_2[0] = LITERAL_a8;
		D_EXPECT_2[0].setProduction(D_EXPECT_2);
		D_EXPECT_2[0].setParentProduction(PROD_D_a);
	}
	
	private final static AbstractStackNode[] E_EXPECT_1 = new AbstractStackNode[1];
	static{
		E_EXPECT_1[0] = NONTERMINAL_F6;
		E_EXPECT_1[0].setProduction(E_EXPECT_1);
		E_EXPECT_1[0].setParentProduction(PROD_E_F);
	}
	
	private final static AbstractStackNode[] F_EXPECT_1 = new AbstractStackNode[1];
	static{
		F_EXPECT_1[0] = NONTERMINAL_G7;
		F_EXPECT_1[0].setProduction(F_EXPECT_1);
		F_EXPECT_1[0].setParentProduction(PROD_F_G);
	}
	
	private final static AbstractStackNode[] G_EXPECT_1 = new AbstractStackNode[1];
	static{
		G_EXPECT_1[0] = LITERAL_a9;
		G_EXPECT_1[0].setProduction(G_EXPECT_1);
		G_EXPECT_1[0].setParentProduction(PROD_G_a);
	}
	
	public Ambiguous6(){
		super();
	}
	
	public AbstractStackNode[] S(){
		return new AbstractStackNode[]{S_EXPECT_1[0], S_EXPECT_2[0]};
	}
	
	public AbstractStackNode[] A(){
		return new AbstractStackNode[]{A_EXPECT_1[0]};
	}
	
	public AbstractStackNode[] B(){
		return new AbstractStackNode[]{B_EXPECT_1[0]};
	}
	
	public AbstractStackNode[] C(){
		return new AbstractStackNode[]{C_EXPECT_1[0]};
	}
	
	public AbstractStackNode[] D(){
		return new AbstractStackNode[]{D_EXPECT_1[0], D_EXPECT_2[0]};
	}
	
	public AbstractStackNode[] E(){
		return new AbstractStackNode[]{E_EXPECT_1[0]};
	}
	
	public AbstractStackNode[] F(){
		return new AbstractStackNode[]{F_EXPECT_1[0]};
	}
	
	public AbstractStackNode[] G(){
		return new AbstractStackNode[]{G_EXPECT_1[0]};
	}
	
	public IConstructor executeParser(){
		return (IConstructor) parse(NONTERMINAL_START_S, null, "a".toCharArray(), new NodeToUPTR());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "amb({appl(prod(sort(\"S\"),[sort(\"A\")],{}),[appl(prod(sort(\"A\"),[sort(\"B\")],{}),[appl(prod(sort(\"B\"),[sort(\"C\")],{}),[appl(prod(sort(\"C\"),[sort(\"D\")],{}),[amb({appl(prod(sort(\"D\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])]),appl(prod(sort(\"D\"),[sort(\"E\")],{}),[appl(prod(sort(\"E\"),[sort(\"F\")],{}),[appl(prod(sort(\"F\"),[sort(\"G\")],{}),[appl(prod(sort(\"G\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])])])])])})])])])]),appl(prod(sort(\"S\"),[sort(\"E\")],{}),[appl(prod(sort(\"E\"),[sort(\"F\")],{}),[appl(prod(sort(\"F\"),[sort(\"G\")],{}),[appl(prod(sort(\"G\"),[lit(\"a\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)])])])])])})";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new ByteArrayInputStream(expectedInput.getBytes()));
	}

	public static void main(String[] args){
		Ambiguous6 a6 = new Ambiguous6();
		IConstructor result = a6.executeParser();
		System.out.println(result);
		
		System.out.println("[S(A(B(C([D(E(F(G(a)))),D(a)])))),S(E(F(G(a))))] <- good");
	}
}
