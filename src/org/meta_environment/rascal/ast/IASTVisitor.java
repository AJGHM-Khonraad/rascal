package org.meta_environment.rascal.ast;
import org.eclipse.imp.pdb.facts.ITree;
public interface IASTVisitor
{
  public Body visitBodyToplevels (Body.Toplevels x);
  public Formal visitFormalTypeName (Formal.TypeName x);
  public Formals visitFormalsFormals (Formals.Formals x);
  public Parameters visitParametersVarArgs (Parameters.VarArgs x);
  public Parameters visitParametersDefault (Parameters.Default x);
  public Expression visitExpressionVisit (Expression.Visit x);
  public Expression visitExpressionForall (Expression.Forall x);
  public Expression visitExpressionExists (Expression.Exists x);
  public Expression visitExpressionComprehension (Expression.Comprehension x);
  public Expression visitExpressionQualifiedName (Expression.QualifiedName x);
  public Expression visitExpressionAreaInFileLocation (Expression.
						       AreaInFileLocation x);
  public Expression visitExpressionAreaLocation (Expression.AreaLocation x);
  public Expression visitExpressionFileLocation (Expression.FileLocation x);
  public Expression visitExpressionArea (Expression.Area x);
  public Expression visitExpressionLocation (Expression.Location x);
  public Expression visitExpressionMapTuple (Expression.MapTuple x);
  public Expression visitExpressionTuple (Expression.Tuple x);
  public Expression visitExpressionSet (Expression.Set x);
  public Expression visitExpressionStepRange (Expression.StepRange x);
  public Expression visitExpressionRange (Expression.Range x);
  public Expression visitExpressionList (Expression.List x);
  public Expression visitExpressionCallOrTree (Expression.CallOrTree x);
  public Expression visitExpressionLiteral (Expression.Literal x);
  public Expression visitExpressionOperator (Expression.Operator x);
  public Expression visitExpressionIfThenElse (Expression.IfThenElse x);
  public Expression visitExpressionIfDefined (Expression.IfDefined x);
  public Expression visitExpressionOr (Expression.Or x);
  public Expression visitExpressionAnd (Expression.And x);
  public Expression visitExpressionIn (Expression.In x);
  public Expression visitExpressionNotIn (Expression.NotIn x);
  public Expression visitExpressionNonEquals (Expression.NonEquals x);
  public Expression visitExpressionEquals (Expression.Equals x);
  public Expression visitExpressionGreaterThanOrEq (Expression.
						    GreaterThanOrEq x);
  public Expression visitExpressionGreaterThan (Expression.GreaterThan x);
  public Expression visitExpressionLessThanOrEq (Expression.LessThanOrEq x);
  public Expression visitExpressionLessThan (Expression.LessThan x);
  public Expression visitExpressionNoMatch (Expression.NoMatch x);
  public Expression visitExpressionMatch (Expression.Match x);
  public Expression visitExpressionSubstraction (Expression.Substraction x);
  public Expression visitExpressionAddition (Expression.Addition x);
  public Expression visitExpressionDivision (Expression.Division x);
  public Expression visitExpressionIntersection (Expression.Intersection x);
  public Expression visitExpressionProduct (Expression.Product x);
  public Expression visitExpressionNegation (Expression.Negation x);
  public Expression visitExpressionAnnotation (Expression.Annotation x);
  public Expression visitExpressionTransitiveClosure (Expression.
						      TransitiveClosure x);
  public Expression visitExpressionTransitiveReflexiveClosure (Expression.
							       TransitiveReflexiveClosure
							       x);
  public Expression visitExpressionSubscript (Expression.Subscript x);
  public Expression visitExpressionFieldAccess (Expression.FieldAccess x);
  public Expression visitExpressionFieldUpdate (Expression.FieldUpdate x);
  public Expression visitExpressionClosureCall (Expression.ClosureCall x);
  public Expression visitExpressionClosure (Expression.Closure x);
  public NatCon visitNatCondigits (NatCon.digits x);
  public Literal visitLiteralString (Literal.String x);
  public Literal visitLiteralDouble (Literal.Double x);
  public Literal visitLiteralInteger (Literal.Integer x);
  public Literal visitLiteralBoolean (Literal.Boolean x);
  public Literal visitLiteralSymbol (Literal.Symbol x);
  public Literal visitLiteralRegExp (Literal.RegExp x);
  public Bound visitBoundDefault (Bound.Default x);
  public Bound visitBoundEmpty (Bound.Empty x);
  public Statement visitStatementGlobalDirective (Statement.
						  GlobalDirective x);
  public Statement visitStatementVariableDeclaration (Statement.
						      VariableDeclaration x);
  public Statement visitStatementFunctionDeclaration (Statement.
						      FunctionDeclaration x);
  public Statement visitStatementBlock (Statement.Block x);
  public Statement visitStatementTryFinally (Statement.TryFinally x);
  public Statement visitStatementTry (Statement.Try x);
  public Statement visitStatementFail (Statement.Fail x);
  public Statement visitStatementReturnVoid (Statement.ReturnVoid x);
  public Statement visitStatementContinue (Statement.Continue x);
  public Statement visitStatementBreak (Statement.Break x);
  public Statement visitStatementThrow (Statement.Throw x);
  public Statement visitStatementInsert (Statement.Insert x);
  public Statement visitStatementReturn (Statement.Return x);
  public Statement visitStatementAssert (Statement.Assert x);
  public Statement visitStatementAssignment (Statement.Assignment x);
  public Statement visitStatementVisit (Statement.Visit x);
  public Statement visitStatementExpression (Statement.Expression x);
  public Statement visitStatementSwitch (Statement.Switch x);
  public Statement visitStatementIfThen (Statement.IfThen x);
  public Statement visitStatementIfThenElse (Statement.IfThenElse x);
  public Statement visitStatementDoWhile (Statement.DoWhile x);
  public Statement visitStatementWhile (Statement.While x);
  public Statement visitStatementFor (Statement.For x);
  public Statement visitStatementSolve (Statement.Solve x);
  public Statement visitStatementSolve (Statement.Solve x);
  public Condition visitConditionConjunction (Condition.Conjunction x);
  public Condition visitConditionExpression (Condition.Expression x);
  public Condition visitConditionNoMatch (Condition.NoMatch x);
  public Condition visitConditionMatch (Condition.Match x);
  public Assignable visitAssignableConstructor (Assignable.Constructor x);
  public Assignable visitAssignableTuple (Assignable.Tuple x);
  public Assignable visitAssignableAnnotation (Assignable.Annotation x);
  public Assignable visitAssignableIfDefined (Assignable.IfDefined x);
  public Assignable visitAssignableFieldAccess (Assignable.FieldAccess x);
  public Assignable visitAssignableSubscript (Assignable.Subscript x);
  public Assignable visitAssignableVariable (Assignable.Variable x);
  public Assignment visitAssignmentInteresection (Assignment.Interesection x);
  public Assignment visitAssignmentDivision (Assignment.Division x);
  public Assignment visitAssignmentProduct (Assignment.Product x);
  public Assignment visitAssignmentSubstraction (Assignment.Substraction x);
  public Assignment visitAssignmentAddition (Assignment.Addition x);
  public Assignment visitAssignmentDefault (Assignment.Default x);
  public Catch visitCatchBindingCatch (Catch.BindingCatch x);
  public Catch visitCatchCatch (Catch.Catch x);
  public Declarator visitDeclaratorDefault (Declarator.Default x);
  public LocalVariableDeclaration
    visitLocalVariableDeclarationDynamic (LocalVariableDeclaration.Dynamic x);
  public LocalVariableDeclaration
    visitLocalVariableDeclarationDefault (LocalVariableDeclaration.Default x);
  public StrChar visitStrCharnormal (StrChar.normal x);
  public StrChar visitStrChardecimal (StrChar.decimal x);
  public StrChar visitStrCharbackslash (StrChar.backslash x);
  public StrChar visitStrCharquote (StrChar.quote x);
  public StrChar visitStrChartab (StrChar.tab x);
  public StrChar visitStrCharnewline (StrChar.newline x);
  public StrCon visitStrCondefault (StrCon.default x);
  public Visibility visitVisibilityPrivate (Visibility.Private x);
  public Visibility visitVisibilityPublic (Visibility.Public x);
  public Toplevel visitToplevelDefaultVisibility (Toplevel.
						  DefaultVisibility x);
  public Toplevel visitToplevelGivenVisibility (Toplevel.GivenVisibility x);
  public Declaration visitDeclarationTag (Declaration.Tag x);
  public Declaration visitDeclarationAnnotation (Declaration.Annotation x);
  public Declaration visitDeclarationRule (Declaration.Rule x);
  public Declaration visitDeclarationVariable (Declaration.Variable x);
  public Declaration visitDeclarationFunction (Declaration.Function x);
  public Declaration visitDeclarationData (Declaration.Data x);
  public Declaration visitDeclarationType (Declaration.Type x);
  public Declaration visitDeclarationView (Declaration.View x);
  public Alternative visitAlternativeNamedType (Alternative.NamedType x);
  public Variant visitVariantNillaryConstructor (Variant.
						 NillaryConstructor x);
  public Variant visitVariantNAryConstructor (Variant.NAryConstructor x);
  public Variant visitVariantType (Variant.Type x);
  public StandardOperator visitStandardOperatorNotIn (StandardOperator.
						      NotIn x);
  public StandardOperator visitStandardOperatorIn (StandardOperator.In x);
  public StandardOperator visitStandardOperatorNot (StandardOperator.Not x);
  public StandardOperator visitStandardOperatorOr (StandardOperator.Or x);
  public StandardOperator visitStandardOperatorAnd (StandardOperator.And x);
  public StandardOperator
    visitStandardOperatorGreaterThanOrEq (StandardOperator.GreaterThanOrEq x);
  public StandardOperator visitStandardOperatorGreaterThan (StandardOperator.
							    GreaterThan x);
  public StandardOperator visitStandardOperatorLessThanOrEq (StandardOperator.
							     LessThanOrEq x);
  public StandardOperator visitStandardOperatorLessThan (StandardOperator.
							 LessThan x);
  public StandardOperator visitStandardOperatorNotEquals (StandardOperator.
							  NotEquals x);
  public StandardOperator visitStandardOperatorEquals (StandardOperator.
						       Equals x);
  public StandardOperator visitStandardOperatorIntersection (StandardOperator.
							     Intersection x);
  public StandardOperator visitStandardOperatorDivision (StandardOperator.
							 Division x);
  public StandardOperator visitStandardOperatorProduct (StandardOperator.
							Product x);
  public StandardOperator visitStandardOperatorSubstraction (StandardOperator.
							     Substraction x);
  public StandardOperator visitStandardOperatorAddition (StandardOperator.
							 Addition x);
  public FunctionName visitFunctionNameOperator (FunctionName.Operator x);
  public FunctionName visitFunctionNameName (FunctionName.Name x);
  public FunctionModifier visitFunctionModifierJava (FunctionModifier.Java x);
  public FunctionModifiers visitFunctionModifiersList (FunctionModifiers.
						       List x);
  public Signature visitSignatureWithThrows (Signature.WithThrows x);
  public Signature visitSignatureNoThrows (Signature.NoThrows x);
  public FunctionDeclaration
    visitFunctionDeclarationAbstract (FunctionDeclaration.Abstract x);
  public FunctionDeclaration
    visitFunctionDeclarationDefault (FunctionDeclaration.Default x);
  public FunctionBody visitFunctionBodyDefault (FunctionBody.Default x);
  public Variable visitVariableGivenInitialization (Variable.
						    GivenInitialization x);
  public Kind visitKindAll (Kind.All x);
  public Kind visitKindTag (Kind.Tag x);
  public Kind visitKindAnno (Kind.Anno x);
  public Kind visitKindType (Kind.Type x);
  public Kind visitKindView (Kind.View x);
  public Kind visitKindData (Kind.Data x);
  public Kind visitKindVariable (Kind.Variable x);
  public Kind visitKindFunction (Kind.Function x);
  public Kind visitKindModule (Kind.Module x);
  public BasicType visitBasicTypeLoc (BasicType.Loc x);
  public BasicType visitBasicTypeVoid (BasicType.Void x);
  public BasicType visitBasicTypeTerm (BasicType.Term x);
  public BasicType visitBasicTypeValue (BasicType.Value x);
  public BasicType visitBasicTypeString (BasicType.String x);
  public BasicType visitBasicTypeDouble (BasicType.Double x);
  public BasicType visitBasicTypeInt (BasicType.Int x);
  public BasicType visitBasicTypeBool (BasicType.Bool x);
  public TypeArg visitTypeArgNamed (TypeArg.Named x);
  public TypeArg visitTypeArgDefault (TypeArg.Default x);
  public StructuredType visitStructuredTypeTuple (StructuredType.Tuple x);
  public StructuredType visitStructuredTypeRelation (StructuredType.
						     Relation x);
  public StructuredType visitStructuredTypeMap (StructuredType.Map x);
  public StructuredType visitStructuredTypeSet (StructuredType.Set x);
  public StructuredType visitStructuredTypeList (StructuredType.List x);
  public FunctionType visitFunctionTypeTypeArguments (FunctionType.
						      TypeArguments x);
  public TypeVar visitTypeVarBounded (TypeVar.Bounded x);
  public TypeVar visitTypeVarFree (TypeVar.Free x);
  public UserType visitUserTypeParametric (UserType.Parametric x);
  public UserType visitUserTypeName (UserType.Name x);
  public DataTypeSelector visitDataTypeSelectorSelector (DataTypeSelector.
							 Selector x);
  public Type visitTypeSelector (Type.Selector x);
  public Type visitTypeSymbol (Type.Symbol x);
  public Type visitTypeUser (Type.User x);
  public Type visitTypeVariable (Type.Variable x);
  public Type visitTypeFunction (Type.Function x);
  public Type visitTypeStructured (Type.Structured x);
  public Type visitTypeBasic (Type.Basic x);
  public Sort visitSortmore - chars (Sort.more - chars x);
  public Sort visitSortone - char (Sort.one - char x);
  public Symbol visitSymbolCaseInsensitiveLiteral (Symbol.
						   CaseInsensitiveLiteral x);
  public Symbol visitSymbolLiteral (Symbol.Literal x);
  public Symbol visitSymbolLiftedSymbol (Symbol.LiftedSymbol x);
  public Symbol visitSymbolCharacterClass (Symbol.CharacterClass x);
  public Symbol visitSymbolAlternative (Symbol.Alternative x);
  public Symbol visitSymbolIterStarSep (Symbol.IterStarSep x);
  public Symbol visitSymbolIterSep (Symbol.IterSep x);
  public Symbol visitSymbolIterStar (Symbol.IterStar x);
  public Symbol visitSymbolIter (Symbol.Iter x);
  public Symbol visitSymbolOptional (Symbol.Optional x);
  public Symbol visitSymbolSequence (Symbol.Sequence x);
  public Symbol visitSymbolEmpty (Symbol.Empty x);
  public Symbol visitSymbolParameterizedSort (Symbol.ParameterizedSort x);
  public Symbol visitSymbolSort (Symbol.Sort x);
  public SingleQuotedStrChar
    visitSingleQuotedStrCharnormal (SingleQuotedStrChar.normal x);
  public SingleQuotedStrChar
    visitSingleQuotedStrChardecimal (SingleQuotedStrChar.decimal x);
  public SingleQuotedStrChar
    visitSingleQuotedStrCharbackslash (SingleQuotedStrChar.backslash x);
  public SingleQuotedStrChar
    visitSingleQuotedStrCharquote (SingleQuotedStrChar.quote x);
  public SingleQuotedStrChar visitSingleQuotedStrChartab (SingleQuotedStrChar.
							  tab x);
  public SingleQuotedStrChar
    visitSingleQuotedStrCharnewline (SingleQuotedStrChar.newline x);
  public SingleQuotedStrCon
    visitSingleQuotedStrCondefault (SingleQuotedStrCon.default x);
  public CharRange visitCharRangeRange (CharRange.Range x);
  public CharRange visitCharRangeCharacter (CharRange.Character x);
  public CharRanges visitCharRangesConcatenate (CharRanges.Concatenate x);
  public CharRanges visitCharRangesRange (CharRanges.Range x);
  public OptCharRanges visitOptCharRangesPresent (OptCharRanges.Present x);
  public OptCharRanges visitOptCharRangesAbsent (OptCharRanges.Absent x);
  public CharClass visitCharClassUnion (CharClass.Union x);
  public CharClass visitCharClassIntersection (CharClass.Intersection x);
  public CharClass visitCharClassDifference (CharClass.Difference x);
  public CharClass visitCharClassComplement (CharClass.Complement x);
  public CharClass visitCharClassSimpleCharclass (CharClass.
						  SimpleCharclass x);
  public NumChar visitNumCharDigits (NumChar.Digits x);
  public ShortChar visitShortCharEscaped (ShortChar.Escaped x);
  public ShortChar visitShortCharRegular (ShortChar.Regular x);
  public Character visitCharacterLabelStart (Character.LabelStart x);
  public Character visitCharacterBottom (Character.Bottom x);
  public Character visitCharacterEOF (Character.EOF x);
  public Character visitCharacterTop (Character.Top x);
  public Character visitCharacterShort (Character.Short x);
  public Character visitCharacterNumeric (Character.Numeric x);
  public Module visitModuleModule (Module.Module x);
  public ModuleWord visitModuleWordWord (ModuleWord.Word x);
  public ModuleName visitModuleNamePath (ModuleName.Path x);
  public ModuleName visitModuleNameRoot (ModuleName.Root x);
  public ModuleName visitModuleNameLeaf (ModuleName.Leaf x);
  public ModuleActuals visitModuleActualsActuals (ModuleActuals.Actuals x);
  public ImportedModule visitImportedModuleDefault (ImportedModule.Default x);
  public ImportedModule visitImportedModuleRenamings (ImportedModule.
						      Renamings x);
  public ImportedModule visitImportedModuleActuals (ImportedModule.Actuals x);
  public ImportedModule visitImportedModuleActualsRenaming (ImportedModule.
							    ActualsRenaming
							    x);
  public Renaming visitRenamingRenaming (Renaming.Renaming x);
  public Renamings visitRenamingsRenamings (Renamings.Renamings x);
  public Import visitImportExtend (Import.Extend x);
  public Import visitImportImport (Import.Import x);
  public ModuleParameters
    visitModuleParametersModuleParameters (ModuleParameters.
					   ModuleParameters x);
  public Header visitHeaderParameters (Header.Parameters x);
  public Header visitHeaderDefault (Header.Default x);
  public QualifiedName visitQualifiedNameDefault (QualifiedName.Default x);
  public Area visitAreaArea (Area.Area x);
  public Tag visitTagDefault (Tag.Default x);
  public Tags visitTagsDefault (Tags.Default x);
  public Pattern visitPatternTypedVariable (Pattern.TypedVariable x);
  public ValueProducer visitValueProducerGivenStrategy (ValueProducer.
							GivenStrategy x);
  public ValueProducer visitValueProducerDefaultStrategy (ValueProducer.
							  DefaultStrategy x);
  public Generator visitGeneratorProducer (Generator.Producer x);
  public Generator visitGeneratorExpression (Generator.Expression x);
  public Strategy visitStrategyInnermost (Strategy.Innermost x);
  public Strategy visitStrategyOutermost (Strategy.Outermost x);
  public Strategy visitStrategyBottomUpBreak (Strategy.BottomUpBreak x);
  public Strategy visitStrategyBottomUp (Strategy.BottomUp x);
  public Strategy visitStrategyTopDownBreak (Strategy.TopDownBreak x);
  public Strategy visitStrategyTopDown (Strategy.TopDown x);
  public Comprehension visitComprehensionList (Comprehension.List x);
  public Comprehension visitComprehensionSet (Comprehension.Set x);
  public Match visitMatchArbitrary (Match.Arbitrary x);
  public Match visitMatchReplacing (Match.Replacing x);
  public Rule visitRuleNoGuard (Rule.NoGuard x);
  public Rule visitRuleWithGuard (Rule.WithGuard x);
  public Case visitCaseDefault (Case.Default x);
  public Case visitCaseRule (Case.Rule x);
  public Visit visitVisitGivenStrategy (Visit.GivenStrategy x);
  public Visit visitVisitDefaultStrategy (Visit.DefaultStrategy x);
  public IntegerLiteral
    visitIntegerLiteralOctalIntegerLiteral (IntegerLiteral.
					    OctalIntegerLiteral x);
  public IntegerLiteral visitIntegerLiteralHexIntegerLiteral (IntegerLiteral.
							      HexIntegerLiteral
							      x);
  public IntegerLiteral
    visitIntegerLiteralDecimalIntegerLiteral (IntegerLiteral.
					      DecimalIntegerLiteral x);
  public LongLiteral visitLongLiteralOctalLongLiteral (LongLiteral.
						       OctalLongLiteral x);
  public LongLiteral visitLongLiteralHexLongLiteral (LongLiteral.
						     HexLongLiteral x);
  public LongLiteral visitLongLiteralDecimalLongLiteral (LongLiteral.
							 DecimalLongLiteral
							 x);
}
