import java.io.*;
import java.util.*;
public class Snapwiz1{
    public static void main(String[]args) {
        Scanner in = new Scanner(System.in);
        int n= in.nextInt();
        Integer arr[] = new Integer[n];
        for(int i=0;i<n;i++){
            arr[i]=in.nextInt();
        }
        Integer.toString(arr[0]).length();
        Arrays.sort(arr,Collections.reverseOrder());
        System.out.println(arr[0]);

    }
}