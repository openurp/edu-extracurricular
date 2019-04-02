package org.openurp.edu.extracurricular.model

import org.beangle.data.model.pojo.TemporalAt
import org.beangle.data.model.LongId
import org.beangle.data.model.pojo.Named
import org.beangle.data.model.pojo.Remark

class TutorialSwitch extends LongId with Named with Remark with TemporalAt{
  
  var opened : Boolean = false
  
  
}