#Spring XD Job module 

##Process an RMQ queue when it reaches a certain size

This example illustrates how to process a DLQ (or other queue) when it reaches a certain size. Presumably it could be
on a periodic trigger, say every 30 minutes to check the queue size and if `maxSize` is exceeded, dump the contents to a
file or persistent store and notify responsible parties.  A second instance of this job could run daily with `maxSize=1`
to guarantee any messages are processed at least within 24 hours. 

### Options:

   addresses     a comma separated list of 'host[:port]' addresses                 ${spring.rabbitmq.addresses}  java.lang.String
   password      the password to use to connect to the broker                      ${spring.rabbitmq.password}   java.lang.String
   username      the username to use to connect to the broker                      ${spring.rabbitmq.username}   java.lang.String
   maxSize       the maximum size of the queue before messages will be processed   1                             int
   queue         the queue from which messages will be processed                   <none>                        java.lang.String


