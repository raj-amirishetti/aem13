package com.aem.aemfeb.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import java.util.HashMap;
import java.util.Map;

@Model(adaptables = SlingHttpServletRequest.class)
public class SelfModel {

    private static final Map<String, String> CONTENT = new HashMap<>();
    
    static {
        CONTENT.put("suffix1", "Content 1");
        CONTENT.put("suffix2", "Content 2");
    }

    @Self
    private RequestAdapter requestAdapter;

    public String getContent() {
        return CONTENT.get(requestAdapter.getSuffix());
    }
}