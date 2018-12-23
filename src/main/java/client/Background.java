package client;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
    private Image image;

    public Background(){
        setOpaque(false);
        setLayout(null);
    }

    public void setImage(Image image){
        this.image=image;
    }

    @Override
    protected void paintComponent(Graphics graphics){
        if (image!=null){
            graphics.drawImage(image,0,0,this);
        }
        super.paintComponent(graphics);
    }
}
