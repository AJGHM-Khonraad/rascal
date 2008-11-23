package org.meta_environment.rascal.interpreter;

import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.meta_environment.rascal.ast.NullASTVisitor;
import org.meta_environment.rascal.ast.TypeArg;
import org.meta_environment.rascal.ast.BasicType.Bool;
import org.meta_environment.rascal.ast.BasicType.Double;
import org.meta_environment.rascal.ast.BasicType.Int;
import org.meta_environment.rascal.ast.BasicType.Loc;
import org.meta_environment.rascal.ast.BasicType.String;
import org.meta_environment.rascal.ast.BasicType.Tree;
import org.meta_environment.rascal.ast.BasicType.Value;
import org.meta_environment.rascal.ast.BasicType.Void;
import org.meta_environment.rascal.ast.StructuredType.List;
import org.meta_environment.rascal.ast.StructuredType.Map;
import org.meta_environment.rascal.ast.StructuredType.Relation;
import org.meta_environment.rascal.ast.StructuredType.Set;
import org.meta_environment.rascal.ast.StructuredType.Tuple;
import org.meta_environment.rascal.ast.Type.Basic;
import org.meta_environment.rascal.ast.Type.Function;
import org.meta_environment.rascal.ast.Type.Structured;
import org.meta_environment.rascal.ast.TypeArg.Default;
import org.meta_environment.rascal.ast.TypeArg.Named;

public class TypeEvaluator extends NullASTVisitor<Type> {
	private TypeFactory tf = TypeFactory.getInstance();

	@Override
	public Type visitTypeBasic(Basic x) {
		return x.getBasic().accept(this);
	}
	
	@Override
	public Type visitTypeFunction(Function x) {
		return x.getFunction().getType().accept(this);
	}
	
	@Override
	public Type visitBasicTypeBool(Bool x) {
		return tf.boolType();
	}
	
	@Override
	public Type visitBasicTypeDouble(Double x) {
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
		java.util.List<TypeArg> args = x.getArguments();
		Type[] fieldTypes = new Type[args.size()];
		
		int i = 0;
		for (TypeArg arg : args) {
			fieldTypes[i++] = arg.getType().accept(this);
		}
		
		return tf.relType(fieldTypes);
	}
	
	@Override
	public Type visitStructuredTypeSet(Set x) {
		return tf.setType(x.getTypeArg().accept(this));
	}
	
	@Override
	public Type visitStructuredTypeTuple(Tuple x) {
		java.util.List<TypeArg> args = x.getArguments();
		Type[] fieldTypes = new Type[args.size()];
		
		int i = 0;
		for (TypeArg arg : args) {
			fieldTypes[i++] = arg.getType().accept(this);
		}
		
		return tf.tupleType(fieldTypes);
	}
	
	@Override
	public Type visitTypeArgDefault(Default x) {
		return x.getType().accept(this);
	}
	
	@Override
	public Type visitTypeArgNamed(Named x) {
		return x.getType().accept(this);
	}
}
