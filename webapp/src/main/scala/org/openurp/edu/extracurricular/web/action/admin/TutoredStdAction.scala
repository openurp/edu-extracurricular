package org.openurp.edu.extracurricular.web.action.admin

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.extracurricular.model.TutoredStd
import org.beangle.data.dao.OqlBuilder
import org.openurp.edu.base.model.Project
import org.openurp.edu.base.model.Semester
import java.time.LocalDate
import org.openurp.edu.base.model.Project
import org.openurp.edu.base.model.Semester

class TutoredStdAction extends RestfulAction[TutoredStd] {

  override protected def indexSetting(): Unit = {
    put("projects", entityDao.getAll(classOf[Project]))
    put("semesters", entityDao.getAll(classOf[Semester]))
    put("currentSemester", getCurSemester())
    super.indexSetting()
  }

  def getCurSemester(): Semester = {
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    builder.where("semester.beginOn <= :date and semester.endOn >= :date", LocalDate.now())
    val semesters = entityDao.search(builder)
    semesters(0)
  }

}