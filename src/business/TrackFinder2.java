package business;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import beans.Album;
import beans.Track;

@Stateless
@Local(TrackFinderInterface.class)
@LocalBean
@Alternative
public class TrackFinder2 implements TrackFinderInterface 
{
	@Override
	public List<Track> getTracks(Album album) {

		ArrayList<Track> tracks = new ArrayList<Track>();
		
		try {
			// Open the Track Info file
			String filename = "/tracks/" + album.getTitle() + "_" + album.getArtist() + "_" + album.getYear() + ".txt";
			filename = filename.replace(' ', '_').toLowerCase();
			String path = Thread.currentThread().getContextClassLoader().getResource(filename).getPath();
			BufferedReader in = new BufferedReader(new FileReader(path));
			
			// Read all the tracks for Album
			int track = 1;
			String line;
			while((line = in.readLine()) != null)
			{
				tracks.add(new Track(line, track));
				++ track;
			}
			
			in.close();
			
			// Return tracks
			return tracks;
		}
		catch(Exception e)
		{
			return new ArrayList<Track>();
		}
	}
}
