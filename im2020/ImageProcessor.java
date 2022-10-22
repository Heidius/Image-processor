package oop.im2020;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 *
 * @author childm
 */
public class ImageProcessor extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JFileChooser chooser = new JFileChooser();
	private final ImagePanel imagePanel = new ImagePanel();

	private BufferedImage image;

	private File loadedImage;

	private final JMenu opMenu = new JMenu("Operations");
	
	private final GrayscaleUI grayscaleUI = new GrayscaleUI();
	private final TintUI tintUI = new TintUI();
	private final ChromaKeyUI chromaKeyUI = new ChromaKeyUI( chooser);

	public ImageProcessor() {
		this.chooser.setMultiSelectionEnabled(false);
		this.chooser.setCurrentDirectory(new File(".")); // set current directory

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		final JMenuBar menuBar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");

		final JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(ev -> doOpenImage());
		fileMenu.add(openItem);

		final JMenuItem revertItem = new JMenuItem("Revert");
		revertItem.addActionListener(ev -> revert());
		fileMenu.add(revertItem);

		menuBar.add(fileMenu);
		menuBar.add(this.opMenu);
		setJMenuBar(menuBar);

		add(this.imagePanel, BorderLayout.CENTER);
		pack();

		addMenuOperation( "Grayscale");
		addMenuOperation( "Tint");
		addMenuOperation( "Chromakey");

		this.setVisible( true);
	}

	private void addMenuOperation(final String identifier) {
		final JMenuItem item = new JMenuItem(identifier);
		item.addActionListener(ev -> doOperation(identifier));
		this.opMenu.add(item);
	}

	private File chooseImageFile() {
		if (this.chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			return this.chooser.getSelectedFile();
		} else {
			return null;
		}
	}

	private void doOpenImage() {
		final File file = chooseImageFile();
		if (file != null) {
			loadImage(file);
		}
	}

	private void loadImage(final File file) {
		try {
			this.image = ImageIO.read(file);
			this.loadedImage = file;
			setImage( this.image);
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}

	private void setImage(final BufferedImage image) {
		this.image = image;
		this.imagePanel.setImage(image);
		pack();
	}

	private void revert() {
		if (this.loadedImage != null) {
			loadImage(this.loadedImage);
		}
	}

	private void doOperation( final String identifier) {
		if ( identifier.equals( "Grayscale")) {
			setImage( doGrayscale( this.image));
		} else if ( identifier.equals( "Tint")) {
			setImage( doTint( this.image));
		} else if ( identifier.equals( "Chromakey")) {
			setImage( doChromaKey( this.image));
		}
	}

	private BufferedImage doGrayscale(final BufferedImage inputImage) {
		final OperationDialog dialog = new OperationDialog( this, grayscaleUI);
		dialog.setVisible( true);
		if (!dialog.wasCancelled()) {
			for (int x = 0; x < inputImage.getWidth(); x++) {
				for (int y = 0; y < inputImage.getHeight(); y++) {
					final int inputRGB = OperationUtilities.getRGB(x, y, inputImage);
					final int outputRGB = OperationUtilities.grayscale(inputRGB);
					OperationUtilities.setRGB(x, y, outputRGB, inputImage);
				}
			}
		}
		return inputImage;
	}


	private BufferedImage doTint( final BufferedImage inputImage) {
		final OperationDialog dialog = new OperationDialog( this, tintUI);
		dialog.setVisible( true);
		if (!dialog.wasCancelled()) {
			final ColourComponent band = tintUI.getChosenColor();
			final double alpha = tintUI.getAlpha() / 100.0;
			for (int x = 0; x < inputImage.getWidth(); x++) {
				for (int y = 0; y < inputImage.getHeight(); y++) {
					final int inputRGB = OperationUtilities.getRGB(x, y, inputImage);
					final int outputRGB = OperationUtilities.tint(inputRGB, alpha, band);
					OperationUtilities.setRGB(x, y, outputRGB, inputImage);
				}
			}
		}
		return inputImage;
	}

	private BufferedImage doChromaKey( final BufferedImage inputImage) {
		final OperationDialog dialog = new OperationDialog( this, chromaKeyUI);
		dialog.setVisible( true);    	
		if (!dialog.wasCancelled()) {
			try {
				double sensitivity = chromaKeyUI.getSensitivity();
				BufferedImage otherImage = ImageIO.read(chromaKeyUI.getOtherImagePath());

				int targetRGB = chromaKeyUI.getTargetColor().getRGB();

				BufferedImage output = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
				for (int x = 0; x < output.getWidth(); x++) {
					for (int y = 0; y < output.getHeight(); y++) {
						int inputRGB = OperationUtilities.getRGB(x, y, inputImage);
						int otherRGB = OperationUtilities.getRGB(x, y, otherImage);
						int outputRGB = OperationUtilities.chromaKey(inputRGB, otherRGB, targetRGB, sensitivity);
						OperationUtilities.setRGB(x, y, outputRGB, output);
					}
				}
				return output;
			} catch (IOException ex) {
				ex.printStackTrace();
				return inputImage;
			}
		} else {
			return inputImage;
		}
	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(() -> new ImageProcessor());
	}

}
