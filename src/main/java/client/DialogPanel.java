package client;

import util.HoverPressUtil;
import util.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.util.Date;

public class DialogPanel extends JFrame implements ActionListener {

    private static String ownerId, friendId, friendNickname, friendSlogan;

    private JButton min, max, close, send, close2, fileTrans, messageRecord;
    private JLabel friendHeadimgLb, friendNicknameLb, friendSloganLb;
    private JTextArea recvAndSentMsg, editMsg;
    private JScrollPane recvAndSentMsgSP, editMsgSP;
    private JPanel recvAndSentMsgP, editMsgP;
    private Point point;


    public DialogPanel(String ownerId, String friendId) throws IOException {
        this.ownerId = ownerId;
        this.friendId = friendId;
        // TODO: 2018/12/26 通过好友ID获取昵称
        this.friendNickname = friendId;
        // TODO: 2018/12/26 通过好友ID获取签名
        this.friendSlogan = "好友签名";

        this.setUndecorated(true);
        this.setSize(555, 535);
        this.setTitle(friendNickname);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setIconImage(ImageIO.read(new File("pic_src/headimg/" + friendId + ".png")));
        this.setVisible(true);


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

        // 好友头像
        friendHeadimgLb = new JLabel(new ImageIcon("pic_src/headimg/" + friendId + ".png"));
        friendHeadimgLb.setBounds(11, 11, 44, 44);
        this.add(friendHeadimgLb);

        // 好友昵称
        // TODO: 2018/12/26 根据好友ID获取昵称
        friendNicknameLb = new JLabel(friendNickname);
        friendNicknameLb.setBounds(56, 12, 60, 20);
        this.add(friendNicknameLb);

        // 好友签名
        // TODO: 2018/12/26 根据好友ID获取签名
        friendSloganLb = new JLabel(friendSlogan);
        friendSloganLb.setBounds(60, 33, 300, 20);
        this.add(friendSloganLb);

        // 文件传输按钮
        fileTrans = HoverPressUtil.getBtnButton("pic_src/dialog/wjcs.png", "pic_src/dialog/wjcs_hover.png", "pic_src/dialog/wjcs.png");
        fileTrans.setBounds(5, 56, 34, 34);
        fileTrans.addActionListener(this);
        this.add(fileTrans);

        // 对话框
        recvAndSentMsg = new JTextArea();
        recvAndSentMsg.setBorder(null);
        recvAndSentMsg.setLineWrap(true);
        recvAndSentMsg.setEditable(false);
        recvAndSentMsgSP = new JScrollPane(recvAndSentMsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        recvAndSentMsgP = new JPanel();
        recvAndSentMsgP.setLayout(new BorderLayout());
        recvAndSentMsgP.setBounds(5, 92, 545, 285);
        recvAndSentMsgP.add(recvAndSentMsgSP);
        this.add(recvAndSentMsgP);

        // 消息编辑框
        editMsg = new JTextArea();
        editMsg.setBorder(null);
        editMsg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // TODO: 2018/12/26 JTextArea中回车键不能触发事件
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    try {
//                        sendMessage(DialogPanel.this);
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
//                }
                super.keyTyped(e);
            }
        });
        editMsgSP = new JScrollPane(editMsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        editMsgP = new JPanel();
        editMsgP.setLayout(new BorderLayout());
        editMsgP.setBounds(5, 410, 545, 90);
        editMsgP.add(editMsgSP);
        this.add(editMsgP);

        // 聊天记录
        messageRecord = HoverPressUtil.getBtnButton("pic_src/dialog/xiaoxi.png", "pic_src/dialog/xiaoxi_hover.png", "pic_src/dialog/xiaoxi.png");
        messageRecord.setBounds(450, 380, 103, 28);
        messageRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MessageRecord(DialogPanel.this);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.add(messageRecord);

        // 最小化按钮
        min = HoverPressUtil.getBtnMin();
        min.setBounds(464, 2, 25, 16);
        min.setIcon(new ImageIcon("pic_src/dialog/min.png"));
        min.setRolloverIcon(new ImageIcon("pic_src/dialog/min_hover.png"));
        min.setPressedIcon(new ImageIcon("pic_src/dialog/min_p.png"));
        min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        this.add(min);

        // 最大化按钮
        max = HoverPressUtil.getBtnClose();
        max.setBounds(492, 2, 25, 16);
        max.setIcon(new ImageIcon("pic_src/dialog/big.png"));
        max.setRolloverIcon(new ImageIcon("pic_src/dialog/big_hover.png"));
        max.setPressedIcon(new ImageIcon("pic_src/dialog/big_p.png"));
        max.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        this.add(max);

        // 左上角关闭按钮
        close = HoverPressUtil.getBtnClose();
        close.setBounds(520, 2, 33, 16);
        close.setIcon(new ImageIcon("pic_src/dialog/close.png"));
        close.setRolloverIcon(new ImageIcon("pic_src/dialog/close_hover.png"));
        close.setPressedIcon(new ImageIcon("pic_src/dialog/close_p.png"));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogPanel.this.dispose();
            }
        });
        this.add(close);

        // 下方关闭按钮
        close2 = HoverPressUtil.getBtnButton(
                "pic_src/dialog/guanbi.png",
                "pic_src/dialog/guanbi_hover.png",
                "pic_src/dialog/guanbi_p.png");
        close2.setBounds(380, 503, 71, 24);
        close2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogPanel.this.dispose();
            }
        });
        this.add(close2);

        // 发送按钮
        send = HoverPressUtil.getBtnButton(
                "pic_src/dialog/fasong.png",
                "pic_src/dialog/fasong_hover.png",
                "pic_src/dialog/fasong_p.png");
        send.setBounds(460, 503, 83, 24);
        send.addActionListener(this);
        this.add(send);

        // 背景
        Background background = new Background();
        background.setImage(ImageIO.read(new File("pic_src/dialog/dialogbg_modify.png")));
        background.setBounds(0, 0, 555, 535);
        this.add(background);

    }

    public void sendMessage(DialogPanel dialogPanel) throws IOException {

        // 构造一个消息
        Message message = new Message();
        message.setMesType(Message.MessageType.message_comm_mes);
        message.setSender(dialogPanel.getOwnerId());
        message.setReceiver(dialogPanel.getFriendId());
        message.setCon(dialogPanel.editMsg.getText());

        // 获取日期、时间
        DateFormat dateFormat = DateFormat.getDateInstance();
        String date = dateFormat.format(new Date());
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        String time = timeFormat.format(new Date());
        message.setSendTime(date + " " + time);

        // 对话框更新、编辑框清空
        dialogPanel.recvAndSentMsg.append("我：" + "       " + date + "  " + time + "\r\n" + dialogPanel.editMsg.getText() + "\r\n");
        String msgStr = message.getSender() + "：" + "       " + message.getSendTime() + "\r\n" + message.getCon() + "\r\n";
        saveRecord(msgStr, DialogPanel.this);
        dialogPanel.editMsg.setText(null);

        // 将消息发送给服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConServerThread.getClientConServerThread(
                            dialogPanel.getOwnerId()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public void showMessage(Message message) throws IOException {
        String messageStr = message.getSender() + "：" + "       " + message.getSendTime() + "\r\n" + message.getCon() + "\r\n";
        recvAndSentMsg.append(messageStr);
        saveRecord(messageStr, DialogPanel.this);
    }

    public void saveRecord(String messageStr, DialogPanel dialogPanel) throws IOException {
        File record = new File("data/" + dialogPanel.getOwnerId() + "/" + dialogPanel.getFriendId() + ".txt");
        // 上级目录（用户ID）不存在时创建上级目录
        if (!record.getParentFile().exists()) {
            record.getParentFile().mkdir();
        }
        // 与好友的聊天记录不存在时创建聊天记录文件
        if (!record.exists()) {
            record.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(record, true);
        byte[] infoInBytes = messageStr.getBytes("utf-8");
        fos.write(infoInBytes);
        fos.flush();
        fos.close();
    }

    public void sendFile(DialogPanel dialogPanel) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.showDialog(new JLabel(), "选择文件");
        File file = chooser.getSelectedFile();
//        System.out.println(file.getPath());

        // TODO: 2018/12/27 发送文件
//        FileInputStream fis = new FileInputStream(file);
//        DataOutputStream dos = new DataOutputStream(ManageClientConServerThread.getClientConServerThread(
//                dialogPanel.getOwnerId()).getSocket().getOutputStream());
//        dos.writeUTF(file.getName());
//        dos.flush();
//        dos.writeLong(file.length());
//        dos.flush();
//
//        byte bytes[] = new byte[1024];
//        int length = 0;
//        while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
//            dos.write(bytes, 0, length);
//            dos.flush();
//        }

        editMsg.append(dialogPanel.getOwnerId() + "向" + dialogPanel.getFriendId() + "发送了" + file.getPath());
        sendMessage(dialogPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == send) {
            try {
                sendMessage(DialogPanel.this);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == fileTrans) {
            try {
                sendFile(DialogPanel.this);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getFriendId() {
        return friendId;
    }

}
