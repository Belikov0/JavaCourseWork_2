package Solution_1.Mover;

import Solution_1.Graph.Sprite;

import java.awt.*;

public interface Mover {
    void draw(Graphics surface);
    void setMover(int x, int y, Sprite sprite);
}
