package business;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import beans.Album;
import data.DataAccessInterface;
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
	
	@EJB
	Cache cache;
	
	@Inject
	TrackFinderInterface tf;
	
	@Override
	public Album addAlbum(Album album) throws TracksNotFoundException {
		
		//Step 1: get the Tracks for the specificed album
		album.setTracks(tf.getTracks(album));
		
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
		// Step 1: Get and return the Album from the Album Cache or the database
		Album albumNew = cache.getObject(album);
		if(albumNew != null)
		{
			return albumNew;
		}
		albumNew = dao.findBy(album);
		
		// Step 2: If album not found throw a Custom Functional Exception else update the Album Cache and Return New Album
		if(albumNew == null) 
		{
			throw new AlbumNotFoundException();
		}
		else
		{
			cache.putObject(albumNew);
			return albumNew;
		}
	}
}
