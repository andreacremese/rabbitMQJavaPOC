# Proof of concept

POC for a system with RabbitMQ Exchage / queues.

With test for business logic in controllers

Install and start rabbitmq - `rabbitmq-server`.

Install and start memcached - `memcached`.

## sender

Sends two types of messages, depending on the number of args

* **one arg** deletes the element in cache matching the argument
* **two args** saves in the cache <Key, Valye> as <arg1, arg2>


## message receiver

Can receive two types of messages (update cache / delete cache)

Unit Tests on message consumers and controllers for 



# TODO

Integration test

Configuration file (equivalent of `web.config`)

Not all controllers will need storage injection, hence the `Subscription` enum to start the listeners may be a bit too stiff

test Future for cache