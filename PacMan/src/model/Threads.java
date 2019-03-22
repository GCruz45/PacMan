package model;

import controller.PacManCatcherController;

public class Threads extends Thread {

    private PacManCatcherController controller;

    public Threads(PacManCatcherController controller) {
        this.controller = controller;
    }
    public void run(){
        while (true){
            controller.moveFig();
            try {
                sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}