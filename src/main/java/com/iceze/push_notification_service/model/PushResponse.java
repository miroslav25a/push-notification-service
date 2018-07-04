package com.iceze.push_notification_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushResponse {
	private String active;
	private String body;
	private String created;
	private String direction;
	private String dismissed;
	private String iden;
	private String modified;
	@JsonProperty("receiver_email")
	private String receiverEmail;
	@JsonProperty("receiver_email_normalized")
	private String receiverEmailNormalized;
	@JsonProperty("receiver_iden")
	private String receiverIden;
	@JsonProperty("sender_email")
	private String senderEmail;
	@JsonProperty("sender_email_normalized")
	private String senderEmailNormalized;
	@JsonProperty("sender_iden")
	private String senderIden;
	@JsonProperty("sender_name")
	private String senderName;
	private String title;
	private String type;
	
	public PushResponse() {
		super();
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDismissed() {
		return dismissed;
	}

	public void setDismissed(String dismissed) {
		this.dismissed = dismissed;
	}

	public String getIden() {
		return iden;
	}

	public void setIden(String iden) {
		this.iden = iden;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getReceiverEmailNormalized() {
		return receiverEmailNormalized;
	}

	public void setReceiverEmailNormalized(String receiverEmailNormalized) {
		this.receiverEmailNormalized = receiverEmailNormalized;
	}

	public String getReceiverIden() {
		return receiverIden;
	}

	public void setReceiverIden(String receiverIden) {
		this.receiverIden = receiverIden;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderEmailNormalized() {
		return senderEmailNormalized;
	}

	public void setSenderEmailNormalized(String senderEmailNormalized) {
		this.senderEmailNormalized = senderEmailNormalized;
	}

	public String getSenderIden() {
		return senderIden;
	}

	public void setSenderIden(String senderIden) {
		this.senderIden = senderIden;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
