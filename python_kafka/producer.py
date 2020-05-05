import time
from kafka import KafkaProducer


#REF: https://pypi.org/project/kafka-python/

producer = KafkaProducer(bootstrap_servers=['192.168.252.100:9092', '192.168.252.100:9093', '192.168.252.100:9094'])
producer.send('numbers', b'message from python prod')
time.sleep(3)       # Necessary otherwise the program will exit before sending the message
