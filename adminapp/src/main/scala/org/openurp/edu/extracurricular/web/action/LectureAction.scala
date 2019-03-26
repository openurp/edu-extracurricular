package org.openurp.edu.extracurricular.web.action

import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Department
import org.openurp.edu.base.model.Classroom
import org.openurp.edu.extracurricular.model.Lecture
import java.time.ZoneId
import org.beangle.data.dao.OqlBuilder
import org.beangle.commons.lang.Strings
import org.openurp.edu.base.model.Project
import org.openurp.edu.base.model.Semester
import org.scalatest.time.Now
import java.util.Date
import java.time.LocalDate

class LectureAction extends RestfulAction[Lecture] {

  override protected def indexSetting(): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("projects", entityDao.getAll(classOf[Project]))
    put("semesters", entityDao.getAll(classOf[Semester]))
    //    put("currentSemester", getCurSemester())
  }

  override protected def editSetting(entity: Lecture): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("rooms", entityDao.getAll(classOf[Classroom]))
    put("projects", entityDao.getAll(classOf[Project]))
    put("semesters", entityDao.getAll(classOf[Semester]))
  }

  def getCurSemester(): Semester = {
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    builder.where("semester.beginOn <= :date and semester.endOn >= :date", new java.util.Date())
    val semesters = entityDao.search(builder)
    semesters(0)
  }

  def room(): View = {
    val codeOrName = get("term").orNull
    val query = OqlBuilder.from(classOf[Classroom], "room")
    populateConditions(query)

    if (Strings.isNotEmpty(codeOrName)) {
      query.where("(room.name like :name or room.code like :code)", '%' + codeOrName + '%',
        '%' + codeOrName + '%')
    }
    val pageLimit = getPageLimit
    query.limit(pageLimit);
    val rooms = entityDao.search(query)
    put("rooms", entityDao.search(query))
    put("pageLimit", pageLimit)
    forward()
  }

  override protected def saveAndRedirect(lecture: Lecture): View = {
    val projectId = get("project").get.toInt
    val project = entityDao.get(classOf[Project], projectId)
    lecture.project = project
    val date = get[LocalDate]("lecture.date", classOf[LocalDate])
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    builder.where("semester.beginOn <= :date and semester.endOn >= :date", date.get)
    val semesters = entityDao.search(builder)
    lecture.semester = semesters(0)
    super.saveAndRedirect(lecture)
  }

}