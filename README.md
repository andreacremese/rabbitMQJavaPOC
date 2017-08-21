# Proof of concept

POC for a system with RabbitMQ Exchage / queues.

With test for business logic in controllers

Install and start rabbitmq - `rabbitmq-server`.

Install and start memcached - `memcached`.

Connect to memcached - `telnet localhost 11211`.


The whole idea is to abstract away the registration of handler messages and whatnot, and allow the developer only to add controllers (with tests).



## sender

Sends two types of messages, depending on the number of args

* **one arg** deletes the element in cache matching the argument
* **two args** saves in the cache <Key, Valye> as <arg1, arg2>


## message receiver

Can receive two types of messages (update cache / delete cache)

Unit Tests on message consumers and controllers for 



# TODO

Remove casting in controllers DeleteCacheMessage m = (DeleteCacheMessage) msg without using reflection.java 


Rethink the injection of storage into controller - it is happening in the Subscription (and with reflection).
Amend the POC with `@Injected`

Switch receiver to the service

Move the configs to the web.xml

Integration test script that starts rabbitmq and memcached

Not all controllers will need storage injection, hence the `Subscription` enum to start the listeners may be a bit too stiff

test Future for cache?