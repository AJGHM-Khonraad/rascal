module experiments::Compiler::Commands::Compile

import IO;
import ParseTree;
import experiments::Compiler::Compile;

layout L = [\ ]+ !>> [\ ];

start syntax CompileArgs 
    = "rascalc" Option* options ModuleName* modulesToCompile;

syntax Option 
    = "--srcPath" Path path
    | "--libPath" Path path
    | "--binDir" Path path
    | "--jvm"
    | "--verbose"
    | "--version"
    | "--help"
    ;

lexical ModuleName 
    = rascalName: {NamePart "::"}+
    | fileName: "/"? {NamePart "/"}+ ".rsc"
    ;

lexical NamePart 
    = ([A-Za-z_][A-Za-z0-9_]) !>> [A-Za-z0-9_];
    
lexical Path 
    = normal: (![ \t\n\"\'\\] | ("\\" ![])) * !>> ![ \t\n\"\'\\]
    | quoted: [\"] InsideQuote [\"]
    ;
lexical InsideQuote = ![\"]*;
    
loc toLocation((Path)`"<InsideQuote inside>"`) = toLocation("<inside>");
default loc toLocation(Path p) = toLocation("<p>");

loc toLocation(/^<fullPath:[\/].*>$/) = |file:///| + fullPath;
default loc toLocation(str relativePath) = |cwd:///| + relativePath;
    
int compile(str commandLine) {
    try {
        t = ([start[Syntax]]commandLine).top;
        if ((Option)`--help` <- t.options) {
            printHelp();
            return 0;
        }
        else if ((Option)`--version` <- t.options) {
            printHelp();
            return 0;
        }
        else if (_ <- t.modulesToCompile) {
            pcfg = PathConfig();
            pcfg.libPath += [ toLocation(p) | (Option)`--libPath <Path p>` <- t.options ];
            if ((Option)`--srcPath <Path _>` <- t.options) {
                pcfg.srcPath += [ toLocation(p) | (Option)`--srcPath <Path p>` <- t.options ];
            }
            else {
                pcfg.srcPath += |cwd:///|;
            }
            if ((Option)`--binDir <Path p>` <- t.options) {
                pcfg.binDir = toLocation(p);
            }

            bool verbose = (Option)`--verbose` <- t.options;
            bool useJVM = (Option)`--jvm` <- t.options;

            for (m <- t.modulesToCompile) {
                moduleName = getModuleName(m);
                println("compiling: <moduleName>");
                compileAndLink(moduleName, pcfg, useJVM = useJVM, serialize=true, verbose = verbose);
            }
            return 1;
        }
        else {
            printHelp();
            return 1;
        }
    }
    catch e: {
        println("Something went wrong:");
        println(e);
        printHelp();
    }
}

void printHelp() {
    println("rascalc [OPTION] ModulesToCompile");
    println("Compile and link one or more modules, the compiler will automatically compile all dependencies.");
    println("Options:");
    println("--srcPath path");
    println("\tAdd new source path, use multiple --srcPaths for multiple paths");
    println("--libPath path");
    println("\tAdd new lib paths, use multiple --libPaths for multiple paths");
    println("--binDir directory");
    println("\tSet the target directory for the bin files");
    println("--jvm");
    println("\tUse the JVM implemementation of the RVM");
    println("--verbose");
    println("\tMake the compiler verbose");
    println("--help");
    println("\tPrint this help message");
    println("--version");
    println("\tPrint version number");
}
