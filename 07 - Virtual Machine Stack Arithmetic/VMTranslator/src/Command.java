import java.util.List;

public abstract class Command{
    public abstract List<String> accept(CommandVisitors visitor);
}