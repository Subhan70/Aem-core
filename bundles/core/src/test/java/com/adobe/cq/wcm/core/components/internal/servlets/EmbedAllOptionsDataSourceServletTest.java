/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2019 Adobe Systems Incorporated
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

package com.adobe.cq.wcm.core.components.internal.servlets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import io.wcm.testing.mock.aem.junit.AemContext;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.adobe.cq.wcm.core.components.context.CoreComponentTestContext;
import com.adobe.cq.wcm.core.components.internal.servlets.EmbedAllOptionsDataSourceServlet.EmbedComponentDescription;
import com.adobe.cq.wcm.core.components.internal.servlets.EmbedAllOptionsDataSourceServlet.EmbeddableTypeResource;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;

@RunWith(MockitoJUnitRunner.class)
public class EmbedAllOptionsDataSourceServletTest {

    @Rule
    public AemContext context = CoreComponentTestContext.createContext("/embed/v1/datasource/embedalltypedatasource",
	    "/apps");

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private ResourceResolver resolver;

    private EmbedAllOptionsDataSourceServlet dataSourceServlet;

    @Before
    public void setUp() {
	Resource embeddable = context.resourceResolver().getResource("/apps/wcm-examples/embeddables");
	List<Resource> embeddableResources = new ArrayList<>();
	embeddableResources.add(embeddable);
	when(request.getResourceResolver()).thenReturn(resolver);

	final ValueMap properties = ResourceUtil.getValueMap(embeddable);
	final String rt = embeddable.getPath().substring("/apps".length() + 1);
	List<Resource> outputResources = new ArrayList<>();
	Resource resources = new EmbeddableTypeResource(new EmbedComponentDescription(rt, embeddable.getName(),
		properties), resolver);
	outputResources.add(resources);
	when(request.getAttribute(DataSource.class.getName())).thenReturn(
		new SimpleDataSource(outputResources.iterator()));
	when(resolver.findResources(any(), any())).thenReturn(embeddableResources.iterator());
	when(resolver.getSearchPath()).thenReturn(context.resourceResolver().getSearchPath());
	when(resolver.getResource("wcm-examples/embeddables/cq:dialog")).thenReturn(embeddable.getChild("cq:dialog"));
    }

    @Test
    public void testEmbedDataSource() throws Exception {
	context.currentResource("/apps/allembedoptionsdatasource");
	dataSourceServlet = new EmbedAllOptionsDataSourceServlet();
	dataSourceServlet.doGet(request, context.response());
	DataSource dataSource = (com.adobe.granite.ui.components.ds.DataSource) request.getAttribute(DataSource.class
		.getName());
	assertNotNull(dataSource);
	Resource resource = dataSource.iterator().next();
	ValueMap valueMap = resource.adaptTo(ValueMap.class);
	assertEquals("You tube", valueMap.get(TextValueDataResourceSource.PN_TEXT, String.class));
	assertEquals("wcm-examples/embeddables", valueMap.get(TextValueDataResourceSource.PN_VALUE, String.class));
    }

}
