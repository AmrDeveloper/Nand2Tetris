import java.util.HashMap;
import java.util.Map;

public class AssemblyConstants {

    private static final Map<String,String> destTable = new HashMap<>();
    private static final Map<String,String> compTable = new HashMap<>();
    private static final Map<String,String> jumpTable = new HashMap<>();

    private volatile static AssemblyConstants mInstance = null;

    public static AssemblyConstants getInstance(){
        if(mInstance == null){
            mInstance = new AssemblyConstants();
            bindDestTableValues();
            bindCompTableValues();
            bindJumpTableValues();
        }
        return mInstance;
    }

    private static void bindDestTableValues(){
        destTable.put("null", "000");
        destTable.put("M", "001");
        destTable.put("D", "010");
        destTable.put("MD", "011");
        destTable.put("A", "100");
        destTable.put("AM", "101");
        destTable.put("AD", "110");
        destTable.put("AMD", "111");
    }

    private static void bindCompTableValues(){
        //a = 0
        compTable.put("0",   "0101010");
        compTable.put("1",   "0111111");
        compTable.put("-1",  "0111010");
        compTable.put("D",   "0001100");
        compTable.put("A",   "0110000");
        compTable.put("!D",  "0001111");
        compTable.put("!A",  "0110011");
        compTable.put("-D",  "0001111");
        compTable.put("-A",  "0110011");
        compTable.put("D+1", "0011111");
        compTable.put("A+1", "0110111");
        compTable.put("D-1", "0001110");
        compTable.put("A-1", "0110010");
        compTable.put("D+A", "0000010");
        compTable.put("D-A", "0010011");
        compTable.put("A-D", "0000111");
        compTable.put("D&A", "0000000");
        compTable.put("D|A", "0010101");
        //a = 1
        compTable.put("M",   "1110000");
        compTable.put("!M",  "1110001");
        compTable.put("-M",  "1110011");
        compTable.put("M+1", "1110111");
        compTable.put("M-1", "1110010");
        compTable.put("D+M", "1000010");
        compTable.put("D-M", "1010011");
        compTable.put("M-D", "1000111");
        compTable.put("D&M", "1000000");
        compTable.put("D|M", "1010101");
    }

    private static void bindJumpTableValues(){
        jumpTable.put("null", "000");
        jumpTable.put("JGT",  "001");
        jumpTable.put("JEQ",  "010");
        jumpTable.put("JGE",  "011");
        jumpTable.put("JLT",  "100");
        jumpTable.put("JNE",  "101");
        jumpTable.put("JLE",  "110");
        jumpTable.put("JMP",  "111");
    }

    public String getDestValue(String dest){
        return destTable.get(dest);
    }

    public String getCompValue(String comp){
        return compTable.get(comp);
    }

    public String getJumpValue(String jump){
        return jumpTable.get(jump);
    }
}
