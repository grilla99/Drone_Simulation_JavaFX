
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DroneInterface extends Application {
	private MyCanvas mc;
	private AnimationTimer timer; // timer used for animation
	private VBox rtPane; // vertical box for putting info
	private DroneArena arena;

	/**
	 * function to show in a box ABout the programme
	 */
	private void showAbout() {
		Alert alert = new Alert(AlertType.INFORMATION); // define what box is
		alert.setTitle("About"); // say is About
		alert.setHeaderText(null);
		alert.setContentText("This drone sim has been created for part two CS students.\n "
				+ "To add another drone to the arena, click the add drone button for the relevant drone."
				+ "To pause the simulation click pause. To load from an existing configuration click load."
				+ "To save the existing configuration click save.  "); // give text
		alert.showAndWait(); // show box and wait for user to close
	}

	/**
	 * set up the mouse event - when mouse pressed, put ball there
	 * 
	 * @param canvas
	 */
	void setMouseEvents(Canvas canvas) {
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, // for MOUSE PRESSED event
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
//	    	        	   	arena.setPaddle(e.getX(), e.getY());
						drawWorld(); // redraw world
						drawStatus();
					}
				});
	}

	/**
	 * set up the menu of commands for the GUI
	 * 
	 * @return the menu bar
	 */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar(); // create main menu

		Menu mFile = new Menu("File"); // add File main menu
		MenuItem mExit = new MenuItem("Exit"); // whose sub menu has Exit
		mExit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) { // action on exit is
				timer.stop(); // stop timer
				System.exit(0); // exit program
			}
		});
		mFile.getItems().addAll(mExit); // add exit to File menu

		Menu mHelp = new Menu("Help"); // create Help menu
		MenuItem mAbout = new MenuItem("About"); // add About sub men item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				showAbout(); // and its action to print about
			}
		});
		mHelp.getItems().addAll(mAbout); // add About to Help main item

		menuBar.getMenus().addAll(mFile, mHelp); // set main menu with File, Help
		return menuBar; // return the menu
	}

	/**
	 * set up the horizontal box for the bottom with relevant buttons
	 * 
	 * @return
	 */
	private HBox setButtons() {
		Button btnStart = new Button("Start"); // create button for starting
		btnStart.setOnAction(new EventHandler<ActionEvent>() { // now define event when it is pressed
			@Override
			public void handle(ActionEvent event) {
				timer.start(); // its action is to start the timer
			}
		});

		Button btnStop = new Button("Pause"); // now button for stop
		btnStop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timer.stop(); // and its action to stop the timer
			}
		});

		Button btnAddCiv = new Button("Another civilian"); // now button for stop
		btnAddCiv.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				arena.addCivilianDrone(); // and its action to stop the timer
				drawWorld();
			}
		});

		Button btnAddMed = new Button("Another medic"); // now button for stop
		btnAddMed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				arena.addMedicalDrone(); // and its action to stop the timer
				drawWorld();
			}
		});

		Button btnAddAttack = new Button("Another attack drone"); // now button for stop
		btnAddAttack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				arena.addAttackDrone(); // and its action to stop the timer
				drawWorld();
			}
		});

		Button btnOpenConfig = new Button("Select arena config");
		btnOpenConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// create a FileFilter and override its accept method
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");

				JFileChooser fileChooser = new JFileChooser();

				// applies filter to file chooser
				fileChooser.addChoosableFileFilter(filter);

				// creates a variable such that the file chooser opens in the current directory
				File workingDirectory = new File(System.getProperty("user.dir"));

				fileChooser.setCurrentDirectory(workingDirectory);

				// opens fileChooser pane
				int returnVal = fileChooser.showOpenDialog(null);

				// if file opened is a valid file
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selFile = fileChooser.getSelectedFile();

					if (selFile.isFile()) {
						// creates variables necessary to store
						try {
							Scanner scanner = new Scanner(selFile);
							// get the x and y config from file
							int x = scanner.nextInt();
							int y = scanner.nextInt();

							arena = new DroneArena(x, y); // set up arena again
							drawWorld();
							drawStatus();

						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Please chooose a valid file");
					}
				}
			}
		});

		Button btnSaveConfig = new Button("Save arena config");
		btnSaveConfig.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextInputDialog dialog = new TextInputDialog("example.txt");

				// creates a dialog for user to save configuration
				dialog.setTitle("Save configuration");
				dialog.setHeaderText("Enter file name");

				// saves user input to result variable
				Optional<String> result = dialog.showAndWait();

				// checking if a (valid) value has been entered
				if (result.isPresent()) {
					// gets current arena details
					double x = arena.getXSize();
					double y = arena.getYSize();
					// extracts String from the Optional<String> datatype
					String fileName = result.get();

					// creates a file with the name provided by the user
					try {
						FileWriter fstream = new FileWriter(fileName);
						// create buffered writer to store text
						BufferedWriter out = new BufferedWriter(fstream);

						// cast the x and y dimensions to integers
						out.write((int) x);
						out.write((int) y);

						// close the input stream
						out.close();

					} catch (IOException e) {
						// error handling
						e.printStackTrace();
					}
				}
			}
		});

		// now add these buttons + labels to a HBox
		return new HBox(new Label("Run: "), btnStart, btnStop, new Label("Add: "), btnAddCiv, new Label("Add: "),
				btnAddMed, new Label("Add: "), btnAddAttack, btnOpenConfig, btnSaveConfig);
	}

	/**
	 * Check that a file is of a txt format for use in the arena configuration
	 * function
	 */

	/**
	 * Show the score .. by writing it at position x,y
	 * 
	 * @param x
	 * @param y
	 * @param score
	 */
	public void showScore(double x, double y, int score) {
		mc.showText(x, y, Integer.toString(score));
	}

	/**
	 * draw the world with ball in it
	 */
	public void drawWorld() {
		mc.clearCanvas(); // set beige colour
		arena.drawArena(mc);
	}

	/**
	 * show where ball is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear(); // clear rtpane
		ArrayList<String> allDs = arena.describeAll();
		for (String s : allDs) {
			Label l = new Label(s); // turn description into a label
			rtPane.getChildren().add(l); // add label
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Aaron's Drone Simulation");
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10, 20, 10, 20));

		bp.setTop(setMenu()); // put menu at the top

		Group root = new Group(); // create group with canvas
		Canvas canvas = new Canvas(400, 500);
		root.getChildren().add(canvas);
		bp.setLeft(root); // load canvas to left area

		mc = new MyCanvas(canvas.getGraphicsContext2D(), 400, 500);

		setMouseEvents(canvas); // set up mouse events

		arena = new DroneArena(400, 500); // set up arena
		drawWorld();

		timer = new AnimationTimer() { // set up timer
			public void handle(long currentNanoTime) { // and its action when on
				arena.checkDrones(); // check the angle of all drones
				arena.adjustDrones(); // move all drones
				drawWorld(); // redraw the world
				drawStatus(); // indicate where drones are
			}
		};

		rtPane = new VBox(); // set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT); // set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5)); // padding
		bp.setRight(rtPane); // add rtPane to borderpane right

		bp.setBottom(setButtons()); // set bottom pane with buttons

		Scene scene = new Scene(bp, 700, 600); // set overall scene
		bp.prefHeightProperty().bind(scene.heightProperty());
		bp.prefWidthProperty().bind(scene.widthProperty());

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args); // launch the GUI

	}

}
