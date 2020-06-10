package se.gustavkarlsson.rocketchat.models.to_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class Field {

	@SerializedName("title")
	private String title;

	@SerializedName("value")
	private String value;

	@SerializedName("short")
	private Boolean shortValue;

	public Field() {
	}

	public Field(String title, String value, boolean shortValue) {
		this.title = title;
		this.value = value;
		this.shortValue = shortValue;
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

	public Boolean isAShort() {
		return shortValue;
	}

	public void setAShort(Boolean aShort) {
		this.shortValue = aShort;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Field field = (Field) o;
		return Objects.equals(title, field.title) &&
				Objects.equals(value, field.value) &&
				Objects.equals(shortValue, field.shortValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, value, shortValue);
	}

	@Override
	public String toString() {
		return "Field{" +
				"title='" + title + '\'' +
				", value='" + value + '\'' +
				", shortValue=" + shortValue +
				'}';
	}
}
