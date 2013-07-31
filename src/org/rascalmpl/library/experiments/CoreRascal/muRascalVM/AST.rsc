module experiments::CoreRascal::muRascalVM::AST

public data Instruction = instruction(str opcode, list[int] operands);

public str ICONST = "iconst";
public str RCONST = "rconst";

public str LOAD = "load";
public str STORE = "store";

public str LABEL = "label";

public str CALLPRIM = "call-prim";
public str CALL = "call";
public str RETURN = "return";
public str YIELD = "yield";

public str ALLOC = "alloc";
public str DEALLOC = "de-alloc";

public str JUMP = "jump";
public str JUMPCOND = "jump-cond";
