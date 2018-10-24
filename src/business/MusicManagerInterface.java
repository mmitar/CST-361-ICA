package business;

import beans.Album;
import util.AlbumNotFoundException;
import util.TracksNotFoundException;

public interface MusicManagerInterface {

	public Album addAlbum(Album album) throws TracksNotFoundException;
	public Album getAlbum(Album album) throws AlbumNotFoundException;
}