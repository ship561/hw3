import java.util.Arrays;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ntshuffle nt = new ntshuffle();
		String s = "GCTTTTAGGGGTGTTAGGGGTTTATCAAAAATCTAAAAACGCCCTTTCTTCTCAAGCAATTGTCGCTACGAGCATGAGCAATTTAGCCCTTAAAGAATACTTAAAATCCCAAGATTTAGAATTGAAGCATTGCGCGATTGGGGATAAGTTTGTGAGCGAATGCATGCAATTGAATAAAGCCAATTTTGGAGGCGAGCAAAGCGGGCATATCATTTTTAGCGATTACGCTAAAACAGGCGATGGTTTGGTGTGCGCTTTGCAAGTGAGCGCGTTAGTGTTAGAAAGCAAGCAAGTAAGCTCTGTTGCACTAAACCCCTTTGAATTATACCCCCAAAGCCTAATAAATTTGAATATCCAAAAAAAGCCTCCTTTAGAAAGCCTGAAAGGTTATAGCGCTCTTTTAAAGGAATTAGACAAGCTAGAAATCCGCCATTTGATCCGCTATAGCGGCACTGAAAACAAATTACGAATCCTCTTAGAAGCTAAAGATGAAAAACTTTTAGAATCCAAAATGCAAGAATTAAAAGAGTTTTTTGAAGGGCATTTGTGCTAAAAACCACCCAAAAAAGCCTGTTGATTTTTATAGTGGTTTTTTCCCTTATTTTTGGCACGGATCAAGCGATTAAATACGCTATTTTAGAGGGGTTTCGCTATGAAAGTTTGATTATAGATATTGTTTTAGTGTTCAATAAAGGCGTGGCGTTTTCCTTGCTCAGTTTTTTAGAGGGGGGTTTGAAATACTTGCAAATCCTTTTGATTTTAGGGCTTTTTATCTTTTTAATGTGCCAAAAGGAGCTT";
		//String s = "AACAAT";
		for(int i=0; i<1000; i++) {
			String ss = nt.dinucleotideshuffle(s);
			String sss = nt.trinucleotideshuffle(s);
			//System.out.println("double " + ss);
			//System.out.println("triple " + sss);

			nucleotide freq = new nucleotide();
			freq.seq = s;
			freq.monofreq(0, freq.seq.length());
			freq.difreq(0, freq.seq.length());
			nucleotide freqss = new nucleotide(ss);
			freqss.seq = ss;
			freqss.monofreq(0, freqss.seq.length());
			freqss.difreq(0, freqss.seq.length());
			nucleotide freqsss = new nucleotide(sss);
			freqsss.seq = sss;
			freqsss.monofreq(0, freqsss.seq.length());
			freqsss.difreq(0, freqsss.seq.length());
			
			if (!Arrays.equals(freq.mono,freqss.mono) && !Arrays.equals(freqss.mono, freqsss.mono)) {
				if(!Arrays.deepEquals(freq.di, freqss.di) && !Arrays.deepEquals(freqss.di, freqsss.di)) {
					System.out.println(ss);
				}
			}
			//System.out.println(ss);
		}
	}

}
