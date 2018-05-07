import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class Send {
  private final static String QUEUE_NAME = "hello";

  public static void main(String... argv) throws IOException, TimeoutException {
    Channel channel = Configuration.create();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    String message = "Hey!";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    Configuration.finish();
  }
}
