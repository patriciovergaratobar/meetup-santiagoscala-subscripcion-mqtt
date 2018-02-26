package cl.meetup.santiago.scala.mqtt.model

/**
  * Created by pvergara on 11-11-17.
  */
case class Broker(hostBroker: String, identifier: String) {
  override def toString: String = s"Broker $hostBroker and my identifier $identifier"
}