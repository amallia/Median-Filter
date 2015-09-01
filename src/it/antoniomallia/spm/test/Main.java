package it.antoniomallia.spm.test;

import it.antoniomallia.spm.J8Farm;
import it.antoniomallia.spm.Matrix;
import it.antoniomallia.spm.SkandiumFarm;
import it.antoniomallia.spm.SkandiumMap;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


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
		// int[][] a = new int[4][4];
		// for (int i = 0; i < 4; i++)
		// for (int j = 0; j < 4; j++) {
		// a[i][j] = Integer.valueOf(String.valueOf(i) + String.valueOf(j));
		// }
		// Matrix[] mats = new SplitMatrix(2).split(new Matrix(a));
		// for (Matrix m : mats) {
		// for (int i = 0; i < m.matrix.length; i++) {
		// for (int j = 0; j < m.matrix[0].length; j++) {
		// System.out.print(m.matrix[i][j] + ",");
		// }
		// System.out.println();
		// }
		// System.out.println("-------------------------------");
		//
		// }
		// System.exit(0);

//		 OpenCV.loadShared();
//		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		// //
//		 Mat image = Highgui.imread(Main.class.getResource("/balloons_noisy.png")
//		 .getPath());
//		  long start =System.currentTimeMillis();
//		// //
//		 Imgproc.medianBlur(image, image, 3);
//		  System.out.println(System.currentTimeMillis()-start+"ms");
		// //
		// //
		// //
		// // // Save the visualized detection.
		// String filename = "faceDetection.png";
		// // // System.out.println(String.format("Writing %s", filename));
		// Highgui.imwrite(filename, image);

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

		File f = new File(Main.class.getResource("/balloons.png").getPath());

		BufferedImage img = ImageIO.read(f);
		int[][] arr = new int[img.getHeight()][img.getWidth()];
		for (int i = 0; i < img.getHeight(); i++)
			for (int j = 0; j < img.getWidth(); j++) {
				arr[i][j] = img.getRGB(j, i);

			}
		

		 Matrix[] arrM = new Matrix[4];
		 for (int i = 0; i < arrM.length; i++) {
			arrM[i]= new Matrix(arr);
		}
		 
		 for (int i = 1; i < arrM.length+1; i++) {
				File output = new File(i+1000+".png");
				ImageIO.write(arrM[i-1].toImage(), "jpg", output);
			
			}
		// for (int i = 0; i < arrM.length; i++) {
		// arrM[i] = new Matrix(4000, 4000);
		// }
		// Matrix m = new Matrix(4, 4);
		// Matrix[] ma= new SplitMatrix(2).split(m);
		//
		// System.out.println(ma.length);
		// System.out.println(ma[0].getHeight()+" "+ma[0].getWidth());
		//
		// ma[0].medianFilter();
		// List<int[][]> array = new ArrayList<int[][]>();
		// array.add(arr);
		// long start = System.currentTimeMillis();
		// System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
		// "8");
		// Matrix m = Arrays.stream(new SplitMatrix2(8).split(new
		// Matrix(arr))).parallel().map(n-> new
		// ExecuteFilter2().execute(n)).sequential().reduce(new Matrix(8), (a,
		// b)->a.add(b));
		//
		// .collect(Collectors.toList()).size());
		// System.out.println(System.currentTimeMillis() - start + "ms");
		//

		// J8Farm farm = new J8Farm(4);
		// arrM = farm.compute(arrM);
		// long start = System.currentTimeMillis();
		// arrM = farm.compute(arrM);
		// System.out.println(System.currentTimeMillis() - start + "ms");
		// System.out.println(Thread.activeCount());

		// start = System.currentTimeMillis();
		// m = Sequential.compute(new Matrix(arr));
		// System.out.println(System.currentTimeMillis() - start + "ms");
		// Matrix m = new Matrix(10, 10).randomValues();

		// SkandiumMap map = new SkandiumMap(3);
//		long start = System.currentTimeMillis();
//
//		Matrix m = Sequential.compute(new Matrix(arr));
//		System.out.println(System.currentTimeMillis() - start + "ms");

		 SkandiumFarm farm = new SkandiumFarm(4);
		
			Matrix[] arrMat=   farm.compute(arrM);
			for (int i = 1; i < arrMat.length+1; i++) {
				File output = new File(i+".png");
				ImageIO.write(arrM[i-1].toImage(), "jpg", output);
			
			}
//		 
			
			  arrM = new Matrix[4];
			 for (int i = 0; i < arrM.length; i++) {
				arrM[i]= new Matrix(arr);
			}
		J8Farm j8farm = new J8Farm(4);
		 
		 arrMat=  j8farm.compute(arrM);
		for (int i = 1; i < arrMat.length+1; i++) {
			File output = new File(i*100+".png");
			ImageIO.write(arrMat[i-1].toImage(), "jpg", output);
		
		}
		 arrM = new Matrix[4];
		 for (int i = 0; i < arrM.length; i++) {
			arrM[i]= new Matrix(arr);
		}
		 SkandiumMap map = new SkandiumMap(4);
			File output = new File("output.png");

			ImageIO.write(map.compute(arrM[0]).toImage(), "jpg", output);

		 
		 
		 
//		// Matrix m = map.compute(new Matrix(arr));
//		File output = new File("pipposss.png");
//		ImageIO.write(m.toImage(), "jpg", output);

		// SkandiumMapTest map = new SkandiumMapTest(4);
		// map.testcompute(1, 4000, 4000);
		// map.testcompute(1, 4000, 4000);
		// map.shutdown();

		//
		// J8MapTest mapj= new J8MapTest(4);
		// mapj.testcompute(1, 4000, 4000);
		// mapj.testcompute(1, 4000, 4000);
		System.exit(0);

	}
}
