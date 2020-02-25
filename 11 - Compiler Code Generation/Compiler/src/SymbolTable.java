import java.util.HashMap;

public class SymbolTable {

    private static HashMap<String, Symbol> classLevelTable;
    private static HashMap<String, Symbol> subroutineLevelTable;
    private static HashMap<Kind, Integer> kindsIndex;

    public SymbolTable() {
        classLevelTable = new HashMap<>();
        subroutineLevelTable = new HashMap<>();

        kindsIndex = new HashMap<>();
        kindsIndex.put(Kind.STATIC, 0);
        kindsIndex.put(Kind.FIELD,0);
        kindsIndex.put(Kind.VARIABLE, 0);
        kindsIndex.put(Kind.ARGUMENT, 0);
    }

    //Reset the subroutine's symbol table
    public void startSubroutine(){
        subroutineLevelTable.clear();
        kindsIndex.put(Kind.VARIABLE, 0);
        kindsIndex.put(Kind.ARGUMENT, 0);
    }

    public void define(String name, String type, Kind kind){
        //Class Scope
        if(kind == Kind.STATIC || kind == Kind.FIELD){
            int kindIndex = kindsIndex.get(kind);
            Symbol symbol = new Symbol(type, kind, kindIndex);
            kindsIndex.put(kind, kindIndex + 1);
            classLevelTable.put(name, symbol);
        }
        //Subroutine scope
        else{
            int kindIndex = kindsIndex.get(kind);
            Symbol symbol = new Symbol(type, kind, kindIndex);
            kindsIndex.put(kind, kindIndex + 1);
            subroutineLevelTable.put(name, symbol);
        }
    }

    public int varCount(Kind kind){
        return kindsIndex.get(kind);
    }

    //Take name and return Kine
    public Kind kindOf(String name){
        Symbol symbol = subroutineLevelTable.get(name);
        if(symbol == null){
           symbol = classLevelTable.get(name);
           if(symbol == null){
               return Kind.NONE;
           }
        }
        return symbol.getKind();
    }

    //take name and return kind
    public String typeOf(String name){
        Symbol symbol = subroutineLevelTable.get(name);
        if(symbol == null){
            symbol = classLevelTable.get(name);
        }
        return symbol.getType();
    }

    //take name and return index assigned to the name identifier
    public int indexOf(String name){
        Symbol symbol = subroutineLevelTable.get(name);
        if(symbol == null){
            symbol = classLevelTable.get(name);
        }
        return symbol.getIndex();
    }
}
