package fluent.cli;

/**
 * Created by michael on 8/20/16.
 */
public class CommandLineOption {
    private final String defaultValue;
    private final boolean takesValue;
    private final boolean takesToken;
    private final boolean required;
    private final String description;
    private final String shortToken;
    private final String longToken;
    private final String name;

    public CommandLineOption(String defaultValue, boolean takesValue, boolean takesToken,
                             boolean required,
                             String description, String shortToken, String longToken,String name) {
        this.defaultValue = defaultValue;
        this.takesValue = takesValue;
        this.takesToken = takesToken;
        this.required = required;
        this.description = description;
        this.shortToken = shortToken;
        this.longToken = longToken;
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isTakesValue() {
        return takesValue;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public String getDescription() {
        return description;
    }

    public String getShortToken() {
        return shortToken;
    }

    public String getLongToken() {
        return longToken;
    }

    public boolean isTakesToken() {
        return takesToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandLineOption that = (CommandLineOption) o;

        if (takesValue != that.takesValue) return false;
        if (required != that.required) return false;
        if (defaultValue != null ? !defaultValue.equals(that.defaultValue) : that.defaultValue != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (shortToken != null ? !shortToken.equals(that.shortToken) : that.shortToken != null) return false;
        return longToken != null ? longToken.equals(that.longToken) : that.longToken == null;

    }

    @Override
    public int hashCode() {
        int result = defaultValue != null ? defaultValue.hashCode() : 0;
        result = 31 * result + (takesValue ? 1 : 0);
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (shortToken != null ? shortToken.hashCode() : 0);
        result = 31 * result + (longToken != null ? longToken.hashCode() : 0);
        return result;
    }

    public static class CommandLineOptionBuilder {
        private final CommandLineGroup.CommandLineGroupBuilder parent;
        private String defaultValue;
        private boolean takesValue = true;
        private boolean takesToken = true;
        private boolean required = false;
        private String description;
        private String shortToken;
        private String longToken;
        private String name;

        public CommandLineOptionBuilder(CommandLineGroup.CommandLineGroupBuilder parent) {
            this.parent = parent;
        }

        public CommandLineGroup.CommandLineGroupBuilder add() {
            return this.parent.add(this.build());
        }

        public CommandLineGroup.CommandLineGroupBuilder addOption() {
            return this.parent.add(this.build());
        }

        public CommandLineSyntax.CommandLineSyntaxBuilder finish() {
            return add().add();
        }

        public CommandLineOption build() {
            return new CommandLineOption(defaultValue,takesValue,takesToken,
                    required,description,shortToken,longToken,name);
        }



        public CommandLineOptionBuilder defaultValue(String x) {
            this.defaultValue = x;
            return this;
        }

        public CommandLineOptionBuilder name(String x) {
            this.name = x;
            return this;
        }

        public CommandLineOptionBuilder takesValue(boolean x) {
            this.takesValue = x;
            return this;
        }

        public CommandLineOptionBuilder takesToken(boolean x) {
            this.takesToken = x;
            return this;
        }

        public CommandLineOptionBuilder required(boolean x) {
            this.required = x;
            return this;
        }

        public CommandLineOptionBuilder description(String x) {
            this.description = x;
            return this;
        }

        public CommandLineOptionBuilder longToken(String x) {
            this.longToken = x;
            return this;
        }

        public CommandLineOptionBuilder shortToken(String x) {
            this.shortToken = x;
            return this;
        }

    }
}
