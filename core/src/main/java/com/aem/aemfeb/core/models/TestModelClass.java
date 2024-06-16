package com.aem.aemfeb.core.models;

<<<<<<< HEAD
=======
import com.day.cq.wcm.msm.commons.BaseAction;
>>>>>>> de3c911 (practice1)
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TestModelClass {

<<<<<<< HEAD
    public String getName(){
        return "I'm from TestModelClass";
=======


    public String getName(){
        return "I'm from TestModel";
>>>>>>> de3c911 (practice1)
    }

}
