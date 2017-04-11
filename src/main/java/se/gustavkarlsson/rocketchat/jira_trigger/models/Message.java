package se.gustavkarlsson.rocketchat.jira_trigger.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public final class Message {

	@SerializedName("_id")
	private String id;

	@SerializedName("rid")
	private String rid;

	@SerializedName("ts")
	private Date ts;

	@SerializedName("msg")
	private String msg;

	@SerializedName("file")
	private File file;

	@SerializedName("groupable")
	private Boolean groupable;

	@SerializedName("attachments")
	private List<FromRocketChatAttachment> attachments;

	@SerializedName("u")
	private U u;

	@SerializedName("_updatedAt")
	private Date updatedAt;

	public Message() {
	}

	public Message(String id, String rid, Date ts, String msg, File file, Boolean groupable, List<FromRocketChatAttachment> attachments, U u, Date updatedAt) {
		this.id = id;
		this.rid = rid;
		this.ts = ts;
		this.msg = msg;
		this.file = file;
		this.groupable = groupable;
		this.attachments = attachments;
		this.u = u;
		this.updatedAt = updatedAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getGroupable() {
		return groupable;
	}

	public void setGroupable(Boolean groupable) {
		this.groupable = groupable;
	}

	public List<FromRocketChatAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<FromRocketChatAttachment> attachments) {
		this.attachments = attachments;
	}

	public U getU() {
		return u;
	}

	public void setU(U u) {
		this.u = u;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return Objects.equals(id, message.id) &&
				Objects.equals(rid, message.rid) &&
				Objects.equals(ts, message.ts) &&
				Objects.equals(msg, message.msg) &&
				Objects.equals(file, message.file) &&
				Objects.equals(groupable, message.groupable) &&
				Objects.equals(attachments, message.attachments) &&
				Objects.equals(u, message.u) &&
				Objects.equals(updatedAt, message.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, rid, ts, msg, file, groupable, attachments, u, updatedAt);
	}

	@Override
	public String toString() {
		return "Message{" +
				"id='" + id + '\'' +
				", rid='" + rid + '\'' +
				", ts=" + ts +
				", msg='" + msg + '\'' +
				", file=" + file +
				", groupable=" + groupable +
				", attachments=" + attachments +
				", u=" + u +
				", updatedAt=" + updatedAt +
				'}';
	}
}
