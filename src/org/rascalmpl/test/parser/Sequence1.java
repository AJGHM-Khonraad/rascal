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

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.rascalmpl.parser.gtd.SGTDBF;
import org.rascalmpl.parser.gtd.result.out.DefaultNodeFlattener;
import org.rascalmpl.parser.gtd.stack.AbstractStackNode;
import org.rascalmpl.parser.gtd.stack.LiteralStackNode;
import org.rascalmpl.parser.gtd.stack.NonTerminalStackNode;
import org.rascalmpl.parser.gtd.stack.SequenceStackNode;
import org.rascalmpl.parser.uptr.UPTRNodeFactory;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

/*
S ::= "a" ("b" "c") "d"

*/
public class Sequence1 extends SGTDBF<IConstructor, ISourceLocation> implements IParserTest{
	private final static IConstructor SYMBOL_START_S = VF.constructor(Factory.Symbol_Sort, VF.string("S"));
	private final static IConstructor SYMBOL_a = VF.constructor(Factory.Symbol_Lit, VF.string("a"));
	private final static IConstructor SYMBOL_char_a = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(97))));
	private final static IConstructor SYMBOL_b = VF.constructor(Factory.Symbol_Lit, VF.string("b"));
	private final static IConstructor SYMBOL_char_b = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(98))));
	private final static IConstructor SYMBOL_c = VF.constructor(Factory.Symbol_Lit, VF.string("c"));
	private final static IConstructor SYMBOL_char_c = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(99))));
	private final static IConstructor SYMBOL_SEQ_bc = VF.constructor(Factory.Symbol_Seq, VF.list(SYMBOL_b, SYMBOL_c));
	private final static IConstructor SYMBOL_d = VF.constructor(Factory.Symbol_Lit, VF.string("d"));
	private final static IConstructor SYMBOL_char_d = VF.constructor(Factory.Symbol_CharClass, VF.list(VF.constructor(Factory.CharRange_Single, VF.integer(100))));
	
	private final static IConstructor PROD_S_a_SEQbc_d = VF.constructor(Factory.Production_Default,  SYMBOL_START_S, VF.list(SYMBOL_a, SYMBOL_SEQ_bc, SYMBOL_d), VF.set());
	private final static IConstructor PROD_SEQbc = VF.constructor(Factory.Production_Regular, SYMBOL_SEQ_bc);
	private final static IConstructor PROD_a_a = VF.constructor(Factory.Production_Default,  SYMBOL_a, VF.list(SYMBOL_char_a), VF.set());
	private final static IConstructor PROD_b_b = VF.constructor(Factory.Production_Default,  SYMBOL_b, VF.list(SYMBOL_char_b), VF.set());
	private final static IConstructor PROD_c_c = VF.constructor(Factory.Production_Default,  SYMBOL_c, VF.list(SYMBOL_char_c), VF.set());
	private final static IConstructor PROD_d_d = VF.constructor(Factory.Production_Default,  SYMBOL_d, VF.list(SYMBOL_char_d), VF.set());
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(AbstractStackNode.START_SYMBOL_ID, 0, "S");
	private final static AbstractStackNode LITERAL_b2 = new LiteralStackNode(2, 0, PROD_b_b, new int[]{'b'});
	private final static AbstractStackNode LITERAL_c3 = new LiteralStackNode(3, 1, PROD_c_c, new int[]{'c'});
	
	private final static AbstractStackNode[] S_EXPECT_1 = new AbstractStackNode[3];
	static{
		S_EXPECT_1[0] = new LiteralStackNode(1, 0, PROD_a_a, new int[]{'a'});
		S_EXPECT_1[0].setProduction(S_EXPECT_1);
		S_EXPECT_1[1] = new SequenceStackNode(4, 1, PROD_SEQbc, new AbstractStackNode[]{LITERAL_b2, LITERAL_c3});
		S_EXPECT_1[1].setProduction(S_EXPECT_1);
		S_EXPECT_1[2] = new LiteralStackNode(5, 2, PROD_d_d, new int[]{'d'});
		S_EXPECT_1[2].setProduction(S_EXPECT_1);
		S_EXPECT_1[2].setAlternativeProduction(PROD_S_a_SEQbc_d);
	}
	
	public Sequence1(){
		super();
	}
	
	public AbstractStackNode[] S(){
		return new AbstractStackNode[]{S_EXPECT_1[0]};
	}
	
	public IConstructor executeParser(){
		return (IConstructor) parse(NONTERMINAL_START_S, null, "abcd".toCharArray(), new DefaultNodeFlattener<IConstructor, ISourceLocation>(), new UPTRNodeFactory());
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "appl(prod(sort(\"S\"),[lit(\"a\"),seq([lit(\"b\"),lit(\"c\")]),lit(\"d\")],{}),[appl(prod(lit(\"a\"),[\\char-class([single(97)])],{}),[char(97)]),appl(regular(seq([lit(\"b\"),lit(\"c\")])),[appl(prod(lit(\"b\"),[\\char-class([single(98)])],{}),[char(98)]),appl(prod(lit(\"c\"),[\\char-class([single(99)])],{}),[char(99)])]),appl(prod(lit(\"d\"),[\\char-class([single(100)])],{}),[char(100)])])";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.Tree, new StringReader(expectedInput));
	}
	
	public static void main(String[] args){
		Sequence1 s1 = new Sequence1();
		IConstructor result = s1.executeParser();
		System.out.println(result);
	}
}
