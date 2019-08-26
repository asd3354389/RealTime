<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertIOWorkShopPWOther" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm ins" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>保密車間臨時權限設置</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		<!-- <div class="control-group"><label for="outCard" style="color:red">多個卡號號之間用英文,（逗號）隔開，如1324567890,1234567890</label></div> -->
		<div class="control-group">
    		<label class="control-label" for="inputCardId">卡號</label>
    		<div class="controls">
      			<input type="text" id="inputCardId" name="inputCardId" class="required form-control" placeholder="卡號">
    		</div>
  		</div>
  		<div class="control-group">
    		<div class="controls">
      			<label for="outCard">車間號</label>
      			        <select id="workShopOther" name='workShopOther' class="selectpicker show-tick" multiple data-live-search="true">
    					<!-- <select id="workShopOther" name='workShopOther' class="form-control"> -->
    					<option value=""></option>
    					</select>  			
    		</div>
  		</div>	
  		
  		<div class="control-group">
    		<label class="control-label" for="dpick1Other">生效起始日期</label>
    		<div class="controls">
      			 <input
						id="dpick1Other" class="Wdate" type="text" name="OVERTIMEDATE"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'%y-\#{%M-2}-01',maxDate:'#F{$dp.$D(\'dpick2\')}'})" autocomplete="off" />  
  			</div>		
  		</div> 
  		
  		<div class="control-group">
    		<label class="control-label" for="dpick2Other">生效結束日期</label>
    		<div class="controls">
    			
					<input id="dpick2Other" class="Wdate" type="text" name="OVERTIMEDATEEnd"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'dpick1\')}'})" autocomplete="off" />
  		</div>
 		
	
	  <div class="control-group">
	 <label class="control-label" for="remark">備註</label>
	 <div class="controls">
      		<input type="text" id="inputRemark" name="inputRemark" class="required form-control" placeholder="備註">
    		</div>
	  </div>
        <br>
  		<button type="submit" id="setIOWorkShopPWOther" class="btn btn-primary">設置</button>
  		<button type="reset" id="resetSubmitOther" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>
	
</div>

