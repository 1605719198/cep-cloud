import { fileUploadUrl, pdfUrl, designerUrl } from '@/config/env.js';

const serveAddress = 'http://192.168.105.249:12888/';//图片显示
const cwcbUrl = 'http://10.32.100.35:8080/';//首页财务地址
const fileUrl = fileUploadUrl;  //上传
const designUrl = designerUrl;  //上传
const pdfFileUrl = pdfUrl;  //pdf下载
export default {
  serveAddress,
  fileUrl,
  designUrl,
  pdfFileUrl,
  cwcbUrl
}
