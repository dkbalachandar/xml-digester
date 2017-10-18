package com;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookConfigurationDigesterTest {

    private static Logger LOGGER = LoggerFactory.getLogger(BookConfigurationDigester.class);

    @Test
    public void testBookConfiguration() {
        BookConfigurationDigester  bookConfigurationDigester = new BookConfigurationDigester();
        bookConfigurationDigester.load();
        List<BookConfigBean> configBeanList = bookConfigurationDigester.getBookConfigBeans();
        LOGGER.debug("configBeanList:{}",configBeanList);
        assertNotNull(configBeanList);
        assertEquals(4 , configBeanList.size());
    }
}