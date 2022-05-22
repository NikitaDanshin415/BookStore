package ui.pageObject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement pageHeader = $(".main-header");
    private final SelenideElement loadingLabel = $("#loading-label");

    @Step("В шапке страницы должен отображаться заголовок {text}")
    public LoginPage pageHeaderIs(String text){
        pageHeader
            .shouldHave(Condition.text(text));

        return this;
    }

    @Step("На странице должен отображаться текст {text}")
    public LoginPage pageTitleIs(String text){
        pageHeader
            .shouldHave(Condition.text(text));

        return this;
    }
}
