import java.io.File;
import java.io.IOException;
import java.util.List;

public class VMTranslator {
    public static void main(String[] args) throws IOException {
        if(args.length < 1){
            throw new RuntimeException("Invalid Virtual Machine file path");
        }
        String inputPath = args[0];
        String outputFile = inputPath.replace(".vm", ".asm");
        File file = new File(inputPath);
        String fileName = file.getName().replace(".vm" , "");

        VirtualMachineParser parser = new VirtualMachineParser();
        List<Command> vmCommands = parser.parseCode(inputPath);

        VirtualMachineTranslator translator = new VirtualMachineTranslator(fileName, vmCommands);
        translator.translate(outputFile);
    }
}
