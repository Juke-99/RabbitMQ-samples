RabbitMQ is Message Broker.  
RabbitMQ fulfills a role of post office.  
RabbitMQ stores and forwards binary blobs of data.  

## Jargon of some RabbitMQ
### producer
a program that sends massages

<div style="text-align:center;">![producer](./images/producer.png "producer")</div>

### queue
This is post box which lives inside RabbitMQ. Message is only stored inside a *queue*. Many *producers* can send message to one queue, and many *consumers* try to receive data from one queue.

<div style="text-align:center;">![queue](./images/queue.png "queue")</div>

### consumer
a program that mostly waits to receive messages

<div style="text-align:center;">![consumer](./images/consumer.png "consumer")</div>

## "Hello Workd" application of RabbitMQ in Java
In the first, two program write:
- a producer that sends s single message
- a consumer that receives messages and prints them out

### Sending
<div style="text-align:center;">![sending](./images/sending.png "sending")</div>

This class call our message publisher(sender) and our message consumer(receiver). The publisher will connect to RabbitMQ. After send single message, then exit.

### Receiving
Consumer is pushed messages from RabbitMQ.

<div style="text-align:center;">![receiving](./images/receiving.png "receiving")</div>

Setting up is the same as publisher. It open a connection and a channel, and declare the queue from which we're going to consume.

Note that we declare the queue here. Because we might start the consumer before the publisher, we want to make sure the queue exists before we try to consume messages from it.

This tell the server to deliver the messages from queue. Message is asynchronously pushed.

## Work Queues
<div style="text-align:center;">![work-queue](./images/python-two.png "work-queue")</div>

Work Queue that will be used to distribute time-consuming tasks among multiple workers.

Work Queues is avoid doing a resource-intensive task immediately and having to wait for it to complete. Instead schedule the task. It encapsulate a task as a message and send it to a queue. A worker process running in the background will pop the tasks and eventually execute the job.

## Message acknowledgment
RabbitMQ supports message acknowledgment. An acknowledgement is sent back by the consumer to tell RabbitMQ that a particular message has been received, processed and that RabbitMQ is free to delete it.

If a consumer dies (its channel is closed, connection is closed, or TCP connection is lost) without sending an acknowledgment, then RabbitMQ will understand that a message wasn't processed fully and will re-queue it.

When the consumer dies, RabbitMQ will redeliver the message.

Manual message acknowledgments are turned on by default.

Acknowledgement must be sent on the same channel the delivery it is for was received on. If attempts to acknowledge using a different channel, result in a channel-level protocol exception. 
