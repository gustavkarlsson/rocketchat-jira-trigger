package se.gustavkarlsson.rocketchat.jira_trigger.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public final class OutgoingMessage {

	@SerializedName("token")
	private String token;

	@SerializedName("channel_id")
	private String channelId;

	@SerializedName("channel_name")
	private String channelName;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("text")
	private String text;

	@SerializedName("timestamp")
	private Date timestamp;

	public OutgoingMessage() {
	}

	public OutgoingMessage(String token, String channelId, String channelName, String userId, String userName,
						   String text, Date timestamp) {
		this.token = token;
		this.channelId = channelId;
		this.channelName = channelName;
		this.userId = userId;
		this.userName = userName;
		this.text = text;
		setTimestamp(timestamp);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTimestamp() {
		return new Date(timestamp.getTime());
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp == null ? null : new Date(timestamp.getTime());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OutgoingMessage that = (OutgoingMessage) o;
		return Objects.equals(token, that.token) &&
				Objects.equals(channelId, that.channelId) &&
				Objects.equals(channelName, that.channelName) &&
				Objects.equals(userId, that.userId) &&
				Objects.equals(userName, that.userName) &&
				Objects.equals(text, that.text) &&
				Objects.equals(timestamp, that.timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(token, channelId, channelName, userId, userName, text, timestamp);
	}

	@Override
	public String toString() {
		return "OutgoingMessage{" +
				"token='" + token + '\'' +
				", channelId='" + channelId + '\'' +
				", channelName='" + channelName + '\'' +
				", userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", text='" + text + '\'' +
				", timestamp=" + timestamp +
				'}';
	}
}
