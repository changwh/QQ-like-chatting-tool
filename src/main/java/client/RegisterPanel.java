package client;

import util.HoverPressUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RegisterPanel extends JFrame implements ActionListener {

    private static JLabel noEqualLb;
    private static JTextField user;
    private static JPasswordField psw, pswConfirm;
    private static JButton close, min, register, cancel;
    private static Background background;
    private Point point;
    private Container container = this.getContentPane();

    private static double screenWidth;


    private static double ScreenHeight;

    public void launch() throws IOException {
        this.setSize(437, 340);
        this.setIconImage(ImageIO.read(new File("pic_src/title.png")));
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        // 设置窗口位置
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setScreenWidth(screenSize.getWidth());
        setScreenHeight(screenSize.getHeight());
        this.setLocation((int) (getScreenWidth() / 2 - this.getWidth() / 2), (int) (getScreenHeight() / 2 - this.getHeight() / 2));

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

        // 用户名输入框
        user = new JTextField(1000);
        user.setBorder(BorderFactory.createLineBorder(Color.black));
        user.setFont(new Font("楷体", Font.PLAIN, 14));
        user.setForeground(Color.gray);
        user.setText("请输入用户账号");
        user.addActionListener(this);
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
        user.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("请输入用户账号".equals(user.getText())) {
                    user.setForeground(Color.black);
                    user.setFont(new Font("宋体", Font.BOLD, 18));
                    user.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (user.getText().length() == 0) {
                    user.setFont(new Font("楷体", Font.PLAIN, 14));
                    user.setForeground(Color.gray);
                    user.setText("请输入用户账号");
                }
            }
        });
        user.setToolTipText("请输入用户账号");
        user.setBounds(140, 177, 174, 28);
        container.add(user);

        // 密码输入框
        psw = new JPasswordField(1000);
        psw.setBorder(BorderFactory.createLineBorder(Color.black));
        psw.setFont(new Font("楷体", Font.PLAIN, 14));
        psw.setForeground(Color.gray);
        psw.setEchoChar((char) 0);
        psw.setText("请输入密码");
        psw.addActionListener(this);
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
        psw.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String str = String.valueOf(psw.getPassword());
                if ("请输入密码".equals(str)) {
                    psw.setForeground(Color.black);
                    psw.setFont(new Font("宋体", Font.BOLD, 9));
                    psw.setEchoChar('\u25cf');
                    psw.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (psw.getPassword().length == 0) {
                    psw.setFont(new Font("楷体", Font.PLAIN, 14));
                    psw.setForeground(Color.gray);
                    psw.setEchoChar((char) 0);
                    psw.setText("请输入密码");
                }
            }
        });
        psw.setToolTipText("请输入密码");
        psw.requestFocus(true);
        psw.setBounds(140, 210, 174, 28);
        container.add(psw);

        // 密码确认框
        pswConfirm = new JPasswordField(1000);
        pswConfirm.setBorder(BorderFactory.createLineBorder(Color.black));
        pswConfirm.setFont(new Font("楷体", Font.PLAIN, 14));
        pswConfirm.setForeground(Color.gray);
        pswConfirm.setEchoChar((char) 0);
        pswConfirm.setText("请再次输入密码");
        pswConfirm.addActionListener(this);
        pswConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pswConfirm.setBorder(BorderFactory.createLineBorder(Color.red));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pswConfirm.setBorder(BorderFactory.createLineBorder(Color.blue));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pswConfirm.setBorder(BorderFactory.createLineBorder(Color.black));
            }
        });
        pswConfirm.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String str = String.valueOf(pswConfirm.getPassword());
                if ("请再次输入密码".equals(str)) {
                    pswConfirm.setForeground(Color.black);
                    pswConfirm.setFont(new Font("宋体", Font.BOLD, 9));
                    pswConfirm.setEchoChar('\u25cf');
                    pswConfirm.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (pswConfirm.getPassword().length == 0) {
                    pswConfirm.setFont(new Font("楷体", Font.PLAIN, 14));
                    pswConfirm.setForeground(Color.gray);
                    pswConfirm.setEchoChar((char) 0);
                    pswConfirm.setText("请再次输入密码");
                }
            }
        });
        pswConfirm.setToolTipText("请再次输入密码");
        pswConfirm.requestFocus(true);
        pswConfirm.setBounds(140, 243, 174, 28);
        container.add(pswConfirm);

        // 密码不一致提示
        noEqualLb = new JLabel(new ImageIcon("pic_src/zhuce5.jpg"));
        noEqualLb.setBounds(320, 240, 100, 37);
        noEqualLb.setVisible(false);
        container.add(noEqualLb);

        // 注册按钮
        register = HoverPressUtil.getBtnButton("pic_src/zhuce.png",
                "pic_src/zhuce_hover.jpg",
                "pic_src/zhuce.png");
        register.addActionListener(this);
        register.setBounds(110, 288, 235, 48);
        container.add(register);

        // 关闭按钮
        close = HoverPressUtil.getBtnClose();
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
        background.setImage(ImageIO.read(new File("pic_src/shezhibg.png")));
        background.setBounds(0, 0, 437, 340);
        container.add(background);

        container.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) {
            try {
                FileReader fr = new FileReader("data/user_data.txt");
                BufferedReader br = new BufferedReader(fr);

                // 读取用户信息表
                String str;
                StringTokenizer st;
                Map<String, String> userData = new HashMap<String, String>();
                while ((str = br.readLine()) != null) {
                    st = new StringTokenizer(str, ",");
                    userData.put(st.nextToken(), st.nextToken());
                }

                String userStr = user.getText();
                String pswStr = String.valueOf(psw.getPassword());
                String pswConStr = String.valueOf(pswConfirm.getPassword());

                // TODO: 2018/12/27 验证输入
                if (!pswStr.equals(pswConStr)) {
                    noEqualLb.setVisible(true);
                    return;
                } else {
                    noEqualLb.setVisible(false);
                }
                if (!userData.containsKey(userStr)) {
                    String usrPswStr = userStr + "," + pswStr + "\r\n";
                    File file = new File("data/user_data.txt");
                    FileOutputStream fos = new FileOutputStream(file, true);
                    byte[] bytes = usrPswStr.getBytes("utf-8");
                    fos.write(bytes);
                    fos.flush();
                    fos.close();
                    JOptionPane.showMessageDialog(this, "注册成功");
                } else {
                    JOptionPane.showMessageDialog(this, "用户已经存在");
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static double getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(double screenWidth) {
        RegisterPanel.screenWidth = screenWidth;
    }

    public static double getScreenHeight() {
        return ScreenHeight;
    }

    public static void setScreenHeight(double screenHeight) {
        ScreenHeight = screenHeight;
    }
}
