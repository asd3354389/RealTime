<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertIOCardMaIP" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增車間休息時間段</h5>
			</div>
			<div class="modal-body">
  		<div class="control-group">
    		<div class="controls">
      			<label for="outCard">車間ip</label>
    			<div class="controls">
      				<input type="text" id="insert_deviceIP" name="inputChineseName" class="required nameCheck" placeholder="車間ip">
    			</div>		
    		</div>
  		</div>
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">員工工號</label>
    		<div class="controls">
      				<input type="text" id="insert_emp_id" name="inputChineseName" class="required nameCheck" placeholder="員工工號">
    		</div>	
  		</div>
  			
	
        <br>
  		<button type="submit" id="setEmpIPBinding" class="btn btn-primary">設置</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>

