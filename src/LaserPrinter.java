public class LaserPrinter implements ServicePrinter {
    private String laserPrinterName;
    private ThreadGroup studentThreads;
    private int currentPaperLevel = FULL_PAPER_TRAY;
    private int currentTonerLevel = FULL_TONER_LEVEL;
    private int printedDocumentCount = 0;
    private Utils utils = new Utils();

    public LaserPrinter(String laserPrinterName, ThreadGroup threadGroup) {
        this.laserPrinterName = laserPrinterName;
        this.studentThreads = threadGroup;
    }

    @Override
    public synchronized void printDocument(Document document) {
       /*
            Allow students to print "documents" using the printDocument( document)
            method, provided it has sufficient quantities of paper and toner to be able to print the
            document.
            Assume that to print one page of a document you need 1 sheet of paper and 1 unit of
            toner.
            Example: the printer can print a 10 page document provided both the paper and
            toner are greater than 10; and that printing this document reduces each by 10.
            If either the paper or toner (or both) are less than 10 then the document cannot be
            printed and printing must wait until there is enough of both to print it.
        */

            int pagesToPrint = document.getNumberOfPages();
            int minTonerOrPaperLevel = Math.min(currentPaperLevel, currentTonerLevel);
            while (pagesToPrint > minTonerOrPaperLevel) {

                try {
                    System.out.println(
                            "Cannot print " + document.getDocumentName() + " by " +
                                    document.getUserID() + " from " + laserPrinterName + " paper level " +
                                    currentPaperLevel + " toner level " + currentTonerLevel + " documents printed " +
                                    printedDocumentCount
                    );

                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (pagesToPrint <= minTonerOrPaperLevel) {

                currentPaperLevel -= pagesToPrint;
                currentTonerLevel -= pagesToPrint;
                printedDocumentCount++;
                System.out.println(
                        "Printed " + pagesToPrint + " pages of " + document.getDocumentName() +
                                " by " + document.getUserID() + " from " + laserPrinterName +
                                " paper level now " + currentPaperLevel + " toner level now " +
                                currentTonerLevel + " documents printed now " + printedDocumentCount
                );
                notifyAll();
            }

        }


        @Override
        public void replaceTonerCartridge ( int attempt, String technicianName){
         /*
            Allow the toner technician to replace the toner cartridge using the
            replaceTonerCartridge( ) method, provided it needs to be replaced, i.e. has a
            toner level of less than 10. Assume a toner cartridge can print 500 sheets of paper.
            Example: the printer has a toner level of 9, therefore, the toner cartridge can be
            replaced.
            But if it has a level of 10, then it cannot be replaced, as it would be a waste of toner,
            and the technician should wait. But he or she is only prepared to wait for 5 seconds
            before checking if it can be replaced it again
         */
            while (currentTonerLevel > MINIMUM_TONER_LEVEL) {
                try {
                    if (utils.isThreadsAlive(studentThreads)) {
                        utils.logger(
                                "Laser Printer",
                                "Refill toner attempt " + attempt + " by " +
                                        technicianName + " on " + laserPrinterName +
                                        " failed due to alive threads using the printer. Waiting for refill sheets"
                        );
                        wait(5000);
                    } else {
                        utils.logger(
                                "Laser Printer",
                                "Completed all threads. No active operation. Toner refill process starting." +
                                        "Toner refill process started by " + technicianName + " on " +
                                        laserPrinterName + " attempt " + attempt
                        );
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (currentTonerLevel < MINIMUM_TONER_LEVEL) {
                currentTonerLevel = FULL_TONER_LEVEL;
                utils.logger(
                        "Laser Printer",
                        "Refill toner attempt " + attempt + " by " + technicianName + " on " +
                                laserPrinterName + " successful. Toner level now: " + currentTonerLevel
                );
                notifyAll();
            }

        }

        @Override
        public void refillPaper ( int attempt, String technicianName){
        /*
            Assume there are 50 sheets per pack of paper & the printer can hold up to 250
            sheets of paper at any one time.
            Example: the printer has 150 sheets of paper, therefore, it can be refilled.
                    But if it has 201 sheets of paper, then it cannot be refilled , as it would result in 251
            sheets, and the technician should wait. But he or she is only prepared to wait for 5
            seconds before checking if it can be refilled it again.
            The refillPaper() method should print the following messages to the console:
         */

            int sheetsPerPack = SHEETS_PER_PACK;
            int fullPaperTray = FULL_PAPER_TRAY;
            while (currentPaperLevel > fullPaperTray - sheetsPerPack) {
                try {
                    if (utils.isThreadsAlive(studentThreads)) {
                        utils.logger(
                                "Laser Printer",
                                "Refill paper attempt " + attempt + " by " +
                                        technicianName + " on " + laserPrinterName +
                                        " failed due to alive threads using the printer. Waiting for refill sheets"
                        );
                        wait(5000);
                    } else {
                        utils.logger(
                                "Laser Printer",
                                "Completed all threads. Refill process terminating"
                        );
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (currentPaperLevel <= fullPaperTray - sheetsPerPack) {
                currentPaperLevel += sheetsPerPack;
                utils.logger(
                        "Laser Printer",
                        "Refill paper attempt " + attempt + " by " + technicianName + " on " +
                                laserPrinterName + " successful. Paper level now " + currentPaperLevel
                );
                notifyAll();
            }

        }

        @Override
        public String toString () {
            return "[" +
                    "Printer Name: '" + laserPrinterName + '\'' +
                    ", Paper Level: " + currentPaperLevel +
                    ", Toner Level: " + currentTonerLevel +
                    ", Documents Printed: " + printedDocumentCount +
                    ']';
        }
    }
