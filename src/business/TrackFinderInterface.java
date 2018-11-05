package business;

import java.util.List;

import beans.Album;
import beans.Track;

public interface TrackFinderInterface 
{
	public List<Track> getTracks(Album album);
}
