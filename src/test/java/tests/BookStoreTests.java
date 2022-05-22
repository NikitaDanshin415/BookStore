package tests;

import api.controllers.AccountAuth;
import api.controllers.BookStore;
import api.models.request.AddListOfBooksRq;
import api.models.request.Isbn;
import api.models.request.LoginRq;
import api.models.response.BookRs;
import api.models.response.LoginRs;
import com.codeborne.selenide.Selenide;
import config.user.UserConfigProvider;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import tests.baseTests.BaseTest;
import ui.pageObject.LoginPage;

import static io.qameta.allure.Allure.step;

public class BookStoreTests extends BaseTest {

    LoginRq loginRq = new LoginRq(UserConfigProvider.userConfig.getLogin()
        , UserConfigProvider.userConfig.getPassword());
    LoginRs loginResponse;
    BookRs[] booksArray;

    @Test
    public void test1(){
        step("Авторизация через АПИ", ()->{
            loginResponse = new AccountAuth()
                .login(loginRq);
        });

        step("Добавляем любую книгу в коллекцию через АПИ", ()->{
            step("Отправляем запрос получения информации о имеющихся книгах", () -> {
                booksArray = new BookStore(loginResponse.getToken())
                    .books();
            });

            step("Добавляем книгу в коллекцию", () -> {
                AddListOfBooksRq rq = new AddListOfBooksRq();

                Isbn[] isbnCollection = new Isbn[]{
                    new Isbn(booksArray[0].getIsbn()),
                };

                rq.setUserId(loginResponse.getUserId());
                rq.setCollectionOfIsbns(isbnCollection);

                new BookStore(loginResponse.getToken())
                    .book(rq);
            });
        });

        step("Авторизуемся на UI", ()->{
            Selenide.open("/login");

            new LoginPage()
                .pageHeaderIs("Login");

            Cookie cookieToken = new Cookie("token"
                ,loginResponse.getToken()
                ,"demoqa.com"
                ,"/"
                ,null);

            Cookie cookieExpires = new Cookie("expires"
                ,loginResponse.getExpires()
                ,"demoqa.com"
                ,"/"
                ,null);

            Cookie cookieUserName = new Cookie("userName"
                ,loginResponse.getUsername()
                ,"demoqa.com"
                ,"/"
                ,null);

            Cookie cookieUserId = new Cookie("userID"
                ,loginResponse.getUserId()
                ,"demoqa.com"
                ,"/"
                ,null);


            setCookieToWebDriver(cookieToken);
            setCookieToWebDriver(cookieExpires);
            setCookieToWebDriver(cookieUserName);
            setCookieToWebDriver(cookieUserId);

            Selenide.refresh();

        });

        step("Проверяем наличие книги в коллекции", ()->{

        });

        step("Удаляем книгу из коллекции", ()->{

        });

        step("Проверяем, что книги нет в коллекции", ()->{

        });
    }
}
