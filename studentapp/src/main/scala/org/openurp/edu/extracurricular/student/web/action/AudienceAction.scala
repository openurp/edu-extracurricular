package org.openurp.edu.extracurricular.student.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Department
import org.openurp.edu.base.model.Project
import org.openurp.edu.base.model.Semester
import org.openurp.edu.extracurricular.model.Lecture
import org.beangle.webmvc.api.annotation.param
import org.beangle.webmvc.api.view.View
import org.beangle.security.Securities
import org.openurp.edu.extracurricular.model.Audience
import org.beangle.data.dao.OqlBuilder
import org.openurp.edu.base.model.Student
import java.time.Instant
import org.beangle.commons.collection.Collections

class AudienceAction extends RestfulAction[Audience] {

  override protected def indexSetting(): Unit = {
    val user = Securities.user
    val stdBuilder = OqlBuilder.from(classOf[Student], "student")
    stdBuilder.where("student.user.code =:code ", user)
    val students = entityDao.search(stdBuilder)
    val audiences = entityDao.findBy(classOf[Audience], "std", students)
    val chooseLectures = audiences.map(_.lecture)

    put("chooseLectures", chooseLectures)
    put("lectures", entityDao.getAll(classOf[Lecture]))
  }

  protected def choose(): View = {
    val user = Securities.user
    val stdBuilder = OqlBuilder.from(classOf[Student], "student")
    stdBuilder.where("student.user.code =:code ", user)
    val students = entityDao.search(stdBuilder)

    val lecture = entityDao.find(classOf[Lecture], longId("lecture")).get

    val audienceBuilder = OqlBuilder.from(classOf[Audience], "audience")
    audienceBuilder.where("audience.std =:std", students(0))
    val audiences = entityDao.search(audienceBuilder)
    val chooseLectures = audiences.map(_.lecture)

    val conflict = chooseLectures.exists(x => x.date == lecture.date && lecture.beginAt < x.endAt && x.beginAt < lecture.endAt)
    if (lecture.audiences.size < lecture.capacity) {
      if (!conflict) {
        val audience = populateEntity()
        audience.lecture = lecture
        audience.std = students(0)
        audience.updatedAt = Instant.now()
        entityDao.saveOrUpdate(audience)
      } else {
        redirect("index", "与已选课堂活动时间冲突")
      }
    } else {
      redirect("index", "所选课堂活动人数已满")
    }
    redirect("index")
  }

  protected def unChoose(): View = {
    val user = Securities.user
    val stdBuilder = OqlBuilder.from(classOf[Student], "student")
    stdBuilder.where("student.user.code =:code ", user)
    val students = entityDao.search(stdBuilder)

    val lecture = entityDao.find(classOf[Lecture], longId("lecture"))
    val audienceBuilder = OqlBuilder.from(classOf[Audience], "audience")
    audienceBuilder.where("audience.std =:std", students(0))
    audienceBuilder.where("audience.lecture =:lecture", lecture)
    val audiences = entityDao.search(audienceBuilder)
    entityDao.remove(audiences)

    redirect("index")
  }

}