// 安环/安全作业分析接口
import request from '@/utils/request';
export const getSafetyOperationAnalysis = (page) => {
    return request({
        url: '/safety/safe/safeAnalysis/list',
        method: 'get',
        params: {
            ...page,

        },
    });
}
export const getSafetyOperationAnalysisPie = (page) => {
    return request({
        url: '/safety/safe/safeAnalysis/pie',
        method: 'get',
        params: {
            ...page,

        },
    });
}
export const getSafetyOperationAnalysisLine = (page) => {
    return request({
        url: '/safety/safe/safeAnalysis/line',
        method: 'get',
        params: {
            ...page,

        },
    });
}
