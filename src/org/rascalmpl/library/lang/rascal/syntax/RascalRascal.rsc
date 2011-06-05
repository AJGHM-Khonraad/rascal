@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
@contributor{Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI}
@contributor{Tijs van der Storm - Tijs.van.der.Storm@cwi.nl}
@contributor{Arnold Lankamp - Arnold.Lankamp@cwi.nl}
@doc{The syntax definition of Rascal, excluding concrete syntax fragments}
@bootstrapParser
module lang::rascal::syntax::RascalRascal

lexical BooleanLiteral
	= "true" 
	| "false" ;

syntax Literal
	= Integer: IntegerLiteral integerLiteral 
	| RegExp: RegExpLiteral regExpLiteral 
	| Real: RealLiteral realLiteral 
	| Boolean: BooleanLiteral booleanLiteral 
	| String: StringLiteral stringLiteral 
	| DateTime: DateTimeLiteral dateTimeLiteral 
	| Location: LocationLiteral locationLiteral ;

start syntax Module
	= Default: Header header Body body ;

start syntax PreModule
    = Default: Header header () !>> HeaderKeyword Word* rest;

keyword HeaderKeyword
  = "import"
  | "syntax"
  | "start"
  | "layout"
  | "lexical"
  | "keyword"
  | "extend"
  ;

lexical Word = ![\ \t\n\r]+ !>> ![\ \t\n\r];          
                   
syntax ModuleParameters
	= Default: "[" {TypeVar ","}+ parameters "]" ;

lexical DateAndTime
	= "$" DatePart "T" TimePartNoTZ TimeZonePart? ;

syntax Strategy
	= TopDownBreak: "top-down-break" 
	| TopDown: "top-down" 
	| BottomUp: "bottom-up" 
	| BottomUpBreak: "bottom-up-break" 
	| Outermost: "outermost" 
	| Innermost: "innermost" ;

lexical UnicodeEscape
	= "\\" [u]+ [0-9 A-F a-f] [0-9 A-F a-f] [0-9 A-F a-f] [0-9 A-F a-f] ;

syntax Variable
	= Initialized: Name name "=" Expression initial 
	| UnInitialized: Name name ;

lexical OctalIntegerLiteral
	= [0] [0-7]+ !>> [0-9 A-Z _ a-z] ;

syntax TypeArg
	= Default: Type type 
	| Named: Type type Name name ;

syntax Renaming
	= Default: Name from "=\>" Name to ;

syntax Catch
	= @Foldable Default: "catch" ":" Statement body 
	| @Foldable Binding: "catch" Pattern pattern ":" Statement body ;

lexical PathChars
	= URLChars [|] ;

syntax Signature
	= WithThrows: Type type FunctionModifiers modifiers Name name Parameters parameters "throws" {Type ","}+ exceptions 
	| NoThrows: Type type FunctionModifiers modifiers Name name Parameters parameters ;

syntax Sym
// named non-terminals
	= Nonterminal: Nonterminal nonterminal
	| Parameter: "&" Nonterminal nonterminal 
	| Parametrized: ParameterizedNonterminal pnonterminal "[" {Sym ","}+ parameters "]"
	| Start: "start" "[" Nonterminal nonterminal "]"
	| Labeled: Sym symbol NonterminalLabel label
// literals 
	| CharacterClass: Class charClass 
	| Literal: StringConstant string 
	| CaseInsensitiveLiteral: CaseInsensitiveStringConstant cistring
// regular expressions
	| Iter: Sym symbol "+" 
	| IterStar: Sym symbol "*" 
	| IterSep: "{" Sym symbol Sym sep "}" "+" 
	| IterStarSep: "{" Sym symbol Sym sep "}" "*" 
	| Optional: Sym symbol "?" 
	| Alternative: "(" Sym first "|" {Sym "|"}+ alternatives ")"
	| Sequence: "(" Sym first Sym+ sequence ")"
	// TODO: MinimalIter: Sym symbol IntegerConstant minimal "+"
	// TODO: MinimalIterSep: "{" Sym symbol Symbol sep "}" IntegerConstant minimal "+"
	// TODO | Permutation: "(" Sym first "~" {Sym "~"}+ participants ")"
	// TODO | Combination: "(" Sym first "#" {Sym "#"}+ elements ")"
	| Empty: "(" ")"
