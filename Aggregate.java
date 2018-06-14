import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
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
            organize(parameters, groups);
    }

    static void organize (String [] parameters, String[] groups){
        BufferedReader br = null;
        try {
          br = new BufferedReader(new FileReader(parameters[1]));
        }catch (IOException e){
            System.err.print("Error reading file \n" + parameters[1]);
        }
        ArrayList <String[]> list = new ArrayList<String[]>();
        // only get what we need
        String [] buffer = null;
        try {
            buffer = br.readLine().split(",");
            int [] indeces = get_index (groups, buffer);
            String [] vol = new String [indeces.length];
            String temp = "";
            int y = 0;
            int x = 0;
            while ((temp = br.readLine()) != null){
                buffer = temp.split(",");
                y = 0;
                for (int i : indeces){
                    vol[y] = buffer[i];
                   y++;
                }
                list.add(x, vol);
                x++;    
            }
            System.out.println (x);
            System.out.println (Arrays.toString (list.get(5)));
        }catch (IOException e){
            System.err.print ("Header missing");
        } 

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
}