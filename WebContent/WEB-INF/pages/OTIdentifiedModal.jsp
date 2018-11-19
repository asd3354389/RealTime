<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="OTIdentified" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="OTIdentifiedModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="gridSystemModalLabel">已審核加班人員</h4>
      </div>
      <div class="modal-body">
      	<div class="row">
      		<div class="col-sm-12 col-md-12">
      			<table id="OTIdentifiedEmpTable" class="table table-striped">
					<thead>
						<tr>
							<th>工號</th>
							<th>名字</th>
							<th>部門代碼</th>
							<th>費用代碼</th>
							<th>直間接人員</th>
							<th>加班日期</th>
							<th>加班時段</th>
							<th>加班小時</th>
							<th>加班類型</th>
							<th>審核狀態</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
      		</div>
      	</div>
      </div>
      <div class="modal-footer">
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->