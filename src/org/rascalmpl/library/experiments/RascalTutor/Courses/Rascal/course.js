/* Generated code for course Rascal, generated $2011-09-06T18:47:08.871+0200 */

var baseConcepts = new Array("!","!:=","!<<","!=","\"","#","%","&","&&","&;","\'","(","()","();",")","),",");",")]","*","*=","+","+=",",","-","-=",".","..","...\"","...;","...]","/","/=",":",":=",";","<","<-","<:","<<","<=","<==>","=","==","==>","=>",">",">=",">>","?","?=","@","Abstract","Addition","AlgebraicDataType","Alias","All","And","Angles","Annotation","Any","Append","Assert","Assignment","Benchmark","Block","Boolean","BoxModel","Call","CarthesianProduct","Class","Color","ColorModel","ColoredTrees","Composition","Comprehension","Comprehensions","Concatenation","Concepts","Concrete","ConcreteSyntax","Concurrency","Conditional","Constructor","DateTime","Declarations","Difference","Disambiguation","Division","Do","EASY","Enumerator","Equal","EquationSolving","Equivalence","Examples","Exception","Expressions","FProperties","FProperty","Factorial","Fail","FieldAssignment","FieldProjection","FieldSelection","Figure","Figures","Filter","For","Forensics","Function","Graph","GreaterThan","GreaterThanOrEqual","Hello","Help","IDE","IDEConstruction","IMP","IO","If","IfDefinedElse","Implication","Import","Insert","Installing","InstallingAndRunning","Integer","InteractionModel","Intersection","IntroCaseDistinction","IntroComprehensions","IntroControlStructures","IntroDatatypes","IntroEnumerators","IntroFunctions","IntroImmutableValues","IntroPatternMatching","IntroRewriteRules","IntroStaticTyping","IntroSyntaxDefinitionAndParsing","IntroVisiting","IsDefined","JDT","Java","Join","L","LGraph","LabeledGraph","LessThan","LessThanOrEqual","Libraries","List","Location","Map","Match","Message","ModelDrivenEngineering","Module","Motivation","Multiplication","Name","Negation","NoMatch","Node","NotEqual","Number","Operators","Or","ParseTrees","PatternWithAction","Patterns","PriorityQueue","Product","Program","Properties","RSF","Range","Rascal","Real","ReflexiveTransitiveClosure","Regular","ReifiedTypes","Relation","Remainder","Renovation","Replacement","Resource","Resources","Return","Rule","Running","Scripting","Security","Selection","Set","Solve","StatementAsExpression","Statements","StaticTyping","Strategy","StrictSubList","StrictSubMap","StrictSubSet","StrictSuperList","StrictSuperMap","StrictSuperSet","String","SubList","SubMap","SubSet","Subscription","Subtraction","SuperList","SuperMap","SuperSet","Switch","Symbols","SyntaxDefinition","T","Tag","Test","Throw","TransitiveClosure","Tree","TryCatch","Tuple","TypeConstraints","TypeParameters","Union","Value","ValueIO","Values","Variable","Vis","Visit","Void","While","[","[&","[@","\\","]","];","]]","^","abs","anchor","anno","appendToFile","arbBool","arbInt","arbReal","arity","at","b","bool","bottom","box","button","carrier","carrierR","carrierX","case","catch","center","charAt","checkbox","choice","color","colorNames","colorScale","colorSteps","complement","computeFigure","computedColor","computedInt","computedNum","computedReal","computedStr","contents","cpuTime","data","datetime","default","delete","dependencies","domain","domainR","domainX","edge","ellipse","else","endsWith","error","eval","evalType","exists","extractClass","extractFactsTransitive","extractMinimum","extractProject","extractResource","extractResources","false","file","finally","findMinimum","folder","from","fromInt","gap","getAnnotations","getName","getOneFrom","getProject","gray","grid","hanchor","hcat","hcenter","head","height","hgap","hint","hvcat","id","ident","in","index","info","insertAt","insertElement","int","interpolateColor","invert","invertUnique","isDirectory","isEmpty","isFile","isOnBuildPath","keyword","label","lastModified","layout","left","lexical","lineColor","lineWidth","listEntries","loc","lub","makeNode","mapper","matchLocations","max","min","mkDirectory","mkPriorityQueue","mouseOver","msg","n","notin","num","o","onClick","outline","pad","palette","parse","permutations","power","power1","predecessors","print","println","project","projects","r","range","rangeR","rangeX","range_2","reach","reachR","reachX","readBinaryValueFile","readFile","readFileBytes","readFileLines","readRSF","readTextValueFile","readTextValueString","reducer","references","rel","reverse","rgb","right","root","s","setAnnotations","shapeClosed","shapeConnected","shapeCurved","shell","shortestPathPair","size","slice","sort","space","start","startsWith","str","substring","successors","syntax","systemTime","t","tail","takeOneFrom","textAngle","textarea","textfield","throws","to","toInt","toList","toLowerCase","toMap","toMapUnique","toReal","toRel","toSet","toString","toUpperCase","top","treemap","true","try","type","unparse","userTime","vanchor","vcenter","vgap","warning","width","writeBinaryValueFile","writeFile","writeTextValueFile","{","|","||","}","}+");

