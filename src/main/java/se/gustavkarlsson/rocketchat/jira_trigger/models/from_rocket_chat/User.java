package se.gustavkarlsson.rocketchat.jira_trigger.models.from_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public final class User {

	@SerializedName("_id")
	private String id;

	@SerializedName("createdAt")
	private Date createdAt;

	@SerializedName("emails")
	private List<Email> emails;

	@SerializedName("type")
	private String type;

	@SerializedName("status")
	private String status;

	@SerializedName("statusDefault")
	private String statusDefault;

	@SerializedName("active")
	private Boolean active;

	@SerializedName("name")
	private String name;

	@SerializedName("_updatedAt")
	private Date updatedAt;

	@SerializedName("roles")
	private List<String> roles;

	@SerializedName("lastLogin")
	private Date lastLogin;

	@SerializedName("statusConnection")
	private String statusConnection;

	@SerializedName("utcOffset")
	private Long utcOffset;

	@SerializedName("username")
	private String username;

	@SerializedName("avatarOrigin")
	private String avatarOrigin;

	public User() {
	}

	public User(String id, Date createdAt, List<Email> emails, String type, String status, String statusDefault, Boolean active, String name, Date updatedAt, List<String> roles, Date lastLogin, String statusConnection, Long utcOffset, String username, String avatarOrigin) {
		this.id = id;
		this.createdAt = createdAt;
		this.emails = emails;
		this.type = type;
		this.status = status;
		this.statusDefault = statusDefault;
		this.active = active;
		this.name = name;
		this.updatedAt = updatedAt;
		this.roles = roles;
		this.lastLogin = lastLogin;
		this.statusConnection = statusConnection;
		this.utcOffset = utcOffset;
		this.username = username;
		this.avatarOrigin = avatarOrigin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDefault() {
		return statusDefault;
	}

	public void setStatusDefault(String statusDefault) {
		this.statusDefault = statusDefault;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getStatusConnection() {
		return statusConnection;
	}

	public void setStatusConnection(String statusConnection) {
		this.statusConnection = statusConnection;
	}

	public Long getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(Long utcOffset) {
		this.utcOffset = utcOffset;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatarOrigin() {
		return avatarOrigin;
	}

	public void setAvatarOrigin(String avatarOrigin) {
		this.avatarOrigin = avatarOrigin;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(createdAt, user.createdAt) &&
				Objects.equals(emails, user.emails) &&
				Objects.equals(type, user.type) &&
				Objects.equals(status, user.status) &&
				Objects.equals(statusDefault, user.statusDefault) &&
				Objects.equals(active, user.active) &&
				Objects.equals(name, user.name) &&
				Objects.equals(updatedAt, user.updatedAt) &&
				Objects.equals(roles, user.roles) &&
				Objects.equals(lastLogin, user.lastLogin) &&
				Objects.equals(statusConnection, user.statusConnection) &&
				Objects.equals(utcOffset, user.utcOffset) &&
				Objects.equals(username, user.username) &&
				Objects.equals(avatarOrigin, user.avatarOrigin);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, createdAt, emails, type, status, statusDefault, active, name, updatedAt, roles, lastLogin, statusConnection, utcOffset, username, avatarOrigin);
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", createdAt=" + createdAt +
				", emails=" + emails +
				", type='" + type + '\'' +
				", status='" + status + '\'' +
				", statusDefault='" + statusDefault + '\'' +
				", active=" + active +
				", name='" + name + '\'' +
				", updatedAt=" + updatedAt +
				", roles=" + roles +
				", lastLogin=" + lastLogin +
				", statusConnection='" + statusConnection + '\'' +
				", utcOffset=" + utcOffset +
				", username='" + username + '\'' +
				", avatarOrigin='" + avatarOrigin + '\'' +
				'}';
	}
}
