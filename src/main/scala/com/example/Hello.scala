package com.example

import org.json4s.Xml._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.{Formats, JValue, NoTypeHints}

import scala.xml.NodeSeq

case class Person(name: String)

object Hello extends App {
  implicit val formats: Formats = Serialization.formats(NoTypeHints)

  val person = Person("Joe")                        // case class
  val jsonStr: String = write(person)               // case class to JSON String
  assert(jsonStr == """{"name":"Joe"}""")           //
  val jval: JValue = parse(jsonStr)                 // JValue from JSON String
  assert(jval.extract[Person] == person)            //
  val nodes: NodeSeq = toXml(jval)                  // NodeSeq from JValue
  assert(nodes.toString == "<name>Joe</name>")      //
  val jvalFromXml: JValue = toJson(nodes)           // JValue from NodeSeq
  assert(jvalFromXml.extract[Person] == person)     // JValue to case class
  
    def mapToJson(map: Map[String, Any]): String = write(map)

  val map: java.util.Map[String, String] = new java.util.HashMap()
  map.put("a", "b")
  map.put("c", "d")

  assert(mapToJson(Map("scala" -> "isCool!")) == """{"scala":"isCool!"}""")
  assert(mapToJson(Map("a" -> 1, "b" -> 2)) == """{"a":1,"b":2}""")
  assert(mapToJson(map.toMap) == """{"a":"b","c":"d"}""")
}

