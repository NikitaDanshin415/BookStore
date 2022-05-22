package tests;

import api.controllers.Account;
import api.controllers.AccountAuth;
import api.controllers.BookStore;
import api.models.request.AddListOfBooksRq;
import api.models.request.DeleteBookRq;
import api.models.request.Isbn;
import api.models.request.LoginRq;
import api.models.response.*;
import config.user.UserConfigProvider;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.baseTests.BaseTestApi;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("api")
public class BookStoreApiTests extends BaseTestApi {

    TokenRs tokenViewModel;
    LoginRs loginResponse;
    UserInfoRs userInfoRs;
    BookRs[] booksArray;
    LoginRq loginRq = new LoginRq(UserConfigProvider.userConfig.getLogin()
        , UserConfigProvider.userConfig.getPassword());

    @Test
    public void generateTokenTest() {
        step("Отправляем запрос на получение токена", () -> {
            tokenViewModel = new AccountAuth()
                .generateToken(loginRq);
        });

        step("Проверяем, что токен не равен Null", () -> {
            assertThat(tokenViewModel.getToken())
                .isNotNull();
        });

        step("Проверяем сообщение об успешной авторизации", () -> {
            assertThat(tokenViewModel.getResult())
                .isEqualTo("User authorized successfully.");
        });

        step("Проверяем результат запроса", () -> {
            assertThat(tokenViewModel.getStatus())
                .isEqualTo("Success");
        });
    }

    @Test
    public void loginTest() {
        step("Отправляем запрос авторизации", () -> {
            loginResponse = new AccountAuth()
                .login(loginRq);
        });

        step("Проверяем, что токен не равен Null", () -> {
            assertThat(loginResponse.getToken())
                .isNotNull();
        });

        step("Проверяем, что userId не равен Null", () -> {
            assertThat(loginResponse.getUserId())
                .isNotNull();
        });
    }

    @Test
    public void userInfoTest() {
        step("Отправляем запрос авторизации", () -> {
            loginResponse = new AccountAuth()
                .login(loginRq);
        });

        step("Отправляем запрос получения информации о пользователе", () -> {
            userInfoRs = new Account(loginResponse.getToken())
                .getUserInfo(loginResponse.getUserId());
        });

        step("Проверяем, что userId пользователя совпадает с ответом из логина", () -> {
            assertThat(userInfoRs.getUserId())
                .isEqualTo(loginResponse.getUserId());
        });

        step("Проверяем, что userName пользователя совпадает с логином", () -> {
            assertThat(userInfoRs.getUserName())
                .isEqualTo(UserConfigProvider.userConfig.getLogin());
        });
    }

    @Test
    public void getArrayBooksTest() {
        step("Отправляем запрос авторизации", () -> {
            loginResponse = new AccountAuth()
                .login(loginRq);
        });

        step("Отправляем запрос получения информации о имеющихся книгах", () -> {
            booksArray = new BookStore(loginResponse.getToken())
                .books();
        });

        step("Количество полученных книг должно быть равно 8", () -> {
            assertThat(booksArray.length)
                .isEqualTo(8);
        });

        step("У всех книг есть ссылка на веб сайт", () -> {
            assertThat(booksArray)
                .allMatch( e -> !e.getWebsite().equals(""));
        });
    }

    @Test
    public void checkBookPipeline() {
        step("Отправляем запрос авторизации", () -> {
            loginResponse = new AccountAuth()
                .login(loginRq);
        });

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

        step("Проверяем, что книга отображается в коллекции", () -> {
            userInfoRs = new Account(loginResponse.getToken())
                .getUserInfo(loginResponse.getUserId());

            assertThat(userInfoRs.getBooks().length)
                .isEqualTo(1);

            assertThat(userInfoRs.getBooks()[0].getIsbn())
                .isEqualTo(booksArray[0].getIsbn());
        });

        step("Удаляем книгу из коллекции", () -> {
            DeleteBookRq rq = new DeleteBookRq( booksArray[0].getIsbn(), loginResponse.getUserId());

            new BookStore(loginResponse.getToken())
                .deleteBook(rq);
        });
    }
}
