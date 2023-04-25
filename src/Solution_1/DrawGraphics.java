package Solution_1;

import Solution_1.Graph.Sprite;
import Solution_1.Graph.GraphFactory;
import Solution_1.Mover.Mover;
import Solution_1.Mover.MoverFactory;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class DrawGraphics {
    // Used in question_1 and question_2
    /*
    ArrayList<Bouncer> bouncers;
    ArrayList<StraightMover> straightMovers;
    */

    // Used in question_3, use interface to abstract two types of mover
    ArrayList<Mover> movers;

    /** Initializes this class for drawing. */
    public DrawGraphics() {
//        bouncers = new ArrayList<>();
//        straightMovers = new ArrayList<>();
        movers = new ArrayList<>();

        // Create graphs
//        Rectangle box = new Rectangle(15, 20, Color.RED);
//        Oval oval = new Oval(30, 20, Color.orange);

        // Create graphs using factories
        GraphFactory graphFactory = new GraphFactory();
        Sprite rect = graphFactory.createGraph("Rectangle");
        Sprite oval = graphFactory.createGraph("Oval");

        // Set init value to graphs
        rect.setSprite(15, 20, Color.RED);
        oval.setSprite(30, 20, Color.ORANGE);

    /*
        // Init moving sprites from graphs
        Bouncer movingSprite1 = new Bouncer(100, 170, rect);
        Bouncer movingSprite2 = new Bouncer(120, 130, oval);
        Bouncer movingSprite3 = new Bouncer(30, 50, rect);
    */
        // Create moving sprites from graphs using factory
        MoverFactory moverFactory = new MoverFactory();
        Mover movingSprite1 = moverFactory.createMover("Bouncer");
        Mover movingSprite2 = moverFactory.createMover("Bouncer");
        Mover movingSprite3 = moverFactory.createMover("Bouncer");

        // Init bouncers
        movingSprite1.setMover(100, 170, rect);
        movingSprite2.setMover(120, 130, oval);
        movingSprite3.setMover(30, 50, rect);

        // Set moving direction and speed of bouncers
        movingSprite1.setMovementVector(3, 1);
        movingSprite2.setMovementVector(2, -4);
        movingSprite3.setMovementVector(-4, 3);

    /*
        // Init straight mover from graphs
        StraightMover straightMover1 = new StraightMover(200, 150, rect);
        StraightMover straightMover2 = new StraightMover(60, 30, oval);
    */

        // Create straight mover from factory
        Mover straightMover1 = moverFactory.createMover("StraightMover");
        Mover straightMover2 = moverFactory.createMover("StraightMover");

        // Init straight mover
        straightMover1.setMover(200, 150, rect);
        straightMover2.setMover(60, 30, oval);

        //  Set moving direction and speed of movers
        straightMover1.setMovementVector(-2, 3);
        straightMover2.setMovementVector(2,-5);

    /*
        // Add bouncers into corresponding list
        bouncers.add(movingSprite1);
        bouncers.add(movingSprite2);
        bouncers.add(movingSprite3);
        // Add straight movers into corresponding list
        straightMovers.add(straightMover1);
        straightMovers.add(straightMover2);
    */

        /** Add movers into Mover list. */
        movers.add(straightMover1);
        movers.add(straightMover2);
        movers.add(movingSprite1);
        movers.add(movingSprite2);
        movers.add(movingSprite3);
    }

    /** Draw the contents of the window on surface. */
    public void draw(Graphics surface) {
    /*
        // Draw bouncers in the frame
        for (Bouncer movingSprite : bouncers){
            movingSprite.draw(surface);
        }

        // Draw straightMovers in the frame
        for (StraightMover mover : straightMovers){
            mover.draw(surface);
        }
    */
        // Draw movers in the frame
        for (Mover mover : movers){
            mover.draw(surface);
        }
    }
}
