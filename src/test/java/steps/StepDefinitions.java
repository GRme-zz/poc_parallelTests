package steps;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	protected WebDriver wd;
	private String seleniumUrl = "http://127.0.0.1:4444/wd/hub";
	private DesiredCapabilities chromeCapabilities;
	private ChromeOptions chromeOptions;
	
	@Before
	public void before() throws MalformedURLException {
		
        this.chromeCapabilities = DesiredCapabilities.chrome();
        this.chromeCapabilities.setJavascriptEnabled(true);
        
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        Map<String, Object> contentSetting = new HashMap<String, Object>();

        contentSetting.put("multiple-automatic-downloads",1);

        prefs.put("download.prompt_for_download", "false");
        prefs.put("profile.default_content_settings", contentSetting);

        options.setExperimentalOption("prefs", prefs);
        this.chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        
        String locale = "de"; // "en"; // "fr";
        String locales = "de-DE,de"; // "en-US,en"; // "fr-FR,fr";
        
        this.chromeOptions = new ChromeOptions();
        this.chromeOptions.addArguments("--lang="+locale);
            
        // Change Chrome language preferences to german
        this.chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, this.chromeOptions);
        Map<String, String> chromePrefs = new HashMap<String,String>();
        chromePrefs.put("settings.language.preferred_languages", locales);
        chromePrefs.put("intl.accept_languages", locales);
        this.chromeOptions.setExperimentalOption("prefs", chromePrefs);
        this.chromeCapabilities.setCapability("chrome.prefs", chromePrefs);
        
        //wd = new RemoteWebDriver(new URL(System.getProperty("selenium-url", seleniumUrl)), this.chromeCapabilities, this.chromeCapabilities);
		wd = new RemoteWebDriver(new URL(System.getProperty("selenium-url", seleniumUrl)), this.chromeCapabilities);
	}
	
	@When("^the page \\[(.*?)\\] is loaded$")
	public void openPage(String url) throws Exception {
		wd.get(url);
		wd.close();
	}
		
	@After
	public void after() {
		wd.quit();
	}
}
