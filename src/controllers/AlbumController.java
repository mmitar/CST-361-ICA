package controllers;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.plaf.synth.SynthSeparatorUI;

import beans.Album;
import beans.Track;
import business.MusicManager;
import business.MusicManagerInterface;
import util.AlbumNotFoundException;
import util.TracksNotFoundException;

@ManagedBean
@ViewScoped
public class AlbumController {

	@EJB
	MusicManagerInterface mgr;
	
	public String onSubmit(Album album)
	{
		//Call Music Manager Business Service
		try {
			album = mgr.addAlbum(album);
		} catch (TracksNotFoundException e) {
			System.out.println("================> Tracks Not Found Exception.");
		}
		
		//Forward to Test Response View along with the User Managed Bean
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("album", album);
		return "AddAlbumResponse.xhtml";
	}
	
	public String onFind(Album album)
	{
		// Call Music Manager Business Service
		try{
			album = mgr.getAlbum(album);
			System.out.println(album.getNumberTracks());
		}catch(AlbumNotFoundException e) {
			
			album.setArtist("?");
			album.setTitle("?");
			album.setYear(-1);
			album.setTracks(new ArrayList<Track>());
			System.out.println("================> Album Not Found Exception");
		}
		
		// Forward to Test Response View along with the Album Managed Bean
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("album",album);
		return "AddAlbumResponse.xhtml";
	}
}
