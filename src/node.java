import java.util.LinkedList;


public class node {
	String base;
	LinkedList edge;
	int index;
	
	node(String s) {
		base = s;
		edge = new LinkedList();
	}
	
	void addedge(node n) {
		this.edge.add(n);
	}
	
	node nextnode() {
		int next = this.index;
		this.index++;
		if(this.index > this.edge.size()) {
			this.index=0;
			return null;
		} else {
			return (node) this.edge.get(next);
		}
	}
	
	String getbase(node current) {
		return current.base;
	}
}
