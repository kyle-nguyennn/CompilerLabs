package test;

import static org.junit.Assert.fail;

import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import parser.Parser;

public class ParserTests {
	private void runtest(String src) {
		runtest(src, true);
	}

	private void runtest(String src, boolean succeed) {
		Parser parser = new Parser();
		try {
			parser.parse(new Lexer(new StringReader(src)));
			if(!succeed) {
				fail("Test was supposed to fail, but succeeded");
			}
		} catch (beaver.Parser.Exception e) {
			if(succeed) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testEmptyModule() {
		runtest("module Test { "
				+ "}");
	}
	
	@Test
	public void testModuleImports() {
		runtest("module Test {"
				+ "import module1;"
				+ "import module2;"
				+ "}");
	}
	
	@Test
	public void testModuleTypeDeclaration() {
		runtest("module Test {"
				+ "public type float = \"FLOAT\";"
				+ "type enum = \"ENUM\";"
				+ "}");
	}
	
	@Test
	public void testModuleFieldDeclaration() {
		runtest("module Test {"
				+ "public boolean booleanfield;"
				+ "int intfield;"
				+ "}");
	}
	
	@Test
	public void testModuleEmptyFunctionDeclaration() {
		runtest("module Test {"
				+ "public void fun() {}"
				+ "}");
	}
	
	@Test
	public void testModuleParameterFunctionDeclaration() {
		runtest("module Test {"
				+ "public void fun(int param) {}"
				+ "}");
	}
	
	@Test
	public void testModuleParameterListFunctionDeclaration() {
		runtest("module Test {"
				+ "public void fun(int param1, boolean param2) {}"
				+ "}");
	}
	
	@Test
	public void testModuleIfWhileFunctionDeclaration() {
		runtest("module Test {"
				+ "public void fun() {"
				+ "  int x;"
				+ "  if(x < 10) {return 10;}"
				+ "  else {return;}"
				+ ""
				+ "  while(x < 15) {x = 10; break; }"
				+ "}"
				+ "}");
	}
	
	@Test
	public void testModuleFunctionDeclaration() {
		runtest("module Test {"
				+ "public void fun() {"
				+ "  a[5+7 / 3] = [2,3,4];"
				+ "  fun(4,5,6);"
				+ "}"
				+ "}");
	}
}
