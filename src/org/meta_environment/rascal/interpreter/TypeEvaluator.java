package org.meta_environment.rascal.interpreter;

import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.meta_environment.rascal.ast.Formal;
import org.meta_environment.rascal.ast.NullASTVisitor;
import org.meta_environment.rascal.ast.TypeArg;
import org.meta_environment.rascal.ast.TypeVar;
import org.meta_environment.rascal.ast.BasicType.Bool;
import org.meta_environment.rascal.ast.BasicType.Real;
import org.meta_environment.rascal.ast.BasicType.Int;
import org.meta_environment.rascal.ast.BasicType.Loc;
import org.meta_environment.rascal.ast.BasicType.String;
import org.meta_environment.rascal.ast.BasicType.Tree;
import org.meta_environment.rascal.ast.BasicType.Value;
import org.meta_environment.rascal.ast.BasicType.Void;
import org.meta_environment.rascal.ast.Formal.TypeName;
import org.meta_environment.rascal.ast.FunctionType.TypeArguments;
import org.meta_environment.rascal.ast.Parameters.VarArgs;
import org.meta_environment.rascal.ast.Signature.NoThrows;
import org.meta_environment.rascal.ast.Signature.WithThrows;
import org.meta_environment.rascal.ast.StructuredType.List;
import org.meta_environment.rascal.ast.StructuredType.Map;
import org.meta_environment.rascal.ast.StructuredType.Relation;
import org.meta_environment.rascal.ast.StructuredType.Set;
import org.meta_environment.rascal.ast.StructuredType.Tuple;
import org.meta_environment.rascal.ast.Type.Ambiguity;
import org.meta_environment.rascal.ast.Type.Basic;
import org.meta_environment.rascal.ast.Type.Function;
import org.meta_environment.rascal.ast.Type.Structured;
import org.meta_environment.rascal.ast.Type.User;
import org.meta_environment.rascal.ast.Type.Variable;
import org.meta_environment.rascal.ast.TypeArg.Default;
import org.meta_environment.rascal.ast.TypeArg.Named;
import org.meta_environment.rascal.interpreter.env.ClosureResult;
import org.meta_environment.rascal.interpreter.env.GlobalEnvironment;

public class TypeEvaluator extends NullASTVisitor<Type> {
	private static TypeFactory tf = TypeFactory.getInstance();
	private static final GlobalEnvironment env = GlobalEnvironment.getInstance();
	private static final TypeEvaluator sInstance = new TypeEvaluator();
	
	private TypeEvaluator() {
	}
	
	public static TypeEvaluator getInstance() {
		return sInstance;
	}
	
	@Override
	public Type visitTypeBasic(Basic x) {
		return x.getBasic().accept(this);
	}
	
	@Override
	public Type visitFormalTypeName(TypeName x) {
		return x.getType().accept(this);
	}
	
	@Override
	public Type visitFunctionDeclarationDefault(
			org.meta_environment.rascal.ast.FunctionDeclaration.Default x) {
		return x.getSignature().accept(this);
	}
	
	@Override
	public Type visitParametersDefault(
			org.meta_environment.rascal.ast.Parameters.Default x) {
		return x.getFormals().accept(this);
	}
	
	@Override
	public Type visitParametersVarArgs(VarArgs x) {
		Type formals = x.getFormals().accept(this);
		int arity = formals.getArity();
		
		if (arity == 0) {
			// TODO is this sensible or should we restrict the syntax?
			return tf.tupleType(tf.listType(tf.valueType()), "args");
		}
		else {
			Type[] types = new Type[arity];
			java.lang.String[] labels = new java.lang.String[arity];
			int i;
			
			for (i = 0; i < arity - 1; i++) {
				types[i] = formals.getFieldType(i);
				labels[i] = formals.getFieldName(i);
			}
			
			types[i] = tf.listType(formals.getFieldType(i));
			labels[i] = formals.getFieldName(i);
			
			return tf.tupleType(types, labels);
		}
		
	}
	
	@Override
	public Type visitSignatureNoThrows(NoThrows x) {
		return x.getParameters().accept(this);
	}
	
	@Override
	public Type visitSignatureWithThrows(WithThrows x) {
		return x.getParameters().accept(this);
	}
	
	@Override
	public Type visitFormalsDefault(
			org.meta_environment.rascal.ast.Formals.Default x) {
		java.util.List<Formal> list = x.getFormals();
		Object[] typesAndNames = new Object[list.size() * 2];
		
		for (int formal = 0, index = 0; formal < list.size(); formal++, index++) {
			typesAndNames[index++] = list.get(formal).accept(this);
			typesAndNames[index] = list.get(formal).getName().toString();
		}
		
		return tf.tupleType(typesAndNames);
	}
	
