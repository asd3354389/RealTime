<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertWorkShopDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增車間</h5>
			</div>
			<div class="modal-body">
		<div class="control-group">
    		<label class="control-label" for="inputWorkShopNo">車間名稱</label>
    		<div class="controls">
      			<input type="text" id="inputWorkShopNo" name="inputWorkShopNo" class="required" placeholder="車間名稱">
    		</div>
  		</div>
        <br>
  		<button type="submit" id="submitNewWorkShop" class="btn btn-primary">新增</button>
  		<button type="reset" id="resetNewWorkShopSubmit" class="btn">清除</button>
			</div>	
		</div>
	</div>
</div>

