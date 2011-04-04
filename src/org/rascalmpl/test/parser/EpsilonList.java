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
import org.rascalmpl.parser.gtd.stack.ListStackNode;
import org.rascalmpl.parser.gtd.stack.NonTerminalStackNode;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

/*
S ::= A+
A ::= epsilon
*/
public class EpsilonList extends SGTDBF implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_A = VF.constructor(Factory.Symbol_Sort, VF.string("A"));
	private final static IConstructor SYMBOL_PLUS_LIST_A = VF.constructor(Factory.Symbol_IterPlus, SYMBOL_A);
	private final static IConstructor SYMBOL_epsilon = VF.constructor(Factory.Symbol_Empty);
	
	private final static IConstructor PROD_S_PLUSLISTA = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_PLUS_LIST_A), SYMBOL_START_S, VF.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_PLUSLISTA = VF.constructor(Factory.Production_Regular, SYMBOL_PLUS_LIST_A, VF.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_A_epsilon = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_epsilon), SYMBOL_A, VF.constructor(Factory.Attributes_NoAttrs));
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode LIST1 = new ListStackNode(1, 0, PROD_PLUSLISTA, NONTERMINAL_A0, true);
	private final static AbstractStackNode EPSILON2 = new EpsilonStackNode(3, 0);
	
	public EpsilonList(){
		super();
	}
	
	public void S(){
		expect(PROD_S_PLUSLISTA, LIST1);
	}
	
	public void A(){
		expect(PROD_A_epsilon, EPSILON2);
	}
	
	public IConstructor executeParser(){
		return parse(NONTERMINAL_START_S, null, "".toCharArray());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "appl(prod([iter(sort(\"A\"))],sort(\"S\"),\\no-attrs()),[appl(regular(iter(sort(\"A\")),\\no-attrs()),[amb({appl(regular(iter(sort(\"A\")),\\no-attrs()),[appl(prod([empty()],sort(\"A\"),\\no-attrs()),[])]),cycle(iter(sort(\"A\")),1)})])])";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new ByteArrayInputStream(expectedInput.getBytes()));
	}
	
	public static void main(String[] args){
		EpsilonList el = new EpsilonList();
		IConstructor result = el.parse(NONTERMINAL_START_S, null, "".toCharArray());
		System.out.println(result);
		
		System.out.println("S([A+(A()),A+(repeat(A())))]) <- good");
	}
}
