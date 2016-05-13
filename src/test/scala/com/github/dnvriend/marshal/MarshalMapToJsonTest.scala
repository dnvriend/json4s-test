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

import com.github.dnvriend.TestSpec
import com.github.dnvriend.marshal.MarshalMapToJsonTest.{ AB, CountAB, Scala }
import org.json4s.native.Serialization._
import org.json4s.{ DefaultFormats, Formats }

object MarshalMapToJsonTest {
  case class AB(a: String, c: String)
  case class Scala(scala: String)
  case class CountAB(a: Int, b: Int)
}

class MarshalMapToJsonTest extends TestSpec {
  implicit val formats: Formats = DefaultFormats // which uses NoTypeHints for the types

  it should "marshal a map to JSON" in {
    write(Map("a" → "b", "c" → "d")) shouldBe """{"a":"b","c":"d"}"""
    write(Map("scala" → "isCool!")) shouldBe """{"scala":"isCool!"}"""
    write(Map("a" → 1, "b" → 2)) shouldBe """{"a":1,"b":2}"""
  }

  it should "unmarshal json to Map" in {
    read[Map[String, Any]]("""{"a":"b","c":"d"}""") shouldBe Map("a" → "b", "c" → "d")
    read[Map[String, Any]]("""{"scala":"isCool!"}""") shouldBe Map("scala" → "isCool!")
    read[Map[String, Any]]("""{"a":1,"b":2}""") shouldBe Map("a" → 1, "b" → 2)
  }

  it should "unmarshal json to a case class" in {
    read[AB]("""{"a":"b","c":"d"}""") shouldBe AB(a = "b", c = "d")
    read[Scala]("""{"scala":"isCool!"}""") shouldBe Scala(scala = "isCool!")
    read[CountAB]("""{"a":1,"b":2}""") shouldBe CountAB(a = 1, b = 2)
  }
}
