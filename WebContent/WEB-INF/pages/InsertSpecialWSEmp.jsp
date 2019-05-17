<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertIOWorkShopPW" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm ins" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>進出車間臨時權限設置</h5>
			</div>
			<div class="modal-body">
		<!--		<form id="addNewUserForm" action="modifyReceiverDevice.do" method="post" class="form-horizontal">   -->
		<div class="control-group"><label for="outCard" style="color:red">多個工號之間用英文,（逗號）隔開，如132456,123456</label></div>
		<div class="control-group">
    		<label class="control-label" for="inputUserName">工號</label>
    		<div class="controls">
      			<input type="text" id="inputUserName" name="inputUserName" class="required form-control" placeholder="工號">
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
    		<label class="control-label" for="dpick1">生效起始日期</label>
    		<div class="controls">
      			 <input
						id="dpick1" class="Wdate" type="text" name="OVERTIMEDATE"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'%y-\#{%M-2}-01',maxDate:'#F{$dp.$D(\'dpick2\')}'})" autocomplete="off" />  
  			</div>		
  		</div> 
  		
  		<div class="control-group">
    		<label class="control-label" for="dpick2">生效結束日期</label>
    		<div class="controls">
    			
					<input id="dpick2" class="Wdate" type="text" name="OVERTIMEDATEEnd"
						onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'dpick1\')}'})" autocomplete="off" />
  		</div>
 		
	
        <br>
  		<button type="submit" id="setIOWorkShopPW" class="btn btn-primary">設置</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>
	
</div>

