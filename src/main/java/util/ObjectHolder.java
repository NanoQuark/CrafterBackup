package util;

public class ObjectHolder<T> {

	private T object;
	
	public ObjectHolder(T object) {
		this.object = object;
	}
	
	public ObjectHolder() {
		this.object = null;
	}
	
	public void set(T object) {
		this.object = object;
	}
	
	public T get() {
		return object;
	}
	
}