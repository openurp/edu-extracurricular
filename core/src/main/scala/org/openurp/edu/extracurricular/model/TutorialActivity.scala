package org.openurp.edu.extracurricular.model

import org.beangle.data.model.LongId
import org.openurp.edu.base.model.Project
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.base.model.Semester
import org.beangle.commons.collection.Collections
import scala.collection.mutable.Buffer
import org.beangle.commons.lang.time.HourMinute
import java.time.LocalDate

/**
 * 辅导活动
 */
class TutorialActivity extends LongId {

  var project: Project = _

  var semester: Semester = _

  var teacher: Teacher = _

  var subject: String = _

  var capacity: Int = _

  var stds: Buffer[TutoredStd] = Collections.newBuffer[TutoredStd]

  var date: LocalDate = _

  var beginAt: HourMinute = _

  var endAt: HourMinute = _
}