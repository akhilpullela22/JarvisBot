
import java.util.ArrayList;
import java.util.HashMap;

public class TrackUser {
    private String name;
    private HashMap<String, Integer> ultsList = new HashMap<>();
    public TrackUser(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public void addUlt(String ult){
        ultsList.put(ult, 0);
    }
    public void addPoint(String ult){
        if (ultsList.containsKey(ult)){
            Integer OldValue = ultsList.get(ult);
            ultsList.replace(ult, OldValue, OldValue+1);
        }
        
    }
    public void removePoint(String ult){
        if (ultsList.containsKey(ult)){
            Integer OldValue = ultsList.get(ult);
            ultsList.replace(ult, OldValue, OldValue-1);
        }
        
    }
    public HashMap<String, Integer> getUlts(){
        return ultsList;
    }
    


    
}
