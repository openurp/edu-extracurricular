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
package org.openurp.edu.extracurricular.model

import org.beangle.data.orm.IdGenerator
import org.beangle.data.orm.MappingModule

class DefaultMapping extends MappingModule {

  def binding(): Unit = {
    defaultIdGenerator(IdGenerator.AutoIncrement)
    defaultCache("openurp.extracurricular", "read-write")

    bind[Lecture].on(e => declare(
      e.audiences is depends("lecture")))

    bind[Audience]

    bind[FaqActivity]

    bind[TutorialActivity].on(e => declare(
      e.stds is depends("activity")))

    bind[TutoredStd]

    bind[TutorialSwitch].on(e => declare(
      e.remark is length(1000)))
  }
}
