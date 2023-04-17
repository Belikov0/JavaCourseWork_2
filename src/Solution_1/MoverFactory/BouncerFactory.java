package Solution_1.MoverFactory;

import Solution_1.Mover.Bouncer;
import Solution_1.Mover.Mover;

public class BouncerFactory implements MoverFactory{
    @Override
    public Bouncer createMover() {
        return new Bouncer();
    }
}
