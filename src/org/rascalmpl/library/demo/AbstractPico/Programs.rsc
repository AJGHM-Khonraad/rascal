@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
module demo::AbstractPico::Programs

import demo::AbstractPico::AbstractSyntax;
import IO;

/*
 * A bunch of sample Pico programs.
 */
 
/*
 * Annotate Pico programs for the benefit of analysis:
 * - Each statement gets an annotation.
 * - In nested expressions only the outermost expression gets an annotation.
 *
 * Caution: the actual order of labeling is unimportant but changing it will break
 * all test cases in the various analysis functions.
 */

public PROGRAM annotate(PROGRAM P)
{
   int N = 0;
 
   return bottom-up visit(P){
   case STATEMENT S:
         { N = N + 1; M = N;
           S1 = top-down visit(S){
                case EXP e: 
                     { N = N + 1; insert e[@pos=N]; }
                };
           insert S1[@pos=M];
         }
    };
}

/********************************************
begin
  declare x : natural,
          s : string;

  x := 10;
  while x do
   x := x - 1;
   s := s || "#"
  od
end
*********************************************/

public PROGRAM small =
program([decl("x", natural()), decl("s", string())],
        [ asgStat("x", natCon(3)),
          whileStat(id("x"),
                    [ asgStat("x", sub(id("x"), natCon(1))),
                      asgStat("s", conc(id("s"), strCon("#")))
                    ]
                   )
        ]
       );
       
public PROGRAM smallUninit =
       
 program([decl("x", natural()), decl("s", string())],
        [ //asgStat("x", natCon(3)),
          whileStat(id("x"),
                    [ asgStat("x", sub(id("x"), natCon(1))),
                      asgStat("s", conc(id("s"), strCon("#")))
                    ]
                   )
        ]
       );

/********************************************
begin
  declare
    input : natural,
    output  :  natural,
    repnr: natural,
    rep: natural;

  input := 10;
  output := 1;
  while input - 1 do
    rep := output;
    repnr := input;
    while repnr - 1 do
      output := output + rep;

      repnr := repnr - 1
    od;
    input := input - 1
  od
end
**********************************************/

public PROGRAM fac =
program([ decl("input", natural()),
          decl("output", natural()),
          decl("repnr", natural()),
          decl("rep", natural())
        ],
        [ asgStat("input", natCon(13)),
          asgStat("output", natCon(1)),
          whileStat(sub(id("input"), natCon(1)),
                    [ asgStat("rep", id("output")),
                      asgStat("repnr", id("input")),
                      whileStat(sub(id("repnr"), natCon(1)),
                                [ asgStat("output", add(id("output"), id("rep"))),
                                  asgStat("repnr", sub(id("repnr"), natCon(1)))
                                ]
                               ),
                      asgStat("input", sub(id("input"), natCon(1)))
                    ]
                   )              
        ]
       );
       
public PROGRAM facUninit =
program([ decl("input", natural()),
          decl("output", natural()),
          decl("repnr", natural()),
          decl("rep", natural())
        ],
        [ asgStat("input", natCon(13)),
          //asgStat("output", natCon(1)),
          whileStat(sub(id("input"), natCon(1)),
                    [ asgStat("rep", id("output")),
                      asgStat("repnr", id("input")),
                      whileStat(sub(id("repnr"), natCon(1)),
                                [ asgStat("output", add(id("output"), id("rep"))),
                                  asgStat("repnr", sub(id("repnr"), natCon(1)))
                                ]
                               ),
                      asgStat("input", sub(id("input"), natCon(1)))
                    ]
                   )            
        ]
       );


/********************************************
begin
  declare
    input: natural,
    output: string,
    i: natural,
    j: natural,
    k: natural,
    s: string,
    t: string;
  input := 14;
  if input then
    i := input;
    s := "";
    while i do
      j := 0;
      k := 0;
      while i do
        i := i-1;
        if j then
          j := 0;
          k := k+1
        else
          j := 1
        fi
      od;
      if j then t := "1" else t := "0" fi;
      s := t||s;
      i := k
    od;
    output := s
  else
    output := "0"
  fi
end
*********************************************/  

public PROGRAM big =
program([ decl("input", natural()),
          decl("output", string()),
          decl("i", natural()),
          decl("j", natural()),
          decl("k", natural()),
          decl("s", string()),
          decl("t", string())
        ],
        [ asgStat("input", natCon(14)),
          ifStat(id("input"),
                 [ asgStat("i", id("input")),
                   asgStat("s", strCon("")),
                   whileStat(id("i"),
                             [ asgStat("j", natCon(0)),
                               asgStat("k", natCon(0)),
                               whileStat(id("i"),
                                         [ asgStat("i", sub(id("i"), natCon(1))),
                                           ifStat(id("j"),
                                                  [ asgStat("j", natCon(0)),
                                                    asgStat("k", add(id("k"),natCon(1)))
                                                  ],
                                                  [ asgStat("j", natCon(1))
                                                  ]
                                                 )
                                         ]
                                        ),
                               ifStat(id("j"), [asgStat("t", strCon("1"))], [asgStat("t", strCon("0"))]),
                               asgStat("s", conc(id("t"),id("s"))),
                               asgStat("i", id("k"))
                             ]
                            ),
                   asgStat("output", id("s"))
                  ],
                  [ asgStat("output", strCon("0"))
                  ]
                )   
                      
        ]
       );
       
test small == annotate(small);
