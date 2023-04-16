package com.student.APIAutomationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
    Workbook xlWorkBook;
    Sheet xlSheet;
    int headerRowNumber = 0;//UFT Changes
    int rowNo, headerRow;
    int ColStartNo=0;
    boolean bCompleteNumericVal;

    public boolean isGetCompleteNumericVal() {
        return bCompleteNumericVal;
    }

    public void setGetCompleteNumericVal(boolean bCompleteNumericVal) {
        this.bCompleteNumericVal = bCompleteNumericVal;
    }

    /* Constructor Started */
    public ExcelUtil() {

    }

    public ExcelUtil(String excelPathWithName){
        setWorkBook(excelPathWithName);
        setHeaderRow(headerRow);
    }

    public ExcelUtil(String excelPathWithName, String sheetName) {
        setSheet(excelPathWithName, sheetName);
        setHeaderRow(headerRow);
    }

    public ExcelUtil(String excelPathWithName, int sheetNo) {
        setSheet(excelPathWithName, sheetNo);
        setHeaderRow(headerRow);
    }

    /* Constructor Completed */

    public Workbook getWorkBook(String excelPathWithName)  {
        Workbook xlWorkBook=null;
        try {
            if (FilenameUtils.getExtension(excelPathWithName).length()==4) {
                xlWorkBook = new XSSFWorkbook(excelPathWithName);
//                InputStream is = new FileInputStream(new File(excelPathWithName));
//                xlWorkBook= StreamingReader.builder().rowCacheSize(200).bufferSize(4096).open(is);
            } else {
                xlWorkBook = new HSSFWorkbook();

            }
        }catch(Exception e){
        }
        return xlWorkBook;
    }

    public Workbook getWorkBook()  {
        return this.xlWorkBook;
    }

    public void setWorkBook(String excelPathWithName) {
        try {
            this.xlWorkBook = getWorkBook(excelPathWithName);
        }catch(Exception e){
        }
    }

    public Sheet getSheet(String sheetName) {
        return this.xlWorkBook.getSheet(sheetName);

    }
    public List<String> getAllSheetNames() {
        List<String> sheetNames = new ArrayList<String>();
        for (int i=0; i<xlWorkBook.getNumberOfSheets(); i++) {
            sheetNames.add(xlWorkBook.getSheetName(i));
        }
        return sheetNames;

    }

    public Sheet getSheet(int sheetNo) {
        return this.xlWorkBook.getSheetAt(sheetNo);

    }
    public Sheet getSheet() {
        return this.xlSheet;

    }

    public void setSheet(String excelPathWithName, String sheetName) {
        this.xlWorkBook = getWorkBook(excelPathWithName);
        this.xlSheet = getSheet(sheetName);
    }

    public void setSheet(String excelPathWithName, int sheetNo) {
        this.xlWorkBook = getWorkBook(excelPathWithName);
        this.xlSheet = getSheet(sheetNo);
    }
    public void setSheet(String sheetName) {
        this.xlSheet = getSheet(sheetName);
    }

    public boolean existSheet(String sheetName) {
        boolean sheetExist=false;
        try{
            Sheet sheet=this.xlWorkBook.getSheet(sheetName);
            if (sheet!=null)
                sheetExist=true;
            else
                sheetExist=false;
        }catch(Exception e){
            sheetExist=false;
        }
        return sheetExist;

    }

    // *****************************************************************************************************//
    /* Get specific Row */

    public Row getRow(String sheetName, int rowNo){
        return this.getSheet(sheetName).getRow(rowNo);
    }

    public Row getRow(Sheet sheet, int rowNo) {
        return sheet.getRow(rowNo);
    }

    public Row getRow(int rowNo)  {
        return this.xlSheet.getRow(rowNo);
    }

    /* Get all Rows */

    public List<Row> getRow(Sheet sheet) {
        List<Row> rowArrayList = new ArrayList<Row>();
        for (int currentRow = getHeaderRow(); currentRow <= this.getRowCount(sheet); currentRow++)
            rowArrayList.add(sheet.getRow(currentRow));
        return rowArrayList;
    }
    public List<Row> getRow() {
        List<Row> rowArrayList = new ArrayList<Row>();
        for (int currentRow = getHeaderRow(); currentRow <= this.getRowCount(xlSheet); currentRow++)
            rowArrayList.add(xlSheet.getRow(currentRow));
        return rowArrayList;
    }
