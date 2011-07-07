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
module lang::pico::\syntax::Main

import ParseTree;
import IO;

start syntax Program 
  = program: "begin" Declarations decls {Statement  ";"}* body "end" ;

syntax Declarations 
  = "declare" {(Id ":" Type) ","}* decls ";" ;  
 
syntax Statement 
  = assign: Id var ":="  Expression val 
  | cond: "if" Expression cond "then" {Statement ";"}*  thenPart "else" {Statement ";"}* elsePart "fi"
  | cond: "if" Expression cond "then" {Statement ";"}*  thenPart "fi"
  | loop: "while" Expression cond "do" {Statement ";"}* body "od"
  ;  
     
syntax Type 
  = natural:"natural" 
  | string :"string" 
  | nil    :"nil-type"
  ;

syntax Expression 
  = id: Id name
  | strcon: String string
  | natcon: Natural natcon
  | bracket "(" Expression e ")"
  > concat: Expression lhs "||" Expression rhs
  > left ( add: Expression lhs "+" Expression rhs
         | min: Expression lhs "-" Expression rhs
         )
  ;

           
lexical Id  = [a-z][a-z0-9]* !>> [a-z0-9];
lexical Natural = [0-9]+ ;
lexical String = "\"" ![\"]*  "\"";

layout Layout = WhitespaceAndComment* !>> [\ \t\n\r%];

lexical WhitespaceAndComment 
   = [\ \t\n\r]
   | @category="Comment" "%" ![%]* "%"
   | @category="Comment" "%%" ![\n]* $
   ;

public Program program(str s) {
  return parse(#start[Program], s);
}

public Program program(str s, loc l) {
  return parse(#start[Program], s, l);
} 