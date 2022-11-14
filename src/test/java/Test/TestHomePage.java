package Test;

import org.testng.annotations.Test;

public class TestHomePage extends BaseClass{

    @Test
    public void HomePage(){
        pageFactory.getHomePage().HomePage();
        pageFactory.getHomePage().verifyBookStore();
    }

}
