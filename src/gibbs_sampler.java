import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.regex.Pattern;

class gibbs_sampler {
      int number;
      String[] sequences;              //sequences S
      String[] sample;                 //list of Xi words taken starting at k start locations
      int wordlength;
      Hashtable<String,double[]> prob_table;
      int k_removed;
      //String Xk_removed;
      double pseudocount;
      double percentConverge;

      gibbs_sampler() {
              wordlength=6;				//searching for k-mer of length wordlength
              pseudocount=.5;
              percentConverge=.000001;
              prob_table = new Hashtable<String,double[]>();
              initialize(prob_table);
              
      }

      Hashtable<String, double[]> initialize(Hashtable<String,double[]> hash) {
              double[] a = new double[wordlength];
              double[] c = new double[wordlength];
              double[] g = new double[wordlength];
              double[] t = new double[wordlength];
              hash.put("A", a);
              hash.put("C", c);
              hash.put("G", g);
              hash.put("T", t);
              return hash;
      }

      void sampler() {
              Random generator = new Random();
              this.sample = new String[this.sequences.length];
              for(int i=0; i<this.sequences.length; i++) {
                      int k = generator.nextInt(this.sequences[i].length()-this.wordlength);
                      this.sample[i]=this.sequences[i].substring(k,k+this.wordlength);
              }
      }

      void fileIN(File f) {           //reads file in
              //StringBuffer contents = new StringBuffer();
              BufferedReader reader = null;
              String[] input = new String[350];

              try {
                      reader = new BufferedReader(new FileReader(f));
                      String text = null;
                      int j=0;
                     while (reader.readLine() != null) {
                              text = reader.readLine();
                              if(! Pattern.matches("^>.*", text)) {
                                      input[j] = text;
                                      j++;
                              }
                      }
              } catch (FileNotFoundException e) {
                      e.printStackTrace();
              } catch (IOException e) {
                      e.printStackTrace();
              }
              this.sequences=input;
      }

      void removal() {
          Random generator = new Random();
          int k = generator.nextInt(this.sequences.length);
          //this.Xk_removed = this.sample[k];
          this.sample[k] = "";
          this.k_removed = k;

      }

      Hashtable<String, double[]> probabilities() {
          Hashtable<String,double[]>temp_table = new Hashtable<String,double[]>();
          initialize(temp_table);; //keys = [A, C, G, T] array = pos[1-6]
          for(int i=0; i< this.sample.length; i++) {
                  for(int j=0; j<this.sample[i].length(); j++) {
                          if(Pattern.matches("(A|a)", this.sample[i].substring(j,j+1))) {
                                  temp_table.get("A")[j] += 1/(this.sample.length-1+this.pseudocount*4);
                          }else if (Pattern.matches("(C|c)",this.sample[i].substring(j,j+1))) {
                                  temp_table.get("C")[j] += 1/(this.sample.length-1+this.pseudocount*4);
                          }else if (Pattern.matches("(G|g)", this.sample[i].substring(j,j+1))) {
                                  temp_table.get("G")[j] += 1/(this.sample.length-1+this.pseudocount*4);
                          }else if (Pattern.matches("(T|t)", this.sample[i].substring(j,j+1))) {
                                  temp_table.get("T")[j] += 1/(this.sample.length-1+this.pseudocount*4);
                          }
                          temp_table.get("A")[j] += this.pseudocount/Math.pow((this.sample.length-1+this.pseudocount*4),2);
                          temp_table.get("C")[j] += this.pseudocount/Math.pow((this.sample.length-1+this.pseudocount*4),2);
                          temp_table.get("G")[j] += this.pseudocount/Math.pow((this.sample.length-1+this.pseudocount*4),2);
                          temp_table.get("T")[j] += this.pseudocount/Math.pow((this.sample.length-1+this.pseudocount*4),2);
                  }
          }
          return temp_table;
      }

      void score() {
          double topscore=-1;
          String str = this.sequences[this.k_removed];
          int topPos=0;
          for(int i = 0; i<str.length()-this.wordlength; i++) {
                  String substr = str.substring(i,i+this.wordlength);
                  substr=substr.toUpperCase();
                  double currentscore=1;
                  for(int j=0; j<this.wordlength; j++) {
                          currentscore *= this.prob_table.get(substr.substring(j,j+1))[j];

                  }
                  if(currentscore > topscore) {
                          topscore = currentscore;
                          topPos = i;
                  }
          }
          this.sample[k_removed]=str.substring(topPos,topPos+wordlength);
      }

      boolean converge(Hashtable<String,double[]> hash) {
          boolean done =false;
          if(! hash.isEmpty()) {
        	  Enumeration<String> i = hash.keys();
                  while( i.hasMoreElements()) {
                	  String base = i.nextElement();
                          for(int j = 0; j<this.wordlength; j++ ) {
                                  if(this.prob_table.get(base)[j]/hash.get(base)[j] < 1+this.percentConverge && this.prob_table.get(base)[j]/hash.get(base)[j] > 1-this.percentConverge) {
                                          done=true;
                                  } else {
                                          done=false;
                                  }
                          }
                  }
          }
          return done;
      }
      public static void main(String[] args) {
              gibbs_sampler gs = new gibbs_sampler();
              File file=null;
              try {
                      file = new File(args[0]);               //file name
              } catch (ArrayIndexOutOfBoundsException e) {
                      System.out.println("no file found using test case example");
              }
              gs.fileIN(file);
              for(int i=0; i<1000; i++) {
            	  gs.sampler();
              }
              int i=0;
              boolean converge=false;
              Hashtable<String,double[]> previoushash = new Hashtable<String,double[]>();
              while(! converge || i <=10000){
                  gs.removal();
                  gs.prob_table = gs.probabilities();
                  gs.score();

                  converge = gs.converge(previoushash);
                  previoushash.clear();
                  previoushash.putAll(gs.prob_table);
                  i++;
                  if(i%1000==0) {
                	  System.out.println(i);
		              for(int j=0; j<gs.wordlength; j++) {
		                  System.out.println(j + " " + gs.prob_table.get("A")[j]+ " " + gs.prob_table.get("C")[j] + " " + gs.prob_table.get("G")[j] + " " + gs.prob_table.get("T")[j]);
		              }
                  }
              }
              for(int j=0; j<gs.sample.length; j++) {
            	  System.out.println(gs.sample[j]);
              }
              System.out.println(i);
              for(int j=0; j<gs.wordlength; j++) {
                  System.out.println(j + " " + gs.prob_table.get("A")[j]+ " " + gs.prob_table.get("C")[j] + " " + gs.prob_table.get("G")[j] + " " + gs.prob_table.get("T")[j]);
              }
      }

}