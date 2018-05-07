import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class NewTask {
  private final static String QUEUE_NAME = "hello";
  private static String[] multiStr = {
    "Oya?",
    "This is anyone.",
    "is that"
  };

  public static void main(String... argv) throws IOException, TimeoutException {
    Channel channel = Configuration.create();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    // String message = getMessage(argv);
    String message = getMessage(multiStr);

    channel.basicPublish("", "hello", null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    Configuration.finish();
  }

  private static String getMessage(String[] strings){
    if (strings.length < 1) return "Hello World!";
    return joinStrings(strings, " ");
  }

  private static String joinStrings(String[] strings, String delimiter) {
    int length = strings.length;
    if (length == 0) return "";

    StringBuilder words = new StringBuilder(strings[0]);

    for (int i = 1; i < length; i++) {
        words.append(delimiter).append(strings[i]);
    }

    return words.toString();
  }
}
