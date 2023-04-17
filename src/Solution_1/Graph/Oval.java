package Solution_1.Graph;

import java.awt.*;

// New bouncing graph
public class Oval implements Sprite {
    private int xAxisLength;
    private int yAxisLength;
    private Color color;

    //  Constructor
    public Oval(){}
    public Oval(int _xAxisLength, int _yAxisLength, Color _color){
        this.color = _color;
        this.xAxisLength = _xAxisLength;
        this.yAxisLength = _yAxisLength;
    }


    @Override   /** Init private member variables. */
    public void setSprite(int _width, int _height, Color _color) {
        this.color = _color;
        this.xAxisLength = _width;
        this.yAxisLength = _height;
    }

    @Override
    public void draw(Graphics surface, int x, int y) {
        surface.setColor(color);
        surface.fillArc(x, y, xAxisLength, yAxisLength, 0, 360);
//        ((Graphics2D) surface).setStroke(new BasicStroke(30.0f));
        surface.setColor(Color.RED);
        surface.drawArc(x, y, xAxisLength, yAxisLength, 0, 180);
        surface.setColor(Color.GREEN);
        surface.drawArc(x, y, xAxisLength, yAxisLength, 180, 180);
    }

    @Override
    public int getWidth() {
        return xAxisLength;
    }

    @Override
    public int getHeight() {
        return yAxisLength;
    }


}
