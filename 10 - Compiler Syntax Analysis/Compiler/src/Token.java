public class Token {

    private String text;
    private TokenType type;

    public Token(String text, TokenType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        if(type == TokenType.SYMBOL){
            text = text.replaceAll("&", "&amp;").
                    replaceAll("<", "&lt;").
                    replaceAll(">", "&gt;");
        }
        return "<"+ type.name() +">" + text + "</"+ type.name() +">";
    }
}
