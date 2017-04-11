// Fahad Syed 			 | netid: fbs14
// Abdulellah Abualshour | netid: aha59

package model;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.util.Pair;

/**
 * Class implementation of a photo for the photo manager.
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 */
public class Photo  implements Serializable{
	
	static final long serialVersionUID = 4L;
	
	private ArrayList<Pair<String, String>> tags; 
	private String caption;
	private Calendar date; 
	private File image;
	
	/**
	 * no-arg default constructor
	 */
	public Photo() {
		this.tags = tags;
		this.caption = "";
		this.date = date;
		this.image = image;
	}
	
	/**
	 * full argument constructor
	 * @param newTags new tags
	 * @param newCaption new caption
	 * @param newDate new date
	 * @param newImage new image
	 */
	public Photo(ArrayList<Pair<String, String>> newTags, String newCaption, Calendar newDate, File newImage){
		this.tags = newTags;
		this.caption = newCaption;
		this.date = newDate;
		this.image = newImage;
	}
	
	/**
	 * getter for tags
	 * @return tags
	 */
	public ArrayList<Pair<String, String>> getTags(){
		return this.tags;
	}
	
	/**
	 * setter for tags
	 * @param newTags new tags
	 */
	public void setTags(ArrayList<Pair<String, String>> newTags){
		this.tags = newTags;
	}
	
	/**
	 * getter for caption
	 * @return caption
	 */
	public String getCaption(){
		return this.caption;
	}
	
	/**
	 * setter for caption
	 * @param newCaption new caption
	 */
	public void setCaption(String newCaption){
		this.caption = newCaption;
	}

	/**
	 * getter for date of picture
	 * @return date
	 */
	public Calendar getDate(){
		return this.date;
	}
	
	/**
	 * setter for the date of the picture
	 * @param newDate new date
	 */
	public void setDate(Calendar newDate){	
		this.date = newDate; 
	}

	/**
	 * getter for image corresponding to the photo object
	 * @return file corresponding to the photo
	 */
	public File getImage(){
		return this.image; 
	}
	
	/**
	 * setter fr the image corresponding to the photo object
	 * @param newImage new image
	 */
	public void setImage(File newImage){
		this.image = newImage;
	}

}

