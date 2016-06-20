package se.gustavkarlsson.rocketchat.jira_trigger.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attachment {

	@SerializedName("color")
	private String color;

	@SerializedName("pretext")
	private String pretext;

	@SerializedName("author_name")
	private String authorName;

	@SerializedName("author_link")
	private String authorLink;

	@SerializedName("author_icon")
	private String authorIcon;

	@SerializedName("title")
	private String title;

	@SerializedName("title_link")
	private String titleLink;

	@SerializedName("text")
	private String text;

	@SerializedName("fields")
	private List<Field> fields;

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("thumb_url")
	private String thumbUrl;

	@SerializedName("ts")
	private Long timestamp;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPretext() {
		return pretext;
	}

	public void setPretext(String pretext) {
		this.pretext = pretext;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
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


}
