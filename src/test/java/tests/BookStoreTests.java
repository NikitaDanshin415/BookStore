package tests;

import apiTools.controllers.AccountAuth;
import apiTools.controllers.BookStore;
import apiTools.models.request.AddListOfBooksRq;
import apiTools.models.request.Isbn;
import apiTools.models.request.LoginRq;
import apiTools.models.response.BookRs;
import apiTools.models.response.LoginRs;
import com.codeborne.selenide.Selenide;
import config.user.UserConfigProvider;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import tests.baseTests.BaseTest;
import uiTools.pageElements.ModalWindow;
import uiTools.pageObject.LoginPage;
import uiTools.pageObject.ProfilePage;

import static io.qameta.allure.Allure.step;


public class BookStoreTests extends BaseTest {

    LoginRq loginRq = new LoginRq(UserConfigProvider.userConfig.getLogin()
        , UserConfigProvider.userConfig.getPassword());
    LoginRs loginResponse;
    BookRs[] booksArray;

    @Test
    @Tag("Store")
    public void test1() {
        step("Авторизация через АПИ", () -> {
            loginResponse = new AccountAuth()
                .login(loginRq);
        });

        step("Добавляем любую книгу в коллекцию через АПИ", () -> {
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

        step("Авторизуемся через куки", () -> {
            step("Открыть страницу авторизации", () -> {
                Selenide.open("/login");
            });


            new LoginPage()
                .pageHeaderIs("Login");

            step("Устанаваливаем куки в браузер", () -> {
                Cookie cookieToken = new Cookie("token"
                    , loginResponse.getToken()
                    , "demoqa.com"
                    , "/"
                    , null);

                Cookie cookieExpires = new Cookie("expires"
                    , loginResponse.getExpires()
                    , "demoqa.com"
                    , "/"
                    , null);

                Cookie cookieUserName = new Cookie("userName"
                    , loginResponse.getUsername()
                    , "demoqa.com"
                    , "/"
                    , null);

                Cookie cookieUserId = new Cookie("userID"
                    , loginResponse.getUserId()
                    , "demoqa.com"
                    , "/"
                    , null);


                setCookieToWebDriver(cookieToken);
                setCookieToWebDriver(cookieExpires);
                setCookieToWebDriver(cookieUserName);
                setCookieToWebDriver(cookieUserId);
            });

            step("Обновить страницу", Selenide::refresh);

            new LoginPage()
                .pageHaveMessage("You are already logged in")
                .toProfile();

        });

        step("Проверяем наличие книги в коллекции", () -> {
            new ProfilePage()
                .tableContainsBook(booksArray[0].getTitle());
        });

        step("Удаляем книгу из коллекции", () -> {
            new ProfilePage()
                .deleteBook(booksArray[0].getTitle());

            new ModalWindow()
                .windowIsVisible()
                .clickOk();

            step("Закрываем алерт", () -> {
                Selenide.confirm();
            });

        });

        step("Проверяем, что книги нет в коллекции", () -> {
            new ProfilePage()
                .tableNotContainsBook(booksArray[0].getTitle());
        });
    }
}
