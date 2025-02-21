package com.kae.auto_vero;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.openxmlformats.schemas.drawingml.x2006.main.ThemeDocument;

import java.util.List;


public class SiteAutomation {
    private static Browser browser;
    private static Page page;
    private static final String URL = "https://verosfa.timbrasil.com.br/spv_negociacao.asp";

    public static void downloadReport(List<String> cnpjs, String downloadsPath) {
        try {
            openBrowserAndNavigate(URL);

            for (String cnpj : cnpjs) {
                addNegotiation(cnpj);
                boolean alertMessage = checkAlertMessage();
                if(alertMessage){
//                    page.locator("#alerta_confirma_cancelar").click(new Locator.ClickOptions().setForce(true));
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    page.evaluate("document.querySelector('#alerta_confirma_cancelar').click()");
                    continue;
                }
                boolean haveAccess = checkAccess(cnpj);
            }


            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        } finally {
            browser.close();
        }
    }

    private static void openBrowserAndNavigate(String url) {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        page = context.newPage();
        page.navigate(url);

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        page.locator("a.eva_botao[onclick*='criar_negociacao']").click();
    }

    private static void addNegotiation(String cnpj){
//        Locator botaoAdicionarNegociacao = page.locator("a.eva_botao[onclick*='criar_negociacao']");
//        botaoAdicionarNegociacao.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Locator campoCNPJ = page.locator("#auto_complete_cnpj");
        campoCNPJ.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(1000));
        campoCNPJ.fill(cnpj);

        Locator razaoSocial = page.locator("#auto_complete_razao");
        razaoSocial.fill("BAJUI");

        Locator btnCreateNegotiation = page.locator("a.eva_botao[onclick*='ajax_negociacao']");
        btnCreateNegotiation.click();
    }

    private static boolean checkAlertMessage() {
        Locator alertMessage = page.locator("#alerta_confirma_texto");
        try {
            alertMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(3000));
            return true;
        } catch (TimeoutError e) {
            System.err.println("Mensagem de alerta não encontrada: " + e.getMessage());
            return false;
        }
    }

    private static boolean checkAccess(String cnpj) {

        Locator btnCancelar = page.locator("a.eva_botao[onclick*='dismiss_confirm']");
        btnCancelar.click();

        page.evaluate("document.querySelector('#alerta_confirma_ok').click()");

//        Locator generateReport = page.locator("a.eva_botao[href='SPV_redirect_fidel.asp']");
        Locator generateReport = page.getByText("Gerar relatório de fidelização");
        generateReport.click();


        return false;
    }
}
