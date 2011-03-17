#!/usr/bin/perl
use strict;

my $n=10000000;
my @bases = qw(A C G T);
my $count =0;
for( my $i=0; $i<$n; $i++) {
      my $s = "";
      my $tata=0;
      for( my $j=0; $j<10000; $j++) {
            
            my $rand = int(rand(4));
            $s .= @bases[$rand];
            if (length $s >6) {
                  $s=substr($s,1);
            }
                  if( $s =~ /TATAAT/) {
                        $tata++;
                        #print "$i $j $s\n";
                  }
      }
      if($tata >= 20) {
            $count++;
      }
}
print "count $count " . $count/$n . "\n";
