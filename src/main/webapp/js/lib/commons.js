Date.prototype.format = function(fmt) { 
     var o = { 
        "M+" : this.getMonth()+1,                 //月份 
        "d+" : this.getDate(),                    //日 
        "h+" : this.getHours(),                   //小时 
        "m+" : this.getMinutes(),                 //分 
        "s+" : this.getSeconds(),                 //秒 
        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        "S"  : this.getMilliseconds()             //毫秒 
    }; 
    if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
     for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
     }
    return fmt; 
};

//表单工具类
(function($){
	//form json序列化
	$.fn.serializeJson=function(){
		var serializeObj={};
		var array=this.serializeArray();
		var str=this.serialize();
		$(array).each(function(){
			if(serializeObj[this.name]){
				if($.isArray(serializeObj[this.name])){
					serializeObj[this.name].push(this.value);
				}else{
					serializeObj[this.name]=[serializeObj[this.name],this.value];
				}
			}else{
				serializeObj[this.name]=this.value;	
			}
		});
		return serializeObj;
	};
	//表单加载json对象数据
	$.fn.fillForm=function (jsonValue) {
        var obj = this;
        $.each(jsonValue, function (name, ival) {
            var $oinput = obj.find("input[name=" + name + "]");
            if ($oinput.attr("type") == "checkbox") {
                if (ival !== null) {
                    var checkboxObj = $("[name=" + name + "]");
                    var checkArray = ival.split(";");
                    for (var i = 0; i < checkboxObj.length; i++) {
                        for (var j = 0; j < checkArray.length; j++) {
                            if (checkboxObj[i].value == checkArray[j]) {
                                checkboxObj[i].click();
                            }
                        }
                    }
                }
            }
            else if ($oinput.attr("type") == "radio") {
                $oinput.each(function () {
                    var radioObj = $("[name=" + name + "]");
                    for (var i = 0; i < radioObj.length; i++) {
                        if (radioObj[i].value == ival) {
                            radioObj[i].click();
                        }
                    }
                });
            }
            else if ($oinput.attr("type") == "textarea") {
                obj.find("[name=" + name + "]").html(ival);
            }
            else {
                obj.find("[name=" + name + "]").val(ival);
            }
        });
    };
	
})(jQuery);