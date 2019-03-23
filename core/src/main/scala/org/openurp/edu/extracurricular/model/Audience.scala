package org.openurp.edu.extracurricular.model

import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Updated
import org.openurp.edu.base.model.Student

/**
 * 上课学生
 */
class Audience extends LongId with Updated {

  var std: Student = _

  var lecture: Lecture = _

}