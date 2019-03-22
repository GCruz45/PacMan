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
import java.util.Random;

public class PacManCatcherController {

    private ArrayList<PacMan> pacManArray;
    private ArrayList<PacMan> arrayCopy;

    public PacManCatcherController() {
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
        Random randomizeLocation = new Random();
        for (int i = 0; i < 5; i++) {
            PacMan pacMan = new PacMan(20, true, 8, mainBox.getWidth() / 2 - 100 +
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
        for (PacMan pM : pacManArray) {
            pM.move(mainBox.getWidth(), mainBox.getHeight());
            pM.transformShape();
            pM.getArc().setLayoutX(pM.getxOffset());
            pM.getArc().setLayoutY(pM.getyOffset());
            if (pM.isbounced()) {//Tiene que ir despues de move() para que determine bien si reboto o no
                bounces++;
            }

            int radius = pacManArray.get(0).getRadius();
            for (int i = 0; i < pacManArray.size(); i++) {
                for (int j = 0; j < pacManArray.size(); j++) {
                    if ((pacManArray.get(i).getArc().getLayoutX() + radius == pacManArray.get(j).getArc().getLayoutX() + radius &&
                            pacManArray.get(i).getArc().getLayoutY() + radius == pacManArray.get(j).getArc().getLayoutY() + radius)
                            || pacManArray.get(i).getArc().getLayoutX() - radius == pacManArray.get(j).getArc().getLayoutX() - radius &&
                            pacManArray.get(i).getArc().getLayoutY() + radius == pacManArray.get(j).getArc().getLayoutY() + radius) {
                    }
                }
            }
//            arrayCopy = pacManArray;
//            int fixer = 0;
//            for (int i = 0; i < arrayCopy.size(); i++) {
//                for (int j = 1; j < arrayCopy.size(); j++) {
//                    ArrayList<Long> xOffsets = new ArrayList<>();
//                    ArrayList<Long> yOffsets = new ArrayList<>();
//                    for (int degrees = 0; degrees < 360; degrees += 2) {
//                        xOffsets.add((long) (arrayCopy.get(i).getxOffset() + ((long) arrayCopy.get(i).getRadius()) / Math.cos(Math.toRadians(degrees))));
//                        yOffsets.add((long) (arrayCopy.get(i).getyOffset() + ((long) arrayCopy.get(i).getRadius()) / Math.sin(Math.toRadians(degrees))));
//                    }
//                    for (int degrees = 0; degrees < 360; degrees += 2) {
//                        xOffsets.add((long) (arrayCopy.get(j).getxOffset() + (long) (arrayCopy.get(j).getRadius()) / Math.cos(Math.toRadians(degrees))));
//                        yOffsets.add((long) (arrayCopy.get(j).getyOffset() + (long) (arrayCopy.get(j).getRadius()) / Math.sin(Math.toRadians(degrees))));
//                    }
//                    for (int k = 0; k < xOffsets.size() / 2; k++) {
//                        if (i < arrayCopy.size() && j < arrayCopy.size()) {
//                        if (arrayCopy.get(i).getArc().getLayoutX() + xOffsets.get(k) == arrayCopy.get(j).getArc().getLayoutX() + xOffsets.get(k + 180)) {
//                            for (int l = 0; l < yOffsets.size() / 2; l++) {
//                                System.out.println("xoff " + xOffsets.size());
//                                System.out.println("yoff " + yOffsets.size());
//                                System.out.println(arrayCopy.size());
//                                System.out.println("i: " + i);
//                                System.out.println("j: " + j);
//                                    if (arrayCopy.get(i).getArc().getLayoutY() + yOffsets.get(l) == arrayCopy.get(j).getArc().getLayoutY() + yOffsets.get(l + 180)) {
//                                        arrayCopy.get(i).collide();
//                                        arrayCopy.get(j).collide();
//                                        bounces++;
//                                        if (i > j) {
//                                            arrayCopy.remove(i);
//                                            arrayCopy.remove(j);
//                                        } else {
//                                            arrayCopy.remove(j);
//                                            arrayCopy.remove(i);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
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