package org.rascalmpl.library.experiments.Compiler.Commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import org.rascalmpl.uri.URIUtil;

/**
 * This is experimental code.
 */
public class Bootstrap {
    private static Process childProcess;

    public static class BootstrapMessage extends Exception {
		private static final long serialVersionUID = -1L;
		protected int phase;

        public BootstrapMessage(int phase) {
            this.phase = phase;
        }

        @Override
        public String getMessage() {
            return "Failed during phase " + phase;
        }
    }
    
    public static void main(String[] args) {
        if (args.length != 5) {
        	System.err.println("Usage: Bootstrap <classpath> <versionToBootstrapOff> <versionToBootstrapTo> <sourceFolder> <targetFolder>");
        	return;
        }
        
        int arg = 0;
        String classpath = args[arg++];
        String versionToUse = args[arg++];
        String versionToBuild = args[arg++];
        
        Thread destroyChild = new Thread() {
            public void run() {
                synchronized (Bootstrap.class) {
                    if (childProcess != null && childProcess.isAlive()) {
                        childProcess.destroy();
                    }
                }
            }
        };
        
        Runtime.getRuntime().addShutdownHook(destroyChild);
        
        Path sourceFolder = new File(args[arg++]).toPath();
        if (!Files.exists(sourceFolder.resolve("org/rascalmpl/library/Prelude.rsc"))) {
        	throw new RuntimeException("source folder " + sourceFolder + " should point to source folder of standard library containing Prelude and the compiler");
        }
        String librarySource = sourceFolder.resolve("org/rascalmpl/library").toAbsolutePath().toString();
        
        Path targetFolder = new File(args[arg++]).toPath();
        if (!Files.exists(targetFolder.resolve("org/rascalmpl/library/Prelude.class"))) {	// PK: PreludeCompiled
        	throw new RuntimeException("target folder " + sourceFolder + " should point to source folder of compiler library and the RVM interpreter.");
        }
        
        Path tmpDir = new File(System.getProperty("java.io.tmpdir") + "/rascal-boot").toPath();
        tmpDir.toFile().mkdir();
        info("bootstrap folder: " + tmpDir.toAbsolutePath());

        if (existsDeployedVersion(tmpDir, versionToBuild)) {
            System.out.println("INFO: Got the kernel version to compile: " + versionToBuild + " already from existing deployed build.");
        }
        
        // We bootstrap in three + one stages, in each step generating a new Kernel file using an existing version:
        //    0. download a released version
        //    1. build new kernel with old jar using kernel inside the jar (creating K')
        //    2. build new kernel with old jar loading kernel K' (creating K'')
        //    3. build new kernel with new classes using kernel K'' (creating K'''')
        try { 
            Path phase0Version = getDeployedVersion(tmpDir, versionToUse);
            Path phase1Version = compilePhase(1, phase0Version.toAbsolutePath().toString(), tmpDir, "|boot:///|", librarySource);
            Path phase2Version = compilePhase(2, phase0Version.toAbsolutePath().toString(), tmpDir, phase1Version.toAbsolutePath().toString(), librarySource);
            Path phase3Version = compilePhase(3, targetFolder + ":" + classpath, tmpDir, phase2Version.toAbsolutePath().toString(), librarySource);
            Path phase4Version = compilePhase(4, targetFolder + ":" + classpath, tmpDir, phase3Version.toAbsolutePath().toString(), "|std:///|");
            
            copyResult(phase4Version, targetFolder.resolve("boot"));
        } 
        catch (BootstrapMessage | IOException | InterruptedException e) {
            info(e.getMessage());
			e.printStackTrace();
		} 
    }
    
