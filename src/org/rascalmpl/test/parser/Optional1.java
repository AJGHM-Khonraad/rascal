package org.rascalmpl.test.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.parser.sgll.SGLL;
import org.rascalmpl.parser.sgll.stack.AbstractStackNode;
import org.rascalmpl.parser.sgll.stack.LiteralStackNode;
import org.rascalmpl.parser.sgll.stack.NonTerminalStackNode;
import org.rascalmpl.parser.sgll.stack.OptionalStackNode;
import org.rascalmpl.values.uptr.Factory;

/*
S ::= aO?
O ::= a
*/
public class Optional1 extends SGLL{
	private final static IConstructor SYMBOL_START_S = vf.constructor(Factory.Symbol_Sort, vf.string("S"));
	private final static IConstructor SYMBOL_O = vf.constructor(Factory.Symbol_Opt, vf.string("O"));
	private final static IConstructor SYMBOL_OPTIONAL_O = vf.constructor(Factory.Symbol_Opt, SYMBOL_O);
	private final static IConstructor SYMBOL_a = vf.constructor(Factory.Symbol_Lit, vf.string("a"));
	private final static IConstructor SYMBOL_char_a = vf.constructor(Factory.Symbol_CharClass, vf.list(vf.constructor(Factory.CharRange_Single, vf.integer(97))));
	
	private final static IConstructor PROD_S_aOPTIONAL_O = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_a, SYMBOL_OPTIONAL_O), SYMBOL_START_S, vf.list(Factory.Attributes));
	private final static IConstructor PROD_OPTIONAL_O_O = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_O), SYMBOL_OPTIONAL_O, vf.list(Factory.Attributes));
	private final static IConstructor PROD_O_a = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_a), SYMBOL_O, vf.list(Factory.Attributes));
	private final static IConstructor PROD_a_a = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_char_a), SYMBOL_a, vf.list(Factory.Attributes));
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(START_SYMBOL_ID, "S");
	private final static AbstractStackNode LITERAL_a0 = new LiteralStackNode(0, PROD_a_a, new char[]{'a'});
	private final static AbstractStackNode LITERAL_a1 = new LiteralStackNode(1, PROD_a_a, new char[]{'a'});
	private final static AbstractStackNode NON_TERMINAL_O2 = new NonTerminalStackNode(2, "O");
	private final static AbstractStackNode OPTIONAL_3 = new OptionalStackNode(3, PROD_OPTIONAL_O_O, NON_TERMINAL_O2);
	
	public Optional1(){
		super();
	}
	
	public void S(){
		expect(PROD_S_aOPTIONAL_O, LITERAL_a0, OPTIONAL_3);
	}
	
	public void O(){
		expect(PROD_O_a, LITERAL_a1);
	}
	
	public IValue parse(IConstructor start, char[] input){
		throw new UnsupportedOperationException();
	}
	
	public IValue parse(IConstructor start, File inputFile) throws IOException{
		throw new UnsupportedOperationException();
	}
	
	public IValue parse(IConstructor start, InputStream in) throws IOException{
		throw new UnsupportedOperationException();
	}
	
	public IValue parse(IConstructor start, Reader in) throws IOException{
		throw new UnsupportedOperationException();
	}
	
	public IValue parse(IConstructor start, String input){
		throw new UnsupportedOperationException();
	}
	
	public static void main(String[] args){
		Optional1 o1 = new Optional1();
		IValue result = o1.parse(NONTERMINAL_START_S, "aa".toCharArray());
		System.out.println(result);
		
		System.out.println("S(a,O?(O(a))) <- good");
	}
}
