import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
public class Aggregate {

    public static void showUsage(){
        System.err.printf("Usage: java Aggregate <function> <aggregation column> <csv file> <group column 1> <group column 2> ...\n");
		System.err.printf("Where <function> is one of \"count\", \"count_distinct\", \"sum\", \"avg\"\n");	
    }
    public static void main (String [] args){
        if (args.length < 4){
            showUsage();
            return;
        }
            String [] parameters = new String [2];
            parameters[0] = args[0];
            parameters[1] = args [2];
           
            int len = args.length - 3;
            String [] groups = new String [len + 1];
            for (int i = 3; i< args.length;i++){
                groups [i-3] = args [i];
            }
            groups [len] = args [1];
            String [][] data = organize(parameters, groups); // get the columns necessary
            sort_data (data);
            for (int i = 0;i<groups.length-1;i++){
                System.out.print (groups[i] + ", ");
            }
            System.out.println (groups[groups.length-1] + "(" + parameters[0] + ")");
            avg_sum (data, parameters [0]);
        }   
    static String [][] organize (String [] parameters, String[] groups){
        BufferedReader br = null;
        try {
          br = new BufferedReader(new FileReader(parameters[1]));
        }catch (IOException e){
            System.err.println("Error reading file: " + parameters[1]);
            System.err.println("File may not in the working folder ");
            System.exit(0);
        }
        ArrayList <String[]> list = new ArrayList<String[]>();
        // only get what we need
        String [] buffer = null;
        try {
            buffer = br.readLine().split(",");
            int [] indeces = get_index (groups, buffer);
            String temp = "";
            while ((temp = br.readLine()) != null){
                String [] vol = new String [indeces.length];
                buffer = temp.split(",");
                int y = 0;
                for (int i : indeces){
                    vol[y] = buffer[i];
                    y++;
                }
                list.add(vol);   
            }
            String [][] final_Strings = new String[list.size()][indeces.length];
            for (int i = 0; i<list.size(); i++){
                final_Strings[i] = list.get(i);
            }
            return final_Strings;
        }catch (IOException e){
            System.err.print ("Header missing");
        }
        return null;
    }
    static int [] get_index (String [] groups, String [] buffer){
        int [] indeces = new int [groups.length];
        int j = 0;
        for (String s : groups){
            int i = Arrays.asList(buffer).indexOf(s);
            indeces [j] = i;
            j++; 
        }
        return indeces;
    }
    static void sort_data (String [][] data){
        Arrays.sort (data, new Comparator <String[]>(){
            @Override
            public int compare(final String[] entry1, final String[] entry2){
                String time1 = entry1[0];
                String time2 = entry2[0];
                if (time1.compareTo(time2) == 0){
                   for (int i = 1; i< data[0].length - 1;i++){
                        time1 = entry1[i];
                        time2 = entry2[i];
                        if (time1.compareTo(time2) != 0){
                            break;
                        }
                   }
                }
                return time1.compareTo(time2);
            }
        });
    }
    static void avg_sum (String [][] data, String function){
        DecimalFormat df = new DecimalFormat("###.#");
        ArrayList <String[]> list = new ArrayList<>();
        String temp [] = data[0];
        int last = data[0].length - 1;
        int counter = 1;
        for (int i = 1; i<data.length;i++){
            if (table_equals(temp, data[i])){
                double x1 = Double.parseDouble(temp[last]);
                double x2 = Double.parseDouble(data [i][last]); 
                temp [last] = Double.toString(x1+x2);
                counter ++;
            }else {
                if (function.equals("avg")){
                    double y1 = Double.parseDouble(temp[last]);
                    temp [last] = df.format(y1/counter);
                } else if (function.equals("count")){
                    temp [last] = Integer.toString(counter);
                }
                list.add(temp);
                temp = new String [data[0].length];
                temp = data[i];
                counter = 1;
            }
        }
        if (function.equals("avg")){
            double y1 = Double.parseDouble(temp[last]);
            temp [last] = df.format(y1/counter);
        } else if (function.equals("count")){
            temp [last] = Integer.toString(counter);
        }
        list.add(temp);
        display(list);
    }
    static boolean table_equals (String [] a1 , String[] a2){
        for (int i = 0; i< a1.length - 1 ; i++){
            String s1 = a1[i];
            String s2 = a2[i];
            if (!s1.equals(s2)){
                return false;
            }
        }
        return true;
    }
    static void display (List<String[]> list){
        for (int i = 0; i<list.size();i++){
            String [] temp = list.get(i);
            for (int j = 0; j<temp.length - 1;j++){
                System.out.print (temp[j] + ", ");
            }
            System.out.println (temp[temp.length-1]);
        }
    }
}