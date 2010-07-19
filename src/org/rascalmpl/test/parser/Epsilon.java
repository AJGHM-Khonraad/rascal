package org.rascalmpl.test.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.rascalmpl.parser.sgll.SGLL;
import org.rascalmpl.parser.sgll.stack.AbstractStackNode;
import org.rascalmpl.parser.sgll.stack.EpsilonStackNode;
import org.rascalmpl.parser.sgll.stack.NonTerminalStackNode;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

/*
S ::= epsilon
*/
public class Epsilon extends SGLL implements IParserTest{
	private final static IConstructor SYMBOL_START_S = vf.constructor(Factory.Symbol_Sort, vf.string("S"));
	private final static IConstructor SYMBOL_epsilon = vf.constructor(Factory.Symbol_Empty);
	
	private final static IConstructor PROD_S_epsilon = vf.constructor(Factory.Production_Default, vf.list(SYMBOL_epsilon), SYMBOL_START_S, vf.constructor(Factory.Attributes_NoAttrs));
	
	private final static AbstractStackNode NONTERMINAL_START_S = new NonTerminalStackNode(START_SYMBOL_ID, "S");
	private final static AbstractStackNode EPSILON_1 = new EpsilonStackNode(1);
	
	public Epsilon(){
		super();
	}
	
	public void S(){
		expect(PROD_S_epsilon, EPSILON_1);
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
	
	public IValue executeParser(){
		return parse(NONTERMINAL_START_S, new char[]{});
	}
	
	public IValue getExpectedResult() throws IOException{
		String expectedInput = "parsetree(appl(prod([empty()],sort(\"S\"),\\no-attrs()),[]),-1)";
		return new StandardTextReader().read(ValueFactoryFactory.getValueFactory(), Factory.uptr, Factory.ParseTree, new ByteArrayInputStream(expectedInput.getBytes()));
	}

	public static void main(String[] args){
		Epsilon e = new Epsilon();
		IValue result = e.parse(NONTERMINAL_START_S, new char[]{});
		System.out.println(result);
		
		System.out.println("S() <- good");
	}
}