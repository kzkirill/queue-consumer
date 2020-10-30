package project.queue;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class QueueConsumer implements QueueConsumable {
	private Queue<Event> queue;
	private Predicate<Event> isMyEvent;
	private Consumer<Event> eventConsumer;
	private Consumer<Exception> onException;

	public QueueConsumer(Queue<Event> queue, Predicate<Event> isMyEvent, Consumer<Event> eventConsumer,
			Consumer<Exception> onException) {
		super();
		this.queue = queue;
		this.isMyEvent = isMyEvent;
		this.eventConsumer = eventConsumer;
		this.onException = onException;
	}

	@Override
	public void next() {
		try {
			Event current = this.queue.poll(1000);
			if (this.isMyEvent.test(current)) {
				this.eventConsumer.accept(current);
			}
		} catch (PollingException e) {
			this.onException.accept(e);
		}
	}
}
