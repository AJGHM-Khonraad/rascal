module experiments::Compiler::Tests::BinaryDependencies

import experiments::Compiler::Compile;
import experiments::Compiler::Execute;
import util::SystemAPI;
import IO;
import util::Reflective;
import util::FileSystem;

private void clean(loc t) {
  for (/file(loc f) := crawl(t))
    remove(f);
}

@doc{check if dependency on a binary module for which no source module is available works}
test bool simpleBinaryDependency() {
   top = |test-modules:///simpleBinaryDependency|;
   clean(top);
   
   // write two modules in different source folders, A and B
   writeFile(top + "a/BDTestA.rsc",
     "module BDTestA
     'import BDTestB;
     'int testa() = testb();
     'int main() = testa();
     ");
     
   writeFile(top + "b/BDTestB.rsc",
     "module BDTestB
     'int testb() = 42;
     ");
     
   // first we compile module B to a B binary 
   pcfgB = pathConfig(srcs=[top + "b", |std:///|], bin=top + "BinB", libs=[top + "BinB"]);
   compileAndLink("BDTestB", pcfgB, jvm=true);
   
   // then we compile A which uses B, but only on the library path available as binary
   pcfgA = pathConfig(srcs=[top + "a", |std:///|], bin=top + "BinA", libs=[top + "BinB", top + "BinA"]);
   compileAndLink("BDTestA", pcfgA, jvm=true); 
   
   // see if it works
   return execute("BDTestA", pcfgA, recompile=false) == 42; 
}

@doc{single module recompilation after edit should have an effect}
test bool simpleRecompile() {
   top = |test-modules:///simpleRecompile|;
   clean(top);
   
   // create a module
   writeFile(top + "a/BDTestA.rsc",
     "module BDTestA
     'int main() = 42;
     ");
     
   // compile the module  
   pcfgA = pathConfig(srcs=[top + "a", |std:///|], bin=top + "BinA", libs=[top + "BinA"]);
   compileAndLink("BDTestA", pcfgA, jvm=true); 
   
   // run the module
   first = execute("BDTestA", pcfgA, recompile=false);
   
   // edit the module
   writeFile(top + "a/BDTestA.rsc",
     "module BDTestA
     'int main() = 43;
     ");
  
   // recompile
   compileAndLink("BDTestA", pcfgA, jvm=true); 
     
   // expect change  
   second = execute("BDTestA", pcfgA, recompile=false);
  
   return first != second && second == 43;
}

@doc{both files are in the same source directory, and the imported one receives an update before the importer is recompiled}
test bool sourceDependencyRecompile() {
   top = |test-modules:///sourceDependencyRecompile|;
   clean(top);
   
   writeFile(top + "BDTestA.rsc",
     "module BDTestA
     'import BDTestB;
     'int testa() = testb();
     'int main() = testa();
     ");
     
   writeFile(top + "BDTestB.rsc",
     "module BDTestB
     'int testb() = 42;
     ");
     
   pcfgA = pathConfig(srcs=[top, |std:///|], bin=top + "Bin", libs=[top + "Bin"]);
   compileAndLink("BDTestA", pcfgA, jvm=true); 
   
   first = execute("BDTestA", pcfgA, recompile=false); 
   
   writeFile(top + "BDTestB.rsc",
     "module BDTestB
     'int testb() = 43;
     ");
     
   // notice the top module is recompiled, not the changed module  
   compileAndLink("BDTestA", pcfgA, jvm=true);
   
   second = execute("BDTestA", pcfgA, recompile=false);
   
   return first != second && second == 43; 
}

@doc{the imported module is only on the library path in binary form, and this imported library receives an update before the importer is recompiled}
test bool binaryDependencyRecompile() {
   top = |test-modules:///binaryDependencyRecompile|;
   clean(top);  
   
   writeFile(top + "a/BDTestA.rsc",
     "module BDTestA
     'import BDTestB;
     'int testa() = testb();
     'int main() = testa();
     ");
     
   writeFile(top + "b/BDTestB.rsc",
     "module BDTestB
     'int testb() = 42;
     ");
     
    // first we compile module B to a B binary 
   pcfgB = pathConfig(srcs=[top + "b", |std:///|], bin=top + "BinB", libs=[top + "BinB"]);
   compileAndLink("BDTestB", pcfgB, jvm=true);
     
   // then in another bin we compile A  
   pcfgA = pathConfig(srcs=[top + "a", |std:///|], bin=top + "BinA", libs=[top + "BinA",top + "BinB"]);
   compileAndLink("BDTestA", pcfgA, jvm=true); 
   
   // see what comes out
   first = execute("BDTestA", pcfgA, recompile=false); 
   
   // change module B
   writeFile(top + "b/BDTestB.rsc",
     "module BDTestB
     'int testb() = 43;
     ");
     
   // recompile B 
   pcfgB = pathConfig(srcs=[top + "b", |std:///|], bin=top + "BinB", libs=[top + "BinB"]);
   compileAndLink("BDTestB", pcfgB, jvm=true);
     
   // recompile A
   pcfgA = pathConfig(srcs=[top + "a", |std:///|], bin=top + "BinA", libs=[top + "BinA",top + "BinB"]);
   compileAndLink("BDTestA", pcfgA, jvm=true); 
   
   // see what comes out
   second = execute("BDTestA", pcfgA, recompile=false);
   
   return first != second && second == 43; 
}

@doc{binary dependencies do not trigger a transitive recompile}
test bool binaryDependencyNoTransitiveRecompile() {
   top = |test-modules:///binaryDependencyNoTransitiveRecompile|;
   clean(top);
   
   writeFile(top + "a/BDTestA.rsc",
     "module BDTestA
     'import BDTestB;
     'int testa() = testb();
     'int main() = testa();
     ");
     
   writeFile(top + "b/BDTestB.rsc",
     "module BDTestB
     'int testb() = 42;
     ");
     
    // first we compile module B to a B binary 
   pcfgB = pathConfig(srcs=[top + "b", |std:///|], bin=top + "BinB", libs=[top + "BinB"]);
   compileAndLink("BDTestB", pcfgB, jvm=true);
     
   // then in another bin we compile A  
   pcfgA = pathConfig(srcs=[top + "a", |std:///|], bin=top + "BinA", libs=[top + "BinA", top + "BinB"]);
   compileAndLink("BDTestA", pcfgA, jvm=true); 
   
   // see what comes out
   first = execute("BDTestA", pcfgA, recompile=false); 
   
   // change module B
   writeFile(top + "b/BDTestB.rsc",
     "module BDTestB
     'int testb() = 43;
     ");
     
   // note: no recompilation for B 
     
   // recompile A, even with "recompile=true"
   pcfgA = pathConfig(srcs=[top + "a", |std:///|], bin=top + "BinA", libs=[top + "BinA", top + "BinB"]);
   compileAndLink("BDTestA", pcfgA, jvm=true, recompile=true); 
   
   // see what comes out
   second = execute("BDTestA", pcfgA, recompile=true);
   
   // no change expected
   return first == second; 
}
