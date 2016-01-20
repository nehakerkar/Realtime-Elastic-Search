Unzip and place the project folder in:
/home/leonia/apache-storm-0.9.3/examples/storm-starter/src/jvm/storm/starter/trident


Part B:
1) Export twitter keys.
export TWITTER_CONSUMER_KEY=<yourkey>
export TWITTER_CONSUMER_SECRET=<yourkey>
export TWITTER_ACCESS_TOKEN=<yourkey>
export TWITTER_ACCESS_SECRET=<yourkey>

2) Export Trident Variables.
export PATH="$HOME/apache-storm-0.9.3/bin:$PATH"
export STORM_HOME="$HOME/apache-storm-0.9.3"
export TRIDENT_STARTER=\ "$STORM_HOME/examples/storm-starter"
export TRIDENT_SRC=\ "$TRIDENT_STARTER/src/jvm/storm/starter/trident/project"
export TRIDENT_TARGET=\ "$TRIDENT_STARTER/target"
export TRIDENT_CLASSES=\ "$TRIDENT_TARGET/classes"
export JAVA_MAIN_CLASS_PATH=\ "storm.starter.trident.project"


3) cd $HOME
   cd elasticsearch-1.4.2/
   ./bin/elasticsearch -d -p pid_es.txt

4) cd $TRIDENT_STARTER

5) storm jar target/storm-starter-0.9.3-jar-with-dependencies.jar \storm.starter.trident.project.RealTimeElasticSearchTopology >>output.txt
cat output.txt | grep "DRPC RESULT"

6) curl -XPOST 'http://localhost:9200/_shutdown'


PART C:
1) Export twitter keys.
export TWITTER_CONSUMER_KEY=<yourkey>
export TWITTER_CONSUMER_SECRET=<yourkey>
export TWITTER_ACCESS_TOKEN=<yourkey>
export TWITTER_ACCESS_SECRET=<yourkey>

2) Export Trident Variables.
export PATH="$HOME/apache-storm-0.9.3/bin:$PATH"
export STORM_HOME="$HOME/apache-storm-0.9.3"
export TRIDENT_STARTER=\ "$STORM_HOME/examples/storm-starter"
export TRIDENT_SRC=\ "$TRIDENT_STARTER/src/jvm/storm/starter/trident/project"
export TRIDENT_TARGET=\ "$TRIDENT_STARTER/target"
export TRIDENT_CLASSES=\ "$TRIDENT_TARGET/classes"
export JAVA_MAIN_CLASS_PATH=\ "storm.starter.trident.project"

3) cd $TRIDENT_STARTER

4) storm jar target/storm-starter-0.9.3-jar-with-dependencies.jar \ storm.starter.trident.project.countmin.CountMinSketchTopology >>output.txt
cat output.txt | grep "DRPC RESULT"


For top-k, I have assumed k=10.


Files Modified:
storm.starter.trident.project.RealTimeElasticSearchTopology
storm.starter.trident.project.countmin.CountMinSketchTopology.java
storm.starter.trident.project.countmin.CountMinSketchStaticTopology.java
storm.starter.trident.project.countmin.state.BloomFilterFunction.java
storm.starter.trident.project.countmin.state.CountMinSketchUpdater.java
storm.starter.trident.project.countmin.state.CountMinQuery.java
storm.starter.trident.project.countmin.state.CustomStopWords.java
storm.starter.trident.project.countmin.state.BloomFilter.java —> Taken from “https://github.com/magnuss/java-bloomfilter”

CountMinSketchStaticTopology.java can be used for testing the top-k filter on fixed batch spout as well.
