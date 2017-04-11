package se.gustavkarlsson.rocketchat.jira_trigger.models.from_rocket_chat;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Room {

	@SerializedName("name")
	private String name;

	@SerializedName("t")
	private String t;

	@SerializedName("msgs")
	private Long msgs;

	@SerializedName("u")
	private U u;

	@SerializedName("ts")
	private Date ts;

	@SerializedName("lm")
	private Date lm;

	@SerializedName("customFields")
	private Map<String, String> customFields;

	@SerializedName("ro")
	private Boolean ro;

	@SerializedName("sysMes")
	private Boolean sysMes;

	@SerializedName("default")
	private Boolean theDefault;

	@SerializedName("_updatedAt")
	private String updatedAt;

	@SerializedName("_id")
	private String id;

	@SerializedName("username")
	private String username;

	@SerializedName("archived")
	private Boolean archived;

	@SerializedName("usernames")
	private List<String> usernames;

	public Room() {
	}

	public Room(String name, String t, Long msgs, U u, Date ts, Date lm, Map<String, String> customFields, Boolean ro, Boolean sysMes, Boolean theDefault, String updatedAt, String id, String username, Boolean archived, List<String> usernames) {
		this.name = name;
		this.t = t;
		this.msgs = msgs;
		this.u = u;
		this.ts = ts;
		this.lm = lm;
		this.customFields = customFields;
		this.ro = ro;
		this.sysMes = sysMes;
		this.theDefault = theDefault;
		this.updatedAt = updatedAt;
		this.id = id;
		this.username = username;
		this.archived = archived;
		this.usernames = usernames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public Long getMsgs() {
		return msgs;
	}

	public void setMsgs(Long msgs) {
		this.msgs = msgs;
	}

	public U getU() {
		return u;
	}

	public void setU(U u) {
		this.u = u;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Date getLm() {
		return lm;
	}

	public void setLm(Date lm) {
		this.lm = lm;
	}

	public Map<String, String> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, String> customFields) {
		this.customFields = customFields;
	}

	public Boolean getRo() {
		return ro;
	}

	public void setRo(Boolean ro) {
		this.ro = ro;
	}

	public Boolean getSysMes() {
		return sysMes;
	}

	public void setSysMes(Boolean sysMes) {
		this.sysMes = sysMes;
	}

	public Boolean getTheDefault() {
		return theDefault;
	}

	public void setTheDefault(Boolean theDefault) {
		this.theDefault = theDefault;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
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

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Room room = (Room) o;
		return Objects.equals(name, room.name) &&
				Objects.equals(t, room.t) &&
				Objects.equals(msgs, room.msgs) &&
				Objects.equals(u, room.u) &&
				Objects.equals(ts, room.ts) &&
				Objects.equals(lm, room.lm) &&
				Objects.equals(customFields, room.customFields) &&
				Objects.equals(ro, room.ro) &&
				Objects.equals(sysMes, room.sysMes) &&
				Objects.equals(theDefault, room.theDefault) &&
				Objects.equals(updatedAt, room.updatedAt) &&
				Objects.equals(id, room.id) &&
				Objects.equals(username, room.username) &&
				Objects.equals(archived, room.archived) &&
				Objects.equals(usernames, room.usernames);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, t, msgs, u, ts, lm, customFields, ro, sysMes, theDefault, updatedAt, id, username, archived, usernames);
	}

	@Override
	public String toString() {
		return "Room{" +
				"name='" + name + '\'' +
				", t='" + t + '\'' +
				", msgs=" + msgs +
				", u=" + u +
				", ts=" + ts +
				", lm=" + lm +
				", customFields=" + customFields +
				", ro=" + ro +
				", sysMes=" + sysMes +
				", theDefault=" + theDefault +
				", updatedAt='" + updatedAt + '\'' +
				", id='" + id + '\'' +
				", username='" + username + '\'' +
				", archived=" + archived +
				", usernames=" + usernames +
				'}';
	}
}
