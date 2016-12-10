package com.vroozi.customerui.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelWriter {

  private static Logger LOG = LoggerFactory.getLogger(ExcelWriter.class);

  OutputStream outputStream = null;
  XSSFWorkbook workbook = null;
  XSSFSheet dataSheet = null;
  String fileName = "";
  private int rows = 0;
  private boolean dataChanged = false;

  public ExcelWriter(String path, String sheetName) {
    workbook = new XSSFWorkbook();
    dataSheet = workbook.createSheet(sheetName);
    fileName = path;
    try {
      outputStream = new FileOutputStream(fileName);
    } catch (Exception ex) {
      LOG.error("ERROR WHILE CREATING EXCEL SHEET", ex);
    }
  }

  public ExcelWriter(OutputStream outputStream, String sheetName) {
    workbook = new XSSFWorkbook();
    dataSheet = workbook.createSheet(sheetName);
    this.outputStream = outputStream;
  }

  public void writeHeader(String[] columns) {
    try {
      XSSFRow headerRow = dataSheet.createRow(rows++);
      XSSFCell headerCell = null;
      int index = 0;
      for (String columnName : columns) {
        headerCell = headerRow.createCell(index++);
        headerCell.setCellValue(columnName);
        dataSheet.autoSizeColumn(index-1);
      }
    } catch (Exception e) {
      LOG.error("ERROR WHILE WRITING EXCEL SHEET", e);
    }
  }

  public void writeRow(String[] columns) {
    try {
      XSSFRow dataRow = dataSheet.createRow(rows++);
      XSSFCell dataCell = null;
      int index = 0;
      for (String columnName : columns) {
        dataCell = dataRow.createCell(index++);
        dataCell.setCellValue(columnName);
      }
      dataChanged = true;
    } catch (Exception e) {
      LOG.error("ERROR WHILE WRITING EXCEL SHEET", e);
    }
  }

  public void flushFile(boolean closeOutputStream) {
    if (dataChanged) {
      try {
        workbook.write(outputStream);
      } catch (Exception ex) {
        LOG.error("ERROR WHILE FLUSHING EXCEL SHEET", ex);
      } finally {
        try {
          if (outputStream != null && closeOutputStream) {
            outputStream.close();
          }
        } catch (IOException ex) {
          LOG.error("ERROR WHILE FLUSHING EXCEL SHEET", ex);
        }
      }
    }
  }
}
