import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CompilationEngine {
    private int index;
    private List<Token> tokenList;
    private List<String> outputCode;

    public CompilationEngine(List<Token> tokens) {
        this.index = 0;
        this.tokenList = tokens;
        this.outputCode = new ArrayList<>();
    }

    public void compileToFile(String output) throws IOException {
        compileCode();
        StringBuilder builder = new StringBuilder();
        for (String str : outputCode) {
            builder.append(str).append("\n");

        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(output), StandardCharsets.UTF_8))) {
            writer.write(builder.toString());
        }
        System.out.println("Translate is Done");
    }

    public void compileCode() {
        //Compile Class
        compileClass();
    }

    private void compileClass(){
        outputCode.add("<class>");
        //Class
        outputCode.add(getCurrentToken().toString());
        //Class Name
        outputCode.add(pointNextToken().toString());
        //{
        outputCode.add(pointNextToken().toString());

        //Parse Field and Subroutines
        Token nextToken = pointNextToken();
        while (TokenConst.isClassField(nextToken) || TokenConst.isClassSubroutine(nextToken)){
            if(TokenConst.isClassField(nextToken)){
                pointPrevToken();
                compileClassVarDec();
            }else if(TokenConst.isClassSubroutine(nextToken)){
                pointPrevToken();
                compileSubroutineDec();
            }
            nextToken = pointNextToken();
        }
        //}
        outputCode.add(nextToken.toString());
        outputCode.add("</class>");
    }

    private void compileClassVarDec(){
        outputCode.add("<classVarDec>");
        //static or field
        outputCode.add(pointNextToken().toString());
        //Type
        outputCode.add(pointNextToken().toString());
        //Name
        outputCode.add(pointNextToken().toString());

        Token nextToken = pointNextToken();
        while(!nextToken.getText().equals(";")){
            //,
            outputCode.add(nextToken.toString());
            //Name
            outputCode.add(pointNextToken().toString());
            nextToken = pointNextToken();
        }

        //;
        outputCode.add(nextToken.toString());
        outputCode.add("</classVarDec>");
    }

    private void compileSubroutineDec(){
        outputCode.add("<subroutineDec>");
        //Method, Function or Constructor
        outputCode.add(pointNextToken().toString());
        //Return type
        outputCode.add(pointNextToken().toString());
        //Function Name
        outputCode.add(pointNextToken().toString());
        //(
        outputCode.add(pointNextToken().toString());
        //Params List
        compileParameterList();
        //)
        outputCode.add(pointNextToken().toString());
        //SubroutineBody
        compileSubroutineBody();
        outputCode.add("</subroutineDec>");
    }

    private void compileSubroutineCall(){
        //Subroutine caller name
        outputCode.add(pointNextToken().toString());

        Token nextToken = pointNextToken();
        if(nextToken.getText().equals("(")){
            //(
            outputCode.add(nextToken.toString());
            //parameter values
            compileExpressionList();
            //)
            outputCode.add(pointNextToken().toString());
        }else if(nextToken.getText().equals(".")){
            //.
            outputCode.add(nextToken.toString());
            //name
            outputCode.add(pointNextToken().toString());
            //(
            outputCode.add(pointNextToken().toString());
            //parameter values
            compileExpressionList();
            //)
            outputCode.add(pointNextToken().toString());
        }
    }

    private void compileSubroutineBody(){
        outputCode.add("<subroutineBody>");
        //{
        outputCode.add(pointNextToken().toString());

        //Compile Variables
        Token nextToken = pointNextToken();
        while(nextToken.getText().equals("var")){
            pointPrevToken();
            compileVarDec();
            nextToken = pointNextToken();
        }

        //Statements
        pointPrevToken();
        compileStatements();

        //}
        outputCode.add(pointNextToken().toString());
        outputCode.add("</subroutineBody>");
    }

    private void compileVarDec(){
        outputCode.add("<varDec>");
        //var
        outputCode.add(pointNextToken().toString());
        //Type
        outputCode.add(pointNextToken().toString());
        //Name
        outputCode.add(pointNextToken().toString());

        Token nextToken = pointNextToken();
        while(!nextToken.getText().equals(";")){
            //,
            outputCode.add(nextToken.toString());
            //Name
            outputCode.add(pointNextToken().toString());
            nextToken = pointNextToken();
        }

        //;
        outputCode.add(nextToken.toString());
        outputCode.add("</varDec>");
    }

    private void compileParameterList(){
        outputCode.add("<parameterList>");

        Token token = pointNextToken();
        if(!token.getText().equals(")")){
            //Type
            outputCode.add(token.toString());

            //Name
            outputCode.add(pointNextToken().toString());

            token = pointNextToken();
            while(token.getText().equals(",")){
                //,
                outputCode.add(token.toString());

                //Type
                outputCode.add(pointNextToken().toString());

                //Name
                outputCode.add(pointNextToken().toString());

                token = pointNextToken();
            }
        }
        pointPrevToken();
        outputCode.add("</parameterList>");
    }

    private void compileStatements(){
        outputCode.add("<statements>");
        Token nextToken = pointNextToken();
        while (TokenConst.isStatement(nextToken)){
            pointPrevToken();
            compileStatement();
            nextToken = pointNextToken();
        }
        pointPrevToken();
        outputCode.add("</statements>");
    }

    private void compileStatement(){
        Token nextToken = pointNextToken();
        String tokenStr = nextToken.getText();
        switch (tokenStr){
            case "if":
                pointPrevToken();
                compileIfStatement();
                break;
            case "let":
                pointPrevToken();
                compileLetStatement();
                break;
            case "do":
                pointPrevToken();
                compileDoStatement();
                break;
            case "while":
                pointPrevToken();
                compileWhileStatement();
                break;
            case "return":
                pointPrevToken();
                compileReturnStatement();
                break;
        }
    }

    private void compileLetStatement(){
        outputCode.add("<letStatement>");
        //Let
        outputCode.add(pointNextToken().toString());
        //var Name
        outputCode.add(pointNextToken().toString());

        Token nextToken = pointNextToken();
        if(nextToken.getText().equals("[")){
            //[
            outputCode.add(nextToken.toString());

            //Array index [exp]
            compileExpression();

            //]
            outputCode.add(pointNextToken().toString());

            nextToken = pointNextToken();
        }

        //=
        outputCode.add(nextToken.toString());

        //value after equal
        compileExpression();

        //;
        outputCode.add(pointNextToken().toString());
        outputCode.add("</letStatement>");
    }

    private void compileIfStatement(){
        outputCode.add("<ifStatement>");
        //if
        outputCode.add(pointNextToken().toString());
        //(
        outputCode.add(pointNextToken().toString());

        compileExpression();

        //)
        outputCode.add(pointNextToken().toString());
        //{
        outputCode.add(pointNextToken().toString());

        compileStatements();

        //}
        outputCode.add(pointNextToken().toString());

        Token nextToken = pointNextToken();
        if(nextToken.getText().equals("else")){
            //else
            outputCode.add(nextToken.toString());

            //{
            outputCode.add(pointNextToken().toString());

            compileStatements();

            //}
            outputCode.add(pointNextToken().toString());
        }else{
            pointPrevToken();
        }
        outputCode.add("</ifStatement>");
    }

    private void compileDoStatement(){
        outputCode.add("<doStatement>");
        //do
        outputCode.add(pointNextToken().toString());
        //subroutine call
        compileSubroutineCall();
        //;
        outputCode.add(pointNextToken().toString());
        outputCode.add("</doStatement>");
    }

    private void compileWhileStatement(){
        outputCode.add("<whileStatement>");
        //While
        outputCode.add(pointNextToken().toString());
        //(
        outputCode.add(pointNextToken().toString());

        compileExpression();

        //)
        outputCode.add(pointNextToken().toString());

        //{
        outputCode.add(pointNextToken().toString());

        compileStatements();

        //}
        outputCode.add(pointNextToken().toString());
        outputCode.add("</whileStatement>");
    }

    private void compileReturnStatement(){
        outputCode.add("<returnStatement>");
        //return
        outputCode.add(pointNextToken().toString());

        //return value or void
        Token nextToken = pointNextToken();
        if(!nextToken.getText().equals(";")){
            pointPrevToken();
            compileExpression();
            nextToken = pointNextToken();
        }
        //;
        outputCode.add(nextToken.toString());
        outputCode.add("</returnStatement>");
    }

    private void compileTerm(){
        outputCode.add("<term>");
        Token nextToken = pointNextToken();
        if(TokenConst.isKeywordConst(nextToken.getText())){
            outputCode.add(nextToken.toString());
        }else if(nextToken.getType() == TokenType.integerConstant){
            outputCode.add(nextToken.toString());
        }
        else if(nextToken.getType() == TokenType.stringConstant){
            outputCode.add(nextToken.toString());
        }else if(TokenConst.isUnaryOperator(nextToken)){
            outputCode.add(nextToken.toString());
            compileTerm();
        }
        else if(nextToken.getText().equals("(")){
            //(
            outputCode.add(nextToken.toString());
            compileExpression();
            //)
            outputCode.add(pointNextToken().toString());
        }else{
            nextToken = pointNextToken();
            if(nextToken.getText().equals("[")){
                //Var name
                outputCode.add(pointPrevToken().toString());
                //[
                outputCode.add(pointNextToken().toString());
                compileExpression();
                //]
                outputCode.add(pointNextToken().toString());
            }else if(nextToken.getText().equals("(") || nextToken.getText().equals(".")){
                pointPrevToken();
                pointPrevToken();
                compileSubroutineCall();
            }else{
                //Var name
                outputCode.add(pointPrevToken().toString());
            }
        }
        outputCode.add("</term>");
    }

    private void compileExpression(){
        outputCode.add("<expression>");
        //term (op term);
        compileTerm();

        Token nextToken = pointNextToken();
        while (TokenConst.isOperator(nextToken)){
            //Operator
            outputCode.add(nextToken.toString());
            //Term
            compileTerm();
            nextToken = pointNextToken();
        }
        pointPrevToken();
        outputCode.add("</expression>");
    }

    private void compileExpressionList(){
        outputCode.add("<expressionList>");

        Token nextToken = pointNextToken();
        //Have at last one expression
        if(!nextToken.getText().equals(")")){
            pointPrevToken();
            compileExpression();
            nextToken = pointNextToken();
            while (nextToken.getText().equals(",")){
                //,
                outputCode.add(nextToken.toString());
                compileExpression();
                nextToken = pointNextToken();
            }
            pointPrevToken();
        }else{
            pointPrevToken();
        }
        outputCode.add("</expressionList>");
    }

    private Token getCurrentToken(){
        return tokenList.get(index);
    }

    private Token pointNextToken(){
        index = index + 1;
        return tokenList.get(index);
    }

    private Token pointPrevToken(){
        index = index - 1;
        return tokenList.get(index);
    }
}
