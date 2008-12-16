package test;

import junit.framework.TestCase;
import java.io.IOException;

public class DataDeclarationTests extends TestCase{
	private TestFramework tf = new TestFramework();
	
	public void testBool() throws IOException {
		
		tf.prepare("data Bool btrue | bfalse | band(Bool left, Bool right) | bor(Bool left, Bool right);");
		
		assertTrue(tf.runTestInSameEvaluator("{Bool b = btrue; b == Bool::btrue;}"));
		assertTrue(tf.runTestInSameEvaluator("{Bool b = bfalse; b == Bool::bfalse;}"));
		assertTrue(tf.runTestInSameEvaluator("{Bool b = band(btrue,bfalse);  b == Bool::band(Bool::btrue,Bool::bfalse);}"));
		assertTrue(tf.runTestInSameEvaluator("{Bool b = bor(btrue,bfalse); b == bor(btrue,bfalse);}"));
		assertTrue(tf.runTestInSameEvaluator("band(btrue,bfalse).left == btrue;"));
		assertTrue(tf.runTestInSameEvaluator("band(btrue,bfalse).right == bfalse;"));
		assertTrue(tf.runTestInSameEvaluator("bor(btrue,bfalse).left == btrue;"));
		assertTrue(tf.runTestInSameEvaluator("bor(btrue,bfalse).right == bfalse;"));
		assertTrue(tf.runTestInSameEvaluator("{Bool b = band(btrue,bfalse).left; b == btrue;}"));
		assertTrue(tf.runTestInSameEvaluator("{Bool b = band(btrue,bfalse).right; b == bfalse;}"));
	}
}
