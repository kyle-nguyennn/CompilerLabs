package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

/**
 * This class contains unit tests for your lexer. Currently, there is only one test, but you
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i=0;
		Token actual=new Token(MODULE, 0, 0, ""), expected;
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch(Error e) {
					if(expected != null)
						fail(e.getMessage());
					/* return; */
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Example unit test. */
	@Test
	public void testKWs() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("module false return while",
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 0, 13, "return"),
				new Token(WHILE, 0, 20, "while"),
				new Token(EOF, 0, 25, ""));
	}

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token)null,
				new Token(EOF, 0, 3, ""));
	}

	@Test
	public void testStringLiteral() {
		runtest("\"\\n\"", 
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));
	}
	
	/** Extra unit tests */
	@Test
	public void testStringLiteralFail() {
		runtest("\"a\"\"", 
				new Token(STRING_LITERAL, 0, 0, "a"),
				(Token)null,
				new Token(EOF, 0, 4, ""));
	}

	@Test
	public void testStringLiteralSimpel() {
		runtest("\"asf\"", 
				new Token(STRING_LITERAL, 0, 0, "asf"),
				new Token(EOF, 0, 5, ""));

	}
	
	@Test
	public void testPunctuation() {
		runtest("[]{}()", 
				new Token(LBRACKET, 0, 0, "["),
				new Token(RBRACKET, 0, 1, "]"),
				new Token(LCURLY, 0, 2, "{"),
				new Token(RCURLY, 0, 3, "}"),
				new Token(LPAREN, 0, 4, "("),
				new Token(RPAREN, 0, 5, ")"),
				new Token(EOF, 0, 6, ""));

	}
	
	@Test
	public void testInt() {
		runtest("1234", 
				new Token(INT_LITERAL, 0, 0, "1234"),
				new Token(EOF, 0, 4, ""));
	}
	
	@Test
	public void testIntWithLeadingZeros() {
		runtest("030", 
				new Token(INT_LITERAL, 0, 0, "030"),
				new Token(EOF, 0, 3, ""));
	}
	
	@Test
	public void testSignedInt() {
		runtest("-123", 
				new Token(MINUS, 0, 0, "-"),
				new Token(INT_LITERAL, 0, 1, "123"),
				new Token(EOF, 0, 4, ""));
	}
	
	@Test
	public void testDeclaration() {
		runtest("int x_1 = \"hello world\"", 
				new Token(INT, 0, 0, "int"),
				new Token(ID, 0, 4, "x_1"),
				new Token(EQL, 0, 8, "="),
				new Token(STRING_LITERAL, 0, 10, "hello world"),
				new Token(EOF, 0, 23, ""));
	}
	
	@Test
	public void testEquals() {
		runtest("===", 
				new Token(EQEQ, 0, 0, "=="),
				new Token(EQL, 0, 2, "="),
				new Token(EOF, 0, 3, ""));
	}
	
	@Test
	public void testBooleanExpression() {
		runtest("true == false", 
				new Token(TRUE, 0, 0, "true"),
				new Token(EQEQ, 0, 5, "=="),
				new Token(FALSE, 0, 8, "false"),
				new Token(EOF, 0, 13, ""));
	}
	
	@Test
	public void testRandomBooleanExpression() {
		runtest("\"true\" >= +", 
				new Token(STRING_LITERAL, 0, 0, "true"),
				new Token(GEQ, 0, 7, ">="),
				new Token(PLUS, 0, 10, "+"),
				new Token(EOF, 0, 11, ""));
	}
}
