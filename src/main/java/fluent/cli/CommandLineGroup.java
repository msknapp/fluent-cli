package fluent.cli;

import java.util.*;

/**
 * Created by michael on 8/20/16.
 */
public class CommandLineGroup {

    public final Map<String,CommandLineOption> options;



    private CommandLineGroup(List<CommandLineOption> options) {
        Map<String,CommandLineOption> opts = new HashMap<>();
        int untokenedIndex = 0;
        for (CommandLineOption opt : options) {
            if (opt.isTakesToken()) {
                if (opt.getShortToken() != null) {
                    opts.put(opt.getShortToken(),opt);
                }
                if (opt.getLongToken() != null) {
                    opts.put(opt.getLongToken(), opt);
                }
                if (opt.getName() != null) {
                    opts.put(opt.getName(), opt);
                }
            } else {
                opts.put("untokened-"+untokenedIndex,opt);
                untokenedIndex++;
            }
        }
        this.options = Collections.unmodifiableMap(opts);
    }

    public CommandLineOption getOption(String name,int untokenedIndex) {
        if (name.startsWith("--")) {
            // search short tokens.
            return options.get(name.substring(2));
        } else if (name.startsWith("-")) {
            return options.get(name.substring(1));
        } else {
            // search the token free (unnamed) options.
            return options.get("untokened-"+untokenedIndex);
        }
    }

    public Collection<CommandLineOption> getOptions() {
        return options.values();
    }

    public static class CommandLineGroupBuilder {
        private final CommandLineSyntax.CommandLineSyntaxBuilder parent;
        private List<CommandLineOption> options = new ArrayList<>();

        public CommandLineGroupBuilder(CommandLineSyntax.CommandLineSyntaxBuilder parent) {
            this.parent = parent;
        }

        public CommandLineGroupBuilder add(CommandLineOption opt) {
            this.options.add(opt);
            return this;
        }

        public CommandLineOption.CommandLineOptionBuilder startOption() {
            return new CommandLineOption.CommandLineOptionBuilder(this);
        }

        public CommandLineSyntax.CommandLineSyntaxBuilder add() {
            return parent.add(this);
        }

        public CommandLineSyntax.CommandLineSyntaxBuilder addGroup() {
            return parent.add(this);
        }

        public CommandLineGroup build() {
            return new CommandLineGroup(options);
        }

    }

}
