package org.meta_environment.rascal.std;

import java.util.Iterator;
import java.util.Random;
import java.util.Map.Entry;

import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IRelationWriter;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.impl.reference.ValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.meta_environment.rascal.interpreter.errors.EmptyMapError;

public class Map {

	private static final ValueFactory values = ValueFactory.getInstance();
	private static final TypeFactory types = TypeFactory.getInstance();
	private static final Random random = new Random();
	

	public static IValue domain(IMap M)
	//@doc{domain -- return the domain (keys) of a map}

	{
	  Type keyType = M.getKeyType();
	  
	  Type resultType = types.setType(keyType);
	  ISetWriter w = resultType.writer(values);
	  Iterator<Entry<IValue,IValue>> iter = M.entryIterator();
	  while (iter.hasNext()) {
	    Entry<IValue,IValue> entry = iter.next();
	    w.insert((IValue)entry.getKey());
	  }
	  return w.done();
	}

	public static IValue getOneFrom(IMap m)  
	//@doc{getOneFrom -- return arbitrary key of a map}
	{
	   int i = 0;
	   int sz = m.size();
	   if(sz == 0){
	      throw new EmptyMapError("getOneFrom", null);
	   }
	   int k = random.nextInt(sz);
	   Iterator<Entry<IValue,IValue>> iter = m.entryIterator();
	  
	   while(iter.hasNext()){
	      if(i == k){
	      	return (IValue) (iter.next()).getKey();
	      }
	      iter.next();
	      i++;
	   }
	   return null;
	}

	public static  IValue range(IMap M)
	//@doc{range -- return the range (values) of a map}
	{
	  Type valueType = M.getValueType();
	  
	  Type resultType = types.setType(valueType);
	  ISetWriter w = resultType.writer(values);
	  Iterator<Entry<IValue,IValue>> iter = M.entryIterator();
	  while (iter.hasNext()) {
	    Entry<IValue,IValue> entry = iter.next();
	    w.insert((IValue)entry.getValue());
	  }
	  return w.done();
	}

	public static IValue size(IMap M)
	{
		return values.integer(M.size());
	}

	public static IValue toList(IMap M)
	//@doc{toList -- convert a map to a list}
	{
	  Type keyType = M.getKeyType();
	  Type valueType = M.getValueType();
	  Type elementType = types.tupleType(keyType,valueType);
	  
	  Type resultType = types.listType(elementType);
	  IListWriter w = resultType.writer(values);
	  Iterator<Entry<IValue,IValue>> iter = M.entryIterator();
	  while (iter.hasNext()) {
	    Entry<IValue,IValue> entry = iter.next();
	    w.insert(values.tuple((IValue)entry.getKey(), (IValue)entry.getValue()));
	  }
	  return w.done();
	}

	public static IValue toRel(IMap M)
	//@doc{toRel -- convert a map to a relation}
	{
	  Type keyType = M.getKeyType();
	  Type valueType = M.getValueType();
	  
	  Type resultType = types.relType(keyType, valueType);
	  IRelationWriter w = resultType.writer(values);
	  Iterator<Entry<IValue,IValue>> iter = M.entryIterator();
	  while (iter.hasNext()) {
	    Entry<IValue,IValue> entry = iter.next();
	    w.insert(values.tuple((IValue)entry.getKey(), (IValue)entry.getValue()));
	  }
	  return w.done();
	}
	  
	public static IValue toString(IMap M)
	{
	  return values.string(M.toString());
	}

}
