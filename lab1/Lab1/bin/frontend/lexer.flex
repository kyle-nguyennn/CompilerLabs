/* You do not need to change anything up here. */
package lexer;

import frontend.Token;
import static frontend.Token.Type.*;

%%

%public
%final
%class Lexer
%function nextToken
%type Token
%unicode
%line
%column

%{
	/* These two methods are for the convenience of rules to create token objects.
	* If you do not want to use them, delete them
	* otherwise add the code in 
	*/
	
	private Token token(Token.Type type) {
		return token(type, yytext());
	}
	
	/* Use this method for rules where you need to process yytext() to get the lexeme of the token.
	 *
	 * Useful for string literals; e.g., the quotes around the literal are part of yytext(),
	 *       but they should not be part of the lexeme. 
	*/
	private Token token(Token.Type type, String text) {
		return new Token(type, yyline, yycolumn, text);
	}
%}

/* This definition may come in handy. If you wish, you can add more definitions here. */
WhiteSpace = [ ] | \t | \f | \n | \r


%%
/* put in your rules here.    */

// Keywords
"boolean" { return token(BOOLEAN); }
"break" { return token(BREAK);}
"else" { return token(ELSE);}
"false" { return token(FALSE);}
"if" { return token(IF);}
"import" { return token(IMPORT);}
"int" { return token(INT);}
"module" { return token(MODULE);}
"public" { return token(PUBLIC);}
"return" { return token(RETURN);}
"true" { return token(TRUE);}
"type" { return token(TYPE);}
"void" { return token(VOID);}
"while" { return token(WHILE);}


// punctuation symbols
","		{ return token(COMMA); }						/* , */
"["		{ return token(LBRACKET); }						/* [ */
"{"		{ return token(LCURLY); }						/* { */
"("		{ return token(LPAREN); }						/* ( */
"]"		{ return token(RBRACKET); }						/* ] */
"}"		{ return token(RCURLY); }						/* } */
")"		{ return token(RPAREN); }						/* ) */
";"		{ return token(SEMICOLON); }					/* ; */

// operators
"/"		{ return token(DIV); }						/* / */
"=="	{ return token(EQEQ); }						/* == */
"="		{ return token(EQL); }						/* = */
">="	{ return token(GEQ); }						/* >= */
">"		{ return token(GT); }						/* > */
"<="	{ return token(LEQ); }						/* <= */
"<"		{ return token(LT); }						/* < */
"-"		{ return token(MINUS); }					/* - */
"!="	{ return token(NEQ); }						/* != */
"+"		{ return token(PLUS); }						/* + */
"*"		{ return token(TIMES); }					/* * */


// identifier
[a-zA-Z][a-zA-Z0-9_]*	{ return token(ID); }

// literals
[0-9]+		{ return token(INT_LITERAL); }
\"[^\"\n]*\"		{ return token(STRING_LITERAL, yytext().substring(1, yytext().length() - 1)); }

{WhiteSpace} {}

/* You don't need to change anything below this line. */
.							{ throw new Error("unexpected character '" + yytext() + "'"); }
<<EOF>>						{ return token(EOF); }
