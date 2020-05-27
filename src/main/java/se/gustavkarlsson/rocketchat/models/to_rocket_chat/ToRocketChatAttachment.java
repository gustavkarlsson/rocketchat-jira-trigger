package se.gustavkarlsson.rocketchat.models.to_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public final class ToRocketChatAttachment {

	@SerializedName("author_name")
	private String authorName;

	@SerializedName("author_link")
	private String authorLink;

	@SerializedName("author_icon")
	private String authorIcon;

	@SerializedName("color")
	private String color;

	@SerializedName("title")
	private String title;

	@SerializedName("title_link")
	private String titleLink;

	@SerializedName("title_link_download")
	private Boolean titleLinkDownload;

	@SerializedName("text")
	private String text;

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("thumb_url")
	private String thumbUrl;

	@SerializedName("ts")
	private Long timestamp;

	@SerializedName("fields")
	private List<Field> fields;

	@SerializedName("message_link")
	private String messageLink;

	@SerializedName("collapsed")
	private Boolean collapsed;

	@SerializedName("audio_url")
	private String audioUrl;

	@SerializedName("video_url")
	private String videoUrl;

	public ToRocketChatAttachment() {
	}

	public ToRocketChatAttachment(String authorName, String authorLink, String authorIcon, String color, String title, String titleLink, Boolean titleLinkDownload, String text, String imageUrl, String thumbUrl, Long timestamp, List<Field> fields, String messageLink, Boolean collapsed, String audioUrl, String videoUrl) {
		this.authorName = authorName;
		this.authorLink = authorLink;
		this.authorIcon = authorIcon;
		this.color = color;
		this.title = title;
		this.titleLink = titleLink;
		this.titleLinkDownload = titleLinkDownload;
		this.text = text;
		this.imageUrl = imageUrl;
		this.thumbUrl = thumbUrl;
		this.timestamp = timestamp;
		this.fields = fields;
		this.messageLink = messageLink;
		this.collapsed = collapsed;
		this.audioUrl = audioUrl;
		this.videoUrl = videoUrl;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorLink() {
		return authorLink;
	}

	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink;
	}

	public String getAuthorIcon() {
		return authorIcon;
	}

	public void setAuthorIcon(String authorIcon) {
		this.authorIcon = authorIcon;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getMessageLink() {
		return messageLink;
	}

	public void setMessageLink(String messageLink) {
		this.messageLink = messageLink;
	}

	public Boolean getCollapsed() {
		return collapsed;
	}

	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ToRocketChatAttachment that = (ToRocketChatAttachment) o;
		return Objects.equals(authorName, that.authorName) &&
				Objects.equals(authorLink, that.authorLink) &&
				Objects.equals(authorIcon, that.authorIcon) &&
				Objects.equals(color, that.color) &&
				Objects.equals(title, that.title) &&
				Objects.equals(titleLink, that.titleLink) &&
				Objects.equals(titleLinkDownload, that.titleLinkDownload) &&
				Objects.equals(text, that.text) &&
				Objects.equals(imageUrl, that.imageUrl) &&
				Objects.equals(thumbUrl, that.thumbUrl) &&
				Objects.equals(timestamp, that.timestamp) &&
				Objects.equals(fields, that.fields) &&
				Objects.equals(messageLink, that.messageLink) &&
				Objects.equals(collapsed, that.collapsed) &&
				Objects.equals(audioUrl, that.audioUrl) &&
				Objects.equals(videoUrl, that.videoUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorName, authorLink, authorIcon, color, title, titleLink, titleLinkDownload, text, imageUrl, thumbUrl, timestamp, fields, messageLink, collapsed, audioUrl, videoUrl);
	}

	@Override
	public String toString() {
		return "ToRocketChatAttachment{" +
				"authorName='" + authorName + '\'' +
				", authorLink='" + authorLink + '\'' +
				", authorIcon='" + authorIcon + '\'' +
				", color='" + color + '\'' +
				", title='" + title + '\'' +
				", titleLink='" + titleLink + '\'' +
				", titleLinkDownload='" + titleLinkDownload + '\'' +
				", text='" + text + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", thumbUrl='" + thumbUrl + '\'' +
				", timestamp=" + timestamp +
				", fields=" + fields +
				", messageLink='" + messageLink + '\'' +
				", collapsed=" + collapsed +
				", audioUrl='" + audioUrl + '\'' +
				", videoUrl='" + videoUrl + '\'' +
				'}';
	}
}
