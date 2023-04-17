package Solution_1.MoverFactory;

import Solution_1.Mover.Mover;
import Solution_1.Mover.StraightMover;

public class StraightMoverFactory implements MoverFactory{
    @Override
    public StraightMover createMover() {
        return new StraightMover();
    }
}
