import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.*;

public class Aggregate {
	public static void main (String [] args) {

		String source_File = args [2];
		String aggregate_function = args [0];
		String aggregation_column = args[1];
		LinkedList<String> group_by_clauses = new LinkedList<String>();

		for (int i = 3; i < args.length ; i++){
			group_by_clauses.add(args[i]);
		}
		
		System.out.println(group_by_clauses);
		
		try {
	           Scanner in = new Scanner (new FileReader(source_File)).useDelimiter("\n");
				System.out.println("File open successful!");
				ArrayList<String[]> inputFile = new ArrayList<String[]>();
				String [] columns = in.nextLine().split(",");
				LinkedList<String> columnss = new LinkedList<String> 
				System.out.println(Arrays.toString(columns));
				for (int i = 0; i < columns.length; i++){
					group_by_clauses.contains(o)
				}

			// we only want the columns where the information is 
			while (in.hasNext()) {
    	 	inputFile.add(in.nextLine().split(","));
			}
			// convert our list to a String array.
			String[][] array = new String[inputFile.size()][0];
			inputFile.toArray(array);
			
			// at this point the data is already saved in a 2d array
			// all the group by clauses should be sorted before doing the aggregate method
			sort_data (array);
			//perform (aggregate_function);
			System.out.println(Arrays.deepToString(array));
			
	            
		} catch (Exception e) {
				System.out.println("Could not open or find file");
		}

	}

	public static void sort_data (String [][] data){
		// If "group by" will be implemented it should be necessary to sort what is given otherwise
		// find the index of the column to be sorted
		//find the index of the column to sort
		Arrays.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[1];
				final String time2 = entry2[1];
				if (time1.equals(time2)){
					String extra1 = entry1[2];
					String extra2 = entry2[2];
					return extra2.compareTo(extra1);
				}else{
				return time2.compareTo(time1);
				}
			}
		});

				
	}
	public static String[][] perform (String[][] data ){
		return null;

	}
}