package com.cachedemo.caching;

import java.util.*;

public class Cache{
	private static Cache instance = null;
	private LinkedHashSet<String> cache;
	static final int cacheMaxSize = 10;
	
	private Cache(){
		cache = new LinkedHashSet<String>();
	}
	
	public static Cache getInstance(){
		if(instance == null)
			synchronized(Cache.class){
				if(instance == null){
					instance = new Cache();
				}
			}
		return instance;
	}
	
	public synchronized int add(String data){
		int returnCode = -1; 
		boolean contains = query(data);
		if(!contains){	
			returnCode = 0; // item added successfully to the cache
		}
		else{
			returnCode = 1; // item already present in the cache
		}
		return returnCode;
	}
	
	public synchronized boolean query(String data){
		//check if item exists
		//if found, delete the item from cache and re-insert to mark it as most recently used.
		if(cache != null){
			if(cache.contains(data)){
				cache.remove(data);
				cache.add(data);
				return true;
			}
		}
		addData(data);
		return false;
	}
	
	public synchronized void addData(String data){
		if(cache != null){
			//remove least recently used element if cache is full
			if(cache.size() == cacheMaxSize){
				Iterator<String> itr = cache.iterator();
				cache.remove(itr.next());
			}
		}
		cache.add(data);
	}
	
	public LinkedHashSet<String> print(){
		LinkedHashSet<String> cacheCopy = new LinkedHashSet<>();
		Iterator<String> itr = cache.iterator();
		while(itr.hasNext()){
			cacheCopy.add(itr.next());
		}
		return cacheCopy;
	}
	
	
	
}