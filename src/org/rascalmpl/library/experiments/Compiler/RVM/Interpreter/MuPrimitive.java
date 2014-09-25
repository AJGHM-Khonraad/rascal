package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.imp.pdb.facts.IBool;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IInteger;
import org.eclipse.imp.pdb.facts.IList;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.INode;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.ITuple;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;

public enum MuPrimitive {
	addition_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((Integer) stack[sp - 2]) + ((Integer) stack[sp - 1]);
			return sp - 1;
		};
	},
	and_mbool_mbool {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;			
			stack[sp - 2] = ((IBool) stack[sp - 2]).and((IBool) stack[sp - 1]);
			return sp - 1;
		};
	},
	assign_pair {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 3;
			int v1 = ((Integer) stack[sp - 3]);
			int v2 = ((Integer) stack[sp - 2]);
			Object[] pair = (Object[]) stack[sp - 1];
			stack[v1] = pair[0];
			stack[v2] = pair[1];
			stack[sp - 3] = pair;
			return sp - 2; // TODO:???
		};
	},
	assign_subscript_array_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 3;
			Object[] ar = (Object[]) stack[sp - 3];
			Integer index = ((Integer) stack[sp - 2]);
			ar[index] = stack[sp - 1];
			stack[sp - 3] = stack[sp - 1];
			return sp - 2;
		};
	},
	check_arg_type {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			Type argType = ((IValue) stack[sp - 2]).getType();
			Type paramType = ((Type) stack[sp - 1]);
			stack[sp - 2] = vf.bool(argType.isSubtypeOf(paramType));
			return sp - 1;
		};
	},
	division_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((Integer) stack[sp - 2]) / ((Integer) stack[sp - 1]);
			return sp - 1;
		};
	},
	equal_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = vf.bool(((Integer) stack[sp - 2]) == ((Integer) stack[sp - 1]));
			return sp - 1;
		};
	},
	equal {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			if (stack[sp - 2] instanceof IValue	&& (stack[sp - 1] instanceof IValue)) {
				stack[sp - 2] = vf.bool(((IValue) stack[sp - 2]).isEqual(((IValue) stack[sp - 1])));
			} else if (stack[sp - 2] instanceof Type && (stack[sp - 1] instanceof Type)) {
				stack[sp - 2] = vf.bool(((Type) stack[sp - 2]) == ((Type) stack[sp - 1]));
			} else
				throw new CompilerError("MuPrimitive equal -- not defined on "
						+ stack[sp - 2].getClass() + " and "
						+ stack[sp - 1].getClass());
			return sp - 1;
		};
	},
	equal_set_mset {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			ISet set = (ISet) stack[sp - 2];
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 1];
			stack[sp - 2] = Rascal_FALSE;
			if (set.size() != mset.size()) {
				return sp - 1;
			}
			for (IValue v : set) {
				if (!mset.contains(v)) {
					return sp - 1;
				}
			}
			stack[sp - 2] = Rascal_TRUE;
			return sp - 1;
		};
	},
	equivalent_mbool_mbool {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((IBool) stack[sp - 2]).equivalent((IBool) stack[sp - 1]);
			return sp - 1;
		};
	},
	get_children {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			INode nd = (INode) stack[sp - 1];
			Object[] elems = new Object[nd.arity()];
			for (int i = 0; i < nd.arity(); i++) {
				elems[i] = nd.get(i);
			}
			stack[sp - 1] = elems;
			return sp;
		};
	},
	get_children_without_layout_or_separators {
		@SuppressWarnings("deprecation")
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			IConstructor cons = (IConstructor) stack[sp - 1];
			IConstructor prod = (IConstructor) cons.get(0);
			IList args = (IList) cons.get(1);
			IConstructor symbol = (IConstructor) prod.get(0);

			int step;

			switch(symbol.getName()){

			case "iter":
			case "iter-star":
				step = 2; break;
			case "iter-seps":
			case "iter-seps-star":
				step = 4; break;
			default:
				step = 2;
			}

			int len = (args.length() / step) + 1;
			int non_lit_len = 0;

			
			for(int i = 0; i < len; i += step){
				if(!$is_literal(args.get(i * step))){
					non_lit_len++;
				}
			}
			IValue[] elems = new IValue[non_lit_len + 1];
			int j = 0;
			for(int i = 0; i < len; i += step){
				if(!$is_literal(args.get(i * step))){
					elems[j++] = args.get(i * step);
				}
			}
			TypeFactory tf = TypeFactory.getInstance();
			elems[non_lit_len] =vf.map(tf.voidType(), tf.voidType());
			stack[sp - 1] = elems;
			return sp;
		}
	},
	get_mmap {
		@SuppressWarnings("unchecked")
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			Map<String,IValue> m = (Map<String,IValue>) stack[sp - 2];
			IString key = (IString) stack[sp - 1];
			stack[sp - 2] = m.get(key.getValue());
			return sp - 1;
		};
	},
	get_name {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			INode nd = (INode) stack[sp - 1];
			stack[sp - 1] = vf.string(nd.getName());
			return sp;
		};
	},
	get_name_and_children_and_keyword_mmap {
		/*
		 * Given a constructor or node get: 
		 * - its name 
		 * - positional arguments 
		 * - keyword parameters collected in a mmap
		 */
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			INode v = (INode) stack[sp - 1];
			int cons_arity = v.arity();
			Object[] elems = new Object[cons_arity + 2];
			elems[0] = vf.string(v.getName());
			for (int i = 0; i < cons_arity; i++) {
			  elems[i + 1] = v.get(i);
			}
			if(v.mayHaveKeywordParameters()){
				elems[cons_arity + 1] = v.asWithKeywordParameters().getParameters();
			} else {
				elems[cons_arity + 1] = emptyKeywordMap;
			}
			stack[sp - 1] = elems;
			return sp;
		};
	},
	get_children_and_keyword_mmap {
		/*
		 * Given a constructor or node get: 
		 * - positional arguments 
		 * - keyword parameters collected in a mmap
		 */
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			INode v = (INode) stack[sp - 1];
			int cons_arity = v.arity();
			Object[] elems = new Object[cons_arity + 1];
			for (int i = 0; i < cons_arity; i++) {
			  elems[i] = v.get(i);
			}
			elems[cons_arity] = v.asWithKeywordParameters().getParameters();
			stack[sp - 1] = elems;
			return sp;
		};
	},
	get_children_and_keyword_values {
		/*
		 * Given a constructor or node get: 
		 * - positional arguments 
		 * - list of values of keyword arguments
		 */
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			INode v = (INode) stack[sp - 1];
			int cons_arity = v.arity();
			Map<String, IValue> m = v.asWithKeywordParameters().getParameters();
			int kw_arity = m.size();
			Object[] elems = new Object[cons_arity + kw_arity];
			for (int i = 0; i < cons_arity; i++) {
			  elems[i] = v.get(i);
			}
			int j = cons_arity;
			for(IValue val : m.values()){
				elems[j++] = val;
			}
			stack[sp - 1] = elems;
			return sp;
		};
	},
	get_tuple_elements {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			ITuple tup = (ITuple) stack[sp - 1];
			int nelem = tup.arity();
			Object[] elems = new Object[nelem];
			for (int i = 0; i < nelem; i++) {
				elems[i] = tup.get(i);
			}
			stack[sp - 1] = elems;
			return sp;
		};
	},
	greater_equal_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = vf.bool(((Integer) stack[sp - 2]) >= ((Integer) stack[sp - 1]));
			return sp - 1;
		};
	},
	greater_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = vf.bool(((Integer) stack[sp - 2]) > ((Integer) stack[sp - 1]));
			return sp - 1;
		};
	},
	// Has a concrete term a given label?
	has_label {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			IValue v = (IValue) stack[sp - 2];
			if(v.getType().isAbstractData()){
				IConstructor cons = (IConstructor) v;
				if(cons.getName().equals("appl")){
					IConstructor prod = (IConstructor) cons.get(0);
					IConstructor symbol = (IConstructor) prod.get(0);
					
					if(symbol.getName().equals("label")){
						IString label_name = (IString) stack[sp - 1];
						if(((IString) symbol.get(0)).equals(label_name)){
							stack[sp - 2] = Rascal_TRUE;
							return sp - 1;
						}
					}
					
				}
			}
			stack[sp - 2] = Rascal_FALSE;
			return sp - 1;
		}
	},
	implies_mbool_mbool {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((IBool) stack[sp - 2]).implies((IBool) stack[sp - 1]);
			return sp - 1;
		};
	},
	is_defined {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			Reference ref = (Reference) stack[sp - 1];
			stack[sp - 1] = vf.bool(ref.isDefined());
			return sp;
		};
	},
	is_element_mset {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = vf.bool(((HashSet<IValue>) stack[sp - 1]).contains((IValue) stack[sp - 2]));
			return sp - 1;
		};
	},
	is_bool {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isBool());
			return sp;
		};
	},
	is_constructor {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isAbstractData());
			return sp;
		};
	},
	is_datetime {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isDateTime());
			return sp;
		};
	},
	is_int {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isInteger());
			return sp;
		};
	},
	is_list {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isList());
			return sp;
		};
	},
	is_lrel {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isListRelation());
			return sp;
		};
	},
	is_loc {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isSourceLocation());
			return sp;
		};
	},
	is_map {	// IMap
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isMap());
			return sp;
		};
	},
	
	is_mmap {	// Map used as internal representation of keyword parameters
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(stack[sp - 1] instanceof Map);
			return sp;
		};
	},
	is_node {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isNode());
			return sp;
		};
	},
	is_num {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isNumber());
			return sp;
		};
	},
	is_real {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isReal());
			return sp;
		};
	},
	is_rat {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isRational());
			return sp;
		};
	},
	is_rel {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 2] = vf.bool(((IValue) stack[sp - 1]).getType().isRelation());
			return sp;
		};
	},
	is_set {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isSet());
			return sp;
		};
	},
	is_str {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isString());
			return sp;
		};
	},

	is_tuple {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.bool(((IValue) stack[sp - 1]).getType().isTuple());
			return sp;
		};
	},
	keys_map {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			IMap map = ((IMap) stack[sp - 1]);
			IListWriter writer = vf.listWriter();
			for (IValue key : map) {
				writer.append(key);
			}
			stack[sp - 1] = writer.done();
			return sp;
		};
	},
	values_map {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			IMap map = ((IMap) stack[sp - 1]);
			IListWriter writer = vf.listWriter();
			for (IValue key : map) {
				writer.append(map.get(key));
			}
			stack[sp - 1] = writer.done();
			return sp;
		};
	},
	less_equal_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = vf.bool(((Integer) stack[sp - 2]) <= ((Integer) stack[sp - 1]));
			return sp - 1;
		};
	},
	less_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = vf.bool(((Integer) stack[sp - 2]) < ((Integer) stack[sp - 1]));
			return sp - 1;
		};
	},
	make_iarray {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity >= 0;

			IValue[] ar = new IValue[arity];
			for (int i = arity - 1; i >= 0; i--) {
				ar[i] = (IValue) stack[sp - arity + i];
			}
			sp = sp - arity + 1;
			stack[sp - 1] = ar;
			return sp;
		};
	},
	make_iarray_of_size {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			int len = ((Integer) stack[sp - 1]);
			stack[sp - 1] = new IValue[len];
			return sp;
		};
	},
	make_array {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity >= 0;

			Object[] ar = new Object[arity];

			for (int i = arity - 1; i >= 0; i--) {
				ar[i] = stack[sp - arity + i];
			}
			sp = sp - arity + 1;
			stack[sp - 1] = ar;
			return sp;
		};
	},
	make_array_of_size {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			int len = ((Integer) stack[sp - 1]);
			stack[sp - 1] = new Object[len];
			return sp;
		};
	},
	make_mset {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 0;
			HashSet<IValue> mset = new HashSet<IValue>();
			stack[sp] = mset;
			return sp + 1;
		};
	},
	// Create a map from keyword name to an entry <type, default_value>
	make_mmap_str_entry {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 0;
			stack[sp] = new HashMap<String, Map.Entry<Type, IValue>>();
			return sp + 1;
		};
	}, 
	// Create an entry <type, default_value>
	make_mentry_type_ivalue {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = new AbstractMap.SimpleEntry<Type, IValue>((Type) stack[sp - 2], (IValue) stack[sp - 1]);
			return sp - 1;
		};
	},
	// Create a keword map with <name, value> entries
	make_mmap {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity >= 0;
			
			if(arity == 0){
				stack[sp] = new HashMap<String, IValue>();
				return sp + 1;
			}
			Map<String, IValue> writer = new HashMap<String, IValue>();
			for (int i = arity; i > 0; i -= 2) {
				writer.put(((IString) stack[sp - i]).getValue(), (IValue) stack[sp - i + 1]);
			}
			sp = sp - arity + 1;
			stack[sp - 1] = writer;

			return sp;
		}
	},
	// Does a keyword map with <name, value> entries contain a given key?
	mmap_contains_key {
		@SuppressWarnings("unchecked")
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			Map<String,IValue> m = (Map<String,IValue>) stack[sp - 2];
			IString key = ((IString) stack[sp - 1]);
			stack[sp - 2] = vf.bool(m.containsKey(key.getValue()));
			return sp - 1;
		};
	},
	min_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			Integer x = ((Integer) stack[sp - 2]);
			Integer y = ((Integer) stack[sp - 1]);
			stack[sp - 2] = x < y ? x : y;
			return sp - 1;
		};
	},
	max_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			Integer x = ((Integer) stack[sp - 2]);
			Integer y = ((Integer) stack[sp - 1]);
			stack[sp - 2] = x > y ? x : y;
			return sp - 1;
		};
	},
	mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = ((IInteger) stack[sp - 1]).intValue();
			return sp;
		};
	},
	modulo_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((Integer) stack[sp - 2])
					% ((Integer) stack[sp - 1]);
			return sp - 1;
		};
	},
	mset {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			ISet set = ((ISet) stack[sp - 1]);
			int n = set.size();
			HashSet<IValue> mset = n > 0 ? new HashSet<IValue>(set.size())
					: emptyMset;
			for (IValue v : set) {
				mset.add(v);
			}
			stack[sp - 1] = mset;
			return sp;
		};
	},
	mset_empty() {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 0;
			stack[sp] = emptyMset;
			return sp + 1;
		};
	},
	mset2list {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 1];
			IListWriter writer = vf.listWriter();
			for (IValue elem : mset) {
				writer.append(elem);
			}
			stack[sp - 1] = writer.done();
			return sp;
		};
	},
	mset_destructive_add_elm {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 2];
			if(mset == emptyMset){
				mset = (HashSet<IValue>) emptyMset.clone();
			}
			IValue elm = ((IValue) stack[sp - 1]);
			mset.add(elm);
			stack[sp - 2] = mset;
			return sp - 1;
		};
	},
	mset_destructive_add_mset {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			HashSet<IValue> lhs = (HashSet<IValue>) stack[sp - 2];
			if(lhs == emptyMset){
				lhs = (HashSet<IValue>) emptyMset.clone();
			}
			// lhs = (HashSet<IValue>) lhs.clone();
			HashSet<IValue> rhs = (HashSet<IValue>) stack[sp - 1];
			lhs.addAll(rhs);
			stack[sp - 2] = lhs;
			return sp - 1;
		};
	},
	mmap_str_entry_add_entry_type_ivalue {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 3;
			/*stack[sp - 3] = */((Map<String, Map.Entry<Type, IValue>>) stack[sp - 3])
					.put(((IString) stack[sp - 2]).getValue(),
							(Map.Entry<Type, IValue>) stack[sp - 1]);
			return sp - 2;
		};
	}, // kwp
