import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;


public class nucleotide {
	String seq;
	double[] mono; //A,C,G,T
	double[][] di;
	
	nucleotide() {
		mono 	= new double[4];
		di 		= new double[4][4];
	}
	
    String fileIN(File f) {           //reads file in
        BufferedReader reader = null;
        String input="";

        try {
                reader = new BufferedReader(new FileReader(f));
                String text = "";
                int j=0;
                while ((text = reader.readLine()) != null) {
                    //text = reader.readLine();
                    //text.trim();
                    if(! Pattern.matches("^>.*", text)) {
                            input += text;
                            j++;
                    }
                }
                reader.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return input;
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
    
    void monofreq(int from, int to) {
    	String sequence = this.seq.substring(from,to);
    	this.mono 	= new double[4];
    	for(int i=0; i< sequence.length(); i++) {
    		this.mono[findnuc(sequence.substring(i,i+1))]++;
    	}
    }
    
    void difreq(int from, int to) {
    	String sequence = this.seq.substring(from,to);
    	this.di 		= new double[4][4];
    	for(int i=0; i< sequence.length()-1; i++) {
    		this.di[findnuc(sequence.substring(i,i+1))][findnuc(sequence.substring(i+1,i+2))]++;
    	}
    }
    
    double cpg() {
    	return this.di[1][2]/(this.mono[1]*this.mono[2]);
    }
    
    public static void main(String[] args) {
    	nucleotide pylori = new nucleotide();
    	File file=new File(args[0]);
    	pylori.seq=pylori.fileIN(file);
    	File outfile = new File("/Users/meyerlab/Desktop/cpg2.txt");
    	
    	FileWriter outFile = null;
    	PrintWriter out=null;
    	int n=0;
		try {
			outFile = new FileWriter(outfile);
			out = new PrintWriter(outFile);
		
    	
			System.out.println(pylori.seq.length());
	    	for(int i=0; i<pylori.seq.length()-500; i++) {		
	    		pylori.monofreq(i,i+500);
	    		pylori.difreq(i,i+500);
	    		n=i;
	    		out.println(i + " " + pylori.cpg());
	    		if( i%10000==0 || i>1607700) {
	    			
	    			System.out.println(i + " " + pylori.cpg() + "\t" + pylori.seq.length());
	    		}
	    	}
	   	} catch (IOException e) {
				// TODO Auto-generated catch block
	   			System.out.println("failed out of " + pylori.seq.length());
				e.printStackTrace();
		} finally {
			System.out.println(n + " " + pylori.seq.substring(n,n+500));
			out.close();
		}
    	
   /*
    	pylori.monofreq(0, pylori.seq.length());
    	pylori.difreq(0, pylori.seq.length());
    	for (int i=0; i < pylori.mono.length; i++ ) {
    		System.out.println(pylori.mono[i]);
    	}
    	for(int i=0; i<4; i++) {
    		int sum = 0;
    		for(int j=0; j<4; j++ ){
    			sum += pylori.di[i][j];
    			System.out.print(pylori.di[i][j] + " ");
    		}
    		System.out.println(sum);
    	}
    	 */ 	
    }

}
