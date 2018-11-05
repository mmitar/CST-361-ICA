package business;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import beans.Album;

@Startup
@Singleton
public class Cache {

		private HashMap<String, Album> cache;
		
		@PostConstruct
		public void init() {
			cache = new HashMap<String, Album>();
		}
		
		public Album getObject(Album album)
		{
			String key = album.getTitle() + " - " + album.getArtist() + " - " + album.getYear();
			if(cache.containsKey(key))
			{
				System.out.println("Cache hit for " + key);
				return cache.get(key);
			}
			else
			{
				System.out.println("Cache miss for " + key);
				return null;
			}
		}
		
		public void putObject(Album album)
		{
			String key = album.getTitle() + " - " + album.getArtist() + " - " + album.getYear();
			cache.put(key, album);
			System.out.println("Cache put for " + key);
		}
}
