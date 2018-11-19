/**
 * 常用的javascript function
 */


function ShowTableHeader(reportType){
	var Title='';
	switch(reportType){
		case 'Account':
			Title = '<tr><th>賬號</th>'+
			'<th>姓名</th>'+
			'<th>關聯助理ID</th>'+
			'<th>部門代碼</th>'+
			'<th>報加班費用代碼</th>'+
			'<th>郵箱</th>'+
			'<th>分機號碼</th>'+
			'<th>查詢賬號權限</th>'+
			'<th>是否有效</th></tr>';
			break;
		case '':
			Title='<tr></tr>';
		break;
		case '':
			Title='<tr></tr>';
			break;
		case '':
			Title='<tr></tr>';
			break;
		default:
			break;
	
	}
	return Title;
}

function ShowTableContents(rawData,reportType){
	var tableContents='';
	if(reportType==''){
		tableContents+='<tr><td>'+rawData.SN+'</td>'+
			'<td>'+rawData.FACTORY+'</td>'+
			'<td>'+rawData.WORKSHOP+'</td>'+
			'<td>'+rawData.LINENO+'</td>'+
			'<td>'+rawData.MODELNO+'</td></tr>';
	}
	else{
		for(var i=0;i<rawData.length;i++){
			 if(reportType=='RobotState'){
				 if(rawData[i]["ERRORCODE"]=='正常運行'){
					tableContents+='<tr class="success"><td>'+rawData[i]["TIME"]+'</td>'+
					'<td>'+rawData[i]["STATUSCODE"]+'</td>'+
					'<td>'+rawData[i]["ERRORCODE"]+'</td></tr>';
				 }
				 else{
						tableContents+='<tr class="danger"><td>'+rawData[i]["TIME"]+'</td>'+
						'<td>'+rawData[i]["STATUSCODE"]+'</td>'+
						'<td>'+rawData[i]["ERRORCODE"]+'</td></tr>';
				 }

			}
			else{
				tableContents+='<tr><td>'+rawData[i]["TIME"]+'</td>'+
					'<td>'+rawData[i]["MAX"]+'</td>'+
					'<td>'+rawData[i]["AVG"]+'</td>'+
					'<td>'+rawData[i]["MIN"]+'</td>'+
				'</tr>';
			}
		}
	}
	return tableContents;
}

/*取得Query String 的值*/
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    if (results[2]==='%') return '%';//安裝車間查詢需要設置LineNo='%'
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
//文件上传函数
var getFileContent = function(fileInput, callback) {
	if (fileInput.files && fileInput.files.length > 0
			&& fileInput.files[0].size > 0) {
		// 下面这一句相当于JQuery的：var file
		// =$("#upload").prop('files')[0];
		var file = fileInput.files[0];
		if (window.FileReader) {
			var reader = new FileReader();
			reader.onloadend = function(evt) {
				if (evt.target.readyState == FileReader.DONE) {
					callback(evt.target.result);
				}
			};
			// 包含中文内容用gbk编码
			reader.readAsText(file, 'gbk');
		}
	}
};

/**
 * 上传函数
 * @param fileInput DOM对象
 * @param callback 回调函数
 */
var getFileContent = function (fileInput, callback) {
    if (fileInput.files && fileInput.files.length > 0 && fileInput.files[0].size > 0) {
        //下面这一句相当于JQuery的：var file =$("#upload").prop('files')[0];
        var file = fileInput.files[0];
        if (window.FileReader) {
            var reader = new FileReader();
            reader.onloadend = function (evt) {
                if (evt.target.readyState == FileReader.DONE) {
                    callback(evt.target.result);
                }
            };
            // 包含中文内容用gbk编码
            reader.readAsText(file, 'gbk');
        }
    }
};


function formatDate(now) {
	var year=now.getYear();
	var month=now.getMonth()+1;
	var date=now.getDate();
	var hour=now.getHours();
	var minute=now.getMinutes();
	var second=now.getSeconds();
	return year+"-"+month+"-"+date;
	} 

function formatTime(now) {
	var year=now.getYear();
	var month=now.getMonth()+1;
	var date=now.getDate();
	var hour=now.getHours();
	var minute=now.getMinutes();
	var second=now.getSeconds();
	return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
	} 

