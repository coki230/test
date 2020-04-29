package storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class WordCountBolt extends BaseRichBolt {
    private OutputCollector outputCollector;

    Map<String, Integer> counts;

    /**
     * 建议所有实例化动作在prepare中实现，因为topology在部署的时候，拓扑结构发送的都是
     * 序列化的数据，所以在prepare方法中会对不可序列化的对象进行实例化的时候会序列化该实例
     * 在构造方法中可以对可序列化的对象实例化。
     * @param map
     * @param topologyContext
     * @param outputCollector
     */
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
        counts = new HashMap<>();
    }

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getStringByField("word");
        Integer integer = counts.get(word);
        if (integer == null) {
            integer = 0;
        }
        integer ++;
        counts.put(word, integer);
        outputCollector.emit(new Values(word, integer));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word", "num"));
    }
}
