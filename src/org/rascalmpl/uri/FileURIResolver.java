/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class FileURIResolver implements IURIInputOutputResolver {
	
	public FileURIResolver(){
		super();
	}
	
	public InputStream getInputStream(URI uri) throws IOException {
		String path = getPath(uri);
		if (path != null) {
			return new FileInputStream(path);
		}
		throw new IOException("uri has no path: " + uri);
	}
	
	public OutputStream getOutputStream(URI uri, boolean append) throws IOException {
		String path = getPath(uri);
		if (path != null) {
			return new BufferedOutputStream(new FileOutputStream(getPath(uri), append));
		}
		throw new IOException("uri has no path: " + uri);
	}
	
	public String scheme() {
		return "file";
	}

	public boolean exists(URI uri) {
		return new File(getPath(uri)).exists();
	}

	/**
	 * To override to build resolvers to specific locations using a prefix for example.
	 */
	protected String getPath(URI uri) {
		return uri.getPath();
	}

	public boolean isDirectory(URI uri) {
		return new File(getPath(uri)).isDirectory();
	}

	public boolean isFile(URI uri) {
		return new File(getPath(uri)).isFile();
	}

	public long lastModified(URI uri) {
		return new File(getPath(uri)).lastModified();
	}

	public String[] listEntries(URI uri) {
		return new File(getPath(uri)).list();
	}

	public boolean mkDirectory(URI uri) {
		return new File(getPath(uri)).mkdir();
	}

	public URI getResourceURI(URI uri) {
		return new File(getPath(uri)).toURI();
	}
}
