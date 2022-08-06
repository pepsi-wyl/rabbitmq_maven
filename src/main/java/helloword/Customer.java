package helloword;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author by pepsi-wyl
 * @date 2022-08-06 10:40
 */

// 消费者
public class Customer {

    // 消费消息
    public static void main(String[] args) throws IOException, InterruptedException {

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
         * 消费消息
         *
         * 参数1.队列名称
         * 参数2.消息的自动确认机制
         * 参数3.消费时候的回调函数
         */
        channel.basicConsume("one", true, new DefaultConsumer(channel) {
            @Override //最后一个参数 消息队列中的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                System.out.println("MSG: " + new String(body));
            }
        });

        // 主线程阻塞
        Thread.sleep(10000);

        // 关闭通道和连接
        RabbitMQUtils.closeResource(channel, connection);

    }
}