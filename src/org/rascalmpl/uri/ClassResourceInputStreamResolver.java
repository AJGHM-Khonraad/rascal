package org.rascalmpl.uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class ClassResourceInputStreamResolver implements
		IURIInputStreamResolver {
	private final Class<?> clazz;
	private final String scheme;

	public ClassResourceInputStreamResolver(String scheme, Class<?> clazz) {
		this.clazz = clazz;
		this.scheme = scheme;
	}
	
	public boolean exists(URI uri) {
		return clazz.getResource(uri.getPath()) != null;
	}

	public InputStream getInputStream(URI uri) throws IOException {
		
		InputStream resourceAsStream = clazz.getResourceAsStream(uri.getPath());
		if(resourceAsStream != null)
			return resourceAsStream;
		throw new FileNotFoundException(uri.toString());
	}

	public String scheme() {
		return scheme;
	}

}
