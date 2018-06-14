public class Aggregate {
    public static void main (String [] args){
        try {
            String function = args[0];
            System.out.println (function);
            String column = args[1];
            System.out.println (column);
            String in = args [2];
            System.out.println (in);
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
            System.out.println ("There's no function!!!");
        }

    }
}