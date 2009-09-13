module experiments::GrammarTools::FirstFollow

import experiments::GrammarTools::Grammar;
import experiments::GrammarTools::Grammars; // for testing
import List;
import Set;
import IO;
import UnitTest;

// First and follow

public set[Symbol] firstNonEmpty(list[Symbol] symbols, map[Symbol, set[Symbol]] FIRST){
    set[Symbol] result = {};
	for(Symbol sym <- symbols){
	    if(isTermSymbol(sym))
	    	return result + {sym};
	    else {
	        f = FIRST[sym] ? {};
	 		if(epsilon() notin f)
			   return (result + f) - {epsilon()};
			else
			   result += f;
		}
	}
	return result;
}

// Compute the first sets for a grammar

public map[Symbol, set[Symbol]] first(Grammar G){
    map[Symbol, set[Symbol]] FIRST = ();
    set[Symbol] ntSymbols = {};   // NB: removing type leads to error in += below
    
    for(Symbol sym <- symbols(G))
        if(isTermSymbol(sym))
		    FIRST[sym] = {sym};
		else {
		    FIRST[sym] = {};
		    ntSymbols += {sym};
		}
			
	solve (FIRST) {
		for(Symbol sym <- ntSymbols){
			for(list[Symbol] symbols <- G.rules[sym]){
				if(isEmpty(symbols))
				   FIRST[sym] += {epsilon()};
				else
				   FIRST[sym] += firstNonEmpty(symbols, FIRST);
			}
		}
	}	
	return FIRST;
}

// Compute the first set of a list of symbols for given FIRST map

public set[Symbol] first(list[Symbol] symbols, map[Symbol, set[Symbol]] FIRST){
  set[Symbol] result = {};
  for(Symbol sym <- symbols){
      f = FIRST[sym];
      result += f - {epsilon()};
      if(epsilon() notin f)
         return result;
  }
  return result + {epsilon()};
}

// Compute the follow sets for a grammar

public map[Symbol, set[Symbol]] follow(Grammar G, map[Symbol, set[Symbol]] FIRST){
	map[Symbol, set[Symbol]] FOLLOW = (); 
	
	for(Symbol sym <- symbols(G))  /* all non-terminals have empoty follow set */
	    if(isNonTermSymbol(sym))
	       FOLLOW[sym] = {};
	       
	FOLLOW[G.start] = {t("$")};   /* start symbol has eof marker in follow set */     
	
	solve (FOLLOW) {
	    for(/<Symbol A, list[Symbol] symbols> <- G){  /* A ::= alpha B beta; */
	        while(!isEmpty(symbols)){ 
	            B = head(symbols);
	            beta = tail(symbols);
	           
		        if(isNonTermSymbol(B)){
		           firstBeta =  first(beta, FIRST);
		           FOLLOW[B] += firstBeta - {epsilon()};
			       if(isEmpty(beta) || epsilon() in firstBeta)
			           FOLLOW[B] += FOLLOW[A];
			    }
			    symbols = beta;
			}
		}
	}	
	return FOLLOW;
}

public bool test(){

    firstG2 = first(G2);
    
    assertEqual(firstG2,
                (nt("T1"):{epsilon(),t("*")},
                 t("*"):{t("*")},t("id"):{t("id")},t("+"):{t("+")},t("("):{t("(")},t(")"):{t(")")},
                 nt("E1"):{epsilon(),t("+")},
                 nt("E"):{t("id"),t("(")},
                 nt("T"):{t("id"),t("(")},
                 nt("F"):{t("id"),t("(")})
                );    
     
     assertEqual(first([nt("T1")], firstG2), {epsilon(),t("*")});
     
     assertEqual(first([nt("F"), nt("T1")], firstG2), {t("id"),t("(")});
 
     assertEqual(follow(G2, firstG2),
                 (nt("E"):  {t(")"), t("$")},
                  nt("E1"): {t(")"), t("$")},
                  nt("T"):  {t("+"), t(")"), t("$")},
                  nt("T1"): {t("+"), t(")"), t("$")},
                  nt("F"):  {t("+"), t("*"), t(")"), t("$")})
                );  
   
	return report("GrammarTools::FirstFollow");
}

