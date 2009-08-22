module experiments::DDA::typecheck

import experiments::DDA::DDA;
import IO;
//alias Type = str;


public void typeCheck(Program p) {
  map[Identifier,Type] syms = ();

  for(Stat s <- p) {
    println("s = <s>");
    switch(s) {
      case Stat[|type <Identifier t>|]:
	syms = addSym(syms, t, "type");
      case Stat[|var <Identifier v> : <Identifier t>|]:
        syms = addSym(syms, v, checkSym(syms, t, "type"));
      case Stat[|compute <Identifier p>:<Identifier P> 
                   along <Identifier DDA> 
                   from <Identifier V> 
                   in <Expr E> 
                 end|]: {
        checkSym(syms, p, checkSym(syms, P, "type"));
        checkSym(syms, DDA, "DDA");
        checkSym(syms, V, "array");
      }
    }
  }
}

map[Identifier,Type] addSym(map[Identifier,Type] symbols, Identifier name,
                   Type type) {
  symbols[name] = type;
  return symbols;
}

Identifier checkSym(map[Identifier,Type] symbols, Identifier name,
                    Type type) {
  t = symbols[name] ? "undefined";

  if(t != type)
    println("Expected <name> to be <type>, but was <t>.");

  return name;
}

public Program parseProgram(str filename) @fileParser;

public bool test(){
   P = parseProgram("src/experiments/DDA/example.dda");
   println(P);
   typeCheck(P);
   return true;
}
