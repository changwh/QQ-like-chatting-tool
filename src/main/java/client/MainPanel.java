package client;

import server.User;
import util.HoverPressUtil;
import util.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainPanel extends JFrame {

    private JLabel headimgLb, headimgbgLb;
    private static JLabel nicknameLb, sloganLb;
    private JButton btnClose, btnMin, btnMessage, btnFriend, btnGroup;
    private JScrollPane messages = new JScrollPane();
    private JScrollPane friends = new JScrollPane();
    private JScrollPane groups = new JScrollPane();

    private Point point;
    private Container container = this.getContentPane();

    private String account;
    private String headPath;
    private String nickname;
    private String status;
    private String slogan;
    private String sex;

    FriendList friendList;

    private String ip;
    private String port;

    private static Socket socket;

    public MainPanel(String userId) throws IOException, ClassNotFoundException {
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File("pic_src/title.png")));
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point = e.getPoint();
            }
        });
        // 面板的鼠标拖拽事件中移动窗体
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point newPoint = e.getLocationOnScreen();
                setLocation(newPoint.x - point.x, newPoint.y - point.y);
            }
        });

        // 设置面板位置
        new Thread() {  // 创建新线程
            public void run() {
                int screenWidth, screenHeight;
                Toolkit kit = Toolkit.getDefaultToolkit();
                Dimension screenSize = kit.getScreenSize();
                screenWidth = (int) screenSize.getWidth();
                screenHeight = (int) screenSize.getHeight();
                for (int i = 0; i <= 674; i += 85) {    // 循环拉伸窗体
                    setBounds(screenWidth - 284, 0, 284, i);    // 不断设置窗体大小与位置
                    try {
                        Thread.sleep(10);    // 线程休眠10毫秒
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }.start();  // 启动线程

        // 昵称
        nicknameLb = new JLabel();
        nicknameLb.setText(userId);
        nicknameLb.setBounds(80, 39, 80, 17);
        nicknameLb.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        nicknameLb.setForeground(Color.BLACK);
        container.add(nicknameLb);

        // 签名
        slogan = "请设置签名";
        sloganLb = new JLabel(slogan);
        sloganLb.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        sloganLb.setForeground(Color.BLACK);
        sloganLb.setBounds(80, 54, 200, 20);
        container.add(sloganLb);

        // 头像及背景
        headimgLb = new JLabel(new ImageIcon("pic_src/headimg2.jpg"));
        headimgLb.setBounds(11, 41, 61, 60);
        headimgLb.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
                headimgLb.setBorder(BorderFactory.createLineBorder(new Color(147, 112, 219), 0));
            }

            public void mouseEntered(MouseEvent e) {
                headimgLb.setBorder(BorderFactory.createLineBorder(new Color(199, 21, 133), 2));
            }
        });
        container.add(headimgLb);

        headimgbgLb = new JLabel(new ImageIcon("pic_src/headimgbg.png"));
        headimgbgLb.setBounds(9, 39, 65, 65);
        container.add(headimgbgLb);

        // 消息面板按钮
        btnMessage = HoverPressUtil.getBtnButton("pic_src/xiang1.png",
                "pic_src/xiang1_press.png",
                "pic_src/xiang1_press.png");
        btnMessage.setToolTipText("信息");
        btnMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                friends.setVisible(false);
                groups.setVisible(false);

                messages.setVisible(true);
                container.repaint();
            }
        });
        btnMessage.setBounds(14, 105 + 2, 34, 40);
        container.add(btnMessage);

        // 好友面板按钮
        btnFriend = HoverPressUtil.getBtnButton("pic_src/xiang2.png",
                "pic_src/xiang2_press.png",
                "pic_src/xiang2_press.png");
        btnFriend.setToolTipText("好友");
        btnFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groups.setVisible(false);
                messages.setVisible(false);

                friends.setVisible(true);
                container.repaint();
            }
        });
        btnFriend.setBounds(72, 105, 34, 40);
        container.add(btnFriend);

        // 群面板按钮
        btnGroup = HoverPressUtil.getBtnButton("pic_src/xiang4.png",
                "pic_src/xiang4_press.png",
                "pic_src/xiang4_press.png");
        btnGroup.setToolTipText("群会话");
        btnGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messages.setVisible(false);
                friends.setVisible(false);

                groups.setVisible(true);
                container.repaint();
            }
        });
        btnGroup.setBounds(178, 105, 34, 40);
        container.add(btnGroup);

        // 好友面板
        friends.setBorder(null);
        friends.setBounds(2, 145, 278, 430);

        User user = new User();
        user.setUserId(userId);
        user.setPasswd(new String(Client.getPsw().getPassword()));

        // 请求在线好友，更新好友列表
        if (sendLoginInfoToServer(user)) {
            friendList = new FriendList(user.getUserId());
            friends.add(friendList);
            friends.setViewportView(friendList);

            ManageFriendList.addFriendList(userId, friendList);

            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(user.getUserId()).getSocket().getOutputStream());

            Message message = new Message();
            message.setMesType(Message.MessageType.message_get_onLineFriend);
            message.setSender(userId);
            oos.writeObject(message);
        }
        container.add(friends);
        friends.setVisible(true);

        // 群会话面板
        groups.setBorder(null);
        groups.setBounds(2, 182, 278, 430);
        groups.setViewportView(new JLabel("               群会话面板尚未完成,敬请期待"));

        container.add(groups);

        // 关闭按钮
        btnClose = HoverPressUtil.getBtnButton("pic_src/Mainclose.png",
                "pic_src/Mainclose_hover.png",
                "pic_src/Mainclose_press.png");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                MainPanel.this.dispose();
            }
        });
        btnClose.setBounds(256, -2, 28, 20);
        container.add(btnClose);

        // 最小化按钮
        btnMin = HoverPressUtil.getBtnButton("pic_src/Mainmin.png",
                "pic_src/Mainmin_hover.png",
                "pic_src/Mainmin_press.png");
        btnMin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        btnMin.setBounds(228, -1, 28, 20);
        container.add(btnMin);

        // 背景
        Background background = new Background();
        background.setImage(ImageIO.read(new File("pic_src/mainbg_modify.png")));
        background.setBounds(0, 0, 284, 674);
        container.add(background);
        container.repaint();
    }

    public boolean sendLoginInfoToServer(Object object) throws IOException, ClassNotFoundException {
        boolean isSend = false;

        socket = new Socket("127.0.0.1", 6666);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(object);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) ois.readObject();

        // 验证登录
        if (message.getMesType() == Message.MessageType.message_login_succeed) {
            // 创建一个和服务器端保持通讯连接的线程
            ClientConServerThread ccst = new ClientConServerThread(socket);
            ccst.start();
            ManageClientConServerThread.addClientConServerThread
                    (((User) object).getUserId(), ccst);
            isSend = true;
        } else {
            socket.close();
        }

        return isSend;
    }

}
