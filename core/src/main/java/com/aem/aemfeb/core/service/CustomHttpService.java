package com.aem.aemfeb.core.service;

import java.util.Map;

public interface CustomHttpService {

	<T> T makeGetCall(String url, Class<T> t, Map<String, String> params);
}
