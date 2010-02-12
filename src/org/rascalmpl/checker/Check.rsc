module org::meta_environment::rascal::checker::Check

import IO;
import List;

import org::meta_environment::rascal::checker::Types;
import org::meta_environment::rascal::checker::SubTypes;

import languages::rascal::syntax::Rascal;

public Expression check(Expression exp) {
	return visit(exp) {
		case Expression e => e[@rtype = typecheck(e)]
	}
}

private bool debug = true;

private RType propagateFailOr(RType checkType, RType newType) {
	return isFailType(checkType) ? checkType : newType;
}

// Should we merge these somehow?
private RType propagateFailOr(RType checkType, RType checkType2, RType newType) {
	return isFailType(checkType) ? checkType : (isFailType(checkType2) ? checkType2 : newType);
}

private RType propagateFailOr(RType checkType, RType checkType2, RType checkType3, RType newType) {
	return isFailType(checkType) ? checkType : (isFailType(checkType2) ? checkType2 : (isFailType(checkType3) ? checkType3 : newType));
}

private RType checkNegativeExpression(Expression ep, Expression e) {
	if (isIntType(e@rtype)) {
		return makeIntType();
	} else if (isRealType(e@rtype)) {
		return makeRealType();
	} else {
		return propagateFailOr(e@rtype,makeFailType("Error in negation operation: <e> should have a numeric type " + 
			"but instead has type " + prettyPrint(e@rtype),ep@\loc));
	}
}

private RType checkNegationExpression(Expression ep, Expression e) {
	if (isBoolType(e@rtype)) {
		return makeBoolType();
	} else {
		return propagateFailOr(e@rtype,makeFailType("Error in negation operation: <e> should have type " + 
			prettyPrint(makeBoolType()) + " but instead has type " + prettyPrint(e@rtype),ep@\loc));
	}
}

