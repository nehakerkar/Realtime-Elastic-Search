package storm.starter.trident.project.countmin.state;

import storm.trident.state.BaseStateUpdater;
import storm.trident.tuple.TridentTuple;
import storm.trident.operation.TridentCollector;
import java.util.List;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 *@author: Preetham MS (pmahish@ncsu.edu)
 */

public class CountMinSketchUpdater extends BaseStateUpdater<CountMinSketchState> {

	public static Map<String,Integer> kmap = new LinkedHashMap<String,Integer>(); //Contains the top-k words
	
    public void updateState(CountMinSketchState state, List<TridentTuple> tuples, TridentCollector collector) {
        List<Long> ids = new ArrayList<Long>();
        List<String> locations = new ArrayList<String>();
        for(TridentTuple t: tuples) {
            //ids.add(t.getLong(0));
            //locations.add(t.getString(1));

            state.add(t.getString(0),1);
	
		String myhash = t.getString(0);
            	    	if(kmap.containsKey(myhash))
            	    		kmap.put(myhash, kmap.get(myhash)+1);
            	    	else
			{
				if(kmap.size()==10)//if sizeof map exceeds k=10 replace smallestcount with new element
            			{
            				String minkey="";
            				int mincount=Integer.MAX_VALUE;
            				Iterator<Map.Entry<String, Integer>> it = kmap.entrySet().iterator();
        	    			while(it.hasNext())
        	    			{
        	    				Map.Entry<String, Integer> i = it.next();
        	    				if(i.getValue()<mincount)
        	    				{
        	    					minkey = i.getKey();
        	    					mincount = i.getValue();
        	    				}
        	    			}

        	    			kmap.remove(minkey);
        	    			kmap.put(myhash, 1);
            			}
				else
					kmap.put(myhash, 1);

			}
		kmap=MapUtil.sortByValue(kmap);
            	    	
	}
        
    }
}
	class MapUtil
{
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o2.getValue()).compareTo(o1.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
