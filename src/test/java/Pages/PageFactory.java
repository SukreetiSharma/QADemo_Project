package Pages;

import org.openqa.selenium.WebDriver;

public class PageFactory {
    WebDriver driver;
    private VerifyBookStore HomePage;

    public PageFactory(WebDriver driver){
        this.driver = driver;
    }

    public VerifyBookStore getHomePage(){
        if(HomePage == null){
            HomePage = new VerifyBookStore(driver);
        }
        return HomePage;
    }
}
