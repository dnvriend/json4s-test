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

package com.github.dnvriend.rest

import java.net.URL

import com.github.dnvriend.{ Person, TestSpec }
import com.stackmob.newman._
import com.stackmob.newman.dsl._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.{ Formats, JValue, NoTypeHints }

class RestClientTest extends TestSpec {
  // put this in a safe place somewhere
  implicit val httpClient = new ApacheHttpClient
  implicit val formats: Formats = Serialization.formats(NoTypeHints)

  // for more info see: https://httpbin.org/
  val getUrl = new URL("https://httpbin.org/get")
  val postUrl = new URL("https://httpbin.org/post")

  case class TheResponse(origin: String, url: String)

  // for more info about newman see: https://github.com/megamsys/newman/

  "NewMan" should "get some data" in {
    val json: String = GET(getUrl).apply.futureValue.bodyString
    val jsonAST: JValue = parse(json)
    jsonAST.extract[TheResponse].url shouldBe "https://httpbin.org/get"
  }

  it should "post some data" in {
    val personJson: String = write(Person("John", "Doe", 30, married = false))
    val json: String = POST(postUrl).addBody(personJson).apply.futureValue.bodyString
    val jsonAST: JValue = parse(json)
    jsonAST.extract[TheResponse].url shouldBe "https://httpbin.org/post"
  }
}
