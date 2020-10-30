package project.queue;

import java.util.Map;

public class Event {
	public final String ID;
	public final String type;
	public final Map<String, String> fields;
	public final String message;

	public Event(String iD, String type, Map<String, String> fields, String message) {
		super();
		this.ID = iD;
		this.type = type;
		this.fields = fields;
		this.message = message;
	}
}
