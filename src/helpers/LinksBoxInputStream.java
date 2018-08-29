package src.helpers;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.InputStream;

public class LinksBoxInputStream extends InputStream {
    byte[] content;
    int pointer;

    public LinksBoxInputStream(final TextArea links) {
        content = links.getText().getBytes();
        pointer = 0;
    }

    public int read() throws IOException {
        // While the pointer doesn't go past the length, do this
        if (pointer >= content.length) {
            return -1;
        }
        return this.content[pointer++];
    }
}
