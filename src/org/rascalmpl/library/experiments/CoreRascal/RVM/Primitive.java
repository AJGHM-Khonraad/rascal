package org.rascalmpl.library.experiments.CoreRascal.RVM;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListRelation;
import org.eclipse.imp.pdb.facts.IRelationalAlgebra;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.INumber;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;

/*
 * The primitives that can be called from the RVM interpreter loop.
 * Each primitive with name P (e.g. addition_int_int) is defined by:
 * - a constant P below
 * - a static method int P(Object[] stack, int sp)
 * 
 * Each primitive implementation gets the current stack and stack pointer as argument
 * and returns a new stack pointer. It may make mdifications to the stack.
 */

public enum Primitive {
	and_bool_bool,
	appendAfter,
	addition_elm_list,
	addition_list_elm,
	addition_list_list,
	addition_map_map,
	addition_num_num,
	addition_elm_set,
	addition_set_elm,
	addition_set_set,
	addition_str_str,
	addition_tuple_tuple,
	composition_lrel_lrel,
	composition_rel_rel,
	composition_map_map,
	division_num_num,
	equals_num_num,
	equivalent_bool_bool,
	greater_num_num,
	greater_equal_num_num,
	implies_bool_bool,
	less_num_num,
	less_equal_num_num,
	make_list,
	make_map,
	make_set,
	make_tuple,
	negative,
	not_bool,
	or_bool_bool,
	println,
	product_num_num,
	subtraction_list_list,
	subtraction_map_map,
	subtraction_num_num,
	subtraction_set_set,
	subscript_list_int, 
	subscript_map,
	transitive_closure_lrel,
	transitive_closure_rel,
	transitive_reflexive_closure_lrel,
	transitive_reflexive_closure_rel;
	
	private static Primitive[] values = Primitive.values();

	public static Primitive fromInteger(int prim){
		return values[prim];
	}
	
	private static IValueFactory vf;
	private static IBool TRUE;
	private static IBool FALSE;
	static Method [] methods;

	/**
	 * Initialize the primitive methods.
	 * @param fact value factory to be used
	 */
	public static void init(IValueFactory fact) {
		vf = fact;
		TRUE = vf.bool(true);
		FALSE = vf.bool(false);
		Method [] methods1 = Primitive.class.getDeclaredMethods();
		HashSet<String> implemented = new HashSet<String>();
		methods = new Method[methods1.length];
		for(int i = 0; i < methods1.length; i++){
			Method m = methods1[i];
			String name = m.getName();
			switch(name){
			case "init":
			case "invoke":
			case "fromInteger":
			case "values":
			case "valueOf":
				/* ignore all utility functions that do not implement some primitive */
				break;
			default:
				implemented.add(name);
				methods[valueOf(name).ordinal()] = m;
			}
		}
		for(int i = 0; i < values.length; i++){
			if(!implemented.contains(values[i].toString())){
				throw new RuntimeException("PANIC: unimplemented primitive " + values[i] + " [add implementation to Primitives]");
			}
		}
	}
	
