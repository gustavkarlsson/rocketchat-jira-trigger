package se.gustavkarlsson.rocketchat.jira_trigger.models.from_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class File {

	@SerializedName("_id")
	private String id;

	@SerializedName("name")
	private String name;

	public File() {
	}

	public File(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		File file = (File) o;
		return Objects.equals(id, file.id) &&
				Objects.equals(name, file.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "File{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
