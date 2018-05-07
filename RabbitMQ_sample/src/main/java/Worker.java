import java.io.IOException;
import java.lang.InterruptedException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;

public class Worker {
  private final static String TASK_QUEUE_NAME = "hello";

  public static void main(String... argv)
      throws IOException, InterruptedException, TimeoutException {
    Channel channel = Configuration.create();

    channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    channel.basicQos(1);  // add Massage acknowledgment

    final Consumer consumer = new DefaultConsumer(channel) {
      @Override public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
        String message = new String(body, "UTF-8");

        System.out.println(" [x] Received '" + message + "'");

        try {
          doWork(message);
        } catch (InterruptedException ie) {

        } finally {
          System.out.println(" [x] Done");
          channel.basicAck(envelope.getDeliveryTag(), false);  // add Massage acknowledgment
        }
      }
    };

    //boolean autoAck = true; // acknowledgment is covered below
    boolean autoAck = false;  // add Massage acknowledgment
    channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
  }

  private static void doWork(String task) throws InterruptedException {
    for (char ch: task.toCharArray()) {
        if (ch == '.') Thread.sleep(1000);
    }
  }
}