	/**
	 * Invoke the implementation of a primitive from the RVM main interpreter loop.
	 * @param stack	stack in the current execution frame
	 * @param sp	stack pointer
	 * @return		new stack pointer and modified stack contents
	 */
	int invoke(Object[] stack, int sp) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return (int) methods[ordinal()].invoke(null, stack,  sp);
	}
	
	/***************************************************************
	 * 				IMPLEMENTATION OF PRIMITIVES                   *
	 ***************************************************************/
	
	/*
	 * addition
	 *
	 * infix Addition "+"
	 * {  
     *		&L <: num x &R <: num               -> LUB(&L, &R),
      
     *		list[&L] x list[&R]                 -> list[LUB(&L,&R)],
	 *		list[&L] x &R              		  -> list[LUB(&L,&R)] when &R is not a list,	  
	 *		&L x list[&R <: &L]                 -> list[LUB(&L,&R)] when &L is not a list,
	  
	 *		set[&L] x set[&R]                   -> set[LUB(&L,&R)],
	 *		set[&L] x &R                        -> set[LUB(&L,&R)] when &R is not a list,
	 *		&L x set[&R]                        -> set[LUB(&L,&R)] when &L is not a list,
	  
	 *		map[&K1,&V1] x map[&K2,&V2]         -> map[LUB(&K1,&K2), LUB(&V1,&V2)],
	  
	 *		str x str                           -> str,
	 *		loc x str                           -> loc,
	 *		tuple[&L1,&L2] x tuple[&R1,&R2,&R3] -> tuple[&L1,&L2,&R1,&R2,&R3]
	 * }
	 */
	
	public static int addition_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).add((INumber) stack[sp - 1]);
		return sp - 1;
	}

	public static int addition_list_list(Object[] stack, int sp) {
		stack[sp - 2] = ((IList) stack[sp - 2]).concat((IList) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int addition_map_map(Object[] stack, int sp) {
		stack[sp - 2] = ((IMap) stack[sp - 2]).join((IMap) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int addition_list_elm(Object[] stack, int sp) {
		stack[sp - 2] = ((IList) stack[sp - 2]).append((IValue) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int addition_elm_list(Object[] stack, int sp) {
		stack[sp - 2] = ((IList) stack[sp - 1]).insert((IValue) stack[sp - 2]);
		return sp - 1;
	}
	
	public static int addition_set_elm(Object[] stack, int sp) {
		stack[sp - 2] = ((ISet) stack[sp - 2]).insert((IValue) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int addition_elm_set(Object[] stack, int sp) {
		stack[sp - 2] = ((ISet) stack[sp - 1]).insert((IValue) stack[sp - 2]);
		return sp - 1;
	}
	
	public static int addition_set_set(Object[] stack, int sp) {
		stack[sp - 2] = ((ISet) stack[sp - 2]).union((ISet) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int addition_str_str(Object[] stack, int sp) {
		stack[sp - 2] = ((IString) stack[sp - 2]).concat((IString) stack[sp - 1]);
		return sp - 1;
	}
	
//	public static int addition_loc_str(Object[] stack, int sp) { 	}
	
	public static int addition_tuple_tuple(Object[] stack, int sp) {
		ITuple t1 = (ITuple) stack[sp - 2];
		ITuple t2 = (ITuple) stack[sp - 1];
		int len1 = t1.arity();
		int len2 = t2.arity();
		IValue elems[] = new IValue[len1 + len2];
		for(int i = 0; i < len1; i++)
			elems[i] = t1.get(i);
		for(int i = 0; i < len2; i++)
			elems[len1 + i] = t2.get(i);
		stack[sp - 2] = vf.tuple(elems);
		return sp - 1;
	}
	
	/*
	 * and
	 */
	
	public static int and_bool_bool(Object[] stack, int sp) {
		stack[sp - 2] = ((IBool) stack[sp - 2]).and((IBool) stack[sp - 1]);
		return sp - 1;
	}

	/*
	 * appendAfter
	 */
	public static int appendAfter(Object[] stack, int sp) {
		stack[sp - 2] = ((IList) stack[sp - 2]).append((IValue) stack[sp - 1]);
		return sp - 1;
	}
	
	/*
	 * asType
	 */
	
	/*
	 * composition
	 * infix Composition "o" {
     	lrel[&A,&B] x lrel[&B,&C] -> lrel[&A,&C],
     	rel[&A,&B] x rel[&B,&C] -> rel[&A,&C],
     	map[&A,&B] x map[&B,&C] -> map[&A,&C]
		}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int composition_lrel_lrel(Object[] stack, int sp) {
		stack[sp - 2] = ((IListRelation) stack[sp - 2]).compose((IListRelation) stack[sp - 1]);
		return sp - 1;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int composition_rel_rel(Object[] stack, int sp) {
		stack[sp - 2] = ((IRelationalAlgebra) stack[sp - 2]).compose((IRelationalAlgebra) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int composition_map_map(Object[] stack, int sp) {
		stack[sp - 2] = ((IMap) stack[sp - 2]).compose((IMap) stack[sp - 1]);
		return sp - 1;
	}
	
	/*
	 * division
	 * 
	 * infix Division "/" { &L <: num x &R <: num        -> LUB(&L, &R) }
	 */
	
	public static int division_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).equal((INumber) stack[sp - 1]);
		return sp - 1;
	}
	
	/*
	 * equals
	 */

	public static int equals_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).equal((INumber) stack[sp - 1]);
		return sp - 1;
	}
	
	/*
	 * equivalent
	 */
	
	public static int equivalent_bool_bool(Object[] stack, int sp) {
		stack[sp - 2] = ((IBool) stack[sp - 2]).equivalent((IBool) stack[sp - 1]);
		return sp - 1;
	}

	/*
	 * fieldAccess
	 */
	/*
	 * fieldUpdate
	 */
	/*
	 * fieldProject
	 */
	/*
	 * getAnnotation
	 */
	/*
	 * greaterThan
	 */
	public static int greater_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).greater((INumber) stack[sp - 1]).getValue() ? TRUE : FALSE;
		return sp - 1;
	}
	/*
	 * greaterThanOrEq
	 */
	public static int greater_equal_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).greaterEqual((INumber) stack[sp - 1]).getValue() ? TRUE : FALSE;
		return sp - 1;
	}
	
	/*
	 * has
	 */
	
	/*
	 * implies
	 */
	
	public static int implies_bool_bool(Object[] stack, int sp) {
		stack[sp - 2] = ((IBool) stack[sp - 2]).implies((IBool) stack[sp - 1]);
		return sp - 1;
	}

	
	/*
	 * insertBefore
	 */
	/*
	 * intersection
	 * 
	 * infix Intersection "&" {
 	 *		list[&L] x list[&R]                  -> list[LUB(&L,&R)],
 	 *		set[&L] x set[&R]                    -> set[LUB(&L,&R)],
 	 * 		map[&K1,&V1] x map[&K2,&V2]          -> map[LUB(&K1,&K2), LUB(&V1,&V2)]
} 
	 */
	/*
	 * in
	 */
	/*
	 * is
	 */
	/*
	 * isDefined
	 */
	/*
	 * join
	 */
	/*
	 * lessThan
	 */
	public static int less_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).less((INumber) stack[sp - 1]).getValue() ? TRUE : FALSE;
		return sp - 1;
	}
	/*
	 * lessThanOrEq
	 */
	public static int less_equal_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).lessEqual((INumber) stack[sp - 1]).getValue() ? TRUE : FALSE;
		return sp - 1;
	}
	
	/*
	 * make_list
	 */
	public static int make_list(Object[] stack, int sp) {
		int len = ((IInteger) stack[sp - 1]).intValue();
		IListWriter writer = vf.listWriter();

		for (int i = len - 1; i >= 0; i--) {
			writer.append((IValue) stack[sp - 2 - i]);
		}
		sp = sp - len;
		stack[sp - 1] = writer.done();

		return sp;
	}
	
	/*
	 * make_map
	 */
	public static int make_map(Object[] stack, int sp) {
		int len = ((IInteger) stack[sp - 1]).intValue();
		IMapWriter writer = vf.mapWriter();

		for (int i = 2 * len; i > 0; i -= 2) {
			writer.put((IValue) stack[sp - 1 - i], (IValue) stack[sp - 1 - i + 1]);
		}
		sp = sp - 2 * len;
		stack[sp - 1] = writer.done();

		return sp;
	}
	
	/*
	 * make_set
	 */
	public static int make_set(Object[] stack, int sp) {
		int len = ((IInteger) stack[sp - 1]).intValue();
		ISetWriter writer = vf.setWriter();

		for (int i = len - 1; i >= 0; i--) {
			writer.insert((IValue) stack[sp - 2 - i]);
		}
		sp = sp - len;
		stack[sp - 1] = writer.done();

		return sp;
	}
	
	public static int make_tuple(Object[] stack, int sp) {
		int len = ((IInteger) stack[sp - 1]).intValue();
		IValue[] elems = new IValue[len];
		
		for (int i = 0; i < len; i++) {
			elems[i] = (IValue) stack[sp - 1 - len + i];
		}
		sp = sp - len;
		stack[sp - 1] = vf.tuple(elems);
		return sp;
	}
	

	/*
	 * mod
	 * 
	 * infix Modulo "%" { int x int -> int }
	 */
	
	/*
	 * multiplication
	 */
	public static int product_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).multiply((INumber) stack[sp - 1]);
		return sp - 1;
	}

	/*
	 * negation
	 */
	
	public static int not_bool(Object[] stack, int sp) {
		stack[sp - 2] = ((IBool) stack[sp - 2]).not();
		return sp - 1;
	}
	
	/*
	 * negative
	 * 
	 * prefix UnaryMinus "-" { &L <: num -> &L }
	 */
	
	public static int negative(Object[] stack, int sp) {
		stack[sp - 1] = ((INumber) stack[sp - 1]).negate();
		return sp - 1;
	}
	
	/*
	 * nonEquals
	 */
	
	/*
	 * or
	 */
	
	public static int or_bool_bool(Object[] stack, int sp) {
		stack[sp - 2] = ((IBool) stack[sp - 2]).or((IBool) stack[sp - 1]);
		return sp - 1;
	}
	
	/*
	 * println
	 */
	
	public static int println(Object[] stack, int sp) {
		System.out.println(stack[sp - 1]);
		return sp - 1;
	}
	
	/*
	 * product
	 * 
	 * infix Product "*" {
 	 *		&L <: num x &R <: num                -> LUB(&L, &R),
 	 * 		list[&L] x list[&R]                  -> lrel[&L,&R],
 	 *		set[&L] x set[&R]                    -> rel[&L,&R]
	 * }
	 */
	
	/*
	 * remainder
	 */
	
	/*
	 * slice
	 */
	
	/*
	 * splice
	 */
	
	/*
	 * setAnnotation
	 */
	
	/*
	 * subscript
	 */
	public static int subscript_list_int(Object[] stack, int sp) {
		stack[sp - 2] = ((IList) stack[sp - 2]).get(((IInteger) stack[sp - 1])
				.intValue());
		return sp - 1;
	}

	public static Object subscript_map(Object[] stack, int sp) {
		stack[sp - 2] = ((IMap) stack[sp - 2]).get((IValue) stack[sp - 1]);
		return sp - 1;
	}

	/*
	 * subtraction
	 * 
	 * infix Difference "-" {
 	 *		&L <: num x &R <: num                -> LUB(&L, &R),
 	 * 		list[&L] x list[&R]                  -> list[LUB(&L,&R)],
 	 *		set[&L] x set[&R]                    -> set[LUB(&L,&R)],
 	 * 		map[&K1,&V1] x map[&K2,&V2]          -> map[LUB(&K1,&K2), LUB(&V1,&V2)]
	 * }
	 */
	public static int subtraction_num_num(Object[] stack, int sp) {
		stack[sp - 2] = ((INumber) stack[sp - 2]).subtract((INumber) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int subtraction_list_list(Object[] stack, int sp) {
		stack[sp - 2] = ((IList) stack[sp - 2]).subtract((IList) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int subtraction_set_set(Object[] stack, int sp) {
		stack[sp - 2] = ((ISet) stack[sp - 2]).subtract((ISet) stack[sp - 1]);
		return sp - 1;
	}
	
	public static int subtraction_map_map(Object[] stack, int sp) {
		stack[sp - 2] = ((IMap) stack[sp - 2]).remove((IMap) stack[sp - 1]);
		return sp - 1;
	}

	/*
	 * transitiveClosure
	 * 
	 * postfix Closure "+", "*" { 
     *  	lrel[&L,&L]			-> lrel[&L,&L],
     * 		rel[&L,&L]  		-> rel[&L,&L]
	 * }
	 */
	
	@SuppressWarnings("rawtypes")
	public static int transitive_closure_lrel(Object[] stack, int sp) {
		stack[sp - 1] = ((IListRelation) stack[sp - 1]).closure();
		return sp;
	}
	
	@SuppressWarnings("rawtypes")
	public static int transitive_closure_rel(Object[] stack, int sp) {
		stack[sp - 1] = ((IRelationalAlgebra) stack[sp - 1]).closure();
		return sp;
	}

	/*
	 * transitiveReflexiveClosure
	 */
	@SuppressWarnings("rawtypes")
	public static int transitive_reflexive_closure_lrel(Object[] stack, int sp) {
		stack[sp - 1] = ((IListRelation) stack[sp - 1]).closureStar();
		return sp;
	}
	
	@SuppressWarnings("rawtypes")
	public static int transitive_reflexive_closure_rel(Object[] stack, int sp) {
		stack[sp - 1] = ((IRelationalAlgebra) stack[sp - 1]).closureStar();
		return sp;
	}

}

