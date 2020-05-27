package se.gustavkarlsson.rocketchat.models.to_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public final class ToRocketChatMessage {

	@SerializedName("alias")
	private String alias;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("channel")
	private String channel;

	@SerializedName("emoji")
	private String emoji;

	@SerializedName("roomId")
	private String roomId;

	@SerializedName("text")
	private String text;

	@SerializedName("attachments")
	private List<ToRocketChatAttachment> attachments;

	public ToRocketChatMessage() {
	}

	public ToRocketChatMessage(String alias, String avatar, String channel, String emoji, String roomId, String text, List<ToRocketChatAttachment> attachments) {
		this.alias = alias;
		this.avatar = avatar;
		this.channel = channel;
		this.emoji = emoji;
		this.roomId = roomId;
		this.text = text;
		this.attachments = attachments;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getEmoji() {
		return emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ToRocketChatAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<ToRocketChatAttachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ToRocketChatMessage that = (ToRocketChatMessage) o;
		return Objects.equals(alias, that.alias) &&
				Objects.equals(avatar, that.avatar) &&
				Objects.equals(channel, that.channel) &&
				Objects.equals(emoji, that.emoji) &&
				Objects.equals(roomId, that.roomId) &&
				Objects.equals(text, that.text) &&
				Objects.equals(attachments, that.attachments);
	}

	@Override
	public int hashCode() {
		return Objects.hash(alias, avatar, channel, emoji, roomId, text, attachments);
	}

	@Override
	public String toString() {
		return "ToRocketChatMessage{" +
				"alias='" + alias + '\'' +
				", avatar='" + avatar + '\'' +
				", channel='" + channel + '\'' +
				", emoji='" + emoji + '\'' +
				", roomId='" + roomId + '\'' +
				", text='" + text + '\'' +
				", attachments=" + attachments +
				'}';
	}
}
