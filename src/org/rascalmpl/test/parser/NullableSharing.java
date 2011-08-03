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
import org.rascalmpl.parser.gtd.stack.EpsilonStackNode;
import org.rascalmpl.parser.gtd.stack.NonTerminalStackNode;
import org.rascalmpl.parser.uptr.NodeToUPTR;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

/*
* S ::= N N
* N ::= A
* A ::= epsilon
*/
public class NullableSharing extends SGTDBF implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_A = VF.constructor(Factory.Symbol_Sort, VF.string("A"));
	private final static IConstructor SYMBOL_N = VF.constructor(Factory.Symbol_Sort, VF.string("N"));
	private final static IConstructor SYMBOL_empty = VF.constructor(Factory.Symbol_Empty);
	
	private final static IConstructor PROD_S_NN = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_N, SYMBOL_N), VF.set());
	private final static IConstructor PROD_N_A = VF.constructor(Factory.Production_Default,  SYMBOL_N, VF.list(SYMBOL_A), VF.set());
	private final static IConstructor PROD_A_empty = VF.constructor(Factory.Production_Default,  SYMBOL_A, VF.list(SYMBOL_empty), VF.set());
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_N1 = new NonTerminalStackNode(1, 0, "N");
	private final static AbstractStackNode NONTERMINAL_N2 = new NonTerminalStackNode(2, 1, "N");
	private final static AbstractStackNode EPSILON3 = new EpsilonStackNode(3, 0);
	
	private final static AbstractStackNode[] S_EXPECT_1 = new AbstractStackNode[2];
	static{
		S_EXPECT_1[0] = NONTERMINAL_N1;
		S_EXPECT_1[0].setProduction(S_EXPECT_1);
		S_EXPECT_1[1] = NONTERMINAL_N2;
		S_EXPECT_1[1].setProduction(S_EXPECT_1);
		S_EXPECT_1[1].setParentProduction(PROD_S_NN);
	}
	
	private final static AbstractStackNode[] A_EXPECT_1 = new AbstractStackNode[1];
	static{
		A_EXPECT_1[0] = EPSILON3;
		A_EXPECT_1[0].setProduction(A_EXPECT_1);
		A_EXPECT_1[0].setParentProduction(PROD_A_empty);
	}
	
	private final static AbstractStackNode[] N_EXPECT_1 = new AbstractStackNode[1];
	static{
		N_EXPECT_1[0] = NONTERMINAL_A0;
		N_EXPECT_1[0].setProduction(N_EXPECT_1);
		N_EXPECT_1[0].setParentProduction(PROD_N_A);
	}
	
	public NullableSharing(){
		super();
	}
	
	public void S(){
		expect(S_EXPECT_1[0]);
	}
	
	public void A(){
		expect(A_EXPECT_1[0]);
	}
	
	public void N(){
		expect(N_EXPECT_1[0]);
	}
	
	public IConstructor executeParser(){
		return parse(NONTERMINAL_START_S, null, "".toCharArray(), new NodeToUPTR());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "appl(prod(sort(\"S\"),[sort(\"N\"),sort(\"N\")],{}),[appl(prod(sort(\"N\"),[sort(\"A\")],{}),[appl(prod(sort(\"A\"),[empty()],{}),[])]),appl(prod(sort(\"N\"),[sort(\"A\")],{}),[appl(prod(sort(\"A\"),[empty()],{}),[])])])";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new ByteArrayInputStream(expectedInput.getBytes()));
	}
	
	public static void main(String[] args){
		NullableSharing ns = new NullableSharing();
		IConstructor result = ns.parse(NONTERMINAL_START_S, null, "".toCharArray(), new NodeToUPTR());
		System.out.println(result);
		
		System.out.println("S(N(A()),N(A())) <- good");
	}
}
