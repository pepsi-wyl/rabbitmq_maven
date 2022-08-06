package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author by pepsi-wyl
 * @date 2022-08-06 10:59
 */

// RabbitMQUtils 工具类
public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;

    private static Properties properties;

    // 静态加载 只加载一次
    static {

        // 获取RabbitMQ配置
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/RabbitMQ.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建连接工厂
        connectionFactory = new ConnectionFactory();
        // 设置RabbitMQ主机
        connectionFactory.setHost(properties.getProperty("RabbitMQHost"));
        // 设置RabbitMQ端口
        connectionFactory.setPort(Integer.parseInt(properties.getProperty("RabbitMQPort")));
        // 设置RabbitMQ虚拟主机
        connectionFactory.setVirtualHost(properties.getProperty("RabbitMQVirtualHost"));
        // 设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername(properties.getProperty("RabbitMQUsername"));
        connectionFactory.setPassword(properties.getProperty("RabbitMQPassword"));

    }

    // 获取连接
    public static Connection getConnection() {
        try {
            // 获取连接对象
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 关闭通道和连接
    public static void closeResource(Channel channel, Connection conn) {
        try {
            if (channel != null) channel.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 测试方法
    public static void main(String[] args) throws IOException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        closeResource(channel, connection);
    }

}