var conceptNames = new Array("Rascal","Rascal/Concepts","Rascal/Concepts/IntroCaseDistinction","Rascal/Concepts/IntroComprehensions","Rascal/Concepts/IntroControlStructures","Rascal/Concepts/IntroDatatypes","Rascal/Concepts/IntroEnumerators","Rascal/Concepts/IntroEnumerators/EquationSolving","Rascal/Concepts/IntroFunctions","Rascal/Concepts/IntroImmutableValues","Rascal/Concepts/IntroPatternMatching","Rascal/Concepts/IntroRewriteRules","Rascal/Concepts/IntroStaticTyping","Rascal/Concepts/IntroSyntaxDefinitionAndParsing","Rascal/Concepts/IntroVisiting","Rascal/Declarations","Rascal/Declarations/AlgebraicDataType","Rascal/Declarations/AlgebraicDataType/Exception","Rascal/Declarations/Alias","Rascal/Declarations/Annotation","Rascal/Declarations/Annotation/Replacement","Rascal/Declarations/Annotation/Selection","Rascal/Declarations/Function","Rascal/Declarations/Import","Rascal/Declarations/Module","Rascal/Declarations/Program","Rascal/Declarations/Rule","Rascal/Declarations/StaticTyping","Rascal/Declarations/StaticTyping/ReifiedTypes","Rascal/Declarations/StaticTyping/TypeConstraints","Rascal/Declarations/StaticTyping/TypeParameters","Rascal/Declarations/SyntaxDefinition","Rascal/Declarations/SyntaxDefinition/ConcreteSyntax","Rascal/Declarations/SyntaxDefinition/Disambiguation","Rascal/Declarations/SyntaxDefinition/ParseTrees","Rascal/Declarations/SyntaxDefinition/Symbols","Rascal/Declarations/Tag","Rascal/Declarations/Variable","Rascal/EASY","Rascal/EASY/Concurrency","Rascal/EASY/Forensics","Rascal/EASY/ModelDrivenEngineering","Rascal/EASY/Renovation","Rascal/EASY/Security","Rascal/Examples","Rascal/Examples/ColoredTrees","Rascal/Examples/Factorial","Rascal/Examples/Hello","Rascal/Expressions","Rascal/Expressions/Call","Rascal/Expressions/Comprehensions","Rascal/Expressions/Comprehensions/Enumerator","Rascal/Expressions/Comprehensions/Filter","Rascal/Expressions/Operators","Rascal/Expressions/Operators/FieldAssignment","Rascal/Expressions/Operators/FieldProjection","Rascal/Expressions/Patterns","Rascal/Expressions/Patterns/Abstract","Rascal/Expressions/Patterns/Concrete","Rascal/Expressions/Patterns/PatternWithAction","Rascal/Expressions/Patterns/Regular","Rascal/Expressions/StatementAsExpression","Rascal/Expressions/Values","Rascal/Expressions/Values/Boolean","Rascal/Expressions/Values/Boolean/All","Rascal/Expressions/Values/Boolean/And","Rascal/Expressions/Values/Boolean/Any","Rascal/Expressions/Values/Boolean/Equivalence","Rascal/Expressions/Values/Boolean/IfDefinedElse","Rascal/Expressions/Values/Boolean/Implication","Rascal/Expressions/Values/Boolean/IsDefined","Rascal/Expressions/Values/Boolean/Match","Rascal/Expressions/Values/Boolean/Negation","Rascal/Expressions/Values/Boolean/NoMatch","Rascal/Expressions/Values/Boolean/Or","Rascal/Expressions/Values/Boolean/arbBool","Rascal/Expressions/Values/Boolean/fromInt","Rascal/Expressions/Values/Boolean/toInt","Rascal/Expressions/Values/Boolean/toReal","Rascal/Expressions/Values/Boolean/toString","Rascal/Expressions/Values/Constructor","Rascal/Expressions/Values/DateTime","Rascal/Expressions/Values/DateTime/Equal","Rascal/Expressions/Values/DateTime/FieldSelection","Rascal/Expressions/Values/DateTime/GreaterThan","Rascal/Expressions/Values/DateTime/GreaterThanOrEqual","Rascal/Expressions/Values/DateTime/LessThan","Rascal/Expressions/Values/DateTime/LessThanOrEqual","Rascal/Expressions/Values/DateTime/NotEqual","Rascal/Expressions/Values/Integer","Rascal/Expressions/Values/List","Rascal/Expressions/Values/List/Comprehension","Rascal/Expressions/Values/List/Concatenation","Rascal/Expressions/Values/List/Difference","Rascal/Expressions/Values/List/Equal","Rascal/Expressions/Values/List/Intersection","Rascal/Expressions/Values/List/NotEqual","Rascal/Expressions/Values/List/Product","Rascal/Expressions/Values/List/StrictSubList","Rascal/Expressions/Values/List/StrictSuperList","Rascal/Expressions/Values/List/SubList","Rascal/Expressions/Values/List/Subscription","Rascal/Expressions/Values/List/SuperList","Rascal/Expressions/Values/List/delete","Rascal/Expressions/Values/List/domain","Rascal/Expressions/Values/List/getOneFrom","Rascal/Expressions/Values/List/head","Rascal/Expressions/Values/List/in","Rascal/Expressions/Values/List/index","Rascal/Expressions/Values/List/insertAt","Rascal/Expressions/Values/List/isEmpty","Rascal/Expressions/Values/List/mapper","Rascal/Expressions/Values/List/max","Rascal/Expressions/Values/List/min","Rascal/Expressions/Values/List/notin","Rascal/Expressions/Values/List/permutations","Rascal/Expressions/Values/List/reducer","Rascal/Expressions/Values/List/reverse","Rascal/Expressions/Values/List/size","Rascal/Expressions/Values/List/slice","Rascal/Expressions/Values/List/sort","Rascal/Expressions/Values/List/tail","Rascal/Expressions/Values/List/takeOneFrom","Rascal/Expressions/Values/List/toMap","Rascal/Expressions/Values/List/toMapUnique","Rascal/Expressions/Values/List/toSet","Rascal/Expressions/Values/List/toString","Rascal/Expressions/Values/Location","Rascal/Expressions/Values/Location/Equal","Rascal/Expressions/Values/Location/FieldSelection","Rascal/Expressions/Values/Location/GreaterThan","Rascal/Expressions/Values/Location/GreaterThanOrEqual","Rascal/Expressions/Values/Location/LessThan","Rascal/Expressions/Values/Location/LessThanOrEqual","Rascal/Expressions/Values/Location/NotEqual","Rascal/Expressions/Values/Map","Rascal/Expressions/Values/Map/Comprehension","Rascal/Expressions/Values/Map/Difference","Rascal/Expressions/Values/Map/Equal","Rascal/Expressions/Values/Map/Intersection","Rascal/Expressions/Values/Map/NotEqual","Rascal/Expressions/Values/Map/StrictSubMap","Rascal/Expressions/Values/Map/StrictSuperMap","Rascal/Expressions/Values/Map/SubMap","Rascal/Expressions/Values/Map/Subscription","Rascal/Expressions/Values/Map/SuperMap","Rascal/Expressions/Values/Map/Union","Rascal/Expressions/Values/Map/domain","Rascal/Expressions/Values/Map/domainR","Rascal/Expressions/Values/Map/domainX","Rascal/Expressions/Values/Map/getOneFrom","Rascal/Expressions/Values/Map/in","Rascal/Expressions/Values/Map/invert","Rascal/Expressions/Values/Map/invertUnique","Rascal/Expressions/Values/Map/isEmpty","Rascal/Expressions/Values/Map/mapper","Rascal/Expressions/Values/Map/notin","Rascal/Expressions/Values/Map/range","Rascal/Expressions/Values/Map/rangeR","Rascal/Expressions/Values/Map/rangeX","Rascal/Expressions/Values/Map/size","Rascal/Expressions/Values/Map/toList","Rascal/Expressions/Values/Map/toRel","Rascal/Expressions/Values/Map/toString","Rascal/Expressions/Values/Node","Rascal/Expressions/Values/Node/Equal","Rascal/Expressions/Values/Node/GreaterThan","Rascal/Expressions/Values/Node/GreaterThanOrEqual","Rascal/Expressions/Values/Node/LessThan","Rascal/Expressions/Values/Node/LessThanOrEqual","Rascal/Expressions/Values/Node/NotEqual","Rascal/Expressions/Values/Node/arity","Rascal/Expressions/Values/Node/getAnnotations","Rascal/Expressions/Values/Node/getName","Rascal/Expressions/Values/Node/makeNode","Rascal/Expressions/Values/Node/setAnnotations","Rascal/Expressions/Values/Number","Rascal/Expressions/Values/Number/Addition","Rascal/Expressions/Values/Number/Conditional","Rascal/Expressions/Values/Number/Division","Rascal/Expressions/Values/Number/Equal","Rascal/Expressions/Values/Number/GreaterThan","Rascal/Expressions/Values/Number/GreaterThanOrEqual","Rascal/Expressions/Values/Number/LessThan","Rascal/Expressions/Values/Number/LessThanOrEqual","Rascal/Expressions/Values/Number/Multiplication","Rascal/Expressions/Values/Number/Negation","Rascal/Expressions/Values/Number/NotEqual","Rascal/Expressions/Values/Number/Remainder","Rascal/Expressions/Values/Number/Subtraction","Rascal/Expressions/Values/Number/abs","Rascal/Expressions/Values/Number/arbInt","Rascal/Expressions/Values/Number/arbReal","Rascal/Expressions/Values/Number/max","Rascal/Expressions/Values/Number/min","Rascal/Expressions/Values/Number/toInt","Rascal/Expressions/Values/Number/toReal","Rascal/Expressions/Values/Number/toString","Rascal/Expressions/Values/Range","Rascal/Expressions/Values/Real","Rascal/Expressions/Values/Relation","Rascal/Expressions/Values/Relation/CarthesianProduct","Rascal/Expressions/Values/Relation/Composition","Rascal/Expressions/Values/Relation/FieldSelection","Rascal/Expressions/Values/Relation/Join","Rascal/Expressions/Values/Relation/ReflexiveTransitiveClosure","Rascal/Expressions/Values/Relation/Subscription","Rascal/Expressions/Values/Relation/TransitiveClosure","Rascal/Expressions/Values/Relation/carrier","Rascal/Expressions/Values/Relation/carrierR","Rascal/Expressions/Values/Relation/carrierX","Rascal/Expressions/Values/Relation/complement","Rascal/Expressions/Values/Relation/domain","Rascal/Expressions/Values/Relation/domainR","Rascal/Expressions/Values/Relation/domainX","Rascal/Expressions/Values/Relation/ident","Rascal/Expressions/Values/Relation/invert","Rascal/Expressions/Values/Relation/range","Rascal/Expressions/Values/Relation/rangeR","Rascal/Expressions/Values/Relation/rangeX","Rascal/Expressions/Values/Set","Rascal/Expressions/Values/Set/Comprehension","Rascal/Expressions/Values/Set/Difference","Rascal/Expressions/Values/Set/Equal","Rascal/Expressions/Values/Set/Intersection","Rascal/Expressions/Values/Set/NotEqual","Rascal/Expressions/Values/Set/Product","Rascal/Expressions/Values/Set/StrictSubSet","Rascal/Expressions/Values/Set/StrictSuperSet","Rascal/Expressions/Values/Set/SubSet","Rascal/Expressions/Values/Set/SuperSet","Rascal/Expressions/Values/Set/Union","Rascal/Expressions/Values/Set/getOneFrom","Rascal/Expressions/Values/Set/in","Rascal/Expressions/Values/Set/isEmpty","Rascal/Expressions/Values/Set/mapper","Rascal/Expressions/Values/Set/max","Rascal/Expressions/Values/Set/min","Rascal/Expressions/Values/Set/notin","Rascal/Expressions/Values/Set/power","Rascal/Expressions/Values/Set/power1","Rascal/Expressions/Values/Set/reducer","Rascal/Expressions/Values/Set/size","Rascal/Expressions/Values/Set/takeOneFrom","Rascal/Expressions/Values/Set/toList","Rascal/Expressions/Values/Set/toMap","Rascal/Expressions/Values/Set/toMapUnique","Rascal/Expressions/Values/Set/toString","Rascal/Expressions/Values/String","Rascal/Expressions/Values/String/Concatenation","Rascal/Expressions/Values/String/Equal","Rascal/Expressions/Values/String/GreaterThan","Rascal/Expressions/Values/String/GreaterThanOrEqual","Rascal/Expressions/Values/String/LessThan","Rascal/Expressions/Values/String/LessThanOrEqual","Rascal/Expressions/Values/String/NotEqual","Rascal/Expressions/Values/String/center","Rascal/Expressions/Values/String/charAt","Rascal/Expressions/Values/String/endsWith","Rascal/Expressions/Values/String/isEmpty","Rascal/Expressions/Values/String/left","Rascal/Expressions/Values/String/reverse","Rascal/Expressions/Values/String/right","Rascal/Expressions/Values/String/size","Rascal/Expressions/Values/String/startsWith","Rascal/Expressions/Values/String/substring","Rascal/Expressions/Values/String/toInt","Rascal/Expressions/Values/String/toLowerCase","Rascal/Expressions/Values/String/toReal","Rascal/Expressions/Values/String/toUpperCase","Rascal/Expressions/Values/Tuple","Rascal/Expressions/Values/Tuple/Concatenation","Rascal/Expressions/Values/Tuple/Equal","Rascal/Expressions/Values/Tuple/FieldSelection","Rascal/Expressions/Values/Tuple/GreaterThan","Rascal/Expressions/Values/Tuple/GreaterThanOrEqual","Rascal/Expressions/Values/Tuple/LessThan","Rascal/Expressions/Values/Tuple/LessThanOrEqual","Rascal/Expressions/Values/Tuple/NotEqual","Rascal/Expressions/Values/Tuple/Subscription","Rascal/Expressions/Values/Value","Rascal/Expressions/Values/Value/Conditional","Rascal/Expressions/Values/Value/Equal","Rascal/Expressions/Values/Value/GreaterThan","Rascal/Expressions/Values/Value/GreaterThanOrEqual","Rascal/Expressions/Values/Value/LessThan","Rascal/Expressions/Values/Value/LessThanOrEqual","Rascal/Expressions/Values/Value/NotEqual","Rascal/Expressions/Values/Void","Rascal/Expressions/Visit","Rascal/IDEConstruction","Rascal/IDEConstruction/IMP","Rascal/InstallingAndRunning","Rascal/InstallingAndRunning/Installing","Rascal/InstallingAndRunning/Running","Rascal/InstallingAndRunning/Running/Help","Rascal/Libraries","Rascal/Libraries/Benchmark","Rascal/Libraries/Benchmark/cpuTime","Rascal/Libraries/Benchmark/systemTime","Rascal/Libraries/Benchmark/userTime","Rascal/Libraries/Graph","Rascal/Libraries/Graph/bottom","Rascal/Libraries/Graph/predecessors","Rascal/Libraries/Graph/reach","Rascal/Libraries/Graph/reachR","Rascal/Libraries/Graph/reachX","Rascal/Libraries/Graph/shortestPathPair","Rascal/Libraries/Graph/successors","Rascal/Libraries/Graph/top","Rascal/Libraries/IDE","Rascal/Libraries/IO","Rascal/Libraries/IO/appendToFile","Rascal/Libraries/IO/exists","Rascal/Libraries/IO/isDirectory","Rascal/Libraries/IO/isFile","Rascal/Libraries/IO/lastModified","Rascal/Libraries/IO/listEntries","Rascal/Libraries/IO/mkDirectory","Rascal/Libraries/IO/print","Rascal/Libraries/IO/println","Rascal/Libraries/IO/readFile","Rascal/Libraries/IO/readFileBytes","Rascal/Libraries/IO/readFileLines","Rascal/Libraries/IO/writeFile","Rascal/Libraries/JDT","Rascal/Libraries/JDT/extractClass","Rascal/Libraries/JDT/extractFactsTransitive","Rascal/Libraries/JDT/extractProject","Rascal/Libraries/JDT/extractResource","Rascal/Libraries/JDT/extractResources","Rascal/Libraries/JDT/isOnBuildPath","Rascal/Libraries/JDT/matchLocations","Rascal/Libraries/Java","Rascal/Libraries/LabeledGraph","Rascal/Libraries/LabeledGraph/bottom","Rascal/Libraries/LabeledGraph/predecessors","Rascal/Libraries/LabeledGraph/reach","Rascal/Libraries/LabeledGraph/reachR","Rascal/Libraries/LabeledGraph/reachX","Rascal/Libraries/LabeledGraph/shortestPathPair","Rascal/Libraries/LabeledGraph/successors","Rascal/Libraries/LabeledGraph/top","Rascal/Libraries/Message","Rascal/Libraries/Message/error","Rascal/Libraries/Message/info","Rascal/Libraries/Message/warning","Rascal/Libraries/PriorityQueue","Rascal/Libraries/PriorityQueue/extractMinimum","Rascal/Libraries/PriorityQueue/findMinimum","Rascal/Libraries/PriorityQueue/insertElement","Rascal/Libraries/PriorityQueue/isEmpty","Rascal/Libraries/PriorityQueue/mkPriorityQueue","Rascal/Libraries/RSF","Rascal/Libraries/RSF/readRSF","Rascal/Libraries/Resources","Rascal/Libraries/Resources/dependencies","Rascal/Libraries/Resources/getProject","Rascal/Libraries/Resources/projects","Rascal/Libraries/Resources/references","Rascal/Libraries/Resources/root","Rascal/Libraries/Scripting","Rascal/Libraries/Scripting/eval","Rascal/Libraries/Scripting/evalType","Rascal/Libraries/Scripting/shell","Rascal/Libraries/Tree","Rascal/Libraries/Tree/parse","Rascal/Libraries/Tree/unparse","Rascal/Libraries/ValueIO","Rascal/Libraries/ValueIO/readBinaryValueFile","Rascal/Libraries/ValueIO/readTextValueFile","Rascal/Libraries/ValueIO/readTextValueString","Rascal/Libraries/ValueIO/writeBinaryValueFile","Rascal/Libraries/ValueIO/writeTextValueFile","Rascal/Libraries/Vis","Rascal/Libraries/Vis/Figure","Rascal/Libraries/Vis/Figure/Angles","Rascal/Libraries/Vis/Figure/BoxModel","Rascal/Libraries/Vis/Figure/ColorModel","Rascal/Libraries/Vis/Figure/ColorModel/color","Rascal/Libraries/Vis/Figure/ColorModel/colorNames","Rascal/Libraries/Vis/Figure/ColorModel/colorScale","Rascal/Libraries/Vis/Figure/ColorModel/colorSteps","Rascal/Libraries/Vis/Figure/ColorModel/gray","Rascal/Libraries/Vis/Figure/ColorModel/interpolateColor","Rascal/Libraries/Vis/Figure/ColorModel/palette","Rascal/Libraries/Vis/Figure/ColorModel/rgb","Rascal/Libraries/Vis/Figure/Figures","Rascal/Libraries/Vis/Figure/Figures/box","Rascal/Libraries/Vis/Figure/Figures/button","Rascal/Libraries/Vis/Figure/Figures/checkbox","Rascal/Libraries/Vis/Figure/Figures/choice","Rascal/Libraries/Vis/Figure/Figures/computeFigure","Rascal/Libraries/Vis/Figure/Figures/edge","Rascal/Libraries/Vis/Figure/Figures/ellipse","Rascal/Libraries/Vis/Figure/Figures/grid","Rascal/Libraries/Vis/Figure/Figures/hcat","Rascal/Libraries/Vis/Figure/Figures/hvcat","Rascal/Libraries/Vis/Figure/Figures/outline","Rascal/Libraries/Vis/Figure/Figures/space","Rascal/Libraries/Vis/Figure/Figures/textarea","Rascal/Libraries/Vis/Figure/Figures/textfield","Rascal/Libraries/Vis/Figure/Figures/treemap","Rascal/Libraries/Vis/Figure/InteractionModel","Rascal/Libraries/Vis/Figure/Properties","Rascal/Libraries/Vis/Figure/Properties/anchor","Rascal/Libraries/Vis/Figure/Properties/bottom","Rascal/Libraries/Vis/Figure/Properties/center","Rascal/Libraries/Vis/Figure/Properties/gap","Rascal/Libraries/Vis/Figure/Properties/hanchor","Rascal/Libraries/Vis/Figure/Properties/hcenter","Rascal/Libraries/Vis/Figure/Properties/height","Rascal/Libraries/Vis/Figure/Properties/hgap","Rascal/Libraries/Vis/Figure/Properties/hint","Rascal/Libraries/Vis/Figure/Properties/id","Rascal/Libraries/Vis/Figure/Properties/left","Rascal/Libraries/Vis/Figure/Properties/lineColor","Rascal/Libraries/Vis/Figure/Properties/lineWidth","Rascal/Libraries/Vis/Figure/Properties/mouseOver","Rascal/Libraries/Vis/Figure/Properties/onClick","Rascal/Libraries/Vis/Figure/Properties/right","Rascal/Libraries/Vis/Figure/Properties/shapeClosed","Rascal/Libraries/Vis/Figure/Properties/shapeConnected","Rascal/Libraries/Vis/Figure/Properties/shapeCurved","Rascal/Libraries/Vis/Figure/Properties/size","Rascal/Libraries/Vis/Figure/Properties/textAngle","Rascal/Libraries/Vis/Figure/Properties/top","Rascal/Libraries/Vis/Figure/Properties/vanchor","Rascal/Libraries/Vis/Figure/Properties/vcenter","Rascal/Libraries/Vis/Figure/Properties/vgap","Rascal/Libraries/Vis/Figure/Properties/width","Rascal/Motivation","Rascal/Statements","Rascal/Statements/Append","Rascal/Statements/Assert","Rascal/Statements/Assignment","Rascal/Statements/Block","Rascal/Statements/Do","Rascal/Statements/Fail","Rascal/Statements/For","Rascal/Statements/If","Rascal/Statements/Insert","Rascal/Statements/Return","Rascal/Statements/Solve","Rascal/Statements/Switch","Rascal/Statements/Test","Rascal/Statements/Throw","Rascal/Statements/TryCatch","Rascal/Statements/While");

