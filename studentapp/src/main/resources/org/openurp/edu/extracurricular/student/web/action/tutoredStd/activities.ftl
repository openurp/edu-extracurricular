[#ftl]
[@b.head/]
[@b.toolbar title="可选辅导活动"/]
[#if switches ?? && switches?size>0]
  [#if activities?? && activities?size>0]
    [@b.grid items=activities?sort_by("date") var="activity" sortable="false"]
      [@b.row]
        [@b.col width="5%" title="序号"]${activity_index+1 }[/@]
        [@b.col width="15%" property="date" title="日期"]${(activity.date?string('yyyy-MM-dd'))?default('')}[/@]
        [@b.col width="15%" title="时间"]${activity.beginAt! }-${activity.endAt }[/@]
        [@b.col width="15%" property="subject" title="活动名称（类别）"/]
        [@b.col width="10%" property="teacher.user.name" title="辅导教师"/]
        [@b.col width="10%" property="location" title="地点"/]
        [@b.col width="10%" property="capacity" title="最大容量"/]
        [@b.col width="10%" title="已选人数"]${(activity.stds.size)! }[/@]
        [@b.col width="10%" title="操作"][#if !chooseAvtivities?seq_contains(activity)][@b.a href="!choose?activity.id=${activity.id}"]预约[/@][#else]已预约[/#if][/@]
      [/@]
    [/@]
  [#else]目前没有可选辅导活动
  [/#if]
[#else]辅导活动还未开放预约
[/#if]
[@b.foot/]