//	// *****************************************************************************************************//

    public String getCellData(String sheetName, int rowNo, int colNo) {
        return getCellVal(this.getSheet(sheetName).getRow(rowNo).getCell(colNo));
    }

    public String getCellData(Sheet sheet, int rowNo, int colNo)  {
        return getCellVal(sheet.getRow(rowNo).getCell(colNo));
    }

    public HashMap getCellData(Row row) {
        HashMap <String, String> rowData= new HashMap<String, String>();
        // return getCellVal(row.getCell(colNo));
        for (int currentCol=0; currentCol<row.getLastCellNum(); currentCol++) {
            rowData.put(getCellData(headerRow, currentCol), getCellVal(row.getCell(currentCol)));
        }
        return rowData;
    }

    public String getCellData(int rowNo, int colNo)  {
        return getCellVal(this.xlSheet.getRow(rowNo).getCell(colNo));
    }

    public String getCellData(Row row, int colNo) {
        // return getCellVal(row.getCell(colNo));
        return getCellVal(row.getCell(colNo));
    }

    public String getCellData(String sheetName, int rowNo, String colName) {
        Sheet sheet = getSheet(sheetName);
        int colNo = this.getColHeaderNo(sheet, colName);
        return getCellVal(this.getSheet(sheetName).getRow(rowNo).getCell(colNo));
    }

    public String getCellData(Sheet sheet, int rowNo, String colName){
        int colNo = this.getColHeaderNo(sheet, colName);
        return getCellVal(sheet.getRow(rowNo).getCell(colNo));
    }

    public String getCellData(int rowNo, String colName){
        int colNo = this.getColHeaderNo(this.xlSheet, colName);
        return getCellVal(this.xlSheet.getRow(rowNo).getCell(colNo));
    }

    public String getCellData(int rowNo, String colName, boolean colLike){
        int colNo = this.getColHeaderNo(this.xlSheet, colName, colLike);
        return getCellVal(this.xlSheet.getRow(rowNo).getCell(colNo));
    }

    public String getCellData(String sheetName, Row row, String colName){
        int colNo = this.getColHeaderNo(getSheet(sheetName), colName);
        return getCellVal(row.getCell(colNo));
    }

    public String getCellData(Row row, String colName) {
        try {
            int colNo = this.getColHeaderNo(row.getSheet(), colName);
//			int colNo = this.getColHeaderNo(this.xlSheet, colName);
            return getCellVal(row.getCell(colNo));
        } catch (Exception e) {
            return null;
        }

    }

    public String getCellData(Row row, String colName, boolean colLike) {
        try {
            int colNo = this.getColHeaderNo(row.getSheet(), colName, colLike);
            return getCellVal(row.getCell(colNo));
        } catch (Exception e) {
            return null;
        }

    }
    // get Cell data based on row No and column no
    public String getCellVal(Cell col) {
        String cellVal = "";
        CellType cell_Type = col.getCellType();
        switch (cell_Type) {
            case NUMERIC:
                if (isGetCompleteNumericVal())
                    cellVal = String.valueOf(col);
                else
                    cellVal = new DataFormatter().formatCellValue(col);
                break;
            case STRING:
                cellVal = col.getStringCellValue();
                break;
            case FORMULA:
                // cellVal = col.getStringCellValue();
                switch (col.getCachedFormulaResultType()) {
                    case NUMERIC:
                        if (bCompleteNumericVal)
                            cellVal = String.valueOf(col);
                        else
                            cellVal = new DataFormatter().formatCellValue(col);
                        // FormulaEvaluator formulaEval =
                        // xlsWorkbook.getCreationHelper().createFormulaEvaluator();
                        // cellVal=formulaEval.evaluate(col).getNumberValue();
                        // cellVal=this.formulaEval.evaluateInCell(col).getCellFormula();
                        break;
                    case STRING:
                        cellVal = col.getStringCellValue();
                        break;
                    case ERROR:
                        cellVal = Byte.toString(col.getErrorCellValue());
                        break;
                    case BOOLEAN:
                        cellVal = Boolean.toString(col.getBooleanCellValue());
                        break;
                    case BLANK:
                        cellVal = "";
                        break;
                    default:
                        System.out.print("Report to automation team need to implement the logic for the cell type in formula:"
                                + col.getCachedFormulaResultType());
                        break;
                }
                break;
            case BLANK:
                cellVal = "";
                break;
            case BOOLEAN:
                cellVal = Boolean.toString(col.getBooleanCellValue());
                break;
            case ERROR:
                cellVal = Byte.toString(col.getErrorCellValue());
                System.out.println("formula has an error");
                break;
            default:
                System.out.print("Report to automation team need to implement the logic for the cell type:" + cell_Type);
                break;
        }
        return cellVal;
    }


    public int getColHeaderNo(Sheet sheet, String colName) {
        int colNo = -1;
        int totCol = getColCount(sheet, headerRow);
        int currentColNo;
        for (currentColNo = 0; currentColNo < totCol; currentColNo++) {
            if (getCellData(sheet, headerRow, currentColNo).equalsIgnoreCase(colName)) {
                colNo = currentColNo;
                break;
            } else if (currentColNo == (totCol - 1)) {
                System.out.println("Column header not found in excel file: " + colName);
            }
        }
        return colNo;
    }

    public int getColHeaderNo(Sheet sheet, String colName, boolean colLike) {
        int colNo = -1;
        int totCol = getColCount(sheet, headerRow);
        int currentColNo;
        for (currentColNo = 0; currentColNo < totCol; currentColNo++) {

            if (colLike){
                if (getCellData(sheet, headerRow, currentColNo).contains(colName)) {
                    colNo = currentColNo;
                    break;
                } else if (currentColNo == (totCol - 1)) {
                    System.out.println("Column header not found in excel file: " + colName);
                }
            }
            else{
                if (getCellData(sheet, headerRow, currentColNo).equalsIgnoreCase(colName)) {
                    colNo = currentColNo;
                    break;
                } else if (currentColNo == (totCol - 1)) {
                    System.out.println("Column header not found in excel file: " + colName);
                }
            }
        }
        return colNo;
    }

    public int getColHeaderNo(String sheetName, String colName) {
        Sheet sheet = getSheet(sheetName);
        int colNo = -1;
        int totCol = getColCount(sheet, headerRow);
        int currentColNo;
        for (currentColNo = 0; currentColNo <= totCol; currentColNo++) {
            if (getCellData(sheet, headerRow, currentColNo).equalsIgnoreCase(colName)) {
                colNo = currentColNo;
                break;
            } else if (currentColNo == totCol) {
                System.out.println("Column header not found in excel file: " + colName);
            }
        }
        return colNo;
    }

    public int getColHeaderNo(String colName) {
        int colNo = -1;
        int totCol = getColCount(this.xlSheet, headerRow);
        int currentColNo;
        for (currentColNo = 0; currentColNo < totCol; currentColNo++) {
            if (getCellData(this.xlSheet, headerRow, currentColNo).equalsIgnoreCase(colName)) {
                colNo = currentColNo;
                break;
            } else if (currentColNo == (totCol - 1)) {
                System.out.println("Column header not found in excel file: " + colName);
            }
        }
        return colNo;
    }
    // get total columns of specific column of specific sheet sheet
    public int getColCount(Sheet sheet, int rowNo) {
        return sheet.getRow(rowNo).getLastCellNum();
    }

    public int getColCount(int rowNo) {
        return this.xlSheet.getRow(rowNo).getLastCellNum();
    }

    // get total rows of specific sheet
    public int getRowCount(Sheet sheet) {
        return sheet.getLastRowNum();
    }

    public int getRowCount(String sheetName) {
        return getSheet(sheetName).getLastRowNum();
    }

    public int getRowCount() {
        return this.xlSheet.getLastRowNum();
    }

    public List<Cell> getColumnSpecificValue(String colHeader) {//UFT Changes
        List<Cell> columnValue = new ArrayList();
        boolean flag = false;
        int firstRowNum = this.headerRowNumber;
        int lastRowNum = this.xlSheet.getLastRowNum();
        Row headerRow = this.xlSheet.getRow(firstRowNum);

        for(int i = headerRow.getFirstCellNum(); i < headerRow.getLastCellNum(); ++i) {
            if (headerRow.getCell(i).getStringCellValue().equalsIgnoreCase(colHeader)) {
                flag = true;

                for(int j = firstRowNum + 1; j <= lastRowNum; ++j) {
                    columnValue.add(this.xlSheet.getRow(j).getCell(i));
                }
            }
        }

        if (flag) {
            return columnValue;
        } else {
            System.out.println("No such Column Header");
            return columnValue;
        }
    }

    public List<Row> filterRow(Sheet sheet, String columnNameToFilter, String criteria) {
        List<Row> row = getRow(sheet);
        return row.stream().filter(Objects::nonNull).filter(currentRow -> {try{return getCellData(currentRow, columnNameToFilter).equals(criteria);}catch(Exception e){return false;}}).collect(Collectors.toList());
    }

    public List<Row> filterRow(Sheet sheet, String columnNameToFilter, boolean colLike, String criteria) {
        List<Row> row = getRow(sheet);
        return row.stream().filter(Objects::nonNull).filter(currentRow ->{try{return getCellData(currentRow, columnNameToFilter, colLike).equals(criteria);}catch(Exception e){return false;}}).collect(Collectors.toList());
    }

    public List<Row> filterPartialMatchedRecord(Sheet sheet, String columnNameToFilter, boolean colLike, String criteria) {
        List<Row> row = getRow(sheet);
        return row.stream().filter(Objects::nonNull).filter(currentRow ->{try{return getCellData(currentRow, columnNameToFilter, colLike).contains(criteria);}catch(Exception e){return false;}}).collect(Collectors.toList());
    }

    public List<Row> filterPartialMatchRecord(Sheet sheet, String columnNameToFilter, String criteria) {
        List<Row> row = getRow(sheet);
        return row.stream().filter(Objects::nonNull).filter(currentRow -> {try{return getCellData(currentRow, columnNameToFilter).contains(criteria);}catch(Exception e){return false;}}).collect(Collectors.toList());
    }

    public List<Row> filterRow(List<Row> row, String columnNameToFilter, String criteria) {
//        return row.stream().filter(currentRow -> getCellData(currentRow, columnNameToFilter).equals(criteria)).collect(Collectors.toList());
        return row.stream().filter(Objects::nonNull).filter(currentRow -> {try{return getCellData(currentRow, columnNameToFilter).equals(criteria);}catch(Exception e){return false;}}).collect(Collectors.toList());
    }

    public void setHeaderRow(int rowNoIndex){
        headerRow=rowNoIndex;
    }

    public int getHeaderRow(){
        return headerRow;
    }

    public String getCellDataBasedOnRef(String cellRef){
        String cellData="";
        CellReference cr= new CellReference(cellRef);
        Row row = this.xlSheet.getRow(cr.getRow());
        Cell cell = row.getCell(cr.getCol());
        cellData= getCellVal(cell);
        return cellData;
    }

    public int getColNoBasedOnRef(String cellRef){
        try {
            CellReference cr = new CellReference(cellRef);
            Row row = this.xlSheet.getRow(cr.getRow());
            Cell cell = row.getCell(cr.getCol());
            return cell.getColumnIndex();
        }catch(Exception e){
            return -1;
        }
    }
    public void shiftCol(int noOfColToShift){
        getSheet().shiftColumns(noOfColToShift, getSheet().getRow(headerRow).getLastCellNum() - 1, -noOfColToShift);
    }
}
