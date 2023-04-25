## Java类与对象基础练习完成情况

​	**注：在打包好的文件中，$pisc$文件夹用来存放`README`中需要的图片，其它的每一个`Solution`为包，分别对应练习的六道题**

### 1. Graphic Strikes Back

​	**完成情况：完成全部功能**

​	**UML类图：**

![image-20230424221756901](pics\Solution_1_UML_1.png)![image-20230424222043404](pics\Solution_1_UML_2.png)

**代码运行结果：**![Graphics](pics\Solution_1_Graphics.gif)

### 2. Nescafé

​	**完成情况：完成全部功能**

​	**代码运行结果：**

![image-20230415115932651](pics\solution_2.png)

### 3. Debit Payment

​	**完成情况：完成全部功能**

​	**UML类图**：![image-20230415201913624](pics\Solution_3_UML.png)

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

- **将过滤噪点`filter(String _fileIn, String _fileOut){}`分为如下几个步骤**

  - 根据文件路径将图片加载为`BufferedImage`对象：`BufferedImage image = loadImage(_fileIn);`

  - 将`BufferedImage`对象转换为一个二维数组，对应图片的每一个像素，存储的格式为`int`
    四个字节分别表示`[alpha, red, green, blue]`四个通道：`int[][] m = getPixelData(image)`

  - 对原图像数据调用过滤噪函数，得到存储了处理后图像颜色信息的二维数组：`int[][] n = medianFilter(m)`

  - 将二位数组展开为一维数组，用于创建新图像：`int[] resData = flattenImage(n)`

  - 调用函数，根据生成路径创建过滤后的新图像`createNewPng(resData, _fileOut)`

    ```java
    static void filter(String _fileIn, String _fileOut){
        BufferedImage image =  loadImage(_fileIn);		//first step
        int[][] matrixData = getPixelData(image);		//second step
        int[][] newPixelData = medianFilter(0, matrixData.length, 0, matrixData[0].length, matrixData);		//third step
        int[] resData = flattenImage(newPixelData);		//forth step
        createNewPng(matrixData.length, matrixData[0].length, resData, _fileOut); //last step
    }
    ```

- **加载图像为BufferedImage对象**

  - 调用函数`loadImage(_fileIn)`

  - 使用`File file = new File(_fileIn)`将图片文件作为文件类型加载进java文件

  - 使用`try-catch语句`调用图像输入输出的静态方法`ImageIO.read(file)`将文件读取为`BufferedImage`类

    ```java
    BufferedImage img = null;
    try {
        //Confirm the file path
        System.out.println(file.getCanonicalPath());
        img = ImageIO.read(file);
    } catch (IOException e) {
        e.printStackTrace();
    }
    ```

  - 将`img`作为返回值返回，在`filter`函数中接收

    ```java
    BufferedImage image =  loadImage(_fileIn);
    ```

- **将BufferedImage转换为二维数组数据**

  - 调用`getPixelData(image)`，将`BufferedImage`像素数据转换成可以处理的二维数组像素数据

  - 在处理的过程中，调用`BufferedImage.getRGB(int, int)`方法，将每一点的`RGB`数据返回

    - RGB数据是一个四个字节的`int`类型数值，其中从高到低四个字节分别表示：`alpha`值、红色通道、绿色通道、蓝色通道，取值分别为`0~255`，使用8位二进制数，即一个字节表示。每一个`int`值表示对应位置像素的颜色值
    - `getRGB`方法是`(int xAxios, int yAxios)`，与二维数组的`int[row][col]`刚好是对称关系，因此要交换次序

    ```java
    public static int[][] getPixelData(BufferedImage _image){
        int width = _image.getWidth();
        int height = _image.getHeight();
        int[][] matrixData = new int[height][width];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                //Pay attention to the coordinate relationship
                //getRGB(j, i) means coordinate (j, i)
                //which means the ith row and the jth column
                matrixData[i][j] = _image.getRGB(j, i);
            }
        }
        return matrixData;
    }
    ```

- ==**将二维数组进行处理，判断每一个像素点是否为噪点并处理**==

  - 创建新数组用于存储过滤后的图像像素数据

  - 二维循环遍历数组，对每一个像素

    - 调用`getGrayscale`获取该像素点的**灰度**

      ```java
      public static int getGrayscale(int _pixel){
      		return _pixel & 0x000000ff;
      	}
      ```

      由于是灰度图片，后三个字节的值可以表示该像素的灰度值(0~255)，使用按位与运算返回**灰度值**

    - 根据灰度值调用`isNoise(grayscale)`判断是否为噪点

      ```java
      static boolean isNoise(int _grayscale){
          return _grayscale == 0x00 || _grayscale == 0xff;
      }
      ```

      根据灰度值，如果是白色`0xff`或者黑色`0x00`则判断为噪点

      - 如果不是噪点，则直接将该像素点的值赋值给新二维数组`newMat`

      - ==**如果是噪点，调用处理噪点函数`handleNoise`将噪点周围两圈的所有非噪点的颜色平均灰度赋值给当前噪点（处理算法）**==


    ```java
    public static int[][] medianFilter(int _startY, int _endY, int _startX, int _endX, int[][] _matrixData){
    		int[][] newMatrix = new int[_endY-_startY][_endX-_startX];
    		for (int i = _startY; i < _endY; i++){
    			for (int j = _startX; j < _endX; j++){
    				//	the result of image.getRGB occupied 4 bytes
    				//	respectively means alpha, red, green, blue
    				int curPixel = _matrixData[i][j];
    				//	The alpha channel is always 255, and red == green == blue
    				//	Use an integer ’grayscale' to stand for the color
    				int grayscale = getGrayscale(curPixel);
    				if (isNoise(grayscale)){
    					int newGrayscale = handleNoise(i, j, _matrixData);
    					//	Convert the grayscale into RGB data
    					// 	The four byte will be [0xff][newGrayscale][newGrayscale][newGrayscale]
    					int newPixel = 255;
    					for (int k = 0; k < 3; k++){
    						newPixel = (newPixel << 8) | newGrayscale;
    					}
    					newMatrix[i][j] = newPixel;
    				}else{
    					newMatrix[i][j] = curPixel;
    				}
    			}
    		}
    		return newMatrix;
    	}
    ```

    - `handleNoise`获取周围两圈的灰度数据，使用二重循环判断是否为一个合法的像素点，即**数组下标不越界**并且**是一个噪点像素**。将所有符合这些条件的像素取灰度平均值，作为这个噪点新的灰度值

    - 返回后通过下面代码将灰度值转换成`RGB`格式的`int`值，赋值给新二维数组

      ```java
      int newPixel = 255;
      for (int k = 0; k < 3; k++){
          newPixel = (newPixel << 8) | newGrayscale;
      }
      ```

  - 二维数组遍历完后，会得到一个新的二维数组，存储了处理噪点后的图像数据，并返回

