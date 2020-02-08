// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

//If Keyboard key value not equal 0, screen = black fully
//Else screen equal white
//Screen = 255 Row and 511 Column

(LOOP) 
   @SCREEN
   D=A
  
   @addr
   M=D

   @KBD
   D=M      //Get Value from keyboard

   @WHITE
   D;JEQ

   @BLACK
   0;JMP

//Make Screen white fully
(WHITE)
   @STATE
   M=0
  
   @DRAW
   0;JMP

//Make screen black fully
(BLACK)
   @STATE
   M=-1

   @DRAW
   0;JMP

//paint current state on the screen 512 Loop round 
(DRAW)
   //Loop Index
   @i
   M=0

(DRAWLOOP)
   @i
   D=M

   //256 * 32
   @8192
   D=A-D

   @LOOP
   D;JEQ

   @STATE
   D=M

   @addr
   A=M
   M=D

   @i
   M=M+1
   
   @1
   D=A

   @addr
   M=M+D
  
   @DRAWLOOP
   0;JMP