//Function command for SimpleFunction.test
(SimpleFunction.test)
@SP
A=M
M=0
@SP
M=M+1
@SP
A=M
M=0
@SP
M=M+1
//Push Local
@LCL
D=M
@0
A=A+D
D=M
@SP
A=M
M=D
@SP
M=M+1
//Push Local
@LCL
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
//Not Command
@SP
A=M-1
M=!M
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
//Return Command
@LCL
D=M
@endFrame
M=D
@endFrame
D=M
@5
A=D-A
D=M
@ret_addr
M=D
@SP
A=M-1
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@endFrame
A=M-1
D=M
@THAT
M=D
@endFrame
D=M
@2
A=D-A
D=M
@THIS
M=D
@endFrame
D=M
@3
A=D-A
D=M
@ARG
M=D
@endFrame
D=M
@4
A=D-A
D=M
@LCL
M=D
@ret_addr
A=M
0;JMP