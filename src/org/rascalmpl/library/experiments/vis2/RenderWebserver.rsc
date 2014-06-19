module experiments::vis2::RenderWebserver

import util::Webserver;
import IO;

loc base = |file:///Users/paulklint/git/rascal/src/org/rascalmpl/library/experiments/vis2|;

loc startRenderWebserver() {
  loc site = |http://localhost:8081|;
  
  while (true) {
    try {
      serve(site, dispatchserver(page));
      return site;
    }  
    catch IO("Address already in use"): {
      site.port += 1; 
    }
  }
}
 
void stopRenderWebserver(loc site) {
  shutdown(site);
}

Response page(get(), /^\/$/                          , map[str,str] _)   = response(base + "index.html");
Response page(get(), /^\/show\/<f:.*>\/<c:.*\.html>$/, map[str, str] ps) = response((base + f) + c);
Response page(get(), /^\/show\/<c:[^\/]*>\.html$/    , map[str, str] ps) = response(((base +c) + "<c>.html"));
Response page(get(), /^\/edit/                       , map[str, str] ps) = response(edit(ps["concept"], ps["new"] == "true")); 
Response page(get(), /^\/save/                       , map[str, str] ps) = response(save(ps["concept"], ps["newcontent"], ps["new"] == "true"));
Response page(get(), /^\/compile/                    , map[str, str] ps) = response(compile(ps["name"]));
Response page(get(), /^\/validate/                   , map[str, str] ps) = response(validateAnswer(ps));
Response page(get(), /^\/validateExam/               , map[str, str] ps) = response(validateExam(ps));

default Response page(get(), str path, map[str, str] ps) = response(base + path); 

default Response page(!get(), str path, map[str, str] ps) {
  throw "invalid <path> with <ps>";
}