// conditionals
	| Column: "@" IntegerLiteral column // TODO: make unary conditional of symbol
	| EndOfLine: "$" // TODO: make unary conditional of symbol
	| StartOfLine: "^" // TODO: make unary conditional of symbol
	>  
	assoc ( 
	  left  ( Follow:     Sym symbol "\>\>" Sym match
	        | NotFollow:  Sym symbol "!\>\>" Sym match
	        )
	  | 
	  right ( Precede:    Sym match "\<\<" Sym symbol 
	        | NotPrecede: Sym match "!\<\<" Sym symbol
	        )
	)
	> 
	left Unequal:  Sym symbol "\\" Sym match 
	;

lexical TimePartNoTZ
	= [0-2] [0-9] [0-5] [0-9] [0-5] [0-9]
	| [0-2] [0-9] [0-5] [0-9] [0-5] [0-9] [, .] [0-9] 
	| [0-2] [0-9] [0-5] [0-9] [0-5] [0-9] [, .] [0-9] [0-9] 
	| [0-2] [0-9] [0-5] [0-9] [0-5] [0-9] [, .] [0-9] [0-9] [0-9] 
	| [0-2] [0-9] ":" [0-5] [0-9] ":" [0-5] [0-9] 
	| [0-2] [0-9] ":" [0-5] [0-9] ":" [0-5] [0-9] [, .] [0-9] 
	| [0-2] [0-9] ":" [0-5] [0-9] ":" [0-5] [0-9] [, .] [0-9] [0-9]
	| [0-2] [0-9] ":" [0-5] [0-9] ":" [0-5] [0-9] [, .] [0-9] [0-9] [0-9] 
	;

syntax Header
	= Parameters: Tags tags "module" QualifiedName name ModuleParameters params Import* imports 
	| Default: Tags tags "module" QualifiedName name Import* imports ;

lexical Name
    // Names are surrounded by non-alphabetical characters, i.e. we want longest match.
	=  ([A-Z a-z _] !<< [A-Z _ a-z] [0-9 A-Z _ a-z]* !>> [0-9 A-Z _ a-z]) \ RascalKeywords 
	| [\\] [A-Z _ a-z] [\- 0-9 A-Z _ a-z]* !>> [\- 0-9 A-Z _ a-z] 
	;

syntax SyntaxDefinition
	=  @Foldable Layout  : Visibility vis "layout"  Sym defined "=" Prod production ";" 
	|  @Foldable Lexical : "lexical" Sym defined "=" Prod production ";" 
	|  @Foldable Keyword : "keyword" Sym defined "=" Prod production ";"
	|  @Foldable Language: Start start "syntax" Sym defined "=" Prod production ";" ;

syntax Kind
	= Function: "function" 
	| Variable: "variable" 
	| All: "all" 
	| Anno: "anno" 
	| Data: "data" 
	| View: "view" 
	| Rule: "rule" 
	| Alias: "alias" 
	| Module: "module" 
	| Tag: "tag" ;

syntax Test
	= Labeled: Tags tags "test" Name label Expression expression 
	| Unlabeled: Tags tags "test" Expression expression
	| Parameterized: Tags tags "test" Parameters parameters Expression expression
	| LabeledParameterized: Tags tags "test" Name label Parameters parameters Expression expression 
	;

syntax ImportedModule
	= Default: QualifiedName name 
	| ActualsRenaming: QualifiedName name ModuleActuals actuals Renamings renamings 
	| Renamings: QualifiedName name Renamings renamings 
	| Actuals: QualifiedName name ModuleActuals actuals ;

syntax Target
	= Empty: 
	| Labeled: Name name ;

syntax IntegerLiteral
	= /*prefer()*/ DecimalIntegerLiteral: DecimalIntegerLiteral decimal 
	| /*prefer()*/ HexIntegerLiteral: HexIntegerLiteral hex 
	| /*prefer()*/ OctalIntegerLiteral: OctalIntegerLiteral octal ;

syntax FunctionBody
	= Default: "{" Statement* statements "}" ;
    
