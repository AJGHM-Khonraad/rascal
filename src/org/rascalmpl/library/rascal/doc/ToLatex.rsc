@cachedParser{org.rascalmpl.library.rascal.doc.ToLatex}
module rascal::doc::ToLatex

import rascal::doc::Document;
import rascal::doc::LatexIsland;
import rascal::syntax::Generator;
import ParseTree;
import String;
import lang::box::util::Highlight;
import lang::box::util::HighlightToLatex;
import Reflective;
import IO;

public map[str,str] mathLiterals = (
		"o": 		"\\circ",
		"\>": 		"\>",
		"\<": 		"\<",
		"\>=": 		"\\geq",
		"\<=": 		"\\leq",
		"\<-": 		"\\leftarrow",
		"in": 		"\\in",
		"*": 		"\\times",
		"&": 		"\\cap",
		"&&": 		"\\wedge",
		"||": 		"\\vee",
		"!": 		"\\neg",
		"any": 		"\\exists",
		"all": 		"\\forall",
		"==": 		"\\equiv",
		"!=": 		"\\neq",
		"==\>": 	"\\Rightarrow",
		"\<=\>": 	"\\Leftrightarrow",
		"=\>": 		"\\mapsto",
		":=": 		"\\cong",
		"!:=": 		"\\not\\cong"

);

public void generateParser() {
	int uniqueItem = -3; // -1 and -2 are reserved by the SGTDBF implementation
 	int newItem() { uniqueItem -= 1; return uniqueItem; };
  	gr = getModuleGrammar(|rascal://rascal::doc::ToLatex|);
  	parser = generate("org.rascalmpl.library.rascal.doc", "ToLatex", "org.rascalmpl.parser.gtd.SGTDBF", newItem, false, true, {}, gr);
  	writeFile(|boot:///src/org/rascalmpl/library/rascal/doc/ToLatex.java|, parser);
}

public str rascalDoc2Latex(str s, loc l) {
	return expand(parse(#Document, s), l, formatBlock, formatInline);
}

public str rascalDoc2Latex(loc l) {
	return expand(parse(#Document, l), l, formatBlock, formatInline);
}

private str formatBlock(Tree t, loc l) {
	snip = unquote("<t>", "\\begin{rascal}", "\\end{rascal}"); 
	return "\\begin{rascaldoc}<rascalToLatex(snip, l)>\\end{rascaldoc}";
}		

private str formatInline(Tree t, loc l) {
	snip = unquote("<t>", "\\irascal{", "}");
	return "\\irascaldoc{<rascalToLatex(snip, l)>}";
}

private str rascalToLatex(str snip, loc l) {
	pt = annotateSpecials(parseCommand(snip, l));
	pt = annotateMathOps(pt, mathLiterals);
	return highlight2latex(highlight(pt));
}

private Tree annotateSpecials(Tree pt) {
	return visit (pt) {
		
		// tuples
		case appl(p:prod([lit("\<"), _*, lit("\>")], _, _), [lt, a*, gt]) =>
			appl(p, [lt[@math="\\langle"], a, gt[@math="\\rangle"]])

		// multi variables			
		case appl(p:prod([_*, lit("*")], _, _), [a*, star]) =>
			appl(p, [a, star[@math="^{*}"]])  
	}
}

private str unquote(str src, str bq, str eq) {
	return substring(src, size(bq), size(src) - size(eq));
}



