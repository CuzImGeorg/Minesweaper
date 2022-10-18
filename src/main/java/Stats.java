import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Stats extends JPanel {

    private int time_seconds = 0;
    private int time_minutes = 0;

    private JLabel jb = new JLabel();
    private JLabel minepng;
    private JLabel uhrpng;

    private JLabel mineCounter = new JLabel();

    private Logic l;

    private JButton restart = new JButton();

    private ArrayList<String> scores = new ArrayList<>();

    private boolean won = false;

    public Stats(Logic l)  {
            loadScores();
            this.l = l;
            setLayout(null);
            setBounds(998, 0, 200, 1000);
            setBackground(Color.gray);
            zeit();
            add(jb);
            jb.setText("0");
            jb.setBounds(90, 180, 100,100);
            jb.setBackground(Color.white);


            restart.setBounds(10, 896, 180, 100);
            restart.setBorder(new LineBorder(Color.black, 4));
            restart.setText("Restart");
            restart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Simulator.getD().restart();
                }
            });
            add(restart);


            readImage();

            mineCounter.setBounds(90,60, 50,50);
            mineCounter.setText(String.valueOf(l.getAufgedeckteMines()) + "/" + l.getAhnzahl_minen());
            add(mineCounter);

    }

    public void updateMines() {
        mineCounter.setText(String.valueOf(l.getAufgedeckteMines()) + "/" + l.getAhnzahl_minen());
    }

    private void readImage() {

        try {
            BufferedImage a = ImageIO.read(getClass().getResource("/mine.png"));
            BufferedImage uhr = ImageIO.read(getClass().getResource("/uhr.png"));
            minepng = new JLabel(new ImageIcon(a));
            minepng.setBounds(75,10, 50,50);
            add(minepng);

            uhrpng = new JLabel(new ImageIcon(uhr));
            uhrpng.setBounds(75, 160, 50,50);
                add(uhrpng);

        } catch (IOException e) {
        }

    }




    public void zeit() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(won) {
                    ses.shutdown();
                }
                time_seconds++;
                if(time_seconds >= 60) {
                    time_seconds = 0;
                    time_minutes++;
                }

                jb.setText(String.valueOf(time_minutes + ":" + time_seconds));


            }
        },1, 1, TimeUnit.SECONDS);

    }

    public void addScore() {
        scores.add(time_minutes +":" + time_seconds);
    }
    public void addScore(String time) {
        scores.add(time);
    }

    public void safescore(String s){
        FileWriter fw = null;
        try {
            fw = new FileWriter(getClass().getResource("/scores_" + Simulator.getD().getDif().toLowerCase() + ".txt").getFile());
            String w = "";
            for(String temp: scores){
                 w += temp+ System.lineSeparator();

            }
            w += s;
            fw.write(w);
            fw.close();
        } catch (IOException e) {}
    }

    public void loadScores() {
        if(!Simulator.getD().getDif().toLowerCase().equalsIgnoreCase("custom")){
            Scanner s = null;

            try {
                File file = new File(getClass().getResource("/scores_" + Simulator.getD().getDif().toLowerCase() + ".txt").getFile());
                s  = new Scanner(file);
            }catch (FileNotFoundException e) {

            }
            while(s.hasNext()) {
                String str = s.next();
                addScore(str);
            }
        }
    }

    public  String getTime(){
       return time_minutes + ":" + time_seconds;
    }

    public void stopUhr(){
        won = true;
    }

    public ArrayList<String> getScores() {
        return scores;
    }
}
