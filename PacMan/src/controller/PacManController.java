package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.PacMan;
import model.Score;
import model.Threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class PacManController {

    private ArrayList<PacMan> pacManArray;

    public PacManController() {
        pacManArray = new ArrayList<>();
    }

    @FXML
    public VBox mainBox;

    @FXML
    public Pane pane;

    @FXML
    public Button startButton;

    @FXML
    Label bouncesLabel;

    private int bounces;

    @FXML
    ChoiceBox levelCB;

    @FXML
    Label levelLabel;

    @FXML
    TextField playerTF;

    @FXML
    MenuItem topScores;

    @FXML
    void checkMouseClicks() {
        if (levelCB.getValue() != null) {
            startButton.setOpacity(1.0);
            startButton.setDisable(false);
        }
    }

    @FXML
    void getTopScores() {
        Score score = new Score();
        score.load();
    }

    @FXML
    void catchPacMan(MouseEvent event) {
        for (PacMan pacMan : pacManArray
        ) {
            double distance = Math.sqrt(Math.pow(pacMan.getxOffset() - event.getSceneX(), 2) +
                    Math.pow(pacMan.getyOffset() - event.getSceneY(), 2));
            if (distance < pacMan.getRadius()) {
                pacMan.catchPacMan();
            }
        }
        boolean gameFinished = true;
        for (PacMan pacMan : pacManArray
        ) {
            if (pacMan.getSpeed() != 0) {
                gameFinished = false;
            }
        }
        if (gameFinished) {
            Date date = new Date();
            date.getTime();
            Score score = new Score();

            int level = -1;
            switch (pacManArray.size()) {
                case 4:
                    level = 0;
                    break;
                case 6:
                    level = 1;
                    break;
                case 8:
                    level = 2;
                    break;
            }
            try {
                score.save(getBounces(), playerTF.getText(), level, date);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void startGame() {
        levelLabel.setVisible(false);
        levelCB.setVisible(false);
        startButton.setVisible(false);

        Threads threads = new Threads(this);
        threads.start();
        switch (levelCB.getValue().toString()) {
            case "Level 0":
                int pacManNumber = 4;
                for (int i = 0; i < pacManNumber; i++) {
                    PacMan pacMan = new PacMan(20, true, mainBox.getWidth() / 2 - 200 +
                            (float) 400 / pacManNumber * i, mainBox.getHeight() / 2 - 120 + (float) 240 / pacManNumber * i);
                    pacManArray.add(pacMan);
                    pacManArray.get(i).getArc().setLayoutX(pacManArray.get(i).getxOffset());
                    pacManArray.get(i).getArc().setLayoutY(pacManArray.get(i).getyOffset());
                    pane.getChildren().add(pacManArray.get(i).getArc());
                }

                break;
            case "Level 1":
                pacManNumber = 6;
                for (int i = 0; i < pacManNumber; i++) {
                    PacMan pacMan = new PacMan(20, true, mainBox.getWidth() / 2 - 200 +
                            (float) 400 / pacManNumber * i, mainBox.getHeight() / 2 - 120 + (float) 240 / pacManNumber * i);
                    pacManArray.add(pacMan);
                    pacManArray.get(i).getArc().setLayoutX(pacManArray.get(i).getxOffset());
                    pacManArray.get(i).getArc().setLayoutY(pacManArray.get(i).getyOffset());
                    pane.getChildren().add(pacManArray.get(i).getArc());
                }

                break;
            case "Level 2":
                pacManNumber = 8;
                for (int i = 0; i < pacManNumber; i++) {
                    PacMan pacMan = new PacMan(20, true, mainBox.getWidth() / 2 - 200 +
                            (float) 400 / pacManNumber * i, mainBox.getHeight() / 2 - 120 + (float) 240 / pacManNumber * i);
                    pacManArray.add(pacMan);
                    pacManArray.get(i).getArc().setLayoutX(pacManArray.get(i).getxOffset());
                    pacManArray.get(i).getArc().setLayoutY(pacManArray.get(i).getyOffset());
                    pane.getChildren().add(pacManArray.get(i).getArc());
                }
                break;
        }
    }

    public void moveFig() {

        int bounces = Integer.valueOf(bouncesLabel.getText());
        double radius = 20;

        ArrayList<Integer> tmpArray = new ArrayList<>();
        for (int i = 0; i < pacManArray.size(); i++) {
            for (int j = 0; j < pacManArray.size(); j++) {
                boolean flag = false;
                if (i == j) {
                    continue;
                }
                for (int tmp : tmpArray
                ) {
                    if (tmp == i || tmp == j) {
                        flag = true;
                    }
                }
                if (flag) {
                    continue;
                }
                double distance = Math.sqrt(Math.pow(pacManArray.get(i).getxOffset() - pacManArray.get(j).getxOffset(), 2) +
                        Math.pow(pacManArray.get(i).getyOffset() - pacManArray.get(j).getyOffset(), 2));
                if (distance < radius * 2) {
                    tmpArray.add(i);
                    tmpArray.add(j);
                }
            }
        }
        for (int tmp : tmpArray
        ) {
            pacManArray.get(tmp).collide();
        }
        bounces += tmpArray.size() / 2;

        for (PacMan pM : pacManArray) {
            pM.move(mainBox.getWidth(), mainBox.getHeight());
            pM.transformShape();
            pM.getArc().setLayoutX(pM.getxOffset());
            pM.getArc().setLayoutY(pM.getyOffset());
            if (pM.isbounced()) {//Tiene que ir despues de move() para que determine bien si reboto o no
                bounces++;
            }
        }

        setBounces(bounces);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bouncesLabel.setText(String.valueOf(getBounces()));
            }
        });
    }

    public int getBounces() {
        return bounces;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }
}