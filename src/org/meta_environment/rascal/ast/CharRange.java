package org.meta_environment.rascal.ast;

import org.eclipse.imp.pdb.facts.ITree;

public abstract class CharRange extends AbstractAST {
	static public class Ambiguity extends CharRange {
		private final java.util.List<org.meta_environment.rascal.ast.CharRange> alternatives;

		public Ambiguity(
				java.util.List<org.meta_environment.rascal.ast.CharRange> alternatives) {
			this.alternatives = java.util.Collections
					.unmodifiableList(alternatives);
		}

		public java.util.List<org.meta_environment.rascal.ast.CharRange> getAlternatives() {
			return alternatives;
		}
	}

	static public class Character extends CharRange {
		private org.meta_environment.rascal.ast.Character character;

		/* character:Character -> CharRange {cons("Character")} */
		private Character() {
		}

		/* package */Character(ITree tree,
				org.meta_environment.rascal.ast.Character character) {
			this.tree = tree;
			this.character = character;
		}

		private void $setCharacter(org.meta_environment.rascal.ast.Character x) {
			this.character = x;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitCharRangeCharacter(this);
		}

		public org.meta_environment.rascal.ast.Character getCharacter() {
			return character;
		}

		public Character setCharacter(
				org.meta_environment.rascal.ast.Character x) {
			Character z = new Character();
			z.$setCharacter(x);
			return z;
		}
	}

	static public class Range extends CharRange {
		private org.meta_environment.rascal.ast.Character end;
		private org.meta_environment.rascal.ast.Character start;

		/* start:Character "-" end:Character -> CharRange {cons("Range")} */
		private Range() {
		}

		/* package */Range(ITree tree,
				org.meta_environment.rascal.ast.Character start,
				org.meta_environment.rascal.ast.Character end) {
			this.tree = tree;
			this.start = start;
			this.end = end;
		}

		private void $setEnd(org.meta_environment.rascal.ast.Character x) {
			this.end = x;
		}

		private void $setStart(org.meta_environment.rascal.ast.Character x) {
			this.start = x;
		}

		public IVisitable accept(IASTVisitor visitor) {
			return visitor.visitCharRangeRange(this);
		}

		public org.meta_environment.rascal.ast.Character getEnd() {
			return end;
		}

		public org.meta_environment.rascal.ast.Character getStart() {
			return start;
		}

		public Range setEnd(org.meta_environment.rascal.ast.Character x) {
			Range z = new Range();
			z.$setEnd(x);
			return z;
		}

		public Range setStart(org.meta_environment.rascal.ast.Character x) {
			Range z = new Range();
			z.$setStart(x);
			return z;
		}
	}
}
