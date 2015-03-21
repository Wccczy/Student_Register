package com.bear.web.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import javax.net.ssl.SSLContext;

public class XMPPManager {

	private static final String USERNAME = "admin";

	private static final String PASSWORD = "admin";

	public static XMPPTCPConnection con;

	public static XMPPTCPConnection getAdminConnection() {
		if (con == null || !con.isConnected()) {
			try {
				ConnectionConfiguration config = new ConnectionConfiguration("www.geetion.com", 5222);
				config.setCustomSSLContext(trustAllHttpsCertificates());
				con = new XMPPTCPConnection(config);
				con.connect();
				con.login(USERNAME, PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Authenticated = " + con.isAuthenticated());
		}
		return con;
	}

	// private static void addAdminListener() {
	// PacketFilter filterMessage = new PacketTypeFilter(Message.class);
	// PacketListener myListener = new PacketListener() {
	// public void processPacket(Packet packet) {
	// Message message = new Message();
	// message.setFrom("sender@192.168.1.199");
	// String toJid = XmppUtil.getJabberID(packet.getFrom());
	// message.setTo(toJid);
	// message.setBody("hello");
	// try {
	// con.sendPacket(message);
	// } catch (NotConnectedException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// };
	// con.addPacketListener(myListener, filterMessage);
	// }

	public static void sendAdminMsg(Message message) {
		try {
			getAdminConnection();
			con.sendPacket(message);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

	public static void closeAdminConn() {
		if (con != null)
			try {
				con.disconnect();
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}
	}

	private static SSLContext trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		return sc;
	}

	static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
			return;
		}
	}
}
