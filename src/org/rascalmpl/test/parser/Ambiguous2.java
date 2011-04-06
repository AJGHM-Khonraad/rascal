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
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

/*
S ::= Aab | bab
A ::= B
B ::= b
*/
public class Ambiguous2 extends SGTDBF implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_A = VF.constructor(Factory.Symbol_Sort, VF.string("A"));
	private final static IConstructor SYMBOL_B = VF.constructor(Factory.Symbol_Sort, VF.string("B"));
	private final static IConstructor SYMBOL_b = VF.constructor(Factory.Symbol_Lit, VF.string("b"));
	private final static IConstructor SYMBOL_ab = VF.constructor(Factory.Symbol_Lit, VF.string("ab"));
	private final static IConstructor SYMBOL_bab = VF.constructor(Factory.Symbol_Lit, VF.string("bab"));
	private final static IConstructor SYMBOL_char_a = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(97))));
	private final static IConstructor SYMBOL_char_b = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(98))));
	
	private final static IConstructor PROD_S_Aab = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_A, SYMBOL_ab), SYMBOL_START_S, VF.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_S_bab = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_bab), SYMBOL_START_S, VF.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_A_B = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_B), SYMBOL_A, VF.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_B_b = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_b), SYMBOL_B, VF.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_b_b = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_char_b), SYMBOL_b, VF.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_ab_ab = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_char_b, SYMBOL_char_a, SYMBOL_char_b), SYMBOL_ab, VF.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_bab_bab = VF.constructor(Factory.Production_Default, VF.list(SYMBOL_char_b, SYMBOL_char_a, SYMBOL_char_b), SYMBOL_bab, VF.constructor(Factory.Attributes_NoAttrs));
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, 0, "A");
	private final static AbstractStackNode NONTERMINAL_B1 = new NonTerminalStackNode(1, 0, "B");
	private final static AbstractStackNode LITERAL_b2 = new LiteralStackNode(2, 0, PROD_b_b, new char[]{'b'});
	private final static AbstractStackNode LITERALL_ab3 = new LiteralStackNode(3, 1, PROD_ab_ab, new char[]{'a','b'});
	private final static AbstractStackNode LITERAL_bab4 = new LiteralStackNode(4, 0, PROD_bab_bab, new char[]{'b','a','b'});
	
	public Ambiguous2(){
		super();
	}
	
	public void S(){
		expect(PROD_S_Aab, NONTERMINAL_A0, LITERALL_ab3);
		
		expect(PROD_S_bab, LITERAL_bab4);
	}
	
	public void A(){
		expect(PROD_A_B, NONTERMINAL_B1);
	}
	
	public void B(){
		expect(PROD_B_b, LITERAL_b2);
	}
	
	public IConstructor executeParser(){
		return parse(NONTERMINAL_START_S, null, "bab".toCharArray());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "amb({appl(prod([sort(\"A\"),lit(\"ab\")],sort(\"S\"),\\no-attrs()),[appl(prod([sort(\"B\")],sort(\"A\"),\\no-attrs()),[appl(prod([lit(\"b\")],sort(\"B\"),\\no-attrs()),[appl(prod([\\char-class([single(98)])],lit(\"b\"),\\no-attrs()),[char(98)])])]),appl(prod([\\char-class([single(98)]),\\char-class([single(97)]),\\char-class([single(98)])],lit(\"ab\"),\\no-attrs()),[char(97),char(98)])]),appl(prod([lit(\"bab\")],sort(\"S\"),\\no-attrs()),[appl(prod([\\char-class([single(98)]),\\char-class([single(97)]),\\char-class([single(98)])],lit(\"bab\"),\\no-attrs()),[char(98),char(97),char(98)])])})";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new ByteArrayInputStream(expectedInput.getBytes()));
	}

	public static void main(String[] args){
		Ambiguous2 a2 = new Ambiguous2();
		IConstructor result = a2.parse(NONTERMINAL_START_S, null, "bab".toCharArray());
		System.out.println(result);
		
		System.out.println("[S(bab),S(A(B(b)),ab)] <- good");
	}
}
