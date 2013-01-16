/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.rascalmpl.library.experiments.resource.results.buffers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;

import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.rascalmpl.interpreter.IEvaluatorContext;
import org.rascalmpl.unicode.UnicodeInputStreamReader;
import org.rascalmpl.values.ValueFactoryFactory;

public class LineStreamFiller implements ILazyFiller {

	private ISourceLocation source;
	private IEvaluatorContext ctx;
	private Reader is;
	private BufferedReader br;
	
	public LineStreamFiller(ISourceLocation source, IEvaluatorContext ctx) {
		this.source = source;
		this.ctx = ctx;
		this.is = null;
	}

	@Override
	public IValue[] refill(int pageSize) {
		try {
			if (is == null) {
				is = ctx.getResolverRegistry().getCharacterReader(source.getURI());
				br = new BufferedReader(is);
			}
			ArrayList<String> al = new ArrayList<String>();
			int readLines = 0;
			while (readLines < pageSize) {
				String line = br.readLine();
				if (line != null) {
					al.add(line);
					++readLines;
				} else {
					break;
				}
			}
			
			IValue res[] = new IValue[al.size()];
			for (int idx = 0; idx < al.size(); ++idx) res[idx] = ValueFactoryFactory.getValueFactory().string(al.get(idx));
			return res;
		} catch (IOException ioe) {
			
		}
		
		return new IValue[0];
	}

	@Override
	public ILazyFiller getBufferedFiller() {
		return new LineStreamFiller(source, ctx);
	}

}
