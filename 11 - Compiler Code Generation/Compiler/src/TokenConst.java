import java.util.Arrays;
import java.util.List;

public class TokenConst {

    public static final List<String> keywords = Arrays.asList(
            "class", "constructor", "function", "method", "field", "static",
            "var", "int", "char", "boolean", "void", "true", "false",
            "null", "this", "let", "do", "if", "else", "while", "return"
    );

    public static final List<Character> symbols = Arrays.asList(
            '[', ']', '(', ')', '{', '}', '.', ',', '+', '-',
            '*', '/', '&', '|', '<', '>', '=', '~', ';'
    );

    public static boolean isKeywordConst(String keyword) {
        return keyword.equals("true") ||
                keyword.equals("false") ||
                keyword.equals("null") ||
                keyword.equals("this");
    }

    public static boolean isSymbolConst(char keyword) {
        return symbols.contains(keyword);
    }

    public static boolean isUnaryOperator(Token token) {
        String strToken = token.getText();
        return strToken.equals("-") || strToken.equals("~");
    }

    public static boolean isStatement(Token token) {
        String strToken = token.getText();
        return strToken.equals("if") ||
                strToken.equals("else") ||
                strToken.equals("while") ||
                strToken.equals("do") ||
                strToken.equals("let") ||
                strToken.equals("return");
    }

    public static boolean isClassSubroutine(Token token) {
        String strToken = token.getText();
        return strToken.equals("constructor") ||
                strToken.equals("function") ||
                strToken.equals("method");
    }

    public static boolean isClassField(Token token) {
        String strToken = token.getText();
        return strToken.equals("static") ||
                strToken.equals("field");
    }

    public static boolean isOperator(Token token) {
        String strToken = token.getText();
        return strToken.equals("+") ||
                strToken.equals("-") ||
                strToken.equals("*") ||
                strToken.equals("/") ||
                strToken.equals(">") ||
                strToken.equals("<") ||
                strToken.equals("=") ||
                strToken.equals("&") ||
                strToken.equals("|");
    }

}
