package client;

import org.codehaus.jackson.map.ObjectMapper;
import util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FriendList extends JPanel implements ActionListener, MouseListener {

    private String ownerId, friendId;
    private JLabel friendLb[], strangerLb[];
    private JButton btnMyFriend, btnStranger;
    private JPanel panel, friendP, strangerP;
    private JScrollPane friendSP, strangerSP;


    public void updateList(Message message) throws IOException {
        String context = message.getCon();
        ObjectMapper mapper = new ObjectMapper();
        List<String> onlineFriend = mapper.readValue(context, ArrayList.class);

        Iterator it = onlineFriend.iterator();
        for (int i = 0; it.hasNext(); i++) {
            friendLb[i].setEnabled(true);
        }
    }

    public FriendList(String userId) {
        this.ownerId = userId;

        // 按钮
        btnMyFriend = new JButton("我的好友");
        btnMyFriend.addActionListener(this);


        btnStranger = new JButton("陌生人");
        btnStranger.addActionListener(this);


        // 好友
        friendP = new JPanel(new GridLayout(50, 1, 4, 4));
        friendP.setBorder(BorderFactory.createEmptyBorder(2, 10, 6, 5));

        friendLb = new JLabel[50];
        for (int i = 0; i < 50; i++) {
            friendLb[i] = new JLabel(i + 10001 + "", new ImageIcon("pic_src/headimg/" + i + ".png"), JLabel.LEFT);
            friendLb[i].setEnabled(false);
            if (friendLb[i].getText().equals(userId)) {
                friendLb[i].setEnabled(true);
            }
            friendLb[i].addMouseListener(this);
            friendLb[i].setFont(new Font("宋体", Font.PLAIN, 15));
            friendP.add(friendLb[i]);
        }
        friendSP = new JScrollPane(friendP);

        // 陌生人
        strangerP = new JPanel(new GridLayout(50, 1, 4, 4));
        strangerP.setBorder(BorderFactory.createEmptyBorder(2, 10, 6, 5));
//        strangerP.add(btnStranger);
        strangerLb = new JLabel[50];
        for (int i = 0; i < 50; i++) {
            strangerLb[i] = new JLabel("stranger" + i, new ImageIcon("pic_src/headimg/" + i + ".png"), JLabel.LEFT);
            strangerLb[i].setFont(new Font("宋体", Font.PLAIN, 15));
            strangerP.add(strangerLb[i]);
        }
        strangerSP = new JScrollPane(strangerP);


        // TODO: 2018/12/25 修改按钮与列表的布局 
        panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.add(btnMyFriend);
        panel.add(friendSP);
        panel.add(btnStranger);
        panel.add(strangerSP);


        this.setLayout(new CardLayout());
        this.add(panel, "1");
    }


    // TODO: 2018/12/25 列表刷新问题
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnMyFriend) {
            strangerSP.setVisible(false);
            friendSP.setVisible(true);
//            this.repaint();
        } else if (e.getSource() == btnStranger) {
            strangerSP.setVisible(true);
            friendSP.setVisible(false);
//            this.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //响应用户双击的事件，并得到好友的编号.
        if (e.getClickCount() == 2) {
            //得到该好友的编号
            friendId = ((JLabel) e.getSource()).getText();
            //System.out.println("你希望和 "+friendNo+" 聊天");
            DialogPanel dialog = new DialogPanel(this.ownerId, friendId);


            //把聊天界面加入到管理类
            ManageChat.addQqChat(this.ownerId + " " + friendId, dialog);

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel jl = (JLabel) e.getSource();
        jl.setBackground(Color.red);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel jl = (JLabel) e.getSource();
        jl.setForeground(Color.black);
    }

}
