// Fahad Syed 			 | netid: fbs14
// Abdulellah Abualshour | netid: aha59

package control;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Album;
import model.Photo;
import model.Photos;
import model.SerializableAlbum;
import model.User;

/**
 * Class implementation of the controller.
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 */
public class Control implements Serializable{
	static final long serialVersionUID = 2L; 
	
	//ALBUM
	@FXML Button b1; // add album
	@FXML Button b2; // open album
	@FXML Button b3; // rename album
	@FXML Button b4; // search album
	@FXML Button b15; //make from search
	@FXML Button b5; // delete
	@FXML Button b6; // logout
	
	//PHOTO
	@FXML Label l2; //Photo controls
	@FXML Button b7; // add photo
	@FXML Button b8; // modify caption
	@FXML Button b9; // move
	@FXML Button b10; // copy
	@FXML Button b11; //display
	@FXML Button b12; //delete photo
	@FXML Button b13; //add tag
	@FXML Button b14; //delete tag
	@FXML Label l3;   //Admin controls
	@FXML Button b16; //Add User
	@FXML Button b17; //delete user
	@FXML Button b18; //List Users
	
	@FXML TableColumn<Album, String> c1; //table columns
	@FXML TableColumn<Album, String> c2; 
	@FXML TableColumn<Album, String> c3;
	@FXML TableColumn<Album, String> c4; 
	
	//Display Image:
	@FXML Button previousButton;
	@FXML Button nextButton;
	@FXML ImageView displayImage; 
	
	//Pane
	@FXML GridPane imagePane;
	
	//TableView
	@FXML TableView<Album> albumView; 
	private ObservableList<Album> albumList;
	private int gridRow = 0;
	private int gridCol = 0; 
	
	//Stage
	private Stage myStage; 
	private Photos myPhotos; 

	private Album currentlySelectedAlbum = null; 
	private int currentlySelectedIndex = -1;
	private Photo currentlySelectedPhoto = null; 
	private int currentlySelectedPhotoIndex = -1; 
	private User currentUser; 
	private ArrayList<User> userBase; 
	private Album searchResult = null; 
	private DisplayControl displayController; 
	private final FileChooser fileChooser = new FileChooser();
	
	public void start(Stage mainStage) {
		this.myStage = mainStage; 
		albumList = FXCollections.observableArrayList();
		
		if(currentUser != null){
			albumList = loadUserAlbums(); 
		}
		else{
			Platform.exit();
			return; 
		}
		
		setAdminOptionVisibility(false);
		if(currentUser.isAdmin()){
			setAdminOptionVisibility(true); 
		}
		
		c1.setCellValueFactory(
                new PropertyValueFactory<Album, String>("name"));
		c2.setCellValueFactory(
                new PropertyValueFactory<Album, String>("numPhotos"));
        c3.setCellValueFactory(
                new PropertyValueFactory<Album, String>("minDateString"));
        c4.setCellValueFactory(
                new PropertyValueFactory<Album, String>("maxDateString"));
        
        albumView.setItems(albumList);
		setPhotoOptionVisibility(false);
		setAlbumOptionVisibility(false);
		
		if(!albumList.isEmpty()){
			albumView.getSelectionModel().select(0);
			currentlySelectedAlbum = albumList.get(0); 
		}
		if(currentlySelectedAlbum != null){
			currentlySelectedAlbum = albumList.get(0);
			currentlySelectedIndex = 0;
			setPhotoOptionVisibility(false);
			setAlbumOptionVisibility(true);
			b2.setDisable(false); 
		}
		albumView.getSelectionModel().selectedIndexProperty()
		   .addListener((obs, oldVal, newVal) -> setSelectedAlbum());	
	}
	
