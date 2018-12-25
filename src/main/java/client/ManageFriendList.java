package client;

import java.util.HashMap;

public class ManageFriendList {
    private static HashMap<String, FriendList> hm = new HashMap<String, FriendList>();

    public static void addFriendList(String userId, FriendList friendList) {
        hm.put(userId, friendList);
    }

    public static FriendList getFriendList(String userId) {
        return hm.get(userId);
    }
}
