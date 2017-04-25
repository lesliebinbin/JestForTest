package com.lemon.c.nms.network;

import java.io.Serializable;

/**
 * Created by C on 2016/8/17.
 */
public class URLs implements Serializable {

    public static final String IN_BASE_URL = "http://192.100.0.2:8088/";
    public static final String BASE_URL= "http://116.77.33.53:8088/";
    //登录
    public final static String LOGIN_VALIDATE_HTTP ="core_sys/index.php/sjlogin/logininfo";

    public final static String ROOM_LIST ="core_sys/index.php/order_management/get_sr/1";
    public final static String CMTS_LIST ="core_sys/index.php/order_management/get_cmts_name/";

    public final static String SEARCH_LIST = "core_sys/app_view/child_view/equ_manage/dct/getcm.php";
    public final static String UPPORT_LIST ="core_sys/index.php/run_report/get_cmtsport_name/";
    public final static String DOWNPORT_LIST ="core_sys/index.php/run_report/get_cmts_down_port_name/";
    public final static String PORT_DETAIL_DOWN_INFO ="core_sys/index.php/cmts_monitoring/cmts_down_port_info";
    public final static String DCT_DETAIL_INFO ="core_sys/index.php/fbscj/dct_info";
    public final static String PORT_SNR_INFO ="core_sys/index.php/cmts_port_chart/get_snr_date";
    public final static String PORT_BER_INFO ="core_sys/index.php/cmts_port_chart/get_ber_date";
    public final static String PORT_SNR_REFRESH ="core_sys/index.php/cmts_port_chart/get_snr_info";
    public final static String PORT_MER_REFRESH ="core_sys/index.php/cmts_port_chart/get_ber_info1";
    public final static String PORT_NPA_INFO ="core_sys/index.php/cmts_port_chart/get_npa_date";
    public final static String DEVICE_SEARCH_INFO="core_sys/index.php/fs/m_fs";
    public final static String DS1610_INFO="core_sys/index.php/sjjk/dsport";
    public final static String UP_CHANNEL="core_sys/index.php/fs/up_link";
    public final static String WORKORDER="core_sys/index.php/fs/err_npa";
    public final static String LOW_SNR="core_sys/index.php/sjjk/cmts20";
    public final static String LOW_BER="core_sys/index.php/cmts/cmts_ber";

    public final static String PORT_DETAIL_UP_INFO ="core_sys/index.php/sjjk/cmts_port_info";
    public final static String ALARM_SNR="core_sys/index.php/sjjk/get_alarm_snr";
    public final static String ALARM_BER="core_sys/index.php/sjjk/get_alarm_ucer";
    public final static String ALARM_NPA="core_sys/index.php/sjjk/get_alarm_npa";
    public final static String ALARM_CMTS="core_sys/index.php/cmts/cmts_ber";
    public final static String ALARM_QAM="core_sys/index.php/sjjk/port_qam_historical_change";
    public final static String ALARM_DCT="core_sys/index.php/sjjk/fbs_info";

    public final static String SPECTRUM_CHART="core_sys/index.php/sjjk/ds_go_get_spectrum";
    public final static String CHANGE_PASSWORD="core_sys/index.php/sjlogin/setpw";
    public final static String SPECTRUM_WORKORDER="core_sys/index.php/sjjk/ds1610_document" ;
    public final static String SPECTRUM_ROOM_URL="core_sys/index.php/sjjk/equ_2_fs";
    public final static String ADD_LACTION="core_sys/index.php/sjjk/location";
    public final static String DCT_UPDATE="core_sys/index.php/sjjk/input_dct";
    public final static String DCT_LEVEL_CHART ="core_sys/index.php/sjjk/get_dct_chart";
    public final static String AVERAGE_NPA="core_sys/index.php/sjjk/network_trends";
    public final static String NPA_SUM="core_sys/index.php/sjjk/npadjtj";
    public final static String NPA_TARGET="core_sys/index.php/sjjk/npadb2";

    //public final static String LOGIN_VALIDATE_HTTP ="core_sys/index.php/login/logininfo";
    public final static String CMTS_ENTITY="core_sys/index.php//cm_monitor/cmtsjc_allinfo";
    public final static String DEVICE_CMTS="core_sys/index.php/cm_monitor/cmtsjcxx3";
    public final static String UPDATE_VERSION="AppVersion.xml";

}
