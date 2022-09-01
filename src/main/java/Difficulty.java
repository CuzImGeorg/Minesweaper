import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Difficulty extends JFrame implements ActionListener {

    private JPanel p = new JPanel();

    private JButton dif_easy = new JButton();
    private JButton dif_medium = new JButton();
    private JButton dif_hard = new JButton();
    private JButton dif_custom = new JButton();

    private String dif ="";

     private  Stats stats;


    public Difficulty(){
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        p.setBackground(Color.darkGray);
        p.setLayout(null);
        p.setBounds(0,0,1000,1000);
        add(p);
        buttons();
        setVisible(true);
    }

    private void buttons(){
        dif_easy.setText("Easy");
        dif_easy.setBackground(Color.gray);
        dif_easy.addActionListener(this);
        dif_easy.setBounds(50,50,400,400);
        dif_easy.setBorder(new LineBorder(Color.black,2));
        p.add(dif_easy);

        dif_medium.setText("Medium");
        dif_medium.setBackground(Color.gray);
        dif_medium.addActionListener(this);
        dif_medium.setBounds(500,50,400,400);
        dif_medium.setBorder(new LineBorder(Color.black,2));
        p.add(dif_medium);

        dif_hard.setText("Hard");
        dif_hard.setBackground(Color.gray);
        dif_hard.addActionListener(this);
        dif_hard.setBounds(50,500,400,400);
        dif_hard.setBorder(new LineBorder(Color.black,2));
        p.add(dif_hard);

        dif_custom.setText("Custom");
        dif_custom.setBackground(Color.gray);
        dif_custom.addActionListener(this);
        dif_custom.setBounds(500,500,400,400);
        dif_custom.setBorder(new LineBorder(Color.black,2));
        p.add(dif_custom);



    }

   private JFrame frame;


    public void start(){
        frame = new JFrame();
        int mines = 0;


        switch (dif) {
            case "Easy" -> mines = 64;
            case "Medium" -> mines = 100;
            case "Hard" -> mines = 256;
            case "Custom" -> {
                JFrame f = new JFrame();
                JPanel panel = new JPanel();
                panel.setLayout(null);
                panel.setBackground(Color.lightGray);
                f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                f.setLocationRelativeTo(null);
                f.setSize(200,120);

                JLabel l1 = new JLabel();
                l1.setBackground(Color.gray);
                l1.setBounds(0,0, 200,20);
                l1.setText("Ahnzahl Minesn pro Achse");

                JLabel l2 = new JLabel();
                l2.setBackground(Color.gray);
                l2.setBounds(0,40, 200,20);
                l2.setText("Prozent Minen");

                JTextField tf = new JTextField();
                tf.setBounds(0,20, 200,20);
                tf.setBackground(Color.gray);

                JTextField percmine = new JTextField();
                percmine.setBounds(0,60, 100,20);
                percmine.setBackground(Color.gray);

                JButton start = new JButton();
                start.setBackground(Color.green);
                start.setBorder(new LineBorder(Color.black));
                start.setBounds(110, 60 ,90,20);
                start.setText("Start");

                start.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startGame(Integer.parseInt(tf.getText()) * Integer.parseInt(tf.getText()), Integer.parseInt(percmine.getText()));
                        f.dispose();
                    }
                });

                panel.add(start);
                panel.add(l1);
                panel.add(l2);
                panel.add(percmine);
                panel.add(tf);
                f.add(panel);
                f.setVisible(true);

            }

        }
        if(!dif.equalsIgnoreCase("custom")) {
            startGame(mines,0);
        }



    }

    public void startGame(int mines, int am) {
        dispose();



        Logic l = new Logic(mines, am);


        frame.setSize(1215,1035);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GUI gui = new GUI(l, mines);
        frame.setContentPane(gui);

        stats = new Stats(l);
        frame.add(stats);
        gui.setStats(stats);

        frame.setVisible(true);
    }

    public void restart(){
        frame.dispose();
        start();

    }




    @Override
    public void actionPerformed(ActionEvent e) {
        JButton  temp  = (JButton) e.getSource();
        dif = temp.getText();
        start();

    }

    public Stats getStats() {
        return stats;
    }

    public String getDif() {
        return dif;
    }
}
