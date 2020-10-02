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


public class Prob-A {
	public static void main(String[] args) throws IOException {
		Reader.init(System.in);
		String s = Reader.next();
		char[] ch = s.toCharArray(); 
		
		boolean ans = check(ch);
		if(ans) System.out.println("YES");
		else System.out.println("NO");
	}
	
	static boolean check(char[] ch) {
		Stack stack = new Stack();
		stack.insert(ch[0]);
//		stack.print();
//		System.out.println();
		for(int i = 1; i<ch.length; i++) {
			char x = ch[i];
			if(stack.empty()) {
				if(i!=ch.length-1) {
					stack.insert(x);
					continue;
				}
			}
			else {
				if(x==stack.st[stack.top]) {
					stack.delete();
				}
				else {
					stack.insert(x);
				}
			}
			
		}
		if(stack.empty()) return true;
		return false;
	}

}

class Stack{
	int top = -1;
	char st[] = new char[100000];
	
	void insert(char c) {
		if(top==99999) return;
		st[++top] = c; return;
	}
	
	void delete() {
		if(top==-1) return ;
//		char x = st[top];
		top--;
		return;
	}
	
	boolean empty() {
		if(top==-1) return true;
		return false;
	}
	
	void print() {
		for(int i = 0; i<top; i++) {
			System.out.print(st[i]+" ");
		}
	}
}