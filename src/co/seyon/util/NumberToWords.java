package co.seyon.util;


public class NumberToWords {

	public static String convertIntegerToWords(int n){
        if(n==0) return "Zero";
        String arr1[]={"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
        String arr2[]={"Twenty","Thirty", "Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
        String unit[]={"Arab","Crore","Lakh","Thousand","Hundred", ""};
        int factor[]={1000000000, 10000000, 100000, 1000, 100,1};
        String answer="";
        if(n<0){
            answer="Negative";
            n=-n;
        }
        int quotient, units, tens;
        for(int i=0; i<factor.length; i++){
                quotient=n/factor[i];             if(quotient>0){
                if(quotient<20){
                    answer = answer + " " + arr1[quotient-1];
                }else{
                    units=quotient%10;
                    tens=quotient/10;
                    answer = answer + " " + arr2[tens-2] + " " + arr1[units-1];
                }
                answer = answer + " " + unit[i];
            }
            n=n%factor[i];
        }
        return answer.trim();
    }
    public static void main(String args[]){
        System.out.println("10000: "+convertIntegerToWords(1000000));
        System.out.println("10005006: "+convertIntegerToWords(10005006));
        System.out.println("-10005006: "+convertIntegerToWords(-10005006));

    }
}