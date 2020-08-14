
import java.io.*;
public class Lcs {
    public int lcsDynamic(char str1[],char str2[]){
        int temp[][]=new  int [str1.length][str2.length];
        int max=0;
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<temp[i].length;j++){
               if(str1[i-1]==str2[j-1]){
                   temp[i][j]=temp[i-1][j-1]+1;
               }
               else{
                   temp[i][j]=Math.max(temp[i][j-1], temp[i-1][j]);
               }
               if(temp[i][j] > max){
                max = temp[i][j];
            }
            }
        }
        return max;
    }
    public static void main(String[]args){
        Lcs lcs = new Lcs();
        String str1 = "ABCDGHLQR";
        String str2 = "AEDPHR";
        
        int result = lcs.lcsDynamic(str1.toCharArray(), str2.toCharArray());
        System.out.print(result);
    }
}