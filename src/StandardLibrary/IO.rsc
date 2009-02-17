module IO

public void java println(value V...)
@javaImports{import java.io.File;}
{
   IList argList = (IList) V;
   for(int i = 0; i < argList.length(); i++){
   	  IValue arg = argList.get(i);
   	  if(arg.getType().isStringType()){
   	  	System.out.print(((IString) arg).getValue().toString());
   	  } else {
   		System.out.print(arg.toString());
   	  }
   }
   System.out.println();
   return;
}

public list[str] java readFile(str filename)
throws NoSuchFileError(str msg), IOError(str msg)
@javaImports
{
	import java.io.File; 
	import java.io.FileReader;
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.FileNotFoundException;
	import org.meta_environment.rascal.interpreter.errors.Error;
}
{
  IList res = null;
  try {
  	BufferedReader in = new BufferedReader(new FileReader(filename.getValue()));
  	String line;
  	
  	IListWriter w = types.listType(types.stringType()).writer(values);
  	do {
  		line = in.readLine();
  		if(line != null){
  			w.append(values.string(line));
  		}
  	} while (line != null);
  	in.close();
  	res =  w.done();
  }
    catch (FileNotFoundException e){
  	throw new NoSuchFileError(filename.getValue(), null);
  }
  catch (IOException e){
    throw new IOError(e.getMessage(), null);
  }

  return res;
}


