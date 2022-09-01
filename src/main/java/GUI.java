import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GUI extends JPanel implements MouseListener {

    private Logic l;

    private JButton[][] btn_array;

    private Stats stats;
    private int mines;


    public GUI(Logic l, int mines){
        this.mines = mines;

        this.l = l;
        setBackground(Color.black);
        setBounds(0,0, 1015,1035);

        setLayout(null);
        start();



    }

    public void start() {

        btn_array  = new JButton[(int) Math.sqrt(mines)][(int) Math.sqrt(mines)];
        for (int i = 0; i <btn_array.length; i++) {
            for (int j = 0; j < btn_array.length; j++) {

                btn_array[i][j] = new JButton();
                btn_array[i][j].setBackground(Color.gray);
                btn_array[i][j].setBounds(1000/(int) Math.sqrt(mines)*i, 1000/(int) Math.sqrt(mines)*j, 1000/(int) Math.sqrt(mines)-2,1000/(int) Math.sqrt(mines)-2);
                btn_array[i][j].addMouseListener(this);
                add(btn_array[i][j]);
            }

        }
        l.bombenLegen(btn_array);
        l.generateValues();
       // l.cheatMode(btn_array);
    }

    public void restart() {
        start();
    }




//    @Override
//    public void actionPerformed(ActionEvent e) {
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                if(btn_array[i][j].equals(e.getSource())) {
//                    l.onLeftClick(i,j);
//                }
//            }
//        }
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        l.onClick(e, stats);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
