// Fahad Syed 			 | netid: fbs14
// Abdulellah Abualshour | netid: aha59

package control;
import java.io.File;
import java.io.Serializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Class implementation of the display image functionality for the photo manager
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 */
public class DisplayControl implements Serializable{

	static final long serialVersionUID = 6L; 
	
	@FXML Button previousButton;
	@FXML Button nextButton;
	@FXML ImageView displayImage; 
	@FXML Label imageCaption; 
	@FXML Label imageTags; 
	
	private Control controller; 
	private int displayIndex = -1; 
	
	public void start(Stage mainStage) {
		displayImage.setImage(openFile(controller.getCurrentlySelectedPhoto().getImage()));
		displayIndex = controller.getCurrentlySelectedPhotoIndex(); 
		imageCaption.setText("caption: "+controller.getCurrentlySelectedPhoto().getCaption());
		imageTags.setText("tags: "+controller.getCurrentlySelectedPhoto().getTags().toString());
		displayImage.setPreserveRatio(true);
		displayImage.maxHeight(550);
		displayImage.maxWidth(700);
	}
	
	/**
	 * click event handling
	 * @param e action event to handle
	 */
	public void click(ActionEvent e) {
		if((e.getSource() instanceof Button)){
			Button b = (Button)e.getSource();
			if (b == previousButton) {
				previous(); 
			}
			else if (b == nextButton){
				next(); 
			}
		}
	}

	/**
	 * next button functionality
	 */
	private void next(){
		if(displayIndex >= controller.getCurrentlySelectedAlbum().getList().size()-1){
			return; 
		}
		else{
			displayIndex += 1; 
			displayImage.setImage(openFile(controller.getCurrentlySelectedAlbum().getList().get(displayIndex).getImage()));
			displayImage.setPreserveRatio(true);
			displayImage.maxHeight(550);
			displayImage.maxWidth(700);
			imageCaption.setText("caption: "+controller.getCurrentlySelectedAlbum().getList().get(displayIndex).getCaption());
			imageTags.setText("tags: "+controller.getCurrentlySelectedPhoto().getTags().toString());
		}
	}
	
	/**
	 * previous button functionality
	 */
	private void previous(){
		if(displayIndex == 0){
			return; 
		}
		else{
			displayIndex -= 1; 
			displayImage.setImage(openFile(controller.getCurrentlySelectedAlbum().getList().get(displayIndex).getImage()));
			displayImage.setPreserveRatio(true);
			displayImage.maxHeight(550);
			displayImage.maxWidth(700);
			imageCaption.setText("caption: "+controller.getCurrentlySelectedAlbum().getList().get(displayIndex).getCaption());
			imageTags.setText("tags: "+controller.getCurrentlySelectedPhoto().getTags().toString());
		}
	}
	
	/**
	 * setter for the controller
	 * @param newController new controller
	 */
	public void setController(Control newController){	
		this.controller = newController; 
	}
	
	/**
	 * getter for the controller
	 * @return controller
	 */
	public Control getController(){
		return this.controller; 
	}
	
	/**
	 * file opener for the image
	 * @param file the file
	 * @return the image
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
	
}