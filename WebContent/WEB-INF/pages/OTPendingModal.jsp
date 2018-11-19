<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="OTPending" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="OTPendingModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="gridSystemModalLabel">已審核加班人員</h4>
      </div>
      <div class="modal-body">
<!--       	<div class="row">
      		<br>
			<div class="col-sm-12 col-md-12">
				<div class="col-sm-3 col-md-3">
					<div class="col-sm-6 col-md-6"><label>時間:</label></div>
					<div class="col-sm-6 col-md-6">
						<select id="overtimeCal">
							<option value="0">待選</option>
							<option value="1">正常班</option>
							<option value="2">假日班</option>
						</select>
					</div>
				</div>
				<div class="col-sm-3 col-md-3">
					<div class="col-sm-7 col-md-7"><label>加班類型：</label></div>
					<div class="col-sm-5 col-md-5">
						<select id="overtimeType">
							<option value="0">待選</option>
        						<option value="1">加班1</option>
        						<option value="2">加班2</option>
        						<option value="3">加班3</option>
						</select>
					</div>
				</div>
				<div class="col-sm-3 col-md-3">
					<div class="col-sm-7 col-md-7"><label>工作內容：</label></div>
					<div class="col-sm-5 col-md-5">
						<input type="text" id="workcontent" class="input-sm" />
					</div>
				</div>
			</div>
			<br>
      	</div>
      	<div class="row">
      		<br>
			<div class="col-sm-3 col-md-3"><label>請輸入員工ID文件:</label></div>
			<div class="col-sm-5 col-md-5"><input type="file" id="EmpIdFile"/></div>
			<div class="col-sm-4 col-md-4"><input type="button" id="fileUploadBtn" class="btn btn-primary btn-sm" value="上傳檔案"/></div>
			<br>
      	</div>
      	<div class="row">
			<br>
			<input type="button" class="OTHrsSubmitBtn" class="btn btn-primary btn-sm" value="提交" />
			<br>
		</div> -->
		<div class="row">
			<div class="col-sm-12 col-md-12">
				<table id="OTPendingEmpTable" class="table table-striped">
					<thead>
						<tr>
							<th></th>
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
				<br>
			</div>
      </div>
      <div class="modal-footer">
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->