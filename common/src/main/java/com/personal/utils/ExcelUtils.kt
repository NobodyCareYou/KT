package com.personal.utils

import android.util.Log
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class ExcelUtils {

    companion object {
        private const val TAG = "ExcelUtils"

        fun readExcel(file: File) {
            try {
                if (!file.exists()) {
                    throw Exception(" 'file' not found ")
                }
                if (!file.isFile) {
                    throw Exception(" 'file' not af file ")
                }

                val workbook = WorkbookFactory.create(file)
                val numberOfSheets = workbook.numberOfSheets
                for (index in 0..numberOfSheets) {
                    val sheet = workbook.getSheetAt(index)
                    sheet.forEach { row ->
                        for (cell in row) {
                            when (cell.cellType) {
                                Cell.CELL_TYPE_NUMERIC -> print(cell.numericCellValue)
                                Cell.CELL_TYPE_STRING -> print(cell.stringCellValue)
                                Cell.CELL_TYPE_FORMULA -> print("FORMULA")
                                Cell.CELL_TYPE_BLANK -> break
                                Cell.CELL_TYPE_BOOLEAN -> print(cell.booleanCellValue)
                                Cell.CELL_TYPE_ERROR -> print("error")
                                else -> print("")
                            }
                            println()
                        }
                    }
                    workbook.cloneSheet(index)
                }

            } catch (e: Exception) {
                e.message?.let { Log.e(TAG, it) }
            }
        }

        fun writeExcel(sheetName: String, exportPath: String, execute: (sheet: Sheet) -> Unit) {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet(sheetName)
            execute(sheet)
            val fos = FileOutputStream(exportPath)
            workbook.write(fos)
            workbook.cloneSheet(workbook.getSheetIndex(sheet))
            fos.close()
        }


    }
}
