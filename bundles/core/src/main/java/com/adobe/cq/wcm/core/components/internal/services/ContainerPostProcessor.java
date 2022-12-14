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
package com.adobe.cq.wcm.core.components.internal.services;

import java.util.ArrayList;
import java.util.List;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.servlets.post.Modification;
import org.apache.sling.servlets.post.SlingPostProcessor;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.internal.models.v1.PanelContainerImpl;

@Component(service = SlingPostProcessor.class,
           property = {
                   Constants.SERVICE_RANKING + ":Integer=" + Integer.MAX_VALUE
           })
public class ContainerPostProcessor implements SlingPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerPostProcessor.class);
    private static final String PARAM_ORDERED_CHILDREN = "itemOrder";
    private static final String PARAM_DELETED_CHILDREN = "deletedItems";

    @Override
    public void process(SlingHttpServletRequest request, List<Modification> modifications) throws Exception {
        ResourceResolver resourceResolver = request.getResource().getResourceResolver();
        ArrayList<Modification> addedModifications = new ArrayList<Modification>();
        if (accepts(request, resourceResolver)) {
            Resource container = request.getResource();
            try {
                handleOrder(container, request);
                handleDelete(container, request, resourceResolver, addedModifications);
            } catch (RepositoryException e) {
                LOGGER.error("Could not order items of the container at {}", container.getPath(), e);
            }
        }
        modifications.addAll(addedModifications);
    }

    protected void handleOrder(Resource container, SlingHttpServletRequest request) throws RepositoryException {
        String[] orderedChildrenNames = StringUtils.split(request.getParameter(PARAM_ORDERED_CHILDREN), ",");
        if (orderedChildrenNames != null && orderedChildrenNames.length > 0) {
            final Node containerNode = container.adaptTo(Node.class);
            if (containerNode != null) {
                for (int i = orderedChildrenNames.length - 1; i >= 0; i--) {
                    if (i == orderedChildrenNames.length - 1 && containerNode.hasNode(orderedChildrenNames[i])) {
                        containerNode.orderBefore(orderedChildrenNames[i], null);
                    } else if (containerNode.hasNode(orderedChildrenNames[i]) && containerNode.hasNode(orderedChildrenNames[i])) {
                        containerNode.orderBefore(orderedChildrenNames[i], orderedChildrenNames[i + 1]);
                    }
                }
            }
        }
    }

    protected void handleDelete(Resource container, SlingHttpServletRequest request, ResourceResolver resolver,
                                List<Modification> addedModifications)
            throws PersistenceException, RepositoryException {
        String[] deletedChildrenNames = StringUtils.split(request.getParameter(PARAM_DELETED_CHILDREN), ",");
        if (deletedChildrenNames != null && deletedChildrenNames.length > 0) {
            for (String childName : deletedChildrenNames) {
                Resource child = container.getChild(childName);
                if (child != null) {
                    String deletedPath = child.getPath();
                    resolver.delete(child);
                    addedModifications.add(Modification.onDeleted(deletedPath));
                }
            }
        }
    }

    private boolean accepts(SlingHttpServletRequest request, ResourceResolver resolver) {
        return resolver.isResourceType(request.getResource(), PanelContainerImpl.RESOURCE_TYPE);
    }
}
