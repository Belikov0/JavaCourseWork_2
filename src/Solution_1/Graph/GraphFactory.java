package Solution_1.Graph;

public class GraphFactory{

    public Sprite createGraph(String _graph){
        if (_graph == "Rectangle"){
            return new Rectangle();
        }else if (_graph == "Oval"){
            return new Oval();
        }
        return null;
    }

}
