package org.meta_environment.rascal.std;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.IRelationWriter;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.meta_environment.rascal.interpreter.utils.RuntimeExceptionFactory;
import org.meta_environment.values.ValueFactoryFactory;


public class AUT{
	private static final IValueFactory values = ValueFactoryFactory.getValueFactory();
	private static final TypeFactory types = TypeFactory.getInstance();

	/*
	 * Read relations from an AUT file. An RSF file contains  ternary
	 * relations in the following format: "(" <int> "," <string> ","<int>")".
	 * 
	 * readAUT takes an AUT file nameAUTFile and generates a
	 * rel[int, str, int] 
	 */
	public static IValue readAUT(IString nameAUTFile){
		Type strType = types.stringType();
		Type intType = types.integerType();
		Type tupleType = types.tupleType(intType, strType, intType);
		java.lang.String fileName = nameAUTFile.getValue();
		IRelationWriter rw = values.relationWriter(tupleType);
		BufferedReader bufRead = null;
		try{
			FileReader input = new FileReader(fileName);
			bufRead = new BufferedReader(input);
			java.lang.String line = bufRead.readLine();
			line = bufRead.readLine();
			while (line != null) {
				java.lang.String[] fields = line.split("\\\"");
				java.lang.String[] field0 = fields[0].split("[\\(\\s,]");
				java.lang.String[] field2 = fields[2].split("[\\)\\s,]");
				rw.insert(values.tuple(values.integer(field0[1]), values.string(fields[1]), values
						.integer(field2[1])));
				line = bufRead.readLine();
			}
		}catch(IOException ioex){
			throw RuntimeExceptionFactory.io(values.string(ioex.getMessage()), null, null);
		}finally{
			if(bufRead != null){
				try{
					bufRead.close();
				}catch(IOException ioex){/* Ignore. */}
			}
		}
		return rw.done();
	}
	
	private static int numberOfStates(IRelation st){
		st.size();
		int r = 0;
		for(IValue v : st){
			ITuple t = (ITuple) v;
			IInteger from = (IInteger) t.get(0);
			IInteger to = (IInteger) t.get(2);
			if (from.intValue()>r) r = from.intValue();
			if (to.intValue()>r) r = to.intValue();
		}
		return r+1;
	}
	
	
	private static void printTransitions(PrintStream fos, IRelation st){
		fos.println("des(0,"+st.size()+","+numberOfStates(st)+")");
		for(IValue v : st){
			ITuple t = (ITuple) v;
			IInteger from = (IInteger) t.get(0);
			IString act = (IString) t.get(1);
			IInteger to = (IInteger) t.get(2);
			fos.print('(');
			fos.print(from.intValue());
			fos.print(',');
			fos.print("\""+act.getValue()+"\"");
			fos.print(',');
			fos.print(to.intValue());
			fos.print(')');
			fos.println();
		}	   
	   return;
	}
	   	  
	public static void writeAUT(IString nameAUTFile, IRelation value){
		java.lang.String fileName = nameAUTFile.getValue();
		
		PrintStream fos = null;
		try{
			File file = new File(fileName);
			fos = new PrintStream(file);
			printTransitions(fos, value);
		}catch(IOException ioex){
			throw RuntimeExceptionFactory.io(values.string(ioex.getMessage()), null, null);
		}finally{
			if(fos != null){
				fos.close();
			}
		}
	}

}
