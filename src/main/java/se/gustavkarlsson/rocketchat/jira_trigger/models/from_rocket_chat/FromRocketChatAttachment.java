package se.gustavkarlsson.rocketchat.jira_trigger.models.from_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class FromRocketChatAttachment {

	@SerializedName("title")
	private String title;

	@SerializedName("description")
	private String description;

	@SerializedName("title_link")
	private String titleLink;

	@SerializedName("title_link_download")
	private Boolean titleLinkDownload;

	public FromRocketChatAttachment() {
	}

	public FromRocketChatAttachment(String title, String description, String titleLink, Boolean titleLinkDownload) {
		this.title = title;
		this.description = description;
		this.titleLink = titleLink;
		this.titleLinkDownload = titleLinkDownload;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitleLink() {
		return titleLink;
	}

	public void setTitleLink(String titleLink) {
		this.titleLink = titleLink;
	}

	public Boolean getTitleLinkDownload() {
		return titleLinkDownload;
	}

	public void setTitleLinkDownload(Boolean titleLinkDownload) {
		this.titleLinkDownload = titleLinkDownload;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FromRocketChatAttachment that = (FromRocketChatAttachment) o;
		return Objects.equals(title, that.title) &&
				Objects.equals(description, that.description) &&
				Objects.equals(titleLink, that.titleLink) &&
				Objects.equals(titleLinkDownload, that.titleLinkDownload);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, description, titleLink, titleLinkDownload);
	}

	@Override
	public String toString() {
		return "FromRocketChatAttachment{" +
				"title='" + title + '\'' +
				", description='" + description + '\'' +
				", titleLink='" + titleLink + '\'' +
				", titleLinkDownload=" + titleLinkDownload +
				'}';
	}
}
