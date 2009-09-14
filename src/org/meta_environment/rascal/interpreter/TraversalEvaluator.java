package org.meta_environment.rascal.interpreter;

import static org.meta_environment.rascal.interpreter.result.ResultFactory.makeResult;

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
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.meta_environment.rascal.ast.Case;
import org.meta_environment.rascal.ast.Expression;
import org.meta_environment.rascal.ast.Replacement;
import org.meta_environment.rascal.interpreter.asserts.ImplementationError;
import org.meta_environment.rascal.interpreter.env.Environment;
import org.meta_environment.rascal.interpreter.env.RewriteRule;
import org.meta_environment.rascal.interpreter.matching.IBooleanResult;
import org.meta_environment.rascal.interpreter.matching.LiteralPattern;
import org.meta_environment.rascal.interpreter.matching.RegExpPatternValue;
import org.meta_environment.rascal.interpreter.result.Result;
import org.meta_environment.rascal.interpreter.result.ResultFactory;
import org.meta_environment.rascal.interpreter.staticErrors.SyntaxError;
import org.meta_environment.rascal.interpreter.staticErrors.UnexpectedTypeError;


// TODO: this class is still too tightly coupled with evaluator
public class TraversalEvaluator {
	public enum DIRECTION  {BottomUp, TopDown}	// Parameters for traversing trees
	public enum FIXEDPOINT {Yes, No}
	public enum PROGRESS   {Continuing, Breaking}
	
	private final Evaluator eval;
	private final IValueFactory vf;
	private final TypeFactory tf = TypeFactory.getInstance();

	public TraversalEvaluator(IValueFactory vf, Evaluator eval) {
		this.vf = vf;
		this.eval = eval;
	}
	
	/*
	 * TraverseResult contains the value returned by a traversal
	 * and a changed flag that indicates whether the value itself or
	 * any of its children has been changed during the traversal.
	 */

	// TODO: can this be put in the result hierarchy?
	public class TraverseResult {
		public boolean matched;   // Some rule matched;
		public Result<IValue> value; 		// Result<IValue> of the 
		public boolean changed;   // Original subject has been changed

		public TraverseResult(boolean someMatch, Result<IValue> value){
			this.matched = someMatch;
			this.value = value;
			this.changed = false;
		}

		public TraverseResult(Result<IValue> value){
			this.matched = false;
			this.value = value;
			this.changed = false;
		}

		public TraverseResult(Result<IValue> value, boolean changed){
			this.matched = true;
			this.value   = value;
			this.changed = changed;
		}
		public TraverseResult(boolean someMatch, Result<IValue> value, boolean changed){
			this.matched = someMatch;
			this.value   = value;
			this.changed = changed;
		}
	}

	/*
	 * CaseOrRule is the union of a Case or a Rule and allows the sharing of
	 * traversal code for both.
	 */
	public class CasesOrRules {
		private java.util.List<Case> cases;
		private java.util.List<RewriteRule> rules;

		@SuppressWarnings("unchecked")
		public CasesOrRules(java.util.List<?> casesOrRules){
			if(casesOrRules.get(0) instanceof Case){
				this.cases = (java.util.List<Case>) casesOrRules;
			} else {
				rules = (java.util.List<RewriteRule>)casesOrRules;
			}
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

		public java.util.List<Case> getCases(){
			return cases;
		}
		public java.util.List<RewriteRule> getRules(){
			return rules;
		}
	}

	public TraverseResult traverse(Result<IValue> subject, CasesOrRules casesOrRules,
			DIRECTION direction, PROGRESS progress, FIXEDPOINT fixedpoint) {
		//System.err.println("traverse: subject=" + subject + ", casesOrRules=" + casesOrRules);
		do {
			TraverseResult tr = traverseOnce(subject, casesOrRules, direction, progress);
			if(fixedpoint == FIXEDPOINT.Yes){
				if (!tr.changed) {
					return tr;
				}
				subject = tr.value;
			} else {
				return tr;
			}
		} while (true);
	}

