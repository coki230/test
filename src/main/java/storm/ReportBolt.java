package storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成报告，属于拓扑的最终节点，所以不需要继续发送数据
 */
public class ReportBolt extends BaseRichBolt {
    Map<String, Integer> integerMap = new HashMap<>();

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getStringByField("word");
        Integer num = tuple.getIntegerByField("num");
        integerMap.put(word, num);
        System.out.println("the result is " + word + " count is " + num);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    /**
     * 在关闭拓扑的时候回调用此方法，一般会关闭资源等动作
     * 但是storm拓扑到集群的时候不保证执行此方法，不建议在生产环境使用
     */
    @Override
    public void cleanup() {
        System.out.println("=========================RESULT===========================");
        integerMap.forEach((word, num) -> {
            System.out.println(word + "  " + num);
        });
        System.out.println("=========================RESULT===========================");
    }
}
