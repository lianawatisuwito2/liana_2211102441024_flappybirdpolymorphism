import greenfoot.*;

public class Flappybird extends Actor {
    private double g = 1;
    private int y = 300;
    private boolean haspressed = false;
    private boolean isalive = true;
    private boolean isacross = false;
    private boolean hasaddscore = false;

    private GreenfootImage[] birdImages;
    private int currentImageIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed = 10;

    public Flappybird() {
        birdImages = new GreenfootImage[2];
        birdImages[0] = new GreenfootImage("bird1.png");
        birdImages[1] = new GreenfootImage("bird2.png");

        for (GreenfootImage image : birdImages) {
            image.scale(50, 40);
        }

        setImage(birdImages[0]);
    }

    /**
     * Act - do whatever the Flappybird wants to do.
     */
    public void act() {
        animationCounter++;

        if (animationCounter % animationSpeed == 0) {
            if (currentImageIndex == 0) {
                setImage(birdImages[1]);
                currentImageIndex = 1;
            } else {
                setImage(birdImages[0]);
                currentImageIndex = 0;
            }
        }

        if (spacePressed()) {
            g = -2;
        }
        g += 0.1;
        y += g;
        setLocation(getX(), (int) (y));

        if (isTouchpipe()) {
            isalive = false;
        }

        if (isAtEdge()) {
            Greenfoot.playSound("peng.mp3");
            isalive = false;
        }

        if (!isalive) {
            getWorld().addObject(new Gameover(), 300, 200);
            getWorld().removeObject(this);
        }

        if (!hasaddscore && isacross && isalive) {
            Greenfoot.playSound("score.mp3");
            Score.add(1);
        }
        hasaddscore = isacross;
    }

    public boolean spacePressed() {
        boolean pressed = false;
        if (Greenfoot.isKeyDown("space")) {
            if (!haspressed) {
                Greenfoot.playSound("flay.mp3");
                pressed = true;
            }
            haspressed = true;
        } else {
            haspressed = false;
        }
        return pressed;
    }

    public boolean isTouchpipe() {
    isacross = false;
    for (Pipe pipe : getWorld().getObjects(Pipe.class)) {
        // Skip checking collision with Plane
        if (pipe instanceof Plane) {
            continue;
        }

        // Check for collision with other pipes
        if (Math.abs(pipe.getX() - getX()) < 60) {
            if (Math.abs(pipe.getY() + 30 - getY()) > 37) {
                Greenfoot.playSound("peng.mp3");
                isalive = false;
            }
            isacross = true;
        }
    }

    // Check for collision with Plane separately
    for (Plane plane : getWorld().getObjects(Plane.class)) {
        if (Math.abs(plane.getX() - getX()) < 60 && Math.abs(plane.getY() - getY()) < 37) {
            Greenfoot.playSound("peng.mp3");
            isalive = false;
            isacross = true;
        }
    }

    return !isalive;
}


    }
