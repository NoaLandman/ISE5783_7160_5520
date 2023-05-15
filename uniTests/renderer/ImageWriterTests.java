package renderer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

class ImageWriterTests {
    /**
     * Test method for {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
     */
    @Test
    public void testImageWriter() {
        ImageWriter writer = new ImageWriter("firstImage", 800, 500);
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                if (i % 50 == 0 || j % 50 == 0 || i == 799 || j == 499)
                    writer.writePixel(j, i, new primitives.Color(Color.black));
                else
                    writer.writePixel(j, i, new primitives.Color(Color.blue));
            }
        }
        writer.writeToImage();

    }
}