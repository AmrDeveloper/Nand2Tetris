import java.io.File;
import java.util.List;
import java.util.Objects;

public class JackCompiler {

    public static void main(String[] args)  {
        String inputPath;
        if(args.length < 1){
            inputPath = "D:\\Nand2Tetris\\Week\\ComplexArrays";
        }else{
            inputPath = args[0];
        }

        File inputFile = new File(inputPath);

        compileFiles(inputFile);
    }

    public static void compileFiles(File inputFile) {
        if(inputFile.isDirectory()){
            File[] files = inputFile.listFiles();
            for(File file : Objects.requireNonNull(files)){
                if(file.getPath().endsWith(".jack")){
                    compileJackFile(file);
                }
            }
        }else{
            if(inputFile.getPath().endsWith(".jack")){
                compileJackFile(inputFile);
            }
            else{
                throw new IllegalArgumentException("No jack file in this directory");
            }
        }
    }

    public static void compileJackFile(File input) {
        String output = input.getPath().replace(".jack", ".vm");
        JackTokenizer tokenizer = new JackTokenizer(input);
        List<Token> tokens = tokenizer.tokenize();
        CompilationEngine engine = new CompilationEngine(tokens, output);
        engine.compileCode();
    }
}
