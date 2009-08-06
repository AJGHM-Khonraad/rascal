package org.meta_environment.rascal.interpreter.load;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.io.PBFWriter;

public class FromDirectoryLoader implements IModuleFileLoader{
	private final String directory;
	
	public FromDirectoryLoader(String directory){
		super();
		
		if(!new File(directory).isDirectory()) throw new IllegalArgumentException("Directory: "+directory+" does not exist.");
		
		this.directory = directory;
	}

	public boolean fileExists(String filename){
		try{
			File f = new File(directory+File.separator+filename);
			return f.exists();
		}catch(RuntimeException ex){
			return false;
		}
	}
	
	public InputStream getInputStream(String filename){
		try{
			File f = new File(directory+File.separator+filename);
			if(f.exists()){
				return new FileInputStream(f);
			}
		}catch(IOException ioex){
			// Ignore, this is fine.
		}
		return null;
	}
	
	public boolean supportsLoadingBinaries(){
		return true;
	}
	
	public boolean tryWriteBinary(String filename, String binaryName, IConstructor tree){
		File binFile = new File(directory+File.separator+binaryName);
		
		BufferedOutputStream outputStream = null;
		
		PBFWriter pbfWriter = new PBFWriter();
		try{
			outputStream = new BufferedOutputStream(new FileOutputStream(binFile));
			pbfWriter.write(tree, outputStream);
			return true;
		}catch(IOException ioex){
			ioex.printStackTrace();
		}finally{
			if(outputStream != null){
				try{
					outputStream.flush();
				}catch(IOException ioex){
					ioex.printStackTrace();
				}
				
				try{
					outputStream.close();
				}catch(IOException ioex){
					ioex.printStackTrace();
				}
			}
		}
		return false;
	}
}
