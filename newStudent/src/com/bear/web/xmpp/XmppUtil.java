package com.bear.web.xmpp;

import com.mindate.web.service.BaseService;

public class XmppUtil {

	public static String getJabberID(String from) {
		String[] res = from.split("/");
		return res[0].toLowerCase();
	}

	public static String uuidToJid(String uuid) {
		return uuid + BaseService.SERVICE_DOMIAN;
	}

}
