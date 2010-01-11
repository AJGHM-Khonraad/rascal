module ValueIO

/* 
 * Library functions for reading and writing textual or binary values:
 * - readValueFile
 * - readBinaryValueFile
 * - readTextValueFile
 * - writeBinaryValueFile
 * - writeTextValueFile
 */

@doc{Read  a value from a binary file in PBF format}
public value readValueFile(loc file) {
  return readValueFile(#value, file);
}



@doc{Read a typed value from a binary file.}
@javaClass{org.meta_environment.rascal.library.ValueIO}
public &T java readBinaryValueFile(type[&T] result, loc file);

public value readBinaryValueFile(loc file) {
  return readBinaryValueFile(#value, file);
}

@doc{Read a typed value from a text file.}
@javaClass{org.meta_environment.rascal.library.ValueIO}
public &T java readTextValueFile(type[&T] result, loc file);

public value readTextValueFile(loc file) {
  return readTextValueFile(#value, file);
}

@doc{Parse a textual string representation of a value}
public value readTextValueString(str input) {
  return readTextValueString(#value, input);
}

@doc{Parse a textual string representation of a value and validate it against the given type}
@javaClass{org.meta_environment.rascal.library.ValueIO}
public &T java readTextValueString(type[&T] result, str input);
	
@doc{Write a value to a file using an efficient binary file format}
@javaClass{org.meta_environment.rascal.library.ValueIO}
public void java writeBinaryValueFile(loc file, value val);
	
@doc{Write a value to a file using a textual file format}
@javaClass{org.meta_environment.rascal.library.ValueIO}
public void java writeTextValueFile(loc file, value val);
	
