package storm.starter.trident.project.countmin; 

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.state.StateFactory;
import storm.trident.testing.MemoryMapState;
import storm.trident.testing.Split;
import storm.trident.testing.FixedBatchSpout;
import backtype.storm.tuple.Values;

import storm.starter.trident.project.countmin.state.CustomStopWords;
import storm.starter.trident.project.countmin.state.BloomFilterFunction;

import storm.trident.operation.builtin.Count;

import storm.starter.trident.project.countmin.state.CountMinSketchStateFactory;
import storm.starter.trident.project.countmin.state.CountMinQuery;
import storm.starter.trident.project.countmin.state.CountMinSketchUpdater;
import storm.starter.trident.tutorial.functions.SplitFunction;

/**
 *@author: Preetham MS (pmahish@ncsu.edu)
 */


public class CountMinSketchStaticTopology {

	 public static StormTopology buildTopology( LocalDRPC drpc ) {

        TridentTopology topology = new TridentTopology();

        int width = 1500;
	int depth = 1000;
	int seed = 10;

    	FixedBatchSpout spoutFixedBatch = new FixedBatchSpout(new Fields("sentence"), 3,
			new Values("the cow jumped over the moon"),
			new Values("the man went to the store and bought some candy"),
			new Values("four score and seven years ago"),
			new Values("how many apples can you eat"),
			new Values("to be or not to be the person"))
			;
		spoutFixedBatch.setCycle(false);


		TridentState countMinDBMS = topology.newStream("tweets", spoutFixedBatch)
			.each(new Fields("sentence"), new Split(), new Fields("words"))
			.each(new Fields("words"),new BloomFilterFunction())
			.partitionPersist( new CountMinSketchStateFactory(depth,width,seed), new Fields("words"), new CountMinSketchUpdater())
			;


		topology.newDRPCStream("get_count", drpc)
			.each( new Fields("args"), new Split(), new Fields("query"))
			.stateQuery(countMinDBMS, new Fields("query"), new CountMinQuery(), new Fields("count"))
			.project(new Fields("query", "count"))
			;

		return topology.build();

	}


	public static void main(String[] args) throws Exception {
		Config conf = new Config();
        	conf.setDebug( false );
        	conf.setMaxSpoutPending( 10 );

		String sw[] = {"to","the", "a", "an"};
		CustomStopWords cs = new CustomStopWords(sw);
        	LocalCluster cluster = new LocalCluster();
        	LocalDRPC drpc = new LocalDRPC();
        	cluster.submitTopology("get_count",conf,buildTopology(drpc));

        	for (int i = 0; i < 5; i++) {

            		System.out.println("DRPC RESULT:"+ drpc.execute("get_count","to the apples"));
            		Thread.sleep( 1000 );
        	}

		System.out.println("STATUS: OK");
		System.out.println("neha "+CustomStopWords.bf.contains("apples"));
		//cluster.shutdown();
        	//drpc.shutdown();
	}
}
