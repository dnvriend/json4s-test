/*
 * Copyright 2016 Dennis Vriend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dnvriend.marshal

import com.github.dnvriend.marshal.MarshalToXmlTest.Name
import com.github.dnvriend.{ Person, TestSpec }
import org.json4s.Xml._
import org.json4s.{ DefaultFormats, Extraction, Formats, JValue }

import scala.xml.NodeSeq

object MarshalToXmlTest {
  case class Name(name: String)
}

class MarshalToXmlTest extends TestSpec {
  it should "marshal person to XML" in {
    implicit val formats: Formats = DefaultFormats
    val person = Person("John", "Doe", 30, married = false)
    val jsonAST: JValue = Extraction.decompose(person)
    println(jsonAST)
    val ns: NodeSeq = <first>John</first><last>Doe</last><age>30</age><married>false</married>
    toXml(jsonAST).toString shouldBe ns.toString
  }

  it should "unmarshal to case class" in {
    implicit val formats: Formats = DefaultFormats
    val ns: NodeSeq = <name>Joe</name>
    val jsonAST: JValue = toJson(ns)
    jsonAST.extract[Name] shouldBe Name("Joe")
  }
}
