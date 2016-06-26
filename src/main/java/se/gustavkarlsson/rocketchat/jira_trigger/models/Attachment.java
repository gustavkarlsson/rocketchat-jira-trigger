package se.gustavkarlsson.rocketchat.jira_trigger.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Attachment {

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

	@SerializedName("pretext")
	private String pretext;

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

	public Attachment() {
	}

	public Attachment(String authorName, String authorLink, String authorIcon, String color, String title,
					  String titleLink, String pretext, String text, String imageUrl, String thumbUrl, Long timestamp,
					  List<Field> fields) {
		this.authorName = authorName;
		this.authorLink = authorLink;
		this.authorIcon = authorIcon;
		this.color = color;
		this.title = title;
		this.titleLink = titleLink;
		this.pretext = pretext;
		this.text = text;
		this.imageUrl = imageUrl;
		this.thumbUrl = thumbUrl;
		this.timestamp = timestamp;
		setFields(fields);
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

	public String getPretext() {
		return pretext;
	}

	public void setPretext(String pretext) {
		this.pretext = pretext;
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
		return new ArrayList<>(fields);
	}

	public void setFields(List<Field> fields) {
		this.fields = fields == null ? null : new ArrayList<>(fields);
	}

	public void addField(Field field) {
		if (fields == null) {
			fields = new ArrayList<>();
		}
		fields.add(field);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Attachment that = (Attachment) o;
		return Objects.equals(authorName, that.authorName) &&
				Objects.equals(authorLink, that.authorLink) &&
				Objects.equals(authorIcon, that.authorIcon) &&
				Objects.equals(color, that.color) &&
				Objects.equals(title, that.title) &&
				Objects.equals(titleLink, that.titleLink) &&
				Objects.equals(pretext, that.pretext) &&
				Objects.equals(text, that.text) &&
				Objects.equals(imageUrl, that.imageUrl) &&
				Objects.equals(thumbUrl, that.thumbUrl) &&
				Objects.equals(timestamp, that.timestamp) &&
				Objects.equals(fields, that.fields);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorName, authorLink, authorIcon, color, title, titleLink, pretext, text, imageUrl,
				thumbUrl, timestamp, fields);
	}

	@Override
	public String toString() {
		return "Attachment{" +
				"authorName='" + authorName + '\'' +
				", authorLink='" + authorLink + '\'' +
				", authorIcon='" + authorIcon + '\'' +
				", color='" + color + '\'' +
				", title='" + title + '\'' +
				", titleLink='" + titleLink + '\'' +
				", pretext='" + pretext + '\'' +
				", text='" + text + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", thumbUrl='" + thumbUrl + '\'' +
				", timestamp=" + timestamp +
				", fields=" + fields +
				'}';
	}
}
