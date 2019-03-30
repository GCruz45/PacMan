package model;

import controller.PacManController;

public class Threads extends Thread {

    private PacManController controller;

    public Threads(PacManController controller) {
        this.controller = controller;
    }

    public void run() {
        while (true) {
            controller.moveFig();
            try {
                sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}