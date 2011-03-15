import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Pattern;


public class ntshuffle {
	double[][] cdf;
	String[] bases = {"", "A", "C", "G", "T", "A"};

	ntshuffle() {
		//null constructor
	}

	ntshuffle(int[][] di) {
		cdf = new double[4][5];
		int sum=0;
		double cum=0;
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++ ){
				sum += di[i][j];
				cdf[i][4]+=di[i][j];
			}

			for(int j=0; j<4; j++) {
				cdf[i][j]=di[i][j]/cdf[i][4]+cum;
				cum=cdf[i][j];
			}
			cum=0;
		}
		for(int i=0; i<4; i++) {   
			cdf[i][4]=cdf[i][4]/sum+cum;
			cum=cdf[i][4];
		}
	}

	int findnuc(String n) {
		if(Pattern.matches("(A|a)", n)) {
			return 0;
		}else if (Pattern.matches("(C|c)",n)) {
			return 1;
		}else if (Pattern.matches("(G|g)", n)) {
			return 2;
		}else if (Pattern.matches("(T|t)", n)) {
			return 3;
		}
		return 4;
	}

	String firstbase() {
		Random rand = new Random();
		double k = rand.nextDouble();
		int i=0;
		while (k > this.cdf[i][4] && i < 4) {
			i++;
		}
		return bases[i+1];
	}

	String nextbase(String currentbase) {
		Random rand = new Random();
		double k = rand.nextDouble();
		int i=0;
		while (k > this.cdf[this.findnuc(currentbase)][i] && i < 4) {
			i++;
		}
		return bases[i+1];
	}
	String randomseq(int len) {
		Random rand = new Random();
		double k = rand.nextDouble();
		String base = "";
		String seq = "";
		String tempseq="";
		for (int i=0; i< len; i++) {
			if(i==0) {
				base=this.firstbase();
			} else {
				base=this.nextbase(base);
			}
			tempseq += base;
			if (i%10000==0) {
				System.out.println(i+1 + " seq.length= " + seq.length());
				seq+=tempseq.substring(0,tempseq.length()-1);
				tempseq=tempseq.substring(tempseq.length()-1);
			}
		}
		seq+=tempseq;
		return seq;
	}
	LinkedList shuffleList(LinkedList original) {
		LinkedList l = new LinkedList();
		Random rand = new Random();
		while (original.size()>1) {
			int i = rand.nextInt(original.size()-1);
			l.add(original.get(i));
			original.remove(i);
		}
		l.addLast(original.getFirst());
		return l;
	}

	String pickrandom(LinkedList<String> l) {
		Random rand = new Random();
		int i = rand.nextInt(l.size());
		String s = l.get(i);
		l.remove(i);
		l.addLast(s);
		return s;
	}

	boolean DFS(graph g, node n, String last) {
		node next = n.nextnode();
		boolean state=false;
		if (n.base == last) {
			return true;
		} else {
			if(next == null)
				return false;
			else 
				state = DFS(g,next,last);
		}
		return state;
	}

	String dinucleotideshuffle(String seq) {
		Hashtable<String, LinkedList<String>> baseArr = new Hashtable<String, LinkedList<String>>();
		String lastbase = seq.substring(seq.length()-1);
		String firstbase = seq.substring(0,1);
		node n = new node(lastbase);
		boolean state = false;
		for(int i=0; i<seq.length()-1; i++) {		//adding all the dinucleotides to a linked list in a hash table k=base, v=linked list of proceeding base
			String curbase = seq.substring(i,i+1);
			try {
				baseArr.get(curbase).add(seq.substring(i+1,i+2));
			} catch (NullPointerException e) {
				LinkedList l = new LinkedList();
				baseArr.put(curbase,l);
				baseArr.get(curbase).add(seq.substring(i+1,i+2));
			}
		}
		while(state ==false) {
			graph G = new graph();
			node[] verticies=null;
			G.insertvertex(n);
			for (Enumeration<String> e = baseArr.keys(); e.hasMoreElements();){
				String k = e.nextElement();
				if(! k.equals(lastbase)){
					String randBase = pickrandom(baseArr.get(k));
					n = new node(randBase);
					verticies = G.verticies();
					for (int i=0; i<verticies.length; i++) {
						if(! verticies[i].base.equals(n.base)) {
							G.insertvertex(n);
							G.insertedge(verticies[i], n);
						}
					}

				}
			}
			for (int i=0; i<verticies.length; i++) {
				if(DFS(G,verticies[i],lastbase)==false) {
					break;
				} else {
					state=true;
				}
			}

			for (Enumeration<String> e = baseArr.keys(); e.hasMoreElements();){
				String k = e.nextElement();
				baseArr.put(k, shuffleList(baseArr.get(k)));
			}
		}
		String newseq="";
		String temp = firstbase;
		for(int i=1; i<seq.length(); i++) {
			temp += baseArr.get(temp.substring(i-1,i)).pop();
			if(i%10000==0) {
				newseq += temp;
			}
		}
		newseq += temp;
		//newseq += lastbase;
		return newseq;
	}
}
