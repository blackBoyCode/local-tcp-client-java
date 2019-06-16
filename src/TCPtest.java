import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class TCPtest {

    public static void main(String[] args) {


        LocalTCPClient tcpClient = new LocalTCPClient();


       // tcpClient.startReadingProcess();

        buttonPageTest(tcpClient);

        Scanner in = new Scanner(System.in);
       // String s = in.nextLine();


        //}

        //System.out.println("should send something " + s);


    }



    public static void buttonPageTest(LocalTCPClient tcpClient){
        JFrame f=new JFrame("Button Example");
        final JLabel tf=new JLabel();
        tf.setBounds(50,50, 300,20);
        JButton b=new JButton("Send");
        b.setBounds(50,100,95,30);
        tf.setText("sendMessage to electron Process");
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                tcpClient.sendRequest("ddddd");
            }
        });
        f.add(b);f.add(tf);
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);

    }
}
