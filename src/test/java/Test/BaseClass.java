package Test;

import Pages.PageFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {
    static WebDriver driver;
    public static Properties prop;

    protected static PageFactory pageFactory;

    @BeforeClass
    public static void setup(){
        try {
            prop = new Properties();
            FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "//src//test//java//Data//Data.properties");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(prop.getProperty("URL"));
        pageFactory = new PageFactory(driver);
    }

    @AfterClass
    public static void close(){
        driver.close();
    }
}
