package no.avexis.image.uploader.transformers;

import no.avexis.image.uploader.models.ResolutionTemplate;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

public class BasicImageTransformerNoMockTest {

    private BasicImageTransformer basicImageTransformer;
    private BufferedImage bufferedImage;

    @Before
    public void buildBufferedImage() throws Exception {
        bufferedImage = new BufferedImage(1920, 1200, 1);
        basicImageTransformer = new BasicImageTransformer();
    }
    @Test
    public void toBase64() throws Exception {
        final int expectedWidth = 5;
        final int expectedHeight = 5;
        final String expectedString = "data:image/jpg;base64,/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAFAAUDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD5/ooooA//2Q==";
        final ResolutionTemplate template = new ResolutionTemplate("x-small", 5, 5, true, true);
        final BufferedImage bufferedImage = basicImageTransformer.resizeBufferedImage(this.bufferedImage, template);
        assertEquals(expectedWidth, bufferedImage.getWidth());
        assertEquals(expectedHeight, bufferedImage.getHeight());

        final String base64String = basicImageTransformer.toBase64(bufferedImage, "jpg");
        assertEquals(expectedString, base64String);
    }
}