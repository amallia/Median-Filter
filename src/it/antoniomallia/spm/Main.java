package it.antoniomallia.spm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import nu.pattern.OpenCV;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Main {

	
	
	//
	// public static int randInt(int min, int max) {
	//
	// // Usually this can be a field rather than a method variable
	// Random rand = new Random();
	//
	// // nextInt is normally exclusive of the top value,
	// // so add 1 to make it inclusive
	// int randomNum = rand.nextInt((max - min) + 1) + min;
	//
	// return randomNum;
	// }
	//
	// private static void salt(Mat image, int n) {
	// if (image.channels() == 1) {
	// System.out.println("Image has a single (greyscale) channel");
	// }
	// if (image.channels() == 3) {
	// System.out.println("Image has three channels");
	// for (int i = 0; i < n; i++) {
	// // create a salt or pepper value randomly
	// double val = randInt(0, 250);
	// // the following double array holds the the three new values for
	// // each channel of the pixel we're modifying
	// double[] speckColor = new double[] { val, val, val };
	// // System.out.println("changing to :" + speckColor[0]+ " " +
	// // speckColor[1] + " " + speckColor[2]);
	//
	// double x = randInt(1, image.height());
	// double y = randInt(1, image.width());
	// // System.out.println("Placing dot at position (" + (int)x +
	// // ", "+ (int)y+ ")");
	// // in C++ this would be something like following to modify one
	// // channel of the pixel
	// // image.at<cv::Vec3b>(rowVal,colVal)[0] = 220;
	// image.put((int) x, (int) y, speckColor); // modify the pixel
	// // value at the
	// // point (i,i)
	// }// end for
	// }// end if
	// }// end salt

	public static void main(String[] args) throws Exception {
		OpenCV.loadShared();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//
//		 Mat image =
//		 Highgui.imread(Main.class.getResource("/output.png").getPath());
		// long start =System.currentTimeMillis();
		//
//		 Imgproc.medianBlur(image, image, 9);
		// System.out.println(System.currentTimeMillis()-start+"ms");
		//
		//
		//
		// // Save the visualized detection.
//		 String filename = "faceDetection.png";
		// System.out.println(String.format("Writing %s", filename));
//		 Highgui.imwrite(filename, image);

		/*
		 * Logic: Captures the colour of 8 pixels around the target
		 * pixel.Including the target pixel there will be 9 pixels. Isolate the
		 * R,G,B values of each pixels and put them in an array.Sort the
		 * arrays.Get the Middle value of the array Which will be the Median of
		 * the color values in those 9 pixels.Set the color to the Target pixel
		 * and move on!
		 */
		// Mat input =
		// Highgui.imread(Main.class.getResource("/babuino.jpg").getPath());
		// salt(input, 1000000);
		// Highgui.imwrite("output.png", input);

		File f = new File(Main.class.getResource("/im_sp1.png").getPath());

		BufferedImage img = ImageIO.read(f);
		int[][] arr = new int[img.getWidth()][img.getHeight()];
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				arr[i][j] = img.getRGB(i, j);

			}
//		List<int[][]> array = new ArrayList<int[][]>();
//		 array.add(arr);
		long start = System.currentTimeMillis();
//		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "8");
//	Matrix m = Arrays.stream(new SplitMatrix2(8).split(new Matrix(arr))).parallel().map(n-> new ExecuteFilter2().execute(n)).sequential().reduce(new Matrix(8), (a, b)->a.add(b));
//		
		// .collect(Collectors.toList()).size());
//		System.out.println(System.currentTimeMillis() - start + "ms");
//
		MapReduce mapReduce = new MapReduce(1);
		start = System.currentTimeMillis();
		Future<Matrix> result = mapReduce.getStream().input(new Matrix(arr));
		Matrix m = result.get();
		System.out.println(System.currentTimeMillis() - start + "ms");

//		start = System.currentTimeMillis();
//		m = Sequential.compute(new Matrix(arr));
//		System.out.println(System.currentTimeMillis() - start + "ms");

		File output = new File("pippo.png");
		ImageIO.write(m.toImage(), "jpg", output);	
		
		System.exit(0);

	}
}
