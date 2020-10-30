package project.queue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Test;

public class QueueConsumerTest {
	List<Event> consumedAccount = new LinkedList<>();
	List<Event> consumedAddon = new LinkedList<>();
	boolean exceptionConsumed = false;

	@Test
	public void test() {
		QueueMock queue = new QueueMock();
		Consumer<Exception> onException = (e) -> this.exceptionConsumed = true;
		QueueConsumable testeeAccount = new QueueConsumer(queue, (event) -> event.type.equals("account"),
				(event) -> this.consumedAccount.add(event), onException);
		for (int i = 0; i < 3; i++) {
			testeeAccount.next();
		}
		assertTrue(this.consumedAccount.size() == 2);
		QueueConsumable testeeAddon = new QueueConsumer(queue, (event) -> event.type.equals("addon"),
				(event) -> this.consumedAddon.add(event), onException);
		for (int i = 0; i < 3; i++) {
			testeeAddon.next();
		}
		assertTrue(this.consumedAddon.size() == 1);

		assertFalse(this.exceptionConsumed);
		queue.throwException = true;
		testeeAddon.next();
		assertTrue(this.exceptionConsumed);
	}

	private static class QueueMock implements Queue<Event> {
		Map<String, String> fields1 = new HashMap<String, String>();
		Map<String, String> fields2 = new HashMap<String, String>();
		Map<String, String> fields3 = new HashMap<String, String>();

		private List<Event> events = new LinkedList<Event>(Arrays.asList(
				new Event("1", "account", fields1, "User created"), new Event("2", "account", fields2, "User updated"),
				new Event("3", "addon", fields3, "Addon trial expired")));
		int index = 0;

		boolean throwException = false;

		public Event poll(int timeout) throws PollingException {
			if (this.throwException) {
				throw new PollingException("");
			} else {
				return this.events.get(index++ % this.events.size());
			}
		}

	}

}
