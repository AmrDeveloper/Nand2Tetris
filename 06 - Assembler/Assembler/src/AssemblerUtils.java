public class AssemblerUtils {

    public static String integerTo16BitBinary(int value){
        return String.format("%16s", Integer.toBinaryString(value))
                .replace(' ', '0');
    }
}
