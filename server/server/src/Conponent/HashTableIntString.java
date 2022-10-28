package Conponent;

import java.util.Iterator;
import java.util.LinkedList;

public class HashTableIntString {
	class Node {
		String key;
		int value;
		
		public Node(String key, int value) {
			this.key = key;
			this.value = value;
		}		

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}
	
	LinkedList<Node>[] data;
	public HashTableIntString(int size) {
		this.data = new LinkedList[size];			
	}
	
	int getHashCode(String key) {
		int hashcode = 0;
		for(char c : key.toCharArray()) {
			hashcode += c;
		}
		return hashcode;
	}
	
	int convertToIndex(int hashcode) {
		return hashcode % data.length;
	}
	
	Node searchKey(LinkedList<Node> list, String key) {
		if (list == null) {
			return null;
		}
		for (Node node : list) {
			if (node.key.equals(key)) {
				return node;
			}
		}
		return null;
	}
	
	public void put(String key, int value) {
		int hashcode = getHashCode(key);
		int index = convertToIndex(hashcode);
		LinkedList<Node> list = data[index];
		if (list == null) {
			list = new LinkedList<Node>();
			data[index] = list;
		}
		Node node = searchKey(list, key);
		if (node == null) {
			list.addLast(new Node(key,value));
		} else {
			node.setValue(value);
		}
	}
	
	public int get(String key) {
		int hashcode = getHashCode(key);
		int index = convertToIndex(hashcode);
		int nullValue = -1;
		LinkedList<Node> lsit = data[index];
		Node node = searchKey(lsit, key);
		return node == null? nullValue : node.getValue();
	}
}
