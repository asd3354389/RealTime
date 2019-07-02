	var _menus = {
		"menus" : [ {
			"menuid" : "1",
			"icon" : "icon-sys",
			"menuname" : "加班單管理",
			"menus" : [ {
				"menuname" : "加班單審核",
				"icon" : "icon-nav",
				"url" : "Overtime/OT.show"
			},{
				"menuname" : "加班單-富強線長",
				"icon" : "icon-nav",
				"url" : "Overtime/OTByLL.show"
			},{
				"menuname" : "員工加班詳情",
				"icon" : "icon-nav",
				"url" : "ShowCheckOverTimeStatus"
			}, {
				"menuname" : "上下班刷卡記錄查詢",
				"icon" : "icon-nav",
				"url" : "ShowCheckSwipeCardRecords"
			}, {
				"menuname" : "上下班刷卡超15分鐘",
				"icon" : "icon-nav",
				"url" : "ShowCheckOverTime15min"
			},{
				"menuname" : "全天無刷卡人员查询",
				"icon" : "icon-nav",
				"url" : "ShowCheckForgetCard"
			},{
				"menuname" : "班别信息查询",
				"icon" : "icon-nav",
				"url" : "ShowCheckShiftStatus"
			},{
				"menuname" : "原始刷卡記錄查詢",
				"icon" : "icon-nav",
				"url" : "RawRecord/ShowRawRecord"
			},{
				"menuname" : "崗位津貼查詢",
				"icon" : "icon-nav",
				"url" : "DGsubsidy/ShowDGsubsidy"
			},{
				"menuname" : "員工職位維護",
				"icon" : "icon-nav",
				"url" : "JobTitle/ShowAllJobTitle"
			},{
				"menuname" : "線別維護",
				"icon" : "icon-nav",
				"url" : "LineMapping/ShowAllLineMapping"
			}]
		},{
			"menuid" : "55",
			"icon" : "icon-sys",
			"menuname" : "車間門禁管理",
			"menus" : [ {
				"menuname" : "隨綫人員維護",
				"icon" : "icon-nav",
				"url" : "FlinePerson/ShowFlinePersonMaintain"
			},{
				"menuname" : "離崗卡與費用代碼綁定維護",
				"icon" : "icon-nav",
				"url" : "OTCardPerson/ShowOTCardbdPerson"
			},{

				"menuname" : "線組代碼對應的實時卡機設定",
				"icon" : "icon-nav",
				"url" : "IpBinding/ShowIpBinding"
			},{

				"menuname" : "員工對應的實時卡機設定",
				"icon" : "icon-nav",
				"url" : "EmpIPBinding/ShowEmpIPBinding"
			},{

				"menuname" : "進出車間卡機IP維護",
				"icon" : "icon-nav",
				"url" : "IOCardBdIP/ShowIOCardbdIP"

			},{
				"menuname" : "進出車間臨時權限維護",
				"icon" : "icon-nav",
				"url" : "IOWorkShopPower/ShowIOWorkShopPwList"

			},{
				"menuname" : "保密車間臨時權限維護",
				"icon" : "icon-nav",
				"url" : "IOSpecialWSEmp/ShowIOSpecialWSEmp"
			},{
				"menuname" : "維護車間休息時間段",
				"icon" : "icon-nav",
				"url" : "WorkShopNoRest/ShowWorkshopNoRestInfo"

			},{
				"menuname" : "进出车间门禁記錄查詢",
				"icon" : "icon-nav",
				"url" : "IOWorkShop/ShowIOWorkShopRecord"

			},{
				"menuname" : "車間例外費用代碼維護",
				"icon" : "icon-nav",
				"url" : "ExceptionCost/ShowExceptionCost"

			},{
				"menuname" : "十四休一維護",
				"icon" : "icon-nav",
				"url" : "FourteenRO/ShowFourteenRO"

			}]
		},{

			"menuid" : "56",
			"icon" : "icon-sys",
			"menuname" : "系統信息管理",
			"menus" : [ {
				"menuname" : "助理信息管理",
				"icon" : "icon-nav",
				"url" : "Assistant/ShowAllAssistant"
			}, {
				"menuname" : "賬號管理",
				"icon" : "icon-nav",
				"url" : "Account/ShowAllAccount"
			}, {
				"menuname" : "車間管理",
				"icon" : "icon-nav",
				"url" : "WorkShop/ShowAllWorkShop"
			}]
		} ]
	};

	$(function() {

		$('#loginOut').click(function() {
			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
				if (r) {
					location.href = 'Login?logout';
				}
			});

		})

	});
	
	$(document).ajaxComplete(function(event,obj,settings){
        if (obj.responseText == 'timeout') { //超时标识
        	alert("session timeOut!!!!");
        	top.location.href='/Login'; //跳转到登录页面
        }
    });