<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertLineNoDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增線體</h5>
			</div>
			<div class="modal-body">
	  <div class="control-group">
    		<label class="control-label" for="selectWorkShopNo">車間名稱</label>
    		<div class="controls">
      			<select id="selectWorkShopNo" class="selectWorkShopNo" name="selectWorkShopNo">     				
      			</select>
    		</div>
  		</div>
  		
  		<div class="control-group">
    		<label class="control-label" for="inputLineNo">線體名稱</label>
    		<div class="controls">
      			<input type="text" id="inputLineNo" name="inputLineNo" class="inputLineNo" placeholder="線體名稱">
    		</div>
  		</div>	
        <br>
  		<button type="submit" id="submitNewLineNo" class="btn btn-primary">新增</button>
  		<button type="reset" id="resetNewLineNoSubmit" class="btn">清除</button>
			</div>	
		</div>
	</div>
</div>

