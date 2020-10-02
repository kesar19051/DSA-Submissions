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

public class Prob-C3 {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int n = Reader.nextInt();
        int s = Reader.nextInt();
        int q = Reader.nextInt();
        int e = Reader.nextInt() - 1;
        int[][] tunnels = new int[2][n-1];
        ArrayList<ArrayList<Integer>> go = new ArrayList<ArrayList<Integer>>(n);
        for(int i = 0; i<n; i++) {
        	go.add(new ArrayList<Integer>());
        }
        for(int i = 0; i<n-1; i++) {
            int u = Reader.nextInt()-1;
            int v = Reader.nextInt()-1;
            edge(go, u, v);
            int w1 = Reader.nextInt();
            tunnels[0][i] = u;
            tunnels[1][i] = v;
        }

//        go.printGraph();

        int[] supply = new int[s];
        for(int i = 0; i<s; i++) {
            int x = Reader.nextInt()-1;
            supply[i] = x;
        }
        
        int pre[] = new int[n]; 
        int post[] = new int[n]; 
        int vis[] = new int[n]; 
        
        depth(go, e, pre, post, vis); 

        for(int i = 0; i<q; i++) {
            int index = Reader.nextInt()-1;
            int source = Reader.nextInt()-1;
            int urem = tunnels[0][index];
            int vrem = tunnels[1][index];
			if(pre[vrem]>pre[urem]) {
				int child = vrem;
				boolean ans = subTree(pre, post, child, source);
				if(ans) System.out.println(0);
				else System.out.println("escaped");
			}
			else {
				int child = urem;
				boolean ans = subTree(pre, post, child, source);
				if(ans) System.out.println(0);
				else System.out.println("escaped");
			}
        }
    }
    
    static void edge(ArrayList<ArrayList<Integer>> g, int u, int v) {
    	g.get(u).add(v);
    	g.get(v).add(u);
    }
    
    static int time = 1; 
    
    static boolean subTree(int[] pre, int[] post, int child, int source) {
    	if(pre[source] >= pre[child] && post[source] <= post[child]) {
    		return true;
    	}
    	return false;
    }
    
    static void depth(ArrayList<ArrayList<Integer> > g, int u, int pre[], int post[], int vis[]) { 
  
        pre[u] = time; 
        time+=1; 
        vis[u] = 1; 
        ArrayList<Integer> list = g.get(u);
        for(int i = 0; i<g.get(u).size();i++) {
        	int v = list.get(i);
            if (vis[v] == 0) {
                depth(g, v, pre, post, vis); 
            }
        } 
        post[u] = time; 
        time+=1; 
    } 
    
}