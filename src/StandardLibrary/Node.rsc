module Node

public int java arity(node T)
@doc{arity -- number of children of a node}
@javaClass{org.meta_environment.rascal.std.Node};

public list[value] java getChildren(node T)
@doc{getChildren -- get the children of a node}
@javaClass{org.meta_environment.rascal.std.Node};

public str java getName(node T)
@doc{getName -- get the function name of a node}
@javaClass{org.meta_environment.rascal.std.Node};

public node java makeNode(str N, value V...)
@doc{makeNode -- create a node given its function name and arguments}
@javaClass{org.meta_environment.rascal.std.Node};
