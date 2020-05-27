package se.gustavkarlsson.rocketchat.models.from_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public final class FromRocketChatMessage {

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

	@SerializedName("bot")
	private Object bot;

	@SerializedName("isEdited")
	private Boolean edited;

	@SerializedName("alias")
	private String alias;

	@SerializedName("message_id")
	private String messageId;

	@SerializedName("timestamp")
	private Date timestamp;

	@SerializedName("trigger_word")
	private String triggerWord;

	public FromRocketChatMessage() {
	}

	public FromRocketChatMessage(String token, String channelId, String channelName, String userId, String userName, String text, Object bot, Boolean edited, String alias, String messageId, Date timestamp, String triggerWord) {
		this.token = token;
		this.channelId = channelId;
		this.channelName = channelName;
		this.userId = userId;
		this.userName = userName;
		this.text = text;
		this.bot = bot;
		this.edited = edited;
		this.alias = alias;
		this.messageId = messageId;
		this.timestamp = timestamp;
		this.triggerWord = triggerWord;
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

	public Object getBot() {
		return bot;
	}

	public void setBot(Object bot) {
		this.bot = bot;
	}

	public Boolean isEdited() {
		return edited;
	}

	public void setEdited(Boolean edited) {
		this.edited = edited;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getTriggerWord() {
		return triggerWord;
	}

	public void setTriggerWord(String triggerWord) {
		this.triggerWord = triggerWord;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FromRocketChatMessage that = (FromRocketChatMessage) o;
		return Objects.equals(token, that.token) &&
				Objects.equals(channelId, that.channelId) &&
				Objects.equals(channelName, that.channelName) &&
				Objects.equals(userId, that.userId) &&
				Objects.equals(userName, that.userName) &&
				Objects.equals(text, that.text) &&
				Objects.equals(bot, that.bot) &&
				Objects.equals(edited, that.edited) &&
				Objects.equals(alias, that.alias) &&
				Objects.equals(messageId, that.messageId) &&
				Objects.equals(timestamp, that.timestamp) &&
				Objects.equals(triggerWord, that.triggerWord);
	}

	@Override
	public int hashCode() {
		return Objects.hash(token, channelId, channelName, userId, userName, text, bot, edited, alias, messageId, timestamp, triggerWord);
	}

	@Override
	public String toString() {
		return "FromRocketChatMessage{" +
				"token='" + token + '\'' +
				", channelId='" + channelId + '\'' +
				", channelName='" + channelName + '\'' +
				", userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", text='" + text + '\'' +
				", bot=" + bot +
				", isEdited=" + edited +
				", alias='" + alias + '\'' +
				", messageId='" + messageId + '\'' +
				", timestamp=" + timestamp +
				", triggerWord='" + triggerWord + '\'' +
				'}';
	}
}
