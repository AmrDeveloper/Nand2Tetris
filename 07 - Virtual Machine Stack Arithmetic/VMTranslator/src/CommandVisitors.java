import java.util.List;

public interface CommandVisitors{
    //Arithmetic Commands
    List<String> visit(Commands.AddCommand command);
    List<String> visit(Commands.SubCommand command);
    List<String> visit(Commands.NegCommand command);
    List<String> visit(Commands.EqualCommand command);
    List<String> visit(Commands.GreaterThanCommand command);
    List<String> visit(Commands.LessThanCommand command);
    List<String> visit(Commands.AndCommand command);
    List<String> visit(Commands.OrCommand command);
    List<String> visit(Commands.NotCommand command);

    //Memory Access Commands
    List<String> visit(Commands.PushCommand command);
    List<String> visit(Commands.PopCommand command);
}