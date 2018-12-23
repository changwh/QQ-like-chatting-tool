package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginingThread extends JFrame implements ActionListener {
    private JButton login, close, min;
    private JLabel headimg, headimgbg, loginLabel, threadbg;

    Client clientFrame;
    Container container = this.getContentPane();


    public LoginingThread(Client clientFrame) {
        this.clientFrame = clientFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
