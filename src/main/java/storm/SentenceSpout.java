package storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

/**
 * 向后端发送tuple数据流
 */
public class SentenceSpout extends BaseRichSpout {
    private SpoutOutputCollector spoutOutputCollector;
    private String[] sentences = {
            "my name is coki",
            "im a boy",
            "i have a pen",
            "my pen is made by wooden",
            "i love basketball"
    };

    private int index = 0;

    /**
     * open 在spout组件初始化的时候调用
     * 包含了 map： 一个包含storm的配置
     * topologyContext： topology对象
     * spoutOutputCollector：发送tuple的方法
     * @param map
     * @param topologyContext
     * @param spoutOutputCollector
     */
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector = spoutOutputCollector;
    }

    /**
     * 此方法为storm的核心方法，storm调用此方法在spoutOutputCollector上发送tuple
     */
    @Override
    public void nextTuple() {
        spoutOutputCollector.emit(new Values(sentences[index]));
        index ++;
        if (index >= sentences.length) {
            index = 0;
        }
        Utils.sleep(1);
    }

    /**
     * 每个spout和bolt都必须实现这个方法，告诉storm数据流包含的字段
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }
}
