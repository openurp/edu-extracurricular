package org.openurp.edu.extracurricular.web.action

import java.time.LocalDate

import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.base.model.Project
import org.openurp.edu.base.model.Semester
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.extracurricular.model.TutorialActivity

class TutorialActivityAction extends RestfulAction[TutorialActivity] {

  override protected def indexSetting(): Unit = {
    put("projects", entityDao.getAll(classOf[Project]))
    put("semesters", entityDao.getAll(classOf[Semester]))
    put("currentSemester", getCurSemester())
    super.indexSetting()
  }

  override protected def editSetting(entity: TutorialActivity): Unit = {
    put("teachers", entityDao.getAll(classOf[Teacher]))
    put("projects", entityDao.getAll(classOf[Project]))
    put("semesters", entityDao.getAll(classOf[Semester]))
    super.editSetting(entity)
  }

  def getCurSemester(): Semester = {
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    builder.where("semester.beginOn <= :date and semester.endOn >= :date", LocalDate.now())
    val semesters = entityDao.search(builder)
    semesters(0)
  }

  def teacher(): View = {
    val codeOrName = get("term").orNull
    val query = OqlBuilder.from(classOf[Teacher], "teacher")
    query.where("teacher.project.id=:projectId", getInt("project").get)
    populateConditions(query);

    if (Strings.isNotEmpty(codeOrName)) {
      query.where("(teacher.user.name like :name or teacher.user.code like :code)", '%' + codeOrName + '%',
        '%' + codeOrName + '%');
    }
    val now = LocalDate.now
    query.where(":now1 >= teacher.beginOn and (teacher.endOn is null or :now2 <= teacher.endOn)", now, now)
      .orderBy("teacher.user.name")
    val pageLimit = getPageLimit
    query.limit(pageLimit);
    val a = entityDao.search(query)
    put("teachers", entityDao.search(query));
    put("pageLimit", pageLimit);
    forward()
  }

  override protected def saveAndRedirect(entity: TutorialActivity): View = {
    val projectId = get("project").get.toInt
    val project = entityDao.get(classOf[Project], projectId)
    entity.project = project
    val date = get[LocalDate]("tutorialActivity.date", classOf[LocalDate])
    val builder = OqlBuilder.from(classOf[Semester], "semester")
    builder.where("semester.beginOn <= :date and semester.endOn >= :date", date.get)
    val semesters = entityDao.search(builder)
    entity.semester = semesters(0)
    super.saveAndRedirect(entity)
  }

}