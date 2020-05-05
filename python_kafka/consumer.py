from kafka import KafkaConsumer
consumer = KafkaConsumer(
    'numbers',                                  # Change topic name here!!!!
    bootstrap_servers=['192.168.252.100:9092', '192.168.252.100:9093', '192.168.252.100:9094'],
    group_id='names-consumer-group'
)

for message in consumer:
    print(message)