package com.aem.aemfeb.core.models;

import com.day.cq.wcm.msm.commons.BaseAction;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TestModelClass {

    public String getName(){
        return "I'm from TestModel";
    }

}
