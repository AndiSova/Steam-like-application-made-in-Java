package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ApplicationMessage")
@NamedQueries(value = {
		@NamedQuery(name = Message.GET_RECEIVED_MESSAGES, query = "from Message where receiver = :receiver"), //
		@NamedQuery(name = Message.GET_SENT_MESSAGES, query = "from Message where sender = :sender") })
public class Message implements Serializable, Model, Columns {

	public static final String GET_RECEIVED_MESSAGES = "Messages.by.receiver";
	public static final String GET_SENT_MESSAGES = "Messages.by.sender";
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;

	@Lob
	private String message;

	public Message(User sender, User receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	public Message() {
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String[] getData() {
		return new String[] { "from " + sender.getUserName(), message };
	}

	@Override
	public String[] getColumns() {
		return new String[] { "Sender", "Message" };
	}

}
