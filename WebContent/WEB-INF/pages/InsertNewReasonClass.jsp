<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="insertReasonClass" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    			<h5>新增原因分類</h5>
			</div>
			<div class="modal-body">
		<div class="control-group">
    		<label class="control-label" for="inputReasonClass">原因分類名稱</label>
    		<div class="controls">
      			<input type="text" id="inputReasonClass" name="inputReasonClass" class="required" placeholder="分類名稱"/>
    		</div>
  		</div>
        <br>
  		<button type="submit" id="submitReasonClass" class="btn btn-primary">新增</button>
  		<button type="reset" id="resetReasonClassSubmit" class="btn">清除</button>
			</div>	
		</div>
	</div>
</div>

