import java.io.*;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.Map;

class Vertex {
	LinkedList<String> edge_list;
	String word;

	public Vertex (String word, Vertex edge){
		edge_list = new LinkedList<>();
	}
}
/* Ladder.java
   CSC 225 - Summer 2018
   
   Starter code for Programming Assignment 3

   B. Bird - 06/30/2018
*/
public class Ladder{

	
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
		int size = words.size();
		int chars = words.get(0).length() ;
		String[] w = words.toArray(new String[words.size()]);

		Map<String, LinkedList<String>> m = new LinkedHashMap<>(size*chars);

		for ( int i = 0 ; i < size ; i++){
			int length = w[i].length();
			for (int j = 0 ; j < length ; j++){
				char [] box = w[i].toCharArray();
				box[j] = '_';
				String container = String.valueOf(box);
				//System.out.println(container);
				if (!m.containsKey(container)){
					LinkedList<String> list = new LinkedList<>();
					list.add(words.get(i));
					m.put(container, list);
				} else{
					m.get(container).add(w[i]);
				}
			}
		}
		
		LinkedList<String> k = new LinkedList<>();
		for (String key:m.keySet()){
			k.add(key);
		}

		for (String key : k){
			int s = m.get(key).size();
			if (s == 1 ){
				m.remove(key);
			}
		}
		int counter = 0;
		for (String key : m.keySet()){
			System.out.println(m.get(key).toString());
			counter++;
		}
		System.out.println(counter);
		/* Find a word ladder between the two specified words. Ensure that the output format matches the assignment exactly. */
	}

}