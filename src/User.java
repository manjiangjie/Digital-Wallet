import java.util.ArrayList;

/**
 * Created by Jiangjie Man on 11/8/16.
 */
public class User {

    private String id;
    private ArrayList<String> friends = new ArrayList<>();

    public User(String id) {
        this.id = id;
    }

    public void addFriend(String id) {
        friends.add(id);
    }

    public String getId() {
        return id;
    }

    public ArrayList getFriends() {
        return friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
