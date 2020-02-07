// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

@R2
M=0  //Result = 0

@R0  //A
D=M

//i = A
@i
M=D

(LOOP)
   @i
   D=M

   @END
   D;JEQ  //End Loop if i = 0

   @R1
   D=M    //B
 
   @R2    //result = A + B
   M=M+D
  
   @i
   M=M-1  //i--

   @LOOP
   0;JMP

(END)
   @END
   0;JMP