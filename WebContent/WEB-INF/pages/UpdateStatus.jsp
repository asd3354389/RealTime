<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal fade bs-example-modal-sm ChangeStatus" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h5>更改狀態設置</h5>
			</div>
			<div class="modal-body">
 		<div class="control-group">
    		<label class="control-label" for="UserNo">工號</label>
    		<div class="controls">
      			<input type="text" id="UserNo" name="UserNo" class="required form-control" placeholder="工號" disabled>
    		</div>
  		</div>
   		<!-- <div class="control-group">
    		<label class="control-label" for="depid">部门代码</label>
    		<div class="controls">
      			<input type="text" id="depid" name="depid" class="required form-control" placeholder="部门代码" disabled>
    		</div>
  		</div><div class="control-group">
    		<label class="control-label" for="SDate">時間</label>
    		<div class="controls">
      			<input type="text" id="SDate" name="SDate" class="required form-control" placeholder="時間" disabled>
    		</div>
  		</div> -->
  		<div class="control-group">
    		<label class="control-label" for="Class_No">班別</label>
    		<div class="controls">
      			<input type="text" id="Class_No" name="Class_No" class="required form-control" placeholder="班別" disabled>
    		</div>
  		</div>
  		<div class="control-group">
    		<div class="controls">
      			<label for="type">選擇異常類型</label>
    			<div class="controls">
      				 <select class="area-select" id='type'">
				            <option value="X">請選擇異常類型</option>
				            <option value="1">請假</option>
				            <option value="2">曠工</option>
				            <option value="3">調出</option>
							<option value="4">新進</option>
							<option value="5">離職</option>
							<option value="8">調休</option>
							<option value="9">遲到</option>
							<option value="0">無異常</option>
        			</select>
    			</div>			
    		</div>
  		</div> 		
  			
  		<div class="control-group">
    		<label class="control-label" for="status">選擇具體是由</label>
    		<div class="controls">
      			<select class="area-select" id="status">
           			 <option>請選擇具體事由</option>
        		</select>
    		</div>
  		</div>
	
        <br>
  		<button type="submit" id="changeStatus" class="btn btn-primary">更改狀態</button>
			</div>	
		</div>
	</div>
</div>

