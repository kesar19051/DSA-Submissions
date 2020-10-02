import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** Class for buffered reading int and double VALUEs */
class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;
	public static Object init;

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



public class Prob-A1 {
//	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		Reader.init(System.in);
		int T = Reader.nextInt();
		for(int i = 0; i<T; i++) {
			int n = Reader.nextInt();
			int l = Reader.nextInt();
			int k = Reader.nextInt();
//			ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>(n);
//			ArrayList<ArrayList<Integer>> weight = new ArrayList<ArrayList<Integer>>(n);
//			ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
			int[][] cost = new int[n][n];
			for(int x = 0; x<n; x++) {
//				graph.add(new ArrayList<Integer>());
//				cost[i] = new ArrayList<Integer>();
				for(int y = 0; y<n; y++) {
					cost[x][y] = -9;
				}
			}
			for(int j = 0; j<l; j++) {
				int s = Reader.nextInt()-1;
				int t = Reader.nextInt()-1;
//				graph.get(s).add(t);
//				graph.get(t).add(s);
				cost[s][t] = 0;
				cost[t][s] = 0;
			}
			for(int j = 0; j<k; j++) {
				int u = Reader.nextInt()-1;
				int v = Reader.nextInt()-1;
				int d = Reader.nextInt();
				if(cost[u][v]!=0) {
					cost[u][v] = d;
					cost[v][u] = d;
				}
			}
			
			
			System.out.println(dijkstra(cost, n)); 
		}
	}
	
	
	static int dijkstra(int graph[][], int n) { 
		
		int[] distance = new int[n]; 
		
		int[] visited = new int[n]; 
		
		for (int i = 0; i < n; i++) { 
			distance[i] = Integer.MAX_VALUE; 
			visited[i] = -1; 
		} 

		distance[0] = 0; 
		for (int count = 0; count < n - 1; count++) { 
			int min = Integer.MAX_VALUE;
			int u = -1; 

			for (int i = 0; i < n; i++) 
				if (visited[i] == -1 && distance[i] <= min) { 
					min = distance[i]; 
					u = i; 
				} 

			visited[u] = 0; 
			
			for (int i = 0; i < n; i++) 
				if (visited[i]!=0 && graph[u][i] != -1) {
					if(distance[u] != Integer.MAX_VALUE)  {
						if(distance[u] + graph[u][i] < distance[i]) {
							distance[i] = distance[u] + graph[u][i]; 
					}
				}
			}
		} 

		return distance[n-1]; 
	} 

}

