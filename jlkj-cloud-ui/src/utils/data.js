/**
 * 下拉树格式化
 */
export function treeDataformat (a, idStr, pidStr, childrenStr) {
  var r = [], hash = {}, id = idStr, pid = pidStr, children = childrenStr, i = 0, j = 0, len = a.length;
  for (; i < len; i++) {
    hash[a[i][id]] = a[i];
  }
  //最外层加参数不可以选
  for (; j < len; j++) {
    var aVal = a[j], hashVP = hash[aVal[pid]];
    if (hashVP) {
      !hashVP[children] && (hashVP[children] = []);
      //设置flag  参数控制可以选中的节点
      aVal.flag = 'indep'
      // console.log(aVal, "aVal")
      hashVP[children].push(aVal);
    } else {
      var data = aVal;
      delete data[pidStr];
      r.push(data);
    }
  }
  return r;
}
//图片上传 需要的数据格式 （传给后台做回显）
//serveAddress 服务器地址（传过来的全局变量）
export function getUrlArray (fileList, serveAddress) {
  // console.log(fileList)
  let urlArray = []
  fileList.forEach((item) => {
    // urlArray.push(item.response.data.id)
    if (item.response) {
      urlArray.push({
        name: item.name,
        url: `${serveAddress}${item.response.data.relativePath}`
      })
    } else {
      urlArray.push({
        name: item.name,
        url: item.url
      })
    }
  })
  // console.log(urlArray, "urlArray")
  return urlArray
}