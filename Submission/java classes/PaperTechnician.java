public class PaperTechnician extends Thread {
    private ServicePrinter servicePrinter;
    private Utils utils = new Utils();

    public PaperTechnician(ThreadGroup threadGroup, ServicePrinter printer, String name) {
        super(threadGroup, name);
        this.servicePrinter = printer;
    }

    @Override
    public void run() {
        int MAX_REFILL_ATTEMPTS = 3;
        String technicianName = this.getName();
        for (int i = 1; i <= MAX_REFILL_ATTEMPTS; i++) {
            servicePrinter.refillPaper(i, technicianName);
            boolean lastRefillTry = i == MAX_REFILL_ATTEMPTS;

            if (!lastRefillTry) {
                int MIN_SLEEP_TIME = 1000;
                int MAX_SLEEP_TIME = 5000;
                int sleepTime = utils.generateRandomNumber(MIN_SLEEP_TIME, MAX_SLEEP_TIME);
                try {
                    utils.logger(
                            "Paper Technician",
                            technicianName + " is sleeping for " + sleepTime +
                                    " milliseconds after refilling papers.",
                            Colours.COLOR_PURPLE
                    );
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    utils.logger(
                            "Paper Technician",
                            technicianName + " was interrupted during sleeping time.",
                            Colours.COLOR_RESET
                    );
                }
            }
        }

        utils.logger(
                "Paper Technician",
                technicianName + " Finished, packs of paper used: " + MAX_REFILL_ATTEMPTS,
                Colours.COLOR_BLUE
        );
    }
}
