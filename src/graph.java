import java.util.LinkedList;


public class graph {
	LinkedList<node> nodes;
	
	graph() {
		nodes = new LinkedList<node>();
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
