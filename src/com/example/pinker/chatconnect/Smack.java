package com.example.pinker.chatconnect;

public interface Smack {
	public boolean login(String account, String password) throws ChatConnectException;

	public boolean logout();

	public boolean isAuthenticated();

	public void addRosterItem(String user, String alias, String group)
			throws ChatConnectException;

	public void removeRosterItem(String user) throws ChatConnectException;

	public void renameRosterItem(String user, String newName)
			throws ChatConnectException;

	public void moveRosterItemToGroup(String user, String group)
			throws ChatConnectException;

	public void renameRosterGroup(String group, String newGroup);

	public void requestAuthorizationForRosterItem(String user);

	public void addRosterGroup(String group);

	public void setStatusFromConfig();

	public void sendMessage(String user, String message);

	public void sendServerPing();

	public String getNameForJID(String jid);
}
