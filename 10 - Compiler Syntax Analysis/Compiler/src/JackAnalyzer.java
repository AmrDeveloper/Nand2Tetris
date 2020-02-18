import java.io.File;

public class JackAnalyzer {

    public static void main(String[] args) {
        if(args.length < 1){
            throw new RuntimeException("Invalid File or Dir Path");
        }

        String inputPath = args[0];
        File inputFile = new File(inputPath);

    }
}
