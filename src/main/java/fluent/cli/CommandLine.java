package fluent.cli;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 8/20/16.
 */
public class CommandLine {
    private Map<String,String> values;

    public CommandLine(Map<String,String> values) {
        this.values = Collections.unmodifiableMap(new HashMap<String, String>(values));
    }

    public String getOptionValue(String token) {
        return values.get(token);
    }

    public int intValue(String token) {
        String x = values.get(token);
        if (x == null) {
            return 0;
        } else {
            return Integer.parseInt(x);
        }
    }

    public boolean booleanValue(String token) {
        String x = values.get(token);
        if (x == null) {
            return false;
        } else {
            return Boolean.parseBoolean(x);
        }
    }


    public static final CommandLineSyntax.CommandLineSyntaxBuilder newSyntax() {
        return new CommandLineSyntax.CommandLineSyntaxBuilder();
    }
}
