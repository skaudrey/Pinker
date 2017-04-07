package com.example.pinker.chatconnect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;

import com.example.pinker.chatinfo.MyChatManagerListener;

import android.content.Context;
import android.util.Log;

public class ClientConServer {

	private static String IP= "192.168.23.4";
	private static int PORT = 5222;
    private static String SERVER_NAME = "odile.openfire.com";
	private static ConnectionConfiguration config;
	private static XMPPConnection connection;
	private static List<String> groups;
	private static List<List<String>> friends;
	private static ChatManager cm;

	public static boolean login(Context context, String a, String p) {
		config = new ConnectionConfiguration(IP, PORT);

		config.setReconnectionAllowed(true);
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		config.setSASLAuthenticationEnabled(true);
		config.setTruststorePath("/system/etc/security/cacerts.bks");
		config.setTruststorePassword("changeit");
		config.setTruststoreType("bks");

		/** �Ƿ����ð�ȫ��֤ */
		// config.setSASLAuthenticationEnabled(false);
		/** �Ƿ����õ��� */
		// config.setDebuggerEnabled(true);
		/** ����connection���� */
		connection = new XMPPConnection(config);
		try {
			/** �������� */
			connection.connect();
			/** ��¼ */
			connection.login(a, p);
			/**
			 * �����û�״̬��available=true��ʾ���ߣ�false��ʾ���ߣ�status״̬ǩ���������½��
			 * ��Spark�ͻ�������оͿ��Կ������½��״̬
			 */
			Presence presence = new Presence(Presence.Type.available);
			presence.setStatus("available");
			connection.sendPacket(presence);

			// ����Ϣ�����¼�
			cm = connection.getChatManager();
			cm.addChatListener(new MyChatManagerListener(context));

			/** ������д�̣߳������뵽�������� */
			// ��ȡ�����б�
			Roster roster = connection.getRoster();
			Collection<RosterGroup> entriesGroup = roster.getGroups();
			groups = new ArrayList<String>();
			for (RosterGroup group : entriesGroup) {
				Collection<RosterEntry> entries = group.getEntries();
				groups.add(group.getName());
				friends = new ArrayList<List<String>>();
				List<String> list = new ArrayList<String>();
				for (RosterEntry entry : entries) {
					Presence pre = roster.getPresence(entry.getUser());
					// �жϺ����Ƿ�����
					if (pre.isAvailable()) {
						Log.e("e", entry.getName());
					}
					list.add(entry.getName());
				}
				friends.add(list);
			}
			
			return true;
		} catch (XMPPException e) {
			Log.e("``````````", "����ʧ��");
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			Log.e("-----------", "Not connected to server.");
			return false;
		}
	}

	public static XMPPConnection getConnection() throws XMPPException {
		if (connection == null) {
			ConnectionConfiguration connConfig = new ConnectionConfiguration(
					IP, PORT);
			connection = new XMPPConnection(connConfig);
			connection.connect();
		}
		return connection;
	}

	public static void closeConnection() {
		connection.disconnect();
		connection = null;
	}

	public static List<String> getGroups() {
		if (groups != null)
			return groups;
		return null;
	}

	public static List<List<String>> getFriends() {
		if (friends != null)
			return friends;
		return null;
	}

	public static ChatManager getChatManager() {
		if (cm != null)
			return cm;
		return null;
	}

	 //�õ��������״̬�����ߣ�����
    public static String getPresence(String username){
        String strURL
          ="http://"+IP+":9090/plugins/presence/status?jid="
            +username+"@"+SERVER_NAME+"&type=xml";
        try {
         URL oUrl = new URL(strURL);
         URLConnection oConn = oUrl.openConnection();
         if (oConn != null) {
             BufferedReader oIn = new BufferedReader
            		    (new InputStreamReader(oConn.getInputStream()));
             if (null != oIn) {
                 String strFlag = oIn.readLine();
                 oIn.close();
                 if (strFlag.indexOf("<status>available</status>") >= 0) {
                     return "����";
                 }
             }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
       return "����";
	}
    
    //��Ӻ���(�޷���) 
    public static boolean addUser(String userName, String name) {  
        if (connection == null) {
            return false; 
        } 
        try {  
        	connection.getRoster().createEntry(userName, name, null);  
            return true;
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

}
