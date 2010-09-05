module experiments::RascalTutor::HTMLGenerator

import experiments::RascalTutor::HTMLUtils;

import String;
import ToString;
import IO;
import List;
import Scripting;

private list[str] listNesting = [];

private void pushList(str listType){
	listNesting = listType + listNesting;
}

private str popList(){
   t = head(listNesting);
   listNesting = tail(listNesting);
   return t;
}

private str listEntry(str listType, int nesting, str entry){
  start = "\<<listType>\>\n";
  
  currentNesting = size(listNesting);
  if(nesting == currentNesting){
  	 return li(markupRestLine(entry));
  } else if(nesting > currentNesting){
     startList = "";
     while(nesting > size(listNesting)){
       startList += start;
       pushList(listType);
     }
     return startList + li(markupRestLine(entry));
  } else {
     endList = "";
     while(nesting < size(listNesting)){
       endList +=  "\n\</<popList()>\>\n";
     }
     return endList + li(markupRestLine(entry));
  }
}

private str closeLists(){
  endList = "";
  while(size(listNesting) > 0){
    endList +=  "\n\</<popList()>\>\n";
  }
  return endList;
}

private str conceptPath = "";

private str markup(list[str] lines){
  return markup(lines, conceptPath);
}

public str markup(list[str] lines, str cp){
  conceptPath = cp;
  n = size(lines);
  int i = 0;
  str res = "";
  while(i < n){
    switch(lines[i]){
    // Sections
    case /^=<eqs:[=]+><label:[^=]*>[=]+/: { res += h(size(eqs), label); i += 1; }
    
    // Unordered lists
    case /^<stars:[\*]+><entry:.*>/: {
       i += 1;
       nl = 0;
       while(i < n && nl < 2){
         more = lines[i];
         if(startsWith(more, "*"))
            nl = 2;
         else {
            if(/^\s*$/ := more)
              nl += 1;
            else if(nl > 0){
               nl = 0;
               entry +=  br() + br() + more;
            } else
              entry += " " + more;
            i += 1;
          }
       }
       res += listEntry("ul", size(stars), entry); 
    }
    
    // Ordered lists
    case /^<hashes:[\#]+><entry:.*>/: {
       i += 1;
       nl = 0;
       while(i < n && nl < 2){
         more = lines[i];
         if(startsWith(more, "#"))
            nl = 2;
         else {
            if(/^\s*$/ := more)
              nl += 1;
            else if(nl > 0){
               nl = 0;
               entry +=  br() + br() + more;
            } else
              entry += " " + more;
            i += 1;
          }
       }
       res += listEntry("ol", size(hashes), entry); 
    }
   
    case /^\<screen\>\s*<codeLines:.*>$/: {
      res += closeLists();
      i += 1;
      start = i;
      while((i < n) && /^\<\/screen\>/ !:= lines[i]){
         codeLines += lines[i] + "\n";
         i += 1;
      }
      res += markupScreen(slice(lines, start, i - start));
      i += 1;
      }
      
    case /^\<listing\s*<name:.+>\>$/: {
      loc L = |stdlib:///|[path = name];
      try {
      	codeLines = readFileLines(L);
      	println("codeLines = <codeLines>");
      	res += markupListing(codeLines);
      } catch: res += "\<warning\>File <name> not found.\</warning\>";
      i += 1;
    }
      
    case /^\<listing\>\s*<rest:.*>$/: {
      res += closeLists();
      i += 1;
      codeLines = [];
      while((i < n) && /^\<\/listing\>/ !:= lines[i]){
         codeLines += lines[i];
         i += 1;
      }
      //res += pre("listing", codeLines);
      res += markupListing(codeLines);
      i += 1;
      }
    case /^$/: {
      res += closeLists();
      i += 1;
      if(i < n && size(lines[i]) == 0){
        i += 1;
        res += br() + br();
      } else
        res += "\n";
    }
      
    default: {
      res += closeLists() + markupRestLine(lines[i]) + "\n";
      i += 1;
    }
  }
  }
  res += closeLists();
  //println("markupLine ==\> <res>");
  return res;
}

