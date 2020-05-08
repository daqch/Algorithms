import java.io.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.PriorityQueue;


// Diego Aquino Chavez CSC 225 Assignment 3
// A word ladder implementation. Usin radix sort and Aggregation makes this implementation of O(m+n)
class Vertex  {
	LinkedList<Vertex> edge_list;
	LinkedList<String> path_list;
	String word;
	int marked;

	public Vertex(){
		edge_list = new LinkedList<>();
		this.marked = 0;
		path_list = new LinkedList<>();
	}

	public Vertex (String word){
		edge_list = new LinkedList<>();
		path_list = new LinkedList<>();
		this.word = word;
		this.marked = 0;
	}
	@Override
	public String toString(){
		return  word;
	}
	public void add_edge(Vertex v){
		edge_list.add(v);
	}
	public void mark_as_visited(){
		marked = 1;
	}
	public boolean was_visited(){
		return marked == 1;
	}
	public void add_path(String s){
		path_list.add(s);
	}
}
class Box {
	LinkedList<Vertex> source;
	String name;
	public Box (){
		source = new LinkedList<>();
		name = "";
	}

	public Box (Vertex init , String name){
		source = new LinkedList<>();
		this.name = name;
		source.add(init);
	}
	int size (){
		return source.size();
	}
	boolean equals (Box b){
		return name.equals(b.name);
	}
	void add(Box b){
		source.add(b.source.remove());
	}
	@Override
	public String toString(){
		return source.toString();
	}
}
/* Ladder.java
   CSC 225 - Summer 2018
   
   Starter code for Programming Assignment 3

   B. Bird - 06/30/2018
*/
public class Ladder {

	
	public static void showUsage(){
		System.err.printf("Usage: java Ladder <word list file> <start word> <end word>\n");
	}
	

	public static void main(String[] args){
		
		//At least four arguments are needed
		if (args.length < 3){
			showUsage();
			return;
		}
		String wordListFile = args[0];
		String startWord = args[1].trim();
		String endWord = args[2].trim();
		
		
		//Read the contents of the word list file into a LinkedList (requires O(nk) time for
		//a list of n words whose maximum length is k).
		//(Feel free to use a different data structure)
		BufferedReader br = null;
		LinkedList<String> words = new LinkedList<String>();
		try{
			br = new BufferedReader(new FileReader(wordListFile));
		}catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",wordListFile);
			return;
		}
		
		try{
			for (String nextLine = br.readLine(); nextLine != null; nextLine = br.readLine()){
				nextLine = nextLine.trim();
				if (nextLine.equals(""))
					continue; //Ignore blank lines
				//Verify that the line contains only lowercase letters
				for(int ci = 0; ci < nextLine.length(); ci++){
					//The test for lowercase values below is not really a good idea, but
					//unfortunately the provided Character.isLowerCase() method is not
					//strict enough about what is considered a lowercase letter.
					if ( nextLine.charAt(ci) < 'a' || nextLine.charAt(ci) > 'z' ){
						System.err.printf("Error: Word \"%s\" is invalid.\n", nextLine);
						return;
					}
				}
				words.add(nextLine);
			}
		} catch (IOException e){
			System.err.printf("Error reading file\n");
			return;
		}
		if (startWord.length() != endWord.length()){
			System.out.println ("No word ladder found");
			System.exit(0);
		}
// End of instructor's parser code
		
		
		// Make sure we are only taking the words with the same length. Else, radix sort will not work.
		int length = startWord.length();
		LinkedList<String> temp  =  new LinkedList<>();

		//O(n)
		for (String s : words){
			if (s.length() == length ){
				temp.add(s);
			}
		}
		words.clear();
		LinkedList<Box> boxes = new LinkedList<>();
		LinkedList<Vertex> vertex_set = new LinkedList<>();


