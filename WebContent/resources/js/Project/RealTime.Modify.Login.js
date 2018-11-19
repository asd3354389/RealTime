$(document).ready(function(){
	var isUserNameValid=false,isChineseNameValid=false,isUserDepIdValid=false,isUserAssistantIdValid=false;   
	    if (window != top) {    
	        top.location.href = location.href;    
	    }     
	  $('#resetPassword').click(function(){
			var user={},errorMessage='';
			user["USERNAME"]=$('#chagePasswordUsername').val();
			user["CHINESENAME"]=$('#chagePasswordChineseName').val();
			user["DEPARTMENTCODE"]=$('#chagePasswordDepId').val();
			user["ASSISTANT_ID"]=$('#chagePasswordAssistantId').val();	
			
			var newPassword=$('#newPassword').val();
			var confirmPassword=$('#confirmPassword').val();
			
			if(user["USERNAME"]==="null" || user["USERNAME"]=='')
				errorMessage+='帳號未填寫\n';
			
			checkUserNameDuplicate(user["USERNAME"]);
			
			if(user["CHINESENAME"]=='' || user["CHINESENAME"]==null){
				errorMessage+='姓名（中文）必填 \n';
			}
			else{
				var regx=/^[\u0391-\uFFE5]+$/;
				if(!user["CHINESENAME"].match(regx)){
					errorMessage+='姓名（中文）格式錯誤，請重新輸入\n'
				}
			}
			
			//checkChineseNameDuplicate(user);
			
			if(user["DEPARTMENTCODE"]==="null" || user["DEPARTMENTCODE"]=='')
				errorMessage+='部門代碼未填寫\n';
			
			//checkUserDepIdDuplicate(user);
			
			if(user["ASSISTANT_ID"]==="null" || user["ASSISTANT_ID"]=='')
				errorMessage+='報加班助理ID未填寫\n';
			
			//checkUserAssistantIdDuplicate(user);
			
			if(newPassword!=confirmPassword)
				errorMessage+='两次输入密码不同，请重新输入!\n';
			else
				user["PASSWORD"]=newPassword;
			
			checkChangePassWordUserInfo(user)
			
		if(errorMessage=='' && isUserNameValid && isChineseNameValid && isUserDepIdValid && isUserAssistantIdValid){
				//修改賬號密码
				$.ajax({
					type:'POST',
					contentType: "application/json",
					url:'ChangePassWord/updateAccountPassWord.do',
					data:JSON.stringify(user),
					dataType:'json',
					success:function(data){
						$('#resetPassword').prop("disabled",false);
						if (data != null && data != "") {
							if(data.StatusCode=="200"){
								alert(data.Message);			
								$('#chagePasswordUsername').val('');
								$('#chagePasswordChineseName').val('');
								$('#chagePasswordDepId').val('');
								$('#chagePasswordAssistantId').val('');
								$("#newPassword ").val('');
								$('#confirmPassword').val('');
							}
							else{
								alert(data.Message);
							}
						}else{
							alert('操作失敗!');
						}
					},
					error:function(e){
						alert('修改賬號密码發生錯誤');
					}
				});
			}
		    else{
		    	if(errorMessage.length>0 ||errorMessage!='' ){
			    alert(errorMessage);		
				event.preventDefault(); //preventDefault() 方法阻止元素发生默认的行为（例如，当点击提交按钮时阻止对表单的提交）。
			}
		}
		
		});
	  	  
	  function checkUserNameDuplicate(userName){
			if(userName!=""){
				$.ajax({
					type:'POST',
					url:'ChangePassWord/checkUserName.do',
					data:{
						userName:userName
					},
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(data){	
						if (data != null && data != "") {
							if(data.StatusCode==500){
								alert(data.Message);
								isUserNameValid=false;
							}
							else
								isUserNameValid=true;
						}else{
							isUserNameValid=false;
						}
					}
				});
			}
		}
	  
	  
	  function checkChangePassWordUserInfo(changePasswordUser){
				$.ajax({
					type:'POST',
					url:'ChangePassWord/FindChangePassWordUserInfo.show',
					contentType: "application/json",
					data:JSON.stringify(changePasswordUser),
					async:false,
					error:function(e){
						alert(e);
					},
					success:function(executeResult){
						if (executeResult != null && executeResult != "") {
											if (executeResult.length > 0) {
												if(changePasswordUser["CHINESENAME"]===executeResult[0]["CHINESENAME"]){
													isChineseNameValid = true;
												} else{
													isChineseNameValid = false;
													alert("賬號姓名错误，请重新填写！")
												}
												if(changePasswordUser["DEPARTMENTCODE"]===executeResult[0]["DEPARTMENTCODE"]){
													isUserDepIdValid = true;
												} else{
													isUserDepIdValid = false;
													alert("賬號部门代码错误，请重新填写！")
												}
												if(changePasswordUser["ASSISTANT_ID"]===executeResult[0]["ASSISTANT_ID"]){
													isUserAssistantIdValid = true;
												} else{
													isUserAssistantIdValid = false;
													alert("賬號助理代码错误，请重新填写！")
												}
											}
						}else{	
								alert('操作失敗!');
						}
				    }
				});
			}
	 
});