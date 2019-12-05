<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertAppLogin" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增卡機綁定可刷卡費用代碼</h5>
			</div>
			<div class="modal-body">
			
		<div class="control-group">
    		<label class="control-label" for="com_name">電腦名稱</label>
    		<div class="controls">
      			<input type="text" id="com_name" name="com_name" class="required deptIDCheck" placeholder="電腦名稱" autocomplete="off">
    		</div>
  		</div>
  			
  		<div class="control-group">
    		<label class="control-label" for="inputIp">卡機ip</label>
    		<div class="controls">
      			<input type="text" id="inputIp" name="inputIp" class="required deptIDCheck" placeholder="卡機ip" autocomplete="off">
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputCostID">責任人費用代碼</label>
    		<div class="controls">
      			<input type="text" id="inputCostID" name="inputCostID" class="required deptIDCheck" placeholder="責任人費用代碼" autocomplete="off">
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputId">責任人工號</label>
    		<div class="controls">
      			<input type="text" id="inputId" name="inputId" class="required deptIDCheck" placeholder="責任人工號" autocomplete="off">
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputTel">責任人分機</label>
    		<div class="controls">
      			<input type="text" id="inputTel" name="inputTel" class="required deptIDCheck" placeholder="責任人分機" autocomplete="off">
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputCostID">卡機類型</label>
    		<label class="control-label" for="inputCostID">例外個人ip（非資訊管制的10.64.117或10.64.119），管控ip（由資訊安裝專門使用的卡機會分配10.64.117或10.64.119,一般不需要添加此類）</label>
    		<div class="controls">
      			<!-- <input type="text" id="inputCostID" name="inputCostID" class="required costIDCheck" placeholder="費用代碼(格式:costId1*costId2)"> -->
    		      <select id="inputType" name="inputType">
    		      	<option value="C">例外個人ip</option>
    		      	<option value="E">管控ip</option>
                  </select>
    		</div>
  		</div>
        <br>
  		<button type="submit" id="submitAppLogin" class="btn btn-primary">新增</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
			</div>	
		</div>
	</div>
</div>

