package Conponent;

import java.util.Iterator;
import java.util.LinkedList;
import Conponent.Option;

public class HashTable {
	class Node {
		String key;
		Option value;
		
		public Node(String key, Option value) {
			this.key = key;
			this.value = value;
		}		

		public Option getValue() {
			return value;
		}

		public void setValue(Option value) {
			this.value = value;
		}
	}
	
	LinkedList<Node>[] data;
	public HashTable(int size) {
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
	
	public void put(String key, Option value) {
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
	
	public Option get(String key) {
		int hashcode = getHashCode(key);
		int index = convertToIndex(hashcode);
		Option nullValue = new Option();
		nullValue.setId("data not include");
		LinkedList<Node> lsit = data[index];
		Node node = searchKey(lsit, key);
		return node == null? nullValue : node.getValue();
	}

	public boolean delete(String key) {
		int hashcode = getHashCode(key);
		int index = convertToIndex(hashcode);
		int nodeIndex = 0;
		LinkedList<Node> list = data[index];
		if (list == null) {
			list = new LinkedList<Node>();
			data[index] = list;
		}
		if (list == null) {
			System.out.println("already empty");
			return true;
		}
		for (Node node : list) {
			if (node.key.equals(key)) {
				list.remove(nodeIndex);
				return true;
			}
			nodeIndex++;
		}
		return false;
	}

	public boolean update(String key, Option op) {
		int hashcode = getHashCode(key);
		int index = convertToIndex(hashcode);
		LinkedList<Node> list = data[index];
		Node node = searchKey(list, key);
		node.value = op;
		return true;
	}
}
