import java.awt.image.*;
import java.awt.color.ColorSpace;
import java.io.*;
import javax.imageio.*;

public class AsciiArt {
	Raster imageRaster;
	BufferedImage image;
	String characterSet = "Wwli:,. ";

	public void loadImage(String path) {
		try {
			File imageFile = new File(path);
			image = ImageIO.read(imageFile);
			System.out.println("Reading complete. Details of Image: \n");
			System.out.println(image + "\n");
			imageRaster = image.getData();
			System.out.println("Type of Image Data :" + image.getType());
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateAsciiArt() {
		String row = "";
		int[] pixelData = new int[3];
		int grayScale = 0;
		int charIndex = 0;
		try {
			FileWriter outputFile = new FileWriter("output.txt");
			for(int y = 0; y < image.getHeight(); y++) {
				row = "";
				for(int x = 0; x < image.getWidth(); x++) {
					pixelData = getPixels(x, y);
					grayScale = getGrayscaleVal(pixelData[0], pixelData[1], pixelData[2]);
					charIndex = map(grayScale, 0, 255, 0, characterSet.length() - 1);
					row += characterSet.charAt(charIndex);
				}
				outputFile.write(row + "\n");
			}
			outputFile.close();
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
    	}

	}

	public int getGrayscaleVal(int r, int g, int b) {
		double gsval = 0.2126 * r + 0.7152 * b + 0.0722 * g;
		int res = (int)Math.floor(gsval);
   		return res;
	}

	public int[] getPixels(int x, int y) {
		int[] pixelData = new int[3];
		imageRaster.getPixel(x, y, pixelData);
		return pixelData;
	} 

	public int map(int num, int in_min, int in_max, int out_min, int out_max) {
	   return (num - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}


	public static void main(String[] args) {
		AsciiArt asciiArt = new AsciiArt();
		asciiArt.loadImage("dog.jpg");
		asciiArt.generateAsciiArt();
	}
}