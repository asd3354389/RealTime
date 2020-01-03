<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertBdOTCard" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>離崗卡綁定費用代碼</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		
		<!-- <div class="control-group">
    		<label class="control-label" for="inputUserName">工號</label>
    		<div class="controls">
      			<input type="text" id="inputUserName" name="inputUserName" class="required form-control" placeholder="工號">
    		</div>
  		</div> -->
  		
  		<div class="control-group">
    		<div class="controls">
      			<label for="outCard">费用代码</label>
    					<!-- <select id="CostNo" name='CostNo' class="form-control">
    						<option value=" "></option>
    					</select>  			 -->
    			<div class="controls">
    				<input type="text" class="form-control" placeholder="费用代码" id="CostNo" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1,');">
      				<%-- <input type="text" id="CostNo" name="CostNo" class="required nameCheck form-control" placeholder="费用代码" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1,');> --%>
    			</div>
    					
    		</div>
  		</div> 		
  			
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">離崗卡號</label>
    		<div class="controls">
      			<input type="text" id="inputOTCarid" name="inputOTCarid" class="required nameCheck form-control" placeholder="離崗卡號">
    		</div>
  		</div>
  		
  		<!-- <div class="control-group">
    		<div class="controls">
      			<label for="deptNo">綫組別號</label>
    					<select id="deptNo" name='deptNo' class="form-control"></select>  			
    		</div>
  		</div> -->
  			
  		<div class="control-group">
    		<div class="controls">
      			<label for="outCard">默認使用車間</label>
    					<select id="workShop" name='workShop' class="form-control"></select>  			
    		</div>
  		</div>
	
        <br>
  		<button type="submit" id="changebdOT" class="btn btn-primary">綁定</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>

