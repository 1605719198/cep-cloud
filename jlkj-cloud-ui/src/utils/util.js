import { validatenull } from './validate'
//表单序列化
export const serialize = data => {
  let list = [];
  Object.keys(data).forEach(ele => {
    list.push(`${ele}=${data[ele]}`)
  })
  return list.join('&');
};
export const getObjType = obj => {
  var toString = Object.prototype.toString;
  var map = {
    '[object Boolean]': 'boolean',
    '[object Number]': 'number',
    '[object String]': 'string',
    '[object Function]': 'function',
    '[object Array]': 'array',
    '[object Date]': 'date',
    '[object RegExp]': 'regExp',
    '[object Undefined]': 'undefined',
    '[object Null]': 'null',
    '[object Object]': 'object'
  };
  if (obj instanceof Element) {
    return 'element';
  }
  return map[toString.call(obj)];
};

/**
 * 对象深拷贝
 */
export const deepClone = data => {
  var type = getObjType(data);
  var obj;
  if (type === 'array') {
    obj = [];
  } else if (type === 'object') {
    obj = {};
  } else {
    //不再具有下一层次
    return data;
  }
  if (type === 'array') {
    for (var i = 0, len = data.length; i < len; i++) {
      obj.push(deepClone(data[i]));
    }
  } else if (type === 'object') {
    for (var key in data) {
      obj[key] = deepClone(data[key]);
    }
  }
  return obj;
};
/**
 * 设置灰度模式
 */
export const toggleGrayMode = (status) => {
  if (status) {
    document.body.className = document.body.className + ' grayMode';
  } else {
    document.body.className = document.body.className.replace(' grayMode', '');
  }
};
/**
 * 设置主题
 */
export const setTheme = (name) => {
  document.body.className = name;
}

/**
 * 加密处理
 */
export const encryption = (params) => {
  let {
    data,
    type,
    param,
    key
  } = params;
  let result = JSON.parse(JSON.stringify(data));
  if (type == 'Base64') {
    param.forEach(ele => {
      result[ele] = btoa(result[ele]);
    })
  } else if (type == 'Aes') {
    param.forEach(ele => {
      result[ele] = window.CryptoJS.AES.encrypt(result[ele], key).toString();
    })

  }
  return result;
};


/**
 * 浏览器判断是否全屏
 */
export const fullscreenToggel = () => {
  if (fullscreenEnable()) {
    exitFullScreen();
  } else {
    reqFullScreen();
  }
};
/**
 * esc监听全屏
 */
export const listenfullscreen = (callback) => {
  function listen () {
    callback()
  }

  document.addEventListener("fullscreenchange", function () {
    listen();
  });
  //lt 火狐适配
  window.addEventListener("mozfullscreenchange", function () {
    if (!fullscreenEnable()) {
      document.getElementsByClassName("avue-left")[0].style.display = "";
      document.getElementsByClassName("avue-header")[0].style.display = "";
      document.getElementsByClassName("avue-tags")[0].style.display = "";
      document.getElementsByClassName("avue-main")[0].classList.remove("fullScreen");
    }
    listen();
  });
  document.addEventListener("webkitfullscreenchange", function () {
    if (!fullscreenEnable()) {
      document.getElementsByClassName("avue-left")[0].style.display = "";
      document.getElementsByClassName("avue-header")[0].style.display = "";
      document.getElementsByClassName("avue-tags")[0].style.display = "";
      document.getElementsByClassName("avue-main")[0].classList.remove("fullScreen");
    }
    listen();
  });
  document.addEventListener("msfullscreenchange", function () {
    listen();
  });
};
/**
 * 浏览器判断是否全屏
 */
export const fullscreenEnable = () => {
  var isFullscreen = document.isFullScreen || document.mozIsFullScreen || document.webkitIsFullScreen || document.mozFullScreen
  return isFullscreen;
}

/**
 * 浏览器全屏
 */
export const reqFullScreen = () => {
  document.getElementsByClassName("avue-left")[0].style.display = "none";
  document.getElementsByClassName("avue-header")[0].style.display = "none";
  document.getElementsByClassName("avue-tags")[0].style.display = "none";
  document.getElementsByClassName("avue-main")[0].classList.add("fullScreen");
  if (document.documentElement.requestFullScreen) {
    document.documentElement.requestFullScreen();
  } else if (document.documentElement.webkitRequestFullScreen) {
    document.documentElement.webkitRequestFullScreen();
  } else if (document.documentElement.mozRequestFullScreen) {
    document.documentElement.mozRequestFullScreen();
  }
};
/**
 * 浏览器退出全屏
 */
