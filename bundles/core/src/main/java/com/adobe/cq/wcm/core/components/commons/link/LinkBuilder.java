/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2022 Adobe
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
package com.adobe.cq.wcm.core.components.commons.link;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.annotation.versioning.ConsumerType;

/**
 * Builds a link and sets link properties.
 */
@ConsumerType
public interface LinkBuilder {

    /**
     * Sets the property name to read the link URL from.
     *
     * @param name The property name
     * @return {@link LinkBuilder}
     */
    @NotNull
    LinkBuilder setLinkUrlPropertyName(@NotNull String name);

    /**
     * Sets the HTML link target.
     *
     * @param target The link target
     * @return {@link LinkBuilder}
     */
    @NotNull
    LinkBuilder setLinkTarget(@NotNull String target);

    /**
     * Sets the HTML link attribute.
     *
     * @param name The attribute name
     * @param value The attribute value
     * @return {@link LinkBuilder}
     */
    @NotNull
    LinkBuilder setLinkAttribute(@NotNull String name, @Nullable String value);

    /**
     * Returns the resolved link.
     *
     * @return {@link Link}
     */
    @NotNull
    Link build();
}
