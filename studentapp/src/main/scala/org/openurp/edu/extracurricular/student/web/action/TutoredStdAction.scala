package org.openurp.edu.extracurricular.student.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.extracurricular.model.TutoredStd
import java.time.Instant
import org.openurp.edu.extracurricular.model.TutorialActivity
import org.beangle.security.Securities
import org.beangle.data.dao.OqlBuilder
import org.openurp.edu.base.model.Student
import org.beangle.webmvc.api.view.View
import org.openurp.edu.extracurricular.model.TutorialSwitch

class TutoredStdAction extends RestfulAction[TutoredStd]{
  
    override protected def indexSetting(): Unit = {
    val user = Securities.user
    val stdBuilder = OqlBuilder.from(classOf[Student], "student")
    stdBuilder.where("student.user.code =:code ", user)
    val students = entityDao.search(stdBuilder)
    val tutoredStds = entityDao.findBy(classOf[TutoredStd], "std", students)
    val chooseAvtivities = tutoredStds.map(_.activity)

    put("chooseAvtivities", chooseAvtivities)
    val switchBuilder = OqlBuilder.from(classOf[TutorialSwitch],"switch")
    switchBuilder.where("switch.opened = true and switch.beginAt < :now and switch,endAt > :now",Instant.now())
    put("switches", entityDao.search(switchBuilder))
    put("activities", entityDao.getAll(classOf[TutorialActivity]))
  }

  protected def choose(): View = {
    val user = Securities.user
    val stdBuilder = OqlBuilder.from(classOf[Student], "student")
    stdBuilder.where("student.user.code =:code ", user)
    val students = entityDao.search(stdBuilder)

    val activity = entityDao.find(classOf[TutorialActivity], longId("activity")).get

    val tutoredStds = entityDao.findBy(classOf[TutoredStd], "std", students)
    val chooseAvtivities = tutoredStds.map(_.activity)

    val conflict = chooseAvtivities.exists(x => x.date == activity.date && activity.beginAt < x.endAt && x.beginAt < activity.endAt)
    if (activity.stds.size < activity.capacity) {
      if (!conflict) {
        val tutoredStd = populateEntity()
        tutoredStd.activity = activity
        tutoredStd.std = students(0)
        tutoredStd.updatedAt = Instant.now()
        entityDao.saveOrUpdate(tutoredStd)
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

    val activity = entityDao.find(classOf[TutorialActivity], longId("activity"))
    val tutoredStdBuilder = OqlBuilder.from(classOf[TutoredStd], "tutoredStd")
    tutoredStdBuilder.where("tutoredStd.std =:std", students(0))
    tutoredStdBuilder.where("tutoredStd.activity =:activity", activity)
    val tutoredStds = entityDao.search(tutoredStdBuilder)
    entityDao.remove(tutoredStds)

    redirect("index")
  }
  
}