package org.rascalmpl.library.experiments.Compiler.Commands;

import java.io.StringWriter;
import java.net.URISyntaxException;

import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RVMCore;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RascalExecutionContext;
import org.rascalmpl.library.experiments.Compiler.RVM.Interpreter.RascalExecutionContextBuilder;
import org.rascalmpl.value.ISourceLocation;
import org.rascalmpl.value.IValueFactory;
import org.rascalmpl.values.ValueFactoryFactory;

public class Rascal {

    static IValueFactory vf = ValueFactoryFactory.getValueFactory();

    static ISourceLocation findBinary(ISourceLocation binDir, String moduleName){
        StringWriter sw = new StringWriter();
        sw.append(binDir.getPath())
        .append("/")
        .append(moduleName.replaceAll("::", "/"))
        .append(".rvm.ser.gz");
        try {
            return vf.sourceLocation("compressed+" + binDir.getScheme(), binDir.getAuthority(), sw.toString());
        } catch (URISyntaxException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }

    /**
     * Main function for execute command: rascal
     * 
     * @param args	list of command-line arguments
     */
    public static void main(String[] args) {
        try {

            CommandOptions cmdOpts = new CommandOptions("rascal");
            cmdOpts
            .pathOption("libPath")		.pathDefault((co) -> vf.list(co.getCommandLocOption("binDir")))
            .help("Add new lib paths, use multiple --libPaths for multiple paths")

            .locOption("bootDir") 		.locDefault(cmdOpts.getDefaultBootLocation())
            .help("Rascal boot directory")

            .locOption("binDir") 		.help("Directory for Rascal binaries")

            //.boolOption("jvm") 			.help("Generate JVM code")

            .boolOption("verbose")		.help("Print compilation steps")

            .boolOption("help")			.help("Print help message for this command")

            .boolOption("trace")		.help("Print Rascal functions during execution")

            .boolOption("profile")		.help("Profile execution of Rascal program")

            .rascalModule("RascalModule::main() to be executed")

            .handleArgs(args);

            RascalExecutionContext rex = RascalExecutionContextBuilder.normalContext(ValueFactoryFactory.getValueFactory())
                    .setTrace(cmdOpts.getCommandBoolOption("trace"))
                    .setProfile(cmdOpts.getCommandBoolOption("profile"))
                    .forModule(cmdOpts.getRascalModule().getValue())
                    .build();

            ISourceLocation binary = findBinary(cmdOpts.getCommandLocOption("binDir"), cmdOpts.getRascalModule().getValue());
            System.out.println(RVMCore.readFromFileAndExecuteProgram(binary, cmdOpts.getModuleOptionsAsIMap(), rex));
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("rascal: cannot execute program: " + e.getMessage());
            System.exit(1);
        }
    }
}
