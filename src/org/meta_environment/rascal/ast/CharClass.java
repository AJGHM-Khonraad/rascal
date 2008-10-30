package org.meta_environment.rascal.ast; 
import org.eclipse.imp.pdb.facts.ITree; 
public abstract class CharClass extends AbstractAST { 
public class SimpleCharclass extends CharClass {
/* "[" optionalCharRanges:OptCharRanges "]" -> CharClass {cons("SimpleCharclass")} */
	private SimpleCharclass() { }
	/*package*/ SimpleCharclass(ITree tree, OptCharRanges optionalCharRanges) {
		this.tree = tree;
		this.optionalCharRanges = optionalCharRanges;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitSimpleCharclassCharClass(this);
	}
private OptCharRanges optionalCharRanges;
	public OptCharRanges getoptionalCharRanges() { return optionalCharRanges; }
	private void privateSetoptionalCharRanges(OptCharRanges x) { this.optionalCharRanges = x; }
	public SimpleCharclass setoptionalCharRanges(OptCharRanges x) { 
		SimpleCharclass z = new SimpleCharclass();
 		z.privateSetoptionalCharRanges(x);
		return z;
	}	
}
public class Ambiguity extends CharClass {
  private final List<CharClass> alternatives;
  public Ambiguity(List<CharClass> alternatives) {
	this.alternatives = Collections.immutableList(alternatives);
  }
  public List<CharClass> getAlternatives() {
	return alternatives;
  }
} 
public class Complement extends CharClass {
/* "~" charClass:CharClass -> CharClass {cons("Complement")} */
	private Complement() { }
	/*package*/ Complement(ITree tree, CharClass charClass) {
		this.tree = tree;
		this.charClass = charClass;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitComplementCharClass(this);
	}
private CharClass charClass;
	public CharClass getcharClass() { return charClass; }
	private void privateSetcharClass(CharClass x) { this.charClass = x; }
	public Complement setcharClass(CharClass x) { 
		Complement z = new Complement();
 		z.privateSetcharClass(x);
		return z;
	}	
} 
public class Difference extends CharClass {
/* lhs:CharClass "/" rhs:CharClass -> CharClass {cons("Difference"), left,memo} */
	private Difference() { }
	/*package*/ Difference(ITree tree, CharClass lhs, CharClass rhs) {
		this.tree = tree;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitDifferenceCharClass(this);
	}
private CharClass lhs;
	public CharClass getlhs() { return lhs; }
	private void privateSetlhs(CharClass x) { this.lhs = x; }
	public Difference setlhs(CharClass x) { 
		Difference z = new Difference();
 		z.privateSetlhs(x);
		return z;
	}
	private CharClass rhs;
	public CharClass getrhs() { return rhs; }
	private void privateSetrhs(CharClass x) { this.rhs = x; }
	public Difference setrhs(CharClass x) { 
		Difference z = new Difference();
 		z.privateSetrhs(x);
		return z;
	}	
} 
public class Intersection extends CharClass {
/* lhs:CharClass "/\\" rhs:CharClass -> CharClass {cons("Intersection"), left,memo} */
	private Intersection() { }
	/*package*/ Intersection(ITree tree, CharClass lhs, CharClass rhs) {
		this.tree = tree;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitIntersectionCharClass(this);
	}
private CharClass lhs;
	public CharClass getlhs() { return lhs; }
	private void privateSetlhs(CharClass x) { this.lhs = x; }
	public Intersection setlhs(CharClass x) { 
		Intersection z = new Intersection();
 		z.privateSetlhs(x);
		return z;
	}
	private CharClass rhs;
	public CharClass getrhs() { return rhs; }
	private void privateSetrhs(CharClass x) { this.rhs = x; }
	public Intersection setrhs(CharClass x) { 
		Intersection z = new Intersection();
 		z.privateSetrhs(x);
		return z;
	}	
} 
public class Union extends CharClass {
/* lhs:CharClass "\\/" rhs:CharClass -> CharClass {cons("Union"), left} */
	private Union() { }
	/*package*/ Union(ITree tree, CharClass lhs, CharClass rhs) {
		this.tree = tree;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	public IVisitable accept(IASTVisitor visitor) {
		return visitor.visitUnionCharClass(this);
	}
private CharClass lhs;
	public CharClass getlhs() { return lhs; }
	private void privateSetlhs(CharClass x) { this.lhs = x; }
	public Union setlhs(CharClass x) { 
		Union z = new Union();
 		z.privateSetlhs(x);
		return z;
	}
	private CharClass rhs;
	public CharClass getrhs() { return rhs; }
	private void privateSetrhs(CharClass x) { this.rhs = x; }
	public Union setrhs(CharClass x) { 
		Union z = new Union();
 		z.privateSetrhs(x);
		return z;
	}	
} 
public class Lexical extends CharClass {
	/* "(" CharClass ")" -> CharClass {bracket, avoid} */
} 
public class Lexical extends CharClass {
	/* "~" CharClass -> CharClass  */
} 
public class Lexical extends CharClass {
	/* CharClass "/" CharClass -> CharClass  */
} 
public class Lexical extends CharClass {
	/* CharClass "/\\" CharClass -> CharClass  */
} 
public class Lexical extends CharClass {
	/* CharClass "\\/" CharClass -> CharClass  */
}
}