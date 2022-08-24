package com.aem.aemfeb.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class)
public class RequestAdapter {

    @Inject
    private SlingHttpServletRequest request;

    public String getSuffix() {
        return request.getRequestPathInfo().getSuffix();
    }
}