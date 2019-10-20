package localhost.android.network;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class NetworkServiceV2<T> {
    public  T  ConvertResponse(T type,String body) {
        try {
            org.json.JSONObject obj = new org.json.JSONArray(body).getJSONObject(0);
            Field[] f = type.getClass().getDeclaredFields();
            for (Field field : f) {
                if(obj.has((field.getName()))){
                    System.out.println("[response-converter]found:"+field.getName());
                    field.setAccessible(true);
                    field.set(type,obj.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
return type;
    }
}
