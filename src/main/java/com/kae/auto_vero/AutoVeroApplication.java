package com.kae.auto_vero;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AutoVeroApplication {

	public static void main(String[] args) {

		String basePath = System.getProperty("user.dir");
		String excelPath = Paths.get(basePath, "cnpjs", "cnpjs.xlsx").toString();
		String downloadsPath = Paths.get(basePath, "relatorios").toString();

		System.out.println(basePath);

        try {
			// criar a pasta de relatórios se não existir
			FileManager.createDownloadsFolder(downloadsPath);

			// ler cnpjs do excel
			List<String> cnpjs = ExcelReader.readCnpjsFromExcel(excelPath);

			SiteAutomation.downloadReport(cnpjs, downloadsPath);


		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

}
