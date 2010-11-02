package org.rascalmpl.uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarURIResolver implements IURIInputStreamResolver{
	private final Class<?> clazz;
	
	public JarURIResolver(Class<?> clazz){
		super();
		
		this.clazz = clazz;
	}
	
	private String getJar(URI uri) {
		String path = uri.toASCIIString();
		return path.substring(path.indexOf("/"), path.indexOf('!'));
	}
	
	private String getPath(URI uri) {
		String path = uri.toASCIIString();
		return path.substring(path.indexOf('!') + 1);
	}
	
	public InputStream getInputStream(URI uri) throws IOException{
		InputStream resourceAsStream = clazz.getResourceAsStream(getPath(uri));
		if (resourceAsStream != null) {
			return resourceAsStream;
		}
		throw new FileNotFoundException(uri.toString());
	}
	
	public boolean exists(URI uri){
		try {
			String jar = getJar(uri);
			String path = getPath(uri);
			
			JarFile jarFile = new JarFile(jar);
			JarEntry jarEntry = jarFile.getJarEntry(path);
			return(jarEntry != null);
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean isDirectory(URI uri){
		try {
			String jar = getJar(uri);
			String path = getPath(uri);
			
			JarFile jarFile = new JarFile(jar);
			JarEntry jarEntry = jarFile.getJarEntry(path);
			return(jarEntry != null && jarEntry.isDirectory());
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean isFile(URI uri){
		try {
			String jar = getJar(uri);
			String path = getPath(uri);
			
			JarFile jarFile = new JarFile(jar);
			JarEntry jarEntry = jarFile.getJarEntry(path);
			return(jarEntry != null && !jarEntry.isDirectory());
		} catch (IOException e) {
			return false;
		}
	}
	
	public long lastModified(URI uri) throws IOException{
		String jar = getJar(uri);
		String path = getPath(uri);
		
		JarFile jarFile = new JarFile(jar);
		JarEntry jarEntry = jarFile.getJarEntry(path);
		if(jarEntry == null) return -1;
		
		return jarEntry.getTime();
	}
	
	public String[] listEntries(URI uri) throws IOException{
		String jar = getJar(uri);
		String path = getPath(uri);
		if(path.startsWith("/")) path = path.substring(1);
		
		JarFile jarFile = new JarFile(jar);
		
		Enumeration<JarEntry> entries =  jarFile.entries();
		ArrayList<String> matchedEntries = new ArrayList<String>();
		while(entries.hasMoreElements()){
			JarEntry je = entries.nextElement();
			String name = je.getName();
			if(name.startsWith(path)){
				String result = name.substring(path.length());
				if(result.endsWith("/")) result = result.substring(0, result.length() - 1);
				
				matchedEntries.add(result);
			}
		}
		
		String[] listedEntries = new String[matchedEntries.size()];
		return matchedEntries.toArray(listedEntries);
	}
	
	public String scheme(){
		return "jar";
	}
	
}