	@Override
	public Type visitTypeFunction(Function x) {
		return ClosureResult.getClosureType();
	}
	
	
	@Override
	public Type visitFunctionTypeTypeArguments(TypeArguments x) {
		return getArgumentTypes(x.getArguments());
	}
	
	@Override
	public Type visitBasicTypeBool(Bool x) {
		return tf.boolType();
	}
	
	@Override
	public Type visitBasicTypeReal(Real x) {
		return tf.doubleType();
	}
	
	@Override
	public Type visitBasicTypeInt(Int x) {
		return tf.integerType();
	}
	
	@Override
	public Type visitBasicTypeLoc(Loc x) {
		return tf.sourceLocationType();
	}
	
	@Override
	public Type visitBasicTypeString(String x) {
		return tf.stringType();
	}
	
	@Override
	public Type visitBasicTypeTree(Tree x) {
		return tf.treeType();
	}
	
	@Override
	public Type visitBasicTypeValue(Value x) {
		return tf.valueType();
	}
	
	@Override
	public Type visitBasicTypeVoid(Void x) {
		return tf.voidType();
	}
	
	@Override
	public Type visitTypeStructured(Structured x) {
		return x.getStructured().accept(this);
	}
	
	@Override
	public Type visitStructuredTypeList(List x) {
		return tf.listType(x.getTypeArg().accept(this));
	}
	
	@Override
	public Type visitStructuredTypeMap(Map x) {
		return tf.mapType(x.getFirst().accept(this), x.getSecond().accept(this));
	}
	
	@Override
	public Type visitStructuredTypeRelation(Relation x) {
		return getArgumentTypes(x.getArguments());
	}

	private Type getArgumentTypes(java.util.List<TypeArg> args) {
		Type[] fieldTypes = new Type[args.size()];
		java.lang.String[] fieldLabels = new java.lang.String[args.size()];
		
		int i = 0;
		for (TypeArg arg : args) {
			fieldTypes[i] = arg.getType().accept(this);
			
			if (arg.isNamed()) {
				fieldLabels[i] = arg.getName().toString();
			}
			else {
				fieldLabels[i] = Integer.toString(i);
			}
			i++;
		}
		
		
		return tf.relTypeFromTuple(tf.tupleType(fieldTypes, fieldLabels));
	}
	
	@Override
	public Type visitStructuredTypeSet(Set x) {
		return tf.setType(x.getTypeArg().accept(this));
	}
	
	@Override
	public Type visitStructuredTypeTuple(Tuple x) {
		java.util.List<TypeArg> args = x.getArguments();
		Type[] fieldTypes = new Type[args.size()];
		java.lang.String[] fieldLabels = new java.lang.String[args.size()];
		
		int i = 0;
		for (TypeArg arg : args) {
			fieldTypes[i] = arg.getType().accept(this);
			

			if (arg.isNamed()) {
				fieldLabels[i] = arg.getName().toString();
			}
			else {
				fieldLabels[i] = Integer.toString(i);
			}
			i++;
		}
		
		return tf.tupleType(fieldTypes, fieldLabels);
	}
	
	@Override
	public Type visitTypeArgDefault(Default x) {
		return x.getType().accept(this);
	}
	
	@Override
	public Type visitTypeArgNamed(Named x) {
		return x.getType().accept(this);
	}
	
	@Override
	public Type visitTypeVariable(Variable x) {
		TypeVar var = x.getTypeVar();
		Type param;
		
		if (var.isBounded()) {
		  param = tf.parameterType(var.getName().toString(), var.getBound().accept(this));
		}
		else {
		  param = tf.parameterType(var.getName().toString());
		}
		
		return param.instantiate(env.getTypeBindings());
	}

	@Override
	public Type visitTypeUser(User x) {
		// TODO add support for parametric types
		java.lang.String name = x.getUser().getName().toString();
		Type type = tf.lookupNamedType(name);
		
		if (type == null) {
			//TODO: This is a hack during transition from double to real
			if(name.equals("real")){
				return tf.doubleType();
			}
			Type tree = tf.lookupNamedTreeType(name);
			
			if (tree == null) {
				throw new RascalTypeError("Use of undeclared type " + x);
			}
			else {
				return tree;
			}
		}
	
		return type;
	}
	
	@Override
	public Type visitTypeAmbiguity(Ambiguity x) {
		throw new RascalBug("Ambiguous type: " + x);
	}
}