export const exitFullScreen = () => {
  document.getElementsByClassName("avue-left")[0].style.display = "";
  document.getElementsByClassName("avue-header")[0].style.display = "";
  document.getElementsByClassName("avue-tags")[0].style.display = "";
  document.getElementsByClassName("avue-main")[0].classList.remove("fullScreen");
  if (document.documentElement.requestFullScreen) {
    document.exitFullScreen();
  } else if (document.documentElement.webkitRequestFullScreen) {
    document.webkitCancelFullScreen();
  } else if (document.documentElement.mozRequestFullScreen) {
    document.mozCancelFullScreen();
  }
};
/**
 * 递归寻找子类的父类
 */

export const findParent = (menu, id) => {
  for (let i = 0; i < menu.length; i++) {
    if (menu[i].children.length != 0) {
      for (let j = 0; j < menu[i].children.length; j++) {
        if (menu[i].children[j].id == id) {
          return menu[i];
        } else {
          if (menu[i].children[j].children.length != 0) {
            return findParent(menu[i].children[j].children, id);
          }
        }
      }
    }
  }
};
/**
 * 判断2个对象属性和值是否相等
 */

/**
 * 动态插入css
 */

export const loadStyle = url => {
  const link = document.createElement('link');
  link.type = 'text/css';
  link.rel = 'stylesheet';
  link.href = url;
  const head = document.getElementsByTagName('head')[0];
  head.appendChild(link);
};
/**
 * 判断路由是否相等
 */
export const diff = (obj1, obj2) => {
  delete obj1.close;
  var o1 = obj1 instanceof Object;
  var o2 = obj2 instanceof Object;
  if (!o1 || !o2) { /*  判断不是对象  */
    return obj1 === obj2;
  }

  if (Object.keys(obj1).length !== Object.keys(obj2).length) {
    return false;
    //Object.keys() 返回一个由对象的自身可枚举属性(key值)组成的数组,例如：数组返回下表：let arr = ["a", "b", "c"];console.log(Object.keys(arr))->0,1,2;
  }

  for (var attr in obj1) {
    var t1 = obj1[attr] instanceof Object;
    var t2 = obj2[attr] instanceof Object;
    if (t1 && t2) {
      return diff(obj1[attr], obj2[attr]);
    } else if (obj1[attr] !== obj2[attr]) {
      return false;
    }
  }
  return true;
}
/**
 * 根据字典的value显示label
 */
export const findByvalue = (dic, value) => {
  let result = '';
  if (validatenull(dic)) return value;
  if (typeof (value) == 'string' || typeof (value) == 'number' || typeof (value) == 'boolean') {
    let index = 0;
    index = findArray(dic, value);
    if (index != -1) {
      result = dic[index].label;
    } else {
      result = value;
    }
  } else if (value instanceof Array) {
    result = [];
    let index = 0;
    value.forEach(ele => {
      index = findArray(dic, ele);
      if (index != -1) {
        result.push(dic[index].label);
      } else {
        result.push(value);
      }
    });
    result = result.toString();
  }
  return result;
};
/**
 * 根据字典的value查找对应的index
 */
export const findArray = (dic, value) => {
  for (let i = 0; i < dic.length; i++) {
    if (dic[i].value == value) {
      return i;
    }
  }
  return -1;
};
/**
 * 生成随机len位数字
 */
export const randomLenNum = (len, date) => {
  let random = '';
  random = Math.ceil(Math.random() * 100000000000000).toString().substr(0, len ? len : 4);
  if (date) random = random + Date.now();
  return random;
};
/**
 * 打开小窗口
 */
export const openWindow = (url, title, w, h) => {
  // Fixes dual-screen position                            Most browsers       Firefox
  const dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left
  const dualScreenTop = window.screenTop !== undefined ? window.screenTop : screen.top

  const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width
  const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height

  const left = ((width / 2) - (w / 2)) + dualScreenLeft
  const top = ((height / 2) - (h / 2)) + dualScreenTop
  const newWindow = window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=no, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left)

  // Puts focus on the newWindow
  if (window.focus) {
    newWindow.focus()
  }
}

/**
 * 获取顶部地址栏地址
 */
