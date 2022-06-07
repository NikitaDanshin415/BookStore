package tests;

import api.controllers.Account;
import api.controllers.BookStore;
import api.models.request.AddListOfBooksRq;
import api.models.request.Isbn;
import api.models.request.LoginRq;
import api.models.response.BookRs;
import api.models.response.LoginRs;
import com.codeborne.selenide.Selenide;
import config.user.UserConfigProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import tests.baseTests.BaseTest;
import tools.CommonTools;
import pages.pageElements.ModalWindow;
import pages.pageObject.LoginPage;
import pages.pageObject.ProfilePage;

import static io.qameta.allure.Allure.step;

@Tags({@Tag("apiUi"), @Tag("regress")})
@DisplayName("Тесты магазина книг")
public class BookStoreTests extends BaseTest {

    LoginRq loginRq = LoginRq
        .builder()
        .userName(UserConfigProvider.userConfig.login())
        .password(UserConfigProvider.userConfig.password())
        .build();

    LoginRs loginResponse;
    BookRs[] booksArray;

    private final String domain = "demoqa.com";
    private final String path = "/";

    @Test
    @Tag("Store")
    @DisplayName("Добавление и удаление книги из коллекции")
    public void test1() {
        step("Авторизация через АПИ", () -> {
            loginResponse = new Account()
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
                rq.setCollectionOfIsbn(isbnCollection);

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
                CommonTools commonTools = new CommonTools();
                commonTools.setCookieToWebDriver("token", loginResponse.getToken(), domain, path);
                commonTools.setCookieToWebDriver("expires", loginResponse.getExpires(), domain, path);
                commonTools.setCookieToWebDriver("userName", loginResponse.getUsername(), domain, path);
                commonTools.setCookieToWebDriver("userID", loginResponse.getUserId(), domain, path);
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