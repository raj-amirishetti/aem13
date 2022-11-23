package com.aem.aemfeb.core.models;

import java.util.Date;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultifieldHelper {

	private String bookName;
	private Date publishDate;
	private int copies;

	private String bookSubject;
	private List<NestedHelper> bookEditions;

	private static final Logger LOG = LoggerFactory.getLogger(MultifieldHelper.class);

	public MultifieldHelper(Resource resource) {
		try {

			if (resource.getValueMap().get("bookname", String.class) != null) {
				this.bookName = resource.getValueMap().get("bookname", String.class);
			}

			if (resource.getValueMap().get("publishdate", Date.class) != null) {
				this.publishDate = resource.getValueMap().get("publishdate", Date.class);
			}
			if (resource.getValueMap().get("copies", Integer.class) != null) {
				this.copies = resource.getValueMap().get("copies", Integer.class);

			}

			if (resource.getValueMap().get("booksubject", String.class) != null) {
				this.bookSubject = resource.getValueMap().get("booksubject", String.class);
			}

		} catch (Exception e) {
			LOG.info("\n BEAN ERROR : {}", e.getMessage());
		}

	}

	public String getBookName() {
		return bookName;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public int getCopies() {
		return copies;
	}

	public String getBookSubject() {
		return bookSubject;
	}

	public List<NestedHelper> getBookEditions() {
		return bookEditions;
		
	}

	public void setBookEditions(List<NestedHelper> bookEditions) {
		this.bookEditions = bookEditions;
	}
}
