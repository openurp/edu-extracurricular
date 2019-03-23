package org.openurp.edu.extracurricular.model

import java.time.LocalDate

import scala.collection.mutable.Buffer

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.time.HourMinute
import org.beangle.data.model.LongId
import org.openurp.base.model.Department
import org.openurp.edu.base.model.{ Classroom, Project, Semester }

/**
 * 讲座、报告、活动
 */
class Lecture extends LongId {

  var project: Project = _

  var semester: Semester = _

  var subject: String = _

  var teachers: String = _

  var depart: Department = _

  var date: LocalDate = _

  var beginAt: HourMinute = _

  var endAt: HourMinute = _

  var room: Option[Classroom] = _

  var location: Option[String] = None

  var capacity: Int = _

  var actual: Int = _

  var audiences: Buffer[Audience] = Collections.newBuffer[Audience]

}