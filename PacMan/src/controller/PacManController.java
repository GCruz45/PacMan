package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.PacMan;
import model.Threads;

import java.util.ArrayList;

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
    public void startGame() {

        startButton.setVisible(false);

        Threads threads = new Threads(this);
        threads.start();
        int positionFixer = 0;
        for (int i = 0; i < 5; i++) {
            PacMan pacMan = new PacMan(20, true, mainBox.getWidth() / 2 - 100 +
                    positionFixer, mainBox.getHeight() / 2 - 60 + positionFixer);
            pacManArray.add(pacMan);
            pacManArray.get(i).getArc().setLayoutX(mainBox.getWidth() / 2 - 100 + positionFixer);
            pacManArray.get(i).getArc().setLayoutY(mainBox.getHeight() / 2 - 100 + positionFixer);
            positionFixer += 50;
            pane.getChildren().add(pacManArray.get(i).getArc());
        }
    }

    public void moveFig() {
        int bounces = Integer.valueOf(bouncesLabel.getText());
        double radius = 22;
        int fixedBounces = 0;
        for (int i = 0; i < pacManArray.size(); i++) {
            for (int j = 1; j < pacManArray.size(); j++) {
                if (i == j) {
                    continue;
                }
                if (Math.abs(pacManArray.get(i).getArc().getLayoutX() - pacManArray.get(j).getArc().getLayoutX()) < radius
                        && Math.abs(pacManArray.get(i).getArc().getLayoutY() - pacManArray.get(j).getArc().getLayoutY()) < radius) {
                    pacManArray.get(i).collide();
                    pacManArray.get(j).collide();
                    fixedBounces++;
                }
            }
        }
        bounces += fixedBounces / 2;

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

    public void setBouncesLabel(int bounces) {
        this.bouncesLabel.setText(String.valueOf(bounces));
    }
}