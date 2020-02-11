import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class VirtualMachineTranslator implements CommandVisitors{

    private String fileName;
    private List<Command> commandList;
    private int branchIndex = 0;

    public VirtualMachineTranslator(String fileName, List<Command> commandList){
        this.fileName = fileName;
        this.commandList = commandList;
        this.branchIndex = 0;
    }

    public String translate(){
        StringBuilder assemblyCode = new StringBuilder();
        for(Command command : commandList){
            List<String> output = command.accept(this);
            for(String instruction : output){
                assemblyCode.append(instruction).append("\n");
            }
        }
        return assemblyCode.toString();
    }

    public void translate(String outFile) throws IOException {
        String output = translate().trim();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outFile), StandardCharsets.UTF_8))) {
            writer.write(output);
        }
        System.out.println("Translate is Done");
    }

    @Override
    public List<String> visit(Commands.AddCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Add Command");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("D=M");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("A=M");
        instructions.add("D=D+A");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.SubCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Sub Command");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("D=M");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("A=M");
        instructions.add("D=A-D");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.NegCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Not Command");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("M=-M");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.EqualCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Equal Command");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("D=M");
        instructions.add("@R13");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("D=M");
        instructions.add("@R13");
        instructions.add("D=D-M");
        instructions.add("@EQUAL_" + branchIndex);
        instructions.add("D;JEQ");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("M=0");
        instructions.add("@NOT_EQUAL_" + branchIndex);
        instructions.add("0;JMP");
        instructions.add("(EQUAL_" + branchIndex +")");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("M=-1");
        instructions.add("(NOT_EQUAL_" + branchIndex +")");
        branchIndex = branchIndex + 1;
        return instructions;
    }

    @Override
    public List<String> visit(Commands.GreaterThanCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Greater than Command");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("D=M");
        instructions.add("@R13");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("D=M");
        instructions.add("@R13");
        instructions.add("D=D-M");
        instructions.add("@GT_" + branchIndex);
        instructions.add("D;JGT");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("M=0");
        instructions.add("@NOT_GT_" + branchIndex);
        instructions.add("0;JMP");
        instructions.add("(GT_" + branchIndex + ")");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("M=-1");
        instructions.add("(NOT_GT_" + branchIndex + ")");
        branchIndex = branchIndex + 1;
        return instructions;
    }

    @Override
    public List<String> visit(Commands.LessThanCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Less than Command");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("D=M");
        instructions.add("@R13");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("D=M");
        instructions.add("@R13");
        instructions.add("D=D-M");
        instructions.add("@LT_" + branchIndex);
        instructions.add("D;JLT");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("M=0");
        instructions.add("@NOT_LT_" + branchIndex);
        instructions.add("0;JMP");
        instructions.add("(LT_" + branchIndex + ")");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("M=-1");
        instructions.add("(NOT_LT_" + branchIndex + ")");
        branchIndex = branchIndex + 1;
        return instructions;
    }

    @Override
    public List<String> visit(Commands.AndCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//And Command");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("D=M");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("A=M");
        instructions.add("D=D&A");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.OrCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Or Command");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("D=M");
        instructions.add("@SP");
        instructions.add("M=M-1");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("A=M");
        instructions.add("D=D|A");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.NotCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Not Command");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("M=!M");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.PushCommand command) {
        String segment = command.getSegment();
        List<String> instructions = new ArrayList<>();
        switch (segment){
            case "local":{
                instructions.add("//Push Local");
                instructions.add("@LCL");
                instructions.add("D=M");
                instructions.add("@" + command.getIndex());
                instructions.add("A=A+D");
                instructions.add("D=M");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("M=M+1");
                break;
            }
            case "argument":{
                instructions.add("//Push Argument");
                instructions.add("@ARG");
                instructions.add("D=M");
                instructions.add("@" + command.getIndex());
                instructions.add("A=A+D");
                instructions.add("D=M");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("M=M+1");
                break;
            }
            case "this":{
                instructions.add("//Push This");
                instructions.add("@THIS");
                instructions.add("D=M");
                instructions.add("@" + command.getIndex());
                instructions.add("A=A+D");
                instructions.add("D=M");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("M=M+1");
                break;
            }
            case "that":{
                instructions.add("//Push That");
                instructions.add("@THAT");
                instructions.add("D=M");
                instructions.add("@" + command.getIndex());
                instructions.add("A=A+D");
                instructions.add("D=M");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("M=M+1");
                break;
            }
            case "constant":{
                instructions.add("//Push constant");
                instructions.add("@" + command.getIndex());
                instructions.add("D=A");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("M=M+1");
                break;
            }
            case "static" : {
                //Invalid you mush add it to the stack
                instructions.add("//Push Static");
                instructions.add("@" + fileName + "." + command.getIndex());
                instructions.add("D=M");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("M=M+1");
                break;
            }
            case "temp":{
                String tempAddress = String.valueOf(5 + Integer.parseInt(command.getIndex()));
                instructions.add("@" + tempAddress);
                instructions.add("D=M");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("M=M+1");
                break;
            }
            case "pointer":{
                String subSegment = command.getIndex().equals("0") ? "THIS" : "THAT";
                instructions.add("//Push Pointer from " + subSegment);
                instructions.add("@SP");
                instructions.add("D=A");
                instructions.add("@" + subSegment);
                instructions.add("AD=D+A");
                instructions.add("D=M");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("M=M+1");
                break;
            }
        }
        return instructions;
    }

    @Override
    public List<String> visit(Commands.PopCommand command) {
        String segment = command.getSegment();
        List<String> instructions = new ArrayList<>();
        switch (segment){
            case "local":{
                instructions.add("//Pop Local");
                instructions.add("@LCL");
                instructions.add("D=M");
                instructions.add("@" + command.getIndex());
                instructions.add("D=D+A");
                instructions.add("@R13");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("AM=M-1");
                instructions.add("D=M");
                instructions.add("@R13");
                instructions.add("A=M");
                instructions.add("M=D");
                break;
            }
            case "argument":{
                instructions.add("//Pop argument");
                instructions.add("@ARG");
                instructions.add("D=M");
                instructions.add("@" + command.getIndex());
                instructions.add("D=D+A");
                instructions.add("@R13");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("AM=M-1");
                instructions.add("D=M");
                instructions.add("@R13");
                instructions.add("A=M");
                instructions.add("M=D");
                break;
            }
            case "this":{
                instructions.add("//Pop This");
                instructions.add("@THIS");
                instructions.add("D=M");
                instructions.add("@" + command.getIndex());
                instructions.add("D=D+A");
                instructions.add("@R13");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("AM=M-1");
                instructions.add("D=M");
                instructions.add("@R13");
                instructions.add("A=M");
                instructions.add("M=D");
                break;
            }
            case "that":{
                instructions.add("//Pop That");
                instructions.add("@THAT");
                instructions.add("D=M");
                instructions.add("@" + command.getIndex());
                instructions.add("D=D+A");
                instructions.add("@R13");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("AM=M-1");
                instructions.add("D=M");
                instructions.add("@R13");
                instructions.add("A=M");
                instructions.add("M=D");
                break;
            }
            case "static":{
                instructions.add("//Pop Static");
                instructions.add("@SP");
                instructions.add("M=M-1");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("D=M");
                instructions.add("@" + fileName + "." + command.getIndex());
                instructions.add("M=D");
                break;
            }
            case "temp":{
                instructions.add("@SP");
                instructions.add("AM=M-1");
                instructions.add("D=M");
                String tempAddress = String.valueOf(5 + Integer.parseInt(command.getIndex()));
                instructions.add("@" + tempAddress);
                instructions.add("M=D");

                break;
            }
            case "pointer":{
                String subSegment = command.getIndex().equals("0") ? "THIS" : "THAT";
                instructions.add("//Pop Pointer from " + subSegment);
                instructions.add("@SP");
                instructions.add("M=M-1");
                instructions.add("@SP");
                instructions.add("D=A");
                instructions.add("@" + subSegment);
                instructions.add("AD=D+A");
                instructions.add("@R13");
                instructions.add("M=D");
                instructions.add("@SP");
                instructions.add("A=M");
                instructions.add("D=M");
                instructions.add("@R13");
                instructions.add("A=M");
                instructions.add("M=D");
                break;
            }
        }
        return instructions;
    }
}