public RType checkPlusExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeIntType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeRealType();
	} else if (isStrType(el@rtype) && isStrType(er@rtype)) {
		return makeStrType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype)) {
		return makeSetType(lub(getSetElementType(el@rtype),getSetElementType(er@rtype)));
	} else if (isListType(el@rtype) && isListType(er@rtype)) {
		return makeListType(lub(getListElementType(el@rtype),getListElementType(er@rtype)));
	} else {
		// TODO: Handle Map, Tuple cases
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in sum operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

//
// TODO:
// Question: why should - change the type of the result? Shouldn't set[a] - set[b] or
// list[a] - list[b] always result in a set or list of type a?
//
public RType checkMinusExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeIntType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeRealType();
	} else if (isStrType(el@rtype) && isStrType(er@rtype)) {
		return makeStrType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype)) {
		return makeSetType(lub(getSetElementType(el@rtype),getSetElementType(er@rtype)));
	} else if (isListType(el@rtype) && isListType(er@rtype)) {
		return makeListType(lub(getListElementType(el@rtype),getListElementType(er@rtype)));
	} else {
		// TODO: Handle Map case
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in difference operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkTimesExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeIntType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeRealType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype)) {
		return makeSetType(makeTupleType([getSetElementType(el@rtype),getSetElementType(er@rtype)]));
	} else if (isListType(el@rtype) && isListType(er@rtype)) {
		return makeListType(makeTupleType([getListElementType(el@rtype),getListElementType(er@rtype)]));
	} else {
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in product operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkDivExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeIntType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeRealType();
	} else {
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in division operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkModExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeIntType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeRealType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeRealType();
	} else {
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in mod operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkLessThanExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else if (isStrType(el@rtype) && isStrType(er@rtype)) {
		return makeBoolType();
	} else if (isListType(el@rtype) && isListType(er@rtype) && getListElementType(el@rtype) == getListElementType(er@rtype)) {
		return makeBoolType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype) && getSetElementType(el@rtype) == getSetElementType(er@rtype)) {
		return makeBoolType();
	} else {
		// TODO: Handle Location, Map, Node, Tuple, Value types
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in less than operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkLessThanOrEqualExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else if (isStrType(el@rtype) && isStrType(er@rtype)) {
		return makeBoolType();
	} else if (isListType(el@rtype) && isListType(er@rtype) && getListElementType(el@rtype) == getListElementType(er@rtype)) {
		return makeBoolType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype) && getSetElementType(el@rtype) == getSetElementType(er@rtype)) {
		return makeBoolType();
	} else {
		// TODO: Handle Location, Map, Node, Tuple, Value types
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in less than or equal to operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkGreaterThanExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else if (isStrType(el@rtype) && isStrType(er@rtype)) {
		return makeBoolType();
	} else if (isListType(el@rtype) && isListType(er@rtype) && getListElementType(el@rtype) == getListElementType(er@rtype)) {
		return makeBoolType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype) && getSetElementType(el@rtype) == getSetElementType(er@rtype)) {
		return makeBoolType();
	} else {
		// TODO: Handle Location, Map, Node, Tuple, Value types
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in greater than operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkGreaterThanOrEqualExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else if (isStrType(el@rtype) && isStrType(er@rtype)) {
		return makeBoolType();
	} else if (isListType(el@rtype) && isListType(er@rtype) && getListElementType(el@rtype) == getListElementType(er@rtype)) {
		return makeBoolType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype) && getSetElementType(el@rtype) == getSetElementType(er@rtype)) {
		return makeBoolType();
	} else {
		// TODO: Handle Location, Map, Node, Tuple, Value types
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in greater than or equal to operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkEqualsExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else if (isStrType(el@rtype) && isStrType(er@rtype)) {
		return makeBoolType();
	} else if (isListType(el@rtype) && isListType(er@rtype) && getListElementType(el@rtype) == getListElementType(er@rtype)) {
		return makeBoolType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype) && getSetElementType(el@rtype) == getSetElementType(er@rtype)) {
		return makeBoolType();
	} else {
		// TODO: Handle Location, Map, Node, Tuple, Value types
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in equals operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkNotEqualsExpression(Expression ep, Expression el, Expression er) {
	if (isIntType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isIntType(el@rtype) && isRealType(er@rtype)) {
		return makeBoolType();
	} else if (isRealType(el@rtype) && isIntType(er@rtype)) {
		return makeBoolType();
	} else if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else if (isStrType(el@rtype) && isStrType(er@rtype)) {
		return makeBoolType();
	} else if (isListType(el@rtype) && isListType(er@rtype) && getListElementType(el@rtype) == getListElementType(er@rtype)) {
		return makeBoolType();
	} else if (isSetType(el@rtype) && isSetType(er@rtype) && getSetElementType(el@rtype) == getSetElementType(er@rtype)) {
		return makeBoolType();
	} else {
		// TODO: Handle Location, Map, Node, Tuple, Value types
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in not equals operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkImplicationExpression(Expression ep, Expression el, Expression er) {
	if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else {
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in implication operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkEquivalenceExpression(Expression ep, Expression el, Expression er) {
	if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else {
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in equivalence operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkAndExpression(Expression ep, Expression el, Expression er) {
	if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else {
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in and operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkOrExpression(Expression ep, Expression el, Expression er) {
	if (isBoolType(el@rtype) && isBoolType(er@rtype)) {
		return makeBoolType();
	} else {
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in or operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkSetExpression(Expression ep, {Expression ","}* es) {
	// TODO: Properly account for subtypes and failures
	// TODO: Probably want to copy the rtype (see if we need to, maybe useful if we think we will assign annotations to the types)
	list[Expression] l = [ e | e <- es];
	return RTypeStructured(RStructuredType(RSetType(), [ RTypeArg( size(l) > 0 ? getOneFrom(l)@rtype : RTypeBasic(RVoidType) ) ]));
}

public RType checkListExpression(Expression ep, {Expression ","}* es) {
	// TODO: Properly account for subtypes and failures
	// TODO: Probably want to copy the rtype (see if we need to, maybe useful if we think we will assign annotations to the types)
	list[Expression] l = [ e | e <- es ];
	return RTypeStructured(RStructuredType(RListType(), [ RTypeArg( size(l) > 0 ? getOneFrom(l)@rtype : RTypeBasic(RVoidType) ) ]));
}

public RType checkTupleExpression(Expression ep, Expression ei, {Expression ","}* es) {
	// TODO: Properly account for subtypes and failures
	// TODO: Probably want to copy the rtype (see if we need to, maybe useful if we think we will assign annotations to the types)
	list[Expression] l = [ei];
	l += [ e | e <- es ];
	return RTypeStructured(RStructuredType(RTupleType(), [ RTypeArg(e@rtype) | e <- l]));
}

public RType checkRangeExpression(Expression ep, Expression e1, Expression e2) {
	if (isIntType(e1@rtype) && isIntType(e2@rtype)) {
		return RTypeStructured(RStructuredType(RListType(), [ RTypeArg(RTypeBasic(RIntType())) ]));
	} else {
		return propagateFailOr(e1@rtype,e2@rtype,makeFailType("Error in range operation: operation is not defined on the types " +
			prettyPrint(e1@rtype) + " and " + prettyPrint(e2@rtype),ep@\loc));
	}
}

public RType checkIsDefinedExpression(Expression ep, Expression e) {
	if (isFailType(e@rtype)) {
		return e@rtype;
	} else {
		return makeBoolType();
	}
}

public RType checkStepRangeExpression(Expression ep, Expression e1, Expression e2, Expression e3) {
	if (isIntType(e1@rtype) && isIntType(e2@rtype) && isIntType(e3@rtype)) {
		return RTypeStructured(RStructuredType(RListType(), [ RTypeArg(RTypeBasic(RIntType())) ]));
	} else {
		return propagateFailOr(e1@rtype,e2@rtype,e3@rtype,makeFailType("Error in step range operation: operation is not defined on the types " +
			prettyPrint(e1@rtype) + ", " + prettyPrint(e2@rtype) + " and " + prettyPrint(e3@rtype),ep@\loc));
	}
}

public RType checkInExpression(Expression ep, Expression el, Expression er) {
	if (isSetType(er@rtype) && getSetElementType(er@rtype) == el@rtype) {
		return makeBoolType();
	} else if (isListType(er@rtype) && getListElementType(er@rtype) == el@rtype) {
		return makeBoolType();
	} else {
		// TODO: Handle Map type, see what is needed for boolean operations
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in in operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType checkNotInExpression(Expression ep, Expression el, Expression er) {
	if (isSetType(er@rtype) && getSetElementType(er@rtype) == el@rtype) {
		return makeBoolType();
	} else if (isListType(er@rtype) && getListElementType(er@rtype) == el@rtype) {
		return makeBoolType();
	} else {
		// TODO: Handle Map type, see what is needed for boolean operations
		return propagateFailOr(el@rtype,er@rtype,makeFailType("Error in notin operation: operation is not defined on the types " +
			prettyPrint(el@rtype) + " and " + prettyPrint(er@rtype),ep@\loc));
	}
}

public RType typecheck(Expression exp) {
	switch(exp) {
		case (Expression)`<BooleanLiteral bl>` : {
			if (debug) println("BooleanLiteral: <bl>");
			if (debug) println("Assigning type: " + prettyPrint(RTypeBasic(RBoolType())));
			return makeBoolType();
		}

		case (Expression)`<DecimalIntegerLiteral il>`  : {
			if (debug) println("DecimalIntegerLiteral: <il>");
			if (debug) println("Assigning type: " + prettyPrint(RTypeBasic(RIntType())));
			return makeIntType();
		}

		case (Expression)`<OctalIntegerLiteral il>`  : {
			if (debug) println("OctalIntegerLiteral: <il>");
			if (debug) println("Assigning type: " + prettyPrint(RTypeBasic(RIntType())));
			return makeIntType();
		}

		case (Expression)`<HexIntegerLiteral il>`  : {
			if (debug) println("HexIntegerLiteral: <il>");
			if (debug) println("Assigning type: " + prettyPrint(RTypeBasic(RIntType())));
			return makeIntType();
		}

		case (Expression)`<RealLiteral rl>`  : {
			if (debug) println("RealLiteral: <rl>");
			if (debug) println("Assigning type: " + prettyPrint(RTypeBasic(RRealType())));
			return makeRealType();
		}

		case (Expression)`<StringLiteral sl>`  : {
			if (debug) println("StringLiteral: <sl>");
			if (debug) println("Assigning type: " + prettyPrint(RTypeBasic(RStrType())));
			return makeStrType();
		}

		case (Expression)`<LocationLiteral ll>`  : {
			if (debug) println("LocationLiteral: <ll>");
			if (debug) println("Assigning type: " + prettyPrint(RTypeBasic(RLocType())));
			return RTypeBasic(RLocType());
		}

		case (Expression)`<DateTimeLiteral dtl>`  : {
			if (debug) println("DateTimeLiteral: <dtl>");
			if (debug) println("Assigning type: " + prettyPrint(RTypeBasic(RDateTimeType())));
			return RTypeBasic(RDateTimeType());
		}

		// QualifiedName
//		case (Expression)`<QualifiedName qn>`: {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RIdentifierExp(convertName(qn));
//			return re[@at = exp@\loc] ;
//		}

		// ReifiedType
//		case `<BasicType t> ( <{Expression ","}* el> )` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RBasicType bt = convertBasicType(t);
//			RType rt = RTypeBasic(bt); rt = rt[@at = t@\loc];
//			RExpression re = RReifiedTypeExp(rt, mapper(getSDFExpListItems(el),convertExpression));
//			return re[@at = exp@\loc];			
//		}

		// CallOrTree
//		case `<Expression e1> ( <{Expression ","}* el> )` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression rct = RCallOrTreeExp(convertExpression(e1),mapper(getSDFExpListItems(el),convertExpression));
//			return rct[@at = exp@\loc];
//		}

		// List
		case `[<{Expression ","}* el>]` : {
			if (debug) println("List: <exp>");
			RType t = checkListExpression(exp,el);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Set
		case `{<{Expression ","}* el>}` : {
			if (debug) println("Set: <exp>");
			RType t = checkSetExpression(exp,el);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Tuple
		case `<<Expression ei>, <{Expression ","}* el>>` : {
			// TODO: This is not yet working
			if (debug) println("Tuple <exp>");
			RType t = checkTupleExpression(exp,ei,el);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// TODO: Map

		// TODO: Closure

		// TODO: VoidClosure

		// TODO: NonEmptyBlock

		// TODO: Visit
		
		// ParenExp
		case `(<Expression e>)` : {
			if (debug) println("ParenExp: <exp>");
			RType t = e@rtype;
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Range
		case `[ <Expression e1> .. <Expression e2> ]` : {
			if (debug) println("Range: <exp>");
			RType t = checkRangeExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// StepRange
		case `[ <Expression e1>, <Expression e2> .. <Expression e3> ]` : {
			if (debug) println("StepRange: <exp>");
			RType t = checkStepRangeExpression(exp,e1,e2,e3);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// ReifyType
		case (Expression)`#<Type t>` : {
			if (debug) println("ReifyType: <exp>");
			RType t = RTypeStructured(RStructuredType(RTypeType(),[RTypeArg(convertType(t))]));
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// FieldUpdate
//		case `<Expression e1> [<Name n> = <Expression e2>]` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RFieldUpdateExp(convertExpression(e1),convertName(n),convertExpression(e2));
//			return re[@at = exp@\loc] ;
//		}

		// FieldAccess
//		case `<Expression e1> . <Name n>` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RFieldAccessExp(convertExpression(e1),convertName(n));
//			return re[@at = exp@\loc] ;
//		}

		// TODO: Add code to deal with fields: FieldProject
//		case `<Expression e1> < <{Field ","}+ fl> >` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression rct = RFieldProjectExp(convertExpression(e1),mapper(getSDFExpListItems(el),convertExpression));
//			return rct[@at = exp@\loc];
//		}

		// TODO: Subscript (currently broken)
//		case `<Expression e1> [ <{Expression ","}+ el> ]` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression rct = RSubscriptExp(convertExpression(e1),mapper(getSDFExpListItems(el),convertExpression));
//			return rct[@at = exp@\loc];
//		}

		// IsDefined
		case `<Expression e> ?` : {
			if (debug) println("IsDefined: <exp>");
			RType t = checkIsDefinedExpression(exp,e);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Negation
		case `! <Expression e>` : {
			if (debug) println("Negation: <exp>");
			RType t = checkNegationExpression(exp,e);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Negative
		case `- <Expression e> ` : {
			if (debug) println("Negative: <exp>");
			RType t = checkNegativeExpression(exp,e);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// TransitiveClosure
//		case `<Expression e> + ` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RTransitiveClosureExp(convertExpression(e));
//			return re[@at = exp@\loc] ;
//		}

		// TransitiveReflexiveClosure
//		case `<Expression e> * ` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RTransitiveReflexiveClosureExp(convertExpression(e));
//			return re[@at = exp@\loc] ;
//		}

		// GetAnnotation
//		case `<Expression e> @ <Name n>` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RGetAnnotationExp(convertExpression(e),convertName(n));
//			return re[@at = exp@\loc] ;
//		}

		// SetAnnotation
//		case `<Expression e1> [@ <Name n> = <Expression e2>]` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RSetAnnotationExp(convertExpression(e1),convertName(n),convertExpression(e2));
//			return re[@at = exp@\loc] ;
//		}

		// Composition
//		case `<Expression e1> o <Expression e2>` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RCompositionExp(convertExpression(e1),convertExpression(e2));
//			return re[@at = exp@\loc] ;
//		}

		// Join
//		case `<Expression e1> join <Expression e2>` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RJoinExp(convertExpression(e1),convertExpression(e2));
//			return re[@at = exp@\loc] ;
//		}

		// Times
		case `<Expression e1> * <Expression e2>` : {
			if (debug) println("Times: <exp>");
			RType t = checkTimesExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Plus
		case `<Expression e1> + <Expression e2>` : {
			if (debug) println("Plus: <exp>");
			RType t = checkPlusExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Minus
		case `<Expression e1> - <Expression e2>` : {
			if (debug) println("Minus: <exp>");
			RType t = checkMinusExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Div
		case `<Expression e1> / <Expression e2>` : {
			if (debug) println("Div: <exp>");
			RType t = checkDivExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Mod
		case `<Expression e1> % <Expression e2>` : {
			if (debug) println("Mod: <exp>");
			RType t = checkModExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// In
		case `<Expression e1> in <Expression e2>` : {
			if (debug) println("In: <exp>");
			RType t = checkInExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// NotIn
		case `<Expression e1> notin <Expression e2>` : {
			if (debug) println("NotIn: <exp>");
			RType t = checkNotInExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// LessThan
		case `<Expression e1> < <Expression e2>` : {
			if (debug) println("LessThan: <exp>");
			RType t = checkLessThanExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// LessThanOrEq
		case `<Expression e1> <= <Expression e2>` : {
			if (debug) println("LessThanOrEq: <exp>");
			RType t = checkLessThanOrEqualExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// GreaterThanOrEq
		case `<Expression e1> >= <Expression e2>` : {
			if (debug) println("GreaterThanOrEq: <exp>");
			RType t = checkGreaterThanOrEqualExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// GreaterThan
		case `<Expression e1> > <Expression e2>` : {
			if (debug) println("GreaterThan: <exp>");
			RType t = checkGreaterThanExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Equals
		case `<Expression e1> == <Expression e2>` : {
			if (debug) println("Equals: <exp>");
			RType t = checkEqualsExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// NotEquals
		case `<Expression e1> != <Expression e2>` : {
			if (debug) println("NotEquals: <exp>");
			RType t = checkNotEqualsExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// IfDefinedOtherwise
//		case `<Expression e1> ? <Expression e2>` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RIfDefExp(convertExpression(e1),convertExpression(e2));
//			return re[@at = exp@\loc] ;
//		}

		// IfThenElse (Ternary)
//		case `<Expression e1> ? <Expression e2> : <Expression e3>` : {
//			if (debug) println("DateTimeLiteral: <dtl>");
//			RExpression re = RTernaryExp(convertExpression(e1),convertExpression(e2),convertExpression(e3));
//			return re[@at = exp@\loc] ;
//		}

		// Implication
		case `<Expression e1> ==> <Expression e2>` : {
			if (debug) println("Implication: <exp>");
			RType t = checkImplicationExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Equivalence
		case `<Expression e1> <==> <Expression e2>` : {
			if (debug) println("Equivalence: <exp>");
			RType t = checkEquivalenceExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// And
		case `<Expression e1> && <Expression e2>` : {
			if (debug) println("And: <exp>");
			RType t = checkAndExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

		// Or
		case `<Expression e1> || <Expression e2>` : {
			if (debug) println("Or: <exp>");
			RType t = checkOrExpression(exp,e1,e2);
			if (debug) println("Assigning type: " + prettyPrint(t));
			return t;
		}

	}
}

