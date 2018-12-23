import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageTest {
    @Test
    public void test() throws IOException, InterruptedException {
        JLabel image = new JLabel(new ImageIcon(ImageIO.read(new File("pic_src/noon.png"))));
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(image);

        JFrame frame = new JFrame();
        frame.setSize(300, 400);
        frame.add(mainPanel);
        frame.setVisible(true);
        Thread.sleep(5000);
    }
}
