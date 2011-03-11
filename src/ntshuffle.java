import java.util.Random;
import java.util.regex.Pattern;


public class ntshuffle {
	double[][] cdf;
	String[] bases = {"", "A", "C", "G", "T", "A"};
	
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
		for (int i=0; i< len; i++) {
			if(i==0) {
				base=this.firstbase();
			} else {
				base=this.nextbase(base);
			}
			seq += base;
			if (i%10000==0) {
				System.out.println(i+1 + " seq.length= " + seq.length());
			}
		}
		return seq;
	}
}
