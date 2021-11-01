import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class TranslationObjectFinderTest {
    String SOURCE_NAME = "translate.breezy.js";

    String sourceUri;
    String sourceCode;

    @BeforeEach
    void setUp() throws IOException {
        URL url = ClassLoader.getSystemResource(SOURCE_NAME);
        sourceUri = url.toString();
        sourceCode = new BufferedReader(new InputStreamReader(url.openStream()))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    @Test
    void testFindTranslations() {
        JSObjectHashMap object = new TranslationObjectFinder(sourceCode, sourceUri).find();
        JSObjectHashMap ptbr = (JSObjectHashMap) object.get("pt-br");

        assertEquals("Remoto ao redor do mundo", ptbr.get("%LABEL_POSITION_TYPE_WORLDWIDE%"));
    }
}
