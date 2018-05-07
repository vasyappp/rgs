import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RGSTest extends BaseTest {
    @Before
    public void generate() {
        Generate.generatePool();
    }

    @FindBy(xpath = ".//a[contains(text(),'Страхование')]")
    WebElement insuranceOptions;

    @FindBy(xpath = ".//a[contains(text(),'ДМС')]")
    WebElement dms;

    @FindBy(xpath = ".//div[@class='page-header']/*[text() != '']")
    WebElement header;

    @FindBy(xpath = ".//a[contains(text(), 'Отправить заявку')]")
    WebElement sendRequest;

    @FindBy(xpath = ".//*[@class = 'modal-title']//*[text() != '']")
    WebElement formHeader;

    @FindBy(name = "LastName")
    WebElement inputLastName;

    @FindBy(name = "FirstName")
    WebElement inputFirstName;

    @FindBy(name = "MiddleName")
    WebElement inputMiddleName;

    @FindBy(name = "Region")
    WebElement inputRegion;

    @FindBy(xpath = ".//option[@value = '77']")
    WebElement regionOption;

    @FindBy(xpath = ".//label[contains(text(), 'Телефон')]/following-sibling::input")
    WebElement inputPhone;

    @FindBy(name = "Email")
    WebElement inputEmail;

    @FindBy(name = "ContactDate")
    WebElement inputContactDate;

    @FindBy(name = "Comment")
    WebElement inputComment;

    @FindBy(className = "checkbox")
    WebElement checkbox;

    @FindBy(id = "button-m")
    WebElement send;

    @FindBy(xpath = ".//*[contains(text(), 'Эл. почта')]/following::*//span[@class='validation-error-text']")
    WebElement errorMessageEmail;


    public String parseToMaskPhone(String phone) {
        String maskPhone = "+7 (";

        maskPhone += phone.substring(0, 3);
        maskPhone += ") ";
        maskPhone += phone.substring(3, 6);
        maskPhone += "-";
        maskPhone += phone.substring(6, 8);
        maskPhone += "-";
        maskPhone += phone.substring(8, 10);

        return maskPhone;
    }

    public String parseToMaskDate(String date) {
        String maskDate = "";

        maskDate += date.substring(0, 2);
        maskDate += ".";
        maskDate += date.substring(2, 4);
        maskDate += ".";
        maskDate += date.substring(4, 8);

        return maskDate;
    }

    public void fillFields(Person person, String contactDate, String comment) {
        inputLastName.sendKeys(person.getLastName());
        inputFirstName.sendKeys(person.getFirstName());
        inputMiddleName.sendKeys(person.getMiddleName());
        inputPhone.sendKeys(person.getPhone());
        inputEmail.sendKeys(person.getEmail());

        inputRegion.click();
        waitVisibility(regionOption);
        regionOption.click();

        inputContactDate.sendKeys(contactDate);

        inputComment.sendKeys(comment);

        checkbox.click();
    }

    @Test
    public void rgsTest () {
        PageFactory.initElements(driver, this);

        waitVisibility(insuranceOptions);
        insuranceOptions.click();

        waitVisibility(dms);
        dms.click();

        waitVisibility(header);
        Assert.assertTrue("На странице нет заголовка с текстом \"Добровольное медицинское страхование\"",
                header.getText().contains("Добровольное медицинское страхование"));

        waitVisibility(sendRequest);
        sendRequest.click();

        waitVisibility(formHeader);
        Assert.assertTrue("На странице нет заголовка с текстом \"Заявка на добровольное медицинское страхование\"",
                formHeader.getText().contains("Заявка на добровольное медицинское страхование"));

        Person person = new Person(Generate.generateLastName(), Generate.generateFirstName(),
                Generate.generateMiddleName(), Generate.generatePhone(), "qwertyqwerty");

        fillFields(person, "22052018", "testtesttest");

        Assert.assertEquals("Отображаемая фамилия не равна введенной",
                person.getLastName(), inputLastName.getAttribute("value"));
        Assert.assertEquals("Отображаемое имя не равно введенному",
                person.getFirstName(), inputFirstName.getAttribute("value"));
        Assert.assertEquals("Отображаемое отчество не равно введенному",
                person.getMiddleName(), inputMiddleName.getAttribute("value"));
        Assert.assertEquals("Отображаемый телефон не равен введенному",
                parseToMaskPhone(person.getPhone()), inputPhone.getAttribute("value"));
        Assert.assertEquals("Отображаемая почта не равна введенной",
                person.getEmail(), inputEmail.getAttribute("value"));

        Assert.assertEquals("Отображаемый регион не равен введенному",
                "77", inputRegion.getAttribute("value"));
        Assert.assertEquals("Отображаемая дата не равна введенной",
                parseToMaskDate("22052018"), inputContactDate.getAttribute("value"));
        Assert.assertEquals("Отображаемый комментарий не равен введенному",
                "testtesttest", inputComment.getAttribute("value"));

        waitVisibility(send);
        send.click();

        waitVisibility(errorMessageEmail);
        Assert.assertTrue("Нет сообщения об ошибке ввода электронной почты", errorMessageEmail.isDisplayed());
    }
}
