module Map

@doc{ return the domain (keys) of a map}
@javaClass{org.meta_environment.rascal.std.Map}
public set[&K] java domain(map[&K, &V] M);

@doc{ return arbitrary key of a map}
@javaClass{org.meta_environment.rascal.std.Map}
public &K java getOneFrom(map[&K, &V] M)  ;

@doc{ return map with key and value inverted; values are not unique and are collected in a set}
@javaClass{org.meta_environment.rascal.std.Map}
public map[&V, set[&K]] java invert(map[&K, &V] M)  ;

@doc{ return map with key and value inverted; values are unique}
@javaClass{org.meta_environment.rascal.std.Map}
public map[&V, &K] java invertUnique(map[&K, &V] M)  ;

@doc{Is map empty?}
@javaClass{org.meta_environment.rascal.std.Map}
public bool java isEmpty(map[&K, &V] M);

@doc{Apply two functions to each key/value pair in a map.}
public map[&K, &V] mapper(map[&K, &V] M, &K (&K) F, &V (&V) G)
{
  return (F(key) : G(M[key]) | &K key <- M);
}

@doc{Return the range (values) of a map}
@javaClass{org.meta_environment.rascal.std.Map}
public set[&V] java range(map[&K, &V] M);

@doc{Number of elements in a map.}
@javaClass{org.meta_environment.rascal.std.Map}
public int java size(map[&K, &V] M);

@doc{Convert a map to a list}
@javaClass{org.meta_environment.rascal.std.Map}
public list[tuple[&K, &V]] java toList(map[&K, &V] M);

@doc{Convert a map to a relation}
@javaClass{org.meta_environment.rascal.std.Map}
public rel[&K, &V] java toRel(map[&K, &V] M);
  
@doc{Convert a list to a string.}
@javaClass{org.meta_environment.rascal.std.Map}
public str java toString(map[&K, &V] M);




