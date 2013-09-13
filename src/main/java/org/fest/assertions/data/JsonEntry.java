package org.fest.assertions.data;

public class JsonEntry {

	private MapEntry entry;

	/**
	 * Creates a new {@link JsonEntry}.
	 *
	 * @param key the key of the entry to create.
	 * @param value the value of the entry to create.
	 * @return the created {@code JsonEntry}.
	 */
	public static JsonEntry entry(String key, Object value) {
		MapEntry mapEntry = MapEntry.entry(key, value);
		return new JsonEntry(mapEntry);
	}

	private JsonEntry(MapEntry entry) {
		this.entry = entry;
	}

	public String key() {
		return (String) entry.key;
	}

	public Object value() {
		return entry.value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof JsonEntry) {
			JsonEntry jsonEntry = (JsonEntry) o;
			return jsonEntry.entry.equals(entry);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return entry.hashCode();
	}

	@Override
	public String toString() {
		return entry.toString();
	}
}
