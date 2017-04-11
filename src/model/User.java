// Fahad Syed 			 | netid: fbs14
// Abdulellah Abualshour | netid: aha59

package model;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class implementation of user of the photo manager.
 * @author Abdulellah Abualshour
 * @author Fahad Syed
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 10L;
	private String userName;
	private boolean isAdmin; 
	private ArrayList<SerializableAlbum> albums;
	
	/**
	 * no-arg constructor
	 */
	public User()
	{
		userName = null;
		isAdmin = false;
		albums = null;
	}
	
	/**
	 * full arg constructor
	 * @param name name
	 * @param isAdmin is the user an admin?
	 * @param albums album list
	 */
	public User(String name, boolean isAdmin, ArrayList<SerializableAlbum> albums)
	{
		userName = name; 
		this.isAdmin = isAdmin; 
		this.albums = albums; 
	}
	
	/**
	 * Function to return boolean for admin or not
	 * @return true or false
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	
	/**
	 * set a user as an admin (true or false)
	 * @param isAdmin true or false
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	/**
	 * getter for username
	 * @return username as a string
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * setter for the username
	 * @param userName username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * getter for the list of albums corresponding for the user
	 * @return album list
	 */
	public ArrayList<SerializableAlbum> getAlbums() {
		return albums;
	}
	
	/**
	 * setter for the album list corrsponding to the user	
	 * @param albums albums
	 */
	public void setAlbums(ArrayList<SerializableAlbum> albums) {
		this.albums = albums;
	} 
	
	/**
	 * writer for object serialization
	 * @param out outout stream
	 * @throws IOException error check
	 */
	public void writeObject(ObjectOutputStream out) 
	throws IOException{
		 out.defaultWriteObject();
	 }
	 
	/**
	 * reader for object deserialization
	 * @param in input stream
	 * @throws IOException error check
	 * @throws ClassNotFoundException error check
	 */
	public void readObject(java.io.ObjectInputStream in) 
    throws IOException, ClassNotFoundException {
    	try {	
    		in.readObject();	
		} catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

	
	@Override
	/**
	 * toString method
	 */
	public String toString() {
		return userName;
	}
}
