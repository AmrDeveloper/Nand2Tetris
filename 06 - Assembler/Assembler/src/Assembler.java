public class Assembler {
    public static void main(String[] args) {
        AssemblyParser parser = new AssemblyParser();
        AssemblyCode assemblyCode = parser.runAssemblyParser("D:\\Software\\SoftwareEngineer\\src\\Add.asm");
        InstructionAssembler assembler = new InstructionAssembler(assemblyCode);
        String machineCode = assembler.translate();
        System.out.println(machineCode);
    }
}
