package se.gustavkarlsson.rocketchat.jira_trigger.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class IncomingMessage {

	@SerializedName("username")
	private String username;

	@SerializedName("icon_url")
	private String iconUrl;

	@SerializedName("text")
	private String text;

	@SerializedName("attachments")
	private List<Attachment> attachments;

	public IncomingMessage() {
	}

	public IncomingMessage(String username, String iconUrl, String text, List<Attachment> attachments) {
		this.username = username;
		this.iconUrl = iconUrl;
		this.text = text;
		this.attachments = attachments;
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

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(Attachment attachment) {
		if (attachments == null) {
			attachments = new ArrayList<>();
		}
		attachments.add(attachment);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IncomingMessage that = (IncomingMessage) o;
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
		return "IncomingMessage{" +
				"username='" + username + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				", text='" + text + '\'' +
				", attachments=" + attachments +
				'}';
	}
}
