import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;

public class matrix {
	//double[][] P;
	boolean stochastic;
	boolean rowstochastic;
	boolean colstochastic;
	boolean doublystochastic;
	boolean substochastic;
	boolean irreducible;
	Hashtable<Integer, double[][]> aperiodicity;
	int[] periods;
	boolean aperiod;
	int limit = 100;

	matrix() {
		//P=matrix;
		stochastic 		= 	false;
		rowstochastic 	=	true;
		colstochastic 	=	true;
		doublystochastic	=	false;
		substochastic 	=	true;
		irreducible 		= 	false;
		aperiod 			=	false;
		aperiodicity 	= new Hashtable< Integer,double[][]>();

	}

	double[][] multiply ( double[][] m, double[][] n) {
		double[][] product = new double[m.length][n[0].length];
		for (int k=0; k< m[0].length; k++) {
			for (int i=0; i<m.length; i++) {
				for( int j=0; j<n[0].length; j++) {
					product[i][j]+= m[i][k]*n[k][j];
				}
			}
		}
		return product;
	}
	
	double[][] power(double[][] m, int n) {
		if (n/2 >= 1) {
			if(n%2==0)
				m = multiply(power(m, n/2),power(m, n/2));
			else 
				m=multiply(power(m,n-1),power(m,1));
		}
		return m;
	}
	
	void sumrow(double[][] m) {
		for (int i=0; i<m.length; i++) {
			double sum=0;
			for( int j=0; j<m[0].length; j++) {
				sum += m[i][j];
				if(m[i][j]<0) {
					this.rowstochastic=false;
					this.substochastic=false;
				}
			}
			if(sum!=1) {
				this.rowstochastic=false;
			}
			if(sum > 1 || sum <0) {
				this.substochastic=false;
			}
		}
	}
	
	void sumcolumn(double[][] m) {
		for (int j=0; j<m[0].length; j++) {
			double sum=0;
			for( int i=0; i<m.length; i++) {
				sum += m[i][j];
				if(m[i][j]<0) {
					this.colstochastic=false;
					this.substochastic=false;
				}
			}
			if(sum!=1) {
				this.colstochastic=false;
			}
			if(sum > 1 || sum <0) {
				this.substochastic=false;
			}
		}
	}
	
	int gcd() {
		int gcd=this.periods[0];
		int i=this.periods.length-1;
		while(gcd > 1 && i >=0) {
			int remainder = this.periods[i]%gcd;
			if(remainder==0) {
				i--;
			} else {
				gcd--;
				i=this.periods.length-1;
			}
		}
		return gcd;
	}
	
	void irregularity() {
		double[][] trace = new double[3][3];

		for(int k=1; k<=this.aperiodicity.size(); k++){
			double[][] temp = this.aperiodicity.get(k);
			int sum=0;
			for(int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					if(temp[i][j]>0) {
						trace[i][j]=1;
					}
					sum+=trace[i][j];
				}
			}
			if(sum==9) {
				k=this.aperiodicity.size() +1;
				this.irreducible=true;
			}
		}
	}
	
	public static void main(String[] args) {
		double[][] ans = new double[3][3];
		matrix m = new matrix();
		//double[][] P = { {0,1,0},{1,0,0},{0,0,1} };					//a
		double[][] P = { {.5,.5,0}, {0,.5,.5}, {.5,0,.5} };			//b
		//double[][] P = { { .5,0,.5}, {.25,.25,.5}, {.25,.75,0} };		//c
		//double[][] P = { { .5,0,.5}, {.9,0,.1}, {.25,.75,0} };		//d
		//double[][] P = { {.5,0,.25}, {.25,.25,.25}, {.25,.75,0} };	//e
		
		
		
		m.sumrow(P);		//test for stochasticsm
		m.sumcolumn(P);		//test for stochasticism 
		if (m.rowstochastic && m.colstochastic) {	
			m.doublystochastic=true;
		}
		if (m.rowstochastic) {
			m.stochastic = true;
		}

		int n=1;
		do {			//calc P^n and stores the values in a hash where key=n value=P^n
			ans=m.power(P, n);
			m.aperiodicity.put(n,ans);
			n++;
		} while(!Arrays.deepEquals(m.aperiodicity.get(n-1),m.aperiodicity.get(n-2)) && n <= m.limit);	//ends if limit is hit or stationary state is hit

		n--;
		m.periods = new int[n];
		int k=0;	//inc to store i-th positions in an array
		for(int i=1; i<n; i++) {
			for(int j=i+1; j<=n; j++){
				if (Arrays.deepEquals(m.aperiodicity.get(i), m.aperiodicity.get(j))) {
					m.periods[k]=j-i;
					i++;
					k++;
				}
			}
		}
		int gcd = m.gcd();
		if(gcd <= 1) {
			m.aperiod=true;
		}
		m.irregularity();
		System.out.println("the answer after " + n + " iterations is: ");
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				System.out.print(ans[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("stochastic: " + m.stochastic + " doublystochastic: " + m.doublystochastic + " substochastic " + m.substochastic );
		System.out.println("aperiodicity: " + m.aperiod + " gcd " + gcd);
		System.out.println("irreducibility: " + m.irreducible);
	}

}