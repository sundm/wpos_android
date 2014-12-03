package com.zc.app.mpos.util;

public enum posStatus {
	// 新建，未开通
	CREATED,
	// 开通，但未签到，暂时不可用
	ACTIVE_UNAVAILABLE,
	// 开通，正常使用
	ACTIVE,
	// 冻结
	FROZEN,
	// 注销
	DESTROYED;
}
