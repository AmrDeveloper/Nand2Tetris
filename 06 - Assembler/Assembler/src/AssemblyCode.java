import java.util.HashMap;
import java.util.List;

public class AssemblyCode {

    private List<Instruction> instructions;
    private HashMap<String, Integer> branches;

    public AssemblyCode(List<Instruction> instructions,
                        HashMap<String, Integer> branches) {
        this.instructions = instructions;
        this.branches = branches;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public HashMap<String, Integer> getBranches() {
        return branches;
    }
}
