package se.gustavkarlsson.rocketchat.jira_trigger.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncomingMessage {

	@SerializedName("username")
	private String username;

	@SerializedName("icon_url")
	private String iconUrl;

	@SerializedName("text")
	private String text;

	@SerializedName("attachments")
	private List<Attachment> attachments;

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
}
