import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class will represent a modified linear probing hash table. The
 * modification is specified in the comments for the put method.
 */
public class HashTable<K, V> {

	/**
	 * Constant determining the max load factor
	 */
	private final double MAX_LOAD_FACTOR = 0.71;

	/**
	 * Constant determining the initial table size
	 */
	private final int INITIAL_TABLE_SIZE = 11;

	/**
	 * Number of elements in the table
	 */
	private int size;

	/**
	 * The backing array of this hash table
	 */
	private MapEntry<K, V>[] table;

	/**
	 * Initialize the instance variables Initialize the backing array to the
	 * initial size given
	 */
	public HashTable() {
		table = new MapEntry[INITIAL_TABLE_SIZE];
		size = 0;
	}

	private int hash(Object key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	private void checkLoadFactor() {
		if ((double) size / table.length >= MAX_LOAD_FACTOR)
			regrow();
	}

	private void regrow() {
		
		MapEntry<K,V>[] backup = table;
		int capacity = (table.length * 2) + 1;
		table = new MapEntry[capacity];
		
		for (int i = 0; i < backup.length; i++) {
			MapEntry<K,V> item = backup[i];
		
			if (item != null && !item.isRemoved()) {
				int num = item.getKey().hashCode() % table.length;
				num = findLocation(num, item.getKey());
				table[num] = new MapEntry<K, V>(item.getKey(),
						item.getValue());
			
				while (item.getNext() != null
						&& !(item.getNext().isRemoved())) {
					item = item.getNext();
					num = item.getKey().hashCode() % table.length;
					num = findLocation(num, item.getKey());
					table[num] = new MapEntry<K, V>(item.getKey(),
							item.getValue());
				}
			}
			
		}
		
	}

	private int findLocation(int index, K key) {
		MapEntry<K,V> bucket = table[index];
		if (bucket == null || bucket.isRemoved()) {
			return index;
		} else {
			while (bucket != null) {
				index++;
				bucket = table[index];
				if (bucket == null)
					return index;
			}
		}
		return -1;
	}

	private MapEntry<K, V> pairFor(Object key) {
		int hashVal = hash(key);
		if (table[hashVal] == null) {
			return null;
		} else if (table[hashVal].getKey().equals(key)
				&& !table[hashVal].isRemoved()) {
			return table[hashVal];
		} else {
			for (int i = 0; i < size; i++) {
				if (table[hashVal].equals(table[i])
						&& !table[hashVal].isRemoved()) {
					return table[hashVal];
				}
			}
			return null;
		}
	}

	/**
	 * Add the key value pair in the form of a MapEntry Use the default hash
	 * code function for hashing This is a linear probing hash table so put the
	 * entry in the table accordingly
	 * 
	 * Make sure to use the given max load factor for resizing Also, resize by
	 * doubling and adding one. In other words:
	 * 
	 * newSize = (oldSize * 2) + 1
	 * 
	 * The load factor should never exceed maxLoadFactor at any point. So if
	 * adding this element will cause the load factor to be exceeded, you should
	 * resize BEFORE adding it. Otherwise do not resize.
	 * 
	 * IMPORTANT Modification: If the given key already exists in the table then
	 * set it as the next entry for the already existing key. This means that
	 * you will never be replacing values in the hashtable, only adding or
	 * removing. This is similar to external chaining
	 * 
	 * @param key
	 *            This will never be null
	 * @param value
	 *            This can be null
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException();
		size++;
		checkLoadFactor();
		int index = key.hashCode() % table.length;
		MapEntry<K, V> item = table[index];

		if (contains(key)) {
			while (item != null) {
				if (item.getKey().equals(key)) {
					while (item.getNext() != null && !(item.isRemoved())) {
						item = item.getNext();
					}
					item.setNext(new MapEntry<K, V>(key, value));
					return;
				} else {
					index++;
					index = index % table.length;
					item = table[index];
				}
			}
		} else {
			int num = -1;
			MapEntry<K, V> item2 = table[index];
			if (item2 == null || item2.isRemoved()) {
				num = index;
			} else {
				while (item2 != null) {
					index++;
					item2 = table[index];
					if (item2 == null) {
						num = index;
					}
				}
			}
			table[num] = new MapEntry<K, V>(key, value);
		}
	}

	/**
	 * Remove the entry with the given key.
	 * 
	 * If there are multiple entries with the same key then remove the last one
	 * 
	 * @param key
	 * @return The value associated with the key removed
	 */
	public V remove(K key) {
		// Nothing to do if it isn't there
		if (!contains(key) || key == null) {
			return null;
		}
		// Search and mark as removed
		MapEntry<K, V> current = pairFor(key);
		size--;

		// no sub-tree
		if (current.getNext() == null) {
			current.setRemoved(true);
			return current.getValue();
		}

		// find the deepest item and make it's previous next null
		else {
			MapEntry<K, V> previous = current;
			current = current.getNext();
			while (current.getNext() != null) {
				current = current.getNext();
				previous = previous.getNext();
			}
			V result = current.getValue();
			previous.setNext(null);
			return result;
		}

	}

	/**
	 * Checks whether an entry with the given key exists in the hash table
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(K key) {
		if (key == null) {
			return false;
		}
		int index = key.hashCode() % table.length;
		MapEntry<K, V> item = table[index];
		while (item != null) {
			if (item.getKey().equals(key) && !item.isRemoved()) {
				return true;
			}
			index++;
			index = index % table.length;
			item = table[index];
		}
		return false;
	}

	/**
	 * Return a collection of all the values
	 * 
	 * We recommend using an ArrayList here
	 * 
	 * @return
	 */
	public Collection<V> values() {
		ArrayList<V> result = new ArrayList<>();
		for (int index = 0; index < table.length; index++) {
			if (table[index] != null && !table[index].isRemoved()) {
				MapEntry<K, V> bucket = table[index];
				result.add(bucket.getValue());
				while (bucket.getNext() != null
						&& !bucket.getNext().isRemoved()) {
					bucket = bucket.getNext();
					result.add(bucket.getValue());
				}
			}
		}
		return result;
	}

	/**
	 * Return a set of all the distinct keys
	 * 
	 * We recommend using a HashSet here
	 * 
	 * Note that the map can contain multiple entries with the same key
	 * 
	 * @return
	 */
	public Set<K> keySet() {
		HashSet<K> set = new HashSet<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null && !table[i].isRemoved()) {
				if (!set.contains(table[i].getKey()))
					set.add(table[i].getKey());
			}
		}
		return set;
	}

	/**
	 * Return the number of values associated with one key Return -1 if the key
	 * does not exist in this table
	 * 
	 * @param key
	 * @return
	 */
	public int keyValues(K key) {
		int count = 0;
		if (!contains(key)) {
			return -1;
		}
		int index = key.hashCode() % table.length;
		MapEntry<K, V> item = table[index];
		if (item.getKey() == key && !item.isRemoved()) {
			count++;
			while (item.getNext() != null && !item.getNext().isRemoved()) {
				item = item.getNext();
				count++;
			}
		}
		if (count == 0) {
			return -1;
		}
		return count;
	}

	/**
	 * Return a set of all the unique key-value entries
	 * 
	 * Note that two map entries with both the same key and value could exist in
	 * the map.
	 * 
	 * @return
	 */
	public Set<MapEntry<K, V>> entrySet() {
		HashSet<MapEntry<K, V>> result = new HashSet<>();
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null && !(table[i].isRemoved())) {
				MapEntry<K, V> item = table[i];
				if (!result.contains(item)) {
					result.add(item);
				}
				while (item.getNext() != null && !item.getNext().isRemoved()) {
					item = item.getNext();
					if (!result.contains(item)) {
						result.add(item);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Clears the hash table
	 */
	public void clear() {
		table = new MapEntry[INITIAL_TABLE_SIZE];
		size = 0;
	}

	/*
	 * The following methods will be used for grading purposes do not modify
	 * them
	 */

	public int size() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public MapEntry<K, V>[] getTable() {
		return table;
	}

	public void setTable(MapEntry<K, V>[] table) {
		this.table = table;
	}

	public String toString() {
		String ret = "[ ";
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null && !table[i].isRemoved()) {
				String add = "(";
				MapEntry<K, V> current = table[i];
				while (current.getNext() != null) {
					add = add + ", " + current.getNext().toString();
					current = current.getNext();
				}
				add = add + ")";
				ret += table[i].toString() + " " + add + ", ";
			} else
				ret += "EMPTY, ";
		}
		ret = ret + "]";
		return ret;
	}

	public int getLength() {
		return table.length;
	}

}
