//Push Argument
@ARG
D=M
@1
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//Pop Pointer from THAT
@SP
M=M-1
@SP
D=A
@THAT
AD=D+A
@R13
M=D
@SP
A=M
D=M
@R13
A=M
M=D
//Push constant
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pop That
@THAT
D=M
@0
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//Push constant
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pop That
@THAT
D=M
@1
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//Push Argument
@ARG
D=M
@0
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//Push constant
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
//Sub Command
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
A=M
D=A-D
@SP
A=M
M=D
@SP
M=M+1
//Pop argument
@ARG
D=M
@0
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//Label MAIN_LOOP_START
(MAIN_LOOP_START)
//Push Argument
@ARG
D=M
@0
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//if Go to COMPUTE_ELEMENT
@SP
AM=M-1
D=M
@COMPUTE_ELEMENT
D;JNE
//Go to END_PROGRAM
@END_PROGRAM
0;JMP
//Label COMPUTE_ELEMENT
(COMPUTE_ELEMENT)
//Push That
@THAT
D=M
@0
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//Push That
@THAT
D=M
@1
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//Add Command
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
A=M
D=D+A
@SP
A=M
M=D
@SP
M=M+1
//Pop That
@THAT
D=M
@2
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//Push Pointer from THAT
@SP
D=A
@THAT
AD=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
//Push constant
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//Add Command
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
A=M
D=D+A
@SP
A=M
M=D
@SP
M=M+1
//Pop Pointer from THAT
@SP
M=M-1
@SP
D=A
@THAT
AD=D+A
@R13
M=D
@SP
A=M
D=M
@R13
A=M
M=D
//Push Argument
@ARG
D=M
@0
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//Push constant
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//Sub Command
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
A=M
D=A-D
@SP
A=M
M=D
@SP
M=M+1
//Pop argument
@ARG
D=M
@0
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D
//Go to MAIN_LOOP_START
@MAIN_LOOP_START
0;JMP
//Label END_PROGRAM
(END_PROGRAM)