		//O(nk) Words that differ by a character are equal if we replace it with a "*"
		// This way if we sort using radix sort we can apply aggregation and connect words that differ by a character.
		// The main idea of this implementation is create a Class box with an initial word in it(a referece to a vertex) and a name.
		//The wile loop runs n times and for each character of a vertex a box is created(k times with words of fixed length k) thus O(nk) 
		while (!temp.isEmpty()){
			Vertex v = new Vertex(temp.remove());
			vertex_set.add(v);
			StringBuilder s = new StringBuilder(v.word);
			for (int j = 0 ; j < length; j++){
				char prev = s.charAt(j);
				s.setCharAt(j, '*');
				Box b = new Box(v, s.toString());
				boxes.add(b);
				s.setCharAt(j, prev);
			}
		}
		int i = 0;
		// Convert to array to use Radix Sort
		Box [] w = new Box[boxes.size()];
		while (!boxes.isEmpty()){
			w[i] = boxes.remove();
			i++;
		}
		lsd(w);// Radix sort call

		boxes.clear();//Free memory

		// Because now the boxes are sorted. We can traverse the number of boxes (in the worst case the number of boxes is nk for words of k characters)
		// And then apply Aggregation to boxes of the same name, as each contain vertex that differ by a character, thus agrouping vertices.
		int curr = 0;
		for (i = 0 ; i <w.length - 1 ; i++){
			if (w[i].equals(w[i+1])){
				w[curr].add(w[i+1]);
			}else {
				curr = i + 1;
			}
		}
		// Remove boxes that contain no edges. this reduces drastically the number of boxes for a large number of disperse words.
		for (i = 0 ; i <w.length ; i++){
			if (w[i].size() > 1){
				boxes.add(w[i]);
			}
		}
		//O(E) The number of operations in making the edges is equal to number of edges of the graph
		while (!boxes.isEmpty()){
			Box te = boxes.remove();
			for (i = 0 ; i<te.source.size() - 1 ; i++){
				for (int j = 0 ; j < te.source.size() - i - 1 ; j++ ){
					te.source.get(i).add_edge(te.source.get(i+j+1));
					te.source.get(i+j+1).add_edge(te.source.get(i));
				}
			}
		}
		int index = 0 ;
		//O (nk) pass to an array, accesing works better this way.
		Vertex [] f_source = new Vertex[vertex_set.size()];
		for (i = 0 ; i<f_source.length; i++){
			f_source[i] = vertex_set.remove();
		}
		//O (nk) Find the pointers of the vertices that contain the start and edwords and then do bfs.
		int w1_found=0;
		int w2_found=0;
		for (i = 0 ; i < f_source.length;i++){
			if (f_source[i].word.equals(startWord)){
				w1_found++;
				break;
			}
			index++;
		}
		int index2 = 0;
		for (i = 0 ; i < f_source.length;i++){
			if (f_source[i].word.equals(endWord)){
				w2_found++;
				break;
			}
			index2++;
		}
		if (w1_found == 0 || w2_found == 0){
			System.out.println("No word ladder found");
			System.exit(0);
		}

		bfs(f_source[index]);
		//Print the path
		Stack<String> st = new Stack<>();
		for (String s: f_source[index2].path_list){
			st.push(s);
		}
		while(!st.isEmpty()){
			System.out.println(st.pop());
		}
		
		System.out.println(f_source[index2].word);

	}

	// O (n + m)
	static void bfs(Vertex src){
		LinkedList<Vertex> q = new LinkedList<>();
		src.mark_as_visited();
		q.add(src);
		while (!q.isEmpty()){
			src = q.poll();
			while (!src.edge_list.isEmpty()){
				Vertex v = src.edge_list.poll();
				if (!v.was_visited()){
					v.mark_as_visited();
					// Add the source vertice
					v.path_list.add(src.word);
					// And the source of the source
					v.path_list.addAll(src.path_list);
					q.addLast(v);
				}
			}
		}
		
	}
	//The following function is not of my complete authority. I used most of the code from https://www.cs.princeton.edu/~rs/AlgsDS07/18RadixSort.pdf slide number 11.
	public static void lsd(Box[] a){
		int N = a.length;
		int W = a[0].name.length();
		Box[] temp = new Box[N]; 
		for (int d = W-1; d >= 0; d--){
			int[] count = new int[256];
		   for (int i = 0; i < N; i++){
			  count[(a[i].name.charAt(d)) + 1]++;
		   }
		   for (int k = 1; k < 256; k++){
			  count[k] += count[k-1];
		   }
		   for (int i = 0; i < N; i++){
			temp[count[a[i].name.charAt(d)]++] = a[i];
		   }
		   for (int i = 0; i < N; i++){
			  a[i] = temp[i];
		   }
		}
 	}
}
