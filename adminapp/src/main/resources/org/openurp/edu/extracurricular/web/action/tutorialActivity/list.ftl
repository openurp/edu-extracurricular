[#ftl]
[@b.head/]
[@b.grid items=tutorialActivities var="tutorialActivity"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="date" title="日期"]${(tutorialActivity.date?string('yyyy-MM-dd'))?default('')}[/@]
    [@b.col width="20%" title="时间"]${tutorialActivity.beginAt! }-${tutorialActivity.endAt }[/@]
    [@b.col width="20%" property="subject" title="活动名称（类别）"/]
    [@b.col width="15%" property="teacher.user.name" title="组织教师"/]
    [@b.col width="10%" property="capacity" title="最大容量"/]
    [@b.col width="15%" property="semester" title="学年学期"]${(tutorialActivity.semester.schoolYear)!}学年${(tutorialActivity.semester.name?replace('0','第'))!}学期[/@]
  [/@]
[/@]
[@b.foot/]
