import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AssemblyParser {

    public AssemblyCode runAssemblyParser(String assemblyFile){
        List<String> lines = readAssemblyFile(assemblyFile);
        return instructionsList(lines);
    }

    private AssemblyCode instructionsList(List<String> lines) {
        int lineCounter = 0;
        HashMap<String, Integer> branches = new HashMap<>();
        List<Instruction> instructions = new ArrayList<>();
        for(String line : lines){
            if(line.startsWith("@")){
                //A Instruction
                line = line.replaceAll("@","");
                switch (line){
                    //Virtual Registers
                    case "R0": line= "0";break;
                    case "R1": line="1";break;
                    case "R2": line= "2";break;
                    case "R3":line= "3";break;
                    case "R4":line= "4";break;
                    case "R5":line= "5";break;
                    case "R6":line= "6";break;
                    case "R7":line= "7";break;
                    case "R8":line= "8";break;
                    case "R9":line= "9";break;
                    case "R10":line= "10";break;
                    case "R11":line= "11";break;
                    case "R12":line= "12";break;
                    case "R13":line= "13";break;
                    case "R14":line= "14";break;
                    case "R15":line= "15";break;

                    //IO Devices
                    case "SCREEN":line="16384";break;
                    case "KBD": line="24576";break;

                    //Other Symbols
                    case "SP": line= "0"; break;
                    case "LCL": line="1";break;
                    case "ARG": line= "2";break;
                    case "THIS": line= "3";break;
                    case "THAT": line= "4";break;
                }
                InstructionA instructionA = new InstructionA(line, lineCounter);
                instructions.add(instructionA);
                lineCounter++;
            }
            else if(line.startsWith("(") && line.endsWith(")")){
                //Branch
                String branchName = line.replaceAll("\\(","");
                branchName = branchName.replaceAll("\\)","");
                branches.put(branchName, lineCounter);
            }
            else{
                //C Instruction
                InstructionC instructionC = new InstructionC(line, lineCounter);
                instructions.add(instructionC);
                lineCounter++;
            }
        }
        return new AssemblyCode(instructions, branches);
    }

    private List<String> readAssemblyFile(String assemblyFile) {
        List<String> lines = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(assemblyFile);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.contains("//") || line.isEmpty()) {
                    continue;
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
