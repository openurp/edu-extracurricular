[#ftl]
[@b.head/]
[@b.toolbar title="辅导活动"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="tutorialActivitySearchForm" action="!search" target="tutorialActivitylist" title="ui.searchForm" theme="search"]
      [@b.select label="学年学期" name="tutorialActivity.semester.id" items={}]
        <option value="">...</option>
        [#list semesters?sort_by("code")?reverse as semester]
          <option value="${semester.id}" [#if semester.id = currentSemester.id]selected[/#if]>${(semester.schoolYear)!}学年${(semester.name?replace('0','第'))!}学期</option>
        [/#list]
      [/@]
      <input type="hidden" name="orderBy" value="date desc"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="tutorialActivitylist" href="!search?orderBy=date desc"/]
    </td>
  </tr>
</table>
[@b.foot/]
