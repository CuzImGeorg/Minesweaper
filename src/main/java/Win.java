import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Win extends JFrame {

    private JPanel p = new JPanel();

    private JLabel youwon = new JLabel();


    public Win(Logic l){
        setSize(200,600);
        setLocationRelativeTo(null);
        p.setLayout(null);


        p.setBackground(Color.gray);

        youwon.setText("you won Playtime:" + Simulator.getD().getStats().getTime());
        youwon.setBackground(Color.gray);
        youwon.setBounds(0,0, 200,20);


        p.add(youwon);
        add(p);
        createScores();
        setVisible(true);
        enetername();


    }

    public void enetername() {
        JFrame f = new JFrame();
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        f.setSize(300,100);

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(0,0,200,100);
        p.setBackground(Color.darkGray);
        JTextField tf = new JTextField();
        tf.setBackground(Color.lightGray);
        tf.setBounds(0,0 , 150,40);

        JButton b = new JButton();
        b.setBorder(new LineBorder(Color.black));
        b.setBackground(Color.green);
        b.setBounds(150,0,100,40);
        b.setText("add Score");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulator.getD().getStats().safescore( tf.getText()+ Simulator.getD().getStats().getTime());
                f.dispose();
            }
        });

        p.add(b);
        p.add(tf);
        f.add(p);
        f.setVisible(true);
    }

    public void createScores() {
        for (int i = 0; i <Simulator.getD().getStats().getScores().size(); i++) {
            JLabel temp = new JLabel();
            temp.setText(Simulator.getD().getStats().getScores().get(i));
            temp.setBounds(0,i*20+20,200,20);
            p.add(temp);
        }
    }

}
