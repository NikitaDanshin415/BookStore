package tests;

import api.controllers.Account;
import api.controllers.BookStore;
import api.models.request.AddListOfBooksRq;
import api.models.request.DeleteBookRq;
import api.models.request.Isbn;
import api.models.request.LoginRq;
import api.models.response.*;
import config.user.UserConfigProvider;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import tests.baseTests.BaseTestApi;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tags({@Tag("api"), @Tag("regress")})
@DisplayName("Тесты АПИ")
public class BookStoreApiTests extends BaseTestApi {
    TokenRs tokenRs;
    LoginRs loginRs;
    UserInfoRs userInfoRs;
    BookRs[] booksRs;
    LoginRq loginRq = LoginRq
        .builder()
        .userName(UserConfigProvider.userConfig.login())
        .password(UserConfigProvider.userConfig.password())
        .build();

    @Test
    @DisplayName("Генерация токена")
    public void generateTokenTest() {
        step("Отправляем запрос на получение токена", () -> {
            tokenRs = new Account()
                .generateToken(loginRq);
        });

        step("Проверяем, что токен не равен Null", () -> {
            assertThat(tokenRs.getToken())
                .isNotNull();
        });

        step("Проверяем сообщение об успешной авторизации", () -> {
            assertThat(tokenRs.getResult())
                .isEqualTo("User authorized successfully.");
        });

        step("Проверяем результат запроса", () -> {
            assertThat(tokenRs.getStatus())
                .isEqualTo("Success");
        });
    }

    @Test
    @DisplayName("Авторизация")
    public void loginTest() {
        step("Отправляем запрос авторизации", () -> {
            loginRs = new Account()
                .login(loginRq);
        });

        step("Проверяем, что токен не равен Null", () -> {
            assertThat(loginRs.getToken())
                .isNotNull();
        });

        step("Проверяем, что userId не равен Null", () -> {
            assertThat(loginRs.getUserId())
                .isNotNull();
        });
    }

    @Test
    @DisplayName("Получение информации о пользователе")
    public void userInfoTest() {
        step("Отправляем запрос авторизации", () -> {
            loginRs = new Account()
                .login(loginRq);
        });

        step("Отправляем запрос получения информации о пользователе", () -> {
            userInfoRs = new Account(loginRs.getToken())
                .getUserInfo(loginRs.getUserId());
        });

        step("Проверяем, что userId пользователя совпадает с ответом из логина", () -> {
            assertThat(userInfoRs.getUserId())
                .isEqualTo(loginRs.getUserId());
        });

        step("Проверяем, что userName пользователя совпадает с логином", () -> {
            assertThat(userInfoRs.getUserName())
                .isEqualTo(UserConfigProvider.userConfig.login());
        });
    }

    @Test
    @DisplayName("Получение списка книг в магазине")
    public void getArrayBooksTest() {
        step("Отправляем запрос авторизации", () -> {
            loginRs = new Account()
                .login(loginRq);
        });

        step("Отправляем запрос получения информации о имеющихся книгах", () -> {
            booksRs = new BookStore(loginRs.getToken())
                .books();
        });

        step("Количество полученных книг должно быть равно 8", () -> {
            assertThat(booksRs.length)
                .isEqualTo(8);
        });

        step("У всех книг есть ссылка на веб сайт", () -> {
            assertThat(booksRs)
                .allMatch(e -> !e.getWebsite().equals(""));
        });
    }

    @Test
    @DisplayName("Проверка работы корзины")
    @Description("Через апи проверяем добавление книги в коллекцию и ее удаление из коллекции")
    public void checkBookPipeline() {
        step("Отправляем запрос авторизации", () -> {
            loginRs = new Account()
                .login(loginRq);
        });

        step("Отправляем запрос получения информации о имеющихся книгах", () -> {
            booksRs = new BookStore(loginRs.getToken())
                .books();
        });

        step("Добавляем книгу в коллекцию", () -> {
            AddListOfBooksRq rq = new AddListOfBooksRq();

            Isbn[] isbnCollection = new Isbn[]{
                new Isbn(booksRs[0].getIsbn()),
            };

            rq.setUserId(loginRs.getUserId());
            rq.setCollectionOfIsbn(isbnCollection);

            new BookStore(loginRs.getToken())
                .book(rq);
        });

        step("Проверяем, что книга отображается в коллекции", () -> {
            userInfoRs = new Account(loginRs.getToken())
                .getUserInfo(loginRs.getUserId());

            assertThat(userInfoRs.getBooks().length)
                .isEqualTo(1);

            assertThat(userInfoRs.getBooks()[0].getIsbn())
                .isEqualTo(booksRs[0].getIsbn());
        });

        step("Удаляем книгу из коллекции", () -> {
            DeleteBookRq rq = DeleteBookRq
                .builder()
                .isbn(booksRs[0].getIsbn())
                .userId(loginRs.getUserId())
                .build();

            new BookStore(loginRs.getToken())
                .deleteBook(rq);
        });
    }
}
