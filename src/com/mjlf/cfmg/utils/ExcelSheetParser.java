package com.mjlf.cfmg.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelSheetParser {

private Workbook workbook;//工作簿


public ExcelSheetParser(File file){
    //获取工作薄workbook
    try {
    	workbook = new XSSFWorkbook(new FileInputStream(file));
	} catch (IOException e) {
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}


public List getDatasInSheet(int sheetNumber){
  List<List> result = new ArrayList<List>();
 
  //获得指定的sheet
  XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(sheetNumber);
  //获得sheet总行数
  int rowCount = sheet.getLastRowNum();
  if(rowCount < 1){
    return result;
  }
 
  //遍历行row
  for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
    //获得行对象
    XSSFRow row = sheet.getRow(rowIndex);
    if(null != row){
      List<Object> rowData = new ArrayList<Object>();
      //获得本行中单元格的个数
      int cellCount = row.getLastCellNum();
      //遍历列cell
      for (short cellIndex = 0; cellIndex < cellCount; cellIndex++) {
        XSSFCell cell = row.getCell(cellIndex);
        //获得指定单元格中的数据
        Object cellStr = this.getCellString(cell);
       
        rowData.add(cellStr);
      }
      result.add(rowData);
    }
  }
 
  return result;
}


private Object getCellString(XSSFCell cell) {
  // TODO Auto-generated method stub
  Object result = null;
  if(cell != null){
    //单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
    int cellType = cell.getCellType();
    switch (cellType) {
    case HSSFCell.CELL_TYPE_STRING:
      result = cell.getRichStringCellValue().getString();
      break;
    case HSSFCell.CELL_TYPE_NUMERIC:
      result = cell.getNumericCellValue();
      break;
    case HSSFCell.CELL_TYPE_FORMULA:
      result = cell.getNumericCellValue();
      break;
    case HSSFCell.CELL_TYPE_BOOLEAN:
      result = cell.getBooleanCellValue();
      break;
    case HSSFCell.CELL_TYPE_BLANK:
      result = null;
      break;
    case HSSFCell.CELL_TYPE_ERROR:
      result = null;
      break;
    default:
      System.out.println("枚举了所有类型");
      break;
    }
  }
  return result;
}

//test
public static void main(String[] args) {
  File file = new File("E:\\SSS.xls");
  ExcelSheetParser parser = new ExcelSheetParser(file);
  List<List> datas = parser.getDatasInSheet(0);
  for (int i = 0; i < datas.size(); i++) {
    List row = datas.get(i);
    for (short j = 0; j < row.size(); j++) {
      Object value = row.get(j);
      String data = String.valueOf(value);
      System.out.println(data + "\t");
    }
    System.out.println();
  }
}
}