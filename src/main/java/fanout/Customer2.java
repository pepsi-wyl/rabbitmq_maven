package fanout;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author by pepsi-wyl
 * @date 2022-08-06 18:11
 */

// 消费者2
public class Customer2 {
    public static void main(String[] args) throws IOException {

        // 获取连接
        Connection connection = RabbitMQUtils.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        // 通道绑定指定的交换机，不存在则创建  参数1：交换机名称  参数2.交换机类型必须为fanout(广播)
        channel.exchangeDeclare("logs", "fanout");

        // 临时队列
        String queue = channel.queueDeclare().getQueue();

        // 绑定交换机和队列
        channel.queueBind(queue, "logs", "");

        // 消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2-MSG: " + new String(body));
            }
        });

    }
}