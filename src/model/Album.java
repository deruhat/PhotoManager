// Fahad Syed 			 | netid: fbs14
// Abdulellah Abualshour | netid: aha59

package model;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class implementation of an album for the photo manager
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 */
public class Album  implements Serializable{

	static final long serialVersionUID = 3L; 
	
	private SimpleStringProperty name;
	private SimpleStringProperty numPhotos;
	private SimpleStringProperty minDateString;
	private SimpleStringProperty maxDateString; 
	private ArrayList<Photo> photoList;
	private int myGridRows;
	private int myGridCols; 
	private Calendar minDate;
	private Calendar maxDate; 
	
	/**
	 * default no-arg constructor
	 */
	public Album() {
		this.name = name;
		this.photoList = photoList;
		this.numPhotos = new SimpleStringProperty("0");
		this.minDateString = new SimpleStringProperty("");
		this.maxDateString = new SimpleStringProperty(""); 
		minDate = null;
		maxDate = null; 
	}
	
	/**
	 * Full argument constructor
	 * @param newName new name
	 * @param newList new list
	 */
	public Album(String newName, ArrayList<Photo> newList){
		this.name = new SimpleStringProperty(newName);
		this.photoList = newList;
		this.numPhotos = new SimpleStringProperty("0");
		this.minDateString = new SimpleStringProperty("");
		this.maxDateString = new SimpleStringProperty(""); 
		minDate = null;
		maxDate = null; 
	}
	
	/**
	 * Serialized album constructor
	 * @param a serialized album
	 */
	public Album(SerializableAlbum a)
	{
		this.name = new SimpleStringProperty(a.getName()); 
		this.photoList = a.getPhotoList();
		this.numPhotos = new SimpleStringProperty(a.getNumPhotos());
		this.minDate = a.getMinDate();
		this.maxDate = a.getMaxDate();
		this.minDateString = new SimpleStringProperty(a.getMinDateString());
		this.maxDateString = new SimpleStringProperty(a.getMaxDateString());
	}
	
	/**
	 * Name getter
	 * @return name
	 */
	public String getName(){
		return this.name.get();
	}

	/**
	 * Name setter
	 * @param newName new name
	 */
	public void setName(String newName){
		this.name = new SimpleStringProperty(newName);
	}
	
	/**
	 * Getter for number of photos
	 * @return number of photos
	 */
	public String getNumPhotos(){
		return this.numPhotos.get();
	}
	
	/**
	 * Setter for number of photos
	 * @param num number of photos
	 */
	public void setNumPhotos(int num){
		numPhotos = new SimpleStringProperty(Integer.toString(num)); 
	}
	
	/**
	 * Getter for earliest modification
	 * @return date of earliest modification
	 */
	public String getMinDateString(){
		return this.minDateString.get();
	}
	
	/**
	 * Setter for earliest modification
	 * @param minDate earliest date
	 */
	public void setMinDateString(String minDate){
		minDateString = new SimpleStringProperty(minDate); 
	}
	
	/**
	 * getter for most recent modification string
	 * @return date of most recent modification
	 */
	public String getMaxDateString(){
		return this.maxDateString.get();
	}
	
	/**
	 * setter for most recent modification string
	 * @param maxDate most recent date
	 */
	public void setMaxDateString(String maxDate){
		maxDateString = new SimpleStringProperty(maxDate); 
	}
	
	/**
	 * getter for photo list
	 * @return photo list
	 */
	public ArrayList<Photo> getList(){
		return this.photoList;
	}
	
	/**
	 * setter for photo list
	 * @param newList new list
	 */
	public void setList(ArrayList<Photo> newList){
		this.photoList = newList;
		this.numPhotos = new SimpleStringProperty(Integer.toString(this.photoList.size())); 
	}
	
	/**
	 * getter for earliest date
	 * @return date of earliest modification
	 */
	public Calendar getMinDate(){
		return this.minDate; 
	}
	
	/**
	 * setter for earliest modification
	 * @param minD earliest date
	 */
	public void setMinDate(Calendar minD){
		this.minDate = minD; 
	}
	
	/**
	 * getter for most recent modification
	 * @return date of most recent modification
	 */
	public Calendar getMaxDate(){
		return this.maxDate; 
	}
	
	/**
	 * setter for most recent modification
	 * @param maxD recent date
	 */
	public void setMaxDate(Calendar maxD){
		this.maxDate = maxD; 
	}
	
	/**
	 * to string method
	 * @return album as a string
	 */
    public String toString(){
    	return name.get();
    }
    
    /**
	 * writes changes into a dat file
	 * @param out Output Stream to save
	 * @throws IOException error check
	 */
    public void writeObject(ObjectOutputStream out) throws IOException{
    	out.defaultWriteObject();
		out.writeObject(name.get());
		out.writeObject(numPhotos.get());
		out.writeObject(maxDateString.get());
		out.writeObject(minDateString.get());
		out.writeObject(photoList);
		out.writeObject(new Integer(myGridRows));
		out.writeObject(new Integer(myGridCols));
		out.writeObject(minDate);
		out.writeObject(maxDate);
	}
    
    /**
	 * reads changes from a dat file
	 * @param in input stream to read from
	 * @throws IOException error check
	 * @throws ClassNotFoundException error check
	 */
    public void readObject(java.io.ObjectInputStream in) 
    throws IOException, ClassNotFoundException {
    	try {	
    		name = new SimpleStringProperty((String)in.readObject()); 
    		numPhotos = new SimpleStringProperty((String)in.readObject());
    		maxDateString = new SimpleStringProperty((String)in.readObject()); 
    		minDateString = new SimpleStringProperty((String)in.readObject());
    		photoList = (ArrayList<Photo>)in.readObject();
    		myGridRows = (Integer)in.readObject(); 
    		myGridCols = (Integer)in.readObject();
    		minDate = (Calendar)in.readObject(); 
    		maxDate = (Calendar)in.readObject(); 
    		
		} catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
}
