//获取时间日期
export function getDateTime(e){
  var today = new Date(); //日期
  var DD = String(today.getDate()).padStart(2, '0'); // 获取日
  var MM = String(today.getMonth() + 1).padStart(2, '0'); //获取月份，1 月为 0
  var yyyy = today.getFullYear(); // 获取年
  var hh = String(today.getHours()).padStart(2, '0'); //获取当前小时数(0-23)
  var mm = String(today.getMinutes()).padStart(2, '0'); //获取当前分钟数(0-59)
  var ss = String(today.getSeconds()).padStart(2, '0'); //获取当前秒数(0-59)
  var time =yyyy + '-' + MM + '-' + DD+' '+hh+':'+mm+':'+ss;
  var date = yyyy + '-' + MM + '-' + DD;
  if(e==0){
    return time;
  }else if(e==1){
    return date;
  }
}
