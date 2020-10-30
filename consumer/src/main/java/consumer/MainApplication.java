package consumer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import project.queue.Event;
import project.queue.PollingException;
import project.queue.Queue;
import project.queue.QueueConsumable;
import project.queue.QueueConsumer;

public class MainApplication {

	public static void main(String[] args) {
		Queue<Event> mainQueue = new QueueMock();

		QueueConsumable myComponent = new QueueConsumer(mainQueue, (event) -> event.type.equals("addon"),
				MainApplication::presentEvent, (exc) -> System.out.println(exc.getMessage()));

		for (int i = 0; i < 5; i++) {
			myComponent.next();
		}

	}

	private static void presentEvent(Event event) {
		System.out.println(event.message);
		event.fields.entrySet().forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));
	}

	private static class QueueMock implements Queue<Event> {
		Map<String, String> fields1 = new HashMap<String, String>();
		Map<String, String> fields2 = new HashMap<String, String>();
		Map<String, String> fields3 = new HashMap<String, String>();

		private List<Event> events = new LinkedList<Event>(Arrays.asList(
				new Event("1", "account", fields1, "User created"), new Event("2", "account", fields2, "User updated"),
				new Event("3", "addon", fields3, "Addon trial started"),
				new Event("4", "addon", fields3, "Addon trial expired")));
		int index = 0;

		boolean throwException = false;

		public QueueMock() {
			super();
			this.fields3.put("addonName", "API security");
		}

		public Event poll(int timeout) throws PollingException {
			if (this.throwException) {
				throw new PollingException("");
			} else {
				return this.events.get(index++ % this.events.size());
			}
		}
	}

}