- 由于创建图像文件需要一维数组，将二维数组摊平为一维数组

- 使用一维数组创建图片文件

  - 根据原有图片的宽高创建新图像文件：`BufferedImage bufferedImage = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);`宽度与高度与之前相同
  - 使用`setRGB`函数，用存储图像`RGB`数据的一维数组作为参数传入，作为`BufferedImage`的图像数据
  - 根据输出路径创建文件，调用`ImageIO.write()`静态方法，将`BufferedImage`图像数据写为`png`格式的文件输出

  ```java
  static void createNewPng(int _height, int _width, int[] _resData, String _fileOut){
      //Read data into new buffered image
      BufferedImage bufferedImage = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);
      bufferedImage.setRGB(0, 0, _width, _height, _resData, 0, _width);
      try {
          File file = new File(_fileOut);
          //Render the image to output file
          ImageIO.write((RenderedImage) bufferedImage, "png", file);
      }catch (IOException e){
          e.printStackTrace();
      }
  }
  ```

  运行完成后，在项目路径可以看到文件名为`_fileOut`的处理好噪点的图片如下

​	**代码运行结果：**

![ship_out](pics\Solution_5.png)



### 6.Conway's Game of Life

​	**完成情况：完成全部功能**

​	**程序思路和算法：**

- 使用`JFram`创建要显示图片的窗口

- 继承`JPanel`创建`ImagePanel`用来存储要显示的图像，构造函数的参数为初态文件路径

  - 在构造函数中使用`FileInputStream`来读取初态图像文件

  - 将读取到的输入文件流作为参数传入`BufferReader`的构造函数得到一个`bufferReader`

    其内容如下

    - 第一次`readLine()`, 返回文件类型,p2或者p5，可以忽略
    - 第二次`readLine()`, 返回一个字符串数组，分别存储了图像的宽和高，将宽高存下

    ```java
    String[] sizeStr = bufferedReader.readLine().split(" ");
    this.width = Integer.parseInt(sizeStr[0]);
    this.height = Integer.parseInt(sizeStr[1]);
    ```

    - 第三次`readLine()`, 返回图像的**二维数组数据字符串**，使用如下方式存储为二维数组

      ```java
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
      ```

  - 使用得到的二维数组数据，配合上一题中的扁平化函数，将图像数据生成`bufferedImage`

    ```java
    // Init bufferedImage
    bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                //  Use flattenImage() implemented in Solution_5 to convert a 2d matrix into 1d array
                bufferedImage.setRGB(0, 0, width, height, MedianFilter.flattenImage(imageData), 0, width);
    ```

  - 至此，在`ImagePanel`中保留了如下四个成员变量

    ```java
    // 保存每一轮用于显示的图像数据
    BufferedImage bufferedImage;
    // 保存每一轮用于显示的像素颜色数据
    int[][] imageData;
    //	图像宽度
    int width;
    //	图像高度
    int height;
    ```

- 在主窗口中，添加`imagePanel`组件，使图像能在`frame`中显示

  ```java
  frame.add(imagePanel);            //Add panel to show image
  frame.setSize(800, 800);          //Set window size
  frame.setVisible(true);           //Show the window
  ```

- 循环调用`imagePanel`的成员方法`renewImage()`，并配合`Thread.sleep`按一定频率刷新显示的图像

  ```java
  Thread.sleep(1000);
  // Every interval make the img into next turn
  for (int k = 0; k < 50000; k++){
      Thread.sleep(100);
      imagePanel.renewImage();
  }
  ```

- 在`renewImage()`中，会调用实现好的`getNextTurn`方法，`getNextTurn`方法会根据当前存储的`imageData`的像素数据和游戏规则生成下一轮的图像数据，并将该数据用于更新图像

  ```java
  public void renewImage(){
      // 获取下一轮的数据
      int[][] newData = LifeGame.getNextTurn(0, this.height, 0, this.width, this.getImageData());
      // 设置为二维数组的数据
      this.setImageData(newData);
      // 用imageData数据生成bufferedImage数据
      this.renewBuffer();
      // 调用重写方法paintComponent()绘图
      this.updateUI();
  }
  ```

- `getNextTurn`方法中对每个当前的生命数据，新建一个二维数组用来存储下一轮的生命数据

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

- 至此，随着循环定时运行，可以使得图片产生动态规律的效果

​	**代码运行结果：**

![Graphics20230417_234116](pics\Solution_6_1.gif)

![Graphics20230417_233527](pics\Solution_6_2.gif)
