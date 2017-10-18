package com;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.Substitutor;
import org.apache.commons.digester3.substitution.MultiVariableExpander;
import org.apache.commons.digester3.substitution.VariableSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookConfigurationDigester {

    private static Logger LOGGER = LoggerFactory.getLogger(BookConfigurationDigester.class);

    private static final String MARKER = "$";

    private List<BookConfigBean> bookConfigBeans = new ArrayList<>();

    public void addBookConfigElement(BookConfigBean bean) {
        bookConfigBeans.add(bean);
    }

    public void load() {

        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("BOOK1_PRICE", "$10");
        sourceMap.put("BOOK2_PRICE", "$10");
        sourceMap.put("BOOK3_PRICE", "$20");
        sourceMap.put("BOOK4_PRICE", "$10");
        MultiVariableExpander mvExpander = new MultiVariableExpander();
        mvExpander.addSource(MARKER, sourceMap);
        Substitutor substitutor = new VariableSubstitutor(mvExpander);
        Digester digester = new Digester();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("book.xml")) {
            digester.push(this);
            digester.addObjectCreate("books/book", BookConfigBean.class);
            digester.addSetNext("books/book", "addBookConfigElement");
            digester.addCallMethod("books/book/title", "setTitle", 0);
            digester.addCallMethod("books/book/author", "setAuthor", 0);
            digester.addCallMethod("books/book/price", "setPrice", 0);
            digester.setSubstitutor(substitutor);
            digester.parse(is);
        }
        catch (Exception e) {
            LOGGER.error("Error parsing configuration xml", e);
            throw new IllegalArgumentException("Error parsing configuration xml", e);
        }
        finally {
            digester.clear();
        }
    }

    public List<BookConfigBean> getBookConfigBeans() {
        return bookConfigBeans;
    }
}
