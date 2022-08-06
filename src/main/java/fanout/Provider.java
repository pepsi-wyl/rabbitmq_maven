package fanout;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author by pepsi-wyl
 * @date 2022-08-06 17:54
 */

// 生产者
public class Provider {
    public static void main(String[] args) throws IOException {

        // 获取连接
        Connection connection = RabbitMQUtils.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        // 通道绑定指定的交换机，不存在则创建  参数1：交换机名称  参数2.交换机类型必须为fanout(广播)
        channel.exchangeDeclare("logs", "fanout");

        // 发布消息 到交换机
        channel.basicPublish("logs", "", null, "fanout type message".getBytes(StandardCharsets.UTF_8));

        // 关闭资源
        RabbitMQUtils.closeResource(channel, connection);

    }
}