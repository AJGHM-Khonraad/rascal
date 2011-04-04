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
 *   * Paul Klint - Paul.Klint@cwi.nl - CWI
 *   * Arnold Lankamp - Arnold.Lankamp@cwi.nl
*******************************************************************************/
package org.rascalmpl.test;


import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rascalmpl.interpreter.staticErrors.RedeclaredVariableError;
import org.rascalmpl.interpreter.staticErrors.UndeclaredVariableError;

public class ScopeTests extends TestFramework {
	
	@Test(expected=UndeclaredVariableError.class)
	public void noEscapeFromToplevelMatch() {
		runTest("{ bool a := true; a;}");
	}
	
	@Test(expected=UndeclaredVariableError.class)
	public void noEscapeFromToplevelMatchStatement() {
		runTest("bool a := true;");
		runTestInSameEvaluator("a;");
	}
	
	@Test(expected=UndeclaredVariableError.class)
	public void noEscapeFromToplevelMatchExpression() {
		runTest("bool a := true"); // notice the necessary lack of a semi-colon here
		runTestInSameEvaluator("a;");
	}
	
	@Test(expected=RedeclaredVariableError.class)
	public void localRedeclarationError1(){
		runTest("{int n; int n;}");
	}
	
	@Test(expected=RedeclaredVariableError.class)
	public void localRedeclarationError2(){
		runTest("{int n = 1; int n;}");
	}
	
	@Test(expected=RedeclaredVariableError.class)
	public void localRedeclarationError3(){
		runTest("{int n = 1; int n = 2;}");
	}
	
	@Test
	public void localShadowing(){
		runTest("{int n = 2; int n := 3;}");
	}
	
	@Test
	public void localRedeclarationInt1(){
		assertTrue(runTest("{int n ; int n := 3 && n == 3;}"));
	}
	
	@Test
	public void localRedeclarationInt2(){
		assertTrue(runTest("{int n; [int n] := [3] && n == 3;}"));
	}
	
	@Test
	public void localShadowing2(){
		runTest("{int n; [list[int] n] := [1,2,3] && n == [1,2,3];}");
	}
	
	@Test
	public void localShadowingListMatch(){
		runTest("{list[int] n = [10,20]; [list[int] n] := [1,2,3] && n == [1,2,3];}");
	}
	
	@Test
	public void localRedeclarationList(){
		assertTrue(runTest("{list[int] n; [list[int] n] := [1,2,3] && n == [1,2,3];}"));
	}
	
	@Test
	public void localRedeclarationError9(){
		runTest("{int n; /<n:[0-9]*>/ := \"123\";}");
	}
	
	@Test
	public void localComprehensionShadowing(){
		runTest("{int n = 5; L = [n | int n <- [1 .. 10]];}");
	}
	
	@Test
	public void localRedeclarationError10(){
		assertTrue(runTest("{int n; L = [n | int n <- [1 .. 10]]; L == [1 .. 10];}"));
	}
	
	@Test(expected=RedeclaredVariableError.class)
	public void moduleRedeclarationError1(){
		prepareModule("XX", "module XX public int n = 1; public int n = 2;");
		runTestInSameEvaluator("import XX;");
		assertTrue(runTestInSameEvaluator("n == 1;"));
	}
	
	@Test
	public void moduleAndLocalVarDeclaration(){
		prepareModule("XX", "module XX public int n = 1;");
		prepareMore("import XX;");
		assertTrue(runTestInSameEvaluator("{int n = 2; n == 2;}"));
	}
	
	@Test(expected=UndeclaredVariableError.class)
	public void ifNoLeak1(){
		runTest("{if(int n := 3){n == 3;}else{n != 3;} n == 3;}");
	}
	
	@Test(expected=UndeclaredVariableError.class)
	public void ifNoLeak2(){
		runTest("{if(int n <- [1 .. 3], n>=3){n == 3;}else{n != 3;} n == 3;}");
	}
	
	@Test(expected=UndeclaredVariableError.class)
	public void blockNoLeak1(){
		runTest("{int n = 1; {int m = 2;}; n == 1 && m == 2;}");
	}
	
	@Test
	public void RedeclaredLocal(){
		assertTrue(runTest("{int n = 1; {int m = 2;}; int m = 3; n == 1 && m == 3;}"));
	}
	
	@Test(expected=UndeclaredVariableError.class)
	public void innerImplicitlyDeclared(){
		assertTrue(runTest("{int n = 1; {m = 2;}; n == 1 && m == 2;}"));
	}
	
	@Test(expected=UndeclaredVariableError.class)
	public void varsInEnumeratorExpressionsShouldNotLeak(){
		assertTrue(runTest("{int n <- [1,2]; n == 1;}"));
	}

}
