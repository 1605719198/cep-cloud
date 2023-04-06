// 配置编译环境和线上环境之间的切换

let baseUrl = '';
let fileUploadUrl = 'http://192.168.105.249:9300';
let pdfUrl = 'http://192.168.105.249:9321';
let iconfontVersion = ['567566_pwc3oottzol', '1066523_6bvkeuqao36'];
let iconfontUrl = `//at.alicdn.com/t/font_$key.css`;
let codeUrl = `${baseUrl}/code`
let mqConfig = {};
const env = process.env
// console.log(env)
if (env.NODE_ENV == 'development') {
  baseUrl = ``; // 开发环境地址
  fileUploadUrl = 'http://localhost:9300';
  pdfUrl = 'http://localhost';
  mqConfig = {
    url: 'ws://192.168.105.218:15674/ws',
    user: 'admin',
    pass: 'admin'
  };
} else if (env.NODE_ENV == 'production') {
  baseUrl = ``; //生产环境地址
  fileUploadUrl = 'http://10.32.157.6:9321/neplatform/fileresource';
  pdfUrl = 'http://10.32.157.6:9321';
  mqConfig = {
    url: 'ws://10.32.157.5:15674/ws',
    user: 'guest',
    pass: 'guest'
  };
} else if (env.NODE_ENV == 'test') {
  baseUrl = ``; //测试环境地址
} else if (env.NODE_ENV == 'xtk') {
  baseUrl = ``; //测试环境地址
  fileUploadUrl = 'http://192.168.105.249:9321/neplatform/fileresource';
  pdfUrl = 'http://192.168.105.249:9321';
  mqConfig = {
    url: 'ws://192.168.105.218:15674/ws',
    user: 'admin',
    pass: 'admin'
  };
} else if (env.NODE_ENV == 'mine') {
  baseUrl = ``; // 开发环境地址
  fileUploadUrl = 'http://localhost:9300';
  pdfUrl = 'http://localhost:9321';
  mqConfig = {
    url: 'ws://192.168.105.218:15674/ws',
    user: 'admin',
    pass: 'admin'
  };
}
export {
  baseUrl,
  fileUploadUrl,
  pdfUrl,
  iconfontUrl,
  iconfontVersion,
  codeUrl,
  env,
  mqConfig,
}
