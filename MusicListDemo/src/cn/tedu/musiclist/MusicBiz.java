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
			m.setName("����"+i);
			m.setAlbum("ר��"+i);
			m.setAlbumPicPath("http://www.musiconline.com/imgs/gequ"+i+".jpg");
			m.setDuration("3:52");
			m.setArtist("�Ź���");
			m.setMusicPath("http://www.musiconline.com/musics/qianqianquege"+i+".mp3");
			musics.add(m);
		}
		/*
		Music m = new Music();
		m.setId(1);
		m.setName("ǧǧ�ʸ�");
		m.setAlbum("���˼�");
		m.setAlbumPicPath("http://www.musiconline.com/imags/qingrenjian.jpg");
		m.setDuration("3:52");
		m.setArtist("�Ź���");
		m.setMusicPath("http://www.musiconline.com/musics/qianqianjuege.mp3");
		musics.add(m);
		
		
		m = new Music();
		m.setId(2);
		m.setName("Ӳ��");
		m.setAlbum("Ц�ſ�");
		m.setAlbumPicPath("http://www.musiconline.com/imags/xiaozheku.jpg");
		m.setDuration("3:52");
		m.setArtist("����");
		m.setMusicPath("http://www.musiconline.com/musics/yingbi.mp3");
		musics.add(m);
		
		
		m = new Music();
		m.setId(3);
		m.setName("�໨��");
		m.setAlbum("�Һ�æ");
		m.setAlbumPicPath("http://www.musiconline.com/imags/wohenmang.jpg");
		m.setDuration("4:52");
		m.setArtist("����");
		m.setMusicPath("http://www.musiconline.com/musics/qinghuaci.mp3");
		musics.add(m);
		
		
		m = new Music();
		m.setId(4);
		m.setName("������");
		m.setAlbum("��������");
		m.setAlbumPicPath("http://www.musiconline.com/imags/junshengjinshi.jpg");
		m.setDuration("4:52");
		m.setArtist("������");
		m.setMusicPath("http://www.musiconline.com/musics/tianmimi.mp3");
		musics.add(m);
		*/
		return musics;
	}
}
