import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jiangjie Man on 11/8/16.
 */
public class User {

    private String id;
    private Set<User> friends = new HashSet<>();

    public User(String id) {
        this.id = id;
    }

    public void addFriend(User usr) {
        friends.add(usr);
    }

    public String getId() {
        return id;
    }

    public Set<User> getFriends() {
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", friends=" + friends +
                '}';
    }
}
