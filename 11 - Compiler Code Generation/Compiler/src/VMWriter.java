import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class VMWriter {

    private PrintWriter mPrintWriter;
    private static HashMap<Segment, String> segmentStrMap = new HashMap<>();
    private static HashMap<Command, String> commandStrMap = new HashMap<>();

    static {
        //Segments
        segmentStrMap.put(Segment.CONST,"constant");
        segmentStrMap.put(Segment.ARGUMENT,"argument");
        segmentStrMap.put(Segment.LOCAL,"local");
        segmentStrMap.put(Segment.STATIC,"static");
        segmentStrMap.put(Segment.THIS,"this");
        segmentStrMap.put(Segment.THAT,"that");
        segmentStrMap.put(Segment.POINTER,"pointer");
        segmentStrMap.put(Segment.TEMP,"temp");

        //Commands
        commandStrMap.put(Command.ADD,"add");
        commandStrMap.put(Command.SUB,"sub");
        commandStrMap.put(Command.NEG,"neg");
        commandStrMap.put(Command.EQ,"eq");
        commandStrMap.put(Command.GT,"gt");
        commandStrMap.put(Command.LT,"lt");
        commandStrMap.put(Command.AND,"and");
        commandStrMap.put(Command.OR,"or");
        commandStrMap.put(Command.NOT,"not");
    }

    public VMWriter(File output){
        try {
            mPrintWriter = new PrintWriter(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writePush(Segment segment, int index){
        //write vm push command in file
        //push + segment name + index
        writeInstruction("push" , segmentStrMap.get(segment), String.valueOf(index));
    }

    public void writePop(Segment segment, int index){
        //write vm pop command in file
        //pop + segment name + index
        writeInstruction("pop" , segmentStrMap.get(segment), String.valueOf(index));
    }

    public void writeArithmetic(Command command){
        //write command
        writeInstruction(commandStrMap.get(command));
    }

    public void writeLabel(String label){
        writeInstruction("label",label);
    }

    public void writeGoto(String label){
        writeInstruction("goto", label);
    }

    public void writeIf(String label){
        writeInstruction("if-goto", label);
    }

    public void writeCall(String name, int nArgs){
        writeInstruction("call", name, String.valueOf(nArgs));
    }

    public void writeFunction(String name, int nArgs){
        writeInstruction("function", name, String.valueOf(nArgs));
    }

    public void writeReturn(){
        writeInstruction("return");
    }

    public void writeInstruction(String command){
        mPrintWriter.write(command + "\n");
    }

    public void writeInstruction(String command, String arg1){
        mPrintWriter.write(command + " " + arg1 + "\n");
    }

    public void writeInstruction(String command, String arg1, String arg2){
        mPrintWriter.write(command + " " + arg1 + " " + arg2 + "\n");
    }

    public void closeStream(){
        //close output stream
        mPrintWriter.close();
    }
}
