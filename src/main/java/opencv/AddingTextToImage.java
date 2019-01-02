package opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class AddingTextToImage {
    public static void main(String args[]) {

        // Loading the OpenCV core library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Reading the Image from the file and storing it in to a Matrix object
        String file = "images/2.png";
        Mat matrix = Imgcodecs.imread(file);

        // Adding Text
        Imgproc.putText(matrix, // Matrix obj of the image
                "HTH", // Text to be added
                new Point(50, 200), // point
                Core.FONT_HERSHEY_SIMPLEX, // front face
                5.0, // front scale
                new Scalar(0, 0, 0), // Scalar object for color
                5); // Thickness

        Imgcodecs.imwrite("images/addingTextOP.jpg", matrix);
        System.out.println("Image Processed");
    }
}
