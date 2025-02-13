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
		List<String> cnpjs = new ArrayList<>();
		cnpjs.add("1");
		cnpjs.add("2");

        try {
			// criar a pasta de relatórios se não existir
			FileManager.createDownloadsFolder(downloadsPath);

			for (int i = 0; i < cnpjs.size(); i++) {
				String cnpj = cnpjs.get(i);
				SiteAutomation.downloadReport(cnpj, i + 1, downloadsPath);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

}
