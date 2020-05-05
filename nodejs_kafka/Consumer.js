const { Kafka } = require('kafkajs')

const kafka = new Kafka({
  clientId: 'my-consumer',
  brokers: ['192.168.252.100:9092', '192.168.252.100:9093', '192.168.252.100:9094']  //Modify brokers here!!!!
})

const consumer = kafka.consumer({ groupId: 'consumer-group' })
const topic = 'numbers'                                             //Change topic here !!!!

const run = async () => {
  // Consuming
  await consumer.connect()

  await consumer.subscribe({ topic })

  await consumer.run({
    eachMessage: async ({ partition, message }) => {
      console.log({
        partition,
        offset: message.offset,
        value: message.value.toString(),
      })
    },
  })
}

run().catch(console.error)