	/**
	 * add album functionality
	 */
	private void addAlbum(){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Add Album: ");
		dialog.setHeaderText("Enter Album Name:");
		dialog.setContentText("Album Name:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			if(result.get().isEmpty()){
				Alert alert = new Alert(AlertType.ERROR);
         		alert.setTitle("ERROR");
         		alert.setHeaderText("Invalid Name");
         		alert.setContentText("An album name cannot be empty.");
         		Optional<ButtonType> result2 = alert.showAndWait();
         		if (result2.get() == ButtonType.OK){
        		    return; 
        		} else {
        		    return; 
        		}
			}
			for(Album a: albumList){
				if(result.get().trim().equals(a.getName())){
					Alert alert = new Alert(AlertType.ERROR);
	         		alert.setTitle("ERROR");
	         		alert.setHeaderText("Invalid Name");
	         		alert.setContentText("An album with this name already exists.");
	         		Optional<ButtonType> result2 = alert.showAndWait();
	         		if (result2.get() == ButtonType.OK){
	        		    return; 
	        		} else {
	        		    return; 
	        		}
				}
			}
			Album album = new Album(result.get(), new ArrayList<Photo>());
			albumList.add(album);
			if(albumList.size() == 2){
				b10.setDisable(false);
				b9.setDisable(false);
			}
		}
	}
	
	/**
	 * add album functionality from search result
	 */
	private void addAlbumFromSearchResult(){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Add Album: ");
		dialog.setHeaderText("Enter Album Name:");
		dialog.setContentText("Album Name:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			if(result.get().isEmpty()){
				Alert alert = new Alert(AlertType.ERROR);
         		alert.setTitle("ERROR");
         		alert.setHeaderText("Invalid Name");
         		alert.setContentText("An album name cannot be empty.");
         		Optional<ButtonType> result2 = alert.showAndWait();
         		if (result2.get() == ButtonType.OK){
        		    return; 
        		} else {
        		    return; 
        		}
			}
			for(Album a: albumList){
				if(result.get().trim().equals(a.getName())){
					Alert alert = new Alert(AlertType.ERROR);
	         		alert.setTitle("ERROR");
	         		alert.setHeaderText("Invalid Name");
	         		alert.setContentText("An album with this name already exists.");
	         		Optional<ButtonType> result2 = alert.showAndWait();
	         		if (result2.get() == ButtonType.OK){
	        		    return; 
	        		} else {
	        		    return; 
	        		}
				}
			}
			searchResult.setName(result.get());
			albumList.add(searchResult);
			albumView.getSelectionModel().selectLast();
			if(albumList.size() == 2){
				b10.setDisable(false);
				b9.setDisable(false);
			}
			b15.setDisable(true);
			searchResult = null; 
			open(); 
		}
	}
	
	/**
	 * add photo functionality
	 * @param photo photo
	 * @param save save flag
	 * @param search search flag
	 * @return photo photo added
	 */
	private Photo addPhoto(Photo photo, boolean save, boolean search){
		
		if(search || (currentlySelectedAlbum != null && currentlySelectedAlbum.getList() != null)){
			ImageView temp = new ImageView(openFile(photo.getImage()));	
			if(temp.getImage() == null){
				return null; 
			}
		//	photo.setImageView(temp);
			temp.setPreserveRatio(true);
			temp.setFitHeight(143);
			temp.setFitWidth(101);
			temp.setTranslateX(4);
			temp.setTranslateY(-10);
			Label txt = new Label(photo.getCaption());
			//photo.setLabel(txt);
			txt.setTranslateY(57);
			imagePane.add(temp, gridCol, gridRow);
			imagePane.add(txt, gridCol, gridRow);
			if(save == true){ saveToList(photo); }
			temp.setOnMouseClicked(new EventHandler<MouseEvent>()
		    {
		        @Override
		        public void handle(MouseEvent arg0)
		        {
		        	for(int index = 0; index < imagePane.getChildren().size(); index++){
		        		imagePane.getChildren().get(index).setStyle(null); 
		        	}
		            temp.setStyle("-fx-effect: innershadow(gaussian, #039ed3, 10, 1.0, 0, 0);");
		            currentlySelectedPhoto = photo;
		            if(currentlySelectedAlbum != null){
			            for(int i = 0; i < currentlySelectedAlbum.getList().size(); i++)
			            {
			            	if(currentlySelectedAlbum.getList().get(i) == currentlySelectedPhoto){
			            		currentlySelectedPhotoIndex = i; 
			            		i = currentlySelectedAlbum.getList().size()+1; 
			            	}
			            }
			            setPhotoOperations(true); 
		            }
		        }
		    });
			gridCol++; 
			 if(gridCol > 3)
			 {
				 gridRow++;
				 gridCol = 0;
				 if(gridRow > 4){
					 RowConstraints row1 = new RowConstraints();
					 row1.setMinHeight(143);
					 row1.setPrefHeight(143);
					 imagePane.getRowConstraints().add(row1);
				 }
			 }
		}
		albumView.refresh();
		return photo; 
	}
	
	/**
	 * add tag functionality
	 */
	private void addTag(){
		if(currentlySelectedPhoto == null || currentlySelectedAlbum == null || currentlySelectedIndex < 0){ return; }
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add Tag");
		dialog.setHeaderText("Use the fields below to enter a tag");

		ButtonType enterButtonType = new ButtonType("Enter", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(enterButtonType, ButtonType.CANCEL);

		// Create the key and value labels and text fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField key = new TextField();
		key.setPromptText("Key");
		TextField value = new TextField();
		value.setPromptText("Value");

		grid.add(new Label("Key:"), 0, 0);
		grid.add(key, 1, 0);
		grid.add(new Label("Value:"), 0, 1);
		grid.add(value, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(enterButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		key.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == enterButtonType) {
		        return new Pair<>(key.getText().trim(), value.getText().trim());
		    }
		    return null;
		});
		
		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(keyValue -> {
			
			if(result.get().getKey().isEmpty() || result.get().getValue().isEmpty())
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Invalid Tag");
				alert.setHeaderText(null);
				alert.setContentText("This tag contains an empty key or value.");
				alert.showAndWait();
				return; 
			}
			
			for(Pair<String, String> kv: currentlySelectedPhoto.getTags()){
				if(kv.getKey().trim().toLowerCase().equals(result.get().getKey()))
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Invalid Tag");
					alert.setHeaderText(null);
					alert.setContentText("This tag is already in the list.");
					alert.showAndWait();
					return; 
				}
			}
			if(currentlySelectedPhoto.getTags().size() == 0)
			{
				b14.setDisable(false);
			}
		    currentlySelectedPhoto.getTags().add(result.get());
		});
	}

	/**
	 * add user functionality
	 */
	private void addUser(){
		if(!currentUser.isAdmin()){
			return; 
		}
		TextInputDialog dialog = new TextInputDialog("Add User");
		dialog.setTitle("Add User");
		dialog.setHeaderText(null);
		dialog.setContentText("Enter the username:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    User newUser = new User(result.get(), false, null);
		    for(User a: userBase){
		    	if(newUser.getUserName().trim().equals(a.getUserName().trim())){
		    		Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Error");
		    		alert.setHeaderText(null);
		    		alert.setContentText("Another user already has this username.");
		    		alert.showAndWait();
		    		return; 
		    	}
		    }
		    userBase.add(newUser);
		}

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}
	
	/**
	 * file browser for image selection
	 * @return file file selected
	 */
	private File browseForImage(){
		File file = fileChooser.showOpenDialog(myStage);
		Image im;
        if (file != null){
            im = openFile(file);
            if(im == null){ 
           	 
	           	Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("ERROR");
        		alert.setHeaderText("Invalid Photo");
        		alert.setContentText("The file you have selected is not an image and cannot be added.");
        		Optional<ButtonType> result = alert.showAndWait();
        		if (result.get() == ButtonType.OK){
        			return null; 
        		}else {
       		    	return null; 
        		}
            }
            return file; 
       }
        return null; 
	}
	
	/**
	 * caption photo functionality
	 */
	private void captionPhoto(){
		if(currentlySelectedPhoto == null || currentlySelectedAlbum == null || currentlySelectedIndex < 0){ return; }
		
		TextInputDialog dialog;
		if(currentlySelectedPhoto.getCaption().isEmpty()){
			dialog = new TextInputDialog("Enter caption here");
		}
		else{
			dialog = new TextInputDialog(currentlySelectedPhoto.getCaption());
		}
		dialog.setTitle("Caption Photo");
		dialog.setHeaderText("Caption Required");
		dialog.setContentText("Please enter the caption:");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			currentlySelectedPhoto.setCaption(result.get().trim());
			int indexToSave = -1; 
			for(int index = 0; index < currentlySelectedAlbum.getList().size(); index++){
				if(currentlySelectedAlbum.getList().get(index) == currentlySelectedPhoto)
				{
					indexToSave = index; 
					index = currentlySelectedAlbum.getList().size();
				}
			}
			if(imagePane.getChildren().get(indexToSave*2+1) instanceof Label)
			{
				Label temp = (Label)imagePane.getChildren().get(indexToSave*2+1);
				temp.setText(currentlySelectedPhoto.getCaption());
			}
			
		}
	}
	
	/**
	 * click event handling functionality
	 * @param e action event to handle
	 */
	public void click(ActionEvent e) {
		if((e.getSource() instanceof Button)){
			Button b = (Button)e.getSource();
			if (b == b1) {
				addAlbum(); 
			 } 
			 else if (b == b2) {
				open(); 
			 }	
			 else if (b == b3) {
				rename();
			 }
			 else if (b == b4){
				search(); 
			 }
			 else if (b == b5){
				delete(); 
			 }
			 else if(b == b6){
				 logout(); 
			 }
			 else if (b == b7){
                 File im = browseForImage(); 
                 if(im == null){ return;  }
                 Calendar cal = Calendar.getInstance();
                 cal.set(Calendar.MILLISECOND, 0);
				 Photo temp = new Photo(new ArrayList<>(), "", cal, im);
				 addPhoto(temp, true, false); 
			 }
			else if(b == b8){
				 captionPhoto(); 
			}
			else if(b == b9){
				movePhoto(); 
			}
			else if(b == b10){
				copyPhoto(); 
			}
			else if(b == b11){
				displayPhoto();
			}
			else if(b == b12){
				deletePhoto(true);
			}
			else if(b == b13){
				addTag(); 
			}
			else if(b == b14){
				deleteTag(); 
			}
			else if(b == b15){
				addAlbumFromSearchResult(); 
			}
			else if(b == b16){
				addUser(); 
			}
			else if(b == b17){
				deleteUser();
			}
			else if(b == b18){
				showUserList();
			}
		}		 
	}
	
	/**
	 * copy photo functionality
	 */
	private void copyPhoto(){
		
		if(currentlySelectedPhoto == null || currentlySelectedAlbum == null || currentlySelectedIndex < 0){ return; }
		if(albumList.size() < 2){ return; }
		
		Photo move = currentlySelectedPhoto;

		ChoiceDialog<Album> dialog = new ChoiceDialog<Album>(albumList.get(0), albumList);
		dialog.setTitle("Copy Photo");
		dialog.setHeaderText("Select an Album to Copy this Photo to");
		dialog.setContentText("Album:");

		// Traditional way to get the response value.
		Optional<Album> result = dialog.showAndWait();
		if (result.isPresent()){
			if(result.get() == currentlySelectedAlbum){
				return; 
			}
			ArrayList<Photo> newList = result.get().getList();
			newList.add(move); 
		    result.get().setList(newList);
		    result.get().setNumPhotos(result.get().getList().size());
		    Calendar min = result.get().getMinDate();
			Calendar max = result.get().getMaxDate(); 
			if(min == null || max == null){
				result.get().setMinDate(move.getDate());
				result.get().setMaxDate(move.getDate());
				result.get().setMinDateString(result.get().getMinDate().getTime().toString());
				result.get().setMaxDateString(result.get().getMaxDate().getTime().toString());
			}
			else if(move.getDate().compareTo(min) < 0){
				result.get().setMinDate(move.getDate());
				result.get().setMinDateString(result.get().getMinDate().getTime().toString());
				result.get().setMaxDateString(result.get().getMaxDate().getTime().toString());
			}
			else if(move.getDate().compareTo(max) > 0){
				result.get().setMaxDate(move.getDate());
				result.get().setMinDateString(result.get().getMinDate().getTime().toString());
				result.get().setMaxDateString(result.get().getMaxDate().getTime().toString());
			} 	
		}
		albumView.refresh(); 
	}
	
	/**
	 * delete album functionality
	 */
	private void delete(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Album");
		alert.setHeaderText("Attention!");
		alert.setContentText("Are you sure you want to delete: "+currentlySelectedAlbum.getName()+"?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    albumList.remove(currentlySelectedIndex);
		    albumView.getSelectionModel().selectFirst();
		    setSelectedAlbum(); 
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
		if(albumList.isEmpty() == true){
			setAlbumOptionVisibility(false);
		}
		if(albumList.size() < 2){
			b9.setDisable(true);
			b10.setDisable(true);
		}
	}

	/**
	 * delete photo functionality
	 * @param setting visibility flag
	 */
	private void deletePhoto(boolean setting){
		
		if(currentlySelectedPhoto == null || currentlySelectedAlbum == null || currentlySelectedIndex < 0){ return; }
		if(setting == false){
			for(int i = 0; i < currentlySelectedAlbum.getList().size(); i++){
				if(currentlySelectedAlbum.getList().get(i).toString().equals(currentlySelectedPhoto.toString())){
					currentlySelectedAlbum.getList().remove(i);
				}
			}
			imagePane.getChildren().clear();
			currentlySelectedAlbum.setNumPhotos(currentlySelectedAlbum.getList().size()); 	
			findAndSetMinandMaxDateStrings(currentlySelectedAlbum);
			open();
			albumView.refresh(); 
			return; 
		}		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Photo");
		alert.setHeaderText("Attention!");
		alert.setContentText("Are you sure you want to delete this photo?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			for(int i = 0; i < currentlySelectedAlbum.getList().size(); i++){
				if(currentlySelectedAlbum.getList().get(i).toString().equals(currentlySelectedPhoto.toString())){
					currentlySelectedAlbum.getList().remove(i);
				}
			}
			imagePane.getChildren().clear();
			currentlySelectedAlbum.setNumPhotos(currentlySelectedAlbum.getList().size()); 		
			open();
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		albumView.refresh(); 
	}
	
	/**
	 * delete tag functionality
	 */
	private void deleteTag(){
		if(currentlySelectedPhoto == null || currentlySelectedAlbum == null || currentlySelectedIndex < 0){ return; }
		if(currentlySelectedPhoto.getTags().size() == 0){return;}
		ChoiceDialog<Pair<String, String>> dialog = new ChoiceDialog<Pair<String, String>>(currentlySelectedPhoto.getTags().get(0), currentlySelectedPhoto.getTags());
		dialog.setTitle("Delete Tag");
		dialog.setHeaderText("Select a tag to delete");
		dialog.setContentText("Tag:");

		// Traditional way to get the response value.
		Optional<Pair<String, String>> result = dialog.showAndWait();
		if (result.isPresent()){
			currentlySelectedPhoto.getTags().remove(result.get());
			if(currentlySelectedPhoto.getTags().size() == 0)
			{
				b14.setDisable(true);
			}
		}
	}
	
	/**
	 * delete user functionality
	 */
	private void deleteUser(){
		if(currentUser.isAdmin() == false){return;}
		ChoiceDialog<User> dialog = new ChoiceDialog<User>(userBase.get(0), userBase);
		dialog.setTitle("Delete User");
		dialog.setHeaderText("Select a user to delete");
		dialog.setContentText("User:");

		// Traditional way to get the response value.
		Optional<User> result = dialog.showAndWait();
		if (result.isPresent()){
			if(result.get().isAdmin()){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Cannot delete the admin.");
				alert.showAndWait();
				return; 
			}
			userBase.remove(result.get());
		}
	}
	
	/**
	 * display photo functionality
	 */
	private void displayPhoto(){
		if(currentlySelectedPhoto == null || currentlySelectedAlbum == null || currentlySelectedIndex < 0){ return; }
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/ui_displayimage.fxml"));
		try {
			AnchorPane root = (AnchorPane)loader.load();
			displayController = loader.getController(); 
			Stage stage = new Stage();
			stage.initOwner(myStage); 
			displayController.setController(this);
			displayController.start(stage);
	        Scene scene = new Scene(root,700,650);
			stage.setScene(scene);
			stage.show();
	        
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * finds and setes earliest and most recent dates of modification
	 * @param a album
	 */
	private void findAndSetMinandMaxDateStrings(Album a){
		
		a.setMinDateString("");
		a.setMaxDateString("");
		a.setMinDate(null);
		a.setMaxDate(null);
		
		for(Photo p: a.getList()){
			if(a.getMinDate() == null || a.getMaxDate() == null){
				a.setMinDate(p.getDate());
				a.setMaxDate(p.getDate());
				a.setMinDateString(a.getMinDate().getTime().toString());
				a.setMaxDateString(a.getMaxDate().getTime().toString());
			}
			else if(p.getDate().compareTo(a.getMinDate()) < 0){
				a.setMinDate(p.getDate());
				a.setMinDateString(a.getMinDate().getTime().toString());
				a.setMaxDateString(a.getMaxDate().getTime().toString());
			}
			else if(p.getDate().compareTo(a.getMaxDate()) > 0){
				a.setMaxDate(p.getDate());
				a.setMinDateString(a.getMinDate().getTime().toString());
				a.setMaxDateString(a.getMaxDate().getTime().toString());
			}
		}
	}
	
	/**
	 * getter for currently selected photo
	 * @return selected photo
	 */
	public Photo getCurrentlySelectedPhoto(){
		return currentlySelectedPhoto; 
	}
	
	/**
	 * getter for currently selected album
	 * @return selected album
	 */
	public Album getCurrentlySelectedAlbum(){
		return currentlySelectedAlbum; 
	}
	
	/**
	 * getter for currently selected album index
	 * @return selected album index
	 */
	public int getCurrentlySelectedIndex(){
		return currentlySelectedIndex;
	}
	
	/**
	 * getter for currently selected photo index
	 * @return selected photo index
	 */
	public int getCurrentlySelectedPhotoIndex(){
		return currentlySelectedPhotoIndex;
	}
	
	/**
	 * getter for display controller
	 * @return display controller
	 */
	public DisplayControl getDisplayController(){
		return displayController; 
	}
		
	/**
	 * getter for current user
	 * @return current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Photos instance getter
	 * @return photos instance
	 */
	public Photos getMyPhotos() {
		return myPhotos;
	}
	
	/**
	 * getter for userBase array
	 * @return userBase array
	 */
	public ArrayList<User> getUserBase() {
		return userBase;
	}
	
	/**
	 * loader for albums corresponding to user selected
	 * @return observable list of albums
	 */
	private ObservableList<Album> loadUserAlbums(){
		List<SerializableAlbum> sL = currentUser.getAlbums();
		ArrayList<Album> albumArray = new ArrayList<Album>();
		
		if(sL == null || sL.isEmpty()){
			return FXCollections.observableList(albumArray);
		}
		for(SerializableAlbum s: sL)
		{
			albumArray.add(new Album(s));
		}
		return FXCollections.observableList(albumArray);
	}
	
	/**
	 * logout functionality
	 */
	private void logout(){
		
		myPhotos.stop(); 
		myStage.close();
		Platform.runLater( () -> new Photos().start(new Stage()));
	}

	/**
	 * open album functionality
	 */
	private void open(){
		setPhotoOperations(false);
		setPhotoOptionVisibility(true);
		currentlySelectedPhoto = null; 
		gridRow = 0; 
		gridCol = 0; 
		for(int i = 0; i < currentlySelectedAlbum.getList().size(); i++){
			addPhoto(currentlySelectedAlbum.getList().get(i), false, false);
		}
		b2.setDisable(true);
	}
	
	/**
	 * file opener for getting image
	 * @param file file to open
	 * @return image
	 */
	private Image openFile(File file) {
    	Image image = new Image(file.toURI().toString()); 
    	if(image.isError()){
    		image = new Image("file:"+file.getAbsolutePath().replace('\\', '/'));
    		if(image.isError()){
    			return null;
    		}
    	}
    	return image; 
    }
	
	/**
	 * move photo functionality
	 */
	private void movePhoto(){
		
		if(currentlySelectedPhoto == null || currentlySelectedAlbum == null || currentlySelectedIndex < 0){ return; }
		if(albumList.size() < 2){ return; }
		
		Photo move = currentlySelectedPhoto;

		ChoiceDialog<Album> dialog = new ChoiceDialog<Album>(albumList.get(0), albumList);
		dialog.setTitle("Move Photo");
		dialog.setHeaderText("Select an Album to Move this Photo to");
		dialog.setContentText("Album:");

		// Traditional way to get the response value.
		Optional<Album> result = dialog.showAndWait();
		if (result.isPresent()){
			
			if(result.get() == currentlySelectedAlbum){
				return; 
			}
			ArrayList<Photo> newList = result.get().getList();
			newList.add(move); 
		    result.get().setList(newList);
		    result.get().setNumPhotos(result.get().getList().size());
		    Calendar min = result.get().getMinDate();
			Calendar max = result.get().getMaxDate(); 
			if(min == null || max == null){
				result.get().setMinDate(move.getDate());
				result.get().setMaxDate(move.getDate());
				result.get().setMinDateString(result.get().getMinDate().getTime().toString());
				result.get().setMaxDateString(result.get().getMaxDate().getTime().toString());
			}
			else if(move.getDate().compareTo(min) < 0){
				result.get().setMinDate(move.getDate());
				result.get().setMinDateString(result.get().getMinDate().getTime().toString());
				result.get().setMaxDateString(result.get().getMaxDate().getTime().toString());
			}
			else if(move.getDate().compareTo(max) > 0){
				result.get().setMaxDate(move.getDate());
				result.get().setMinDateString(result.get().getMinDate().getTime().toString());
				result.get().setMaxDateString(result.get().getMaxDate().getTime().toString());
			} 
		    deletePhoto(false);
		    if(currentlySelectedAlbum.getList().isEmpty()){
				currentlySelectedAlbum.setMinDate(null);
				currentlySelectedAlbum.setMaxDate(null);
				currentlySelectedAlbum.setMinDateString("");
				currentlySelectedAlbum.setMaxDateString("");
				albumView.refresh();
			}
		}
	}
	
	/**
	 * rename album functionality
	 */
	private void rename(){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Rename Album: ");
		dialog.setHeaderText("Enter Album Name:");
		dialog.setContentText("Album Name:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			if(result.get().isEmpty()){
				Alert alert = new Alert(AlertType.ERROR);
         		alert.setTitle("ERROR");
         		alert.setHeaderText("Invalid Name");
         		alert.setContentText("An album name cannot be empty.");
         		Optional<ButtonType> result2 = alert.showAndWait();
         		if (result2.get() == ButtonType.OK){
        		    return; 
        		} else {
        		    return; 
        		}
			}
			for(Album a: albumList){
				if(result.get().trim().equals(a.getName())){
					Alert alert = new Alert(AlertType.ERROR);
	         		alert.setTitle("ERROR");
	         		alert.setHeaderText("Invalid Name");
	         		alert.setContentText("An album with this name already exists.");
	         		Optional<ButtonType> result2 = alert.showAndWait();
	         		if (result2.get() == ButtonType.OK){
	        		    return; 
	        		} else {
	        		    return; 
	        		}
				}
			}
			currentlySelectedAlbum.setName(result.get());
			albumView.refresh(); 
		}
	}
	
	/**
	 * save photo to a list
	 * @param photo photo
	 */
	private void saveToList(Photo photo){
		if(currentlySelectedAlbum != null){
			ArrayList<Photo> photoListToAdd = currentlySelectedAlbum.getList(); 
			photoListToAdd.add(photo);
			currentlySelectedAlbum.setList(photoListToAdd); 
			Calendar min = currentlySelectedAlbum.getMinDate();
			Calendar max = currentlySelectedAlbum.getMaxDate(); 
			
			if(min == null || max == null){
				currentlySelectedAlbum.setMinDate(photo.getDate());
				currentlySelectedAlbum.setMaxDate(photo.getDate());
				currentlySelectedAlbum.setMinDateString(currentlySelectedAlbum.getMinDate().getTime().toString());
				currentlySelectedAlbum.setMaxDateString(currentlySelectedAlbum.getMaxDate().getTime().toString());
			}
			else if(photo.getDate().compareTo(min) < 0){
				currentlySelectedAlbum.setMinDate(photo.getDate());
				currentlySelectedAlbum.setMinDateString(currentlySelectedAlbum.getMinDate().getTime().toString());
				currentlySelectedAlbum.setMaxDateString(currentlySelectedAlbum.getMaxDate().getTime().toString());
			}
			else if(photo.getDate().compareTo(max) > 0){
				currentlySelectedAlbum.setMaxDate(photo.getDate());
				currentlySelectedAlbum.setMinDateString(currentlySelectedAlbum.getMinDate().getTime().toString());
				currentlySelectedAlbum.setMaxDateString(currentlySelectedAlbum.getMaxDate().getTime().toString());
			}
		}
	}
	
	/**
	 * save serialized album
	 */
	public void saveAlbums(){
		ArrayList<SerializableAlbum> sL = new ArrayList<SerializableAlbum>();
		for(Album a: albumList){
			sL.add(new SerializableAlbum(a));
		}
		currentUser.setAlbums(sL);
	}
	
	/**
	 * search functionality
	 */
	private void search(){
		
		List<String> choices = new ArrayList<>();
		choices.add("Search by Date");
		choices.add("Search by Tags");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Search by Date", choices);
		dialog.setTitle("Search for Photos");
		dialog.setHeaderText(null);
		dialog.setContentText("Select your search method");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		   if(result.get().equals("Search by Date")){
			   searchByDate();
		   }
		   else{
			   searchByTag();
		   }
		}
		return; 
	}
	
	/**
	 * search by date functionality
	 */
	private void searchByDate(){
		
		// Create the custom dialog.
		Dialog<Pair<Calendar, Calendar>> dialog = new Dialog<>();
		dialog.setTitle("Search for Photos by Date");
		dialog.setHeaderText("Enter start date and end date");
		
		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Search", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		DatePicker start = new DatePicker();
		DatePicker end = new DatePicker();

		grid.add(start, 0, 1);
		grid.add(new Label("Start:"), 0, 0);
		grid.add(new Label("End:"), 1, 0);
		grid.add(end, 1, 1);

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> start.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		    	
		    	Calendar startCal = Calendar.getInstance();
		    	startCal.clear(); 
		    	startCal.set(start.getValue().getYear(), start.getValue().getMonthValue()-1, start.getValue().getDayOfMonth(), 0, 0, 0);
		    	startCal.set(Calendar.MILLISECOND,0);
		    	
		    	Calendar endCal = Calendar.getInstance();
		    	endCal.clear();
		    	endCal.set(end.getValue().getYear(), end.getValue().getMonthValue()-1, end.getValue().getDayOfMonth(), 11, 59, 59);
		        endCal.set(Calendar.MILLISECOND,0);
		        return new Pair<>(startCal, endCal);
		    }
		    return null;
		});

		Optional<Pair<Calendar, Calendar>> result = dialog.showAndWait();
		Pair<Calendar, Calendar> searchRange = null;  
		if(result.isPresent()){
			
			searchRange = result.get(); 
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Bad Search Parameters");
			alert.setHeaderText(null);
			alert.setContentText("Did not enter a start date or end date properly.");
			alert.showAndWait();
			return; 
		}
		if(searchRange.getKey() == null || searchRange.getValue() == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Bad Search Parameters");
			alert.setHeaderText(null);
			alert.setContentText("Did not enter a start date or end date properly.");
			alert.showAndWait();
			return; 
		}
		if(searchRange.getKey().compareTo(searchRange.getValue()) > 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Bad Search Parameters");
			alert.setHeaderText(null);
			alert.setContentText("Start date is after end date.");
			alert.showAndWait();
			return; 
		}
		
		//Create list of shallow search results. 
		currentlySelectedAlbum = null;
		currentlySelectedIndex = -1;
		currentlySelectedPhoto = null; 
		currentlySelectedPhotoIndex = -1; 
		setAlbumOptionVisibility(false);
		setPhotoOptionVisibility(false); 
		setPhotoOperations(false); 
		
		ArrayList<Photo> photoList = new ArrayList<Photo>(); 
		Album temp = new Album("", photoList); 
		for(Album a: albumList){
			for(Photo p: a.getList()){
				if(p.getDate().compareTo(searchRange.getKey()) >= 0 && p.getDate().compareTo(searchRange.getValue()) <= 0){
					photoList.add(p);
					temp.setList(photoList);
					
					//proper dating. 
					Calendar min = temp.getMinDate();
					Calendar max = temp.getMaxDate(); 
					if(min == null || max == null){
						temp.setMinDate(p.getDate());
						temp.setMaxDate(p.getDate());
						temp.setMinDateString(temp.getMinDate().getTime().toString());
						temp.setMaxDateString(temp.getMaxDate().getTime().toString());
					}
					else if(p.getDate().compareTo(min) < 0){
						temp.setMinDate(p.getDate());
						temp.setMinDateString(temp.getMinDate().getTime().toString());
						temp.setMaxDateString(temp.getMaxDate().getTime().toString());
					}
					else if(p.getDate().compareTo(max) > 0){
						temp.setMaxDate(p.getDate());
						temp.setMinDateString(temp.getMinDate().getTime().toString());
						temp.setMaxDateString(temp.getMaxDate().getTime().toString());
					}
				}
			}
		}
		
		//return if no photos remaining
		if(temp.getList() == null || temp.getList().isEmpty()){
			return; 
		}
		else{
			imagePane.setVisible(true);
			imagePane.getChildren().clear(); 
			gridRow = 0; 
			gridCol = 0; 
			for(int i = 0; i < temp.getList().size(); i++){
				addPhoto(temp.getList().get(i), false, true);
			}
			b15.setDisable(false);
			searchResult = temp; 
		}
	}
	
	/**
	 * search by tag functionality
	 */
	private void searchByTag(){
		
		searchResult = null; 
		
		//get tags
		TextInputDialog dialog = new TextInputDialog("Search for Photos");
		dialog.setTitle("Search for Photos");
		dialog.setHeaderText("Enter tags in the following format (x=y)");
		dialog.setContentText("tags:");
		String tagString = null; 
		ArrayList<Pair<String, String>> tagArray = new ArrayList<Pair<String,String>>();
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		   	tagString = result.get(); 
		}
		
		//parse tags. 
		if(tagString != null){
			tagString = tagString.trim(); 
			for(int index = 0; index < tagString.length(); index++){
				if(tagString.charAt(index) == '('){
					Pair<String, String> pair = new Pair<String,String>(null, null); 
					boolean passedAnEqual = false;
					int startOfValue = -1; 
					for(int innerIndex = index+1; innerIndex < tagString.length(); innerIndex++){
						if(tagString.charAt(innerIndex) == '=' && !passedAnEqual){
							pair = new Pair<String,String>(tagString.substring(index+1, innerIndex).trim(), null);
							passedAnEqual = true;
							startOfValue = innerIndex+1; 
						}
						else if(tagString.charAt(innerIndex) == '=' && passedAnEqual){
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Bad Search Query");
							alert.setHeaderText(null);
							alert.setContentText("A tag with more than one \'=\' was entered.");

							alert.showAndWait();
							return; 
						}
						if(tagString.charAt(innerIndex) == ')' && passedAnEqual && startOfValue != -1){
							pair = new Pair<String,String>(pair.getKey(), tagString.substring(startOfValue, innerIndex).trim());
							tagArray.add(pair);
							index = innerIndex;
							innerIndex = tagString.length(); 
						}
					}
				}
			}
		}
		
		if(tagArray.size() == 0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Bad Search Query");
			alert.setHeaderText(null);
			alert.setContentText("No tags were entered.");
			alert.showAndWait();
			return;
		}
	
		//Create list of shallow search results. 
		currentlySelectedAlbum = null;
		currentlySelectedIndex = -1;
		currentlySelectedPhoto = null; 
		currentlySelectedPhotoIndex = -1; 
		setAlbumOptionVisibility(false);
		setPhotoOptionVisibility(false); 
		setPhotoOperations(false); 
		
		ArrayList<Photo> photoList = new ArrayList<Photo>(); 
		Album temp = new Album("", photoList); 
		for(Album a: albumList){
			for(Photo p: a.getList()){
				ArrayList<Pair<String,String>> tags = p.getTags();
				if(tags.contains(tagArray.get(0))){
					photoList.add(p);
					temp.setList(photoList);
					
					//proper dating. 
					Calendar min = temp.getMinDate();
					Calendar max = temp.getMaxDate(); 
					if(min == null || max == null){
						temp.setMinDate(p.getDate());
						temp.setMaxDate(p.getDate());
						temp.setMinDateString(temp.getMinDate().getTime().toString());
						temp.setMaxDateString(temp.getMaxDate().getTime().toString());
					}
					else if(p.getDate().compareTo(min) < 0){
						temp.setMinDate(p.getDate());
						temp.setMinDateString(temp.getMinDate().getTime().toString());
						temp.setMaxDateString(temp.getMaxDate().getTime().toString());
					}
					else if(p.getDate().compareTo(max) > 0){
						temp.setMaxDate(p.getDate());
						temp.setMinDateString(temp.getMinDate().getTime().toString());
						temp.setMaxDateString(temp.getMaxDate().getTime().toString());
					}
				}
			}
		}
		
		if(photoList == null || photoList.isEmpty()){
			return; 
		}
		else{
			tagArray.remove(0);
		}
		
		//find intersection of all tags. 
		if(tagArray.isEmpty() == false){
			for(Pair<String, String> tag: tagArray){
				for(int index = 0; index < photoList.size(); index++){
					Photo p = photoList.get(index);
					ArrayList<Pair<String,String>> tags = p.getTags();
					if(!tags.contains(tag)){
						photoList.remove(index);
						temp.setList(photoList);
						findAndSetMinandMaxDateStrings(temp);
						index--; 
					}
				}
			}
		}
		
		//return if no photos remaining
		if(temp.getList() == null || temp.getList().isEmpty()){
			return; 
		}
		else{
			imagePane.setVisible(true);
			imagePane.getChildren().clear(); 
			gridRow = 0; 
			gridCol = 0; 
			for(int i = 0; i < temp.getList().size(); i++){
				addPhoto(temp.getList().get(i), false, true);
			}
			b15.setDisable(false);
			searchResult = temp; 
		}
	}
	
	/**
	 * album option visibility setting
	 * @param setting boolean setting
	 */
	private void setAlbumOptionVisibility(boolean setting){
		b2.setDisable(!setting);
		b3.setDisable(!setting);
		b5.setDisable(!setting);
		b15.setDisable(true); 
	}
	
	/**
	 * photo option visibility setting
	 * @param setting boolean setting
	 */
	private void setPhotoOptionVisibility(boolean setting){
		l2.setVisible(setting);
		b7.setVisible(setting);
		imagePane.getChildren().clear(); 
		imagePane.setVisible(setting);
		currentlySelectedPhoto = null;
		if(setting == false){
			b8.setVisible(setting);
			b9.setVisible(setting);
			b10.setVisible(setting);
			b11.setVisible(setting);
			b12.setVisible(setting);
			b13.setVisible(setting);
			b14.setVisible(setting);
			l2.setVisible(setting);
		}
	}
	
	/**
	 * admin option visibility setting
	 * @param setting boolean setting
	 */
	private void setAdminOptionVisibility(boolean setting){
		l3.setVisible(setting);
		b16.setVisible(setting);
		b17.setVisible(setting);
		b18.setVisible(setting);
	}
	
	/**
	 * photo operations visibility setting
	 * @param setting boolean setting
	 */
	private void setPhotoOperations(boolean setting){
		b7.setVisible(setting);
		b8.setVisible(setting);
		b9.setVisible(setting);
		b10.setVisible(setting);
		b11.setVisible(setting);
		b12.setVisible(setting);
		b13.setVisible(setting);
		b14.setVisible(setting);
		if(currentlySelectedPhoto != null && currentlySelectedPhoto.getTags().size() > 0){
			b14.setDisable(false);
		}
		else{
			b14.setDisable(true);
		}
		if(albumList.size() <= 1 && setting == true){
			b10.setDisable(true);
			b9.setDisable(true);
		}
		else{
			b10.setDisable(false);
			b9.setDisable(false); 
		}
	}
	
	/**
	 * setter for currently selected album
	 */
	private void setSelectedAlbum(){
		currentlySelectedAlbum = albumView.getSelectionModel().getSelectedItem();
		currentlySelectedIndex = albumView.getSelectionModel().getSelectedIndex(); 
		setPhotoOptionVisibility(false);
		setAlbumOptionVisibility(true);
		b2.setDisable(false); 
	}
	
	/**
	 * setter for currently selected photo
	 * @param photo photo
	 */
	public void setCurrentlySelectedPhoto(Photo photo){
		currentlySelectedPhoto = photo; 
	}
	
	/**
	 * setter for current user
	 * @param currentUser current user
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * setter for photos instanc photos instancee
	 * @param myPhotos
	 */
	public void setMyPhotos(Photos myPhotos) {
		this.myPhotos = myPhotos;
	}
	
	/**
	 * setter for userBase array
	 * @param userBase userBase array
	 */
	public void setUserBase(ArrayList<User> userBase) {
		this.userBase = userBase;
	}
	
	/**
	 * method for showing user list (only used by admin)
	 */
	private void showUserList(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User List");
		alert.setHeaderText("Below is a list of users.");
		String exceptionText = "";
		Label label = new Label("User List:");
		
		for(int x = 0; x < userBase.size(); x++){
			exceptionText = exceptionText + userBase.get(x).getUserName() + "\n";
		}
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setContent(expContent);
		alert.showAndWait();
	}
}
