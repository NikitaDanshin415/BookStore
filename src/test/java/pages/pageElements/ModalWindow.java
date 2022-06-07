package pages.pageElements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class ModalWindow {
    private final SelenideElement modalWindow = $(".modal-content");
    private final SelenideElement okBtn = $(".modal-content #closeSmallModal-ok");

    @Step("Модальное окно отображается на странице")
    public ModalWindow windowIsVisible() {
        modalWindow
            .shouldBe(Condition.visible);

        return this;
    }

    @Step("Нажать кнопку 'ОК' в модальном окне")
    public ModalWindow clickOk() {
        okBtn
            .click();

        return this;
    }
}
