package com.tamguo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Setting - 系统
 * 
 * @author candy.tam
 *
 */
@Component
public final class Setting {

	/** 域名 */
	@Value(value="${domain.name}")
	public String domain;
	
	/**站点编号 **/
	@Value(value="${sitenum}")
	private String sitenum;
	
	public String getSitenum() {
		return sitenum;
	}
	public void setSitenum(String sitenum) {
		this.sitenum = sitenum;
	}
	
}
