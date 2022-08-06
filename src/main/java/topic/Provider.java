package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author by pepsi-wyl
 * @date 2022-08-06 20:25
 */

// 生产者
public class Provider {
    public static void main(String[] args) throws IOException {

        // 获取连接
        Connection connection = RabbitMQUtils.getConnection();

        // 获取通道对象
        Channel channel = Objects.requireNonNull(connection).createChannel();

        // 通道绑定指定的交换机，不存在则创建  参数1：交换机名称  参数2.交换机类型必须为topic(动态)
        String exchangeName = "logs_topic";
        channel.exchangeDeclare(exchangeName, "topic");

        // 发布消息
        String routingKey1 = "save.user.delete";
        String routingKey2 = "save.user.delete.findAll";
        channel.basicPublish(exchangeName, routingKey1, null, ("这里是topic动态路由模型,routingKey: [" + routingKey1 + "]").getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, ("这里是topic动态路由模型,routingKey: [" + routingKey2 + "]").getBytes());

        // 关闭资源
        RabbitMQUtils.closeResource(channel, connection);

    }
}
