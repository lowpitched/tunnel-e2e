

function get(url,  data=null, headers={}) {
    return ajaxRequest(url, 'GET', data, headers);
}

function post(url, data=null, headers={}) {
    return ajaxRequest(url, 'POST', data, headers);
}

function put(url, data=null, headers={}) {
    return ajaxRequest(url, 'PUT', data, headers);
}

function del(url, data=null, headers={}) {
    return ajaxRequest(url, 'DELETE', data, headers);
}

function generateUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3) | 0x8;
    return v.toString(16);
  });
}

/**
 * 通用 AJAX 请求方法
 * @param {string} url - 请求地址
 * @param {string} method - 请求方法（GET, POST, PUT, DELETE 等）
 * @param {Object|null} data - 请求体数据（POST/PUT 时使用）
 * @param {Object} headers - 自定义请求头
 * @returns {Promise<any>} 响应数据
 */
async function ajaxRequest(url, method = 'GET', data = null, headers = {}) {
    const config = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            ...headers
        }
    };

    if (data) {
        config.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(url, config);

        if (!response.ok) {
            throw new Error(`HTTP 错误! 状态码: ${response.status}`);
        }
        const text = await response.text(); // 先以文本形式读取
        if (!text) {
            return null;
        }
        return JSON.parse(text); // 手动解析 JSON
    } catch (error) {
        console.error('AJAX 请求失败:', error);
        throw error;
    }
}

