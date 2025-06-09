document.addEventListener('DOMContentLoaded', function () {
    const selector = document.getElementById('templateSelector');

    fetch('/play-templates')
        .then(response => response.json())
        .then(data => {
            data.forEach(template => {
                const option = document.createElement('option');
                option.value = template.id;
                option.textContent = template.name;
                selector.appendChild(option);
            });
        })
        .catch(error => {
            console.error('加载模板失败:', error);
        });

        renderTemplates();
});

function renderTemplates() {
    document.getElementById('templateSelector').addEventListener('change', function () {
        const selectedId = this.value;
        if (selectedId) {
            fetch(`/play-templates/${selectedId}`)
                .then(response => response.json())
                .then(template => {
                    console.log('选中的模板:', template);
                    //清空上次的渲染
                    document.querySelectorAll('.draggable-button').forEach(btn => btn.remove());
                    // 在这里处理按钮渲染等逻辑
                    template.buttons.forEach(button => {
                        const btn = document.createElement('div');
                        btn.textContent = button.text;
                        btn.className = 'draggable-button';
                        btn.id = button.id;
                        btn.style.position = 'absolute'; // 必须为 absolute 才能定位
                        btn.style.left = button.x + 'px'; // 假设 button.x 是数字类型，如 100
                        btn.style.top = button.y + 'px';
                        btn.style.height  = button.buttonSize + 'px';
                        btn.style.width  = button.buttonSize + 'px';
                        btn.style.lineHeight = button.buttonSize + 'px';

                        document.body.appendChild(btn);
                        makeDraggable(btn);
                        addBtnClickEvent(btn);

                    });
                });
        }
    });
}

function addBtnClickEvent(btn) {
btn.addEventListener('click', function () {
    const keyCode = btn.textContent.trim(); // 获取按钮文字作为 keyCode
    if (!keyCode) return;

    // 发起请求 /play/key/{keyCode}
    get(`/play/key/${encodeURIComponent(keyCode)}`,null, {'Content-Type': 'application/json'});
});
}


