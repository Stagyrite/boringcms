package boringcms.hl7v2parser;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class HL7MessageTest {

    @Test
    public void testSystemName() {
        String source = "MSH|^~\\&|SENDER_APP";
        HL7Message message = new HL7Message(source);
        Map<String, List<String>> parts = message.getParts();
        String systemName = parts.get("MSH.3.1").get(0);
        Assert.assertEquals("SENDER_APP", systemName);
    }

    @Test
    public void testMarkdownTableConverter() throws IOException {
        Map<String, List<String>> items = parseSampleMessage();
        StringBuilder table = new StringBuilder();
        int columns = 0;

        for (List<String> values : items.values()) {

            if (columns < values.size()) {
                columns = values.size();
            }

        }

        table.append("| Item | ");

        for (int i = 1; i <= columns; i++) {
            table.append(" Value #").append(i);
        }

        for (Map.Entry<String, List<String>> entry : items.entrySet()) {
            List<String> values = entry.getValue();
            table.append("| ").append(entry.getKey()).append(" | ");

            for (int i = 0; i < columns; i++) {

                if (i < values.size()) {
                    table.append(values.get(i));
                }

                table.append(" | ");
            }

            table.append("\n");
        }

        System.out.println(table);
    }

    private Map<String, List<String>> parseSampleMessage() throws IOException {
        URL resource = getClass().getResource("validExample.hl7");
        Assert.assertNotNull(resource);
        File inputFile = new File(resource.getFile());
        String validExample = new String(Files.readAllBytes(inputFile.toPath()));
        HL7Message parser = new HL7Message(validExample);
        return parser.getParts();
    }

}
