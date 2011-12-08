@license{
  Copyright (c) 2009-2011 CWI
  All rights reserved. This program and the accompanying materials
  are made available under the terms of the Eclipse Public License v1.0
  which accompanies this distribution, and is available at
  http://www.eclipse.org/legal/epl-v10.html
}
// High level linear programming interface
module LinearProgramming

import LLLinearProgramming;
import List;
import Maybe;
import Set;
import Number;
import Map;
import Integer;
import Real;

alias Coefficients = map[str var,num coef];

data ObjectiveFun = objFun(Coefficients coefficients, num const);

public ObjectiveFun objFun(Coefficients coefficients) =
	objFun(coefficients,0);

data ConstraintType = leq() | eq() | geq();

data Constraint = 
	constraint(	Coefficients coefficients,
			   	ConstraintType ctype, num const);
				
alias Constraints = set[Constraint];
alias VariableVals = map[str var, num val];

data Solution = solution(VariableVals varVals, num funVal);

num runObjFul(ObjectiveFun f, VariableVals vals) =
	(f.const | 
	 it + f.coefficients[var]*varVals[var] |
	 var <- domain(f.coefficients));


public Maybe[Solution] 
minimizeNonNegative(Constraints constraints, ObjectiveFun f) =
	optimize(true,true, constraints, f);

public Maybe[Solution] 
minimize(Constraints constraints, ObjectiveFun f) =
	optimize(true,false, constraints, f);


public Maybe[Solution] 
maximizeNonNegative(Constraints constraints, ObjectiveFun f) =
	optimize(false,true, constraints, f);

public Maybe[Solution] 
maximize(set[Constraint] constraints, ObjectiveFun f) =
	optimize(false,false, constraints, f);


public Maybe[Solution] 
optimize(bool minimize, bool nonZero, 
		 Constraints constraints, ObjectiveFun f) {
	indexVar = getIndexVar(constraints,f);
	llConstraints = toLLConstraints(constraints,indexVar);
	llf = toLLObjectiveFun(f, indexVar);
	llSol = llOptimize(minimize, nonZero, llConstraints, llf);
	switch(llSol) {
		case nothing()   : return nothing();
		case just(llsol) : return just(fromLLSolution(llsol,indexVar));
	}
}

num zero = 0;

list[num] toLLCoefficients(Coefficients coefficients, list[str] indexVar) =
	[coefficients[i] ? zero | i <- indexVar];

Coefficients normalize(Coefficients coefs) =
	( () | it + ((c != 0) ? (var : c) : ()) | <var,c> <- toList(coefs));

Coefficients 
fromLLVariableVals(LLVariableVals vars, list[str] indexVar) =
	( indexVar[i] : vars[i] | i <- index(indexVar));


LLObjectiveFun toLLObjectiveFun(ObjectiveFun f, list[str] indexVar) =
	llObjFun(toLLCoefficients(f.coefficients,indexVar),f.const);

LLConstraint toLLConstraint(Constraint c, list[str] indexVar) =
	llConstraint(toLLCoefficients(c.coefficients,indexVar),c.ctype, c.const);

LLConstraints toLLConstraints(Constraints cs, list[str] indexVar) =
	{ toLLConstraint(c,indexVar) | c <- cs }; 
	
list[str] getIndexVar(Constraints cons,ObjectiveFun f) =
	toList({domain(con.coefficients) | con <- cons} 
		   + domain(f.coefficients));


Solution fromLLSolution(LLSolution l, list[str] indexVar) =
	solution( normalize(( indexVar[i] : l.varVals[i] | i <- index(l.varVals))),
			 l.funVal);
	