//Push constant
@111
D=A
@SP
A=M
M=D
@SP
M=M+1
//Push constant
@333
D=A
@SP
A=M
M=D
@SP
M=M+1
//Push constant
@888
D=A
@SP
A=M
M=D
@SP
M=M+1
//Pop Static
@SP
M=M-1
@SP
A=M
D=M
@Learn.8
M=D
//Pop Static
@SP
M=M-1
@SP
A=M
D=M
@Learn.3
M=D
//Pop Static
@SP
M=M-1
@SP
A=M
D=M
@Learn.1
M=D
//Push Static
@Learn.3
D=M
@SP
A=M
M=D
@SP
M=M+1
//Push Static
@Learn.1
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
//Push Static
@Learn.8
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