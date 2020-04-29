package storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

/**
 * 程序入口
 */
public class StormMain {

    public static void main(String[] args) {
        String spoutTitle = "sentenceSpout";

        // 实例化bolt 和spout
        SentenceSpout sentenceSpout = new SentenceSpout();
        SplitSentenceBolt splitSentenceBolt = new SplitSentenceBolt();
        ReportBolt reportBolt = new ReportBolt();
        WordCountBolt wordCountBolt = new WordCountBolt();

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        // 注册一个spout并设置两个线程跑
        topologyBuilder.setSpout(spoutTitle, sentenceSpout, 2);
        // 注册一个bolt并订阅spout，shuffleGrouping用来订阅spout
        topologyBuilder.setBolt("splitSentenceBolt", splitSentenceBolt, 2).setNumTasks(4).shuffleGrouping(spoutTitle);
        // 注册一个wordCountBolt并订阅分词的splitSentenceBolt，只处理word字段。
        topologyBuilder.setBolt("wordCountBolt", wordCountBolt, 2).fieldsGrouping("splitSentenceBolt", new Fields("word"));
        topologyBuilder.setBolt("reportBolt", reportBolt).shuffleGrouping("wordCountBolt");

        Config config = new Config();
        LocalCluster localCluster = new LocalCluster();

        localCluster.submitTopology("topo", config, topologyBuilder.createTopology());

        Utils.sleep(10000);
        localCluster.killTopology("topo");
        localCluster.shutdown();
    }
}
