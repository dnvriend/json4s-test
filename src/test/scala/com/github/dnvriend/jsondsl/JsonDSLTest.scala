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

package com.github.dnvriend.jsondsl

import com.github.dnvriend.TestSpec
import org.json4s.JsonDSL._
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.text.Document

/**
 * By importing `import org.json4s.JsonDSL._` primitive types will automagically
 * be mapped to JSON primitives
 */
class JsonDSLTest extends TestSpec {
  implicit val formats: Formats = DefaultFormats
  it should "Any seq produces JSON array" in {
    val jsonList: String = "[1,2,3]"
    val jsonAST: JValue = List(1, 2, 3)
    val document: Document = render(jsonAST) // pretty printing library support
    compact(document) shouldBe jsonList
    compact(render(Seq(1, 2, 3))) shouldBe jsonList
    compact(render(Vector(1, 2, 3))) shouldBe jsonList
    compact(render(Array(1, 2, 3).toList)) shouldBe jsonList
  }

  it should "Tuple2[String, A] produces field" in {
    val jsonAST = ("name" → "Joe")
    compact(render(jsonAST)) shouldBe """{"name":"Joe"}"""
  }

  it should "~ operator produces object by combining fields" in {
    val jsonAST = ("name" → "Joe") ~ ("age" → 35)
    compact(render(jsonAST)) shouldBe """{"name":"Joe","age":35}"""
  }
}
