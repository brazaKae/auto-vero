package com.kae.auto_vero;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static List<String> readCnpjsFromExcel(String filePath) throws IOException {
        List<String> cnpjs = new ArrayList<>();
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet){
            Cell cell = row.getCell(0);
            if (cell != null) {
                String cnpj = "";

                if(cell.getCellType() == CellType.STRING){
                    cnpj = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    cnpj = String.valueOf((long) cell.getNumericCellValue());
                }

                cnpj = cnpj.replaceAll("[^0-9]", "");

                if(cnpj.length() == 14){
                    cnpjs.add(cnpj);
                }
            }
        }

        workbook.close();
        file.close();
        return cnpjs;
    }
}
