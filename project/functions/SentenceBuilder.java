package storm.starter.trident.project.functions;
import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import twitter4j.Status;
/**
* Function that just emits the text from a tweet.
*/
@SuppressWarnings("serial")
public class SentenceBuilder extends BaseFunction {
@Override
public void execute(TridentTuple tuple, TridentCollector collector) {
        collector.emit(new Values(tuple.get(0)));

}
}
