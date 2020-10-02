import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** Class for buffered reading int and double VALUEs */
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

public class Prob-C12 {
	public static void main(String[] args) throws IOException{
		Reader.init(System.in);
		int n = Reader.nextInt();
		int q = Reader.nextInt();
		int[] block = new int[n];
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>(n);
		for(int i = 0; i<n; i++) {
			graph.add(new ArrayList<Integer>());
		}
		int zer0 = 0;
		Disjoint entangled = new Disjoint(n);
		long[] specialValue = new long[n];
		int jj = 0;
		while(jj<n) {
			long x = Reader.nextInt();
			specialValue[jj] = x;
			jj+=1;
		}
		for(int ii = 0; ii<q; ii++) {
			int query = Reader.nextInt();
			if(query==3) {
				int i = Reader.nextInt()-1;
				int j = Reader.nextInt()-1;
				long w = Reader.nextInt();
				int seti=entangled.find(i);
                int setj=entangled.find(j);
                if(seti!=setj || block[seti]==1){
                    System.out.println(0);
                    continue;
                }
                long[] arr = new long[n];
                long edges = breadthFS(i, graph, arr, j);
                long a = (long)Math.pow(-1,edges%2)*specialValue[i]*w;
                long b = specialValue[j];
                System.out.println(fraction(a,b));
			}
			else if(query == 2) {
				int i = Reader.nextInt()-1;
				int j = Reader.nextInt()-1;
				int seti = entangled.find(i);
				int setj = entangled.find(j);
				if(seti!=setj) {
					graph.get(i).add(j);
                    graph.get(j).add(i);
                    entangled.union(seti,setj);
                    checkBlock(block, seti, setj);
				}
				else if(seti==setj){
                    if(block[seti]==1){
                        continue;
                    }
                    long[] arr = new long[n];
                    long edges = breadthFS(i, graph, arr, j);
                    if(edges%2==0){
                        block[seti]=1; zer0 = 9;
                        continue;
                    }
                    continue;
                }
                else{
                    
                }
			}
			else {
				int i = Reader.nextInt()-1;
				int v = Reader.nextInt();
				specialValue[i] = v;
			}
		}
	}
	
	static long breadthFS(int s1, ArrayList<ArrayList<Integer>> g, long [] arr1, int destination) {
		arr1[s1] = 1; 
//		int lastMarked = 0;
		ArrayList<Integer> q1 = new ArrayList<Integer>();
		q1.add(s1);
		while(!q1.isEmpty()) {
			s1 = q1.get(0);
			q1.remove(0);
//			if(toCheck[s1]==1) {
//				lastMarked = s1;
//			}
			for(int i = 0; i<g.get(s1).size(); i++) {
				int x1 = g.get(s1).get(i);
				if (arr1[x1] != 0)
                    continue;
                arr1[x1] = arr1[s1] + 1;
                q1.add(x1);
			}
		}
		return arr1[destination]-1;
	}
	
	static String fraction(long a, long b){
        long gcd = gcd(a<0?-a:a,b<0?-b:b);
        String as = Long.toString(a/gcd);
        String bs = Long.toString(b/gcd);
        return as+"/"+bs;
    }
	
	static long gcd(long l, long m)  { 
        if (l == 0) 
            return m; 
        else {
        	return gcd(m%l, l); 
        }
    } 
	
	static void checkBlock(int []block, int x, int y) {
		if(y!=x) {
			if(block[x]==1 && block[y]==1) {
				block[x] = 1;
			}
			else if(block[x]==1 && block[y]==0) {
				block[x] = 1;
			}
			else if(block[x]==0 && block[y]==1) {
				block[x] = 1;
			}
			else {
				block[x] = 0;
			}
		}
		else {
			
		}
	}
}

class Disjoint{
	int[] parent;
	
	Disjoint(int n){
		this.parent = new int[n];
		for(int i = 0; i<n; i++) {
			parent[i] = i;
		}
	}
	
	int find(int x){
        if(parent[x]!=x){
            parent[x]=find(parent[x]);
        }
        else {
        	
        }
        return parent[x];
    }
	
	void union(int x, int y) {
		x = find(x);
		y = find(y);
		parent[y] = x;
	}
}