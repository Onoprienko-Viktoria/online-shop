package com.onoprienko.onlineshop.web.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PageGeneratorTest {

    @Test
    void getPageReturnsPage() throws IOException {
        PageGenerator pageGenerator = new PageGenerator("/temp");
        String page = pageGenerator.getPage("test.html");

        assertNotNull(page);
        assertEquals("<body>test</body>", page);
    }

    @Test
    void getPageWithMessageReturnsPage() throws IOException {
        PageGenerator pageGenerator = new PageGenerator("/temp");
        String page = pageGenerator.getPageWithMessage("test.html", "HEllo");

        assertNotNull(page);
        assertEquals("<body>test</body>", page);
    }
}