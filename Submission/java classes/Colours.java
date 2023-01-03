public enum Colours {
    COLOR_RESET("\u001B[0m"),
    COLOR_RED("\u001B[31m"),
    COLOR_GREEN("\u001B[32m"),
    COLOR_YELLOW("\u001B[33m"),
    COLOR_BLUE("\u001B[34m"),
    COLOR_PURPLE("\u001B[35m"),
    COLOR_WHITE("\u001B[37m");

    String consoleColor;

    Colours(String consoleColor) {
        this.consoleColor = consoleColor;
    }

    @Override
    public String toString() {
        return consoleColor;
    }
}
