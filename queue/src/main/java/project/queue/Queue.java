package project.queue;

public interface Queue<V> {
    V poll (int timeout) throws PollingException;
}
