import processing.core.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class DrawPoints extends PApplet {

	String s;

	public void settings() {
    	size(500, 500);
		s = Paths.get("").toAbsolutePath().getFileName().toString();
	}
  
	public void setup() {
    	background(128);
    	noLoop();
  	}

  	public void draw() {
		// You should create a stream of Points using either a list or stream builder.
		// TODO: Initialize either a stream builder or list here
		Stream.Builder<Point> builder;
		builder = Stream.builder();


		try (Scanner scanner = new Scanner(new File("positions.txt"))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();


				// Each line contains comma and space separated x, y, and z values
				// You will add a Point to the stream builder/list for each line
				// TODO: Process the line as a string here
				String[] lineArray = line.split(",");
				Point currentPoint = new Point(Double.parseDouble(lineArray[0]), Double.parseDouble(lineArray[1]),Double.parseDouble(lineArray[2]));
				builder.add(currentPoint);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Initialize the stream
		// TODO: Create the stream here using the builder/list
		Stream<Point> pointStream = builder.build();

		// Process the stream
		List<Point> pointList = pointStream
		// TODO: Add additional operations here to transform the points
				.filter(p -> p.z <= 2.0).map(p -> new Point(p.x * 0.5, p.y * 0.5, p.z * 0.5)).map(p -> new Point(p.x -150.0, p.y -562.0, p.z)).map(p -> new Point(p.x, p.y * -1, p.z)).toList();

		// Display the stream
		for (Point p : pointList){
			ellipse((int) p.x, (int) p.y, 1, 1);
			fill(126, 126, 126);
			text(s, 0, 500);
		}
  	}

  	public static void main(String[] args) {
      PApplet.main("DrawPoints");
   }
}
