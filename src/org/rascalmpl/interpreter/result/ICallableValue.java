/*******************************************************************************
 * Copyright (c) 2009-2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
*******************************************************************************/
package org.rascalmpl.interpreter.result;

import java.util.List;
import java.util.Map;

import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.rascalmpl.interpreter.IEvaluator;
import org.rascalmpl.interpreter.IRascalMonitor;

public interface ICallableValue extends IValue {
	public int getArity();
	public boolean hasVarArgs();
	public boolean hasKeywordArgs();
	public Result<IValue> call(IRascalMonitor monitor, Type[] argTypes, IValue[] argValues, Map<String, Result<IValue>> keyArgValues);
	public Result<IValue> call(Type[] argTypes, IValue[] argValues, Map<String, Result<IValue>> keyArgValues);
	public Result<IValue> call(IRascalMonitor monitor, Type[] argTypes, IValue[] argValues, Map<String, Result<IValue>> keyArgValues, Result<IValue> self, List<String> selfParams, List<Result<IValue>> selfParamBounds);
	public Result<IValue> call(Type[] argTypes, IValue[] argValues, Map<String, Result<IValue>> keyArgValues, Result<IValue> self, List<String> selfParams, List<Result<IValue>> selfParamBounds);
	public boolean isStatic();
	
	public IEvaluator<Result<IValue>> getEval();
}
