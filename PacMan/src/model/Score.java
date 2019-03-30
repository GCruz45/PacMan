package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Score {

    private String player;
    private int score;
    private int level;
    private Date date;

    private static final String FILE_NAME = "src/serialized.dat";

    private ArrayList<String> scores;

    private FileOutputStream fos = null;
    private ObjectOutputStream oos = null;

    private FileInputStream fis = null;
    private ObjectInputStream ois = null;

    public void save(int score, String player, int level, Date date) throws IOException {//   @#$: Indicador del comienzo de un nuevo this
        this.score = score;
        this.player = player;
        this.level = level;
        this.date = date;
        File file = new File(FILE_NAME);
        scores = new ArrayList<>();
        try {
            this.fos = new FileOutputStream(FILE_NAME);
            this.oos = new ObjectOutputStream(fos);

            this.fis = new FileInputStream(FILE_NAME);
            this.ois = new ObjectInputStream(fis);

            if (file.exists()) {

                for (int i = 0; i < 999; i++) {
                    scores.add((String) ois.readObject());
                }

            } else {
                oos.writeObject("@#$");
                oos.writeObject("p " + this.player);
                oos.writeObject("s " + this.score);
                oos.writeObject("l " + this.level);
                oos.writeObject("d " + this.date);
                oos.close();
            }
        } catch (EOFException e) {
            scores.add("@#$");
            scores.add("p " + this.player);
            scores.add("s " + this.score);
            scores.add("l " + this.level);
            scores.add("d " + this.date);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            this.fos = new FileOutputStream(FILE_NAME);
            this.oos = new ObjectOutputStream(fos);
            for (String scr :
                    scores) {
                oos.writeObject(scr);
            }
            oos.close();
        }
    }

    public void load() {
//TODO: agregar if de por si no existe el archivo
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            for (int i = 0; i < 999; i++) {
                System.out.println((String) ois.readObject());
            }
        } catch (EOFException ignored) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}