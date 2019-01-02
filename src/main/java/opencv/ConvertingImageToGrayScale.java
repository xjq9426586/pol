package opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ConvertingImageToGrayScale {
   public static void main(String args[]) throws Exception {

      //Loading the OpenCV core library
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
      String input = "images/2.png";

      //Reading the image
      Mat src = Imgcodecs.imread(input);

      //Creating the empty destination matrix
      Mat dst = new Mat();

      //Converting the image to gray scale and saving it in the dst matrix
      Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);

      //Writing the image
      Imgcodecs.imwrite("images/zhonglouToGrayScale.jpg", dst);
      System.out.println("Converted to Grayscale");
   }
}
