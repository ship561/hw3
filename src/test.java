import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;


public class test {

	Hashtable<String,Integer> h;
	
	test() {
		h = new Hashtable();
	}
	
	void trifreq(String seq) {
		for(int i=0; i<seq.length()-3; i++) {
			String s = seq.substring(i,i+3);
			if(! h.containsKey(s)) {
				h.put(s, 0);
			} else {
				int v = h.get(s);
				v++;
				h.put(s,v);
			}
		}
		
	}
	public static void main(String[] args) {
		ntshuffle nt = new ntshuffle();
		String s = "GCTTTTAGGGGTGTTAGGGGTTTATCAAAAATCTAAAAACGCCCTTTCTTCTCAAGCAATTGTCGCTACGAGCATGAGCAATTTAGCCCTTAAAGAATACTTAAAATCCCAAGATTTAGAATTGAAGCATTGCGCGATTGGGGATAAGTTTGTGAGCGAATGCATGCAATTGAATAAAGCCAATTTTGGAGGCGAGCAAAGCGGGCATATCATTTTTAGCGATTACGCTAAAACAGGCGATGGTTTGGTGTGCGCTTTGCAAGTGAGCGCGTTAGTGTTAGAAAGCAAGCAAGTAAGCTCTGTTGCACTAAACCCCTTTGAATTATACCCCCAAAGCCTAATAAATTTGAATATCCAAAAAAAGCCTCCTTTAGAAAGCCTGAAAGGTTATAGCGCTCTTTTAAAGGAATTAGACAAGCTAGAAATCCGCCATTTGATCCGCTATAGCGGCACTGAAAACAAATTACGAATCCTCTTAGAAGCTAAAGATGAAAAACTTTTAGAATCCAAAATGCAAGAATTAAAAGAGTTTTTTGAAGGGCATTTGTGCTAAAAACCACCCAAAAAAGCCTGTTGATTTTTATAGTGGTTTTTTCCCTTATTTTTGGCACGGATCAAGCGATTAAATACGCTATTTTAGAGGGGTTTCGCTATGAAAGTTTGATTATAGATATTGTTTTAGTGTTCAATAAAGGCGTGGCGTTTTCCTTGCTCAGTTTTTTAGAGGGGGGTTTGAAATACTTGCAAATCCTTTTGATTTTAGGGCTTTTTATCTTTTTAATGTGCCAAAAGGAGCTT";
		//String s = "AGACATAAAGTTCCGTACTGCCGG";
		for(int i=0; i<1000; i++) {
			String ss = nt.dinucleotideshuffle(s);
			String sss = nt.trinucleotideshuffle(s);
			//System.out.println("double " + ss);
			System.out.println("triple " + sss);

//			test freq = new test();
//			freq.trifreq(s);
//			test freqss = new test();
//			freqss.trifreq(ss);
//			test freqsss = new test();
//			freqsss.trifreq(sss);
//
//			for(Enumeration<String> e=freq.h.keys(); e.hasMoreElements();) {
//				String k = e.nextElement();
//				if(freq.h.get(e) != freqss.h.get(e) && freqss.h.get(e)!=freqsss.h.get(e))
//					System.out.println("error");
//			}
			//System.out.println(ss);
		}
	}

}
