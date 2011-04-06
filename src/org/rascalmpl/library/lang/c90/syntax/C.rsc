@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}
module C

syntax Statement = "{" Declaration* Statement* "}" |
                   Identifier ":" Statement |
                   "case" Expression ":" Statement |
                   "default" ":" Statement |
                   ";" |
                   Expression ";" |
                   "if" "(" Expression ")" Statement |
                   "if" "(" Expression ")" Statement "else" Statement |
                   "switch" "(" Expression ")" Statement |
                   "while" "(" Expression ")" Statement |
                   "do" Statement "while" "(" Expression ")" ";" |
                   "for" "(" Expression? ";" Expression? ";" Expression? ")" Statement |
                   "goto" Identifier ";" |
                   "continue" ";" |
                   "break" ";" |
                   "return" ";" |
                   "return" Expression ";"
                   ;

syntax Expression = Variable: Identifier |
                    @category="Constant" HexadecimalConstant |
                    @category="Constant" IntegerConstant |
                    @category="Constant" CharacterConstant |
                    @category="Constant" FloatingPointConstant |
                    @category="Constant" StringConstant |
                    Expression "[" Expression "]" |
                    Expression "(" {NonCommaExpression ","}* ")" |
                    "sizeof" "(" TypeName ")" |
                    bracket Bracket: "(" Expression ")" |
                    Expression "." Identifier |
                    Expression "-\>" Identifier |
                    Expression "++" |
                    Expression "--" >
                    "++" Expression |
                    "--" Expression |
                    "&" Expression |
                    "*" Expression |
                    "+" Expression |
                    "-" Expression |
                    "~" Expression |
                    "!" Expression |
                    "sizeof" Expression exp {
                       list[Tree] children;
                       if(appl(prod(_,_,attrs([_*,term(cons("Bracket")),_*])),children) := exp){
                          Tree child = children[1];
                          if(appl(prod(_,_,attrs([_*,term(cons("Variable")),_*])),_) := child){
                             if("<child>" in typeDefs){
                                  fail;
                               }
                          }
                       }
                    } | // May be ambiguous with "sizeof(TypeName)".
                    "(" TypeName ")" Expression >
                    left (
                         Expression lexp "*" Expression rexp {
                            if(appl(prod(_,_,attrs([_*,term(cons("Variable")),_*])),_) := lexp){
                               if("<child>" in typeDefs){
                                  fail;
                               }
                            }
                         } | // May be ambiguous with "TypeName *Declarator".
                         Expression "/" Expression |
                         Expression "%" Expression
                    ) >
                    left (
                         Expression "+" Expression |
                         Expression "-" Expression
                    ) >
                    left (
                         Expression "\<\<" Expression |
                         Expression "\>\>" Expression
                    ) >
                    left (
                         Expression "\<" Expression |
                         Expression "\>" Expression |
                         Expression "\<=" Expression |
                         Expression "\>=" Expression
                    ) >
                    left (
                         Expression "==" Expression |
                         Expression "!=" Expression
                    ) >
                    left Expression "&" Expression >
                    left Expression "^" Expression >
                    left Expression "|" Expression >
                    left Expression "&&" Expression >
                    left Expression "||" Expression >
                    right Expression "?" Expression ":" Expression >
                    right (
                          Expression "=" Expression |
                          Expression "*=" Expression |
                          Expression "/=" Expression |
                          Expression "%=" Expression |
                          Expression "+=" Expression |
                          Expression "-=" Expression |
                          Expression "\<\<=" Expression |
                          Expression "\>\>=" Expression |
                          Expression "&=" Expression |
                          Expression "^=" Expression |
                          Expression "|=" Expression
                    ) >
                    left CommaExpression: Expression "," Expression
                    ;

syntax NonCommaExpression = Expression expr {
                               if(appl(prod(_,_,attrs([_*,term(cons("CommaExpression")),_*])),_) := expr){
                                  fail;
                               }
                            }
                            ;

syntax "+" = ... # [+];

syntax "-" = ... # [\-];

syntax "&" = ... # [&];

syntax Identifier = lex [a-zA-Z_] [a-zA-Z0-9_]*
                    - Keyword
                    # [a-zA-Z0-9_]
                    ;

syntax AnonymousIdentifier = 
                             ;

syntax Keyword = "auto" |
                 "break" |
                 "case" |
                 "char" |
                 "const" |
                 "continue" |
                 "default" |
                 "do" |
                 "double" |
                 "else" |
                 "enum" |
                 "extern" |
                 "float" |
                 "for" |
                 "goto" |
                 "if" |
                 "int" |
                 "long" |
                 "register" |
                 "return" |
                 "short" |
                 "signed" |
                 "sizeof" |
                 "static" |
                 "struct" |
                 "switch" |
                 "typedef" |
                 "union" |
                 "unsigned" |
                 "void" |
                 "volatile" |
                 "while"
                 # [a-zA-Z0-9_]
                 ;

