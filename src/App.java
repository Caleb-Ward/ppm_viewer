import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.FontMetrics;
import java.awt.Font;

import java.awt.event.KeyEvent;

public class App extends GameEngine {
	private Pixel[] pixels;
	private Integer[] window;
	private String format;
	private int fileIndex;
	private File[] files;
	private File dir;
	//Font
	private FontMetrics metrics;
	private Font font;

	public static void main (String args[])  {
		createGame (new App(),1);
	}

	public void init(){
		fileIndex = 0;
		window = new Integer[2];
		dir = new File("src/ppm_files");
		files = dir.listFiles((dirTmp, name) -> name.endsWith(".ppm"));
		if (files.length > 0) {
			displayImage();
		}
	}

	public void displayImage(){
		readFile(files[fileIndex].getPath());
		setWindowSize(window[0],window[1] + 50);
	}

	public void readFile(String filePath) {
		try {
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			int index = 0;
			while (scanner.hasNextLine()) {
				String lineData[] = scanner.nextLine().split("\\s");
				if (lineData[0].contains("P")) {
					format = lineData[0];
					continue;
				}
				//ignore colour value while only working 256 colours # is comment
				if (lineData.length < 2 || lineData[0].contains("#")) {
					continue;
				}
				//only window dimensions line should have 2 integers
				if (lineData.length == 2) {
					window[0] = Integer.parseInt(lineData[0]);
					window[1] = Integer.parseInt(lineData[1]);
					pixels = new Pixel[window[0]*window[1]];
					continue;
				}
				//put values into temporary array then store in pixel once full
				Integer[] rgb = new Integer[3];
				int i = 0;
				for (String value : lineData) {
					if (value.contains("#")){
						break;
					} else {
						if (!value.isEmpty()) {
							rgb[i] = Integer.parseInt(value);
							i++;
						}       
						if (i > 2) {
							i = 0;
							pixels[index] = new Pixel(rgb[0], rgb[1], rgb[2]);
							index++;
						}
					}
				}		
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void update(double dt){

	}

	public void keyPressed(KeyEvent event) {
	int key = event.getKeyCode();
		if (key == KeyEvent.VK_LEFT && fileIndex > 0) {
			fileIndex--;
			displayImage();
		}
		if (key == KeyEvent.VK_RIGHT && fileIndex < files.length - 1) {
			fileIndex++;
			displayImage();
		}
		if (key == KeyEvent.VK_O) {
			findFile();
		}
	}

	public void findFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(dir);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM files", "ppm");
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File newFile = fileChooser.getSelectedFile();
			System.out.println("Opening file " + newFile.getPath());
			if (!searchFilesArray(newFile.getName())) {
				JOptionPane.showMessageDialog(null, "Error opening file");
			} else {
				displayImage();
			}
		} else {
			System.out.println("User cancelled");
		}
	}
	public boolean searchFilesArray(String search) {
		int searchIndex = 0;
		for (File file : files) {
			if (file.getName().equals(search)) {
				fileIndex = searchIndex;
				return true;
			}
			searchIndex++;
		}
		return false;
	}
	private void centerText(int y, String str, int size){
		font = new Font("Verdana", Font.PLAIN, size);
		metrics = mGraphics.getFontMetrics(font);
		//centerTextY = ((screenHeight - metrics.getHeight()) / 2) + metrics.getAscent();
		int centerX = (width() - metrics.stringWidth(str)) / 2;
		drawText(centerX,y,str,"Verdana",size);
	}

	public void paintComponent(){
		clearBackground(width(), height());
		int x = 0;
		int y = 0;
		for(Pixel p : pixels) {
			changeColor(new Color(p.getR(), p.getG(), p.getB()));
			drawSolidRectangle(x,y,1,1);
			x++;
			if (x >= window[0]) {
				x = 0;
				y++;
			}
		}
		changeColor(Color.BLACK);
		centerText(height()-15,files[fileIndex].getName() + " (" + format + ")  " + 
			"  X: " + window[0] + "px    Y: " + window[1] + "px",20);
	}
}
