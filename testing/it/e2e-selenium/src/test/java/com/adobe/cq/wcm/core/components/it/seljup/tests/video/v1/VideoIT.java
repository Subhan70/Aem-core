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

package com.adobe.cq.wcm.core.components.it.seljup.tests.video.v1;

import com.adobe.cq.testing.selenium.pageobject.EditorPage;
import com.adobe.cq.testing.selenium.pageobject.PageEditorPage;
import com.adobe.cq.testing.selenium.pagewidgets.sidepanel.SidePanel;
import com.adobe.cq.wcm.core.components.it.seljup.AuthorBaseUITest;
import com.adobe.cq.wcm.core.components.it.seljup.components.image.ImageEditDialog;
import com.adobe.cq.wcm.core.components.it.seljup.components.video.VideoEditDialog;
import com.adobe.cq.wcm.core.components.it.seljup.components.video.v1.Video;
import com.adobe.cq.wcm.core.components.it.seljup.constant.CoreComponentConstants;
import com.adobe.cq.wcm.core.components.it.seljup.util.Commons;
import org.apache.http.HttpStatus;
import org.apache.sling.testing.clients.ClientException;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeoutException;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("group2")
public class VideoIT extends AuthorBaseUITest {

    private static String componentName = "video";
    private static String testAssetsPath         = "/content/dam/core-components-examples";
    private static String testVideoPath          = testAssetsPath + "/library/videoSample/mp4";
    private static String assetFilterSelect         = "coral-select.assetfilter";
    private static String assetFilterVideosOption      = "coral-selectlist-item[value='Videos']";

    private String proxyComponentPath;

    protected String testPage;
    protected EditorPage editorPage;
    protected Video video;
    protected String cmpPath;
    protected String videoRT;

    private SidePanel sidePanel;

    private void setupResources() {
        videoRT = Commons.rtVideo_v1;
    }

    protected void setup() throws ClientException {
        testPage = authorClient.createPage("testPage", "Test Page", rootPage, defaultPageTemplate, 200, 201).getSlingPath();
        proxyComponentPath = Commons.creatProxyComponent(adminClient, videoRT, "Proxy Video", componentName);
        addPathtoComponentPolicy(responsiveGridPath, proxyComponentPath);
        cmpPath = Commons.addComponent(adminClient, proxyComponentPath,testPage + Commons.relParentCompPath, componentName, null);
        editorPage = new PageEditorPage(testPage);
        editorPage.open();
        this.video = new Video();
    }

    public void openSidePanel() {
        sidePanel = new SidePanel();
        if(sidePanel.isHidden()) {
            sidePanel.show();
            sidePanel.element().find(assetFilterSelect).click();
            sidePanel.element().find(assetFilterVideosOption).click();
        }
    }

    @BeforeEach
    public void setupBefore() throws Exception {
        setupResources();
        setup();
        openSidePanel();
    }

    @AfterEach
    public void cleanup() throws ClientException, InterruptedException {
        Commons.deleteProxyComponent(adminClient, proxyComponentPath);
        authorClient.deletePageWithRetry(testPage, true,false, CoreComponentConstants.TIMEOUT_TIME_MS, CoreComponentConstants.RETRY_TIME_INTERVAL,  HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Test: Add video component")
    public void testAddVideo() throws InterruptedException {
        VideoEditDialog editDialog = video.getEditDialog();
        Thread.sleep(4000);
    }

    @Test
    @DisplayName("Test: Check video boxes")
    public void testCheckBoxes() throws InterruptedException, TimeoutException {
        Commons.openEditDialog(editorPage, cmpPath);
        VideoEditDialog editDialog = video.getEditDialog();

        editDialog.openVideoTab();
        editDialog.uploadVideoFromSidePanel(testVideoPath);

        editDialog.openPropertiesTab();
        editDialog.clickLoopEnabled();

        Commons.saveConfigureDialog();

        editorPage.enterPreviewMode();
        Commons.switchContext("ContentFrame");

        assertTrue(video.element().find("video").getAttribute("loop").equals("true"), "loop is set");
    }
}
