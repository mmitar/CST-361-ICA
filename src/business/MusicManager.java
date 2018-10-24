package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.Album;
import beans.Track;
import data.DataAccessInterface;
import data.MusicDataService;
import util.AlbumNotFoundException;
import util.TracksNotFoundException;

/**
 * Session Bean implementation class MusicManager
 */
@Stateless
@Local(MusicManagerInterface.class)
@LocalBean
public class MusicManager implements MusicManagerInterface {

	@EJB
	DataAccessInterface<Album> dao;
	
	HashMap<String, List<Track>> trackInfo;
	
	public MusicManager() {
		
		trackInfo = new HashMap<String, List<Track>>();
		List<Track> tracks1 = new ArrayList<Track>();
		tracks1.add(new Track("Taxman", 1));
		tracks1.add(new Track("Love You To", 2));
		tracks1.add(new Track("Here, There and Everywhere", 3));
		tracks1.add(new Track("I Want To Tell You", 4));
		tracks1.add(new Track("Yellow Submarine", 5));
		tracks1.add(new Track("For No One", 6));
		tracks1.add(new Track("Tomorrow Never Knows", 7));
		tracks1.add(new Track("Good Day Sunshine", 8));
		trackInfo.put("The Beatles - Revolver - 1966", tracks1);
	}
	
	private List<Track> getTracks(Album album) {
		
		String key = album.getArtist() + " - " + album.getTitle() + " - " + album.getYear();
		
		if(trackInfo.containsKey(key))
		{
			return trackInfo.get(key);
		}
		else
		{
			return new ArrayList<Track>();
		}
	}
	
	@Override
	public Album addAlbum(Album album) throws TracksNotFoundException {
		
		//Step 1: get the Tracks for the specificed album
		album.setTracks(getTracks(album));
		
		//Step 2: Validate the Tracks that were populated for the Album
		if(album.getNumberTracks() == 0)
			throw new TracksNotFoundException();
		
		//Step 3: Persist the Album in the database
		dao.create(album);
		
		//Step 4: (For Testing) Find and return this Album in the database
		return dao.findBy(album);
	}
	
	@Override
	public Album getAlbum(Album album) throws AlbumNotFoundException
	{
		// Step 1: Get and return the Album from the database
		Album albumNew = dao.findBy(album);
		
		// Step 2: If album not found throw a Custom Functional Exception else return New Album
		if(albumNew == null)
			throw new AlbumNotFoundException();
		else
			return albumNew;
	}
}
