import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class VirtualMachineParser {

    public List<Command> parse(File inputFile){
        if(inputFile.isDirectory()){
            return parseDirectory(inputFile);
        }else{
            return parseCode(inputFile.getPath(), inputFile.getName());
        }
    }

    public List<Command> parseCode(String inputFile, String fileName){
        List<String> vmLines = readVmFile(inputFile);
        return parseCommandList(vmLines, fileName);
    }

    public List<Command> parseDirectory(File directory){
        List<Command> vmLines = new ArrayList<>();
        File[] filesList = Objects.requireNonNull(directory.listFiles());
        for(File file : filesList){
            if(file.getName().endsWith(".vm")){
                if(file.getName().endsWith("Sys.vm")){
                    vmLines.add(0, new Commands.InitCommand());
                    List<Command> sublist = parseCode(file.getPath(), file.getName());
                    System.out.println(file.getPath());
                    vmLines.addAll(1, sublist);
                }else{
                    List<Command> sublist = parseCode(file.getPath(), file.getName());
                    vmLines.addAll(sublist);
                }
            }
        }
        return vmLines;
    }

    public List<Command> parseCommandList(List<String> lines, String fileName){
        List<Command> commands = new ArrayList<>();
        for(String line : lines){
            String[] args = line.split(" ");
            String commandName = args[0];
            switch (commandName){
                case "push":
                    String pushSegment = args[1];
                    String pushIndex = args[2];
                    commands.add(new Commands.PushCommand(pushIndex, pushSegment, fileName));
                    break;
                case "pop":
                    String popSegment = args[1];
                    String popIndex = args[2];
                    commands.add(new Commands.PopCommand(popIndex, popSegment, fileName));
                    break;
                case "add":
                    commands.add(new Commands.AddCommand());
                    break;
                case "sub":
                    commands.add(new Commands.SubCommand());
                    break;
                case "neg":
                    commands.add(new Commands.NegCommand());
                    break;
                case "eq":
                    commands.add(new Commands.EqualCommand());
                    break;
                case "gt":
                    commands.add(new Commands.GreaterThanCommand());
                    break;
                case "lt":
                    commands.add(new Commands.LessThanCommand());
                    break;
                case "and":
                    commands.add(new Commands.AndCommand());
                    break;
                case "or":
                    commands.add(new Commands.OrCommand());
                    break;
                case "not":
                    commands.add(new Commands.NotCommand());
                    break;
                //Branch Commands
                case "goto":
                    String labelGoto = args[1];
                    commands.add(new Commands.GotoCommand(labelGoto));
                    break;
                case "label":
                    String labelLabel = args[1];
                    commands.add(new Commands.LabelCommand(labelLabel));
                    break;
                case "if-goto":
                    String labelIfGoto = args[1];
                    commands.add(new Commands.IfGotoCommand(labelIfGoto));
                    break;
                //Function Commands
                case "function":
                    String functionName = args[1];
                    int functionVarsNum = Integer.parseInt(args[2]);
                    commands.add(new Commands.FunctionCommand(functionName, functionVarsNum));
                    break;
                case "call":
                    String callName = args[1];
                    int callArgsNum = Integer.parseInt(args[2]);
                    commands.add(new Commands.CallCommand(callName, callArgsNum));
                    break;
                case "return":
                    commands.add(new Commands.ReturnCommand());
                    break;
            }
        }
        return commands;
    }

    private List<String> readVmFile(String vmFile){
        List<String> lines = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(vmFile);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("//") || line.isEmpty()) {
                    continue;
                }else if(line.contains("//")){
                    String[] args = line.split("/");
                    line = args[0].trim();
                }
                lines.add(line);
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
