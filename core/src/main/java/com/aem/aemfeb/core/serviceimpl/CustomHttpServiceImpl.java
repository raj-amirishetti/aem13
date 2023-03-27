package com.aem.aemfeb.core.serviceimpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.osgi.services.HttpClientBuilderFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.aemfeb.core.service.CustomHttpService;
import com.google.gson.Gson;

@Component(immediate = true, service = CustomHttpService.class)
public class CustomHttpServiceImpl implements CustomHttpService {

	private Gson gson = new Gson();

	@Reference
	private HttpClientBuilderFactory httpFactory;

	protected HttpClient getHttpClient() {
		return httpFactory.newBuilder().build();
	}


	
	@Override
	public <T> T makeGetCall(String url, Class<T> t, Map<String, String> params) {

		String response = null;

		try {
			URIBuilder builder = createBuilderWithParams(url, params);
			HttpGet getRequest = new HttpGet(builder.build());
			HttpClient httpClient = getHttpClient();
			HttpResponse httpResponse = httpClient.execute(getRequest);
			response = IOUtils.toString(httpResponse.getEntity().getContent(), "utf-8");

		} catch (URISyntaxException | IOException e) {
			// log.error("Exception: {}",e.getMessage());
		}

		return t.equals(String.class) ? t.cast(response) : gson.fromJson(response, t);
	}

	private URIBuilder createBuilderWithParams(String url, Map<String, String> params) throws URISyntaxException {
		URIBuilder builder = new URIBuilder(url);
		OptionalUtil.resolveNestedField(() -> params).ifPresent(httpParams -> params.forEach((k, v) -> {
			try {
				builder.addParameter(k, URLDecoder.decode(v, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// log.error("UnsupportedEncodingException : {} ", e.getMessage());
			}
		}));
		return builder;

	}
}