package Pages;

import Enums.HomePage;
import POJOClass.Book;
import com.google.gson.*;
import dev.failsafe.internal.util.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class VerifyBookStorePage {

    String[] strArr = {"Elements","Forms","Alerts, Frame & Windows","Interactions","Widgets","Book Store Application"};
    By ele = By.xpath("//h5");
    String Elements = "//h5[contains(text(),'%s')]";
    By BookStore = By.xpath("//div[@class='main-header']");
    String Title ="//a[contains(text(),'%s')]";
    String Author = "//div[contains(text(),'%s')]";
    String Publisher = "(//div[contains(text(),\"%s\")])[1]";
    public static Properties prop;
    WebDriver driver;
    WebDriverWait wait;
    public VerifyBookStorePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public void HomePage(){
        try {
            prop = new Properties();
            FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "//src//test//java//Data//Data.properties");
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<WebElement> Element = driver.findElements(ele);
        for (WebElement allElements : Element) {
            String AllHomePageElements = allElements.getText();
            System.out.println(AllHomePageElements);
        }
        for(String s : Arrays.asList(strArr)){
                String actual = driver.findElement(By.xpath(String.format(Elements,s))).getText();
        Assert.isTrue(actual.equals(s), "Expected result does not match with actual result");
            }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,850)", "");
        driver.findElement(By.xpath(String.format(Elements, HomePage.BOOKSTORE.getResourcesName()))).click();
    }
    public void BookStorePage() {
        String actual4 = driver.findElement(BookStore).getText();
        Assert.isTrue(actual4.equals(prop.getProperty("Title")), "Expected result does not match with actual result");
        RestAssured.baseURI = "https://demoqa.com";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/BookStore/v1/Books");
        String Response = response.asString();
        JsonElement fileElement = JsonParser.parseString(Response);
        JsonObject fileObject = fileElement.getAsJsonObject();
        JsonArray JsonArrayOfAddress = fileObject.get("books").getAsJsonArray();
        List<Book> books = new ArrayList();
        for (JsonElement BooksElement : JsonArrayOfAddress.getAsJsonArray()) {
            JsonObject  BooksJsonObject = BooksElement.getAsJsonObject();
            String title = BooksJsonObject.get("title").getAsString();
            String author = BooksJsonObject.get("author").getAsString();
            String publisher = BooksJsonObject.get("publisher").getAsString();
            Book Details = new Book(title, author, publisher);
            books.add(Details);
        }
        Book first = books.get(0);
        String Title = first.getTitle();
        String Author = first.getAuthor();
        String Publisher = first.getPublisher();
        System.out.println(Title);
        System.out.println(Author);
        System.out.println(Publisher);
        String actualTitle = driver.findElement(By.xpath(String.format(this.Title, Title))).getText();
        Assert.isTrue(actualTitle.equals(Title), "Expected result does not match with actual result");
        String actualAuthor = driver.findElement(By.xpath(String.format(this.Author, Author))).getText();
        Assert.isTrue(actualAuthor.equals(Author), "Expected result does not match with actual result");
        String actualPublisher = driver.findElement(By.xpath(String.format(this.Publisher, Publisher))).getText();
        Assert.isTrue(actualPublisher.equals(Publisher), "Expected result does not match with actual result");
    }
}
