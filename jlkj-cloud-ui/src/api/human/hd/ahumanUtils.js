//获取时间日期
export function getDateTime(e,dates){
  var today ;
  if(dates){
    today = dates
  }else{
    today = new Date();
  }
  var DD = String(today.getDate()).padStart(2, '0'); // 获取日
  var MM = String(today.getMonth() + 1).padStart(2, '0'); //获取月份，1 月为 0
  var yyyy = today.getFullYear(); // 获取年
  var hh = String(today.getHours()).padStart(2, '0'); //获取当前小时数(0-23)
  var mm = String(today.getMinutes()).padStart(2, '0'); //获取当前分钟数(0-59)
  var ss = String(today.getSeconds()).padStart(2, '0'); //获取当前秒数(0-59)
  var time =yyyy + '-' + MM + '-' + DD+' '+hh+':'+mm+':'+ss;
  var date = yyyy + '-' + MM + '-' + DD;
  var ymdhms = yyyy+MM+DD+hh+mm+ss
  if(e===0){
    return time;
  }else if(e===1){
    return date;
  }else if(e==='year'){
    return yyyy
  }else if(e==='ymdhms'){
    return ymdhms
  }
}

// 数字长度校验
export function numberLength(rule,value,callback,required,maxLength,minLength) {
  const re = /^[0-9]*[1-9][0-9]*$/;
  const rsCheck = re.test(value);
  if(required){
  }
  if (!rsCheck) {
    // callback(new Error('请输入正整数'));
    callback()
  }else{
    let str = value.toString();
    let length = str.length;
    if((maxLength&&length>maxLength)||(minLength&&length<minLength)){
      callback(new Error('请输入长度为'+minLength+'到'+maxLength+'之间的数'))
    }else{
      callback();
    }
  }
}
