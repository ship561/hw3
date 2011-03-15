import java.util.LinkedList;


public class graph {
	LinkedList<node> nodes;
	
	graph() {
		nodes = new LinkedList<node>();
	}
	
	int size() {
		return this.nodes.size();
	}
	boolean contains(String s) {
		node[] verticies = this.verticies();
		boolean exists = false;
		for (int i=0; i<verticies.length; i++) {
			if(verticies[i].base.equals(s)) {
				exists = true;
			}
		}
		return exists;
	}
	
	node find(String s) {
		node[] verticies = this.verticies();
		for (int i=0; i<verticies.length; i++) {
			if(verticies[i].base.equals(s)) {
				return verticies[i];
			}
		}
		return null;
	}
	node[] verticies() {
		node[] n= new node[this.nodes.size()];
		return this.nodes.toArray(n);
	}
	void insertvertex(node n) {
		this.nodes.add(n);
	}
	
	void insertedge(node v, node w) {
		v.addedge(w);
	}
}
