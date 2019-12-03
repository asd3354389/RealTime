<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertHolidayInfo" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm ins" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>節假日或補休設置</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		<div class="control-group">
    		<label class="control-label" for="selectHolidayType">假日類型</label>
    		<div class="controls">
      			<select id="selectHolidayType" class="required">
      				<option value="L">節假日</option>
      				<option value="S">補休</option>
      			</select>
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="HolidayDate">節假日或補休日期日期</label>
    		<div class="controls">
      			 <input id="HolidayDate" class="Wdate" type="text" onClick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" autocomplete="off" /> 
  			</div>		
  		</div> 
 		
	
        <br>
  		<button type="submit" id="setHoliday" class="btn btn-primary">設置</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>
	
</div>

