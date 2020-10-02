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

public class Prob-D{
	public static void main(String[] args) throws IOException{
		Reader.init(System.in);
		int n = Reader.nextInt();
		int h = Reader.nextInt();
		int x = Reader.nextInt();
		int[] marked = new int[h];
		int[] toCheck = new int[n];
		for(int i = 0; i<h; i++) {
			int hotspot = Reader.nextInt()-1;
			marked[i] = hotspot;
			toCheck[hotspot] = 1;
		}
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>(n);
		for(int i = 0; i<n; i++) {
			graph.add(new ArrayList<Integer>());
		}
		for(int i = 0; i<n-1; i++) {
			int u = Reader.nextInt()-1;
			int v = Reader.nextInt()-1;
			graph.get(u).add(v);
			graph.get(v).add(u);
		}

		int[] arr0 = new int[n];
		int node1 = breadthFS(0, graph, arr0, toCheck);
//		System.out.println(node1);
		int[] arr1 = new int[n];
		int node2 = breadthFS(node1, graph, arr1, toCheck);
//		System.out.println(node2);
		int[] arr2 = new int[n];
		breadthFS(node2, graph, arr2, toCheck);
		int count = 0;

	for(int i = 0; i<n; i++) {
		if(arr1[i]-1<=x && arr2[i]-1<=x) {
				count++;
		}
	}
	System.out.println(count);
	}
	
	static int breadthFS(int s1, ArrayList<ArrayList<Integer>> g, int [] arr1, int[] toCheck) {
		arr1[s1] = 1; 
		int lastMarked = 0;
		ArrayList<Integer> q1 = new ArrayList<Integer>();
		q1.add(s1);
		while(q1.size()!=0) {
			s1 = q1.get(0);
			q1.remove(0);
			if(toCheck[s1]==1) {
				lastMarked = s1;
			}
			for(int i = 0; i<g.get(s1).size(); i++) {
				int x1 = g.get(s1).get(i);
				if (arr1[x1] != 0)
                    continue;
                arr1[x1] = arr1[s1] + 1;
                q1.add(x1);
			}
		}
		return lastMarked;
	}
}