//	mmap_str_ivalue_add_ivalue {
//		@Override
//		@SuppressWarnings("unchecked")
//		public int execute(Object[] stack, int sp, int arity) {
//			assert arity == 3;
//			stack[sp - 3] = ((Map<String, IValue>) stack[sp - 3]).put(
//					((IString) stack[sp - 2]).getValue(),
//					(IValue) stack[sp - 1]);
//			return sp - 2;
//		};
//	}, // kwp
	multiplication_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((Integer) stack[sp - 2])
					* ((Integer) stack[sp - 1]);
			return sp - 1;
		};
	},
	mset_destructive_subtract_mset {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			HashSet<IValue> lhs = (HashSet<IValue>) stack[sp - 2];
			// lhs = (HashSet<IValue>) lhs.clone();
			HashSet<IValue> rhs = (HashSet<IValue>) stack[sp - 1];

			lhs.removeAll(rhs);
			stack[sp - 2] = lhs;
			return sp - 1;
		};
	},
	mset_subtract_mset {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			HashSet<IValue> lhs = (HashSet<IValue>) stack[sp - 2];
			lhs = (HashSet<IValue>) lhs.clone();
			HashSet<IValue> rhs = (HashSet<IValue>) stack[sp - 1];
			lhs.removeAll(rhs);
			stack[sp - 2] = lhs;
			return sp - 1;
		};
	},
	mset_destructive_subtract_set {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 2];
			mset = (HashSet<IValue>) mset.clone();
			ISet set = ((ISet) stack[sp - 1]);
			for (IValue v : set) {
				mset.remove(v);
			}
			stack[sp - 2] = mset;
			return sp - 1;
		};
	},
	mset_subtract_set {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 2];
			mset = (HashSet<IValue>) mset.clone();
			ISet set = ((ISet) stack[sp - 1]);
			for (IValue v : set) {
				mset.remove(v);
			}
			stack[sp - 2] = mset;
			return sp - 1;
		};
	},
	mset_destructive_subtract_elm {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 2];
			// mset = (HashSet<IValue>) mset.clone();
			IValue elm = ((IValue) stack[sp - 1]);
			mset.remove(elm);
			stack[sp - 2] = mset;
			return sp - 1;
		};
	},
	mset_subtract_elm {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 2];
			mset = (HashSet<IValue>) mset.clone();
			IValue elm = ((IValue) stack[sp - 1]);
			mset.remove(elm);
			stack[sp - 2] = mset;
			return sp - 1;
		};
	},
	not_equal_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = vf.bool(((Integer) stack[sp - 2]) != ((Integer) stack[sp - 1]));
			return sp - 1;
		};
	},
	not_mbool {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = ((IBool) stack[sp - 1]).not();
			return sp;
		};
	},
	occurs_list_list_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 3;
			IList sublist = ((IList) stack[sp - 3]);
			int nsub = sublist.length();
			IList list = ((IList) stack[sp - 2]);
			Integer start = (Integer) stack[sp - 1];
			int nlist = list.length();
			stack[sp - 3] = Rascal_FALSE;
			int newsp = sp - 2;
			if (start + nsub <= nlist) {
				for (int i = 0; i < nsub; i++) {
					if (!sublist.get(i).isEqual(list.get(start + i)))
						return newsp;
				}
			} else {
				return newsp;
			}

			stack[sp - 3] = Rascal_TRUE;
			return newsp;
		};
	},
	or_mbool_mbool {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((IBool) stack[sp - 2]).or((IBool) stack[sp - 1]);
			return sp - 1;
		};
	},
	power_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			int n1 = ((Integer) stack[sp - 2]);
			int n2 = ((Integer) stack[sp - 1]);
			int pow = 1;
			for (int i = 0; i < n2; i++) {
				pow *= n1;
			}
			stack[sp - 2] = pow;
			return sp - 1;
		};
	},
	rbool {  // TODO should go
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			return sp;
		};
	},
	rint {
		/*
		 * rint -- convert muRascal int (mint) to Rascal int (rint)
		 */
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = vf.integer((Integer) stack[sp - 1]);
			return sp;
		};
	},
	regexp_compile {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			String RegExpAsString = ((IString) stack[sp - 2]).getValue();
			String subject = ((IString) stack[sp - 1]).getValue();
			try {
				Pattern pat = Pattern.compile(RegExpAsString);
				stack[sp - 2] = pat.matcher(subject);
				return sp - 1;
			} catch (PatternSyntaxException e) {
				//throw new CompilerError("Syntax error in regular expression: " + RegExpAsString);
				//TODO: change to something like:
				throw RascalRuntimeException.RegExpSyntaxError(RegExpAsString, null);
			}
		};
	},
	regexp_find {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			Matcher matcher = (Matcher) stack[sp - 1];
			stack[sp - 1] = vf.bool(matcher.find());
			return sp;
		};
	},
	regexp_group {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			Matcher matcher = (Matcher) stack[sp - 2];
			int idx = (Integer) stack[sp - 1];
			stack[sp - 2] = vf.string(matcher.group(idx));
			return sp - 1;
		};
	},
	set {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 1];
			ISetWriter w = vf.setWriter();
			for (IValue v : mset) {
				w.insert(v);
			}
			stack[sp - 1] = w.done();
			return sp;
		};
	},
	set2list {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			ISet set = (ISet) stack[sp - 1];
			IListWriter writer = vf.listWriter();
			for (IValue elem : set) {
				writer.append(elem);
			}
			stack[sp - 1] = writer.done();
			return sp;
		};
	},
	set_is_subset_of_mset {
		@Override
		@SuppressWarnings("unchecked")
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			ISet subset = ((ISet) stack[sp - 2]);
			HashSet<IValue> mset = (HashSet<IValue>) stack[sp - 1];
			for (IValue v : subset) {
				if (!mset.contains(v)) {
					stack[sp - 2] = Rascal_FALSE;
					return sp - 1;
				}
			}
			stack[sp - 2] = Rascal_TRUE;
			return sp - 1;
		};
	},
	size_array {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = ((Object[]) stack[sp - 1]).length;
			return sp;
		};
	},
	size_list {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = ((IList) stack[sp - 1]).length();
			return sp;
		};
	},
	size_set {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = ((ISet) stack[sp - 1]).size();
			return sp;
		};
	},
	size_mset {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = ((HashSet<?>) stack[sp - 1]).size();
			return sp;
		};
	},
	size_map {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = ((IMap) stack[sp - 1]).size();
			return sp;
		};
	},
	size_tuple {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			stack[sp - 1] = ((ITuple) stack[sp - 1]).arity();
			return sp;
		};
	},
	size {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			if (stack[sp - 1] instanceof IConstructor) {
				stack[sp - 1] = ((IConstructor) stack[sp - 1]).arity();
			} else if (stack[sp - 1] instanceof INode) {
				stack[sp - 1] = ((INode) stack[sp - 1]).arity();
			} else if (stack[sp - 1] instanceof IList) {
				stack[sp - 1] = ((IList) stack[sp - 1]).length();
			} else if (stack[sp - 1] instanceof ISet) {
				stack[sp - 1] = ((ISet) stack[sp - 1]).size();
			} else if (stack[sp - 1] instanceof IMap) {
				stack[sp - 1] = ((IMap) stack[sp - 1]).size();
			} else if (stack[sp - 1] instanceof ITuple) {
				stack[sp - 1] = ((ITuple) stack[sp - 1]).arity();
			}
			return sp;
		};
	},
	starts_with {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 3;
			IList sublist = (IList) stack[sp - 3];
			IList list = (IList) stack[sp - 2];
			int start = (Integer) stack[sp - 1];
			IBool eq = Rascal_TRUE;

			if (start + sublist.length() <= list.length()) {
				for (int i = 0; i < sublist.length() && eq.getValue(); i++) {
					if (!sublist.get(i).equals(list.get(start + i))) {
						eq = Rascal_FALSE;
					}
				}
			}
			stack[sp - 3] = eq;
			return sp - 2;
		};
	},
	sublist_list_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 3;
			IList lst = (IList) stack[sp - 3];
			int offset = ((Integer) stack[sp - 2]);
			int length = ((Integer) stack[sp - 1]);
			stack[sp - 3] = lst.sublist(offset, length);
			return sp - 2;
		};
	},
	subscript_array_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((Object[]) stack[sp - 2])[((Integer) stack[sp - 1])];
			return sp - 1;
		};
	},
	subscript_list_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((IList) stack[sp - 2]).get((Integer) stack[sp - 1]);
			return sp - 1;
		};
	},
	subscript_tuple_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((ITuple) stack[sp - 2]).get((Integer) stack[sp - 1]);
			return sp - 1;
		};
	},
	subtraction_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((Integer) stack[sp - 2]) - ((Integer) stack[sp - 1]);
			return sp - 1;
		};
	},
	subtype {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = vf.bool(((Type) stack[sp - 2]).isSubtypeOf((Type) stack[sp - 1]));
			return sp - 1;
		};
	},
	typeOf {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			if (stack[sp - 1] instanceof Integer) {
				stack[sp - 1] = TypeFactory.getInstance().integerType();
			} else {
				stack[sp - 1] = ((IValue) stack[sp - 1]).getType();
			}
			return sp;
		};
	},
	typeOfMset {
		@SuppressWarnings("unchecked")
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			if (stack[sp - 1] instanceof HashSet) {
				HashSet<IValue> mset =  (HashSet<IValue>) stack[sp - 1];
				Type elmType = TypeFactory.getInstance().voidType();
				for (IValue elm : mset) {
					elmType = elm.getType().lub(elmType);
				}
				stack[sp - 1] = TypeFactory.getInstance().setType(elmType);
			}
			return sp;
		};
	},
	undefine {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			Reference ref = (Reference) stack[sp - 1];
			stack[sp - 1] = ref.getValue();
			ref.undefine();
			return sp;
		};
	},
	product_mint_mint {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 2;
			stack[sp - 2] = ((Integer) stack[sp - 2]) * ((Integer) stack[sp - 1]);
			return sp - 1;
		};
	},

	typeOf_constructor {
		@Override
		public int execute(Object[] stack, int sp, int arity) {
			assert arity == 1;
			if (stack[sp - 1] instanceof Integer) {
				stack[sp - 1] = TypeFactory.getInstance().integerType();
			} else if (stack[sp - 1] instanceof IConstructor) {
				stack[sp - 1] = ((IConstructor) stack[sp - 1]).getConstructorType();
			} else {
				stack[sp - 1] = ((IValue) stack[sp - 1]).getType();
			}
			return sp;
		}
	};

	private static IValueFactory vf;
