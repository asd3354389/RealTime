<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertAccountDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增使用者</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		<div class="control-group">
    		<label class="control-label" for="inputUserName">帳號</label>
    		<div class="controls">
      			<input type="text" id="inputUserName" name="inputUserName" class="required" placeholder="工號">
    		</div>
  		</div>
  			
  		<div class="control-group">
    		<label class="control-label" for="inputChineseName">姓名(中文)</label>
    		<div class="controls">
      			<input type="text" id="inputChineseName" name="inputChineseName" class="required nameCheck" placeholder="姓名(中文)">
    		</div>
  		</div>
  			
  		<div class="control-group">
    		<label class="control-label" for="inputDepID">部門代碼</label>
    		<div class="controls">
      			<input type="text" id="inputDepID" name="inputDepID" class="required deptIDCheck" placeholder="部門代碼">
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
  		
  		<div class="control-group">
    		<label class="control-label" for="inputAssistantId">報加班助理ID</label>
    		<div class="controls">
      			<select id="inputAssistantId" class="inputAssistantId" name="inputAssistantId">     				
      			</select>
    		</div>
  		</div>
  		
  		 <div class="control-group">
    		<label class="control-label" for="inputPhoneTel">分機</label>
    		<div class="controls">
      			<input type="text" id="inputPhoneTel" name="inputPhoneTel"class="required phoneTel" placeholder="分機">
    		</div>
  		</div>
  		
  		 <div class="control-group">
    		<label class="control-label" for="inputRole">賬號權限</label>
    		<div class="controls">
      			<input type="text" id="inputRole" name="inputRole"class="required Role" placeholder="賬號權限,格式ROLE_**">
    		</div>
  		</div>
  		
        <br>
  		<button type="submit" id="submitNewUser" class="btn btn-primary">新增</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>

