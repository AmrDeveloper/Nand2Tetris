import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JackTokenizer {

    private String source;
    private int startIndex;
    private int currentIndex;

    public JackTokenizer(File input) {
        this.currentIndex = 0;
        this.startIndex = 0;
        this.source = readJackFile(input.getPath());
        this.source = source.replaceAll("/\\*.*\\*/", " ")
                .replaceAll("(?s)/\\*.*\\*/", " ");
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < source.length(); i++) {
            currentIndex = i;
            startIndex = currentIndex;
            char c = source.charAt(i);
            if (c == ' ' || c == '\t' || c == '\n') {
                continue;
            }
            //Handle Symbols
            else if (TokenConst.symbols.contains(c)) {
                tokens.add(new Token(String.valueOf(c), TokenType.symbol));
            }
            //Handle Integer Const
            else if (isDigit(c)) {
                String intValue = handleIntegerConst();
                tokens.add(new Token(intValue, TokenType.integerConstant));
                i = i + intValue.length() - 1;
            }
            //Handle String
            else if (isDoubleQuotes(c)) {
                StringBuilder strValue = new StringBuilder();
                char newC = source.charAt(i + 1);
                i = i + 1;
                while (newC != '"'){
                    strValue.append(newC);
                    i = i + 1;
                    newC = source.charAt(i);
                }
                tokens.add(new Token(strValue.toString(), TokenType.stringConstant));
            }
            //Handle IDENTIFIER or Keyword
            else if (isAlpha(c)) {
                String alphaValue = handleAlpha();
                if (TokenConst.keywords.contains(alphaValue)) {
                    tokens.add(new Token(alphaValue, TokenType.keyword));
                } else {
                    tokens.add(new Token(alphaValue, TokenType.identifier));
                }
                i = i + alphaValue.length() - 1;
            } else {
                throw new RuntimeException("Invalid Token : " + c);
            }
        }
        return tokens;
    }

    private String readJackFile(String assemblyFile) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(assemblyFile);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("//") || line.startsWith("/**") || line.startsWith("*")  || line.isEmpty()) {
                    continue;
                } else if (line.contains("//")) {
                    String[] args = line.split("/");
                    line = args[0].trim();
                }
                builder.append(line);
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String handleIntegerConst() {
        while (isDigit(getCurrentChar())) pointToNextChar();
        if (getCurrentChar() == '.' && isDigit(getNextChar())) {
            // Consume the "."
            pointToNextChar();
            while (isDigit(getCurrentChar())) pointToNextChar();
        }
        return source.substring(startIndex, currentIndex);
    }

    private String handleStringConst() {
        while (getNextChar() != '"' && hasMoreTokens()) {
            pointToNextChar();
        }

        // Trim the surrounding quotes.
        return source.substring(startIndex + 1, currentIndex - 1);
    }

    private String handleAlpha() {
        while (isAlphaNumeric(getCurrentChar())) pointToNextChar();
        return source.substring(startIndex, currentIndex);
    }

    public boolean hasMoreTokens() {
        return source.length() > currentIndex;
    }

    public char getCurrentChar() {
        return source.charAt(currentIndex);
    }

    private char getNextChar() {
        if (currentIndex + 1 >= source.length()) return '\0';
        return source.charAt(currentIndex + 1);
    }

    private char pointToNextChar() {
        currentIndex++;
        return source.charAt(currentIndex - 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isDoubleQuotes(char c) {
        return c == '"';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}
