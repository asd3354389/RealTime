<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertLineMappingDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增產線管理</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		<div class="control-group">
    		<label class="control-label" for="inputOGN">OGN代碼</label>
    		<div class="controls">
      			<input type="text" id="inputOGN" name="inputOGN" class="required" placeholder="OGN代碼">
    		</div>
  		</div>
  			
  		<div class="control-group">
    		<label class="control-label" for="inputMESPDLineId">MES系統對應的產線號</label>
    		<div class="controls">
      			<input type="text" id="inputMESPDLineId" name="inputMESPDLineId" class="required nameCheck" placeholder="MES系統對應的產線號">
    		</div>
  		</div>
  			
  		<div class="control-group">
    		<label class="control-label" for="inputMESPDLineName">MES系統對應的產線名稱</label>
    		<div class="controls">
      			<input type="text" id="inputMESPDLineName" name="inputMESPDLineName" class="required deptIDCheck" placeholder="MES系統對應的產線名稱">
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputMESPDLineDesc">MES系統對應的產線描述</label>
    		<div class="controls">
      			<!-- <input type="text" id="inputCostID" name="inputCostID" class="required costIDCheck" placeholder="費用代碼(格式:costId1*costId2)"> -->
    		      <input type="text" id="inputMESPDLineDesc" name="inputMESPDLineDesc" class="required deptIDCheck" placeholder="MES系統對應的產線描述">
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="RTLINE">事實系統對應的產線名稱</label>
    		<div class="controls">
      			<select id="RTLINE" class="RTLINE" name="RTLINE">     				
      			</select>
    		</div>
  		</div>
  		
  		 <div class="control-group">
    		<label class="control-label" for="inputSTDManPower">標準人力</label>
    		<div class="controls">
      			<input type="text" id="inputSTDManPower" name="inputSTDManPower"class="required phoneTel" placeholder="標準人力">
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

