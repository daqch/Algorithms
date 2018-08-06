public class something {
    public static void main (String args[]){
    String word = "hello";
    int value = 0;

    for (int i = 0 ; i<word.length()  ; i++ ){
        Character x =  new Character (word.charAt(i));
        value += x.charValue();
        
    }
    System.out.println (value);
}
}