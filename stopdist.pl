#!/usr/bin/perl
### stopdist.pl
use strict; 

my $file = @ARGV[0];
my $k = @ARGV[1];
my @stops;

open(FILE, $file) || die ("can't open file\n");
my $hpylori = "";
while(my $line = <FILE>) {
      chomp $line;
      $hpylori .= $line;
}
#print $hpylori . "\n";
my $len = length $hpylori;
for(my $i=0; $i<$len-3; $i++) {
      my $codon = substr($hpylori,$i,3);
      if($codon =~ /T(AA|AG|GA)/) {
            push(@stops, $i);
            #print ("$codon\n");
            #           print $i . "\n";
      }
}
$len= @stops-$k;
for(my $i=0; $i<$len; $i++) {
      my $dist = @stops[$k]-@stops[$i];
      print ("$dist\n");
      #print ("$dist nt separate the " . $i+1 . "th and $k th stop codons\n");
      $k++;
}