syntax Declaration = Specifier* specs {InitDeclarator ","}+ initDeclarator ";" {
                        list[Tree] specChildren;
                        if(appl(_,specChildren) := specs){
                           if([_*,appl(prod(_,_,attrs([_*,term(cons("TypeDef")),_*])),_),_*] := specChildren){
                              str declType = findType(specs);
                              list[tuple[str,str]] variables = findVariableNames(initDeclarators);
                              for(variableTuple <- variables){
                                 str variable = variableTuple.var;
                                 str initDecl = variableTuple.initDecl;
                                 list[str] modifiers = findModifiers(specs, initDecl);
                                 typeDefs += (variable:<declType, modifiers>); // Record the typedef.
                              }
                           }
                        }
                        
                        str declType = findType(specs);
                        if("<declType>" notin typeDefs){
                           fail;
                        } // May be ambiguous with "Exp * Exp".
                     } |
                     Specifier* specs ";" {
                        list[Tree] specChildren;
                        if(appl(_,specChildren) := specs){
                           if([_*,appl(prod(_,_,attrs([_*,term(cons("TypeDef")),_*])),_),_*] := specChildren){
                              str declType = findType(specs);
                              list[tuple[str,str]] variables = findVariableNames(initDeclarators);
                              for(variableTuple <- variables){
                                 str variable = variableTuple.var;
                                 str initDecl = variableTuple.initDecl;
                                 list[str] modifiers = findModifiers(specs, initDecl);
                                 typeDefs += (variable:<declType, modifiers>); // Record the typedef.
                              }
                           }
                        }
                        
                        str declType = findType(specs);
                        if("<declType>" notin typeDefs){
                           fail;
                        } // May be ambiguous with "Exp * Exp".
                     }  // TODO: Avoid
                     ;

syntax InitDeclarator = Declarator |
                        Declarator "=" Initializer
                       ;

syntax Specifier = Identifier |
                   TypeDef: "typedef" |
                   "extern" |
                   "static" |
                   "auto" |
                   "register" |
                   "void" |
                   "char" |
                   "short" |
                   "int" |
                   "long" |
                   "float" |
                   "double" |
                   "signed" |
                   "unsigned" |
                   "const" |
                   "volatile" |
                   "struct" Identifier |
                   "struct" Identifier "{" StructDeclaration+ "}" |
                   "struct" "{" StructDeclaration+ "}" |
                   "union" Identifier |
                   "union" Identifier "{" StructDeclaration+ "}" |
                   "union" "{" StructDeclaration+ "}" |
                   "enum" Identifier
                   "enum" Identifier "{" {Enumerator ","}+ "}" |
                   "enum" "{" {Enumerator ","}+ "}" |
                   ;

syntax StructDeclaration = Specifier* specs {StructDeclarator ","}+ ";" // TODO Disallow typedef specifier and such.
                           ;

syntax StructDeclarator = Declarator |
                          Declarator? ":" Expression
                          ;

syntax Parameters = {Parameter ","}+ MoreParameters?
                    ;

syntax MoreParameters = "," "..."
                        ;

syntax Initializer = NonCommaExpression |
                     "{" {Initializer ","}+ ","?  "}"
                     ;

syntax TypeName = Specifier+ AbstractDeclarator
                  ;

syntax Pointer = PointerContent+
                 ;

syntax PointerContent = "*" Specifier+; // TODO: Only allow type qualifiers and identifiers.

syntax Enumerator = Identifier |
                    Identifier "=" NonCommaExpression
                    ;

syntax AbstractDeclarator = AnonymousIdentifier |
                            "(" AbstractDeclarator ")" |
                            AbstractDeclarator "[" Expression? "]" |
                            AbstractDeclarator "(" Parameters? ")" >
                            non-assoc Pointer AbstractDeclarator
                            ;

syntax Declarator = Identifier |
                    bracket "(" Declarator ")" |
                    Declarator "[" Expression? "]" |
                    Declarator "(" Parameters? ")" >
                    non-assoc PointerDeclarator: Pointer Declarator
                    ;

syntax Parameter = Specifier+ Declarator |
                   Specifier+ AbstractDeclarator
                   ;

syntax HexadecimalConstant = lex [0] [xX] [a-fA-F0-9]+ [uUlL]*
                             # [a-fA-F0-9]
                             ;

syntax IntegerConstant = lex [0-9]+ [uUlL]*
                         # [0-9]
                         ;

syntax CharacterConstant = lex [L]? [\'] CharacterConstantContent+ [\']
                           ;

syntax CharacterConstantContent = lex [\\] ![] |
                                  lex ![\\\']
                                  ;

syntax FloatingPointConstant = lex [0-9]+ Exponent [fFlL]? |
                               lex [0-9]* [.] [0-9]+ Exponent? [fFlL]? |
                               lex [0-9]+ [.] Exponent? [fFlL]?
                               # [0-9]
                               ;

syntax StringConstant = lex [L]? [\"] StringConstant* [\"]
                        ;

syntax StringConstantContent = lex [\\] ![] |
                               lex ![\\\"]
                               ;

syntax Exponent = lex [Ee] [+\-]? [0-9]+
                  ;

syntax ExternalDeclaration = FunctionDefinition |
                             Declaration
                             ;

syntax FunctionDefinition = Specifier* Declarator Declaration* "{" Declaration* Statement* "}" // TODO Disallow typedef specifier and such.
                            ;

start syntax TranslationUnit = ExternalDeclaration+
                               ;

syntax Comment = lex [/][*] MultiLineCommentBodyToken* [*][/] |
                 lex "//" ![\n]* [\n]
                 ;

syntax MultiLineCommentBodyToken = lex ![*] |
                                   lex Asterisk
                                   ;

syntax Asterisk = lex [*]
                  # [/]
                  ;

layout LAYOUTLIST = LAYOUT*
                    # [\ \t\n\r]
                    ;

syntax LAYOUT = lex Whitespace: [\ \t\n\r] |
                @category="Comment" lex Comment: Comment
                ;


map[str name, tuple[str var, list[str] modifiers] cType] typeDefs = (); // Name to type mapping.

private str findType(Specifier* specs){
	// If empty type is int.
	
	return ""; // TODO: Implement.
}

private list[str] findModifiers(Specifier* specifiers, InitDeclarator initDecl){
	return []; // TODO: Implement.
}

private list[tuple[str var, str initDecl]] findVariableNames({InitDeclarator ","}+ initDecls){
	return []; // TODO: Implement.
}