syntax Expression
	= NonEmptyBlock  : "{" Statement+ statements "}" 
	| bracket Bracket: "(" Expression expression ")" 
	| Closure        : Type type Parameters parameters "{" Statement+ statements "}" 
	| StepRange      : "[" Expression first "," Expression second ".." Expression last "]" 
	| VoidClosure    : Parameters parameters "{" Statement* statements "}" 
	| Visit          : Label label Visit visit 
	| Reducer        : "(" Expression init "|" Expression result "|" {Expression ","}+ generators ")" 
	| ReifiedType    : BasicType basicType "(" {Expression ","}* arguments ")" 
	| CallOrTree     : Expression expression "(" {Expression ","}* arguments ")"
	| Literal        : Literal literal 
	| Any            : "any" "(" {Expression ","}+ generators ")" 
	| All            : "all" "(" {Expression ","}+ generators ")" 
	| Comprehension  : Comprehension comprehension 
	| Set            : "{" {Expression ","}* elements "}" 
	| List           : "[" {Expression ","}* elements "]"
	| ReifyType      : "#" Type type 
	| Range          : "[" Expression first ".." Expression last "]"
	| Tuple          : "\<" {Expression ","}+ elements "\>" 
	| Map            : "(" {Mapping[Expression] ","}* mappings ")" 
	| It             : "it" 
	| QualifiedName  : QualifiedName qualifiedName 
	
	>  Subscript  : Expression expression "[" {Expression ","}+ subscripts "]" 
	| FieldAccess : Expression expression "." Name field 
	| FieldUpdate : Expression expression "[" Name key "=" Expression replacement "]" 
	| FieldProject: Expression expression "\<" {Field ","}+ fields "\>" 
	| Is : Expression expression "is" Name name
	| Has : Expression expression "has" Name name
	> IsDefined: Expression argument "?" 
	> Negation: "!" Expression argument 
	| Negative: "\<" !<< "-" Expression argument 
	> TransitiveClosure: Expression argument "+" 
	| TransitiveReflexiveClosure: Expression argument "*" 
	> SetAnnotation: Expression expression "[" "@" Name name "=" Expression value "]" 
	| GetAnnotation: Expression expression "@" Name name 
	> left Composition: Expression lhs "o" Expression rhs 
	> left ( Product: Expression lhs "*" Expression rhs  
		   | Join   : Expression lhs "join" Expression rhs 
	       )
	> left ( Modulo: Expression lhs "%" Expression rhs  
		   | Division: Expression lhs "/" Expression rhs 
	       )
	> left Intersection: Expression lhs "&" Expression rhs 
	> left ( Addition   : Expression lhs "+" Expression rhs  
		   | Subtraction: Expression lhs "-" Expression rhs 
	       )
	> non-assoc (  NotIn: Expression lhs "notin" Expression rhs  
		        |  In: Expression lhs "in" Expression rhs 
	)
	> non-assoc ( GreaterThanOrEq: Expression lhs "\>=" Expression rhs  
		        | LessThanOrEq   : Expression lhs "\<=" Expression rhs 
		        | LessThan       : Expression lhs "\<" !>> "-" Expression rhs 
		        | GreaterThan    : Expression lhs "\>" Expression rhs 
	            )
	> left IfThenElse: Expression condition "?" Expression thenExp ":" Expression elseExp
	> non-assoc ( Equals         : Expression lhs "==" Expression rhs
	            | NonEquals      : Expression lhs "!=" Expression rhs 
	            )
	> non-assoc IfDefinedOtherwise: Expression lhs "?" Expression rhs
	> non-assoc ( NoMatch: Pattern pattern "!:=" Expression expression  
		        | Match: Pattern pattern ":=" Expression expression 
		        | /*prefer()*/ Enumerator: Pattern pattern "\<-" Expression expression 
	            ) 
	> non-assoc ( Implication: Expression lhs "==\>" Expression rhs  
		        | Equivalence: Expression lhs "\<==\>" Expression rhs 
	            )
	> left And: Expression lhs "&&" Expression rhs 
	> left Or: Expression lhs "||" Expression rhs 
	; 

