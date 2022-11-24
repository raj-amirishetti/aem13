package com.aem.aemfeb.core.serviceimpl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

public class SampleWcmUsePojo extends WCMUsePojo {

	private static final Logger log = LoggerFactory.getLogger(SampleWcmUsePojo.class);

	private HashMap<String, String> links = new HashMap<>();

	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub
		String linkpath[] = getProperties().get("linkpath", String[].class);
		String linktitle[] = getProperties().get("linktitle", String[].class);

		for (int i = 0; i < linktitle.length; i++) {
			links.put(linktitle[i], linkpath[i]);
		}

		log.info(links.toString());
	}

	public HashMap<String, String> getLinks() {
		return links;

	}
}
