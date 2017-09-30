package ua.alex.pab.java.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Laws {
	private Map<String,Law> lawMap;
	
	class EntityLaw extends Law {
		public EntityLaw(String name, Law owner) {
			this.name  = name;
			this.owner = owner;
		}
	}
	
	private final Law ROOT = new EntityLaw("root", null);
	
	public Laws() {
		lawMap = new HashMap<String,Law>();
		lawMap.put("root", ROOT);
	}
	
	public Law getRoot() {
		return ROOT;
	}
	
	public boolean addLaw(String name, String owner) {
		Set<String> keys = lawMap.keySet();
		
		if (keys.contains(name) || !keys.contains(owner)) {
			return false;
		}
		
		Law ownerObj = lawMap.get(owner);
		Law newLawObj = new EntityLaw(name, ownerObj);
		lawMap.put(name, newLawObj);
		
		return true;
	}
	
	public List<Law> getAll() {
		return new ArrayList<Law>(lawMap.values());
	}
	
	public Law getFromName(String name) {
		return lawMap.get(name);
	}
	
}