import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CompilationEngine {

    private int index;
    private int labelIndex;
    private String classScope;
    private String subroutineScope;

    private List<Token> tokenList;
    private VMWriter vmWriter;
    private SymbolTable symbolTable;

    public CompilationEngine(List<Token> tokens, String output) {
        this.index = -1;
        this.labelIndex = 0;
        this.tokenList = tokens;
        this.vmWriter = new VMWriter(new File(output));
        this.symbolTable = new SymbolTable();
    }

    public void compileCode() {
        //Compile Class
        compileClass();
    }

    private void compileClass(){
        //Class keyword
        pointNextToken();
        //Class Name
        classScope = pointNextToken().toString();
        //{
        pointNextToken();

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
        vmWriter.closeStream();
    }

    private void compileClassVarDec(){
        //static or field
        String strKind = pointNextToken().toString().toUpperCase();
        Kind kind = Kind.valueOf(strKind);

        //Type
        String type = pointNextToken().toString();

        //Name
        String name = pointNextToken().getText();

        symbolTable.define(name, type, kind);

        Token nextToken = pointNextToken();
        while(!nextToken.getText().equals(";")){
            //Name
            name = pointNextToken().toString();
            symbolTable.define(name, type, kind);

            nextToken = pointNextToken();
        }
    }

    private void compileSubroutineDec(){
        symbolTable.startSubroutine();

        //Method, Function or Constructor
        String subroutineKeyword = pointNextToken().getText();

        if(subroutineKeyword.equals("method")){
            symbolTable.define("this", classScope, Kind.ARGUMENT);
        }

        //TODO : Return Type
        String returnType = pointNextToken().getText();

        //Function Name
        subroutineScope = pointNextToken().getText();

        //(
        pointNextToken();
        //Params List
        compileParameterList();
        
        //)
        pointNextToken();
        
        //SubroutineBody
        compileSubroutineBody(subroutineKeyword);
    }

    private void compileSubroutineCall(){
        //Subroutine caller name
        String callerName = pointNextToken().toString();


        Token nextToken = pointNextToken();
        if(nextToken.getText().equals("(")){
            vmWriter.writePush(Segment.POINTER,0);
            //(
            //parameter values
            int nArgs = compileExpressionList() + 1;
            vmWriter.writeCall(classScope + "." + callerName, nArgs);
            //)
            pointNextToken();
        }else if(nextToken.getText().equals(".")){
            //.
            //name
            String callerSubName = callerName + "." + pointNextToken().toString();
            //(
            pointNextToken();
            
            //parameter values
            int nArgs = compileExpressionList();

            vmWriter.writeCall(callerSubName, nArgs);
            //)
            pointNextToken();
        }
    }

    private void compileSubroutineBody(String srKeyword){
        //{
        pointNextToken();

        //Compile Variables
        Token nextToken = pointNextToken();
        while(nextToken.getText().equals("var")){
            pointPrevToken();
            compileVarDec();
            nextToken = pointNextToken();
        }

        writeFunctionDec(srKeyword);

        //Statements
        pointPrevToken();

        compileStatements();

        //}
        pointNextToken();
    }

    private void writeFunctionDec(String srKeyword){
        vmWriter.writeFunction(generateCurrentFuncName(), symbolTable.varCount(Kind.VARIABLE));

        if(srKeyword.equals("constructor")){
            vmWriter.writePush(Segment.CONST, symbolTable.varCount(Kind.FIELD));
            vmWriter.writeCall("Memory.alloc", 1);
            vmWriter.writePop(Segment.POINTER, 0);
        }
        else if(srKeyword.equals("method")){
            //first argument is object
            vmWriter.writePush(Segment.ARGUMENT, 0);
            vmWriter.writePop(Segment.POINTER, 0);
        }
    }

    private void compileVarDec(){
        //var
        pointNextToken();
        Kind kind = Kind.VARIABLE;
        //Type
        String type = pointNextToken().toString();
        //Name
        String name = pointNextToken().toString();

        symbolTable.define(name, type, kind);

        Token nextToken = pointNextToken();
        while(!nextToken.getText().equals(";")){
            //,
            //Name
            name = pointNextToken().toString();
            symbolTable.define(name, type, kind);
            nextToken = pointNextToken();
        }
    }

    private void compileParameterList(){
        Token token = pointNextToken();
        if(!token.getText().equals(")")){
            //Type
            String type = token.toString();

            //Name
            String name = pointNextToken().toString();

            symbolTable.define(name, type, Kind.ARGUMENT);

            token = pointNextToken();
            while(token.getText().equals(",")){
                //,

                //Type
                type = pointNextToken().toString();

                //Name
                name = pointNextToken().toString();

                symbolTable.define(name, type, Kind.ARGUMENT);

                token = pointNextToken();
            }
        }
        pointPrevToken();
    }

    private void compileStatements(){
        Token nextToken = pointNextToken();
        while (TokenConst.isStatement(nextToken)){
            pointPrevToken();
            compileStatement();
            nextToken = pointNextToken();
        }
        pointPrevToken();
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
        //Let
        pointNextToken();
        //var Name
        String varName = pointNextToken().toString();

        boolean isExpressionExit = false;

        Token nextToken = pointNextToken();
        if(nextToken.getText().equals("[")){
            isExpressionExit = true;
            //[
            vmWriter.writePush(getKindSegment(symbolTable.kindOf(varName)), symbolTable.indexOf(varName));

            //Array index [exp]
            compileExpression();

            //]
            pointNextToken();

            pointNextToken();
            vmWriter.writeArithmetic(Command.ADD);
        }
        //value after equal
        compileExpression();

        //;
        pointNextToken();

        if(isExpressionExit){
            vmWriter.writePop(Segment.TEMP,0);
            vmWriter.writePop(Segment.POINTER,1);
            vmWriter.writePush(Segment.TEMP,0);
            vmWriter.writePop(Segment.THAT,0);
        }else{
            vmWriter.writePop(getKindSegment(symbolTable.kindOf(varName)), symbolTable.indexOf(varName));
        }
    }

    private void compileIfStatement(){
        String elseLabel = generateLabel();
        String endLabel = generateLabel();

        //if
        pointNextToken();
        //(
        pointNextToken();

        compileExpression();

        vmWriter.writeArithmetic(Command.NOT);
        vmWriter.writeIf(elseLabel);

        //)
        pointNextToken();
        //{
        pointNextToken();

        compileStatements();

        //}
        pointNextToken();

        vmWriter.writeGoto(endLabel);
        vmWriter.writeLabel(elseLabel);

        Token nextToken = pointNextToken();
        if(nextToken.getText().equals("else")){
            //else
            //{
            pointNextToken();

            compileStatements();

            //};
            pointNextToken();
        }else{
            pointPrevToken();
        }
        vmWriter.writeLabel(endLabel);
    }

    private void compileDoStatement(){
        //do
        pointNextToken();
        //subroutine call
        compileSubroutineCall();
        //;
        pointNextToken();

        vmWriter.writePop(Segment.TEMP,0);
    }

    private void compileWhileStatement(){
        String continueLabel = generateLabel();
        String topLabel = generateLabel();

        vmWriter.writeLabel(topLabel);

        //While
        pointNextToken();
        //(
        pointNextToken();

        compileExpression();

        //)
        pointNextToken();

        vmWriter.writeArithmetic(Command.NOT);
        vmWriter.writeIf(continueLabel);

        //{
        pointNextToken();

        compileStatements();

        //}
        pointNextToken();

        vmWriter.writeGoto(topLabel);
        vmWriter.writeLabel(continueLabel);
    }

    private void compileReturnStatement(){
        //return
        pointNextToken();

        //return value or void
        Token nextToken = pointNextToken();
        if(!nextToken.getText().equals(";")){
            pointPrevToken();
            compileExpression();
            nextToken = pointNextToken();
        }else{
            vmWriter.writePush(Segment.CONST, 0);
        }
        vmWriter.writeReturn();
    }

    private void compileTerm(){
        Token nextToken = pointNextToken();
        if(TokenConst.isKeywordConst(nextToken.getText())){
            String keyword = nextToken.getText();
            switch (keyword){
                case "true":
                    //0 with not will be -1 -> true in 16bit format
                    vmWriter.writePush(Segment.CONST, 0);
                    vmWriter.writeArithmetic(Command.NOT);
                    break;
                case "false":vmWriter.writePush(Segment.CONST, 0); break;
                case "this":vmWriter.writePush(Segment.POINTER, 0); break;
            }
        }
        else if(nextToken.getType() == TokenType.integerConstant){
            vmWriter.writePush(Segment.CONST, Integer.parseInt(nextToken.getText()));
        }
        else if(nextToken.getType() == TokenType.stringConstant){
            String string = nextToken.toString();
            int length = string.length();

            //alloc space for current string
            vmWriter.writePush(Segment.CONST, length);
            vmWriter.writeCall("String.new", 1);

            for(char c : string.toCharArray()){
                vmWriter.writePush(Segment.CONST, c);
                vmWriter.writeCall("String.appendChar",2);
            }
        }
        else if(TokenConst.isUnaryOperator(nextToken)){
            String operator = nextToken.toString();
            compileTerm();
            if(operator.equals("-")){
                vmWriter.writeArithmetic(Command.NEG);
            }else{
                vmWriter.writeArithmetic(Command.NOT);
            }
        }
        else if(nextToken.getText().equals("(")){
            //(
            compileExpression();
            //)
            pointNextToken();
        }
        else{
            nextToken = pointNextToken();
            if(nextToken.getText().equals("[")){
                //Var name
                String varName = pointPrevToken().toString();
                vmWriter.writePush(getKindSegment(symbolTable.kindOf(varName)), symbolTable.indexOf(varName));
                //[
                pointNextToken();
                compileExpression();
                //]
                pointNextToken();

                vmWriter.writeArithmetic(Command.ADD);
                vmWriter.writePop(Segment.POINTER, 1);
                vmWriter.writePush(Segment.THAT, 0);
            }else if(nextToken.getText().equals("(") || nextToken.getText().equals(".")){
                pointPrevToken();
                pointPrevToken();
                compileSubroutineCall();
            }else{
                //Var name
                pointPrevToken();
                String varName = getCurrentToken().toString();
                vmWriter.writePush(getKindSegment(symbolTable.kindOf(varName)), symbolTable.indexOf(varName));
            }
        }
    }

    private void compileExpression(){
        //term (op term);
        compileTerm();

        do{
            Token nextToken = pointNextToken();
            if(TokenConst.isOperator(nextToken)){
                String operator = nextToken.toString();
                String operatorText = getOperatorText(operator);
                
                compileTerm();

                vmWriter.writeInstruction(operatorText);
            }else{
                pointPrevToken();
                break;
            }
        }while (true);
    }

    private int compileExpressionList(){
        int expressionCount = 1;

        Token nextToken = pointNextToken();
        //Have at last one expression
        if(!nextToken.getText().equals(")")){
            pointPrevToken();
            compileExpression();
            nextToken = pointNextToken();
            while (nextToken.getText().equals(",")){
                //,
                compileExpression();
                nextToken = pointNextToken();
                expressionCount = expressionCount + 1;
            }
            pointPrevToken();
        }else{
            pointPrevToken();
        }
        return expressionCount;
    }

    private Token getCurrentToken(){
        return tokenList.get(index);
    }

    private Token getNextToken(){
        return tokenList.get(index + 1);
    }

    private Token getPrevToken(){
        return tokenList.get(index - 1);
    }

    private Token pointNextToken(){
        index = index + 1;
        return tokenList.get(index);
    }

    private Token pointPrevToken(){
        index = index - 1;
        return tokenList.get(index);
    }

    private String generateLabel(){
        return "label_" + labelIndex++;
    }

    private String generateCurrentFuncName(){
        if(!classScope.isEmpty() && !subroutineScope.isEmpty()){
            return classScope + "." + subroutineScope;
        }
        return "";
    }

    private Segment getKindSegment(Kind kind){

        switch (kind){
            case FIELD:return Segment.THIS;
            case STATIC:return Segment.STATIC;
            case VARIABLE:return Segment.LOCAL;
            case ARGUMENT:return Segment.ARGUMENT;
            default:return Segment.NONE;
        }

    }

    private String getOperatorText(String operator){
        switch (operator){
            case "+":return "add";
            case "-":return "sub";
            case "*":return "call Math.multiply 2";
            case "/":return "call Math.divide 2";
            case "<":return "lt";
            case ">":return "gt";
            case "=":return "eq";
            case "&":return "and";
            case "|":return "or";
            default: return null;
        }
    }
}
