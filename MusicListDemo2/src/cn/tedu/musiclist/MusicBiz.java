package cn.tedu.musiclist;

import java.util.ArrayList;

public class MusicBiz {

	public static ArrayList<Music> getMusics(int page){
		ArrayList<Music> musics = new ArrayList<Music>();
		int i = (page-1)*20+1;
		int end = i+20;
		for(;i<end;i++){
			Music m = new Music();
			m.setId(i);
			m.setName("¸èÇú"+i);
			m.setAlbum("×¨¼­"+i);
			m.setAlbumPicPath("http://www.musiconline.com/imgs/gequ"+i+".jpg");
			m.setDuration("3:52");
			m.setArtist("ÕÅ¹úÈÙ");
			m.setMusicPath("http://www.musiconline.com/musics/qianqianquege"+i+".mp3");
			musics.add(m);
		}
		/*
		Music m = new Music();
		m.setId(1);
		m.setName("Ç§Ç§ØÊ¸è");
		m.setAlbum("ÇéÈË¼ý");
		m.setAlbumPicPath("http://www.musiconline.com/imags/qingrenjian.jpg");
		m.setDuration("3:52");
		m.setArtist("ÕÅ¹úÈÙ");
		m.setMusicPath("http://www.musiconline.com/musics/qianqianjuege.mp3");
		musics.add(m);
		
		
		m = new Music();
		m.setId(2);
		m.setName("Ó²±Ò");
		m.setAlbum("Ð¦×Å¿Þ");
		m.setAlbumPicPath("http://www.musiconline.com/imags/xiaozheku.jpg");
		m.setDuration("3:52");
		m.setArtist("Íô·å");
		m.setMusicPath("http://www.musiconline.com/musics/yingbi.mp3");
		musics.add(m);
		
		
		m = new Music();
		m.setId(3);
		m.setName("Çà»¨´É");
		m.setAlbum("ÎÒºÜÃ¦");
		m.setAlbumPicPath("http://www.musiconline.com/imags/wohenmang.jpg");
		m.setDuration("4:52");
		m.setArtist("Íô·å");
		m.setMusicPath("http://www.musiconline.com/musics/qinghuaci.mp3");
		musics.add(m);
		
		
		m = new Music();
		m.setId(4);
		m.setName("ÌðÃÛÃÛ");
		m.setAlbum("¾ýÉú½ñÊÀ");
		m.setAlbumPicPath("http://www.musiconline.com/imags/junshengjinshi.jpg");
		m.setDuration("4:52");
		m.setArtist("µËÀö¾ý");
		m.setMusicPath("http://www.musiconline.com/musics/tianmimi.mp3");
		musics.add(m);
		*/
		return musics;
	}
}
