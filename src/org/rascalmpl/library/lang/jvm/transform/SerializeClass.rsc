module lang::jvm::transform::SerializeClass

import lang::jvm::ast::Level0;

@javaClass{org.rascalmpl.library.lang.jvm.SerializeClass}
public void java serialize(Class class, loc path)
throws PathNotFound(loc), IOError(str msg), JavaBytecodeError(str msg);

@javaClass{org.rascalmpl.library.lang.jvm.Rascalify}
public void java deserializeToDisk(loc source, loc destination, str moduleName)
throws PathNotFound(loc), IOError(str msg), JavaBytecodeError(str msg);
