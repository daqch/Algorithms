import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
public class Aggregate {
    public static void main (String [] args){
        try {
            String [] parameters = new String [3];
            parameters[0] = args[0];
            parameters[1] = args[1];
            parameters[2] = args [2];
            if (args.length > 3){
                int len = args.length - 3;
                String [] groups = new String [len];
                for (int i = 3; i< args.length;i++){
                    groups [i-3] = args [i];
                }
                System.out.println (groups [0] + "" + groups.length);
            }else {
                String [] groups = null;
            }

        }catch (Exception e){
            System.err.println ("There's no function!!!");
        }
        organize(parameters, groups);
    }

    public void organize (String [] parameters, String[] groups){
        try {
            br = new BufferedReader(parameters[1]);
        }catch (IOException e){
            System.err.print("Error reading file \n" + parameters[1]);
        }
        ArrayList <String> list = new ArrayList<>();
        // only get what we need
    }
}