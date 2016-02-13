package org.asciidoc.intellij.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.LexerTestCase;

/**
 * @author yole
 */
public class AsciiDocLexerTest extends LexerTestCase {
  public void testSimple() {
    doTest("abc\ndef",
        "AsciiDoc:TEXT ('abc')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:TEXT ('def')");
  }

  public void testLineComment() {
    doTest("// foo\n// bar", "AsciiDoc:LINE_COMMENT ('// foo')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:LINE_COMMENT ('// bar')");
  }

  public void testListing() {
    doTest("aaa\n----\nbbbb\n----\ncccc",
        "AsciiDoc:TEXT ('aaa')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:LISTING_DELIMITER ('----')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:LISTING_TEXT ('bbbb')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:LISTING_DELIMITER ('----')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:TEXT ('cccc')");
  }

  public void testHeading() {
    doTest("= Abc\nabc\n== Def\ndef",
        "AsciiDoc:HEADING ('= Abc')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:TEXT ('abc')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:HEADING ('== Def')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:TEXT ('def')");
  }

  public void testCommentBlock() {
    doTest("////\nfoo bar\n////\nabc",
        "AsciiDoc:BLOCK_COMMENT ('////\\nfoo bar\\n////')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:TEXT ('abc')");
  }

  public void testBlockMacro() {
    doTest("image::foo.png[Caption]\nabc",
            "AsciiDoc:BLOCK_MACRO_ID ('image::')\n" +
            "AsciiDoc:BLOCK_MACRO_BODY ('foo.png')\n" +
            "AsciiDoc:BLOCK_MACRO_ATTRIBUTES ('[Caption]')\n" +
            "AsciiDoc:LINE_BREAK ('\\n')\n" +
            "AsciiDoc:TEXT ('abc')");
  }

  public void testExample() {
    doTest("====\nFoo Bar Baz\n====\n",
        "AsciiDoc:EXAMPLE_BLOCK_DELIMITER ('====\\n')\n"+
        "AsciiDoc:TEXT ('Foo Bar Baz')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:EXAMPLE_BLOCK_DELIMITER ('====\\n')");
  }

  public void testTitle() {
    doTest(".Foo bar baz\nFoo bar baz",
        "AsciiDoc:TITLE ('.Foo bar baz')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')\n" +
        "AsciiDoc:TEXT ('Foo bar baz')");
  }

  public void testBlockAttrs() {
    doTest("[NOTE]\n",
        "AsciiDoc:BLOCK_ATTRS_START ('[')\n" +
        "AsciiDoc:BLOCK_ATTR_NAME ('NOTE')\n" +
        "AsciiDoc:BLOCK_ATTRS_END (']')\n" +
        "AsciiDoc:LINE_BREAK ('\\n')");
  }


  @Override
  protected Lexer createLexer() {
    return new AsciiDocLexer();
  }

  @Override
  protected String getDirPath() {
    return null;
  }
}
