bins=50;
figure(1);
subplot(2,2,1)
[counts,bin]=hist(k1,bins);
bar(bin,counts/length(k1),1);

clear('epdf','chisq');
lambda=1/mean(k1);
epdf(1)=-exp(-lambda*bin(1))+1;
for i=1:numel(bin)-1
   epdf(i+1)= -exp(-lambda*bin(i+1))+exp(-lambda*bin(i));
   chisq(i)=(counts(i)/length(k1)-epdf(i))^2/epdf(i);
end
hold on;
plot(bin,epdf, 'r');
hold off;
sum(chisq);

subplot(2,2,2)
[counts,bin]=hist(k2,bins);
bar(bin,counts/length(k2),1);
subplot(2,2,3)
[counts,bin]=hist(k3,bins);
bar(bin,counts/length(k3),1);
subplot(2,2,4);
[counts,bin]=hist(k4,bins);
bar(bin,counts/length(k4),1);

figure(2);
subplot(2,2,1)
hist(k1,bins);
subplot(2,2,2)
hist(k2,bins);
subplot(2,2,3)
hist(k3,bins);
subplot(2,2,4);
hist(k4,bins);

clear('epdf');
lambda=1/mean(k1);
epdf(1)=-exp(-lambda*bin(1))+1;
for i=1:numel(bin)-1
   epd

k4=importdata('c:\Users\belldandy\Downloads\k4.txt');
alpha=mean(k4)^2/(var(k4)-mean(k4))
beta=mean(k4)/alpha
bins=99577;
[counts,bin]=hist(k4,bins);
xpdf=counts/numel(k4);
bar(bin,xpdf,1);
hold on;
x=1:bins;
gpdf=gampdf(bin,alpha,beta);
plot(bin,gpdf,'r');

clear('chi');
for i=1:numel(k4)
    chi(i)=(xpdf(i)-gpdf(i))^2/gpdf(i);
end
sum(chi);

[h,p,st]=chi2gof(xpdf,'ctrs',bin,'frequency',xpdf,'expected',gpdf,'nparams',bins-3)