public str markupRestLine(str line){
  ///println("markupRestLine(<line>)");
  return visit(line){
    
    case /^<op1:__?>+<text:[^_]><op2:__?>/: {
       if(op1 != op2)
          fail;
       insert (size(op1) == 1) ? i(text) : b(text);
    }
    
    case /^`<c:[^`]*>`/ => code(markupCode(c))
    
    case /^\$<var:[A-Za-z]*><ext:[_\^A-Za-z0-9]*>\$/ => code(i(var) + markupSubs(ext))              
    
    case /^\[<text:[^\]]*>\]\(<url:[^)]+>\)/ => link(url, text)
    
    case /^\[<concept:[A-Za-z0-9\/]+>\]/  => show(concept)
    
    case /^\\<char:.>/ :         //TODO nested matching is broken, since wrong last match is used!
      if(char == "\\") 	    insert	"\\";
      else if(char ==  "`") insert	"`";
      else if(char == "*")	insert "*";
      else if(char == "_")  insert "_";
      else if(char == "+")	insert "+";
      else if(char == ".")	insert ".";
      else insert char;
    
    case /^<span:\<[^\>]+\>>/ => span
    
    case /^<ent:&[A-Za-z0-9]+;>/ => ent
    
    case /^&/ => "&amp;"
    
    case /^\</ => "&lt;"
    
    case /^\/\*<dig:[0-9]>\*\//  => "\<img src=\"images/<dig>.png\"\>"
    
    case /^!\[<alt:[^\]]*>\]\(<file:[A-Za-z0-9\-\_]+\.png><opts:[^\)]*>\)/ => "\<img <getImgOpts(opts)> alt=\"<alt>\" src=\"<conceptPath>/<file>\"\>"
    
   };
}

test markupRestLine("\\\\") ==  "\\";
test markupRestLine("\\`") ==  "`";
test markupRestLine("\\*") ==  "*";
test markupRestLine("\\_") ==  "_";
test markupRestLine("\\+") ==  "+";
test markupRestLine("\\.") ==  ".";

test markupRestLine("*abc*") == "\<i\>abc\</i\>";
test markupRestLine("**abc**") == "\<b\>abc\</b\>";
test markupRestLine("_abc_") == "\<i\>abc\</i\>";
test markupRestLine("__abc__") == "\<b\>abc\</b\>";

test markupRestLine("`printf()`") == "\<code\>printf()\</code\>";
test markupRestLine("x\<sub\>1\</sub\>") ==  "x\<sub\>1\</sub\>";
test markupRestLine("x\<y") ==  "x\<sub\>1\</sub\>";

test markupRestLine("&copy;") == "&copy;";
test markupRestLine("C&A") == "C&A";

public str markupSubs(str txt){
  return visit(txt){
    case /^_<subsup:[A-Za-z0-9]+>/  => sub(subsup) 
    case /^\^<subsup:[A-Za-z0-9]+>/ => sup(subsup)   
  }
}

public str show(str cn){
  return "\<a href=\"/show?concept=<cn>\"\><cn>\</a\>";
}

public str link(str url, str text){
  return "\<a href=\"<url>\"\><(text=="")?url:text>\</a\>";
}

public str getImgOpts(str txt){
  opts = "";
  visit(txt){
    case /^\s*\|\s*left/: {opts += "align=\"left\" "; }
    case /^\s*\|\s*right/: {opts += "align=\"right\" "; }
    case /^\s*\|\s*<N:[0-9]+>\s*px/: {opts += "width=\"<N>px\" height=\"<N>px\" "; }
  }
  return opts;
}

public str markupListing(list[str] lines){
  txt = "";
  for(line <- lines)
    txt += markupCode(line) + "\n";
  return pre("listing", txt);
}

public str markupCode(str text){
  return visit(text){
    case /^\</   => "&lt;"
    case /^&/    => "&amp;"
    case /^\$\$/ => "$"
    case /^\$<var:[A-Za-z]*><ext:[_\^A-Za-z0-9]*>\$/ => i(var) + markupSubs(ext)
    case /^\/\*<dig:[0-9]>\*\// => "\<img src=\"images/<dig>.png\"\>"
  };
}

public str markupRascalPrompt(list[str] lines){
  return  "<for(str line <- lines){><visit(line){ case /^rascal\>/ => b("rascal\>") }>\n<}>";
}

public str markupScreen(list[str] lines){
   stripped_code = "<for(line <- lines){><(startsWith(line, "//")) ? "" : (line + "\n")><}>";
   result_lines = shell(stripped_code);
   
   int i = 0; int upbi = size(lines);
   int j = 0; int upbj = size(result_lines);
   pre_open = "\<pre class=\"screen\"\>";
   codeLines = pre_open;
   inPre = true;
   prompt =       "rascal\>";
   continuation = "\>\>\>\>\>\>\>";
   while(i < upbi && j < upbj){
   		 if(i < upbi && startsWith(lines[i], "//")){
           start = i;
           while(i < upbi && startsWith(lines[i], "//")){
               lines[i] = substring(lines[i], 2);
               i += 1;
           }
           codeLines += "\</pre\>\n<markup(slice(lines, start, i - start))>\n<pre_open>";
         }
         if(i <upbi) {
         	codeLines += b(prompt) + escapeForHtml(lines[i]) + "\n";
         	i += 1; j += 1;
         }
         while(j < upbj && !startsWith(result_lines[j], prompt)){
           codeLines += result_lines[j] + "\n";
           if(i < upbi && startsWith(result_lines[j], continuation)){
              i += 1;
             }
           j += 1;
         }
         
         if(i < upbi && startsWith(lines[i], "//")){
           start = i;
           while(i < upbi && startsWith(lines[i], "//")){
               lines[i] = substring(lines[i], 2);
               i += 1;
           }
           codeLines += "\</pre\>\n<markup(slice(lines, start, i - start))>\n<pre_open>";
         }

   }
   codeLines += "\</pre\>";
   return codeLines;
}

public set[str] searchTermsCode(str line){
  set[str] terms = {};
  visit(line){
    case /^\s+/: insert "";
    case /^\$[^\$]*\$/: insert "";
    case /^<kw:[a-zA-z]+>/: {terms += kw; insert ""; }
    case /^<op:[^a-zA-Z\$\ \t]+>/: { terms += op; insert ""; }
  }
  return terms;
}
public set[str]  searchTermsSynopsis(list[str] lines){
   set[str] terms = {};
   for(int k <- [0 .. size(lines) - 1])
       visit(lines[k]){
         case /Syntax:[^`]*`<syn:[^`]*>`$/: {terms += searchTermsCode(syn); insert ""; }
         
         case /Type:[^`]*`<tp:[^`]*>`$/: {terms += searchTermsCode(tp) - {"[", "]", ","}; insert "";}
         //TODO: more than one ` ` section
       };
    return terms;
}

test markup(["===Level 2==="]) == "\<h2\>Level 2\</h2\>\n";

test markup(["_abc_"]) == "\<i\>abc\</i\>";
test markup(["__abc__"]) == "\<b\>abc\</b\>";

test markup(["* abc"]) == "\<ul\>\n\<li\> abc\</li\>\n\</ul\>\n";
test markup(["* abc"]) == "\<ul\>\n\<li\> abc\</li\>\n\</ul\>\n";
test markup(["* abc", "X"]) == "\<ul\>\n\<li\> abc\</li\>\n\</ul\>\nX";
test markup(["* abc", "* def", "X"]) == "\<ul\>\n\<li\> abc\</li\>\n\<li\> def\</li\>\n\</ul\>\nX";
test markup(["* abc", "** def", "* ghi", "X"]) == "\<ul\>\n\<li\> abc\</li\>\n\<ul\>\n\<li\> def\</li\>\n\n\</ul\>\n\<li\> ghi\</li\>\n\</ul\>\nX";
test markup(["* abc", "## def", "* ghi", "X"]) == "\<ul\>\n\<li\> abc\</li\>\n\<ol\>\n\<li\> def\</li\>\n\n\</ol\>\n\<li\> ghi\</li\>\n\</ul\>\nX";
test markup(["* __abc__"]) == "\<ul\>\n\<li\> \<i\>abc\</i\>\</li\>\n\n\</ul\>\n";
test markup(["* abc", "* def", "_ghi__"]) == "\<ul\>\n\<li\> abc\</li\>\n\<li\> def\</li\>\n\</ul\>\n\<i\>ghi\</i\>";

