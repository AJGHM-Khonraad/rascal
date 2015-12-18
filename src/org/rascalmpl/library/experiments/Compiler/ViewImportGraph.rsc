module experiments::Compiler::ViewImportGraph

import String;
import Relation;
import Set;
import IO;

import util::Reflective;
import lang::rascal::types::CheckTypes;

import experiments::vis2::sandbox::Figure;
import experiments::vis2::sandbox::FigureServer;

rel[str,str] importGraph = {
<"experiments::Compiler::muRascal::AST", "Message">,
<"experiments::Compiler::muRascal::AST", "List">,
<"experiments::Compiler::muRascal::AST", "Node">,
<"experiments::Compiler::muRascal::AST", "Type">,
<"experiments::Compiler::muRascal::AST", "ParseTree">,

<"experiments::Compiler::muRascal::Implode", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::muRascal::Implode", "IO">,
<"experiments::Compiler::muRascal::Implode", "ValueIO">,
<"experiments::Compiler::muRascal::Implode", "Set">,
<"experiments::Compiler::muRascal::Implode", "List">,
<"experiments::Compiler::muRascal::Implode", "String">,
<"experiments::Compiler::muRascal::Implode", "Type">,
<"experiments::Compiler::muRascal::Implode", "Map">,
<"experiments::Compiler::muRascal::Implode", "experiments::Compiler::muRascal::MuBoolExp">,
<"experiments::Compiler::muRascal::Implode", "experiments::Compiler::Rascal2muRascal::TypeUtils">,

<"experiments::Compiler::muRascal::Load", "ParseTree">,
<"experiments::Compiler::muRascal::Load", "experiments::Compiler::muRascal::Parse">,
<"experiments::Compiler::muRascal::Load", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::muRascal::Load", "experiments::Compiler::muRascal::Implode">,
<"experiments::Compiler::muRascal::Load", "experiments::Compiler::muRascal::Syntax">,

<"experiments::Compiler::muRascal::MuBoolExp", "List">,
<"experiments::Compiler::muRascal::MuBoolExp", "IO">,
<"experiments::Compiler::muRascal::MuBoolExp", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::muRascal::MuBoolExp", "experiments::Compiler::Rascal2muRascal::TmpAndLabel">,
<"experiments::Compiler::muRascal::MuBoolExp", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::muRascal::MuBoolExp", "Type">,

<"experiments::Compiler::muRascal::Parse", "experiments::Compiler::muRascal::Syntax">,
<"experiments::Compiler::muRascal::Parse", "ParseTree">,
<"experiments::Compiler::muRascal::Parse", "IO">,

<"experiments::Compiler::muRascal2RVM::mu2rvm", "IO">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "Type">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "List">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "Set">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "Map">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "ListRelation">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "Node">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "Message">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "String">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "ToString">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "experiments::Compiler::RVM::AST">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "experiments::Compiler::Rascal2muRascal::TypeReifier">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "experiments::Compiler::muRascal2RVM::ToplevelType">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "experiments::Compiler::muRascal2RVM::PeepHole">,
<"experiments::Compiler::muRascal2RVM::mu2rvm", "experiments::Compiler::muRascal2RVM::StackValidator">,

<"experiments::Compiler::muRascal2RVM::PeepHole", "IO">,
<"experiments::Compiler::muRascal2RVM::PeepHole", "String">,
<"experiments::Compiler::muRascal2RVM::PeepHole", "Set">,
<"experiments::Compiler::muRascal2RVM::PeepHole", "List">,
<"experiments::Compiler::muRascal2RVM::PeepHole", "Map">,
<"experiments::Compiler::muRascal2RVM::PeepHole", "experiments::Compiler::RVM::AST">,

<"experiments::Compiler::muRascal2RVM::StackValidator", "experiments::Compiler::RVM::AST">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "Type">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "List">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "Set">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "String">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "Relation">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "Map">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "IO">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "Node">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "analysis::graphs::Graph">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "ParseTree">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "util::Math">,
<"experiments::Compiler::muRascal2RVM::StackValidator", "experiments::Compiler::muRascal::AST">,

<"experiments::Compiler::muRascal2RVM::ToplevelType", "List">,

<"experiments::Compiler::Rascal2muRascal::ModuleInfo", "List">,
<"experiments::Compiler::Rascal2muRascal::ModuleInfo", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Rascal2muRascal::ModuleInfo", "experiments::Compiler::Rascal2muRascal::TmpAndLabel">,
<"experiments::Compiler::Rascal2muRascal::ModuleInfo", "IO">,

<"experiments::Compiler::Rascal2muRascal::ParseModule", "ParseTree">,
<"experiments::Compiler::Rascal2muRascal::ParseModule", "lang::rascal::syntax::Rascal">,

<"experiments::Compiler::Rascal2muRascal::RascalConstantCall", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Rascal2muRascal::RascalConstantCall", "ParseTree">,
<"experiments::Compiler::Rascal2muRascal::RascalConstantCall", "String">,
<"experiments::Compiler::Rascal2muRascal::RascalConstantCall", "List">,
<"experiments::Compiler::Rascal2muRascal::RascalConstantCall", "Set">,
<"experiments::Compiler::Rascal2muRascal::RascalConstantCall", "Type">,

<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "IO">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "Map">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "Set">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "String">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "lang::rascal::syntax::Rascal">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "ParseTree">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "lang::rascal::types::AbstractName">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "lang::rascal::types::CheckerConfig">,       // to be sure
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::Rascal2muRascal::ModuleInfo">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::Rascal2muRascal::RascalType">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::Rascal2muRascal::TypeReifier">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::Rascal2muRascal::TmpAndLabel">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::Rascal2muRascal::RascalExpression">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::Rascal2muRascal::RascalPattern">,
<"experiments::Compiler::Rascal2muRascal::RascalDeclaration", "experiments::Compiler::Rascal2muRascal::RascalStatement">,

<"experiments::Compiler::Rascal2muRascal::RascalExpression", "IO">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "ValueIO">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "String">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "Node">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "Map">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "Set">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "ParseTree">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "util::Reflective">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "lang::rascal::syntax::Rascal">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::muRascal::MuBoolExp">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "lang::rascal::types::TestChecker">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "lang::rascal::types::CheckTypes">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "lang::rascal::types::AbstractName">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "lang::rascal::types::AbstractType">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "lang::rascal::types::TypeInstantiation">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "lang::rascal::types::TypeExceptions">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "lang::rascal::types::CheckerConfig">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::TmpAndLabel">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::ModuleInfo">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::RascalType">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::TypeReifier">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::RascalConstantCall">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::RascalDeclaration">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::RascalPattern">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::RascalStatement">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::Rascal2muRascal::RascalConstantCall">,
<"experiments::Compiler::Rascal2muRascal::RascalExpression", "experiments::Compiler::RVM::Interpreter::ParsingTools">,

<"experiments::Compiler::Rascal2muRascal::RascalModule", "IO">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "Map">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "String">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "Set">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "List">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "Relation">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "util::Reflective">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "ParseTree">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "Type">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "lang::rascal::syntax::Rascal">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "lang::rascal::types::AbstractName">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "lang::rascal::types::AbstractType">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "lang::rascal::types::CheckTypes">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "lang::rascal::types::CheckerConfig">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "experiments::Compiler::Rascal2muRascal::ModuleInfo">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "experiments::Compiler::Rascal2muRascal::TmpAndLabel">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "experiments::Compiler::Rascal2muRascal::RascalType">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "experiments::Compiler::Rascal2muRascal::TypeReifier">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "experiments::Compiler::Rascal2muRascal::RascalDeclaration">,
<"experiments::Compiler::Rascal2muRascal::RascalModule", "experiments::Compiler::Rascal2muRascal::RascalExpression">,

<"experiments::Compiler::Rascal2muRascal::RascalPattern", "IO">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "ValueIO">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "Node">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "Map">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "Set">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "String">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "ParseTree">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "lang::rascal::syntax::Rascal">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "experiments::Compiler::Rascal2muRascal::RascalType">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "experiments::Compiler::Rascal2muRascal::TmpAndLabel">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "experiments::Compiler::Rascal2muRascal::TypeReifier">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "experiments::Compiler::Rascal2muRascal::ModuleInfo">, // just in case
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "lang::rascal::types::CheckerConfig">, // just in case
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "experiments::Compiler::Rascal2muRascal::RascalExpression">,
<"experiments::Compiler::Rascal2muRascal::RascalPattern", "experiments::Compiler::RVM::Interpreter::ParsingTools">,

<"experiments::Compiler::Rascal2muRascal::RascalStatement", "IO">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "ValueIO">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "Node">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "Map">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "Set">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "String">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "ParseTree">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "util::Reflective">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "lang::rascal::syntax::Rascal">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "lang::rascal::types::CheckerConfig">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::Rascal2muRascal::TmpAndLabel">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::Rascal2muRascal::ModuleInfo">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::Rascal2muRascal::RascalDeclaration">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::Rascal2muRascal::RascalExpression">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::Rascal2muRascal::RascalPattern">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::Rascal2muRascal::RascalType">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::Rascal2muRascal::TypeReifier">,
<"experiments::Compiler::Rascal2muRascal::RascalStatement", "experiments::Compiler::muRascal::AST">,

<"experiments::Compiler::Rascal2muRascal::TmpAndLabel", "List">,
<"experiments::Compiler::Rascal2muRascal::TmpAndLabel", "Type">,
<"experiments::Compiler::Rascal2muRascal::TmpAndLabel", "lang::rascal::syntax::Rascal">,

<"experiments::Compiler::Rascal2muRascal::TypeReifier", "lang::rascal::types::CheckerConfig">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "lang::rascal::types::AbstractName">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "lang::rascal::types::AbstractType">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "lang::rascal::grammar::definition::Symbols">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "ParseTree">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "List">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "Map">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "Set">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "Relation">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "Type">,
<"experiments::Compiler::Rascal2muRascal::TypeReifier", "IO">,

<"experiments::Compiler::Rascal2muRascal::TypeUtils", "IO">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "ValueIO">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "Set">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "Map">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "Node">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "Relation">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "String">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "util::Reflective">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "lang::rascal::syntax::Rascal">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "lang::rascal::grammar::definition::Symbols">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "lang::rascal::types::CheckerConfig">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "lang::rascal::types::AbstractName">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "lang::rascal::types::AbstractType">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "experiments::Compiler::Rascal2muRascal::TypeReifier">,
<"experiments::Compiler::Rascal2muRascal::TypeUtils", "experiments::Compiler::Rascal2muRascal::TmpAndLabel">,

<"experiments::Compiler::RVM::AST", "Type">,
<"experiments::Compiler::RVM::AST", "Message">,
<"experiments::Compiler::RVM::AST", "ParseTree">,

<"experiments::Compiler::RVM::ExecuteProgram", "experiments::Compiler::RVM::AST">,
<"experiments::Compiler::RVM::ExecuteProgram", "Type">,


<"experiments::Compiler::RVM::Load", "experiments::Compiler::RVM::AST">,
<"experiments::Compiler::RVM::Load", "experiments::Compiler::RVM::Parse">,
<"experiments::Compiler::RVM::Load", "ParseTree">,

<"experiments::Compiler::RVM::Parse", "experiments::Compiler::RVM::Syntax">,
<"experiments::Compiler::RVM::Parse", "ParseTree">,

<"experiments::Compiler::RVM::Syntax", "ParseTree">,

<"experiments::Compiler::Compile", "IO">,
<"experiments::Compiler::Compile", "ValueIO">,
<"experiments::Compiler::Compile", "String">,
<"experiments::Compiler::Compile", "Message">,
<"experiments::Compiler::Compile", "ParseTree">,
<"experiments::Compiler::Compile", "util::Reflective">,
<"experiments::Compiler::Compile", "util::Benchmark">,
<"experiments::Compiler::Compile", "Map">,
<"experiments::Compiler::Compile", "Relation">,
<"experiments::Compiler::Compile", "lang::rascal::syntax::Rascal">,
<"experiments::Compiler::Compile", "experiments::Compiler::Rascal2muRascal::ParseModule">,
<"experiments::Compiler::Compile", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Compile", "experiments::Compiler::RVM::AST">,
<"experiments::Compiler::Compile", "experiments::Compiler::Rascal2muRascal::RascalModule">,
<"experiments::Compiler::Compile", "experiments::Compiler::Rascal2muRascal::TypeUtils">,
<"experiments::Compiler::Compile", "experiments::Compiler::muRascal2RVM::mu2rvm">,
<"experiments::Compiler::Compile", "lang::rascal::types::TestChecker">,
<"experiments::Compiler::Compile", "lang::rascal::types::CheckTypes">,
<"experiments::Compiler::Compile", "lang::rascal::types::AbstractName">,

<"experiments::Compiler::CompileMuLibrary", "IO">,
<"experiments::Compiler::CompileMuLibrary", "ValueIO">,
<"experiments::Compiler::CompileMuLibrary", "util::Reflective">,
<"experiments::Compiler::CompileMuLibrary", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::CompileMuLibrary", "experiments::Compiler::muRascal::Load">,
<"experiments::Compiler::CompileMuLibrary", "experiments::Compiler::RVM::AST">,
<"experiments::Compiler::CompileMuLibrary", "experiments::Compiler::muRascal2RVM::mu2rvm">,
<"experiments::Compiler::CompileMuLibrary", "experiments::Compiler::muRascal2RVM::StackValidator">, // TODO: hide these two,
<"experiments::Compiler::CompileMuLibrary", "experiments::Compiler::muRascal2RVM::PeepHole">,

<"experiments::Compiler::Execute", "IO">,
<"experiments::Compiler::Execute", "ValueIO">,
<"experiments::Compiler::Execute", "String">,
<"experiments::Compiler::Execute", "Type">,
<"experiments::Compiler::Execute", "Message">,
<"experiments::Compiler::Execute", "List">,
<"experiments::Compiler::Execute", "Map">,
<"experiments::Compiler::Execute", "Set">,
<"experiments::Compiler::Execute", "ParseTree">,
<"experiments::Compiler::Execute", "util::Benchmark">,
<"experiments::Compiler::Execute", "analysis::graphs::Graph">,
<"experiments::Compiler::Execute", "DateTime">,
<"experiments::Compiler::Execute", "experiments::Compiler::muRascal::AST">,
<"experiments::Compiler::Execute", "experiments::Compiler::muRascal::Load">,
<"experiments::Compiler::Execute", "experiments::Compiler::RVM::AST">,
<"experiments::Compiler::Execute", "experiments::Compiler::RVM::ExecuteProgram">,
<"experiments::Compiler::Execute", "experiments::Compiler::Compile">,
<"experiments::Compiler::Execute", "experiments::Compiler::muRascal2RVM::mu2rvm">,
<"experiments::Compiler::Execute", "util::Reflective">
};

