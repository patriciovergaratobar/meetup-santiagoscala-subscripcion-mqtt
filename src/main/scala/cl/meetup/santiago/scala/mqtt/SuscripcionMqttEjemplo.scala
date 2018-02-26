package cl.meetup.santiago.scala.mqtt


import cl.meetup.santiago.scala.mqtt.model.Broker
import org.eclipse.paho.client.mqttv3._
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
  * Clase que muestra un ejemplo de suscripción a topic de un broker por mqtt con
  * la librería org.eclipse.paho.client.mqttv3.
  *
  * Created by pvergara.
  */
object SuscripcionMqttEjemplo {

  def main(args: Array[String]): Unit = {
    val broker = new Broker("tcp://localhost:1883", MqttClient.generateClientId)

    val mqttConexion: MqttClient = getMqttConection(broker)

    val topics = Array("cl/topicoA", "cl/topicoB", "meetup/+", "meetup/+/+")

    mqttListener(mqttConexion, topics)

  }

  /**
    * Método que se encarga de realizar la suscripción a topics del broker por Mqtt.
    *
    * @param mqttConexion Conexión al broker
    * @param topics Array String con los topicos que se suscribira
    */
  def mqttListener(mqttConexion: MqttClient, topics: Array[String]): Unit = {

    /**
      * Se realiza la suscripción a los topics.
      * */
    mqttConexion.subscribe(topics)

    /**
      * Se define el objeto con los callback de la suscripción.
      *
      */
    val callback = new MqttCallback {

      /**
        * Método que recibe los mensajes que llega a uno de los topics suscritos.
        * @param topic de donde proviene el mensaje.
        * @param message mensaje recibido.
        */
      override def messageArrived(topic: String, message: MqttMessage): Unit =
        println(s"Topic : $topic, Message : $message")


      /**
        * Metodo que indica cuando hay una perdida en la conexión
        * @param cause
        */
      override def connectionLost(cause: Throwable): Unit =
        println(s"Connection Lost by $cause")

      /**
        * Metodo que se llama cuando se ha completado la entrega de un mensaje.
        *
        * @param token
        */
      override def deliveryComplete(token: IMqttDeliveryToken): Unit =
        println("Completada :B")

    }

    /**
      * Se notifica a la conexión cuales son los callback que se utilizan en la suscripción.
      */
    mqttConexion.setCallback(callback)
  }

  /**
    * Metodo que se encarga de obtener la conexion al broker.
    *
    * @param broker Objeto con la información para construir la conexión.
    *
    * @return MqttClient con conexión activada.
    */
  def getMqttConection(broker: Broker) : MqttClient = {
    val persistence = new MemoryPersistence

    val clientMqtt = new MqttClient(broker.hostBroker, broker.identifier, persistence)

    clientMqtt.connect

    clientMqtt
  }
}
