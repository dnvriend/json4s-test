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

import com.github.dnvriend.{ Person, TestSpec }
import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.{ DefaultFormats, Formats, FullTypeHints, NoTypeHints, ShortTypeHints }

class MarshalPersonTest extends TestSpec {
  it should "be marshalled with DefaultFormats" in {
    implicit val formats: Formats = DefaultFormats // which uses NoTypeHints for the types
    val person = Person("John", "Doe", 30, married = false)
    val json: String = write(person)
    read[Person](json) shouldBe person
    json shouldBe """{"first":"John","last":"Doe","age":30,"married":false}"""
  }

  it should "be marshalled without type hints" in {
    implicit val formats: Formats = Serialization.formats(NoTypeHints)
    val person = Person("John", "Doe", 30, married = false)
    val json: String = write(person)
    read[Person](json) shouldBe person
    json shouldBe """{"first":"John","last":"Doe","age":30,"married":false}"""
  }

  it should "be marshalled with short type hints" in {
    implicit val formats: Formats = Serialization.formats(ShortTypeHints(List(classOf[Person])))
    val person = Person("John", "Doe", 30, married = false)
    val json: String = write(person)
    read[Person](json) shouldBe person
    json shouldBe """{"jsonClass":"Person","first":"John","last":"Doe","age":30,"married":false}"""
  }

  it should "be marshalled with full type hints" in {
    implicit val formats: Formats = Serialization.formats(FullTypeHints(List(classOf[Person])))
    val person = Person("John", "Doe", 30, married = false)
    val json: String = write(person)
    read[Person](json) shouldBe person
    json shouldBe """{"jsonClass":"com.github.dnvriend.Person","first":"John","last":"Doe","age":30,"married":false}"""
  }
}
