import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** Class for buffered reading int and double values */
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                    reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }

    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}


public class Prob-B1 {
	public static void main(String[] args) throws IOException {
		Reader.init(System.in);
		int n = Reader.nextInt();
		int k = Reader.nextInt();
		double[][] arr = new double[3][n];
		//1st skill
		//2nd wage
		//3rd ratio
		for(int i = 0; i<n; i++) {
			arr[0][i] = (double)Reader.nextInt();
		}
		for(int i = 0; i<n; i++) {
			arr[1][i] = (double)Reader.nextInt();
		}
		for(int i = 0; i<n; i++) {
			arr[2][i] = arr[1][i]/arr[0][i];
		}
		
		Sort object = new Sort();
        object.sorting(arr, 0, n-1);
        Heep heap = new Heep(n);
        double ans = 1e9;
        int sum = 0;
        for(int i = 0; i<n; i++) {
        	heap.insert(-arr[0][i]);
        	sum+=arr[0][i];
        	if (heap.index > k) {
                sum += heap.arr[0];
                heap.delete();
        	}
            if (heap.index == k)
                ans = Math.min(ans, sum * arr[2][i]);
        }
//        Heep h = new Heep(4);
//        h.insert(9);
//        h.insert(0);
//        h.insert(7);
//        h.insert(4);
//        h.print();
        System.out.println((int)Math.ceil(ans));
	}
	
//	static void ra(int[] skill, int[] wage, double[] ratio, int n) {
//		for(int i = 0; i<n; i++) {
//			ratio[i] = (double) wage[i]/skill[i];
//		}
//	}
}

class Heep{
	int n;
	double[] arr;
	int index = 0;
	
	Heep(int size){
		n = size;
		arr = new double[size];
    }
    
    void print(){
        for(int i=0; i<index; ++i){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
	
	void insert(double arr2) {
		arr[index] = arr2;
		int i = index; 
		while(i>0) {
			int parent = (i+1)/2 -1;
			if(arr[parent]>arr[i]) {
				double temp = arr[i];
				arr[i] = arr[parent];
				arr[parent] = temp;
				i = parent;
            }
            else
                break;
		}
		index++;
	}
	
	void heapify(int i) { 
        int largest = i; 
        int l = 2 * i + 1; 
        int r = 2 * i + 2;
        if (l < index && arr[l] < arr[largest]) 
            largest = l;
        if (r < index && arr[r] < arr[largest]) 
            largest = r;
        if (largest != i) { 
            double temp = arr[i]; 
            arr[i] = arr[largest]; 
            arr[largest] = temp;
            heapify(largest); 
        } 
    } 
	
	void delete() { 
        double lastElement = arr[index - 1];
        arr[0] = lastElement; 
        n = index - 1; 
        index--;
        heapify(0);
    }
}

class Sort{
    
    void merge(double[][] ratio, int lo, int mid, int hi) {
        int n1 = mid-lo+1;
        int n2 = hi-mid;
        double[][] left = new double[3][n1];
        double[][] right = new double[3][n2];
        for(int i = 0; i<n1; ++i) {
            left[0][i] = ratio[0][lo+i];
            left[1][i] = ratio[1][lo+i];
            left[2][i] = ratio[2][lo+i];
        }
        for(int j = 0; j<n2; ++j) {
            right[0][j] = ratio[0][mid+1+j];
            right[1][j] = ratio[1][mid+1+j];
            right[2][j] = ratio[2][mid+1+j];
        }
        int i = 0;
        int j = 0; 
        int k = lo;
        while(i<n1 && j<n2) {
            if(left[2][i]<=right[2][j]) {
            	ratio[0][k] = left[0][i];
            	ratio[1][k] = left[1][i];
            	ratio[2][k] = left[2][i];
            	i++;
            }
            else {
            	ratio[0][k] = right[0][j]; 
            	ratio[1][k] = right[1][j];
            	ratio[2][k] = right[2][j];
            	j++;
            }
            k++;
        }
        
        while(i<n1) {
            ratio[0][k] = left[0][i]; 
            ratio[1][k] = left[1][i];
            ratio[2][k] = left[2][i];
            i++; 
            k++;
        }
        while(j<n2) {
            ratio[0][k] = right[0][j];
            ratio[1][k] = right[1][j];
            ratio[2][k] = right[2][j];
            j++; 
            k++;
        }
        
    }
    
    void sorting(double[][] ratio, int lo, int hi) {
        if(lo<hi) {
        int mid = (lo+hi)/2;
        sorting(ratio, lo, mid);
        sorting(ratio, mid+1, hi);
        merge (ratio, lo, mid, hi);
        }
    }
}