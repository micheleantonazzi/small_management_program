package small_management_program.controller;

import java.util.*;


public class DuplicateMap<K, V> {

    private Map<K, ArrayList<V>> map = new TreeMap<>();

    public void put(K k, V v) {
        if (map.containsKey(k)) {
            map.get(k).add(v);
        } else {
            ArrayList<V> arr = new ArrayList<>();
            arr.add(v);
            map.put(k, arr);
        }
    }

    public ArrayList<V> get(K k) {
        return map.get(k);
    }

    public V get(K k, int index) {
        return map.get(k).size()-1 < index ? null : map.get(k).get(index);
    }

    public Set<K> keySet(){
        return map.keySet();
    }

    @Override
    public String toString(){
        Set<K> keys = map.keySet();
        StringBuilder ret = new StringBuilder();
        for(Iterator<K> it = keys.iterator(); it.hasNext();){
            K key = it.next();
            ret.append(map.get(key).toString() + "\n");
        }
        return ret.toString();
    }
}

