package binbin.leslie.cn.myvediolive.constant;

public class Constant {
	public static String VIDEO_KEY="bfe17b4aecab9927c473826b2d7ba49a";
	public static String VIDEO_SECRET="2674e4a69e70";

	public static final Boolean DEBUG = true;

	public static final int LEFT = 0;
	public static final int MIDDLE=1;
	public static final int RIGHT=2;

	public static final String API_SERVER = "https://api.netease.im/nimserver/user/create.action";
	public static final String API_LOGIN = "user/login";
	public static final String API_LOGOUT = "user/logout";
	public static final String API_NAME_REGISTER = "user/reg";
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_KEY_APP_KEY = "APPKEY";
	public static final String HEADER_KEY_APP_SEACRET = "appSecret";
	public static final String HEADER_KEY_APP_NONCE = "Nonce";
	public static final String HEADER_KEY_APP_CURTIME = "CurTime";
	public static final String HEADER_KEY_APP_CHECKSUM = "CheckSum";




	public static final String RESULT_KEY_CODE = "code";
	public static final String RESULT_KEY_ERROR_MSG = "msg";

	public static final String RESULT_KEY_RES = "ret";
	public static final String RESULT_KEY_TOKEN = "token";
	public static final String RESULT_KEY_SID = "sid";

	public static final String RESULT_ROOMID = "roomid";
	public static final String RESULT_PUSH_URL = "pushUrl";
	public static final String RESULT_RTMP_URL = "rtmpPullUrl";
	public static final String RESULT_HLS_URL = "hlsPullUrl";
	public static final String RESULT_HTTP_URL = "httpPullUrl";
	public static final String RESULT_OWNER = "owner";
	public static final String RESULT_STATUS = "status";

	// request login
	public static final String REQUEST_USER_ACCID="accid";
	public static final String REQUEST_USER_NAME = "username";
	public static final String REQUEST_NICK_NAME = "nickname";
	public static final String REQUEST_PASSWORD = "password";

	//request room
	public static final String REQUEST_SID = "sid";
	public static final String REQUEST_ROOM_ID = "roomid";
	public static final String REQUEST_PULL_URL = "pullUrl";
	public static final String CID = "cid";
	public static final String REQUEST_DEVICE_ID = "deviceid";

	// code
	public static final int RESULT_CODE_SUCCESS = 200;


	public static enum Position{
		LEFT,MIDDLE,RIGHT
	};
}
