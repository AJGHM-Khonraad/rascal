module demo::PicoAbstract::PicoUninit

import demo::PicoAbstract::PicoAbstractSyntax;
import demo::PicoAbstract::PicoAnalysis;
import demo::PicoAbstract::PicoControlflow;
import demo::PicoAbstract::PicoUseDef;
import demo::PicoAbstract::PicoPrograms;
import UnitTest;
import IO;
import Graph;

set[ProgramPoint] uninit(PROGRAM P) {
    BLOCK ControlFlow = cflow(P);
    rel[PicoId, ProgramPoint] Uses = uses(P);
    rel[PicoId, ProgramPoint] Defs = defs(P);
    
    println("Uses=<Uses>\nDefs=<Defs>\nControlFlow=<ControlFlow>");
    
    R= ControlFlow.entry;
    G= ControlFlow.graph;
    
    println("R=<R>\nG=<G>");
    dx = Defs["x"]; ux = Uses["x"];
    ds = Defs["s"]; us = Uses["s"];
    
    println("dx=<dx>\nux=<ux>");
    println("ds=<ds>\nus=<us>");
    
    for(<PicoId Id, ProgramPoint PP> <- Uses){
      println("Id=<Id>, PP=<PP>");
      rx = reachX(ControlFlow.graph, ControlFlow.entry, Defs[Id]);
      println("reachX=<rx>");
    }
    

    return {PP | <PicoId Id, ProgramPoint PP> <- Uses,
                 PP in reachX(ControlFlow.graph, ControlFlow.entry, Defs[Id])
    };
}

public bool test(){

ui = uninit(smallUninit);
println("ui=<ui>");
return true;
}