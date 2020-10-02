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

public class Prob-B3{
	public static void main(String[] args) throws IOException{
		Reader.init(System.in);
		int n = Reader.nextInt();
		int m = Reader.nextInt();
		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>(n);
		ArrayList<ArrayList<Long>> time = new ArrayList<ArrayList<Long>>(n);
		for(int i = 0; i<n; i++) {
			graph.add(new ArrayList<Node>());
			time.add(new ArrayList<Long>());
		}
		
		for(int i = 0; i<m; i++) {
			int a = Reader.nextInt()-1;
			int b = Reader.nextInt()-1;
			int c = Reader.nextInt();
			Node hi = new Node(b, c);
			graph.get(a).add(hi);
			Node ho = new Node(a, c);
			graph.get(b).add(ho);
		}
		for(int i = 0; i<n; i++) {
			int k = Reader.nextInt();
			for(int j = 0; j<k; j++) {
				int stop = Reader.nextInt();
				time.get(i).add((long)stop);
			}
		}
		long ans =  dijkstra(graph, time, n);
		System.out.println(ans);
	}
	
	static long dijkstra(ArrayList<ArrayList<Node>> graph, ArrayList<ArrayList<Long>> time, int n) {
		int[] visited = new int[n];
		long[] dist = new long[n];
		
		for(int i = 0; i<n; i++) {
			dist[i] = Long.MAX_VALUE;
			visited[i] = -1;
		}
		
		dist[0] = 0;	
		PriorityQueue<Node> q = new PriorityQueue<Node>(new Compare());
		Node go = new Node(0,0);
		q.offer(go);
		
		while(!q.isEmpty()) {
			Node hi = q.poll();
			
			int v = hi.vertex;
			long cost = hi.cost;
			
			if(visited[v]!=-1) continue;
			
//			int v = hi.vertex;
//			long cost = hi.cost;
//			
			if(v<n-1) {
				ArrayList<Long> list = time.get(v);
				int ind = list.indexOf(cost);
// it will see if the how many times the spider needs to wait.
				if(ind>-1) {
					while(ind<list.size() && list.get(ind)==cost) {
						cost+=1;
						ind+=1;
					}
				}
				else {}
			}
			else {}
			
			int i = 0;
			while(i<graph.get(v).size()) {
				Node a = graph.get(v).get(i);
				int index = a.vertex;
				
				if(cost+a.cost<dist[index]) {
					dist[index] = cost+a.cost;
				}
				else {
					
				}
				q.offer(new Node(index, dist[index]));
				i+=1;
			}
			visited[v] = 0;
		}
//		for(long i : dist) {
//			System.out.print(" "+i+" ");
//		}
		if(dist[n-1]==Long.MAX_VALUE) {
            return -1;
		}
		else {
			return dist[n-1];
		}
        
        
	}
}


// this node is the same for both purposes.
class Node{
	int vertex;
	long cost;
	
	public Node() {
		
	}
	
	Node(int vertex, long dist){
		this.vertex = vertex;
		this.cost = dist;
	}
}

class Compare implements Comparator<Node>{
    
    public int compare(Node arg0, Node arg1) {
       return (int) (arg0.cost-arg1.cost);
    }
}