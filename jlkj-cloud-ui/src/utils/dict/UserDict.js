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
