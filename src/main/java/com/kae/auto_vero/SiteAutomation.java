package com.kae.auto_vero;

import com.microsoft.playwright.*;


public class SiteAutomation {
    public static void downloadReport(String cnpj, int index, String downloadsPath) {
        try (Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("http://verosfa.timbrasil.com.br/");


            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            browser.close();
        }
    }
}
