const ws = new WebSocket("ws://localhost:8080/ws/stream");

// 设置接收二进制数据类型为 blob
ws.binaryType = 'blob';
let currentImageUrl = null;
// 接收到消息时处理
ws.onmessage = function(event) {
    const blob = event.data;
    if (currentImageUrl) {
        URL.revokeObjectURL(currentImageUrl); // 释放旧资源
    }
    // 创建临时 URL 用于展示图片
    const imageUrl = URL.createObjectURL(blob);

    // 获取目标元素（例如 .stream-container）
    const container = document.querySelector('.stream-container');

    if (container) {
        // 设置背景图
        container.style.backgroundImage = `url(${imageUrl})`;
        container.style.backgroundSize = 'cover'; // 自适应容器大小
        container.style.backgroundPosition = 'center'; // 居中显示
    }
};

// WebSocket 错误处理
ws.onerror = function(error) {
    console.error('WebSocket Error:', error);
};

// WebSocket 关闭处理
ws.onclose = function() {
    console.log('WebSocket connection closed');
};
