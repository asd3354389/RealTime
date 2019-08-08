<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="InsertMoreDeptId" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>電腦Ip綁定多個線組別代碼</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		<div class="control-group">
    		<label class="control-label" for="inputIp">電腦Ip</label>
    		<div class="controls">
      			<input type="text" id="MoreinputIp" name="inputIp" class="required form-control" placeholder="電腦Ip">
    		</div>
  		</div>
  			
  	 	
  		<div class="control-group">
    		<div class="controls">
      			<label for="outCard">费用代码</label>
    					<select id="MoreCostNo" name='MoreCostNo' class="form-control">
    						<option value=" " ></option>
    					</select>  			
    		</div>
  		</div> 	
  			<div class="control-group">
    		<div class="controls">
      			<label class="control-label" for="MoredeptNo">綫組別號</label>
    					<!--  <select id="MoredeptNo" name='MoredeptNo' class="form-control"></select>   -->	
    				 	 <select id="MoredeptNo" name="MoredeptNo" class="selectpicker show-tick" multiple data-live-search="true"> 
                  </select>		
    		</div>
  		</div>	
        <br>
  		<button type="submit" id="MorechangebdOT" class="btn btn-primary">綁定</button>
  		<button type="reset" id="MoreresetSubmit" class="btn">清除</button>

			</div>	
		</div>
	</div>
</div>