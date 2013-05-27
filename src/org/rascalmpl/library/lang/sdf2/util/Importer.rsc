module lang::sdf2::util::Importer

import lang::sdf2::util::Load;
import lang::sdf2::util::SDF2Grammar;
import lang::rascal::format::Grammar;
import lang::rascal::grammar::definition::Modules;

@resource{sdf}
@doc{
  the sdf uri scheme works like this:
  sdf:///<modulename>
}
public str generate(str name, loc at) {
   def = loadSDF2Module(at.path, [|rascal:///|,|rascal:///src|]);
   gr = injectStarts(fuse(dup(sdf2grammar(at.path[1..], def))));
   return "module <name>
          '
          '<grammar2rascal(gr)>
          ";  
}