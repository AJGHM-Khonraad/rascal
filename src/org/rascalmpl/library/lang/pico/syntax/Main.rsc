@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Paul Klint - Paul.Klint@cwi.nl - CWI}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}
module lang::pico::syntax::Main

start syntax PROGRAM = program: "begin" DECLS decls {STATEMENT  ";"}* body "end" ;

syntax DECLS = "declare" {IDTYPE ","}* decls ";" ;  
 
syntax STATEMENT = assign: ID var ":="  EXP val 
                 | cond:   "if" EXP cond "then" {STATEMENT ";"}*  thenPart "else" {STATEMENT ";"}* elsePart "fi"
                 | cond:   "if" EXP cond "then" {STATEMENT ";"}*  thenPart "fi"
                 | loop:   "while" EXP cond "do" {STATEMENT ";"}* body "od"
                 ;  

syntax IDTYPE = ID id ":" TYPE type;
     
syntax TYPE = natural:"natural" 
            | string:"string" 
            | nil:"nil-type"
            ;

syntax EXP = id: ID name
           | strcon: STR string
           | natcon: NAT natcon
           | bracket "(" EXP e ")"
           > concat: EXP lhs "||" EXP rhs
           > left (add: EXP lhs "+" EXP rhs
                  |min: EXP lhs "-" EXP rhs
                  )
           ;

           
syntax ID  = lex [a-z][a-z0-9]* # [a-z0-9];
syntax NAT = lex [0-9]+ ;
syntax STR = lex "\"" ![\"]*  "\"";

layout Pico = WhitespaceAndComment*  
            # [\ \t\n\r]
            # "%"
            ;

syntax WhitespaceAndComment 
   = lex [\ \t\n\r]
   | lex "%" ![%]* "%"
   | lex "%%" ![\n]* "\n"
   ;

public PROGRAM program(str s) {
  return parse(#PROGRAM, s);
} 
