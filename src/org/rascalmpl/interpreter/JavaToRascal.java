/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   * Bert  B. Lisser - Bert.Lisser@cwi.nl - CWI
 *******************************************************************************/
package org.rascalmpl.interpreter;

import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;

import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.env.GlobalEnvironment;
import org.rascalmpl.interpreter.env.ModuleEnvironment;
import org.rascalmpl.interpreter.result.AbstractFunction;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.values.ValueFactoryFactory;

public class JavaToRascal {

	private final static String SHELL_MODULE = "$shell$";
	private GlobalEnvironment heap = new GlobalEnvironment();

	final private Evaluator evaluator;

	protected final static IValueFactory vf = ValueFactoryFactory
			.getValueFactory();

	protected static final TypeFactory TF = TypeFactory.getInstance();

	public Evaluator getEvaluator() {
		return evaluator;
	}

	public JavaToRascal(PrintWriter stdout, PrintWriter stderr) {
		this.evaluator = new Evaluator(vf, stderr, stdout,
				new ModuleEnvironment(SHELL_MODULE, heap), heap);
	}

	public JavaToRascal(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	public Object call(String name, Object... args) {
		IValue[] vals = new IValue[args.length];
		for (int i = 0; i < args.length; i++)
			vals[i] = rascalObject(args[i]);
		return javaObject(evaluator.call(name, vals));
	}

//	public IValue call(String name, IValue... args) {
//		return evaluator.call(name, args);
//	}

	public void voidValue(String command, String location) {
		evaluator.eval(null, command, URI.create(location));
	}

	/**
	 * Evaluates Rascal Command without returning a result.
	 * 
	 * @param command
	 *            Rascal command.
	 * 
	 */
	public void voidValue(String command) {
		voidValue(command, "stdin:///");
	}

	public String stringValue(String command, String location) {
		Result<IValue> result = evaluator.eval(null, command,
				URI.create(location));
		return ((IString) (result.getValue())).getValue();
	}

	/**
	 * @param command
	 * @return result of Rascal Command evaluation which has type string
	 */
	public String stringValue(String command) {
		return stringValue(command, "stdin:///");
	}

	public int intValue(String command, String location) {
		Result<IValue> result = evaluator.eval(null, command,
				URI.create(location));
		return ((IInteger) (result.getValue())).intValue();
	}

	/**
	 * @param command
	 * @return result of Rascal Command evaluation which has type int
	 */
	public int intValue(String command) {
		return intValue(command, "stdin:///");
	}

	public boolean boolValue(String command, String location) {
		Result<IValue> result = evaluator.eval(null, command,
				URI.create(location));
		return ((IBool) (result.getValue())).getValue();
	}

	/**
	 * @param command
	 * @return result of Rascal Command evaluation which has type boolean
	 */
	public boolean boolValue(String command) {
		return boolValue(command, "stdin:///");
	}

	private Object javaObject(IValue v) {
		if (v.getType().isBoolType())
			return new Boolean(((IBool) v).getValue());
		if (v.getType().isIntegerType())
			return new Integer(((IInteger) v).intValue());
		if (v.getType().isStringType())
			return ((IString) v).getValue();
		if (v.getType().isListType())
			return _listValue((IList) v);
		return null;
	}

	private IValue rascalObject(Object v) {
		if (v == null)
			return null;
		if (v instanceof Integer)
			return vf.integer(((Integer) v).intValue());
		if (v instanceof String)
			return vf.string(((String) v));
		if (v instanceof Boolean)
			return vf.bool(((Boolean) v).booleanValue());
		return null;
	}

	private Object[] _listValue(IList q) {
		ArrayList<Object> r = new ArrayList<Object>();
		for (IValue v : q) {
			r.add(javaObject(v));
		}
		return r.toArray(new Object[] {});
	}

	public Object[] listValue(String command, String location) {
		Result<IValue> result = evaluator.eval(null, command,
				URI.create(location));
		return _listValue((IList) (result.getValue()));
	}

	/**
	 * @param command
	 * @return result of Rascal Command evaluation which has type Object[]
	 */
	public Object[] listValue(String command) {
		return listValue(command, "stdin:///");
	}

	public Object eval(String command, String location) {
		Result<IValue> result = evaluator.eval(null, command,
				URI.create(location));
		if (result.getType().isBoolType())
			return new Boolean(((IBool) (result.getValue())).getValue());
		if (result.getType().isIntegerType())
			return new Integer(((IInteger) (result.getValue())).intValue());
		if (result.getType().isStringType())
			return ((IString) (result.getValue())).getValue();
		if (result.getType().isVoidType())
			return null;
		if (result.getType().isListType()) {
			return _listValue((IList) (result.getValue()));
		}
		return result;
	}

	/**
	 * @param command
	 * @return result of Rascal Command evaluation which can have type: Integer,
	 *         Bool, String or Object[],
	 */
	public Object eval(String command) {
		return eval(command, "stdin:///");
	}

	public boolean isVoidInModule(String moduleName, String procedureName) {
		Environment old = evaluator.getCurrentEnvt();
		try {
			evaluator.doImport(null, moduleName);
			ArrayList<AbstractFunction> funcs = new ArrayList<AbstractFunction>();
			evaluator.getCurrentEnvt().getImport(moduleName)
					.getAllFunctions(TF.voidType(), procedureName, funcs);
			for (AbstractFunction f : funcs) {
				if (f.getArity() == 0) {
					return true;
				}
			}
			return false;
		} finally {
			evaluator.unwind(old);
		}
	}

	public boolean isStringInModule(String moduleName, String procedureName) {
		Environment old = evaluator.getCurrentEnvt();
		try {
			evaluator.doImport(null, moduleName);
			ArrayList<AbstractFunction> funcs = new ArrayList<AbstractFunction>();
			evaluator.getCurrentEnvt().getImport(moduleName)
					.getAllFunctions(TF.stringType(), procedureName, funcs);
			for (AbstractFunction f : funcs) {
				if (f.getArity() == 0) {
					return true;
				}
			}
			return false;
		} finally {
			evaluator.unwind(old);
		}
	}

	public static void main(String[] args) {
		final JavaToRascal jr = new JavaToRascal(new PrintWriter(System.out),
				new PrintWriter(System.err));
		jr.voidValue("import List;");
		System.out.println(jr.stringValue("\"<2+3>\";"));
		System.out.println(jr.stringValue("\"aap:<size([2,3])>\";"));
		final IInteger d1 = vf.integer(1), d2 = vf.integer(2);
		final IList l = vf.list(d1, d2);
		System.out.println(jr.call("size", l));
		// System.out.println(jr.call("+", d1, d2));
	}
}