	private TraverseResult traverseOnce(Result<IValue> subject, CasesOrRules casesOrRules, 
			DIRECTION direction, 
			PROGRESS progress){
		Type subjectType = subject.getType();
		boolean matched = false;
		boolean changed = false;
		Result<IValue> result = subject;

		//System.err.println("traverseOnce: " + subject + ", type=" + subject.getType() + ", direction=" + direction + ", progress=" + progress);
		if(subjectType.isStringType()){
			return traverseString(subject, casesOrRules);
		}

		if(direction == DIRECTION.TopDown){
			TraverseResult tr = traverseTop(subject, casesOrRules);
			matched |= tr.matched;
			changed |= tr.changed;
			if((progress == PROGRESS.Breaking) && changed){
				return tr;
			}
			subject = tr.value;
		}

		if(subjectType.isAbstractDataType()){
			IConstructor cons = (IConstructor)subject.getValue();
			if(cons.arity() == 0){
				result = subject;
			} else {
				IValue args[] = new IValue[cons.arity()];

				for(int i = 0; i < cons.arity(); i++){
					IValue child = cons.get(i);
//					Type childType = cons.getConstructorType().getFieldType(i);
					TraverseResult tr = traverseOnce(ResultFactory.makeResult(child.getType(), child, eval), casesOrRules, direction, progress);
					matched |= tr.matched;
					changed |= tr.changed;
					args[i] = tr.value.getValue();
				}
				IConstructor rcons = vf.constructor(cons.getConstructorType(), args);
				result = applyRules(makeResult(subjectType, rcons.setAnnotations(cons.getAnnotations()), eval));
			}
		} else
			if(subjectType.isNodeType()){
				INode node = (INode)subject.getValue();
				if(node.arity() == 0){
					result = subject;
				} else {
					IValue args[] = new IValue[node.arity()];

					for(int i = 0; i < node.arity(); i++){
						IValue child = node.get(i);
						TraverseResult tr = traverseOnce(ResultFactory.makeResult(child.getType(), child, eval), casesOrRules, direction, progress);
						matched |= tr.matched;
						changed |= tr.changed;
						args[i] = tr.value.getValue();
					}
					result = applyRules(makeResult(tf.nodeType(), vf.node(node.getName(), args).setAnnotations(node.getAnnotations()), eval));
				}
			} else
				if(subjectType.isListType()){
					IList list = (IList) subject.getValue();
					int len = list.length();
					if(len > 0){
						IListWriter w = list.getType().writer(vf);
						Type elemType = list.getType().getElementType();
						
						for(int i = 0; i < len; i++){
							IValue elem = list.get(i);
							TraverseResult tr = traverseOnce(ResultFactory.makeResult(elemType, elem, eval), casesOrRules, direction, progress);
							matched |= tr.matched;
							changed |= tr.changed;
							w.append(tr.value.getValue());
						}
						result = makeResult(subjectType, w.done(), eval);
					} else {
						result = subject;
					}
				} else 
					if(subjectType.isSetType()){
						ISet set = (ISet) subject.getValue();
						if(!set.isEmpty()){
							ISetWriter w = set.getType().writer(vf);
							Type elemType = set.getType().getElementType();
							
							for (IValue v : set){
								TraverseResult tr = traverseOnce(ResultFactory.makeResult(elemType, v, eval), casesOrRules, direction, progress);
								matched |= tr.matched;
								changed |= tr.changed;
								w.insert(tr.value.getValue());
							}
							result = makeResult(subjectType, w.done(), eval);
						} else {
							result = subject;
						}
					} else
						if (subjectType.isMapType()) {
							IMap map = (IMap) subject.getValue();
							if(!map.isEmpty()){
								IMapWriter w = map.getType().writer(vf);
								Iterator<Entry<IValue,IValue>> iter = map.entryIterator();
								Type keyType = map.getKeyType();
								Type valueType = map.getValueType();

								while (iter.hasNext()) {
									Entry<IValue,IValue> entry = iter.next();
									TraverseResult tr = traverseOnce(ResultFactory.makeResult(keyType, entry.getKey(), eval), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									IValue newKey = tr.value.getValue();
									tr = traverseOnce(ResultFactory.makeResult(valueType, entry.getValue(), eval), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									IValue newValue = tr.value.getValue();
									w.put(newKey, newValue);
								}
								result = makeResult(subjectType, w.done(), eval);
							} else {
								result = subject;
							}
						} else
							if(subjectType.isTupleType()){
								ITuple tuple = (ITuple) subject.getValue();
								int arity = tuple.arity();
								IValue args[] = new IValue[arity];
								for(int i = 0; i < arity; i++){
									Type fieldType = subjectType.getFieldType(i);
									TraverseResult tr = traverseOnce(ResultFactory.makeResult(fieldType, tuple.get(i), eval), casesOrRules, direction, progress);
									matched |= tr.matched;
									changed |= tr.changed;
									args[i] = tr.value.getValue();
								}
								result = makeResult(subjectType, vf.tuple(args), eval);
							} else {
								result = subject;
							}

		if(direction == DIRECTION.BottomUp){
			//System.err.println("traverseOnce: bottomup: changed=" + changed);
			if((progress == PROGRESS.Breaking) && changed){
				return new TraverseResult(matched, result, changed);
			}

			TraverseResult tr = traverseTop(result, casesOrRules);
			matched |= tr.matched;
			changed |= tr.changed;
			return new TraverseResult(matched, tr.value, changed);
		}
		return new TraverseResult(matched,result,changed);
	}

	/**
	 * Replace an old subject by a new one as result of an insert statement.
	 */
	private TraverseResult replacement(Result<IValue> oldSubject, Result<IValue> newSubject){
		if(newSubject.getType().equivalent((oldSubject.getType())))
			return new TraverseResult(true, newSubject, true);
		throw new UnexpectedTypeError(oldSubject.getType(), newSubject.getType(), eval.getCurrentAST());
	}

	/**
	 * Loop over all cases or rules.
	 */

	public TraverseResult applyCasesOrRules(Result<IValue> subject, CasesOrRules casesOrRules) {
		//System.err.println("applyCasesOrRules: " + subject.getValue());
		if(casesOrRules.hasCases()){
			for (Case cs : casesOrRules.getCases()) {
				Environment old = eval.getCurrentEnvt();
				
				try {
					eval.pushEnv();
					eval.setCurrentAST(cs);
					if (cs.isDefault()) {
						cs.getStatement().accept(eval);
						return new TraverseResult(true,subject);
					}

					TraverseResult tr = applyOneRule(subject, cs.getPatternWithAction());
					//System.err.println("applyCasesOrRules: matches");
					if(tr.matched){
						return tr;
					}
				}
				finally {
					eval.unwind(old);
				}
			}
		} else {
			//System.err.println("hasRules");
			for(RewriteRule rule : casesOrRules.getRules()){
				Environment oldEnv = eval.getCurrentEnvt();
				
				try {
					eval.setCurrentAST(rule.getRule());
					eval.setCurrentEnvt(rule.getEnvironment());
					eval.pushEnv();

					TraverseResult tr = applyOneRule(subject, rule.getRule());
					if(tr.matched){
						return tr;
					}
				}
				finally {
					eval.setCurrentEnvt(oldEnv);
				}
			}
		}
		//System.err.println("applyCasesorRules does not match");
		return new TraverseResult(subject);
	}

	/*
	 * traverseTop: traverse the outermost symbol of the subject.
	 */

	public TraverseResult traverseTop(Result<IValue> subject, CasesOrRules casesOrRules) {
		//System.err.println("traversTop(" + subject + ")");
		try {
			return applyCasesOrRules(subject, casesOrRules);	
		} catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e) {
			//System.err.println("traversTop(" + subject + "): replacement: " + e.getValue());
			return replacement(subject, e.getValue());
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
	public TraverseResult traverseString(Result<IValue> subject, CasesOrRules casesOrRules){
		String subjectString = ((IString) subject.getValue()).getValue();
		int len = subjectString.length();
		boolean matched = false;
		boolean changed = false;
		int subjectCursor = 0;
		int subjectCursorForResult = 0;
		StringBuffer replacementString = null; 

		while(subjectCursor < len){
			//System.err.println("cursor = " + cursor);
			try {
				IString substring = vf.string(subjectString.substring(subjectCursor, len));
				Result<IValue> subresult  = ResultFactory.makeResult(tf.stringType(), substring, eval);
				TraverseResult tr = applyCasesOrRules(subresult, casesOrRules);
				matched |= tr.matched;
				changed |= tr.changed;
				//System.err.println("matched=" + matched + ", changed=" + changed);
				subjectCursor++;
			} catch (org.meta_environment.rascal.interpreter.control_exceptions.Insert e){
				IValue repl = e.getValue().getValue();
				if(repl.getType().isStringType()){
					int start;
					int end;
					IBooleanResult lastPattern = e.getMatchPattern();
					if(lastPattern == null)
						throw new ImplementationError("No last pattern known");
					if(lastPattern instanceof RegExpPatternValue){
						start = ((RegExpPatternValue)lastPattern).getStart();
						end = ((RegExpPatternValue)lastPattern).getEnd();
					} else if(lastPattern instanceof LiteralPattern){
						start = 0;
						end = ((IString)repl).getValue().length();
					} else {
						throw new SyntaxError("Illegal pattern " + lastPattern + " in string visit", eval.getCurrentAST().getLocation());
					}
					
					// Create replacementString when this is the first replacement
					if(replacementString == null)
						replacementString = new StringBuffer();
					
					// Copy replacement into replacement string
					for(; subjectCursorForResult < subjectCursor + start; subjectCursorForResult++){
						replacementString.append(subjectString.charAt(subjectCursorForResult));
					}
					subjectCursorForResult = subjectCursor + end;
					replacementString.append(((IString)repl).getValue());

					matched = changed = true;
					subjectCursor += end;
				} else {
					throw new UnexpectedTypeError(tf.stringType(),repl.getType(), eval.getCurrentAST());
				}
			}
		}

		if(!changed){
			return new TraverseResult(matched, subject, changed);
		}
		
		// Copy remaining characters of subject string into replacement string
		for(; subjectCursorForResult < len; subjectCursorForResult++){
			replacementString.append(subjectString.charAt(subjectCursorForResult));
		}
		return new TraverseResult(matched, ResultFactory.makeResult(tf.stringType(), vf.string(replacementString.toString()), eval), changed);
	}

	/*
	 * applyOneRule: try to apply one rule to the subject.
	 */

	private TraverseResult applyOneRule(Result<IValue> subject,
			org.meta_environment.rascal.ast.PatternWithAction rule) {

		//System.err.println("applyOneRule: subject=" + subject + ", type=" + subject.getType() + ", rule=" + rule);

		if (rule.isArbitrary()){
			if(eval.matchAndEval(subject, rule.getPattern(), rule.getStatement())) {
				return new TraverseResult(true, subject);
			}
			/*
		} else if (rule.isGuarded()) {
			org.meta_environment.rascal.ast.Type tp = rule.getType();
			Type type = evalType(tp);
			rule = rule.getRule();
			if (subject.getType().isSubtypeOf(type) && 
				matchAndEval(subject, rule.getPattern(), rule.getStatement())) {
				return new TraverseResult(true, subject);
			}
			 */
		} else if (rule.isReplacing()) {
			//System.err.println("applyOneRule: subject=" + subject + ", replacing");
			Replacement repl = rule.getReplacement();
			java.util.List<Expression> conditions = repl.isConditional() ? repl.getConditions() : new ArrayList<Expression>();
			if(eval.matchEvalAndReplace(subject, rule.getPattern(), conditions, repl.getReplacementExpression())){
				//System.err.println("applyOneRule: matches");
				return new TraverseResult(true, subject);
			}
		} else {
			throw new ImplementationError("Impossible case in rule");
		}
		return new TraverseResult(subject);
	}
	
	public Result<IValue> applyRules(Result<IValue> v, IEvaluatorContext ctx) {
		//System.err.println("applyRules(" + v + ")");
		// we search using the run-time type of a value
		
		Type typeToSearchFor = v.getValue().getType();
		if (typeToSearchFor.isAbstractDataType()) {
			typeToSearchFor = ((IConstructor) v.getValue()).getConstructorType();
		}

		java.util.List<RewriteRule> rules = ctx.getHeap().getRules(typeToSearchFor);
		
		if (rules.size() > 0) {
			TraverseResult tr = traverseTop(v, new CasesOrRules(rules));
			return tr.value;
		}

		return v;
	}

	Result<IValue> applyRules(Result<IValue> v) {
		//System.err.println("applyRules(" + v + ")");
		// we search using the run-time type of a value
		Type typeToSearchFor = v.getValue().getType();
		if (typeToSearchFor.isAbstractDataType()) {
			typeToSearchFor = ((IConstructor) v.getValue()).getConstructorType();
		}

		java.util.List<RewriteRule> rules = eval.getHeap().getRules(typeToSearchFor);
		if(rules.isEmpty()){
			return v;
		}

		TraverseResult tr = traverseTop(v, new CasesOrRules(rules));
		/* innermost is achieved by repeated applications of applyRules
		 * when intermediate results are produced.
		 */

		return tr.value;
	}

	/*
	 *  singleCase returns a single case or rules if casesOrRueles has length 1 and null otherwise.
	 */

	private Object singleCase(CasesOrRules casesOrRules){
		if(casesOrRules.length() == 1){
			if(casesOrRules.hasCases()){
				return casesOrRules.getCases().get(0);
			}

			return casesOrRules.getRules().get(0);
		}
		return null;
	}


}