	private static void copyResult(Path sourcePath, Path targetPath) throws IOException {
	    Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult preVisitDirectory(final Path dir,  final BasicFileAttributes attrs) throws IOException {
	            Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
	            return FileVisitResult.CONTINUE;
	        }

	        @Override
	        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
	            Files.copy(file, targetPath.resolve(sourcePath.relativize(file)));
	            return FileVisitResult.CONTINUE;
	        }
	    });
    }

    private static boolean existsDeployedVersion(Path folder, String version) {	
		try (InputStream s = deployedVersion(version).toURL().openStream()) {
			return s != null;
		} catch (IOException e) {
			return false;
		} 
	}
    
    /**
     * Either download or get the jar of the deployed version of Rascal from a previously downloaded instance.
     */
    private static Path getDeployedVersion(Path tmp, String version) throws IOException {
		Path cached = cachedDeployedVersion(tmp, version);
		
		
		if (!cached.toFile().exists()) {
    		URI deployedVersion = deployedVersion(version);
    		info("downloading " + deployedVersion);
			Files.copy(deployedVersion.toURL().openStream(), cached);
    	}
		
		info("deployed version ready: " + cached);
		return cached;
    }

    private static Path cachedDeployedVersion(Path tmpFolder, String version) {
    	return tmpFolder.resolve("rascal-" + version + ".jar");
	}

	private static URI deployedVersion(String version) {
	    if ("unstable".equals(version)) {
	        info("YOU ARE NOT SUPPOSED TO BOOTSTRAP OFF AN UNSTABLE VERSION! ***ONLY FOR DEBUGGING PURPOSES***");
	        return unstableVersion();
	    }

	    return URIUtil.assumeCorrect("http", "update.rascal-mpl.org", "/console/rascal-" + version + ".jar");
	}
	
	private static URI unstableVersion() {
        return URIUtil.assumeCorrect("http", "update.rascal-mpl.org", "/console/rascal-shell-unstable.jar");
    }
	
    private static Path phaseFolder(int phase, Path tmp) {
        Path result = tmp.resolve("phase" + phase);
        result.toFile().mkdir();
        return result;
    }
    
    private static Path compilePhase(int phase, String classPath, Path tmp, String bootPath, String sourcePath) throws BootstrapMessage, IOException, InterruptedException {
        Path result = phaseFolder(phase, tmp);
        info("phase " + phase + ": " + result);
       
        if (phase > 3) compileMuLibrary(phase, classPath, bootPath, sourcePath, result);
        compileModule(phase, classPath, bootPath, sourcePath, result, "Prelude");
        compileModule(phase, classPath, bootPath, sourcePath, result, "lang::rascal::boot::Kernel");
        compileModule(phase, classPath, bootPath, sourcePath, result, "lang::rascal::grammar::ParserGenerator");
        
        return result;
    }

    private static void compileModule(int phase, String classPath, String bootDir, String sourcePath, Path result,
            String module) throws IOException, InterruptedException, BootstrapMessage {
        info("\tcompiling " + module);
        if (runCompiler(classPath, 
                "--binDir", result.toAbsolutePath().toString(),
                "--srcPath", sourcePath,
                "--bootDir", bootDir,
                "--verbose",
                module) != 0) {
            
            throw new BootstrapMessage(phase);
        }
    }
    
    private static void compileMuLibrary(int phase, String classPath, String bootDir, String sourcePath, Path result) throws IOException, InterruptedException, BootstrapMessage {
        info("\tcompiling MuLibrary");
        if (runMuLibraryCompiler(classPath, 
                "--binDir", result.toAbsolutePath().toString(),
                "--srcPath", sourcePath,
                "--bootDir", bootDir) != 0) {
            
            throw new BootstrapMessage(phase);
        }
    }

    private static int runCompiler(String classPath, String... arguments) throws IOException, InterruptedException {
    	String[] command = new String[arguments.length + 4];
    	command[0] = "java";
    	command[1] = "-cp";
    	command[2] = classPath;
    	command[3] = "org.rascalmpl.library.experiments.Compiler.Commands.RascalC";
    	System.arraycopy(arguments, 0, command, 4, arguments.length);
    	return runChildProcess(command);
    }
    
    private static int runMuLibraryCompiler(String classPath, String... arguments) throws IOException, InterruptedException {
        String[] command = new String[arguments.length + 4];
        command[0] = "java";
        command[1] = "-cp";
        command[2] = classPath;
        command[3] = "org.rascalmpl.library.experiments.Compiler.Commands.CompileMuLibrary";
        System.arraycopy(arguments, 0, command, 4, arguments.length);
        return runChildProcess(command);
    }
    
    private static int runChildProcess(String[] command) throws IOException, InterruptedException {
        synchronized (Bootstrap.class) {
            info("command: " + Arrays.toString(command));
            childProcess = new ProcessBuilder(command).inheritIO().start();
            childProcess.waitFor();
            return childProcess.exitValue();    
        }
    }
    
    private static void info(String msg) {
        System.err.println("INFO:" + msg);
    }
}
