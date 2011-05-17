package org.rascalmpl.parser.gtd.result.uptr;

import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.IListWriter;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.rascalmpl.parser.gtd.result.LiteralNode;
import org.rascalmpl.parser.gtd.util.PointerKeyedHashMap;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

public class LiteralNodeConverter{
	private final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
	
	private final PointerKeyedHashMap<LiteralNode, IConstructor> cache;
	
	public LiteralNodeConverter(){
		super();
		
		cache = new PointerKeyedHashMap<LiteralNode, IConstructor>();
	}
	
	public IConstructor convertToUPTR(LiteralNode node){
		IConstructor result = cache.get(node);
		if(result != null) return result;
		
		IConstructor production = node.getProduction();
		char[] content = node.getContent();
		
		int numberOfCharacters = content.length;
		
		IListWriter listWriter = VF.listWriter(Factory.Tree);
		for(int i = 0; i < numberOfCharacters; ++i){
			listWriter.append(VF.constructor(Factory.Tree_Char, VF.integer(content[i])));
		}
		
		result = VF.constructor(Factory.Tree_Appl, production, listWriter.done());
		
		cache.putUnsafe(node, result);
		
		return result;
	}
}
