package opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


public class CopyAndPasteAnImage {
    public static void main(String args[]) {
        String propath = System.getProperty("user.dir");
        System.out.println(propath);
        // Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Reading the Image from the file and storing it in to a Matrix object
        String file = propath+"/images/2.png";

        Mat matrix = Imgcodecs.imread(file);
        System.out.println("Image Loaded ..........");
        String file2 = propath+"/images/mydogResaved.jpg";

        // Writing the image
        Imgcodecs.imwrite(file2, matrix);
        System.out.println("Image Saved ............");
    }
}
