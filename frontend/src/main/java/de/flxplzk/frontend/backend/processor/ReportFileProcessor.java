package de.flxplzk.frontend.backend.processor;

import de.flxplzk.frontend.backend.domain.Transaction;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static de.flxplzk.frontend.backend.util.Printer.EMPLOYEE_STRING_PRINTER;
import static de.flxplzk.frontend.backend.util.Printer.WORKING_TIME_PRINTER;

public class ReportFileProcessor implements Processor<List<Transaction>, ByteArrayOutputStream> {

    private static final int EMPLOYEE_COLUMN_INDEX = 0;
    private static final String EMPLOYEE_COLUMN_HEADING = "Mitarbeiter";

    private static final int TRANSACTION_DATE_COLUMN_INDEX = 1;
    private static final String TRANSACTION_DATE_COLUMN_HEADING = "Arbeitstag";

    private static final int STARTING_HOUR_COLUMN_INDEX = 2;
    private static final String STARTING_HOUR_COLUMN_HEADING = "Schichtbeginn";

    private static final int ENDING_HOUR_COLUMN_INDEX = 3;
    private static final String ENDING_HOUR_COLUMN_HEADING = "Schichtende";

    private static final int BREAK_COLUMN_INDEX = 4;
    private static final String BREAK_COLUMN_COLUMN_HEADING = "Pause in Minuten";

    private static final int WORKING_TIME_COLUMN_INDEX = 5;
    private static final String WORKING_TIME_COLUMN_HEADING = "geleistete Arbeitszeit";

    @Override
    public ByteArrayOutputStream process(List<Transaction> input) {
        int rowIndex = 0;
        XSSFWorkbook tempWorkBook = new XSSFWorkbook();
        XSSFSheet workSheet = tempWorkBook.createSheet("Arbeitszeitliste");
        XSSFRow headerRow = workSheet.createRow(rowIndex);
        buildHeaderRow(headerRow);
        for (Transaction transaction : input) {
            XSSFRow contentRow = workSheet.createRow(++rowIndex);
            contentRow.createCell(EMPLOYEE_COLUMN_INDEX).setCellValue(EMPLOYEE_STRING_PRINTER.print(transaction.getEmployee()));
            contentRow.createCell(TRANSACTION_DATE_COLUMN_INDEX).setCellValue(DateTimeFormatter.ISO_LOCAL_DATE.format(transaction.getTransactionDate()));
            contentRow.createCell(STARTING_HOUR_COLUMN_INDEX).setCellValue(DateTimeFormatter.ISO_TIME.format(transaction.getStart()));
            contentRow.createCell(ENDING_HOUR_COLUMN_INDEX).setCellValue(DateTimeFormatter.ISO_TIME.format(transaction.getStart()));
            contentRow.createCell(BREAK_COLUMN_INDEX).setCellValue(transaction.getMinutesBreak());
            contentRow.createCell(WORKING_TIME_COLUMN_INDEX).setCellValue(WORKING_TIME_PRINTER.print(transaction.getAmount()));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            tempWorkBook.write(outputStream);
        } catch (IOException e) {
            throw new ReportFileCreationErrorException();
        }
        return outputStream;
    }

    private void buildHeaderRow(XSSFRow headerRow) {
        headerRow.createCell(EMPLOYEE_COLUMN_INDEX).setCellValue(EMPLOYEE_COLUMN_HEADING);
        headerRow.createCell(TRANSACTION_DATE_COLUMN_INDEX).setCellValue(TRANSACTION_DATE_COLUMN_HEADING);
        headerRow.createCell(STARTING_HOUR_COLUMN_INDEX).setCellValue(STARTING_HOUR_COLUMN_HEADING);
        headerRow.createCell(ENDING_HOUR_COLUMN_INDEX).setCellValue(ENDING_HOUR_COLUMN_HEADING);
        headerRow.createCell(BREAK_COLUMN_INDEX).setCellValue(BREAK_COLUMN_COLUMN_HEADING);
        headerRow.createCell(WORKING_TIME_COLUMN_INDEX).setCellValue(WORKING_TIME_COLUMN_HEADING);
    }

    private class ReportFileCreationErrorException extends RuntimeException {
    }
}
