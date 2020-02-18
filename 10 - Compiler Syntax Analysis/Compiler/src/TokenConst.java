import java.util.Arrays;
import java.util.List;

public class TokenConst {

    public static final List<String> keywords = Arrays.asList(
            "class", "constructor", "function", "method", "field", "static",
            "var", "int", "char", "boolean", "void", "true", "false",
            "null", "this", "let", "do", "if", "else", "while", "return"
    );

    public static final List<Character> symbols = Arrays.asList(
            '{', '}', '(', ')', '{', '}', '.', ',', '+', '-',
            '*', '/', '&', '|', '<', '>', '=', '~', ';'
    );

    public static boolean isKeywordConst(String keyword){
        return keywords.contains(keyword);
    }

    public static boolean isSymbolConst(char keyword){
        return symbols.contains(keyword);
    }
}
