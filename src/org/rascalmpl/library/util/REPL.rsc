module util::REPL

import Message;
import ParseTree;
import util::Proposer;

alias Completion
 = tuple[int offset, list[str] suggestions];

alias CommandResult
  = tuple[str result, list[Message] messages, str prompt] 
  ;
  
alias CompletionFunction = Completion (str prefix, int requestOffset)
  ;
  

data REPL
  = repl(str title, str welcome, str prompt, loc history, 
         CommandResult (str line) handler,
         Completion(str line, int cursor) completor);

@javaClass{org.rascalmpl.library.util.TermREPL}
@reflect
java void startREPL(REPL repl);

public REPL repl(str title, str welcome, str prompt, loc history, CommandResult (str line) handler, type[&N <: Tree] g){
	return repl(title, welcome, prompt, history, handler, completion(g));
}

public CompletionFunction completion(type[&N <: Tree] g){
	return Completion (str prefix, int offset) {
    	proposerFunction = proposer(g);
   		return <0, ["<prop.newText>"|prop <- proposerFunction(prefix, offset)]>;
  	};
}