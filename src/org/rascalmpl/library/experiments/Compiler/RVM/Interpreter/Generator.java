package org.rascalmpl.library.experiments.Compiler.RVM.Interpreter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.imp.pdb.facts.IList;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Generator implements Opcodes {
	byte[] endCode = null;
	private ClassWriter cw = null;
	private MethodVisitor mv = null;
	private String className = null;
	private String packageName = null;
	private String fullClassName = null;
	private String[] funcArray = null;
	private boolean emit = true;
	private HashMap<String, Label> labelMap = new HashMap<String, Label>();
	private Label[] hotEntryLabels = null;
	private Label exitLabel = null;
	private String currentName = null;

	public Generator(String packageName2, String className2) {

	}

	void enableOutput(boolean flag) {
		emit = flag;
	}

	public void emitClass(String pName, String cName) {
		this.className = cName;
		this.packageName = pName;
		this.fullClassName = packageName + "/" + className;
		this.fullClassName = "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Running";
		cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

		// cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, fullClassName, null, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/RVMRun",
		// new String[] { "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/IDynamicRun" });
		cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, fullClassName, null, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/RVMRun", null);

		// Main constructor
		mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/eclipse/imp/pdb/facts/IValueFactory;Lorg/rascalmpl/interpreter/IEvaluatorContext;ZZ)V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ALOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitVarInsn(ILOAD, 4);
		mv.visitMethodInsn(INVOKESPECIAL, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/RVMRun", "<init>",
				"(Lorg/eclipse/imp/pdb/facts/IValueFactory;Lorg/rascalmpl/interpreter/IEvaluatorContext;ZZ)V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();

		// Add constructor initialzing super.
		mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/eclipse/imp/pdb/facts/IValueFactory;)V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESPECIAL, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/RVMRun", "<init>", "(Lorg/eclipse/imp/pdb/facts/IValueFactory;)V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}

	public void emitMethod(String name, int continuationPoints, boolean debug) {
		if (!emit)
			return;
		mv = cw.visitMethod(ACC_PUBLIC, name, "()Ljava/lang/Object;", null, null);
		labelMap.clear(); // New set of labels.

		mv.visitCode();

		currentName = name;

		if (continuationPoints != 0) {
			hotEntryLabels = new Label[continuationPoints + 1]; // Add entry 0
			exitLabel = new Label();

			for (int i = 0; i < hotEntryLabels.length; i++)
				hotEntryLabels[i] = new Label();

			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
			mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "hotEntryPoint", "I");
			mv.visitTableSwitchInsn(0, hotEntryLabels.length - 1, exitLabel, hotEntryLabels);
			System.out.println(currentName + " 00 : entrypoint :" + 0);

			mv.visitLabel(hotEntryLabels[0]); // Start at 'address' 0
		} else {
			exitLabel = null;
			// hotEntryLabels = null ;
		}
	}

	public void closeMethod() {
		if (!emit)
			return;
		if (exitLabel != null) { // This label should never be reached placed to keep JVM verifier happy.
			System.out.println(currentName + " : exitLabel");

			mv.visitLabel(exitLabel);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, fullClassName, "PANIC", "Lorg/eclipse/imp/pdb/facts/IString;");
			mv.visitInsn(ARETURN);
		}
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}

	public void emitJMP(String targetLabel) {
		if (!emit)
			return;
		Label lb = labelMap.get(targetLabel);
		if (lb == null) {
			lb = new Label();
			labelMap.put(targetLabel, lb);
		}
		mv.visitJumpInsn(GOTO, lb);
	}

	public void emitJMPTRUE(String targetLabel, boolean debug) {
		if (!emit)
			return;
		Label lb = labelMap.get(targetLabel);
		if (lb == null) {
			lb = new Label();
			labelMap.put(targetLabel, lb);
		}

		if ( debug ) emitCall("dinsnJMPTRUE",1);
		
		emitPOP(false);  // pop part of jmp...

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(AALOAD);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "TRUE", "Ljava/lang/Boolean;");

		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z");
		mv.visitJumpInsn(IFNE, lb); // Direct goto possible

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(AALOAD);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "Rascal_TRUE", "Lorg/eclipse/imp/pdb/facts/IBool;");

		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z");
		mv.visitJumpInsn(IFNE, lb);

	}

	public void emitJMPFALSE(String targetLabel, boolean debug) {
		if (!emit)
			return;

		Label lb = labelMap.get(targetLabel);
		if (lb == null) {
			lb = new Label();
			labelMap.put(targetLabel, lb);
		}

		if ( debug ) emitCall("dinsnJMPFALSE",2);
		
		emitPOP(false);  // pop part of jmp...


		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(AALOAD);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "FALSE", "Ljava/lang/Boolean;");

		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z");
		mv.visitJumpInsn(IFNE, lb); // Direct goto possible

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(AALOAD);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "Rascal_FALSE", "Lorg/eclipse/imp/pdb/facts/IBool;");

		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z");
		mv.visitJumpInsn(IFNE, lb);
	}

	public void emitPOP(boolean debug) {
		if (!emit)
			return;
		if (debug)
			emitCall("dinsnPOP");

		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(DUP);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(ICONST_1);
		mv.visitInsn(ISUB);
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
	}

	public void emitLabel(String targetLabel) {
		if (!emit)
			return;
		Label lb = labelMap.get(targetLabel);
		if (lb == null) {
			lb = new Label();
			labelMap.put(targetLabel, lb);
		}
		mv.visitLabel(lb);
	}

	// A call to a RVM instruction not CALL or OCALL
	public void emitCall(String fname) {
		if (!emit)
			return;
		mv.visitVarInsn(ALOAD, 0); // Load this on stack.
		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, fname, "()V");
	}

	public void emitCall(String fname, int arg1) {
		if (!emit)
			return;
		mv.visitVarInsn(ALOAD, 0); // Load this on stack.

		if (arg1 >= -128 && arg1 <= 127)
			mv.visitIntInsn(BIPUSH, arg1);
		else
			mv.visitIntInsn(SIPUSH, arg1);

		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, fname, "(I)V");
	}

	public void emitCall(String fname, int arg1, int arg2) {
		if (!emit)
			return;
		mv.visitVarInsn(ALOAD, 0); // Load this on stack.
		if (arg1 >= -128 && arg1 <= 127)
			mv.visitIntInsn(BIPUSH, arg1);
		else
			mv.visitIntInsn(SIPUSH, arg1);
		if (arg2 >= -128 && arg2 <= 127)
			mv.visitIntInsn(BIPUSH, arg2);
		else
			mv.visitIntInsn(SIPUSH, arg2);
		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, fname, "(II)V");
	}

	byte[] finalizeCode() {
		if (endCode == null) {
			cw.visitEnd();
			endCode = cw.toByteArray();
		}
		return endCode;
	}

	public void emitReturn0() {
		if (!emit)
			return;
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "NONE", "Lorg/eclipse/imp/pdb/facts/IString;");
		mv.visitInsn(ARETURN);
	}

	public void emitExhaust() {
		if (!emit)
			return;
		Label l0 = new Label();

		// Check for previousCallFrame if none return Rascal_False
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "previousCallFrame",
				"Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(PUTFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitJumpInsn(IFNONNULL, l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "Rascal_FALSE", "Lorg/eclipse/imp/pdb/facts/IBool;");
		mv.visitInsn(ARETURN);

		// TODO handle reentry on NEXT ??
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "stack", "[Ljava/lang/Object;");
		mv.visitFieldInsn(PUTFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "sp", "I");
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(DUP);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(DUP_X1);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IADD);
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "Rascal_FALSE", "Lorg/eclipse/imp/pdb/facts/IBool;");
		mv.visitInsn(AASTORE);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "NONE", "Lorg/eclipse/imp/pdb/facts/IString;");
		mv.visitInsn(ARETURN);
	}

	public void emitReturn1() {
		Label l0 = new Label();
		if (!emit)
			return;
		// 1 Object rval ;
		// does : Object = return1Helper() ;
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, "return1Helper", "()Ljava/lang/Object;");
		mv.visitVarInsn(ASTORE, 1);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitJumpInsn(IFNONNULL, l0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitInsn(ARETURN);

		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "stack", "[Ljava/lang/Object;");
		mv.visitFieldInsn(PUTFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "sp", "I");
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(DUP);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(DUP_X1);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IADD);
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
		mv.visitVarInsn(ALOAD, 1);
		mv.visitInsn(AASTORE);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "NONE", "Lorg/eclipse/imp/pdb/facts/IString;");
		mv.visitInsn(ARETURN);
	}

	public void emitFailreturn() {
		if (!emit)
			return;
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "FAILRETURN", "Lorg/eclipse/imp/pdb/facts/IString;");
		mv.visitInsn(ARETURN);
	}

	public void dump(String loc) {
		if (endCode == null)
			finalizeCode();
		try {
			FileOutputStream fos = new FileOutputStream(loc);
			fos.write(endCode);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void emitDynPrelude() {
	// // 0 this
	// // 1 fname
	// // 2 args
	// // 3 int n
	// // 4 Function Func
	// // 5 Frame root
	//
	// mv = cw.visitMethod(ACC_PUBLIC, "dynRun", "(Ljava/lang/String;[Lorg/eclipse/imp/pdb/facts/IValue;)Ljava/lang/Object;", null, null);
	// mv.visitCode();
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "functionMap", "Ljava/util/Map;");
	// mv.visitVarInsn(ALOAD, 1);
	// mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
	// mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
	// mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
	// mv.visitVarInsn(ISTORE, 3);
	//
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "functionStore", "Ljava/util/ArrayList;");
	// mv.visitVarInsn(ILOAD, 3);
	// mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "get", "(I)Ljava/lang/Object;");
	// mv.visitTypeInsn(CHECKCAST, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function");
	// mv.visitVarInsn(ASTORE, 4);
	//
	// mv.visitTypeInsn(NEW, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame");
	// mv.visitInsn(DUP);
	// mv.visitVarInsn(ALOAD, 4);
	// mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function", "scopeId", "I");
	// mv.visitInsn(ACONST_NULL);
	// mv.visitVarInsn(ALOAD, 4);
	// mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function", "maxstack", "I");
	// mv.visitVarInsn(ALOAD, 4);
	// mv.visitMethodInsn(INVOKESPECIAL, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "<init>",
	// "(ILorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;ILorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function;)V");
	// mv.visitVarInsn(ASTORE, 5);
	//
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitVarInsn(ALOAD, 5);
	// mv.visitFieldInsn(PUTFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
	// mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "stack", "[Ljava/lang/Object;");
	// mv.visitFieldInsn(PUTFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
	//
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
	// mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "stack", "[Ljava/lang/Object;");
	// mv.visitInsn(ICONST_0);
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "vf", "Lorg/eclipse/imp/pdb/facts/IValueFactory;");
	// mv.visitVarInsn(ALOAD, 2);
	// mv.visitMethodInsn(INVOKEINTERFACE, "org/eclipse/imp/pdb/facts/IValueFactory", "list", "([Lorg/eclipse/imp/pdb/facts/IValue;)Lorg/eclipse/imp/pdb/facts/IList;");
	// mv.visitInsn(AASTORE);
	//
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
	// mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "stack", "[Ljava/lang/Object;");
	// mv.visitInsn(ICONST_1);
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "vf", "Lorg/eclipse/imp/pdb/facts/IValueFactory;");
	// mv.visitMethodInsn(INVOKEINTERFACE, "org/eclipse/imp/pdb/facts/IValueFactory", "mapWriter", "()Lorg/eclipse/imp/pdb/facts/IMapWriter;");
	// mv.visitMethodInsn(INVOKEINTERFACE, "org/eclipse/imp/pdb/facts/IMapWriter", "done", "()Lorg/eclipse/imp/pdb/facts/IMap;");
	// mv.visitInsn(AASTORE);
	//
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitVarInsn(ALOAD, 4);
	// mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function", "nlocals", "I");
	// mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
	// mv.visitFieldInsn(PUTFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "sp", "I");
	//
	// // Function exit
	// mv.visitVarInsn(ALOAD, 0);
	// mv.visitFieldInsn(GETFIELD, fullClassName, "vf", "Lorg/eclipse/imp/pdb/facts/IValueFactory;");
	// mv.visitInsn(ICONST_0);
	// mv.visitMethodInsn(INVOKEINTERFACE, "org/eclipse/imp/pdb/facts/IValueFactory", "bool", "(Z)Lorg/eclipse/imp/pdb/facts/IBool;");
	// mv.visitInsn(ARETURN);
	// mv.visitMaxs(0, 0);
	// mv.visitEnd();
	// }

	public void emitDynDispatch(int numberOfFunctions) {
		funcArray = new String[numberOfFunctions];
	}

	public void emitDynCaLL(String fname, Integer value) {
		funcArray[value] = fname;
	}

	public void emitDynFinalize() {
		int nrFuncs = funcArray.length;
		Label[] caseLabels = new Label[nrFuncs];

		for (int i = 0; i < nrFuncs; i++) {
			caseLabels[i] = new Label();
		}
		Label defaultlabel = new Label();

		mv = cw.visitMethod(ACC_PUBLIC, "dynRun", "(I)Ljava/lang/Object;", null, null);
		mv.visitCode();

		// Case switch on int at loc 3 (java stack)
		mv.visitVarInsn(ILOAD, 1);
		mv.visitTableSwitchInsn(0, nrFuncs - 1, defaultlabel, caseLabels);
		for (int i = 0; i < nrFuncs; i++) {
			mv.visitLabel(caseLabels[i]);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, NameMangler.mangle(funcArray[i]), "()Ljava/lang/Object;");
			mv.visitInsn(ARETURN);
		}
		mv.visitLabel(defaultlabel);

		// Function exit
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "vf", "Lorg/eclipse/imp/pdb/facts/IValueFactory;");
		mv.visitInsn(ICONST_0);
		mv.visitMethodInsn(INVOKEINTERFACE, "org/eclipse/imp/pdb/facts/IValueFactory", "bool", "(Z)Lorg/eclipse/imp/pdb/facts/IBool;");
		mv.visitInsn(ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}

	public void emitOCallHandler(String OCallName, String funIn, int scopeIn, int[] functions, int[] constructors) {
		if (!emit)
			return;
		// 1 String name ;
		// 2 int scope ;
		// 3 int[] fnctions ;
		// 4 int[] cons ;
		// 5 Object rval ;
		// 6 Function func ;
		// 7 Frame root ;
		//
		mv = cw.visitMethod(ACC_PUBLIC, OCallName, "()Ljava/lang/Object;", null, null);
		mv.visitCode();

		if (funIn == null)
			funIn = new String("unknown_funIn");
		System.out.println(funIn);
		mv.visitLdcInsn(funIn);
		mv.visitVarInsn(ASTORE, 1);

		mv.visitLdcInsn(new Integer(scopeIn));
		mv.visitVarInsn(ISTORE, 2);

		mv.visitIntInsn(SIPUSH, functions.length);
		mv.visitIntInsn(NEWARRAY, T_INT);
		mv.visitVarInsn(ASTORE, 3);

		for (int i = 0; i < functions.length; i++) {
			mv.visitVarInsn(ALOAD, 3);
			mv.visitIntInsn(SIPUSH, i);
			mv.visitIntInsn(SIPUSH, functions[i]);
			mv.visitInsn(IASTORE);
		}

		mv.visitIntInsn(SIPUSH, constructors.length);
		mv.visitIntInsn(NEWARRAY, T_INT);
		mv.visitVarInsn(ASTORE, 4);

		for (int i = 0; i < constructors.length; i++) {
			mv.visitVarInsn(ALOAD, 4);
			mv.visitIntInsn(SIPUSH, i);
			mv.visitIntInsn(SIPUSH, constructors[i]);
			mv.visitInsn(IASTORE);
		}
	}

	public void emitOCallCALL(String callFunc, int funcListIndex) {
		if (!emit)
			return;

		Label noExit = new Label();

		// 0 this
		// 1 String name ;
		// 2 int scope ;
		// 3 int[] fnctions ;
		// 4 int[] cons ;
		// 5 Object rval ;
		// 6 Function func ;
		// 7 Frame root ;
		//

		// does : cf.sp = sp ;
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitFieldInsn(PUTFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "sp", "I");

		// First part make make frame based, the function is found in a local table
		// index by funcListIndex

		// does : Function func = functionStore.get(function[funcListIndex]);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "functionStore", "Ljava/util/ArrayList;");
		mv.visitVarInsn(ALOAD, 3);
		mv.visitIntInsn(SIPUSH, funcListIndex);
		mv.visitInsn(IALOAD);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "get", "(I)Ljava/lang/Object;");
		mv.visitTypeInsn(CHECKCAST, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function");
		mv.visitVarInsn(ASTORE, 6);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitVarInsn(ALOAD, 6);
		mv.visitInsn(ACONST_NULL);
		mv.visitVarInsn(ALOAD, 6);
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function", "nformals", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitMethodInsn(
				INVOKEVIRTUAL,
				"org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame",
				"getFrame",
				"(Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function;Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;II)Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitVarInsn(ASTORE, 7);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 7);
		mv.visitFieldInsn(PUTFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");

		// does : stack = cf.stack ;
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "stack", "[Ljava/lang/Object;");
		mv.visitFieldInsn(PUTFIELD, fullClassName, "stack", "[Ljava/lang/Object;");

		// does : sp = func.nlocals ; ;
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 6);
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function", "nlocals", "I");
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");

		// Call function and store return result
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, callFunc, "()Ljava/lang/Object;");
		mv.visitVarInsn(ASTORE, 5);

		// Check return result
		mv.visitVarInsn(ALOAD, 5);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "NONE", "Lorg/eclipse/imp/pdb/facts/IString;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z");
		mv.visitJumpInsn(IFEQ, noExit);
		mv.visitVarInsn(ALOAD, 5);
		mv.visitInsn(ARETURN);
		mv.visitLabel(noExit); // FAIlRETURN try next alternative
	}

	public void emitOCallEnd() {
		if (!emit)
			return;
		// This code handles the case that ALL alternatives fail

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "NONE", "Lorg/eclipse/imp/pdb/facts/IString;");
		mv.visitInsn(ARETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}

	public static void Pain(String[] argv) {
		byte[] result = null;
		System.out.println("Getting started!\n");
		Generator emittor = new Generator("packageName", "className");

		emittor.emitClass("org/rascalmpl/library/experiments/Compiler/RVM/Interpreter", "Runner");
		emittor.emitMethod("main", 0, false);
		emittor.emitLabel("entrypoint");
		emittor.emitCall("main");
		emittor.emitCall("main", 10, 20);
		emittor.emitJMPTRUE("entrypoint",true);
		emittor.emitPOP(true);
		emittor.emitJMP("entrypoint");
		emittor.closeMethod();
		result = emittor.finalizeCode();

		try {
			FileOutputStream fos = new FileOutputStream("/Users/ferryrietveld/Runner.class");
			fos.write(result);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void emitOCall(String ocallFunc, int hotEntryPoint) {
		if (!emit)
			return;
		if (exitLabel != null) { // If there is an exit label there is a
			System.out.println(currentName + " OC : entrypoint :" + hotEntryPoint);
			mv.visitLabel(hotEntryLabels[hotEntryPoint]);
		}
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, ocallFunc, "()Ljava/lang/Object;");
		mv.visitVarInsn(ASTORE, 1);
	}

	public void emitInlineLoadCon(int arg, boolean debug) {
		if (!emit)
			return;

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(DUP);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(DUP_X1);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IADD);
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "function",
				"Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function", "constantStore", "[Lorg/eclipse/imp/pdb/facts/IValue;");
		if (arg >= -128 && arg <= 127)
			mv.visitIntInsn(BIPUSH, arg);
		else
			mv.visitIntInsn(SIPUSH, arg);
		mv.visitInsn(AALOAD);
		mv.visitInsn(AASTORE);
	}

	public void emitInlineLoadLoc3(boolean debug) {
		if (!emit)
			return;
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(DUP);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(DUP_X1);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IADD);
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitInsn(ICONST_3);
		mv.visitInsn(AALOAD);
		mv.visitInsn(AASTORE);
	}

	public void emitInlineStoreLoc(int loc, boolean debug) {
		// Moves stack to stack
		if (!emit)
			return;

		if (debug) { // That we can trace the methodcall!
			emitCall("insnSTORELOC", loc);
			return;
		}

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");

		if (loc >= -128 && loc <= 127) // Can omit negetive test
			mv.visitIntInsn(BIPUSH, loc);
		else
			mv.visitIntInsn(SIPUSH, loc);

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(ICONST_1);
		mv.visitInsn(ISUB);
		mv.visitInsn(AALOAD);
		mv.visitInsn(AASTORE);
	}

	public void emitInlineLoadType(int t, boolean debug) {
		if (!emit)
			return;

		if (debug) { // That we can trace the methodcall!
			emitCall("insnLOADTYPE", t);
			return;
		}

		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "stack", "[Ljava/lang/Object;");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitInsn(DUP);
		mv.visitFieldInsn(GETFIELD, fullClassName, "sp", "I");
		mv.visitInsn(DUP_X1);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IADD);
		mv.visitFieldInsn(PUTFIELD, fullClassName, "sp", "I");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "cf", "Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Frame", "function",
				"Lorg/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function;");
		mv.visitFieldInsn(GETFIELD, "org/rascalmpl/library/experiments/Compiler/RVM/Interpreter/Function", "typeConstantStore", "[Lorg/eclipse/imp/pdb/facts/type/Type;");
		if (t >= -128 && t <= 127) // Can omit negetive test
			mv.visitIntInsn(BIPUSH, t);
		else
			mv.visitIntInsn(SIPUSH, t);
		mv.visitInsn(AALOAD);
		mv.visitInsn(AASTORE);
	}

	public void emitHotEntryJumpTable(int continuationPoints, boolean debug) {
		if (!emit)
			return;

		if (debug) {
		}

		hotEntryLabels = new Label[continuationPoints + 1]; // Add default 0 entry point.

	}

	public void emitCallJava(int className2, int methodName, int parameterTypes, int reflect, boolean debug) {
		if (!emit)
			return;

		mv.visitVarInsn(ALOAD, 0); // Load this on stack.

		if (className2 >= -128 && className2 <= 127)
			mv.visitIntInsn(BIPUSH, className2);
		else
			mv.visitIntInsn(SIPUSH, className2);

		if (methodName >= -128 && methodName <= 127)
			mv.visitIntInsn(BIPUSH, methodName);
		else
			mv.visitIntInsn(SIPUSH, methodName);

		if (parameterTypes >= -128 && parameterTypes <= 127)
			mv.visitIntInsn(BIPUSH, parameterTypes);
		else
			mv.visitIntInsn(SIPUSH, parameterTypes);

		if (reflect >= -128 && reflect <= 127)
			mv.visitIntInsn(BIPUSH, reflect);
		else
			mv.visitIntInsn(SIPUSH, reflect);

		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, "insnCALLJAVA", "(IIII)V");
	}

	public void emitJmpIndex(IList labels, boolean dcode) {
		// TODO Auto-generated method stub

	}

	public void emitTypeSwitch(IList labels, boolean dcode) {
		// TODO Auto-generated method stub

	}

	public void emitYield0(int hotEntryPoint) {
		if ( !emit ) 
			return ;
		
		// TODO: Implement real yield this stub is only needed to get the generated
		// code past the JVM verifier.
		

		mv.visitVarInsn(ALOAD, 0); // Load this on stack.
		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, "insnYIELD0", "()V");

		System.out.println(currentName + " Y0 : entrypoint :" + hotEntryPoint);
		mv.visitLabel(hotEntryLabels[hotEntryPoint]);

	}

	public void emitYield1(int arity, int hotEntryPoint) {
		// TODO: Implement real yield this stub is only needed to get the generated
		// code past the JVM verifier.
		if ( !emit ) 
			return ;
		
		
		mv.visitVarInsn(ALOAD, 0);

		if (arity >= -128 && arity <= 127)
			mv.visitIntInsn(BIPUSH, arity);
		else
			mv.visitIntInsn(SIPUSH, arity);

		mv.visitMethodInsn(INVOKEVIRTUAL, fullClassName, "insnYIELD1", "(I)V");


		System.out.println(currentName + " Y1 : entrypoint :" + hotEntryPoint);
		mv.visitLabel(hotEntryLabels[hotEntryPoint]);

	}

	public void emitPanicReturn() {
		if (!emit)
			return;
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, fullClassName, "PANIC", "Lorg/eclipse/imp/pdb/facts/IString;");
		mv.visitInsn(ARETURN);
	}
}
