package uiTools.pageObject;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage {
    private final ElementsCollection bookList = $$(".rt-tr-group");

    @Step("Проверяем наличие книги '{bookTitle}' в таблице")
    public ProfilePage tableContainsBook(String bookTitle){
        bookList
            .filter(Condition.text(bookTitle))
            .shouldHave(CollectionCondition.size(1));

        return this;
    }

    @Step("Удаляем книгу '{bookTitle}' из таблицы")
    public ProfilePage deleteBook(String bookTitle){
        bookList
            .filter(Condition.text(bookTitle))
            .get(0)
            .$("[title='Delete']")
            .click();

        return this;
    }

    @Step("Проверяем отсутствие книги '{bookTitle}' в таблице")
    public ProfilePage tableNotContainsBook(String bookTitle){
        bookList
            .filter(Condition.text(bookTitle))
            .shouldHave(CollectionCondition.size(0));

        return this;
    }
}