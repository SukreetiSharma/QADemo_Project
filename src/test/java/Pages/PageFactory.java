package Pages;

import org.openqa.selenium.WebDriver;

public class PageFactory {
    WebDriver driver;
    private VerifyBookStorePage HomePage;

    public PageFactory(WebDriver driver){
        this.driver = driver;
    }

    public VerifyBookStorePage getHomePage(){
        if(HomePage == null){
            HomePage = new VerifyBookStorePage(driver);
        }
        return HomePage;
    }
}
