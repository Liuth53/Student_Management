package utils;

import JDBC.javaBean.StudentInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.List;

public class ListToVector<T> {
    public static Vector<Vector> ListTOVector1(List list){
        Vector<Vector> vectors = new Vector<>();
        for (int i = 0; i < list.size(); i++) {

//            HashMap map = (HashMap) list.get(i);
            Vector vector = new Vector();
            vector.add(list.get(i));
//            vector.addAll(map.values());
            vectors.add(vector);
        }
        return vectors;
    }
}
