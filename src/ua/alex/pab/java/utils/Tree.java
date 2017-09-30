package ua.alex.pab.java.utils;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class Tree<T> {
	private Map<T,T> map;
	private T root;
	
	public Tree(T root) {
		map = new HashMap<T,T>();
		this.root = root;
	}
	
	public T getRoot() {
		return root;
	}
	/*
	public boolean addBranch(T owner, T val) {
		if (owner != null) {
			//Set<T> map.keySet();
			owner = root;
		}
	}*/
}
