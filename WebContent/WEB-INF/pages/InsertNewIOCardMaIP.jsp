<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertIOCardMaIP" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>卡機I/O狀態設置</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		<div class="control-group">
    		<label class="control-label" for="inputCardMachineIP">卡機IP</label>
    		<div class="controls">
      			<input type="text" id="inputCardMachineIP" name="inputCardMachineIP" class="required form-control" placeholder="卡機IP">
    		</div>
  		</div>
  		<div class="control-group">
    		<div class="controls">
      			<label for="outCard">車間號</label>
    					<select id="workShop" name='workShop' class="form-control">
    					<option value=""></option>
    					</select>  			
    		</div>
  		</div>	
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">車間描述</label>
    		<div class="controls">
      			<textarea class="form-control" id="message-text" maxlength="60"></textarea>
    		</div>
  		</div>
  		<div class="control-group">
    		<div class="controls">
      			<label for="machineState">卡機I/O狀態</label>
    					<select id="machineState" name='machineState' class="form-control">
    						<option value="I">進</option>
							<option value="O">出</option>
    					</select>  			
    		</div>
  		</div>			
	
        <br>
  		<button type="submit" id="setIOCardMaIP" class="btn btn-primary">設置</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>

