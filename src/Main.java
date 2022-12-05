public class Main {
    public static void main(String[] args) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_WHITE = "\u001B[37m";

        String className = Main.class.getSimpleName();
        String message = "Hello world!";
        System.out.println("Hello world!");


        System.out.println(ANSI_RED + className + " : " + message + ANSI_RESET);
        System.out.println(ANSI_GREEN + className + " : " + message + ANSI_RESET);
        System.out.println(ANSI_YELLOW + className + " : " + message + ANSI_RESET);
        System.out.println(ANSI_BLUE + className + " : " + message + ANSI_RESET);
        System.out.println(ANSI_PURPLE + className + " : " + message + ANSI_RESET);
        System.out.println(ANSI_CYAN + className + " : " + message + ANSI_RESET);
        System.out.println(ANSI_WHITE + className + " : " + message + ANSI_RESET);
        System.out.println(ANSI_BLACK + className + " : " + message + ANSI_RESET);
        System.out.println(className + " : " + message);
    }
}