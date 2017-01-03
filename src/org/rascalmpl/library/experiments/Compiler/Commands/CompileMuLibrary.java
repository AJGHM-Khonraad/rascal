package org.rascalmpl.library.experiments.Compiler.Commands;

import java.io.IOException;
import java.net.URISyntaxException;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.NoSuchRascalFunction;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.java2rascal.Java2Rascal;
import org.rascalmpl.library.lang.rascal.boot.IKernel;
import org.rascalmpl.library.util.PathConfig;
import org.rascalmpl.value.IValueFactory;
import org.rascalmpl.values.ValueFactoryFactory;

public class CompileMuLibrary {

    /**
     * This command is used by Bootstrap only.
     * 
     * @param args	list of command-line arguments
     * @throws NoSuchRascalFunction 
     * @throws IOException 
     * @throws URISyntaxException 
     */
    public static void main(String[] args)  {
        try {
            IValueFactory vf = ValueFactoryFactory.getValueFactory();
            CommandOptions cmdOpts = new CommandOptions("compileMuLibrary");
            cmdOpts
            .locsOption("src")		
            .locsDefault(cmdOpts.getDefaultStdlocs().isEmpty() ? vf.list(cmdOpts.getDefaultStdlocs()) : cmdOpts.getDefaultStdlocs())
            .respectNoDefaults()
            .help("Add (absolute!) source location, use multiple --src arguments for multiple locations")
            
            .locsOption("lib")		
            .locsDefault((co) -> vf.list(co.getCommandLocOption("bin")))
            .respectNoDefaults()
            .help("Add new lib location, use multiple --lib arguments for multiple locations")
            
            .locOption("boot")		
            .locDefault(cmdOpts.getDefaultBootLocation())
            .help("Rascal boot directory")
            
            .locOption("bin") 		
            .respectNoDefaults()
            .help("Directory for Rascal binaries")
            
            .locsOption("courses")
            .locsDefault(PathConfig.getDefaultCoursesList())
            .help("Add new courses location, use multipl --courses arguments for multiple locations")
            
            .locsOption("javaCompilerPath")
            .locsDefault(PathConfig.getDefaultJavaCompilerPathList())
            .help("Add new java classpath location, use multiple --javaCompilerPath options for multiple locations")
        
            .locsOption("classloaders")
            .locsDefault(PathConfig.getDefaultClassloadersList())
            .help("Add new java classloader location, use multiple --classloader options for multiple locations")
        
            .boolOption("help") 		
            .help("Print help message for this command")
            
            .boolOption("trace") 		
            .help("Print Rascal functions during execution of compiler")
            
            .boolOption("profile") 		
            .help("Profile execution of compiler")
           
            .boolOption("verbose") 		
            .help("Make the compiler verbose")
            
            .noModuleArgument()
            .handleArgs(args);

            PathConfig pcfg = cmdOpts.getPathConfig();
            IKernel kernel = Java2Rascal.Builder.bridge(vf, pcfg, IKernel.class)
                .trace(cmdOpts.getCommandBoolOption("trace"))
                .profile(cmdOpts.getCommandBoolOption("profile"))
                .verbose(cmdOpts.getCommandBoolOption("verbose")).
                build();

            kernel.compileMuLibrary(pcfg.asConstructor(kernel), kernel.kw_compileMu());
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
