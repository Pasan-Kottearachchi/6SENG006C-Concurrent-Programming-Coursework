/**
 * *************************************************************
 * File:      ServicePrinter.java (INTERFACE)
 * Author:    P. Howells
 * Contents:  6SENG002W CWK
 * This provides the interface for the technicians
 * to use & service the printer.
 * Date:      22/10/20
 * Version:   1.0
 * *************************************************************
 */

public interface ServicePrinter extends Printer {

    // from Printer:
    //    public void printDocument( Document document ) ;

    // Printer constants 

    public final int FULL_PAPER_TRAY = 250; // Can print 250 pages before refilling paper
    public final int SHEETS_PER_PACK = 50;

    public final int FULL_TONER_LEVEL = 500; // Can print 500 pages before replacing toner
    public final int MINIMUM_TONER_LEVEL = 10;
    public final int PAGES_PER_TONER_CARTRIDGE = 500;


    // Technician methods

    void replaceTonerCartridge(int attempt, String technicianName);

    void refillPaper(int attempt, String technicianName);


} // ServicePrinter

