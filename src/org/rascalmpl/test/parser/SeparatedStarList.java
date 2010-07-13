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
import org.rascalmpl.parser.sgll.stack.SeparatedListStackNode;
import org.rascalmpl.values.uptr.Factory;

/*
S ::= sep(A, b)*
A ::= a

sep(X, Y) means, a list of X separated by Y's.
*/
public class SeparatedStarList extends SGLL implements IParserTest{
	private final static IConstructor SYMBOL_START_S = vf.constructor(Factory.Symbol_Sort, vf.string("S"));
	private final static IConstructor SYMBOL_A = vf.constructor(Factory.Symbol_Sort, vf.string("A"));
	private final static IConstructor SYMBOL_b = vf.constructor(Factory.Symbol_Lit, vf.string("b"));
	private final static IConstructor SYMBOL_SEP_STAR_LIST_A = vf.constructor(Factory.Symbol_IterStarSep, SYMBOL_A, vf.list(SYMBOL_b));
	private final static IConstructor SYMBOL_a = vf.constructor(Factory.Symbol_Lit, vf.string("a"));
	private final static IConstructor SYMBOL_char_a = vf.constructor(Factory.Symbol_CharClass, vf.list(vf.constructor(Factory.CharRange_Single, vf.integer(97))));
	private final static IConstructor SYMBOL_char_b = vf.constructor(Factory.Symbol_CharClass, vf.list(vf.constructor(Factory.CharRange_Single, vf.integer(98))));
	
	private final static IConstructor PROD_S_SEPSTARLIST_A_b = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_SEP_STAR_LIST_A), SYMBOL_START_S, vf.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_SEPSTARLIST_A_b = vf.constructor(Factory.Production_List, vf.list(SYMBOL_A, vf.list(SYMBOL_b)), vf.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_A_a = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_a), SYMBOL_A, vf.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_a_a = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_char_a), SYMBOL_a, vf.constructor(Factory.Attributes_NoAttrs));
	private final static IConstructor PROD_b_b = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_char_b), SYMBOL_b, vf.constructor(Factory.Attributes_NoAttrs));
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(START_SYMBOL_ID, "S");
	private final static AbstractStackNode NONTERMINAL_A0 = new NonTerminalStackNode(0, "A");
	private final static AbstractStackNode LITERAL_b1 = new LiteralStackNode(1, PROD_b_b, new char[]{'b'});
	private final static AbstractStackNode LIST2 = new SeparatedListStackNode(2, PROD_SEPSTARLIST_A_b, NONTERMINAL_A0, new AbstractStackNode[]{LITERAL_b1}, false);
	private final static AbstractStackNode LITERAL_a3 = new LiteralStackNode(3, PROD_a_a, new char[]{'a'});
	
	public SeparatedStarList(){
		super();
	}
	
	public void S(){
		expect(PROD_S_SEPSTARLIST_A_b, LIST2);
	}
	
	public void A(){
		expect(PROD_A_a, LITERAL_a3);
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
	
	public boolean executeTest(){
		SeparatedStarList nrsl = new SeparatedStarList();
		IValue result = nrsl.parse(NONTERMINAL_START_S, "ababa".toCharArray());
		return result.equals("TODO");
	}

	public static void main(String[] args){
		SeparatedStarList nrsl = new SeparatedStarList();
		IValue result = nrsl.parse(NONTERMINAL_START_S, "ababa".toCharArray());
		System.out.println(result);
		
		System.out.println("S((Ab)*(A(a),b,(Ab)*(A(a),b,(Ab)*(A(a))))) <- good");
	}
}
