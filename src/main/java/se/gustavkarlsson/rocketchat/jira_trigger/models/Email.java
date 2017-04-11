package se.gustavkarlsson.rocketchat.jira_trigger.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class Email {

	@SerializedName("address")
	private String address;

	@SerializedName("verified")
	private Boolean verified;

	public Email() {
	}

	public Email(String address, Boolean verified) {
		this.address = address;
		this.verified = verified;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Email email = (Email) o;
		return Objects.equals(address, email.address) &&
				Objects.equals(verified, email.verified);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, verified);
	}

	@Override
	public String toString() {
		return "Email{" +
				"address='" + address + '\'' +
				", verified=" + verified +
				'}';
	}
}