//	static Method[] methods;
	// Changed values to public (Ferry)
	public static final MuPrimitive[] values = MuPrimitive.values();
	private static final HashSet<IValue> emptyMset = new HashSet<IValue>(0);

//	private static boolean profiling = false;
//	private static long timeSpent[] = new long[values.length];

//	private static PrintWriter stdout;
	
	private static IBool Rascal_TRUE;
	private static IBool Rascal_FALSE;
	
	private static final Map<String, IValue> emptyKeywordMap = new  HashMap<String, IValue>();

	public static MuPrimitive fromInteger(int muprim) {
		return values[muprim];
	}

	public int execute(Object[] stack, int sp, int arity) {
		System.err.println("Not implemented mufunction");
		return 0 ;
	}
	/**
	 * Initialize the primitive methods.
	 * 
	 * @param fact
	 *            value factory to be used
	 * @param stdout
	 *            TODO
	 * @param doProfile
	 *            TODO
	 * @param stdout
	 */
	public static void init(IValueFactory fact, PrintWriter stdoutWriter,
			boolean doProfile) {
		vf = fact;
		Rascal_TRUE = vf.bool(true);
		Rascal_FALSE = vf.bool(false);
	}

	public static void exit() {
	}

	/*******************************************************************
	 *                 AUXILIARY FUNCTIONS                             *
	 ******************************************************************/   
	private static boolean $is_literal(IValue v){
		if(v.getType().isAbstractData()){
			IConstructor appl = (IConstructor) v;
			if(appl.getName().equals("appl")){
				IConstructor prod = (IConstructor) appl.get(0);
				IConstructor symbol = (IConstructor) prod.get(0);
				return symbol.getName().equals("lit");
			}
		}
		return false;
	}
	 
}
