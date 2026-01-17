### Centralized Log Aggregation System

A distributed logging system built using 
Spring Boot, Kafka, and 
Elasticsearch to collect, process, and analyze logs 
from multiple microservices.

#### Architecture
- User Service → Kafka
- Order Service → Kafka
- Kafka → Log Processor Service → Elasticsearch

#### Tech Stack
- Java 17
- Spring Boot
- Apache Kafka (KRaft mode)
- Elasticsearch
- Docker
- REST APIs

#### Features
- Centralized log collection from multiple microservices
- Asynchronous, high-throughput log ingestion using Kafka
- Trace-id based distributed request tracking
- Real-time log indexing into Elasticsearch
- Decoupled and scalable architecture

#### Services
- **User Service** –
- Generates user-related endpoint logs
- **Order Service** –
-  Generates order-related endpoint logs
- **Log Processor Service** –
- Consumes Kafka logs and stores them in Elasticsearch

#### Setup Instructions
(steps below 👇)
-----

Download Kafka from the official Apache website:
=====
https://www.apache.org/dyn/closer.cgi?path=/kafka/4.1.1/kafka_2.13-4.1.1.tgz
apache kafka dwld

Run kafka command to run server locally 
========
bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties
bin/kafka-server-start.sh config/kraft/server.properties


to genrate the new kafka cluster id if old one is not working 
=====
bin/kafka-storage.sh random-uuid
bin/kafka-storage.sh format \
  -t oSumVxNxTcO68neBgPXNrw \
  -c config/kraft/server.properties



Run docker instance for elasticsearch:
=============
docker rm -f elasticsearch                              
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 
-e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:9.2.2



#### Future Enhancements
- Kibana dashboards and alerts
- Dead-letter topic for failed logs
- Log levels (INFO, WARN, ERROR)
- Schema Registry for event versioning
