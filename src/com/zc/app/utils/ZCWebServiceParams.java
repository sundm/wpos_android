package com.zc.app.utils;

public class ZCWebServiceParams {
	public static final int OK = 0x00000000;

	public static final int HTTP_SUCCESS = 0x90000000;
	public static final int HTTP_PURCHASE_SUCCESS = 0x80000000;
	public static final int HTTP_START = 0x90000001;
	public static final int HTTP_FINISH = 0x90000002;
	public static final int HTTP_FAILED = 0x90000003;
	public static final int HTTP_UNAUTH = 0x90000004;
	public static final int HTTP_THROWABLE = 0x90000009;

	public static final String BASE_SSL_URL = "https://192.168.2.68/wpos-front";

	public static final String BASE_URL = "http://192.168.2.69:8080/wpos-front";
	// https://222.84.136.83:8081/wpos-front/
	// "http://183.129.165.194:8088/wpos-front";
	// "http://192.168.2.69:8080/wpos-front";
	// "http://192.168.2.117:8088/wpos-front";
	public static final String RES_URL = "http://192.168.2.117:8080/resources-console/version";

	// 根据用户名,密码登录
	public static final String LOGIN_URL = BASE_URL
			+ "/j_spring_security_check";
	// 用户登出
	public static final String LOGOUT_URL = BASE_URL
			+ "/j_spring_security_logout";
	// 根据用户名，返回用户信息
	public static final String USERINFO_URL = BASE_URL + "/user/userinfo.json";
	// 注册用户信息，新增用户，用户名不能重复
	public static final String REGISTER_URL = BASE_URL + "/user/register.json";
	// 验证用户名唯一性
	public static final String REGISTER_USER_URL = BASE_URL
			+ "/user/isExistUsername.json";

	// 获取短信验证码
	public static final String REGISTER_CODE = BASE_URL
			+ "/user/registerCode.json";

	// 修改用户信息
	public static final String CHANGEUSER_URL = BASE_URL
			+ "/user/modifyinfo.json";
	// 更改密码
	public static final String MODIFYPASSWORD_URL = BASE_URL
			+ "/user/modifypassword.json";
	// 激活Pos，用户首次登陆后激活设备，由手机客户端发起
	public static final String ACTIVE_URL = BASE_URL + "/pos/active.json";
	// 终端信息查询接口,主要查询商户号、商户序列号等Pos信息
	public static final String QUERY_URL = BASE_URL + "/pos/query.json";
	// 申请验证码，手机客户端发起
	public static final String CHANGEBYPOS_URL = BASE_URL + "/pos/change.json";
	// 更换设备后验证Pos
	public static final String VALIDATEPOS_URL = BASE_URL
			+ "/pos/validate.json";

	// 获取POS信息
	public static final String GETPOSINFO_URL = BASE_URL
			+ "/purchase/init.json";
	// 上传消费初始化数据
	public static final String INITPURCHASE_URL = BASE_URL
			+ "/purchase/deal.json";
	// 上传交易结果
	public static final String UPDATEMAC2_URL = BASE_URL
			+ "/purchase/update.json";

	// 查询交易日志
	public static final String QUERY_LOG_URL = BASE_URL
			+ "/purchase/querypurchaselog.json";

	public static final String ZIP_URL = RES_URL + "/checkZip.json";
	public static final String ZIP_DOWNLOAD = RES_URL + "/hdp.zip/download.do";
	public static final String FILE_CHECK = RES_URL + "/checkFileMd5.json";
}