package fluent.cli;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by michael on 8/20/16.
 */
public class CommandLineTest {

    @Test
    public void testCli() {
        CommandLineSyntax syntax = CommandLine.newSyntax()
                .startGroup()
                .startOption()
                .shortToken("f").longToken("file").defaultValue("/home").description("a directory").required(true)
                .addOption()
                .startOption()
                .required(false).shortToken("s").longToken("stuff").defaultValue("xyz")
                .addOption()
                .startOption()
                .required(false).shortToken("b").longToken("bool").defaultValue("false").takesValue(false)
                .addOption()
                .addGroup()
                .build();
        CommandLine cli = syntax.parse(new String[] {"-f","/foo"});
        Assert.assertEquals("/foo",cli.getOptionValue("f"));
        Assert.assertEquals("xyz",cli.getOptionValue("s"));
        Assert.assertFalse(cli.booleanValue("b"));
    }

    @Test
    public void testUntokened() {
        CommandLineSyntax syntax = CommandLine.newSyntax()
                .startGroup()
                .startOption()
                .name("foo").required(true).takesToken(false).description("an untokened argument")
                .addOption()
                .startOption()
                .name("bar").required(false).takesToken(false).description("an untokened argument 2")
                .addOption()
                .addGroup()
                .startGroup()
                .startOption()
                .name("zop").required(true).takesToken(false).description("my zop value")
                .addOption()
                .startOption()
                .shortToken("a").longToken("abba").required(true).description("my abba value")
                .addOption()
                .addGroup()
                .build();

        CommandLine cli = syntax.parse(new String[] {"alpha","beta","--abba","charlie","delta"});
        Assert.assertEquals("alpha",cli.getOptionValue("foo"));
        Assert.assertEquals("beta",cli.getOptionValue("bar"));
        Assert.assertEquals("charlie",cli.getOptionValue("a"));
        Assert.assertEquals("delta",cli.getOptionValue("zop"));
    }

    @Test
    public void testExample() {

        CommandLineSyntax syntax = CommandLine.newSyntax()
                .startGroup()
                .startOption()
                .takesToken(false).name("mode").required(true).description("The mode of operation")
                .addOption()
                .startOption()
                .shortToken("f").longToken("file").takesValue(true).required(true).description("The file to use")
                .addOption()
                .startOption()
                .shortToken("c").longToken("continuous").takesValue(false).required(false).defaultValue("false")
                .description("Whether or not to run continuously")
                .addOption()
                .startOption()
                .shortToken("i").longToken("interval").takesValue(true).required(false).defaultValue("60000")
                .description("The interval in milliseconds")
                .addOption()
                .addGroup()
                .build();

        CommandLine cli = syntax.parse(new String[] {"normal","-f","/home/jerry/myfile.txt","-c"});
        Assert.assertEquals("normal",cli.getOptionValue("mode"));
        Assert.assertEquals("/home/jerry/myfile.txt",cli.getOptionValue("file"));
        Assert.assertTrue(cli.booleanValue("c"));
        Assert.assertEquals(60000,cli.intValue("i"));
    }
}
