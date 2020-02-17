import java.io.File;
import java.io.IOException;
import java.util.List;

public class VMTranslator {
    public static void main(String[] args) throws IOException {
        if(args.length < 1){
            throw new RuntimeException("Invalid Virtual Machine file path");
        }

        String inputPath = args[0];
        File file = new File(inputPath);
        String fileName = file.getName().replace(".vm" , "");

        VirtualMachineParser parser = new VirtualMachineParser();
        String output = getOutputFilePath(inputPath, fileName);
        List<Command> commands = parser.parse(file);

        VirtualMachineTranslator translator = new VirtualMachineTranslator(commands);
        translator.translate(output);
    }

    public static String getOutputFilePath(String input, String fileName){
        if(input.endsWith(".vm")){
            return input.replaceAll(".vm", ".asm");
        }else{
            return input + File.separator +fileName + ".asm";
        }
    }
}
