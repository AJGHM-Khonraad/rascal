package org.rascalmpl.values.xml;

import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.type.TypeStore;

public class Factory {
	public static TypeStore xml = new TypeStore(
			org.rascalmpl.values.errors.Factory.getStore(), 
			org.rascalmpl.values.locations.Factory.getStore());
	
	private static TypeFactory tf = TypeFactory.getInstance();
	
	public static final Type Node = tf.abstractDataType(xml, "Node");
	public static final Type Namespace = tf.abstractDataType(xml, "Namespace");

	public static final Type Namespace_namespace = tf.constructor(xml, Namespace, "namespace", 
			tf.stringType(), "prefix",
			tf.stringType(), "uri");
	public static final Type Namespace_none = tf.constructor(xml, Namespace, "none"); 
	
	
	public static final Type Node_document = tf.constructor(xml, Node, "document", 
			Node, "root");

	public static final Type Node_attribute = tf.constructor(xml, Node, "attribute", 
			Namespace, "namespace",
			tf.stringType(), "name",
			tf.stringType(), "text");

	public static final Type Node_element = tf.constructor(xml, Node, "element", 
			Namespace, "namespace",
			tf.stringType(), "name",
			tf.listType(Node), "children");

	
	public static final Type Node_charData = tf.constructor(xml, Node, "charData",
			tf.stringType(), "text");
	public static final Type Node_cdata = tf.constructor(xml, Node, "cdata",
			tf.stringType(), "text");
	public static final Type Node_comment = tf.constructor(xml, Node, "comment",
			tf.stringType(), "text");
	public static final Type Node_pi = tf.constructor(xml, Node, "pi",
			tf.stringType(), "target",
			tf.stringType(), "text");
	
	public static final Type Node_entityRef = tf.constructor(xml, Node, "entityRef",
			tf.stringType(), "name");

	public static final Type Node_charRef = tf.constructor(xml, Node, "charRef",
			tf.integerType(), "code");

	private static final class InstanceHolder {
		public final static Factory factory = new Factory();
	}
	  
	public static Factory getInstance() {
		return InstanceHolder.factory;
	}
	
	private Factory() {
	}
	
	public static TypeStore getStore() {
		return xml;
	}
}
