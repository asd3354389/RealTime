$(document).ready(function(){
	$("#id-query2").click(function(){
		$("#idEcharts").empty();
		var varCostID=$('#CostID').val();
		var varStartTime=$('#id-StartTimeValue').val();
		var varEndTime=$('#id-EndTimeValue').val();
		$('#id-query2').attr("disabled",true);
		$.ajax({
			   url:"../EChartss/getChartSwipeCardAB", 
			   type:'post',
			   data:{varStartTime:varStartTime,varEndTime:varEndTime},
			   success:function(data){
				if(data == null || data == ''){
					alert("查無料號資料！");
					$('#id-query2').attr("disabled",false);
					return;
				}else{
					$.each(data,function(i,result){
						console.log(result);					
						//console.log(result['SIZE_SN']);
						creatChart(i,result);
					});
					$('#id-query2').attr("disabled",false);
				}
			   }, 
			   error:function(err){ 
			    alert("查詢ALERT_SWIPECARD_AB_WECHAT，失敗原因："+err); 
			    $('#id-query2').attr("disabled",false);
			   } 
		}) 
	});
})
	
	function creatChart(i,result){
		$('#idEcharts').append("   <div >"+i+" </div> <div id='main"+i+"' style=\"height:300px;width:90%\"> ");
		
		var myChart = echarts.init(document.getElementById('main'+i));
		var DataOne=result['0']['1'];
		DataOne=DataOne.substr(1,DataOne.length-2).split(",");
		var DataTwo=result['0']['2'];
		DataTwo=DataTwo.substr(1,DataTwo.length-2).split(",");
		var DataThree=result['0']['3'];
		DataThree=DataThree.substr(1,DataThree.length-2).split(",");
		var DataFour=result['0']['4'];
		DataFour=DataFour.substr(1,DataFour.length-2).split(",");
		var xdate=result['0']['xdate'];
		xdate=xdate.substr(1,xdate.length-2).split(",");
		

		option = {
				color:['#458FE3', '#48C964', '#FB8989', '#FFA500'],
			    title: {
			        text:i+"異常曲綫圖"
			    },
			    tooltip: {
			        trigger: 'axis'
			    },
			    legend: {
			        data: ['上班超時15分鐘刷卡', '下班超時15分鐘刷卡', '超7休1', '下班超15分鐘未刷卡']
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '20%',
			        containLabel: true
			    },
			    toolbox: {
			        feature: {
			            saveAsImage: {}
			        }
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: xdate
			    },
	    	    dataZoom:{//时间轴？
	    	        realtime:true, //拖动滚动条时是否动态的更新图表数据
	    	        height:25,//滚动条高度
	    	        start:0,//滚动条开始位置（共100等份）
	    	        end:100//结束位置（共100等份）
	    	     },
			    yAxis: {
			        type: 'value'
			    },
			    series: [
			        {
			            name: '上班超時15分鐘刷卡',
			            type: 'line',
			            symbol: 'pin',
			            symbolSize: 12, //曲线点大小
			            label: {
			                normal: {
			                    show: true,
			                    position: 'top'
			                }
			            },
			            lineStyle: {
			                normal: {
			                    width: 2
			                }
			            },
			            smooth: true,
			            data: DataOne
			        },
			        {
			            name: '下班超時15分鐘刷卡',
			            type: 'line',
			            symbol: 'pin',
			            symbolSize: 12, //曲线点大小
			            label: {
			                normal: {
			                    show: true,
			                    position: 'top'
			                }
			            },
			            lineStyle: {
			                normal: {
			                    width: 2
			                }
			            },
			            smooth: true,
			            data: DataTwo

			        },
			        {
			            name: '超7休1',
			            type: 'line',
			            symbol: 'pin',
			            symbolSize: 12, //曲线点大小
			            label: {
			                normal: {
			                    show: true,
			                    position: 'top'
			                }
			            },
			            lineStyle: {
			                normal: {
			                    width: 2
			                }
			            },
			            smooth: true,
			            data: DataThree
			        },
			        {
			            name: '下班超15分鐘未刷卡',
			            type: 'line',
			            symbol: 'pin',
			            symbolSize: 12, //曲线点大小
			            label: {
			                normal: {
			                    show: true,
			                    position: 'top'
			                }
			            },
			            lineStyle: {
			                normal: {
			                    width: 2
			                }
			            },
			            smooth: true,
			            data: DataFour
			        }
			    ]
			};
		
	    myChart.setOption(option);   
	    $(window).resize(function(){
	        myChart.resize();

	    })

	}
	
	
	
