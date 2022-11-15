package Test;

import org.testng.annotations.Test;
import java.io.IOException;

public class TestHomePage extends BaseClass{

    @Test
    public void HomePage() throws IOException {
        pageFactory.getHomePage().HomePage();
        pageFactory.getHomePage().BookStorePage();
    }

}
