package cn.tedu.musiclist;

public class Music {
	private int id;
	private String name;
	private String album;
	private String duration;
	private String artist;
	private String musicPath;
	private String albumPicPath;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getMusicPath() {
		return musicPath;
	}
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	public String getAlbumPicPath() {
		return albumPicPath;
	}
	public void setAlbumPicPath(String albumPicPath) {
		this.albumPicPath = albumPicPath;
	}
	@Override
	public String toString() {
		return "Music [id=" + id + ", name=" + name + ", album=" + album + ", duration=" + duration + ", artist="
				+ artist + ", musicPath=" + musicPath + ", albumPicPath=" + albumPicPath + "]";
	}
	
}