var searchTerms = {};


searchTerms["Rascal/Expressions/Values/Boolean/Or"] = new Array("||","bool");

searchTerms["Rascal/Expressions/Values/Tuple/Concatenation"] = new Array("tuple","[","+",",","]",">");

searchTerms["Rascal/Expressions/Values/Number"] = new Array("num","real","int");

searchTerms["Rascal/Expressions/Values"] = null;

searchTerms["Rascal/EASY/Renovation"] = null;

searchTerms["Rascal/Expressions/Values/Relation/rangeX"] = null;

searchTerms["Rascal/Expressions/Values/Number/Multiplication"] = new Array("*","real","int");

searchTerms["Rascal/Expressions/Values/DateTime/LessThanOrEqual"] = new Array("<=","bool","datetime");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/id"] = null;

searchTerms["Rascal/Declarations/Import"] = new Array("import",";");

searchTerms["Rascal/Expressions/Values/Relation/rangeR"] = null;

searchTerms["Rascal/Libraries"] = null;

searchTerms["Rascal/Expressions/Values/Boolean"] = new Array("false","bool","true");

searchTerms["Rascal/Expressions/Values/Set/Equal"] = new Array("==","set","bool","[","]");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/shapeClosed"] = null;

searchTerms["Rascal/Concepts/IntroStaticTyping"] = null;

