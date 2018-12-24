package client;

import com.sun.xml.internal.bind.v2.TODO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Client extends JFrame implements ActionListener {

    private JButton login, setting, close, min, loginCancel;
    private JLabel headimg, headimgbg, loginLabel, autoLogin, rememberMe, loginCancelLabel, linkTipPanel;
    private static JTextField user;
    private static JPasswordField psw;
    private static Background background;
    private Point point;
    private Container container = this.getContentPane();

    static double screenWidth, screenHeight;

    public void launch() throws IOException {
        this.setSize(437, 340);
        this.setIconImage(ImageIO.read(new File("pic_src/title.png")));
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
        this.setLocation((int) (screenWidth / 2 - this.getWidth() / 2), (int) (screenHeight / 2 - this.getHeight() / 2));
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
        this.setLayout(null);

        // 登录头像及背景
        headimg = new JLabel(new ImageIcon("pic_src/headimg.png"));
        headimg.setBounds(42, 197, 80, 79);
        container.add(headimg);

        headimgbg = new JLabel(new ImageIcon("pid_src/headimgbg.png"));
        headimgbg.setBounds(40, 195, 84, 84);
        container.add(headimgbg);

        // 用户名输入框
        user = new JTextField(1000);
        user.addActionListener(this);
        user.setBorder(BorderFactory.createLineBorder(Color.black));
        user.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user.setBorder(BorderFactory.createLineBorder(Color.red));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                user.setBorder(BorderFactory.createLineBorder(Color.blue));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                user.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        });
        user.setFont(new Font("宋体", Font.BOLD, 19));
        user.setForeground(Color.black);
        user.setToolTipText("请输入用户账号");
        user.setBounds(140, 197, 174, 28);
        container.add(user);

        // 密码输入框
        psw = new JPasswordField(1000);
        psw.addActionListener(this);
        psw.setBorder(BorderFactory.createLineBorder(Color.black));
        psw.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) { // 回车事件
                    try {
                        do_login();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        psw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                psw.setBorder(BorderFactory.createLineBorder(Color.red));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                psw.setBorder(BorderFactory.createLineBorder(Color.blue));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                psw.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        });
        psw.setFont(new Font("宋体", Font.BOLD, 9));
        psw.setEchoChar('\u25cf');
        psw.setForeground(Color.black);
        psw.setToolTipText("请输入密码");
        psw.requestFocus(true);
        psw.setBounds(140, 230, 174, 28);
        container.add(psw);

        // 登录按钮及文字
        loginLabel = new JLabel("登 录");
        loginLabel.setForeground(Color.white);
        loginLabel.setFont(new Font("宋体", Font.BOLD, 19));
        loginLabel.setBounds(190, 292, 250, 40);
        container.add(loginLabel);

        login = HoverPressUtil.getBtnButton(
                "pic_src/button_blue_normal.png",
                "pic_src/button_blue_hover.png",
                "pic_src/button_blue_press.png");
        login.addActionListener(this);
        login.setBounds(100, 288, 237, 48);
        container.add(login);

        // 关闭按钮
        close = HoverPressUtil.getBtnClose();
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        close.setBounds(400, 5, 27, 19);
        container.add(close);

        // 最小化按钮
        min = HoverPressUtil.getBtnMin();
        min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        min.setBounds(375, 5, 27, 17);
        container.add(min);


        // 背景
        background = new Background();
        background.setImage(ImageIO.read(new File("pic_src/loginbg.png")));
        background.setBounds(0, 0, 437, 340);
        container.add(background);

        container.repaint();

        // TODO: 2018/12/23 register,multi-user,forgetpsw,auto-login,remember-me,setting


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            try {
                do_login();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == loginCancel) {
            dispose();
            try {
                new Client().launch();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void do_login() throws IOException, InterruptedException {
        if (user.getText().length() != 0 && psw.getPassword().length != 0) {
            FileReader fr;
            fr = new FileReader("data/user_data.txt");
            BufferedReader br = new BufferedReader(fr);

            // 读取用户信息表
            int userNum = 0;
            String str;
            StringTokenizer st;
            Map<String, String> userData = new HashMap<String, String>();
            while ((str = br.readLine()) != null) {
                st = new StringTokenizer(str, ",");
                userData.put(st.nextToken(), st.nextToken());
                userNum++;
            }

//            System.out.println(user.getText());
//            System.out.println(psw.getPassword());
//            System.out.println(userData.containsKey(user.getText()));
//            System.out.println(userData.get(user.getText()));
//            System.out.println(String.valueOf(psw.getPassword()).equals(userData.get(user.getText())));
//            System.out.println(psw.getPassword().length);
//            System.out.println(userData.get(user.getText()).length());
            // 对比用户输入账号密码是否正确
            if (userData.containsKey(user.getText())
                    && String.valueOf(psw.getPassword()).equals(userData.get(user.getText()))) {  // 输入用户存在且密码正确
//                this.setVisible(false);
//                logining_thread ing=new  logining_thread(Login.this);
//                ing.launch();
//                ing.setLocation(Login.this.getX(),Login.this.getY());
//                ing.setVisible(true);

                doing_login();

                // TODO: 2018/12/24 在 登录中 页面停留一段时间 
                // 隐藏登录界面，打开主面板
                dispose();
                MainPanel mainPanel = new MainPanel(user.getText());
                mainPanel.setVisible(true);


            } else if (userData.containsKey(user.getText())
                    && !String.valueOf(psw.getPassword()).equals(userData.get(user.getText()))) { // 输入用户存在但密码错误
                JOptionPane.showMessageDialog(this, "密码错误！");
            } else { //输入用户不存在
                JOptionPane.showMessageDialog(this, "用户不存在！");
            }


        }
    }

    public void doing_login() throws InterruptedException {
        // 隐藏登录按钮，用户输入框，密码输入框，同时隐藏背景（防止之后新增组件被覆盖）
        container.remove(loginLabel);
        container.remove(login);
        container.remove(background);
        user.setVisible(false);
        psw.setVisible(false);

        // 新增取消登录按钮及文字
        loginCancelLabel = new JLabel("取 消 登 录");
        loginCancelLabel.setForeground(Color.white);
        loginCancelLabel.setFont(new Font("宋体", Font.BOLD, 19));
        loginCancelLabel.setBounds(164, 270, 250, 40);
        container.add(loginCancelLabel);

        loginCancel = HoverPressUtil.getBtnButton(
                "pic_src/button_blue_normal.png",
                "pic_src/button_blue_press.png",
                "pic_src/button_blue_hover.png");
        loginCancel.addActionListener(this);
        loginCancel.setBounds(100, 266, 237, 48);
        container.add(loginCancel);

        // 新增登录中提示
        linkTipPanel = new JLabel("正在连接服务器，请稍后.....");
        linkTipPanel.setForeground(Color.darkGray);
        linkTipPanel.setFont(new Font("楷体", Font.BOLD, 14));
        linkTipPanel.setBounds(115, 295, 300, 48);
        container.add(linkTipPanel);

        // 移动头像位置
        headimg.setLocation(176, 160);
        headimgbg.setLocation(174, 158);

        // 防止背景遮住新增的按钮，重新添加背景
        container.add(background);
        container.repaint();

    }


    public static void main(String args[]) throws IOException {
        new Client().launch();
    }

}
