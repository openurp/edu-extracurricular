/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.extracurricular.web

import org.beangle.cdi.bind.BindModule
import org.openurp.edu.extracurricular.web.action.admin.LectureAction
import org.openurp.edu.extracurricular.web.action.admin.TutoredStdAction
import org.openurp.edu.extracurricular.web.action.admin.TutorialActivityAction
import org.openurp.edu.extracurricular.web.action.admin.TutorialSwitchAction
import org.openurp.edu.extracurricular.web.action.student.ActivityReservationAction
import org.openurp.edu.extracurricular.web.action.student.LectureReservationAction

class DefaultModule extends BindModule {

  protected override def binding() {
    bind(classOf[LectureAction],classOf[TutorialActivityAction],classOf[TutorialSwitchAction],classOf[TutoredStdAction])

    bind(classOf[LectureReservationAction],classOf[ActivityReservationAction])
  }
}
