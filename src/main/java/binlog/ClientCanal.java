package binlog;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;


public class ClientCanal {
    public static void main(String[] args) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("10.67.9.187", 3306), "example", "root",
                "123456");// AddressUtils.getHostIp(),
        connector.connect();
        connector.subscribe("*");
        connector.rollback();

        Message message = connector.getWithoutAck(1000);

    }
}
