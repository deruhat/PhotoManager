// Fahad Syed 			 | netid: fbs14
// Abdulellah Abualshour | netid: aha59

package model;
import java.io.Serializable;
import java.util.*;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class implementation of the serialized album for the photo manager
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 */
public class SerializableAlbum  implements Serializable{

	static final long serialVersionUID = 3L; 
	
	private String name;
	private String numPhotos;
	private String minDateString;
	private String maxDateString; 
	private ArrayList<Photo> photoList;
	private Calendar minDate;
	private Calendar maxDate; 
	
	/**
	 * no-arg constructor
	 */
	public SerializableAlbum() {
		this.name = "";
		this.numPhotos = "0";
		this.minDateString = "";
		this.maxDateString = ""; 
		this.photoList = new ArrayList<Photo>();
		minDate = null;
		maxDate = null; 
	}
	
	/**
	 * full argument constructor
	 * @param newName new name
	 * @param newList new list
	 * @param newNumPhotos new number of photos
	 * @param newMinDS new earliest date of modification
	 * @param newMaxDS new most recent date of modification
	 */
	public SerializableAlbum(SimpleStringProperty newName, ArrayList<Photo> newList, 
			                 SimpleStringProperty newNumPhotos, SimpleStringProperty newMinDS,
			                 SimpleStringProperty newMaxDS){
		this.name = newName.get();
		this.photoList = newList;
		this.numPhotos = newNumPhotos.get();
		this.minDateString = newMinDS.get(); 
		this.maxDateString = newMaxDS.get(); 
		minDate = null;
		maxDate = null; 
	}   
	
	/**
	 * getter for the name of the album
	 * @return name as a string
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * setter for the name of the album
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter for the number of photos in the album
	 * @return number of photos in the album
	 */
	public String getNumPhotos() {
		return numPhotos;
	}

	/**
	 * setter for the number of photos in the album
	 * @param numPhotos number of photos
	 */
	public void setNumPhotos(String numPhotos) {
		this.numPhotos = numPhotos;
	}

	/**
	 * getter for the earliest date of modification string
	 * @return earliest date of modification string
	 */
	public String getMinDateString() {
		return minDateString;
	}

	/**
	 * setter for the earliest date of modification string
	 * @param minDateString earliest day of modification string
	 */
	public void setMinDateString(String minDateString) {
		this.minDateString = minDateString;
	}

	/**
	 * getter for most recent date of modification string
	 * @return most recent date of modification string
	 */
	public String getMaxDateString() {
		return maxDateString;
	}

	/**
	 * setter for most recent date of modification string
	 * @param maxDateString most recent date of modification string
	 */
	public void setMaxDateString(String maxDateString) {
		this.maxDateString = maxDateString;
	}

	/**
	 * getter for the array of photos corresponding to the album
	 * @return array of photos
	 */
	public ArrayList<Photo> getPhotoList() {
		return photoList;
	}

	/**
	 * setter for the photo list corresponding to the album
	 * @param photoList photo list
	 */
	public void setPhotoList(ArrayList<Photo> photoList) {
		this.photoList = photoList;
	}

	/**
	 * getter for the earliest date of modification 
	 * @return earliest date of modification 
	 */
	public Calendar getMinDate() {
		return minDate;
	}

	/**
	 * setter for the earliest date of modification
	 * @param minDate earliest day of modification 
	 */
	public void setMinDate(Calendar minDate) {
		this.minDate = minDate;
	}

	/**
	 * getter for most recent date of modification 
	 * @return most recent date of modification 
	 */
	public Calendar getMaxDate() {
		return maxDate;
	}

	/**
	 * setter for most recent date of modification 
	 * @param maxDate most recent date of modification 
	 */
	public void setMaxDate(Calendar maxDate) {
		this.maxDate = maxDate;
	}

	/**
	 * one argument constructor to convert an album into a serialized album
	 * @param a album
	 */
	public SerializableAlbum(Album a)
	{
		this.name = a.getName(); 
		this.photoList = a.getList();
		this.numPhotos = a.getNumPhotos();
		this.minDate = a.getMinDate();
		this.maxDate = a.getMaxDate();
		this.minDateString = a.getMinDateString();
		this.maxDateString = a.getMaxDateString();
	}
}