set[str] exclude = {"IO", "String", "Set", "Map", "List", "Relation", "ParseTree", "DateTime", "ValueIO", "Message", "Node", "Type", "util::Math", "util::Benchmark", "analysis::graphs::Graph",  "util::Reflective"};

str baseName(str qualifiedName){
    int n = findLast(qualifiedName, "::");
    return n > 0 ? qualifiedName[n+2 ..] : qualifiedName;
}

Figure makeToolTip(str qualifiedName){
    //imports = [ text(baseName(nm), fontSize=11, fontColor="black") | nm <- importGraph[qualifiedName]];
    //return box(fig=vcat(figs=imports, grow=1.2), grow=1.2, fillColor="white");
    return box(fig=text(qualifiedName, fontSize=14, fontColor="green"), fillColor="orange", grow=1.2) ;
}

void viewImportGraph(){

    modules = [<nm, box(fig=text(baseName(nm), fontSize=11, fontColor="white"), fillColor = "black", tooltip=makeToolTip(nm))> | nm <- carrier(importGraph), nm notin exclude];
    edges = [edge(nm2, nm1) | <nm1, nm2> <- importGraph, nm1 notin exclude, nm2 notin exclude];
    g = box(width = 8000, height = 1000, fig=graph(modules, edges, width = 8000, height = 1000, lineWidth=1, graphOptions=graphOptions(nodeSep=0,layerSep=50, edgeSep=0)));
    render(g); 
 }
 
 void main() { viewImportGraph(); }
 
 void checkImportGraph(){
    modules = carrier(importGraph);
    C1BIN=pathConfig(binDir=|home:///c1bin|, libPath=[|home:///c1bin|]);
    for(m <- modules){
        try {
            ig = getCachedConfig(m, C1BIN).importGraph;
            if(size(ig) == 0){
                println("+++empty imports+++ <m>");
            }
        } catch e: {
            println("---not found --- <e>");
        }
    }
 }