syntax UserType
	= Name: QualifiedName name 
	| Parametric: QualifiedName name "[" {Type ","}+ parameters "]" ;

syntax Import
	= Extend: "extend" ImportedModule module ";" 
	| Default: "import" ImportedModule module ";" 
	| Syntax: SyntaxDefinition syntax ;

syntax Body
	= Toplevels: Toplevel* toplevels ;

lexical URLChars
	= ![\t-\n \r \  \< |]* ;

lexical TimeZonePart
	= [+ \-] [0-1] [0-9] ":" [0-5] [0-9] 
	| "Z" 
	| [+ \-] [0-1] [0-9] 
	| [+ \-] [0-1] [0-9] [0-5] [0-9] 
	;

syntax ProtocolPart
	= NonInterpolated: ProtocolChars protocolChars 
	| Interpolated: PreProtocolChars pre Expression expression ProtocolTail tail ;

syntax StringTemplate
	= IfThen    : "if"    "(" {Expression ","}+ conditions ")" "{" Statement* preStats StringMiddle body Statement* postStats "}" 
	| IfThenElse: "if"    "(" {Expression ","}+ conditions ")" "{" Statement* preStatsThen StringMiddle thenString Statement* postStatsThen "}" "else" "{" Statement* preStatsElse StringMiddle elseString Statement* postStatsElse "}" 
	| For       : "for"   "(" {Expression ","}+ generators ")" "{" Statement* preStats StringMiddle body Statement* postStats "}" 
	| DoWhile   : "do"    "{" Statement* preStats StringMiddle body Statement* postStats "}" "while" "(" Expression condition ")" 
	| While     : "while" "(" Expression condition ")" "{" Statement* preStats StringMiddle body Statement* postStats "}" ;

