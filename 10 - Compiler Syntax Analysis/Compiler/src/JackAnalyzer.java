import java.io.File;
import java.util.List;
import java.util.Objects;

public class JackAnalyzer {

    public static void main(String[] args) {
        if(args.length < 1){
            throw new RuntimeException("Invalid File or Dir Path");
        }

        String inputPath = args[0];
        File inputFile = new File(inputPath);

        compileFiles(inputFile);
    }

    public static void compileFiles(File inputFile){
        if(inputFile.isDirectory()){
            File[] files = inputFile.listFiles();
            for(File file : Objects.requireNonNull(files)){
                if(file.getPath().endsWith(".jack")){
                    compileJackCode(file);
                }
            }
        }else{
            compileJackCode(inputFile);
        }
    }

    public static void compileJackCode(File input){
        String output = input.getParent().replaceAll("jack", "xml");
        JackTokenizer tokenizer = new JackTokenizer(input);
        List<Token> tokens = tokenizer.tokenize();
        CompilationEngine engine = new CompilationEngine(tokens);
        engine.compile(output);
    }
}