searchTerms["Rascal/Libraries/IDE"] = null;

searchTerms["Rascal/Expressions/Values/Set/Product"] = new Array("set","rel","*","[",",","]");

searchTerms["Rascal/Expressions/Values/List/tail"] = null;

searchTerms["Rascal/Expressions/Values/List/toSet"] = null;

searchTerms["Rascal/Expressions/Values/List/insertAt"] = null;

searchTerms["Rascal/Libraries/Scripting/evalType"] = null;

searchTerms["Rascal/Expressions/Patterns/Regular"] = null;

searchTerms["Rascal/Declarations/Function"] = new Array("(",")",",","throws");

searchTerms["Rascal/Expressions/Values/String/endsWith"] = null;

searchTerms["Rascal/Expressions/Values/Relation/complement"] = null;

searchTerms["Rascal/Expressions/Values/Set/power"] = null;

searchTerms["Rascal/Expressions/Values/Map/SubMap"] = new Array("<=","bool","[",",","map","]");

searchTerms["Rascal/Expressions/Values/Relation/carrierX"] = null;

searchTerms["Rascal/Declarations/Alias"] = new Array("alias",";","=");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/mouseOver"] = null;

searchTerms["Rascal/Expressions/Values/Set/Difference"] = new Array("set",")]","(","lub","[",",","]","-");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/shapeCurved"] = null;

searchTerms["Rascal/Expressions/Values/DateTime/GreaterThan"] = new Array("bool","datetime",">");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/top"] = null;

searchTerms["Rascal/Expressions/Values/List/Subscription"] = new Array("[","]","list","int");

searchTerms["Rascal/Expressions/Values/Tuple/Equal"] = new Array("==","tuple","bool","[",",","]");

searchTerms["Rascal/Expressions/Values/Relation/carrierR"] = null;

searchTerms["Rascal/Expressions/Patterns/Concrete"] = null;

searchTerms["Rascal/Expressions/Values/List/toMapUnique"] = null;

searchTerms["Rascal/IDEConstruction/IMP"] = null;

searchTerms["Rascal/Statements/Assignment"] = new Array("@","(",")",",","/=",".","-=","+=","*=","[","<","=","]","?=",">","?");

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel/palette"] = null;

searchTerms["Rascal/Expressions/Values/Value/NotEqual"] = new Array("value","bool","!=");

searchTerms["Rascal/Libraries/Vis/Figure/Figures"] = new Array("alias","Figure","[","=","];","list","Figures");

searchTerms["Rascal/Expressions/Values/Boolean/Match"] = new Array("value",":=","bool");

searchTerms["Rascal/Expressions/Values/Value/GreaterThan"] = new Array("value","bool",">");

searchTerms["Rascal/Expressions/Values/Set/SuperSet"] = new Array("set","bool","[","]",">=");

searchTerms["Rascal/Statements/Block"] = new Array("{","}");

searchTerms["Rascal/Libraries/JDT/extractClass"] = null;

searchTerms["Rascal/Libraries/Graph/predecessors"] = null;

searchTerms["Rascal/Expressions/Values/Constructor"] = new Array("(",")",",");

searchTerms["Rascal/Expressions/Values/Node/GreaterThan"] = new Array("node","bool",">");

searchTerms["Rascal/Concepts/IntroDatatypes"] = null;

