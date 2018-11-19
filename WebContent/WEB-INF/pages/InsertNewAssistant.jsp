<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertAssistantDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增助理</h5>
			</div>
			<div class="modal-body">
		<div class="control-group">
    		<label class="control-label" for="inputAssistantId">工號</label>
    		<div class="controls">
      			<input type="text" id="inputAssistantId" name="inputAssistantId" class="required" placeholder="工號">
    		</div>
  		</div>
  			
  		<div class="control-group">
    		<label class="control-label" for="inputAssistantName">姓名(中文)</label>
    		<div class="controls">
      			<input type="text" id="inputAssistantName" name=""inputAssistantName"" class="required nameCheck" placeholder="姓名(中文)">
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputCostID">費用代碼</label>
    		<div class="controls">
      			<input type="text" id="inputCostID" name="inputCostID" class="required costIDCheck" placeholder="費用代碼">
    		</div>
  		</div>
  			
  		<div class="control-group">
    		<label class="control-label" for="inputDepID">部門代碼</label>
    		<div class="controls">
      			<input type="text" id="inputDepID" name="inputDepID" class="required deptIDCheck" placeholder="部門代碼">
    		</div>
  		</div> 				
  		
  		 <div class="control-group">
    		<label class="control-label" for="inputPhoneTel">分機</label>
    		<div class="controls">
      			<input type="text" id="inputPhoneTel" name="inputPhoneTel"class="required phoneTel" placeholder="分機">
    		</div>
  		</div>
  		 		  		
  		<div class="control-group">
    		<label class="control-label" for="inputEmail">Notes郵箱</label>
    		<div class="controls">
      			<input type="text" id="inputEmail" name="inputPhoneTel"class="required phoneTel" placeholder="Notes郵箱">
    		</div>
  		</div>
  		
        <br>
  		<button type="submit" id="submitNewAssistant" class="btn btn-primary">新增</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
			</div>	
		</div>
	</div>
</div>

