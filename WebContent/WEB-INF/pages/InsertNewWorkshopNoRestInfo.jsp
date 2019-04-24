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
      			<label for="outCard">車間號</label>
    					<select id="workShop" name='workShop' class="form-control">
    					<option value=""></option>
    					</select>  			
    		</div>
  		</div>
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">上午中休開始時間</label>
      		<input id="insert_rest_start1" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
      		<label for="machineState">上午中休結束時間</label>
    			<input id="insert_rest_end1" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">下午中休開始時間</label>
      		<input id="insert_rest_start2" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
      		<label for="machineState">下午中休結束時間</label>
    			<input id="insert_rest_end2" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">上半夜中休開始時間</label>
      		<input id="insert_rest_start3" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
      		<label for="machineState">上半夜中休結束時間</label>
    		<input id="insert_rest_end3" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />		
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">下半夜中休開始時間</label>
      		<input id="insert_rest_start4" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />
      		<label for="machineState">下半夜中休結束時間</label>
    		<input id="insert_rest_end4" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" />		
  		</div>
  			
	
        <br>
  		<button type="submit" id="setWorkshopNoRestInfo" class="btn btn-primary">設置</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>

