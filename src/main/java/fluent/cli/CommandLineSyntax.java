package fluent.cli;

import java.util.*;

/**
 * Created by michael on 8/20/16.
 */
public class CommandLineSyntax {
    private final List<CommandLineGroup> groups;

    private CommandLineSyntax(List<CommandLineGroup> groups) {
        this.groups = Collections.unmodifiableList(new ArrayList<CommandLineGroup>(groups));
    }

    public CommandLine parse(String[] arguments) {
        Map<String,String> values = new HashMap<>();
        Iterator<CommandLineGroup> groupIterator = groups.iterator();
        CommandLineGroup group = groupIterator.next();
        CommandLineOption currentOption = null;
        int untokenedIndex = 0;

        for (int i = 0;i<arguments.length;i++) {
            String x = arguments[i];
            if (currentOption != null) {
                put(x,currentOption,values);
                // do not keep setting the value for this option, set it to null.
                currentOption = null;
            } else {
                // specifying an option
                currentOption = group.getOption(x,untokenedIndex);
                while (currentOption == null && groupIterator.hasNext()) {
                    group = groupIterator.next();
                    untokenedIndex = 0;
                    currentOption = group.getOption(x,untokenedIndex);
                }
                if (currentOption != null) {
                    if (!currentOption.isTakesToken()) {
                        untokenedIndex++;
                        // this is a value.
                        values.put(currentOption.getName(),x);
                        currentOption = null;
                    } else if (!currentOption.isTakesValue()) {
                        if (currentOption.getDefaultValue().toLowerCase().equals("true")) {
                            put("false", currentOption, values);
                        } else {
                            put("true", currentOption, values);
                        }
                        currentOption = null;
                    }
                } else {
                    throw new IllegalArgumentException("invalid command line");
                }
            }
        }
        for (CommandLineGroup g : groups) {
            for (CommandLineOption o : g.getOptions()) {
                if (o.isRequired()) {
                    if (o.getShortToken() != null && !values.containsKey(o.getShortToken())) {
                        throw new IllegalArgumentException("invalid command line");
                    }
                    if (o.getLongToken() != null && !values.containsKey(o.getLongToken())) {
                        throw new IllegalArgumentException("invalid command line");
                    }
                    if (o.getName() != null && !values.containsKey(o.getName())) {
                        throw new IllegalArgumentException("invalid command line");
                    }
                }
                if (o.getDefaultValue() != null) {
                    if (o.getShortToken() != null && !values.containsKey(o.getShortToken())) {
                        values.put(o.getShortToken(), o.getDefaultValue());
                    }
                    if (o.getLongToken() != null && !values.containsKey(o.getLongToken())) {
                        values.put(o.getLongToken(), o.getDefaultValue());
                    }
                    if (o.getName() != null && !values.containsKey(o.getName())) {
                        values.put(o.getName(), o.getDefaultValue());
                    }
                }
            }
        }
        return new CommandLine(values);
    }

    private void put(String x,CommandLineOption currentOption,Map<String,String> values) {
        if (currentOption.getShortToken() != null) {
            values.put(currentOption.getShortToken(),x);
        }
        if (currentOption.getLongToken() != null) {
            values.put(currentOption.getLongToken(),x);
        }
        if (currentOption.getName() != null) {
            values.put(currentOption.getName(),x);
        }
    }

    public static class CommandLineSyntaxBuilder {
        private List<CommandLineGroup> groups = new ArrayList<>();

        public CommandLineGroup.CommandLineGroupBuilder startGroup() {
            return new fluent.cli.CommandLineGroup.CommandLineGroupBuilder(this);
        }

        public CommandLineOption.CommandLineOptionBuilder start() {
            return startGroup().startOption();
        }

        public CommandLineSyntaxBuilder add(CommandLineGroup.CommandLineGroupBuilder group) {
            groups.add(group.build());
            return this;
        }

        public CommandLineSyntax build() {
            return new CommandLineSyntax(groups);
        }
    }

}
