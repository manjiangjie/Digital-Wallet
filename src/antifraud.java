import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Jiangjie Man on 11/8/16.
 */
public class Antifraud {

    private HashSet<User> users = new HashSet<>();

    public void buildGraph() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("paymo_input/batch_payment.csv"));
            String nextLine, id1, id2;
            String firstLine = br.readLine();
            while ((nextLine = br.readLine()) != null) {
                //System.out.println(nextLine);
                String[] values = nextLine.split(", ");
                if (values.length == 5) {
                    id1 = values[1];
                    id2 = values[2];
                    User usr1 = new User(id1);
                    User usr2 = new User(id2);
                    if (!users.contains(usr1)) {
                        users.add(usr1);
                    }
                    if (!users.contains(usr2)) {
                        users.add(usr2);
                    }
                    usr1.addFriend(id2);
                    usr2.addFriend(id1);
                }
            }
            System.out.println(users.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void output() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("paymo_input/stream_payment.csv"));
            BufferedWriter bw1 = new BufferedWriter(new FileWriter("paymo_output/output1.txt"));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter("paymo_output/output2.txt"));
            BufferedWriter bw3 = new BufferedWriter(new FileWriter("paymo_output/output3.txt"));
            String nextLine, id1, id2;
            int degree = 0;
            String firstLine = br.readLine();
            while ((nextLine = br.readLine()) != null) {
                String[] values = nextLine.split(", ");
                if (values.length == 5) {
                    id1 = values[1];
                    id2 = values[2];
                    User usr1 = new User(id1);
                    User usr2 = new User(id2);
                    degree = getDegree(usr1, usr2);
                    if (degree == 0) {
                        bw1.write("unverified");
                        bw2.write("unverified");
                        bw3.write("unverified");
                    } else if (degree == 1) {
                        bw1.write("trusted");
                        bw2.write("trusted");
                        bw3.write("trusted");
                    } else if (degree == 2) {
                        bw1.write("unverified");
                        bw2.write("trusted");
                        bw3.write("trusted");
                    } else if (degree > 2 && degree < 5) {
                        bw1.write("unverified");
                        bw2.write("unverified");
                        bw3.write("trusted");
                    } else {
                        bw1.write("unverified");
                        bw2.write("unverified");
                        bw3.write("unverified");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getDegree(User usr1, User usr2) {
        
    }

    private void bfs() {

    }

    public static void main(String[] args) {
        Antifraud anti = new Antifraud();
        anti.buildGraph();
        anti.output();
    }
}
