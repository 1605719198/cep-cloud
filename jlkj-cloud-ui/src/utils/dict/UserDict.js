
// 回显数据字典
export function UserDictOnlyName(datas, value) {
  const actions = [];
  Object.keys(datas).some((key) => {
    if (datas[key].userName == ('' + value)) {
      actions.push(datas[key].nickName);
      return true;
    }
  })
  return actions.join('');
}
// 回显数据字典 格式：工号 _ 姓名
export function UserDictFullName(datas, value) {
  const actions = [];
  Object.keys(datas).some((key) => {
    if (datas[key].userName == ('' + value)) {
      actions.push(datas[key].userName + '_' +datas[key].nickName);
      return true;
    }
  })
  return actions.join('');
}
