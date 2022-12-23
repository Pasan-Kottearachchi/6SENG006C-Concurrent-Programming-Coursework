import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public boolean isThreadsAlive(ThreadGroup threadGroup) {
        return threadGroup.activeCount() > 0;
    }

//    customer logger function
    public void logger(String logContext, String message, Enum logColor) {
        System.out.println(
                logColor + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss SSS a")) +
                        " - " + logContext + " ::: " + message + Colours.COLOR_RESET
        );
    }

    public int generateRandomNumber(int lowerBound, int upperBound) {
        return (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
    }

}