searchTerms["Rascal/Expressions/Values/Set/toMap"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/arbBool"] = null;

searchTerms["Rascal/Libraries/JDT"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Figures/treemap"] = null;

searchTerms["Rascal/Expressions/Values/DateTime/LessThan"] = new Array("bool","datetime","<");

searchTerms["Rascal/Libraries/Graph/reach"] = null;

searchTerms["Rascal/Libraries/ValueIO/readTextValueFile"] = null;

searchTerms["Rascal/Concepts/IntroImmutableValues"] = null;

searchTerms["Rascal/IDEConstruction"] = null;

searchTerms["Rascal/Expressions/Values/Node"] = new Array("value","str","node","(",")",",");

searchTerms["Rascal/Expressions/Values/Set/toMapUnique"] = null;

searchTerms["Rascal/Expressions/Values/Map/StrictSuperMap"] = new Array("bool","[",",","map","]",">");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/center"] = null;

searchTerms["Rascal/Expressions/Values/Relation"] = new Array("rel","[","{",",","<","]","}",">");

searchTerms["Rascal/EASY/ModelDrivenEngineering"] = null;

searchTerms["Rascal/Libraries/IO/lastModified"] = null;

searchTerms["Rascal/Expressions/Values/Relation/Composition"] = new Array("rel","[",",","]","o");

searchTerms["Rascal/Libraries/Resources"] = new Array("loc","root","set","(",")","data",",","folder","Resource",");","project","projects","contents","[","id","file","|","=","]");

searchTerms["Rascal/Expressions/Values/String/toInt"] = null;

searchTerms["Rascal/Libraries/JDT/extractProject"] = null;

searchTerms["Rascal/Expressions/Values/Node/getName"] = null;

searchTerms["Rascal/EASY"] = null;

searchTerms["Rascal/Concepts/IntroVisiting"] = null;

searchTerms["Rascal/Libraries/ValueIO/readBinaryValueFile"] = null;

searchTerms["Rascal/Expressions/Values/Tuple/GreaterThan"] = new Array("tuple","bool","[",",","]",">");

searchTerms["Rascal/Expressions/Values/List/size"] = null;

searchTerms["Rascal/Declarations/StaticTyping/ReifiedTypes"] = new Array("#","type","Name");

searchTerms["Rascal/Expressions/Values/Relation/carrier"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/toInt"] = null;

searchTerms["Rascal/Expressions/Values/String/reverse"] = null;

searchTerms["Rascal/Expressions/Values/Relation/TransitiveClosure"] = new Array("rel","+","[",",","]");

searchTerms["Rascal/Expressions/Values/Map/range"] = null;

searchTerms["Rascal/Expressions/Values/List/slice"] = null;

searchTerms["Rascal/Expressions/Values/Value/Conditional"] = new Array("(",")","lub",":","bool",",","?");

searchTerms["Rascal/Libraries/PriorityQueue/mkPriorityQueue"] = null;

searchTerms["Rascal/Statements/Do"] = new Array("while",");","(","do");

searchTerms["Rascal/Expressions/Values/Map/notin"] = new Array("notin","bool","[",",","map","]");

searchTerms["Rascal/Expressions/Operators/FieldProjection"] = new Array("<",",",">");

searchTerms["Rascal/Expressions/Values/Set/Union"] = new Array("set",")]","(","lub","+","[",",","]");

searchTerms["Rascal/Examples"] = null;

searchTerms["Rascal/Declarations/Rule"] = new Array(";","rule");

searchTerms["Rascal/Libraries/ValueIO/readTextValueString"] = null;

searchTerms["Rascal/Expressions/Values/Map/domain"] = null;

searchTerms["Rascal/Libraries/RSF"] = null;

searchTerms["Rascal/Expressions/Values/Number/GreaterThan"] = new Array("bool","real",">","int");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/hgap"] = null;

searchTerms["Rascal/Expressions/Values/List/toString"] = null;

searchTerms["Rascal/Expressions/Values/String/substring"] = null;

searchTerms["Rascal/Libraries/PriorityQueue/insertElement"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/bottom"] = null;

searchTerms["Rascal/Expressions/Values/List/isEmpty"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Figures/space"] = null;

searchTerms["Rascal/Libraries/Graph/shortestPathPair"] = null;

searchTerms["Rascal/Libraries/LabeledGraph/bottom"] = null;

searchTerms["Rascal/Declarations/StaticTyping/TypeConstraints"] = new Array("&","<:");

searchTerms["Rascal/Expressions/Values/Map/rangeX"] = null;

searchTerms["Rascal/Libraries/Graph/reachX"] = null;

searchTerms["Rascal/Expressions/Values/String/GreaterThanOrEqual"] = new Array("str","bool",">=");

searchTerms["Rascal/Expressions/StatementAsExpression"] = null;

searchTerms["Rascal/Libraries/IO/readFileLines"] = null;

searchTerms["Rascal/Expressions/Values/Number/arbReal"] = null;

searchTerms["Rascal/Libraries/Java"] = null;

searchTerms["Rascal/Expressions/Values/List/head"] = null;

searchTerms["Rascal/Expressions/Values/Map/rangeR"] = null;

searchTerms["Rascal/Expressions/Patterns"] = null;

searchTerms["Rascal/Expressions/Values/Set/getOneFrom"] = null;

searchTerms["Rascal/Libraries/Graph/reachR"] = null;

searchTerms["Rascal/Expressions/Values/Node/Equal"] = new Array("==","node","bool");

searchTerms["Rascal/Libraries/Vis/Figure/Figures/computeFigure"] = null;

searchTerms["Rascal/Statements/Solve"] = new Array("(",")",";",",","solve");

searchTerms["Rascal/Expressions/Call"] = new Array("(",")",",");

searchTerms["Rascal/Expressions"] = null;

searchTerms["Rascal/Expressions/Patterns/Abstract"] = null;

searchTerms["Rascal/Declarations/AlgebraicDataType"] = new Array("data",";","|","=");

searchTerms["Rascal/InstallingAndRunning"] = null;

searchTerms["Rascal/Examples/ColoredTrees"] = null;

searchTerms["Rascal/Expressions/Values/Map/toString"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/Equivalence"] = new Array("<==>","bool");

searchTerms["Rascal/Expressions/Values/Relation/CarthesianProduct"] = new Array("set","rel","*","[",",","]");

searchTerms["Rascal/Libraries/Resources/dependencies"] = null;

searchTerms["Rascal/Expressions/Values/String/LessThanOrEqual"] = new Array("<=","str","bool");

searchTerms["Rascal/Expressions/Values/Map/Equal"] = new Array("==","bool","[",",","map","]");

searchTerms["Rascal/Expressions/Values/Map/in"] = new Array("in","bool","[",",","map","]");

searchTerms["Rascal/Libraries/JDT/matchLocations"] = null;

searchTerms["Rascal/Expressions/Values/Location"] = new Array("loc","(",")","|",",","<",">");

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel"] = new Array("alias","Color",";","=","int");

searchTerms["Rascal/Expressions/Values/Node/getAnnotations"] = null;

searchTerms["Rascal/Libraries/Vis"] = null;

searchTerms["Rascal/Declarations/Annotation/Replacement"] = new Array("[@","=","]");

searchTerms["Rascal/Expressions/Values/Set/isEmpty"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/right"] = null;

searchTerms["Rascal/Libraries/IO/readFile"] = null;

searchTerms["Rascal/Expressions/Values/Number/abs"] = null;

searchTerms["Rascal/Libraries/JDT/extractResource"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Figures/edge"] = null;

searchTerms["Rascal/Expressions/Values/List/NotEqual"] = new Array("bool","[","!=","]","list");

searchTerms["Rascal/Expressions/Values/Tuple/NotEqual"] = new Array("tuple","bool","[","!=",",","]");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/gap"] = null;

searchTerms["Rascal/Libraries/Message/warning"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/InteractionModel"] = null;

searchTerms["Rascal/Expressions/Values/Number/Negation"] = new Array("-","real","int");

searchTerms["Rascal/Expressions/Values/Number/Conditional"] = new Array("bool",":","real","int","?");

searchTerms["Rascal/Libraries/Graph"] = new Array("alias","T","&","rel","from","[&","to",",","]","=","Graph","];");

searchTerms["Rascal/Expressions/Visit"] = new Array("case","default","Strategy","(",")",":","visit","{",";","}");

searchTerms["Rascal/Expressions/Values/Real"] = null;

searchTerms["Rascal/Expressions/Values/Tuple/GreaterThanOrEqual"] = new Array("tuple","bool","[",",","]",">=");

searchTerms["Rascal/Declarations/SyntaxDefinition/ConcreteSyntax"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel/colorScale"] = null;

searchTerms["Rascal/Expressions/Values/DateTime"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel/colorNames"] = null;

searchTerms["Rascal/Statements/Assert"] = new Array("str","assert","bool",":");

searchTerms["Rascal/Expressions/Values/String/toLowerCase"] = null;

searchTerms["Rascal/Libraries/RSF/readRSF"] = null;

searchTerms["Rascal"] = null;

searchTerms["Rascal/Expressions/Values/String/Equal"] = new Array("==","str","bool");

searchTerms["Rascal/Expressions/Values/Node/makeNode"] = null;

searchTerms["Rascal/Libraries/Benchmark/systemTime"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/NoMatch"] = new Array("value","!:=","bool");

searchTerms["Rascal/Expressions/Values/Map/NotEqual"] = new Array("bool","[","!=",",","map","]");

searchTerms["Rascal/Expressions/Values/Void"] = null;

searchTerms["Rascal/Expressions/Values/Number/GreaterThanOrEqual"] = new Array("bool","real",">=","int");

searchTerms["Rascal/Expressions/Comprehensions"] = null;

searchTerms["Rascal/Expressions/Values/List/Intersection"] = new Array(")]","&","(","lub","[",",","]","list");

searchTerms["Rascal/Expressions/Values/Node/arity"] = null;

searchTerms["Rascal/Examples/Factorial"] = null;

searchTerms["Rascal/Expressions/Values/Set/Comprehension"] = new Array("set","(",")","lub","{","[","|",",","}","]");

searchTerms["Rascal/Expressions/Values/String/toReal"] = null;

searchTerms["Rascal/Concepts/IntroEnumerators/EquationSolving"] = null;

searchTerms["Rascal/Expressions/Values/Set/mapper"] = null;

searchTerms["Rascal/Expressions/Values/Set/size"] = null;

searchTerms["Rascal/Expressions/Values/Number/Subtraction"] = new Array("-","real","int");

searchTerms["Rascal/Expressions/Values/Number/min"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/Negation"] = new Array("!","bool");

searchTerms["Rascal/Libraries/Resources/projects"] = null;

searchTerms["Rascal/Expressions/Values/Tuple/LessThanOrEqual"] = new Array("<=","tuple","bool","[",",","]");

searchTerms["Rascal/Expressions/Values/Relation/Subscription"] = new Array("set","rel","[",",","]","int");

searchTerms["Rascal/Expressions/Values/List/notin"] = new Array("notin","bool","[","]","list");

searchTerms["Rascal/Expressions/Values/List/sort"] = null;

searchTerms["Rascal/Expressions/Values/List/Concatenation"] = new Array(")]","(","lub","+","[",",","]","list");

searchTerms["Rascal/Statements/Switch"] = new Array("case","default","switch","(",")",":","{",";","}");

searchTerms["Rascal/Expressions/Values/List/Difference"] = new Array(")]","(","lub","[",",","]","-","list");

searchTerms["Rascal/Expressions/Values/Location/FieldSelection"] = new Array("loc",".");

searchTerms["Rascal/Concepts/IntroRewriteRules"] = null;

searchTerms["Rascal/Statements/Return"] = new Array("return",";");

searchTerms["Rascal/Expressions/Values/Boolean/toReal"] = null;

searchTerms["Rascal/Expressions/Values/Node/LessThan"] = new Array("node","bool","<");

searchTerms["Rascal/Expressions/Values/Boolean/Any"] = new Array("(",")","bool",",","any");

searchTerms["Rascal/Declarations/SyntaxDefinition/Disambiguation"] = null;

searchTerms["Rascal/Expressions/Values/Set/toString"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel/colorSteps"] = null;

searchTerms["Rascal/Libraries/PriorityQueue/extractMinimum"] = null;

searchTerms["Rascal/Expressions/Values/Map"] = new Array(")]","(",")","lub",":","[",",","map");

searchTerms["Rascal/Libraries/LabeledGraph/predecessors"] = null;

searchTerms["Rascal/Expressions/Values/Set/SubSet"] = new Array("<=","set","bool","[","]");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/hanchor"] = null;

searchTerms["Rascal/Concepts/IntroEnumerators"] = null;

searchTerms["Rascal/Libraries/IO"] = null;

searchTerms["Rascal/Expressions/Values/List/domain"] = null;

searchTerms["Rascal/Statements/Append"] = null;

searchTerms["Rascal/Libraries/Scripting/shell"] = null;

searchTerms["Rascal/Declarations/Annotation/Selection"] = new Array("@","node","<:");

searchTerms["Rascal/Expressions/Values/Boolean/And"] = new Array("&&","bool");

searchTerms["Rascal/Libraries/ValueIO"] = null;

searchTerms["Rascal/Expressions/Values/List/SuperList"] = new Array("bool","[","]","list",">=");

searchTerms["Rascal/Expressions/Values/List/Equal"] = new Array("==","bool","[","]","list");

searchTerms["Rascal/Declarations/StaticTyping"] = null;

searchTerms["Rascal/Expressions/Values/List/min"] = null;

searchTerms["Rascal/Expressions/Values/Relation/invert"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties"] = new Array("computedInt","Color","computedStr","num","computedReal","int","alias","str","computedColor","computedNum","FProperties","();","FProperty","[","=","real","];","list");

searchTerms["Rascal/Expressions/Values/String/left"] = null;

searchTerms["Rascal/Expressions/Values/List/reducer"] = null;

searchTerms["Rascal/Expressions/Comprehensions/Enumerator"] = null;

searchTerms["Rascal/Expressions/Values/String/size"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/width"] = null;

searchTerms["Rascal/Expressions/Values/Number/toReal"] = null;

searchTerms["Rascal/EASY/Concurrency"] = null;

searchTerms["Rascal/Expressions/Values/Integer"] = null;

searchTerms["Rascal/Libraries/IO/exists"] = null;

searchTerms["Rascal/Expressions/Values/List/permutations"] = null;

searchTerms["Rascal/Expressions/Values/Map/SuperMap"] = new Array("bool","[",",","map","]",">=");

searchTerms["Rascal/Expressions/Values/Value/GreaterThanOrEqual"] = new Array("value","bool",">=");

searchTerms["Rascal/Expressions/Values/Map/mapper"] = null;

searchTerms["Rascal/Expressions/Values/Map/Union"] = new Array("),","(",")","lub","+","[",",","map","]");

searchTerms["Rascal/Libraries/Message/error"] = null;

searchTerms["Rascal/Expressions/Values/String/LessThan"] = new Array("str","bool","<");

searchTerms["Rascal/Expressions/Values/Boolean/IsDefined"] = new Array("bool","?");

searchTerms["Rascal/Libraries/Vis/Figure/Figures/choice"] = null;

searchTerms["Rascal/Expressions/Values/Set/NotEqual"] = new Array("set","bool","[","!=","]");

searchTerms["Rascal/Libraries/Vis/Figure/Figures/outline"] = null;

searchTerms["Rascal/Expressions/Values/DateTime/NotEqual"] = new Array("bool","datetime","!=");

searchTerms["Rascal/Declarations/Annotation"] = new Array("@","anno");

searchTerms["Rascal/Declarations"] = null;

searchTerms["Rascal/Declarations/StaticTyping/TypeParameters"] = null;

searchTerms["Rascal/Libraries/LabeledGraph/reach"] = null;

searchTerms["Rascal/Libraries/Tree"] = null;

searchTerms["Rascal/Expressions/Values/String/isEmpty"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/lineWidth"] = null;

searchTerms["Rascal/Expressions/Values/Location/GreaterThan"] = new Array("loc","bool",">");

searchTerms["Rascal/Libraries/JDT/extractResources"] = null;

searchTerms["Rascal/Expressions/Values/Set/min"] = null;

searchTerms["Rascal/Expressions/Values/Set/StrictSuperSet"] = new Array("set","bool","[","]",">");

searchTerms["Rascal/Examples/Hello"] = null;

searchTerms["Rascal/EASY/Forensics"] = null;

searchTerms["Rascal/Expressions/Values/Node/GreaterThanOrEqual"] = new Array("node","bool",">=");

searchTerms["Rascal/Expressions/Values/Number/Addition"] = new Array("+","real","int");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/onClick"] = null;

searchTerms["Rascal/Libraries/Vis/Figure"] = null;

searchTerms["Rascal/Expressions/Values/Location/Equal"] = new Array("==","loc","bool");

searchTerms["Rascal/Libraries/Vis/Figure/Figures/textfield"] = null;

searchTerms["Rascal/Libraries/IO/readFileBytes"] = null;

searchTerms["Rascal/Expressions/Values/Set/reducer"] = null;

searchTerms["Rascal/Expressions/Values/Set/Intersection"] = new Array("set",")]","&","(","lub","[",",","]");

searchTerms["Rascal/Declarations/Tag"] = null;

searchTerms["Rascal/Statements"] = null;

searchTerms["Rascal/Expressions/Values/Relation/ReflexiveTransitiveClosure"] = new Array("rel","*","[",",","]");

searchTerms["Rascal/Libraries/Vis/Figure/Figures/grid"] = null;

searchTerms["Rascal/Libraries/LabeledGraph/successors"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/All"] = new Array("all","(",")","bool",",");

searchTerms["Rascal/Expressions/Values/Value/LessThanOrEqual"] = new Array("<=","value","bool");

searchTerms["Rascal/Libraries/Vis/Figure/Figures/checkbox"] = null;

searchTerms["Rascal/Expressions/Values/List/toMap"] = null;

searchTerms["Rascal/Expressions/Values/Number/toInt"] = null;

searchTerms["Rascal/Expressions/Values/Location/LessThan"] = new Array("loc","bool","<");

searchTerms["Rascal/Expressions/Values/String/center"] = new Array("str","pad","s","center","(",")",",","n","int");

searchTerms["Rascal/Expressions/Values/Node/setAnnotations"] = null;

searchTerms["Rascal/Expressions/Values/Tuple/Subscription"] = new Array("[","]");

searchTerms["Rascal/Expressions/Values/List/StrictSubList"] = new Array("bool","[","<","]","list");

searchTerms["Rascal/Expressions/Values/List/getOneFrom"] = null;

searchTerms["Rascal/Libraries/LabeledGraph"] = new Array("alias","LGraph","T","label","&","rel","from","[&","to",",","L","]","=","];");

searchTerms["Rascal/Expressions/Operators"] = null;

searchTerms["Rascal/Expressions/Values/List/index"] = null;

searchTerms["Rascal/Libraries/PriorityQueue"] = null;

searchTerms["Rascal/Expressions/Values/Relation/ident"] = null;

searchTerms["Rascal/Expressions/Values/Map/invertUnique"] = null;

searchTerms["Rascal/Expressions/Values/Set/power1"] = null;

searchTerms["Rascal/Expressions/Values/Number/LessThan"] = new Array("bool","<","real","int");

searchTerms["Rascal/Libraries/Message"] = new Array("loc","str","msg",");","at","Message","error","(",")","data",",","warning","|","=","info");

searchTerms["Rascal/Expressions/Values/Map/StrictSubMap"] = new Array("bool","[","<",",","map","]");

searchTerms["Rascal/Expressions/Values/Boolean/fromInt"] = null;

searchTerms["Rascal/EASY/Security"] = null;

searchTerms["Rascal/Expressions/Values/String"] = new Array("str","\"","r","b","...\"","t","\'","<","\\",">","n");

searchTerms["Rascal/Expressions/Values/Map/Subscription"] = new Array("[",",","map","]");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/shapeConnected"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/hcenter"] = null;

searchTerms["Rascal/Expressions/Values/Value/Equal"] = new Array("==","value","bool");

searchTerms["Rascal/Libraries/ValueIO/writeBinaryValueFile"] = null;

searchTerms["Rascal/Statements/Insert"] = new Array("insert",";");

searchTerms["Rascal/Expressions/Values/Map/Comprehension"] = new Array("(",")",":","[","map",",","|","]");

searchTerms["Rascal/Declarations/Module"] = new Array(";","module");

searchTerms["Rascal/Libraries/Scripting"] = null;

searchTerms["Rascal/Concepts/IntroSyntaxDefinitionAndParsing"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/vgap"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel/color"] = null;

searchTerms["Rascal/Libraries/JDT/isOnBuildPath"] = null;

searchTerms["Rascal/Expressions/Values/Set/toList"] = null;

searchTerms["Rascal/Expressions/Values/DateTime/GreaterThanOrEqual"] = new Array("bool","datetime",">=");

searchTerms["Rascal/Expressions/Values/Relation/domainX"] = null;

searchTerms["Rascal/Expressions/Values/String/charAt"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/vanchor"] = null;

searchTerms["Rascal/Expressions/Values/Relation/domainR"] = null;

searchTerms["Rascal/Concepts/IntroCaseDistinction"] = null;

searchTerms["Rascal/Expressions/Values/Tuple/FieldSelection"] = new Array("tuple","[",",","]",".");

searchTerms["Rascal/Declarations/SyntaxDefinition"] = new Array("start","syntax","lexical","&;","left","keyword","layout",":",";","{","|","=","...;","}",">");

searchTerms["Rascal/Expressions/Values/Set/in"] = new Array("set","in","bool","[","]");

searchTerms["Rascal/Expressions/Values/Number/LessThanOrEqual"] = new Array("<=","bool","real","int");

searchTerms["Rascal/Libraries/Tree/unparse"] = null;

searchTerms["Rascal/Statements/Fail"] = new Array(";","fail");

searchTerms["Rascal/Expressions/Values/Map/Difference"] = new Array("),",")]","(","lub","[",",","map","]","-");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/hint"] = null;

searchTerms["Rascal/Expressions/Values/Number/Division"] = new Array("real","/","int");

searchTerms["Rascal/Libraries/LabeledGraph/reachX"] = null;

searchTerms["Rascal/Expressions/Values/List/mapper"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Figures/ellipse"] = null;

searchTerms["Rascal/Expressions/Values/Map/invert"] = null;

searchTerms["Rascal/Libraries/LabeledGraph/top"] = null;

searchTerms["Rascal/Concepts/IntroComprehensions"] = null;

searchTerms["Rascal/Motivation"] = null;

searchTerms["Rascal/Libraries/JDT/extractFactsTransitive"] = null;

searchTerms["Rascal/Libraries/Graph/top"] = null;

searchTerms["Rascal/Libraries/LabeledGraph/reachR"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/lineColor"] = null;

searchTerms["Rascal/Expressions/Values/Tuple"] = new Array("tuple","[","<",",","]",">");

searchTerms["Rascal/Libraries/Vis/Figure/Figures/button"] = null;

searchTerms["Rascal/Expressions/Values/DateTime/FieldSelection"] = new Array("datetime",".");

searchTerms["Rascal/Libraries/IO/writeFile"] = null;

searchTerms["Rascal/Declarations/Variable"] = new Array(";","=","<:");

searchTerms["Rascal/Expressions/Values/Value/LessThan"] = new Array("value","bool","<");

searchTerms["Rascal/InstallingAndRunning/Running/Help"] = null;

searchTerms["Rascal/Expressions/Values/Set/StrictSubSet"] = new Array("set","bool","[","<","]");

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel/interpolateColor"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/IfDefinedElse"] = null;

searchTerms["Rascal/Expressions/Values/Map/size"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Figures/hvcat"] = null;

searchTerms["Rascal/Statements/For"] = new Array("(","for",")",";",",");

searchTerms["Rascal/Libraries/Benchmark"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/height"] = null;

searchTerms["Rascal/Expressions/Values/Number/Equal"] = new Array("==","bool","real","int");

searchTerms["Rascal/Libraries/IO/mkDirectory"] = null;

searchTerms["Rascal/Expressions/Values/Map/toList"] = null;

searchTerms["Rascal/Libraries/IO/appendToFile"] = null;

searchTerms["Rascal/Expressions/Values/Node/NotEqual"] = new Array("node","bool","!=");

searchTerms["Rascal/Expressions/Values/Map/toRel"] = null;

searchTerms["Rascal/Libraries/IO/isDirectory"] = null;

searchTerms["Rascal/Libraries/IO/isFile"] = null;

searchTerms["Rascal/Expressions/Values/Number/Remainder"] = new Array("%","int");

searchTerms["Rascal/Expressions/Values/String/Concatenation"] = new Array("str","+");

searchTerms["Rascal/Expressions/Values/String/toUpperCase"] = null;

searchTerms["Rascal/Expressions/Values/List/takeOneFrom"] = null;

searchTerms["Rascal/Statements/While"] = new Array("while","(",")",";");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/textAngle"] = null;

searchTerms["Rascal/Libraries/Resources/getProject"] = null;

searchTerms["Rascal/Libraries/Graph/bottom"] = null;

searchTerms["Rascal/Expressions/Values/List/Comprehension"] = new Array("(",")","lub","[","|",",","]","list");

searchTerms["Rascal/Libraries/LabeledGraph/shortestPathPair"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Angles"] = null;

searchTerms["Rascal/Expressions/Values/Node/LessThanOrEqual"] = new Array("<=","node","bool");

searchTerms["Rascal/Expressions/Values/Map/isEmpty"] = null;

searchTerms["Rascal/Expressions/Values/Location/GreaterThanOrEqual"] = new Array("loc","bool",">=");

searchTerms["Rascal/Libraries/Vis/Figure/Figures/textarea"] = null;

searchTerms["Rascal/Expressions/Patterns/PatternWithAction"] = new Array("=>",":");

searchTerms["Rascal/Libraries/PriorityQueue/findMinimum"] = null;

searchTerms["Rascal/Expressions/Values/DateTime/Equal"] = new Array("==","bool","datetime");

searchTerms["Rascal/Declarations/SyntaxDefinition/Symbols"] = new Array("@",">>","<<","!","()","!<<","\"","\'","(",")","*","+","}+","...]","range_2","Class","[","{","|","\\","^","?");

searchTerms["Rascal/Declarations/Program"] = null;

searchTerms["Rascal/Expressions/Values/Number/max"] = null;

searchTerms["Rascal/Expressions/Values/String/right"] = null;

searchTerms["Rascal/Expressions/Values/List"] = new Array("set",")]","(","lub","[",",","]");

searchTerms["Rascal/Expressions/Values/String/GreaterThan"] = new Array("str","bool",">");

searchTerms["Rascal/Concepts/IntroControlStructures"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/vcenter"] = null;

searchTerms["Rascal/Concepts"] = null;

searchTerms["Rascal/Concepts/IntroFunctions"] = null;

searchTerms["Rascal/Expressions/Values/String/NotEqual"] = new Array("str","bool","!=");

searchTerms["Rascal/Libraries/Graph/successors"] = null;

searchTerms["Rascal/Expressions/Values/Relation/Join"] = new Array("rel","join","[",",","]");

searchTerms["Rascal/Expressions/Values/Set/takeOneFrom"] = null;

searchTerms["Rascal/Statements/TryCatch"] = new Array("finally",":","catch",";","try");

searchTerms["Rascal/Expressions/Values/String/startsWith"] = null;

searchTerms["Rascal/Expressions/Values/Map/getOneFrom"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/Implication"] = new Array("bool","==>");

searchTerms["Rascal/Libraries/IO/print"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/BoxModel"] = null;

searchTerms["Rascal/Expressions/Comprehensions/Filter"] = null;

searchTerms["Rascal/Expressions/Values/Number/toString"] = null;

searchTerms["Rascal/Expressions/Values/Value"] = null;

searchTerms["Rascal/Expressions/Values/Number/arbInt"] = null;

searchTerms["Rascal/Expressions/Values/Map/Intersection"] = new Array("set",")]","&","(","lub","[","map",",","]");

searchTerms["Rascal/Expressions/Values/Set"] = new Array("set","(",")","lub","{","[",",","}","]");

searchTerms["Rascal/Expressions/Values/Map/domainX"] = null;

searchTerms["Rascal/Declarations/SyntaxDefinition/ParseTrees"] = null;

searchTerms["Rascal/Expressions/Values/Boolean/toString"] = null;

searchTerms["Rascal/Expressions/Values/Map/domainR"] = null;

searchTerms["Rascal/Libraries/Resources/root"] = null;

searchTerms["Rascal/Expressions/Values/Location/NotEqual"] = new Array("loc","bool","!=");

searchTerms["Rascal/Expressions/Values/List/max"] = null;

searchTerms["Rascal/Libraries/Benchmark/userTime"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel/rgb"] = null;

searchTerms["Rascal/Expressions/Values/List/reverse"] = null;

searchTerms["Rascal/Libraries/IO/listEntries"] = null;

searchTerms["Rascal/Statements/If"] = new Array("void","(","lub","else",")","bool",";",",","if");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/left"] = null;

searchTerms["Rascal/Expressions/Values/List/SubList"] = new Array("<=","bool","[","]","list");

searchTerms["Rascal/Statements/Throw"] = null;

searchTerms["Rascal/Concepts/IntroPatternMatching"] = null;

searchTerms["Rascal/Expressions/Values/Relation/range"] = null;

searchTerms["Rascal/Libraries/Tree/parse"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Properties/size"] = null;

searchTerms["Rascal/Statements/Test"] = new Array("str","test","bool");

searchTerms["Rascal/Expressions/Values/Set/notin"] = new Array("set","notin","bool","[","]");

searchTerms["Rascal/Expressions/Values/Tuple/LessThan"] = new Array("tuple","bool","[","<",",","]");

searchTerms["Rascal/Declarations/AlgebraicDataType/Exception"] = null;

searchTerms["Rascal/Libraries/ValueIO/writeTextValueFile"] = null;

searchTerms["Rascal/Expressions/Values/Number/NotEqual"] = new Array("bool","!=","real","int");

searchTerms["Rascal/Expressions/Values/List/StrictSuperList"] = new Array("bool","[","]",">","list");

searchTerms["Rascal/Expressions/Values/List/Product"] = new Array("]]","tuple","*","[",",","]","list");

searchTerms["Rascal/Expressions/Values/Relation/FieldSelection"] = new Array("set","rel","[",",","]",".");

searchTerms["Rascal/Libraries/Message/info"] = null;

searchTerms["Rascal/Expressions/Values/Location/LessThanOrEqual"] = new Array("loc","<=","bool");

searchTerms["Rascal/Libraries/Vis/Figure/Properties/anchor"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Figures/hcat"] = null;

searchTerms["Rascal/Libraries/Scripting/eval"] = null;

searchTerms["Rascal/Expressions/Operators/FieldAssignment"] = new Array("[","=","]");

searchTerms["Rascal/Libraries/Resources/references"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/ColorModel/gray"] = null;

searchTerms["Rascal/Libraries/Vis/Figure/Figures/box"] = null;

searchTerms["Rascal/Expressions/Values/Range"] = new Array("..","[",",","]");

searchTerms["Rascal/Libraries/Benchmark/cpuTime"] = null;

searchTerms["Rascal/Expressions/Values/List/delete"] = null;

searchTerms["Rascal/Libraries/PriorityQueue/isEmpty"] = null;

searchTerms["Rascal/InstallingAndRunning/Running"] = null;

searchTerms["Rascal/Expressions/Values/Set/max"] = null;

searchTerms["Rascal/Expressions/Values/Relation/domain"] = null;

searchTerms["Rascal/InstallingAndRunning/Installing"] = null;

searchTerms["Rascal/Expressions/Values/List/in"] = new Array("in","bool","[","]","list");

searchTerms["Rascal/Libraries/IO/println"] = null;
