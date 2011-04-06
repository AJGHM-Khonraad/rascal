/*******************************************************************************
 * Copyright (c) 2009-2011 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:

 *   * Jurgen J. Vinju - Jurgen.Vinju@cwi.nl - CWI
 *   * Tijs van der Storm - Tijs.van.der.Storm@cwi.nl
 *   * Emilie Balland - emilie.balland@inria.fr (INRIA)
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Mark Hills - Mark.Hills@cwi.nl (CWI)
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.interpreter;

import static org.rascalmpl.interpreter.result.ResultFactory.makeResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.rascalmpl.ast.AbstractAST;
import org.rascalmpl.ast.Case;
import org.rascalmpl.ast.Expression;
import org.rascalmpl.ast.PatternWithAction;
import org.rascalmpl.ast.Replacement;
import org.rascalmpl.interpreter.asserts.ImplementationError;
import org.rascalmpl.interpreter.control_exceptions.Failure;
import org.rascalmpl.interpreter.control_exceptions.InterruptException;
import org.rascalmpl.interpreter.env.Environment;
import org.rascalmpl.interpreter.env.RewriteRule;
import org.rascalmpl.interpreter.matching.IBooleanResult;
import org.rascalmpl.interpreter.matching.LiteralPattern;
import org.rascalmpl.interpreter.matching.RegExpPatternValue;
import org.rascalmpl.interpreter.result.Result;
import org.rascalmpl.interpreter.staticErrors.SyntaxError;
import org.rascalmpl.interpreter.staticErrors.UnexpectedTypeError;
import org.rascalmpl.interpreter.types.NonTerminalType;
import org.rascalmpl.values.uptr.TreeAdapter;


// TODO: this class is still too tightly coupled with evaluator
public class TraversalEvaluator {
	public enum DIRECTION  {BottomUp, TopDown}	// Parameters for traversing trees
	public enum FIXEDPOINT {Yes, No}
	public enum PROGRESS   {Continuing, Breaking}
	
	private final Evaluator eval;
	private static final TypeFactory tf = TypeFactory.getInstance();

	public TraversalEvaluator(Evaluator eval) {
		this.eval = eval;
	}
	
	/*
	 * CaseOrRule is the union of a Case or a Rule and allows the sharing of
	 * traversal code for both.
	 */
	public class CasesOrRules {
		private java.util.List<Case> cases;
		private java.util.List<RewriteRule> rules;
		private boolean allConcretePatternCases = true;
		private boolean hasRegexp = false;

		@SuppressWarnings("unchecked")
		public CasesOrRules(java.util.List<?> casesOrRules){
			if(casesOrRules.get(0) instanceof Case){
				this.cases = (java.util.List<Case>) casesOrRules;
				for (Case c : cases){
					PatternWithAction pa = c.getPatternWithAction();
					Expression pattern = pa.getPattern();
					Type pt = pattern._getType();
					
					if (pt == null || !(pt instanceof NonTerminalType)){
						allConcretePatternCases = false;
					}
					
					if (pattern.isLiteral() && pattern.getLiteral().isRegExp()) {
						hasRegexp = true;
					}
				}
			} else {
				rules = (java.util.List<RewriteRule>) casesOrRules;
			}
		}

		public boolean hasRegexp() {
			return hasRegexp;
		}
		
		public boolean hasRules(){
			return rules != null;
		}

		public boolean hasCases(){
			return cases != null;
		}

		public int length(){
			return (cases != null) ? cases.size() : rules.size();
		}
		
		public boolean hasAllConcretePatternCases(){
			return allConcretePatternCases;
		}

		public java.util.List<Case> getCases(){
			return cases;
		}
		public java.util.List<RewriteRule> getRules(){
			return rules;
		}
	}

	public IValue traverse(IValue subject, CasesOrRules casesOrRules, DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint) {
		return traverseOnce(subject, casesOrRules, direction, progress, fixedpoint, new TraverseResult());
	}

	private IValue traverseOnce(IValue subject, CasesOrRules casesOrRules, DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint, TraverseResult tr){
		Type subjectType = subject.getType();
		IValue result = subject;

		if (/* casesOrRules.hasRegexp()  && */ subjectType.isStringType()) {
			return traverseStringOnce(subject, casesOrRules, tr);
		}

		if (direction == DIRECTION.TopDown){
			IValue newTop = traverseTop(subject, casesOrRules, tr);

			if ((progress == PROGRESS.Breaking) && tr.matched) {
				return newTop;
			}
			else if (fixedpoint == FIXEDPOINT.Yes && tr.changed) {
				do {
					tr.changed = false;
					newTop = traverseTop(newTop, casesOrRules, tr);
				} while (tr.changed);
				tr.changed = true;
				subject = newTop;
			}
			else {
				subject = newTop;
			}
		}

		if (subjectType.isAbstractDataType()){
			result = traverseADTOnce(subject, casesOrRules, direction, progress, fixedpoint, tr);
		} else if (subjectType.isNodeType()){
			result = traverseNodeOnce(subject, casesOrRules, direction, progress, fixedpoint, tr);
		} else if(subjectType.isListType()){
			result = traverseListOnce(subject, casesOrRules, direction, progress, fixedpoint, tr);
		} else if(subjectType.isSetType()){
			result = traverseSetOnce(subject, casesOrRules, direction, progress, fixedpoint, tr);
		} else if (subjectType.isMapType()) {
			result = traverseMapOnce(subject, casesOrRules, direction, progress, fixedpoint, tr);
		} else if(subjectType.isTupleType()){
			result = traverseTupleOnce(subject, casesOrRules, direction, progress, fixedpoint, tr);
		} else {
			result = subject;
		}

		if (direction == DIRECTION.BottomUp) {
			if ((progress == PROGRESS.Breaking) && tr.changed) {
				return result;
			}

			boolean hasMatched = tr.matched;
			boolean hasChanged = tr.changed;
			tr.matched = false;
			tr.changed = false;
			result = traverseTop(result, casesOrRules, tr);
			
			if (tr.changed && fixedpoint == FIXEDPOINT.Yes) {
				do {
					tr.changed = false;
					result = traverseTop(result, casesOrRules, tr);
				} while (tr.changed);
				tr.changed = true;
			}
			
			tr.changed |= hasChanged;
			tr.matched |= hasMatched;
		}
		
		return result;
	}

	private IValue traverseStringOnce(IValue subject,
			CasesOrRules casesOrRules, TraverseResult tr) {
		boolean hasMatched = tr.matched;
		boolean hasChanged = tr.changed;
		tr.matched = false;
		tr.changed = false;
		IValue res = traverseString(subject, casesOrRules, tr);
		tr.matched |= hasMatched;
		tr.changed |= hasChanged;
		return res;
	}

	private IValue traverseTupleOnce(IValue subject, CasesOrRules casesOrRules,
			DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint, TraverseResult tr) {
		IValue result;
		ITuple tuple = (ITuple) subject;
		int arity = tuple.arity();
		IValue args[] = new IValue[arity];
		boolean hasMatched = false;
		boolean hasChanged = false;
		
		
		for (int i = 0; i < arity; i++){
			tr.changed = false;
			tr.matched = false;
			args[i] = traverseOnce(tuple.get(i), casesOrRules, direction, progress, fixedpoint, tr);
			hasMatched |= tr.matched;
			hasChanged |= tr.changed;
		}
		
		result = eval.getValueFactory().tuple(args);
		tr.changed = hasChanged;
		tr.matched = hasMatched;
		return result;
	}

	private IValue traverseADTOnce(IValue subject, CasesOrRules casesOrRules,
			DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint, TraverseResult tr) {
		IConstructor cons = (IConstructor)subject;
		
		if (cons.arity() == 0) {
			return subject; // constants have no children to traverse into
		} 

		if (casesOrRules.hasAllConcretePatternCases() && TreeAdapter.isChar(cons)) {
				return subject; // we dont traverse into the structure of literals and characters
		}

		IValue args[] = new IValue[cons.arity()];

		if (casesOrRules.hasAllConcretePatternCases() && TreeAdapter.isAppl(cons)){
			// Constructor is "appl": we are dealing with a syntax tree
			// - Lexical or literal are returned immediately

			if (TreeAdapter.isLexical(cons)|| TreeAdapter.isLiteral(cons)){
				return subject; // we dont traverse into the structure of literals, lexicals, and characters
			}
			
			// Otherwise:
			// - Copy prod node verbatim to result
			// - Only visit non-layout nodes in argument list
			args[0] = cons.get(0);
			IList list = (IList) cons.get(1);
			int len = list.length();

			if (len > 0) {
				IListWriter w = list.getType().writer(eval.getValueFactory());
				boolean hasChanged = false;
				boolean hasMatched = false;

				for (int i = 0; i < len; i++){
					IValue elem = list.get(i);
					if (i % 2 == 0) { // Recursion to all non-layout elements
						tr.changed = false;
						tr.matched = false;
						w.append(traverseOnce(elem, casesOrRules, direction, progress, fixedpoint, tr));
						hasChanged |= tr.changed;
						hasMatched |= tr.matched;
					} else { // Just copy layout elements
						w.append(list.get(i));
					}
				}
				tr.changed = hasChanged;
				tr.matched = hasMatched;
				args[1] = w.done();
			} else {
				args[1] = list;
			}
		} else {
			// Constructor is not "appl", or at least one of the patterns is not a concrete pattern
			boolean hasChanged = false;
			boolean hasMatched = false;
			for (int i = 0; i < cons.arity(); i++){
				IValue child = cons.get(i);
				tr.matched = false;
				tr.changed = false;
				args[i] = traverseOnce(child, casesOrRules, direction, progress, fixedpoint, tr);
				hasChanged |= tr.changed;
				hasMatched |= tr.matched;
			}
			tr.matched = hasMatched;
			tr.changed = hasChanged;
		}

		if (tr.changed) {
			Type t = cons.getConstructorType();
			IConstructor rcons = eval.getValueFactory().constructor(t, args);

			if (cons.hasAnnotations()) {
				rcons = rcons.setAnnotations(cons.getAnnotations());
			}

			// check whether new rewrite rules now apply, but only if something indeed changed.
			return applyRules(rcons);
		}
		else {
			return subject;
		}
	}

	private IValue traverseMapOnce(IValue subject, CasesOrRules casesOrRules,
			DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint, TraverseResult tr) {
		IMap map = (IMap) subject;
		if(!map.isEmpty()){
			IMapWriter w = map.getType().writer(eval.getValueFactory());
			Iterator<Entry<IValue,IValue>> iter = map.entryIterator();
			boolean hasChanged = false;
			boolean hasMatched = false;
			
			while (iter.hasNext()) {
				Entry<IValue,IValue> entry = iter.next();
				tr.changed = false;
				tr.matched = false;
				IValue newKey = traverseOnce(entry.getKey(), casesOrRules, direction, progress, fixedpoint, tr);
				hasChanged |= tr.changed;
				hasMatched |= tr.matched;
				tr.changed = false;
				tr.matched = false;
				IValue newValue = traverseOnce(entry.getValue(), casesOrRules, direction, progress, fixedpoint, tr);
				hasChanged |= tr.changed;
				hasMatched |= tr.matched;
				w.put(newKey, newValue);
			}
			tr.changed = hasChanged;
			tr.matched = hasMatched;
			return w.done();
		} else {
			return subject;
		}
	}

	private IValue traverseSetOnce(IValue subject, CasesOrRules casesOrRules,
			DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint, TraverseResult tr) {
		ISet set = (ISet) subject;
		if(!set.isEmpty()){
			ISetWriter w = set.getType().writer(eval.getValueFactory());
			boolean hasChanged = false;
			boolean hasMatched = false;
			
			for (IValue v : set) {
				tr.changed = false;
				tr.matched = false;
				w.insert(traverseOnce(v, casesOrRules, direction, progress, fixedpoint, tr));
				hasChanged |= tr.changed;
				hasMatched |= tr.matched;
			}
			
			tr.changed = hasChanged;
			tr.matched = hasMatched;
			return w.done();
		} else {
			return subject;
		}
	}

	private IValue traverseListOnce(IValue subject, CasesOrRules casesOrRules,
			DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint, TraverseResult tr) {
		IList list = (IList) subject;
		int len = list.length();
		if (len > 0){
			IListWriter w = list.getType().writer(eval.getValueFactory());
			boolean hasChanged = false;
			boolean hasMatched = false;
			
			for (int i = 0; i < len; i++){
				IValue elem = list.get(i);
				tr.changed = false;
				tr.matched = false;
				w.append(traverseOnce(elem, casesOrRules, direction, progress, fixedpoint, tr));
				hasChanged |= tr.changed;
				hasMatched |= tr.matched;
			}
			
			tr.changed = hasChanged;
			tr.matched = hasMatched;
			return w.done();
		} else {
			return subject;
		}
	}

	private IValue traverseNodeOnce(IValue subject, CasesOrRules casesOrRules,
			DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint, TraverseResult tr) {
		IValue result;
		INode node = (INode)subject;
		if (node.arity() == 0){
			result = subject;
		} 
		else {
			IValue args[] = new IValue[node.arity()];
			boolean hasChanged = false;
			boolean hasMatched = false;
			
			for (int i = 0; i < node.arity(); i++){
				IValue child = node.get(i);
				tr.changed = false;
				tr.matched = false;
				args[i] = traverseOnce(child, casesOrRules, direction, progress, fixedpoint, tr);
				hasChanged |= tr.changed;
				hasMatched |= tr.matched;
			}
			
			tr.changed = hasChanged;
			tr.matched = hasMatched;
			
			INode n = eval.getValueFactory().node(node.getName(), args);
			
			if (node.hasAnnotations()) {
				n = n.setAnnotations(node.getAnnotations());
			}
			
			if (tr.changed) {
				result = applyRules(n);
			}
			else {
				result = n;
			}
		}
		return result;
	}

	/**
	 * Replace an old subject by a new one as result of an insert statement.
	 */
	public IValue applyCasesOrRules(IValue subject, CasesOrRules casesOrRules, TraverseResult tr) {
		if (casesOrRules.hasCases()) {
			return applyCases(subject, casesOrRules, tr);
		} 
		else if (casesOrRules.hasRules()) {
			return applyRules(subject, casesOrRules, tr);
		}
		else {
			return subject;
		}
	}

	private IValue applyRules(IValue subject, CasesOrRules casesOrRules, TraverseResult tr) {
		for (RewriteRule rule : casesOrRules.getRules()) {
			Environment oldEnv = eval.getCurrentEnvt();
			AbstractAST oldAST = eval.getCurrentAST();
			
			if (eval.isInterrupted()) {
				throw new InterruptException(eval.getStackTrace());
			}
			
			try {
				eval.setCurrentAST(rule.getRule());
				eval.setCurrentEnvt(rule.getEnvironment());
				eval.pushEnv();

				// this will throw fail or insert exceptions
				applyOneRule(subject, rule.getRule(), tr);
			}
			finally {
				eval.setCurrentAST(oldAST);
				eval.setCurrentEnvt(oldEnv);
			}
		}
		
		return subject;
	}

	private IValue applyCases(IValue subject, CasesOrRules casesOrRules, TraverseResult tr) {
		for (Case cs : casesOrRules.getCases()) {
			Environment old = eval.getCurrentEnvt();
			AbstractAST prevAst = eval.getCurrentAST();
			
			try {
				eval.pushEnv();
				eval.setCurrentAST(cs);
				
				if (cs.isDefault()) {
					cs.getStatement().interpret(eval);
					tr.matched = true;
					return subject;
				}

				IValue newSubject = applyOneRule(subject, cs.getPatternWithAction(), tr);

				if (tr.matched){
					return newSubject;
				}
			}
			finally {
				eval.unwind(old);
				eval.setCurrentAST(prevAst);
			}
		}
		
		return subject;
	}

	/*
	 * traverseTop: traverse the outermost symbol of the subject.
	 */
	public IValue traverseTop(IValue subject, CasesOrRules casesOrRules, TraverseResult tr) {
		try {
			return applyCasesOrRules(subject, casesOrRules, tr);	
		} 
		catch (org.rascalmpl.interpreter.control_exceptions.Insert e) {
			tr.changed = true;
			tr.matched = true;
			Result<IValue> toBeInserted = e.getValue();
			if (!toBeInserted.getType().equivalent(subject.getType())) {
				throw new UnexpectedTypeError(subject.getType(), toBeInserted.getType(), eval.getCurrentAST());
			}
			return e.getValue().getValue();
		}
	}
	
	/*
	 * traverseString implements a visit of a string subject by visiting subsequent substrings 
	 * subject[0,len], subject[1,len] ...and trying to match the cases. If a case matches
	 * the subject cursor is advanced by the length of the match and the matched substring may be replaced.
	 * At the end, the subject string including all replacements is returned.
	 * 
	 * Performance issue: we create a lot of garbage by producing all these substrings.
	 */
	public IValue traverseString(IValue subject, CasesOrRules casesOrRules, TraverseResult tr){
		String subjectString = ((IString) subject).getValue();
		int len = subjectString.length();
		int subjectCursor = 0;
		int subjectCursorForResult = 0;
		StringBuffer replacementString = null; 
		boolean hasMatched = false;
		boolean hasChanged = false;

		while (subjectCursor < len){
			//System.err.println("cursor = " + cursor);
		
			
			try {
				IString substring = eval.getValueFactory().string(subjectString.substring(subjectCursor, len));
				IValue subresult  = substring;
				tr.matched = false;
				tr.changed = false;
				
				// will throw insert or fail
				applyCasesOrRules(subresult, casesOrRules, tr);
				
				hasMatched |= tr.matched;
				hasChanged |= tr.changed;
				
				subjectCursor++;
			} catch (org.rascalmpl.interpreter.control_exceptions.Insert e) {
				IValue repl = e.getValue().getValue();
				hasChanged = true;
				hasMatched = true;
				
				if (repl.getType().isStringType()){
					int start;
					int end;
					IBooleanResult lastPattern = e.getMatchPattern();
					if (lastPattern == null) {
						throw new ImplementationError("No last pattern known");
					}
					if (lastPattern instanceof RegExpPatternValue){
						start = ((RegExpPatternValue)lastPattern).getStart();
						end = ((RegExpPatternValue)lastPattern).getEnd();
					} 
					else if (lastPattern instanceof LiteralPattern){
						start = 0;
						end = ((IString)repl).getValue().length();
					} 
					else {
						throw new SyntaxError("Illegal pattern " + lastPattern + " in string visit", eval.getCurrentAST().getLocation());
					}
					
					// Create replacementString when this is the first replacement
					if (replacementString == null) {
						replacementString = new StringBuffer();
					}
					
					// Copy string before the match to the replacement string
					for (; subjectCursorForResult < subjectCursor + start; subjectCursorForResult++){
						replacementString.append(subjectString.charAt(subjectCursorForResult));
					}
					subjectCursorForResult = subjectCursor + end;
					// Copy replacement into replacement string
					replacementString.append(((IString)repl).getValue());

					tr.matched = true;
					tr.changed = true;
					subjectCursor += end;
				} else {
					throw new UnexpectedTypeError(tf.stringType(),repl.getType(), eval.getCurrentAST());
				}
			}
		}
		
		tr.changed |= hasChanged;
		tr.matched |= hasMatched;

		if (!tr.changed) {
			return subject;
		}
		
		// Copy remaining characters of subject string into replacement string
		for (; subjectCursorForResult < len; subjectCursorForResult++){
			replacementString.append(subjectString.charAt(subjectCursorForResult));
		}
		
		return eval.getValueFactory().string(replacementString.toString());
	}

	private IValue applyOneRule(IValue subject, org.rascalmpl.ast.PatternWithAction rule, TraverseResult tr) {
		if (rule.isArbitrary()){
			// will throw an insert exception, or a fail
			eval.matchAndEval(makeResult(subject.getType(), subject, eval), rule.getPattern(), rule.getStatement());
			// or otherwise we'll just return the old value
			return subject;
		} 
		else if (rule.isReplacing()) {
			Replacement repl = rule.getReplacement();
			java.util.List<Expression> conditions = repl.isConditional() ? repl.getConditions() : new ArrayList<Expression>();
			
			// will throw an insert exception, or a fail
			eval.matchEvalAndReplace(makeResult(subject.getType(), subject, eval), rule.getPattern(), conditions, repl.getReplacementExpression());
			// or otherwise we'll just return the old value
			return subject;
		} 
		else {
			throw new ImplementationError("Impossible case in rule");
		}
	}
	
	public IValue applyRules(IValue value) {
		Type typeToSearchFor = value.getType();
		if (typeToSearchFor.isAbstractDataType()) {
			typeToSearchFor = ((IConstructor) value).getConstructorType();
		}

		java.util.List<RewriteRule> rules = eval.getHeap().getRules(typeToSearchFor);
		
		if (rules.size() > 0) {
			TraverseResult tr = new TraverseResult(); 
			return traverseTop(value, new CasesOrRules(rules), tr);
		}

		return value;
	}
}
