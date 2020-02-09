public class InstructionA extends Instruction {
    public InstructionA(String text, int lineNum) {
        super(text, lineNum);
    }

    @Override
    public String accept(AssemblerVisitor visitor) {
        return visitor.visit(this);
    }
}
