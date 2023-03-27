package com.aem.aemfeb.core.service;



import org.apache.sling.api.resource.ResourceResolver;

public interface ResourceResolverService {

    /**
     * This method returns the instance of resource resolver
     *
     * @return @{@link ResourceResolver}
     */
    ResourceResolver getResourceResolver();
}