export const getTopUrl = () => {
  return window.location.href.split("/#/")[0];
}

/**
 * 获取url参数
 * @param name 参数名
 */
export const getQueryString = (name) => {
  let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
  let r = window.location.search.substr(1).match(reg);
  if (r != null) return unescape(decodeURI(r[2]));
  return null;
}
// 验证金额数字不可以为负数
export const moneyNValid = function (rule,val,cb) {
  valdateFn(MoneyTest,val,cb);
};
export const valdateFn = function (rule,val,cb) {
  setTimeout(() => {
    if(val) {
      let inputVal = val.replace(/\s/g,"")|| '';
      inputVal = delcommafy(inputVal);
      if (rule.test(inputVal)) {
        cb()
      } else {
        cb('只能是数字金额,最多两位小数')
      }
    }
    cb()
  })
}
// 获取输入框的值
export const getInputValue = function (el,num) {
  let inputVal = el.target.value.replace(/\s/g,"")|| '';
  return formatCurrency(delcommafy(inputVal),num);
};
// 金额千分位 （全局挂载）
export function  stateFormat(row, column, cellValue) {
  cellValue += '';
  if (!cellValue.includes('.')) cellValue += '.';
  return cellValue.replace(/(\d)(?=(\d{3})+\.)/g, function ($0, $1) {
    return $1 + ',';
  }).replace(/\.$/, '');
}
//金额千分符 会在整数后添加两个0
export function  stateToFixedFormat(row, column, cellValue) {
  if (cellValue) {
    return Number(cellValue)
      .toFixed(2)
      .replace(/(\d)(?=(\d{3})+\.)/g, ($0, $1) => {
        return $1 + ",";
      })
      .replace(/\.$/, "");
  }
}
// 金额千分位
export function formatCurrency (num) {
  if (num) {
    // 将num中的$,去掉，将num变成一个纯粹的数据格式字符串
    num = num.toString().replace(/\$|\,/g, '')
    // 如果num不是数字，则将num置0，并返回
    if (num === '' || isNaN(num)) {
      return 'Not a Number ! '
    }
    // 如果num是负数，则获取她的符号
    var sign = ''
    if (num.indexOf('-') !== -1) {
      sign = '-'
      num = num.substr(1)
    }
    // 如果存在小数点，则获取数字的小数部分
    var cents = num.indexOf('.') > 0 ? num.substr(num.indexOf('.')) : ''
    cents = cents.length > 1 ? cents : '.00' // 注意：这里如果是使用change方法不断的调用，小数是输入不了的
    // 获取数字的整数数部分
    num = num.indexOf('.') > 0 ? num.substring(0, (num.indexOf('.'))) : num
    // 如果没有小数点，整数部分不能以0开头
    if (cents === '') {
      if (num.length > 1 && num.substr(0, 1) === '0') {
        return 'Not a Number ! '
      }
    } else {
      if (num.length > 1 && num.substr(0, 1) === '0') {
        return 'Not a Number ! '
      }
    }
    // 针对整数部分进行格式化处理，这是此方法的核心，也是稍难理解的一个地方，逆向的来思考或者采用简单的事例来实现就容易多了
    /*
      也可以这样想象，现在有一串数字字符串在你面前，如果让你给他家千分位的逗号的话，你是怎么来思考和操作的?
      字符串长度为0/1/2/3时都不用添加
      字符串长度大于3的时候，从右往左数，有三位字符就加一个逗号，然后继续往前数，直到不到往前数少于三位字符为止
     */
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) {
      num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3))
    }
    // 将数据（符号、整数部分、小数部分）整体组合返回
    return sign + num + cents
  }
}
export function strToDate(dateStr,separator){
  if(!separator){
    separator="-";
  }
  var dateArr = dateStr.split(separator);
  var year = parseInt(dateArr[0]);
  //var month;
//处理月份为01这样的情况
 /* if(dateArr[1].indexOf("0") == 0){
    month = parseInt(dateArr[1].substring(1));
  }else{
    month = parseInt(dateArr[1]);
  }*/
  var month = parseInt(dateArr[1]);
  var day = parseInt(dateArr[2]);
  var date = new Date(year,month -1,day);
  return date;
}
//去除千分位中的‘，'
export const delcommafy = function (num){
  if(!num) return num;
  num = num.toString();
  num = num.replace(/,/gi, '');
  return num;
};


