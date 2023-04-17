package Solution_5;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MedianFilter {
	//filter reads in a png image, applies the filter and outputs the result as a png image.
	static void filter(String _fileIn, String _fileOut){
		BufferedImage image =  loadImage(_fileIn);
		int[][] matrixData = getPixelData(image);
		int[][] newPixelData = medianFilter(0, matrixData.length, 0, matrixData[0].length, matrixData);
		int[] resData = flattenImage(newPixelData);
		createNewPng(matrixData.length, matrixData[0].length, resData, _fileOut);
	}

	// loadImage opens a file and returns the contents as an image object.
	public static BufferedImage loadImage(String fileIn){
		//Read the image file
		File file = new File(fileIn);
		BufferedImage img = null;
		try {
			//Confirm the file path
			System.out.println(file.getCanonicalPath());
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	 }


	//getPixelData transfers an image object to 2D pixel(make matrix)
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

	 
	// medianFilter applies the filter between the given x and y bounds on the given matrix.
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

	// Get the grayscale of the pixel
	// The principle is [alpha, gray, gray, gray] & [0x00, 0x00, 0x00, 0xff] = [0x00, 0x00, 0x00, gray]
	public static int getGrayscale(int _pixel){
		return _pixel & 0x000000ff;
	}

	//	Judge if a pixel is noisy point by its grayscale
	//	0x00 means black while 0xff stands for white
	static boolean isNoise(int _grayscale){
		return _grayscale == 0x00 || _grayscale == 0xff;
	}

	//	handle the noise by get the average grayscale of the normal pixel around the noise
	// 	The round space sizes 5*5
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

	// Judge if the coordinate is legal or not
	public static boolean isLegalPixel(int _j, int _i, int[][] _matrixData){
		return _j > 0 && _i > 0 && _j < _matrixData.length && _i < _matrixData[0].length;
	}

	// flattenImage takes a 2D array and flattens it into a single 1D array.
	public static int[] flattenImage(int[][] _newPixelData){
		int height = _newPixelData.length;
		int width = _newPixelData[0].length;
		int[] res = new int[height*width];
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				res[i*width+j] = _newPixelData[i][j];
			}
		}
		return res;
	}
	
	//createNewPng creates a new filtered png file.
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

	public static void main(String[] args) {
	// main reads in the filepath and calls filter().
		String filePathIn = "ship.png";
		String filePathOut = "ship_out.png";
		filter(filePathIn,filePathOut);
	}
}
