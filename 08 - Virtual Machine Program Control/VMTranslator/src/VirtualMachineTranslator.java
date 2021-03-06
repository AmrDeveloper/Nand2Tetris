import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class VirtualMachineTranslator implements CommandVisitors{

    private List<Command> commandList;
    private int branchIndex = 0;

    public VirtualMachineTranslator(List<Command> commandList){
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
                instructions.add("@" + command.getFileName() + "_" + command.getIndex());
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
                instructions.add("@" + command.getFileName() + "_" + command.getIndex());
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

    @Override
    public List<String> visit(Commands.LabelCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Label " + command.getLabel());
        instructions.add("("  + command.getLabel() + ")");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.GotoCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Go to " + command.getLabel());
        instructions.add("@"  + command.getLabel());
        instructions.add("0;JMP");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.IfGotoCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//if Go to " + command.getLabel());
        instructions.add("@SP");
        instructions.add("AM=M-1");
        instructions.add("D=M");
        instructions.add("@" + command.getLabel());
        instructions.add("D;JNE");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.FunctionCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Function command for " + command.getName());
        instructions.add("(" + command.getName() + ")");
        for(int i = 0 ; i < command.getVarsNum() ; i++){
            instructions.add("@SP");
            instructions.add("A=M");
            instructions.add("M=0");
            instructions.add("@SP");
            instructions.add("M=M+1");
        }
        return instructions;
    }

    @Override
    public List<String> visit(Commands.CallCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Call command for " + command.getName());
        //Push return_address
        instructions.add("@return_address_" + command.getName() + "_" + command.getArgsNum() + "_" + branchIndex);
        instructions.add("D=A");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");

        //Push LCL
        instructions.add("@LCL");
        instructions.add("D=M");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");
        //Push ARG
        instructions.add("@ARG");
        instructions.add("D=M");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");
        //Push This
        instructions.add("@THIS");
        instructions.add("D=M");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");
        //Push That
        instructions.add("@THAT");
        instructions.add("D=M");
        instructions.add("@SP");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("M=M+1");

        //ARG = SP - 5 - nArgs
        instructions.add("@5");
        instructions.add("D=A");
        instructions.add("@" + command.getArgsNum());
        instructions.add("D=D+A");
        instructions.add("@SP");
        instructions.add("D=M-D");
        instructions.add("@ARG");
        instructions.add("M=D");
        //instructions.add("@SP");
        //instructions.add("M=D");

        //LCL = SP
        instructions.add("@SP");
        instructions.add("D=M");
        instructions.add("@LCL");
        instructions.add("M=D");

        //GOTO function Name
        instructions.add("@" + command.getName());
        instructions.add("0;JMP");

        //(return_address)
        instructions.add("(return_address_" + command.getName() + "_" + command.getArgsNum() + "_" + branchIndex + ")");
        branchIndex = branchIndex + 1;
        return instructions;
    }

    @Override
    public List<String> visit(Commands.ReturnCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Return");
        instructions.add("//Return Command");
        instructions.add("@LCL");
        instructions.add("D=M");
        instructions.add("@endFrame");
        instructions.add("M=D");
        instructions.add("@endFrame");
        instructions.add("D=M");
        instructions.add("@5");
        instructions.add("A=D-A");
        instructions.add("D=M");
        instructions.add("@ret_addr");
        instructions.add("M=D");
        instructions.add("@SP");
        instructions.add("A=M-1");
        instructions.add("D=M");
        instructions.add("@ARG");
        instructions.add("A=M");
        instructions.add("M=D");
        instructions.add("@ARG");
        instructions.add("D=M+1");
        instructions.add("@SP");
        instructions.add("M=D");
        instructions.add("@endFrame");
        instructions.add("A=M-1");
        instructions.add("D=M");
        instructions.add("@THAT");
        instructions.add("M=D");
        instructions.add("@endFrame");
        instructions.add("D=M");
        instructions.add("@2");
        instructions.add("A=D-A");
        instructions.add("D=M");
        instructions.add("@THIS");
        instructions.add("M=D");
        instructions.add("@endFrame");
        instructions.add("D=M");
        instructions.add("@3");
        instructions.add("A=D-A");
        instructions.add("D=M");
        instructions.add("@ARG");
        instructions.add("M=D");
        instructions.add("@endFrame");
        instructions.add("D=M");
        instructions.add("@4");
        instructions.add("A=D-A");
        instructions.add("D=M");
        instructions.add("@LCL");
        instructions.add("M=D");
        instructions.add("@ret_addr");
        instructions.add("A=M");
        instructions.add("0;JMP");
        return instructions;
    }

    @Override
    public List<String> visit(Commands.InitCommand command) {
        List<String> instructions = new ArrayList<>();
        instructions.add("//Bootstrap code");
        instructions.add("@256");
        instructions.add("D=A");
        instructions.add("@SP");
        instructions.add("M=D");
        instructions.addAll(visit(new Commands.CallCommand("Sys.init", 0)));
        return instructions;
    }
}
