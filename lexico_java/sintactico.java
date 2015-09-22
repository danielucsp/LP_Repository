package sintactico;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Alumno-C
 */

public class sintactico {
	/* file variable name */
	static BufferedReader in_fp;
	/* Character classes */
	static int LETTER = 0;
	static int DIGIT = 1;
	static int POINT = 2;
	static int UNKNOWN = 99;;
	/* Token codes */
	static int INT_LIT = 10;
	static int FLOAT_LIT = 12;
	static int POINT_LIT = 33;
	static int IDENT = 11;
	static int ASSIGN_OP = 20;
	static int ADD_OP = 21;
	static int SUB_OP = 22;
	static int MULT_OP = 23;
	static int DIV_OP = 24;
	static int LEFT_PAREN = 25;
	static int RIGHT_PAREN = 26;

	int charClass;
	static char[] lexeme = new char[100];
	static char nextChar;
	static int lexLen;
	int token;
	static int nextToken;

	static boolean flag = false;
	static boolean flag_validated = false;

	/*****************************************************/
	/* addChar - a function to add nextChar to lexeme */
	public static void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
		} else
			System.out.println("Error - lexeme is too long \n");
	}

	/*****************************************************/
	/*
	 * getChar - a function to get the next character of input and determine its
	 * character class
	 */

	void getChar() throws IOException {
		if ((nextChar = (char) in_fp.read()) != -1) {
			if (Character.isLetter(nextChar))
				charClass = LETTER;
			else if (Character.isDigit(nextChar))
				charClass = DIGIT;
			else if (nextChar == '.')
				charClass = POINT;
			else
				charClass = UNKNOWN;
		} else
			charClass = -1;
	}

	public static char getc1(String text) {

		char letter;

		letter = text.charAt(0);

		System.out.println(letter);
		return letter;
	}

	public boolean isAlpha(String nextChar2) {
		char[] chars = nextChar2.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}

		return true;
	}

	private char getc(String in_fp2) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @throws IOException
	 ***************************************************/
	/*
	 * getNonBlank - a function to call getChar until it returns a
	 * non-whitespace character
	 */
	void getNonBlank() throws IOException {
		while (Character.isDigit(nextChar) || nextChar == ' ')
			getChar();
	}

	/**
	 * @throws IOException
	 ***************************************************/
	/*
	 * lex - a simple lexical analyzer for arithmetic expressions
	 */
	public int lex() throws IOException {
		lexLen = 0;
		getNonBlank();
		if (charClass == LETTER) {
			addChar();
			getChar();
			while (charClass == LETTER || charClass == DIGIT) {
				addChar();
				getChar();
			}
			nextToken = IDENT;
		} else if (charClass == DIGIT) {
			addChar();
			getChar();
			while (charClass == DIGIT) {
				addChar();
				getChar();
			}
			if (charClass == POINT) {
				addChar();
				getChar();
				flag = true;
				while (charClass == DIGIT) {
					addChar();
					getChar();
                                        //if LETTER QUE RECORRA TODO EL LEXEMA Y UN NEXTTOKEN =NONE
                                        if(charClass == LETTER){
                                            addChar();
                                            nextToken = UNKNOWN;
                                        }
				}
			}

			if (charClass == LETTER) {
				addChar();
				getChar();
				flag_validated = true;
				while (!(nextChar == ' ')) {
					addChar();
					getChar();
				}
			}

			if (flag && !flag_validated) {
				nextToken = FLOAT_LIT;
				flag = false;
			} else if (flag_validated) {
				nextToken = UNKNOWN;
				flag_validated = false;
			} else
				nextToken = INT_LIT;
		} else if (charClass == POINT) {
			addChar();
			getChar();
			while (charClass == DIGIT) {
				addChar();
				getChar();
			}

			if (charClass == LETTER) {
				addChar();
				getChar();
				flag_validated = true;
				while (!(nextChar == ' ')) {
					addChar();
					getChar();
				}
			}

			if (flag_validated) {
				nextToken = UNKNOWN;
				flag = false;
			} else
				nextToken = FLOAT_LIT;
		} else if (charClass == UNKNOWN) {
			lookup(nextChar);
			getChar();
		} else if (charClass == -1) {
			nextToken = -1;
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';
			lexeme[3] = 0;
		}

		System.out.printf("Next token is: %d, Next lexeme is %s\n", nextToken,lexeme);
		return nextToken;
	}

	int lookup(char ch) {
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
				String file = "front.in";
		sintactico m = new sintactico();
		in_fp = new BufferedReader(new FileReader(file));
		m.getChar();
		while (nextToken != -1) {
	    
		{
			m.lex();
		}
		}


}
}
