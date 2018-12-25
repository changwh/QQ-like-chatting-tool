package client;

import java.util.HashMap;

public class ManageChat {
    private static HashMap hm = new HashMap<String, DialogPanel>();

    //加入
    public static void addQqChat(String loginIdAnFriendId, DialogPanel dialog) {
        hm.put(loginIdAnFriendId, dialog);
    }

    //取出
    public static DialogPanel getQqChat(String loginIdAnFriendId) {
        return (DialogPanel) hm.get(loginIdAnFriendId);
    }
}
