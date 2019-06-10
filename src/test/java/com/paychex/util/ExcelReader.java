package com.paychex.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.testng.Assert;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class ExcelReader {

    private Cell openCell;
    private Row openRow;
    private Sheet openSheet;
    private String data;
    private Workbook openWorkbook;

    /**
     * Creates an Excel reader object and open the said workbook at the specified path.
     *
     * @param excelPath - The path of the excel file
     */
    public ExcelReader(String excelPath) {
        this(new File(excelPath));
    }

    /**
     * Creates an Excel reader object and open the said workbook at the specified path.
     *
     * @param excelFile - File object of the excel file
     */
    public ExcelReader(File excelFile) {
        try {
            InputStream inp = new FileInputStream(excelFile);
            if (isXlsx(excelFile))
                openWorkbook = new XSSFWorkbook(inp);
            else
                openWorkbook = new HSSFWorkbook(inp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens the sheet at the specified number for reading
     *
     * @param sheetNo
     */
    public void openSheet(int sheetNo) {
        try {
            openSheet = openWorkbook.getSheetAt(sheetNo);

        } catch (Exception e) {
            Assert.fail("Unable to open the sheet and the cause is ");
        }
    }

    /**
     * Open sheet with the specified name
     *
     * @param sheetName
     */
    public void openSheet(String sheetName) {
        try {
            openSheet = openWorkbook.getSheet(sheetName);

        } catch (Exception e) {
            Assert.fail("Unable to open the sheet and the cause is ");
        }
    }

    /**
     * Gets current sheet name
     *
     * @param sheetNo - Index of sheet
     * @return - Sheet name
     */
    public String getCurrentSheetName(int sheetNo) {
        try {
            openSheet = openWorkbook.getSheetAt(sheetNo);
            return openSheet.getSheetName();

        } catch (Exception e) {
            Assert.fail("Unable to open the sheet and the cause is ");
            return null;
        }
    }

    /**
     * Gets number of sheets in the given workbook
     *
     * @return - number of sheets in the workbook
     */
    public int getNoOfSheets() {
        try {
            return openWorkbook.getNumberOfSheets();
        } catch (Exception e) {
            Assert.fail("Unable to get the number of sheets and the cause is ");
            return 0;
        }
    }

    /**
     * Gets the data from the currently opened sheet present on the specified row and column
     *
     * @param column
     * @param row
     * @return - Respective data in string format. "" if the said row -column does not exist
     */
    public String getData(int column, int row) {
        try {
            openRow = openSheet.getRow(row);
            openCell = openRow.getCell(column);
            int cellType = openCell.getCellType();
            data = getDataInCell(cellType);

            if (data == null) {
                data = "";
            }
            return data;
        } catch (Exception e) {
            if (openRow == null || openCell == null || data == null) {
                data = "";
                return data;
            } else {
                System.out.println(e);
                return "";
            }
        }
    }

    /**
     * Returns all the rows data
     *
     * @return
     */
    public HashMap<Integer, HashMap> readRows() {
        HashMap rowsData = new HashMap<Integer, HashMap>();
        for (int i = 1; i <= openSheet.getLastRowNum(); i++) {
            rowsData.put(i, readRow(i));
        }
        return rowsData;
    }

    /**
     * Returns said row data
     *
     * @return
     */
    public HashMap readRow(int num) {
        HashMap rowValues;
        Row rowHeader;
        rowHeader = openSheet.getRow(0);
        openRow = openSheet.getRow(num);
        rowValues = new HashMap();
        for (int i = 0; i < openRow.getPhysicalNumberOfCells(); i++) {
            openCell = openRow.getCell(i);
            rowValues.put(rowHeader.getCell(i).toString(), getDataInCell(openRow.getCell(i).getCellType()));
        }
        return rowValues;
    }

    /**
     * Returns the data in cell
     *
     * @return
     */
    public String getDataInCell(int cellType) {
        try {
            switch (cellType) {
                case 0:
                    if (DateUtil.isCellDateFormatted(openCell)) {
                        Date dt = openCell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "dd MM yyyy HH:mm:ss");
                        data = sdf.format(dt);
                    } else
                        data = Long.toString(Math.round(openCell
                                .getNumericCellValue()));
                    break;
                case 1:
                case 2:
                case 3:
                    data = openCell.getRichStringCellValue().getString();
                    break;
                case 4:
                    data = Boolean.toString(openCell.getBooleanCellValue());
                    break;
                case 5:
                    data = Byte.toString(openCell.getErrorCellValue());
                    break;
                default:
                    data = openCell.getRichStringCellValue().getString();
                    if (data == null) {
                        data = "";
                    }
                    return data;
            }
        } catch (Exception e) {
            if (openRow == null || openCell == null || data == null) {
                data = "";
                return data;
            } else {
                System.out.println(e);
                return "";
            }
        }
        return data;
    }

    /**
     * Number of rows in the said sheet.
     *
     * @return
     */
    public int getNoOfRows() {
        return (openSheet.getLastRowNum() + 1);
    }

    /**
     * Number of columns in the first row of the sheet
     *
     * @return
     */
    public int getNoOfColumn() {
        Row rw = openSheet.getRow(0);
        if (rw == null)
            return 0;
        return rw.getLastCellNum();
    }

    /**
     * Number of columns of a particular row number that is specified
     *
     * @param rowNumber
     * @return
     */
    public int getNoOfColumn(int rowNumber) {
        Row rw = openSheet.getRow(rowNumber);
        if (rw == null)
            return 0;
        return rw.getLastCellNum();
    }

    /**
     * Gets the current open sheet name
     *
     * @return
     */
    public String getSheetName() {
        return openSheet.getSheetName();
    }

    /**
     * Returns true or false depending upon whether file name ends with xlsx
     *
     * @param fl
     * @return
     */
    private boolean isXlsx(File fl) {
        String fileName = fl.getName();
        if (fileName.endsWith("xlsx"))
            return true;
        return false;
    }

    /**
     * Returns the count of Merged Regions.
     *
     * @return
     */
    private int getNumMergedRegions() {
        return openSheet.getNumMergedRegions();
    }

    /**
     * Returns the cell address of Merged Region based on position.
     *
     * @return
     */
    private CellRangeAddress getMergedRegionAt(int position) {
        return openSheet.getMergedRegion(position);
    }


    public static void main(String[] args) {
        ExcelReader a = new ExcelReader("C:\\Users\\pradeeps\\Downloads\\OFFICIAL - APIs that R Uses.xlsx");
        a.openSheet(1);
        //System.out.println(a.getData(1, 5));
       /*HashMap k = a.readRow(5);
       System.out.println(k.get("Product Type") + "..............." + k.get("API Name"));*/


        HashMap<Integer, HashMap> k = a.readRows();
        for (int i = 1; i <= k.size(); i++) {
            System.out.println(k.get(i).get("Product Type") + "..............." + k.get(i).get("API Name"));
        }
    }

}
