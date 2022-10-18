import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.Random;

public class Logic {
    private JButton[][] btn_array;
    private int[][] spielfeld;

    private boolean run = true;
    private int mines = 0;
    private int aufgedeckteMines = 0;
    private int ahnzahl_minen;

    public Logic(int mines, int am) {
        if(am != 0){
            ahnzahl_minen = mines / am;
        }

        this.mines = mines;
        switch (mines) {
            case 64-> ahnzahl_minen = 10;
            case 100 -> ahnzahl_minen = 20;
            case 256 -> ahnzahl_minen = 40;
            case 121 -> ahnzahl_minen = 25;
        }
        spielfeld = new int[(int) Math.sqrt(mines)][(int) Math.sqrt(mines)];
    }


    private Random rdm = new Random();

    public void cheatMode(JButton[][] array){
        for (int i = 0; i < (int) Math.sqrt(mines); i++) {
            for (int j = 0; j < (int) Math.sqrt(mines); j++) {
                array[i][j].setText(String.valueOf(spielfeld[i][j]));            }
        }
    }

    public void bombenLegen(JButton[][] array){
        btn_array = array;
        for (int i = 0; i < (int) ahnzahl_minen; i++) {
            int rdmX = rdm.nextInt((int) Math.sqrt(mines));
            int rdmY = rdm.nextInt((int) Math.sqrt(mines));
            while(spielfeld[rdmX][rdmY] == -1) {
                rdmX = rdm.nextInt((int) Math.sqrt(mines));
                rdmY = rdm.nextInt((int) Math.sqrt(mines));
            }
            spielfeld[rdmX][rdmY] = -1;

        }
    }

    public void generateValues(){
        for (int i = 0; i < (int) Math.sqrt(mines); i++) {
            for (int j = 0; j < (int) Math.sqrt(mines); j++) {
                if(spielfeld[i][j] != -1) {
                    int counter = 0;
                    if(i > 0) {
                        if(spielfeld[i-1][j] == -1) counter++;
                        if(j > 0) {
                            if(spielfeld[i-1][j-1] == -1) counter++;
                        }
                        if(j < (int) Math.sqrt(mines)-1) {
                            if(spielfeld[i-1][j+1] == -1 ) counter++;
                        }
                    }
                    if(i < (int) Math.sqrt(mines)-1){
                        if(spielfeld[i+1][j] == -1) counter++;
                        if(j > 0) {
                            if(spielfeld[i+1][j-1] == -1) counter++;
                        }
                        if(j < (int) Math.sqrt(mines)-1) {
                            if(spielfeld[i+1][j+1] == -1) counter++;
                        }
                    }
                    if(j > 0) {
                        if(spielfeld[i][j-1] == -1) counter++;
                    }
                    if(j < (int) Math.sqrt(mines)-1) {
                        if(spielfeld[i][j+1] == -1) counter++;
                    }

                    spielfeld[i][j] = counter;

                }
            }
        }
    }

    private void valueAufdecken(int x, int y){
       int i = (spielfeld[x][y]);
       if(i== 0 && btn_array[x][y].getBackground() == Color.gray) {
           btn_array[x][y].setBackground(Color.lightGray);
           zeroAufdecken(x,y);
       }
       Color c = null;
       if(i == 0) c = Color.lightGray;
       if(i == 1) c = Color.green;
       if(i == 2) c = Color.yellow;
       if(i == 3) c = Color.orange;
       if(i == 4) c = Color.pink;

        btn_array[x][y].setText(String.valueOf(i));
        btn_array[x][y].setBackground(c);

    }

    private void zeroAufdecken(int x, int y){
            if(x > 0){
                valueAufdecken(x-1,y);
                if(y > 0) {
                    valueAufdecken(x-1,y-1);
                }
                if(y < (int) Math.sqrt(mines)-1) {
                    valueAufdecken(x-1, y+1);
                }
            }
            if(y > 0) {
                valueAufdecken(x,y-1);
            }
            if(y < (int) Math.sqrt(mines)-1) {
                valueAufdecken(x, y+1);
            }
            if(x < (int) Math.sqrt(mines)-1){
                valueAufdecken(x+1,y);
                if( y > 0) {
                    valueAufdecken(x+1,y-1);
                }
                if(y < (int) Math.sqrt(mines)-1){
                    valueAufdecken(x+1, y+1);
                }
            }
    }

    public void onClick(MouseEvent e, Stats stats){
        if(e.getButton() == MouseEvent.BUTTON1) {
            for (int i = 0; i < (int) Math.sqrt(mines); i++) {
                for (int j = 0; j < (int) Math.sqrt(mines); j++) {
                    if (btn_array[i][j].equals(e.getSource()) &&!btn_array[i][j].getBackground().equals(Color.red)) {
                        if(run) {
                            if (spielfeld[i][j] == -1) {
                                bombenAufdecken();
                                run = false;
                            } else {
                                valueAufdecken(i,j);
                            }
                        }
                    }
                }
            }
        }else if(e.getButton() == 3){
            for (int i = 0; i < (int) Math.sqrt(mines); i++) {
                for (int j = 0; j < (int) Math.sqrt(mines); j++) {
                    if (btn_array[i][j].equals(e.getSource())) {
                        if(run) {
                            if(btn_array[i][j].getBackground().equals(Color.gray)){
                                if(aufgedeckteMines < ahnzahl_minen) {
                                    btn_array[i][j].setBackground(Color.red);

                                    ImageIcon temp = new ImageIcon(Objects.requireNonNull(getClass().getResource(("/flag.png"))));
                                    Image img = temp.getImage();
                                    Image newimg = img.getScaledInstance(btn_array[i][j].getWidth(), btn_array[i][j].getHeight(), Image.SCALE_SMOOTH);
                                    ImageIcon newIcon = new ImageIcon(newimg);
                                    btn_array[i][j].setIcon(newIcon);


                                    aufgedeckteMines++;
                                    stats.updateMines();
                                }
                            }else if(btn_array[i][j].getBackground().equals(Color.red)){
                                btn_array[i][j].setIcon(null);
                                btn_array[i][j].setBackground(Color.gray);
                                aufgedeckteMines--;
                                stats.updateMines();
                            }
                        }
                    }
                }
            }
        }
        checkIfWon();

    }

    private void bombenAufdecken(){
        for (int i = 0; i < (int) Math.sqrt(mines); i++) {
            for (int j = 0; j < (int) Math.sqrt(mines); j++) {
                if(spielfeld[i][j] == -1){
                    btn_array[i][j].setBackground(Color.red);
                    ImageIcon temp = new ImageIcon(Objects.requireNonNull(getClass().getResource("/bombe.png")));
                    Image img = temp.getImage();
                    Image newimg = img.getScaledInstance(btn_array[i][j].getWidth(), btn_array[i][j].getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon newIcon = new ImageIcon(newimg);
                    btn_array[i][j].setIcon(newIcon);
                }
            }
        }
    }

    public void checkIfWon(){
        for (int i = 0; i < (int) Math.sqrt(mines); i++) {
            for (int j = 0; j < (int) Math.sqrt(mines); j++) {
                if(!btn_array[i][j].getBackground().equals(Color.gray)) {


                }
                else return;
            }
        }
        run =false;
        Simulator.getD().getStats().stopUhr();

        Win win = new Win(this);


    }

    public int getAufgedeckteMines() {
        return aufgedeckteMines;
    }

    public int getAhnzahl_minen() {
        return ahnzahl_minen;
    }


}
