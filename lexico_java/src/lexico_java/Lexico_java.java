/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico_java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class Lexico_java {
    
    /* Character classes */
    static int LETTER = 0;
    static int DIGIT = 1;
    static int POINT = 2;
    static int UNKNOWN = 99;

    /* Token codes */
    static int INT_LIT = 10;
    static int FLO_LIT = 13;
    static int IDENT = 11;
    static int ASSIGN_OP = 20;
    static int ADD_OP = 21;
    static int SUB_OP = 22;
    static int MULT_OP = 23;
    static int DIV_OP = 24;
    static int LEFT_PAREN = 25;
    static int RIGHT_PAREN = 26;
    
    int charClass;
    char[] lexeme = new char[100];
    char nextChar;
    int lexLen;
    int token;
    static int nextToken;
    
    boolean flag_float = false;
    boolean flag_unknown = false;
    
    static Leer l = new Leer();
    
    static BufferedReader br;
    
    /*****************************************************/
    /* addChar - a function to add nextChar to lexeme */
    public void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
          }
        else    System.out.println("Error - lexeme is too long \n");
    }

    /**
     * @throws IOException ***************************************************/
    /* getChar - a function to get the next character of
                 input and determine its character class */
    public void getChar() throws IOException{
        if ((nextChar = (char)br.read()) != -1) {
            if (Character.isLetter(nextChar))
                charClass = LETTER;
            else if (Character.isDigit(nextChar))
                charClass = DIGIT;
            else if (nextChar == '.')
                charClass = POINT;
            else charClass = UNKNOWN;
        }
        else    charClass = -1;
    }

    /**
     * @throws IOException ***************************************************/
    /* getNonBlank - a function to call getChar until it
                     returns a non-whitespace character */
    public void getNonBlank() throws IOException {
    while (nextChar == ' ')
        getChar();
    }

    public int lex() throws IOException {
	  lexLen = 0;
	  getNonBlank();
	  if(charClass == LETTER)
	  {
		  addChar();
		  getChar();
		  while (charClass == LETTER || charClass == DIGIT) {
                	addChar();
                	getChar();
              	}
		  nextToken = IDENT;  
	  }
	  else if(charClass == DIGIT)
	  {
	  	addChar();
        getChar();
        while (charClass == DIGIT) {
                addChar();
                getChar();
            }
        if(charClass == POINT)
        {
            addChar();
            getChar();
            flag_float = true;
            while (charClass == DIGIT) {
                addChar();
                getChar();
            }
        }

        if(charClass == LETTER)
        {
            addChar();
            getChar();
            flag_unknown = true;
            while (!(nextChar == ' ')) {
                addChar();
                getChar();
            }
        }

        if(flag_float && !flag_unknown){
            nextToken = FLO_LIT;
            flag_float = false;
        }
        else if(flag_unknown){
            nextToken = UNKNOWN;
            flag_unknown = false;
        }
        else           nextToken = INT_LIT; 
	  }
	  else if(charClass == POINT)
	  {
	  	addChar();
        getChar();
        while(charClass == DIGIT){
            addChar();
            getChar();
        }

        if(charClass == LETTER)
        {
            addChar();
            getChar();
            flag_unknown = true;
            while (!(nextChar == ' ')) {
                addChar();
                getChar();
            }
        }

        if(flag_unknown){
            nextToken = UNKNOWN;
            flag_float = false;
        }
        else nextToken = FLO_LIT;
	  }
	  else if(charClass == UNKNOWN)
	  {
	  	lookup(nextChar);
        getChar(); 
	  }
	  else if(charClass == -1)
	  {
	  	nextToken = -1;
        lexeme[0] = 'E';
        lexeme[1] = 'O';
        lexeme[2] = 'F';
        lexeme[3] = 0;
	  }

	  System.out.printf("Next token is: %d, Next lexeme is %s\n",
	          nextToken, lexeme);
	return nextToken;
	}  /* End of function lex */
    
    public int lookup(char ch) {
    switch (ch) {
    case '(':
          addChar();
          nextToken = LEFT_PAREN;
          break;
    case ')':
          addChar();
          nextToken = RIGHT_PAREN;
          break;
    case '+':
          addChar();
          nextToken = ADD_OP;
          break;
    case '-':
          addChar();
          nextToken = SUB_OP;
          break;
    case '*':
          addChar();
          nextToken = MULT_OP;
          break;
    case '/':
          addChar();
          nextToken = DIV_OP;
          break;
    case '.':
          addChar();
          nextToken = POINT;
          break;
    default:
          addChar();
          nextToken = -1;
          break;
    }
    return nextToken;
}
    
    public static void main(String[] args) throws IOException { 
    	String file = "C:\\Users\\Daniel\\workspace\\lexico_java\\src\\front.in";
    	
    	Lexico_java m = new Lexico_java();
    	br = new BufferedReader( new FileReader(file));
    	
    	m.getChar();
    	
    	do
    	{
    		m.lex();
    	}
    	while(nextToken != -1);
    	
        //List<String> file = l.readFile("C:\\Users\\Daniel\\workspace\\lexico_java\\src\\front.in");
    }
    
}
