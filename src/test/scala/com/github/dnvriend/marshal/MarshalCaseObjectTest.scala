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
import com.github.dnvriend.marshal.MarshalCaseObjectTest.Gender.{ Female, Male }
import com.github.dnvriend.marshal.MarshalCaseObjectTest.MemberLevel._
import com.github.dnvriend.marshal.MarshalCaseObjectTest._
import org.json4s.JsonAST.{ JNull, JString }
import org.json4s.native.Serialization._
import org.json4s.{ CustomSerializer, DefaultFormats, Extraction, Formats }

object MarshalCaseObjectTest {

  sealed trait Gender

  object Gender {
    case object Male extends Gender
    case object Female extends Gender
  }

  sealed trait MemberLevel
  object MemberLevel {
    case object Brass extends MemberLevel
    case object Silver extends MemberLevel
    case object Gold extends MemberLevel
    case object Titanium extends MemberLevel
    case object Platinum extends MemberLevel
  }

  case class Member(name: String, gender: Gender, memberLevel: MemberLevel)

  case object MemberLevelSerializer extends CustomSerializer[MemberLevel](_ ⇒ (
    {
      case JString(value) ⇒ value match {
        case "Brass"    ⇒ Brass
        case "Silver"   ⇒ Silver
        case "Gold"     ⇒ Gold
        case "Titanium" ⇒ Titanium
        case "Platinum" ⇒ Platinum
      }
      case JNull ⇒ null
    },
    {
      case obj: MemberLevel ⇒ JString(obj.getClass.getName.replace("com.github.dnvriend.marshal.MarshalCaseObjectTest$MemberLevel$", "").dropRight(1))
    }
  ))

  case object GenderSerializer extends CustomSerializer[Gender](_ ⇒ (
    {
      case JString(value) ⇒ value match {
        case "Male"   ⇒ Male
        case "Female" ⇒ Female
      }
      case JNull ⇒ null
    },
    {
      case obj: Gender ⇒ JString(obj.getClass.getName.replace("com.github.dnvriend.marshal.MarshalCaseObjectTest$Gender$", "").dropRight(1))
    }
  ))
}

class MarshalCaseObjectTest extends TestSpec {
  implicit val formats: Formats = DefaultFormats ++ List(MemberLevelSerializer, GenderSerializer)

  it should "marshal a case class containing case objects" in {
    val johnDoe = Member("John Doe", Gender.Male, MemberLevel.Brass)
    val janeDoe = Member("Jane Doe", Gender.Female, MemberLevel.Titanium)
    //    println(Extraction.decompose(johnDoe))
    //    println(Extraction.decompose(janeDoe))
    val johnDoeJson: String = write(johnDoe)
    val janeDoeJson: String = write(janeDoe)
    johnDoeJson shouldBe """{"name":"John Doe","gender":"Male","memberLevel":"Brass"}"""
    janeDoeJson shouldBe """{"name":"Jane Doe","gender":"Female","memberLevel":"Titanium"}"""
    read[Member](johnDoeJson) shouldBe johnDoe
    read[Member](janeDoeJson) shouldBe janeDoe
  }
}
