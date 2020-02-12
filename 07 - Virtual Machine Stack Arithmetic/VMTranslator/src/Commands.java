import java.util.List;

public class Commands {

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

    public static abstract class MemoryCommand extends Command{

        private String index;
        private String segment;

        public MemoryCommand(String index, String segment){
            this.index = index;
            this.segment = segment;
        }

        public String getIndex() {
            return index;
        }

        public String getSegment() {
            return segment;
        }
    }

    public static class PushCommand extends MemoryCommand{

        public PushCommand(String index, String segment) {
            super(index, segment);
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

    public static class PopCommand extends MemoryCommand{

        public PopCommand(String index, String segment) {
            super(index, segment);
        }

        @Override
        public List<String> accept(CommandVisitors visitor) {
            return visitor.visit(this);
        }
    }

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
}
