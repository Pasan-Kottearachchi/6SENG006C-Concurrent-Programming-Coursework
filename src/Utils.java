import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public boolean isThreadsAlive(ThreadGroup threadGroup) {
        return threadGroup.activeCount() > 0;
    }

//    customer logger function
    public void logger(String logContext, String message) {
        System.out.println(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss SSS a")) +
                        logContext + "::" + message
        );
    }

}
