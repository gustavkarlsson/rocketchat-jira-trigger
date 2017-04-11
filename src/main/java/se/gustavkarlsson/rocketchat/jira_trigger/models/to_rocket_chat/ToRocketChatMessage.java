package se.gustavkarlsson.rocketchat.jira_trigger.models.to_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public final class ToRocketChatMessage {

	@SerializedName("username")
	private String username;

	@SerializedName("icon_url")
	private String iconUrl;

	@SerializedName("text")
	private String text;

	@SerializedName("attachments")
	private List<ToRocketChatAttachment> attachments;

	public ToRocketChatMessage() {
	}

	public ToRocketChatMessage(String username, String iconUrl, String text, List<ToRocketChatAttachment> attachments) {
		this.username = username;
		this.iconUrl = iconUrl;
		this.text = text;
		setAttachments(attachments);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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
		return Objects.equals(username, that.username) &&
				Objects.equals(iconUrl, that.iconUrl) &&
				Objects.equals(text, that.text) &&
				Objects.equals(attachments, that.attachments);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, iconUrl, text, attachments);
	}

	@Override
	public String toString() {
		return "ToRocketChatMessage{" +
				"username='" + username + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				", text='" + text + '\'' +
				", attachments=" + attachments +
				'}';
	}
}
