<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertIOCardMaIP" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增員工休息時間段</h5>
			</div>
			<div class="modal-body">
  		<div class="control-group">
    		<div class="controls">
      			<label>工號</label>
      			<input type="text" id="empId" name='empId'
					class="input-sm">
    					<!-- <select id="workShop" name='workShop' class="form-control">
    					<option value=""></option>
    					</select>  			 -->
    		</div>
    		<div class="controls">
      			<label>班別</label>
    					<input type="text" id="classNo" name='classNo'
					class="input-sm">
    		</div>
  		</div>
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">第一次休息開始時間</label>
      		<input id="insert_rest_start1" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
      		<label for="machineState">第一次休息結束時間</label>
    			<input id="insert_rest_end1" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">第二次休息開始時間</label>
      		<input id="insert_rest_start2" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
      		<label for="machineState">第二次休息結束時間</label>
    			<input id="insert_rest_end2" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
  		</div>
  		
        <br>
  		<button type="submit" id="setWorkshopNoRestInfo" class="btn btn-primary">設置</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>

