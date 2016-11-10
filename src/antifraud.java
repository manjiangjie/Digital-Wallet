import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Jiangjie Man on 11/8/16.
 */
public class Antifraud {

    private Map<String, User> users = new HashMap<>();
    private int d;
    private String batch, stream, output1, output2, output3;

    public Antifraud(String[] args) {
        batch = args[0];
        stream = args[1];
        output1 = args[2];
        output2 = args[3];
        output3 = args[4];
    }

    public void buildGraph() {
        try {
            System.out.println("Building user network...");
            BufferedReader br = new BufferedReader(new FileReader(batch));
            String nextLine, id1, id2;
            br.readLine();
            while ((nextLine = br.readLine()) != null) {
                //System.out.println(nextLine);
                String[] values = nextLine.split(", ");
                if (values.length == 5) {
                    id1 = values[1];
                    id2 = values[2];
                    User usr1 = new User(id1);
                    User usr2 = new User(id2);
                    if (!users.containsKey(id1)) {
                        usr1.addFriend(usr2);
                        users.put(id1, usr1);
                    } else {
                        users.get(id1).addFriend(usr2);
                    }
                    if (!users.containsKey(id2)) {
                        usr2.addFriend(usr1);
                        users.put(id2, usr2);
                    } else {
                        users.get(id2).addFriend(usr1);
                    }
                }
            }
            System.out.println("Total users: " + users.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void output() {
        try {
            System.out.println("Processing payments...");
            BufferedReader br = new BufferedReader(new FileReader(stream));
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(output1));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(output2));
            BufferedWriter bw3 = new BufferedWriter(new FileWriter(output3));
            String nextLine, id1, id2;
            int degree;
            br.readLine();
            while ((nextLine = br.readLine()) != null) {
                String[] values = nextLine.split(", ");
                if (values.length == 5) {
                    id1 = values[1];
                    id2 = values[2];
                    User usr1 = users.get(id1);
                    User usr2 = users.get(id2);
                    degree = getDegree(usr1, usr2);
                    if (degree == 0) {
                        bw1.write("unverified\n");
                        bw2.write("unverified\n");
                        bw3.write("unverified\n");
                    } else if (degree == 1) {
                        bw1.write("trusted\n");
                        bw2.write("trusted\n");
                        bw3.write("trusted\n");
                    } else if (degree == 2) {
                        bw1.write("unverified\n");
                        bw2.write("trusted\n");
                        bw3.write("trusted\n");
                    } else {
                        bw1.write("unverified\n");
                        bw2.write("unverified\n");
                        bw3.write("trusted\n");
                    }
                }
            }
            bw1.close();
            bw2.close();
            bw3.close();
            System.out.println("Finished! Output 3 text files in the paymo_output directory.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getDegree(User usr1, User usr2) {
        if (usr1 == null || usr2 == null) {
            return 0;
        }

        d = 1;
        Queue<User> q1 = new LinkedList<>();
        Queue<User> q2 = new LinkedList<>();
        Set<User> marked1 = new HashSet<>();
        Set<User> marked2 = new HashSet<>();
        marked1.add(usr1);
        marked2.add(usr2);
        q1.add(usr1);
        q2.add(usr2);

        while (!q1.isEmpty() && !q2.isEmpty() && d < 5) {
            if (BidirectionalBFS(marked1, marked2, q1)) {
                return d;
            }
            d += 1;
            if (BidirectionalBFS(marked2, marked1, q2)) {
                return d;
            }
            d += 1;
        }

        return 0;
    }

    private boolean BidirectionalBFS(Set<User> marked1, Set<User> marked2, Queue<User> q) {
        if (!q.isEmpty()) {
            User usr = q.remove();
            for (User friend : usr.getFriends()) {
                if (marked2.contains(friend)) {
                    return true;
                }
                if (!marked1.contains(friend)) {
                    q.add(friend);
                    marked1.add(friend);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Antifraud anti = new Antifraud(args);
        anti.buildGraph();
        anti.output();
    }
}
