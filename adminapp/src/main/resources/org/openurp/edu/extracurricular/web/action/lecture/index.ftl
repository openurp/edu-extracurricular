[#ftl]
[@b.head/]
[@b.toolbar title="课堂活动"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="lectureSearchForm" action="!search" target="lecturelist" title="ui.searchForm" theme="search"]
      [@b.select label="学年学期" name="lecture.semester.id" items={}]
        <option value="">...</option>
        [#list semesters?sort_by("code")?reverse as semester]
          <option value="${semester.id}">${(semester.schoolYear)!}学年${(semester.name?replace('0','第'))!}学期</option>
        [/#list]
      [/@]
      [@b.select name="lecture.depart.id" label="开课院系" value="${(lecture.depart.id)!}" items=departments empty="..."/]
      <input type="hidden" name="orderBy" value="date desc"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="lecturelist" href="!search?orderBy=date desc"/]
    </td>
  </tr>
</table>
[@b.foot/]
