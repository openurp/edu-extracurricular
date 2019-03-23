package org.openurp.edu.extracurricular.model

import org.beangle.data.model.LongId
import org.openurp.edu.base.model.Student
import org.beangle.data.model.pojo.Updated

/**
 * 受辅导的学生
 */
class TutoredStd extends LongId with Updated {

  var activity: TutorialActivity = _

  var std: Student = _
}