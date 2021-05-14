/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2021 Adobe
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package com.adobe.cq.wcm.core.components.it.seljup.components.formbutton;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.wcm.core.components.it.seljup.constant.CoreComponentConstants;
import com.adobe.cq.wcm.core.components.it.seljup.util.Commons;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;

public class BaseFormButton extends BaseComponent {
    private String button;

    public BaseFormButton(String button) {
        super(button);
        this.button = button;
    }

    public boolean isButtonPresentByType(String type) {
        return $(button +"[type='" + type + "']").isDisplayed();
    }

    public String getButtonText() {
        return $(button).getText();
    }

    public boolean isButtonPresentByName(String name) throws InterruptedException {
        Commons.webDriverWait(CoreComponentConstants.WEBDRIVER_WAIT_TIME_MS);
        //final WebDriver webDriver = WebDriverRunner.getWebDriver();
        //new WebDriverWait(webDriver, CoreComponentConstants.TIMEOUT_TIME_SEC)
        //    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(button +"[name='" + name + "']")));
        return $(button +"[name='" + name + "']").isDisplayed();
    }

    public boolean isButtonPresentByValue(String value) throws InterruptedException {
        Commons.webDriverWait(CoreComponentConstants.WEBDRIVER_WAIT_TIME_MS);
        //final WebDriver webDriver = WebDriverRunner.getWebDriver();
        //new WebDriverWait(webDriver, CoreComponentConstants.TIMEOUT_TIME_SEC)
        //    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(button +"[value='" + value + "']")));
        return $(button +"[value='" + value + "']").isDisplayed();
    }
}
