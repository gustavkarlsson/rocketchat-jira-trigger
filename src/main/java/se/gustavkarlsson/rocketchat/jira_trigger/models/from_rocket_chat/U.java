package se.gustavkarlsson.rocketchat.jira_trigger.models.from_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class U {

	@SerializedName("_id")
	private String id;

	@SerializedName("username")
	private String username;

	public U() {
	}

	public U(String id, String username) {
		this.id = id;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		U u = (U) o;
		return Objects.equals(id, u.id) &&
				Objects.equals(username, u.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}

	@Override
	public String toString() {
		return "U{" +
				"id='" + id + '\'' +
				", username='" + username + '\'' +
				'}';
	}
}
