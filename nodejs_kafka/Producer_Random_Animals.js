const { Kafka } = require('kafkajs')            //npm install kafkajs
const Chance = require('chance')                //npm install chance

const chance = new Chance()

const kafka = new Kafka({
  clientId: 'my-producer',
  brokers: ['192.168.252.100:9092', '192.168.252.100:9093', '192.168.252.100:9094']  //Modify brokers here!!!!
})

const producer = kafka.producer()
const topic = 'numbers'             //Change topic name here !!!!!

const produceMessage = async () => {
    const value = chance.animal();
    console.log(value);

    try {
      await producer.send({
          topic,
          messages: [
            { value },
          ],
        })
    } catch (error) {
        console.log(error);
    }
}

const run = async () => {
  // Producing
  await producer.connect()
  setInterval(produceMessage, 1000)
}

run().catch(console.error)