module demo::AbstractPico::Controlflow
 
import demo::AbstractPico::AbstractSyntax;
import demo::AbstractPico::Analysis;
import demo::AbstractPico::Programs;
import IO;
import UnitTest;
      
/*
 * Extract the control flow graph from a program.
 * Convention: the entry point is always labelled with 0.
 */               

public BLOCK cflow(PROGRAM P){
    if(program(list[DECL] Decls, list[STATEMENT] Stats) := P){
            BLOCK ControlFlow = cflow(Stats);
            // Add a unique entry point to the graph
            ProgramEntry = {0};
            CFG = ({0} * ControlFlow.entry) + ControlFlow.graph;
            return block({0}, CFG, ControlFlow.exit);
    }
    throw("Malformed Pcio program");;
}

public BLOCK cflow(list[STATEMENT] Stats){ 
    switch (Stats) {
    
      case [STATEMENT Stat]:
       		return cflow(Stat);
       		
      case [STATEMENT Stat, list[STATEMENT] Stats2]: {
           CF1 = cflow(Stat);
           CF2 = cflow(Stats2);
           return block(CF1.entry, 
                   CF1.graph + CF2.graph + (CF1.exit * CF2.entry), 
                   CF2.exit);
      }
      case []: return block({}, {}, {});
    }
    throw("cflow returns no value");
}

public BLOCK cflow(STATEMENT Stat){
    switch (Stat) {                
      case ifStat(EXP Exp, list[STATEMENT] Stats1,
                            list[STATEMENT] Stats2): {
           BLOCK CF1 = cflow(Stats1);
           BLOCK CF2 = cflow(Stats2);
           set[ProgramPoint] E = {Exp@pos};
           return block( E, 
                    (E * CF1.entry) + (E * CF2.entry) + 
                                      CF1.graph + CF2.graph,
                    CF1.exit + CF2.exit
                  );
      }
      
      case whileStat(EXP Exp, list[STATEMENT] Stats): {
           BLOCK CF = cflow(Stats);
           set[ProgramPoint] E = {Exp@pos};
           return block(E, 
                    (E * CF.entry) + CF.graph + (CF.exit * E),
                    E
                  );
      }
         
      case STATEMENT Stat1: return block({Stat1@pos}, {}, {Stat1@pos});
    }
    throw("cflowstat returns no value");
}

// Tests
  
test cflow(annotate(small)) == block({0},{<0,1>,<3,7>,<7,12>,<12,3>,<1,12>},{12});
          
test cflow(annotate(fac)) == block({0},{<1,3>,<5,7>,<7,37>,<3,32>,<0,1>,<9,13>,<32,5>,<27,32>,<13,37>,<37,27>,<37,9>},{32});

