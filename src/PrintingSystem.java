public class PrintingSystem {
    public static void main(String[] args) {
        Utils utils = new Utils();
        ThreadGroup studentsGroup = new ThreadGroup("students");
        ThreadGroup techniciansGroup = new ThreadGroup("technicians");

        ServicePrinter sharedPrinter = new LaserPrinter(
                "Shared Printer",
                studentsGroup,
                10,
                50
        );

        Thread student1 = new Student(studentsGroup, sharedPrinter, "student1");
        Thread student2 = new Student(studentsGroup, sharedPrinter, "student2");
        Thread student3 = new Student(studentsGroup, sharedPrinter, "student3");
        Thread student4 = new Student(studentsGroup, sharedPrinter, "student4");

        Thread paperTechnician = new PaperTechnician(techniciansGroup, sharedPrinter, "paperTechnician");
        Thread tonerTechnician = new TonerTechnician(techniciansGroup, sharedPrinter, "tonerTechnician");

        printSummary(sharedPrinter);

        student1.start();
        student2.start();
        student3.start();
        student4.start();
        paperTechnician.start();
        tonerTechnician.start();

        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            paperTechnician.join();
            tonerTechnician.join();

            printSummary(sharedPrinter);
        } catch (InterruptedException e) {
            e.printStackTrace();
            utils.logger(
                    "PrintingSystem",
                    "InterruptedException "+ e.getMessage(),
                    Colours.COLOR_RESET
            );

        }
    }

//    add method to print the summary of the printer
    public static void printSummary(ServicePrinter printer){
        String preContent = " ===============================================\n" +
                            "|                PRINTER SUMMARY                |  \n" +
                            " ===============================================\n";
        System.out.print(preContent);
        System.out.println(printer.toString());
        System.out.println();
    }

}
