version: '3.7'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.0.1
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:7.0.1
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
      - "9093:9093"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://kafka:9093
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_CREATE_TOPICS: "integrationsvc.confirmpayment.generate.response:1:1"

#  ksqldb-server:
#    image: confluentinc/ksqldb-server:latest
#    depends_on:
#      - kafka
#    ports:
#      - "8088:8088"
#    environment:
#      KSQL_CONFIG_DIR: "/etc/ksqldb"
#      KSQL_BOOTSTRAP_SERVERS: kafka:9093
#      KSQL_KSQL_SERVICE_ID: "ksql-service"
#      KSQL_LISTENERS: "http://0.0.0.0:8088"