package com.fedex.smartpost.spark.deliverycost.util

import java.io.File
import java.io.FileOutputStream
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.DataFormatter
import java.io.InputStream
import java.io.OutputStream

object ExcelUtils {
  def convertXlsToCsv(inputFile: InputStream, outputFiles: Array[OutputStream]) = {
    if (!outputFiles.isEmpty) {    
      var data: StringBuffer = new StringBuffer()
      var sheetsToRead = outputFiles.size
      
      // Emulate Excel formatting
      val dataFormatter = new DataFormatter(true)
      
      val workbook = new XSSFWorkbook(inputFile)
      
      if (sheetsToRead > workbook.getNumberOfSheets) {
        sheetsToRead = workbook.getNumberOfSheets()
      }
      
      for (i <- 0 until sheetsToRead) {
        val tab = workbook.getSheetAt(i)
        
        val rows = tab.iterator()
        while (rows.hasNext()) {
          val row = rows.next()
          
          val cells = row.cellIterator()
          while (cells.hasNext()) {
            val cell = cells.next()
            data.append(dataFormatter.formatCellValue(cell) + ",")
          }
          data.append("\n")
        }
        
        val stream = outputFiles(i)
        stream.write(data.toString().getBytes)
        stream.close()
  
        data.setLength(0)
      }
    }
  }
}