<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertBdOTCard" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>離崗卡綁定工號</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		<div class="control-group">
    		<label class="control-label" for="inputUserName">工號</label>
    		<div class="controls">
      			<input type="text" id="inputUserName" name="inputUserName" class="required form-control" placeholder="工號">
    		</div>
  		</div>
  			
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">離崗卡號</label>
    		<div class="controls">
      			<input type="text" id="inputOTCarid" name="inputOTCarid" class="required nameCheck form-control" placeholder="離崗卡號">
    		</div>
  		</div>
  			
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

