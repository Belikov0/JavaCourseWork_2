## Java类与对象基础练习完成情况

​	**注：在打包好的文件中，$pisc$文件夹用来存放`README`中需要的图片，其它的每一个`Solution`为包，分别对应练习的六道题**

### 1. Graphic Strikes Back

​	**完成情况：完成全部功能**

**代码运行结果：**![Graphics](pics\Solution_1_Graphics.gif)

### 2. Nescafé

​	**完成情况：完成全部功能**

​	**代码运行结果：**

![image-20230415115932651](pics\solution_2.png)

### 3. Debit Payment

​	**完成情况：完成全部功能**

**UML类图**：![image-20230415201913624](pics\Solution_3_UML.png)

​	**代码运行结果：**

![image-20230415200828989](pics\Solution_3_result.png)

### 4.Odd and Even

​	**完成情况：完成全部功能**

​	**代码运行结果：**

​		说明：测试了初值错误时的异常

![image-20230416113001247](pics\Solution_4.png)

### 5.Filter Dots

​	**完成情况：完成全部功能**

​	**程序思路与算法：**

- 二重循环遍历图片每一个像素点，判断是否是噪点

  - 如果不是噪点，则`continue`

  - 如果是噪点，将噪点周围两圈的所有非噪点的颜色平均灰度赋值给当前噪点

    ```java
    static int handleNoise(int _y, int _x, int[][] _matrixData){
        ArrayList list = new ArrayList();
        int sy = _y - 2;
        int ey = _y + 2;
        int sx = _x - 2;
        int ex = _x + 2;
        for (int i = sy; i <= ey; i++){
            for (int j = sx; j <= ex; j++){
                //Continue if the coordinate is illegal
                if (!isLegalPixel(j, i, _matrixData))
                    continue;
                int grayscale = getGrayscale(_matrixData[i][j]);
                //Continue if the pixel is a noise
                if (isNoise(grayscale))
                    continue;
                list.add(grayscale);
            }
        }
        int sum = 0;
        for (int i = 0; i < list.size(); i++){
            sum += (int)list.get(i);
        }
        int ave = (int)(sum/list.size());
        return ave;
    }
    ```

  - 遍历到末尾后基本处理完毕

​	**代码运行结果：**

![ship_out](pics\Solution_5.png)



### 6.Conway's Game of Life

​	**完成情况：完成全部功能**

​	**程序思路和算法：**

- 对每个当前的生命数据，新建一个二维数组用来存储下一轮的生命数据

  - 遍历二维数组，对每一个像素点，即`cell`，获取它本身的生存状态和周围8个细胞的生存状态

  - 根据规则，可以编写如下代码，得到下一轮该细胞的生存状态。其中`RulesEnum`是表示四种游戏规则的枚举类型，分别为

    - 繁殖：`DEAD`状态的细胞，周围有三个活细胞，下一轮状态为`ALIVE`
    - 喧闹：`ALIVE`状态的细胞，周围有三个以上的活细胞，下一轮状态为`DEAD`
    - 孤独：`ALIVE`状态的细胞，周围有一个或零个细胞，下一轮状态为`DEAD`
    - 正常：`ALIVE`状态的细胞，周围有两个或三个活细胞，下一轮状态不变

    根据规则可以将代码编写如下，隐含的条件为：死细胞保持死亡状态

    在代码中设置为`REST`规则

    ```java
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
    ```

  - 根据当前细胞要遵循的规则将当前细胞下一轮的状态返回，由于`_map`中存储的是`RGB`数据，通过静态方法`getStatusByColor`颜色数据转换为**语义化**的生存状态`Status`

    ```java
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
    ```

  - 对每一个细胞获取下一轮的状态，通过方法`getColorByStatus`将`status`转化为生成图像的`RGB`数据，并将存储下一轮显示信息的数组返回

    ```java
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
    ```

  - 在`ImagePanel`中更新成员变量`imageData`后并更新 `BufferedImage`，并调用方法`updateUI`刷新显示，该方法会在一个计时循环中被反复调用，以获得变化的图像。成员变量`imageData`会通过`getImageData`方法在每一次需要获取下一轮数据时被调用

    ```java
    // 在循环中调用用于更新团
    public void renewImage(){
        //	接收下一轮的数据
        int[][] newData = LifeGame.getNextTurn(0, this.height, 0, this.width, this.getImageData());
        // set 方法更新成员变量
        this.setImageData(newData);
        // 更新图片数据
        this.renewBuffer();
        this.updateUI();
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
    ```

  - 在`main`方法中，循环调用上述方法，达到获得动态图片的效果

    ```java
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
    ```

​	**代码运行结果：**

![Graphics20230417_234116](pics\Solution_6_1.gif)

![Graphics20230417_233527](pics\Solution_6_2.gif)
