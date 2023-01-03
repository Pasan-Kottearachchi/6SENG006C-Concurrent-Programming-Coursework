import java.util.Random;

public class Student extends Thread {
    private Printer studentPrinter;
    private Utils utils = new Utils();

    public Student(ThreadGroup threadGroup, Printer printer, String name) {
        super(threadGroup, name);
        this.studentPrinter = printer;
    }

    /*
        -   Student class behaviour.
        -   Create and print five documents with different lengths and names.
        -   He/she should "sleep" for a random amount of time between each printing request.
        -   When he/she has finished printing, print out a message
    */

    @Override
    public void run() {
        int numberOfDocumentsPerStudent = 5;
        String studentName = this.getName();
        int totalNumberOfPages = 0;

        for (int i = 1; i <= numberOfDocumentsPerStudent; i++) {
            /*
                -   MIN_PAGE_COUNT is set to 1 because a given document should at least have one page to be printed.
                -   Every document is checked whether it is the last document since if it is we don't have to sleep
                    the student after printing his/her last document
                -   Randomly generated document name was given which consists the format of
                    DOCUMENT-{studentName}-{pageCount} eg: 'DOCUMENT-Pasan-5';
             */

            int MIN_PAGE_COUNT = 1;
            int MAX_PAGE_COUNT = 10;
            int pageCount = utils.generateRandomNumber(MIN_PAGE_COUNT, MAX_PAGE_COUNT);
            boolean lastDocument = i == numberOfDocumentsPerStudent;

            String documentName = "DOCUMENT_" + i + " - " + studentName + " - " + pageCount;
            Document studentDocument = new Document(studentName, documentName, pageCount);
            studentPrinter.printDocument(studentDocument);
            totalNumberOfPages += pageCount;

            if (!lastDocument) {
                int MIN_SLEEP_TIME = 1000;
                int MAX_SLEEP_TIME = 5000;
                int sleepTime = utils.generateRandomNumber(MIN_SLEEP_TIME, MAX_SLEEP_TIME);
                try {
                    utils.logger(
                            "Student Document",
                            studentName + " is sleeping for " + sleepTime +
                                    " milliseconds after printing " + documentName + " document.",
                            Colours.COLOR_RESET
                    );
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    utils.logger(
                            "Student Document",
                            documentName + " was interrupted during sleeping time.",
                            Colours.COLOR_RESET
                    );
                }
            }
        }

        utils.logger(
                "Student Document",
                studentName + " Finished Printing : " + numberOfDocumentsPerStudent + " documents, " +
                        totalNumberOfPages + " pages.",
                Colours.COLOR_GREEN
        );
    }

}
