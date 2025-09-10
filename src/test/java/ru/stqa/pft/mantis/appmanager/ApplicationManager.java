package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
  private final Properties properties;
  WebDriver wd;

  private String browser;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));

    if (browser.equals("edge")) {
      wd = new EdgeDriver();
    } else if (browser.equals("chrome")) {
      wd = new ChromeDriver();
    } else if (browser.equals("firefox")) {
      wd = new FirefoxDriver();
    } else {
      throw new IllegalArgumentException("Unsupported browser: " + browser);
    }
    wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    wd.get(properties.getProperty("web.baseUrl"));
  }

  public void stop() {
    wd.quit();
  }


}
