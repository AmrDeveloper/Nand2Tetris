import java.util.HashMap;
import java.util.List;

public class AssemblyCode {

    private int variableCounter;
    private List<Instruction> instructions;
    private HashMap<String, Integer> branches;
    private HashMap<String, Integer> variables;

    public AssemblyCode(List<Instruction> instructions,
                        HashMap<String, Integer> branches) {
        this.instructions = instructions;
        this.branches = branches;
        this.variables = new HashMap<>();
        this.variableCounter = 16;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public HashMap<String, Integer> getBranches() {
        return branches;
    }

    public HashMap<String, Integer> getVariables() {
        return variables;
    }

    public int addVariables(String symbol){
        int oldCounter = variableCounter;
        variables.put(symbol, variableCounter);
        variableCounter = variableCounter + 1;
        return oldCounter;
    }
}
