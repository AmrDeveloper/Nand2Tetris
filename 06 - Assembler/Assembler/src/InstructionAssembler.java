import java.util.List;

public class InstructionAssembler implements AssemblerVisitor {
    private AssemblyCode assemblyCode;
    private AssemblyConstants assemblyConstants;

    public InstructionAssembler(AssemblyCode assemblyCode){
        this.assemblyCode = assemblyCode;
        this.assemblyConstants = AssemblyConstants.getInstance();
    }

    public String translate(){
        StringBuilder machineLanguageCode = new StringBuilder();
        List<Instruction> instructions = assemblyCode.getInstructions();
        for(Instruction instruction : instructions){
            String output = instruction.accept(this);
            machineLanguageCode.append(output).append("\n");
        }
        return machineLanguageCode.toString();
    }

    @Override
    public String visit(InstructionA instructionA) {
        String symbol = instructionA.getText();
        if(symbol.matches("[0-9]+")){
            int value = Integer.parseInt(symbol);
            return AssemblerUtils.integerTo16BitBinary(value);
        }
        else if(assemblyCode.getBranches().containsKey(symbol)){
            int value = assemblyCode.getBranches().get(symbol);
            return AssemblerUtils.integerTo16BitBinary(value);
        }else{
            if(assemblyCode.getVariables().containsKey(symbol)){
                int value = assemblyCode.getBranches().get(symbol);
                return AssemblerUtils.integerTo16BitBinary(value);
            }else{
                int value = assemblyCode.addVariables(symbol);
                return AssemblerUtils.integerTo16BitBinary(value);
            }
        }
    }

    @Override
    public String visit(InstructionC instructionC) {
        String line = instructionC.getText();

        String dest = "null";
        String comp = "";
        String jump ="null";

        //Without Jump
        if(line.contains("=")){
            String[] args = line.split("=");
            dest = args[0];
            comp = args[1];
        }
        //With Jump
        else{
            String[] args = line.split(";");
            comp = args[0];
            jump = args[1];
        }
        //Get Values from Constants Table
        dest = assemblyConstants.getDestValue(dest);
        comp = assemblyConstants.getCompValue(comp);
        jump = assemblyConstants.getJumpValue(jump);
        //Generate Code
        return "111" + comp + dest + jump;
    }
}
