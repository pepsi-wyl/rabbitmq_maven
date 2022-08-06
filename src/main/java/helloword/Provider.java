package helloword;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author by pepsi-wyl
 * @date 2022-08-06 9:42
 */

// 生产者
public class Provider {

    // 生产消息
    public static void main(String[] args) throws IOException {

        // 获取连接
        Connection connection = RabbitMQUtils.getConnection();

        // 获取通道对象
        Channel channel = Objects.requireNonNull(connection).createChannel();

        /**
         * 通道绑定消息队列
         *
         * 参数1.queue 队列名称  队列不存在则自动创建
         * 参数2.durable 队列是否要持久化,仅仅只是队列持久化  true持久化   false不持久化
         * 参数3.exclusive 是否独占队列      true独占     false不独占
         * 参数4.autoDelete 消费完成是否立即删除队列,消费者完成必须断开连接     true删除     false不删除
         * 参数5.额外配置
         */
        channel.queueDeclare("one", true, false, true, null);

        /**
         * 发布消息
         *
         * 参数1.交换机名称
         * 参数2.队列名称  发布的队列
         * 参数3.传递消息额外配置   MessageProperties.PERSISTENT_TEXT_PLAIN 消息持久化
         * 参数4.消息具体内容
         */
        channel.basicPublish("", "one", MessageProperties.PERSISTENT_TEXT_PLAIN, "Hello RabbitMq".getBytes(StandardCharsets.UTF_8));

        // 关闭通道和连接
        RabbitMQUtils.closeResource(channel, connection);

    }
}