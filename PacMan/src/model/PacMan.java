package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

import java.util.Random;

public class PacMan {

    private int radius;
    private boolean isActive;
    private double speed;
    private int xDirection;
    private int yDirection;
    private double xOffset;
    private double yOffset;
    private Arc arc;
    private boolean animationStep = true;
    private double angleTransform;
    private boolean bounced = false;

    public PacMan(int radius, boolean isActive, double xOffset, double yOffset) {
        Random randomBoolean = new Random();
        int xDir;           // Si es 1, va hacia la derecha.
        int yDir;           // Si es 1, va hacia abajo.
        if (randomBoolean.nextBoolean()) {
            xDir = 1;
        } else {
            xDir = -1;
        }
        if (randomBoolean.nextBoolean()) {
            yDir = 1;
        } else {
            yDir = -1;
        }
        if (randomBoolean.nextBoolean()) {   //Escoge una de las dos direcciones, para que no se mueva diagonalmente
            xDir = 0;
        } else {
            yDir = 0;
        }
        Random rng = new Random();
        this.radius = radius;
        this.isActive = isActive;
        this.speed = rng.nextInt(6) + 5;
        this.xDirection = xDir;
        this.yDirection = yDir;
        this.arc = new Arc();
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        arc.setRadiusX(radius);
        arc.setRadiusY(radius);
        arc.setStartAngle(45);
        arc.setFill(Color.rgb(255, 255, 0));
        arc.setType(ArcType.ROUND);
        arc.setLength(270);
        arc.setStroke(Color.valueOf("BLACK"));
        arc.setStrokeWidth(3);
    }

    public void transformShape() {
        if (arc.getLength() == 270) {
            arc.setStartAngle(22.5 + angleTransform);
            arc.setLength(315);
        } else if (arc.getLength() == 315) {
            if (animationStep) {
                arc.setStartAngle(0 + angleTransform);
                arc.setLength(360);
                animationStep = false;
            } else {
                arc.setStartAngle(45 + angleTransform);
                arc.setLength(270);
                animationStep = true;
            }
        } else if (arc.getLength() == 360) {
            arc.setStartAngle(22.5 + angleTransform);
            arc.setLength(315);
        }
    }

    public void move(double windowWidth, double windowHeight) {
        bounced = false;
        if (xOffset - radius - 5 < 0 || xOffset + radius + 5 > windowWidth) {
            xDirection *= -1;
            bounced = true;
        }
        if (yOffset - radius - 38 < 0 || yOffset + radius + 5 > windowHeight) { //La altura de menuBar es 30
            yDirection *= -1;
            bounced = true;
        }

        switch (xDirection) {
            case 1:
                angleTransform = 0;
                break;
            case -1:
                angleTransform = 180;
                break;
            case 0:
                if (yDirection == 1) {
                    angleTransform = 270;
                } else {
                    angleTransform = 90;
                }
                break;
        }
        xOffset = xOffset + speed * xDirection;
        yOffset = yOffset + speed * yDirection;
    }

    public void collide() {
        this.xDirection *= -1;
        this.yDirection *= -1;
    }

    public void catchPacMan() {
        speed = 0;
        arc.setFill(Color.rgb(255, 30, 0));
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getxDirection() {
        return xDirection;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public Arc getArc() {
        return arc;
    }

    public void setArc(Arc arc) {
        this.arc = arc;
    }

    public boolean isAnimationStep() {
        return animationStep;
    }

    public void setAnimationStep(boolean animationStep) {
        this.animationStep = animationStep;
    }

    public double getAngleTransform() {
        return angleTransform;
    }

    public void setAngleTransform(double angleTransform) {
        this.angleTransform = angleTransform;
    }

    public boolean isbounced() {
        return bounced;
    }
}
