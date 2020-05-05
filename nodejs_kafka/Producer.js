const { Kafka } = require('kafkajs')                                                //due to npm install kafkajs

const kafka = new Kafka({
  clientId: 'my-app',
  brokers: ['192.168.252.100:9092', '192.168.252.100:9093', '192.168.252.100:9094']  //Modify brokers here!!!!
})

const producer = kafka.producer()


const run = async () => {
  // Producing
  await producer.connect()
  await producer.send({
    topic: 'numbers',
    messages: [
      { value: 'Hello KafkaJS user!' },
    ],
  })

}

run().catch(console.error)