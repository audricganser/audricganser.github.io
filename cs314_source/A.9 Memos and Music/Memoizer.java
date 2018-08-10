import java.util.HashMap;

public class Memoizer {
	private HashMap<Object, Object> map;
	private Functor functor;
	public Memoizer (Functor f) {
		map = new HashMap<Object, Object>();
		functor = f;
	}
	public Object call (Object x) {
		Object value = map.get(x);
		if (value != null) {
			return value;
		}
		value = functor.fn(x);
		map.put(x, value);
		return value;
	}
}
