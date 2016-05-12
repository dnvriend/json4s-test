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
import com.github.dnvriend.marshal.MarshalSequenceOfTupleTest.Foo
import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.{ Formats, NoTypeHints }

object MarshalSequenceOfTupleTest {
  case class Foo(myTuples: Seq[(Int, String)] = Seq((100, "one hundred"), (101, "one hundred and one"), (102, "one hundred and two")))
}

class MarshalSequenceOfTupleTest extends TestSpec {
  it should "marshal a sequence of tuple without type hints" in {
    implicit val formats: Formats = Serialization.formats(NoTypeHints)
    val myFoo = Foo()
    val json: String = write(myFoo)
    json shouldBe """{"myTuples":[{"_1":100,"_2":"one hundred"},{"_1":101,"_2":"one hundred and one"},{"_1":102,"_2":"one hundred and two"}]}"""
  }

  it should "unmarshal a sequence of tuple without type hints" in {
    implicit val formats: Formats = Serialization.formats(NoTypeHints)
    val json = """{"myTuples":[{"_1":100,"_2":"one hundred"},{"_1":101,"_2":"one hundred and one"},{"_1":102,"_2":"one hundred and two"}]}"""
    val foo = read[Foo](json)
    foo shouldBe Foo()
    foo.myTuples.head shouldBe (100, "one hundred")
    foo.myTuples.drop(1).head shouldBe (101, "one hundred and one")
    foo.myTuples.drop(2).head shouldBe (102, "one hundred and two")
  }
}
