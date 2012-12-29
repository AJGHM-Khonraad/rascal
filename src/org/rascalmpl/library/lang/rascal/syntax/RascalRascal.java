package org.rascalmpl.library.lang.rascal.syntax;

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.rascalmpl.parser.gtd.stack.*;
import org.rascalmpl.parser.gtd.stack.filter.*;
import org.rascalmpl.parser.gtd.stack.filter.follow.*;
import org.rascalmpl.parser.gtd.stack.filter.match.*;
import org.rascalmpl.parser.gtd.stack.filter.precede.*;
import org.rascalmpl.parser.gtd.preprocessing.ExpectBuilder;
import org.rascalmpl.parser.gtd.util.IntegerKeyedHashMap;
import org.rascalmpl.parser.gtd.util.IntegerList;
import org.rascalmpl.parser.gtd.util.IntegerMap;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

@SuppressWarnings("all")
public class RascalRascal extends org.rascalmpl.parser.gtd.SGTDBF<IConstructor, IConstructor, ISourceLocation> {
  protected final static IValueFactory VF = ValueFactoryFactory.getValueFactory();
  
  protected static IValue _read(java.lang.String s, org.eclipse.imp.pdb.facts.type.Type type) {
    try {
      return new StandardTextReader().read(VF, org.rascalmpl.values.uptr.Factory.uptr, type, new StringReader(s));
    }
    catch (FactTypeUseException e) {
      throw new RuntimeException("unexpected exception in generated parser", e);  
    } catch (IOException e) {
      throw new RuntimeException("unexpected exception in generated parser", e);  
    }
  }
	
  protected static java.lang.String _concat(String ...args) {
    int length = 0;
    for (java.lang.String s :args) {
      length += s.length();
    }
    java.lang.StringBuilder b = new java.lang.StringBuilder(length);
    for (java.lang.String s : args) {
      b.append(s);
    }
    return b.toString();
  }
  protected static final TypeFactory _tf = TypeFactory.getInstance();
  
  private static final IntegerMap _resultStoreIdMappings;
  private static final IntegerKeyedHashMap<IntegerList> _dontNest;
	
  protected static void _putDontNest(IntegerKeyedHashMap<IntegerList> result, int parentId, int childId) {
    IntegerList donts = result.get(childId);
    if (donts == null) {
      donts = new IntegerList();
      result.put(childId, donts);
    }
    donts.add(parentId);
  }
    
  protected int getResultStoreId(int parentId) {
    return _resultStoreIdMappings.get(parentId);
  }
    
  protected static IntegerKeyedHashMap<IntegerList> _initDontNest() {
    IntegerKeyedHashMap<IntegerList> result = new IntegerKeyedHashMap<IntegerList>(); 
    
    
    
    
    _putDontNest(result, -6341, -7159);
    
    _putDontNest(result, -7018, -7067);
    
    _putDontNest(result, -7052, -7097);
    
    _putDontNest(result, -7173, -7352);
    
    _putDontNest(result, -7128, -7142);
    
    _putDontNest(result, -6849, -7159);
    
    _putDontNest(result, -6912, -7052);
    
    _putDontNest(result, -6849, -7293);
    
    _putDontNest(result, -6976, -7374);
    
    _putDontNest(result, -6998, -7189);
    
    _putDontNest(result, -6945, -7234);
    
    _putDontNest(result, -6990, -7293);
    
    _putDontNest(result, -7165, -7310);
    
    _putDontNest(result, -7151, -7324);
    
    _putDontNest(result, -6560, -7338);
    
    _putDontNest(result, -6642, -7220);
    
    _putDontNest(result, -6582, -7248);
    
    _putDontNest(result, -6582, -7128);
    
    _putDontNest(result, -6523, -6939);
    
    _putDontNest(result, -6328, -7128);
    
    _putDontNest(result, -7767, -7929);
    
    _putDontNest(result, -6998, -7159);
    
    _putDontNest(result, -7034, -7067);
    
    _putDontNest(result, -7316, -7352);
    
    _putDontNest(result, -7189, -7352);
    
    _putDontNest(result, -6939, -7203);
    
    _putDontNest(result, -6728, -7189);
    
    _putDontNest(result, -7159, -7173);
    
    _putDontNest(result, -7052, -7338);
    
    _putDontNest(result, -7024, -7310);
    
    _putDontNest(result, -7142, -7220);
    
    _putDontNest(result, -7111, -7189);
    
    _putDontNest(result, -6939, -7352);
    
    _putDontNest(result, -6341, -7189);
    
    _putDontNest(result, -6664, -6953);
    
    _putDontNest(result, -7240, -7324);
    
    _putDontNest(result, -6930, -6984);
    
    _putDontNest(result, -175, -344);
    
    _putDontNest(result, -6728, -7173);
    
    _putDontNest(result, -7111, -7173);
    
    _putDontNest(result, -7159, -7189);
    
    _putDontNest(result, -6990, -7324);
    
    _putDontNest(result, -7044, -7234);
    
    _putDontNest(result, -7058, -7248);
    
    _putDontNest(result, -7151, -7293);
    
    _putDontNest(result, -6341, -7173);
    
    _putDontNest(result, -6363, -7310);
    
    _putDontNest(result, -6582, -7083);
    
    _putDontNest(result, -7654, -7915);
    
    _putDontNest(result, -7058, -7128);
    
    _putDontNest(result, -7767, -7957);
    
    _putDontNest(result, -6953, -7338);
    
    _putDontNest(result, -6998, -7173);
    
    _putDontNest(result, -6509, -7142);
    
    _putDontNest(result, -6912, -7248);
    
    _putDontNest(result, -6976, -7310);
    
    _putDontNest(result, -7165, -7374);
    
    _putDontNest(result, -6328, -7220);
    
    _putDontNest(result, -6606, -7352);
    
    _putDontNest(result, -6970, -7067);
    
    _putDontNest(result, -6903, -6998);
    
    _putDontNest(result, -6664, -7189);
    
    _putDontNest(result, -7024, -7374);
    
    _putDontNest(result, -7103, -7324);
    
    _putDontNest(result, -6930, -7265);
    
    _putDontNest(result, -6582, -6939);
    
    _putDontNest(result, -6523, -7128);
    
    _putDontNest(result, -6728, -6953);
    
    _putDontNest(result, -6523, -7083);
    
    _putDontNest(result, -6888, -6970);
    
    _putDontNest(result, -6998, -7111);
    
    _putDontNest(result, -6939, -7052);
    
    _putDontNest(result, -6984, -6998);
    
    _putDontNest(result, -6912, -7083);
    
    _putDontNest(result, -6888, -7352);
    
    _putDontNest(result, -6664, -7173);
    
    _putDontNest(result, -7142, -7203);
    
    _putDontNest(result, -7089, -7279);
    
    _putDontNest(result, -6888, -7203);
    
    _putDontNest(result, -6363, -7374);
    
    _putDontNest(result, -6328, -6903);
    
    _putDontNest(result, -6341, -6984);
    
    _putDontNest(result, -6341, -7111);
    
    _putDontNest(result, -6582, -6903);
    
    _putDontNest(result, -6945, -7142);
    
    _putDontNest(result, -7957, -7957);
    
    _putDontNest(result, -7943, -7943);
    
    _putDontNest(result, -6849, -6984);
    
    _putDontNest(result, -6642, -6970);
    
    _putDontNest(result, -6849, -7111);
    
    _putDontNest(result, -6796, -7220);
    
    _putDontNest(result, -7073, -7279);
    
    _putDontNest(result, -7103, -7293);
    
    _putDontNest(result, -6962, -7265);
    
    _putDontNest(result, -6642, -7203);
    
    _putDontNest(result, -6509, -7338);
    
    _putDontNest(result, -6328, -7111);
    
    _putDontNest(result, -7879, -7929);
    
    _putDontNest(result, -6945, -6998);
    
    _putDontNest(result, -7893, -7915);
    
    _putDontNest(result, -6664, -7352);
    
    _putDontNest(result, -7067, -7338);
    
    _putDontNest(result, -7120, -7293);
    
    _putDontNest(result, -7089, -7248);
    
    _putDontNest(result, -7128, -7189);
    
    _putDontNest(result, -6962, -7248);
    
    _putDontNest(result, -6523, -7310);
    
    _putDontNest(result, -7067, -7097);
    
    _putDontNest(result, -7212, -7248);
    
    _putDontNest(result, -7226, -7234);
    
    _putDontNest(result, -6728, -7142);
    
    _putDontNest(result, -6912, -7067);
    
    _putDontNest(result, -7067, -7203);
    
    _putDontNest(result, -7073, -7248);
    
    _putDontNest(result, -7189, -7374);
    
    _putDontNest(result, -7004, -7052);
    
    _putDontNest(result, -7097, -7083);
    
    _putDontNest(result, -7083, -7097);
    
    _putDontNest(result, -7142, -7159);
    
    _putDontNest(result, -6970, -7083);
    
    _putDontNest(result, -6903, -7142);
    
    _putDontNest(result, -7083, -7203);
    
    _putDontNest(result, -6930, -7248);
    
    _putDontNest(result, -6998, -7220);
    
    _putDontNest(result, -6642, -6930);
    
    _putDontNest(result, -7645, -7943);
    
    _putDontNest(result, -6953, -6953);
    
    _putDontNest(result, -6363, -6939);
    
    _putDontNest(result, -7173, -7374);
    
    _putDontNest(result, -7220, -7352);
    
    _putDontNest(result, -7302, -7374);
    
    _putDontNest(result, -7879, -7943);
    
    _putDontNest(result, -7893, -7957);
    
    _putDontNest(result, -7083, -7338);
    
    _putDontNest(result, -7097, -7352);
    
    _putDontNest(result, -7128, -7173);
    
    _putDontNest(result, -6970, -7352);
    
    _putDontNest(result, -7134, -7324);
    
    _putDontNest(result, -7120, -7310);
    
    _putDontNest(result, -6523, -7293);
    
    _putDontNest(result, -6363, -7083);
    
    _putDontNest(result, -5803, -1337);
    
    _putDontNest(result, -6984, -6970);
    
    _putDontNest(result, -6953, -7067);
    
    _putDontNest(result, -6930, -7142);
    
    _putDontNest(result, -6341, -6970);
    
    _putDontNest(result, -1575, -1585);
    
    _putDontNest(result, -7195, -7279);
    
    _putDontNest(result, -6888, -6998);
    
    _putDontNest(result, -6728, -7352);
    
    _putDontNest(result, -7058, -7279);
    
    _putDontNest(result, -7044, -7265);
    
    _putDontNest(result, -7004, -7338);
    
    _putDontNest(result, -6523, -7374);
    
    _putDontNest(result, -6509, -6998);
    
    _putDontNest(result, -6523, -6984);
    
    _putDontNest(result, -6560, -6912);
    
    _putDontNest(result, -1113, -1113);
    
    _putDontNest(result, -7083, -7052);
    
    _putDontNest(result, -7004, -7097);
    
    _putDontNest(result, -7179, -7279);
    
    _putDontNest(result, -7234, -7338);
    
    _putDontNest(result, -6664, -7142);
    
    _putDontNest(result, -6796, -6930);
    
    _putDontNest(result, -6606, -6953);
    
    _putDontNest(result, -7004, -7203);
    
    _putDontNest(result, -6363, -7352);
    
    _putDontNest(result, -6582, -7067);
    
    _putDontNest(result, -6328, -7018);
    
    _putDontNest(result, -6341, -7128);
    
    _putDontNest(result, -6962, -7142);
    
    _putDontNest(result, -6984, -7128);
    
    _putDontNest(result, -7189, -7310);
    
    _putDontNest(result, -7034, -7083);
    
    _putDontNest(result, -7067, -7052);
    
    _putDontNest(result, -7111, -7128);
    
    _putDontNest(result, -7089, -7142);
    
    _putDontNest(result, -1099, -1143);
    
    _putDontNest(result, -6849, -7128);
    
    _putDontNest(result, -7018, -7352);
    
    _putDontNest(result, -6582, -7279);
    
    _putDontNest(result, -6606, -6998);
    
    _putDontNest(result, -6328, -7034);
    
    _putDontNest(result, -6328, -7159);
    
    _putDontNest(result, -7173, -7310);
    
    _putDontNest(result, -7073, -7142);
    
    _putDontNest(result, -1099, -1127);
    
    _putDontNest(result, -7302, -7310);
    
    _putDontNest(result, -7018, -7083);
    
    _putDontNest(result, -1545, -1575);
    
    _putDontNest(result, -6509, -6953);
    
    _putDontNest(result, -6903, -7352);
    
    _putDontNest(result, -7120, -7374);
    
    _putDontNest(result, -6945, -7279);
    
    _putDontNest(result, -7034, -7352);
    
    _putDontNest(result, -6888, -6953);
    
    _putDontNest(result, -7767, -7915);
    
    _putDontNest(result, -7721, -7901);
    
    _putDontNest(result, -6984, -7159);
    
    _putDontNest(result, -7316, -7338);
    
    _putDontNest(result, -6998, -7018);
    
    _putDontNest(result, -6796, -7097);
    
    _putDontNest(result, -7052, -7203);
    
    _putDontNest(result, -6962, -7374);
    
    _putDontNest(result, -7103, -7374);
    
    _putDontNest(result, -6939, -7338);
    
    _putDontNest(result, -7052, -7083);
    
    _putDontNest(result, -6939, -7097);
    
    _putDontNest(result, -6998, -7034);
    
    _putDontNest(result, -6945, -7248);
    
    _putDontNest(result, -6984, -7189);
    
    _putDontNest(result, -7165, -7324);
    
    _putDontNest(result, -7151, -7310);
    
    _putDontNest(result, -6341, -6892);
    
    _putDontNest(result, -6606, -7142);
    
    _putDontNest(result, -7654, -7929);
    
    _putDontNest(result, -7330, -7352);
    
    _putDontNest(result, -7203, -7352);
    
    _putDontNest(result, -7195, -7248);
    
    _putDontNest(result, -6664, -6998);
    
    _putDontNest(result, -7767, -7943);
    
    _putDontNest(result, -6953, -7203);
    
    _putDontNest(result, -6930, -7374);
    
    _putDontNest(result, -6984, -7173);
    
    _putDontNest(result, -6953, -7352);
    
    _putDontNest(result, -7024, -7293);
    
    _putDontNest(result, -6953, -6970);
    
    _putDontNest(result, -6363, -7067);
    
    _putDontNest(result, -7240, -7310);
    
    _putDontNest(result, -7004, -7067);
    
    _putDontNest(result, -7179, -7248);
    
    _putDontNest(result, -6953, -7083);
    
    _putDontNest(result, -443, -467);
    
    _putDontNest(result, -6930, -6998);
    
    _putDontNest(result, -175, -330);
    
    _putDontNest(result, -6888, -7142);
    
    _putDontNest(result, -6849, -7310);
    
    _putDontNest(result, -7151, -7279);
    
    _putDontNest(result, -7165, -7265);
    
    _putDontNest(result, -6582, -7097);
    
    _putDontNest(result, -6328, -7097);
    
    _putDontNest(result, -7058, -7142);
    
    _putDontNest(result, -1545, -1560);
    
    _putDontNest(result, -6363, -6953);
    
    _putDontNest(result, -6903, -6984);
    
    _putDontNest(result, -7943, -7929);
    
    _putDontNest(result, -6962, -7310);
    
    _putDontNest(result, -7103, -7310);
    
    _putDontNest(result, -2087, -1337);
    
    _putDontNest(result, -6953, -7052);
    
    _putDontNest(result, -7097, -7067);
    
    _putDontNest(result, -6912, -7234);
    
    _putDontNest(result, -7097, -7173);
    
    _putDontNest(result, -7128, -7352);
    
    _putDontNest(result, -7151, -7374);
    
    _putDontNest(result, -6642, -7234);
    
    _putDontNest(result, -6849, -6998);
    
    _putDontNest(result, -6728, -6998);
    
    _putDontNest(result, -7103, -7279);
    
    _putDontNest(result, -7073, -7293);
    
    _putDontNest(result, -7097, -7189);
    
    _putDontNest(result, -6930, -7310);
    
    _putDontNest(result, -6962, -7279);
    
    _putDontNest(result, -6509, -7352);
    
    _putDontNest(result, -6903, -6953);
    
    _putDontNest(result, -6363, -6984);
    
    _putDontNest(result, -6984, -7111);
    
    _putDontNest(result, -7240, -7374);
    
    _putDontNest(result, -6984, -6984);
    
    _putDontNest(result, -7226, -7265);
    
    _putDontNest(result, -7265, -7338);
    
    _putDontNest(result, -7128, -7128);
    
    _putDontNest(result, -1560, -1585);
    
    _putDontNest(result, -7052, -7052);
    
    _putDontNest(result, -7212, -7279);
    
    _putDontNest(result, -6888, -7338);
    
    _putDontNest(result, -6849, -7374);
    
    _putDontNest(result, -7142, -7338);
    
    _putDontNest(result, -7134, -7234);
    
    _putDontNest(result, -7120, -7248);
    
    _putDontNest(result, -7089, -7293);
    
    _putDontNest(result, -7052, -7220);
    
    _putDontNest(result, -6976, -7293);
    
    _putDontNest(result, -1738, -6722);
    
    _putDontNest(result, -7034, -7052);
    
    _putDontNest(result, -6728, -6903);
    
    _putDontNest(result, -7067, -7083);
    
    _putDontNest(result, -7212, -7234);
    
    _putDontNest(result, -7352, -7374);
    
    _putDontNest(result, -7226, -7248);
    
    _putDontNest(result, -7907, -7957);
    
    _putDontNest(result, -7103, -7248);
    
    _putDontNest(result, -6976, -7248);
    
    _putDontNest(result, -6606, -7293);
    
    _putDontNest(result, -7018, -7052);
    
    _putDontNest(result, -7128, -7159);
    
    _putDontNest(result, -7879, -7915);
    
    _putDontNest(result, -6849, -7142);
    
    _putDontNest(result, -6664, -7338);
    
    _putDontNest(result, -6945, -7374);
    
    _putDontNest(result, -7134, -7265);
    
    _putDontNest(result, -7120, -7279);
    
    _putDontNest(result, -6509, -7310);
    
    _putDontNest(result, -2094, -6722);
    
    _putDontNest(result, -6363, -6903);
    
    _putDontNest(result, -1585, -1533);
    
    _putDontNest(result, -7220, -7338);
    
    _putDontNest(result, -6664, -7128);
    
    _putDontNest(result, -7058, -7374);
    
    _putDontNest(result, -7004, -7220);
    
    _putDontNest(result, -6509, -7293);
    
    _putDontNest(result, -6523, -7018);
    
    _putDontNest(result, -6341, -6939);
    
    _putDontNest(result, -7083, -7083);
    
    _putDontNest(result, -7097, -7097);
    
    _putDontNest(result, -6970, -7097);
    
    _putDontNest(result, -6970, -7203);
    
    _putDontNest(result, -7097, -7203);
    
    _putDontNest(result, -6341, -7220);
    
    _putDontNest(result, -6642, -7265);
    
    _putDontNest(result, -6606, -7310);
    
    _putDontNest(result, -6560, -6998);
    
    _putDontNest(result, -7645, -7957);
    
    _putDontNest(result, -6523, -7159);
    
    _putDontNest(result, -6523, -7034);
    
    _putDontNest(result, -6970, -6970);
    
    _putDontNest(result, -6642, -6912);
    
    _putDontNest(result, -6970, -7052);
    
    _putDontNest(result, -6664, -6903);
    
    _putDontNest(result, -7097, -7052);
    
    _putDontNest(result, -7234, -7352);
    
    _putDontNest(result, -7004, -7083);
    
    _putDontNest(result, -1099, -1113);
    
    _putDontNest(result, -7179, -7265);
    
    _putDontNest(result, -7111, -7352);
    
    _putDontNest(result, -6998, -7338);
    
    _putDontNest(result, -6984, -7352);
    
    _putDontNest(result, -6953, -7173);
    
    _putDontNest(result, -6363, -7338);
    
    _putDontNest(result, -6523, -7111);
    
    _putDontNest(result, -6728, -6334);
    
    _putDontNest(result, -6796, -6892);
    
    _putDontNest(result, -7195, -7265);
    
    _putDontNest(result, -6888, -6984);
    
    _putDontNest(result, -6903, -7128);
    
    _putDontNest(result, -6796, -7189);
    
    _putDontNest(result, -6728, -7338);
    
    _putDontNest(result, -7165, -7234);
    
    _putDontNest(result, -7151, -7248);
    
    _putDontNest(result, -7058, -7293);
    
    _putDontNest(result, -6945, -7310);
    
    _putDontNest(result, -7083, -7220);
    
    _putDontNest(result, -7024, -7248);
    
    _putDontNest(result, -6328, -7173);
    
    _putDontNest(result, -6582, -7173);
    
    _putDontNest(result, -6509, -7374);
    
    _putDontNest(result, -6341, -7083);
    
    _putDontNest(result, -7173, -7324);
    
    _putDontNest(result, -7052, -7067);
    
    _putDontNest(result, -7018, -7097);
    
    _putDontNest(result, -7302, -7324);
    
    _putDontNest(result, -1113, -1127);
    
    _putDontNest(result, -6523, -6953);
    
    _putDontNest(result, -6728, -7128);
    
    _putDontNest(result, -6903, -7338);
    
    _putDontNest(result, -6796, -7173);
    
    _putDontNest(result, -7018, -7203);
    
    _putDontNest(result, -7067, -7220);
    
    _putDontNest(result, -6990, -7234);
    
    _putDontNest(result, -6945, -7293);
    
    _putDontNest(result, -7058, -7310);
    
    _putDontNest(result, -7044, -7324);
    
    _putDontNest(result, -6328, -7189);
    
    _putDontNest(result, -6582, -7189);
    
    _putDontNest(result, -7018, -6970);
    
    _putDontNest(result, -6582, -6892);
    
    _putDontNest(result, -7189, -7324);
    
    _putDontNest(result, -6998, -7128);
    
    _putDontNest(result, -6939, -7067);
    
    _putDontNest(result, -1113, -1143);
    
    _putDontNest(result, -7034, -7097);
    
    _putDontNest(result, -7034, -7203);
    
    _putDontNest(result, -7159, -7352);
    
    _putDontNest(result, -6953, -7189);
    
    _putDontNest(result, -6606, -7374);
    
    _putDontNest(result, -6560, -7324);
    
    _putDontNest(result, -6363, -7128);
    
    _putDontNest(result, -6939, -7083);
    
    _putDontNest(result, -6984, -7034);
    
    _putDontNest(result, -7111, -7159);
    
    _putDontNest(result, -7240, -7293);
    
    _putDontNest(result, -6728, -7159);
    
    _putDontNest(result, -6912, -7220);
    
    _putDontNest(result, -6990, -7265);
    
    _putDontNest(result, -7089, -7374);
    
    _putDontNest(result, -6796, -7203);
    
    _putDontNest(result, -7018, -7173);
    
    _putDontNest(result, -6796, -6970);
    
    _putDontNest(result, -6363, -7018);
    
    _putDontNest(result, -6970, -7111);
    
    _putDontNest(result, -7226, -7374);
    
    _putDontNest(result, -6984, -7018);
    
    _putDontNest(result, -6796, -7083);
    
    _putDontNest(result, -7034, -7173);
    
    _putDontNest(result, -7073, -7374);
    
    _putDontNest(result, -6642, -6939);
    
    _putDontNest(result, -6939, -6970);
    
    _putDontNest(result, -6363, -7034);
    
    _putDontNest(result, -7310, -7338);
    
    _putDontNest(result, -6953, -7097);
    
    _putDontNest(result, -6888, -6903);
    
    _putDontNest(result, -7179, -7234);
    
    _putDontNest(result, -6903, -7018);
    
    _putDontNest(result, -6728, -7324);
    
    _putDontNest(result, -6849, -7324);
    
    _putDontNest(result, -7165, -7279);
    
    _putDontNest(result, -7151, -7265);
    
    _putDontNest(result, -7034, -7189);
    
    _putDontNest(result, -6328, -7083);
    
    _putDontNest(result, -6341, -7067);
    
    _putDontNest(result, -7018, -7159);
    
    _putDontNest(result, -7195, -7234);
    
    _putDontNest(result, -7203, -7338);
    
    _putDontNest(result, -7159, -7159);
    
    _putDontNest(result, -6328, -6970);
    
    _putDontNest(result, -6664, -7111);
    
    _putDontNest(result, -6903, -7034);
    
    _putDontNest(result, -6664, -6984);
    
    _putDontNest(result, -6582, -6970);
    
    _putDontNest(result, -6796, -7248);
    
    _putDontNest(result, -7024, -7279);
    
    _putDontNest(result, -7018, -7189);
    
    _putDontNest(result, -6328, -7352);
    
    _putDontNest(result, -6582, -7203);
    
    _putDontNest(result, -6328, -7203);
    
    _putDontNest(result, -6523, -6903);
    
    _putDontNest(result, -6939, -7128);
    
    _putDontNest(result, -6998, -7067);
    
    _putDontNest(result, -7034, -7159);
    
    _putDontNest(result, -7142, -7128);
    
    _putDontNest(result, -7083, -7067);
    
    _putDontNest(result, -6664, -7159);
    
    _putDontNest(result, -6888, -7128);
    
    _putDontNest(result, -7083, -7173);
    
    _putDontNest(result, -7067, -7189);
    
    _putDontNest(result, -7128, -7338);
    
    _putDontNest(result, -7089, -7310);
    
    _putDontNest(result, -6642, -7248);
    
    _putDontNest(result, -6582, -7220);
    
    _putDontNest(result, -6560, -7142);
    
    _putDontNest(result, -6582, -7052);
    
    _putDontNest(result, -6328, -7052);
    
    _putDontNest(result, -7212, -7324);
    
    _putDontNest(result, -7034, -7111);
    
    _putDontNest(result, -7226, -7310);
    
    _putDontNest(result, -7044, -7142);
    
    _putDontNest(result, -6962, -7324);
    
    _putDontNest(result, -7073, -7310);
    
    _putDontNest(result, -6970, -7173);
    
    _putDontNest(result, -6930, -7293);
    
    _putDontNest(result, -6328, -6939);
    
    _putDontNest(result, -7018, -7111);
    
    _putDontNest(result, -6998, -6984);
    
    _putDontNest(result, -7226, -7279);
    
    _putDontNest(result, -7212, -7265);
    
    _putDontNest(result, -7265, -7352);
    
    _putDontNest(result, -6796, -6939);
    
    _putDontNest(result, -6664, -7324);
    
    _putDontNest(result, -7134, -7248);
    
    _putDontNest(result, -7142, -7352);
    
    _putDontNest(result, -7120, -7234);
    
    _putDontNest(result, -6939, -7220);
    
    _putDontNest(result, -6970, -7189);
    
    _putDontNest(result, -6976, -7279);
    
    _putDontNest(result, -6642, -7083);
    
    _putDontNest(result, -7111, -7111);
    
    _putDontNest(result, -7067, -7067);
    
    _putDontNest(result, -6728, -7111);
    
    _putDontNest(result, -6728, -6984);
    
    _putDontNest(result, -6796, -7052);
    
    _putDontNest(result, -7083, -7189);
    
    _putDontNest(result, -7103, -7265);
    
    _putDontNest(result, -6930, -7324);
    
    _putDontNest(result, -7067, -7173);
    
    _putDontNest(result, -6962, -7293);
    
    _putDontNest(result, -6523, -7203);
    
    _putDontNest(result, -6523, -7352);
    
    _putDontNest(result, -7721, -7915);
    
    _putDontNest(result, -6970, -7159);
    
    _putDontNest(result, -7767, -7901);
    
    _putDontNest(result, -6888, -7324);
    
    _putDontNest(result, -7120, -7265);
    
    _putDontNest(result, -7134, -7279);
    
    _putDontNest(result, -7034, -7220);
    
    _putDontNest(result, -6509, -7324);
    
    _putDontNest(result, -2087, -1533);
    
    _putDontNest(result, -6606, -7034);
    
    _putDontNest(result, -1829, -1533);
    
    _putDontNest(result, -6606, -7159);
    
    _putDontNest(result, -7083, -7128);
    
    _putDontNest(result, -7921, -7957);
    
    _putDontNest(result, -7103, -7234);
    
    _putDontNest(result, -7018, -7220);
    
    _putDontNest(result, -6976, -7234);
    
    _putDontNest(result, -6606, -7018);
    
    _putDontNest(result, -6642, -7067);
    
    _putDontNest(result, -6990, -7142);
    
    _putDontNest(result, -6953, -7111);
    
    _putDontNest(result, -7338, -7374);
    
    _putDontNest(result, -6888, -7034);
    
    _putDontNest(result, -6888, -7159);
    
    _putDontNest(result, -6363, -7220);
    
    _putDontNest(result, -6642, -7279);
    
    _putDontNest(result, -6606, -7324);
    
    _putDontNest(result, -6560, -7374);
    
    _putDontNest(result, -6560, -6984);
    
    _putDontNest(result, -6509, -7159);
    
    _putDontNest(result, -6509, -7034);
    
    _putDontNest(result, -6328, -6953);
    
    _putDontNest(result, -6939, -6984);
    
    _putDontNest(result, -7915, -7929);
    
    _putDontNest(result, -1585, -1611);
    
    _putDontNest(result, -6888, -7018);
    
    _putDontNest(result, -6976, -7203);
    
    _putDontNest(result, -7103, -7203);
    
    _putDontNest(result, -7044, -7374);
    
    _putDontNest(result, -6509, -7018);
    
    _putDontNest(result, -6341, -7052);
    
    _putDontNest(result, -6984, -7052);
    
    _putDontNest(result, -7067, -7128);
    
    _putDontNest(result, -175, -313);
    
    _putDontNest(result, -7165, -7248);
    
    _putDontNest(result, -7151, -7234);
    
    _putDontNest(result, -7044, -7293);
    
    _putDontNest(result, -7097, -7220);
    
    _putDontNest(result, -6945, -7324);
    
    _putDontNest(result, -6970, -7220);
    
    _putDontNest(result, -7024, -7234);
    
    _putDontNest(result, -6939, -7189);
    
    _putDontNest(result, -6341, -7203);
    
    _putDontNest(result, -6341, -7097);
    
    _putDontNest(result, -6341, -6334);
    
    _putDontNest(result, -6998, -7083);
    
    _putDontNest(result, -7248, -7352);
    
    _putDontNest(result, -6984, -7097);
    
    _putDontNest(result, -7220, -7220);
    
    _putDontNest(result, -1127, -1143);
    
    _putDontNest(result, -6888, -7111);
    
    _putDontNest(result, -6796, -7279);
    
    _putDontNest(result, -6849, -7338);
    
    _putDontNest(result, -7052, -7189);
    
    _putDontNest(result, -7111, -7338);
    
    _putDontNest(result, -6998, -7352);
    
    _putDontNest(result, -6984, -7338);
    
    _putDontNest(result, -6341, -7338);
    
    _putDontNest(result, -6560, -7293);
    
    _putDontNest(result, -6509, -7111);
    
    _putDontNest(result, -6328, -7067);
    
    _putDontNest(result, -1143, -1143);
    
    _putDontNest(result, -1127, -1127);
    
    _putDontNest(result, -6796, -7067);
    
    _putDontNest(result, -7024, -7203);
    
    _putDontNest(result, -7151, -7203);
    
    _putDontNest(result, -7203, -7159);
    
    _putDontNest(result, -7159, -7338);
    
    _putDontNest(result, -7052, -7173);
    
    _putDontNest(result, -6560, -7310);
    
    _putDontNest(result, -6582, -6930);
    
    _putDontNest(result, -7179, -7324);
    
    _putDontNest(result, -6953, -7159);
    
    _putDontNest(result, -7879, -7901);
    
    _putDontNest(result, -6953, -7034);
    
    _putDontNest(result, -6990, -7248);
    
    _putDontNest(result, -7044, -7310);
    
    _putDontNest(result, -6939, -7173);
    
    _putDontNest(result, -7058, -7324);
    
    _putDontNest(result, -6606, -7111);
    
    _putDontNest(result, -7195, -7324);
    
    _putDontNest(result, -7004, -7128);
    
    _putDontNest(result, -7240, -7248);
    
    _putDontNest(result, -7324, -7324);
    
    _putDontNest(result, -6953, -7018);
    
    _putDontNest(result, -6728, -7034);
    
    _putDontNest(result, -7044, -7352);
    
    _putDontNest(result, -7058, -7338);
    
    _putDontNest(result, -7004, -7189);
    
    _putDontNest(result, -6363, -7189);
    
    _putDontNest(result, -6341, -6912);
    
    _putDontNest(result, -7310, -7352);
    
    _putDontNest(result, -6953, -7128);
    
    _putDontNest(result, -7134, -7142);
    
    _putDontNest(result, -6849, -6912);
    
    _putDontNest(result, -7097, -7111);
    
    _putDontNest(result, -6728, -7018);
    
    _putDontNest(result, -6903, -7324);
    
    _putDontNest(result, -7165, -7173);
    
    _putDontNest(result, -6990, -7279);
    
    _putDontNest(result, -6341, -6930);
    
    _putDontNest(result, -6363, -7159);
    
    _putDontNest(result, -7004, -7159);
    
    _putDontNest(result, -7212, -7374);
    
    _putDontNest(result, -6728, -6892);
    
    _putDontNest(result, -7240, -7279);
    
    _putDontNest(result, -6849, -6930);
    
    _putDontNest(result, -6796, -7234);
    
    _putDontNest(result, -7165, -7189);
    
    _putDontNest(result, -6998, -7310);
    
    _putDontNest(result, -6984, -7324);
    
    _putDontNest(result, -7024, -7265);
    
    _putDontNest(result, -6328, -7338);
    
    _putDontNest(result, -6560, -7248);
    
    _putDontNest(result, -6582, -7338);
    
    _putDontNest(result, -6523, -7142);
    
    _putDontNest(result, -6509, -6903);
    
    _putDontNest(result, -6984, -7067);
    
    _putDontNest(result, -7052, -7128);
    
    _putDontNest(result, -7173, -7234);
    
    _putDontNest(result, -7018, -7034);
    
    _putDontNest(result, -7203, -7203);
    
    _putDontNest(result, -6903, -7159);
    
    _putDontNest(result, -6903, -7293);
    
    _putDontNest(result, -6664, -7374);
    
    _putDontNest(result, -6728, -7310);
    
    _putDontNest(result, -7052, -7248);
    
    _putDontNest(result, -7120, -7220);
    
    _putDontNest(result, -7004, -7173);
    
    _putDontNest(result, -6363, -7173);
    
    _putDontNest(result, -6560, -7128);
    
    _putDontNest(result, -7189, -7234);
    
    _putDontNest(result, -7034, -7034);
    
    _putDontNest(result, -7018, -7018);
    
    _putDontNest(result, -6582, -6334);
    
    _putDontNest(result, -6664, -7034);
    
    _putDontNest(result, -6903, -7111);
    
    _putDontNest(result, -7120, -7203);
    
    _putDontNest(result, -7073, -7324);
    
    _putDontNest(result, -6509, -7128);
    
    _putDontNest(result, -7044, -6369);
    
    _putDontNest(result, -7654, -7901);
    
    _putDontNest(result, -6664, -7018);
    
    _putDontNest(result, -6953, -7220);
    
    _putDontNest(result, -7089, -7324);
    
    _putDontNest(result, -7226, -7324);
    
    _putDontNest(result, -7212, -7310);
    
    _putDontNest(result, -7203, -7220);
    
    _putDontNest(result, -6998, -7374);
    
    _putDontNest(result, -6606, -7234);
    
    _putDontNest(result, -6523, -7338);
    
    _putDontNest(result, -6363, -7111);
    
    _putDontNest(result, -7721, -7929);
    
    _putDontNest(result, -7004, -7111);
    
    _putDontNest(result, -7293, -7338);
    
    _putDontNest(result, -7279, -7352);
    
    _putDontNest(result, -6970, -7018);
    
    _putDontNest(result, -6888, -7097);
    
    _putDontNest(result, -6664, -7310);
    
    _putDontNest(result, -6728, -7374);
    
    _putDontNest(result, -6976, -7265);
    
    _putDontNest(result, -6642, -7097);
    
    _putDontNest(result, -7226, -7293);
    
    _putDontNest(result, -7097, -7159);
    
    _putDontNest(result, -6970, -7034);
    
    _putDontNest(result, -7134, -7189);
    
    _putDontNest(result, -6930, -7338);
    
    _putDontNest(result, -6606, -7265);
    
    _putDontNest(result, -6642, -7310);
    
    _putDontNest(result, -6728, -6369);
    
    _putDontNest(result, -7645, -7915);
    
    _putDontNest(result, -6903, -7052);
    
    _putDontNest(result, -6664, -7097);
    
    _putDontNest(result, -6912, -7173);
    
    _putDontNest(result, -6888, -7310);
    
    _putDontNest(result, -7083, -7248);
    
    _putDontNest(result, -7097, -7234);
    
    _putDontNest(result, -7151, -7220);
    
    _putDontNest(result, -7024, -7220);
    
    _putDontNest(result, -6970, -7234);
    
    _putDontNest(result, -7142, -7310);
    
    _putDontNest(result, -6970, -7128);
    
    _putDontNest(result, -6953, -6984);
    
    _putDontNest(result, -7097, -7128);
    
    _putDontNest(result, -7935, -7957);
    
    _putDontNest(result, -6888, -7293);
    
    _putDontNest(result, -6912, -7189);
    
    _putDontNest(result, -7089, -7338);
    
    _putDontNest(result, -7067, -7248);
    
    _putDontNest(result, -6962, -7338);
    
    _putDontNest(result, -6509, -7265);
    
    _putDontNest(result, -6560, -7111);
    
    _putDontNest(result, -6642, -6892);
    
    _putDontNest(result, -6939, -7111);
    
    _putDontNest(result, -6998, -7052);
    
    _putDontNest(result, -7195, -7374);
    
    _putDontNest(result, -7324, -7374);
    
    _putDontNest(result, -7901, -7943);
    
    _putDontNest(result, -7134, -7173);
    
    _putDontNest(result, -7073, -7338);
    
    _putDontNest(result, -7128, -7324);
    
    _putDontNest(result, -6642, -7293);
    
    _putDontNest(result, -7024, -7142);
    
    _putDontNest(result, -7179, -7374);
    
    _putDontNest(result, -7929, -7929);
    
    _putDontNest(result, -7052, -7111);
    
    _putDontNest(result, -7234, -7220);
    
    _putDontNest(result, -6796, -6984);
    
    _putDontNest(result, -6728, -7052);
    
    _putDontNest(result, -6990, -7203);
    
    _putDontNest(result, -6849, -7352);
    
    _putDontNest(result, -6796, -7265);
    
    _putDontNest(result, -7103, -7220);
    
    _putDontNest(result, -7173, -7159);
    
    _putDontNest(result, -6976, -7220);
    
    _putDontNest(result, -7018, -7234);
    
    _putDontNest(result, -6341, -7352);
    
    _putDontNest(result, -6560, -7279);
    
    _putDontNest(result, -6642, -7374);
    
    _putDontNest(result, -6560, -7018);
    
    _putDontNest(result, -7018, -7128);
    
    _putDontNest(result, -7052, -7159);
    
    _putDontNest(result, -6939, -7018);
    
    _putDontNest(result, -7203, -7173);
    
    _putDontNest(result, -7179, -7293);
    
    _putDontNest(result, -6728, -7097);
    
    _putDontNest(result, -6888, -7374);
    
    _putDontNest(result, -7189, -7159);
    
    _putDontNest(result, -7142, -7374);
    
    _putDontNest(result, -7034, -7234);
    
    _putDontNest(result, -6363, -7203);
    
    _putDontNest(result, -6560, -7034);
    
    _putDontNest(result, -6523, -6998);
    
    _putDontNest(result, -6560, -7159);
    
    _putDontNest(result, -6509, -6984);
    
    _putDontNest(result, -7034, -7128);
    
    _putDontNest(result, -6939, -7159);
    
    _putDontNest(result, -7248, -7338);
    
    _putDontNest(result, -6363, -6970);
    
    _putDontNest(result, -6939, -7034);
    
    _putDontNest(result, -7195, -7293);
    
    _putDontNest(result, -1575, -1575);
    
    _putDontNest(result, -1560, -1560);
    
    _putDontNest(result, -6984, -7083);
    
    _putDontNest(result, -6998, -7097);
    
    _putDontNest(result, -7853, -7943);
    
    _putDontNest(result, -7004, -7248);
    
    _putDontNest(result, -6976, -7142);
    
    _putDontNest(result, -7195, -7310);
    
    _putDontNest(result, -7324, -7310);
    
    _putDontNest(result, -1585, -1545);
    
    _putDontNest(result, -7103, -7142);
    
    _putDontNest(result, -1545, -1585);
    
    _putDontNest(result, -7165, -7203);
    
    _putDontNest(result, -3777, -7248);
    
    _putDontNest(result, -6606, -6984);
    
    _putDontNest(result, -6328, -6912);
    
    _putDontNest(result, -6582, -6912);
    
    _putDontNest(result, -7179, -7310);
    
    _putDontNest(result, -7203, -7189);
    
    _putDontNest(result, -7893, -7901);
    
    _putDontNest(result, -6903, -7097);
    
    _putDontNest(result, -6664, -7052);
    
    _putDontNest(result, -6728, -7293);
    
    _putDontNest(result, -6903, -7310);
    
    _putDontNest(result, -7151, -7173);
    
    _putDontNest(result, -7004, -7279);
    
    _putDontNest(result, -6953, -7234);
    
    _putDontNest(result, -6341, -7293);
    
    _putDontNest(result, -6363, -7279);
    
    _putDontNest(result, -6523, -7220);
    
    _putDontNest(result, -6642, -7052);
    
    _putDontNest(result, -6930, -6953);
    
    _putDontNest(result, -7044, -6334);
    
    _putDontNest(result, -6606, -6903);
    
    _putDontNest(result, -6990, -7159);
    
    _putDontNest(result, -7240, -7265);
    
    _putDontNest(result, -6970, -6984);
    
    _putDontNest(result, -6888, -6939);
    
    _putDontNest(result, -7058, -7352);
    
    _putDontNest(result, -7044, -7338);
    
    _putDontNest(result, -7159, -7324);
    
    _putDontNest(result, -6990, -7189);
    
    _putDontNest(result, -7654, -7943);
    
    _putDontNest(result, -6560, -7097);
    
    _putDontNest(result, -7324, -7352);
    
    _putDontNest(result, -7083, -7111);
    
    _putDontNest(result, -7004, -7034);
    
    _putDontNest(result, -7120, -7142);
    
    _putDontNest(result, -6888, -7052);
    
    _putDontNest(result, -6849, -7189);
    
    _putDontNest(result, -7052, -7234);
    
    _putDontNest(result, -7134, -7220);
    
    _putDontNest(result, -6939, -7248);
    
    _putDontNest(result, -7018, -7265);
    
    _putDontNest(result, -7111, -7324);
    
    _putDontNest(result, -6990, -7173);
    
    _putDontNest(result, -6606, -6939);
    
    _putDontNest(result, -7248, -7324);
    
    _putDontNest(result, -7067, -7111);
    
    _putDontNest(result, -7189, -7248);
    
    _putDontNest(result, -6728, -7067);
    
    _putDontNest(result, -6796, -7128);
    
    _putDontNest(result, -6849, -7173);
    
    _putDontNest(result, -6998, -7324);
    
    _putDontNest(result, -7151, -7189);
    
    _putDontNest(result, -6984, -7310);
    
    _putDontNest(result, -6945, -7338);
    
    _putDontNest(result, -6560, -7234);
    
    _putDontNest(result, -6582, -7352);
    
    _putDontNest(result, -1560, -1533);
    
    _putDontNest(result, -7338, -7338);
    
    _putDontNest(result, -7173, -7248);
    
    _putDontNest(result, -6642, -6369);
    
    _putDontNest(result, -6903, -7374);
    
    _putDontNest(result, -6664, -7293);
    
    _putDontNest(result, -6509, -7234);
    
    _putDontNest(result, -7709, -7915);
    
    _putDontNest(result, -6796, -6903);
    
    _putDontNest(result, -7134, -7203);
    
    _putDontNest(result, -7067, -7265);
    
    _putDontNest(result, -7103, -7173);
    
    _putDontNest(result, -6606, -7083);
    
    _putDontNest(result, -6849, -6953);
    
    _putDontNest(result, -7668, -7901);
    
    _putDontNest(result, -6962, -7067);
    
    _putDontNest(result, -6341, -6953);
    
    _putDontNest(result, -7067, -7159);
    
    _putDontNest(result, -7018, -6984);
    
    _putDontNest(result, -6888, -7248);
    
    _putDontNest(result, -7103, -7189);
    
    _putDontNest(result, -6606, -7128);
    
    _putDontNest(result, -7083, -7159);
    
    _putDontNest(result, -7212, -7293);
    
    _putDontNest(result, -1560, -1575);
    
    _putDontNest(result, -1575, -1560);
    
    _putDontNest(result, -6664, -7067);
    
    _putDontNest(result, -7709, -7943);
    
    _putDontNest(result, -6984, -7374);
    
    _putDontNest(result, -6912, -7203);
    
    _putDontNest(result, -6606, -7248);
    
    _putDontNest(result, -6912, -6970);
    
    _putDontNest(result, -6363, -6998);
    
    _putDontNest(result, -6930, -7067);
    
    _putDontNest(result, -6990, -7111);
    
    _putDontNest(result, -6953, -7142);
    
    _putDontNest(result, -7293, -7352);
    
    _putDontNest(result, -7279, -7338);
    
    _putDontNest(result, -6888, -7083);
    
    _putDontNest(result, -6523, -6970);
    
    _putDontNest(result, -6912, -7352);
    
    _putDontNest(result, -7165, -7220);
    
    _putDontNest(result, -7083, -7234);
    
    _putDontNest(result, -7097, -7248);
    
    _putDontNest(result, -7142, -7324);
    
    _putDontNest(result, -6970, -7248);
    
    _putDontNest(result, -6509, -7067);
    
    _putDontNest(result, -1575, -1533);
    
    _putDontNest(result, -6363, -7142);
    
    _putDontNest(result, -7279, -7324);
    
    _putDontNest(result, -7293, -7310);
    
    _putDontNest(result, -6930, -7083);
    
    _putDontNest(result, -7358, -7374);
    
    _putDontNest(result, -6953, -6998);
    
    _putDontNest(result, -6341, -6369);
    
    _putDontNest(result, -7220, -7248);
    
    _putDontNest(result, -6888, -7067);
    
    _putDontNest(result, -7120, -7189);
    
    _putDontNest(result, -6930, -7352);
    
    _putDontNest(result, -6606, -7279);
    
    _putDontNest(result, -6642, -7324);
    
    _putDontNest(result, -6523, -7173);
    
    _putDontNest(result, -6582, -6998);
    
    _putDontNest(result, -7645, -7929);
    
    _putDontNest(result, -6976, -7128);
    
    _putDontNest(result, -7134, -7159);
    
    _putDontNest(result, -7103, -7128);
    
    _putDontNest(result, -6664, -7083);
    
    _putDontNest(result, -7120, -7173);
    
    _putDontNest(result, -7073, -7352);
    
    _putDontNest(result, -7128, -7310);
    
    _putDontNest(result, -6523, -7189);
    
    _putDontNest(result, -6642, -7018);
    
    _putDontNest(result, -6606, -7067);
    
    _putDontNest(result, -7034, -7142);
    
    _putDontNest(result, -7265, -7265);
    
    _putDontNest(result, -7226, -7338);
    
    _putDontNest(result, -7212, -7352);
    
    _putDontNest(result, -6962, -7083);
    
    _putDontNest(result, -6728, -6939);
    
    _putDontNest(result, -6582, -6953);
    
    _putDontNest(result, -7089, -7352);
    
    _putDontNest(result, -7067, -7234);
    
    _putDontNest(result, -6990, -7220);
    
    _putDontNest(result, -6962, -7352);
    
    _putDontNest(result, -6509, -7279);
    
    _putDontNest(result, -6642, -7034);
    
    _putDontNest(result, -6642, -7159);
    
    _putDontNest(result, -1688, -1533);
    
    _putDontNest(result, -7018, -7142);
    
    _putDontNest(result, -7310, -7374);
    
    _putDontNest(result, -7293, -7293);
    
    _putDontNest(result, -7901, -7957);
    
    _putDontNest(result, -6796, -7111);
    
    _putDontNest(result, -6888, -7279);
    
    _putDontNest(result, -6664, -7248);
    
    _putDontNest(result, -7034, -7248);
    
    _putDontNest(result, -6849, -6970);
    
    _putDontNest(result, -6642, -7111);
    
    _putDontNest(result, -6945, -7067);
    
    _putDontNest(result, -7293, -7374);
    
    _putDontNest(result, -7024, -7128);
    
    _putDontNest(result, -1585, -1575);
    
    _putDontNest(result, -7189, -7189);
    
    _putDontNest(result, -7907, -7901);
    
    _putDontNest(result, -6796, -7034);
    
    _putDontNest(result, -7853, -7957);
    
    _putDontNest(result, -6796, -7159);
    
    _putDontNest(result, -6903, -7248);
    
    _putDontNest(result, -7018, -7248);
    
    _putDontNest(result, -6328, -7324);
    
    _putDontNest(result, -6560, -7265);
    
    _putDontNest(result, -7173, -7189);
    
    _putDontNest(result, -6912, -6998);
    
    _putDontNest(result, -6796, -7018);
    
    _putDontNest(result, -6728, -7083);
    
    _putDontNest(result, -7128, -7374);
    
    _putDontNest(result, -6363, -7248);
    
    _putDontNest(result, -6912, -6953);
    
    _putDontNest(result, -6328, -6930);
    
    _putDontNest(result, -6970, -7142);
    
    _putDontNest(result, -7097, -7142);
    
    _putDontNest(result, -7234, -7234);
    
    _putDontNest(result, -7173, -7173);
    
    _putDontNest(result, -6903, -7083);
    
    _putDontNest(result, -6664, -6939);
    
    _putDontNest(result, -6796, -7324);
    
    _putDontNest(result, -7004, -7234);
    
    _putDontNest(result, -7310, -7310);
    
    _putDontNest(result, -7853, -7915);
    
    _putDontNest(result, -7189, -7173);
    
    _putDontNest(result, -1575, -1545);
    
    _putDontNest(result, -6728, -7248);
    
    _putDontNest(result, -7024, -7173);
    
    _putDontNest(result, -7159, -7310);
    
    _putDontNest(result, -7654, -7957);
    
    _putDontNest(result, -6560, -7083);
    
    _putDontNest(result, -6509, -6939);
    
    _putDontNest(result, -7324, -7338);
    
    _putDontNest(result, -6728, -6912);
    
    _putDontNest(result, -6976, -7111);
    
    _putDontNest(result, -6990, -7034);
    
    _putDontNest(result, -7179, -7352);
    
    _putDontNest(result, -7044, -7203);
    
    _putDontNest(result, -7034, -7310);
    
    _putDontNest(result, -6970, -7374);
    
    _putDontNest(result, -6953, -7248);
    
    _putDontNest(result, -7004, -7265);
    
    _putDontNest(result, -6363, -7265);
    
    _putDontNest(result, -6560, -7203);
    
    _putDontNest(result, -6509, -7052);
    
    _putDontNest(result, -6970, -6998);
    
    _putDontNest(result, -7195, -7352);
    
    _putDontNest(result, -7203, -7248);
    
    _putDontNest(result, -6903, -7067);
    
    _putDontNest(result, -6728, -7279);
    
    _putDontNest(result, -7018, -7310);
    
    _putDontNest(result, -6945, -7352);
    
    _putDontNest(result, -6945, -6970);
    
    _putDontNest(result, -7338, -7352);
    
    _putDontNest(result, -7024, -7159);
    
    _putDontNest(result, -6912, -7142);
    
    _putDontNest(result, -6945, -7203);
    
    _putDontNest(result, -6939, -7234);
    
    _putDontNest(result, -7111, -7310);
    
    _putDontNest(result, -7024, -7189);
    
    _putDontNest(result, -6341, -7310);
    
    _putDontNest(result, -7248, -7310);
    
    _putDontNest(result, -6945, -7083);
    
    _putDontNest(result, -7165, -7159);
    
    _putDontNest(result, -6903, -7265);
    
    _putDontNest(result, -1738, -6409);
    
    _putDontNest(result, -7004, -7324);
    
    _putDontNest(result, -7159, -7374);
    
    _putDontNest(result, -6606, -7097);
    
    _putDontNest(result, -6664, -6912);
    
    _putDontNest(result, -6939, -7142);
    
    _putDontNest(result, -451, -467);
    
    _putDontNest(result, -7018, -6998);
    
    _putDontNest(result, -1560, -1545);
    
    _putDontNest(result, -6883, -1015);
    
    _putDontNest(result, -6888, -7234);
    
    _putDontNest(result, -7034, -7374);
    
    _putDontNest(result, -6970, -7310);
    
    _putDontNest(result, -7142, -7234);
    
    _putDontNest(result, -7073, -7173);
    
    _putDontNest(result, -7134, -7338);
    
    _putDontNest(result, -7120, -7352);
    
    _putDontNest(result, -6976, -7173);
    
    _putDontNest(result, -6509, -7248);
    
    _putDontNest(result, -1481, -1611);
    
    _putDontNest(result, -6560, -6903);
    
    _putDontNest(result, -7024, -7111);
    
    _putDontNest(result, -6945, -7052);
    
    _putDontNest(result, -7709, -7929);
    
    _putDontNest(result, -451, -451);
    
    _putDontNest(result, -467, -467);
    
    _putDontNest(result, -6664, -7279);
    
    _putDontNest(result, -7089, -7173);
    
    _putDontNest(result, -7018, -7374);
    
    _putDontNest(result, -6976, -7189);
    
    _putDontNest(result, -6509, -7203);
    
    _putDontNest(result, -6560, -7220);
    
    _putDontNest(result, -6642, -7338);
    
    _putDontNest(result, -6560, -7052);
    
    _putDontNest(result, -6582, -7142);
    
    _putDontNest(result, -6341, -6998);
    
    _putDontNest(result, -7120, -7128);
    
    _putDontNest(result, -6509, -6970);
    
    _putDontNest(result, -6912, -7338);
    
    _putDontNest(result, -7089, -7189);
    
    _putDontNest(result, -7128, -7248);
    
    _putDontNest(result, -7111, -7374);
    
    _putDontNest(result, -6341, -7374);
    
    _putDontNest(result, -6560, -6939);
    
    _putDontNest(result, -6509, -7083);
    
    _putDontNest(result, -6976, -7159);
    
    _putDontNest(result, -7248, -7374);
    
    _putDontNest(result, -1585, -1560);
    
    _putDontNest(result, -6642, -6334);
    
    _putDontNest(result, -7709, -7957);
    
    _putDontNest(result, -7073, -7189);
    
    _putDontNest(result, -7044, -7220);
    
    _putDontNest(result, -6328, -7374);
    
    _putDontNest(result, -6509, -7173);
    
    _putDontNest(result, -6582, -7374);
    
    _putDontNest(result, -1688, -1611);
    
    _putDontNest(result, -7004, -7142);
    
    _putDontNest(result, -7120, -7159);
    
    _putDontNest(result, -7929, -7943);
    
    _putDontNest(result, -7915, -7957);
    
    _putDontNest(result, -6606, -7189);
    
    _putDontNest(result, -6930, -6970);
    
    _putDontNest(result, -6341, -7142);
    
    _putDontNest(result, -7293, -7324);
    
    _putDontNest(result, -7279, -7310);
    
    _putDontNest(result, -7220, -7234);
    
    _putDontNest(result, -6930, -7097);
    
    _putDontNest(result, -7344, -7374);
    
    _putDontNest(result, -7901, -7929);
    
    _putDontNest(result, -6930, -7203);
    
    _putDontNest(result, -6953, -7374);
    
    _putDontNest(result, -6606, -7173);
    
    _putDontNest(result, -6523, -7279);
    
    _putDontNest(result, -6523, -6930);
    
    _putDontNest(result, -1545, -1533);
    
    _putDontNest(result, -6888, -6912);
    
    _putDontNest(result, -7279, -7293);
    
    _putDontNest(result, -6849, -6939);
    
    _putDontNest(result, -6888, -7265);
    
    _putDontNest(result, -7073, -7203);
    
    _putDontNest(result, -6664, -7234);
    
    _putDontNest(result, -6509, -7189);
    
    _putDontNest(result, -7265, -7279);
    
    _putDontNest(result, -7226, -7352);
    
    _putDontNest(result, -6939, -6998);
    
    _putDontNest(result, -6962, -7097);
    
    _putDontNest(result, -7212, -7338);
    
    _putDontNest(result, -1575, -1611);
    
    _putDontNest(result, -1829, -1611);
    
    _putDontNest(result, -6962, -7203);
    
    _putDontNest(result, -6849, -7220);
    
    _putDontNest(result, -6796, -7374);
    
    _putDontNest(result, -7089, -7203);
    
    _putDontNest(result, -7103, -7352);
    
    _putDontNest(result, -6976, -7352);
    
    _putDontNest(result, -6328, -7310);
    
    _putDontNest(result, -6582, -7310);
    
    _putDontNest(result, -6642, -6984);
    
    _putDontNest(result, -1369, -1611);
    
    _putDontNest(result, -6962, -7052);
    
    _putDontNest(result, -7067, -7142);
    
    _putDontNest(result, -6912, -6984);
    
    _putDontNest(result, -6796, -7293);
    
    _putDontNest(result, -7058, -7189);
    
    _putDontNest(result, -7159, -7248);
    
    _putDontNest(result, -6945, -7173);
    
    _putDontNest(result, -1022, -7747);
    
    _putDontNest(result, -7279, -7374);
    
    _putDontNest(result, -6849, -7083);
    
    _putDontNest(result, -6903, -7234);
    
    _putDontNest(result, -1022, -7639);
    
    _putDontNest(result, -6953, -7310);
    
    _putDontNest(result, -6990, -7338);
    
    _putDontNest(result, -6930, -7052);
    
    _putDontNest(result, -6990, -7128);
    
    _putDontNest(result, -7310, -7324);
    
    _putDontNest(result, -7853, -7929);
    
    _putDontNest(result, -1105, -1127);
    
    _putDontNest(result, -6728, -7234);
    
    _putDontNest(result, -7165, -7338);
    
    _putDontNest(result, -7151, -7352);
    
    _putDontNest(result, -7052, -7324);
    
    _putDontNest(result, -7024, -7352);
    
    _putDontNest(result, -6328, -7293);
    
    _putDontNest(result, -6363, -7234);
    
    _putDontNest(result, -6582, -7293);
    
    _putDontNest(result, -6560, -7067);
    
    _putDontNest(result, -1105, -1143);
    
    _putDontNest(result, -7083, -7142);
    
    _putDontNest(result, -7234, -7248);
    
    _putDontNest(result, -6796, -7310);
    
    _putDontNest(result, -6939, -7324);
    
    _putDontNest(result, -7058, -7173);
    
    _putDontNest(result, -7111, -7248);
    
    _putDontNest(result, -6984, -7248);
    
    _putDontNest(result, -6945, -7189);
    
    _putDontNest(result, -6998, -7234);
    
    _putDontNest(result, -6523, -7052);
    
    _putDontNest(result, -6363, -6930);
    
    _putDontNest(result, -6962, -7111);
    
    _putDontNest(result, -7195, -7338);
    
    _putDontNest(result, -7203, -7234);
    
    _putDontNest(result, -1560, -1611);
    
    _putDontNest(result, -7173, -7203);
    
    _putDontNest(result, -6849, -7265);
    
    _putDontNest(result, -6796, -7352);
    
    _putDontNest(result, -6728, -7265);
    
    _putDontNest(result, -7018, -7324);
    
    _putDontNest(result, -7097, -7374);
    
    _putDontNest(result, -6328, -7248);
    
    _putDontNest(result, -6341, -7265);
    
    _putDontNest(result, -7668, -7957);
    
    _putDontNest(result, -6363, -6912);
    
    _putDontNest(result, -7302, -7338);
    
    _putDontNest(result, -7179, -7338);
    
    _putDontNest(result, -7189, -7203);
    
    _putDontNest(result, -6888, -7220);
    
    _putDontNest(result, -7058, -7203);
    
    _putDontNest(result, -7034, -7324);
    
    _putDontNest(result, -6341, -7324);
    
    _putDontNest(result, -6990, -7067);
    
    _putDontNest(result, -6930, -7111);
    
    _putDontNest(result, -6945, -7097);
    
    _putDontNest(result, -7151, -7159);
    
    _putDontNest(result, -6849, -7067);
    
    _putDontNest(result, -7004, -7310);
    
    _putDontNest(result, -7018, -7293);
    
    _putDontNest(result, -6606, -7220);
    
    _putDontNest(result, -6606, -7052);
    
    _putDontNest(result, -6642, -6903);
    
    _putDontNest(result, -7668, -7929);
    
    _putDontNest(result, -7234, -7310);
    
    _putDontNest(result, -7352, -7352);
    
    _putDontNest(result, -6606, -7203);
    
    _putDontNest(result, -6523, -7248);
    
    _putDontNest(result, -6796, -6369);
    
    _putDontNest(result, -1386, -1533);
    
    _putDontNest(result, -6606, -6970);
    
    _putDontNest(result, -6664, -7265);
    
    _putDontNest(result, -7083, -7324);
    
    _putDontNest(result, -6930, -7189);
    
    _putDontNest(result, -7097, -7310);
    
    _putDontNest(result, -6642, -7128);
    
    _putDontNest(result, -1785, -1533);
    
    _putDontNest(result, -7220, -7324);
    
    _putDontNest(result, -6930, -7159);
    
    _putDontNest(result, -7052, -7142);
    
    _putDontNest(result, -6912, -7128);
    
    _putDontNest(result, -7721, -7943);
    
    _putDontNest(result, -7134, -7352);
    
    _putDontNest(result, -7120, -7338);
    
    _putDontNest(result, -7142, -7248);
    
    _putDontNest(result, -7067, -7293);
    
    _putDontNest(result, -6970, -7324);
    
    _putDontNest(result, -6962, -7173);
    
    _putDontNest(result, -1022, -7762);
    
    _putDontNest(result, -6509, -7097);
    
    _putDontNest(result, -1785, -1611);
    
    _putDontNest(result, -7189, -7220);
    
    _putDontNest(result, -7004, -7374);
    
    _putDontNest(result, -7058, -7220);
    
    _putDontNest(result, -6962, -7189);
    
    _putDontNest(result, -6642, -7352);
    
    _putDontNest(result, -1386, -1611);
    
    _putDontNest(result, -6962, -7159);
    
    _putDontNest(result, -7234, -7374);
    
    _putDontNest(result, -6976, -7034);
    
    _putDontNest(result, -7103, -7159);
    
    _putDontNest(result, -7173, -7220);
    
    _putDontNest(result, -7134, -7128);
    
    _putDontNest(result, -6582, -6369);
    
    _putDontNest(result, -7128, -7234);
    
    _putDontNest(result, -7067, -7324);
    
    _putDontNest(result, -6930, -7173);
    
    _putDontNest(result, -1022, -7858);
    
    _putDontNest(result, -6582, -7111);
    
    _putDontNest(result, -6341, -6903);
    
    _putDontNest(result, -6998, -7142);
    
    _putDontNest(result, -6849, -6903);
    
    _putDontNest(result, -6903, -7220);
    
    _putDontNest(result, -6606, -6930);
    
    _putDontNest(result, -6796, -6953);
    
    _putDontNest(result, -6328, -6998);
    
    _putDontNest(result, -7265, -7310);
    
    _putDontNest(result, -7024, -7052);
    
    _putDontNest(result, -6945, -7111);
    
    _putDontNest(result, -1545, -1611);
    
    _putDontNest(result, -7330, -7374);
    
    _putDontNest(result, -7915, -7943);
    
    _putDontNest(result, -175, -353);
    
    _putDontNest(result, -7929, -7957);
    
    _putDontNest(result, -6912, -7324);
    
    _putDontNest(result, -7265, -7293);
    
    _putDontNest(result, -7058, -7111);
    
    _putDontNest(result, -6796, -6998);
    
    _putDontNest(result, -6912, -7018);
    
    _putDontNest(result, -6939, -7374);
    
    _putDontNest(result, -7103, -7338);
    
    _putDontNest(result, -6976, -7338);
    
    _putDontNest(result, -6523, -7265);
    
    _putDontNest(result, -6509, -6930);
    
    _putDontNest(result, -6523, -6912);
    
    _putDontNest(result, -7279, -7279);
    
    _putDontNest(result, -7293, -7265);
    
    _putDontNest(result, -6976, -7097);
    
    _putDontNest(result, -6912, -7034);
    
    _putDontNest(result, -6888, -6930);
    
    _putDontNest(result, -6912, -7159);
    
    _putDontNest(result, -6849, -7052);
    
    _putDontNest(result, -6728, -7220);
    
    _putDontNest(result, -7052, -7374);
    
    _putDontNest(result, -6560, -7189);
    
    _putDontNest(result, -6976, -7052);
    
    _putDontNest(result, -7203, -7324);
    
    _putDontNest(result, -7240, -7352);
    
    _putDontNest(result, -6849, -7097);
    
    _putDontNest(result, -6912, -7111);
    
    _putDontNest(result, -6984, -7203);
    
    _putDontNest(result, -7111, -7203);
    
    _putDontNest(result, -7089, -7220);
    
    _putDontNest(result, -6953, -7324);
    
    _putDontNest(result, -7179, -7159);
    
    _putDontNest(result, -6849, -7203);
    
    _putDontNest(result, -6962, -7220);
    
    _putDontNest(result, -6990, -7352);
    
    _putDontNest(result, -6582, -7324);
    
    _putDontNest(result, -6642, -6998);
    
    _putDontNest(result, -7265, -7374);
    
    _putDontNest(result, -7058, -7159);
    
    _putDontNest(result, -6990, -7083);
    
    _putDontNest(result, -7212, -7220);
    
    _putDontNest(result, -7073, -7220);
    
    _putDontNest(result, -7044, -7189);
    
    _putDontNest(result, -7159, -7234);
    
    _putDontNest(result, -7195, -7159);
    
    _putDontNest(result, -6582, -7018);
    
    _putDontNest(result, -6945, -7159);
    
    _putDontNest(result, -6945, -7034);
    
    _putDontNest(result, -7024, -7097);
    
    _putDontNest(result, -6642, -6953);
    
    _putDontNest(result, -6849, -7234);
    
    _putDontNest(result, -7044, -7173);
    
    _putDontNest(result, -7111, -7234);
    
    _putDontNest(result, -6939, -7310);
    
    _putDontNest(result, -6998, -7248);
    
    _putDontNest(result, -6930, -7220);
    
    _putDontNest(result, -6984, -7234);
    
    _putDontNest(result, -6341, -7234);
    
    _putDontNest(result, -6328, -7279);
    
    _putDontNest(result, -6560, -7173);
    
    _putDontNest(result, -6582, -7034);
    
    _putDontNest(result, -6582, -7159);
    
    _putDontNest(result, -7645, -7901);
    
    _putDontNest(result, -1369, -1533);
    
    _putDontNest(result, -6945, -7018);
    
    _putDontNest(result, -1119, -1143);
    
    _putDontNest(result, -7248, -7248);
    
    _putDontNest(result, -7316, -7324);
    
    _putDontNest(result, -6664, -7220);
    
    _putDontNest(result, -7159, -7203);
    
    _putDontNest(result, -7165, -7352);
    
    _putDontNest(result, -7151, -7338);
    
    _putDontNest(result, -7024, -7338);
    
    _putDontNest(result, -7052, -7310);
    
    _putDontNest(result, -7668, -7943);
    
    _putDontNest(result, -6796, -6334);
    
    _putDontNest(result, -6341, -7034);
    
    _putDontNest(result, -5803, -1533);
    
    _putDontNest(result, -6945, -7128);
    
    _putDontNest(result, -7024, -7067);
    
    _putDontNest(result, -7220, -7374);
    
    _putDontNest(result, -7302, -7352);
    
    _putDontNest(result, -7189, -7338);
    
    _putDontNest(result, -7089, -7111);
    
    _putDontNest(result, -7179, -7203);
    
    _putDontNest(result, -6849, -7034);
    
    _putDontNest(result, -7024, -7324);
    
    _putDontNest(result, -7052, -7352);
    
    _putDontNest(result, -6509, -7220);
    
    _putDontNest(result, -6341, -7018);
    
    _putDontNest(result, -7173, -7338);
    
    _putDontNest(result, -7073, -7111);
    
    _putDontNest(result, -7195, -7203);
    
    _putDontNest(result, -6849, -7018);
    
    _putDontNest(result, -6560, -6970);
    
    _putDontNest(result, -6728, -6930);
    
    _putDontNest(result, -6796, -7338);
    
    _putDontNest(result, -6849, -7279);
    
    _putDontNest(result, -6903, -7173);
    
    _putDontNest(result, -7083, -7374);
    
    _putDontNest(result, -7004, -7293);
    
    _putDontNest(result, -6363, -7293);
    
    _putDontNest(result, -6328, -7234);
    
    _putDontNest(result, -6341, -7279);
    
    _putDontNest(result, -6582, -7234);
    
    _putDontNest(result, -6560, -7352);
    
    _putDontNest(result, -6642, -7142);
    
    _putDontNest(result, -7668, -7915);
    
    _putDontNest(result, -7234, -7324);
    
    _putDontNest(result, -7044, -7128);
    
    _putDontNest(result, -6903, -7189);
    
    _putDontNest(result, -7128, -7220);
    
    _putDontNest(result, -7067, -7374);
    
    _putDontNest(result, -5803, -1611);
    
    _putDontNest(result, -7709, -7901);
    
    _putDontNest(result, -7142, -7142);
    
    _putDontNest(result, -7165, -7293);
    
    _putDontNest(result, -6990, -7310);
    
    _putDontNest(result, -7044, -7248);
    
    _putDontNest(result, -7058, -7234);
    
    _putDontNest(result, -6363, -7324);
    
    _putDontNest(result, -7220, -7310);
    
    _putDontNest(result, -6930, -7018);
    
    _putDontNest(result, -7721, -7957);
    
    _putDontNest(result, -6523, -7234);
    
    _putDontNest(result, -6976, -7067);
    
    _putDontNest(result, -6930, -7034);
    
    _putDontNest(result, -175, -294);
    
    _putDontNest(result, -6664, -6930);
    
    _putDontNest(result, -6796, -7142);
    
    _putDontNest(result, -7128, -7203);
    
    _putDontNest(result, -6976, -7324);
    
    _putDontNest(result, -7083, -7310);
    
    _putDontNest(result, -7097, -7324);
    
    _putDontNest(result, -6945, -7220);
    
    _putDontNest(result, -6606, -7338);
    
    _putDontNest(result, -6328, -7142);
    
    _putDontNest(result, -7943, -7957);
    
    _putDontNest(result, -1481, -1533);
    
    _putDontNest(result, -7195, -7220);
    
    _putDontNest(result, -7073, -7159);
    
    _putDontNest(result, -7073, -7265);
    
    _putDontNest(result, -7067, -7310);
    
    _putDontNest(result, -6523, -7097);
    
    _putDontNest(result, -7179, -7220);
    
    _putDontNest(result, -6962, -7034);
    
    _putDontNest(result, -6998, -6998);
    
    _putDontNest(result, -7089, -7159);
    
    _putDontNest(result, -6912, -7097);
    
    _putDontNest(result, -6990, -7374);
    
    _putDontNest(result, -7089, -7265);
    
    _putDontNest(result, -6664, -6970);
    
    _putDontNest(result, -6582, -6984);
    
    _putDontNest(result, -6328, -6984);
    
    _putDontNest(result, -6939, -6953);
    
    _putDontNest(result, -6606, -6912);
    
    _putDontNest(result, -7265, -7324);
    
    _putDontNest(result, -7853, -7901);
    
    _putDontNest(result, -7073, -7128);
    
    _putDontNest(result, -6560, -6953);
    
    _putDontNest(result, -6888, -7173);
    
    _putDontNest(result, -6912, -7310);
    
    _putDontNest(result, -7073, -7234);
    
    _putDontNest(result, -6664, -7203);
    
    _putDontNest(result, -7142, -7173);
    
    _putDontNest(result, -7159, -7220);
    
    _putDontNest(result, -6523, -7067);
    
    _putDontNest(result, -6984, -7142);
    
    _putDontNest(result, -7203, -7374);
    
    _putDontNest(result, -6962, -7128);
    
    _putDontNest(result, -6945, -6984);
    
    _putDontNest(result, -7111, -7142);
    
    _putDontNest(result, -7089, -7128);
    
    _putDontNest(result, -7089, -7234);
    
    _putDontNest(result, -7134, -7293);
    
    _putDontNest(result, -7067, -7352);
    
    _putDontNest(result, -6962, -7234);
    
    _putDontNest(result, -6642, -7173);
    
    _putDontNest(result, -6523, -7324);
    
    _putDontNest(result, -6509, -6912);
    
    _putDontNest(result, -6363, -7052);
    
    _putDontNest(result, -7293, -7279);
    
    _putDontNest(result, -7279, -7265);
    
    _putDontNest(result, -7316, -7374);
    
    _putDontNest(result, -6976, -7083);
    
    _putDontNest(result, -7879, -7957);
    
    _putDontNest(result, -7083, -7352);
    
    _putDontNest(result, -7097, -7338);
    
    _putDontNest(result, -7134, -7310);
    
    _putDontNest(result, -7120, -7324);
    
    _putDontNest(result, -6970, -7338);
    
    _putDontNest(result, -6642, -7189);
    
    _putDontNest(result, -6930, -7128);
    
    _putDontNest(result, -6990, -7052);
    
    _putDontNest(result, -7907, -7915);
    
    _putDontNest(result, -7044, -7111);
    
    _putDontNest(result, -6888, -7189);
    
    _putDontNest(result, -2087, -1611);
    
    _putDontNest(result, -7142, -7189);
    
    _putDontNest(result, -7111, -7220);
    
    _putDontNest(result, -6930, -7234);
    
    _putDontNest(result, -6984, -7220);
    
    _putDontNest(result, -6728, -6970);
    
    _putDontNest(result, -6560, -6930);
    
    _putDontNest(result, -7195, -7189);
    
    _putDontNest(result, -7044, -7159);
    
    _putDontNest(result, -7226, -7220);
    
    _putDontNest(result, -6990, -7097);
    
    _putDontNest(result, -6796, -6912);
    
    _putDontNest(result, -6912, -7374);
    
    _putDontNest(result, -6728, -7203);
    
    _putDontNest(result, -6363, -7097);
    
    _putDontNest(result, -6998, -6970);
    
    _putDontNest(result, -7203, -7310);
    
    _putDontNest(result, -7179, -7189);
    
    _putDontNest(result, -1585, -1585);
    
    _putDontNest(result, -7240, -7338);
    
    _putDontNest(result, -1545, -1545);
    
    _putDontNest(result, -6998, -7203);
    
    _putDontNest(result, -7044, -7279);
    
    _putDontNest(result, -7058, -7265);
    
    _putDontNest(result, -7004, -7352);
    
    _putDontNest(result, -7316, -7310);
    
    _putDontNest(result, -7179, -7173);
    
    _putDontNest(result, -7034, -7338);
    
    _putDontNest(result, -6945, -7265);
    
    _putDontNest(result, -7134, -7374);
    
    _putDontNest(result, -6903, -6970);
    
    _putDontNest(result, -7195, -7173);
    
    _putDontNest(result, -7024, -7083);
    
    _putDontNest(result, -6849, -7248);
    
    _putDontNest(result, -7018, -7338);
    
    _putDontNest(result, -6903, -7203);
    
    _putDontNest(result, -6328, -7265);
    
    _putDontNest(result, -6341, -7248);
    
    _putDontNest(result, -6582, -7265);
   return result;
  }
    
  protected static IntegerMap _initDontNestGroups() {
    IntegerMap result = new IntegerMap();
    int resultStoreId = result.size();
    
    
    ++resultStoreId;
    
    result.putUnsafe(-7338, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-6930, resultStoreId);
    result.putUnsafe(-6903, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7018, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7240, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7943, resultStoreId);
    result.putUnsafe(-7929, resultStoreId);
    result.putUnsafe(-7915, resultStoreId);
    result.putUnsafe(-7901, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-2087, resultStoreId);
    result.putUnsafe(-5803, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7034, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-2094, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7248, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7234, resultStoreId);
    result.putUnsafe(-7220, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-3777, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-6945, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-467, resultStoreId);
    result.putUnsafe(-443, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-6998, resultStoreId);
    result.putUnsafe(-6984, resultStoreId);
    result.putUnsafe(-6970, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7024, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-1829, resultStoreId);
    result.putUnsafe(-1688, resultStoreId);
    result.putUnsafe(-1785, resultStoreId);
    result.putUnsafe(-1481, resultStoreId);
    result.putUnsafe(-1369, resultStoreId);
    result.putUnsafe(-1386, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7134, resultStoreId);
    result.putUnsafe(-7103, resultStoreId);
    result.putUnsafe(-7120, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-1585, resultStoreId);
    result.putUnsafe(-1575, resultStoreId);
    result.putUnsafe(-1560, resultStoreId);
    result.putUnsafe(-1545, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-6953, resultStoreId);
    result.putUnsafe(-6939, resultStoreId);
    result.putUnsafe(-6912, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-6883, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-6642, resultStoreId);
    result.putUnsafe(-6341, resultStoreId);
    result.putUnsafe(-6582, resultStoreId);
    result.putUnsafe(-6728, resultStoreId);
    result.putUnsafe(-6796, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7142, resultStoreId);
    result.putUnsafe(-7128, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-175, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7044, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7179, resultStoreId);
    result.putUnsafe(-7195, resultStoreId);
    result.putUnsafe(-7165, resultStoreId);
    result.putUnsafe(-7151, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-1738, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7073, resultStoreId);
    result.putUnsafe(-7089, resultStoreId);
    result.putUnsafe(-7058, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7203, resultStoreId);
    result.putUnsafe(-7173, resultStoreId);
    result.putUnsafe(-7189, resultStoreId);
    result.putUnsafe(-7159, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7097, resultStoreId);
    result.putUnsafe(-7083, resultStoreId);
    result.putUnsafe(-7052, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7316, resultStoreId);
    result.putUnsafe(-7302, resultStoreId);
    result.putUnsafe(-7324, resultStoreId);
    result.putUnsafe(-7310, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-6849, resultStoreId);
    result.putUnsafe(-6328, resultStoreId);
    result.putUnsafe(-6664, resultStoreId);
    result.putUnsafe(-6888, resultStoreId);
    result.putUnsafe(-6363, resultStoreId);
    result.putUnsafe(-6523, resultStoreId);
    result.putUnsafe(-6509, resultStoreId);
    result.putUnsafe(-6606, resultStoreId);
    result.putUnsafe(-6560, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7907, resultStoreId);
    result.putUnsafe(-7893, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-6962, resultStoreId);
    result.putUnsafe(-7004, resultStoreId);
    result.putUnsafe(-6990, resultStoreId);
    result.putUnsafe(-6976, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7921, resultStoreId);
    result.putUnsafe(-7957, resultStoreId);
    result.putUnsafe(-7935, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7265, resultStoreId);
    result.putUnsafe(-7293, resultStoreId);
    result.putUnsafe(-7279, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7330, resultStoreId);
    result.putUnsafe(-7352, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-1143, resultStoreId);
    result.putUnsafe(-1119, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-451, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-1022, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7668, resultStoreId);
    result.putUnsafe(-7654, resultStoreId);
    result.putUnsafe(-7767, resultStoreId);
    result.putUnsafe(-7879, resultStoreId);
    result.putUnsafe(-7721, resultStoreId);
    result.putUnsafe(-7645, resultStoreId);
    result.putUnsafe(-7853, resultStoreId);
    result.putUnsafe(-7709, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7358, resultStoreId);
    result.putUnsafe(-7344, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-1105, resultStoreId);
    result.putUnsafe(-1127, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7067, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7226, resultStoreId);
    result.putUnsafe(-7212, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-7111, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(-1113, resultStoreId);
    result.putUnsafe(-1099, resultStoreId);
      
    return result;
  }
  
  protected boolean hasNestingRestrictions(String name){
		return (_dontNest.size() != 0); // TODO Make more specific.
  }
    
  protected IntegerList getFilteredParents(int childId) {
		return _dontNest.get(childId);
  }
    
  // initialize priorities     
  static {
    _dontNest = _initDontNest();
    _resultStoreIdMappings = _initDontNestGroups();
  }
    
  // Production declarations
	
  private static final IConstructor prod__negative_$Pattern__lit___$layouts_LAYOUTLIST_argument_$Pattern_ = (IConstructor) _read("prod(label(\"negative\",sort(\"Pattern\")),[lit(\"-\"),layouts(\"LAYOUTLIST\"),label(\"argument\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor prod__default_$PreModule__header_$Header_$layouts_LAYOUTLIST_empty_$layouts_LAYOUTLIST_rest_$Rest_ = (IConstructor) _read("prod(label(\"default\",sort(\"PreModule\")),[label(\"header\",sort(\"Header\")),layouts(\"LAYOUTLIST\"),conditional(empty(),{\\not-follow(keywords(\"HeaderKeyword\"))}),layouts(\"LAYOUTLIST\"),label(\"rest\",lex(\"Rest\"))],{})", Factory.Production);
  private static final IConstructor prod__parametric_$UserType__name_$QualifiedName_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"parametric\",sort(\"UserType\")),[conditional(label(\"name\",sort(\"QualifiedName\")),{follow(lit(\"[\"))}),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"parameters\",\\iter-seps(sort(\"Type\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__$RegExpLiteral__lit___47_iter_star__$RegExp_lit___47_$RegExpModifier_ = (IConstructor) _read("prod(lex(\"RegExpLiteral\"),[lit(\"/\"),\\iter-star(lex(\"RegExp\")),lit(\"/\"),lex(\"RegExpModifier\")],{})", Factory.Production);
  private static final IConstructor prod__midTemplate_$StringTail__mid_$MidStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringTail_ = (IConstructor) _read("prod(label(\"midTemplate\",sort(\"StringTail\")),[label(\"mid\",lex(\"MidStringChars\")),layouts(\"LAYOUTLIST\"),label(\"template\",sort(\"StringTemplate\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"StringTail\"))],{})", Factory.Production);
  private static final IConstructor prod__$HeaderKeyword__lit_start_ = (IConstructor) _read("prod(keywords(\"HeaderKeyword\"),[lit(\"start\")],{})", Factory.Production);
  private static final IConstructor prod__default_$ImportedModule__name_$QualifiedName_ = (IConstructor) _read("prod(label(\"default\",sort(\"ImportedModule\")),[label(\"name\",sort(\"QualifiedName\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"QualifiedName\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__globalDirective_$Statement__lit_global_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_names_iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"globalDirective\",sort(\"Statement\")),[lit(\"global\"),layouts(\"LAYOUTLIST\"),label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"names\",\\iter-seps(sort(\"QualifiedName\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__shell_$Command__lit___58_$layouts_LAYOUTLIST_command_$ShellCommand_ = (IConstructor) _read("prod(label(\"shell\",sort(\"Command\")),[lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"command\",sort(\"ShellCommand\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_fail__char_class___range__102_102_char_class___range__97_97_char_class___range__105_105_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"fail\"),[\\char-class([range(102,102)]),\\char-class([range(97,97)]),\\char-class([range(105,105)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__labeled_$DataTarget__label_$Name_$layouts_LAYOUTLIST_lit___58_ = (IConstructor) _read("prod(label(\"labeled\",sort(\"DataTarget\")),[label(\"label\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\":\")],{})", Factory.Production);
  private static final IConstructor prod__slice_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_optFirst_$OptionalExpression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_optLast_$OptionalExpression_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"slice\",sort(\"Expression\")),[conditional(label(\"expression\",sort(\"Expression\")),{except(\"transitiveReflexiveClosure\"),except(\"transitiveClosure\"),except(\"isDefined\")}),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"optFirst\",sort(\"OptionalExpression\")),layouts(\"LAYOUTLIST\"),lit(\"..\"),layouts(\"LAYOUTLIST\"),label(\"optLast\",sort(\"OptionalExpression\")),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor regular__iter_star__$LAYOUT = (IConstructor) _read("regular(\\iter-star(lex(\"LAYOUT\")))", Factory.Production);
  private static final IConstructor prod__expression_$Statement__expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"expression\",sort(\"Statement\")),[label(\"expression\",conditional(sort(\"Expression\"),{except(\"visit\"),except(\"nonEmptyBlock\")})),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__default_$LocationLiteral__protocolPart_$ProtocolPart_$layouts_LAYOUTLIST_pathPart_$PathPart_ = (IConstructor) _read("prod(label(\"default\",sort(\"LocationLiteral\")),[label(\"protocolPart\",sort(\"ProtocolPart\")),layouts(\"LAYOUTLIST\"),label(\"pathPart\",sort(\"PathPart\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___45_61__char_class___range__45_45_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"-=\"),[\\char-class([range(45,45)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__listRelation_$BasicType__lit_lrel_ = (IConstructor) _read("prod(label(\"listRelation\",sort(\"BasicType\")),[lit(\"lrel\")],{})", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor prod__match_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_61_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"match\",sort(\"Expression\")),[label(\"pattern\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\":=\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__expression_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__Foldable_tag__breakable___123_101_120_112_114_101_115_115_105_111_110_125 = (IConstructor) _read("prod(label(\"expression\",sort(\"FunctionDeclaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),label(\"signature\",sort(\"Signature\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(Foldable()),tag(breakable(\"{expression}\"))})", Factory.Production);
  private static final IConstructor prod__timeLiteral_$DateTimeLiteral__time_$JustTime_ = (IConstructor) _read("prod(label(\"timeLiteral\",sort(\"DateTimeLiteral\")),[label(\"time\",lex(\"JustTime\"))],{})", Factory.Production);
  private static final IConstructor prod__dateLiteral_$DateTimeLiteral__date_$JustDate_ = (IConstructor) _read("prod(label(\"dateLiteral\",sort(\"DateTimeLiteral\")),[label(\"date\",lex(\"JustDate\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_assoc_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"assoc\")],{})", Factory.Production);
  private static final IConstructor prod__noThrows_$Signature__modifiers_$FunctionModifiers_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_parameters_$Parameters_ = (IConstructor) _read("prod(label(\"noThrows\",sort(\"Signature\")),[label(\"modifiers\",sort(\"FunctionModifiers\")),layouts(\"LAYOUTLIST\"),label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),label(\"parameters\",sort(\"Parameters\"))],{})", Factory.Production);
  private static final IConstructor regular__opt__char_class___range__48_57 = (IConstructor) _read("regular(opt(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor prod__list_$Expression__lit___91_$layouts_LAYOUTLIST_elements_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"list\",sort(\"Expression\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"elements\",\\iter-star-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__lit_function__char_class___range__102_102_char_class___range__117_117_char_class___range__110_110_char_class___range__99_99_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"function\"),[\\char-class([range(102,102)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__lit_int__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"int\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__name_$Field__fieldName_$Name_ = (IConstructor) _read("prod(label(\"name\",sort(\"Field\")),[label(\"fieldName\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__topDownBreak_$Strategy__lit_top_down_break_ = (IConstructor) _read("prod(label(\"topDownBreak\",sort(\"Strategy\")),[lit(\"top-down-break\")],{})", Factory.Production);
  private static final IConstructor prod__$JustTime__lit___36_84_$TimePartNoTZ_$TimeZonePart_ = (IConstructor) _read("prod(lex(\"JustTime\"),[lit(\"$T\"),lex(\"TimePartNoTZ\"),lex(\"TimeZonePart\")],{})", Factory.Production);
  private static final IConstructor prod__dateTime_$Literal__dateTimeLiteral_$DateTimeLiteral_ = (IConstructor) _read("prod(label(\"dateTime\",sort(\"Literal\")),[label(\"dateTimeLiteral\",sort(\"DateTimeLiteral\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__filter_$Statement__lit_filter_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"filter\",sort(\"Statement\")),[lit(\"filter\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__lit_when__char_class___range__119_119_char_class___range__104_104_char_class___range__101_101_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"when\"),[\\char-class([range(119,119)]),\\char-class([range(104,104)]),\\char-class([range(101,101)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"type\"),[\\char-class([range(116,116)]),\\char-class([range(121,121)]),\\char-class([range(112,112)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__division_$Assignment__lit___47_61_ = (IConstructor) _read("prod(label(\"division\",sort(\"Assignment\")),[lit(\"/=\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_catch_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"catch\")],{})", Factory.Production);
  private static final IConstructor prod__lit_modules__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"modules\"),[\\char-class([range(109,109)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(101,101)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_catch__char_class___range__99_99_char_class___range__97_97_char_class___range__116_116_char_class___range__99_99_char_class___range__104_104_ = (IConstructor) _read("prod(lit(\"catch\"),[\\char-class([range(99,99)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(99,99)]),\\char-class([range(104,104)])],{})", Factory.Production);
  private static final IConstructor prod__$Name__seq___char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_ = (IConstructor) _read("prod(lex(\"Name\"),[conditional(seq([conditional(\\char-class([range(65,90),range(95,95),range(97,122)]),{\\not-precede(\\char-class([range(65,90),range(95,95),range(97,122)]))}),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})]),{delete(keywords(\"RascalKeywords\"))})],{})", Factory.Production);
  private static final IConstructor prod__dataAbstract_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_data_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"dataAbstract\",sort(\"Declaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),lit(\"data\"),layouts(\"LAYOUTLIST\"),label(\"user\",sort(\"UserType\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__lessThanOrEq_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"lessThanOrEq\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"\\<=\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__lit_case__char_class___range__99_99_char_class___range__97_97_char_class___range__115_115_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"case\"),[\\char-class([range(99,99)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_data_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"data\")],{})", Factory.Production);
  private static final IConstructor prod__alias_$Kind__lit_alias_ = (IConstructor) _read("prod(label(\"alias\",sort(\"Kind\")),[lit(\"alias\")],{})", Factory.Production);
  private static final IConstructor prod__lit___47_61__char_class___range__47_47_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"/=\"),[\\char-class([range(47,47)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__default_$Declarator__type_$Type_$layouts_LAYOUTLIST_variables_iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"default\",sort(\"Declarator\")),[label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"variables\",\\iter-seps(sort(\"Variable\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_dynamic_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"dynamic\")],{})", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57 = (IConstructor) _read("regular(seq([\\char-class([range(44,44),range(46,46)]),\\char-class([range(48,57)]),opt(seq([\\char-class([range(48,57)]),opt(\\char-class([range(48,57)]))]))]))", Factory.Production);
  private static final IConstructor prod__quit_$ShellCommand__lit_quit_ = (IConstructor) _read("prod(label(\"quit\",sort(\"ShellCommand\")),[lit(\"quit\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$TypeVar__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"TypeVar\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__conditional_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit_when_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__Foldable_tag__breakable___123_101_120_112_114_101_115_115_105_111_110_44_99_111_110_100_105_116_105_111_110_115_125 = (IConstructor) _read("prod(label(\"conditional\",sort(\"FunctionDeclaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),label(\"signature\",sort(\"Signature\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"when\"),layouts(\"LAYOUTLIST\"),label(\"conditions\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(Foldable()),tag(breakable(\"{expression,conditions}\"))})", Factory.Production);
  private static final IConstructor prod__lit___47_47__char_class___range__47_47_char_class___range__47_47_ = (IConstructor) _read("prod(lit(\"//\"),[\\char-class([range(47,47)]),\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__lit_help__char_class___range__104_104_char_class___range__101_101_char_class___range__108_108_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"help\"),[\\char-class([range(104,104)]),\\char-class([range(101,101)]),\\char-class([range(108,108)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__default_$TypeArg__type_$Type_ = (IConstructor) _read("prod(label(\"default\",sort(\"TypeArg\")),[label(\"type\",sort(\"Type\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_num__char_class___range__110_110_char_class___range__117_117_char_class___range__109_109_ = (IConstructor) _read("prod(lit(\"num\"),[\\char-class([range(110,110)]),\\char-class([range(117,117)]),\\char-class([range(109,109)])],{})", Factory.Production);
  private static final IConstructor prod__lit_tag__char_class___range__116_116_char_class___range__97_97_char_class___range__103_103_ = (IConstructor) _read("prod(lit(\"tag\"),[\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(103,103)])],{})", Factory.Production);
  private static final IConstructor prod__dynamic_$LocalVariableDeclaration__lit_dynamic_$layouts_LAYOUTLIST_declarator_$Declarator_ = (IConstructor) _read("prod(label(\"dynamic\",sort(\"LocalVariableDeclaration\")),[lit(\"dynamic\"),layouts(\"LAYOUTLIST\"),label(\"declarator\",sort(\"Declarator\"))],{})", Factory.Production);
  private static final IConstructor regular__iter__char_class___range__48_57 = (IConstructor) _read("regular(iter(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor regular__iter__char_class___range__48_55 = (IConstructor) _read("regular(iter(\\char-class([range(48,55)])))", Factory.Production);
  private static final IConstructor prod__equivalence_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_61_61_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"equivalence\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"\\<==\\>\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Mapping__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(\\parameterized-sort(\"Mapping\",[sort(\"Pattern\")]),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__negative_$Expression__lit___$layouts_LAYOUTLIST_argument_$Expression_ = (IConstructor) _read("prod(label(\"negative\",sort(\"Expression\")),[lit(\"-\"),layouts(\"LAYOUTLIST\"),label(\"argument\",sort(\"Expression\"))],{})", Factory.Production);
  private static final IConstructor prod__default_$QualifiedName__names_iter_seps__$Name__$layouts_LAYOUTLIST_lit___58_58_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"default\",sort(\"QualifiedName\")),[conditional(label(\"names\",\\iter-seps(lex(\"Name\"),[layouts(\"LAYOUTLIST\"),lit(\"::\"),layouts(\"LAYOUTLIST\")])),{\\not-follow(lit(\"::\"))})],{})", Factory.Production);
  private static final IConstructor prod__$PreStringChars__char_class___range__34_34_iter_star__$StringCharacter_char_class___range__60_60__tag__category___67_111_110_115_116_97_110_116 = (IConstructor) _read("prod(lex(\"PreStringChars\"),[\\char-class([range(34,34)]),\\iter-star(lex(\"StringCharacter\")),\\char-class([range(60,60)])],{tag(category(\"Constant\"))})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Import__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Import\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__lit___58_47_47__char_class___range__58_58_char_class___range__47_47_char_class___range__47_47_ = (IConstructor) _read("prod(lit(\"://\"),[\\char-class([range(58,58)]),\\char-class([range(47,47)]),\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__reifiedType_$Expression__lit_type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_symbol_$Expression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_definitions_$Expression_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"reifiedType\",sort(\"Expression\")),[lit(\"type\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"symbol\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\"),label(\"definitions\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__dateTime_$BasicType__lit_datetime_ = (IConstructor) _read("prod(label(\"dateTime\",sort(\"BasicType\")),[lit(\"datetime\")],{})", Factory.Production);
  private static final IConstructor prod__structured_$Type__structured_$StructuredType_ = (IConstructor) _read("prod(label(\"structured\",sort(\"Type\")),[label(\"structured\",sort(\"StructuredType\"))],{})", Factory.Production);
  private static final IConstructor prod__$JustDate__lit___36_$DatePart_ = (IConstructor) _read("prod(lex(\"JustDate\"),[lit(\"$\"),lex(\"DatePart\")],{})", Factory.Production);
  private static final IConstructor prod__template_$StringLiteral__pre_$PreStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringTail_ = (IConstructor) _read("prod(label(\"template\",sort(\"StringLiteral\")),[label(\"pre\",lex(\"PreStringChars\")),layouts(\"LAYOUTLIST\"),label(\"template\",sort(\"StringTemplate\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"StringTail\"))],{})", Factory.Production);
  private static final IConstructor prod__assignment_$Statement__assignable_$Assignable_$layouts_LAYOUTLIST_operator_$Assignment_$layouts_LAYOUTLIST_statement_$Statement__tag__breakable = (IConstructor) _read("prod(label(\"assignment\",sort(\"Statement\")),[label(\"assignable\",sort(\"Assignable\")),layouts(\"LAYOUTLIST\"),label(\"operator\",sort(\"Assignment\")),layouts(\"LAYOUTLIST\"),label(\"statement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__while_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable = (IConstructor) _read("prod(label(\"while\",sort(\"Statement\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),lit(\"while\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"conditions\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),label(\"body\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{tag(breakable())})", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__100_100_range__105_105_range__109_109_range__115_115 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(100,100),range(105,105),range(109,109),range(115,115)])))", Factory.Production);
  private static final IConstructor prod__lit___33_62_62__char_class___range__33_33_char_class___range__62_62_char_class___range__62_62_ = (IConstructor) _read("prod(lit(\"!\\>\\>\"),[\\char-class([range(33,33)]),\\char-class([range(62,62)]),\\char-class([range(62,62)])],{})", Factory.Production);
  private static final IConstructor prod__default_$Renaming__from_$Name_$layouts_LAYOUTLIST_lit___61_62_$layouts_LAYOUTLIST_to_$Name_ = (IConstructor) _read("prod(label(\"default\",sort(\"Renaming\")),[label(\"from\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"=\\>\"),layouts(\"LAYOUTLIST\"),label(\"to\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__basic_$Type__basic_$BasicType_ = (IConstructor) _read("prod(label(\"basic\",sort(\"Type\")),[label(\"basic\",sort(\"BasicType\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___46_46__char_class___range__46_46_char_class___range__46_46_ = (IConstructor) _read("prod(lit(\"..\"),[\\char-class([range(46,46)]),\\char-class([range(46,46)])],{})", Factory.Production);
  private static final IConstructor prod__ascii_$UnicodeEscape__lit___92_char_class___range__97_97_char_class___range__48_55_char_class___range__48_57_range__65_70_range__97_102_ = (IConstructor) _read("prod(label(\"ascii\",lex(\"UnicodeEscape\")),[lit(\"\\\\\"),\\char-class([range(97,97)]),\\char-class([range(48,55)]),\\char-class([range(48,57),range(65,70),range(97,102)])],{})", Factory.Production);
  private static final IConstructor prod__undeclare_$ShellCommand__lit_undeclare_$layouts_LAYOUTLIST_name_$QualifiedName_ = (IConstructor) _read("prod(label(\"undeclare\",sort(\"ShellCommand\")),[lit(\"undeclare\"),layouts(\"LAYOUTLIST\"),label(\"name\",sort(\"QualifiedName\"))],{})", Factory.Production);
  private static final IConstructor prod__variable_$Type__typeVar_$TypeVar_ = (IConstructor) _read("prod(label(\"variable\",sort(\"Type\")),[label(\"typeVar\",sort(\"TypeVar\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_throw__char_class___range__116_116_char_class___range__104_104_char_class___range__114_114_char_class___range__111_111_char_class___range__119_119_ = (IConstructor) _read("prod(lit(\"throw\"),[\\char-class([range(116,116)]),\\char-class([range(104,104)]),\\char-class([range(114,114)]),\\char-class([range(111,111)]),\\char-class([range(119,119)])],{})", Factory.Production);
  private static final IConstructor prod__equals_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___61_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"equals\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"==\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__fieldUpdate_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_key_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_replacement_$Expression_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"fieldUpdate\",sort(\"Expression\")),[label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"key\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"replacement\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__mid_$PathTail__mid_$MidPathChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$PathTail_ = (IConstructor) _read("prod(label(\"mid\",sort(\"PathTail\")),[label(\"mid\",lex(\"MidPathChars\")),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"PathTail\"))],{})", Factory.Production);
  private static final IConstructor prod__product_$Assignment__lit___42_61_ = (IConstructor) _read("prod(label(\"product\",sort(\"Assignment\")),[lit(\"*=\")],{})", Factory.Production);
  private static final IConstructor prod__tuple_$Assignable__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_ = (IConstructor) _read("prod(label(\"tuple\",sort(\"Assignable\")),[lit(\"\\<\"),layouts(\"LAYOUTLIST\"),label(\"elements\",\\iter-seps(sort(\"Assignable\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"\\>\")],{})", Factory.Production);
  private static final IConstructor prod__right_$Assoc__lit_right_ = (IConstructor) _read("prod(label(\"right\",sort(\"Assoc\")),[lit(\"right\")],{})", Factory.Production);
  private static final IConstructor prod__lit_on__char_class___range__111_111_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"on\"),[\\char-class([range(111,111)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__$StringCharacter__$UnicodeEscape_ = (IConstructor) _read("prod(lex(\"StringCharacter\"),[lex(\"UnicodeEscape\")],{})", Factory.Production);
  private static final IConstructor prod__lit___47_42__char_class___range__47_47_char_class___range__42_42_ = (IConstructor) _read("prod(lit(\"/*\"),[\\char-class([range(47,47)]),\\char-class([range(42,42)])],{})", Factory.Production);
  private static final IConstructor prod__loc_$BasicType__lit_loc_ = (IConstructor) _read("prod(label(\"loc\",sort(\"BasicType\")),[lit(\"loc\")],{})", Factory.Production);
  private static final IConstructor prod__$DecimalIntegerLiteral__lit_0_ = (IConstructor) _read("prod(lex(\"DecimalIntegerLiteral\"),[conditional(lit(\"0\"),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__renamings_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_renamings_$Renamings_ = (IConstructor) _read("prod(label(\"renamings\",sort(\"ImportedModule\")),[label(\"name\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),label(\"renamings\",sort(\"Renamings\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_is__char_class___range__105_105_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"is\"),[\\char-class([range(105,105)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_it__char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"it\"),[\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_loc__char_class___range__108_108_char_class___range__111_111_char_class___range__99_99_ = (IConstructor) _read("prod(lit(\"loc\"),[\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(99,99)])],{})", Factory.Production);
  private static final IConstructor prod__insertBefore_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"insertBefore\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"\\>\\>\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__default_$Tags__tags_iter_star_seps__$Tag__$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"default\",sort(\"Tags\")),[label(\"tags\",\\iter-star-seps(sort(\"Tag\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_any_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"any\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_all_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"all\")],{})", Factory.Production);
  private static final IConstructor prod__lit_in__char_class___range__105_105_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"in\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__negation_$Expression__lit___33_$layouts_LAYOUTLIST_argument_$Expression_ = (IConstructor) _read("prod(label(\"negation\",sort(\"Expression\")),[lit(\"!\"),layouts(\"LAYOUTLIST\"),label(\"argument\",conditional(sort(\"Expression\"),{except(\"noMatch\"),except(\"match\")}))],{})", Factory.Production);
  private static final IConstructor prod__declaration_$EvalCommand__declaration_$Declaration_ = (IConstructor) _read("prod(label(\"declaration\",sort(\"EvalCommand\")),[label(\"declaration\",sort(\"Declaration\"))],{})", Factory.Production);
  private static final IConstructor prod__integer_$Literal__integerLiteral_$IntegerLiteral_ = (IConstructor) _read("prod(label(\"integer\",sort(\"Literal\")),[label(\"integerLiteral\",sort(\"IntegerLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__expression_$OptionalExpression__expression_$Expression_ = (IConstructor) _read("prod(label(\"expression\",sort(\"OptionalExpression\")),[label(\"expression\",sort(\"Expression\"))],{})", Factory.Production);
  private static final IConstructor prod__$MidStringChars__char_class___range__62_62_iter_star__$StringCharacter_char_class___range__60_60__tag__category___67_111_110_115_116_97_110_116 = (IConstructor) _read("prod(lex(\"MidStringChars\"),[\\char-class([range(62,62)]),\\iter-star(lex(\"StringCharacter\")),\\char-class([range(60,60)])],{tag(category(\"Constant\"))})", Factory.Production);
  private static final IConstructor prod__labeled_$Target__name_$Name_ = (IConstructor) _read("prod(label(\"labeled\",sort(\"Target\")),[label(\"name\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_right__char_class___range__114_114_char_class___range__105_105_char_class___range__103_103_char_class___range__104_104_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"right\"),[\\char-class([range(114,114)]),\\char-class([range(105,105)]),\\char-class([range(103,103)]),\\char-class([range(104,104)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_declarations__char_class___range__100_100_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__114_114_char_class___range__97_97_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"declarations\"),[\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__ifThenElse_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_thenStatement_$Statement_$layouts_LAYOUTLIST_lit_else_$layouts_LAYOUTLIST_elseStatement_$Statement__tag__breakable = (IConstructor) _read("prod(label(\"ifThenElse\",sort(\"Statement\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),lit(\"if\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"conditions\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),label(\"thenStatement\",sort(\"Statement\")),layouts(\"LAYOUTLIST\"),lit(\"else\"),layouts(\"LAYOUTLIST\"),label(\"elseStatement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_case_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"case\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_layout_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"layout\")],{})", Factory.Production);
  private static final IConstructor prod__topDown_$Strategy__lit_top_down_ = (IConstructor) _read("prod(label(\"topDown\",sort(\"Strategy\")),[lit(\"top-down\")],{})", Factory.Production);
  private static final IConstructor prod__append_$Statement__lit_append_$layouts_LAYOUTLIST_dataTarget_$DataTarget_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable = (IConstructor) _read("prod(label(\"append\",sort(\"Statement\")),[lit(\"append\"),layouts(\"LAYOUTLIST\"),label(\"dataTarget\",sort(\"DataTarget\")),layouts(\"LAYOUTLIST\"),label(\"statement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{assoc(\\non-assoc()),tag(breakable())})", Factory.Production);
  private static final IConstructor prod__lit___59__char_class___range__59_59_ = (IConstructor) _read("prod(lit(\";\"),[\\char-class([range(59,59)])],{})", Factory.Production);
  private static final IConstructor prod__default_$Parameters__lit___40_$layouts_LAYOUTLIST_formals_$Formals_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"default\",sort(\"Parameters\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"formals\",sort(\"Formals\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_bag_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"bag\")],{})", Factory.Production);
  private static final IConstructor prod__lit___58__char_class___range__58_58_ = (IConstructor) _read("prod(lit(\":\"),[\\char-class([range(58,58)])],{})", Factory.Production);
  private static final IConstructor prod__lit___61__char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"=\"),[\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit_data__char_class___range__100_100_char_class___range__97_97_char_class___range__116_116_char_class___range__97_97_ = (IConstructor) _read("prod(lit(\"data\"),[\\char-class([range(100,100)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(97,97)])],{})", Factory.Production);
  private static final IConstructor prod__lit___60__char_class___range__60_60_ = (IConstructor) _read("prod(lit(\"\\<\"),[\\char-class([range(60,60)])],{})", Factory.Production);
  private static final IConstructor prod__visit_$Expression__label_$Label_$layouts_LAYOUTLIST_visit_$Visit_ = (IConstructor) _read("prod(label(\"visit\",sort(\"Expression\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),label(\"visit\",sort(\"Visit\"))],{})", Factory.Production);
  private static final IConstructor prod__statement_$EvalCommand__statement_$Statement_ = (IConstructor) _read("prod(label(\"statement\",sort(\"EvalCommand\")),[label(\"statement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"visit\"),except(\"variableDeclaration\")}))],{})", Factory.Production);
  private static final IConstructor prod__lit___63__char_class___range__63_63_ = (IConstructor) _read("prod(lit(\"?\"),[\\char-class([range(63,63)])],{})", Factory.Production);
  private static final IConstructor prod__default_$LocalVariableDeclaration__declarator_$Declarator_ = (IConstructor) _read("prod(label(\"default\",sort(\"LocalVariableDeclaration\")),[label(\"declarator\",sort(\"Declarator\"))],{})", Factory.Production);
  private static final IConstructor regular__opt__char_class___range__43_43_range__45_45 = (IConstructor) _read("regular(opt(\\char-class([range(43,43),range(45,45)])))", Factory.Production);
  private static final IConstructor prod__lit___33_60_60__char_class___range__33_33_char_class___range__60_60_char_class___range__60_60_ = (IConstructor) _read("prod(lit(\"!\\<\\<\"),[\\char-class([range(33,33)]),\\char-class([range(60,60)]),\\char-class([range(60,60)])],{})", Factory.Production);
  private static final IConstructor prod__lit___62__char_class___range__62_62_ = (IConstructor) _read("prod(lit(\"\\>\"),[\\char-class([range(62,62)])],{})", Factory.Production);
  private static final IConstructor prod__history_$ShellCommand__lit_history_ = (IConstructor) _read("prod(label(\"history\",sort(\"ShellCommand\")),[lit(\"history\")],{})", Factory.Production);
  private static final IConstructor prod__lit_0__char_class___range__48_48_ = (IConstructor) _read("prod(lit(\"0\"),[\\char-class([range(48,48)])],{})", Factory.Production);
  private static final IConstructor prod__lit___41__char_class___range__41_41_ = (IConstructor) _read("prod(lit(\")\"),[\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__all_$Kind__lit_all_ = (IConstructor) _read("prod(label(\"all\",sort(\"Kind\")),[lit(\"all\")],{})", Factory.Production);
  private static final IConstructor prod__decimalIntegerLiteral_$IntegerLiteral__decimal_$DecimalIntegerLiteral_ = (IConstructor) _read("prod(label(\"decimalIntegerLiteral\",sort(\"IntegerLiteral\")),[label(\"decimal\",lex(\"DecimalIntegerLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___40__char_class___range__40_40_ = (IConstructor) _read("prod(lit(\"(\"),[\\char-class([range(40,40)])],{})", Factory.Production);
  private static final IConstructor prod__assertWithMessage_$Statement__lit_assert_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_message_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"assertWithMessage\",sort(\"Statement\")),[lit(\"assert\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"message\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__lit___43__char_class___range__43_43_ = (IConstructor) _read("prod(lit(\"+\"),[\\char-class([range(43,43)])],{})", Factory.Production);
  private static final IConstructor prod__lit___42__char_class___range__42_42_ = (IConstructor) _read("prod(lit(\"*\"),[\\char-class([range(42,42)])],{})", Factory.Production);
  private static final IConstructor prod__lit____char_class___range__45_45_ = (IConstructor) _read("prod(lit(\"-\"),[\\char-class([range(45,45)])],{})", Factory.Production);
  private static final IConstructor prod__expression_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression__tag__Folded_tag__category___67_111_109_109_101_110_116 = (IConstructor) _read("prod(label(\"expression\",sort(\"Tag\")),[lit(\"@\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\"))],{tag(Folded()),tag(category(\"Comment\"))})", Factory.Production);
  private static final IConstructor prod__fieldProject_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___60_$layouts_LAYOUTLIST_fields_iter_seps__$Field__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_ = (IConstructor) _read("prod(label(\"fieldProject\",sort(\"Expression\")),[conditional(label(\"expression\",sort(\"Expression\")),{except(\"transitiveReflexiveClosure\"),except(\"transitiveClosure\"),except(\"isDefined\")}),layouts(\"LAYOUTLIST\"),lit(\"\\<\"),layouts(\"LAYOUTLIST\"),label(\"fields\",\\iter-seps(sort(\"Field\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"\\>\")],{})", Factory.Production);
  private static final IConstructor prod__lit___44__char_class___range__44_44_ = (IConstructor) _read("prod(lit(\",\"),[\\char-class([range(44,44)])],{})", Factory.Production);
  private static final IConstructor prod__lit___47__char_class___range__47_47_ = (IConstructor) _read("prod(lit(\"/\"),[\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__lit___46__char_class___range__46_46_ = (IConstructor) _read("prod(lit(\".\"),[\\char-class([range(46,46)])],{})", Factory.Production);
  private static final IConstructor prod__lit_if__char_class___range__105_105_char_class___range__102_102_ = (IConstructor) _read("prod(lit(\"if\"),[\\char-class([range(105,105)]),\\char-class([range(102,102)])],{})", Factory.Production);
  private static final IConstructor prod__lit___33__char_class___range__33_33_ = (IConstructor) _read("prod(lit(\"!\"),[\\char-class([range(33,33)])],{})", Factory.Production);
  private static final IConstructor prod__start__$PreModule__$layouts_LAYOUTLIST_top_$PreModule_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(start(sort(\"PreModule\")),[layouts(\"LAYOUTLIST\"),label(\"top\",sort(\"PreModule\")),layouts(\"LAYOUTLIST\")],{})", Factory.Production);
  private static final IConstructor prod__lit___35__char_class___range__35_35_ = (IConstructor) _read("prod(lit(\"#\"),[\\char-class([range(35,35)])],{})", Factory.Production);
  private static final IConstructor prod__tag_$Kind__lit_tag_ = (IConstructor) _read("prod(label(\"tag\",sort(\"Kind\")),[lit(\"tag\")],{})", Factory.Production);
  private static final IConstructor prod__lit___34__char_class___range__34_34_ = (IConstructor) _read("prod(lit(\"\\\"\"),[\\char-class([range(34,34)])],{})", Factory.Production);
  private static final IConstructor prod__lit___37__char_class___range__37_37_ = (IConstructor) _read("prod(lit(\"%\"),[\\char-class([range(37,37)])],{})", Factory.Production);
  private static final IConstructor prod__lit___36__char_class___range__36_36_ = (IConstructor) _read("prod(lit(\"$\"),[\\char-class([range(36,36)])],{})", Factory.Production);
  private static final IConstructor prod__lit___39__char_class___range__39_39_ = (IConstructor) _read("prod(lit(\"\\'\\\\\"),[\\char-class([range(39,39)])],{})", Factory.Production);
  private static final IConstructor prod__lit_return__char_class___range__114_114_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"return\"),[\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(116,116)]),\\char-class([range(117,117)]),\\char-class([range(114,114)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__lit_datetime__char_class___range__100_100_char_class___range__97_97_char_class___range__116_116_char_class___range__101_101_char_class___range__116_116_char_class___range__105_105_char_class___range__109_109_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"datetime\"),[\\char-class([range(100,100)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(109,109)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit___38__char_class___range__38_38_ = (IConstructor) _read("prod(lit(\"&\"),[\\char-class([range(38,38)])],{})", Factory.Production);
  private static final IConstructor prod__range_$Expression__lit___91_$layouts_LAYOUTLIST_first_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_last_$Expression_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"range\",sort(\"Expression\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"first\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"..\"),layouts(\"LAYOUTLIST\"),label(\"last\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor regular__seq___lit___92_char_class___range__123_123_range__125_125 = (IConstructor) _read("regular(seq([lit(\"\\\\\"),\\char-class([range(123,123),range(125,125)])]))", Factory.Production);
  private static final IConstructor prod__lit_Z__char_class___range__90_90_ = (IConstructor) _read("prod(lit(\"Z\"),[\\char-class([range(90,90)])],{})", Factory.Production);
  private static final IConstructor prod__lit___91__char_class___range__91_91_ = (IConstructor) _read("prod(lit(\"[\"),[\\char-class([range(91,91)])],{})", Factory.Production);
  private static final IConstructor prod__literal_$Expression__literal_$Literal_ = (IConstructor) _read("prod(label(\"literal\",sort(\"Expression\")),[label(\"literal\",sort(\"Literal\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___94__char_class___range__94_94_ = (IConstructor) _read("prod(lit(\"^\"),[\\char-class([range(94,94)])],{})", Factory.Production);
  private static final IConstructor prod__visit_$Statement__label_$Label_$layouts_LAYOUTLIST_visit_$Visit__tag__breakable = (IConstructor) _read("prod(label(\"visit\",sort(\"Statement\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),label(\"visit\",sort(\"Visit\"))],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__extend_$Import__lit_extend_$layouts_LAYOUTLIST_module_$ImportedModule_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"extend\",sort(\"Import\")),[lit(\"extend\"),layouts(\"LAYOUTLIST\"),label(\"module\",sort(\"ImportedModule\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__difference_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_rhs_$Class__assoc__left = (IConstructor) _read("prod(label(\"difference\",sort(\"Class\")),[label(\"lhs\",sort(\"Class\")),layouts(\"LAYOUTLIST\"),lit(\"-\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Class\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__lit___92__char_class___range__92_92_ = (IConstructor) _read("prod(lit(\"\\\\\"),[\\char-class([range(92,92)])],{})", Factory.Production);
  private static final IConstructor prod__lit___93__char_class___range__93_93_ = (IConstructor) _read("prod(lit(\"]\"),[\\char-class([range(93,93)])],{})", Factory.Production);
  private static final IConstructor prod__set_$Expression__lit___123_$layouts_LAYOUTLIST_elements_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"set\",sort(\"Expression\")),[lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"elements\",\\iter-star-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__lit_mod__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_ = (IConstructor) _read("prod(lit(\"mod\"),[\\char-class([range(109,109)]),\\char-class([range(111,111)]),\\char-class([range(100,100)])],{})", Factory.Production);
  private static final IConstructor prod__tryFinally_$Statement__lit_try_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_handlers_iter_seps__$Catch__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit_finally_$layouts_LAYOUTLIST_finallyBody_$Statement__tag__breakable = (IConstructor) _read("prod(label(\"tryFinally\",sort(\"Statement\")),[lit(\"try\"),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"Statement\")),layouts(\"LAYOUTLIST\"),label(\"handlers\",\\iter-seps(sort(\"Catch\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"finally\"),layouts(\"LAYOUTLIST\"),label(\"finallyBody\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__variable_$Assignable__qualifiedName_$QualifiedName_ = (IConstructor) _read("prod(label(\"variable\",sort(\"Assignable\")),[label(\"qualifiedName\",sort(\"QualifiedName\"))],{})", Factory.Production);
  private static final IConstructor prod__$NamedRegExp__$NamedBackslash_ = (IConstructor) _read("prod(lex(\"NamedRegExp\"),[lex(\"NamedBackslash\")],{})", Factory.Production);
  private static final IConstructor prod__default_$Renamings__lit_renaming_$layouts_LAYOUTLIST_renamings_iter_seps__$Renaming__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"default\",sort(\"Renamings\")),[lit(\"renaming\"),layouts(\"LAYOUTLIST\"),label(\"renamings\",\\iter-seps(sort(\"Renaming\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__interpolated_$ProtocolPart__pre_$PreProtocolChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$ProtocolTail_ = (IConstructor) _read("prod(label(\"interpolated\",sort(\"ProtocolPart\")),[label(\"pre\",lex(\"PreProtocolChars\")),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"ProtocolTail\"))],{})", Factory.Production);
  private static final IConstructor prod__$RegExp__$Backslash_ = (IConstructor) _read("prod(lex(\"RegExp\"),[lex(\"Backslash\")],{})", Factory.Production);
  private static final IConstructor prod__lit_T__char_class___range__84_84_ = (IConstructor) _read("prod(lit(\"T\"),[\\char-class([range(84,84)])],{})", Factory.Production);
  private static final IConstructor prod__lit_alias__char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__97_97_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"alias\"),[\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(97,97)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__outermost_$Strategy__lit_outermost_ = (IConstructor) _read("prod(label(\"outermost\",sort(\"Strategy\")),[lit(\"outermost\")],{})", Factory.Production);
  private static final IConstructor prod__nonAssociative_$Assoc__lit_non_assoc_ = (IConstructor) _read("prod(label(\"nonAssociative\",sort(\"Assoc\")),[lit(\"non-assoc\")],{})", Factory.Production);
  private static final IConstructor prod__default_$Case__lit_default_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_statement_$Statement__tag__Foldable = (IConstructor) _read("prod(label(\"default\",sort(\"Case\")),[lit(\"default\"),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"statement\",sort(\"Statement\"))],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__typedVariable_$Pattern__type_$Type_$layouts_LAYOUTLIST_name_$Name_ = (IConstructor) _read("prod(label(\"typedVariable\",sort(\"Pattern\")),[label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_assert_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"assert\")],{})", Factory.Production);
  private static final IConstructor prod__lit___64__char_class___range__64_64_ = (IConstructor) _read("prod(lit(\"@\"),[\\char-class([range(64,64)])],{})", Factory.Production);
  private static final IConstructor prod__lit___43_61__char_class___range__43_43_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"+=\"),[\\char-class([range(43,43)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_import_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"import\")],{})", Factory.Production);
  private static final IConstructor prod__lit___123__char_class___range__123_123_ = (IConstructor) _read("prod(lit(\"{\"),[\\char-class([range(123,123)])],{})", Factory.Production);
  private static final IConstructor prod__import_$Command__imported_$Import_ = (IConstructor) _read("prod(label(\"import\",sort(\"Command\")),[label(\"imported\",sort(\"Import\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___33_58_61__char_class___range__33_33_char_class___range__58_58_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"!:=\"),[\\char-class([range(33,33)]),\\char-class([range(58,58)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Tag__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Tag\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__actuals_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_actuals_$ModuleActuals_ = (IConstructor) _read("prod(label(\"actuals\",sort(\"ImportedModule\")),[label(\"name\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),label(\"actuals\",sort(\"ModuleActuals\"))],{})", Factory.Production);
  private static final IConstructor prod__$StringCharacter__char_class___range__0_33_range__35_38_range__40_59_range__61_61_range__63_91_range__93_16777215_ = (IConstructor) _read("prod(lex(\"StringCharacter\"),[\\char-class([range(0,33),range(35,38),range(40,59),range(61,61),range(63,91),range(93,16777215)])],{})", Factory.Production);
  private static final IConstructor prod__lit___125__char_class___range__125_125_ = (IConstructor) _read("prod(lit(\"}\"),[\\char-class([range(125,125)])],{})", Factory.Production);
  private static final IConstructor prod__lit___124__char_class___range__124_124_ = (IConstructor) _read("prod(lit(\"|\"),[\\char-class([range(124,124)])],{})", Factory.Production);
  private static final IConstructor prod__append_$Assignment__lit___60_60_61_ = (IConstructor) _read("prod(label(\"append\",sort(\"Assignment\")),[lit(\"\\<\\<=\")],{})", Factory.Production);
  private static final IConstructor prod__caseInsensitiveLiteral_$Sym__cistring_$CaseInsensitiveStringConstant_ = (IConstructor) _read("prod(label(\"caseInsensitiveLiteral\",sort(\"Sym\")),[label(\"cistring\",lex(\"CaseInsensitiveStringConstant\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___42_47__char_class___range__42_42_char_class___range__47_47_ = (IConstructor) _read("prod(lit(\"*/\"),[\\char-class([range(42,42)]),\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__mid_$StringMiddle__mid_$MidStringChars_ = (IConstructor) _read("prod(label(\"mid\",sort(\"StringMiddle\")),[label(\"mid\",lex(\"MidStringChars\"))],{})", Factory.Production);
  private static final IConstructor prod__list_$Comprehension__lit___91_$layouts_LAYOUTLIST_results_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93__tag__breakable___123_114_101_115_117_108_116_115_44_103_101_110_101_114_97_116_111_114_115_125 = (IConstructor) _read("prod(label(\"list\",sort(\"Comprehension\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"results\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),label(\"generators\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{tag(breakable(\"{results,generators}\"))})", Factory.Production);
  private static final IConstructor prod__variableDeclaration_$Statement__declaration_$LocalVariableDeclaration_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"variableDeclaration\",sort(\"Statement\")),[label(\"declaration\",sort(\"LocalVariableDeclaration\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__utf32_$UnicodeEscape__lit___92_char_class___range__85_85_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_ = (IConstructor) _read("prod(label(\"utf32\",lex(\"UnicodeEscape\")),[lit(\"\\\\\"),\\char-class([range(85,85)]),\\char-class([range(48,57),range(65,70),range(97,102)]),\\char-class([range(48,57),range(65,70),range(97,102)]),\\char-class([range(48,57),range(65,70),range(97,102)]),\\char-class([range(48,57),range(65,70),range(97,102)]),\\char-class([range(48,57),range(65,70),range(97,102)]),\\char-class([range(48,57),range(65,70),range(97,102)])],{})", Factory.Production);
  private static final IConstructor prod__subtraction_$Assignment__lit___45_61_ = (IConstructor) _read("prod(label(\"subtraction\",sort(\"Assignment\")),[lit(\"-=\")],{})", Factory.Production);
  private static final IConstructor prod__default_$ModuleActuals__lit___91_$layouts_LAYOUTLIST_types_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"default\",sort(\"ModuleActuals\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"types\",\\iter-seps(sort(\"Type\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_true_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"true\")],{})", Factory.Production);
  private static final IConstructor prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"TimeZonePart\"),[\\char-class([range(43,43),range(45,45)]),\\char-class([range(48,49)]),\\char-class([range(48,57)]),lit(\":\"),\\char-class([range(48,53)]),\\char-class([range(48,57)])],{})", Factory.Production);
  private static final IConstructor prod__symbol_$Type__symbol_$Sym_ = (IConstructor) _read("prod(label(\"symbol\",sort(\"Type\")),[label(\"symbol\",conditional(sort(\"Sym\"),{except(\"labeled\"),except(\"parametrized\"),except(\"parameter\"),except(\"nonterminal\")}))],{})", Factory.Production);
  private static final IConstructor prod__$RationalLiteral__char_class___range__48_57_iter_star__char_class___range__48_57_char_class___range__114_114_ = (IConstructor) _read("prod(lex(\"RationalLiteral\"),[\\char-class([range(48,57)]),\\iter-star(\\char-class([range(48,57)])),\\char-class([range(114,114)])],{})", Factory.Production);
  private static final IConstructor prod__lit_o__char_class___range__111_111_ = (IConstructor) _read("prod(lit(\"o\"),[\\char-class([range(111,111)])],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"ProdModifier\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Toplevel__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Toplevel\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__defaultStrategy_$Visit__lit_visit_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_subject_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"defaultStrategy\",sort(\"Visit\")),[lit(\"visit\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"subject\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"cases\",\\iter-seps(sort(\"Case\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__$DateAndTime__lit___36_$DatePart_lit_T_$TimePartNoTZ_ = (IConstructor) _read("prod(lex(\"DateAndTime\"),[lit(\"$\"),lex(\"DatePart\"),lit(\"T\"),conditional(lex(\"TimePartNoTZ\"),{\\not-follow(\\char-class([range(43,43),range(45,45)]))})],{})", Factory.Production);
  private static final IConstructor prod__labeled_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_label_$NonterminalLabel_ = (IConstructor) _read("prod(label(\"labeled\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),label(\"label\",lex(\"NonterminalLabel\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_unimport__char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"unimport\"),[\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(109,109)]),\\char-class([range(112,112)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__num_$BasicType__lit_num_ = (IConstructor) _read("prod(label(\"num\",sort(\"BasicType\")),[lit(\"num\")],{})", Factory.Production);
  private static final IConstructor prod__unconditional_$Replacement__replacementExpression_$Expression_ = (IConstructor) _read("prod(label(\"unconditional\",sort(\"Replacement\")),[label(\"replacementExpression\",sort(\"Expression\"))],{})", Factory.Production);
  private static final IConstructor prod__$Comment__lit___47_42_iter_star__alt___char_class___range__42_42_char_class___range__0_41_range__43_16777215_lit___42_47__tag__category___67_111_109_109_101_110_116 = (IConstructor) _read("prod(lex(\"Comment\"),[lit(\"/*\"),\\iter-star(alt({conditional(\\char-class([range(42,42)]),{\\not-follow(\\char-class([range(47,47)]))}),\\char-class([range(0,41),range(43,16777215)])})),lit(\"*/\")],{tag(category(\"Comment\"))})", Factory.Production);
  private static final IConstructor prod__qualifiedName_$Pattern__qualifiedName_$QualifiedName_ = (IConstructor) _read("prod(label(\"qualifiedName\",sort(\"Pattern\")),[label(\"qualifiedName\",sort(\"QualifiedName\"))],{})", Factory.Production);
  private static final IConstructor prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"TimeZonePart\"),[\\char-class([range(43,43),range(45,45)]),\\char-class([range(48,49)]),\\char-class([range(48,57)]),\\char-class([range(48,53)]),\\char-class([range(48,57)])],{})", Factory.Production);
  private static final IConstructor prod__lit___42_61__char_class___range__42_42_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"*=\"),[\\char-class([range(42,42)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__$RegExpModifier__iter_star__char_class___range__100_100_range__105_105_range__109_109_range__115_115_ = (IConstructor) _read("prod(lex(\"RegExpModifier\"),[\\iter-star(\\char-class([range(100,100),range(105,105),range(109,109),range(115,115)]))],{})", Factory.Production);
  private static final IConstructor prod__$NamedRegExp__lit___60_$Name_lit___62_ = (IConstructor) _read("prod(lex(\"NamedRegExp\"),[lit(\"\\<\"),lex(\"Name\"),lit(\"\\>\")],{})", Factory.Production);
  private static final IConstructor prod__midInterpolated_$StringTail__mid_$MidStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringTail_ = (IConstructor) _read("prod(label(\"midInterpolated\",sort(\"StringTail\")),[label(\"mid\",lex(\"MidStringChars\")),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"StringTail\"))],{})", Factory.Production);
  private static final IConstructor regular__alt___char_class___range__42_42_char_class___range__0_41_range__43_16777215 = (IConstructor) _read("regular(alt({conditional(\\char-class([range(42,42)]),{\\not-follow(\\char-class([range(47,47)]))}),\\char-class([range(0,41),range(43,16777215)])}))", Factory.Production);
  private static final IConstructor prod__lit_edit__char_class___range__101_101_char_class___range__100_100_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"edit\"),[\\char-class([range(101,101)]),\\char-class([range(100,100)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor regular__alt___$TagString_char_class___range__0_122_range__124_124_range__126_16777215_seq___lit___92_char_class___range__123_123_range__125_125 = (IConstructor) _read("regular(alt({lex(\"TagString\"),\\char-class([range(0,122),range(124,124),range(126,16777215)]),seq([lit(\"\\\\\"),\\char-class([range(123,123),range(125,125)])])}))", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_finally_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"finally\")],{})", Factory.Production);
  private static final IConstructor prod__lit_node__char_class___range__110_110_char_class___range__111_111_char_class___range__100_100_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"node\"),[\\char-class([range(110,110)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__ifThenElse_$Expression__condition_$Expression_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_thenExp_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_elseExp_$Expression__assoc__right = (IConstructor) _read("prod(label(\"ifThenElse\",sort(\"Expression\")),[label(\"condition\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"?\"),layouts(\"LAYOUTLIST\"),label(\"thenExp\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"elseExp\",sort(\"Expression\"))],{assoc(right())})", Factory.Production);
  private static final IConstructor prod__subtraction_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"subtraction\",sort(\"Expression\")),[label(\"lhs\",conditional(sort(\"Expression\"),{except(\"transitiveReflexiveClosure\"),except(\"transitiveClosure\")})),layouts(\"LAYOUTLIST\"),lit(\"-\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__set_$Comprehension__lit___123_$layouts_LAYOUTLIST_results_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125__tag__breakable___123_114_101_115_117_108_116_115_44_103_101_110_101_114_97_116_111_114_115_125 = (IConstructor) _read("prod(label(\"set\",sort(\"Comprehension\")),[lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"results\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),label(\"generators\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{tag(breakable(\"{results,generators}\"))})", Factory.Production);
  private static final IConstructor prod__intersection_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___38_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"intersection\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),conditional(lit(\"&\"),{\\not-follow(lit(\"&\"))}),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_filter_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"filter\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_while_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"while\")],{})", Factory.Production);
  private static final IConstructor regular__iter__char_class___range__48_57_range__65_70_range__97_102 = (IConstructor) _read("regular(iter(\\char-class([range(48,57),range(65,70),range(97,102)])))", Factory.Production);
  private static final IConstructor prod__declaration_$Command__declaration_$Declaration_ = (IConstructor) _read("prod(label(\"declaration\",sort(\"Command\")),[label(\"declaration\",sort(\"Declaration\"))],{})", Factory.Production);
  private static final IConstructor prod__bool_$BasicType__lit_bool_ = (IConstructor) _read("prod(label(\"bool\",sort(\"BasicType\")),[lit(\"bool\")],{})", Factory.Production);
  private static final IConstructor prod__ifThen_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_thenStatement_$Statement_$layouts_LAYOUTLIST_empty__tag__breakable = (IConstructor) _read("prod(label(\"ifThen\",sort(\"Statement\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),lit(\"if\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"conditions\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),label(\"thenStatement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")})),layouts(\"LAYOUTLIST\"),conditional(empty(),{\\not-follow(lit(\"else\"))})],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__lit___36_84__char_class___range__36_36_char_class___range__84_84_ = (IConstructor) _read("prod(lit(\"$T\"),[\\char-class([range(36,36)]),\\char-class([range(84,84)])],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Name__$layouts_LAYOUTLIST_lit___58_58_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(lex(\"Name\"),[layouts(\"LAYOUTLIST\"),lit(\"::\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__tuple_$Pattern__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_ = (IConstructor) _read("prod(label(\"tuple\",sort(\"Pattern\")),[lit(\"\\<\"),layouts(\"LAYOUTLIST\"),label(\"elements\",\\iter-seps(sort(\"Pattern\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"\\>\")],{})", Factory.Production);
  private static final IConstructor prod__lit_do__char_class___range__100_100_char_class___range__111_111_ = (IConstructor) _read("prod(lit(\"do\"),[\\char-class([range(100,100)]),\\char-class([range(111,111)])],{})", Factory.Production);
  private static final IConstructor prod__bracket_$Assignable__lit___40_$layouts_LAYOUTLIST_arg_$Assignable_$layouts_LAYOUTLIST_lit___41__bracket = (IConstructor) _read("prod(label(\"bracket\",sort(\"Assignable\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"arg\",sort(\"Assignable\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{bracket()})", Factory.Production);
  private static final IConstructor prod__start__$Command__$layouts_LAYOUTLIST_top_$Command_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(start(sort(\"Command\")),[layouts(\"LAYOUTLIST\"),label(\"top\",sort(\"Command\")),layouts(\"LAYOUTLIST\")],{})", Factory.Production);
  private static final IConstructor prod__statement_$Command__statement_$Statement_ = (IConstructor) _read("prod(label(\"statement\",sort(\"Command\")),[label(\"statement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"visit\"),except(\"variableDeclaration\")}))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_visit_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"visit\")],{})", Factory.Production);
  private static final IConstructor prod__function_$Type__function_$FunctionType_ = (IConstructor) _read("prod(label(\"function\",sort(\"Type\")),[label(\"function\",sort(\"FunctionType\"))],{})", Factory.Production);
  private static final IConstructor prod__$JustTime__lit___36_84_$TimePartNoTZ_ = (IConstructor) _read("prod(lex(\"JustTime\"),[lit(\"$T\"),conditional(lex(\"TimePartNoTZ\"),{\\not-follow(\\char-class([range(43,43),range(45,45)]))})],{})", Factory.Production);
  private static final IConstructor prod__$MidProtocolChars__lit___62_$URLChars_lit___60_ = (IConstructor) _read("prod(lex(\"MidProtocolChars\"),[lit(\"\\>\"),lex(\"URLChars\"),lit(\"\\<\")],{})", Factory.Production);
  private static final IConstructor prod__lit_java__char_class___range__106_106_char_class___range__97_97_char_class___range__118_118_char_class___range__97_97_ = (IConstructor) _read("prod(lit(\"java\"),[\\char-class([range(106,106)]),\\char-class([range(97,97)]),\\char-class([range(118,118)]),\\char-class([range(97,97)])],{})", Factory.Production);
  private static final IConstructor prod__binding_$Catch__lit_catch_$layouts_LAYOUTLIST_pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_body_$Statement_ = (IConstructor) _read("prod(label(\"binding\",sort(\"Catch\")),[lit(\"catch\"),layouts(\"LAYOUTLIST\"),label(\"pattern\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"Statement\"))],{})", Factory.Production);
  private static final IConstructor prod__fieldAccess_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_field_$Name_ = (IConstructor) _read("prod(label(\"fieldAccess\",sort(\"Assignable\")),[label(\"receiver\",sort(\"Assignable\")),layouts(\"LAYOUTLIST\"),lit(\".\"),layouts(\"LAYOUTLIST\"),label(\"field\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_notin_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"notin\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_append_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"append\")],{})", Factory.Production);
  private static final IConstructor prod__void_$BasicType__lit_void_ = (IConstructor) _read("prod(label(\"void\",sort(\"BasicType\")),[lit(\"void\")],{})", Factory.Production);
  private static final IConstructor prod__$StringCharacter__char_class___range__10_10_iter_star__char_class___range__9_9_range__32_32_range__65_65_range__160_160_range__5760_5760_range__8192_8192_range__8239_8239_range__8287_8287_range__12288_12288_char_class___range__39_39_ = (IConstructor) _read("prod(lex(\"StringCharacter\"),[\\char-class([range(10,10)]),\\iter-star(\\char-class([range(9,9),range(32,32),range(65,65),range(160,160),range(5760,5760),range(8192,8192),range(8239,8239),range(8287,8287),range(12288,12288)])),\\char-class([range(39,39)])],{})", Factory.Production);
  private static final IConstructor prod__givenStrategy_$Visit__strategy_$Strategy_$layouts_LAYOUTLIST_lit_visit_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_subject_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"givenStrategy\",sort(\"Visit\")),[label(\"strategy\",sort(\"Strategy\")),layouts(\"LAYOUTLIST\"),lit(\"visit\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"subject\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"cases\",\\iter-seps(sort(\"Case\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Sym__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__reifiedType_$Pattern__lit_type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_symbol_$Pattern_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_definitions_$Pattern_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"reifiedType\",sort(\"Pattern\")),[lit(\"type\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"symbol\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\"),label(\"definitions\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__nAryConstructor_$Variant__name_$Name_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"nAryConstructor\",sort(\"Variant\")),[label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"arguments\",\\iter-star-seps(sort(\"TypeArg\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__alias_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_alias_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_base_$Type_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"alias\",sort(\"Declaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),lit(\"alias\"),layouts(\"LAYOUTLIST\"),label(\"user\",sort(\"UserType\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"base\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__lit_quit__char_class___range__113_113_char_class___range__117_117_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"quit\"),[\\char-class([range(113,113)]),\\char-class([range(117,117)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_tuple__char_class___range__116_116_char_class___range__117_117_char_class___range__112_112_char_class___range__108_108_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"tuple\"),[\\char-class([range(116,116)]),\\char-class([range(117,117)]),\\char-class([range(112,112)]),\\char-class([range(108,108)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__$OctalIntegerLiteral__char_class___range__48_48_iter__char_class___range__48_55_ = (IConstructor) _read("prod(lex(\"OctalIntegerLiteral\"),[\\char-class([range(48,48)]),conditional(iter(\\char-class([range(48,55)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__variable_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_variables_iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"variable\",sort(\"Declaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"variables\",\\iter-seps(sort(\"Variable\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Variant__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Variant\"),[layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__implication_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___61_61_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"implication\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"==\\>\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_try_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"try\")],{})", Factory.Production);
  private static final IConstructor prod__data_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_data_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_variants_iter_seps__$Variant__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__Foldable = (IConstructor) _read("prod(label(\"data\",sort(\"Declaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),lit(\"data\"),layouts(\"LAYOUTLIST\"),label(\"user\",sort(\"UserType\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"variants\",\\iter-seps(sort(\"Variant\"),[layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__patternWithAction_$Case__lit_case_$layouts_LAYOUTLIST_patternWithAction_$PatternWithAction__tag__Foldable = (IConstructor) _read("prod(label(\"patternWithAction\",sort(\"Case\")),[lit(\"case\"),layouts(\"LAYOUTLIST\"),label(\"patternWithAction\",sort(\"PatternWithAction\"))],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__node_$BasicType__lit_node_ = (IConstructor) _read("prod(label(\"node\",sort(\"BasicType\")),[lit(\"node\")],{})", Factory.Production);
  private static final IConstructor prod__lit_has__char_class___range__104_104_char_class___range__97_97_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"has\"),[\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__function_$Declaration__functionDeclaration_$FunctionDeclaration_ = (IConstructor) _read("prod(label(\"function\",sort(\"Declaration\")),[label(\"functionDeclaration\",sort(\"FunctionDeclaration\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_insert_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"insert\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_anno_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"anno\")],{})", Factory.Production);
  private static final IConstructor prod__constructor_$Assignable__name_$Name_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"constructor\",sort(\"Assignable\")),[label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"arguments\",\\iter-seps(sort(\"Assignable\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__default_$Mapping__$Expression__from_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Expression_ = (IConstructor) _read("prod(label(\"default\",\\parameterized-sort(\"Mapping\",[sort(\"Expression\")])),[label(\"from\",conditional(sort(\"Expression\"),{except(\"ifDefinedOtherwise\")})),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"to\",sort(\"Expression\"))],{})", Factory.Production);
  private static final IConstructor prod__callOrTree_$Pattern__expression_$Pattern_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"callOrTree\",sort(\"Pattern\")),[label(\"expression\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"arguments\",\\iter-star-seps(sort(\"Pattern\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__parameter_$Sym__lit___38_$layouts_LAYOUTLIST_nonterminal_$Nonterminal_ = (IConstructor) _read("prod(label(\"parameter\",sort(\"Sym\")),[lit(\"&\"),layouts(\"LAYOUTLIST\"),label(\"nonterminal\",lex(\"Nonterminal\"))],{})", Factory.Production);
  private static final IConstructor prod__type_$BasicType__lit_type_ = (IConstructor) _read("prod(label(\"type\",sort(\"BasicType\")),[lit(\"type\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_start_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"start\")],{})", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(seq([conditional(\\char-class([range(65,90),range(95,95),range(97,122)]),{\\not-precede(\\char-class([range(65,90),range(95,95),range(97,122)]))}),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})]))", Factory.Production);
  private static final IConstructor prod__lit_outermost__char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__109_109_char_class___range__111_111_char_class___range__115_115_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"outermost\"),[\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(109,109)]),\\char-class([range(111,111)]),\\char-class([range(115,115)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__descendant_$Pattern__lit___47_$layouts_LAYOUTLIST_pattern_$Pattern_ = (IConstructor) _read("prod(label(\"descendant\",sort(\"Pattern\")),[lit(\"/\"),layouts(\"LAYOUTLIST\"),label(\"pattern\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor prod__default_$StructuredType__basicType_$BasicType_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_arguments_iter_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"default\",sort(\"StructuredType\")),[label(\"basicType\",sort(\"BasicType\")),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"arguments\",\\iter-seps(sort(\"TypeArg\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__join_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_join_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"join\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"join\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__default_$FunctionModifier__lit_default_ = (IConstructor) _read("prod(label(\"default\",sort(\"FunctionModifier\")),[lit(\"default\")],{})", Factory.Production);
  private static final IConstructor prod__empty_$Label__ = (IConstructor) _read("prod(label(\"empty\",sort(\"Label\")),[],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_test_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"test\")],{})", Factory.Production);
  private static final IConstructor prod__$RegExp__lit___60_$Name_lit___62_ = (IConstructor) _read("prod(lex(\"RegExp\"),[lit(\"\\<\"),lex(\"Name\"),lit(\"\\>\")],{})", Factory.Production);
  private static final IConstructor prod__$DatePart__char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_49_char_class___range__48_57_char_class___range__48_51_char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"DatePart\"),[\\char-class([range(48,57)]),\\char-class([range(48,57)]),\\char-class([range(48,57)]),\\char-class([range(48,57)]),\\char-class([range(48,49)]),\\char-class([range(48,57)]),\\char-class([range(48,51)]),\\char-class([range(48,57)])],{})", Factory.Production);
  private static final IConstructor prod__lit___38_38__char_class___range__38_38_char_class___range__38_38_ = (IConstructor) _read("prod(lit(\"&&\"),[\\char-class([range(38,38)]),\\char-class([range(38,38)])],{})", Factory.Production);
  private static final IConstructor prod__labeled_$Prod__modifiers_iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_args_iter_star_seps__$Sym__$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"labeled\",sort(\"Prod\")),[label(\"modifiers\",\\iter-star-seps(sort(\"ProdModifier\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"args\",\\iter-star-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__ifThenElse_$StringTemplate__lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStatsThen_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_thenString_$StringMiddle_$layouts_LAYOUTLIST_postStatsThen_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit_else_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStatsElse_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_elseString_$StringMiddle_$layouts_LAYOUTLIST_postStatsElse_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"ifThenElse\",sort(\"StringTemplate\")),[lit(\"if\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"conditions\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"preStatsThen\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"thenString\",sort(\"StringMiddle\")),layouts(\"LAYOUTLIST\"),label(\"postStatsThen\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\"),layouts(\"LAYOUTLIST\"),lit(\"else\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"preStatsElse\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"elseString\",sort(\"StringMiddle\")),layouts(\"LAYOUTLIST\"),label(\"postStatsElse\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__import_$EvalCommand__imported_$Import_ = (IConstructor) _read("prod(label(\"import\",sort(\"EvalCommand\")),[label(\"imported\",sort(\"Import\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Statement__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__real_$BasicType__lit_real_ = (IConstructor) _read("prod(label(\"real\",sort(\"BasicType\")),[lit(\"real\")],{})", Factory.Production);
  private static final IConstructor prod__$BooleanLiteral__lit_false_ = (IConstructor) _read("prod(lex(\"BooleanLiteral\"),[lit(\"false\")],{})", Factory.Production);
  private static final IConstructor prod__int_$BasicType__lit_int_ = (IConstructor) _read("prod(label(\"int\",sort(\"BasicType\")),[lit(\"int\")],{})", Factory.Production);
  private static final IConstructor prod__$URLChars__iter_star__char_class___range__0_8_range__11_12_range__14_31_range__33_59_range__61_123_range__125_16777215_ = (IConstructor) _read("prod(lex(\"URLChars\"),[\\iter-star(\\char-class([range(0,8),range(11,12),range(14,31),range(33,59),range(61,123),range(125,16777215)]))],{})", Factory.Production);
  private static final IConstructor prod__reducer_$Expression__lit___40_$layouts_LAYOUTLIST_init_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_result_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"reducer\",sort(\"Expression\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"init\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),label(\"result\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),label(\"generators\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$FunctionModifier__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"FunctionModifier\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__alternative_$Sym__lit___40_$layouts_LAYOUTLIST_first_$Sym_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_alternatives_iter_seps__$Sym__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"alternative\",sort(\"Sym\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"first\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),label(\"alternatives\",\\iter-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__left_$Assoc__lit_left_ = (IConstructor) _read("prod(label(\"left\",sort(\"Assoc\")),[lit(\"left\")],{})", Factory.Production);
  private static final IConstructor prod__lit___38_61__char_class___range__38_38_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"&=\"),[\\char-class([range(38,38)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__solve_$Statement__lit_solve_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_variables_iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_bound_$Bound_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable = (IConstructor) _read("prod(label(\"solve\",sort(\"Statement\")),[lit(\"solve\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"variables\",\\iter-seps(sort(\"QualifiedName\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"bound\",sort(\"Bound\")),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),label(\"body\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__post_$ProtocolTail__post_$PostProtocolChars_ = (IConstructor) _read("prod(label(\"post\",sort(\"ProtocolTail\")),[label(\"post\",lex(\"PostProtocolChars\"))],{})", Factory.Production);
  private static final IConstructor prod__modulo_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_mod_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"modulo\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"mod\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__default_$Bound__lit___59_$layouts_LAYOUTLIST_expression_$Expression_ = (IConstructor) _read("prod(label(\"default\",sort(\"Bound\")),[lit(\";\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\"))],{})", Factory.Production);
  private static final IConstructor prod__endOfLine_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___36_ = (IConstructor) _read("prod(label(\"endOfLine\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"$\")],{})", Factory.Production);
  private static final IConstructor prod__transitiveReflexiveClosure_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___42_ = (IConstructor) _read("prod(label(\"transitiveReflexiveClosure\",sort(\"Expression\")),[label(\"argument\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),conditional(lit(\"*\"),{\\not-follow(lit(\"=\"))})],{})", Factory.Production);
  private static final IConstructor prod__module_$Kind__lit_module_ = (IConstructor) _read("prod(label(\"module\",sort(\"Kind\")),[lit(\"module\")],{})", Factory.Production);
  private static final IConstructor prod__nonInterpolated_$StringLiteral__constant_$StringConstant_ = (IConstructor) _read("prod(label(\"nonInterpolated\",sort(\"StringLiteral\")),[label(\"constant\",lex(\"StringConstant\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_break_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"break\")],{})", Factory.Production);
  private static final IConstructor prod__asType_$Expression__lit___91_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___93_$layouts_LAYOUTLIST_argument_$Expression_ = (IConstructor) _read("prod(label(\"asType\",sort(\"Expression\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),lit(\"]\"),layouts(\"LAYOUTLIST\"),label(\"argument\",conditional(sort(\"Expression\"),{except(\"noMatch\"),except(\"match\")}))],{})", Factory.Production);
  private static final IConstructor prod__dateAndTimeLiteral_$DateTimeLiteral__dateAndTime_$DateAndTime_ = (IConstructor) _read("prod(label(\"dateAndTimeLiteral\",sort(\"DateTimeLiteral\")),[label(\"dateAndTime\",lex(\"DateAndTime\"))],{})", Factory.Production);
  private static final IConstructor prod__lessThan_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"lessThan\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),conditional(lit(\"\\<\"),{\\not-follow(lit(\"-\"))}),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__appendAfter_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_60_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"appendAfter\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),conditional(lit(\"\\<\\<\"),{\\not-follow(lit(\"=\"))}),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_real_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"real\")],{})", Factory.Production);
  private static final IConstructor prod__lit___33_61__char_class___range__33_33_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"!=\"),[\\char-class([range(33,33)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__default_$FunctionBody__lit___123_$layouts_LAYOUTLIST_statements_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"default\",sort(\"FunctionBody\")),[lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"statements\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__interpolated_$StringLiteral__pre_$PreStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringTail_ = (IConstructor) _read("prod(label(\"interpolated\",sort(\"StringLiteral\")),[label(\"pre\",lex(\"PreStringChars\")),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"StringTail\"))],{})", Factory.Production);
  private static final IConstructor prod__default_$Module__header_$Header_$layouts_LAYOUTLIST_body_$Body_ = (IConstructor) _read("prod(label(\"default\",sort(\"Module\")),[label(\"header\",sort(\"Header\")),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"Body\"))],{})", Factory.Production);
  private static final IConstructor prod__$StringConstant__lit___34_iter_star__$StringCharacter_lit___34__tag__category___67_111_110_115_116_97_110_116 = (IConstructor) _read("prod(lex(\"StringConstant\"),[lit(\"\\\"\"),\\iter-star(lex(\"StringCharacter\")),lit(\"\\\"\")],{tag(category(\"Constant\"))})", Factory.Production);
  private static final IConstructor prod__notIn_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_notin_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"notIn\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"notin\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__$PreProtocolChars__lit___124_$URLChars_lit___60_ = (IConstructor) _read("prod(lex(\"PreProtocolChars\"),[lit(\"|\"),lex(\"URLChars\"),lit(\"\\<\")],{})", Factory.Production);
  private static final IConstructor prod__$RegExp__char_class___range__92_92_char_class___range__47_47_range__60_60_range__62_62_range__92_92_ = (IConstructor) _read("prod(lex(\"RegExp\"),[\\char-class([range(92,92)]),\\char-class([range(47,47),range(60,60),range(62,62),range(92,92)])],{})", Factory.Production);
  private static final IConstructor prod__lit_anno__char_class___range__97_97_char_class___range__110_110_char_class___range__110_110_char_class___range__111_111_ = (IConstructor) _read("prod(lit(\"anno\"),[\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(110,110)]),\\char-class([range(111,111)])],{})", Factory.Production);
  private static final IConstructor prod__public_$Visibility__lit_public_ = (IConstructor) _read("prod(label(\"public\",sort(\"Visibility\")),[lit(\"public\")],{})", Factory.Production);
  private static final IConstructor prod__lit_top_down__char_class___range__116_116_char_class___range__111_111_char_class___range__112_112_char_class___range__45_45_char_class___range__100_100_char_class___range__111_111_char_class___range__119_119_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"top-down\"),[\\char-class([range(116,116)]),\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(45,45)]),\\char-class([range(100,100)]),\\char-class([range(111,111)]),\\char-class([range(119,119)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__lit___46_46_46__char_class___range__46_46_char_class___range__46_46_char_class___range__46_46_ = (IConstructor) _read("prod(lit(\"...\"),[\\char-class([range(46,46)]),\\char-class([range(46,46)]),\\char-class([range(46,46)])],{})", Factory.Production);
  private static final IConstructor prod__lit_top_down_break__char_class___range__116_116_char_class___range__111_111_char_class___range__112_112_char_class___range__45_45_char_class___range__100_100_char_class___range__111_111_char_class___range__119_119_char_class___range__110_110_char_class___range__45_45_char_class___range__98_98_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__107_107_ = (IConstructor) _read("prod(lit(\"top-down-break\"),[\\char-class([range(116,116)]),\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(45,45)]),\\char-class([range(100,100)]),\\char-class([range(111,111)]),\\char-class([range(119,119)]),\\char-class([range(110,110)]),\\char-class([range(45,45)]),\\char-class([range(98,98)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(97,97)]),\\char-class([range(107,107)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_join_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"join\")],{})", Factory.Production);
  private static final IConstructor prod__$Char__lit___92_char_class___range__32_32_range__34_34_range__39_39_range__45_45_range__60_60_range__62_62_range__91_93_range__98_98_range__102_102_range__110_110_range__114_114_range__116_116__tag__category___67_111_110_115_116_97_110_116 = (IConstructor) _read("prod(lex(\"Char\"),[lit(\"\\\\\"),\\char-class([range(32,32),range(34,34),range(39,39),range(45,45),range(60,60),range(62,62),range(91,93),range(98,98),range(102,102),range(110,110),range(114,114),range(116,116)])],{tag(category(\"Constant\"))})", Factory.Production);
  private static final IConstructor prod__expression_$Command__expression_$Expression_ = (IConstructor) _read("prod(label(\"expression\",sort(\"Command\")),[label(\"expression\",conditional(sort(\"Expression\"),{except(\"nonEmptyBlock\")}))],{})", Factory.Production);
  private static final IConstructor prod__view_$Kind__lit_view_ = (IConstructor) _read("prod(label(\"view\",sort(\"Kind\")),[lit(\"view\")],{})", Factory.Production);
  private static final IConstructor prod__lit_view__char_class___range__118_118_char_class___range__105_105_char_class___range__101_101_char_class___range__119_119_ = (IConstructor) _read("prod(lit(\"view\"),[\\char-class([range(118,118)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(119,119)])],{})", Factory.Production);
  private static final IConstructor prod__lit_innermost__char_class___range__105_105_char_class___range__110_110_char_class___range__110_110_char_class___range__101_101_char_class___range__114_114_char_class___range__109_109_char_class___range__111_111_char_class___range__115_115_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"innermost\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(109,109)]),\\char-class([range(111,111)]),\\char-class([range(115,115)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__test_$ShellCommand__lit_test_ = (IConstructor) _read("prod(label(\"test\",sort(\"ShellCommand\")),[lit(\"test\")],{})", Factory.Production);
  private static final IConstructor prod__lit_one__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"one\"),[\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Renaming__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Renaming\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__$HeaderKeyword__lit_import_ = (IConstructor) _read("prod(keywords(\"HeaderKeyword\"),[lit(\"import\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"TypeArg\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__$CaseInsensitiveStringConstant__lit___39_iter_star__$StringCharacter_lit___39__tag__category___67_111_110_115_116_97_110_116 = (IConstructor) _read("prod(lex(\"CaseInsensitiveStringConstant\"),[lit(\"\\'\\\\\"),\\iter-star(lex(\"StringCharacter\")),lit(\"\\'\\\\\")],{tag(category(\"Constant\"))})", Factory.Production);
  private static final IConstructor prod__toplevels_$Body__toplevels_iter_star_seps__$Toplevel__$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"toplevels\",sort(\"Body\")),[label(\"toplevels\",\\iter-star-seps(sort(\"Toplevel\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__ifDefinedOrDefault_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_defaultExpression_$Expression_ = (IConstructor) _read("prod(label(\"ifDefinedOrDefault\",sort(\"Assignable\")),[label(\"receiver\",sort(\"Assignable\")),layouts(\"LAYOUTLIST\"),lit(\"?\"),layouts(\"LAYOUTLIST\"),label(\"defaultExpression\",sort(\"Expression\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_while__char_class___range__119_119_char_class___range__104_104_char_class___range__105_105_char_class___range__108_108_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"while\"),[\\char-class([range(119,119)]),\\char-class([range(104,104)]),\\char-class([range(105,105)]),\\char-class([range(108,108)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_notin__char_class___range__110_110_char_class___range__111_111_char_class___range__116_116_char_class___range__105_105_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"notin\"),[\\char-class([range(110,110)]),\\char-class([range(111,111)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__iter_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___43_ = (IConstructor) _read("prod(label(\"iter\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"+\")],{})", Factory.Production);
  private static final IConstructor prod__bottomUpBreak_$Strategy__lit_bottom_up_break_ = (IConstructor) _read("prod(label(\"bottomUpBreak\",sort(\"Strategy\")),[lit(\"bottom-up-break\")],{})", Factory.Production);
  private static final IConstructor prod__list_$Commands__commands_iter_seps__$EvalCommand__$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"list\",sort(\"Commands\")),[label(\"commands\",\\iter-seps(sort(\"EvalCommand\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__default_$ModuleParameters__lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$TypeVar__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"default\",sort(\"ModuleParameters\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"parameters\",\\iter-seps(sort(\"TypeVar\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__optional_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___63_ = (IConstructor) _read("prod(label(\"optional\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"?\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_lrel_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"lrel\")],{})", Factory.Production);
  private static final IConstructor prod__default_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_body_$FunctionBody__tag__Foldable = (IConstructor) _read("prod(label(\"default\",sort(\"FunctionDeclaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),label(\"signature\",sort(\"Signature\")),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"FunctionBody\"))],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__absent_$Start__ = (IConstructor) _read("prod(label(\"absent\",sort(\"Start\")),[],{})", Factory.Production);
  private static final IConstructor regular__opt__seq___char_class___range__48_57_opt__char_class___range__48_57 = (IConstructor) _read("regular(opt(seq([\\char-class([range(48,57)]),opt(\\char-class([range(48,57)]))])))", Factory.Production);
  private static final IConstructor prod__switch_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_switch_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125__tag__breakable = (IConstructor) _read("prod(label(\"switch\",sort(\"Statement\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),lit(\"switch\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"cases\",\\iter-seps(sort(\"Case\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__remainder_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___37_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"remainder\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"%\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__$Nonterminal__char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_ = (IConstructor) _read("prod(lex(\"Nonterminal\"),[conditional(\\char-class([range(65,90)]),{\\not-precede(\\char-class([range(65,90)]))}),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),delete(sort(\"RascalReservedKeywords\"))})],{})", Factory.Production);
  private static final IConstructor prod__ifDefinedOtherwise_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"ifDefinedOtherwise\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"?\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__bag_$BasicType__lit_bag_ = (IConstructor) _read("prod(label(\"bag\",sort(\"BasicType\")),[lit(\"bag\")],{})", Factory.Production);
  private static final IConstructor prod__unequal_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___92_$layouts_LAYOUTLIST_match_$Sym__assoc__left = (IConstructor) _read("prod(label(\"unequal\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"\\\\\"),layouts(\"LAYOUTLIST\"),label(\"match\",sort(\"Sym\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_void_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"void\")],{})", Factory.Production);
  private static final IConstructor prod__noMatch_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___33_58_61_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"noMatch\",sort(\"Expression\")),[label(\"pattern\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\"!:=\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__getAnnotation_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_ = (IConstructor) _read("prod(label(\"getAnnotation\",sort(\"Expression\")),[label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"@\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__tuple_$BasicType__lit_tuple_ = (IConstructor) _read("prod(label(\"tuple\",sort(\"BasicType\")),[lit(\"tuple\")],{})", Factory.Production);
  private static final IConstructor prod__doWhile_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_do_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"doWhile\",sort(\"Statement\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),lit(\"do\"),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"Statement\")),layouts(\"LAYOUTLIST\"),lit(\"while\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"condition\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__first_$Prod__lhs_$Prod_$layouts_LAYOUTLIST_lit___62_$layouts_LAYOUTLIST_rhs_$Prod__assoc__left = (IConstructor) _read("prod(label(\"first\",sort(\"Prod\")),[label(\"lhs\",sort(\"Prod\")),layouts(\"LAYOUTLIST\"),conditional(lit(\"\\>\"),{\\not-follow(lit(\"\\>\"))}),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Prod\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__continue_$Statement__lit_continue_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"continue\",sort(\"Statement\")),[lit(\"continue\"),layouts(\"LAYOUTLIST\"),label(\"target\",sort(\"Target\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__string_$Literal__stringLiteral_$StringLiteral_ = (IConstructor) _read("prod(label(\"string\",sort(\"Literal\")),[label(\"stringLiteral\",sort(\"StringLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__transitiveClosure_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___43_ = (IConstructor) _read("prod(label(\"transitiveClosure\",sort(\"Expression\")),[label(\"argument\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),conditional(lit(\"+\"),{\\not-follow(lit(\"=\"))})],{})", Factory.Production);
  private static final IConstructor prod__lit_default__char_class___range__100_100_char_class___range__101_101_char_class___range__102_102_char_class___range__97_97_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"default\"),[\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(102,102)]),\\char-class([range(97,97)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Pattern\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__lit_bool__char_class___range__98_98_char_class___range__111_111_char_class___range__111_111_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"bool\"),[\\char-class([range(98,98)]),\\char-class([range(111,111)]),\\char-class([range(111,111)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__for_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_for_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable___123_103_101_110_101_114_97_116_111_114_115_125_tag__breakable = (IConstructor) _read("prod(label(\"for\",sort(\"Statement\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),lit(\"for\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"generators\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"Statement\"))],{tag(breakable(\"{generators}\")),tag(breakable())})", Factory.Production);
  private static final IConstructor regular__opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102 = (IConstructor) _read("regular(opt(\\char-class([range(68,68),range(70,70),range(100,100),range(102,102)])))", Factory.Production);
  private static final IConstructor prod__literal_$Sym__string_$StringConstant_ = (IConstructor) _read("prod(label(\"literal\",sort(\"Sym\")),[label(\"string\",lex(\"StringConstant\"))],{})", Factory.Production);
  private static final IConstructor prod__others_$Prod__lit___46_46_46_ = (IConstructor) _read("prod(label(\"others\",sort(\"Prod\")),[lit(\"...\")],{})", Factory.Production);
  private static final IConstructor prod__notFollow_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___33_62_62_$layouts_LAYOUTLIST_match_$Sym__assoc__left = (IConstructor) _read("prod(label(\"notFollow\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"!\\>\\>\"),layouts(\"LAYOUTLIST\"),label(\"match\",sort(\"Sym\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Field__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Field\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__real_$Literal__realLiteral_$RealLiteral_ = (IConstructor) _read("prod(label(\"real\",sort(\"Literal\")),[label(\"realLiteral\",lex(\"RealLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__$TimeZonePart__lit_Z_ = (IConstructor) _read("prod(lex(\"TimeZonePart\"),[lit(\"Z\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_default_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"default\")],{})", Factory.Production);
  private static final IConstructor prod__$RationalLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_char_class___range__114_114_char_class___range__48_57_iter_star__char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"RationalLiteral\"),[\\char-class([range(49,57)]),\\iter-star(\\char-class([range(48,57)])),\\char-class([range(114,114)]),\\char-class([range(48,57)]),conditional(\\iter-star(\\char-class([range(48,57)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__$HeaderKeyword__lit_layout_ = (IConstructor) _read("prod(keywords(\"HeaderKeyword\"),[lit(\"layout\")],{})", Factory.Production);
  private static final IConstructor prod__splice_$Expression__lit___42_$layouts_LAYOUTLIST_argument_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"splice\",sort(\"Expression\")),[lit(\"*\"),layouts(\"LAYOUTLIST\"),label(\"argument\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__precede_$Sym__match_$Sym_$layouts_LAYOUTLIST_lit___60_60_$layouts_LAYOUTLIST_symbol_$Sym__assoc__right = (IConstructor) _read("prod(label(\"precede\",sort(\"Sym\")),[label(\"match\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"\\<\\<\"),layouts(\"LAYOUTLIST\"),label(\"symbol\",sort(\"Sym\"))],{assoc(right())})", Factory.Production);
  private static final IConstructor prod__lit_variable__char_class___range__118_118_char_class___range__97_97_char_class___range__114_114_char_class___range__105_105_char_class___range__97_97_char_class___range__98_98_char_class___range__108_108_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"variable\"),[\\char-class([range(118,118)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(105,105)]),\\char-class([range(97,97)]),\\char-class([range(98,98)]),\\char-class([range(108,108)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__boolean_$Literal__booleanLiteral_$BooleanLiteral_ = (IConstructor) _read("prod(label(\"boolean\",sort(\"Literal\")),[label(\"booleanLiteral\",lex(\"BooleanLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___60_61_61_62__char_class___range__60_60_char_class___range__61_61_char_class___range__61_61_char_class___range__62_62_ = (IConstructor) _read("prod(lit(\"\\<==\\>\"),[\\char-class([range(60,60)]),\\char-class([range(61,61)]),\\char-class([range(61,61)]),\\char-class([range(62,62)])],{})", Factory.Production);
  private static final IConstructor prod__$NamedRegExp__char_class___range__92_92_char_class___range__47_47_range__60_60_range__62_62_range__92_92_ = (IConstructor) _read("prod(lex(\"NamedRegExp\"),[\\char-class([range(92,92)]),\\char-class([range(47,47),range(60,60),range(62,62),range(92,92)])],{})", Factory.Production);
  private static final IConstructor prod__varArgs_$Parameters__lit___40_$layouts_LAYOUTLIST_formals_$Formals_$layouts_LAYOUTLIST_lit___46_46_46_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"varArgs\",sort(\"Parameters\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"formals\",sort(\"Formals\")),layouts(\"LAYOUTLIST\"),lit(\"...\"),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_fail_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"fail\")],{})", Factory.Production);
  private static final IConstructor prod__lit_str__char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_ = (IConstructor) _read("prod(lit(\"str\"),[\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(114,114)])],{})", Factory.Production);
  private static final IConstructor prod__default_$Catch__lit_catch_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_body_$Statement_ = (IConstructor) _read("prod(label(\"default\",sort(\"Catch\")),[lit(\"catch\"),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"Statement\"))],{})", Factory.Production);
  private static final IConstructor prod__$Rest__iter_star__char_class___range__0_16777215_ = (IConstructor) _read("prod(lex(\"Rest\"),[conditional(\\iter-star(\\char-class([range(0,16777215)])),{\\not-follow(\\char-class([range(0,16777215)]))})],{})", Factory.Production);
  private static final IConstructor prod__edit_$ShellCommand__lit_edit_$layouts_LAYOUTLIST_name_$QualifiedName_ = (IConstructor) _read("prod(label(\"edit\",sort(\"ShellCommand\")),[lit(\"edit\"),layouts(\"LAYOUTLIST\"),label(\"name\",sort(\"QualifiedName\"))],{})", Factory.Production);
  private static final IConstructor prod__except_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___33_$layouts_LAYOUTLIST_label_$NonterminalLabel_ = (IConstructor) _read("prod(label(\"except\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"!\"),layouts(\"LAYOUTLIST\"),label(\"label\",lex(\"NonterminalLabel\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_solve_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"solve\")],{})", Factory.Production);
  private static final IConstructor prod__splicePlus_$Pattern__lit___43_$layouts_LAYOUTLIST_argument_$Pattern_ = (IConstructor) _read("prod(label(\"splicePlus\",sort(\"Pattern\")),[lit(\"+\"),layouts(\"LAYOUTLIST\"),label(\"argument\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])))", Factory.Production);
  private static final IConstructor prod__$NonterminalLabel__char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_ = (IConstructor) _read("prod(lex(\"NonterminalLabel\"),[\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__data_$Kind__lit_data_ = (IConstructor) _read("prod(label(\"data\",sort(\"Kind\")),[lit(\"data\")],{})", Factory.Production);
  private static final IConstructor prod__lit___61_62__char_class___range__61_61_char_class___range__62_62_ = (IConstructor) _read("prod(lit(\"=\\>\"),[\\char-class([range(61,61)]),\\char-class([range(62,62)])],{})", Factory.Production);
  private static final IConstructor prod__lit___61_61__char_class___range__61_61_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"==\"),[\\char-class([range(61,61)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"TypeArg\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__$NamedRegExp__char_class___range__0_46_range__48_59_range__61_61_range__63_91_range__93_16777215_ = (IConstructor) _read("prod(lex(\"NamedRegExp\"),[\\char-class([range(0,46),range(48,59),range(61,61),range(63,91),range(93,16777215)])],{})", Factory.Production);
  private static final IConstructor prod__startOfLine_$Sym__lit___94_$layouts_LAYOUTLIST_symbol_$Sym_ = (IConstructor) _read("prod(label(\"startOfLine\",sort(\"Sym\")),[lit(\"^\"),layouts(\"LAYOUTLIST\"),label(\"symbol\",sort(\"Sym\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___62_61__char_class___range__62_62_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"\\>=\"),[\\char-class([range(62,62)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__default_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_contents_$TagString__tag__Folded_tag__category___67_111_109_109_101_110_116 = (IConstructor) _read("prod(label(\"default\",sort(\"Tag\")),[lit(\"@\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),label(\"contents\",lex(\"TagString\"))],{tag(Folded()),tag(category(\"Comment\"))})", Factory.Production);
  private static final IConstructor prod__sequence_$Sym__lit___40_$layouts_LAYOUTLIST_first_$Sym_$layouts_LAYOUTLIST_sequence_iter_seps__$Sym__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"sequence\",sort(\"Sym\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"first\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),label(\"sequence\",\\iter-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__$BooleanLiteral__lit_true_ = (IConstructor) _read("prod(lex(\"BooleanLiteral\"),[lit(\"true\")],{})", Factory.Production);
  private static final IConstructor prod__intersection_$Assignment__lit___38_61_ = (IConstructor) _read("prod(label(\"intersection\",sort(\"Assignment\")),[lit(\"&=\")],{})", Factory.Production);
  private static final IConstructor prod__lit_non_assoc__char_class___range__110_110_char_class___range__111_111_char_class___range__110_110_char_class___range__45_45_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__111_111_char_class___range__99_99_ = (IConstructor) _read("prod(lit(\"non-assoc\"),[\\char-class([range(110,110)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(45,45)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(99,99)])],{})", Factory.Production);
  private static final IConstructor prod__enumerator_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___60_45_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"enumerator\",sort(\"Expression\")),[label(\"pattern\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\"\\<-\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__conditional_$Replacement__replacementExpression_$Expression_$layouts_LAYOUTLIST_lit_when_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"conditional\",sort(\"Replacement\")),[label(\"replacementExpression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"when\"),layouts(\"LAYOUTLIST\"),label(\"conditions\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__relation_$BasicType__lit_rel_ = (IConstructor) _read("prod(label(\"relation\",sort(\"BasicType\")),[lit(\"rel\")],{})", Factory.Production);
  private static final IConstructor prod__$RealLiteral__iter__char_class___range__48_57_lit___46_iter_star__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_ = (IConstructor) _read("prod(lex(\"RealLiteral\"),[iter(\\char-class([range(48,57)])),lit(\".\"),\\iter-star(\\char-class([range(48,57)])),\\char-class([range(69,69),range(101,101)]),opt(\\char-class([range(43,43),range(45,45)])),iter(\\char-class([range(48,57)])),opt(\\char-class([range(68,68),range(70,70),range(100,100),range(102,102)]))],{})", Factory.Production);
  private static final IConstructor prod__lit_private__char_class___range__112_112_char_class___range__114_114_char_class___range__105_105_char_class___range__118_118_char_class___range__97_97_char_class___range__116_116_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"private\"),[\\char-class([range(112,112)]),\\char-class([range(114,114)]),\\char-class([range(105,105)]),\\char-class([range(118,118)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__return_$Statement__lit_return_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable = (IConstructor) _read("prod(label(\"return\",sort(\"Statement\")),[lit(\"return\"),layouts(\"LAYOUTLIST\"),label(\"statement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{assoc(\\non-assoc()),tag(breakable())})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_num_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"num\")],{})", Factory.Production);
  private static final IConstructor prod__language_$SyntaxDefinition__start_$Start_$layouts_LAYOUTLIST_lit_syntax_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable = (IConstructor) _read("prod(label(\"language\",sort(\"SyntaxDefinition\")),[label(\"start\",sort(\"Start\")),layouts(\"LAYOUTLIST\"),lit(\"syntax\"),layouts(\"LAYOUTLIST\"),label(\"defined\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"production\",sort(\"Prod\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__$RealLiteral__iter__char_class___range__48_57_char_class___range__68_68_range__70_70_range__100_100_range__102_102_ = (IConstructor) _read("prod(lex(\"RealLiteral\"),[iter(\\char-class([range(48,57)])),\\char-class([range(68,68),range(70,70),range(100,100),range(102,102)])],{})", Factory.Production);
  private static final IConstructor prod__help_$ShellCommand__lit_help_ = (IConstructor) _read("prod(label(\"help\",sort(\"ShellCommand\")),[lit(\"help\")],{})", Factory.Production);
  private static final IConstructor prod__lit_any__char_class___range__97_97_char_class___range__110_110_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"any\"),[\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__parameters_$Header__tags_$Tags_$layouts_LAYOUTLIST_lit_module_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_params_$ModuleParameters_$layouts_LAYOUTLIST_imports_iter_star_seps__$Import__$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"parameters\",sort(\"Header\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),lit(\"module\"),layouts(\"LAYOUTLIST\"),label(\"name\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),label(\"params\",sort(\"ModuleParameters\")),layouts(\"LAYOUTLIST\"),label(\"imports\",\\iter-star-seps(sort(\"Import\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__lit_join__char_class___range__106_106_char_class___range__111_111_char_class___range__105_105_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"join\"),[\\char-class([range(106,106)]),\\char-class([range(111,111)]),\\char-class([range(105,105)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_bracket_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"bracket\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_continue_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"continue\")],{})", Factory.Production);
  private static final IConstructor prod__lit___124_124__char_class___range__124_124_char_class___range__124_124_ = (IConstructor) _read("prod(lit(\"||\"),[\\char-class([range(124,124)]),\\char-class([range(124,124)])],{})", Factory.Production);
  private static final IConstructor prod__lit___60_61__char_class___range__60_60_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"\\<=\"),[\\char-class([range(60,60)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit_finally__char_class___range__102_102_char_class___range__105_105_char_class___range__110_110_char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"finally\"),[\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(108,108)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__stepRange_$Expression__lit___91_$layouts_LAYOUTLIST_first_$Expression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_second_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_last_$Expression_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"stepRange\",sort(\"Expression\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"first\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\"),label(\"second\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"..\"),layouts(\"LAYOUTLIST\"),label(\"last\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__$PostPathChars__lit___62_$URLChars_lit___124_ = (IConstructor) _read("prod(lex(\"PostPathChars\"),[lit(\"\\>\"),lex(\"URLChars\"),lit(\"|\")],{})", Factory.Production);
  private static final IConstructor prod__lit___60_60__char_class___range__60_60_char_class___range__60_60_ = (IConstructor) _read("prod(lit(\"\\<\\<\"),[\\char-class([range(60,60)]),\\char-class([range(60,60)])],{})", Factory.Production);
  private static final IConstructor prod__location_$Literal__locationLiteral_$LocationLiteral_ = (IConstructor) _read("prod(label(\"location\",sort(\"Literal\")),[label(\"locationLiteral\",sort(\"LocationLiteral\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_star__$RegExp = (IConstructor) _read("regular(\\iter-star(lex(\"RegExp\")))", Factory.Production);
  private static final IConstructor prod__has_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit_has_$layouts_LAYOUTLIST_name_$Name_ = (IConstructor) _read("prod(label(\"has\",sort(\"Expression\")),[label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"has\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__it_$Expression__lit_it_ = (IConstructor) _read("prod(label(\"it\",sort(\"Expression\")),[conditional(lit(\"it\"),{\\not-precede(\\char-class([range(65,90),range(95,95),range(97,122)])),\\not-follow(\\char-class([range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_one_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"one\")],{})", Factory.Production);
  private static final IConstructor prod__variable_$Kind__lit_variable_ = (IConstructor) _read("prod(label(\"variable\",sort(\"Kind\")),[lit(\"variable\")],{})", Factory.Production);
  private static final IConstructor prod__unlabeled_$Prod__modifiers_iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_args_iter_star_seps__$Sym__$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"unlabeled\",sort(\"Prod\")),[label(\"modifiers\",\\iter-star-seps(sort(\"ProdModifier\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"args\",\\iter-star-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_node_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"node\")],{})", Factory.Production);
  private static final IConstructor prod__innermost_$Strategy__lit_innermost_ = (IConstructor) _read("prod(label(\"innermost\",sort(\"Strategy\")),[lit(\"innermost\")],{})", Factory.Production);
  private static final IConstructor prod__default_$Label__name_$Name_$layouts_LAYOUTLIST_lit___58_ = (IConstructor) _read("prod(label(\"default\",sort(\"Label\")),[label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\":\")],{})", Factory.Production);
  private static final IConstructor prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"import\"),[\\char-class([range(105,105)]),\\char-class([range(109,109)]),\\char-class([range(112,112)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"else\"),[\\char-class([range(101,101)]),\\char-class([range(108,108)]),\\char-class([range(115,115)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_global__char_class___range__103_103_char_class___range__108_108_char_class___range__111_111_char_class___range__98_98_char_class___range__97_97_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"global\"),[\\char-class([range(103,103)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(98,98)]),\\char-class([range(97,97)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__start_$Sym__lit_start_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_nonterminal_$Nonterminal_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"start\",sort(\"Sym\")),[lit(\"start\"),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"nonterminal\",lex(\"Nonterminal\")),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__product_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___42_$layouts_LAYOUTLIST_empty_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"product\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"*\"),layouts(\"LAYOUTLIST\"),conditional(empty(),{\\not-follow(lit(\"*\"))}),layouts(\"LAYOUTLIST\"),label(\"rhs\",conditional(sort(\"Expression\"),{except(\"noMatch\"),except(\"match\")}))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__hexIntegerLiteral_$IntegerLiteral__hex_$HexIntegerLiteral_ = (IConstructor) _read("prod(label(\"hexIntegerLiteral\",sort(\"IntegerLiteral\")),[label(\"hex\",lex(\"HexIntegerLiteral\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_star__alt___char_class___range__42_42_char_class___range__0_41_range__43_16777215 = (IConstructor) _read("regular(\\iter-star(alt({conditional(\\char-class([range(42,42)]),{\\not-follow(\\char-class([range(47,47)]))}),\\char-class([range(0,41),range(43,16777215)])})))", Factory.Production);
  private static final IConstructor prod__asType_$Pattern__lit___91_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___93_$layouts_LAYOUTLIST_argument_$Pattern_ = (IConstructor) _read("prod(label(\"asType\",sort(\"Pattern\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),lit(\"]\"),layouts(\"LAYOUTLIST\"),label(\"argument\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__$BasicType_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[sort(\"BasicType\")],{})", Factory.Production);
  private static final IConstructor prod__splice_$Pattern__lit___42_$layouts_LAYOUTLIST_argument_$Pattern_ = (IConstructor) _read("prod(label(\"splice\",sort(\"Pattern\")),[lit(\"*\"),layouts(\"LAYOUTLIST\"),label(\"argument\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_star__alt___$TagString_char_class___range__0_122_range__124_124_range__126_16777215_seq___lit___92_char_class___range__123_123_range__125_125 = (IConstructor) _read("regular(\\iter-star(alt({lex(\"TagString\"),\\char-class([range(0,122),range(124,124),range(126,16777215)]),seq([lit(\"\\\\\"),\\char-class([range(123,123),range(125,125)])])})))", Factory.Production);
  private static final IConstructor prod__composition_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_o_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"composition\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"o\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__utf16_$UnicodeEscape__lit___92_char_class___range__117_117_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_ = (IConstructor) _read("prod(label(\"utf16\",lex(\"UnicodeEscape\")),[lit(\"\\\\\"),\\char-class([range(117,117)]),\\char-class([range(48,57),range(65,70),range(97,102)]),\\char-class([range(48,57),range(65,70),range(97,102)]),\\char-class([range(48,57),range(65,70),range(97,102)]),\\char-class([range(48,57),range(65,70),range(97,102)])],{})", Factory.Production);
  private static final IConstructor prod__$PostProtocolChars__lit___62_$URLChars_lit___58_47_47_ = (IConstructor) _read("prod(lex(\"PostProtocolChars\"),[lit(\"\\>\"),lex(\"URLChars\"),lit(\"://\")],{})", Factory.Production);
  private static final IConstructor prod__associative_$Assoc__lit_assoc_ = (IConstructor) _read("prod(label(\"associative\",sort(\"Assoc\")),[lit(\"assoc\")],{})", Factory.Production);
  private static final IConstructor prod__empty_$Sym__lit___40_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"empty\",sort(\"Sym\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__rational_$Literal__rationalLiteral_$RationalLiteral_ = (IConstructor) _read("prod(label(\"rational\",sort(\"Literal\")),[label(\"rationalLiteral\",lex(\"RationalLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__nonInterpolated_$PathPart__pathChars_$PathChars_ = (IConstructor) _read("prod(label(\"nonInterpolated\",sort(\"PathPart\")),[label(\"pathChars\",lex(\"PathChars\"))],{})", Factory.Production);
  private static final IConstructor prod__map_$BasicType__lit_map_ = (IConstructor) _read("prod(label(\"map\",sort(\"BasicType\")),[lit(\"map\")],{})", Factory.Production);
  private static final IConstructor prod__$RegExp__lit___60_$Name_lit___58_iter_star__$NamedRegExp_lit___62_ = (IConstructor) _read("prod(lex(\"RegExp\"),[lit(\"\\<\"),lex(\"Name\"),lit(\":\"),\\iter-star(lex(\"NamedRegExp\")),lit(\"\\>\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_loc_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"loc\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Pattern\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star__$NamedRegExp = (IConstructor) _read("regular(\\iter-star(lex(\"NamedRegExp\")))", Factory.Production);
  private static final IConstructor prod__lit_keyword__char_class___range__107_107_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_ = (IConstructor) _read("prod(lit(\"keyword\"),[\\char-class([range(107,107)]),\\char-class([range(101,101)]),\\char-class([range(121,121)]),\\char-class([range(119,119)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(100,100)])],{})", Factory.Production);
  private static final IConstructor prod__addition_$Assignment__lit___43_61_ = (IConstructor) _read("prod(label(\"addition\",sort(\"Assignment\")),[lit(\"+=\")],{})", Factory.Production);
  private static final IConstructor prod__subscript_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_subscripts_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"subscript\",sort(\"Expression\")),[conditional(label(\"expression\",sort(\"Expression\")),{except(\"transitiveReflexiveClosure\"),except(\"transitiveClosure\"),except(\"isDefined\")}),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"subscripts\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__doWhile_$StringTemplate__lit_do_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"doWhile\",sort(\"StringTemplate\")),[lit(\"do\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"preStats\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"StringMiddle\")),layouts(\"LAYOUTLIST\"),label(\"postStats\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\"),layouts(\"LAYOUTLIST\"),lit(\"while\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"condition\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__$RegExp__char_class___range__0_46_range__48_59_range__61_61_range__63_91_range__93_16777215_ = (IConstructor) _read("prod(lex(\"RegExp\"),[\\char-class([range(0,46),range(48,59),range(61,61),range(63,91),range(93,16777215)])],{})", Factory.Production);
  private static final IConstructor prod__lit_syntax__char_class___range__115_115_char_class___range__121_121_char_class___range__110_110_char_class___range__116_116_char_class___range__97_97_char_class___range__120_120_ = (IConstructor) _read("prod(lit(\"syntax\"),[\\char-class([range(115,115)]),\\char-class([range(121,121)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(120,120)])],{})", Factory.Production);
  private static final IConstructor prod__anno_$Kind__lit_anno_ = (IConstructor) _read("prod(label(\"anno\",sort(\"Kind\")),[lit(\"anno\")],{})", Factory.Production);
  private static final IConstructor prod__variableBecomes_$Pattern__name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_pattern_$Pattern_ = (IConstructor) _read("prod(label(\"variableBecomes\",sort(\"Pattern\")),[label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"pattern\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_all__char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"all\"),[\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_map_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"map\")],{})", Factory.Production);
  private static final IConstructor prod__user_$Type__user_$UserType_ = (IConstructor) _read("prod(label(\"user\",sort(\"Type\")),[conditional(label(\"user\",sort(\"UserType\")),{delete(keywords(\"HeaderKeyword\"))})],{})", Factory.Production);
  private static final IConstructor prod__subscript_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_subscript_$Expression_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"subscript\",sort(\"Assignable\")),[label(\"receiver\",sort(\"Assignable\")),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"subscript\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__lit___62_62__char_class___range__62_62_char_class___range__62_62_ = (IConstructor) _read("prod(lit(\"\\>\\>\"),[\\char-class([range(62,62)]),\\char-class([range(62,62)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_private_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"private\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Type\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_module_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"module\")],{})", Factory.Production);
  private static final IConstructor prod__lit_try__char_class___range__116_116_char_class___range__114_114_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"try\"),[\\char-class([range(116,116)]),\\char-class([range(114,114)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__lit___63_61__char_class___range__63_63_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"?=\"),[\\char-class([range(63,63)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__column_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_column_$IntegerLiteral_ = (IConstructor) _read("prod(label(\"column\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"@\"),layouts(\"LAYOUTLIST\"),label(\"column\",sort(\"IntegerLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_throw_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"throw\")],{})", Factory.Production);
  private static final IConstructor prod__template_$StringMiddle__mid_$MidStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringMiddle_ = (IConstructor) _read("prod(label(\"template\",sort(\"StringMiddle\")),[label(\"mid\",lex(\"MidStringChars\")),layouts(\"LAYOUTLIST\"),label(\"template\",sort(\"StringTemplate\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"StringMiddle\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_bottom_up__char_class___range__98_98_char_class___range__111_111_char_class___range__116_116_char_class___range__116_116_char_class___range__111_111_char_class___range__109_109_char_class___range__45_45_char_class___range__117_117_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"bottom-up\"),[\\char-class([range(98,98)]),\\char-class([range(111,111)]),\\char-class([range(116,116)]),\\char-class([range(116,116)]),\\char-class([range(111,111)]),\\char-class([range(109,109)]),\\char-class([range(45,45)]),\\char-class([range(117,117)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__is_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit_is_$layouts_LAYOUTLIST_name_$Name_ = (IConstructor) _read("prod(label(\"is\",sort(\"Expression\")),[label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"is\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_break__char_class___range__98_98_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__107_107_ = (IConstructor) _read("prod(lit(\"break\"),[\\char-class([range(98,98)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(97,97)]),\\char-class([range(107,107)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_mod_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"mod\")],{})", Factory.Production);
  private static final IConstructor prod__mid_$ProtocolTail__mid_$MidProtocolChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$ProtocolTail_ = (IConstructor) _read("prod(label(\"mid\",sort(\"ProtocolTail\")),[label(\"mid\",lex(\"MidProtocolChars\")),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"ProtocolTail\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_rat_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"rat\")],{})", Factory.Production);
  private static final IConstructor prod__typedVariableBecomes_$Pattern__type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_pattern_$Pattern_ = (IConstructor) _read("prod(label(\"typedVariableBecomes\",sort(\"Pattern\")),[label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"pattern\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Range__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Range\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__associativityGroup_$Prod__associativity_$Assoc_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_group_$Prod_$layouts_LAYOUTLIST_lit___41__tag__Foldable = (IConstructor) _read("prod(label(\"associativityGroup\",sort(\"Prod\")),[label(\"associativity\",sort(\"Assoc\")),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"group\",sort(\"Prod\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__$MidPathChars__lit___62_$URLChars_lit___60_ = (IConstructor) _read("prod(lex(\"MidPathChars\"),[lit(\"\\>\"),lex(\"URLChars\"),lit(\"\\<\")],{})", Factory.Production);
  private static final IConstructor prod__lit_true__char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"true\"),[\\char-class([range(116,116)]),\\char-class([range(114,114)]),\\char-class([range(117,117)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__addition_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___43_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"addition\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"+\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",conditional(sort(\"Expression\"),{except(\"noMatch\"),except(\"match\")}))],{assoc(left())})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Sym__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_rel_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"rel\")],{})", Factory.Production);
  private static final IConstructor prod__selector_$DataTypeSelector__sort_$QualifiedName_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_production_$Name_ = (IConstructor) _read("prod(label(\"selector\",sort(\"DataTypeSelector\")),[label(\"sort\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),lit(\".\"),layouts(\"LAYOUTLIST\"),label(\"production\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_extend__char_class___range__101_101_char_class___range__120_120_char_class___range__116_116_char_class___range__101_101_char_class___range__110_110_char_class___range__100_100_ = (IConstructor) _read("prod(lit(\"extend\"),[\\char-class([range(101,101)]),\\char-class([range(120,120)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(100,100)])],{})", Factory.Production);
  private static final IConstructor prod__lit_append__char_class___range__97_97_char_class___range__112_112_char_class___range__112_112_char_class___range__101_101_char_class___range__110_110_char_class___range__100_100_ = (IConstructor) _read("prod(lit(\"append\"),[\\char-class([range(97,97)]),\\char-class([range(112,112)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(100,100)])],{})", Factory.Production);
  private static final IConstructor prod__$RealLiteral__iter__char_class___range__48_57_lit___46_iter_star__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_ = (IConstructor) _read("prod(lex(\"RealLiteral\"),[iter(\\char-class([range(48,57)])),conditional(lit(\".\"),{\\not-follow(lit(\".\"))}),\\iter-star(\\char-class([range(48,57)])),opt(\\char-class([range(68,68),range(70,70),range(100,100),range(102,102)]))],{})", Factory.Production);
  private static final IConstructor prod__lit_assoc__char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__111_111_char_class___range__99_99_ = (IConstructor) _read("prod(lit(\"assoc\"),[\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(99,99)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_bool_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"bool\")],{})", Factory.Production);
  private static final IConstructor prod__lit_map__char_class___range__109_109_char_class___range__97_97_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"map\"),[\\char-class([range(109,109)]),\\char-class([range(97,97)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__lit_real__char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"real\"),[\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(97,97)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__noExpression_$OptionalExpression__ = (IConstructor) _read("prod(label(\"noExpression\",sort(\"OptionalExpression\")),[],{})", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__45_45_range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(45,45),range(48,57),range(65,90),range(95,95),range(97,122)])))", Factory.Production);
  private static final IConstructor prod__nonEmptyBlock_$Statement__label_$Label_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"nonEmptyBlock\",sort(\"Statement\")),[label(\"label\",sort(\"Label\")),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"statements\",\\iter-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__bracket_$Class__lit___40_$layouts_LAYOUTLIST_charclass_$Class_$layouts_LAYOUTLIST_lit___41__bracket = (IConstructor) _read("prod(label(\"bracket\",sort(\"Class\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"charclass\",sort(\"Class\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{bracket()})", Factory.Production);
  private static final IConstructor prod__iterStarSep_$Sym__lit___123_$layouts_LAYOUTLIST_symbol_$Sym_$layouts_LAYOUTLIST_sep_$Sym_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit___42_ = (IConstructor) _read("prod(label(\"iterStarSep\",sort(\"Sym\")),[lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),label(\"sep\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"}\"),layouts(\"LAYOUTLIST\"),lit(\"*\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_datetime_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"datetime\")],{})", Factory.Production);
  private static final IConstructor prod__greaterThanOrEq_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"greaterThanOrEq\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"\\>=\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__arbitrary_$PatternWithAction__pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_statement_$Statement_ = (IConstructor) _read("prod(label(\"arbitrary\",sort(\"PatternWithAction\")),[label(\"pattern\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"statement\",sort(\"Statement\"))],{})", Factory.Production);
  private static final IConstructor prod__function_$Kind__lit_function_ = (IConstructor) _read("prod(label(\"function\",sort(\"Kind\")),[lit(\"function\")],{})", Factory.Production);
  private static final IConstructor prod__tuple_$Expression__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_ = (IConstructor) _read("prod(label(\"tuple\",sort(\"Expression\")),[lit(\"\\<\"),layouts(\"LAYOUTLIST\"),label(\"elements\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"\\>\")],{})", Factory.Production);
  private static final IConstructor prod__$Char__char_class___range__0_31_range__33_33_range__35_38_range__40_44_range__46_59_range__61_61_range__63_90_range__94_16777215__tag__category___67_111_110_115_116_97_110_116 = (IConstructor) _read("prod(lex(\"Char\"),[\\char-class([range(0,31),range(33,33),range(35,38),range(40,44),range(46,59),range(61,61),range(63,90),range(94,16777215)])],{tag(category(\"Constant\"))})", Factory.Production);
  private static final IConstructor prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_ = (IConstructor) _read("prod(lit(\"void\"),[\\char-class([range(118,118)]),\\char-class([range(111,111)]),\\char-class([range(105,105)]),\\char-class([range(100,100)])],{})", Factory.Production);
  private static final IConstructor prod__$RealLiteral__lit___46_iter__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_ = (IConstructor) _read("prod(lex(\"RealLiteral\"),[conditional(lit(\".\"),{\\not-precede(\\char-class([range(46,46)]))}),iter(\\char-class([range(48,57)])),\\char-class([range(69,69),range(101,101)]),opt(\\char-class([range(43,43),range(45,45)])),iter(\\char-class([range(48,57)])),opt(\\char-class([range(68,68),range(70,70),range(100,100),range(102,102)]))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_set_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"set\")],{})", Factory.Production);
  private static final IConstructor prod__and_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___38_38_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"and\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"&&\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__free_$TypeVar__lit___38_$layouts_LAYOUTLIST_name_$Name_ = (IConstructor) _read("prod(label(\"free\",sort(\"TypeVar\")),[lit(\"&\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__ifDefined_$Assignment__lit___63_61_ = (IConstructor) _read("prod(label(\"ifDefined\",sort(\"Assignment\")),[lit(\"?=\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_return_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"return\")],{})", Factory.Production);
  private static final IConstructor prod__voidClosure_$Expression__parameters_$Parameters_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"voidClosure\",sort(\"Expression\")),[label(\"parameters\",sort(\"Parameters\")),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"statements\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__empty__ = (IConstructor) _read("prod(empty(),[],{})", Factory.Production);
  private static final IConstructor prod__list_$FunctionModifiers__modifiers_iter_star_seps__$FunctionModifier__$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"list\",sort(\"FunctionModifiers\")),[label(\"modifiers\",\\iter-star-seps(sort(\"FunctionModifier\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__selector_$Type__selector_$DataTypeSelector_ = (IConstructor) _read("prod(label(\"selector\",sort(\"Type\")),[label(\"selector\",sort(\"DataTypeSelector\"))],{})", Factory.Production);
  private static final IConstructor prod__initialized_$Variable__name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_initial_$Expression_ = (IConstructor) _read("prod(label(\"initialized\",sort(\"Variable\")),[label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"initial\",sort(\"Expression\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_str_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"str\")],{})", Factory.Production);
  private static final IConstructor prod__all_$Prod__lhs_$Prod_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_rhs_$Prod__assoc__left = (IConstructor) _read("prod(label(\"all\",sort(\"Prod\")),[label(\"lhs\",sort(\"Prod\")),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Prod\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__throw_$Statement__lit_throw_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable = (IConstructor) _read("prod(label(\"throw\",sort(\"Statement\")),[lit(\"throw\"),layouts(\"LAYOUTLIST\"),label(\"statement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{assoc(\\non-assoc()),tag(breakable())})", Factory.Production);
  private static final IConstructor prod__lit_switch__char_class___range__115_115_char_class___range__119_119_char_class___range__105_105_char_class___range__116_116_char_class___range__99_99_char_class___range__104_104_ = (IConstructor) _read("prod(lit(\"switch\"),[\\char-class([range(115,115)]),\\char-class([range(119,119)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(99,99)]),\\char-class([range(104,104)])],{})", Factory.Production);
  private static final IConstructor prod__named_$TypeArg__type_$Type_$layouts_LAYOUTLIST_name_$Name_ = (IConstructor) _read("prod(label(\"named\",sort(\"TypeArg\")),[label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__follow_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___62_62_$layouts_LAYOUTLIST_match_$Sym__assoc__left = (IConstructor) _read("prod(label(\"follow\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"\\>\\>\"),layouts(\"LAYOUTLIST\"),label(\"match\",sort(\"Sym\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__lit_solve__char_class___range__115_115_char_class___range__111_111_char_class___range__108_108_char_class___range__118_118_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"solve\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(108,108)]),\\char-class([range(118,118)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_layout__char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"layout\"),[\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__48_57_opt__char_class___range__48_57 = (IConstructor) _read("regular(seq([\\char-class([range(48,57)]),opt(\\char-class([range(48,57)]))]))", Factory.Production);
  private static final IConstructor prod__empty_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name__tag__Folded_tag__category___67_111_109_109_101_110_116 = (IConstructor) _read("prod(label(\"empty\",sort(\"Tag\")),[lit(\"@\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\"))],{tag(Folded()),tag(category(\"Comment\"))})", Factory.Production);
  private static final IConstructor prod__callOrTree_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"callOrTree\",sort(\"Expression\")),[label(\"expression\",conditional(sort(\"Expression\"),{except(\"transitiveReflexiveClosure\"),except(\"transitiveClosure\"),except(\"isDefined\")})),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"arguments\",\\iter-star-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__annotation_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_annotation_$Name_ = (IConstructor) _read("prod(label(\"annotation\",sort(\"Assignable\")),[label(\"receiver\",sort(\"Assignable\")),layouts(\"LAYOUTLIST\"),lit(\"@\"),layouts(\"LAYOUTLIST\"),label(\"annotation\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_extend_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"extend\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Catch__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Catch\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__character_$Range__character_$Char_ = (IConstructor) _read("prod(label(\"character\",sort(\"Range\")),[label(\"character\",lex(\"Char\"))],{})", Factory.Production);
  private static final IConstructor prod__tag_$ProdModifier__tag_$Tag_ = (IConstructor) _read("prod(label(\"tag\",sort(\"ProdModifier\")),[label(\"tag\",sort(\"Tag\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_tag_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"tag\")],{})", Factory.Production);
  private static final IConstructor prod__replacing_$PatternWithAction__pattern_$Pattern_$layouts_LAYOUTLIST_lit___61_62_$layouts_LAYOUTLIST_replacement_$Replacement_ = (IConstructor) _read("prod(label(\"replacing\",sort(\"PatternWithAction\")),[label(\"pattern\",sort(\"Pattern\")),layouts(\"LAYOUTLIST\"),lit(\"=\\>\"),layouts(\"LAYOUTLIST\"),label(\"replacement\",sort(\"Replacement\"))],{})", Factory.Production);
  private static final IConstructor prod__union_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___124_124_$layouts_LAYOUTLIST_rhs_$Class__assoc__left = (IConstructor) _read("prod(label(\"union\",sort(\"Class\")),[label(\"lhs\",sort(\"Class\")),layouts(\"LAYOUTLIST\"),lit(\"||\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Class\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor regular__iter_star__$StringCharacter = (IConstructor) _read("regular(\\iter-star(lex(\"StringCharacter\")))", Factory.Production);
  private static final IConstructor prod__$HeaderKeyword__lit_keyword_ = (IConstructor) _read("prod(keywords(\"HeaderKeyword\"),[lit(\"keyword\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_list_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"list\")],{})", Factory.Production);
  private static final IConstructor prod__iterSep_$Sym__lit___123_$layouts_LAYOUTLIST_symbol_$Sym_$layouts_LAYOUTLIST_sep_$Sym_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit___43_ = (IConstructor) _read("prod(label(\"iterSep\",sort(\"Sym\")),[lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),label(\"sep\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"}\"),layouts(\"LAYOUTLIST\"),lit(\"+\")],{})", Factory.Production);
  private static final IConstructor prod__rational_$BasicType__lit_rat_ = (IConstructor) _read("prod(label(\"rational\",sort(\"BasicType\")),[lit(\"rat\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$EvalCommand__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"EvalCommand\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_public_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"public\")],{})", Factory.Production);
  private static final IConstructor prod__fieldAccess_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_field_$Name_ = (IConstructor) _read("prod(label(\"fieldAccess\",sort(\"Expression\")),[label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\".\"),layouts(\"LAYOUTLIST\"),label(\"field\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__bracket_$Expression__lit___40_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___41__bracket = (IConstructor) _read("prod(label(\"bracket\",sort(\"Expression\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{bracket()})", Factory.Production);
  private static final IConstructor prod__$layouts_$default$__ = (IConstructor) _read("prod(layouts(\"$default$\"),[],{})", Factory.Production);
  private static final IConstructor prod__lit_assert__char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"assert\"),[\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit___60_58__char_class___range__60_60_char_class___range__58_58_ = (IConstructor) _read("prod(lit(\"\\<:\"),[\\char-class([range(60,60)]),\\char-class([range(58,58)])],{})", Factory.Production);
  private static final IConstructor prod__bracket_$ProdModifier__lit_bracket_ = (IConstructor) _read("prod(label(\"bracket\",sort(\"ProdModifier\")),[lit(\"bracket\")],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__nonEmptyBlock_$Expression__lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"nonEmptyBlock\",sort(\"Expression\")),[lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"statements\",\\iter-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__lit_lexical__char_class___range__108_108_char_class___range__101_101_char_class___range__120_120_char_class___range__105_105_char_class___range__99_99_char_class___range__97_97_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"lexical\"),[\\char-class([range(108,108)]),\\char-class([range(101,101)]),\\char-class([range(120,120)]),\\char-class([range(105,105)]),\\char-class([range(99,99)]),\\char-class([range(97,97)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__name_$UserType__name_$QualifiedName_ = (IConstructor) _read("prod(label(\"name\",sort(\"UserType\")),[label(\"name\",sort(\"QualifiedName\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___60_45__char_class___range__60_60_char_class___range__45_45_ = (IConstructor) _read("prod(lit(\"\\<-\"),[\\char-class([range(60,60)]),\\char-class([range(45,45)])],{})", Factory.Production);
  private static final IConstructor prod__syntax_$Import__syntax_$SyntaxDefinition_ = (IConstructor) _read("prod(label(\"syntax\",sort(\"Import\")),[label(\"syntax\",sort(\"SyntaxDefinition\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_filter__char_class___range__102_102_char_class___range__105_105_char_class___range__108_108_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_ = (IConstructor) _read("prod(lit(\"filter\"),[\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(108,108)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)])],{})", Factory.Production);
  private static final IConstructor prod__java_$FunctionModifier__lit_java_ = (IConstructor) _read("prod(label(\"java\",sort(\"FunctionModifier\")),[lit(\"java\")],{})", Factory.Production);
  private static final IConstructor prod__$TimePartNoTZ__char_class___range__48_50_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"TimePartNoTZ\"),[\\char-class([range(48,50)]),\\char-class([range(48,57)]),lit(\":\"),\\char-class([range(48,53)]),\\char-class([range(48,57)]),lit(\":\"),\\char-class([range(48,53)]),\\char-class([range(48,57)]),opt(seq([\\char-class([range(44,44),range(46,46)]),\\char-class([range(48,57)]),opt(seq([\\char-class([range(48,57)]),opt(\\char-class([range(48,57)]))]))]))],{})", Factory.Production);
  private static final IConstructor prod__lit_start__char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"start\"),[\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit___58_58__char_class___range__58_58_char_class___range__58_58_ = (IConstructor) _read("prod(lit(\"::\"),[\\char-class([range(58,58)]),\\char-class([range(58,58)])],{})", Factory.Production);
  private static final IConstructor prod__$Backslash__char_class___range__92_92_ = (IConstructor) _read("prod(lex(\"Backslash\"),[conditional(\\char-class([range(92,92)]),{\\not-follow(\\char-class([range(47,47),range(60,60),range(62,62),range(92,92)]))})],{})", Factory.Production);
  private static final IConstructor prod__default_$Mapping__$Pattern__from_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Pattern_ = (IConstructor) _read("prod(label(\"default\",\\parameterized-sort(\"Mapping\",[sort(\"Pattern\")])),[label(\"from\",conditional(sort(\"Pattern\"),{except(\"ifDefinedOtherwise\")})),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"to\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor prod__lit___58_61__char_class___range__58_58_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\":=\"),[\\char-class([range(58,58)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__bottomUp_$Strategy__lit_bottom_up_ = (IConstructor) _read("prod(label(\"bottomUp\",sort(\"Strategy\")),[lit(\"bottom-up\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_throws_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"throws\")],{})", Factory.Production);
  private static final IConstructor prod__map_$Expression__lit___40_$layouts_LAYOUTLIST_mappings_iter_star_seps__$Mapping__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"map\",sort(\"Expression\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"mappings\",\\iter-star-seps(\\parameterized-sort(\"Mapping\",[sort(\"Expression\")]),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__private_$Visibility__lit_private_ = (IConstructor) _read("prod(label(\"private\",sort(\"Visibility\")),[lit(\"private\")],{})", Factory.Production);
  private static final IConstructor prod__try_$Statement__lit_try_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_handlers_iter_seps__$Catch__$layouts_LAYOUTLIST__assoc__non_assoc_tag__breakable = (IConstructor) _read("prod(label(\"try\",sort(\"Statement\")),[lit(\"try\"),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"Statement\")),layouts(\"LAYOUTLIST\"),label(\"handlers\",\\iter-seps(sort(\"Catch\"),[layouts(\"LAYOUTLIST\")]))],{assoc(\\non-assoc()),tag(breakable())})", Factory.Production);
  private static final IConstructor prod__default_$Assignment__lit___61_ = (IConstructor) _read("prod(label(\"default\",sort(\"Assignment\")),[lit(\"=\")],{})", Factory.Production);
  private static final IConstructor prod__interpolated_$PathPart__pre_$PrePathChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$PathTail_ = (IConstructor) _read("prod(label(\"interpolated\",sort(\"PathPart\")),[label(\"pre\",lex(\"PrePathChars\")),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"PathTail\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_switch_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"switch\")],{})", Factory.Production);
  private static final IConstructor prod__$DatePart__char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_lit___char_class___range__48_49_char_class___range__48_57_lit___char_class___range__48_51_char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"DatePart\"),[\\char-class([range(48,57)]),\\char-class([range(48,57)]),\\char-class([range(48,57)]),\\char-class([range(48,57)]),lit(\"-\"),\\char-class([range(48,49)]),\\char-class([range(48,57)]),lit(\"-\"),\\char-class([range(48,51)]),\\char-class([range(48,57)])],{})", Factory.Production);
  private static final IConstructor prod__$PostStringChars__char_class___range__62_62_iter_star__$StringCharacter_char_class___range__34_34__tag__category___67_111_110_115_116_97_110_116 = (IConstructor) _read("prod(lex(\"PostStringChars\"),[\\char-class([range(62,62)]),\\iter-star(lex(\"StringCharacter\")),\\char-class([range(34,34)])],{tag(category(\"Constant\"))})", Factory.Production);
  private static final IConstructor prod__anti_$Pattern__lit___33_$layouts_LAYOUTLIST_pattern_$Pattern_ = (IConstructor) _read("prod(label(\"anti\",sort(\"Pattern\")),[lit(\"!\"),layouts(\"LAYOUTLIST\"),label(\"pattern\",sort(\"Pattern\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_for__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_ = (IConstructor) _read("prod(lit(\"for\"),[\\char-class([range(102,102)]),\\char-class([range(111,111)]),\\char-class([range(114,114)])],{})", Factory.Production);
  private static final IConstructor prod__octalIntegerLiteral_$IntegerLiteral__octal_$OctalIntegerLiteral_ = (IConstructor) _read("prod(label(\"octalIntegerLiteral\",sort(\"IntegerLiteral\")),[label(\"octal\",lex(\"OctalIntegerLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_left__char_class___range__108_108_char_class___range__101_101_char_class___range__102_102_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"left\"),[\\char-class([range(108,108)]),\\char-class([range(101,101)]),\\char-class([range(102,102)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__for_$StringTemplate__lit_for_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"for\",sort(\"StringTemplate\")),[lit(\"for\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"generators\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"preStats\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"StringMiddle\")),layouts(\"LAYOUTLIST\"),label(\"postStats\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__$TimePartNoTZ__char_class___range__48_50_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"TimePartNoTZ\"),[\\char-class([range(48,50)]),\\char-class([range(48,57)]),\\char-class([range(48,53)]),\\char-class([range(48,57)]),\\char-class([range(48,53)]),\\char-class([range(48,57)]),opt(seq([\\char-class([range(44,44),range(46,46)]),\\char-class([range(48,57)]),opt(seq([\\char-class([range(48,57)]),opt(\\char-class([range(48,57)]))]))]))],{})", Factory.Production);
  private static final IConstructor regular__empty = (IConstructor) _read("regular(empty())", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__0_9_range__11_16777215 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(0,9),range(11,16777215)])))", Factory.Production);
  private static final IConstructor prod__lit_bracket__char_class___range__98_98_char_class___range__114_114_char_class___range__97_97_char_class___range__99_99_char_class___range__107_107_char_class___range__101_101_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"bracket\"),[\\char-class([range(98,98)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(107,107)]),\\char-class([range(101,101)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__nonterminal_$Sym__nonterminal_$Nonterminal_ = (IConstructor) _read("prod(label(\"nonterminal\",sort(\"Sym\")),[conditional(label(\"nonterminal\",lex(\"Nonterminal\")),{\\not-follow(lit(\"[\"))})],{})", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__0_8_range__11_12_range__14_31_range__33_59_range__61_123_range__125_16777215 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(0,8),range(11,12),range(14,31),range(33,59),range(61,123),range(125,16777215)])))", Factory.Production);
  private static final IConstructor prod__reference_$Prod__lit___58_$layouts_LAYOUTLIST_referenced_$Name_ = (IConstructor) _read("prod(label(\"reference\",sort(\"Prod\")),[lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"referenced\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__fromTo_$Range__start_$Char_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_end_$Char_ = (IConstructor) _read("prod(label(\"fromTo\",sort(\"Range\")),[label(\"start\",lex(\"Char\")),layouts(\"LAYOUTLIST\"),lit(\"-\"),layouts(\"LAYOUTLIST\"),label(\"end\",lex(\"Char\"))],{})", Factory.Production);
  private static final IConstructor prod__ifThen_$StringTemplate__lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"ifThen\",sort(\"StringTemplate\")),[lit(\"if\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"conditions\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"preStats\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"StringMiddle\")),layouts(\"LAYOUTLIST\"),label(\"postStats\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__empty_$DataTarget__ = (IConstructor) _read("prod(label(\"empty\",sort(\"DataTarget\")),[],{})", Factory.Production);
  private static final IConstructor prod__test_$FunctionModifier__lit_test_ = (IConstructor) _read("prod(label(\"test\",sort(\"FunctionModifier\")),[lit(\"test\")],{})", Factory.Production);
  private static final IConstructor prod__$Comment__lit___47_47_iter_star__char_class___range__0_9_range__11_16777215__tag__category___67_111_109_109_101_110_116 = (IConstructor) _read("prod(lex(\"Comment\"),[lit(\"//\"),conditional(\\iter-star(\\char-class([range(0,9),range(11,16777215)])),{\\not-follow(\\char-class([range(9,9),range(13,13),range(32,32),range(65,65),range(160,160),range(5760,5760),range(8192,8192),range(8239,8239),range(8287,8287),range(12288,12288)])),\\end-of-line()})],{tag(category(\"Comment\"))})", Factory.Production);
  private static final IConstructor prod__default_$Formals__formals_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"default\",sort(\"Formals\")),[label(\"formals\",\\iter-star-seps(sort(\"Pattern\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__unInitialized_$Variable__name_$Name_ = (IConstructor) _read("prod(label(\"unInitialized\",sort(\"Variable\")),[label(\"name\",lex(\"Name\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_for_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"for\")],{})", Factory.Production);
  private static final IConstructor prod__$StringCharacter__lit___92_char_class___range__34_34_range__39_39_range__60_60_range__62_62_range__92_92_range__98_98_range__102_102_range__110_110_range__114_114_range__116_116_ = (IConstructor) _read("prod(lex(\"StringCharacter\"),[lit(\"\\\\\"),\\char-class([range(34,34),range(39,39),range(60,60),range(62,62),range(92,92),range(98,98),range(102,102),range(110,110),range(114,114),range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_insert__char_class___range__105_105_char_class___range__110_110_char_class___range__115_115_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"insert\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_it_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"it\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_in_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"in\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_else_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"else\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_if_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"if\")],{})", Factory.Production);
  private static final IConstructor prod__iterStar_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___42_ = (IConstructor) _read("prod(label(\"iterStar\",sort(\"Sym\")),[label(\"symbol\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"*\")],{})", Factory.Production);
  private static final IConstructor prod__lit_history__char_class___range__104_104_char_class___range__105_105_char_class___range__115_115_char_class___range__116_116_char_class___range__111_111_char_class___range__114_114_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"history\"),[\\char-class([range(104,104)]),\\char-class([range(105,105)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__$LAYOUT__char_class___range__9_13_range__32_32_range__133_133_range__160_160_range__5760_5760_range__6158_6158_range__8192_8202_range__8232_8233_range__8239_8239_range__8287_8287_range__12288_12288_ = (IConstructor) _read("prod(lex(\"LAYOUT\"),[\\char-class([range(9,13),range(32,32),range(133,133),range(160,160),range(5760,5760),range(6158,6158),range(8192,8202),range(8232,8233),range(8239,8239),range(8287,8287),range(12288,12288)])],{})", Factory.Production);
  private static final IConstructor prod__listModules_$ShellCommand__lit_modules_ = (IConstructor) _read("prod(label(\"listModules\",sort(\"ShellCommand\")),[lit(\"modules\")],{})", Factory.Production);
  private static final IConstructor prod__external_$Import__lit_import_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_at_$LocationLiteral_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"external\",sort(\"Import\")),[lit(\"import\"),layouts(\"LAYOUTLIST\"),label(\"name\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"at\",sort(\"LocationLiteral\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__annotation_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_anno_$layouts_LAYOUTLIST_annoType_$Type_$layouts_LAYOUTLIST_onType_$Type_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"annotation\",sort(\"Declaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),lit(\"anno\"),layouts(\"LAYOUTLIST\"),label(\"annoType\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"onType\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),lit(\"@\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__start__$EvalCommand__$layouts_LAYOUTLIST_top_$EvalCommand_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(start(sort(\"EvalCommand\")),[layouts(\"LAYOUTLIST\"),label(\"top\",sort(\"EvalCommand\")),layouts(\"LAYOUTLIST\")],{})", Factory.Production);
  private static final IConstructor prod__$Char__$UnicodeEscape__tag__category___67_111_110_115_116_97_110_116 = (IConstructor) _read("prod(lex(\"Char\"),[lex(\"UnicodeEscape\")],{tag(category(\"Constant\"))})", Factory.Production);
  private static final IConstructor prod__characterClass_$Sym__charClass_$Class_ = (IConstructor) _read("prod(label(\"characterClass\",sort(\"Sym\")),[label(\"charClass\",sort(\"Class\"))],{})", Factory.Production);
  private static final IConstructor prod__layout_$SyntaxDefinition__vis_$Visibility_$layouts_LAYOUTLIST_lit_layout_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable = (IConstructor) _read("prod(label(\"layout\",sort(\"SyntaxDefinition\")),[label(\"vis\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),lit(\"layout\"),layouts(\"LAYOUTLIST\"),label(\"defined\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"production\",sort(\"Prod\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__$RealLiteral__iter__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_ = (IConstructor) _read("prod(lex(\"RealLiteral\"),[iter(\\char-class([range(48,57)])),\\char-class([range(69,69),range(101,101)]),opt(\\char-class([range(43,43),range(45,45)])),iter(\\char-class([range(48,57)])),opt(\\char-class([range(68,68),range(70,70),range(100,100),range(102,102)]))],{})", Factory.Production);
  private static final IConstructor prod__list_$BasicType__lit_list_ = (IConstructor) _read("prod(label(\"list\",sort(\"BasicType\")),[lit(\"list\")],{})", Factory.Production);
  private static final IConstructor prod__associativity_$ProdModifier__associativity_$Assoc_ = (IConstructor) _read("prod(label(\"associativity\",sort(\"ProdModifier\")),[label(\"associativity\",sort(\"Assoc\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_type_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"type\")],{})", Factory.Production);
  private static final IConstructor regular__iter_star_seps__$Mapping__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(\\parameterized-sort(\"Mapping\",[sort(\"Expression\")]),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__lit_undeclare__char_class___range__117_117_char_class___range__110_110_char_class___range__100_100_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__114_114_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"undeclare\"),[\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor regular__opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57 = (IConstructor) _read("regular(opt(seq([\\char-class([range(44,44),range(46,46)]),\\char-class([range(48,57)]),opt(seq([\\char-class([range(48,57)]),opt(\\char-class([range(48,57)]))]))])))", Factory.Production);
  private static final IConstructor prod__$DateAndTime__lit___36_$DatePart_lit_T_$TimePartNoTZ_$TimeZonePart_ = (IConstructor) _read("prod(lex(\"DateAndTime\"),[lit(\"$\"),lex(\"DatePart\"),lit(\"T\"),lex(\"TimePartNoTZ\"),lex(\"TimeZonePart\")],{})", Factory.Production);
  private static final IConstructor prod__listDeclarations_$ShellCommand__lit_declarations_ = (IConstructor) _read("prod(label(\"listDeclarations\",sort(\"ShellCommand\")),[lit(\"declarations\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_o_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"o\")],{})", Factory.Production);
  private static final IConstructor prod__division_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___47_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"division\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"/\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__post_$StringTail__post_$PostStringChars_ = (IConstructor) _read("prod(label(\"post\",sort(\"StringTail\")),[label(\"post\",lex(\"PostStringChars\"))],{})", Factory.Production);
  private static final IConstructor prod__actualsRenaming_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_actuals_$ModuleActuals_$layouts_LAYOUTLIST_renamings_$Renamings_ = (IConstructor) _read("prod(label(\"actualsRenaming\",sort(\"ImportedModule\")),[label(\"name\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),label(\"actuals\",sort(\"ModuleActuals\")),layouts(\"LAYOUTLIST\"),label(\"renamings\",sort(\"Renamings\"))],{})", Factory.Production);
  private static final IConstructor prod__comprehension_$Expression__comprehension_$Comprehension_ = (IConstructor) _read("prod(label(\"comprehension\",sort(\"Expression\")),[label(\"comprehension\",sort(\"Comprehension\"))],{})", Factory.Production);
  private static final IConstructor prod__$TagString__lit___123_contents_iter_star__alt___$TagString_char_class___range__0_122_range__124_124_range__126_16777215_seq___lit___92_char_class___range__123_123_range__125_125_lit___125_ = (IConstructor) _read("prod(lex(\"TagString\"),[conditional(lit(\"{\"),{\\not-precede(lit(\"\\\\\"))}),label(\"contents\",\\iter-star(alt({lex(\"TagString\"),\\char-class([range(0,122),range(124,124),range(126,16777215)]),seq([lit(\"\\\\\"),\\char-class([range(123,123),range(125,125)])])}))),conditional(lit(\"}\"),{\\not-precede(lit(\"\\\\\"))})],{})", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__9_9_range__32_32_range__65_65_range__160_160_range__5760_5760_range__8192_8192_range__8239_8239_range__8287_8287_range__12288_12288 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(9,9),range(32,32),range(65,65),range(160,160),range(5760,5760),range(8192,8192),range(8239,8239),range(8287,8287),range(12288,12288)])))", Factory.Production);
  private static final IConstructor prod__emptyStatement_$Statement__lit___59_ = (IConstructor) _read("prod(label(\"emptyStatement\",sort(\"Statement\")),[lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__set_$Pattern__lit___123_$layouts_LAYOUTLIST_elements_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"set\",sort(\"Pattern\")),[lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"elements\",\\iter-star-seps(sort(\"Pattern\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__nonEquals_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___33_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"nonEquals\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"!=\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__simpleCharclass_$Class__lit___91_$layouts_LAYOUTLIST_ranges_iter_star_seps__$Range__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"simpleCharclass\",sort(\"Class\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"ranges\",\\iter-star-seps(sort(\"Range\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__lit_rel__char_class___range__114_114_char_class___range__101_101_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"rel\"),[\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__typeArguments_$FunctionType__type_$Type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"typeArguments\",sort(\"FunctionType\")),[label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"arguments\",\\iter-star-seps(sort(\"TypeArg\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"TimeZonePart\"),[\\char-class([range(43,43),range(45,45)]),\\char-class([range(48,49)]),\\char-class([range(48,57)])],{})", Factory.Production);
  private static final IConstructor prod__empty_$Bound__ = (IConstructor) _read("prod(label(\"empty\",sort(\"Bound\")),[],{})", Factory.Production);
  private static final IConstructor prod__lit___61_61_62__char_class___range__61_61_char_class___range__61_61_char_class___range__62_62_ = (IConstructor) _read("prod(lit(\"==\\>\"),[\\char-class([range(61,61)]),\\char-class([range(61,61)]),\\char-class([range(62,62)])],{})", Factory.Production);
  private static final IConstructor prod__sliceStep_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_optFirst_$OptionalExpression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_second_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_optLast_$OptionalExpression_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"sliceStep\",sort(\"Expression\")),[conditional(label(\"expression\",sort(\"Expression\")),{except(\"transitiveReflexiveClosure\"),except(\"transitiveClosure\"),except(\"isDefined\")}),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"optFirst\",sort(\"OptionalExpression\")),layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\"),label(\"second\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"..\"),layouts(\"LAYOUTLIST\"),label(\"optLast\",sort(\"OptionalExpression\")),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__bracket_$Type__lit___40_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___41__bracket = (IConstructor) _read("prod(label(\"bracket\",sort(\"Type\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),lit(\")\")],{bracket()})", Factory.Production);
  private static final IConstructor prod__all_$Expression__lit_all_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"all\",sort(\"Expression\")),[lit(\"all\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"generators\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__parametrized_$Sym__nonterminal_$Nonterminal_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$Sym__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"parametrized\",sort(\"Sym\")),[conditional(label(\"nonterminal\",lex(\"Nonterminal\")),{follow(lit(\"[\"))}),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"parameters\",\\iter-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Case__$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Case\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__$layouts_LAYOUTLIST__iter_star__$LAYOUT_ = (IConstructor) _read("prod(layouts(\"LAYOUTLIST\"),[conditional(\\iter-star(lex(\"LAYOUT\")),{\\not-follow(lit(\"//\")),\\not-follow(lit(\"/*\")),\\not-follow(\\char-class([range(9,13),range(32,32),range(133,133),range(160,160),range(5760,5760),range(6158,6158),range(8192,8202),range(8232,8233),range(8239,8239),range(8287,8287),range(12288,12288)]))})],{})", Factory.Production);
  private static final IConstructor prod__$PrePathChars__$URLChars_lit___60_ = (IConstructor) _read("prod(lex(\"PrePathChars\"),[lex(\"URLChars\"),lit(\"\\<\")],{})", Factory.Production);
  private static final IConstructor prod__$HeaderKeyword__lit_syntax_ = (IConstructor) _read("prod(keywords(\"HeaderKeyword\"),[lit(\"syntax\")],{})", Factory.Production);
  private static final IConstructor prod__lexical_$SyntaxDefinition__lit_lexical_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable = (IConstructor) _read("prod(label(\"lexical\",sort(\"SyntaxDefinition\")),[lit(\"lexical\"),layouts(\"LAYOUTLIST\"),label(\"defined\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"production\",sort(\"Prod\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__lit_continue__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__110_110_char_class___range__117_117_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"continue\"),[\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(117,117)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__tag_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_tag_$layouts_LAYOUTLIST_kind_$Kind_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit_on_$layouts_LAYOUTLIST_types_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"tag\",sort(\"Declaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),lit(\"tag\"),layouts(\"LAYOUTLIST\"),label(\"kind\",sort(\"Kind\")),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"on\"),layouts(\"LAYOUTLIST\"),label(\"types\",\\iter-seps(sort(\"Type\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__lit___60_60_61__char_class___range__60_60_char_class___range__60_60_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"\\<\\<=\"),[\\char-class([range(60,60)]),\\char-class([range(60,60)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__$LAYOUT__$Comment_ = (IConstructor) _read("prod(lex(\"LAYOUT\"),[lex(\"Comment\")],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_tuple_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"tuple\")],{})", Factory.Production);
  private static final IConstructor prod__map_$Pattern__lit___40_$layouts_LAYOUTLIST_mappings_iter_star_seps__$Mapping__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"map\",sort(\"Pattern\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"mappings\",\\iter-star-seps(\\parameterized-sort(\"Mapping\",[sort(\"Pattern\")]),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__setAnnotation_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_value_$Expression_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"setAnnotation\",sort(\"Expression\")),[label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),lit(\"@\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"value\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__reifyType_$Expression__lit___35_$layouts_LAYOUTLIST_type_$Type_ = (IConstructor) _read("prod(label(\"reifyType\",sort(\"Expression\")),[lit(\"#\"),layouts(\"LAYOUTLIST\"),conditional(label(\"type\",sort(\"Type\")),{\\not-follow(lit(\"[\")),except(\"selector\")})],{})", Factory.Production);
  private static final IConstructor prod__$HeaderKeyword__lit_lexical_ = (IConstructor) _read("prod(keywords(\"HeaderKeyword\"),[lit(\"lexical\")],{})", Factory.Production);
  private static final IConstructor prod__$ProtocolChars__char_class___range__124_124_$URLChars_lit___58_47_47_ = (IConstructor) _read("prod(lex(\"ProtocolChars\"),[\\char-class([range(124,124)]),lex(\"URLChars\"),conditional(lit(\"://\"),{\\not-follow(\\char-class([range(9,10),range(13,13),range(32,32),range(65,65),range(160,160),range(5760,5760),range(8192,8192),range(8239,8239),range(8287,8287),range(12288,12288)]))})],{})", Factory.Production);
  private static final IConstructor prod__interpolated_$StringMiddle__mid_$MidStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringMiddle_ = (IConstructor) _read("prod(label(\"interpolated\",sort(\"StringMiddle\")),[label(\"mid\",lex(\"MidStringChars\")),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),label(\"tail\",sort(\"StringMiddle\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_dynamic__char_class___range__100_100_char_class___range__121_121_char_class___range__110_110_char_class___range__97_97_char_class___range__109_109_char_class___range__105_105_char_class___range__99_99_ = (IConstructor) _read("prod(lit(\"dynamic\"),[\\char-class([range(100,100)]),\\char-class([range(121,121)]),\\char-class([range(110,110)]),\\char-class([range(97,97)]),\\char-class([range(109,109)]),\\char-class([range(105,105)]),\\char-class([range(99,99)])],{})", Factory.Production);
  private static final IConstructor prod__withThrows_$Signature__modifiers_$FunctionModifiers_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_parameters_$Parameters_$layouts_LAYOUTLIST_lit_throws_$layouts_LAYOUTLIST_exceptions_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"withThrows\",sort(\"Signature\")),[label(\"modifiers\",sort(\"FunctionModifiers\")),layouts(\"LAYOUTLIST\"),label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),label(\"parameters\",sort(\"Parameters\")),layouts(\"LAYOUTLIST\"),lit(\"throws\"),layouts(\"LAYOUTLIST\"),label(\"exceptions\",\\iter-seps(sort(\"Type\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__map_$Comprehension__lit___40_$layouts_LAYOUTLIST_from_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41__tag__breakable___123_102_114_111_109_44_116_111_44_103_101_110_101_114_97_116_111_114_115_125 = (IConstructor) _read("prod(label(\"map\",sort(\"Comprehension\")),[lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"from\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\":\"),layouts(\"LAYOUTLIST\"),label(\"to\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),label(\"generators\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{tag(breakable(\"{from,to,generators}\"))})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Sym__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__index_$Field__fieldIndex_$IntegerLiteral_ = (IConstructor) _read("prod(label(\"index\",sort(\"Field\")),[label(\"fieldIndex\",sort(\"IntegerLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_false_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"false\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Sym__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Sym\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__qualifiedName_$Expression__qualifiedName_$QualifiedName_ = (IConstructor) _read("prod(label(\"qualifiedName\",sort(\"Expression\")),[label(\"qualifiedName\",sort(\"QualifiedName\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_list__char_class___range__108_108_char_class___range__105_105_char_class___range__115_115_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"list\"),[\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(115,115)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__keyword_$SyntaxDefinition__lit_keyword_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable = (IConstructor) _read("prod(label(\"keyword\",sort(\"SyntaxDefinition\")),[lit(\"keyword\"),layouts(\"LAYOUTLIST\"),label(\"defined\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"production\",sort(\"Prod\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(Foldable())})", Factory.Production);
  private static final IConstructor prod__$DecimalIntegerLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"DecimalIntegerLiteral\"),[\\char-class([range(49,57)]),conditional(\\iter-star(\\char-class([range(48,57)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__abstract_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"abstract\",sort(\"FunctionDeclaration\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),label(\"visibility\",sort(\"Visibility\")),layouts(\"LAYOUTLIST\"),label(\"signature\",sort(\"Signature\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__greaterThan_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"greaterThan\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"\\>\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__lit_test__char_class___range__116_116_char_class___range__101_101_char_class___range__115_115_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"test\"),[\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(115,115)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__present_$Start__lit_start_ = (IConstructor) _read("prod(label(\"present\",sort(\"Start\")),[lit(\"start\")],{})", Factory.Production);
  private static final IConstructor prod__assert_$Statement__lit_assert_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"assert\",sort(\"Statement\")),[lit(\"assert\"),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__closure_$Expression__type_$Type_$layouts_LAYOUTLIST_parameters_$Parameters_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"closure\",sort(\"Expression\")),[label(\"type\",sort(\"Type\")),layouts(\"LAYOUTLIST\"),label(\"parameters\",sort(\"Parameters\")),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"statements\",\\iter-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__bounded_$TypeVar__lit___38_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___60_58_$layouts_LAYOUTLIST_bound_$Type_ = (IConstructor) _read("prod(label(\"bounded\",sort(\"TypeVar\")),[lit(\"&\"),layouts(\"LAYOUTLIST\"),label(\"name\",lex(\"Name\")),layouts(\"LAYOUTLIST\"),lit(\"\\<:\"),layouts(\"LAYOUTLIST\"),label(\"bound\",sort(\"Type\"))],{})", Factory.Production);
  private static final IConstructor prod__lit_false__char_class___range__102_102_char_class___range__97_97_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"false\"),[\\char-class([range(102,102)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(115,115)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__set_$BasicType__lit_set_ = (IConstructor) _read("prod(label(\"set\",sort(\"BasicType\")),[lit(\"set\")],{})", Factory.Production);
  private static final IConstructor prod__$NamedBackslash__char_class___range__92_92_ = (IConstructor) _read("prod(lex(\"NamedBackslash\"),[conditional(\\char-class([range(92,92)]),{\\not-follow(\\char-class([range(60,60),range(62,62),range(92,92)]))})],{})", Factory.Production);
  private static final IConstructor prod__lit_lrel__char_class___range__108_108_char_class___range__114_114_char_class___range__101_101_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"lrel\"),[\\char-class([range(108,108)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__in_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_in_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc = (IConstructor) _read("prod(label(\"in\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"in\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__complement_$Class__lit___33_$layouts_LAYOUTLIST_charClass_$Class_ = (IConstructor) _read("prod(label(\"complement\",sort(\"Class\")),[lit(\"!\"),layouts(\"LAYOUTLIST\"),label(\"charClass\",sort(\"Class\"))],{})", Factory.Production);
  private static final IConstructor prod__list_$Pattern__lit___91_$layouts_LAYOUTLIST_elements_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"list\",sort(\"Pattern\")),[lit(\"[\"),layouts(\"LAYOUTLIST\"),label(\"elements\",\\iter-star-seps(sort(\"Pattern\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__start__$Commands__$layouts_LAYOUTLIST_top_$Commands_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(start(sort(\"Commands\")),[layouts(\"LAYOUTLIST\"),label(\"top\",sort(\"Commands\")),layouts(\"LAYOUTLIST\")],{})", Factory.Production);
  private static final IConstructor prod__lit_set__char_class___range__115_115_char_class___range__101_101_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"set\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_non_assoc_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"non-assoc\")],{})", Factory.Production);
  private static final IConstructor prod__lit_bottom_up_break__char_class___range__98_98_char_class___range__111_111_char_class___range__116_116_char_class___range__116_116_char_class___range__111_111_char_class___range__109_109_char_class___range__45_45_char_class___range__117_117_char_class___range__112_112_char_class___range__45_45_char_class___range__98_98_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__107_107_ = (IConstructor) _read("prod(lit(\"bottom-up-break\"),[\\char-class([range(98,98)]),\\char-class([range(111,111)]),\\char-class([range(116,116)]),\\char-class([range(116,116)]),\\char-class([range(111,111)]),\\char-class([range(109,109)]),\\char-class([range(45,45)]),\\char-class([range(117,117)]),\\char-class([range(112,112)]),\\char-class([range(45,45)]),\\char-class([range(98,98)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(97,97)]),\\char-class([range(107,107)])],{})", Factory.Production);
  private static final IConstructor prod__break_$Statement__lit_break_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"break\",sort(\"Statement\")),[lit(\"break\"),layouts(\"LAYOUTLIST\"),label(\"target\",sort(\"Target\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__literal_$Pattern__literal_$Literal_ = (IConstructor) _read("prod(label(\"literal\",sort(\"Pattern\")),[label(\"literal\",sort(\"Literal\"))],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Variable\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__fail_$Statement__lit_fail_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable = (IConstructor) _read("prod(label(\"fail\",sort(\"Statement\")),[lit(\"fail\"),layouts(\"LAYOUTLIST\"),label(\"target\",sort(\"Target\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__any_$Expression__lit_any_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"any\",sort(\"Expression\")),[lit(\"any\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"generators\",\\iter-seps(sort(\"Expression\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__lit_throws__char_class___range__116_116_char_class___range__104_104_char_class___range__114_114_char_class___range__111_111_char_class___range__119_119_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"throws\"),[\\char-class([range(116,116)]),\\char-class([range(104,104)]),\\char-class([range(114,114)]),\\char-class([range(111,111)]),\\char-class([range(119,119)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_bag__char_class___range__98_98_char_class___range__97_97_char_class___range__103_103_ = (IConstructor) _read("prod(lit(\"bag\"),[\\char-class([range(98,98)]),\\char-class([range(97,97)]),\\char-class([range(103,103)])],{})", Factory.Production);
  private static final IConstructor prod__lit_visit__char_class___range__118_118_char_class___range__105_105_char_class___range__115_115_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"visit\"),[\\char-class([range(118,118)]),\\char-class([range(105,105)]),\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__default_$Import__lit_import_$layouts_LAYOUTLIST_module_$ImportedModule_$layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"default\",sort(\"Import\")),[lit(\"import\"),layouts(\"LAYOUTLIST\"),label(\"module\",sort(\"ImportedModule\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__string_$BasicType__lit_str_ = (IConstructor) _read("prod(label(\"string\",sort(\"BasicType\")),[lit(\"str\")],{})", Factory.Production);
  private static final IConstructor regular__iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Assignable\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor prod__setOption_$ShellCommand__lit_set_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_expression_$Expression_ = (IConstructor) _read("prod(label(\"setOption\",sort(\"ShellCommand\")),[lit(\"set\"),layouts(\"LAYOUTLIST\"),label(\"name\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),label(\"expression\",sort(\"Expression\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_value_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"value\")],{})", Factory.Production);
  private static final IConstructor prod__regExp_$Literal__regExpLiteral_$RegExpLiteral_ = (IConstructor) _read("prod(label(\"regExp\",sort(\"Literal\")),[label(\"regExpLiteral\",lex(\"RegExpLiteral\"))],{})", Factory.Production);
  private static final IConstructor prod__$HeaderKeyword__lit_extend_ = (IConstructor) _read("prod(keywords(\"HeaderKeyword\"),[lit(\"extend\")],{})", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__0_16777215 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(0,16777215)])))", Factory.Production);
  private static final IConstructor prod__isDefined_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___63_ = (IConstructor) _read("prod(label(\"isDefined\",sort(\"Expression\")),[label(\"argument\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"?\")],{})", Factory.Production);
  private static final IConstructor prod__value_$BasicType__lit_value_ = (IConstructor) _read("prod(label(\"value\",sort(\"BasicType\")),[lit(\"value\")],{})", Factory.Production);
  private static final IConstructor prod__unimport_$ShellCommand__lit_unimport_$layouts_LAYOUTLIST_name_$QualifiedName_ = (IConstructor) _read("prod(label(\"unimport\",sort(\"ShellCommand\")),[lit(\"unimport\"),layouts(\"LAYOUTLIST\"),label(\"name\",sort(\"QualifiedName\"))],{})", Factory.Production);
  private static final IConstructor prod__notPrecede_$Sym__match_$Sym_$layouts_LAYOUTLIST_lit___33_60_60_$layouts_LAYOUTLIST_symbol_$Sym__assoc__right = (IConstructor) _read("prod(label(\"notPrecede\",sort(\"Sym\")),[label(\"match\",sort(\"Sym\")),layouts(\"LAYOUTLIST\"),lit(\"!\\<\\<\"),layouts(\"LAYOUTLIST\"),label(\"symbol\",sort(\"Sym\"))],{assoc(right())})", Factory.Production);
  private static final IConstructor prod__multiVariable_$Pattern__qualifiedName_$QualifiedName_$layouts_LAYOUTLIST_lit___42_ = (IConstructor) _read("prod(label(\"multiVariable\",sort(\"Pattern\")),[label(\"qualifiedName\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),lit(\"*\")],{})", Factory.Production);
  private static final IConstructor prod__default_$Header__tags_$Tags_$layouts_LAYOUTLIST_lit_module_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_imports_iter_star_seps__$Import__$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"default\",sort(\"Header\")),[label(\"tags\",sort(\"Tags\")),layouts(\"LAYOUTLIST\"),lit(\"module\"),layouts(\"LAYOUTLIST\"),label(\"name\",sort(\"QualifiedName\")),layouts(\"LAYOUTLIST\"),label(\"imports\",\\iter-star-seps(sort(\"Import\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__default_$Visibility__ = (IConstructor) _read("prod(label(\"default\",sort(\"Visibility\")),[],{})", Factory.Production);
  private static final IConstructor prod__lit_public__char_class___range__112_112_char_class___range__117_117_char_class___range__98_98_char_class___range__108_108_char_class___range__105_105_char_class___range__99_99_ = (IConstructor) _read("prod(lit(\"public\"),[\\char-class([range(112,112)]),\\char-class([range(117,117)]),\\char-class([range(98,98)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(99,99)])],{})", Factory.Production);
  private static final IConstructor prod__givenVisibility_$Toplevel__declaration_$Declaration_ = (IConstructor) _read("prod(label(\"givenVisibility\",sort(\"Toplevel\")),[label(\"declaration\",sort(\"Declaration\"))],{})", Factory.Production);
  private static final IConstructor prod__$RealLiteral__lit___46_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_ = (IConstructor) _read("prod(lex(\"RealLiteral\"),[conditional(lit(\".\"),{\\not-precede(\\char-class([range(46,46)]))}),iter(\\char-class([range(48,57)])),opt(\\char-class([range(68,68),range(70,70),range(100,100),range(102,102)]))],{})", Factory.Production);
  private static final IConstructor prod__lit_rat__char_class___range__114_114_char_class___range__97_97_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"rat\"),[\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__insert_$Statement__lit_insert_$layouts_LAYOUTLIST_dataTarget_$DataTarget_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable = (IConstructor) _read("prod(label(\"insert\",sort(\"Statement\")),[lit(\"insert\"),layouts(\"LAYOUTLIST\"),label(\"dataTarget\",sort(\"DataTarget\")),layouts(\"LAYOUTLIST\"),label(\"statement\",conditional(sort(\"Statement\"),{except(\"functionDeclaration\"),except(\"variableDeclaration\")}))],{assoc(\\non-assoc()),tag(breakable())})", Factory.Production);
  private static final IConstructor prod__$Name__char_class___range__92_92_char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__45_45_range__48_57_range__65_90_range__95_95_range__97_122_ = (IConstructor) _read("prod(lex(\"Name\"),[\\char-class([range(92,92)]),\\char-class([range(65,90),range(95,95),range(97,122)]),conditional(\\iter-star(\\char-class([range(45,45),range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(45,45),range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__intersection_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___38_38_$layouts_LAYOUTLIST_rhs_$Class__assoc__left = (IConstructor) _read("prod(label(\"intersection\",sort(\"Class\")),[label(\"lhs\",sort(\"Class\")),layouts(\"LAYOUTLIST\"),lit(\"&&\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Class\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__lit_renaming__char_class___range__114_114_char_class___range__101_101_char_class___range__110_110_char_class___range__97_97_char_class___range__109_109_char_class___range__105_105_char_class___range__110_110_char_class___range__103_103_ = (IConstructor) _read("prod(lit(\"renaming\"),[\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(97,97)]),\\char-class([range(109,109)]),\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(103,103)])],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_alias_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"alias\")],{})", Factory.Production);
  private static final IConstructor prod__functionDeclaration_$Statement__functionDeclaration_$FunctionDeclaration__tag__breakable = (IConstructor) _read("prod(label(\"functionDeclaration\",sort(\"Statement\")),[label(\"functionDeclaration\",sort(\"FunctionDeclaration\"))],{tag(breakable())})", Factory.Production);
  private static final IConstructor prod__while_$StringTemplate__lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"while\",sort(\"StringTemplate\")),[lit(\"while\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),label(\"condition\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"preStats\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),label(\"body\",sort(\"StringMiddle\")),layouts(\"LAYOUTLIST\"),label(\"postStats\",\\iter-star-seps(sort(\"Statement\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__or_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___124_124_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left = (IConstructor) _read("prod(label(\"or\",sort(\"Expression\")),[label(\"lhs\",sort(\"Expression\")),layouts(\"LAYOUTLIST\"),lit(\"||\"),layouts(\"LAYOUTLIST\"),label(\"rhs\",sort(\"Expression\"))],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__lit_value__char_class___range__118_118_char_class___range__97_97_char_class___range__108_108_char_class___range__117_117_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"value\"),[\\char-class([range(118,118)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(117,117)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__$PathChars__$URLChars_char_class___range__124_124_ = (IConstructor) _read("prod(lex(\"PathChars\"),[lex(\"URLChars\"),\\char-class([range(124,124)])],{})", Factory.Production);
  private static final IConstructor prod__$HexIntegerLiteral__char_class___range__48_48_char_class___range__88_88_range__120_120_iter__char_class___range__48_57_range__65_70_range__97_102_ = (IConstructor) _read("prod(lex(\"HexIntegerLiteral\"),[\\char-class([range(48,48)]),\\char-class([range(88,88),range(120,120)]),conditional(iter(\\char-class([range(48,57),range(65,70),range(97,102)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__nonInterpolated_$ProtocolPart__protocolChars_$ProtocolChars_ = (IConstructor) _read("prod(label(\"nonInterpolated\",sort(\"ProtocolPart\")),[label(\"protocolChars\",lex(\"ProtocolChars\"))],{})", Factory.Production);
  private static final IConstructor prod__$RascalKeywords__lit_int_ = (IConstructor) _read("prod(keywords(\"RascalKeywords\"),[lit(\"int\")],{})", Factory.Production);
  private static final IConstructor prod__start__$Module__$layouts_LAYOUTLIST_top_$Module_$layouts_LAYOUTLIST_ = (IConstructor) _read("prod(start(sort(\"Module\")),[layouts(\"LAYOUTLIST\"),label(\"top\",sort(\"Module\")),layouts(\"LAYOUTLIST\")],{})", Factory.Production);
  private static final IConstructor prod__empty_$Target__ = (IConstructor) _read("prod(label(\"empty\",sort(\"Target\")),[],{})", Factory.Production);
  private static final IConstructor prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"module\"),[\\char-class([range(109,109)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__post_$PathTail__post_$PostPathChars_ = (IConstructor) _read("prod(label(\"post\",sort(\"PathTail\")),[label(\"post\",lex(\"PostPathChars\"))],{})", Factory.Production);
    
  // Item declarations
	
	
  protected static class $Tag {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_contents_$TagString__tag__Folded_tag__category___67_111_109_109_101_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-20, 4, "$TagString", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-18, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-15, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-13, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-11, 0, prod__lit___64__char_class___range__64_64_, new int[] {64}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_contents_$TagString__tag__Folded_tag__category___67_111_109_109_101_110_116, tmp);
	}
    protected static final void _init_prod__expression_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression__tag__Folded_tag__category___67_111_109_109_101_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-37, 6, "$Expression", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-35, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-33, 4, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-32, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-29, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-27, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-25, 0, prod__lit___64__char_class___range__64_64_, new int[] {64}, null, null);
      builder.addAlternative(RascalRascal.prod__expression_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression__tag__Folded_tag__category___67_111_109_109_101_110_116, tmp);
	}
    protected static final void _init_prod__empty_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name__tag__Folded_tag__category___67_111_109_109_101_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-46, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-44, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-42, 0, prod__lit___64__char_class___range__64_64_, new int[] {64}, null, null);
      builder.addAlternative(RascalRascal.prod__empty_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name__tag__Folded_tag__category___67_111_109_109_101_110_116, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_contents_$TagString__tag__Folded_tag__category___67_111_109_109_101_110_116(builder);
      
        _init_prod__expression_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression__tag__Folded_tag__category___67_111_109_109_101_110_116(builder);
      
        _init_prod__empty_$Tag__lit___64_$layouts_LAYOUTLIST_name_$Name__tag__Folded_tag__category___67_111_109_109_101_110_116(builder);
      
    }
  }
	
  protected static class $DateAndTime {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$DateAndTime__lit___36_$DatePart_lit_T_$TimePartNoTZ_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[4];
      
      tmp[3] = new NonTerminalStackNode<IConstructor>(-75, 3, "$TimePartNoTZ", null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{43,43},{45,45}})});
      tmp[2] = new LiteralStackNode<IConstructor>(-71, 2, prod__lit_T__char_class___range__84_84_, new int[] {84}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-70, 1, "$DatePart", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-68, 0, prod__lit___36__char_class___range__36_36_, new int[] {36}, null, null);
      builder.addAlternative(RascalRascal.prod__$DateAndTime__lit___36_$DatePart_lit_T_$TimePartNoTZ_, tmp);
	}
    protected static final void _init_prod__$DateAndTime__lit___36_$DatePart_lit_T_$TimePartNoTZ_$TimeZonePart_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-85, 4, "$TimeZonePart", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-83, 3, "$TimePartNoTZ", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-81, 2, prod__lit_T__char_class___range__84_84_, new int[] {84}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-80, 1, "$DatePart", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-78, 0, prod__lit___36__char_class___range__36_36_, new int[] {36}, null, null);
      builder.addAlternative(RascalRascal.prod__$DateAndTime__lit___36_$DatePart_lit_T_$TimePartNoTZ_$TimeZonePart_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$DateAndTime__lit___36_$DatePart_lit_T_$TimePartNoTZ_(builder);
      
        _init_prod__$DateAndTime__lit___36_$DatePart_lit_T_$TimePartNoTZ_$TimeZonePart_(builder);
      
    }
  }
	
  protected static class $Pattern {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__negative_$Pattern__lit___$layouts_LAYOUTLIST_argument_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-113, 2, "$Pattern", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-111, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-109, 0, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      builder.addAlternative(RascalRascal.prod__negative_$Pattern__lit___$layouts_LAYOUTLIST_argument_$Pattern_, tmp);
	}
    protected static final void _init_prod__descendant_$Pattern__lit___47_$layouts_LAYOUTLIST_pattern_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-294, 2, "$Pattern", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-292, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-290, 0, prod__lit___47__char_class___range__47_47_, new int[] {47}, null, null);
      builder.addAlternative(RascalRascal.prod__descendant_$Pattern__lit___47_$layouts_LAYOUTLIST_pattern_$Pattern_, tmp);
	}
    protected static final void _init_prod__multiVariable_$Pattern__qualifiedName_$QualifiedName_$layouts_LAYOUTLIST_lit___42_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-123, 2, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-122, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-119, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__multiVariable_$Pattern__qualifiedName_$QualifiedName_$layouts_LAYOUTLIST_lit___42_, tmp);
	}
    protected static final void _init_prod__splicePlus_$Pattern__lit___43_$layouts_LAYOUTLIST_argument_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-131, 2, "$Pattern", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-129, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-127, 0, prod__lit___43__char_class___range__43_43_, new int[] {43}, null, null);
      builder.addAlternative(RascalRascal.prod__splicePlus_$Pattern__lit___43_$layouts_LAYOUTLIST_argument_$Pattern_, tmp);
	}
    protected static final void _init_prod__typedVariableBecomes_$Pattern__type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_pattern_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-313, 6, "$Pattern", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-311, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-309, 4, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-308, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-305, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-303, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-300, 0, "$Type", null, null);
      builder.addAlternative(RascalRascal.prod__typedVariableBecomes_$Pattern__type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_pattern_$Pattern_, tmp);
	}
    protected static final void _init_prod__asType_$Pattern__lit___91_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___93_$layouts_LAYOUTLIST_argument_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-330, 6, "$Pattern", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-328, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-326, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-325, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-322, 2, "$Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-320, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-318, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(RascalRascal.prod__asType_$Pattern__lit___91_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___93_$layouts_LAYOUTLIST_argument_$Pattern_, tmp);
	}
    protected static final void _init_prod__list_$Pattern__lit___91_$layouts_LAYOUTLIST_elements_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-161, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-160, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-157, 2, regular__iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-151, 0, "$Pattern", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-153, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-154, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-156, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-149, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-147, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(RascalRascal.prod__list_$Pattern__lit___91_$layouts_LAYOUTLIST_elements_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__variableBecomes_$Pattern__name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_pattern_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-344, 4, "$Pattern", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-342, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-340, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-339, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-336, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__variableBecomes_$Pattern__name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_pattern_$Pattern_, tmp);
	}
    protected static final void _init_prod__splice_$Pattern__lit___42_$layouts_LAYOUTLIST_argument_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-169, 2, "$Pattern", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-167, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-165, 0, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      builder.addAlternative(RascalRascal.prod__splice_$Pattern__lit___42_$layouts_LAYOUTLIST_argument_$Pattern_, tmp);
	}
    protected static final void _init_prod__set_$Pattern__lit___123_$layouts_LAYOUTLIST_elements_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-211, 4, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-210, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-207, 2, regular__iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-201, 0, "$Pattern", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-203, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-204, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-206, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-199, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-197, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      builder.addAlternative(RascalRascal.prod__set_$Pattern__lit___123_$layouts_LAYOUTLIST_elements_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__literal_$Pattern__literal_$Literal_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-222, 0, "$Literal", null, null);
      builder.addAlternative(RascalRascal.prod__literal_$Pattern__literal_$Literal_, tmp);
	}
    protected static final void _init_prod__typedVariable_$Pattern__type_$Type_$layouts_LAYOUTLIST_name_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-142, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-140, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-137, 0, "$Type", null, null);
      builder.addAlternative(RascalRascal.prod__typedVariable_$Pattern__type_$Type_$layouts_LAYOUTLIST_name_$Name_, tmp);
	}
    protected static final void _init_prod__callOrTree_$Pattern__expression_$Pattern_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-193, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-192, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-189, 4, regular__iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-183, 0, "$Pattern", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-185, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-186, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-188, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-181, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-179, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-178, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-175, 0, "$Pattern", null, null);
      builder.addAlternative(RascalRascal.prod__callOrTree_$Pattern__expression_$Pattern_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__qualifiedName_$Pattern__qualifiedName_$QualifiedName_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-216, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__qualifiedName_$Pattern__qualifiedName_$QualifiedName_, tmp);
	}
    protected static final void _init_prod__map_$Pattern__lit___40_$layouts_LAYOUTLIST_mappings_iter_star_seps__$Mapping__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-243, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-242, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-239, 2, regular__iter_star_seps__$Mapping__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-233, 0, "$Mapping__$Pattern", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-235, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-236, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-238, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-229, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-227, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__map_$Pattern__lit___40_$layouts_LAYOUTLIST_mappings_iter_star_seps__$Mapping__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__anti_$Pattern__lit___33_$layouts_LAYOUTLIST_pattern_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-353, 2, "$Pattern", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-351, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-349, 0, prod__lit___33__char_class___range__33_33_, new int[] {33}, null, null);
      builder.addAlternative(RascalRascal.prod__anti_$Pattern__lit___33_$layouts_LAYOUTLIST_pattern_$Pattern_, tmp);
	}
    protected static final void _init_prod__tuple_$Pattern__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-284, 4, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-283, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-280, 2, regular__iter_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-274, 0, "$Pattern", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-276, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-277, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-279, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-272, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-270, 0, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      builder.addAlternative(RascalRascal.prod__tuple_$Pattern__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_, tmp);
	}
    protected static final void _init_prod__reifiedType_$Pattern__lit_type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_symbol_$Pattern_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_definitions_$Pattern_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(-266, 10, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-265, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-262, 8, "$Pattern", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-260, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-258, 6, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-257, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-254, 4, "$Pattern", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-252, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-250, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-249, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-247, 0, prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_, new int[] {116,121,112,101}, null, null);
      builder.addAlternative(RascalRascal.prod__reifiedType_$Pattern__lit_type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_symbol_$Pattern_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_definitions_$Pattern_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__negative_$Pattern__lit___$layouts_LAYOUTLIST_argument_$Pattern_(builder);
      
        _init_prod__descendant_$Pattern__lit___47_$layouts_LAYOUTLIST_pattern_$Pattern_(builder);
      
        _init_prod__multiVariable_$Pattern__qualifiedName_$QualifiedName_$layouts_LAYOUTLIST_lit___42_(builder);
      
        _init_prod__splicePlus_$Pattern__lit___43_$layouts_LAYOUTLIST_argument_$Pattern_(builder);
      
        _init_prod__typedVariableBecomes_$Pattern__type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_pattern_$Pattern_(builder);
      
        _init_prod__asType_$Pattern__lit___91_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___93_$layouts_LAYOUTLIST_argument_$Pattern_(builder);
      
        _init_prod__list_$Pattern__lit___91_$layouts_LAYOUTLIST_elements_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__variableBecomes_$Pattern__name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_pattern_$Pattern_(builder);
      
        _init_prod__splice_$Pattern__lit___42_$layouts_LAYOUTLIST_argument_$Pattern_(builder);
      
        _init_prod__set_$Pattern__lit___123_$layouts_LAYOUTLIST_elements_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__literal_$Pattern__literal_$Literal_(builder);
      
        _init_prod__typedVariable_$Pattern__type_$Type_$layouts_LAYOUTLIST_name_$Name_(builder);
      
        _init_prod__callOrTree_$Pattern__expression_$Pattern_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__qualifiedName_$Pattern__qualifiedName_$QualifiedName_(builder);
      
        _init_prod__map_$Pattern__lit___40_$layouts_LAYOUTLIST_mappings_iter_star_seps__$Mapping__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__anti_$Pattern__lit___33_$layouts_LAYOUTLIST_pattern_$Pattern_(builder);
      
        _init_prod__tuple_$Pattern__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_(builder);
      
        _init_prod__reifiedType_$Pattern__lit_type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_symbol_$Pattern_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_definitions_$Pattern_$layouts_LAYOUTLIST_lit___41_(builder);
      
    }
  }
	
  protected static class $Prod {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__all_$Prod__lhs_$Prod_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_rhs_$Prod__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-451, 4, "$Prod", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-449, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-447, 2, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-446, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-443, 0, "$Prod", null, null);
      builder.addAlternative(RascalRascal.prod__all_$Prod__lhs_$Prod_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_rhs_$Prod__assoc__left, tmp);
	}
    protected static final void _init_prod__unlabeled_$Prod__modifiers_iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_args_iter_star_seps__$Sym__$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new SeparatedListStackNode<IConstructor>(-382, 2, regular__iter_star_seps__$Sym__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-379, 0, "$Sym", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-381, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-377, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new SeparatedListStackNode<IConstructor>(-374, 0, regular__iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-371, 0, "$ProdModifier", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-373, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      builder.addAlternative(RascalRascal.prod__unlabeled_$Prod__modifiers_iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_args_iter_star_seps__$Sym__$layouts_LAYOUTLIST_, tmp);
	}
    protected static final void _init_prod__associativityGroup_$Prod__associativity_$Assoc_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_group_$Prod_$layouts_LAYOUTLIST_lit___41__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-400, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-399, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-396, 4, "$Prod", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-394, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-392, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-391, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-388, 0, "$Assoc", null, null);
      builder.addAlternative(RascalRascal.prod__associativityGroup_$Prod__associativity_$Assoc_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_group_$Prod_$layouts_LAYOUTLIST_lit___41__tag__Foldable, tmp);
	}
    protected static final void _init_prod__reference_$Prod__lit___58_$layouts_LAYOUTLIST_referenced_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-408, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-406, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-404, 0, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      builder.addAlternative(RascalRascal.prod__reference_$Prod__lit___58_$layouts_LAYOUTLIST_referenced_$Name_, tmp);
	}
    protected static final void _init_prod__first_$Prod__lhs_$Prod_$layouts_LAYOUTLIST_lit___62_$layouts_LAYOUTLIST_rhs_$Prod__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-467, 4, "$Prod", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-465, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-463, 2, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {62})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-460, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-457, 0, "$Prod", null, null);
      builder.addAlternative(RascalRascal.prod__first_$Prod__lhs_$Prod_$layouts_LAYOUTLIST_lit___62_$layouts_LAYOUTLIST_rhs_$Prod__assoc__left, tmp);
	}
    protected static final void _init_prod__others_$Prod__lit___46_46_46_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-413, 0, prod__lit___46_46_46__char_class___range__46_46_char_class___range__46_46_char_class___range__46_46_, new int[] {46,46,46}, null, null);
      builder.addAlternative(RascalRascal.prod__others_$Prod__lit___46_46_46_, tmp);
	}
    protected static final void _init_prod__labeled_$Prod__modifiers_iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_args_iter_star_seps__$Sym__$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new SeparatedListStackNode<IConstructor>(-437, 6, regular__iter_star_seps__$Sym__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-434, 0, "$Sym", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-436, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-432, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-430, 4, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-429, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-426, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-424, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new SeparatedListStackNode<IConstructor>(-421, 0, regular__iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-418, 0, "$ProdModifier", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-420, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      builder.addAlternative(RascalRascal.prod__labeled_$Prod__modifiers_iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_args_iter_star_seps__$Sym__$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__all_$Prod__lhs_$Prod_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_rhs_$Prod__assoc__left(builder);
      
        _init_prod__unlabeled_$Prod__modifiers_iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_args_iter_star_seps__$Sym__$layouts_LAYOUTLIST_(builder);
      
        _init_prod__associativityGroup_$Prod__associativity_$Assoc_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_group_$Prod_$layouts_LAYOUTLIST_lit___41__tag__Foldable(builder);
      
        _init_prod__reference_$Prod__lit___58_$layouts_LAYOUTLIST_referenced_$Name_(builder);
      
        _init_prod__first_$Prod__lhs_$Prod_$layouts_LAYOUTLIST_lit___62_$layouts_LAYOUTLIST_rhs_$Prod__assoc__left(builder);
      
        _init_prod__others_$Prod__lit___46_46_46_(builder);
      
        _init_prod__labeled_$Prod__modifiers_iter_star_seps__$ProdModifier__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_args_iter_star_seps__$Sym__$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $UnicodeEscape {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__ascii_$UnicodeEscape__lit___92_char_class___range__97_97_char_class___range__48_55_char_class___range__48_57_range__65_70_range__97_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[4];
      
      tmp[3] = new CharStackNode<IConstructor>(-479, 3, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-478, 2, new int[][]{{48,55}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-477, 1, new int[][]{{97,97}}, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-476, 0, prod__lit___92__char_class___range__92_92_, new int[] {92}, null, null);
      builder.addAlternative(RascalRascal.prod__ascii_$UnicodeEscape__lit___92_char_class___range__97_97_char_class___range__48_55_char_class___range__48_57_range__65_70_range__97_102_, tmp);
	}
    protected static final void _init_prod__utf16_$UnicodeEscape__lit___92_char_class___range__117_117_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[6];
      
      tmp[5] = new CharStackNode<IConstructor>(-488, 5, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[4] = new CharStackNode<IConstructor>(-487, 4, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(-486, 3, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-485, 2, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-484, 1, new int[][]{{117,117}}, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-483, 0, prod__lit___92__char_class___range__92_92_, new int[] {92}, null, null);
      builder.addAlternative(RascalRascal.prod__utf16_$UnicodeEscape__lit___92_char_class___range__117_117_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_, tmp);
	}
    protected static final void _init_prod__utf32_$UnicodeEscape__lit___92_char_class___range__85_85_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[8];
      
      tmp[7] = new CharStackNode<IConstructor>(-499, 7, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[6] = new CharStackNode<IConstructor>(-498, 6, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[5] = new CharStackNode<IConstructor>(-497, 5, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[4] = new CharStackNode<IConstructor>(-496, 4, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(-495, 3, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-494, 2, new int[][]{{48,57},{65,70},{97,102}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-493, 1, new int[][]{{85,85}}, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-492, 0, prod__lit___92__char_class___range__92_92_, new int[] {92}, null, null);
      builder.addAlternative(RascalRascal.prod__utf32_$UnicodeEscape__lit___92_char_class___range__85_85_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__ascii_$UnicodeEscape__lit___92_char_class___range__97_97_char_class___range__48_55_char_class___range__48_57_range__65_70_range__97_102_(builder);
      
        _init_prod__utf16_$UnicodeEscape__lit___92_char_class___range__117_117_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_(builder);
      
        _init_prod__utf32_$UnicodeEscape__lit___92_char_class___range__85_85_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_char_class___range__48_57_range__65_70_range__97_102_(builder);
      
    }
  }
	
  protected static class $OctalIntegerLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$OctalIntegerLiteral__char_class___range__48_48_iter__char_class___range__48_55_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new ListStackNode<IConstructor>(-542, 1, regular__iter__char_class___range__48_55, new CharStackNode<IConstructor>(-539, 0, new int[][]{{48,55}}, null, null), true, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      tmp[0] = new CharStackNode<IConstructor>(-538, 0, new int[][]{{48,48}}, null, null);
      builder.addAlternative(RascalRascal.prod__$OctalIntegerLiteral__char_class___range__48_48_iter__char_class___range__48_55_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$OctalIntegerLiteral__char_class___range__48_48_iter__char_class___range__48_55_(builder);
      
    }
  }
	
  protected static class $RationalLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$RationalLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_char_class___range__114_114_char_class___range__48_57_iter_star__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new ListStackNode<IConstructor>(-561, 4, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(-558, 0, new int[][]{{48,57}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      tmp[3] = new CharStackNode<IConstructor>(-557, 3, new int[][]{{48,57}}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-556, 2, new int[][]{{114,114}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-555, 1, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(-554, 0, new int[][]{{48,57}}, null, null), false, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-553, 0, new int[][]{{49,57}}, null, null);
      builder.addAlternative(RascalRascal.prod__$RationalLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_char_class___range__114_114_char_class___range__48_57_iter_star__char_class___range__48_57_, tmp);
	}
    protected static final void _init_prod__$RationalLiteral__char_class___range__48_57_iter_star__char_class___range__48_57_char_class___range__114_114_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new CharStackNode<IConstructor>(-567, 2, new int[][]{{114,114}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-566, 1, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(-565, 0, new int[][]{{48,57}}, null, null), false, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-564, 0, new int[][]{{48,57}}, null, null);
      builder.addAlternative(RascalRascal.prod__$RationalLiteral__char_class___range__48_57_iter_star__char_class___range__48_57_char_class___range__114_114_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$RationalLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_char_class___range__114_114_char_class___range__48_57_iter_star__char_class___range__48_57_(builder);
      
        _init_prod__$RationalLiteral__char_class___range__48_57_iter_star__char_class___range__48_57_char_class___range__114_114_(builder);
      
    }
  }
	
  protected static class $BooleanLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$BooleanLiteral__lit_false_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-584, 0, prod__lit_false__char_class___range__102_102_char_class___range__97_97_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {102,97,108,115,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$BooleanLiteral__lit_false_, tmp);
	}
    protected static final void _init_prod__$BooleanLiteral__lit_true_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-587, 0, prod__lit_true__char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__101_101_, new int[] {116,114,117,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$BooleanLiteral__lit_true_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$BooleanLiteral__lit_false_(builder);
      
        _init_prod__$BooleanLiteral__lit_true_(builder);
      
    }
  }
	
  protected static class $Toplevel {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__givenVisibility_$Toplevel__declaration_$Declaration_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-596, 0, "$Declaration", null, null);
      builder.addAlternative(RascalRascal.prod__givenVisibility_$Toplevel__declaration_$Declaration_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__givenVisibility_$Toplevel__declaration_$Declaration_(builder);
      
    }
  }
	
  protected static class $ProdModifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__bracket_$ProdModifier__lit_bracket_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-605, 0, prod__lit_bracket__char_class___range__98_98_char_class___range__114_114_char_class___range__97_97_char_class___range__99_99_char_class___range__107_107_char_class___range__101_101_char_class___range__116_116_, new int[] {98,114,97,99,107,101,116}, null, null);
      builder.addAlternative(RascalRascal.prod__bracket_$ProdModifier__lit_bracket_, tmp);
	}
    protected static final void _init_prod__associativity_$ProdModifier__associativity_$Assoc_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-610, 0, "$Assoc", null, null);
      builder.addAlternative(RascalRascal.prod__associativity_$ProdModifier__associativity_$Assoc_, tmp);
	}
    protected static final void _init_prod__tag_$ProdModifier__tag_$Tag_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-616, 0, "$Tag", null, null);
      builder.addAlternative(RascalRascal.prod__tag_$ProdModifier__tag_$Tag_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__bracket_$ProdModifier__lit_bracket_(builder);
      
        _init_prod__associativity_$ProdModifier__associativity_$Assoc_(builder);
      
        _init_prod__tag_$ProdModifier__tag_$Tag_(builder);
      
    }
  }
	
  protected static class $TypeVar {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__bounded_$TypeVar__lit___38_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___60_58_$layouts_LAYOUTLIST_bound_$Type_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-637, 6, "$Type", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-635, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-633, 4, prod__lit___60_58__char_class___range__60_60_char_class___range__58_58_, new int[] {60,58}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-632, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-629, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-627, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-625, 0, prod__lit___38__char_class___range__38_38_, new int[] {38}, null, null);
      builder.addAlternative(RascalRascal.prod__bounded_$TypeVar__lit___38_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___60_58_$layouts_LAYOUTLIST_bound_$Type_, tmp);
	}
    protected static final void _init_prod__free_$TypeVar__lit___38_$layouts_LAYOUTLIST_name_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-646, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-644, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-642, 0, prod__lit___38__char_class___range__38_38_, new int[] {38}, null, null);
      builder.addAlternative(RascalRascal.prod__free_$TypeVar__lit___38_$layouts_LAYOUTLIST_name_$Name_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__bounded_$TypeVar__lit___38_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___60_58_$layouts_LAYOUTLIST_bound_$Type_(builder);
      
        _init_prod__free_$TypeVar__lit___38_$layouts_LAYOUTLIST_name_$Name_(builder);
      
    }
  }
	
  protected static class $FunctionModifiers {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__list_$FunctionModifiers__modifiers_iter_star_seps__$FunctionModifier__$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SeparatedListStackNode<IConstructor>(-801, 0, regular__iter_star_seps__$FunctionModifier__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-798, 0, "$FunctionModifier", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-800, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      builder.addAlternative(RascalRascal.prod__list_$FunctionModifiers__modifiers_iter_star_seps__$FunctionModifier__$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__list_$FunctionModifiers__modifiers_iter_star_seps__$FunctionModifier__$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $Comprehension {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__set_$Comprehension__lit___123_$layouts_LAYOUTLIST_results_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125__tag__breakable___123_114_101_115_117_108_116_115_44_103_101_110_101_114_97_116_111_114_115_125(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-723, 8, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-722, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-719, 6, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-713, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-715, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-716, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-718, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-711, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-709, 4, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-708, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-705, 2, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-699, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-701, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-702, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-704, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-697, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-695, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      builder.addAlternative(RascalRascal.prod__set_$Comprehension__lit___123_$layouts_LAYOUTLIST_results_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125__tag__breakable___123_114_101_115_117_108_116_115_44_103_101_110_101_114_97_116_111_114_115_125, tmp);
	}
    protected static final void _init_prod__map_$Comprehension__lit___40_$layouts_LAYOUTLIST_from_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41__tag__breakable___123_102_114_111_109_44_116_111_44_103_101_110_101_114_97_116_111_114_115_125(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[12] = new LiteralStackNode<IConstructor>(-757, 12, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-756, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new SeparatedListStackNode<IConstructor>(-753, 10, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-747, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-749, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-750, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-752, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-745, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-743, 8, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-742, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-739, 6, "$Expression", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-737, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-735, 4, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-734, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-731, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-729, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-727, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__map_$Comprehension__lit___40_$layouts_LAYOUTLIST_from_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41__tag__breakable___123_102_114_111_109_44_116_111_44_103_101_110_101_114_97_116_111_114_115_125, tmp);
	}
    protected static final void _init_prod__list_$Comprehension__lit___91_$layouts_LAYOUTLIST_results_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93__tag__breakable___123_114_101_115_117_108_116_115_44_103_101_110_101_114_97_116_111_114_115_125(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-789, 8, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-788, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-785, 6, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-779, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-781, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-782, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-784, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-777, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-775, 4, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-774, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-771, 2, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-765, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-767, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-768, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-770, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-763, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-761, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(RascalRascal.prod__list_$Comprehension__lit___91_$layouts_LAYOUTLIST_results_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93__tag__breakable___123_114_101_115_117_108_116_115_44_103_101_110_101_114_97_116_111_114_115_125, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__set_$Comprehension__lit___123_$layouts_LAYOUTLIST_results_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125__tag__breakable___123_114_101_115_117_108_116_115_44_103_101_110_101_114_97_116_111_114_115_125(builder);
      
        _init_prod__map_$Comprehension__lit___40_$layouts_LAYOUTLIST_from_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41__tag__breakable___123_102_114_111_109_44_116_111_44_103_101_110_101_114_97_116_111_114_115_125(builder);
      
        _init_prod__list_$Comprehension__lit___91_$layouts_LAYOUTLIST_results_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93__tag__breakable___123_114_101_115_117_108_116_115_44_103_101_110_101_114_97_116_111_114_115_125(builder);
      
    }
  }
	
  protected static class $Declaration {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__alias_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_alias_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_base_$Type_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[12] = new LiteralStackNode<IConstructor>(-842, 12, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-841, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new NonTerminalStackNode<IConstructor>(-838, 10, "$Type", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-836, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-834, 8, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-833, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-830, 6, "$UserType", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-828, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-826, 4, prod__lit_alias__char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__97_97_char_class___range__115_115_, new int[] {97,108,105,97,115}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-825, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-822, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-820, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-817, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__alias_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_alias_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_base_$Type_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__function_$Declaration__functionDeclaration_$FunctionDeclaration_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-811, 0, "$FunctionDeclaration", null, null);
      builder.addAlternative(RascalRascal.prod__function_$Declaration__functionDeclaration_$FunctionDeclaration_, tmp);
	}
    protected static final void _init_prod__annotation_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_anno_$layouts_LAYOUTLIST_annoType_$Type_$layouts_LAYOUTLIST_onType_$Type_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new LiteralStackNode<IConstructor>(-877, 14, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-876, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new NonTerminalStackNode<IConstructor>(-873, 12, "$Name", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-871, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(-869, 10, prod__lit___64__char_class___range__64_64_, new int[] {64}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-868, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-865, 8, "$Type", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-863, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-860, 6, "$Type", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-858, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-856, 4, prod__lit_anno__char_class___range__97_97_char_class___range__110_110_char_class___range__110_110_char_class___range__111_111_, new int[] {97,110,110,111}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-855, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-852, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-850, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-847, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__annotation_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_anno_$layouts_LAYOUTLIST_annoType_$Type_$layouts_LAYOUTLIST_onType_$Type_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__tag_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_tag_$layouts_LAYOUTLIST_kind_$Kind_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit_on_$layouts_LAYOUTLIST_types_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new LiteralStackNode<IConstructor>(-918, 14, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-917, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new SeparatedListStackNode<IConstructor>(-914, 12, regular__iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-908, 0, "$Type", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-910, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-911, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-913, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-906, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(-904, 10, prod__lit_on__char_class___range__111_111_char_class___range__110_110_, new int[] {111,110}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-903, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-900, 8, "$Name", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-898, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-895, 6, "$Kind", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-893, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-891, 4, prod__lit_tag__char_class___range__116_116_char_class___range__97_97_char_class___range__103_103_, new int[] {116,97,103}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-890, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-887, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-885, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-882, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__tag_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_tag_$layouts_LAYOUTLIST_kind_$Kind_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit_on_$layouts_LAYOUTLIST_types_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__variable_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_variables_iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-948, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-947, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-944, 6, regular__iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-938, 0, "$Variable", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-940, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-941, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-943, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-936, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-933, 4, "$Type", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-931, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-928, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-926, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-923, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__variable_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_variables_iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__dataAbstract_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_data_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-970, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-969, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-966, 6, "$UserType", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-964, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-962, 4, prod__lit_data__char_class___range__100_100_char_class___range__97_97_char_class___range__116_116_char_class___range__97_97_, new int[] {100,97,116,97}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-961, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-958, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-956, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-953, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__dataAbstract_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_data_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__data_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_data_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_variants_iter_seps__$Variant__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[12] = new LiteralStackNode<IConstructor>(-1006, 12, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-1005, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new SeparatedListStackNode<IConstructor>(-1002, 10, regular__iter_seps__$Variant__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-996, 0, "$Variant", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-998, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-999, 2, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null), new NonTerminalStackNode<IConstructor>(-1001, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-994, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-992, 8, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-991, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-988, 6, "$UserType", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-986, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-984, 4, prod__lit_data__char_class___range__100_100_char_class___range__97_97_char_class___range__116_116_char_class___range__97_97_, new int[] {100,97,116,97}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-983, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-980, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-978, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-975, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__data_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_data_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_variants_iter_seps__$Variant__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__Foldable, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__alias_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_alias_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_base_$Type_$layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__function_$Declaration__functionDeclaration_$FunctionDeclaration_(builder);
      
        _init_prod__annotation_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_anno_$layouts_LAYOUTLIST_annoType_$Type_$layouts_LAYOUTLIST_onType_$Type_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__tag_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_tag_$layouts_LAYOUTLIST_kind_$Kind_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit_on_$layouts_LAYOUTLIST_types_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__variable_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_variables_iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__dataAbstract_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_data_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__data_$Declaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_lit_data_$layouts_LAYOUTLIST_user_$UserType_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_variants_iter_seps__$Variant__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__Foldable(builder);
      
    }
  }
	
  protected static class $Type {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__selector_$Type__selector_$DataTypeSelector_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1015, 0, "$DataTypeSelector", null, null);
      builder.addAlternative(RascalRascal.prod__selector_$Type__selector_$DataTypeSelector_, tmp);
	}
    protected static final void _init_prod__symbol_$Type__symbol_$Sym_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1022, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__symbol_$Type__symbol_$Sym_, tmp);
	}
    protected static final void _init_prod__structured_$Type__structured_$StructuredType_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1028, 0, "$StructuredType", null, null);
      builder.addAlternative(RascalRascal.prod__structured_$Type__structured_$StructuredType_, tmp);
	}
    protected static final void _init_prod__bracket_$Type__lit___40_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___41__bracket(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-1041, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1040, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1037, 2, "$Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1035, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1033, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__bracket_$Type__lit___40_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___41__bracket, tmp);
	}
    protected static final void _init_prod__function_$Type__function_$FunctionType_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1046, 0, "$FunctionType", null, null);
      builder.addAlternative(RascalRascal.prod__function_$Type__function_$FunctionType_, tmp);
	}
    protected static final void _init_prod__user_$Type__user_$UserType_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1056, 0, "$UserType", null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {108,101,120,105,99,97,108}), new StringMatchRestriction(new int[] {105,109,112,111,114,116}), new StringMatchRestriction(new int[] {115,116,97,114,116}), new StringMatchRestriction(new int[] {115,121,110,116,97,120}), new StringMatchRestriction(new int[] {107,101,121,119,111,114,100}), new StringMatchRestriction(new int[] {108,97,121,111,117,116}), new StringMatchRestriction(new int[] {101,120,116,101,110,100})});
      builder.addAlternative(RascalRascal.prod__user_$Type__user_$UserType_, tmp);
	}
    protected static final void _init_prod__basic_$Type__basic_$BasicType_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1061, 0, "$BasicType", null, null);
      builder.addAlternative(RascalRascal.prod__basic_$Type__basic_$BasicType_, tmp);
	}
    protected static final void _init_prod__variable_$Type__typeVar_$TypeVar_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1067, 0, "$TypeVar", null, null);
      builder.addAlternative(RascalRascal.prod__variable_$Type__typeVar_$TypeVar_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__selector_$Type__selector_$DataTypeSelector_(builder);
      
        _init_prod__symbol_$Type__symbol_$Sym_(builder);
      
        _init_prod__structured_$Type__structured_$StructuredType_(builder);
      
        _init_prod__bracket_$Type__lit___40_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___41__bracket(builder);
      
        _init_prod__function_$Type__function_$FunctionType_(builder);
      
        _init_prod__user_$Type__user_$UserType_(builder);
      
        _init_prod__basic_$Type__basic_$BasicType_(builder);
      
        _init_prod__variable_$Type__typeVar_$TypeVar_(builder);
      
    }
  }
	
  protected static class $Class {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__union_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___124_124_$layouts_LAYOUTLIST_rhs_$Class__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1143, 4, "$Class", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1141, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1139, 2, prod__lit___124_124__char_class___range__124_124_char_class___range__124_124_, new int[] {124,124}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1138, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1135, 0, "$Class", null, null);
      builder.addAlternative(RascalRascal.prod__union_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___124_124_$layouts_LAYOUTLIST_rhs_$Class__assoc__left, tmp);
	}
    protected static final void _init_prod__simpleCharclass_$Class__lit___91_$layouts_LAYOUTLIST_ranges_iter_star_seps__$Range__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-1091, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1090, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-1087, 2, regular__iter_star_seps__$Range__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1084, 0, "$Range", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1086, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1082, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1080, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(RascalRascal.prod__simpleCharclass_$Class__lit___91_$layouts_LAYOUTLIST_ranges_iter_star_seps__$Range__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__difference_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_rhs_$Class__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1113, 4, "$Class", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1111, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1109, 2, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1108, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1105, 0, "$Class", null, null);
      builder.addAlternative(RascalRascal.prod__difference_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_rhs_$Class__assoc__left, tmp);
	}
    protected static final void _init_prod__complement_$Class__lit___33_$layouts_LAYOUTLIST_charClass_$Class_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1099, 2, "$Class", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1097, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1095, 0, prod__lit___33__char_class___range__33_33_, new int[] {33}, null, null);
      builder.addAlternative(RascalRascal.prod__complement_$Class__lit___33_$layouts_LAYOUTLIST_charClass_$Class_, tmp);
	}
    protected static final void _init_prod__bracket_$Class__lit___40_$layouts_LAYOUTLIST_charclass_$Class_$layouts_LAYOUTLIST_lit___41__bracket(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-1156, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1155, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1152, 2, "$Class", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1150, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1148, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__bracket_$Class__lit___40_$layouts_LAYOUTLIST_charclass_$Class_$layouts_LAYOUTLIST_lit___41__bracket, tmp);
	}
    protected static final void _init_prod__intersection_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___38_38_$layouts_LAYOUTLIST_rhs_$Class__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1127, 4, "$Class", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1125, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1123, 2, prod__lit___38_38__char_class___range__38_38_char_class___range__38_38_, new int[] {38,38}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1122, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1119, 0, "$Class", null, null);
      builder.addAlternative(RascalRascal.prod__intersection_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___38_38_$layouts_LAYOUTLIST_rhs_$Class__assoc__left, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__union_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___124_124_$layouts_LAYOUTLIST_rhs_$Class__assoc__left(builder);
      
        _init_prod__simpleCharclass_$Class__lit___91_$layouts_LAYOUTLIST_ranges_iter_star_seps__$Range__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__difference_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_rhs_$Class__assoc__left(builder);
      
        _init_prod__complement_$Class__lit___33_$layouts_LAYOUTLIST_charClass_$Class_(builder);
      
        _init_prod__bracket_$Class__lit___40_$layouts_LAYOUTLIST_charclass_$Class_$layouts_LAYOUTLIST_lit___41__bracket(builder);
      
        _init_prod__intersection_$Class__lhs_$Class_$layouts_LAYOUTLIST_lit___38_38_$layouts_LAYOUTLIST_rhs_$Class__assoc__left(builder);
      
    }
  }
	
  protected static class $Bound {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__empty_$Bound__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(-1163, 0);
      builder.addAlternative(RascalRascal.prod__empty_$Bound__, tmp);
	}
    protected static final void _init_prod__default_$Bound__lit___59_$layouts_LAYOUTLIST_expression_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1171, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1169, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1167, 0, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Bound__lit___59_$layouts_LAYOUTLIST_expression_$Expression_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__empty_$Bound__(builder);
      
        _init_prod__default_$Bound__lit___59_$layouts_LAYOUTLIST_expression_$Expression_(builder);
      
    }
  }
	
  protected static class $FunctionType {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__typeArguments_$FunctionType__type_$Type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-1214, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1213, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-1210, 4, regular__iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1204, 0, "$TypeArg", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1206, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-1207, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-1209, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1202, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1200, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1199, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1196, 0, "$Type", null, null);
      builder.addAlternative(RascalRascal.prod__typeArguments_$FunctionType__type_$Type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__typeArguments_$FunctionType__type_$Type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
    }
  }
	
  protected static class $Case {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__patternWithAction_$Case__lit_case_$layouts_LAYOUTLIST_patternWithAction_$PatternWithAction__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1241, 2, "$PatternWithAction", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1239, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1237, 0, prod__lit_case__char_class___range__99_99_char_class___range__97_97_char_class___range__115_115_char_class___range__101_101_, new int[] {99,97,115,101}, null, null);
      builder.addAlternative(RascalRascal.prod__patternWithAction_$Case__lit_case_$layouts_LAYOUTLIST_patternWithAction_$PatternWithAction__tag__Foldable, tmp);
	}
    protected static final void _init_prod__default_$Case__lit_default_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_statement_$Statement__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1253, 4, "$Statement", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1251, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1249, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1248, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1246, 0, prod__lit_default__char_class___range__100_100_char_class___range__101_101_char_class___range__102_102_char_class___range__97_97_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_, new int[] {100,101,102,97,117,108,116}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Case__lit_default_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_statement_$Statement__tag__Foldable, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__patternWithAction_$Case__lit_case_$layouts_LAYOUTLIST_patternWithAction_$PatternWithAction__tag__Foldable(builder);
      
        _init_prod__default_$Case__lit_default_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_statement_$Statement__tag__Foldable(builder);
      
    }
  }
	
  protected static class $Statement {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__while_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new NonTerminalStackNode<IConstructor>(-1369, 10, "$Statement", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-1366, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-1364, 8, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1363, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-1360, 6, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1354, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1356, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-1357, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-1359, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1352, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-1350, 4, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1349, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1347, 2, prod__lit_while__char_class___range__119_119_char_class___range__104_104_char_class___range__105_105_char_class___range__108_108_char_class___range__101_101_, new int[] {119,104,105,108,101}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1346, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1343, 0, "$Label", null, null);
      builder.addAlternative(RascalRascal.prod__while_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable, tmp);
	}
    protected static final void _init_prod__assignment_$Statement__assignable_$Assignable_$layouts_LAYOUTLIST_operator_$Assignment_$layouts_LAYOUTLIST_statement_$Statement__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1386, 4, "$Statement", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1383, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1380, 2, "$Assignment", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1378, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1375, 0, "$Assignable", null, null);
      builder.addAlternative(RascalRascal.prod__assignment_$Statement__assignable_$Assignable_$layouts_LAYOUTLIST_operator_$Assignment_$layouts_LAYOUTLIST_statement_$Statement__tag__breakable, tmp);
	}
    protected static final void _init_prod__visit_$Statement__label_$Label_$layouts_LAYOUTLIST_visit_$Visit__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1332, 0, "$Label", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1335, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1337, 2, "$Visit", null, null);
      builder.addAlternative(RascalRascal.prod__visit_$Statement__label_$Label_$layouts_LAYOUTLIST_visit_$Visit__tag__breakable, tmp);
	}
    protected static final void _init_prod__for_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_for_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable___123_103_101_110_101_114_97_116_111_114_115_125_tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new NonTerminalStackNode<IConstructor>(-1417, 10, "$Statement", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-1415, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-1413, 8, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1412, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-1409, 6, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1403, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1405, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-1406, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-1408, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1401, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-1399, 4, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1398, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1396, 2, prod__lit_for__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_, new int[] {102,111,114}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1395, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1392, 0, "$Label", null, null);
      builder.addAlternative(RascalRascal.prod__for_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_for_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable___123_103_101_110_101_114_97_116_111_114_115_125_tag__breakable, tmp);
	}
    protected static final void _init_prod__switch_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_switch_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new LiteralStackNode<IConstructor>(-1452, 14, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-1451, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new SeparatedListStackNode<IConstructor>(-1448, 12, regular__iter_seps__$Case__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1445, 0, "$Case", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1447, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-1443, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(-1441, 10, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-1440, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-1438, 8, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1437, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-1434, 6, "$Expression", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1432, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-1430, 4, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1429, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1427, 2, prod__lit_switch__char_class___range__115_115_char_class___range__119_119_char_class___range__105_105_char_class___range__116_116_char_class___range__99_99_char_class___range__104_104_, new int[] {115,119,105,116,99,104}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1426, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1423, 0, "$Label", null, null);
      builder.addAlternative(RascalRascal.prod__switch_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_switch_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125__tag__breakable, tmp);
	}
    protected static final void _init_prod__emptyStatement_$Statement__lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-1456, 0, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      builder.addAlternative(RascalRascal.prod__emptyStatement_$Statement__lit___59_, tmp);
	}
    protected static final void _init_prod__tryFinally_$Statement__lit_try_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_handlers_iter_seps__$Catch__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit_finally_$layouts_LAYOUTLIST_finallyBody_$Statement__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new NonTerminalStackNode<IConstructor>(-1481, 8, "$Statement", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1478, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-1476, 6, prod__lit_finally__char_class___range__102_102_char_class___range__105_105_char_class___range__110_110_char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_char_class___range__121_121_, new int[] {102,105,110,97,108,108,121}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1475, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-1472, 4, regular__iter_seps__$Catch__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1469, 0, "$Catch", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1471, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1467, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1464, 2, "$Statement", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1462, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1460, 0, prod__lit_try__char_class___range__116_116_char_class___range__114_114_char_class___range__121_121_, new int[] {116,114,121}, null, null);
      builder.addAlternative(RascalRascal.prod__tryFinally_$Statement__lit_try_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_handlers_iter_seps__$Catch__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit_finally_$layouts_LAYOUTLIST_finallyBody_$Statement__tag__breakable, tmp);
	}
    protected static final void _init_prod__filter_$Statement__lit_filter_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-1489, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1488, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1486, 0, prod__lit_filter__char_class___range__102_102_char_class___range__105_105_char_class___range__108_108_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_, new int[] {102,105,108,116,101,114}, null, null);
      builder.addAlternative(RascalRascal.prod__filter_$Statement__lit_filter_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__globalDirective_$Statement__lit_global_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_names_iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-1524, 6, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1523, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-1520, 4, regular__iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1514, 0, "$QualifiedName", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1516, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-1517, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-1519, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1512, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1509, 2, "$Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1507, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1505, 0, prod__lit_global__char_class___range__103_103_char_class___range__108_108_char_class___range__111_111_char_class___range__98_98_char_class___range__97_97_char_class___range__108_108_, new int[] {103,108,111,98,97,108}, null, null);
      builder.addAlternative(RascalRascal.prod__globalDirective_$Statement__lit_global_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_names_iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__variableDeclaration_$Statement__declaration_$LocalVariableDeclaration_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-1533, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1532, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1529, 0, "$LocalVariableDeclaration", null, null);
      builder.addAlternative(RascalRascal.prod__variableDeclaration_$Statement__declaration_$LocalVariableDeclaration_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__break_$Statement__lit_break_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-1501, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1500, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1497, 2, "$Target", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1495, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1493, 0, prod__lit_break__char_class___range__98_98_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__107_107_, new int[] {98,114,101,97,107}, null, null);
      builder.addAlternative(RascalRascal.prod__break_$Statement__lit_break_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__insert_$Statement__lit_insert_$layouts_LAYOUTLIST_dataTarget_$DataTarget_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1575, 4, "$Statement", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1572, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1569, 2, "$DataTarget", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1567, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1565, 0, prod__lit_insert__char_class___range__105_105_char_class___range__110_110_char_class___range__115_115_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_, new int[] {105,110,115,101,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__insert_$Statement__lit_insert_$layouts_LAYOUTLIST_dataTarget_$DataTarget_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable, tmp);
	}
    protected static final void _init_prod__assertWithMessage_$Statement__lit_assert_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_message_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-1606, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1605, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-1602, 6, "$Expression", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1600, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-1598, 4, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1597, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1594, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1592, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1590, 0, prod__lit_assert__char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_, new int[] {97,115,115,101,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__assertWithMessage_$Statement__lit_assert_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_message_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__throw_$Statement__lit_throw_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1545, 2, "$Statement", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1542, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1540, 0, prod__lit_throw__char_class___range__116_116_char_class___range__104_104_char_class___range__114_114_char_class___range__111_111_char_class___range__119_119_, new int[] {116,104,114,111,119}, null, null);
      builder.addAlternative(RascalRascal.prod__throw_$Statement__lit_throw_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable, tmp);
	}
    protected static final void _init_prod__functionDeclaration_$Statement__functionDeclaration_$FunctionDeclaration__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1611, 0, "$FunctionDeclaration", null, null);
      builder.addAlternative(RascalRascal.prod__functionDeclaration_$Statement__functionDeclaration_$FunctionDeclaration__tag__breakable, tmp);
	}
    protected static final void _init_prod__nonEmptyBlock_$Statement__label_$Label_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-1632, 6, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1631, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-1628, 4, regular__iter_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1625, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1627, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1623, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1621, 2, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1620, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1617, 0, "$Label", null, null);
      builder.addAlternative(RascalRascal.prod__nonEmptyBlock_$Statement__label_$Label_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__try_$Statement__lit_try_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_handlers_iter_seps__$Catch__$layouts_LAYOUTLIST__assoc__non_assoc_tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new SeparatedListStackNode<IConstructor>(-1648, 4, regular__iter_seps__$Catch__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1645, 0, "$Catch", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1647, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1643, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1640, 2, "$Statement", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1638, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1636, 0, prod__lit_try__char_class___range__116_116_char_class___range__114_114_char_class___range__121_121_, new int[] {116,114,121}, null, null);
      builder.addAlternative(RascalRascal.prod__try_$Statement__lit_try_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_handlers_iter_seps__$Catch__$layouts_LAYOUTLIST__assoc__non_assoc_tag__breakable, tmp);
	}
    protected static final void _init_prod__doWhile_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_do_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new LiteralStackNode<IConstructor>(-1720, 14, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-1719, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new LiteralStackNode<IConstructor>(-1717, 12, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-1716, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new NonTerminalStackNode<IConstructor>(-1713, 10, "$Expression", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-1711, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-1709, 8, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1708, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-1706, 6, prod__lit_while__char_class___range__119_119_char_class___range__104_104_char_class___range__105_105_char_class___range__108_108_char_class___range__101_101_, new int[] {119,104,105,108,101}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1705, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1702, 4, "$Statement", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1700, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1698, 2, prod__lit_do__char_class___range__100_100_char_class___range__111_111_, new int[] {100,111}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1697, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1694, 0, "$Label", null, null);
      builder.addAlternative(RascalRascal.prod__doWhile_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_do_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__ifThenElse_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_thenStatement_$Statement_$layouts_LAYOUTLIST_lit_else_$layouts_LAYOUTLIST_elseStatement_$Statement__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new NonTerminalStackNode<IConstructor>(-1688, 14, "$Statement", null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-1685, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new LiteralStackNode<IConstructor>(-1683, 12, prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {101,108,115,101}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-1682, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new NonTerminalStackNode<IConstructor>(-1679, 10, "$Statement", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-1677, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-1675, 8, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1674, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-1671, 6, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1665, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1667, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-1668, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-1670, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1663, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-1661, 4, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1660, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1658, 2, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1657, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1654, 0, "$Label", null, null);
      builder.addAlternative(RascalRascal.prod__ifThenElse_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_thenStatement_$Statement_$layouts_LAYOUTLIST_lit_else_$layouts_LAYOUTLIST_elseStatement_$Statement__tag__breakable, tmp);
	}
    protected static final void _init_prod__continue_$Statement__lit_continue_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-1732, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1731, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1728, 2, "$Target", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1726, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1724, 0, prod__lit_continue__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__110_110_char_class___range__117_117_char_class___range__101_101_, new int[] {99,111,110,116,105,110,117,101}, null, null);
      builder.addAlternative(RascalRascal.prod__continue_$Statement__lit_continue_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__expression_$Statement__expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-1742, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1741, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1738, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__expression_$Statement__expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__assert_$Statement__lit_assert_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-1754, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1753, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1750, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1748, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1746, 0, prod__lit_assert__char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_, new int[] {97,115,115,101,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__assert_$Statement__lit_assert_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__solve_$Statement__lit_solve_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_variables_iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_bound_$Bound_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new NonTerminalStackNode<IConstructor>(-1785, 10, "$Statement", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-1782, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-1780, 8, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1779, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-1776, 6, "$Bound", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1774, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-1771, 4, regular__iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1765, 0, "$QualifiedName", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1767, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-1768, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-1770, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1763, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1761, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1760, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1758, 0, prod__lit_solve__char_class___range__115_115_char_class___range__111_111_char_class___range__108_108_char_class___range__118_118_char_class___range__101_101_, new int[] {115,111,108,118,101}, null, null);
      builder.addAlternative(RascalRascal.prod__solve_$Statement__lit_solve_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_variables_iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_bound_$Bound_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable, tmp);
	}
    protected static final void _init_prod__append_$Statement__lit_append_$layouts_LAYOUTLIST_dataTarget_$DataTarget_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1560, 4, "$Statement", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1557, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1554, 2, "$DataTarget", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1552, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1550, 0, prod__lit_append__char_class___range__97_97_char_class___range__112_112_char_class___range__112_112_char_class___range__101_101_char_class___range__110_110_char_class___range__100_100_, new int[] {97,112,112,101,110,100}, null, null);
      builder.addAlternative(RascalRascal.prod__append_$Statement__lit_append_$layouts_LAYOUTLIST_dataTarget_$DataTarget_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable, tmp);
	}
    protected static final void _init_prod__fail_$Statement__lit_fail_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-1798, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1797, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1794, 2, "$Target", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1792, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1790, 0, prod__lit_fail__char_class___range__102_102_char_class___range__97_97_char_class___range__105_105_char_class___range__108_108_, new int[] {102,97,105,108}, null, null);
      builder.addAlternative(RascalRascal.prod__fail_$Statement__lit_fail_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable, tmp);
	}
    protected static final void _init_prod__ifThen_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_thenStatement_$Statement_$layouts_LAYOUTLIST_empty__tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[12] = new EmptyStackNode<IConstructor>(-1835, 12, regular__empty, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {101,108,115,101})});
      tmp[11] = new NonTerminalStackNode<IConstructor>(-1832, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new NonTerminalStackNode<IConstructor>(-1829, 10, "$Statement", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-1826, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-1824, 8, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-1823, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-1820, 6, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1814, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1816, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-1817, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-1819, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-1812, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-1810, 4, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1809, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-1807, 2, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1806, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1803, 0, "$Label", null, null);
      builder.addAlternative(RascalRascal.prod__ifThen_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_thenStatement_$Statement_$layouts_LAYOUTLIST_empty__tag__breakable, tmp);
	}
    protected static final void _init_prod__return_$Statement__lit_return_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1585, 2, "$Statement", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1582, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1580, 0, prod__lit_return__char_class___range__114_114_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_, new int[] {114,101,116,117,114,110}, null, null);
      builder.addAlternative(RascalRascal.prod__return_$Statement__lit_return_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__while_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable(builder);
      
        _init_prod__assignment_$Statement__assignable_$Assignable_$layouts_LAYOUTLIST_operator_$Assignment_$layouts_LAYOUTLIST_statement_$Statement__tag__breakable(builder);
      
        _init_prod__visit_$Statement__label_$Label_$layouts_LAYOUTLIST_visit_$Visit__tag__breakable(builder);
      
        _init_prod__for_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_for_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable___123_103_101_110_101_114_97_116_111_114_115_125_tag__breakable(builder);
      
        _init_prod__switch_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_switch_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125__tag__breakable(builder);
      
        _init_prod__emptyStatement_$Statement__lit___59_(builder);
      
        _init_prod__tryFinally_$Statement__lit_try_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_handlers_iter_seps__$Catch__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit_finally_$layouts_LAYOUTLIST_finallyBody_$Statement__tag__breakable(builder);
      
        _init_prod__filter_$Statement__lit_filter_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__globalDirective_$Statement__lit_global_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_names_iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__variableDeclaration_$Statement__declaration_$LocalVariableDeclaration_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__break_$Statement__lit_break_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__insert_$Statement__lit_insert_$layouts_LAYOUTLIST_dataTarget_$DataTarget_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable(builder);
      
        _init_prod__assertWithMessage_$Statement__lit_assert_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_message_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__throw_$Statement__lit_throw_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable(builder);
      
        _init_prod__functionDeclaration_$Statement__functionDeclaration_$FunctionDeclaration__tag__breakable(builder);
      
        _init_prod__nonEmptyBlock_$Statement__label_$Label_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__try_$Statement__lit_try_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_handlers_iter_seps__$Catch__$layouts_LAYOUTLIST__assoc__non_assoc_tag__breakable(builder);
      
        _init_prod__doWhile_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_do_$layouts_LAYOUTLIST_body_$Statement_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__ifThenElse_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_thenStatement_$Statement_$layouts_LAYOUTLIST_lit_else_$layouts_LAYOUTLIST_elseStatement_$Statement__tag__breakable(builder);
      
        _init_prod__continue_$Statement__lit_continue_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__expression_$Statement__expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__assert_$Statement__lit_assert_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__solve_$Statement__lit_solve_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_variables_iter_seps__$QualifiedName__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_bound_$Bound_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_body_$Statement__tag__breakable(builder);
      
        _init_prod__append_$Statement__lit_append_$layouts_LAYOUTLIST_dataTarget_$DataTarget_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable(builder);
      
        _init_prod__fail_$Statement__lit_fail_$layouts_LAYOUTLIST_target_$Target_$layouts_LAYOUTLIST_lit___59__tag__breakable(builder);
      
        _init_prod__ifThen_$Statement__label_$Label_$layouts_LAYOUTLIST_lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_thenStatement_$Statement_$layouts_LAYOUTLIST_empty__tag__breakable(builder);
      
        _init_prod__return_$Statement__lit_return_$layouts_LAYOUTLIST_statement_$Statement__assoc__non_assoc_tag__breakable(builder);
      
    }
  }
	
  protected static class $Renamings {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Renamings__lit_renaming_$layouts_LAYOUTLIST_renamings_iter_seps__$Renaming__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new SeparatedListStackNode<IConstructor>(-1893, 2, regular__iter_seps__$Renaming__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-1887, 0, "$Renaming", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-1889, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-1890, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-1892, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1885, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1883, 0, prod__lit_renaming__char_class___range__114_114_char_class___range__101_101_char_class___range__110_110_char_class___range__97_97_char_class___range__109_109_char_class___range__105_105_char_class___range__110_110_char_class___range__103_103_, new int[] {114,101,110,97,109,105,110,103}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Renamings__lit_renaming_$layouts_LAYOUTLIST_renamings_iter_seps__$Renaming__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Renamings__lit_renaming_$layouts_LAYOUTLIST_renamings_iter_seps__$Renaming__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $StringLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__nonInterpolated_$StringLiteral__constant_$StringConstant_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1903, 0, "$StringConstant", null, null);
      builder.addAlternative(RascalRascal.prod__nonInterpolated_$StringLiteral__constant_$StringConstant_, tmp);
	}
    protected static final void _init_prod__template_$StringLiteral__pre_$PreStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringTail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1919, 4, "$StringTail", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1917, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1914, 2, "$StringTemplate", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1912, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1909, 0, "$PreStringChars", null, null);
      builder.addAlternative(RascalRascal.prod__template_$StringLiteral__pre_$PreStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringTail_, tmp);
	}
    protected static final void _init_prod__interpolated_$StringLiteral__pre_$PreStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringTail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-1935, 4, "$StringTail", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-1933, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-1930, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1928, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-1925, 0, "$PreStringChars", null, null);
      builder.addAlternative(RascalRascal.prod__interpolated_$StringLiteral__pre_$PreStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringTail_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__nonInterpolated_$StringLiteral__constant_$StringConstant_(builder);
      
        _init_prod__template_$StringLiteral__pre_$PreStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringTail_(builder);
      
        _init_prod__interpolated_$StringLiteral__pre_$PreStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringTail_(builder);
      
    }
  }
	
  protected static class $Visibility {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__public_$Visibility__lit_public_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-1963, 0, prod__lit_public__char_class___range__112_112_char_class___range__117_117_char_class___range__98_98_char_class___range__108_108_char_class___range__105_105_char_class___range__99_99_, new int[] {112,117,98,108,105,99}, null, null);
      builder.addAlternative(RascalRascal.prod__public_$Visibility__lit_public_, tmp);
	}
    protected static final void _init_prod__default_$Visibility__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(-1966, 0);
      builder.addAlternative(RascalRascal.prod__default_$Visibility__, tmp);
	}
    protected static final void _init_prod__private_$Visibility__lit_private_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-1970, 0, prod__lit_private__char_class___range__112_112_char_class___range__114_114_char_class___range__105_105_char_class___range__118_118_char_class___range__97_97_char_class___range__116_116_char_class___range__101_101_, new int[] {112,114,105,118,97,116,101}, null, null);
      builder.addAlternative(RascalRascal.prod__private_$Visibility__lit_private_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__public_$Visibility__lit_public_(builder);
      
        _init_prod__default_$Visibility__(builder);
      
        _init_prod__private_$Visibility__lit_private_(builder);
      
    }
  }
	
  protected static class $PostPathChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$PostPathChars__lit___62_$URLChars_lit___124_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-1980, 2, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-1979, 1, "$URLChars", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-1977, 0, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      builder.addAlternative(RascalRascal.prod__$PostPathChars__lit___62_$URLChars_lit___124_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$PostPathChars__lit___62_$URLChars_lit___124_(builder);
      
    }
  }
	
  protected static class $NamedBackslash {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$NamedBackslash__char_class___range__92_92_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(-1989, 0, new int[][]{{92,92}}, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{60,60},{62,62},{92,92}})});
      builder.addAlternative(RascalRascal.prod__$NamedBackslash__char_class___range__92_92_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$NamedBackslash__char_class___range__92_92_(builder);
      
    }
  }
	
  protected static class $Command {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__import_$Command__imported_$Import_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2080, 0, "$Import", null, null);
      builder.addAlternative(RascalRascal.prod__import_$Command__imported_$Import_, tmp);
	}
    protected static final void _init_prod__statement_$Command__statement_$Statement_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2087, 0, "$Statement", null, null);
      builder.addAlternative(RascalRascal.prod__statement_$Command__statement_$Statement_, tmp);
	}
    protected static final void _init_prod__expression_$Command__expression_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2094, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__expression_$Command__expression_$Expression_, tmp);
	}
    protected static final void _init_prod__declaration_$Command__declaration_$Declaration_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2100, 0, "$Declaration", null, null);
      builder.addAlternative(RascalRascal.prod__declaration_$Command__declaration_$Declaration_, tmp);
	}
    protected static final void _init_prod__shell_$Command__lit___58_$layouts_LAYOUTLIST_command_$ShellCommand_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2109, 2, "$ShellCommand", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2107, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2105, 0, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      builder.addAlternative(RascalRascal.prod__shell_$Command__lit___58_$layouts_LAYOUTLIST_command_$ShellCommand_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__import_$Command__imported_$Import_(builder);
      
        _init_prod__statement_$Command__statement_$Statement_(builder);
      
        _init_prod__expression_$Command__expression_$Expression_(builder);
      
        _init_prod__declaration_$Command__declaration_$Declaration_(builder);
      
        _init_prod__shell_$Command__lit___58_$layouts_LAYOUTLIST_command_$ShellCommand_(builder);
      
    }
  }
	
  protected static class $Visit {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__defaultStrategy_$Visit__lit_visit_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_subject_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[12] = new LiteralStackNode<IConstructor>(-2037, 12, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-2036, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new SeparatedListStackNode<IConstructor>(-2033, 10, regular__iter_seps__$Case__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-2030, 0, "$Case", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-2032, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-2028, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-2026, 8, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-2025, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-2023, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-2022, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-2019, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-2017, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-2015, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2014, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2012, 0, prod__lit_visit__char_class___range__118_118_char_class___range__105_105_char_class___range__115_115_char_class___range__105_105_char_class___range__116_116_, new int[] {118,105,115,105,116}, null, null);
      builder.addAlternative(RascalRascal.prod__defaultStrategy_$Visit__lit_visit_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_subject_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__givenStrategy_$Visit__strategy_$Strategy_$layouts_LAYOUTLIST_lit_visit_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_subject_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new LiteralStackNode<IConstructor>(-2071, 14, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-2070, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new SeparatedListStackNode<IConstructor>(-2067, 12, regular__iter_seps__$Case__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-2064, 0, "$Case", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-2066, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-2062, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(-2060, 10, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-2059, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-2057, 8, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-2056, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-2053, 6, "$Expression", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-2051, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-2049, 4, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-2048, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-2046, 2, prod__lit_visit__char_class___range__118_118_char_class___range__105_105_char_class___range__115_115_char_class___range__105_105_char_class___range__116_116_, new int[] {118,105,115,105,116}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2045, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2042, 0, "$Strategy", null, null);
      builder.addAlternative(RascalRascal.prod__givenStrategy_$Visit__strategy_$Strategy_$layouts_LAYOUTLIST_lit_visit_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_subject_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__defaultStrategy_$Visit__lit_visit_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_subject_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__givenStrategy_$Visit__strategy_$Strategy_$layouts_LAYOUTLIST_lit_visit_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_subject_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_cases_iter_seps__$Case__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
    }
  }
	
  protected static class $ProtocolTail {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__post_$ProtocolTail__post_$PostProtocolChars_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2119, 0, "$PostProtocolChars", null, null);
      builder.addAlternative(RascalRascal.prod__post_$ProtocolTail__post_$PostProtocolChars_, tmp);
	}
    protected static final void _init_prod__mid_$ProtocolTail__mid_$MidProtocolChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$ProtocolTail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-2135, 4, "$ProtocolTail", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-2133, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2130, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2128, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2125, 0, "$MidProtocolChars", null, null);
      builder.addAlternative(RascalRascal.prod__mid_$ProtocolTail__mid_$MidProtocolChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$ProtocolTail_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__post_$ProtocolTail__post_$PostProtocolChars_(builder);
      
        _init_prod__mid_$ProtocolTail__mid_$MidProtocolChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$ProtocolTail_(builder);
      
    }
  }
	
  protected static class $PreStringChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$PreStringChars__char_class___range__34_34_iter_star__$StringCharacter_char_class___range__60_60__tag__category___67_111_110_115_116_97_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new CharStackNode<IConstructor>(-2215, 2, new int[][]{{60,60}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-2214, 1, regular__iter_star__$StringCharacter, new NonTerminalStackNode<IConstructor>(-2213, 0, "$StringCharacter", null, null), false, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-2211, 0, new int[][]{{34,34}}, null, null);
      builder.addAlternative(RascalRascal.prod__$PreStringChars__char_class___range__34_34_iter_star__$StringCharacter_char_class___range__60_60__tag__category___67_111_110_115_116_97_110_116, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$PreStringChars__char_class___range__34_34_iter_star__$StringCharacter_char_class___range__60_60__tag__category___67_111_110_115_116_97_110_116(builder);
      
    }
  }
	
  protected static class $QualifiedName {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$QualifiedName__names_iter_seps__$Name__$layouts_LAYOUTLIST_lit___58_58_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SeparatedListStackNode<IConstructor>(-2233, 0, regular__iter_seps__$Name__$layouts_LAYOUTLIST_lit___58_58_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-2224, 0, "$Name", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-2226, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-2227, 2, prod__lit___58_58__char_class___range__58_58_char_class___range__58_58_, new int[] {58,58}, null, null), new NonTerminalStackNode<IConstructor>(-2229, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {58,58})});
      builder.addAlternative(RascalRascal.prod__default_$QualifiedName__names_iter_seps__$Name__$layouts_LAYOUTLIST_lit___58_58_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$QualifiedName__names_iter_seps__$Name__$layouts_LAYOUTLIST_lit___58_58_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $StringMiddle {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__template_$StringMiddle__mid_$MidStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringMiddle_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-2284, 4, "$StringMiddle", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-2282, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2279, 2, "$StringTemplate", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2277, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2274, 0, "$MidStringChars", null, null);
      builder.addAlternative(RascalRascal.prod__template_$StringMiddle__mid_$MidStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringMiddle_, tmp);
	}
    protected static final void _init_prod__interpolated_$StringMiddle__mid_$MidStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringMiddle_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-2300, 4, "$StringMiddle", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-2298, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2295, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2293, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2290, 0, "$MidStringChars", null, null);
      builder.addAlternative(RascalRascal.prod__interpolated_$StringMiddle__mid_$MidStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringMiddle_, tmp);
	}
    protected static final void _init_prod__mid_$StringMiddle__mid_$MidStringChars_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2306, 0, "$MidStringChars", null, null);
      builder.addAlternative(RascalRascal.prod__mid_$StringMiddle__mid_$MidStringChars_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__template_$StringMiddle__mid_$MidStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringMiddle_(builder);
      
        _init_prod__interpolated_$StringMiddle__mid_$MidStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringMiddle_(builder);
      
        _init_prod__mid_$StringMiddle__mid_$MidStringChars_(builder);
      
    }
  }
	
  protected static class $URLChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$URLChars__iter_star__char_class___range__0_8_range__11_12_range__14_31_range__33_59_range__61_123_range__125_16777215_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new ListStackNode<IConstructor>(-2265, 0, regular__iter_star__char_class___range__0_8_range__11_12_range__14_31_range__33_59_range__61_123_range__125_16777215, new CharStackNode<IConstructor>(-2264, 0, new int[][]{{0,8},{11,12},{14,31},{33,59},{61,123},{125,16777215}}, null, null), false, null, null);
      builder.addAlternative(RascalRascal.prod__$URLChars__iter_star__char_class___range__0_8_range__11_12_range__14_31_range__33_59_range__61_123_range__125_16777215_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$URLChars__iter_star__char_class___range__0_8_range__11_12_range__14_31_range__33_59_range__61_123_range__125_16777215_(builder);
      
    }
  }
	
  protected static class $TimeZonePart {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$TimeZonePart__lit_Z_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2314, 0, prod__lit_Z__char_class___range__90_90_, new int[] {90}, null, null);
      builder.addAlternative(RascalRascal.prod__$TimeZonePart__lit_Z_, tmp);
	}
    protected static final void _init_prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new CharStackNode<IConstructor>(-2319, 2, new int[][]{{48,57}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-2318, 1, new int[][]{{48,49}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-2317, 0, new int[][]{{43,43},{45,45}}, null, null);
      builder.addAlternative(RascalRascal.prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_, tmp);
	}
    protected static final void _init_prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(-2326, 4, new int[][]{{48,57}}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(-2325, 3, new int[][]{{48,53}}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-2324, 2, new int[][]{{48,57}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-2323, 1, new int[][]{{48,49}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-2322, 0, new int[][]{{43,43},{45,45}}, null, null);
      builder.addAlternative(RascalRascal.prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_, tmp);
	}
    protected static final void _init_prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[6];
      
      tmp[5] = new CharStackNode<IConstructor>(-2334, 5, new int[][]{{48,57}}, null, null);
      tmp[4] = new CharStackNode<IConstructor>(-2333, 4, new int[][]{{48,53}}, null, null);
      tmp[3] = new LiteralStackNode<IConstructor>(-2332, 3, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-2331, 2, new int[][]{{48,57}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-2330, 1, new int[][]{{48,49}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-2329, 0, new int[][]{{43,43},{45,45}}, null, null);
      builder.addAlternative(RascalRascal.prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$TimeZonePart__lit_Z_(builder);
      
        _init_prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_(builder);
      
        _init_prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_(builder);
      
        _init_prod__$TimeZonePart__char_class___range__43_43_range__45_45_char_class___range__48_49_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_(builder);
      
    }
  }
	
  protected static class $ShellCommand {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__edit_$ShellCommand__lit_edit_$layouts_LAYOUTLIST_name_$QualifiedName_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2346, 2, "$QualifiedName", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2344, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2342, 0, prod__lit_edit__char_class___range__101_101_char_class___range__100_100_char_class___range__105_105_char_class___range__116_116_, new int[] {101,100,105,116}, null, null);
      builder.addAlternative(RascalRascal.prod__edit_$ShellCommand__lit_edit_$layouts_LAYOUTLIST_name_$QualifiedName_, tmp);
	}
    protected static final void _init_prod__help_$ShellCommand__lit_help_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2351, 0, prod__lit_help__char_class___range__104_104_char_class___range__101_101_char_class___range__108_108_char_class___range__112_112_, new int[] {104,101,108,112}, null, null);
      builder.addAlternative(RascalRascal.prod__help_$ShellCommand__lit_help_, tmp);
	}
    protected static final void _init_prod__quit_$ShellCommand__lit_quit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2355, 0, prod__lit_quit__char_class___range__113_113_char_class___range__117_117_char_class___range__105_105_char_class___range__116_116_, new int[] {113,117,105,116}, null, null);
      builder.addAlternative(RascalRascal.prod__quit_$ShellCommand__lit_quit_, tmp);
	}
    protected static final void _init_prod__setOption_$ShellCommand__lit_set_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_expression_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-2368, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-2366, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2363, 2, "$QualifiedName", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2361, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2359, 0, prod__lit_set__char_class___range__115_115_char_class___range__101_101_char_class___range__116_116_, new int[] {115,101,116}, null, null);
      builder.addAlternative(RascalRascal.prod__setOption_$ShellCommand__lit_set_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_expression_$Expression_, tmp);
	}
    protected static final void _init_prod__history_$ShellCommand__lit_history_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2373, 0, prod__lit_history__char_class___range__104_104_char_class___range__105_105_char_class___range__115_115_char_class___range__116_116_char_class___range__111_111_char_class___range__114_114_char_class___range__121_121_, new int[] {104,105,115,116,111,114,121}, null, null);
      builder.addAlternative(RascalRascal.prod__history_$ShellCommand__lit_history_, tmp);
	}
    protected static final void _init_prod__unimport_$ShellCommand__lit_unimport_$layouts_LAYOUTLIST_name_$QualifiedName_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2381, 2, "$QualifiedName", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2379, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2377, 0, prod__lit_unimport__char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_, new int[] {117,110,105,109,112,111,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__unimport_$ShellCommand__lit_unimport_$layouts_LAYOUTLIST_name_$QualifiedName_, tmp);
	}
    protected static final void _init_prod__undeclare_$ShellCommand__lit_undeclare_$layouts_LAYOUTLIST_name_$QualifiedName_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2390, 2, "$QualifiedName", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2388, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2386, 0, prod__lit_undeclare__char_class___range__117_117_char_class___range__110_110_char_class___range__100_100_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__114_114_char_class___range__101_101_, new int[] {117,110,100,101,99,108,97,114,101}, null, null);
      builder.addAlternative(RascalRascal.prod__undeclare_$ShellCommand__lit_undeclare_$layouts_LAYOUTLIST_name_$QualifiedName_, tmp);
	}
    protected static final void _init_prod__listModules_$ShellCommand__lit_modules_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2395, 0, prod__lit_modules__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_char_class___range__115_115_, new int[] {109,111,100,117,108,101,115}, null, null);
      builder.addAlternative(RascalRascal.prod__listModules_$ShellCommand__lit_modules_, tmp);
	}
    protected static final void _init_prod__test_$ShellCommand__lit_test_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2399, 0, prod__lit_test__char_class___range__116_116_char_class___range__101_101_char_class___range__115_115_char_class___range__116_116_, new int[] {116,101,115,116}, null, null);
      builder.addAlternative(RascalRascal.prod__test_$ShellCommand__lit_test_, tmp);
	}
    protected static final void _init_prod__listDeclarations_$ShellCommand__lit_declarations_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2403, 0, prod__lit_declarations__char_class___range__100_100_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__114_114_char_class___range__97_97_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_, new int[] {100,101,99,108,97,114,97,116,105,111,110,115}, null, null);
      builder.addAlternative(RascalRascal.prod__listDeclarations_$ShellCommand__lit_declarations_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__edit_$ShellCommand__lit_edit_$layouts_LAYOUTLIST_name_$QualifiedName_(builder);
      
        _init_prod__help_$ShellCommand__lit_help_(builder);
      
        _init_prod__quit_$ShellCommand__lit_quit_(builder);
      
        _init_prod__setOption_$ShellCommand__lit_set_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_expression_$Expression_(builder);
      
        _init_prod__history_$ShellCommand__lit_history_(builder);
      
        _init_prod__unimport_$ShellCommand__lit_unimport_$layouts_LAYOUTLIST_name_$QualifiedName_(builder);
      
        _init_prod__undeclare_$ShellCommand__lit_undeclare_$layouts_LAYOUTLIST_name_$QualifiedName_(builder);
      
        _init_prod__listModules_$ShellCommand__lit_modules_(builder);
      
        _init_prod__test_$ShellCommand__lit_test_(builder);
      
        _init_prod__listDeclarations_$ShellCommand__lit_declarations_(builder);
      
    }
  }
	
  protected static class $LocationLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$LocationLiteral__protocolPart_$ProtocolPart_$layouts_LAYOUTLIST_pathPart_$PathPart_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2417, 2, "$PathPart", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2415, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2412, 0, "$ProtocolPart", null, null);
      builder.addAlternative(RascalRascal.prod__default_$LocationLiteral__protocolPart_$ProtocolPart_$layouts_LAYOUTLIST_pathPart_$PathPart_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$LocationLiteral__protocolPart_$ProtocolPart_$layouts_LAYOUTLIST_pathPart_$PathPart_(builder);
      
    }
  }
	
  protected static class $Range {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__character_$Range__character_$Char_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2485, 0, "$Char", null, null);
      builder.addAlternative(RascalRascal.prod__character_$Range__character_$Char_, tmp);
	}
    protected static final void _init_prod__fromTo_$Range__start_$Char_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_end_$Char_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-2499, 4, "$Char", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-2497, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-2495, 2, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2494, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2491, 0, "$Char", null, null);
      builder.addAlternative(RascalRascal.prod__fromTo_$Range__start_$Char_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_end_$Char_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__character_$Range__character_$Char_(builder);
      
        _init_prod__fromTo_$Range__start_$Char_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_end_$Char_(builder);
      
    }
  }
	
  protected static class $Rest {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$Rest__iter_star__char_class___range__0_16777215_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new ListStackNode<IConstructor>(-2510, 0, regular__iter_star__char_class___range__0_16777215, new CharStackNode<IConstructor>(-2507, 0, new int[][]{{0,16777215}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{0,16777215}})});
      builder.addAlternative(RascalRascal.prod__$Rest__iter_star__char_class___range__0_16777215_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$Rest__iter_star__char_class___range__0_16777215_(builder);
      
    }
  }
	
  protected static class $JustTime {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$JustTime__lit___36_84_$TimePartNoTZ_$TimeZonePart_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-2529, 2, "$TimeZonePart", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2527, 1, "$TimePartNoTZ", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2525, 0, prod__lit___36_84__char_class___range__36_36_char_class___range__84_84_, new int[] {36,84}, null, null);
      builder.addAlternative(RascalRascal.prod__$JustTime__lit___36_84_$TimePartNoTZ_$TimeZonePart_, tmp);
	}
    protected static final void _init_prod__$JustTime__lit___36_84_$TimePartNoTZ_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2536, 1, "$TimePartNoTZ", null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{43,43},{45,45}})});
      tmp[0] = new LiteralStackNode<IConstructor>(-2532, 0, prod__lit___36_84__char_class___range__36_36_char_class___range__84_84_, new int[] {36,84}, null, null);
      builder.addAlternative(RascalRascal.prod__$JustTime__lit___36_84_$TimePartNoTZ_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$JustTime__lit___36_84_$TimePartNoTZ_$TimeZonePart_(builder);
      
        _init_prod__$JustTime__lit___36_84_$TimePartNoTZ_(builder);
      
    }
  }
	
  protected static class $StringCharacter {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$StringCharacter__$UnicodeEscape_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2559, 0, "$UnicodeEscape", null, null);
      builder.addAlternative(RascalRascal.prod__$StringCharacter__$UnicodeEscape_, tmp);
	}
    protected static final void _init_prod__$StringCharacter__char_class___range__0_33_range__35_38_range__40_59_range__61_61_range__63_91_range__93_16777215_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(-2562, 0, new int[][]{{0,33},{35,38},{40,59},{61,61},{63,91},{93,16777215}}, null, null);
      builder.addAlternative(RascalRascal.prod__$StringCharacter__char_class___range__0_33_range__35_38_range__40_59_range__61_61_range__63_91_range__93_16777215_, tmp);
	}
    protected static final void _init_prod__$StringCharacter__lit___92_char_class___range__34_34_range__39_39_range__60_60_range__62_62_range__92_92_range__98_98_range__102_102_range__110_110_range__114_114_range__116_116_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new CharStackNode<IConstructor>(-2566, 1, new int[][]{{34,34},{39,39},{60,60},{62,62},{92,92},{98,98},{102,102},{110,110},{114,114},{116,116}}, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2565, 0, prod__lit___92__char_class___range__92_92_, new int[] {92}, null, null);
      builder.addAlternative(RascalRascal.prod__$StringCharacter__lit___92_char_class___range__34_34_range__39_39_range__60_60_range__62_62_range__92_92_range__98_98_range__102_102_range__110_110_range__114_114_range__116_116_, tmp);
	}
    protected static final void _init_prod__$StringCharacter__char_class___range__10_10_iter_star__char_class___range__9_9_range__32_32_range__65_65_range__160_160_range__5760_5760_range__8192_8192_range__8239_8239_range__8287_8287_range__12288_12288_char_class___range__39_39_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new CharStackNode<IConstructor>(-2572, 2, new int[][]{{39,39}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-2571, 1, regular__iter_star__char_class___range__9_9_range__32_32_range__65_65_range__160_160_range__5760_5760_range__8192_8192_range__8239_8239_range__8287_8287_range__12288_12288, new CharStackNode<IConstructor>(-2570, 0, new int[][]{{9,9},{32,32},{65,65},{160,160},{5760,5760},{8192,8192},{8239,8239},{8287,8287},{12288,12288}}, null, null), false, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-2569, 0, new int[][]{{10,10}}, null, null);
      builder.addAlternative(RascalRascal.prod__$StringCharacter__char_class___range__10_10_iter_star__char_class___range__9_9_range__32_32_range__65_65_range__160_160_range__5760_5760_range__8192_8192_range__8239_8239_range__8287_8287_range__12288_12288_char_class___range__39_39_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$StringCharacter__$UnicodeEscape_(builder);
      
        _init_prod__$StringCharacter__char_class___range__0_33_range__35_38_range__40_59_range__61_61_range__63_91_range__93_16777215_(builder);
      
        _init_prod__$StringCharacter__lit___92_char_class___range__34_34_range__39_39_range__60_60_range__62_62_range__92_92_range__98_98_range__102_102_range__110_110_range__114_114_range__116_116_(builder);
      
        _init_prod__$StringCharacter__char_class___range__10_10_iter_star__char_class___range__9_9_range__32_32_range__65_65_range__160_160_range__5760_5760_range__8192_8192_range__8239_8239_range__8287_8287_range__12288_12288_char_class___range__39_39_(builder);
      
    }
  }
	
  protected static class $layouts_LAYOUTLIST {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$layouts_LAYOUTLIST__iter_star__$LAYOUT_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new ListStackNode<IConstructor>(-2589, 0, regular__iter_star__$LAYOUT, new NonTerminalStackNode<IConstructor>(-2584, 0, "$LAYOUT", null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{9,13},{32,32},{133,133},{160,160},{5760,5760},{6158,6158},{8192,8202},{8232,8233},{8239,8239},{8287,8287},{12288,12288}}), new StringFollowRestriction(new int[] {47,47}), new StringFollowRestriction(new int[] {47,42})});
      builder.addAlternative(RascalRascal.prod__$layouts_LAYOUTLIST__iter_star__$LAYOUT_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$layouts_LAYOUTLIST__iter_star__$LAYOUT_(builder);
      
    }
  }
	
  protected static class $DataTarget {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__labeled_$DataTarget__label_$Name_$layouts_LAYOUTLIST_lit___58_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-2734, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2733, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2730, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__labeled_$DataTarget__label_$Name_$layouts_LAYOUTLIST_lit___58_, tmp);
	}
    protected static final void _init_prod__empty_$DataTarget__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(-2737, 0);
      builder.addAlternative(RascalRascal.prod__empty_$DataTarget__, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__labeled_$DataTarget__label_$Name_$layouts_LAYOUTLIST_lit___58_(builder);
      
        _init_prod__empty_$DataTarget__(builder);
      
    }
  }
	
  protected static class $RealLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$RealLiteral__iter__char_class___range__48_57_char_class___range__68_68_range__70_70_range__100_100_range__102_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new CharStackNode<IConstructor>(-2746, 1, new int[][]{{68,68},{70,70},{100,100},{102,102}}, null, null);
      tmp[0] = new ListStackNode<IConstructor>(-2745, 0, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2744, 0, new int[][]{{48,57}}, null, null), true, null, null);
      builder.addAlternative(RascalRascal.prod__$RealLiteral__iter__char_class___range__48_57_char_class___range__68_68_range__70_70_range__100_100_range__102_102_, tmp);
	}
    protected static final void _init_prod__$RealLiteral__iter__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new OptionalStackNode<IConstructor>(-2757, 4, regular__opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102, new CharStackNode<IConstructor>(-2756, 0, new int[][]{{68,68},{70,70},{100,100},{102,102}}, null, null), null, null);
      tmp[3] = new ListStackNode<IConstructor>(-2755, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2754, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new OptionalStackNode<IConstructor>(-2753, 2, regular__opt__char_class___range__43_43_range__45_45, new CharStackNode<IConstructor>(-2752, 0, new int[][]{{43,43},{45,45}}, null, null), null, null);
      tmp[1] = new CharStackNode<IConstructor>(-2751, 1, new int[][]{{69,69},{101,101}}, null, null);
      tmp[0] = new ListStackNode<IConstructor>(-2750, 0, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2749, 0, new int[][]{{48,57}}, null, null), true, null, null);
      builder.addAlternative(RascalRascal.prod__$RealLiteral__iter__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_, tmp);
	}
    protected static final void _init_prod__$RealLiteral__lit___46_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new OptionalStackNode<IConstructor>(-2780, 2, regular__opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102, new CharStackNode<IConstructor>(-2779, 0, new int[][]{{68,68},{70,70},{100,100},{102,102}}, null, null), null, null);
      tmp[1] = new ListStackNode<IConstructor>(-2778, 1, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2777, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2776, 0, prod__lit___46__char_class___range__46_46_, new int[] {46}, new IEnterFilter[] {new CharPrecedeRestriction(new int[][]{{46,46}})}, null);
      builder.addAlternative(RascalRascal.prod__$RealLiteral__lit___46_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_, tmp);
	}
    protected static final void _init_prod__$RealLiteral__iter__char_class___range__48_57_lit___46_iter_star__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[0] = new ListStackNode<IConstructor>(-2761, 0, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2760, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(-2762, 1, prod__lit___46__char_class___range__46_46_, new int[] {46}, null, null);
      tmp[2] = new ListStackNode<IConstructor>(-2764, 2, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(-2763, 0, new int[][]{{48,57}}, null, null), false, null, null);
      tmp[3] = new CharStackNode<IConstructor>(-2765, 3, new int[][]{{69,69},{101,101}}, null, null);
      tmp[4] = new OptionalStackNode<IConstructor>(-2767, 4, regular__opt__char_class___range__43_43_range__45_45, new CharStackNode<IConstructor>(-2766, 0, new int[][]{{43,43},{45,45}}, null, null), null, null);
      tmp[5] = new ListStackNode<IConstructor>(-2769, 5, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2768, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[6] = new OptionalStackNode<IConstructor>(-2771, 6, regular__opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102, new CharStackNode<IConstructor>(-2770, 0, new int[][]{{68,68},{70,70},{100,100},{102,102}}, null, null), null, null);
      builder.addAlternative(RascalRascal.prod__$RealLiteral__iter__char_class___range__48_57_lit___46_iter_star__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_, tmp);
	}
    protected static final void _init_prod__$RealLiteral__iter__char_class___range__48_57_lit___46_iter_star__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[4];
      
      tmp[3] = new OptionalStackNode<IConstructor>(-2791, 3, regular__opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102, new CharStackNode<IConstructor>(-2790, 0, new int[][]{{68,68},{70,70},{100,100},{102,102}}, null, null), null, null);
      tmp[2] = new ListStackNode<IConstructor>(-2789, 2, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(-2788, 0, new int[][]{{48,57}}, null, null), false, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(-2787, 1, prod__lit___46__char_class___range__46_46_, new int[] {46}, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {46})});
      tmp[0] = new ListStackNode<IConstructor>(-2784, 0, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2783, 0, new int[][]{{48,57}}, null, null), true, null, null);
      builder.addAlternative(RascalRascal.prod__$RealLiteral__iter__char_class___range__48_57_lit___46_iter_star__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_, tmp);
	}
    protected static final void _init_prod__$RealLiteral__lit___46_iter__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[6];
      
      tmp[5] = new OptionalStackNode<IConstructor>(-2805, 5, regular__opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102, new CharStackNode<IConstructor>(-2804, 0, new int[][]{{68,68},{70,70},{100,100},{102,102}}, null, null), null, null);
      tmp[4] = new ListStackNode<IConstructor>(-2803, 4, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2802, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[3] = new OptionalStackNode<IConstructor>(-2801, 3, regular__opt__char_class___range__43_43_range__45_45, new CharStackNode<IConstructor>(-2800, 0, new int[][]{{43,43},{45,45}}, null, null), null, null);
      tmp[2] = new CharStackNode<IConstructor>(-2799, 2, new int[][]{{69,69},{101,101}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-2798, 1, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(-2797, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2796, 0, prod__lit___46__char_class___range__46_46_, new int[] {46}, new IEnterFilter[] {new CharPrecedeRestriction(new int[][]{{46,46}})}, null);
      builder.addAlternative(RascalRascal.prod__$RealLiteral__lit___46_iter__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$RealLiteral__iter__char_class___range__48_57_char_class___range__68_68_range__70_70_range__100_100_range__102_102_(builder);
      
        _init_prod__$RealLiteral__iter__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(builder);
      
        _init_prod__$RealLiteral__lit___46_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(builder);
      
        _init_prod__$RealLiteral__iter__char_class___range__48_57_lit___46_iter_star__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(builder);
      
        _init_prod__$RealLiteral__iter__char_class___range__48_57_lit___46_iter_star__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(builder);
      
        _init_prod__$RealLiteral__lit___46_iter__char_class___range__48_57_char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_opt__char_class___range__68_68_range__70_70_range__100_100_range__102_102_(builder);
      
    }
  }
	
  protected static class $layouts_$default$ {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$layouts_$default$__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(-2826, 0);
      builder.addAlternative(RascalRascal.prod__$layouts_$default$__, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$layouts_$default$__(builder);
      
    }
  }
	
  protected static class $Replacement {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__unconditional_$Replacement__replacementExpression_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2840, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__unconditional_$Replacement__replacementExpression_$Expression_, tmp);
	}
    protected static final void _init_prod__conditional_$Replacement__replacementExpression_$Expression_$layouts_LAYOUTLIST_lit_when_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new SeparatedListStackNode<IConstructor>(-2860, 4, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-2854, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-2856, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-2857, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-2859, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-2852, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-2850, 2, prod__lit_when__char_class___range__119_119_char_class___range__104_104_char_class___range__101_101_char_class___range__110_110_, new int[] {119,104,101,110}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2849, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2846, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__conditional_$Replacement__replacementExpression_$Expression_$layouts_LAYOUTLIST_lit_when_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__unconditional_$Replacement__replacementExpression_$Expression_(builder);
      
        _init_prod__conditional_$Replacement__replacementExpression_$Expression_$layouts_LAYOUTLIST_lit_when_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $Assoc {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__right_$Assoc__lit_right_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2905, 0, prod__lit_right__char_class___range__114_114_char_class___range__105_105_char_class___range__103_103_char_class___range__104_104_char_class___range__116_116_, new int[] {114,105,103,104,116}, null, null);
      builder.addAlternative(RascalRascal.prod__right_$Assoc__lit_right_, tmp);
	}
    protected static final void _init_prod__nonAssociative_$Assoc__lit_non_assoc_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2909, 0, prod__lit_non_assoc__char_class___range__110_110_char_class___range__111_111_char_class___range__110_110_char_class___range__45_45_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__111_111_char_class___range__99_99_, new int[] {110,111,110,45,97,115,115,111,99}, null, null);
      builder.addAlternative(RascalRascal.prod__nonAssociative_$Assoc__lit_non_assoc_, tmp);
	}
    protected static final void _init_prod__left_$Assoc__lit_left_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2913, 0, prod__lit_left__char_class___range__108_108_char_class___range__101_101_char_class___range__102_102_char_class___range__116_116_, new int[] {108,101,102,116}, null, null);
      builder.addAlternative(RascalRascal.prod__left_$Assoc__lit_left_, tmp);
	}
    protected static final void _init_prod__associative_$Assoc__lit_assoc_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-2917, 0, prod__lit_assoc__char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__111_111_char_class___range__99_99_, new int[] {97,115,115,111,99}, null, null);
      builder.addAlternative(RascalRascal.prod__associative_$Assoc__lit_assoc_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__right_$Assoc__lit_right_(builder);
      
        _init_prod__nonAssociative_$Assoc__lit_non_assoc_(builder);
      
        _init_prod__left_$Assoc__lit_left_(builder);
      
        _init_prod__associative_$Assoc__lit_assoc_(builder);
      
    }
  }
	
  protected static class $RegExpModifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$RegExpModifier__iter_star__char_class___range__100_100_range__105_105_range__109_109_range__115_115_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new ListStackNode<IConstructor>(-2930, 0, regular__iter_star__char_class___range__100_100_range__105_105_range__109_109_range__115_115, new CharStackNode<IConstructor>(-2929, 0, new int[][]{{100,100},{105,105},{109,109},{115,115}}, null, null), false, null, null);
      builder.addAlternative(RascalRascal.prod__$RegExpModifier__iter_star__char_class___range__100_100_range__105_105_range__109_109_range__115_115_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$RegExpModifier__iter_star__char_class___range__100_100_range__105_105_range__109_109_range__115_115_(builder);
      
    }
  }
	
  protected static class $MidStringChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MidStringChars__char_class___range__62_62_iter_star__$StringCharacter_char_class___range__60_60__tag__category___67_111_110_115_116_97_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new CharStackNode<IConstructor>(-2941, 2, new int[][]{{60,60}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-2940, 1, regular__iter_star__$StringCharacter, new NonTerminalStackNode<IConstructor>(-2939, 0, "$StringCharacter", null, null), false, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-2937, 0, new int[][]{{62,62}}, null, null);
      builder.addAlternative(RascalRascal.prod__$MidStringChars__char_class___range__62_62_iter_star__$StringCharacter_char_class___range__60_60__tag__category___67_111_110_115_116_97_110_116, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$MidStringChars__char_class___range__62_62_iter_star__$StringCharacter_char_class___range__60_60__tag__category___67_111_110_115_116_97_110_116(builder);
      
    }
  }
	
  protected static class $RegExp {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$RegExp__char_class___range__0_46_range__48_59_range__61_61_range__63_91_range__93_16777215_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(-2948, 0, new int[][]{{0,46},{48,59},{61,61},{63,91},{93,16777215}}, null, null);
      builder.addAlternative(RascalRascal.prod__$RegExp__char_class___range__0_46_range__48_59_range__61_61_range__63_91_range__93_16777215_, tmp);
	}
    protected static final void _init_prod__$RegExp__lit___60_$Name_lit___58_iter_star__$NamedRegExp_lit___62_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-2958, 4, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(-2957, 3, regular__iter_star__$NamedRegExp, new NonTerminalStackNode<IConstructor>(-2956, 0, "$NamedRegExp", null, null), false, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-2954, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2953, 1, "$Name", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2951, 0, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      builder.addAlternative(RascalRascal.prod__$RegExp__lit___60_$Name_lit___58_iter_star__$NamedRegExp_lit___62_, tmp);
	}
    protected static final void _init_prod__$RegExp__lit___60_$Name_lit___62_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-2964, 2, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-2963, 1, "$Name", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-2961, 0, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      builder.addAlternative(RascalRascal.prod__$RegExp__lit___60_$Name_lit___62_, tmp);
	}
    protected static final void _init_prod__$RegExp__char_class___range__92_92_char_class___range__47_47_range__60_60_range__62_62_range__92_92_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new CharStackNode<IConstructor>(-2968, 1, new int[][]{{47,47},{60,60},{62,62},{92,92}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-2967, 0, new int[][]{{92,92}}, null, null);
      builder.addAlternative(RascalRascal.prod__$RegExp__char_class___range__92_92_char_class___range__47_47_range__60_60_range__62_62_range__92_92_, tmp);
	}
    protected static final void _init_prod__$RegExp__$Backslash_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-2972, 0, "$Backslash", null, null);
      builder.addAlternative(RascalRascal.prod__$RegExp__$Backslash_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$RegExp__char_class___range__0_46_range__48_59_range__61_61_range__63_91_range__93_16777215_(builder);
      
        _init_prod__$RegExp__lit___60_$Name_lit___58_iter_star__$NamedRegExp_lit___62_(builder);
      
        _init_prod__$RegExp__lit___60_$Name_lit___62_(builder);
      
        _init_prod__$RegExp__char_class___range__92_92_char_class___range__47_47_range__60_60_range__62_62_range__92_92_(builder);
      
        _init_prod__$RegExp__$Backslash_(builder);
      
    }
  }
	
  protected static class $PathPart {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__nonInterpolated_$PathPart__pathChars_$PathChars_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3017, 0, "$PathChars", null, null);
      builder.addAlternative(RascalRascal.prod__nonInterpolated_$PathPart__pathChars_$PathChars_, tmp);
	}
    protected static final void _init_prod__interpolated_$PathPart__pre_$PrePathChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$PathTail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-3033, 4, "$PathTail", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3031, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-3028, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3026, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3023, 0, "$PrePathChars", null, null);
      builder.addAlternative(RascalRascal.prod__interpolated_$PathPart__pre_$PrePathChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$PathTail_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__nonInterpolated_$PathPart__pathChars_$PathChars_(builder);
      
        _init_prod__interpolated_$PathPart__pre_$PrePathChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$PathTail_(builder);
      
    }
  }
	
  protected static class $StringTemplate {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__doWhile_$StringTemplate__lit_do_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[19];
      
      tmp[18] = new LiteralStackNode<IConstructor>(-3128, 18, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[17] = new NonTerminalStackNode<IConstructor>(-3127, 17, "$layouts_LAYOUTLIST", null, null);
      tmp[16] = new NonTerminalStackNode<IConstructor>(-3124, 16, "$Expression", null, null);
      tmp[15] = new NonTerminalStackNode<IConstructor>(-3122, 15, "$layouts_LAYOUTLIST", null, null);
      tmp[14] = new LiteralStackNode<IConstructor>(-3120, 14, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-3119, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new LiteralStackNode<IConstructor>(-3117, 12, prod__lit_while__char_class___range__119_119_char_class___range__104_104_char_class___range__105_105_char_class___range__108_108_char_class___range__101_101_, new int[] {119,104,105,108,101}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-3116, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(-3114, 10, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-3113, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new SeparatedListStackNode<IConstructor>(-3110, 8, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3107, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3109, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3105, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-3102, 6, "$StringMiddle", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3100, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-3097, 4, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3094, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3096, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3092, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3090, 2, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3089, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3087, 0, prod__lit_do__char_class___range__100_100_char_class___range__111_111_, new int[] {100,111}, null, null);
      builder.addAlternative(RascalRascal.prod__doWhile_$StringTemplate__lit_do_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__while_$StringTemplate__lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[17];
      
      tmp[16] = new LiteralStackNode<IConstructor>(-3170, 16, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[15] = new NonTerminalStackNode<IConstructor>(-3169, 15, "$layouts_LAYOUTLIST", null, null);
      tmp[14] = new SeparatedListStackNode<IConstructor>(-3166, 14, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3163, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3165, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-3161, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new NonTerminalStackNode<IConstructor>(-3158, 12, "$StringMiddle", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-3156, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new SeparatedListStackNode<IConstructor>(-3153, 10, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3150, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3152, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-3148, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-3146, 8, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3145, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-3143, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3142, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-3139, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3137, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3135, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3134, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3132, 0, prod__lit_while__char_class___range__119_119_char_class___range__104_104_char_class___range__105_105_char_class___range__108_108_char_class___range__101_101_, new int[] {119,104,105,108,101}, null, null);
      builder.addAlternative(RascalRascal.prod__while_$StringTemplate__lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__ifThenElse_$StringTemplate__lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStatsThen_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_thenString_$StringMiddle_$layouts_LAYOUTLIST_postStatsThen_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit_else_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStatsElse_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_elseString_$StringMiddle_$layouts_LAYOUTLIST_postStatsElse_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[29];
      
      tmp[28] = new LiteralStackNode<IConstructor>(-3296, 28, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[27] = new NonTerminalStackNode<IConstructor>(-3295, 27, "$layouts_LAYOUTLIST", null, null);
      tmp[26] = new SeparatedListStackNode<IConstructor>(-3292, 26, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3289, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3291, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[25] = new NonTerminalStackNode<IConstructor>(-3287, 25, "$layouts_LAYOUTLIST", null, null);
      tmp[24] = new NonTerminalStackNode<IConstructor>(-3284, 24, "$StringMiddle", null, null);
      tmp[23] = new NonTerminalStackNode<IConstructor>(-3282, 23, "$layouts_LAYOUTLIST", null, null);
      tmp[22] = new SeparatedListStackNode<IConstructor>(-3279, 22, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3276, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3278, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[21] = new NonTerminalStackNode<IConstructor>(-3274, 21, "$layouts_LAYOUTLIST", null, null);
      tmp[20] = new LiteralStackNode<IConstructor>(-3272, 20, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[19] = new NonTerminalStackNode<IConstructor>(-3271, 19, "$layouts_LAYOUTLIST", null, null);
      tmp[18] = new LiteralStackNode<IConstructor>(-3269, 18, prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {101,108,115,101}, null, null);
      tmp[17] = new NonTerminalStackNode<IConstructor>(-3268, 17, "$layouts_LAYOUTLIST", null, null);
      tmp[16] = new LiteralStackNode<IConstructor>(-3266, 16, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[15] = new NonTerminalStackNode<IConstructor>(-3265, 15, "$layouts_LAYOUTLIST", null, null);
      tmp[14] = new SeparatedListStackNode<IConstructor>(-3262, 14, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3259, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3261, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-3257, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new NonTerminalStackNode<IConstructor>(-3254, 12, "$StringMiddle", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-3252, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new SeparatedListStackNode<IConstructor>(-3249, 10, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3246, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3248, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-3244, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-3242, 8, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3241, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-3239, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3238, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-3235, 4, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3229, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3231, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-3232, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-3234, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3227, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3225, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3224, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3222, 0, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      builder.addAlternative(RascalRascal.prod__ifThenElse_$StringTemplate__lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStatsThen_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_thenString_$StringMiddle_$layouts_LAYOUTLIST_postStatsThen_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit_else_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStatsElse_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_elseString_$StringMiddle_$layouts_LAYOUTLIST_postStatsElse_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__for_$StringTemplate__lit_for_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[17];
      
      tmp[16] = new LiteralStackNode<IConstructor>(-3218, 16, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[15] = new NonTerminalStackNode<IConstructor>(-3217, 15, "$layouts_LAYOUTLIST", null, null);
      tmp[14] = new SeparatedListStackNode<IConstructor>(-3214, 14, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3211, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3213, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-3209, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new NonTerminalStackNode<IConstructor>(-3206, 12, "$StringMiddle", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-3204, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new SeparatedListStackNode<IConstructor>(-3201, 10, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3198, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3200, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-3196, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-3194, 8, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3193, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-3191, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3190, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-3187, 4, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3181, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3183, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-3184, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-3186, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3179, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3177, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3176, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3174, 0, prod__lit_for__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_, new int[] {102,111,114}, null, null);
      builder.addAlternative(RascalRascal.prod__for_$StringTemplate__lit_for_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__ifThen_$StringTemplate__lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[17];
      
      tmp[16] = new LiteralStackNode<IConstructor>(-3344, 16, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[15] = new NonTerminalStackNode<IConstructor>(-3343, 15, "$layouts_LAYOUTLIST", null, null);
      tmp[14] = new SeparatedListStackNode<IConstructor>(-3340, 14, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3337, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3339, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-3335, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new NonTerminalStackNode<IConstructor>(-3332, 12, "$StringMiddle", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-3330, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new SeparatedListStackNode<IConstructor>(-3327, 10, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3324, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3326, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-3322, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-3320, 8, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3319, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-3317, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3316, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-3313, 4, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3307, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3309, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-3310, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-3312, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3305, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3303, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3302, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3300, 0, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      builder.addAlternative(RascalRascal.prod__ifThen_$StringTemplate__lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__doWhile_$StringTemplate__lit_do_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__while_$StringTemplate__lit_while_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_condition_$Expression_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__ifThenElse_$StringTemplate__lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStatsThen_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_thenString_$StringMiddle_$layouts_LAYOUTLIST_postStatsThen_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit_else_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStatsElse_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_elseString_$StringMiddle_$layouts_LAYOUTLIST_postStatsElse_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__for_$StringTemplate__lit_for_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__ifThen_$StringTemplate__lit_if_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_preStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_body_$StringMiddle_$layouts_LAYOUTLIST_postStats_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
    }
  }
	
  protected static class $DecimalIntegerLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$DecimalIntegerLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new ListStackNode<IConstructor>(-3355, 1, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(-3352, 0, new int[][]{{48,57}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      tmp[0] = new CharStackNode<IConstructor>(-3351, 0, new int[][]{{49,57}}, null, null);
      builder.addAlternative(RascalRascal.prod__$DecimalIntegerLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_, tmp);
	}
    protected static final void _init_prod__$DecimalIntegerLiteral__lit_0_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3360, 0, prod__lit_0__char_class___range__48_48_, new int[] {48}, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      builder.addAlternative(RascalRascal.prod__$DecimalIntegerLiteral__lit_0_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$DecimalIntegerLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_(builder);
      
        _init_prod__$DecimalIntegerLiteral__lit_0_(builder);
      
    }
  }
	
  protected static class $LAYOUT {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$LAYOUT__$Comment_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3396, 0, "$Comment", null, null);
      builder.addAlternative(RascalRascal.prod__$LAYOUT__$Comment_, tmp);
	}
    protected static final void _init_prod__$LAYOUT__char_class___range__9_13_range__32_32_range__133_133_range__160_160_range__5760_5760_range__6158_6158_range__8192_8202_range__8232_8233_range__8239_8239_range__8287_8287_range__12288_12288_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(-3399, 0, new int[][]{{9,13},{32,32},{133,133},{160,160},{5760,5760},{6158,6158},{8192,8202},{8232,8233},{8239,8239},{8287,8287},{12288,12288}}, null, null);
      builder.addAlternative(RascalRascal.prod__$LAYOUT__char_class___range__9_13_range__32_32_range__133_133_range__160_160_range__5760_5760_range__6158_6158_range__8192_8202_range__8232_8233_range__8239_8239_range__8287_8287_range__12288_12288_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$LAYOUT__$Comment_(builder);
      
        _init_prod__$LAYOUT__char_class___range__9_13_range__32_32_range__133_133_range__160_160_range__5760_5760_range__6158_6158_range__8192_8202_range__8232_8233_range__8239_8239_range__8287_8287_range__12288_12288_(builder);
      
    }
  }
	
  protected static class $Body {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__toplevels_$Body__toplevels_iter_star_seps__$Toplevel__$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SeparatedListStackNode<IConstructor>(-3387, 0, regular__iter_star_seps__$Toplevel__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3384, 0, "$Toplevel", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3386, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      builder.addAlternative(RascalRascal.prod__toplevels_$Body__toplevels_iter_star_seps__$Toplevel__$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__toplevels_$Body__toplevels_iter_star_seps__$Toplevel__$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $Import {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__syntax_$Import__syntax_$SyntaxDefinition_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3443, 0, "$SyntaxDefinition", null, null);
      builder.addAlternative(RascalRascal.prod__syntax_$Import__syntax_$SyntaxDefinition_, tmp);
	}
    protected static final void _init_prod__default_$Import__lit_import_$layouts_LAYOUTLIST_module_$ImportedModule_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-3456, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3455, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-3452, 2, "$ImportedModule", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3450, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3448, 0, prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_, new int[] {105,109,112,111,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Import__lit_import_$layouts_LAYOUTLIST_module_$ImportedModule_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__extend_$Import__lit_extend_$layouts_LAYOUTLIST_module_$ImportedModule_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-3468, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3467, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-3464, 2, "$ImportedModule", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3462, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3460, 0, prod__lit_extend__char_class___range__101_101_char_class___range__120_120_char_class___range__116_116_char_class___range__101_101_char_class___range__110_110_char_class___range__100_100_, new int[] {101,120,116,101,110,100}, null, null);
      builder.addAlternative(RascalRascal.prod__extend_$Import__lit_extend_$layouts_LAYOUTLIST_module_$ImportedModule_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__external_$Import__lit_import_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_at_$LocationLiteral_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-3488, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3487, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-3484, 6, "$LocationLiteral", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3482, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-3480, 4, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3479, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-3476, 2, "$QualifiedName", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3474, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3472, 0, prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_, new int[] {105,109,112,111,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__external_$Import__lit_import_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_at_$LocationLiteral_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__syntax_$Import__syntax_$SyntaxDefinition_(builder);
      
        _init_prod__default_$Import__lit_import_$layouts_LAYOUTLIST_module_$ImportedModule_$layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__extend_$Import__lit_extend_$layouts_LAYOUTLIST_module_$ImportedModule_$layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__external_$Import__lit_import_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_at_$LocationLiteral_$layouts_LAYOUTLIST_lit___59_(builder);
      
    }
  }
	
  protected static class $UserType {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__parametric_$UserType__name_$QualifiedName_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-3428, 6, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3427, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-3424, 4, regular__iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3418, 0, "$Type", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3420, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-3421, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-3423, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3416, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3414, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3413, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3411, 0, "$QualifiedName", null, new ICompletionFilter[] {new StringFollowRequirement(new int[] {91})});
      builder.addAlternative(RascalRascal.prod__parametric_$UserType__name_$QualifiedName_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__name_$UserType__name_$QualifiedName_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3433, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__name_$UserType__name_$QualifiedName_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__parametric_$UserType__name_$QualifiedName_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__name_$UserType__name_$QualifiedName_(builder);
      
    }
  }
	
  protected static class $FunctionBody {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$FunctionBody__lit___123_$layouts_LAYOUTLIST_statements_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-3514, 4, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3513, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-3510, 2, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3507, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3509, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3505, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3503, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$FunctionBody__lit___123_$layouts_LAYOUTLIST_statements_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$FunctionBody__lit___123_$layouts_LAYOUTLIST_statements_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
    }
  }
	
  protected static class start__$Command {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__start__$Command__$layouts_LAYOUTLIST_top_$Command_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-3540, 2, "$layouts_LAYOUTLIST", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3537, 1, "$Command", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3535, 0, "$layouts_LAYOUTLIST", null, null);
      builder.addAlternative(RascalRascal.prod__start__$Command__$layouts_LAYOUTLIST_top_$Command_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__start__$Command__$layouts_LAYOUTLIST_top_$Command_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $IntegerLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__decimalIntegerLiteral_$IntegerLiteral__decimal_$DecimalIntegerLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3555, 0, "$DecimalIntegerLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__decimalIntegerLiteral_$IntegerLiteral__decimal_$DecimalIntegerLiteral_, tmp);
	}
    protected static final void _init_prod__octalIntegerLiteral_$IntegerLiteral__octal_$OctalIntegerLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3561, 0, "$OctalIntegerLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__octalIntegerLiteral_$IntegerLiteral__octal_$OctalIntegerLiteral_, tmp);
	}
    protected static final void _init_prod__hexIntegerLiteral_$IntegerLiteral__hex_$HexIntegerLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3567, 0, "$HexIntegerLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__hexIntegerLiteral_$IntegerLiteral__hex_$HexIntegerLiteral_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__decimalIntegerLiteral_$IntegerLiteral__decimal_$DecimalIntegerLiteral_(builder);
      
        _init_prod__octalIntegerLiteral_$IntegerLiteral__octal_$OctalIntegerLiteral_(builder);
      
        _init_prod__hexIntegerLiteral_$IntegerLiteral__hex_$HexIntegerLiteral_(builder);
      
    }
  }
	
  protected static class $Target {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__labeled_$Target__name_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3577, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__labeled_$Target__name_$Name_, tmp);
	}
    protected static final void _init_prod__empty_$Target__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(-3581, 0);
      builder.addAlternative(RascalRascal.prod__empty_$Target__, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__labeled_$Target__name_$Name_(builder);
      
        _init_prod__empty_$Target__(builder);
      
    }
  }
	
  protected static class $SyntaxDefinition {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__language_$SyntaxDefinition__start_$Start_$layouts_LAYOUTLIST_lit_syntax_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(-3629, 10, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-3628, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-3625, 8, "$Prod", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3623, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-3621, 6, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3620, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-3617, 4, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3615, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3613, 2, prod__lit_syntax__char_class___range__115_115_char_class___range__121_121_char_class___range__110_110_char_class___range__116_116_char_class___range__97_97_char_class___range__120_120_, new int[] {115,121,110,116,97,120}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3612, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3609, 0, "$Start", null, null);
      builder.addAlternative(RascalRascal.prod__language_$SyntaxDefinition__start_$Start_$layouts_LAYOUTLIST_lit_syntax_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable, tmp);
	}
    protected static final void _init_prod__keyword_$SyntaxDefinition__lit_keyword_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-3649, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3648, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-3645, 6, "$Prod", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3643, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-3641, 4, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3640, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-3637, 2, "$Sym", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3635, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3633, 0, prod__lit_keyword__char_class___range__107_107_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_, new int[] {107,101,121,119,111,114,100}, null, null);
      builder.addAlternative(RascalRascal.prod__keyword_$SyntaxDefinition__lit_keyword_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable, tmp);
	}
    protected static final void _init_prod__lexical_$SyntaxDefinition__lit_lexical_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-3669, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3668, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-3665, 6, "$Prod", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3663, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-3661, 4, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3660, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-3657, 2, "$Sym", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3655, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3653, 0, prod__lit_lexical__char_class___range__108_108_char_class___range__101_101_char_class___range__120_120_char_class___range__105_105_char_class___range__99_99_char_class___range__97_97_char_class___range__108_108_, new int[] {108,101,120,105,99,97,108}, null, null);
      builder.addAlternative(RascalRascal.prod__lexical_$SyntaxDefinition__lit_lexical_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable, tmp);
	}
    protected static final void _init_prod__layout_$SyntaxDefinition__vis_$Visibility_$layouts_LAYOUTLIST_lit_layout_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(-3694, 10, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-3693, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-3690, 8, "$Prod", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-3688, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-3686, 6, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-3685, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-3682, 4, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3680, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3678, 2, prod__lit_layout__char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_, new int[] {108,97,121,111,117,116}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3677, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3674, 0, "$Visibility", null, null);
      builder.addAlternative(RascalRascal.prod__layout_$SyntaxDefinition__vis_$Visibility_$layouts_LAYOUTLIST_lit_layout_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__language_$SyntaxDefinition__start_$Start_$layouts_LAYOUTLIST_lit_syntax_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable(builder);
      
        _init_prod__keyword_$SyntaxDefinition__lit_keyword_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable(builder);
      
        _init_prod__lexical_$SyntaxDefinition__lit_lexical_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable(builder);
      
        _init_prod__layout_$SyntaxDefinition__vis_$Visibility_$layouts_LAYOUTLIST_lit_layout_$layouts_LAYOUTLIST_defined_$Sym_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_production_$Prod_$layouts_LAYOUTLIST_lit___59__tag__Foldable(builder);
      
    }
  }
	
  protected static class $Kind {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__all_$Kind__lit_all_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3702, 0, prod__lit_all__char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_, new int[] {97,108,108}, null, null);
      builder.addAlternative(RascalRascal.prod__all_$Kind__lit_all_, tmp);
	}
    protected static final void _init_prod__module_$Kind__lit_module_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3706, 0, prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_, new int[] {109,111,100,117,108,101}, null, null);
      builder.addAlternative(RascalRascal.prod__module_$Kind__lit_module_, tmp);
	}
    protected static final void _init_prod__variable_$Kind__lit_variable_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3710, 0, prod__lit_variable__char_class___range__118_118_char_class___range__97_97_char_class___range__114_114_char_class___range__105_105_char_class___range__97_97_char_class___range__98_98_char_class___range__108_108_char_class___range__101_101_, new int[] {118,97,114,105,97,98,108,101}, null, null);
      builder.addAlternative(RascalRascal.prod__variable_$Kind__lit_variable_, tmp);
	}
    protected static final void _init_prod__anno_$Kind__lit_anno_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3714, 0, prod__lit_anno__char_class___range__97_97_char_class___range__110_110_char_class___range__110_110_char_class___range__111_111_, new int[] {97,110,110,111}, null, null);
      builder.addAlternative(RascalRascal.prod__anno_$Kind__lit_anno_, tmp);
	}
    protected static final void _init_prod__data_$Kind__lit_data_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3718, 0, prod__lit_data__char_class___range__100_100_char_class___range__97_97_char_class___range__116_116_char_class___range__97_97_, new int[] {100,97,116,97}, null, null);
      builder.addAlternative(RascalRascal.prod__data_$Kind__lit_data_, tmp);
	}
    protected static final void _init_prod__tag_$Kind__lit_tag_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3722, 0, prod__lit_tag__char_class___range__116_116_char_class___range__97_97_char_class___range__103_103_, new int[] {116,97,103}, null, null);
      builder.addAlternative(RascalRascal.prod__tag_$Kind__lit_tag_, tmp);
	}
    protected static final void _init_prod__function_$Kind__lit_function_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3726, 0, prod__lit_function__char_class___range__102_102_char_class___range__117_117_char_class___range__110_110_char_class___range__99_99_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_, new int[] {102,117,110,99,116,105,111,110}, null, null);
      builder.addAlternative(RascalRascal.prod__function_$Kind__lit_function_, tmp);
	}
    protected static final void _init_prod__view_$Kind__lit_view_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3730, 0, prod__lit_view__char_class___range__118_118_char_class___range__105_105_char_class___range__101_101_char_class___range__119_119_, new int[] {118,105,101,119}, null, null);
      builder.addAlternative(RascalRascal.prod__view_$Kind__lit_view_, tmp);
	}
    protected static final void _init_prod__alias_$Kind__lit_alias_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3734, 0, prod__lit_alias__char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__97_97_char_class___range__115_115_, new int[] {97,108,105,97,115}, null, null);
      builder.addAlternative(RascalRascal.prod__alias_$Kind__lit_alias_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__all_$Kind__lit_all_(builder);
      
        _init_prod__module_$Kind__lit_module_(builder);
      
        _init_prod__variable_$Kind__lit_variable_(builder);
      
        _init_prod__anno_$Kind__lit_anno_(builder);
      
        _init_prod__data_$Kind__lit_data_(builder);
      
        _init_prod__tag_$Kind__lit_tag_(builder);
      
        _init_prod__function_$Kind__lit_function_(builder);
      
        _init_prod__view_$Kind__lit_view_(builder);
      
        _init_prod__alias_$Kind__lit_alias_(builder);
      
    }
  }
	
  protected static class $Mapping__$Expression {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Mapping__$Expression__from_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-3785, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3783, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-3781, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3780, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3777, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__default_$Mapping__$Expression__from_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Expression_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Mapping__$Expression__from_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Expression_(builder);
      
    }
  }
	
  protected static class $PostProtocolChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$PostProtocolChars__lit___62_$URLChars_lit___58_47_47_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-3841, 2, prod__lit___58_47_47__char_class___range__58_58_char_class___range__47_47_char_class___range__47_47_, new int[] {58,47,47}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3840, 1, "$URLChars", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3838, 0, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      builder.addAlternative(RascalRascal.prod__$PostProtocolChars__lit___62_$URLChars_lit___58_47_47_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$PostProtocolChars__lit___62_$URLChars_lit___58_47_47_(builder);
      
    }
  }
	
  protected static class $MidPathChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MidPathChars__lit___62_$URLChars_lit___60_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-3894, 2, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3893, 1, "$URLChars", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3891, 0, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      builder.addAlternative(RascalRascal.prod__$MidPathChars__lit___62_$URLChars_lit___60_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$MidPathChars__lit___62_$URLChars_lit___60_(builder);
      
    }
  }
	
  protected static class $Char {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$Char__$UnicodeEscape__tag__category___67_111_110_115_116_97_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3902, 0, "$UnicodeEscape", null, null);
      builder.addAlternative(RascalRascal.prod__$Char__$UnicodeEscape__tag__category___67_111_110_115_116_97_110_116, tmp);
	}
    protected static final void _init_prod__$Char__char_class___range__0_31_range__33_33_range__35_38_range__40_44_range__46_59_range__61_61_range__63_90_range__94_16777215__tag__category___67_111_110_115_116_97_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(-3905, 0, new int[][]{{0,31},{33,33},{35,38},{40,44},{46,59},{61,61},{63,90},{94,16777215}}, null, null);
      builder.addAlternative(RascalRascal.prod__$Char__char_class___range__0_31_range__33_33_range__35_38_range__40_44_range__46_59_range__61_61_range__63_90_range__94_16777215__tag__category___67_111_110_115_116_97_110_116, tmp);
	}
    protected static final void _init_prod__$Char__lit___92_char_class___range__32_32_range__34_34_range__39_39_range__45_45_range__60_60_range__62_62_range__91_93_range__98_98_range__102_102_range__110_110_range__114_114_range__116_116__tag__category___67_111_110_115_116_97_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new CharStackNode<IConstructor>(-3909, 1, new int[][]{{32,32},{34,34},{39,39},{45,45},{60,60},{62,62},{91,93},{98,98},{102,102},{110,110},{114,114},{116,116}}, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3908, 0, prod__lit___92__char_class___range__92_92_, new int[] {92}, null, null);
      builder.addAlternative(RascalRascal.prod__$Char__lit___92_char_class___range__32_32_range__34_34_range__39_39_range__45_45_range__60_60_range__62_62_range__91_93_range__98_98_range__102_102_range__110_110_range__114_114_range__116_116__tag__category___67_111_110_115_116_97_110_116, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$Char__$UnicodeEscape__tag__category___67_111_110_115_116_97_110_116(builder);
      
        _init_prod__$Char__char_class___range__0_31_range__33_33_range__35_38_range__40_44_range__46_59_range__61_61_range__63_90_range__94_16777215__tag__category___67_111_110_115_116_97_110_116(builder);
      
        _init_prod__$Char__lit___92_char_class___range__32_32_range__34_34_range__39_39_range__45_45_range__60_60_range__62_62_range__91_93_range__98_98_range__102_102_range__110_110_range__114_114_range__116_116__tag__category___67_111_110_115_116_97_110_116(builder);
      
    }
  }
	
  protected static class $PrePathChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$PrePathChars__$URLChars_lit___60_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new LiteralStackNode<IConstructor>(-3918, 1, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-3917, 0, "$URLChars", null, null);
      builder.addAlternative(RascalRascal.prod__$PrePathChars__$URLChars_lit___60_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$PrePathChars__$URLChars_lit___60_(builder);
      
    }
  }
	
  protected static class $Strategy {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__bottomUpBreak_$Strategy__lit_bottom_up_break_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3941, 0, prod__lit_bottom_up_break__char_class___range__98_98_char_class___range__111_111_char_class___range__116_116_char_class___range__116_116_char_class___range__111_111_char_class___range__109_109_char_class___range__45_45_char_class___range__117_117_char_class___range__112_112_char_class___range__45_45_char_class___range__98_98_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__107_107_, new int[] {98,111,116,116,111,109,45,117,112,45,98,114,101,97,107}, null, null);
      builder.addAlternative(RascalRascal.prod__bottomUpBreak_$Strategy__lit_bottom_up_break_, tmp);
	}
    protected static final void _init_prod__outermost_$Strategy__lit_outermost_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3945, 0, prod__lit_outermost__char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__109_109_char_class___range__111_111_char_class___range__115_115_char_class___range__116_116_, new int[] {111,117,116,101,114,109,111,115,116}, null, null);
      builder.addAlternative(RascalRascal.prod__outermost_$Strategy__lit_outermost_, tmp);
	}
    protected static final void _init_prod__bottomUp_$Strategy__lit_bottom_up_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3949, 0, prod__lit_bottom_up__char_class___range__98_98_char_class___range__111_111_char_class___range__116_116_char_class___range__116_116_char_class___range__111_111_char_class___range__109_109_char_class___range__45_45_char_class___range__117_117_char_class___range__112_112_, new int[] {98,111,116,116,111,109,45,117,112}, null, null);
      builder.addAlternative(RascalRascal.prod__bottomUp_$Strategy__lit_bottom_up_, tmp);
	}
    protected static final void _init_prod__innermost_$Strategy__lit_innermost_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3953, 0, prod__lit_innermost__char_class___range__105_105_char_class___range__110_110_char_class___range__110_110_char_class___range__101_101_char_class___range__114_114_char_class___range__109_109_char_class___range__111_111_char_class___range__115_115_char_class___range__116_116_, new int[] {105,110,110,101,114,109,111,115,116}, null, null);
      builder.addAlternative(RascalRascal.prod__innermost_$Strategy__lit_innermost_, tmp);
	}
    protected static final void _init_prod__topDownBreak_$Strategy__lit_top_down_break_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3957, 0, prod__lit_top_down_break__char_class___range__116_116_char_class___range__111_111_char_class___range__112_112_char_class___range__45_45_char_class___range__100_100_char_class___range__111_111_char_class___range__119_119_char_class___range__110_110_char_class___range__45_45_char_class___range__98_98_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__107_107_, new int[] {116,111,112,45,100,111,119,110,45,98,114,101,97,107}, null, null);
      builder.addAlternative(RascalRascal.prod__topDownBreak_$Strategy__lit_top_down_break_, tmp);
	}
    protected static final void _init_prod__topDown_$Strategy__lit_top_down_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-3961, 0, prod__lit_top_down__char_class___range__116_116_char_class___range__111_111_char_class___range__112_112_char_class___range__45_45_char_class___range__100_100_char_class___range__111_111_char_class___range__119_119_char_class___range__110_110_, new int[] {116,111,112,45,100,111,119,110}, null, null);
      builder.addAlternative(RascalRascal.prod__topDown_$Strategy__lit_top_down_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__bottomUpBreak_$Strategy__lit_bottom_up_break_(builder);
      
        _init_prod__outermost_$Strategy__lit_outermost_(builder);
      
        _init_prod__bottomUp_$Strategy__lit_bottom_up_(builder);
      
        _init_prod__innermost_$Strategy__lit_innermost_(builder);
      
        _init_prod__topDownBreak_$Strategy__lit_top_down_break_(builder);
      
        _init_prod__topDown_$Strategy__lit_top_down_(builder);
      
    }
  }
	
  protected static class $ModuleParameters {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$ModuleParameters__lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$TypeVar__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-4000, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-3999, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-3996, 2, regular__iter_seps__$TypeVar__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-3990, 0, "$TypeVar", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-3992, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-3993, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-3995, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-3988, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-3986, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$ModuleParameters__lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$TypeVar__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$ModuleParameters__lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$TypeVar__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
    }
  }
	
  protected static class $RascalKeywords {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$RascalKeywords__lit_solve_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4016, 0, prod__lit_solve__char_class___range__115_115_char_class___range__111_111_char_class___range__108_108_char_class___range__118_118_char_class___range__101_101_, new int[] {115,111,108,118,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_solve_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_rat_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4013, 0, prod__lit_rat__char_class___range__114_114_char_class___range__97_97_char_class___range__116_116_, new int[] {114,97,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_rat_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_break_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4019, 0, prod__lit_break__char_class___range__98_98_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__107_107_, new int[] {98,114,101,97,107}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_break_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_rel_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4022, 0, prod__lit_rel__char_class___range__114_114_char_class___range__101_101_char_class___range__108_108_, new int[] {114,101,108}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_rel_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_false_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4025, 0, prod__lit_false__char_class___range__102_102_char_class___range__97_97_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {102,97,108,115,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_false_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_any_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4028, 0, prod__lit_any__char_class___range__97_97_char_class___range__110_110_char_class___range__121_121_, new int[] {97,110,121}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_any_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_all_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4031, 0, prod__lit_all__char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_, new int[] {97,108,108}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_all_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_real_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4037, 0, prod__lit_real__char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__108_108_, new int[] {114,101,97,108}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_real_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_finally_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4034, 0, prod__lit_finally__char_class___range__102_102_char_class___range__105_105_char_class___range__110_110_char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_char_class___range__121_121_, new int[] {102,105,110,97,108,108,121}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_finally_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_bool_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4040, 0, prod__lit_bool__char_class___range__98_98_char_class___range__111_111_char_class___range__111_111_char_class___range__108_108_, new int[] {98,111,111,108}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_bool_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_filter_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4043, 0, prod__lit_filter__char_class___range__102_102_char_class___range__105_105_char_class___range__108_108_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_, new int[] {102,105,108,116,101,114}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_filter_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_datetime_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4046, 0, prod__lit_datetime__char_class___range__100_100_char_class___range__97_97_char_class___range__116_116_char_class___range__101_101_char_class___range__116_116_char_class___range__105_105_char_class___range__109_109_char_class___range__101_101_, new int[] {100,97,116,101,116,105,109,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_datetime_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_while_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4055, 0, prod__lit_while__char_class___range__119_119_char_class___range__104_104_char_class___range__105_105_char_class___range__108_108_char_class___range__101_101_, new int[] {119,104,105,108,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_while_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_case_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4052, 0, prod__lit_case__char_class___range__99_99_char_class___range__97_97_char_class___range__115_115_char_class___range__101_101_, new int[] {99,97,115,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_case_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_layout_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4049, 0, prod__lit_layout__char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_, new int[] {108,97,121,111,117,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_layout_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_num_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4058, 0, prod__lit_num__char_class___range__110_110_char_class___range__117_117_char_class___range__109_109_, new int[] {110,117,109}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_num_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_set_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4061, 0, prod__lit_set__char_class___range__115_115_char_class___range__101_101_char_class___range__116_116_, new int[] {115,101,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_set_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_bag_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4064, 0, prod__lit_bag__char_class___range__98_98_char_class___range__97_97_char_class___range__103_103_, new int[] {98,97,103}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_bag_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_assoc_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4067, 0, prod__lit_assoc__char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__111_111_char_class___range__99_99_, new int[] {97,115,115,111,99}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_assoc_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_for_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4070, 0, prod__lit_for__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_, new int[] {102,111,114}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_for_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_continue_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4073, 0, prod__lit_continue__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__110_110_char_class___range__117_117_char_class___range__101_101_, new int[] {99,111,110,116,105,110,117,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_continue_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_bracket_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4076, 0, prod__lit_bracket__char_class___range__98_98_char_class___range__114_114_char_class___range__97_97_char_class___range__99_99_char_class___range__107_107_char_class___range__101_101_char_class___range__116_116_, new int[] {98,114,97,99,107,101,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_bracket_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_visit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4079, 0, prod__lit_visit__char_class___range__118_118_char_class___range__105_105_char_class___range__115_115_char_class___range__105_105_char_class___range__116_116_, new int[] {118,105,115,105,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_visit_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_return_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4094, 0, prod__lit_return__char_class___range__114_114_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_, new int[] {114,101,116,117,114,110}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_return_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_else_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4091, 0, prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {101,108,115,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_else_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_in_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4088, 0, prod__lit_in__char_class___range__105_105_char_class___range__110_110_, new int[] {105,110}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_in_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_it_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4085, 0, prod__lit_it__char_class___range__105_105_char_class___range__116_116_, new int[] {105,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_it_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_join_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4082, 0, prod__lit_join__char_class___range__106_106_char_class___range__111_111_char_class___range__105_105_char_class___range__110_110_, new int[] {106,111,105,110}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_join_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_if_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4097, 0, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_if_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_non_assoc_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4100, 0, prod__lit_non_assoc__char_class___range__110_110_char_class___range__111_111_char_class___range__110_110_char_class___range__45_45_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__111_111_char_class___range__99_99_, new int[] {110,111,110,45,97,115,115,111,99}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_non_assoc_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_one_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4103, 0, prod__lit_one__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_, new int[] {111,110,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_one_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_str_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4106, 0, prod__lit_str__char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_, new int[] {115,116,114}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_str_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_node_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4109, 0, prod__lit_node__char_class___range__110_110_char_class___range__111_111_char_class___range__100_100_char_class___range__101_101_, new int[] {110,111,100,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_node_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_catch_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4112, 0, prod__lit_catch__char_class___range__99_99_char_class___range__97_97_char_class___range__116_116_char_class___range__99_99_char_class___range__104_104_, new int[] {99,97,116,99,104}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_catch_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_type_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4118, 0, prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_, new int[] {116,121,112,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_type_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_notin_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4115, 0, prod__lit_notin__char_class___range__110_110_char_class___range__111_111_char_class___range__116_116_char_class___range__105_105_char_class___range__110_110_, new int[] {110,111,116,105,110}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_notin_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_extend_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4121, 0, prod__lit_extend__char_class___range__101_101_char_class___range__120_120_char_class___range__116_116_char_class___range__101_101_char_class___range__110_110_char_class___range__100_100_, new int[] {101,120,116,101,110,100}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_extend_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_append_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4124, 0, prod__lit_append__char_class___range__97_97_char_class___range__112_112_char_class___range__112_112_char_class___range__101_101_char_class___range__110_110_char_class___range__100_100_, new int[] {97,112,112,101,110,100}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_append_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_data_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4127, 0, prod__lit_data__char_class___range__100_100_char_class___range__97_97_char_class___range__116_116_char_class___range__97_97_, new int[] {100,97,116,97}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_data_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_tag_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4130, 0, prod__lit_tag__char_class___range__116_116_char_class___range__97_97_char_class___range__103_103_, new int[] {116,97,103}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_tag_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_o_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4133, 0, prod__lit_o__char_class___range__111_111_, new int[] {111}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_o_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_dynamic_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4136, 0, prod__lit_dynamic__char_class___range__100_100_char_class___range__121_121_char_class___range__110_110_char_class___range__97_97_char_class___range__109_109_char_class___range__105_105_char_class___range__99_99_, new int[] {100,121,110,97,109,105,99}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_dynamic_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_lrel_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4139, 0, prod__lit_lrel__char_class___range__108_108_char_class___range__114_114_char_class___range__101_101_char_class___range__108_108_, new int[] {108,114,101,108}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_lrel_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_list_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4142, 0, prod__lit_list__char_class___range__108_108_char_class___range__105_105_char_class___range__115_115_char_class___range__116_116_, new int[] {108,105,115,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_list_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_value_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4145, 0, prod__lit_value__char_class___range__118_118_char_class___range__97_97_char_class___range__108_108_char_class___range__117_117_char_class___range__101_101_, new int[] {118,97,108,117,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_value_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__$BasicType_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4149, 0, "$BasicType", null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__$BasicType_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_try_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4152, 0, prod__lit_try__char_class___range__116_116_char_class___range__114_114_char_class___range__121_121_, new int[] {116,114,121}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_try_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_void_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4155, 0, prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_, new int[] {118,111,105,100}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_void_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_public_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4158, 0, prod__lit_public__char_class___range__112_112_char_class___range__117_117_char_class___range__98_98_char_class___range__108_108_char_class___range__105_105_char_class___range__99_99_, new int[] {112,117,98,108,105,99}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_public_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_insert_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4164, 0, prod__lit_insert__char_class___range__105_105_char_class___range__110_110_char_class___range__115_115_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_, new int[] {105,110,115,101,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_insert_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_anno_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4161, 0, prod__lit_anno__char_class___range__97_97_char_class___range__110_110_char_class___range__110_110_char_class___range__111_111_, new int[] {97,110,110,111}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_anno_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_loc_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4170, 0, prod__lit_loc__char_class___range__108_108_char_class___range__111_111_char_class___range__99_99_, new int[] {108,111,99}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_loc_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_assert_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4167, 0, prod__lit_assert__char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_, new int[] {97,115,115,101,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_assert_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_import_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4173, 0, prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_, new int[] {105,109,112,111,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_import_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_start_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4176, 0, prod__lit_start__char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__116_116_, new int[] {115,116,97,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_start_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_test_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4179, 0, prod__lit_test__char_class___range__116_116_char_class___range__101_101_char_class___range__115_115_char_class___range__116_116_, new int[] {116,101,115,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_test_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_map_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4182, 0, prod__lit_map__char_class___range__109_109_char_class___range__97_97_char_class___range__112_112_, new int[] {109,97,112}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_map_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_private_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4188, 0, prod__lit_private__char_class___range__112_112_char_class___range__114_114_char_class___range__105_105_char_class___range__118_118_char_class___range__97_97_char_class___range__116_116_char_class___range__101_101_, new int[] {112,114,105,118,97,116,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_private_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_true_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4185, 0, prod__lit_true__char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__101_101_, new int[] {116,114,117,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_true_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_module_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4191, 0, prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_, new int[] {109,111,100,117,108,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_module_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_throws_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4194, 0, prod__lit_throws__char_class___range__116_116_char_class___range__104_104_char_class___range__114_114_char_class___range__111_111_char_class___range__119_119_char_class___range__115_115_, new int[] {116,104,114,111,119,115}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_throws_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_default_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4197, 0, prod__lit_default__char_class___range__100_100_char_class___range__101_101_char_class___range__102_102_char_class___range__97_97_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_, new int[] {100,101,102,97,117,108,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_default_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_alias_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4200, 0, prod__lit_alias__char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__97_97_char_class___range__115_115_, new int[] {97,108,105,97,115}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_alias_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_throw_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4203, 0, prod__lit_throw__char_class___range__116_116_char_class___range__104_104_char_class___range__114_114_char_class___range__111_111_char_class___range__119_119_, new int[] {116,104,114,111,119}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_throw_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_switch_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4206, 0, prod__lit_switch__char_class___range__115_115_char_class___range__119_119_char_class___range__105_105_char_class___range__116_116_char_class___range__99_99_char_class___range__104_104_, new int[] {115,119,105,116,99,104}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_switch_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_fail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4209, 0, prod__lit_fail__char_class___range__102_102_char_class___range__97_97_char_class___range__105_105_char_class___range__108_108_, new int[] {102,97,105,108}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_fail_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_mod_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4212, 0, prod__lit_mod__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_, new int[] {109,111,100}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_mod_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_int_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4215, 0, prod__lit_int__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_, new int[] {105,110,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_int_, tmp);
	}
    protected static final void _init_prod__$RascalKeywords__lit_tuple_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4218, 0, prod__lit_tuple__char_class___range__116_116_char_class___range__117_117_char_class___range__112_112_char_class___range__108_108_char_class___range__101_101_, new int[] {116,117,112,108,101}, null, null);
      builder.addAlternative(RascalRascal.prod__$RascalKeywords__lit_tuple_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$RascalKeywords__lit_solve_(builder);
      
        _init_prod__$RascalKeywords__lit_rat_(builder);
      
        _init_prod__$RascalKeywords__lit_break_(builder);
      
        _init_prod__$RascalKeywords__lit_rel_(builder);
      
        _init_prod__$RascalKeywords__lit_false_(builder);
      
        _init_prod__$RascalKeywords__lit_any_(builder);
      
        _init_prod__$RascalKeywords__lit_all_(builder);
      
        _init_prod__$RascalKeywords__lit_real_(builder);
      
        _init_prod__$RascalKeywords__lit_finally_(builder);
      
        _init_prod__$RascalKeywords__lit_bool_(builder);
      
        _init_prod__$RascalKeywords__lit_filter_(builder);
      
        _init_prod__$RascalKeywords__lit_datetime_(builder);
      
        _init_prod__$RascalKeywords__lit_while_(builder);
      
        _init_prod__$RascalKeywords__lit_case_(builder);
      
        _init_prod__$RascalKeywords__lit_layout_(builder);
      
        _init_prod__$RascalKeywords__lit_num_(builder);
      
        _init_prod__$RascalKeywords__lit_set_(builder);
      
        _init_prod__$RascalKeywords__lit_bag_(builder);
      
        _init_prod__$RascalKeywords__lit_assoc_(builder);
      
        _init_prod__$RascalKeywords__lit_for_(builder);
      
        _init_prod__$RascalKeywords__lit_continue_(builder);
      
        _init_prod__$RascalKeywords__lit_bracket_(builder);
      
        _init_prod__$RascalKeywords__lit_visit_(builder);
      
        _init_prod__$RascalKeywords__lit_return_(builder);
      
        _init_prod__$RascalKeywords__lit_else_(builder);
      
        _init_prod__$RascalKeywords__lit_in_(builder);
      
        _init_prod__$RascalKeywords__lit_it_(builder);
      
        _init_prod__$RascalKeywords__lit_join_(builder);
      
        _init_prod__$RascalKeywords__lit_if_(builder);
      
        _init_prod__$RascalKeywords__lit_non_assoc_(builder);
      
        _init_prod__$RascalKeywords__lit_one_(builder);
      
        _init_prod__$RascalKeywords__lit_str_(builder);
      
        _init_prod__$RascalKeywords__lit_node_(builder);
      
        _init_prod__$RascalKeywords__lit_catch_(builder);
      
        _init_prod__$RascalKeywords__lit_type_(builder);
      
        _init_prod__$RascalKeywords__lit_notin_(builder);
      
        _init_prod__$RascalKeywords__lit_extend_(builder);
      
        _init_prod__$RascalKeywords__lit_append_(builder);
      
        _init_prod__$RascalKeywords__lit_data_(builder);
      
        _init_prod__$RascalKeywords__lit_tag_(builder);
      
        _init_prod__$RascalKeywords__lit_o_(builder);
      
        _init_prod__$RascalKeywords__lit_dynamic_(builder);
      
        _init_prod__$RascalKeywords__lit_lrel_(builder);
      
        _init_prod__$RascalKeywords__lit_list_(builder);
      
        _init_prod__$RascalKeywords__lit_value_(builder);
      
        _init_prod__$RascalKeywords__$BasicType_(builder);
      
        _init_prod__$RascalKeywords__lit_try_(builder);
      
        _init_prod__$RascalKeywords__lit_void_(builder);
      
        _init_prod__$RascalKeywords__lit_public_(builder);
      
        _init_prod__$RascalKeywords__lit_insert_(builder);
      
        _init_prod__$RascalKeywords__lit_anno_(builder);
      
        _init_prod__$RascalKeywords__lit_loc_(builder);
      
        _init_prod__$RascalKeywords__lit_assert_(builder);
      
        _init_prod__$RascalKeywords__lit_import_(builder);
      
        _init_prod__$RascalKeywords__lit_start_(builder);
      
        _init_prod__$RascalKeywords__lit_test_(builder);
      
        _init_prod__$RascalKeywords__lit_map_(builder);
      
        _init_prod__$RascalKeywords__lit_private_(builder);
      
        _init_prod__$RascalKeywords__lit_true_(builder);
      
        _init_prod__$RascalKeywords__lit_module_(builder);
      
        _init_prod__$RascalKeywords__lit_throws_(builder);
      
        _init_prod__$RascalKeywords__lit_default_(builder);
      
        _init_prod__$RascalKeywords__lit_alias_(builder);
      
        _init_prod__$RascalKeywords__lit_throw_(builder);
      
        _init_prod__$RascalKeywords__lit_switch_(builder);
      
        _init_prod__$RascalKeywords__lit_fail_(builder);
      
        _init_prod__$RascalKeywords__lit_mod_(builder);
      
        _init_prod__$RascalKeywords__lit_int_(builder);
      
        _init_prod__$RascalKeywords__lit_tuple_(builder);
      
    }
  }
	
  protected static class $NamedRegExp {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$NamedRegExp__lit___60_$Name_lit___62_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-4234, 2, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4233, 1, "$Name", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-4231, 0, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      builder.addAlternative(RascalRascal.prod__$NamedRegExp__lit___60_$Name_lit___62_, tmp);
	}
    protected static final void _init_prod__$NamedRegExp__char_class___range__92_92_char_class___range__47_47_range__60_60_range__62_62_range__92_92_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new CharStackNode<IConstructor>(-4238, 1, new int[][]{{47,47},{60,60},{62,62},{92,92}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-4237, 0, new int[][]{{92,92}}, null, null);
      builder.addAlternative(RascalRascal.prod__$NamedRegExp__char_class___range__92_92_char_class___range__47_47_range__60_60_range__62_62_range__92_92_, tmp);
	}
    protected static final void _init_prod__$NamedRegExp__$NamedBackslash_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4242, 0, "$NamedBackslash", null, null);
      builder.addAlternative(RascalRascal.prod__$NamedRegExp__$NamedBackslash_, tmp);
	}
    protected static final void _init_prod__$NamedRegExp__char_class___range__0_46_range__48_59_range__61_61_range__63_91_range__93_16777215_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(-4245, 0, new int[][]{{0,46},{48,59},{61,61},{63,91},{93,16777215}}, null, null);
      builder.addAlternative(RascalRascal.prod__$NamedRegExp__char_class___range__0_46_range__48_59_range__61_61_range__63_91_range__93_16777215_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$NamedRegExp__lit___60_$Name_lit___62_(builder);
      
        _init_prod__$NamedRegExp__char_class___range__92_92_char_class___range__47_47_range__60_60_range__62_62_range__92_92_(builder);
      
        _init_prod__$NamedRegExp__$NamedBackslash_(builder);
      
        _init_prod__$NamedRegExp__char_class___range__0_46_range__48_59_range__61_61_range__63_91_range__93_16777215_(builder);
      
    }
  }
	
  protected static class $RegExpLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$RegExpLiteral__lit___47_iter_star__$RegExp_lit___47_$RegExpModifier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[4];
      
      tmp[3] = new NonTerminalStackNode<IConstructor>(-4293, 3, "$RegExpModifier", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-4291, 2, prod__lit___47__char_class___range__47_47_, new int[] {47}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-4290, 1, regular__iter_star__$RegExp, new NonTerminalStackNode<IConstructor>(-4289, 0, "$RegExp", null, null), false, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-4287, 0, prod__lit___47__char_class___range__47_47_, new int[] {47}, null, null);
      builder.addAlternative(RascalRascal.prod__$RegExpLiteral__lit___47_iter_star__$RegExp_lit___47_$RegExpModifier_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$RegExpLiteral__lit___47_iter_star__$RegExp_lit___47_$RegExpModifier_(builder);
      
    }
  }
	
  protected static class $HexIntegerLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$HexIntegerLiteral__char_class___range__48_48_char_class___range__88_88_range__120_120_iter__char_class___range__48_57_range__65_70_range__97_102_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new ListStackNode<IConstructor>(-4381, 2, regular__iter__char_class___range__48_57_range__65_70_range__97_102, new CharStackNode<IConstructor>(-4378, 0, new int[][]{{48,57},{65,70},{97,102}}, null, null), true, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      tmp[1] = new CharStackNode<IConstructor>(-4377, 1, new int[][]{{88,88},{120,120}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-4376, 0, new int[][]{{48,48}}, null, null);
      builder.addAlternative(RascalRascal.prod__$HexIntegerLiteral__char_class___range__48_48_char_class___range__88_88_range__120_120_iter__char_class___range__48_57_range__65_70_range__97_102_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$HexIntegerLiteral__char_class___range__48_48_char_class___range__88_88_range__120_120_iter__char_class___range__48_57_range__65_70_range__97_102_(builder);
      
    }
  }
	
  protected static class $PostStringChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$PostStringChars__char_class___range__62_62_iter_star__$StringCharacter_char_class___range__34_34__tag__category___67_111_110_115_116_97_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new CharStackNode<IConstructor>(-4369, 2, new int[][]{{34,34}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-4368, 1, regular__iter_star__$StringCharacter, new NonTerminalStackNode<IConstructor>(-4367, 0, "$StringCharacter", null, null), false, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-4365, 0, new int[][]{{62,62}}, null, null);
      builder.addAlternative(RascalRascal.prod__$PostStringChars__char_class___range__62_62_iter_star__$StringCharacter_char_class___range__34_34__tag__category___67_111_110_115_116_97_110_116, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$PostStringChars__char_class___range__62_62_iter_star__$StringCharacter_char_class___range__34_34__tag__category___67_111_110_115_116_97_110_116(builder);
      
    }
  }
	
  protected static class $ModuleActuals {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$ModuleActuals__lit___91_$layouts_LAYOUTLIST_types_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-4424, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-4423, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-4420, 2, regular__iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-4414, 0, "$Type", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-4416, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-4417, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-4419, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4412, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-4410, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$ModuleActuals__lit___91_$layouts_LAYOUTLIST_types_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$ModuleActuals__lit___91_$layouts_LAYOUTLIST_types_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
    }
  }
	
  protected static class start__$EvalCommand {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__start__$EvalCommand__$layouts_LAYOUTLIST_top_$EvalCommand_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-4402, 2, "$layouts_LAYOUTLIST", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4399, 1, "$EvalCommand", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4397, 0, "$layouts_LAYOUTLIST", null, null);
      builder.addAlternative(RascalRascal.prod__start__$EvalCommand__$layouts_LAYOUTLIST_top_$EvalCommand_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__start__$EvalCommand__$layouts_LAYOUTLIST_top_$EvalCommand_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $PathChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$PathChars__$URLChars_char_class___range__124_124_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new CharStackNode<IConstructor>(-4433, 1, new int[][]{{124,124}}, null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4432, 0, "$URLChars", null, null);
      builder.addAlternative(RascalRascal.prod__$PathChars__$URLChars_char_class___range__124_124_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$PathChars__$URLChars_char_class___range__124_124_(builder);
      
    }
  }
	
  protected static class $DateTimeLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__timeLiteral_$DateTimeLiteral__time_$JustTime_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4463, 0, "$JustTime", null, null);
      builder.addAlternative(RascalRascal.prod__timeLiteral_$DateTimeLiteral__time_$JustTime_, tmp);
	}
    protected static final void _init_prod__dateLiteral_$DateTimeLiteral__date_$JustDate_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4469, 0, "$JustDate", null, null);
      builder.addAlternative(RascalRascal.prod__dateLiteral_$DateTimeLiteral__date_$JustDate_, tmp);
	}
    protected static final void _init_prod__dateAndTimeLiteral_$DateTimeLiteral__dateAndTime_$DateAndTime_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4475, 0, "$DateAndTime", null, null);
      builder.addAlternative(RascalRascal.prod__dateAndTimeLiteral_$DateTimeLiteral__dateAndTime_$DateAndTime_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__timeLiteral_$DateTimeLiteral__time_$JustTime_(builder);
      
        _init_prod__dateLiteral_$DateTimeLiteral__date_$JustDate_(builder);
      
        _init_prod__dateAndTimeLiteral_$DateTimeLiteral__dateAndTime_$DateAndTime_(builder);
      
    }
  }
	
  protected static class $BasicType {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__type_$BasicType__lit_type_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4507, 0, prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_, new int[] {116,121,112,101}, null, null);
      builder.addAlternative(RascalRascal.prod__type_$BasicType__lit_type_, tmp);
	}
    protected static final void _init_prod__set_$BasicType__lit_set_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4511, 0, prod__lit_set__char_class___range__115_115_char_class___range__101_101_char_class___range__116_116_, new int[] {115,101,116}, null, null);
      builder.addAlternative(RascalRascal.prod__set_$BasicType__lit_set_, tmp);
	}
    protected static final void _init_prod__string_$BasicType__lit_str_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4515, 0, prod__lit_str__char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_, new int[] {115,116,114}, null, null);
      builder.addAlternative(RascalRascal.prod__string_$BasicType__lit_str_, tmp);
	}
    protected static final void _init_prod__dateTime_$BasicType__lit_datetime_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4519, 0, prod__lit_datetime__char_class___range__100_100_char_class___range__97_97_char_class___range__116_116_char_class___range__101_101_char_class___range__116_116_char_class___range__105_105_char_class___range__109_109_char_class___range__101_101_, new int[] {100,97,116,101,116,105,109,101}, null, null);
      builder.addAlternative(RascalRascal.prod__dateTime_$BasicType__lit_datetime_, tmp);
	}
    protected static final void _init_prod__bag_$BasicType__lit_bag_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4523, 0, prod__lit_bag__char_class___range__98_98_char_class___range__97_97_char_class___range__103_103_, new int[] {98,97,103}, null, null);
      builder.addAlternative(RascalRascal.prod__bag_$BasicType__lit_bag_, tmp);
	}
    protected static final void _init_prod__rational_$BasicType__lit_rat_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4527, 0, prod__lit_rat__char_class___range__114_114_char_class___range__97_97_char_class___range__116_116_, new int[] {114,97,116}, null, null);
      builder.addAlternative(RascalRascal.prod__rational_$BasicType__lit_rat_, tmp);
	}
    protected static final void _init_prod__real_$BasicType__lit_real_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4531, 0, prod__lit_real__char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__108_108_, new int[] {114,101,97,108}, null, null);
      builder.addAlternative(RascalRascal.prod__real_$BasicType__lit_real_, tmp);
	}
    protected static final void _init_prod__node_$BasicType__lit_node_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4535, 0, prod__lit_node__char_class___range__110_110_char_class___range__111_111_char_class___range__100_100_char_class___range__101_101_, new int[] {110,111,100,101}, null, null);
      builder.addAlternative(RascalRascal.prod__node_$BasicType__lit_node_, tmp);
	}
    protected static final void _init_prod__int_$BasicType__lit_int_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4539, 0, prod__lit_int__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_, new int[] {105,110,116}, null, null);
      builder.addAlternative(RascalRascal.prod__int_$BasicType__lit_int_, tmp);
	}
    protected static final void _init_prod__map_$BasicType__lit_map_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4543, 0, prod__lit_map__char_class___range__109_109_char_class___range__97_97_char_class___range__112_112_, new int[] {109,97,112}, null, null);
      builder.addAlternative(RascalRascal.prod__map_$BasicType__lit_map_, tmp);
	}
    protected static final void _init_prod__tuple_$BasicType__lit_tuple_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4547, 0, prod__lit_tuple__char_class___range__116_116_char_class___range__117_117_char_class___range__112_112_char_class___range__108_108_char_class___range__101_101_, new int[] {116,117,112,108,101}, null, null);
      builder.addAlternative(RascalRascal.prod__tuple_$BasicType__lit_tuple_, tmp);
	}
    protected static final void _init_prod__list_$BasicType__lit_list_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4551, 0, prod__lit_list__char_class___range__108_108_char_class___range__105_105_char_class___range__115_115_char_class___range__116_116_, new int[] {108,105,115,116}, null, null);
      builder.addAlternative(RascalRascal.prod__list_$BasicType__lit_list_, tmp);
	}
    protected static final void _init_prod__num_$BasicType__lit_num_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4555, 0, prod__lit_num__char_class___range__110_110_char_class___range__117_117_char_class___range__109_109_, new int[] {110,117,109}, null, null);
      builder.addAlternative(RascalRascal.prod__num_$BasicType__lit_num_, tmp);
	}
    protected static final void _init_prod__loc_$BasicType__lit_loc_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4559, 0, prod__lit_loc__char_class___range__108_108_char_class___range__111_111_char_class___range__99_99_, new int[] {108,111,99}, null, null);
      builder.addAlternative(RascalRascal.prod__loc_$BasicType__lit_loc_, tmp);
	}
    protected static final void _init_prod__value_$BasicType__lit_value_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4563, 0, prod__lit_value__char_class___range__118_118_char_class___range__97_97_char_class___range__108_108_char_class___range__117_117_char_class___range__101_101_, new int[] {118,97,108,117,101}, null, null);
      builder.addAlternative(RascalRascal.prod__value_$BasicType__lit_value_, tmp);
	}
    protected static final void _init_prod__relation_$BasicType__lit_rel_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4567, 0, prod__lit_rel__char_class___range__114_114_char_class___range__101_101_char_class___range__108_108_, new int[] {114,101,108}, null, null);
      builder.addAlternative(RascalRascal.prod__relation_$BasicType__lit_rel_, tmp);
	}
    protected static final void _init_prod__void_$BasicType__lit_void_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4571, 0, prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_, new int[] {118,111,105,100}, null, null);
      builder.addAlternative(RascalRascal.prod__void_$BasicType__lit_void_, tmp);
	}
    protected static final void _init_prod__listRelation_$BasicType__lit_lrel_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4575, 0, prod__lit_lrel__char_class___range__108_108_char_class___range__114_114_char_class___range__101_101_char_class___range__108_108_, new int[] {108,114,101,108}, null, null);
      builder.addAlternative(RascalRascal.prod__listRelation_$BasicType__lit_lrel_, tmp);
	}
    protected static final void _init_prod__bool_$BasicType__lit_bool_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-4579, 0, prod__lit_bool__char_class___range__98_98_char_class___range__111_111_char_class___range__111_111_char_class___range__108_108_, new int[] {98,111,111,108}, null, null);
      builder.addAlternative(RascalRascal.prod__bool_$BasicType__lit_bool_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__type_$BasicType__lit_type_(builder);
      
        _init_prod__set_$BasicType__lit_set_(builder);
      
        _init_prod__string_$BasicType__lit_str_(builder);
      
        _init_prod__dateTime_$BasicType__lit_datetime_(builder);
      
        _init_prod__bag_$BasicType__lit_bag_(builder);
      
        _init_prod__rational_$BasicType__lit_rat_(builder);
      
        _init_prod__real_$BasicType__lit_real_(builder);
      
        _init_prod__node_$BasicType__lit_node_(builder);
      
        _init_prod__int_$BasicType__lit_int_(builder);
      
        _init_prod__map_$BasicType__lit_map_(builder);
      
        _init_prod__tuple_$BasicType__lit_tuple_(builder);
      
        _init_prod__list_$BasicType__lit_list_(builder);
      
        _init_prod__num_$BasicType__lit_num_(builder);
      
        _init_prod__loc_$BasicType__lit_loc_(builder);
      
        _init_prod__value_$BasicType__lit_value_(builder);
      
        _init_prod__relation_$BasicType__lit_rel_(builder);
      
        _init_prod__void_$BasicType__lit_void_(builder);
      
        _init_prod__listRelation_$BasicType__lit_lrel_(builder);
      
        _init_prod__bool_$BasicType__lit_bool_(builder);
      
    }
  }
	
  protected static class $FunctionDeclaration {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__expression_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__Foldable_tag__breakable___123_101_120_112_114_101_115_115_105_111_110_125(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(-4632, 10, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-4631, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-4628, 8, "$Expression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-4626, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-4624, 6, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-4623, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-4620, 4, "$Signature", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-4618, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-4615, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4613, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4610, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__expression_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__Foldable_tag__breakable___123_101_120_112_114_101_115_115_105_111_110_125, tmp);
	}
    protected static final void _init_prod__abstract_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-4651, 6, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-4650, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-4647, 4, "$Signature", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-4645, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-4642, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4640, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4637, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__abstract_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__default_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_body_$FunctionBody__tag__Foldable(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-4671, 6, "$FunctionBody", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-4669, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-4666, 4, "$Signature", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-4664, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-4661, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4659, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4656, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__default_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_body_$FunctionBody__tag__Foldable, tmp);
	}
    protected static final void _init_prod__conditional_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit_when_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__Foldable_tag__breakable___123_101_120_112_114_101_115_115_105_111_110_44_99_111_110_100_105_116_105_111_110_115_125(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new LiteralStackNode<IConstructor>(-4713, 14, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-4712, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new SeparatedListStackNode<IConstructor>(-4709, 12, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-4703, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-4705, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-4706, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-4708, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-4701, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(-4699, 10, prod__lit_when__char_class___range__119_119_char_class___range__104_104_char_class___range__101_101_char_class___range__110_110_, new int[] {119,104,101,110}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-4698, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-4695, 8, "$Expression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-4693, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-4691, 6, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-4690, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-4687, 4, "$Signature", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-4685, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-4682, 2, "$Visibility", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4680, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4677, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__conditional_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit_when_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__Foldable_tag__breakable___123_101_120_112_114_101_115_115_105_111_110_44_99_111_110_100_105_116_105_111_110_115_125, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__expression_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___59__tag__Foldable_tag__breakable___123_101_120_112_114_101_115_115_105_111_110_125(builder);
      
        _init_prod__abstract_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__default_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_body_$FunctionBody__tag__Foldable(builder);
      
        _init_prod__conditional_$FunctionDeclaration__tags_$Tags_$layouts_LAYOUTLIST_visibility_$Visibility_$layouts_LAYOUTLIST_signature_$Signature_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit_when_$layouts_LAYOUTLIST_conditions_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___59__tag__Foldable_tag__breakable___123_101_120_112_114_101_115_115_105_111_110_44_99_111_110_100_105_116_105_111_110_115_125(builder);
      
    }
  }
	
  protected static class $Variant {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__nAryConstructor_$Variant__name_$Name_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-4770, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-4769, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-4766, 4, regular__iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-4760, 0, "$TypeArg", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-4762, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-4763, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-4765, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-4758, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-4756, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4755, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4752, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__nAryConstructor_$Variant__name_$Name_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__nAryConstructor_$Variant__name_$Name_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
    }
  }
	
  protected static class start__$PreModule {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__start__$PreModule__$layouts_LAYOUTLIST_top_$PreModule_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-4791, 2, "$layouts_LAYOUTLIST", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4788, 1, "$PreModule", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4786, 0, "$layouts_LAYOUTLIST", null, null);
      builder.addAlternative(RascalRascal.prod__start__$PreModule__$layouts_LAYOUTLIST_top_$PreModule_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__start__$PreModule__$layouts_LAYOUTLIST_top_$PreModule_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $Declarator {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Declarator__type_$Type_$layouts_LAYOUTLIST_variables_iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new SeparatedListStackNode<IConstructor>(-4859, 2, regular__iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-4853, 0, "$Variable", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-4855, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-4856, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-4858, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4851, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4848, 0, "$Type", null, null);
      builder.addAlternative(RascalRascal.prod__default_$Declarator__type_$Type_$layouts_LAYOUTLIST_variables_iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Declarator__type_$Type_$layouts_LAYOUTLIST_variables_iter_seps__$Variable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $StructuredType {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$StructuredType__basicType_$BasicType_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_arguments_iter_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-4933, 6, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-4932, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-4929, 4, regular__iter_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-4923, 0, "$TypeArg", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-4925, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-4926, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-4928, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-4921, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-4919, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-4918, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-4915, 0, "$BasicType", null, null);
      builder.addAlternative(RascalRascal.prod__default_$StructuredType__basicType_$BasicType_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_arguments_iter_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$StructuredType__basicType_$BasicType_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_arguments_iter_seps__$TypeArg__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
    }
  }
	
  protected static class $TimePartNoTZ {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$TimePartNoTZ__char_class___range__48_50_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new OptionalStackNode<IConstructor>(-4962, 8, regular__opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57, new SequenceStackNode<IConstructor>(-4961, 0, regular__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(-4954, 0, new int[][]{{44,44},{46,46}}, null, null), new CharStackNode<IConstructor>(-4955, 1, new int[][]{{48,57}}, null, null), new OptionalStackNode<IConstructor>(-4960, 2, regular__opt__seq___char_class___range__48_57_opt__char_class___range__48_57, new SequenceStackNode<IConstructor>(-4959, 0, regular__seq___char_class___range__48_57_opt__char_class___range__48_57, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(-4956, 0, new int[][]{{48,57}}, null, null), new OptionalStackNode<IConstructor>(-4958, 1, regular__opt__char_class___range__48_57, new CharStackNode<IConstructor>(-4957, 0, new int[][]{{48,57}}, null, null), null, null)}, null, null), null, null)}, null, null), null, null);
      tmp[7] = new CharStackNode<IConstructor>(-4953, 7, new int[][]{{48,57}}, null, null);
      tmp[6] = new CharStackNode<IConstructor>(-4952, 6, new int[][]{{48,53}}, null, null);
      tmp[5] = new LiteralStackNode<IConstructor>(-4951, 5, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[4] = new CharStackNode<IConstructor>(-4950, 4, new int[][]{{48,57}}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(-4949, 3, new int[][]{{48,53}}, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-4948, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-4947, 1, new int[][]{{48,57}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-4946, 0, new int[][]{{48,50}}, null, null);
      builder.addAlternative(RascalRascal.prod__$TimePartNoTZ__char_class___range__48_50_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57_, tmp);
	}
    protected static final void _init_prod__$TimePartNoTZ__char_class___range__48_50_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new OptionalStackNode<IConstructor>(-4979, 6, regular__opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57, new SequenceStackNode<IConstructor>(-4978, 0, regular__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(-4971, 0, new int[][]{{44,44},{46,46}}, null, null), new CharStackNode<IConstructor>(-4972, 1, new int[][]{{48,57}}, null, null), new OptionalStackNode<IConstructor>(-4977, 2, regular__opt__seq___char_class___range__48_57_opt__char_class___range__48_57, new SequenceStackNode<IConstructor>(-4976, 0, regular__seq___char_class___range__48_57_opt__char_class___range__48_57, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(-4973, 0, new int[][]{{48,57}}, null, null), new OptionalStackNode<IConstructor>(-4975, 1, regular__opt__char_class___range__48_57, new CharStackNode<IConstructor>(-4974, 0, new int[][]{{48,57}}, null, null), null, null)}, null, null), null, null)}, null, null), null, null);
      tmp[5] = new CharStackNode<IConstructor>(-4970, 5, new int[][]{{48,57}}, null, null);
      tmp[4] = new CharStackNode<IConstructor>(-4969, 4, new int[][]{{48,53}}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(-4968, 3, new int[][]{{48,57}}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-4967, 2, new int[][]{{48,53}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-4966, 1, new int[][]{{48,57}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-4965, 0, new int[][]{{48,50}}, null, null);
      builder.addAlternative(RascalRascal.prod__$TimePartNoTZ__char_class___range__48_50_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$TimePartNoTZ__char_class___range__48_50_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_lit___58_char_class___range__48_53_char_class___range__48_57_opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57_(builder);
      
        _init_prod__$TimePartNoTZ__char_class___range__48_50_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_char_class___range__48_53_char_class___range__48_57_opt__seq___char_class___range__44_44_range__46_46_char_class___range__48_57_opt__seq___char_class___range__48_57_opt__char_class___range__48_57_(builder);
      
    }
  }
	
  protected static class $Name {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$Name__seq___char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SequenceStackNode<IConstructor>(-5006, 0, regular__seq___char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(-4998, 0, new int[][]{{65,90},{95,95},{97,122}}, new IEnterFilter[] {new CharPrecedeRestriction(new int[][]{{65,90},{95,95},{97,122}})}, null), new ListStackNode<IConstructor>(-5002, 1, regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, new CharStackNode<IConstructor>(-4999, 0, new int[][]{{48,57},{65,90},{95,95},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})})}, null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {114,97,116}), new StringMatchRestriction(new int[] {105,110}), new StringMatchRestriction(new int[] {105,109,112,111,114,116}), new StringMatchRestriction(new int[] {99,111,110,116,105,110,117,101}), new StringMatchRestriction(new int[] {97,108,108}), new StringMatchRestriction(new int[] {102,97,108,115,101}), new StringMatchRestriction(new int[] {108,114,101,108}), new StringMatchRestriction(new int[] {97,110,110,111}), new StringMatchRestriction(new int[] {98,114,97,99,107,101,116}), new StringMatchRestriction(new int[] {100,97,116,97}), new StringMatchRestriction(new int[] {106,111,105,110}), new StringMatchRestriction(new int[] {108,97,121,111,117,116}), new StringMatchRestriction(new int[] {105,116}), new StringMatchRestriction(new int[] {115,119,105,116,99,104}), new StringMatchRestriction(new int[] {99,97,115,101}), new StringMatchRestriction(new int[] {114,101,116,117,114,110}), new StringMatchRestriction(new int[] {119,104,105,108,101}), new StringMatchRestriction(new int[] {115,116,114}), new StringMatchRestriction(new int[] {115,111,108,118,101}), new StringMatchRestriction(new int[] {100,121,110,97,109,105,99}), new StringMatchRestriction(new int[] {110,111,116,105,110}), new StringMatchRestriction(new int[] {101,108,115,101}), new StringMatchRestriction(new int[] {105,110,115,101,114,116}), new StringMatchRestriction(new int[] {116,121,112,101}), new StringMatchRestriction(new int[] {116,114,121}), new StringMatchRestriction(new int[] {99,97,116,99,104}), new StringMatchRestriction(new int[] {110,117,109}), new StringMatchRestriction(new int[] {110,111,100,101}), new StringMatchRestriction(new int[] {109,111,100}), new StringMatchRestriction(new int[] {112,114,105,118,97,116,101}), new StringMatchRestriction(new int[] {102,105,110,97,108,108,121}), new StringMatchRestriction(new int[] {116,114,117,101}), new StringMatchRestriction(new int[] {98,97,103}), new StringMatchRestriction(new int[] {118,111,105,100}), new StringMatchRestriction(new int[] {97,115,115,111,99}), new StringMatchRestriction(new int[] {110,111,110,45,97,115,115,111,99}), new StringMatchRestriction(new int[] {116,101,115,116}), new StringMatchRestriction(new int[] {105,102}), new StringMatchRestriction(new int[] {114,101,97,108}), new StringMatchRestriction(new int[] {108,105,115,116}), new StringMatchRestriction(new int[] {102,97,105,108}), new StringMatchRestriction(new int[] {114,101,108}), new StringMatchRestriction(new int[] {97,112,112,101,110,100}), new StringMatchRestriction(new int[] {101,120,116,101,110,100}), new StringMatchRestriction(new int[] {116,97,103}), new StringMatchRestriction(new int[] {111,110,101}), new StringMatchRestriction(new int[] {116,104,114,111,119}), new StringMatchRestriction(new int[] {115,101,116}), new StringMatchRestriction(new int[] {115,116,97,114,116}), new StringMatchRestriction(new int[] {97,110,121}), new StringMatchRestriction(new int[] {109,111,100,117,108,101}), new StringMatchRestriction(new int[] {111}), new StringMatchRestriction(new int[] {105,110,116}), new StringMatchRestriction(new int[] {112,117,98,108,105,99}), new StringMatchRestriction(new int[] {98,111,111,108}), new StringMatchRestriction(new int[] {118,97,108,117,101}), new StringMatchRestriction(new int[] {98,114,101,97,107}), new StringMatchRestriction(new int[] {102,105,108,116,101,114}), new StringMatchRestriction(new int[] {100,97,116,101,116,105,109,101}), new StringMatchRestriction(new int[] {97,115,115,101,114,116}), new StringMatchRestriction(new int[] {108,111,99}), new StringMatchRestriction(new int[] {100,101,102,97,117,108,116}), new StringMatchRestriction(new int[] {116,104,114,111,119,115}), new StringMatchRestriction(new int[] {116,117,112,108,101}), new StringMatchRestriction(new int[] {102,111,114}), new StringMatchRestriction(new int[] {118,105,115,105,116}), new StringMatchRestriction(new int[] {97,108,105,97,115}), new StringMatchRestriction(new int[] {109,97,112})});
      builder.addAlternative(RascalRascal.prod__$Name__seq___char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_, tmp);
	}
    protected static final void _init_prod__$Name__char_class___range__92_92_char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__45_45_range__48_57_range__65_90_range__95_95_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new ListStackNode<IConstructor>(-5014, 2, regular__iter_star__char_class___range__45_45_range__48_57_range__65_90_range__95_95_range__97_122, new CharStackNode<IConstructor>(-5011, 0, new int[][]{{45,45},{48,57},{65,90},{95,95},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{45,45},{48,57},{65,90},{95,95},{97,122}})});
      tmp[1] = new CharStackNode<IConstructor>(-5010, 1, new int[][]{{65,90},{95,95},{97,122}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-5009, 0, new int[][]{{92,92}}, null, null);
      builder.addAlternative(RascalRascal.prod__$Name__char_class___range__92_92_char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__45_45_range__48_57_range__65_90_range__95_95_range__97_122_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$Name__seq___char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(builder);
      
        _init_prod__$Name__char_class___range__92_92_char_class___range__65_90_range__95_95_range__97_122_iter_star__char_class___range__45_45_range__48_57_range__65_90_range__95_95_range__97_122_(builder);
      
    }
  }
	
  protected static class $Start {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__absent_$Start__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(-5021, 0);
      builder.addAlternative(RascalRascal.prod__absent_$Start__, tmp);
	}
    protected static final void _init_prod__present_$Start__lit_start_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5025, 0, prod__lit_start__char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__116_116_, new int[] {115,116,97,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__present_$Start__lit_start_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__absent_$Start__(builder);
      
        _init_prod__present_$Start__lit_start_(builder);
      
    }
  }
	
  protected static class $Formals {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Formals__formals_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SeparatedListStackNode<IConstructor>(-5055, 0, regular__iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-5049, 0, "$Pattern", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-5051, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-5052, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-5054, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Formals__formals_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Formals__formals_iter_star_seps__$Pattern__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $Tags {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Tags__tags_iter_star_seps__$Tag__$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SeparatedListStackNode<IConstructor>(-5068, 0, regular__iter_star_seps__$Tag__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-5065, 0, "$Tag", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-5067, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Tags__tags_iter_star_seps__$Tag__$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Tags__tags_iter_star_seps__$Tag__$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $OptionalExpression {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__expression_$OptionalExpression__expression_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5078, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__expression_$OptionalExpression__expression_$Expression_, tmp);
	}
    protected static final void _init_prod__noExpression_$OptionalExpression__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(-5082, 0);
      builder.addAlternative(RascalRascal.prod__noExpression_$OptionalExpression__, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__expression_$OptionalExpression__expression_$Expression_(builder);
      
        _init_prod__noExpression_$OptionalExpression__(builder);
      
    }
  }
	
  protected static class start__$Commands {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__start__$Commands__$layouts_LAYOUTLIST_top_$Commands_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5098, 2, "$layouts_LAYOUTLIST", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5095, 1, "$Commands", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5093, 0, "$layouts_LAYOUTLIST", null, null);
      builder.addAlternative(RascalRascal.prod__start__$Commands__$layouts_LAYOUTLIST_top_$Commands_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__start__$Commands__$layouts_LAYOUTLIST_top_$Commands_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $Backslash {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$Backslash__char_class___range__92_92_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(-5118, 0, new int[][]{{92,92}}, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{47,47},{60,60},{62,62},{92,92}})});
      builder.addAlternative(RascalRascal.prod__$Backslash__char_class___range__92_92_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$Backslash__char_class___range__92_92_(builder);
      
    }
  }
	
  protected static class $CaseInsensitiveStringConstant {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$CaseInsensitiveStringConstant__lit___39_iter_star__$StringCharacter_lit___39__tag__category___67_111_110_115_116_97_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-5109, 2, prod__lit___39__char_class___range__39_39_, new int[] {39}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-5108, 1, regular__iter_star__$StringCharacter, new NonTerminalStackNode<IConstructor>(-5107, 0, "$StringCharacter", null, null), false, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5105, 0, prod__lit___39__char_class___range__39_39_, new int[] {39}, null, null);
      builder.addAlternative(RascalRascal.prod__$CaseInsensitiveStringConstant__lit___39_iter_star__$StringCharacter_lit___39__tag__category___67_111_110_115_116_97_110_116, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$CaseInsensitiveStringConstant__lit___39_iter_star__$StringCharacter_lit___39__tag__category___67_111_110_115_116_97_110_116(builder);
      
    }
  }
	
  protected static class $JustDate {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$JustDate__lit___36_$DatePart_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5157, 1, "$DatePart", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5155, 0, prod__lit___36__char_class___range__36_36_, new int[] {36}, null, null);
      builder.addAlternative(RascalRascal.prod__$JustDate__lit___36_$DatePart_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$JustDate__lit___36_$DatePart_(builder);
      
    }
  }
	
  protected static class $PathTail {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__mid_$PathTail__mid_$MidPathChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$PathTail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5199, 4, "$PathTail", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5197, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5194, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5192, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5189, 0, "$MidPathChars", null, null);
      builder.addAlternative(RascalRascal.prod__mid_$PathTail__mid_$MidPathChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$PathTail_, tmp);
	}
    protected static final void _init_prod__post_$PathTail__post_$PostPathChars_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5205, 0, "$PostPathChars", null, null);
      builder.addAlternative(RascalRascal.prod__post_$PathTail__post_$PostPathChars_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__mid_$PathTail__mid_$MidPathChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$PathTail_(builder);
      
        _init_prod__post_$PathTail__post_$PostPathChars_(builder);
      
    }
  }
	
  protected static class $MidProtocolChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MidProtocolChars__lit___62_$URLChars_lit___60_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-5180, 2, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5179, 1, "$URLChars", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5177, 0, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      builder.addAlternative(RascalRascal.prod__$MidProtocolChars__lit___62_$URLChars_lit___60_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$MidProtocolChars__lit___62_$URLChars_lit___60_(builder);
      
    }
  }
	
  protected static class $PatternWithAction {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__arbitrary_$PatternWithAction__pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_statement_$Statement_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5261, 4, "$Statement", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5259, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-5257, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5256, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5253, 0, "$Pattern", null, null);
      builder.addAlternative(RascalRascal.prod__arbitrary_$PatternWithAction__pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_statement_$Statement_, tmp);
	}
    protected static final void _init_prod__replacing_$PatternWithAction__pattern_$Pattern_$layouts_LAYOUTLIST_lit___61_62_$layouts_LAYOUTLIST_replacement_$Replacement_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5275, 4, "$Replacement", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5273, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-5271, 2, prod__lit___61_62__char_class___range__61_61_char_class___range__62_62_, new int[] {61,62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5270, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5267, 0, "$Pattern", null, null);
      builder.addAlternative(RascalRascal.prod__replacing_$PatternWithAction__pattern_$Pattern_$layouts_LAYOUTLIST_lit___61_62_$layouts_LAYOUTLIST_replacement_$Replacement_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__arbitrary_$PatternWithAction__pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_statement_$Statement_(builder);
      
        _init_prod__replacing_$PatternWithAction__pattern_$Pattern_$layouts_LAYOUTLIST_lit___61_62_$layouts_LAYOUTLIST_replacement_$Replacement_(builder);
      
    }
  }
	
  protected static class $DataTypeSelector {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__selector_$DataTypeSelector__sort_$QualifiedName_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_production_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5293, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5291, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-5289, 2, prod__lit___46__char_class___range__46_46_, new int[] {46}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5288, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5285, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__selector_$DataTypeSelector__sort_$QualifiedName_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_production_$Name_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__selector_$DataTypeSelector__sort_$QualifiedName_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_production_$Name_(builder);
      
    }
  }
	
  protected static class $StringTail {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__post_$StringTail__post_$PostStringChars_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5303, 0, "$PostStringChars", null, null);
      builder.addAlternative(RascalRascal.prod__post_$StringTail__post_$PostStringChars_, tmp);
	}
    protected static final void _init_prod__midTemplate_$StringTail__mid_$MidStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringTail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5319, 4, "$StringTail", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5317, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5314, 2, "$StringTemplate", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5312, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5309, 0, "$MidStringChars", null, null);
      builder.addAlternative(RascalRascal.prod__midTemplate_$StringTail__mid_$MidStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringTail_, tmp);
	}
    protected static final void _init_prod__midInterpolated_$StringTail__mid_$MidStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringTail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5335, 4, "$StringTail", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5333, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5330, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5328, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5325, 0, "$MidStringChars", null, null);
      builder.addAlternative(RascalRascal.prod__midInterpolated_$StringTail__mid_$MidStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringTail_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__post_$StringTail__post_$PostStringChars_(builder);
      
        _init_prod__midTemplate_$StringTail__mid_$MidStringChars_$layouts_LAYOUTLIST_template_$StringTemplate_$layouts_LAYOUTLIST_tail_$StringTail_(builder);
      
        _init_prod__midInterpolated_$StringTail__mid_$MidStringChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$StringTail_(builder);
      
    }
  }
	
  protected static class start__$Module {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__start__$Module__$layouts_LAYOUTLIST_top_$Module_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5352, 2, "$layouts_LAYOUTLIST", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5349, 1, "$Module", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5347, 0, "$layouts_LAYOUTLIST", null, null);
      builder.addAlternative(RascalRascal.prod__start__$Module__$layouts_LAYOUTLIST_top_$Module_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__start__$Module__$layouts_LAYOUTLIST_top_$Module_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $StringConstant {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$StringConstant__lit___34_iter_star__$StringCharacter_lit___34__tag__category___67_111_110_115_116_97_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-5387, 2, prod__lit___34__char_class___range__34_34_, new int[] {34}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-5386, 1, regular__iter_star__$StringCharacter, new NonTerminalStackNode<IConstructor>(-5385, 0, "$StringCharacter", null, null), false, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5383, 0, prod__lit___34__char_class___range__34_34_, new int[] {34}, null, null);
      builder.addAlternative(RascalRascal.prod__$StringConstant__lit___34_iter_star__$StringCharacter_lit___34__tag__category___67_111_110_115_116_97_110_116, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$StringConstant__lit___34_iter_star__$StringCharacter_lit___34__tag__category___67_111_110_115_116_97_110_116(builder);
      
    }
  }
	
  protected static class $LocalVariableDeclaration {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__dynamic_$LocalVariableDeclaration__lit_dynamic_$layouts_LAYOUTLIST_declarator_$Declarator_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5422, 2, "$Declarator", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5420, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5418, 0, prod__lit_dynamic__char_class___range__100_100_char_class___range__121_121_char_class___range__110_110_char_class___range__97_97_char_class___range__109_109_char_class___range__105_105_char_class___range__99_99_, new int[] {100,121,110,97,109,105,99}, null, null);
      builder.addAlternative(RascalRascal.prod__dynamic_$LocalVariableDeclaration__lit_dynamic_$layouts_LAYOUTLIST_declarator_$Declarator_, tmp);
	}
    protected static final void _init_prod__default_$LocalVariableDeclaration__declarator_$Declarator_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5428, 0, "$Declarator", null, null);
      builder.addAlternative(RascalRascal.prod__default_$LocalVariableDeclaration__declarator_$Declarator_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__dynamic_$LocalVariableDeclaration__lit_dynamic_$layouts_LAYOUTLIST_declarator_$Declarator_(builder);
      
        _init_prod__default_$LocalVariableDeclaration__declarator_$Declarator_(builder);
      
    }
  }
	
  protected static class $Parameters {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Parameters__lit___40_$layouts_LAYOUTLIST_formals_$Formals_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-5498, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5497, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5494, 2, "$Formals", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5492, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5490, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Parameters__lit___40_$layouts_LAYOUTLIST_formals_$Formals_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__varArgs_$Parameters__lit___40_$layouts_LAYOUTLIST_formals_$Formals_$layouts_LAYOUTLIST_lit___46_46_46_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-5513, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-5512, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-5510, 4, prod__lit___46_46_46__char_class___range__46_46_char_class___range__46_46_char_class___range__46_46_, new int[] {46,46,46}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5509, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5506, 2, "$Formals", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5504, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5502, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__varArgs_$Parameters__lit___40_$layouts_LAYOUTLIST_formals_$Formals_$layouts_LAYOUTLIST_lit___46_46_46_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Parameters__lit___40_$layouts_LAYOUTLIST_formals_$Formals_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__varArgs_$Parameters__lit___40_$layouts_LAYOUTLIST_formals_$Formals_$layouts_LAYOUTLIST_lit___46_46_46_$layouts_LAYOUTLIST_lit___41_(builder);
      
    }
  }
	
  protected static class $DatePart {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$DatePart__char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_49_char_class___range__48_57_char_class___range__48_51_char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[8];
      
      tmp[7] = new CharStackNode<IConstructor>(-5527, 7, new int[][]{{48,57}}, null, null);
      tmp[6] = new CharStackNode<IConstructor>(-5526, 6, new int[][]{{48,51}}, null, null);
      tmp[5] = new CharStackNode<IConstructor>(-5525, 5, new int[][]{{48,57}}, null, null);
      tmp[4] = new CharStackNode<IConstructor>(-5524, 4, new int[][]{{48,49}}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(-5523, 3, new int[][]{{48,57}}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-5522, 2, new int[][]{{48,57}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-5521, 1, new int[][]{{48,57}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-5520, 0, new int[][]{{48,57}}, null, null);
      builder.addAlternative(RascalRascal.prod__$DatePart__char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_49_char_class___range__48_57_char_class___range__48_51_char_class___range__48_57_, tmp);
	}
    protected static final void _init_prod__$DatePart__char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_lit___char_class___range__48_49_char_class___range__48_57_lit___char_class___range__48_51_char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[10];
      
      tmp[9] = new CharStackNode<IConstructor>(-5539, 9, new int[][]{{48,57}}, null, null);
      tmp[8] = new CharStackNode<IConstructor>(-5538, 8, new int[][]{{48,51}}, null, null);
      tmp[7] = new LiteralStackNode<IConstructor>(-5537, 7, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      tmp[6] = new CharStackNode<IConstructor>(-5536, 6, new int[][]{{48,57}}, null, null);
      tmp[5] = new CharStackNode<IConstructor>(-5535, 5, new int[][]{{48,49}}, null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-5534, 4, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(-5533, 3, new int[][]{{48,57}}, null, null);
      tmp[2] = new CharStackNode<IConstructor>(-5532, 2, new int[][]{{48,57}}, null, null);
      tmp[1] = new CharStackNode<IConstructor>(-5531, 1, new int[][]{{48,57}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(-5530, 0, new int[][]{{48,57}}, null, null);
      builder.addAlternative(RascalRascal.prod__$DatePart__char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_lit___char_class___range__48_49_char_class___range__48_57_lit___char_class___range__48_51_char_class___range__48_57_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$DatePart__char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_49_char_class___range__48_57_char_class___range__48_51_char_class___range__48_57_(builder);
      
        _init_prod__$DatePart__char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_char_class___range__48_57_lit___char_class___range__48_49_char_class___range__48_57_lit___char_class___range__48_51_char_class___range__48_57_(builder);
      
    }
  }
	
  protected static class $HeaderKeyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$HeaderKeyword__lit_lexical_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5589, 0, prod__lit_lexical__char_class___range__108_108_char_class___range__101_101_char_class___range__120_120_char_class___range__105_105_char_class___range__99_99_char_class___range__97_97_char_class___range__108_108_, new int[] {108,101,120,105,99,97,108}, null, null);
      builder.addAlternative(RascalRascal.prod__$HeaderKeyword__lit_lexical_, tmp);
	}
    protected static final void _init_prod__$HeaderKeyword__lit_layout_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5592, 0, prod__lit_layout__char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_, new int[] {108,97,121,111,117,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$HeaderKeyword__lit_layout_, tmp);
	}
    protected static final void _init_prod__$HeaderKeyword__lit_extend_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5595, 0, prod__lit_extend__char_class___range__101_101_char_class___range__120_120_char_class___range__116_116_char_class___range__101_101_char_class___range__110_110_char_class___range__100_100_, new int[] {101,120,116,101,110,100}, null, null);
      builder.addAlternative(RascalRascal.prod__$HeaderKeyword__lit_extend_, tmp);
	}
    protected static final void _init_prod__$HeaderKeyword__lit_syntax_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5598, 0, prod__lit_syntax__char_class___range__115_115_char_class___range__121_121_char_class___range__110_110_char_class___range__116_116_char_class___range__97_97_char_class___range__120_120_, new int[] {115,121,110,116,97,120}, null, null);
      builder.addAlternative(RascalRascal.prod__$HeaderKeyword__lit_syntax_, tmp);
	}
    protected static final void _init_prod__$HeaderKeyword__lit_keyword_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5601, 0, prod__lit_keyword__char_class___range__107_107_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_, new int[] {107,101,121,119,111,114,100}, null, null);
      builder.addAlternative(RascalRascal.prod__$HeaderKeyword__lit_keyword_, tmp);
	}
    protected static final void _init_prod__$HeaderKeyword__lit_import_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5604, 0, prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_, new int[] {105,109,112,111,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$HeaderKeyword__lit_import_, tmp);
	}
    protected static final void _init_prod__$HeaderKeyword__lit_start_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5607, 0, prod__lit_start__char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__116_116_, new int[] {115,116,97,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__$HeaderKeyword__lit_start_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$HeaderKeyword__lit_lexical_(builder);
      
        _init_prod__$HeaderKeyword__lit_layout_(builder);
      
        _init_prod__$HeaderKeyword__lit_extend_(builder);
      
        _init_prod__$HeaderKeyword__lit_syntax_(builder);
      
        _init_prod__$HeaderKeyword__lit_keyword_(builder);
      
        _init_prod__$HeaderKeyword__lit_import_(builder);
      
        _init_prod__$HeaderKeyword__lit_start_(builder);
      
    }
  }
	
  protected static class $Assignable {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__tuple_$Assignable__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-5671, 4, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5670, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-5667, 2, regular__iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-5661, 0, "$Assignable", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-5663, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-5664, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-5666, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5659, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5657, 0, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      builder.addAlternative(RascalRascal.prod__tuple_$Assignable__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_, tmp);
	}
    protected static final void _init_prod__bracket_$Assignable__lit___40_$layouts_LAYOUTLIST_arg_$Assignable_$layouts_LAYOUTLIST_lit___41__bracket(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-5683, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5682, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-5679, 2, "$Assignable", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5677, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5675, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__bracket_$Assignable__lit___40_$layouts_LAYOUTLIST_arg_$Assignable_$layouts_LAYOUTLIST_lit___41__bracket, tmp);
	}
    protected static final void _init_prod__constructor_$Assignable__name_$Name_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5688, 0, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5691, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-5692, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5694, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-5702, 4, regular__iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-5696, 0, "$Assignable", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-5698, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-5699, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-5701, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-5705, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-5706, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      builder.addAlternative(RascalRascal.prod__constructor_$Assignable__name_$Name_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__fieldAccess_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_field_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5719, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5717, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-5715, 2, prod__lit___46__char_class___range__46_46_, new int[] {46}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5714, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5711, 0, "$Assignable", null, null);
      builder.addAlternative(RascalRascal.prod__fieldAccess_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_field_$Name_, tmp);
	}
    protected static final void _init_prod__variable_$Assignable__qualifiedName_$QualifiedName_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5753, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__variable_$Assignable__qualifiedName_$QualifiedName_, tmp);
	}
    protected static final void _init_prod__annotation_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_annotation_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5733, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5731, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-5729, 2, prod__lit___64__char_class___range__64_64_, new int[] {64}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5728, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5725, 0, "$Assignable", null, null);
      builder.addAlternative(RascalRascal.prod__annotation_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_annotation_$Name_, tmp);
	}
    protected static final void _init_prod__ifDefinedOrDefault_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_defaultExpression_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5747, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5745, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-5743, 2, prod__lit___63__char_class___range__63_63_, new int[] {63}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5742, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5739, 0, "$Assignable", null, null);
      builder.addAlternative(RascalRascal.prod__ifDefinedOrDefault_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_defaultExpression_$Expression_, tmp);
	}
    protected static final void _init_prod__subscript_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_subscript_$Expression_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-5771, 6, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-5770, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-5767, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-5765, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-5763, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5762, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5759, 0, "$Assignable", null, null);
      builder.addAlternative(RascalRascal.prod__subscript_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_subscript_$Expression_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__tuple_$Assignable__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_(builder);
      
        _init_prod__bracket_$Assignable__lit___40_$layouts_LAYOUTLIST_arg_$Assignable_$layouts_LAYOUTLIST_lit___41__bracket(builder);
      
        _init_prod__constructor_$Assignable__name_$Name_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_seps__$Assignable__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__fieldAccess_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_field_$Name_(builder);
      
        _init_prod__variable_$Assignable__qualifiedName_$QualifiedName_(builder);
      
        _init_prod__annotation_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_annotation_$Name_(builder);
      
        _init_prod__ifDefinedOrDefault_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_defaultExpression_$Expression_(builder);
      
        _init_prod__subscript_$Assignable__receiver_$Assignable_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_subscript_$Expression_$layouts_LAYOUTLIST_lit___93_(builder);
      
    }
  }
	
  protected static class $FunctionModifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__test_$FunctionModifier__lit_test_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5779, 0, prod__lit_test__char_class___range__116_116_char_class___range__101_101_char_class___range__115_115_char_class___range__116_116_, new int[] {116,101,115,116}, null, null);
      builder.addAlternative(RascalRascal.prod__test_$FunctionModifier__lit_test_, tmp);
	}
    protected static final void _init_prod__default_$FunctionModifier__lit_default_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5783, 0, prod__lit_default__char_class___range__100_100_char_class___range__101_101_char_class___range__102_102_char_class___range__97_97_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_, new int[] {100,101,102,97,117,108,116}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$FunctionModifier__lit_default_, tmp);
	}
    protected static final void _init_prod__java_$FunctionModifier__lit_java_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5787, 0, prod__lit_java__char_class___range__106_106_char_class___range__97_97_char_class___range__118_118_char_class___range__97_97_, new int[] {106,97,118,97}, null, null);
      builder.addAlternative(RascalRascal.prod__java_$FunctionModifier__lit_java_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__test_$FunctionModifier__lit_test_(builder);
      
        _init_prod__default_$FunctionModifier__lit_default_(builder);
      
        _init_prod__java_$FunctionModifier__lit_java_(builder);
      
    }
  }
	
  protected static class $EvalCommand {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__declaration_$EvalCommand__declaration_$Declaration_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5796, 0, "$Declaration", null, null);
      builder.addAlternative(RascalRascal.prod__declaration_$EvalCommand__declaration_$Declaration_, tmp);
	}
    protected static final void _init_prod__statement_$EvalCommand__statement_$Statement_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5803, 0, "$Statement", null, null);
      builder.addAlternative(RascalRascal.prod__statement_$EvalCommand__statement_$Statement_, tmp);
	}
    protected static final void _init_prod__import_$EvalCommand__imported_$Import_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5809, 0, "$Import", null, null);
      builder.addAlternative(RascalRascal.prod__import_$EvalCommand__imported_$Import_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__declaration_$EvalCommand__declaration_$Declaration_(builder);
      
        _init_prod__statement_$EvalCommand__statement_$Statement_(builder);
      
        _init_prod__import_$EvalCommand__imported_$Import_(builder);
      
    }
  }
	
  protected static class $Assignment {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__addition_$Assignment__lit___43_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5830, 0, prod__lit___43_61__char_class___range__43_43_char_class___range__61_61_, new int[] {43,61}, null, null);
      builder.addAlternative(RascalRascal.prod__addition_$Assignment__lit___43_61_, tmp);
	}
    protected static final void _init_prod__product_$Assignment__lit___42_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5834, 0, prod__lit___42_61__char_class___range__42_42_char_class___range__61_61_, new int[] {42,61}, null, null);
      builder.addAlternative(RascalRascal.prod__product_$Assignment__lit___42_61_, tmp);
	}
    protected static final void _init_prod__division_$Assignment__lit___47_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5838, 0, prod__lit___47_61__char_class___range__47_47_char_class___range__61_61_, new int[] {47,61}, null, null);
      builder.addAlternative(RascalRascal.prod__division_$Assignment__lit___47_61_, tmp);
	}
    protected static final void _init_prod__ifDefined_$Assignment__lit___63_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5842, 0, prod__lit___63_61__char_class___range__63_63_char_class___range__61_61_, new int[] {63,61}, null, null);
      builder.addAlternative(RascalRascal.prod__ifDefined_$Assignment__lit___63_61_, tmp);
	}
    protected static final void _init_prod__default_$Assignment__lit___61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5846, 0, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Assignment__lit___61_, tmp);
	}
    protected static final void _init_prod__append_$Assignment__lit___60_60_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5850, 0, prod__lit___60_60_61__char_class___range__60_60_char_class___range__60_60_char_class___range__61_61_, new int[] {60,60,61}, null, null);
      builder.addAlternative(RascalRascal.prod__append_$Assignment__lit___60_60_61_, tmp);
	}
    protected static final void _init_prod__intersection_$Assignment__lit___38_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5854, 0, prod__lit___38_61__char_class___range__38_38_char_class___range__61_61_, new int[] {38,61}, null, null);
      builder.addAlternative(RascalRascal.prod__intersection_$Assignment__lit___38_61_, tmp);
	}
    protected static final void _init_prod__subtraction_$Assignment__lit___45_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-5858, 0, prod__lit___45_61__char_class___range__45_45_char_class___range__61_61_, new int[] {45,61}, null, null);
      builder.addAlternative(RascalRascal.prod__subtraction_$Assignment__lit___45_61_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__addition_$Assignment__lit___43_61_(builder);
      
        _init_prod__product_$Assignment__lit___42_61_(builder);
      
        _init_prod__division_$Assignment__lit___47_61_(builder);
      
        _init_prod__ifDefined_$Assignment__lit___63_61_(builder);
      
        _init_prod__default_$Assignment__lit___61_(builder);
      
        _init_prod__append_$Assignment__lit___60_60_61_(builder);
      
        _init_prod__intersection_$Assignment__lit___38_61_(builder);
      
        _init_prod__subtraction_$Assignment__lit___45_61_(builder);
      
    }
  }
	
  protected static class $ProtocolChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$ProtocolChars__char_class___range__124_124_$URLChars_lit___58_47_47_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-5822, 2, prod__lit___58_47_47__char_class___range__58_58_char_class___range__47_47_char_class___range__47_47_, new int[] {58,47,47}, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{9,10},{13,13},{32,32},{65,65},{160,160},{5760,5760},{8192,8192},{8239,8239},{8287,8287},{12288,12288}})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-5819, 1, "$URLChars", null, null);
      tmp[0] = new CharStackNode<IConstructor>(-5817, 0, new int[][]{{124,124}}, null, null);
      builder.addAlternative(RascalRascal.prod__$ProtocolChars__char_class___range__124_124_$URLChars_lit___58_47_47_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$ProtocolChars__char_class___range__124_124_$URLChars_lit___58_47_47_(builder);
      
    }
  }
	
  protected static class $Field {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__name_$Field__fieldName_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5899, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__name_$Field__fieldName_$Name_, tmp);
	}
    protected static final void _init_prod__index_$Field__fieldIndex_$IntegerLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-5905, 0, "$IntegerLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__index_$Field__fieldIndex_$IntegerLiteral_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__name_$Field__fieldName_$Name_(builder);
      
        _init_prod__index_$Field__fieldIndex_$IntegerLiteral_(builder);
      
    }
  }
	
  protected static class $Comment {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$Comment__lit___47_47_iter_star__char_class___range__0_9_range__11_16777215__tag__category___67_111_109_109_101_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new ListStackNode<IConstructor>(-5965, 1, regular__iter_star__char_class___range__0_9_range__11_16777215, new CharStackNode<IConstructor>(-5962, 0, new int[][]{{0,9},{11,16777215}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{9,9},{13,13},{32,32},{65,65},{160,160},{5760,5760},{8192,8192},{8239,8239},{8287,8287},{12288,12288}}), new AtEndOfLineRequirement()});
      tmp[0] = new LiteralStackNode<IConstructor>(-5961, 0, prod__lit___47_47__char_class___range__47_47_char_class___range__47_47_, new int[] {47,47}, null, null);
      builder.addAlternative(RascalRascal.prod__$Comment__lit___47_47_iter_star__char_class___range__0_9_range__11_16777215__tag__category___67_111_109_109_101_110_116, tmp);
	}
    protected static final void _init_prod__$Comment__lit___47_42_iter_star__alt___char_class___range__42_42_char_class___range__0_41_range__43_16777215_lit___42_47__tag__category___67_111_109_109_101_110_116(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-5975, 2, prod__lit___42_47__char_class___range__42_42_char_class___range__47_47_, new int[] {42,47}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(-5974, 1, regular__iter_star__alt___char_class___range__42_42_char_class___range__0_41_range__43_16777215, new AlternativeStackNode<IConstructor>(-5973, 0, regular__alt___char_class___range__42_42_char_class___range__0_41_range__43_16777215, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(-5971, 0, new int[][]{{42,42}}, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{47,47}})}), new CharStackNode<IConstructor>(-5972, 0, new int[][]{{0,41},{43,16777215}}, null, null)}, null, null), false, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-5968, 0, prod__lit___47_42__char_class___range__47_47_char_class___range__42_42_, new int[] {47,42}, null, null);
      builder.addAlternative(RascalRascal.prod__$Comment__lit___47_42_iter_star__alt___char_class___range__42_42_char_class___range__0_41_range__43_16777215_lit___42_47__tag__category___67_111_109_109_101_110_116, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$Comment__lit___47_47_iter_star__char_class___range__0_9_range__11_16777215__tag__category___67_111_109_109_101_110_116(builder);
      
        _init_prod__$Comment__lit___47_42_iter_star__alt___char_class___range__42_42_char_class___range__0_41_range__43_16777215_lit___42_47__tag__category___67_111_109_109_101_110_116(builder);
      
    }
  }
	
  protected static class $Label {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Label__name_$Name_$layouts_LAYOUTLIST_lit___58_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-6030, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6029, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6026, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__default_$Label__name_$Name_$layouts_LAYOUTLIST_lit___58_, tmp);
	}
    protected static final void _init_prod__empty_$Label__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(-6033, 0);
      builder.addAlternative(RascalRascal.prod__empty_$Label__, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Label__name_$Name_$layouts_LAYOUTLIST_lit___58_(builder);
      
        _init_prod__empty_$Label__(builder);
      
    }
  }
	
  protected static class $ProtocolPart {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__nonInterpolated_$ProtocolPart__protocolChars_$ProtocolChars_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6042, 0, "$ProtocolChars", null, null);
      builder.addAlternative(RascalRascal.prod__nonInterpolated_$ProtocolPart__protocolChars_$ProtocolChars_, tmp);
	}
    protected static final void _init_prod__interpolated_$ProtocolPart__pre_$PreProtocolChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$ProtocolTail_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6058, 4, "$ProtocolTail", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6056, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6053, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6051, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6048, 0, "$PreProtocolChars", null, null);
      builder.addAlternative(RascalRascal.prod__interpolated_$ProtocolPart__pre_$PreProtocolChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$ProtocolTail_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__nonInterpolated_$ProtocolPart__protocolChars_$ProtocolChars_(builder);
      
        _init_prod__interpolated_$ProtocolPart__pre_$PreProtocolChars_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_tail_$ProtocolTail_(builder);
      
    }
  }
	
  protected static class $PreModule {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$PreModule__header_$Header_$layouts_LAYOUTLIST_empty_$layouts_LAYOUTLIST_rest_$Rest_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6108, 4, "$Rest", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6106, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new EmptyStackNode<IConstructor>(-6104, 2, regular__empty, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {108,101,120,105,99,97,108}), new StringFollowRestriction(new int[] {105,109,112,111,114,116}), new StringFollowRestriction(new int[] {115,116,97,114,116}), new StringFollowRestriction(new int[] {115,121,110,116,97,120}), new StringFollowRestriction(new int[] {108,97,121,111,117,116}), new StringFollowRestriction(new int[] {101,120,116,101,110,100}), new StringFollowRestriction(new int[] {107,101,121,119,111,114,100})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6100, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6097, 0, "$Header", null, null);
      builder.addAlternative(RascalRascal.prod__default_$PreModule__header_$Header_$layouts_LAYOUTLIST_empty_$layouts_LAYOUTLIST_rest_$Rest_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$PreModule__header_$Header_$layouts_LAYOUTLIST_empty_$layouts_LAYOUTLIST_rest_$Rest_(builder);
      
    }
  }
	
  protected static class $TagString {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$TagString__lit___123_contents_iter_star__alt___$TagString_char_class___range__0_122_range__124_124_range__126_16777215_seq___lit___92_char_class___range__123_123_range__125_125_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-6193, 2, prod__lit___125__char_class___range__125_125_, new int[] {125}, new IEnterFilter[] {new StringPrecedeRestriction(new int[] {92})}, null);
      tmp[1] = new ListStackNode<IConstructor>(-6189, 1, regular__iter_star__alt___$TagString_char_class___range__0_122_range__124_124_range__126_16777215_seq___lit___92_char_class___range__123_123_range__125_125, new AlternativeStackNode<IConstructor>(-6188, 0, regular__alt___$TagString_char_class___range__0_122_range__124_124_range__126_16777215_seq___lit___92_char_class___range__123_123_range__125_125, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6183, 0, "$TagString", null, null), new CharStackNode<IConstructor>(-6184, 0, new int[][]{{0,122},{124,124},{126,16777215}}, null, null), new SequenceStackNode<IConstructor>(-6187, 0, regular__seq___lit___92_char_class___range__123_123_range__125_125, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new LiteralStackNode<IConstructor>(-6185, 0, prod__lit___92__char_class___range__92_92_, new int[] {92}, null, null), new CharStackNode<IConstructor>(-6186, 1, new int[][]{{123,123},{125,125}}, null, null)}, null, null)}, null, null), false, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6181, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, new IEnterFilter[] {new StringPrecedeRestriction(new int[] {92})}, null);
      builder.addAlternative(RascalRascal.prod__$TagString__lit___123_contents_iter_star__alt___$TagString_char_class___range__0_122_range__124_124_range__126_16777215_seq___lit___92_char_class___range__123_123_range__125_125_lit___125_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$TagString__lit___123_contents_iter_star__alt___$TagString_char_class___range__0_122_range__124_124_range__126_16777215_seq___lit___92_char_class___range__123_123_range__125_125_lit___125_(builder);
      
    }
  }
	
  protected static class $Nonterminal {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$Nonterminal__char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new ListStackNode<IConstructor>(-6208, 1, regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, new CharStackNode<IConstructor>(-6203, 0, new int[][]{{48,57},{65,90},{95,95},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      tmp[0] = new CharStackNode<IConstructor>(-6202, 0, new int[][]{{65,90}}, new IEnterFilter[] {new CharPrecedeRestriction(new int[][]{{65,90}})}, null);
      builder.addAlternative(RascalRascal.prod__$Nonterminal__char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$Nonterminal__char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(builder);
      
    }
  }
	
  protected static class $Mapping__$Pattern {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Mapping__$Pattern__from_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Pattern_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6253, 4, "$Pattern", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6251, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6249, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6248, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6245, 0, "$Pattern", null, null);
      builder.addAlternative(RascalRascal.prod__default_$Mapping__$Pattern__from_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Pattern_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Mapping__$Pattern__from_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_to_$Pattern_(builder);
      
    }
  }
	
  protected static class $Expression {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__transitiveReflexiveClosure_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___42_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-6369, 2, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {61})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6366, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6363, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__transitiveReflexiveClosure_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___42_, tmp);
	}
    protected static final void _init_prod__asType_$Expression__lit___91_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___93_$layouts_LAYOUTLIST_argument_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-6930, 6, "$Expression", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6927, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-6925, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6924, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6921, 2, "$Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6919, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6917, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(RascalRascal.prod__asType_$Expression__lit___91_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___93_$layouts_LAYOUTLIST_argument_$Expression_, tmp);
	}
    protected static final void _init_prod__addition_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___43_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7067, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7064, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7062, 2, prod__lit___43__char_class___range__43_43_, new int[] {43}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7061, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7058, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__addition_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___43_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__appendAfter_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_60_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7083, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7081, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7079, 2, prod__lit___60_60__char_class___range__60_60_char_class___range__60_60_, new int[] {60,60}, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {61})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7076, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7073, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__appendAfter_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_60_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__lessThan_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7189, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7187, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7185, 2, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {45})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7182, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7179, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__lessThan_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__insertBefore_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7097, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7095, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7093, 2, prod__lit___62_62__char_class___range__62_62_char_class___range__62_62_, new int[] {62,62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7092, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7089, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__insertBefore_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__ifThenElse_$Expression__condition_$Expression_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_thenExp_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_elseExp_$Expression__assoc__right(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new NonTerminalStackNode<IConstructor>(-7374, 8, "$Expression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-7372, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-7370, 6, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7369, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7366, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7364, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7362, 2, prod__lit___63__char_class___range__63_63_, new int[] {63}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7361, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7358, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__ifThenElse_$Expression__condition_$Expression_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_thenExp_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_elseExp_$Expression__assoc__right, tmp);
	}
    protected static final void _init_prod__negation_$Expression__lit___33_$layouts_LAYOUTLIST_argument_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6903, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6900, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6898, 0, prod__lit___33__char_class___range__33_33_, new int[] {33}, null, null);
      builder.addAlternative(RascalRascal.prod__negation_$Expression__lit___33_$layouts_LAYOUTLIST_argument_$Expression_, tmp);
	}
    protected static final void _init_prod__qualifiedName_$Expression__qualifiedName_$QualifiedName_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6574, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__qualifiedName_$Expression__qualifiedName_$QualifiedName_, tmp);
	}
    protected static final void _init_prod__slice_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_optFirst_$OptionalExpression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_optLast_$OptionalExpression_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(-6601, 10, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-6600, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-6597, 8, "$OptionalExpression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6595, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-6593, 6, prod__lit___46_46__char_class___range__46_46_char_class___range__46_46_, new int[] {46,46}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6592, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6589, 4, "$OptionalExpression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6587, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6585, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6584, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6582, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__slice_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_optFirst_$OptionalExpression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_optLast_$OptionalExpression_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__subtraction_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7052, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7050, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7048, 2, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7047, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7044, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__subtraction_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__intersection_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___38_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7034, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7032, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7030, 2, prod__lit___38__char_class___range__38_38_, new int[] {38}, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {38})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7027, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7024, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__intersection_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___38_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__greaterThan_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7159, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7157, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7155, 2, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7154, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7151, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__greaterThan_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__enumerator_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___60_45_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7279, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7277, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7275, 2, prod__lit___60_45__char_class___range__60_60_char_class___range__45_45_, new int[] {60,45}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7274, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7271, 0, "$Pattern", null, null);
      builder.addAlternative(RascalRascal.prod__enumerator_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___60_45_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__greaterThanOrEq_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7173, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7171, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7169, 2, prod__lit___62_61__char_class___range__62_62_char_class___range__61_61_, new int[] {62,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7168, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7165, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__greaterThanOrEq_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__closure_$Expression__type_$Type_$layouts_LAYOUTLIST_parameters_$Parameters_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-6771, 8, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6770, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-6767, 6, regular__iter_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6764, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6766, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6762, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-6760, 4, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6759, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6756, 2, "$Parameters", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6754, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6751, 0, "$Type", null, null);
      builder.addAlternative(RascalRascal.prod__closure_$Expression__type_$Type_$layouts_LAYOUTLIST_parameters_$Parameters_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__notIn_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_notin_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7142, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7140, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7138, 2, prod__lit_notin__char_class___range__110_110_char_class___range__111_111_char_class___range__116_116_char_class___range__105_105_char_class___range__110_110_, new int[] {110,111,116,105,110}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7137, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7134, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__notIn_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_notin_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__tuple_$Expression__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-6789, 4, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6788, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-6785, 2, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6779, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6781, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6782, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6784, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6777, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6775, 0, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      builder.addAlternative(RascalRascal.prod__tuple_$Expression__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_, tmp);
	}
    protected static final void _init_prod__match_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_61_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7293, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7291, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7289, 2, prod__lit___58_61__char_class___range__58_58_char_class___range__61_61_, new int[] {58,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7288, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7285, 0, "$Pattern", null, null);
      builder.addAlternative(RascalRascal.prod__match_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_61_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__and_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___38_38_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7338, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7336, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7334, 2, prod__lit___38_38__char_class___range__38_38_char_class___range__38_38_, new int[] {38,38}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7333, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7330, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__and_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___38_38_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__in_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_in_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7128, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7126, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7124, 2, prod__lit_in__char_class___range__105_105_char_class___range__110_110_, new int[] {105,110}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7123, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7120, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__in_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_in_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__list_$Expression__lit___91_$layouts_LAYOUTLIST_elements_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-6393, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6392, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-6389, 2, regular__iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6383, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6385, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6386, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6388, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6381, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6379, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(RascalRascal.prod__list_$Expression__lit___91_$layouts_LAYOUTLIST_elements_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__visit_$Expression__label_$Label_$layouts_LAYOUTLIST_visit_$Visit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6409, 2, "$Visit", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6407, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6404, 0, "$Label", null, null);
      builder.addAlternative(RascalRascal.prod__visit_$Expression__label_$Label_$layouts_LAYOUTLIST_visit_$Visit_, tmp);
	}
    protected static final void _init_prod__voidClosure_$Expression__parameters_$Parameters_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-6497, 6, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6496, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-6493, 4, regular__iter_star_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6490, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6492, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6488, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6486, 2, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6485, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6482, 0, "$Parameters", null, null);
      builder.addAlternative(RascalRascal.prod__voidClosure_$Expression__parameters_$Parameters_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__stepRange_$Expression__lit___91_$layouts_LAYOUTLIST_first_$Expression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_second_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_last_$Expression_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-6453, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6455, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6457, 2, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6460, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-6461, 4, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6463, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-6465, 6, "$Expression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6468, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-6469, 8, prod__lit___46_46__char_class___range__46_46_char_class___range__46_46_, new int[] {46,46}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-6471, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new NonTerminalStackNode<IConstructor>(-6473, 10, "$Expression", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-6476, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new LiteralStackNode<IConstructor>(-6477, 12, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      builder.addAlternative(RascalRascal.prod__stepRange_$Expression__lit___91_$layouts_LAYOUTLIST_first_$Expression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_second_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_last_$Expression_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__has_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit_has_$layouts_LAYOUTLIST_name_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6517, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6515, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6513, 2, prod__lit_has__char_class___range__104_104_char_class___range__97_97_char_class___range__115_115_, new int[] {104,97,115}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6512, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6509, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__has_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit_has_$layouts_LAYOUTLIST_name_$Name_, tmp);
	}
    protected static final void _init_prod__it_$Expression__lit_it_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-6504, 0, prod__lit_it__char_class___range__105_105_char_class___range__116_116_, new int[] {105,116}, new IEnterFilter[] {new CharPrecedeRestriction(new int[][]{{65,90},{95,95},{97,122}})}, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{65,90},{95,95},{97,122}})});
      builder.addAlternative(RascalRascal.prod__it_$Expression__lit_it_, tmp);
	}
    protected static final void _init_prod__fieldProject_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___60_$layouts_LAYOUTLIST_fields_iter_seps__$Field__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-6659, 6, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6658, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-6655, 4, regular__iter_seps__$Field__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6649, 0, "$Field", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6651, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6652, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6654, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6647, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6645, 2, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6644, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6642, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__fieldProject_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___60_$layouts_LAYOUTLIST_fields_iter_seps__$Field__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_, tmp);
	}
    protected static final void _init_prod__callOrTree_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-6746, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6745, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-6742, 4, regular__iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6736, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6738, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6739, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6741, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6734, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6732, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6731, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6728, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__callOrTree_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__lessThanOrEq_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7203, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7201, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7199, 2, prod__lit___60_61__char_class___range__60_60_char_class___range__61_61_, new int[] {60,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7198, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7195, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__lessThanOrEq_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__product_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___42_$layouts_LAYOUTLIST_empty_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-7018, 6, "$Expression", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7015, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new EmptyStackNode<IConstructor>(-7013, 4, regular__empty, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {42})});
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7010, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7008, 2, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7007, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7004, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__product_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___42_$layouts_LAYOUTLIST_empty_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__any_$Expression__lit_any_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-6844, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6843, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-6840, 4, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6834, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6836, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6837, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6839, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6832, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6830, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6829, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6827, 0, prod__lit_any__char_class___range__97_97_char_class___range__110_110_char_class___range__121_121_, new int[] {97,110,121}, null, null);
      builder.addAlternative(RascalRascal.prod__any_$Expression__lit_any_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__range_$Expression__lit___91_$layouts_LAYOUTLIST_first_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_last_$Expression_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-6307, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6309, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6311, 2, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6314, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-6315, 4, prod__lit___46_46__char_class___range__46_46_char_class___range__46_46_, new int[] {46,46}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6317, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-6319, 6, "$Expression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6322, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-6323, 8, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      builder.addAlternative(RascalRascal.prod__range_$Expression__lit___91_$layouts_LAYOUTLIST_first_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_last_$Expression_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__literal_$Expression__literal_$Literal_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6374, 0, "$Literal", null, null);
      builder.addAlternative(RascalRascal.prod__literal_$Expression__literal_$Literal_, tmp);
	}
    protected static final void _init_prod__division_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___47_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6970, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6968, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6966, 2, prod__lit___47__char_class___range__47_47_, new int[] {47}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6965, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6962, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__division_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___47_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__comprehension_$Expression__comprehension_$Comprehension_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6398, 0, "$Comprehension", null, null);
      builder.addAlternative(RascalRascal.prod__comprehension_$Expression__comprehension_$Comprehension_, tmp);
	}
    protected static final void _init_prod__set_$Expression__lit___123_$layouts_LAYOUTLIST_elements_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-6428, 4, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6427, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-6424, 2, regular__iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6418, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6420, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6421, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6423, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6416, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6414, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      builder.addAlternative(RascalRascal.prod__set_$Expression__lit___123_$layouts_LAYOUTLIST_elements_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__remainder_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___37_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6998, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6996, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6994, 2, prod__lit___37__char_class___range__37_37_, new int[] {37}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6993, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6990, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__remainder_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___37_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__ifDefinedOtherwise_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7248, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7246, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7244, 2, prod__lit___63__char_class___range__63_63_, new int[] {63}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7243, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7240, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__ifDefinedOtherwise_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__composition_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_o_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6953, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6951, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6949, 2, prod__lit_o__char_class___range__111_111_, new int[] {111}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6948, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6945, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__composition_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_o_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__implication_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___61_61_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7324, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7322, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7320, 2, prod__lit___61_61_62__char_class___range__61_61_char_class___range__61_61_char_class___range__62_62_, new int[] {61,61,62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7319, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7316, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__implication_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___61_61_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__nonEquals_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___33_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7234, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7232, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7230, 2, prod__lit___33_61__char_class___range__33_33_char_class___range__61_61_, new int[] {33,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7229, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7226, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__nonEquals_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___33_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__fieldAccess_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_field_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6568, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6566, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6564, 2, prod__lit___46__char_class___range__46_46_, new int[] {46}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6563, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6560, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__fieldAccess_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_field_$Name_, tmp);
	}
    protected static final void _init_prod__bracket_$Expression__lit___40_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___41__bracket(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-6555, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6554, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6551, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6549, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6547, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__bracket_$Expression__lit___40_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___41__bracket, tmp);
	}
    protected static final void _init_prod__noMatch_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___33_58_61_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7265, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7263, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7261, 2, prod__lit___33_58_61__char_class___range__33_33_char_class___range__58_58_char_class___range__61_61_, new int[] {33,58,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7260, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7257, 0, "$Pattern", null, null);
      builder.addAlternative(RascalRascal.prod__noMatch_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___33_58_61_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__getAnnotation_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6614, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6612, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6610, 2, prod__lit___64__char_class___range__64_64_, new int[] {64}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6609, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6606, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__getAnnotation_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_, tmp);
	}
    protected static final void _init_prod__equivalence_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_61_61_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7310, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7308, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7306, 2, prod__lit___60_61_61_62__char_class___range__60_60_char_class___range__61_61_char_class___range__61_61_char_class___range__62_62_, new int[] {60,61,61,62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7305, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7302, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__equivalence_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_61_61_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__isDefined_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___63_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-6892, 2, prod__lit___63__char_class___range__63_63_, new int[] {63}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6891, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6888, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__isDefined_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___63_, tmp);
	}
    protected static final void _init_prod__negative_$Expression__lit___$layouts_LAYOUTLIST_argument_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6939, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6937, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6935, 0, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      builder.addAlternative(RascalRascal.prod__negative_$Expression__lit___$layouts_LAYOUTLIST_argument_$Expression_, tmp);
	}
    protected static final void _init_prod__nonEmptyBlock_$Expression__lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-6722, 4, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6721, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-6718, 2, regular__iter_seps__$Statement__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6715, 0, "$Statement", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6717, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6713, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6711, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      builder.addAlternative(RascalRascal.prod__nonEmptyBlock_$Expression__lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__sliceStep_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_optFirst_$OptionalExpression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_second_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_optLast_$OptionalExpression_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new LiteralStackNode<IConstructor>(-6823, 14, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(-6822, 13, "$layouts_LAYOUTLIST", null, null);
      tmp[12] = new NonTerminalStackNode<IConstructor>(-6819, 12, "$OptionalExpression", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-6817, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(-6815, 10, prod__lit___46_46__char_class___range__46_46_char_class___range__46_46_, new int[] {46,46}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-6814, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-6811, 8, "$Expression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6809, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-6807, 6, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6806, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6803, 4, "$OptionalExpression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6801, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6799, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6798, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6796, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__sliceStep_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_optFirst_$OptionalExpression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_second_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_optLast_$OptionalExpression_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__reifiedType_$Expression__lit_type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_symbol_$Expression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_definitions_$Expression_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(-6303, 10, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-6302, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-6299, 8, "$Expression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6297, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-6295, 6, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6294, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6291, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6289, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6287, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6286, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6284, 0, prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_, new int[] {116,121,112,101}, null, null);
      builder.addAlternative(RascalRascal.prod__reifiedType_$Expression__lit_type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_symbol_$Expression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_definitions_$Expression_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__transitiveClosure_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___43_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-6334, 2, prod__lit___43__char_class___range__43_43_, new int[] {43}, null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {61})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6331, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6328, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__transitiveClosure_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___43_, tmp);
	}
    protected static final void _init_prod__subscript_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_subscripts_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-6358, 6, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6357, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-6354, 4, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6348, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6350, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6351, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6353, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6346, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6344, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6343, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6341, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__subscript_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_subscripts_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__join_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_join_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6984, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6982, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6980, 2, prod__lit_join__char_class___range__106_106_char_class___range__111_111_char_class___range__105_105_char_class___range__110_110_, new int[] {106,111,105,110}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6979, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6976, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__join_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_join_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__all_$Expression__lit_all_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-6449, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6448, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-6445, 4, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6439, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6441, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6442, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6444, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6437, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6435, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6434, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6432, 0, prod__lit_all__char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_, new int[] {97,108,108}, null, null);
      builder.addAlternative(RascalRascal.prod__all_$Expression__lit_all_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__equals_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___61_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7220, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7218, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7216, 2, prod__lit___61_61__char_class___range__61_61_char_class___range__61_61_, new int[] {61,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7215, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7212, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__equals_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___61_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__fieldUpdate_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_key_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_replacement_$Expression_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(-6543, 10, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-6542, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(-6539, 8, "$Expression", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6537, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-6535, 6, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6534, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6531, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6529, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6527, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6526, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6523, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__fieldUpdate_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_key_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_replacement_$Expression_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__splice_$Expression__lit___42_$layouts_LAYOUTLIST_argument_$Expression__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6912, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6910, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6908, 0, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      builder.addAlternative(RascalRascal.prod__splice_$Expression__lit___42_$layouts_LAYOUTLIST_argument_$Expression__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__map_$Expression__lit___40_$layouts_LAYOUTLIST_mappings_iter_star_seps__$Mapping__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(-6635, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6634, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(-6631, 2, regular__iter_star_seps__$Mapping__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6625, 0, "$Mapping__$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6627, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6628, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6630, 3, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6621, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6619, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__map_$Expression__lit___40_$layouts_LAYOUTLIST_mappings_iter_star_seps__$Mapping__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__is_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit_is_$layouts_LAYOUTLIST_name_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-6672, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6670, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6668, 2, prod__lit_is__char_class___range__105_105_char_class___range__115_115_, new int[] {105,115}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6667, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6664, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__is_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit_is_$layouts_LAYOUTLIST_name_$Name_, tmp);
	}
    protected static final void _init_prod__reducer_$Expression__lit___40_$layouts_LAYOUTLIST_init_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_result_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[12] = new LiteralStackNode<IConstructor>(-6707, 12, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-6706, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new SeparatedListStackNode<IConstructor>(-6703, 10, regular__iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-6697, 0, "$Expression", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-6699, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-6700, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-6702, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-6695, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-6693, 8, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6692, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-6689, 6, "$Expression", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6687, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-6685, 4, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6684, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6681, 2, "$Expression", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6679, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6677, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__reducer_$Expression__lit___40_$layouts_LAYOUTLIST_init_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_result_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__or_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___124_124_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7352, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7350, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7348, 2, prod__lit___124_124__char_class___range__124_124_char_class___range__124_124_, new int[] {124,124}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7347, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7344, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__or_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___124_124_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__modulo_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_mod_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7111, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7109, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7107, 2, prod__lit_mod__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_, new int[] {109,111,100}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7106, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7103, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__modulo_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_mod_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left, tmp);
	}
    protected static final void _init_prod__setAnnotation_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_value_$Expression_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[12] = new LiteralStackNode<IConstructor>(-6872, 12, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(-6871, 11, "$layouts_LAYOUTLIST", null, null);
      tmp[10] = new NonTerminalStackNode<IConstructor>(-6868, 10, "$Expression", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-6866, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-6864, 8, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-6863, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-6860, 6, "$Name", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-6858, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-6856, 4, prod__lit___64__char_class___range__64_64_, new int[] {64}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-6855, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-6853, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6852, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-6849, 0, "$Expression", null, null);
      builder.addAlternative(RascalRascal.prod__setAnnotation_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_value_$Expression_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__reifyType_$Expression__lit___35_$layouts_LAYOUTLIST_type_$Type_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-6883, 2, "$Type", null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {91})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-6878, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-6876, 0, prod__lit___35__char_class___range__35_35_, new int[] {35}, null, null);
      builder.addAlternative(RascalRascal.prod__reifyType_$Expression__lit___35_$layouts_LAYOUTLIST_type_$Type_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__transitiveReflexiveClosure_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___42_(builder);
      
        _init_prod__asType_$Expression__lit___91_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_lit___93_$layouts_LAYOUTLIST_argument_$Expression_(builder);
      
        _init_prod__addition_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___43_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__appendAfter_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_60_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__lessThan_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__insertBefore_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__ifThenElse_$Expression__condition_$Expression_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_thenExp_$Expression_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_elseExp_$Expression__assoc__right(builder);
      
        _init_prod__negation_$Expression__lit___33_$layouts_LAYOUTLIST_argument_$Expression_(builder);
      
        _init_prod__qualifiedName_$Expression__qualifiedName_$QualifiedName_(builder);
      
        _init_prod__slice_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_optFirst_$OptionalExpression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_optLast_$OptionalExpression_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__subtraction_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__intersection_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___38_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__greaterThan_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__enumerator_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___60_45_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc(builder);
      
        _init_prod__greaterThanOrEq_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___62_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__closure_$Expression__type_$Type_$layouts_LAYOUTLIST_parameters_$Parameters_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__notIn_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_notin_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__tuple_$Expression__lit___60_$layouts_LAYOUTLIST_elements_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_(builder);
      
        _init_prod__match_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_61_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc(builder);
      
        _init_prod__and_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___38_38_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__in_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_in_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__list_$Expression__lit___91_$layouts_LAYOUTLIST_elements_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__visit_$Expression__label_$Label_$layouts_LAYOUTLIST_visit_$Visit_(builder);
      
        _init_prod__voidClosure_$Expression__parameters_$Parameters_$layouts_LAYOUTLIST_lit___123_$layouts_LAYOUTLIST_statements_iter_star_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__stepRange_$Expression__lit___91_$layouts_LAYOUTLIST_first_$Expression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_second_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_last_$Expression_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__has_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit_has_$layouts_LAYOUTLIST_name_$Name_(builder);
      
        _init_prod__it_$Expression__lit_it_(builder);
      
        _init_prod__fieldProject_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___60_$layouts_LAYOUTLIST_fields_iter_seps__$Field__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___62_(builder);
      
        _init_prod__callOrTree_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_arguments_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__lessThanOrEq_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__product_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___42_$layouts_LAYOUTLIST_empty_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__any_$Expression__lit_any_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__range_$Expression__lit___91_$layouts_LAYOUTLIST_first_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_last_$Expression_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__literal_$Expression__literal_$Literal_(builder);
      
        _init_prod__division_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___47_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__comprehension_$Expression__comprehension_$Comprehension_(builder);
      
        _init_prod__set_$Expression__lit___123_$layouts_LAYOUTLIST_elements_iter_star_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__remainder_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___37_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__ifDefinedOtherwise_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___63_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__composition_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_o_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__implication_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___61_61_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__nonEquals_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___33_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__fieldAccess_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___46_$layouts_LAYOUTLIST_field_$Name_(builder);
      
        _init_prod__bracket_$Expression__lit___40_$layouts_LAYOUTLIST_expression_$Expression_$layouts_LAYOUTLIST_lit___41__bracket(builder);
      
        _init_prod__noMatch_$Expression__pattern_$Pattern_$layouts_LAYOUTLIST_lit___33_58_61_$layouts_LAYOUTLIST_expression_$Expression__assoc__non_assoc(builder);
      
        _init_prod__getAnnotation_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_(builder);
      
        _init_prod__equivalence_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___60_61_61_62_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__isDefined_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___63_(builder);
      
        _init_prod__negative_$Expression__lit___$layouts_LAYOUTLIST_argument_$Expression_(builder);
      
        _init_prod__nonEmptyBlock_$Expression__lit___123_$layouts_LAYOUTLIST_statements_iter_seps__$Statement__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__sliceStep_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_optFirst_$OptionalExpression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_second_$Expression_$layouts_LAYOUTLIST_lit___46_46_$layouts_LAYOUTLIST_optLast_$OptionalExpression_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__reifiedType_$Expression__lit_type_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_symbol_$Expression_$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_definitions_$Expression_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__transitiveClosure_$Expression__argument_$Expression_$layouts_LAYOUTLIST_lit___43_(builder);
      
        _init_prod__subscript_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_subscripts_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__join_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_join_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__all_$Expression__lit_all_$layouts_LAYOUTLIST_lit___40_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__equals_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___61_61_$layouts_LAYOUTLIST_rhs_$Expression__assoc__non_assoc(builder);
      
        _init_prod__fieldUpdate_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_key_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_replacement_$Expression_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__splice_$Expression__lit___42_$layouts_LAYOUTLIST_argument_$Expression__assoc__non_assoc(builder);
      
        _init_prod__map_$Expression__lit___40_$layouts_LAYOUTLIST_mappings_iter_star_seps__$Mapping__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__is_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit_is_$layouts_LAYOUTLIST_name_$Name_(builder);
      
        _init_prod__reducer_$Expression__lit___40_$layouts_LAYOUTLIST_init_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_result_$Expression_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_generators_iter_seps__$Expression__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__or_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit___124_124_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__modulo_$Expression__lhs_$Expression_$layouts_LAYOUTLIST_lit_mod_$layouts_LAYOUTLIST_rhs_$Expression__assoc__left(builder);
      
        _init_prod__setAnnotation_$Expression__expression_$Expression_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_value_$Expression_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__reifyType_$Expression__lit___35_$layouts_LAYOUTLIST_type_$Type_(builder);
      
    }
  }
	
  protected static class $ImportedModule {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__actuals_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_actuals_$ModuleActuals_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7438, 2, "$ModuleActuals", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7436, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7433, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__actuals_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_actuals_$ModuleActuals_, tmp);
	}
    protected static final void _init_prod__actualsRenaming_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_actuals_$ModuleActuals_$layouts_LAYOUTLIST_renamings_$Renamings_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7454, 4, "$Renamings", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7452, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7449, 2, "$ModuleActuals", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7447, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7444, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__actualsRenaming_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_actuals_$ModuleActuals_$layouts_LAYOUTLIST_renamings_$Renamings_, tmp);
	}
    protected static final void _init_prod__default_$ImportedModule__name_$QualifiedName_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7460, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__default_$ImportedModule__name_$QualifiedName_, tmp);
	}
    protected static final void _init_prod__renamings_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_renamings_$Renamings_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7471, 2, "$Renamings", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7469, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7466, 0, "$QualifiedName", null, null);
      builder.addAlternative(RascalRascal.prod__renamings_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_renamings_$Renamings_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__actuals_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_actuals_$ModuleActuals_(builder);
      
        _init_prod__actualsRenaming_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_actuals_$ModuleActuals_$layouts_LAYOUTLIST_renamings_$Renamings_(builder);
      
        _init_prod__default_$ImportedModule__name_$QualifiedName_(builder);
      
        _init_prod__renamings_$ImportedModule__name_$QualifiedName_$layouts_LAYOUTLIST_renamings_$Renamings_(builder);
      
    }
  }
	
  protected static class $Commands {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__list_$Commands__commands_iter_seps__$EvalCommand__$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SeparatedListStackNode<IConstructor>(-7526, 0, regular__iter_seps__$EvalCommand__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-7523, 0, "$EvalCommand", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-7525, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      builder.addAlternative(RascalRascal.prod__list_$Commands__commands_iter_seps__$EvalCommand__$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__list_$Commands__commands_iter_seps__$EvalCommand__$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $Header {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Header__tags_$Tags_$layouts_LAYOUTLIST_lit_module_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_imports_iter_star_seps__$Import__$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new SeparatedListStackNode<IConstructor>(-7568, 6, regular__iter_star_seps__$Import__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-7565, 0, "$Import", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-7567, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7563, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7560, 4, "$QualifiedName", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7558, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7556, 2, prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_, new int[] {109,111,100,117,108,101}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7555, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7552, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__default_$Header__tags_$Tags_$layouts_LAYOUTLIST_lit_module_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_imports_iter_star_seps__$Import__$layouts_LAYOUTLIST_, tmp);
	}
    protected static final void _init_prod__parameters_$Header__tags_$Tags_$layouts_LAYOUTLIST_lit_module_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_params_$ModuleParameters_$layouts_LAYOUTLIST_imports_iter_star_seps__$Import__$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new SeparatedListStackNode<IConstructor>(-7595, 8, regular__iter_star_seps__$Import__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-7592, 0, "$Import", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-7594, 1, "$layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-7590, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-7587, 6, "$ModuleParameters", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7585, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7582, 4, "$QualifiedName", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7580, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7578, 2, prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_, new int[] {109,111,100,117,108,101}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7577, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7574, 0, "$Tags", null, null);
      builder.addAlternative(RascalRascal.prod__parameters_$Header__tags_$Tags_$layouts_LAYOUTLIST_lit_module_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_params_$ModuleParameters_$layouts_LAYOUTLIST_imports_iter_star_seps__$Import__$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Header__tags_$Tags_$layouts_LAYOUTLIST_lit_module_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_imports_iter_star_seps__$Import__$layouts_LAYOUTLIST_(builder);
      
        _init_prod__parameters_$Header__tags_$Tags_$layouts_LAYOUTLIST_lit_module_$layouts_LAYOUTLIST_name_$QualifiedName_$layouts_LAYOUTLIST_params_$ModuleParameters_$layouts_LAYOUTLIST_imports_iter_star_seps__$Import__$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $NonterminalLabel {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$NonterminalLabel__char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new ListStackNode<IConstructor>(-7607, 1, regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, new CharStackNode<IConstructor>(-7604, 0, new int[][]{{48,57},{65,90},{95,95},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      tmp[0] = new CharStackNode<IConstructor>(-7603, 0, new int[][]{{97,122}}, null, null);
      builder.addAlternative(RascalRascal.prod__$NonterminalLabel__char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$NonterminalLabel__char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(builder);
      
    }
  }
	
  protected static class $Sym {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__endOfLine_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___36_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-7649, 2, prod__lit___36__char_class___range__36_36_, new int[] {36}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7648, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7645, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__endOfLine_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___36_, tmp);
	}
    protected static final void _init_prod__parameter_$Sym__lit___38_$layouts_LAYOUTLIST_nonterminal_$Nonterminal_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-7635, 0, prod__lit___38__char_class___range__38_38_, new int[] {38}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7637, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7639, 2, "$Nonterminal", null, null);
      builder.addAlternative(RascalRascal.prod__parameter_$Sym__lit___38_$layouts_LAYOUTLIST_nonterminal_$Nonterminal_, tmp);
	}
    protected static final void _init_prod__except_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___33_$layouts_LAYOUTLIST_label_$NonterminalLabel_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7662, 4, "$NonterminalLabel", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7660, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7658, 2, prod__lit___33__char_class___range__33_33_, new int[] {33}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7657, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7654, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__except_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___33_$layouts_LAYOUTLIST_label_$NonterminalLabel_, tmp);
	}
    protected static final void _init_prod__optional_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___63_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-7672, 2, prod__lit___63__char_class___range__63_63_, new int[] {63}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7671, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7668, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__optional_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___63_, tmp);
	}
    protected static final void _init_prod__caseInsensitiveLiteral_$Sym__cistring_$CaseInsensitiveStringConstant_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7677, 0, "$CaseInsensitiveStringConstant", null, null);
      builder.addAlternative(RascalRascal.prod__caseInsensitiveLiteral_$Sym__cistring_$CaseInsensitiveStringConstant_, tmp);
	}
    protected static final void _init_prod__iterSep_$Sym__lit___123_$layouts_LAYOUTLIST_symbol_$Sym_$layouts_LAYOUTLIST_sep_$Sym_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit___43_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-7682, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7684, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7686, 2, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7689, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7691, 4, "$Sym", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7694, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-7695, 6, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-7697, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-7698, 8, prod__lit___43__char_class___range__43_43_, new int[] {43}, null, null);
      builder.addAlternative(RascalRascal.prod__iterSep_$Sym__lit___123_$layouts_LAYOUTLIST_symbol_$Sym_$layouts_LAYOUTLIST_sep_$Sym_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit___43_, tmp);
	}
    protected static final void _init_prod__literal_$Sym__string_$StringConstant_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7703, 0, "$StringConstant", null, null);
      builder.addAlternative(RascalRascal.prod__literal_$Sym__string_$StringConstant_, tmp);
	}
    protected static final void _init_prod__iterStar_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___42_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-7713, 2, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7712, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7709, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__iterStar_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___42_, tmp);
	}
    protected static final void _init_prod__unequal_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___92_$layouts_LAYOUTLIST_match_$Sym__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7957, 4, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7955, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7953, 2, prod__lit___92__char_class___range__92_92_, new int[] {92}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7952, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7949, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__unequal_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___92_$layouts_LAYOUTLIST_match_$Sym__assoc__left, tmp);
	}
    protected static final void _init_prod__notFollow_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___33_62_62_$layouts_LAYOUTLIST_match_$Sym__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7943, 4, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7941, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7939, 2, prod__lit___33_62_62__char_class___range__33_33_char_class___range__62_62_char_class___range__62_62_, new int[] {33,62,62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7938, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7935, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__notFollow_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___33_62_62_$layouts_LAYOUTLIST_match_$Sym__assoc__left, tmp);
	}
    protected static final void _init_prod__parametrized_$Sym__nonterminal_$Nonterminal_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$Sym__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-7747, 6, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7746, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-7743, 4, regular__iter_seps__$Sym__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-7737, 0, "$Sym", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-7739, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-7740, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-7742, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7735, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7733, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7730, 0, "$Nonterminal", null, new ICompletionFilter[] {new StringFollowRequirement(new int[] {91})});
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7732, 1, "$layouts_LAYOUTLIST", null, null);
      builder.addAlternative(RascalRascal.prod__parametrized_$Sym__nonterminal_$Nonterminal_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$Sym__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__startOfLine_$Sym__lit___94_$layouts_LAYOUTLIST_symbol_$Sym_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7721, 2, "$Sym", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7719, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-7717, 0, prod__lit___94__char_class___range__94_94_, new int[] {94}, null, null);
      builder.addAlternative(RascalRascal.prod__startOfLine_$Sym__lit___94_$layouts_LAYOUTLIST_symbol_$Sym_, tmp);
	}
    protected static final void _init_prod__empty_$Sym__lit___40_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[0] = new LiteralStackNode<IConstructor>(-7751, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7753, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7754, 2, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      builder.addAlternative(RascalRascal.prod__empty_$Sym__lit___40_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__precede_$Sym__match_$Sym_$layouts_LAYOUTLIST_lit___60_60_$layouts_LAYOUTLIST_symbol_$Sym__assoc__right(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7901, 4, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7899, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7897, 2, prod__lit___60_60__char_class___range__60_60_char_class___range__60_60_, new int[] {60,60}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7896, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7893, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__precede_$Sym__match_$Sym_$layouts_LAYOUTLIST_lit___60_60_$layouts_LAYOUTLIST_symbol_$Sym__assoc__right, tmp);
	}
    protected static final void _init_prod__column_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_column_$IntegerLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7775, 4, "$IntegerLiteral", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7773, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7771, 2, prod__lit___64__char_class___range__64_64_, new int[] {64}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7770, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7767, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__column_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_column_$IntegerLiteral_, tmp);
	}
    protected static final void _init_prod__nonterminal_$Sym__nonterminal_$Nonterminal_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7762, 0, "$Nonterminal", null, new ICompletionFilter[] {new StringFollowRestriction(new int[] {91})});
      builder.addAlternative(RascalRascal.prod__nonterminal_$Sym__nonterminal_$Nonterminal_, tmp);
	}
    protected static final void _init_prod__sequence_$Sym__lit___40_$layouts_LAYOUTLIST_first_$Sym_$layouts_LAYOUTLIST_sequence_iter_seps__$Sym__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-7796, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7795, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(-7792, 4, regular__iter_seps__$Sym__$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-7789, 0, "$Sym", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-7791, 1, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7787, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7784, 2, "$Sym", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7782, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-7780, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__sequence_$Sym__lit___40_$layouts_LAYOUTLIST_first_$Sym_$layouts_LAYOUTLIST_sequence_iter_seps__$Sym__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__follow_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___62_62_$layouts_LAYOUTLIST_match_$Sym__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7929, 4, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7927, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7925, 2, prod__lit___62_62__char_class___range__62_62_char_class___range__62_62_, new int[] {62,62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7924, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7921, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__follow_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___62_62_$layouts_LAYOUTLIST_match_$Sym__assoc__left, tmp);
	}
    protected static final void _init_prod__alternative_$Sym__lit___40_$layouts_LAYOUTLIST_first_$Sym_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_alternatives_iter_seps__$Sym__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-7822, 8, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-7821, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(-7818, 6, regular__iter_seps__$Sym__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-7812, 0, "$Sym", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-7814, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-7815, 2, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null), new NonTerminalStackNode<IConstructor>(-7817, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7810, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-7808, 4, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7807, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7804, 2, "$Sym", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7802, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-7800, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(RascalRascal.prod__alternative_$Sym__lit___40_$layouts_LAYOUTLIST_first_$Sym_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_alternatives_iter_seps__$Sym__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__iterStarSep_$Sym__lit___123_$layouts_LAYOUTLIST_symbol_$Sym_$layouts_LAYOUTLIST_sep_$Sym_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit___42_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(-7842, 8, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-7841, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(-7839, 6, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7838, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7835, 4, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7833, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7830, 2, "$Sym", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7828, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-7826, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      builder.addAlternative(RascalRascal.prod__iterStarSep_$Sym__lit___123_$layouts_LAYOUTLIST_symbol_$Sym_$layouts_LAYOUTLIST_sep_$Sym_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit___42_, tmp);
	}
    protected static final void _init_prod__characterClass_$Sym__charClass_$Class_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7847, 0, "$Class", null, null);
      builder.addAlternative(RascalRascal.prod__characterClass_$Sym__charClass_$Class_, tmp);
	}
    protected static final void _init_prod__labeled_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_label_$NonterminalLabel_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-7858, 2, "$NonterminalLabel", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7856, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7853, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__labeled_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_label_$NonterminalLabel_, tmp);
	}
    protected static final void _init_prod__start_$Sym__lit_start_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_nonterminal_$Nonterminal_$layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(-7874, 6, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-7873, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7870, 4, "$Nonterminal", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7868, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7866, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7865, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-7863, 0, prod__lit_start__char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__116_116_, new int[] {115,116,97,114,116}, null, null);
      builder.addAlternative(RascalRascal.prod__start_$Sym__lit_start_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_nonterminal_$Nonterminal_$layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__notPrecede_$Sym__match_$Sym_$layouts_LAYOUTLIST_lit___33_60_60_$layouts_LAYOUTLIST_symbol_$Sym__assoc__right(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7915, 4, "$Sym", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7913, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7911, 2, prod__lit___33_60_60__char_class___range__33_33_char_class___range__60_60_char_class___range__60_60_, new int[] {33,60,60}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7910, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7907, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__notPrecede_$Sym__match_$Sym_$layouts_LAYOUTLIST_lit___33_60_60_$layouts_LAYOUTLIST_symbol_$Sym__assoc__right, tmp);
	}
    protected static final void _init_prod__iter_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___43_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-7883, 2, prod__lit___43__char_class___range__43_43_, new int[] {43}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7882, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7879, 0, "$Sym", null, null);
      builder.addAlternative(RascalRascal.prod__iter_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___43_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__endOfLine_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___36_(builder);
      
        _init_prod__parameter_$Sym__lit___38_$layouts_LAYOUTLIST_nonterminal_$Nonterminal_(builder);
      
        _init_prod__except_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___33_$layouts_LAYOUTLIST_label_$NonterminalLabel_(builder);
      
        _init_prod__optional_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___63_(builder);
      
        _init_prod__caseInsensitiveLiteral_$Sym__cistring_$CaseInsensitiveStringConstant_(builder);
      
        _init_prod__iterSep_$Sym__lit___123_$layouts_LAYOUTLIST_symbol_$Sym_$layouts_LAYOUTLIST_sep_$Sym_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit___43_(builder);
      
        _init_prod__literal_$Sym__string_$StringConstant_(builder);
      
        _init_prod__iterStar_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___42_(builder);
      
        _init_prod__unequal_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___92_$layouts_LAYOUTLIST_match_$Sym__assoc__left(builder);
      
        _init_prod__notFollow_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___33_62_62_$layouts_LAYOUTLIST_match_$Sym__assoc__left(builder);
      
        _init_prod__parametrized_$Sym__nonterminal_$Nonterminal_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_parameters_iter_seps__$Sym__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__startOfLine_$Sym__lit___94_$layouts_LAYOUTLIST_symbol_$Sym_(builder);
      
        _init_prod__empty_$Sym__lit___40_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__precede_$Sym__match_$Sym_$layouts_LAYOUTLIST_lit___60_60_$layouts_LAYOUTLIST_symbol_$Sym__assoc__right(builder);
      
        _init_prod__column_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___64_$layouts_LAYOUTLIST_column_$IntegerLiteral_(builder);
      
        _init_prod__nonterminal_$Sym__nonterminal_$Nonterminal_(builder);
      
        _init_prod__sequence_$Sym__lit___40_$layouts_LAYOUTLIST_first_$Sym_$layouts_LAYOUTLIST_sequence_iter_seps__$Sym__$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__follow_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___62_62_$layouts_LAYOUTLIST_match_$Sym__assoc__left(builder);
      
        _init_prod__alternative_$Sym__lit___40_$layouts_LAYOUTLIST_first_$Sym_$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_alternatives_iter_seps__$Sym__$layouts_LAYOUTLIST_lit___124_$layouts_LAYOUTLIST_$layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__iterStarSep_$Sym__lit___123_$layouts_LAYOUTLIST_symbol_$Sym_$layouts_LAYOUTLIST_sep_$Sym_$layouts_LAYOUTLIST_lit___125_$layouts_LAYOUTLIST_lit___42_(builder);
      
        _init_prod__characterClass_$Sym__charClass_$Class_(builder);
      
        _init_prod__labeled_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_label_$NonterminalLabel_(builder);
      
        _init_prod__start_$Sym__lit_start_$layouts_LAYOUTLIST_lit___91_$layouts_LAYOUTLIST_nonterminal_$Nonterminal_$layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__notPrecede_$Sym__match_$Sym_$layouts_LAYOUTLIST_lit___33_60_60_$layouts_LAYOUTLIST_symbol_$Sym__assoc__right(builder);
      
        _init_prod__iter_$Sym__symbol_$Sym_$layouts_LAYOUTLIST_lit___43_(builder);
      
    }
  }
	
  protected static class $Renaming {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Renaming__from_$Name_$layouts_LAYOUTLIST_lit___61_62_$layouts_LAYOUTLIST_to_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-7992, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-7990, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-7988, 2, prod__lit___61_62__char_class___range__61_61_char_class___range__62_62_, new int[] {61,62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-7987, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-7984, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__default_$Renaming__from_$Name_$layouts_LAYOUTLIST_lit___61_62_$layouts_LAYOUTLIST_to_$Name_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Renaming__from_$Name_$layouts_LAYOUTLIST_lit___61_62_$layouts_LAYOUTLIST_to_$Name_(builder);
      
    }
  }
	
  protected static class $Catch {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__binding_$Catch__lit_catch_$layouts_LAYOUTLIST_pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_body_$Statement_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-8013, 6, "$Statement", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-8011, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(-8009, 4, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-8008, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-8005, 2, "$Pattern", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-8003, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-8001, 0, prod__lit_catch__char_class___range__99_99_char_class___range__97_97_char_class___range__116_116_char_class___range__99_99_char_class___range__104_104_, new int[] {99,97,116,99,104}, null, null);
      builder.addAlternative(RascalRascal.prod__binding_$Catch__lit_catch_$layouts_LAYOUTLIST_pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_body_$Statement_, tmp);
	}
    protected static final void _init_prod__default_$Catch__lit_catch_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_body_$Statement_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-8025, 4, "$Statement", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-8023, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-8021, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-8020, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-8018, 0, prod__lit_catch__char_class___range__99_99_char_class___range__97_97_char_class___range__116_116_char_class___range__99_99_char_class___range__104_104_, new int[] {99,97,116,99,104}, null, null);
      builder.addAlternative(RascalRascal.prod__default_$Catch__lit_catch_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_body_$Statement_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__binding_$Catch__lit_catch_$layouts_LAYOUTLIST_pattern_$Pattern_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_body_$Statement_(builder);
      
        _init_prod__default_$Catch__lit_catch_$layouts_LAYOUTLIST_lit___58_$layouts_LAYOUTLIST_body_$Statement_(builder);
      
    }
  }
	
  protected static class $Signature {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__noThrows_$Signature__modifiers_$FunctionModifiers_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_parameters_$Parameters_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(-8103, 6, "$Parameters", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-8101, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-8098, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-8096, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-8093, 2, "$Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-8091, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8088, 0, "$FunctionModifiers", null, null);
      builder.addAlternative(RascalRascal.prod__noThrows_$Signature__modifiers_$FunctionModifiers_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_parameters_$Parameters_, tmp);
	}
    protected static final void _init_prod__withThrows_$Signature__modifiers_$FunctionModifiers_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_parameters_$Parameters_$layouts_LAYOUTLIST_lit_throws_$layouts_LAYOUTLIST_exceptions_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new SeparatedListStackNode<IConstructor>(-8138, 10, regular__iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(-8132, 0, "$Type", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(-8134, 1, "$layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(-8135, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(-8137, 3, "$layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(-8130, 9, "$layouts_LAYOUTLIST", null, null);
      tmp[8] = new LiteralStackNode<IConstructor>(-8128, 8, prod__lit_throws__char_class___range__116_116_char_class___range__104_104_char_class___range__114_114_char_class___range__111_111_char_class___range__119_119_char_class___range__115_115_, new int[] {116,104,114,111,119,115}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(-8127, 7, "$layouts_LAYOUTLIST", null, null);
      tmp[6] = new NonTerminalStackNode<IConstructor>(-8124, 6, "$Parameters", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(-8122, 5, "$layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(-8119, 4, "$Name", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-8117, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(-8114, 2, "$Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-8112, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8109, 0, "$FunctionModifiers", null, null);
      builder.addAlternative(RascalRascal.prod__withThrows_$Signature__modifiers_$FunctionModifiers_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_parameters_$Parameters_$layouts_LAYOUTLIST_lit_throws_$layouts_LAYOUTLIST_exceptions_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__noThrows_$Signature__modifiers_$FunctionModifiers_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_parameters_$Parameters_(builder);
      
        _init_prod__withThrows_$Signature__modifiers_$FunctionModifiers_$layouts_LAYOUTLIST_type_$Type_$layouts_LAYOUTLIST_name_$Name_$layouts_LAYOUTLIST_parameters_$Parameters_$layouts_LAYOUTLIST_lit_throws_$layouts_LAYOUTLIST_exceptions_iter_seps__$Type__$layouts_LAYOUTLIST_lit___44_$layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class $Variable {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__initialized_$Variable__name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_initial_$Expression_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(-8177, 4, "$Expression", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(-8175, 3, "$layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(-8173, 2, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-8172, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8169, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__initialized_$Variable__name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_initial_$Expression_, tmp);
	}
    protected static final void _init_prod__unInitialized_$Variable__name_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8183, 0, "$Name", null, null);
      builder.addAlternative(RascalRascal.prod__unInitialized_$Variable__name_$Name_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__initialized_$Variable__name_$Name_$layouts_LAYOUTLIST_lit___61_$layouts_LAYOUTLIST_initial_$Expression_(builder);
      
        _init_prod__unInitialized_$Variable__name_$Name_(builder);
      
    }
  }
	
  protected static class $TypeArg {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__named_$TypeArg__type_$Type_$layouts_LAYOUTLIST_name_$Name_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-8153, 2, "$Name", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-8151, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8148, 0, "$Type", null, null);
      builder.addAlternative(RascalRascal.prod__named_$TypeArg__type_$Type_$layouts_LAYOUTLIST_name_$Name_, tmp);
	}
    protected static final void _init_prod__default_$TypeArg__type_$Type_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8159, 0, "$Type", null, null);
      builder.addAlternative(RascalRascal.prod__default_$TypeArg__type_$Type_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__named_$TypeArg__type_$Type_$layouts_LAYOUTLIST_name_$Name_(builder);
      
        _init_prod__default_$TypeArg__type_$Type_(builder);
      
    }
  }
	
  protected static class $PreProtocolChars {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$PreProtocolChars__lit___124_$URLChars_lit___60_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(-8220, 2, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-8219, 1, "$URLChars", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(-8217, 0, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      builder.addAlternative(RascalRascal.prod__$PreProtocolChars__lit___124_$URLChars_lit___60_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__$PreProtocolChars__lit___124_$URLChars_lit___60_(builder);
      
    }
  }
	
  protected static class $Literal {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__location_$Literal__locationLiteral_$LocationLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8257, 0, "$LocationLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__location_$Literal__locationLiteral_$LocationLiteral_, tmp);
	}
    protected static final void _init_prod__real_$Literal__realLiteral_$RealLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8263, 0, "$RealLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__real_$Literal__realLiteral_$RealLiteral_, tmp);
	}
    protected static final void _init_prod__regExp_$Literal__regExpLiteral_$RegExpLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8269, 0, "$RegExpLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__regExp_$Literal__regExpLiteral_$RegExpLiteral_, tmp);
	}
    protected static final void _init_prod__rational_$Literal__rationalLiteral_$RationalLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8275, 0, "$RationalLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__rational_$Literal__rationalLiteral_$RationalLiteral_, tmp);
	}
    protected static final void _init_prod__integer_$Literal__integerLiteral_$IntegerLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8281, 0, "$IntegerLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__integer_$Literal__integerLiteral_$IntegerLiteral_, tmp);
	}
    protected static final void _init_prod__boolean_$Literal__booleanLiteral_$BooleanLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8287, 0, "$BooleanLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__boolean_$Literal__booleanLiteral_$BooleanLiteral_, tmp);
	}
    protected static final void _init_prod__dateTime_$Literal__dateTimeLiteral_$DateTimeLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8293, 0, "$DateTimeLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__dateTime_$Literal__dateTimeLiteral_$DateTimeLiteral_, tmp);
	}
    protected static final void _init_prod__string_$Literal__stringLiteral_$StringLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8299, 0, "$StringLiteral", null, null);
      builder.addAlternative(RascalRascal.prod__string_$Literal__stringLiteral_$StringLiteral_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__location_$Literal__locationLiteral_$LocationLiteral_(builder);
      
        _init_prod__real_$Literal__realLiteral_$RealLiteral_(builder);
      
        _init_prod__regExp_$Literal__regExpLiteral_$RegExpLiteral_(builder);
      
        _init_prod__rational_$Literal__rationalLiteral_$RationalLiteral_(builder);
      
        _init_prod__integer_$Literal__integerLiteral_$IntegerLiteral_(builder);
      
        _init_prod__boolean_$Literal__booleanLiteral_$BooleanLiteral_(builder);
      
        _init_prod__dateTime_$Literal__dateTimeLiteral_$DateTimeLiteral_(builder);
      
        _init_prod__string_$Literal__stringLiteral_$StringLiteral_(builder);
      
    }
  }
	
  protected static class $Module {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__default_$Module__header_$Header_$layouts_LAYOUTLIST_body_$Body_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(-8323, 2, "$Body", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(-8321, 1, "$layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(-8318, 0, "$Header", null, null);
      builder.addAlternative(RascalRascal.prod__default_$Module__header_$Header_$layouts_LAYOUTLIST_body_$Body_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
      
        _init_prod__default_$Module__header_$Header_$layouts_LAYOUTLIST_body_$Body_(builder);
      
    }
  }
	
  public RascalRascal() {
    super();
  }

  // Parse methods    
  
  public AbstractStackNode<IConstructor>[] $Tag() {
    return $Tag.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $DateAndTime() {
    return $DateAndTime.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Pattern() {
    return $Pattern.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Prod() {
    return $Prod.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $UnicodeEscape() {
    return $UnicodeEscape.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $OctalIntegerLiteral() {
    return $OctalIntegerLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $RationalLiteral() {
    return $RationalLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $BooleanLiteral() {
    return $BooleanLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Toplevel() {
    return $Toplevel.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $ProdModifier() {
    return $ProdModifier.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $TypeVar() {
    return $TypeVar.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Comprehension() {
    return $Comprehension.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $FunctionModifiers() {
    return $FunctionModifiers.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Declaration() {
    return $Declaration.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Type() {
    return $Type.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Class() {
    return $Class.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Bound() {
    return $Bound.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $FunctionType() {
    return $FunctionType.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Case() {
    return $Case.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Statement() {
    return $Statement.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Renamings() {
    return $Renamings.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $StringLiteral() {
    return $StringLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Visibility() {
    return $Visibility.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PostPathChars() {
    return $PostPathChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $NamedBackslash() {
    return $NamedBackslash.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Visit() {
    return $Visit.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Command() {
    return $Command.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $ProtocolTail() {
    return $ProtocolTail.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PreStringChars() {
    return $PreStringChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $QualifiedName() {
    return $QualifiedName.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $URLChars() {
    return $URLChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $StringMiddle() {
    return $StringMiddle.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $TimeZonePart() {
    return $TimeZonePart.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $ShellCommand() {
    return $ShellCommand.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $LocationLiteral() {
    return $LocationLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Range() {
    return $Range.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Rest() {
    return $Rest.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $JustTime() {
    return $JustTime.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $StringCharacter() {
    return $StringCharacter.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $layouts_LAYOUTLIST() {
    return $layouts_LAYOUTLIST.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $DataTarget() {
    return $DataTarget.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $RealLiteral() {
    return $RealLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $layouts_$default$() {
    return $layouts_$default$.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Replacement() {
    return $Replacement.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Assoc() {
    return $Assoc.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $RegExpModifier() {
    return $RegExpModifier.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $MidStringChars() {
    return $MidStringChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $RegExp() {
    return $RegExp.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PathPart() {
    return $PathPart.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $StringTemplate() {
    return $StringTemplate.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $DecimalIntegerLiteral() {
    return $DecimalIntegerLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Body() {
    return $Body.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $LAYOUT() {
    return $LAYOUT.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $UserType() {
    return $UserType.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Import() {
    return $Import.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $FunctionBody() {
    return $FunctionBody.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] start__$Command() {
    return start__$Command.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $IntegerLiteral() {
    return $IntegerLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Target() {
    return $Target.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $SyntaxDefinition() {
    return $SyntaxDefinition.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Kind() {
    return $Kind.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Mapping__$Expression() {
    return $Mapping__$Expression.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PostProtocolChars() {
    return $PostProtocolChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $MidPathChars() {
    return $MidPathChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Char() {
    return $Char.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PrePathChars() {
    return $PrePathChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Strategy() {
    return $Strategy.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $ModuleParameters() {
    return $ModuleParameters.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $RascalKeywords() {
    return $RascalKeywords.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $NamedRegExp() {
    return $NamedRegExp.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $RegExpLiteral() {
    return $RegExpLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PostStringChars() {
    return $PostStringChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $HexIntegerLiteral() {
    return $HexIntegerLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] start__$EvalCommand() {
    return start__$EvalCommand.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $ModuleActuals() {
    return $ModuleActuals.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PathChars() {
    return $PathChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $DateTimeLiteral() {
    return $DateTimeLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $BasicType() {
    return $BasicType.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $FunctionDeclaration() {
    return $FunctionDeclaration.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Variant() {
    return $Variant.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] start__$PreModule() {
    return start__$PreModule.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Declarator() {
    return $Declarator.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $StructuredType() {
    return $StructuredType.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $TimePartNoTZ() {
    return $TimePartNoTZ.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Name() {
    return $Name.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Start() {
    return $Start.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Formals() {
    return $Formals.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Tags() {
    return $Tags.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $OptionalExpression() {
    return $OptionalExpression.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] start__$Commands() {
    return start__$Commands.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $CaseInsensitiveStringConstant() {
    return $CaseInsensitiveStringConstant.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Backslash() {
    return $Backslash.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $JustDate() {
    return $JustDate.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $MidProtocolChars() {
    return $MidProtocolChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PathTail() {
    return $PathTail.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PatternWithAction() {
    return $PatternWithAction.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $DataTypeSelector() {
    return $DataTypeSelector.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $StringTail() {
    return $StringTail.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] start__$Module() {
    return start__$Module.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $StringConstant() {
    return $StringConstant.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $LocalVariableDeclaration() {
    return $LocalVariableDeclaration.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Parameters() {
    return $Parameters.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $DatePart() {
    return $DatePart.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $HeaderKeyword() {
    return $HeaderKeyword.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Assignable() {
    return $Assignable.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $FunctionModifier() {
    return $FunctionModifier.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $EvalCommand() {
    return $EvalCommand.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $ProtocolChars() {
    return $ProtocolChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Assignment() {
    return $Assignment.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Field() {
    return $Field.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Comment() {
    return $Comment.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Label() {
    return $Label.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $ProtocolPart() {
    return $ProtocolPart.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PreModule() {
    return $PreModule.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $TagString() {
    return $TagString.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Nonterminal() {
    return $Nonterminal.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Mapping__$Pattern() {
    return $Mapping__$Pattern.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Expression() {
    return $Expression.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $ImportedModule() {
    return $ImportedModule.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Commands() {
    return $Commands.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Header() {
    return $Header.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $NonterminalLabel() {
    return $NonterminalLabel.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Sym() {
    return $Sym.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Renaming() {
    return $Renaming.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Catch() {
    return $Catch.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Signature() {
    return $Signature.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $TypeArg() {
    return $TypeArg.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Variable() {
    return $Variable.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $PreProtocolChars() {
    return $PreProtocolChars.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Literal() {
    return $Literal.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] $Module() {
    return $Module.EXPECTS;
  }
}