
// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

@R0
D=M
@ZERO
D;JEQ

@R1
D=M
@n
M=D
@ZERO
D;JEQ

@i
M=1

@R2
M=0

(LOOP)
@i
D=M
@n
D=D-M
@END
D;JGT

@R2
D=M
@R0
D=D+M
@R2
M=D

@i
M=M+1
@LOOP
0;JMP

(ZERO)
@R2
M=0

(END)
@END
0;JMP
