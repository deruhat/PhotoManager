// Fahad Syed 			 | netid: fbs14
// Abdulellah Abualshour | netid: aha59

package model;
import java.io.*;
import java.util.*;
import control.Control;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Class implementation of the main stage and login screen for the photo manager.
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 */
public class Photos extends Application implements Serializable{	

	private static final long serialVersionUID = 1L;
	private Control controller;
	private ArrayList<User> userBase = new ArrayList<User>();
	private User currentUser;
	
	/**
	 * no-arg default constructor
	 */
	public Photos() {
		
	}

	/**
	 * login functionality of the photo manager
	 * @return current user
	 */
	private User login(){
		
		// Create the custom dialog.
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Photos Login");
		dialog.setHeaderText("Please Enter Your Username:");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);
		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return username.getText();
		    }
		    return null;
		});
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent() && result.get() != null){
			
			for(User u: userBase){
				if(result.get().trim().equals(u.getUserName().trim()))
				{
					return u; 
				}
			}
			User user = new User(result.get().trim(), false, null);
			userBase.add(user);
			return user; 
		}
		return null;
		//TODO: save album to user.
		//TODO: load user after checking userBase to see if they exist. 
		//TODO: if they do not exist, create new user and start application. 
	}
	
	@Override
	public void start(Stage primaryStage) {
		readUserBase(); 
		initAdminAndStock(); 

		currentUser = login();
		if(currentUser == null){
			Platform.exit();
			return; 
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/ui_pm.fxml"));
		try {
			AnchorPane root = (AnchorPane)loader.load();
			controller = loader.getController();
			controller.setCurrentUser(currentUser);
			controller.setUserBase(userBase);
			controller.setMyPhotos(this);
			controller.start(primaryStage);
	        Scene scene = new Scene(root,998,658);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	/**
	 * Album saving functionality when the app is terminated
	 */
	public void stop(){
		if(controller != null){
			controller.saveAlbums();
		}
		writeUserBase(); 
	}
	
	/**
	 * writer for userBase serialization
	 */
	private void writeUserBase(){
	try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.dat"));
			oos.writeObject(userBase);
				
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}
	
	/**
	 * reader for userBase deserialization
	 */
	private void readUserBase(){
		try {	
			ObjectInputStream ois = new ObjectInputStream(
			new FileInputStream("data.dat"));
			userBase = (ArrayList<User>) ois.readObject();
		} 
		catch (ClassNotFoundException e){
			
	    } 
		catch (IOException e){
			
	    }
	}
	
	/**
	 * Initialization of the admin user and stock library
	 */
	private void initAdminAndStock(){
		User admin = new User("admin", true, null); 
		boolean addAdmin = true; 
		for(User u: userBase){
			if(u.getUserName().equals("admin") && u.isAdmin()){
				addAdmin = false; 
			}
		}
		if(addAdmin){
			userBase.add(admin);
		}
		
		boolean addStock = true; 
		for(User u: userBase){
			if(u.getUserName().equals("stock")){
				addStock = false; 
			}
		}
		if(addStock){
			//Photos
			try{
				Photo p1 = new Photo(new ArrayList<Pair<String, String>>(), "1", Calendar.getInstance(), new File(Photos.class.getResource("../stock/1.jpg").getFile()));
				Photo p2 = new Photo(new ArrayList<Pair<String, String>>(), "2", Calendar.getInstance(), new File(Photos.class.getResource("../stock/2.jpg").getFile()));
				Photo p3 = new Photo(new ArrayList<Pair<String, String>>(), "3", Calendar.getInstance(), new File(Photos.class.getResource("../stock/3.jpg").getFile()));
				Photo p4 = new Photo(new ArrayList<Pair<String, String>>(), "4", Calendar.getInstance(), new File(Photos.class.getResource("../stock/4.jpg").getFile()));
				Photo p5 = new Photo(new ArrayList<Pair<String, String>>(), "5", Calendar.getInstance(), new File(Photos.class.getResource("../stock/5.jpg").getFile()));
				
				SerializableAlbum albumAbdul = new SerializableAlbum(); 
				albumAbdul.setName("Abdul's Album");
				albumAbdul.setNumPhotos("3");
				albumAbdul.setMinDate(Calendar.getInstance());
				albumAbdul.setMaxDate(Calendar.getInstance());
				albumAbdul.setMinDateString(Calendar.getInstance().getTime().toString());
				albumAbdul.setMaxDateString(Calendar.getInstance().getTime().toString());
				albumAbdul.getPhotoList().add(p1);
				albumAbdul.getPhotoList().add(p2);
				albumAbdul.getPhotoList().add(p3);
				
				SerializableAlbum albumFahad = new SerializableAlbum();
				albumFahad.setName("Fahad's Album");
				albumFahad.setNumPhotos("2");
				albumFahad.getPhotoList().add(p4);
				albumFahad.getPhotoList().add(p5);
				albumFahad.setMinDate(Calendar.getInstance());
				albumFahad.setMaxDate(Calendar.getInstance());
				albumFahad.setMinDateString(Calendar.getInstance().getTime().toString());
				albumFahad.setMaxDateString(Calendar.getInstance().getTime().toString());
				
				//Album List
				ArrayList<SerializableAlbum> stockAlbums = new ArrayList<SerializableAlbum>();
				stockAlbums.add(albumAbdul);
				stockAlbums.add(albumFahad);
				
				//User
				User stock = new User("stock", false, stockAlbums);
				userBase.add(stock);
			}
			catch(NullPointerException e){
				
			}
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);	
	}
}
