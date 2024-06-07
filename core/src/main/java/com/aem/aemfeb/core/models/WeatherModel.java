package com.aem.aemfeb.core.models;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.aem.aemfeb.core.service.CustomHttpService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Model(adaptables= Resource.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class WeatherModel {
	
	
	@Inject @Default(values ="Hyderabad")
	private String location;
	
	@OSGiService
	private CustomHttpService customHttpService;
	
	private int temparature;
	
	private String weatherDescription;
	
	@PostConstruct
	public  void init() {
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("q", location);
		params.put("APPID", "239629dcd19a0a10978895b64ed1ff2c");
		
		String weatherInfo =customHttpService.makeGetCall("https://api.openweathermap.org/data/2.5/weather", String.class, params);
		
		JsonObject jsonObject = new Gson().fromJson(weatherInfo	, JsonObject.class);
		
		JsonObject weatherDescriptionObj = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
		
		weatherDescription = weatherDescriptionObj.get("description").getAsString();
		
		temparature = jsonObject.get("main").getAsJsonObject().get("temp").getAsInt();		
		
		
	}

	public String getLocation() {
		return location;
	}

	public int getTemparature() {
		return (int) (temparature -273.15);
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}
	
	
	
	
	
	

}
