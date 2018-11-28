package us.vicentini.extranjeria.services;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j
@Service
public class ScenarioGenerator implements CommandLineRunner {
    public static final String START_PAGE = "https://reservahora.extranjeria.gob.cl/inicio.action";

    private volatile HtmlPage htmlPage;
    private volatile WebClient webClient;


    @Override
    public void run(String... args) throws IOException {
        log.info("Start!!!!");
        loginPage();
        bienvenidoPage();
        seleccionarTramitePage();
        antecedentesGeneralesPage();
        requisitosBasicosPage();
        datosUsuarioPage();
        reservaEnCalendarioPage();
    }


    private void bienvenidoPage() throws IOException {
        log.info("Start Bienvenido");
        HtmlAnchor reservarHora = (HtmlAnchor) htmlPage.getElementById("btnReservarHora");
        htmlPage = reservarHora.click();
        log.info("Finish Bienvenido");
    }


    private void loginPage() throws IOException {
        log.info("Start Login");
        htmlPage = webClient.getPage(START_PAGE);
        HtmlTextInput username = htmlPage.getElementByName("j_username");
        HtmlPasswordInput password = htmlPage.getElementByName("j_password");
        HtmlSubmitInput submit = htmlPage.getElementByName("Ingresar");
        username.setText("");
        password.setText("");
        htmlPage = submit.click();
        HtmlSpan myName = (HtmlSpan) htmlPage.getElementById("spanDetalle");
        log.info("logged: " + myName.asText());
        log.info("Finish Login");
    }


    private void reservaEnCalendarioPage() throws IOException {
        log.info("Start Reserva En Calendario");
        clickPreviousIfAvailable();
        do {
            checkAvailableTimes();
        } while (nextPage());
        log.info("Finish Reserva En Calendario");
    }


    private boolean nextPage() throws IOException {
        HtmlButton htmlButton = null;
        while (htmlButton == null) {
            log.debug("Next Button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButton = (HtmlButton) htmlPage.getElementById("btnNext");
        }
        if (!htmlButton.isDisabled()) {
            htmlPage = htmlButton.click();
            return true;
        }
        return false;
    }


    private void checkAvailableTimes() {
        List<HtmlDivision> htmlButtons = Collections.emptyList();
        while (htmlButtons.isEmpty()) {
            log.debug("div elements is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButtons = htmlPage.getByXPath(
                    "//div[@class='fc-event fc-event-vert fc-event-start fc-event-end']");
        }
        for (HtmlDivision htmlDivision : htmlButtons) {
            log.info(htmlDivision.asText());
        }
    }


    private void clickPreviousIfAvailable() throws IOException {
        HtmlButton htmlButton = getPreviousHtmlButton();
        while (!htmlButton.isDisabled()) {
            htmlPage = htmlButton.click();
            htmlButton = getPreviousHtmlButton();
        }
    }


    private HtmlButton getPreviousHtmlButton() {
        HtmlButton htmlButton = null;
        while (htmlButton == null) {
            log.debug("Previous Button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButton = (HtmlButton) htmlPage.getElementById("btnPrev");
        }
        return htmlButton;
    }


    private void datosUsuarioPage() throws IOException {
        log.info("Start Datos Usuario");

        List<HtmlButton> htmlButtons = Collections.emptyList();
        while (htmlButtons.isEmpty()) {
            log.debug("dialog button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButtons = htmlPage.getByXPath(
                    "//button[@class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']");
        }
        HtmlButton noButton = getDialogNoButton(htmlButtons);
        htmlPage = noButton.click();

        HtmlButton nextPageButton = null;
        for (int i = 0; nextPageButton == null && i < 10; i++) {
            log.debug("next button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            nextPageButton = (HtmlButton) htmlPage.getElementById("btnSiguienteDU");
        }
        if (nextPageButton != null) {
            htmlPage = nextPageButton.click();
        }


        log.info("Finish Datos Usuario");
    }


    private HtmlButton getDialogNoButton(List<HtmlButton> htmlButtons) {
        for (HtmlButton button : htmlButtons) {
            HtmlSpan innerSpan = (HtmlSpan) button.getFirstChild();
            if (innerSpan.asText().equals("No")) {
                return button;
            }
        }
        return null;
    }


    private void requisitosBasicosPage() throws IOException {
        log.info("Start Requisitos Basicos");

        HtmlRadioButtonInput opcionRadio1 = null;
        while (opcionRadio1 == null) {
            log.debug("radio button option is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            opcionRadio1 = (HtmlRadioButtonInput) htmlPage.getElementById("opcionRadio1");
        }
        htmlPage = (HtmlPage) opcionRadio1.setChecked(true);

        HtmlButton nextPageButton = (HtmlButton) htmlPage.getElementById("btnSiguienteRB");
        htmlPage = nextPageButton.click();

        log.info("Finish Requisitos Basicos");

    }


    private void antecedentesGeneralesPage() throws IOException {
        log.info("Start Antecedentes Generales");
        HtmlButton nextPageButton = null;
        while (nextPageButton == null) {
            log.debug("next button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            nextPageButton = (HtmlButton) htmlPage.getElementById("btnSiguienteAG");
        }
        htmlPage = nextPageButton.click();
        log.info("Finish Antecedentes Generales");
    }


    private void seleccionarTramitePage() throws IOException {
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


    @PostConstruct
    public void init() throws IOException {
//        webClient = new WebClient(BrowserVersion.FIREFOX_60);
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//        webClient.waitForBackgroundJavaScript(10000);
//        webClient.waitForBackgroundJavaScriptStartingBefore(5000);
//        webClient.setAjaxController(new AjaxController(){
//            @Override
//            public boolean processSynchron(HtmlPage page, WebRequest request, boolean async)
//            {
//                return true;
//            }
//        });
    }
}
