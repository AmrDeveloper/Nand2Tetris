public class InstructionC extends Instruction{

    public InstructionC(String text, int lineNum) {
        super(text, lineNum);
    }

    @Override
    public String accept(AssemblerVisitor visitor) {
        return visitor.visit(this);
    }
}
