<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertPowerDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增權限</h5>
			</div>
			<div class="modal-body">
  		<div class="control-group">
    		<label class="control-label" for="inputPower">賬號權限</label>
    		<div class="controls">
      			<!-- <input type="text" id="inputRole" name="inputRole"class="required Role" placeholder="賬號權限,格式ROLE_**"> -->
      			<select id="inputPower" name="inputPower"class="required Role">
      				<option value="ROLE_ADMIN">ROLE_ADMIN</option>
      				<option value="ROLE_ASSISTANT">ROLE_ASSISTANT</option>
      				<option value="ROLE_LineLeader">ROLE_LineLeader</option>
      				<option value="ROLE_TXADLOW">ROLE_TXADLOW</option>
      				<option value="ROLE_ZJADLOW">ROLE_ZJADLOW</option>
      				<option value="ROLE_VIC_ADMIN">ROLE_VIC_ADMIN</option>
      				<option value="ROLE_VIC_ASSISTANT">ROLE_VIC_ASSISTANT</option>
      			</select>
    		</div>
  		</div>
  			
        <br>
  		<button type="submit" id="setNewPowerInfo" class="btn btn-primary">設置</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>

