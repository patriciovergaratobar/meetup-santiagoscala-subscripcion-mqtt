
lazy val commonSettings = Seq(
  version := "1.0",
  organization := "cl.meetup.santiago.scala.mqtt",
  scalaVersion := "2.12.4",
  test in assembly := {}
)

lazy val app = (project in file("app")).
  settings(commonSettings: _*).
  settings(
    mainClass in Compile := Some("cl.meetup.santiago.scala.mqtt.MqttSubscriberScala"),
    mainClass in assembly := Some("cl.meetup.santiago.scala.mqtt.MqttSubscriberScala")
  )

libraryDependencies += "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.0.2"

resolvers += "MQTT Repository" at "https://repo.eclipse.org/content/repositories/paho-releases/"