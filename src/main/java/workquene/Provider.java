package workquene;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author by pepsi-wyl
 * @date 2022-08-06 16:46
 */

// 生产者
public class Provider {
    public static void main(String[] args) throws IOException {

        // 获取连接
        Connection connection = RabbitMQUtils.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        // 绑定队列
        channel.queueDeclare("two", true, false, true, null);

        // 发布消息
        for (int i = 1; i <= 100; i++){
            channel.basicPublish("", "two", MessageProperties.PERSISTENT_TEXT_PLAIN, (i + "Hello RabbitMq").getBytes(StandardCharsets.UTF_8));
        }

        // 关闭资源
        RabbitMQUtils.closeResource(channel, connection);

    }
}