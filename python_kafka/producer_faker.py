import time

from kafka import KafkaProducer
from faker import Faker         # Faker, produce fake data
fake = Faker()
producer = KafkaProducer(bootstrap_servers=['192.168.252.100:9092', '192.168.252.100:9093', '192.168.252.100:9094'])
for _ in range(10):
    name = fake.name()
    producer.send('numbers', name.encode('utf-8'))    # Change here the topic name
    print(name)
time.sleep(3)