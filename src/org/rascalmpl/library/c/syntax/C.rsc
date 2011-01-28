module c::syntax::C

syntax Statement = "{" Declaration* Statement*  "}" |
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

syntax Expression = Identifier |
                    HexadecimalConstant | // {category("Constant")}
                    IntegerConstant | // {category("Constant")}
                    CharacterConstant | // {category("Constant")}
                    FloatingPointConstant | // {category("Constant")}
                    StringConstant | // {category("Constant")}
                    Expression "[" Expression "]" | // TODO: Limit <0> to constants, identifiers and post-fix expressions.
                    Expression "(" {Expression ","}* ")" | // TODO: Limit <0> to constants, identifiers and post-fix expressions.
                    "sizeof" "(" TypeName ")" |
                    bracket "(" Expression ")" |
                    Expression "." Identifier |
                    Expression "-\>" Identifier |
                    Expression "++" |
                    Expression "--" >
                    "++" Expression | // Add precede restriction "+"
                    "--" Expression | // Add precede restriction "-"
                    "&" Expression | // Add precede restriction "&"
                    "*" Expression |
                    "+" Expression | // Add precede restriction "+"
                    "-" Expression | // Add precede restriction "-"
                    "~" Expression |
                    "!" Expression |
                    "sizeof" Expression |
                    "(" TypeName ")" Expression >
                    left (
                         Expression "*" Expression |
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
                    right Expression "?" Expression ":" Expression -> Expression >
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
                    left Expression "," Expression
                    ;

syntax Identifier = lex [a-zA-Z_][a-zA-Z_0-9]*
                    # [0-9a-zA-Z_]
                    - Keyword
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
                 # [0-9a-zA-Z_]
                 ;

syntax Declaration = Specifier+ {InitDeclarator ","}+ ";" |
                     Specifier+ ";"  // {avoid}
                     ;

syntax InitDeclarator = Declarator |
                        Declarator "=" Initializer
                       ;

syntax Specifier = Identifier |
                   "typedef" |
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
                   "struct"|"union" Identifier "{" StructDeclaration+ "}" |
                   "struct"|"union" "{" StructDeclaration+ "}" |
                   "struct"|"union" Identifier |
                   "enum" "{" {Enumerator ","}+  "}" |
                   "enum" Identifier "{" {Enumerator ","}+  "}" |
                   "enum" Identifier
                   ;

syntax StructDeclaration = Specifier+ {StructDeclarator ","}+ ";"
                           ;

syntax StructDeclarator = Declarator |
                          ":" Expression |
                          Declarator ":" Expression
                          ;

syntax Parameters = {Parameter ","}+ MoreParameters?
                    ;

syntax MoreParameters = "," "..."
                        ;

syntax Initializer = Expression |
                     "{" {Initializer ","}+ ","?  "}"
                     ;

syntax TypeName = Specifier+ AbstractDeclarator
                  ;

syntax Pointer = PointerContent+
                 ;

syntax PointerContent = "*" Specifier*;

syntax Enumerator = Identifier |
                    Identifier "=" Expression
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
                    non-assoc Pointer Declarator
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

syntax CharacterConstantContent = [\\]![]|
                                  ![\\\']
                                  ;

syntax FloatingPointConstant = lex [0-9]+ Exponent [fFlL]? |
                               lex [0-9]* [.] [0-9]+ Exponent? [fFlL]? |
                               lex [0-9]+ [.] Exponent? [fFlL]?
                               # [0-9]
                               ;

syntax StringConstant = lex [L]? [\"] StringConstant* [\"]
                        ;

syntax StringConstantContent = [\\]![] |
                               ![\\\"]
                               ;

syntax Exponent = lex [Ee] [+\-]? [0-9]+
                  ;

syntax ExternalDeclaration = FunctionDefinition |
                             Declaration
                             ;

syntax FunctionDefinition = Specifier* Declarator Declaration* "{" Declaration* Statement* "}"
                            ;

start syntax TranslationUnit = ExternalDeclaration+
                               ;

syntax Comment = lex [/][*] MultiLineCommentBodyToken* [*][/] | // category("Comment")
                 lex "//" ![\n]* [\n] // category("Comment")
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
                lex Comment: Comment
                ;

//////////////////////////// TODO: SDF stuff ////////////////////////////
/*
Expression                -> Initializer >
Expression "," Expression -> Expression,

Expression                -> {Expression ","}+ >
Expression "," Expression -> Expression,

Identifier "=" Expression -> Enumerator >
Expression "," Expression -> Expression
*/
