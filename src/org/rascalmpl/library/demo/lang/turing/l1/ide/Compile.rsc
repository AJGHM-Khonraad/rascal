module demo::lang::turing::l1::ide::Compile

import IO;
import List;
import demo::lang::turing::l1::ast::Turing;

public void compile(Program prog, loc target) {
	writeFile(target, compile(prog));
}

public str compile(Program prog) 
	= (compile(head(prog.statements)) | it + "\n" + compile(r) | r <- tail(prog.statements));
	
	
public str compile(jumpAlways(l)) = "J_<l>";
public str compile(jumpSet(l)) = "J1<l>";
public str compile(jumpUnset(l)) = "J0<l>";
public str compile(writeSet()) = "W1";
public str compile(writeUnset()) = "W0";
public str compile(moveForward()) = "MF";
public str compile(moveBackward()) = "MB";

public default str compile(Statement s) {
	throw "Statement <s> not supported";
}
