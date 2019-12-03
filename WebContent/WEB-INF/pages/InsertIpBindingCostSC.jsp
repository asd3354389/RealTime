<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertIpBindingCostSC" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增卡機綁定可刷卡費用代碼</h5>
			</div>
			<div class="modal-body">
  			
  		<div class="control-group">
    		<label class="control-label" for="inputIp">卡機ip</label>
    		<div class="controls">
      			<input type="text" id="inputIp" name="inputIp" class="required deptIDCheck" placeholder="部門代碼">
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputCostID">所報加班部門費用代碼</label>
    		<div class="controls">
      			<!-- <input type="text" id="inputCostID" name="inputCostID" class="required costIDCheck" placeholder="費用代碼(格式:costId1*costId2)"> -->
    		      <select id="inputCostID" name="inputCostID" class="selectpicker show-tick" multiple data-live-search="true">
                  </select>
    		</div>
  		</div>
  		<br>
  		<br>
  		<br>
  		<br><br>
  		<br><br><br><br><br>
        <br>
  		<button type="submit" id="submitIpBindingCostSC" class="btn btn-primary">新增</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
			</div>	
		</div>
	</div>
</div>

