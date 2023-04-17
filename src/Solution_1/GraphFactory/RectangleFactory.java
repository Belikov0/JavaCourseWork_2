package Solution_1.GraphFactory;

import Solution_1.Graph.Rectangle;

public class RectangleFactory implements GraphFactory {
    @Override
    public Rectangle createGraph() {
        return new Rectangle();
    }
}