lexical PreStringChars
	= @category="Constant" [\"] StringCharacter* [\<] ;

lexical CaseInsensitiveStringConstant
	= @category="Constant" "\'" StringCharacter* "\'" ;

lexical Backslash
	= [\\] !>> [/ \< \> \\] ;

syntax Label
	= Default: Name name ":" 
	| Empty: ;

lexical MidProtocolChars
	= "\>" URLChars "\<" ;

lexical NamedBackslash
	= [\\] !>> [\< \> \\] ;

syntax Field
	= Index: IntegerLiteral fieldIndex 
	| Name: Name fieldName ;

lexical JustDate
	= "$" DatePart ;

lexical PostPathChars
	=  "\>" URLChars "|" ;

syntax PathPart
	= NonInterpolated: PathChars pathChars 
	| Interpolated: PrePathChars pre Expression expression PathTail tail ;

lexical DatePart
	= [0-9] [0-9] [0-9] [0-9] "-" [0-1] [0-9] "-" [0-3] [0-9] 
	| [0-9] [0-9] [0-9] [0-9] [0-1] [0-9] [0-3] [0-9] ;

syntax FunctionModifier
	= Java: "java" 
	| Default: "default";

syntax Assignment
	= IfDefined: "?=" 
	| Division: "/=" 
	| Product: "*=" 
	| Intersection: "&=" 
	| Subtraction: "-=" 
	| Default: "=" 
	| Addition: "+=" ;

syntax Assignable
	= bracket Bracket   : "(" Assignable arg ")"
	| Variable          : QualifiedName qualifiedName
    | Subscript         : Assignable receiver "[" Expression subscript "]" 
	| FieldAccess       : Assignable receiver "." Name field 
	| IfDefinedOrDefault: Assignable receiver "?" Expression defaultExpression 
	| Constructor       : Name name "(" {Assignable ","}+ arguments ")"  
	| Tuple             : "\<" {Assignable ","}+ elements "\>" 
	| Annotation        : Assignable receiver "@" Name annotation  ;

lexical StringConstant
	= @category="Constant" "\"" StringCharacter* "\"" ;

syntax Assoc
	= Associative: "assoc" 
	| Left: "left" 
	| NonAssociative: "non-assoc" 
	| Right: "right" ;

syntax Replacement
	= Unconditional: Expression replacementExpression 
	| Conditional: Expression replacementExpression "when" {Expression ","}+ conditions ;

lexical ParameterizedNonterminal
	= [A-Z] [0-9 A-Z _ a-z]* !>> ![\[];
	
syntax DataTarget
	= Empty: 
	| Labeled: Name label ":" ;

lexical StringCharacter
	= "\\" [\" \' \< \> \\ b f n r t] 
	| UnicodeEscape 
	| OctalEscapeSequence 
	| ![\" \' \< \> \\]
	| [\n][\ \t]* [\'] // margin 
	;

lexical JustTime
	= "$T" TimePartNoTZ TimeZonePart? ;

lexical MidStringChars
	= @category="Constant" [\>] StringCharacter* [\<] ;

lexical ProtocolChars
	= [|] URLChars "://" !>> [\t-\n \r \ ];

lexical RegExpModifier
	= [d i m s]* ;

syntax Parameters
	= Default: "(" Formals formals ")" 
	| VarArgs: "(" Formals formals "..." ")" ;

lexical RegExp
	= ![/ \< \> \\] 
	| "\<" Name "\>" 
	| [\\] [/ \< \> \\] 
	| "\<" Name ":" NamedRegExp* "\>" 
	| Backslash ;

layout LAYOUTLIST
	= LAYOUT* !>> [\t-\n \r \ ] !>> "//" !>> "/*";

syntax LocalVariableDeclaration
	= Default: Declarator declarator 
	| Dynamic: "dynamic" Declarator declarator ;

lexical RealLiteral
	= [0-9]+ [D F d f] 
	| [0-9]+ [E e] [+ \-]? [0-9]+ [D F d f]?
	| [0-9]+ "." [0-9]* [D F d f]? 
	| [0-9]+ "." [0-9]* [E e] [+ \-]? [0-9]+ [D F d f]? 
	| [.] !<< "." [0-9]+ [D F d f]? 
	| [.] !<< "." [0-9]+ [E e] [+ \-]? [0-9]+ [D F d f]? 
	;

syntax Range
	= FromTo: Char start "-" Char end 
	| Character: Char character ;

syntax LocationLiteral
	= Default: ProtocolPart protocolPart PathPart pathPart ;

syntax ShellCommand
	= SetOption: "set" QualifiedName name Expression expression 
	| Undeclare: "undeclare" QualifiedName name 
	| Help: "help" 
	| Edit: "edit" QualifiedName name 
	| Unimport: "unimport" QualifiedName name 
	| ListDeclarations: "declarations" 
	| Quit: "quit" 
	| History: "history" 
	| Test: "test" 
	| ListModules: "modules" ;

syntax StringMiddle
	= Mid: MidStringChars mid 
	| Template: MidStringChars mid StringTemplate template StringMiddle tail 
	| Interpolated: MidStringChars mid Expression expression StringMiddle tail ;

syntax QualifiedName
	= Default: {Name "::"}+ names !>> "::" ;

lexical DecimalIntegerLiteral
	= "0" !>> [0-9 A-Z _ a-z] 
	| [1-9] [0-9]* !>> [0-9 A-Z _ a-z] ;

syntax DataTypeSelector
	= Selector: QualifiedName sort "." Name production ;

syntax StringTail
	= MidInterpolated: MidStringChars mid Expression expression StringTail tail 
	| Post: PostStringChars post 
	| MidTemplate: MidStringChars mid StringTemplate template StringTail tail ;

syntax PatternWithAction
	= Replacing: Pattern pattern "=\>" Replacement replacement 
	| Arbitrary: Pattern pattern ":" Statement statement ;

lexical LAYOUT
	= Comment 
	| [\t-\n \r \ ] 
	;

syntax Visit
	= GivenStrategy: Strategy strategy "visit" "(" Expression subject ")" "{" Case+ cases "}" 
	| DefaultStrategy: "visit" "(" Expression subject ")" "{" Case+ cases "}" ;

start syntax Commands
	= Command+
	;

start syntax Command
	= Expression: Expression expression 
	| Declaration: Declaration declaration 
	| Shell: ":" ShellCommand command 
	| Statement: Statement statement 
	| Import: Import imported ;

lexical TagString
	= "{" (![}] | ([\\][}]))* "}" ;

syntax ProtocolTail
	= Mid: MidProtocolChars mid Expression expression ProtocolTail tail 
	| Post: PostProtocolChars post ;

lexical Nonterminal
	= [A-Z] !<< [A-Z] [0-9 A-Z _ a-z]* !>> [0-9 A-Z _ a-z] !>> [\[];

syntax PathTail
	= Mid: MidPathChars mid Expression expression PathTail tail 
	| Post: PostPathChars post ;



syntax Visibility
	= Private: "private" 
	| Default: 
	| Public: "public" ;

syntax StringLiteral
	= Template: PreStringChars pre StringTemplate template StringTail tail 
	| Interpolated: PreStringChars pre Expression expression StringTail tail 
	| NonInterpolated: StringConstant constant ;

lexical Comment
	= @category="Comment" "/*" (![*] | [*] !>> [/]) "*/" 
	| @category="Comment" "//" ![\n]* [\n]
	;

lexical RegExp
	= @category="MetaVariable" [\<]  Expression expression [\>] ;

syntax Renamings
	= Default: "renaming" {Renaming ","}+ renamings ;

syntax Tags
	= Default: Tag* tags ;

syntax Formals
	= Default: {Pattern ","}* formals ;

lexical PostProtocolChars
	= "\>" URLChars "://" ;

syntax Start
	= Absent: 
	| Present: "start" ;

syntax Statement
	= Assert: "assert" Expression expression ";" 
	| AssertWithMessage: "assert" Expression expression ":" Expression message ";" 
	| Expression: Expression expression ";" 
	| Visit: Label label Visit visit 
	| While: Label label "while" "(" {Expression ","}+ conditions ")" Statement body 
	| DoWhile: Label label "do" Statement body "while" "(" Expression condition ")" ";" 
	| For: Label label "for" "(" {Expression ","}+ generators ")" Statement body 
	| IfThen: Label label "if" "(" {Expression ","}+ conditions ")" Statement thenStatement () !>> "else" 
	| IfThenElse: Label label "if" "(" {Expression ","}+ conditions ")" Statement thenStatement "else" Statement elseStatement 
	| Switch: Label label "switch" "(" Expression expression ")" "{" Case+ cases "}" 
	| Fail: "fail" Target target ";" 
	| Break: "break" Target target ";" 
	| Continue: "continue" Target target ";" 
	| Solve: "solve" "(" {QualifiedName ","}+ variables Bound bound ")" Statement body 
	| non-assoc Try: "try" Statement body Catch+ handlers 
	| TryFinally: "try" Statement body Catch+ handlers "finally" Statement finallyBody 
	| NonEmptyBlock: Label label "{" Statement+ statements "}" 
	| EmptyStatement: ";" 
	| GlobalDirective: "global" Type type {QualifiedName ","}+ names ";" 
	| Assignment: Assignable assignable Assignment operator Statement statement
	| non-assoc ( Return    : "return" Statement statement  
		        | Throw     : "throw" Statement statement 
		        | Insert    : "insert" DataTarget dataTarget Statement statement 
		        | Append    : "append" DataTarget dataTarget Statement statement 
	            )
    > FunctionDeclaration: FunctionDeclaration functionDeclaration 
	| VariableDeclaration: LocalVariableDeclaration declaration ";"
	; 
	
    
syntax StructuredType
	= Default: BasicType basicType "[" {TypeArg ","}+ arguments "]" ;

lexical NonterminalLabel
	= [a-z] [0-9 A-Z _ a-z]* !>> [0-9 A-Z _ a-z] ;

syntax FunctionType
	= TypeArguments: Type type "(" {TypeArg ","}* arguments ")" ;

syntax Case
	= @Foldable PatternWithAction: "case" PatternWithAction patternWithAction 
	| @Foldable Default: "default" ":" Statement statement ;

syntax Declarator
	= Default: Type type {Variable ","}+ variables ;

syntax Bound
	= Default: ";" Expression expression 
	| Empty: ;

keyword RascalKeywords
	= "int" 
	| "true" 
	| "bag" 
	| "num" 
	| "node" 
	| "finally" 
	| "private" 
	| "real" 
	| "list" 
	| "fail" 
	| "if" 
	| "tag" 
	| BasicType
	| "extend" 
	| "append" 
	| "repeat" 
	| "rel" 
	| "void" 
	| "non-assoc" 
	| "assoc" 
	| "test" 
	| "anno" 
	| "layout" 
	| "data" 
	| "join" 
	| "it" 
	| "bracket" 
	| "in" 
	| "import" 
	| "false" 
	| "all" 
	| "dynamic" 
	| "solve" 
	| "type" 
	| "try" 
	| "catch" 
	| "notin" 
	| "else" 
	| "insert" 
	| "switch" 
	| "return" 
	| "case" 
	| "while" 
	| "str" 
	| "throws" 
	| "visit" 
	| "tuple" 
	| "for" 
	| "assert" 
	| "loc" 
	| "default" 
	| "map" 
	| "alias" 
	| "any" 
	| "module" 
	| "bool" 
	| "public" 
	| "one" 
	| "throw" 
	| "set" 
	| "fun" 
	| "non-terminal" 
	| "rule" 
	| "constructor" 
	| "datetime" 
	| "value" 
	;

syntax Type
	= bracket Bracket: "(" Type type ")" 
	| User: UserType user 
	| Function: FunctionType function 
	| Structured: StructuredType structured 
	| Basic: BasicType basic 
	| Selector: DataTypeSelector selector 
	| Variable: TypeVar typeVar 
	| Symbol: Sym symbol
	;

syntax Declaration
	= Variable    : Tags tags Visibility visibility Type type {Variable ","}+ variables ";" 
	| Annotation  : Tags tags Visibility visibility "anno" Type annoType Type onType "@" Name name ";" 
	| Alias       : Tags tags Visibility visibility "alias" UserType user "=" Type base ";" 
	| Tag         : Tags tags Visibility visibility "tag" Kind kind Name name "on" {Type ","}+ types ";" 
	| DataAbstract: Tags tags Visibility visibility "data" UserType user ";" 
	| @Foldable Data : Tags tags Visibility visibility "data" UserType user "=" {Variant "|"}+ variants ";"
	| Rule           : Tags tags "rule" Name name PatternWithAction patternAction ";" 
	| Function       : FunctionDeclaration functionDeclaration 
	| @Foldable Test : Test test ";" 
	;

syntax Class
	= SimpleCharclass: "[" Range* ranges "]" 
	| Complement: "!" Class charClass 
	> left Difference: Class lhs "-" Class rhs 
	> left Intersection: Class lhs "&&" Class rhs 
	> left Union: Class lhs "||" Class rhs 
	| bracket Bracket: "(" Class charclass ")" ;

lexical RegExpLiteral
	= "/" RegExp* "/" RegExpModifier ;

syntax FunctionModifiers
	= List: FunctionModifier* modifiers ;

syntax Comprehension
	= Set: "{" {Expression ","}+ results "|" {Expression ","}+ generators "}" 
	| Map: "(" Expression from ":" Expression to "|" {Expression ","}+ generators ")" 
	| List: "[" {Expression ","}+ results "|" {Expression ","}+ generators "]" ;

syntax Variant
	= NAryConstructor: Name name "(" {TypeArg ","}* arguments ")" ;

syntax FunctionDeclaration
	= Abstract: Tags tags Visibility visibility Signature signature ";" 
	| @Foldable Expression: Tags tags Visibility visibility Signature signature "=" Expression expression ";"
	| @Foldable Conditional: Tags tags Visibility visibility Signature signature "=" Expression expression "when" {Expression ","}+ conditions ";"
	| @Foldable Body: Tags tags Visibility visibility Signature signature FunctionBody body ;

lexical PreProtocolChars
	= "|" URLChars "\<" ;

lexical NamedRegExp
	= "\<" Name "\>" 
	| [\\] [/ \< \> \\] 
	| NamedBackslash 
	| ![/ \< \> \\] ;

syntax ProdModifier
	= Associativity: Assoc associativity 
	| Bracket: "bracket" 
	| Tag: Tag tag;

syntax Toplevel
	= GivenVisibility: Declaration declaration ;

lexical PostStringChars
	= @category="Constant" [\>] StringCharacter* [\"] ;

lexical HexIntegerLiteral
	= [0] [X x] [0-9 A-F a-f]+ !>> [0-9 A-Z _ a-z] ;

syntax TypeVar
	= Free: "&" Name name 
	| Bounded: "&" Name name "\<:" Type bound ;

lexical OctalEscapeSequence
	= "\\" [0-7] [0-7] !>> [0-7] 
	| "\\" [0-3] [0-7] [0-7] 
	| "\\" [0-7] !>> [0-7]
	;

syntax BasicType
	= Value: "value" 
	| Loc: "loc" 
	| Node: "node" 
	| Num: "num" 
	| ReifiedType: "type" 
	| Bag: "bag" 
	| Int: "int" 
	| Relation: "rel" 
	| ReifiedTypeParameter: "parameter" 
	| Real: "real" 
	| ReifiedFunction: "fun" 
	| Tuple: "tuple" 
	| String: "str" 
	| Bool: "bool" 
	| ReifiedReifiedType: "reified" 
	| Void: "void" 
	| ReifiedNonTerminal: "non-terminal" 
	| DateTime: "datetime" 
	| Set: "set" 
	| Map: "map" 
	| ReifiedConstructor: "constructor" 
	| List: "list" 
	| ReifiedAdt: "adt" 
	;

lexical Char
	= @category="Constant" "\\" [\  \" \' \- \< \> \[ \\ \] b f n r t] 
	| @category="Constant" ![\  \" \' \- \< \> \[ \\ \]] 
	| @category="Constant" UnicodeEscape 
	| @category="Constant" OctalEscapeSequence ;

syntax Prod
	= Reference: ":" Name referenced
	// TODO: uncomment below and make Sym*->Sym+ in Labeled and Unlabeled 
	// | LabeledEmpty: ProdModifier* modifiers Name name ":" "(" ")"
	// | UnlabeledEmpty: ProdModifier* modifiers "(" ")"
	| Labeled: ProdModifier* modifiers Name name ":" Sym* args 
	| Others: "..." 
	| Unlabeled: ProdModifier* modifiers Sym* args
	| @Foldable AssociativityGroup: Assoc associativity "(" Prod group ")" 
	> left All   : Prod lhs "|" Prod rhs 
	> left First : Prod lhs "\>" !>> "\>" Prod rhs
	;

syntax DateTimeLiteral
	= /*prefer()*/ DateLiteral: JustDate date 
	| /*prefer()*/ TimeLiteral: JustTime time 
	| /*prefer()*/ DateAndTimeLiteral: DateAndTime dateAndTime ;

lexical PrePathChars
	= URLChars "\<" ;

syntax Mapping[&T]
	= Default: &T from ":" &T to 
	;

lexical MidPathChars
	= "\>" URLChars "\<" ;

syntax Pattern
	= Set                 : "{" {Pattern ","}* elements "}" 
	| List                : "[" {Pattern ","}* elements "]" 
	| QualifiedName       : QualifiedName qualifiedName 
	| MultiVariable       : QualifiedName qualifiedName "*" 
	| Literal             : Literal literal 
	| Tuple               : "\<" {Pattern ","}+ elements "\>" 
	| TypedVariable       : Type type Name name 
	| Map                 : "(" {Mapping[Pattern] ","}* mappings ")" 
	| ReifiedType         : BasicType basicType "(" {Pattern ","}* arguments ")" 
	| CallOrTree          : Pattern expression "(" {Pattern ","}* arguments ")" 
	> VariableBecomes     : Name name ":" Pattern pattern
	| Guarded             : "[" Type type "]" Pattern pattern 
	| Descendant          : "/" Pattern pattern 
	| Anti                : "!" Pattern pattern 
	| TypedVariableBecomes: Type type Name name ":" Pattern pattern 
    ;
    
syntax Tag
	= @Foldable @category="Comment" Default   : "@" Name name TagString contents 
	| @Foldable @category="Comment" Empty     : "@" Name name 
	| @Foldable @category="Comment" Expression: "@" Name name "=" Expression expression ;

syntax ModuleActuals
	= Default: "[" {Type ","}+ types "]" ;
