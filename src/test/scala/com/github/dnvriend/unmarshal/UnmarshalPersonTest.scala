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

package com.github.dnvriend.unmarshal

import com.github.dnvriend.{ Person, TestSpec }
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.{ Formats, FullTypeHints, JValue, NoTypeHints, ShortTypeHints }

class UnmarshalPersonTest extends TestSpec {
  "Person" should "be unmarshalled without type hints" in {
    implicit val formats: Formats = Serialization.formats(NoTypeHints)
    val json = """{"first":"John","last":"Doe","age":30,"married":false}"""

    // convert to AST and then extract
    val jsonAST: JValue = parse(json)
    jsonAST.extract[Person] shouldBe Person("John", "Doe", 30, married = false)

    // read the json string and convert
    read[Person](json) shouldBe Person("John", "Doe", 30, married = false)
  }

  it should "be unmarshalled with short type hints" in {
    implicit val formats: Formats = Serialization.formats(ShortTypeHints(List(classOf[Person])))
    val json = """{"jsonClass":"Person","first":"John","last":"Doe","age":30,"married":false}"""

    // convert to AST and then extract
    val jsonAST: JValue = parse(json)
    jsonAST.extract[Person] shouldBe Person("John", "Doe", 30, married = false)

    // read the json string and convert
    read[Person](json) shouldBe Person("John", "Doe", 30, married = false)
  }

  it should "be unmarshalled with full type hints" in {
    implicit val formats: Formats = Serialization.formats(FullTypeHints(List(classOf[Person])))
    val json = """{"jsonClass":"com.github.dnvriend.Person","first":"John","last":"Doe","age":30,"married":false}"""

    // convert to AST and then extract
    val jsonAST: JValue = parse(json)
    jsonAST.extract[Person] shouldBe Person("John", "Doe", 30, married = false)

    // read the json string and convert
    read[Person](json) shouldBe Person("John", "Doe", 30, married = false)
  }
}
