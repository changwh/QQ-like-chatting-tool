package client;

import util.HoverPressUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MessageRecord extends JFrame {
    private JTextArea record;
    private JScrollPane recordSP;
    private JLabel label;
    private JButton close, min;
    private Point point;

    public MessageRecord(DialogPanel dialogPanel) throws IOException {
        this.setBounds(440, 256, 400, 380);
        this.setUndecorated(true);//设置无边框
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point = e.getPoint();
            }
        });
        // 面板的鼠标拖曳事件中移动窗体
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point newpoint = e.getLocationOnScreen();
                setLocation(newpoint.x - point.x, newpoint.y - point.y);
            }
        });

        // 标题
        label = new JLabel("与" + dialogPanel.getFriendId() + "聊天记录");
        label.setFont(new Font("隶书", Font.PLAIN, 20));
        label.setBounds(130, 0, 150, 30);
        this.add(label);

        // 记录
        record = new JTextArea();
        recordSP = new JScrollPane(record);
        recordSP.setBounds(0, 26, 400, 350);
        this.add(recordSP);

        getRecord(dialogPanel);

        // 最小化按钮
        min = HoverPressUtil.getBtnMin();
        min.setBounds(342, -4, 26, 26);
        min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        this.add(min);

        // 左上角关闭按钮
        close = HoverPressUtil.getBtnClose();
        close.setBounds(372, -4, 26, 26);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        this.add(close);

    }

    private void getRecord(DialogPanel dialogPanel) throws IOException {
        File file = new File("data/" + dialogPanel.getOwnerId() + "/" + dialogPanel.getFriendId() + ".txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        // 与好友的聊天记录不存在时创建聊天记录文件
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str = "";
            String result = "";
            while ((str = br.readLine()) != null) {
                result += str + "\r\n";
            }
            record.setText(result);
            br.close();
            fr.close();
        }
    }
}
