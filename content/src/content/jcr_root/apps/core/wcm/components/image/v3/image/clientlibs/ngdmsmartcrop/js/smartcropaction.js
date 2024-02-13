/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2023 Adobe
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
/* global CQ */
(function($, ns, authorNs) {
    "use strict";
    ns.image.v3.actions.smartCrop = function() {
        const editable = this;
        if (this.authorNs) {
            authorNs.DialogFrame.openDialog(new ns.image.v3.smartCropDialog(editable));
        }
    };

    ns.image.v3.actions.smartCrop.condition = function(editable) {
        if (this.authorNs) {
            return authorNs.pageInfoHelper.canModify() && hasNGDMSmartCropAction(editable) && isNGDMImage(editable);
        }
    };

    function hasNGDMSmartCropAction(editable) {
        return editable.config.editConfig.actions.some((action) => (typeof action === "object" && action.name === "ngdm-smartcrop"));
    }

    function isNGDMImage(editable) {
        return ($(editable.dom).find(".cq-dd-image[data-cmp-filereference^='/urn:']").length > 0);
    }
})(jQuery, CQ.CoreComponents, Granite.author);
