import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VirtualMachineParser {

    public List<Command> parseCode(String inputFile){
        List<String> vmLines = readVmFile(inputFile);
        return parseCommandList(vmLines);
    }

    public List<Command> parseCommandList(List<String> lines){
        List<Command> commands = new ArrayList<>();
        for(String line : lines){
            String[] args = line.split(" ");
            String commandName = args[0];
            switch (commandName){
                case "push":
                    String pushSegment = args[1];
                    String pushIndex = args[2];
                    commands.add(new Commands.PushCommand(pushIndex, pushSegment));
                    break;
                case "pop":
                    String popSegment = args[1];
                    String popIndex = args[2];
                    commands.add(new Commands.PopCommand(popIndex, popSegment));
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
