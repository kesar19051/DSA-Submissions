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

public class Prob-C1 {
    public static void main(String[] args) throws IOException {
        Reader.init(System.in);
        int n = Reader.nextInt();
        int s = Reader.nextInt();
        int q = Reader.nextInt();
        int e = Reader.nextInt() - 1;
        int[][] tunnels = new int[2][n-1];
        Graph go = new Graph(n, e);

        long[][] weight = new long[n][n];
        for(int i = 0; i<n-1; i++) {
            int u = Reader.nextInt()-1;
            int v = Reader.nextInt()-1;

            int w1 = Reader.nextInt();
            long w = (long) w1;
            
            restore(go, u, v, weight, w);
            tunnels[0][i] = u;
            tunnels[1][i] = v;
        }

//        go.printGraph();

        int[] supply = new int[s];
        for(int i = 0; i<s; i++) {
            int x = Reader.nextInt()-1;
            supply[i] = x;
            go.g.get(x).ifSupply = true;
        }

        for(int i = 0; i<q; i++) {
            int index = Reader.nextInt()-1;
            int source = Reader.nextInt()-1;
            int urem = tunnels[0][index];
            int vrem = tunnels[1][index];
//			System.out.println("urem = "+urem);
//			System.out.println("vrem = "+vrem);
            go.del(urem, vrem);
//            go.printGraph();
            long wrem = weight[urem][vrem];
            weight[urem][vrem] = 0;
            weight[vrem][urem] = 0;

            go.breadth(source, n, s, weight, supply);

            restore(go, urem, vrem, weight, wrem);
        }
    }
    
    static void restore(Graph g, int u, int v, long[][] arr, long w) {
    	g.edge(u, v);
    	arr[v][u] = w;
    	arr[u][v] = w;
    }
    
}


class Graph{
	
	class Node {
	    int vertex;
	    ArrayList<Integer> connect = new ArrayList<Integer>();
	    boolean ifSupply = false;
//	    boolean found = false;


	    Node(int d){
	        this.vertex = d;
	    }
	}
	
    ArrayList<Node> g = new ArrayList<Node>();
    int escape;

    Graph(int n, int e){
        for(int i = 0; i<n; i++) {
            Node hi = new Node(i);
            this.g.add(hi);
        }
        this.escape = e;
    }

    void edge(int u, int v) {
        g.get(u).connect.add(v);
        g.get(v).connect.add(u);
    }

    void del(int u, int v) {
        g.get(u).connect.remove(new Integer(v));
        g.get(v).connect.remove(new Integer(u));
    }
    
    void breadth(int source, int n, int s, long[][] weight, int[] supply) {
        int[] visited = new int[n];
        long[] distance = new long[n];
        ArrayList<Integer> q = new ArrayList<Integer>();

        for(int i = 0; i<n; i++) {
            distance[i]-=2;
        }

        visited[source] = 1;
        q.add(source);
        distance[source] = 0;

        while(q.size()!=0) {
            source = q.get(0);
//            System.out.println(source);
            q.remove(0);

            if(source==this.escape) {
                System.out.println("escaped");
                return;
            }
//            System.out.println(g.get(source).connect);
            int k = 0;
            while(k<g.get(source).connect.size()) {
            	int temp = g.get(source).connect.get(k);
                if(visited[temp]==0) {
                    
                    distance[temp] = distance[source] + weight[source][temp];
                    q.add(temp);
                    visited[temp] = 1;
                }
                k++;
            }
        }

        long ans = (long) (Math.pow(2, 63)-1);
        for(int i=0;i<s;i++) {
            int tmpnd = supply[i];
            if(distance[tmpnd] == -2){
                continue;
            }
            if(ans>distance[tmpnd]){
                ans = distance[tmpnd];
            }
            
            
        }

        if(ans != (long)(Math.pow(2, 63)-1) ){
            System.out.println(ans);
        }
        else {
            System.out.println("oo");
        }

    }
}

//Took help of Keshav Gambhir in the bfs