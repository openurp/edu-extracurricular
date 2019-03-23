package org.openurp.edu.extracurricular.model

import org.beangle.data.model.LongId
import org.openurp.edu.base.model.Project
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.base.model.Semester
import org.beangle.commons.lang.time.WeekTime

/**
 * 答疑活动
 */
class FaqActivity extends LongId {
  var project: Project = _

  var semester: Semester = _

  var teacher: Teacher = _

  var time: WeekTime = _

  var location: String = _

  var course: String = _

}