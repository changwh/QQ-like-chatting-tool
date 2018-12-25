package util;

import javax.swing.*;
import java.awt.*;

public class HoverPressUtil {
    public static JButton getBtnText(String str) {
        JButton button = new JButton(str);
        button.setFont(new Font("宋体", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    public static JButton getBtnIcon(String iconPath) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(iconPath));
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    public static JButton getBtnButton(String iconPath, String rollOverIconPath, String pressIconPath) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(iconPath));
        button.setRolloverIcon(new ImageIcon(rollOverIconPath));
        button.setPressedIcon(new ImageIcon(pressIconPath));
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    public static JButton getBtnIconDoublePress(String iconPath, String pressIconPath1, String pressIconPath2) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(iconPath));
        button.setPressedIcon(new ImageIcon(pressIconPath1));
        button.setPressedIcon(new ImageIcon(pressIconPath2));
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    public static JButton getBtnMin() {
        JButton button = new JButton();
        button.setIcon(new ImageIcon("pic_src/min.png"));
        button.setRolloverIcon(new ImageIcon("pic_src/min_hover.png"));
        button.setPressedIcon(new ImageIcon("pic_src/min_press.png"));
        button.setToolTipText("最小化");
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    public static JButton getBtnClose() {
        JButton button = new JButton();
        button.setIcon(new ImageIcon("pic_src/close.png"));
        button.setRolloverIcon(new ImageIcon("pic_src/close_hover.png"));
        button.setPressedIcon(new ImageIcon("pic_src/close_press.png"));
        button.setToolTipText("关闭");
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }


}
