package ua.alex.pab.java.base;

public abstract class Law {
	protected String name;
	protected Law owner;

	public String getName() {
		return name;
	}

	public Law getOwner() {
		return owner;
	}
	
	public boolean inBranches(String[] branches) {
		Law tmp = this;
		
		while (tmp.owner != null) {
			for (int i = 0; i < branches.length; i++) {
				if (tmp.name.compareTo(branches[i]) == 0) {
					return true;
				}
			}
			tmp = tmp.owner;
		}
		
		return false;
	}
	
	public boolean inBranch(String branch) {
		return inBranches(new String[] {branch});
	}
}
