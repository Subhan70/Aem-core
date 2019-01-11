/*******************************************************************************
 * Copyright 2019 Adobe
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

window.CQ.CoreComponentsIT.Page.v3 = window.CQ.CoreComponentsIT.Page.v3 || {};

/**
 * Tests for the core page component.
 */
(function(h, $) {
    "use strict";

    // shortcut
    var c = window.CQ.CoreComponentsIT.commons;
    var pageV1 = window.CQ.CoreComponentsIT.Page.v1;
    var pageV2 = window.CQ.CoreComponentsIT.Page.v2;
    var pageV3 = window.CQ.CoreComponentsIT.Page.v3;

    /**
     * v3 specifics
     */
    var pageSelector = {
        segmentPath: "/conf/we-retail/settings/wcm/segments"
    };
    var tcExecuteBeforeTest = pageV3.tcExecuteBeforeTest();
    var tcExecuteAfterTest = pageV1.tcExecuteAfterTest();

    /**
     * The main test suite for Page component
     */
    new h.TestSuite("Page v3", { path: "/apps/core/wcm/tests/test-suites/Page/v3/Page.js",
        execBefore: c.tcExecuteBeforeTestSuite,
        execInNewWindow: false })

        .addTestCase(pageV1.tcBasicTitleAndTagsPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcBasicTitlesAndDescriptionsPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcBasicOnOffTimePageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcBasicVanityUrlPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcAdvancedSettingsPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcAdvancedTemplatesSettingsPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV2.tcAdvancedConfigurationPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcAdvancedAuthenticationPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcAdvancedExportPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcThumbnailPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcSocialMediaPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcCloudServicesPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcPersonalizationPageProperties(pageSelector, tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcAddPermissionsPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcEditUserGroupPermissionsPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcEffectivePermissionsPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV2.tcBlueprintPageProperties64(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV1.tcLiveCopyPageProperties(tcExecuteBeforeTest, tcExecuteAfterTest))
        .addTestCase(pageV3.tcHierarchyJSON(tcExecuteBeforeTest, tcExecuteAfterTest));

}(hobs, jQuery));
