package storm.starter.trident.project.countmin.state;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

import storm.starter.trident.project.countmin.state.CustomStopWords;

/**
* Filter that just emits the text from a tweet.
*/

public class BloomFilterFunction extends BaseFilter {
@Override
public boolean isKeep(TridentTuple tuple) {

		String word = (String)tuple.getString(0);
	//System.out.println("function neha "+tuple.get(0)+" "+CustomStopWords.bf.contains(word));

	if(CustomStopWords.bf.contains(word)) //if BloomFilter contains the word, then it is a stop word. Return false.
		return false;
	else
		return true;

}
}
