package Solution_6;

import Solution_5.MedianFilter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static java.lang.Thread.sleep;

public class LifeGame {
    // Describe the four rules of game of life
    public enum RulesEnum {REPRODUCTION, LONELINESS, OVERCROWD, NORMALITY, REST}
    // Describe two types of status of a cell
    public enum StatusEnum { ALIVE, DEAD }
    // Set constant to represent status of a cell
    static final int WHITE = 0xfffffffd;
    static final int BLACK = 0xff000000;


    // Set next turn's image data from 1D data array
    static BufferedImage getNextImg(int _height, int _width, int[] _imgData){
        BufferedImage bufferedImage = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, _width, _height, _imgData, 0, _width);
        return bufferedImage;
    }

    // Judge if the cell is dead
    static boolean isDead(StatusEnum _cell){
        return _cell == StatusEnum.DEAD;
    }

    // Judge if the cell is alive
    static boolean isAlive(StatusEnum _cell){
        return _cell == StatusEnum.ALIVE;
    }

    // Convert the number describing the color of the pixel into
    // the concept of cell(abstraction)
    static StatusEnum getStatusByColor(int _color){
        return switch (_color & 0xff){
            case 0xfd -> StatusEnum.ALIVE;
            case 0x00 -> StatusEnum.DEAD;
            default -> null;
        };
    }

    // Convert the cell's status into image data of a pixel
    static int getColorByStatus(StatusEnum _status){
        return switch (_status){
            case ALIVE -> WHITE;
            case DEAD -> BLACK;
        };
    }

    // Four rules can be refined as for conditions below
    // Four conditions are not completely consistent to the rules
    // Because the dead cell is STILL dead in the next turn unless it has three alive cell around
    static RulesEnum lifeRules(int _aroundAliveCount, StatusEnum _currentCellStatus){
        // Rule Reproduction
        if (_aroundAliveCount == 3 && isDead(_currentCellStatus)){
            // the 'isDead condition' is redundant
            return RulesEnum.REPRODUCTION;
        // Rule Loneliness
        }else if (_aroundAliveCount < 2 && isAlive(_currentCellStatus)){
            // the 'isAlive condition' is redundant because dead cell keep its status
            return RulesEnum.LONELINESS;
        //  Rule Overcrowd
        } else if (_aroundAliveCount > 3 && isAlive(_currentCellStatus)) {
            // the 'isAlive condition' is redundant because dead cell keep its status
            return RulesEnum.OVERCROWD;
        //  Rule Normality
        }else if ((_aroundAliveCount == 2 || _aroundAliveCount == 3) && isAlive(_currentCellStatus)){
            return RulesEnum.NORMALITY;
        }else {
            return RulesEnum.REST;
        }
    }

    // Get new status by cells around and current cell's status
    static StatusEnum getNewCell(int _y, int _x, int[][] _map){
        // Store the live status of eight cells around current cell
        StatusEnum currentStatus = getStatusByColor(_map[_y][_x]);
        int aroundAliveCount = 0;
        for (int i = _y-1; i <= _y+1; i++){
            for (int j = _x-1; j <= _x+1; j++){
                // Repeating pic
                int row = (i+_map.length)%_map.length;
                int col = (j+_map[0].length)%_map[0].length;
                StatusEnum status = getStatusByColor(_map[row][col]);
                // Count alive cell around
                if (isAlive(status) && !(i == _y && j == _x)){
                    aroundAliveCount++;
                }
            }
        }
        //  Conditional setting of new status of current cell
        //  aka the rule of Conway's Game of Life
        return switch (lifeRules(aroundAliveCount, currentStatus)){
            case REPRODUCTION -> StatusEnum.ALIVE;
            case LONELINESS -> StatusEnum.DEAD;
            case OVERCROWD ->  StatusEnum.DEAD;
            case NORMALITY -> StatusEnum.ALIVE;
            case REST -> StatusEnum.DEAD;
        };
    }

    // Get new image data from the old image data
    public static int[][] getNextTurn(int _startY, int _endY, int _startX, int _endX, int[][] _currentMap){
        int[][] newMap = new int[_endY-_startY][_endX-_startX];
        int cnt = 0;
        for (int i = _startY; i < _endY; i++){
            for (int j = _startX; j < _endX; j++){
                //  Convert image data into cell status, making the programme more semantic
               StatusEnum newCell = getNewCell(i, j, _currentMap);
               //   Set the new image data into the corresponding pixel by the cell's status
               newMap[i][j] = getColorByStatus(newCell);
            }
        }
        return newMap;
    }


    public static void main(String[] args) throws InterruptedException {
        // File names
        String s16 = "16x16.pgm";
        String s64 = "64x64.pgm";
        String s128 = "128x128.pgm";
        String s256 = "256x256.pgm";
        String s512 = "512x512.pgm";
        String s5120 = "5120x5120.pgm";

        //  Create window and init GUI
        JFrame frame = new JFrame("Conway's Game of Life");
        ImagePanel imagePanel = new ImagePanel(s16);   //Use image to set image data
        frame.add(imagePanel);                          //Add panel to show image
        frame.setSize(800, 800);          //Set window size
        frame.setVisible(true);                         //Show the window

        Thread.sleep(3000);
        // Every interval make the img into next turn
        for (int k = 0; k < 50000; k++){
            Thread.sleep(40);
            imagePanel.renewImage();
        }
    }
}

class ImagePanel extends JPanel {
    private BufferedImage bufferedImage;
    private int[][] imageData;
    int width;
    int height;

    public ImagePanel(String fileIn){
        try{
            FileInputStream fileInputStream = new FileInputStream(fileIn);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            // String: p5, trivial
            bufferedReader.readLine();
            // String describing the size of the pics
            // Set the width and height of the image
            String[] sizeStr = bufferedReader.readLine().split(" ");
            this.width = Integer.parseInt(sizeStr[0]);
            this.height = Integer.parseInt(sizeStr[1]);
            // Biggest pixel, trivial
            bufferedReader.readLine();

            // 2d image data
            imageData = new int[height][width];
            for (int i = 0; i < height; i++){
                for (int j = 0; j < width; j++){
                    int tmp = 255;
                    // return the color value of each pixel typed int ranged [0, 255]
                    int currentColor = bufferedReader.read();
                    // Set the color into RGB value
                    for (int k = 0; k < 3; k++){
                        tmp = (tmp << 8) | currentColor;
                    }
                    imageData[i][j] = tmp;
                }
            }
            // Init bufferedImage
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //  Use flattenImage() implemented in Solution_5 to convert a 2d matrix into 1d array
            bufferedImage.setRGB(0, 0, width, height, MedianFilter.flattenImage(imageData), 0, width);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Getters
    public int[][] getImageData(){
        return imageData;
    }

    // Setters, do not provide the setter of width and height
    // Because they are only dependent on the image size
    public void setImageData(int[][] _newData){
        imageData = _newData;
    }

    // Renew the content of variable bufferedImage by the newly set rgb image data
    public void renewBuffer(){
        //  Use flattenImage() implemented in Solution_5 to convert a 2d matrix into 1d array
        bufferedImage.setRGB(0, 0, width, height, MedianFilter.flattenImage(imageData), 0, width);
    }

    //  Get and set the image data of the next turn by current image data
    public void renewImage(){
        int[][] newData = LifeGame.getNextTurn(0, this.height, 0, this.width, this.getImageData());
        this.setImageData(newData);
        this.renewBuffer();
        this.updateUI();
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(this.bufferedImage, 0, 0, getWidth(), getHeight(), null);
    }
}
