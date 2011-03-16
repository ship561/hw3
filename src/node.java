import java.util.LinkedList;


public class node {
	String base;
	node edge;
	boolean visited;
	
	node(String s) {
		base = s;
		visited = false;
	}
	
	void addedge(node n) {
		this.edge = n;
	}
	
	node nextnode() {
		return this.edge;
	}
	
	String getbase(node current) {
		return current.base;
	}
	
	boolean hasvisited() {
		return this.visited;
	}
}
