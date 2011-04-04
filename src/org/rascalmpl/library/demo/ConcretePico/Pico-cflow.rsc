@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
module \Pico-controlflow
 
import pico/syntax/Pico;

data CP exp(EXP) | stat(STATEMENT);

alias tuple[set[CP] entry, 
           rel[CP,CP] graph, 
           set[CP] exit] CFSEGMENT;

CFSEGMENT cflow({STATEMENT ";"}* Stats){ 
    switch (Stats) {
      case <STATEMENT Stat> ; <{STATEMENT ";"}* Stats2>: { 
           CFSEGMENT CF1 = cflow(Stat);
           CFSEGMENT CF2 = cflow(Stats2);
           return <CF1.entry, 
                   CF1.graph | CF2.graph | (CF1.exit * CF2.entry), 
                   CF2.exit>;
      }

      case ` `: return <{}, {}, {}>;
    }
}

CFSEGMENT cflow(STATEMENT Stat){
    switch (Stat) {
      case while <EXP Exp> do <{STATEMENT ";"}* Stats> od : {
           CFSEGMENT CF = cflow(Stats);
           set[CP] E = {exp(Exp)};
           return < E, 
                    (E * CF.entry) | CF.graph | (CF.exit * E),
                    E
                  >;
      }
                
      case if <EXP Exp> then <{STATEMENT ";"}* Stats1> 
                   else <{STATEMENT ";"}* Stats2> fi: {
           CFSEGMENT CF1 = cflow(Stats1);
           CFSEGMENT CF2 = cflow(Stats2);
           set[CP] E = {exp(Exp)};
           return < E, 
                    (E * CF1.entry) | (E * CF2.entry) | 
                                      CF1.graph | CF2.graph,
                    CF1.exit | CF2.exit
                  >;
      }
         
      case <STATEMENT Stat>: return <{Stat}, {}, {Stat}>;
    }
}
