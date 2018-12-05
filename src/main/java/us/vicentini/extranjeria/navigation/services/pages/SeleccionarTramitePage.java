package us.vicentini.extranjeria.navigation.services.pages;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
class SeleccionarTramitePage extends BasePageNavigation {
    SeleccionarTramitePage(HtmlPage htmlPage, WebClient webClient, NavigationFactory navigationFactory) {
        super(htmlPage, webClient, navigationFactory);
    }


    @Override
    protected void navigateImpl() throws IOException {
        log.info("Start Seleccionar Tramite");
        skipFirstDialog();

        HtmlSelect regionesST = (HtmlSelect) htmlPage.getElementById("cmbRegionesST");
        HtmlOption selectedOptionRegiones = regionesST.getOptionByText("REGION METROPOLITANA DE SANTIAGO");
        htmlPage = (HtmlPage) selectedOptionRegiones.setSelected(true);

        HtmlSelect comunasST = (HtmlSelect) htmlPage.getElementById("cmbComunasST");

        HtmlOption selectedOptionComunas = null;
        while (selectedOptionComunas == null) {
            try {
                selectedOptionComunas = comunasST.getOptionByText("LAS CONDES");
                htmlPage = (HtmlPage) selectedOptionComunas.setSelected(true);
            } catch (ElementNotFoundException ex) {
                log.debug("element not found... waiting");
                webClient.waitForBackgroundJavaScript(60000);
            }
        }

        HtmlSelect tramitesDisponiblesST = (HtmlSelect) htmlPage.getElementById("cmbTramitesDisponiblesST");
        HtmlOption selectedOptionTramite =
                tramitesDisponiblesST.getOptionByText("(PDI) Certificado de vigencia Permanencia Definitiva");
        htmlPage = (HtmlPage) selectedOptionTramite.setSelected(true);

        HtmlButton nextPageButton = (HtmlButton) htmlPage.getElementById("btnSiguienteST");
        htmlPage = nextPageButton.click();
        log.info("Finish Seleccionar Tramite");
    }


    private void skipFirstDialog() throws IOException {
        List<HtmlButton> htmlButtons = htmlPage.getByXPath(
                "//button[@class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only " +
                "ui-state-focus']");
        while (htmlButtons.isEmpty()) {
            log.debug("dialog button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButtons = htmlPage.getByXPath(
                    "//button[@class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only " +
                    "ui-state-focus']");
        }
        htmlPage = htmlButtons.iterator().next().click();
    }


    @Override
    public PageNavigationStrategy next() {
        return navigationFactory.getAntecedentesGeneralesPage(htmlPage, webClient);
    }
}
