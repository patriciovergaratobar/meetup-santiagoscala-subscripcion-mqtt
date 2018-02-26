package cl.meetup.santiago.scala.mqtt

import cl.meetup.santiago.scala.mqtt.model.Broker
import org.eclipse.paho.client.mqttv3.{MqttClient, MqttException}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
  * Clase que muestra un ejemplo de envió de mensajes por mqtt con
  * la librería org.eclipse.paho.client.mqttv3.
  *
  * Created by pvergara.
  */
object EnvioMqttEjemplo {

  def main(args: Array[String]): Unit = {

    val broker = new Broker("tcp://localhost:1883", MqttClient.generateClientId)

    val mqttConexion: MqttClient = getMqttConection(broker)

    val menssage = "Hola meetup SantiagoScala!!!"

    mqttPublisher(mqttConexion, menssage)
  }

  /**
    * Metodo que se encarga de publicar el mensaje por Mqtt.
    *
    * @param mqttConexion Conexión al broker
    * @param messageSend Mensaje para publicar.
    */
  def mqttPublisher(mqttConexion: MqttClient, messageSend: String): Unit = {

    try {

      val topicMqtt = mqttConexion.getTopic("cl/topicoA")

      /**
        * Los niveles de calidad de entrega:
        *
        * 0 -> como máximo una vez: garantiza una entrega de con el mejor esfuerzo.
        *
        * 1 -> al menos una vez: garantizado que se entregará un mensaje al menos
        *      una vez. Pero el mensaje también se puede entregar más de una vez.
        *
        * 2 -> exactamente una vez: garantiza que cada mensaje sea recibido una sola
        *      vez por la contraparte.
        */
      val nivelEntregaQOs = 2

      /**
        * La retención se utiliza para que un cliente recién suscrito reciba el último mensaje de forma inmediata.
        * true -> retenido / false -> sin retención
        */
      val retencionMensaje = false

      /**
        * topicMqtt.publish -> Método para enviar el mensaje al broker usando el protocolo MQTT.
        *
        * Parámetro 1: messageSend.getBytes() -> Mensaje que se enviara.
        * Parámetro 2: nivelEntregaQOs -> el nivel de entrega al enviar el mensaje.
        * Parámetro 3: retencionMensaje -> indica si se desea retener el mensaje en el broker.
        */
      topicMqtt.publish(messageSend.getBytes(), nivelEntregaQOs, retencionMensaje)

      println("Mensaje publicado!!!")
    } catch {

      case e: MqttException => println("Exception : " + e)

    }
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
