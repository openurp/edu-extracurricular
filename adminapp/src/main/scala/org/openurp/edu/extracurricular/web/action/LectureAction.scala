package org.openurp.edu.extracurricular.web.action

import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Department
import org.openurp.edu.base.model.Classroom
import org.openurp.edu.extracurricular.model.Lecture
import java.time.ZoneId
import org.beangle.data.dao.OqlBuilder
import org.beangle.commons.lang.Strings

class LectureAction extends RestfulAction[Lecture] {

  override protected def indexSetting(): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
  }

  override protected def editSetting(entity: Lecture): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("rooms", entityDao.getAll(classOf[Classroom]))
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

}