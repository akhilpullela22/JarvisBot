
import java.util.ArrayList;
import java.util.HashMap;

public class UltUser {
    private String name;
    private HashMap<String, Integer> ultsList;
    public UltUser(String name){
        this.name = name;
    }
    public void addUlt(String ult){
        ultsList.put(ult, 0);
    }
    public void addPoint(String ult){
        Integer OldValue = ultsList.get(ult);
        ultsList.replace(ult, OldValue, OldValue++);
    }
    


    
}
