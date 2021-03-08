package connect.appinsights;

import connect.logger.Logger;
import connect.models.IdModel;
import connect.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AzureLoggerFormatterTest {
    private AzureLoggerFormatter formatter;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        final IdModel request = new IdModel();
        request.id = "ID";
        formatter = new AzureLoggerFormatter(request);
    }

    @org.junit.jupiter.api.Test
    void formatSection() {
        final String section = "Section name";
        final String expected = "ID # Section name";
        assertEquals(expected, formatter.formatSection(Logger.LEVEL_INFO, 1, section));
    }

    @org.junit.jupiter.api.Test
    void formatBlock() {
        final String block = "Hello\nWorld";
        final String expected = "\nHello\nWorld";
        assertEquals(expected, formatter.formatBlock(Logger.LEVEL_INFO, block));
    }

    @org.junit.jupiter.api.Test
    void formatCodeBlock() {
        final String block = "Hello\nWorld";
        final String expected = "\nHello\nWorld";
        assertEquals(expected, formatter.formatCodeBlock(Logger.LEVEL_INFO, block, "java"));
    }

    @org.junit.jupiter.api.Test
    void formatList() {
        final Collection<String> list = new Collection<String>().push("Hello").push("World");
        final String expected = "\nHello\nWorld";
        assertEquals(expected, formatter.formatList(Logger.LEVEL_INFO, list));
    }

    @org.junit.jupiter.api.Test
    void formatTable() {
        final Collection<Collection<String>> table = new Collection<Collection<String>>()
                .push(new Collection<String>().push("Hello").push("World"))
                .push(new Collection<String>().push("Goodbye").push("World"));
        final String expected = "\n| Hello | World |\n| Goodbye | World |";
        assertEquals(expected, formatter.formatTable(Logger.LEVEL_INFO, table));
    }

    @org.junit.jupiter.api.Test
    void formatLine() {
        final String line = "Line";
        final String expected = "ID Line";
        assertEquals(expected, formatter.formatLine(Logger.LEVEL_INFO, line));
    }

    @org.junit.jupiter.api.Test
    void getFileExtension() {
        assertEquals("log", formatter.getFileExtension());
    }

    @org.junit.jupiter.api.Test
    void copy() {
        final IdModel newRequest = new IdModel();
        newRequest.id = "NEW";
        assertNotNull(formatter.copy(newRequest));
    }
}
