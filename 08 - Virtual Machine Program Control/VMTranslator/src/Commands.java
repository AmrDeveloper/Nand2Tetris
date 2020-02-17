import java.util.List;

public class Commands {

    /**
     * Arithmetic Commands
     */
    public static class AddCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class SubCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class NegCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class EqualCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class GreaterThanCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class LessThanCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class AndCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class OrCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class NotCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Memory Access Commands
     */
    public static abstract class MemoryCommand extends Command{

        private String index;
        private String segment;
        private String fileName;

        public MemoryCommand(String index, String segment, String fileName){
            this.index = index;
            this.segment = segment;
            this.fileName = fileName;
        }

        public String getIndex() {
            return index;
        }

        public String getSegment() {
            return segment;
        }

        public String getFileName(){
            return fileName;
        }
    }

    public static class PushCommand extends MemoryCommand{

        public PushCommand(String index, String segment, String fileName) {
            super(index, segment,fileName);
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class PopCommand extends MemoryCommand{

        public PopCommand(String index, String segment, String fileName) {
            super(index, segment, fileName);
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Branch Commands
     */
    public static abstract class BranchCommand extends Command{

        private String label;

        public BranchCommand(String label){
            this.label = label;
        }

        public String getLabel(){
            return label;
        }
    }

    public static class GotoCommand extends BranchCommand{

        public GotoCommand(String label) {
            super(label);
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class LabelCommand extends BranchCommand{

        public LabelCommand(String label) {
            super(label);
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class IfGotoCommand extends BranchCommand{
        public IfGotoCommand(String label) {
            super(label);
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    /**
     * Function Commands
     */
    public static class FunctionCommand extends Command{

        private String name;
        private int varsNum;

        public FunctionCommand(String name, int varsNum) {
            this.name = name;
            this.varsNum = varsNum;
        }

        public String getName() {
            return name;
        }

        public int getVarsNum() {
            return varsNum;
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class CallCommand extends Command{

        private String name;
        private int argsNum;

        public CallCommand(String name, int argsNum) {
            this.name = name;
            this.argsNum = argsNum;
        }

        public String getName() {
            return name;
        }

        public int getArgsNum() {
            return argsNum;
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class ReturnCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class InitCommand extends Command{

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }
}
