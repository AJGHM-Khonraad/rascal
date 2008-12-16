module Set

public set[&T] java add(&T elm, set[&T] st)
@doc{add -- add an element to a set}
 {
    return st.insert(elm);
 }
 
public &T average(set[&T] st, &T zero)
@doc{average -- compute the average of the elements of a set}
{
  return sum(st, zero)/size(st);
}

public &T java getOneFrom(set[&T] st)
@doc{getOneFrom -- pick a random element from a set}
@java-imports{import java.util.Iterator;}
{
   int i = 0;
   int k = random.nextInt(st.size());
   Iterator iter = st.iterator();
  
   while(iter.hasNext()){
      if(i == k){
      	return (IValue) iter.next();
      }
      iter.next();
      i++;
   }
   return null;
}

public set[&T] mapper(set[&T] st, &T (&T,&T) fn)
@doc{mapper -- apply a function to each element of a set}
{
  return {#f(elm) | &T elm : st};
}

public &T max(set[&T] st)
@doc{max -- largest element of a set}
{
  &T result = getOneFrom(st);
  for(&T elm : st){
  	if(elm > result){
    	result = elm;
    }
  }
  return result;
}

public &T min(set[&T] st)
@doc{min -- smallest element of a set}
{
  &T result = getOneFrom(st);
  for(&T elm : st){
   if(elm < result){
      result = elm;
   }
  }
  return result;
}

public &T multiply(set[&T] st, &T unity)
@doc{multiply -- multiply the elements of a set}
{
  return reducer(st, #*, unity);
}

public set[set[&T]] power(set[&T] st)
@doc{power -- return all subsets of a set}
{
  set[set[&T]] result = {st};
  for(&T elm : st){
  	set[set[&T]] pw = power(st - {elm});
  	result = result + pw;
  	for(set[&T] sub : pw){
  		result = result + {sub + {elm}};
  	}
  }
  return result;
}

public &T reducer(set[&T] st, &T (&T,&T) fn, &T unit)
@doc{reducer -- apply function F to successive elements of a set}
{
  &T result = unit;
  for(&T elm : st){
    result = #fn(result, elm);
  }
  return result;
}

public int java size(set[&T] st)
@doc{size -- number of elements in a set}
{
   return values.integer(st.size());
}
 
public &T sum(set[&T] st, &T zero)
@doc{sum -- add the elements of a set}
{
  return reducer(st, #+, zero);
}

public tuple[&T, set[&T]] java takeOneFrom(set[&T] st)
@doc{takeOneFrom -- remove an arbitrary element from a set, returns the element and the modified set}
@java-imports{import java.util.Iterator;}
{
   int n = st.size();
   
   if(n > 0){
      int i = 0;
   	  int k = random.nextInt(n);
   	  IValue pick = null;
   	  IListWriter w = st.getType().writer(values);
   	  Iterator iter = st.iterator();
  
      while(iter.hasNext()){
      	if(i == k){
      		pick = (IValue) iter.next();
      	} else {
      		w.insert((IValue) iter.next());
      	}
      i++;
   	  }
      return values.tuple(pick, w.done());
   	} else {
   		throw new RascalException(values, "empty_list");
   	}
}
  
public list[&T] java toList(set[&T] st)
@doc{toList -- convert a set to a list}
@java-imports{import java.util.Iterator;}
{
  Type resultType = types.listType(st.getElementType());
  IListWriter w = resultType.writer(values);
  Iterator iter = st.iterator();
  while (iter.hasNext()) {
    w.insert((IValue) iter.next());
  }
	
  return w.done();
}

// TODO: multiple elements in map?

public map[&A,&B] java toMap(set[tuple[&A, &B]] st)
@doc{toMap -- convert a set of tuples to a map}
@java-imports{import java.util.Iterator;}
{
   TupleType tuple = (TupleType) st.getElementType();
   Type resultType = types.mapType(tuple.getFieldType(0), tuple.getFieldType(1));
  
   IMapWriter w = resultType.writer(values);
   Iterator iter = st.iterator();
   while (iter.hasNext()) {
     ITuple t = (ITuple) iter.next();
     w.put(t.get(0), t.get(1));
   }
   return w.done();
}

public str java toString(set[&T] st)
@doc{toString -- convert a set to a string}
{
	return values.string(st.toString());
}



