package Solution_1.GraphFactory;

import Solution_1.Graph.Oval;

public class OvalFactory implements GraphFactory {
    @Override
    public Oval createGraph() {
        return new Oval();
    }
}
