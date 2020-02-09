public abstract class Instruction {
    private String text;
    private int lineNum;

    public Instruction(String text, int lineNum) {
        this.text = text;
        this.lineNum = lineNum;
    }

    public String getText() {
        return text;
    }

    public int getLineNum() {
        return lineNum;
    }

    public abstract String accept(AssemblerVisitor visitor);
}
