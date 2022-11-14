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
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VerifyBookStore {
    String Elements = "//h5[contains(text(),'%s')]";
    By BookStore = By.xpath("//div[@class='main-header']");
    String Row ="//a[contains(text(),'%s')]";
    String Name = "//div[contains(text(),'%s')]";
    String authorName = "(//div[contains(text(),\"%s\")])[1]";
    public static Properties prop;
    WebDriver driver;
    WebDriverWait wait;
    public VerifyBookStore(WebDriver driver) {
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
        String actual = driver.findElement(By.xpath(String.format(Elements, HomePage.ELEMENT.getResourcesName()))).getText();
        Assert.isTrue(actual.equals(prop.getProperty("Element")), "Expected result does not match with actual result");
        String act = driver.findElement(By.xpath(String.format(Elements, HomePage.FORM.getResourcesName()))).getText();
        Assert.isTrue(act.equals(prop.getProperty("Form")), "Expected result does not match with actual result");
        String actual1 = driver.findElement(By.xpath(String.format(Elements, HomePage.ALERTS.getResourcesName()))).getText();
        Assert.isTrue(actual1.equals(prop.getProperty("Alerts&Frame")), "Expected result does not match with actual result");
        String actual2 = driver.findElement(By.xpath(String.format(Elements, HomePage.INTERACTIONS.getResourcesName()))).getText();
        Assert.isTrue(actual2.equals(prop.getProperty("Interactions")), "Expected result does not match with actual result");
        String actual3 = driver.findElement(By.xpath(String.format(Elements, HomePage.WIDGETS.getResourcesName()))).getText();
        Assert.isTrue(actual3.equals(prop.getProperty("Widgits")), "Expected result does not match with actual result");
        String actual4 = driver.findElement(By.xpath(String.format(Elements, HomePage.BOOKSTORE.getResourcesName()))).getText();
        Assert.isTrue(actual4.equals(prop.getProperty("BookStore")), "Expected result does not match with actual result");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,850)", "");
        driver.findElement(By.xpath(String.format(Elements, HomePage.BOOKSTORE.getResourcesName()))).click();
    }
    public void verifyBookStore() {
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
        JsonObject BooksJsonObject = null;
        for (JsonElement BooksElement : JsonArrayOfAddress.getAsJsonArray()) {
            BooksJsonObject = BooksElement.getAsJsonObject();
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
        String actual5 = driver.findElement(By.xpath(String.format(Row, Title))).getText();
        Assert.isTrue(actual5.equals(Title), "Expected result does not match with actual result");
        String actual6 = driver.findElement(By.xpath(String.format(Name, Author))).getText();
        Assert.isTrue(actual6.equals(Author), "Expected result does not match with actual result");
        String actual7 = driver.findElement(By.xpath(String.format(authorName, Publisher))).getText();
        Assert.isTrue(actual7.equals(Publisher), "Expected result does not match with actual result");
    }
}
