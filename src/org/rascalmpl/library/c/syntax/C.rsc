module C

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
                    @category="Constant" HexadecimalConstant |
                    @category="Constant" IntegerConstant |
                    @category="Constant" CharacterConstant |
                    @category="Constant" FloatingPointConstant |
                    @category="Constant" StringConstant |
                    Expression "[" Expression "]" |
                    Expression "(" {Expression ","}* ")" |
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
                    left Expression "," Expression
                    ;

syntax "+" = "+" # "+";

syntax "-" = "-" # "-";

syntax "&" = "&" # "&";

syntax Identifier = lex [a-zA-Z_][a-zA-Z0-9_]*
                    # [a-zA-Z0-9_]
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
                 # [a-zA-Z0-9_]
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
                   "struct" Identifier |
                   "struct" Identifier "{" StructDeclaration+ "}" |
                   "struct" "{" StructDeclaration+ "}" |
                   "union" Identifier |
                   "union" Identifier "{" StructDeclaration+ "}" |
                   "union" "{" StructDeclaration+ "}" |
                   "enum" Identifier
                   "enum" Identifier "{" {Enumerator ","}+  "}" |
                   "enum" "{" {Enumerator ","}+  "}" |
                   ;

syntax StructDeclaration = Specifier+ {StructDeclarator ","}+ ";"
                           ;

syntax StructDeclarator = Declarator |
                          Declarator ":" Expression |
                          ":" Expression
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

syntax CharacterConstantContent = [\\]![] |
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

//////////////////////////// TODO: SDF stuff ////////////////////////////
/*
Expression                -> Initializer >
Expression "," Expression -> Expression,

Expression                -> {Expression ","}+ >
Expression "," Expression -> Expression,

Identifier "=" Expression -> Enumerator >
Expression "," Expression -> Expression
*/
