package Solution_1.Mover;

public class MoverFactory {
    public Mover createMover(String _mover){
        if (_mover == "Bouncer"){
            return new Bouncer();
        }else if (_mover == "StraightMover"){
            return new StraightMover();
        }
        return null;
    }
}
