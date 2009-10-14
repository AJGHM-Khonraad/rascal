package org.meta_environment.rascal.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.meta_environment.rascal.interpreter.utils.RuntimeExceptionFactory;
import org.meta_environment.values.ValueFactoryFactory;


public class List {
	private static final IValueFactory values = ValueFactoryFactory.getValueFactory();
	private static final TypeFactory types = TypeFactory.getInstance();
	private static final Random random = new Random();
	
	public static IValue delete(IList lst, IInteger n)
	// @doc{delete -- delete nth element from list}
	{
		try {
			return lst.delete(n.intValue());
		} catch (IndexOutOfBoundsException e){
			 throw RuntimeExceptionFactory.indexOutOfBounds(n, null, null);
		}
	}
	
	public static IValue domain(IList lst)
	//@doc{domain -- a list of all legal index values for a list}
	{
		ISetWriter w = values.setWriter(types.integerType());
		int len = lst.length();
		for (int i = 0; i < len; i++){
			w.insert(values.integer(i));
		}
		return w.done();
	}
	
	public static IValue head(IList lst)
	// @doc{head -- get the first element of a list}
	{
	   if(lst.length() > 0){
	      return lst.get(0);
	   }
	   
	   throw RuntimeExceptionFactory.emptyList(null, null);
	}

	public static IValue head(IList lst, IInteger n)
	  throws IndexOutOfBoundsException
	// @doc{head -- get the first n elements of a list}
	{
	   try {
	      return lst.sublist(0, n.intValue());
	   } catch(IndexOutOfBoundsException e){
		   IInteger end = values.integer(n.intValue() - 1);
	      throw RuntimeExceptionFactory.indexOutOfBounds(end, null, null);
	   }
	}

	public static IValue getOneFrom(IList lst)
	//@doc{getOneFrom -- get an arbitrary element from a list}
	{
		int n = lst.length();
		if(n > 0){
			return lst.get(random.nextInt(n));
		}
		
		throw RuntimeExceptionFactory.emptyList(null, null);
	}

	public static IValue insertAt(IList lst, IInteger n, IValue elm)
	  throws IndexOutOfBoundsException
	 //@doc{insertAt -- add an element at a specific position in a list}
	 {
	 	IListWriter w = values.listWriter(elm.getType().lub(lst.getElementType()));
	 	
	 	int k = n.intValue();
	    if(k >= 0 && k <= lst.length()){
	      if(k == lst.length()){
	      	w.insert(elm);
	      }
	      for(int i = lst.length()-1; i >= 0; i--) {
	        w.insert(lst.get(i));
	        if(i == k){
	        	w.insert(elm);
	        }
	      }
	      return w.done();
	    }
	    
		throw RuntimeExceptionFactory.indexOutOfBounds(n, null, null);
	 }
	
	public static IValue isEmpty(IList lst)
	//@doc{isEmpty -- is list empty?}
	{
		return values.bool(lst.length() == 0);
	}

	public static IValue reverse(IList lst)
	//@doc{reverse -- elements of a list in reverse order}
	{
		return lst.reverse();
	}

	public static IValue size(IList lst)
	//@doc{size -- number of elements in a list}
	{
	   return values.integer(lst.length());
	}

	 public static IValue slice(IList lst, IInteger start, IInteger len)
	 //@doc{slice -- sublist from start of length len}
	 {
		try {
			return lst.sublist(start.intValue(), len.intValue());
		} catch (IndexOutOfBoundsException e){
			IInteger end = values.integer(start.intValue() + len.intValue());
			throw RuntimeExceptionFactory.indexOutOfBounds(end, null, null);
		}
	 }

	 public static IValue tail(IList lst)
	 //@doc{tail -- all but the first element of a list}
	 {
	 	try {
	 		return lst.sublist(1, lst.length()-1);
	 	} catch (IndexOutOfBoundsException e){
	 		throw RuntimeExceptionFactory.emptyList(null, null);
	 	}
	 }
	 
	  public static IValue tail(IList lst, IInteger len)
	 //@doc{tail -- last n elements of a list}
	 {
	 	int lenVal = len.intValue();
	 	int lstLen = lst.length();
	 
	 	try {
	 		return lst.sublist(lstLen - lenVal, lenVal);
	 	} catch (IndexOutOfBoundsException e){
	 		IInteger end = values.integer(lenVal - lstLen);
	 		throw RuntimeExceptionFactory.indexOutOfBounds(end, null, null);
	 	}
	 }
	 
	public static IValue takeOneFrom(IList lst)
	//@doc{takeOneFrom -- remove an arbitrary element from a list, returns the element and the modified list}
	{
	   int n = lst.length();
	   
	   if(n > 0){
	   	  int k = random.nextInt(n);
	   	  IValue pick = lst.get(0);
	   	  IListWriter w = lst.getType().writer(values);
	  
	      for(int i = n - 1; i >= 0; i--) {
	         if(i == k){
	         	pick = lst.get(i);
	         } else {
	            w.insert(lst.get(i));
	         }
	      }
	      return values.tuple(pick, w.done());
	   	}
	   
	   throw RuntimeExceptionFactory.emptyList(null, null);
	}
	
	public static IValue toMap(IList lst)
	// @doc{toMap -- convert a list of tuples to a map; first value in old tuples is associated with a set of second values}
	{
		Type tuple = lst.getElementType();
		Type keyType = tuple.getFieldType(0);
		Type valueType = tuple.getFieldType(1);
		Type valueSetType = types.setType(valueType);

		HashMap<IValue,ISetWriter> hm = new HashMap<IValue,ISetWriter>();

		for (IValue v : lst) {
			ITuple t = (ITuple) v;
			IValue key = t.get(0);
			IValue val = t.get(1);
			ISetWriter wValSet = hm.get(key);
			if(wValSet == null){
				wValSet = valueSetType.writer(values);
				hm.put(key, wValSet);
			}
			wValSet.insert(val);
		}
		
		Type resultType = types.mapType(keyType, valueSetType);
		IMapWriter w = resultType.writer(values);
		for(IValue v : hm.keySet()){
			w.put(v, hm.get(v).done());
		}
		return w.done();
	}
	
	public static IValue toMapUnique(IList lst)
	//@doc{toMapUnique -- convert a list of tuples to a map; result should be a map}
	{
	   if(lst.length() == 0){
	      return values.map(types.voidType(), types.voidType());
	   }
	   Type tuple = lst.getElementType();
	   Type resultType = types.mapType(tuple.getFieldType(0), tuple.getFieldType(1));
	  
	   IMapWriter w = resultType.writer(values);
	   HashSet<IValue> seenKeys = new HashSet<IValue>();
	   for(IValue v : lst){
		   ITuple t = (ITuple) v;
		   IValue key = t.get(0);
		   if(seenKeys.contains(key)) 
				throw RuntimeExceptionFactory.MultipleKey(key, null, null);
		   seenKeys.add(key);
	     w.put(key, t.get(1));
	   }
	   return w.done();
	}

	public static IValue toSet(IList lst)
	//@doc{toSet -- convert a list to a set}
	{
	  Type resultType = types.setType(lst.getElementType());
	  ISetWriter w = resultType.writer(values);
	  
	  for(IValue v : lst){
	    w.insert(v);
	  }
		
	  return w.done();
	}

	public static IValue toString(IList lst)
	//@doc{toString -- convert a list to a string}
	{
		return values.string(lst.toString());
	}
	
}
