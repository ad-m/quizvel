package client.window;

public interface WindowObject<T> {
	public T getObject();

	public void updateObject();

	public void setStatus(boolean s);

	public boolean getStatus();

	public void dispose();

}