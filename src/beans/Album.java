package beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
@ViewScoped // Retained for the duration of the same View.
// @RequestScoped - Dies once HTTP Request is fullfilled
// @SessionScoped - Will live till the session is destroyed. When user logs out/browser closes.
// @ApplicationScoped - Lives a long as the application is running on the server.
// @ConversationScoped - Can Start and End anytime.
public class Album {

	@NotNull(message="Please enter an Album Title between 5 and 50 characters. This is a required field.")
	@Size(min=5,max=50, message="Please enter an Album Title between 5 and 50 characters. This is a required field.")
	String title;
	
	@NotNull(message="Please enter an Album Artist between 5 and 25 characters. This is a required field.")
	@Size(min=5,max=25, message="Please enter an Album Title between 5 and 25 characters. This is a required field.")
	String artist;
	
	@Min(value=1920, message="Please enter a year from 1920 to 2020. This is a required field.")
	@Max(value=2020, message="Please enter a year from 1920 to 2020. This is a required field.")
	int year;
	
	List<Track> tracks;
	
	public Album()
	{
		title = "";
		artist = "";
		year = 0;
		tracks = new ArrayList<Track>();
	}
	
	public Album(String title, String artist, int year)
	{
		this.title = title;
		this.artist = artist;
		this.year = year;
		tracks = new ArrayList<Track>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	public int getNumberTracks() 
	{
		return tracks.size();
	}
}
