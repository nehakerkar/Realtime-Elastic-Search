package storm.starter.trident.project.countmin.state;

import storm.starter.trident.project.countmin.state.BloomFilter;
public class CustomStopWords
{
	public static BloomFilter<String> bf;

	public CustomStopWords(String[] sw)
	{
		double falsePositiveProbability = 0.4;
		int expectedSize = 100;
		bf = new BloomFilter<String>(falsePositiveProbability, expectedSize);

		for(int i=0; i<sw.length; i++)
			bf.add(sw[i]);
		System.out.println("Added Stop Words");
	}
}
