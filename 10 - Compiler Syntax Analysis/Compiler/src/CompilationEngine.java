import java.util.ArrayList;
import java.util.List;

public class CompilationEngine {

    private int currentIndex;
    private List<Token> tokenList;
    private List<String> compiledCode;

    public CompilationEngine(List<Token> tokenList) {
        this.currentIndex = 0;
        this.tokenList = tokenList;
        this.compiledCode = new ArrayList<>();
    }

    public void compile(String output){

    }
}
