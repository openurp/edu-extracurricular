[#ftl]
[@b.head/]
[@b.toolbar title="课堂活动"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="lectureSearchForm" action="!search" target="lecturelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="lecture.crn;名称"/]
      [@b.select name="lecture.depart.id" label="开课院系" value="${(lecture.depart.id)!}" items=departments empty="..."/]
      <input type="hidden" name="orderBy" value="crn desc"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="lecturelist" href="!search?orderBy=crn desc"/]
    </td>
  </tr>
</table>
[@b.foot/]
