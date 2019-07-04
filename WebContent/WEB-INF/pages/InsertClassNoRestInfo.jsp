<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="replactNewClassNo" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>替换班别加班休息時間段</h5>
			</div>
			<div class="modal-body">
			
		<div class="control-group">
    		<div class="controls">
      			<label for="outCard">費用代碼</label>
    					<!-- <select id="CostNo" name='CostNo' class="form-control">
    						<option value=" "></option>
    					</select>  			 -->
    			<div class="controls">
      				<input type="text" id="CostNo" name="CostNo" class="required nameCheck form-control" placeholder="费用代码" onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1,');">
    			</div>
    					
    		</div>
  		</div> 		
  		<div class="control-group">
    		<div class="controls">
      			<label for="outCard">班別號</label>
    					<select id="classNo" name='classNo' class="form-control">
    					<option value=""></option>
    					</select>  		<!-- 	 -->
    		</div>
  		</div>
  		<div class="control-group">
  <!--   		<label class="control-label" for="inputOTCarid">加班休息開始時間一</label>
      		<input id="insert_rest_start1" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" / disabled="disabled" >
      		<label for="machineState">加班休息結束時間一</label>
    		<input id="insert_rest_end1" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" / disabled="disabled">
  		</div> -->
  		
  		<div class="control-group">
    		<label class="control-label" for="inputOTCarid">加班替換休息開始時間</label>
      		<input id="insert_rest_start2" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" /disabled="disabled">
      		<label for="machineState">加班替換休息結束時間</label>
    			<input id="insert_rest_end2" class="Wdate" type="text"  style="width:60px" onClick="WdatePicker({lang:'zh-cn',dateFmt:'HHmm'})" autocomplete="off" / disabled="disabled">
  		</div>
  		
  		
  			
	
        <br>
  		<button type="submit" id="setClassNoRestInfo" class="btn btn-primary">設置</button>
  		<button type="reset" id="resetSubmit" class="btn">清除</button>
<!-- 	</form>	  -->
			</div>	
		</div>
	</div>
</div>

