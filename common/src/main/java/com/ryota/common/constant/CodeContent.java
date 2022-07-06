package com.ryota.common.constant;

/**
 * 常量接口
 *
 * @author jzy
 */
public interface CodeContent {


    //通用数字
    public final static Integer COMMON_ZERO = 0;
    public final static Integer COMMON_ONE = 1;
    public final static Integer COMMON_MINUS_ONE = -1;

    //csp租户名
    public final static String CSP_TENTID = "Cnooc-hf";

    //接口访问前缀
    public final static String API_PATH = "/csp/schedule";


    //缓存前缀
    public final static String DISTRICT ="csp_district:";   //当前登录人
    public final static String DICT ="csp_dict:";       //字典
    public final static String CURRENT_USER ="csp_current_user:";       //当前登录用户
    public final static String LPG_USER_INFO ="csp_user_info:";       //当前登录用户

    //时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //时间格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    //单位id
    public static final String ORG_OFFICE = "1351047258731016193";
    public static final String ORG_LEADER ="1351047258785542145";

    //日程活动类型
    public static final String SCH_OFFICE = "1"; //办公室活动
    public static final String SCH_LEADER= "2";  //领导活动

    //csp系统管理员code
    public static final String CSP_ADMIN_CODE= "csp-admin";

    //csp领导日程管理员
    public static final String CSP_LEADER_SCHEDULE_ADMIN_CODE= "csp-m-oa-leaderschedule-admin";


}
