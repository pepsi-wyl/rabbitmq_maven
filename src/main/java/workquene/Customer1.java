package workquene;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author by pepsi-wyl
 * @date 2022-08-06 16:53
 */

// 消费者1
public class Customer1 {
    public static void main(String[] args) throws IOException {

        // 获取连接
        Connection connection = RabbitMQUtils.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        // 绑定队列
        channel.queueDeclare("two", true, false, true, null);

        // 每一次只能消费一个消息
        channel.basicQos(1);

        // 消费消息  参数2:消息自动确认  true-消费者自动向rabbitmq确认消息消费  false-消费者不会自动向rabbitmq确认消息消费
        channel.basicConsume("two", false, new DefaultConsumer(channel) {
            @Override //最后一个参数 消息队列中的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                // 线程阻塞
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("消费者-1-MSG: " + new String(body));
                // 消息手动确认  参数1:手动确认消息标识  参数2:false 每次确认一个
                channel.basicAck(envelope.getDeliveryTag(), false);

            }
        });

    }
}