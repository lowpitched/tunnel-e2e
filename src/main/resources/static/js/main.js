
function saveButtonPosition() {
    const modal = new InputModal({
            title: '请输入模板名称',
            placeholder: '例如：原神操作面板',
            confirmText: '确定',
            cancelText: '取消',
            onConfirm: function(inputValue) {
                console.log('用户输入:', inputValue);
                post('');
            },
            onCancel: function() {
                console.log('用户取消了输入');
            }
        });
        modal.open();
}

function addNewButton(btn) {
    const button = document.createElement('div');
    button.className = 'draggable-button';
    button.textContent = '•';

    // 设置初始位置在加号按钮附近
    button.style.top = (btn.offsetTop + 50) + 'px';
    button.style.left = (btn.offsetLeft) + 'px';

    document.body.appendChild(button);

    makeDraggable(button);
}

function makeDraggable(element) {
    let offsetX = 0, offsetY = 0, isDragging = false;

    element.addEventListener('mousedown', function (e) {
        isDragging = true;
        offsetX = e.clientX - element.offsetLeft;
        offsetY = e.clientY - element.offsetTop;
        element.style.cursor = 'grabbing';
    });

    document.addEventListener('mousemove', function (e) {
        if (isDragging) {
            element.style.left = (e.clientX - offsetX) + 'px';
            element.style.top = (e.clientY - offsetY) + 'px';
        }
    });

    document.addEventListener('mouseup', function () {
        isDragging = false;
        element.style.cursor = 'grab';
    });
}


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

        return await response.json(); // 或者返回 response.text()
    } catch (error) {
        console.error('AJAX 请求失败:', error);
        throw error;
    }
}

