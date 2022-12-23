public class LaserPrinter implements ServicePrinter {
    private String laserPrinterName;
    private ThreadGroup studentThreads;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int printedDocumentCount = 0;
    private Utils utils = new Utils();

    public LaserPrinter(String laserPrinterName, ThreadGroup threadGroup, int paperLevel, int tonerLevel) {
        this.laserPrinterName = laserPrinterName;
        this.studentThreads = threadGroup;
        this.currentPaperLevel = paperLevel;
        this.currentTonerLevel = tonerLevel;
    }

    @Override
    public synchronized void printDocument(Document document) {
        int pagesToPrint = document.getNumberOfPages();
        boolean notEnoughPaper = pagesToPrint > currentPaperLevel;
        boolean notEnoughToner = pagesToPrint > currentTonerLevel;
        while (notEnoughPaper || notEnoughToner) {
            try {
                utils.logger(
                        "Laser Printer ",
                        "Cannot print [" + document.getDocumentName() + "] by: " +
                                document.getUserID() + " from " + laserPrinterName + ". Paper Level: " +
                                currentPaperLevel + ". Toner Level: " + currentTonerLevel + ". Documents Printed: " +
                                printedDocumentCount,
                        Colours.COLOR_RED
                );

                wait();
                notEnoughPaper = pagesToPrint > currentPaperLevel;
                notEnoughToner = pagesToPrint > currentTonerLevel;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        currentPaperLevel -= pagesToPrint;
        currentTonerLevel -= pagesToPrint;
        printedDocumentCount++;
        utils.logger(
                "Laser Printer ",
                "Printed " + pagesToPrint + " pages of " + document.getDocumentName() +
                        " by " + document.getUserID() + " from " + laserPrinterName +
                        ". Paper Level now: " + currentPaperLevel + ". Toner Level now: " +
                        currentTonerLevel + ". Documents Printed now: " + printedDocumentCount,
                Colours.COLOR_RESET
        );
    }


    @Override
    public synchronized void replaceTonerCartridge(int attempt, String technicianName) {
        boolean tonerNotToBeReplaced = currentTonerLevel >= MINIMUM_TONER_LEVEL;
        while (tonerNotToBeReplaced) {
            try {
                wait(5000);
                if (utils.isThreadsAlive(studentThreads)) {
                    tonerNotToBeReplaced = currentTonerLevel >= MINIMUM_TONER_LEVEL;
                } else {
                    utils.logger(
                            "Laser Printer",
                            "Completed all threads. Toner refill process terminating",
                            Colours.COLOR_YELLOW
                    );
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        currentTonerLevel += PAGES_PER_TONER_CARTRIDGE;
        utils.logger(
                "Laser Printer",
                "Refill toner attempt " + attempt + " by " + technicianName + " on " +
                        laserPrinterName + " successful. Toner level now: " + currentTonerLevel,
                Colours.COLOR_BLUE
        );
        notifyAll();
    }

    @Override
    public synchronized void refillPaper(int attempt, String technicianName) {
        /*
            Assume there are 50 sheets per pack of paper & the printer can hold up to 250
            sheets of paper at any one time.
            Example: the printer has 150 sheets of paper, therefore, it can be refilled.
                    But if it has 201 sheets of paper, then it cannot be refilled , as it would result in 251
            sheets, and the technician should wait. But he or she is only prepared to wait for 5
            seconds before checking if it can be refilled it again.
            The refillPaper() method should print the following messages to the console:
         */

//        int sheetsPerPack = SHEETS_PER_PACK;
        int fullPaperTray = FULL_PAPER_TRAY;
        boolean paperNotToBeReplaced = (currentPaperLevel + SHEETS_PER_PACK) > FULL_PAPER_TRAY;
        ;

        while (paperNotToBeReplaced) {
            try {
                wait(5000);
                if (utils.isThreadsAlive(studentThreads)) {
                    paperNotToBeReplaced = currentTonerLevel >= MINIMUM_TONER_LEVEL;
                } else {
                    utils.logger(
                            "Laser Printer",
                            "Completed all threads. Paper refill process terminating",
                            Colours.COLOR_YELLOW
                    );
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        currentPaperLevel += SHEETS_PER_PACK;
        utils.logger(
                "Laser Printer",
                "Refill paper attempt " + attempt + " by " + technicianName + " on " +
                        laserPrinterName + " successful. Paper level now " + currentPaperLevel,
                Colours.COLOR_BLUE
        );
        notifyAll();

    }

    @Override
    public String toString() {
        return "{" +
                "Printer Name: '" + laserPrinterName + '\'' +
                ", Paper Level: " + currentPaperLevel +
                ", Toner Level: " + currentTonerLevel +
                ", Documents Printed: " + printedDocumentCount +
                '}';
    }
}
