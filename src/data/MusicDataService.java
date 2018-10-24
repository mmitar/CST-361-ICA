package data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.Album;
import beans.Track;
import util.DatabaseException;

@Stateless
@Local(DataAccessInterface.class)
@LocalBean
public class MusicDataService implements DataAccessInterface <Album>
{
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/music";
	String username = "root";
	String password = "root";
	
	@Override
	public List<Album> findAll() {

		List<Album> albums = new ArrayList<Album>();
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			
			String sql1 = "SELECT * FROM ALBUM LIMIT 1000";
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			
			while(rs1.next())
			{
				Album album = new Album(rs1.getString("TITLE"), rs1.getString("ARTIST"), rs1.getInt("YEAR"));
				
				List<Track> tracks = new ArrayList<Track>();
				String sql2 = "SELECT * FROM TRACK WHERE ALBUM_ID = "+ rs1.getInt("ID");
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sql2);
				
				while(rs2.next())
				{
					tracks.add(new Track(rs2.getString("TITLE"), rs2.getInt("NUMBER")));
				}
				
				rs2.close();
				stmt2.close();
				rs1.close();
				stmt1.close();
				
				album.setTracks(tracks);
				albums.add(album);
			}
			
		} catch(SQLException e) 
		{
			e.printStackTrace();			
			throw new DatabaseException(e);
		} finally
		{
			//Cleanup Database
			if(conn != null) 
			{
				try {
				conn.close();
				} catch (SQLException e)
				{
					e.printStackTrace();			
					throw new DatabaseException(e);
				}
			}
		}
		
		return albums;	
	}
	@Override
	public Album findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Album findBy(Album album) {
		
		try {
			
			conn = DriverManager.getConnection(url, username, password);
			String sql1 = String.format("SELECT * FROM ALBUM WHERE TITLE='%s' AND ARTIST='%s' AND YEAR='%d'", album.getTitle(), album.getArtist(), album.getYear());
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			
			if(!rs1.next())
			{
				rs1.close();
				stmt1.close();
				return null;
			}
			
			album.setTitle(rs1.getString("TITLE"));
			album.setArtist(rs1.getString("ARTIST"));
			album.setYear(rs1.getInt("YEAR"));
			
			List<Track> tracks = new ArrayList<Track>();
			String sql2 = "SELECT * FROM TRACK WHERE ALBUM_ID = "+ rs1.getInt("ID");
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			
			while(rs2.next())
			{
				tracks.add(new Track(rs2.getString("TITLE"), rs2.getInt("NUMBER")));
			}

			album.setTracks(tracks);
			
			rs2.close();
			stmt2.close();
			rs1.close();
			stmt1.close();
			
		} catch(SQLException e) 
		{
			e.printStackTrace();			
			throw new DatabaseException(e);
		} finally
		{
			//Cleanup Database
			if(conn != null) 
			{
				try {
				conn.close();
				} catch (SQLException e)
				{
					e.printStackTrace();			
					throw new DatabaseException(e);
				}
			}
		}
		return album;
	}
	
	@Override
	public boolean create(Album album) {
		//Insert Album and Tracks
		try {
			//Connect to the database
			conn = DriverManager.getConnection(url, username, password);
			
			//Insert an Album
			String sql = String.format("INSERT INTO album (TITLE, ARTIST, YEAR) VALUES('%s', '%s', %d)", 
					album.getTitle(), album.getArtist(), album.getYear());
			Statement stmt = conn.createStatement(); //Prepared Statement is more secure from SQL injection
			stmt.executeUpdate(sql);
			// 
			// Get Auto-Increment PK back
			String sql2 = "SELECT LAST_INSERT_ID() AS LAST_ID FROM album";
			ResultSet rs = stmt.executeQuery(sql2);
			rs.next();
			int albumId = rs.getInt("LAST_ID");
			rs.close();
			stmt.close();
			
			// Insert all the Tracks
			Statement stmt2 = conn.createStatement();
			for(Track track: album.getTracks())
			{
				String sql3 = String.format("INSERT INTO track (ALBUM_ID, TITLE, NUMBER) VALUES(%d, '%s', %d)",
						albumId, track.getTitle(), track.getNumber());
				stmt2.executeUpdate(sql3);
			}
			
		} catch(SQLException e) 
		{
			e.printStackTrace();			
			throw new DatabaseException(e);
		} finally
		{
			//Cleanup Database
			if(conn != null) 
			{
				try {
				conn.close();
				} catch (SQLException e)
				{
					e.printStackTrace();			
					throw new DatabaseException(e);
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean update(Album t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(Album t) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
