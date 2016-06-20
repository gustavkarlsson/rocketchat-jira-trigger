package se.gustavkarlsson.rocketchat.jira_trigger.models;

import com.google.gson.annotations.SerializedName;

public class Field {

	@SerializedName("title")
	private String title;

	@SerializedName("value")
	private String value;

	@SerializedName("short")
	private boolean aShort;

	public Field(String title, String value, boolean aShort) {
		this.title = title;
		this.value = value;
		this.aShort = aShort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isAShort() {
		return aShort;
	}

	public void setAShort(boolean aShort) {
		this.aShort = aShort;
	}
}
