package com.aem.aemfeb.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.aemfeb.core.bean.MultifieldHelper;
import com.aem.aemfeb.core.bean.NestedHelper;

@Model(adaptables = SlingHttpServletRequest.class,

        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AuthorBooksImpl {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorBooksImpl.class);

    @SlingObject
    Resource componentResource;

    @ValueMapValue
    @Default(values = "AEM LEARNING")
    private String authorname;

    @ValueMapValue
    private List<String> books;

    public String getAuthorName() {

        return authorname;
    }

    public List<String> getAuthorBooks() {
        if (books != null) {
            return new ArrayList<String>(books);
        } else {
            return Collections.emptyList();
        }
    }

    public List<Map<String, String>> getBookDetailsWithMap() {
        List<Map<String, String>> bookDetailsMap = new ArrayList<>();
        try {
            Resource bookDetail = componentResource.getChild("bookdetailswithmap");
            if (bookDetail != null) {
                for (Resource book : bookDetail.getChildren()) {
                    Map<String, String> bookMap = new HashMap<>();
                    bookMap.put("bookname", book.getValueMap().get("bookname", String.class));
                    bookMap.put("booksubject", book.getValueMap().get("booksubject", String.class));
                    bookMap.put("publishyear", book.getValueMap().get("publishyear", String.class));
                    bookDetailsMap.add(bookMap);
                }
            }
        } catch (Exception e) {
            LOG.info("\n ERROR while getting Book Details {} ", e.getMessage());
        }
        LOG.info("\n SIZE {} ", bookDetailsMap.size());
        return bookDetailsMap;
    }

    public List<MultifieldHelper> getBookDetailsWithBean() {
        List<MultifieldHelper> bookDetailsBean = new ArrayList<>();
        try {
            Resource bookDetailBean = componentResource.getChild("bookdetailswithbean");
            if (bookDetailBean != null) {
                for (Resource bookBean : bookDetailBean.getChildren()) {
                    LOG.info("\n PATH Bean {} ", bookBean.getPath());
                    LOG.info("\n BEAN PRO {} ", bookBean.getValueMap().get("bookname", String.class));

                    bookDetailsBean.add(new MultifieldHelper(bookBean));
                }
            }
        } catch (Exception e) {
            LOG.info("\n ERROR while getting Book Details With Bean {} ", e.getMessage());
        }
        return bookDetailsBean;
    }

    public List<MultifieldHelper> getBookDetailsWithNestedMultifield() {
        List<MultifieldHelper> bookDetailsNested = new ArrayList<>();
        try {
            Resource bookDetailNested = componentResource.getChild("bookdetailswithnestedmultifield");
            if (bookDetailNested != null) {
                for (Resource bookNested : bookDetailNested.getChildren()) {
                    MultifieldHelper multifieldHelper = new MultifieldHelper(bookNested);
                    if (bookNested.hasChildren()) {
                        List<NestedHelper> bookNestedList = new ArrayList<>();
                        Resource nestedResource = bookNested.getChild("bookeditions");
                        for (Resource nested : nestedResource.getChildren()) {
                            bookNestedList.add(new NestedHelper(nested));
                        }
                        multifieldHelper.setBookEditions(bookNestedList);
                    }
                    bookDetailsNested.add(multifieldHelper);
                }
            }
        } catch (Exception e) {
            LOG.info("\n ERROR while getting Book Details With Nested Multifield {} ", e.getMessage());
        }
		LOG.info("\n SIZE Multifield {} ", bookDetailsNested.size());
		return bookDetailsNested;
    }
}
