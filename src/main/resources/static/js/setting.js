function saveButtonPosition() {
    const templateId = document.getElementById('templateSelector').value;
    saveButtonPositions(templateId);
}

async function saveButtonPositions(templateId) {
    const positions = getDraggableButtonPositions();

    try {
        const response = await fetch('/play-templates/'+templateId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                buttons: positions
            })
        });

        if (response.ok) {
            console.log('按钮坐标已成功保存');
        } else {
            console.error('保存失败');
        }
    } catch (error) {
        console.error('请求出错:', error);
    }
}

function getDraggableButtonPositions() {
    const buttons = document.querySelectorAll('div.draggable-button');
    const positions = [];

    buttons.forEach(button => {
        const rect = button.getBoundingClientRect();
        positions.push({
            serialNo: button.id || null, // 可选：如果按钮有唯一标识
            x: rect.left + window.scrollX,
            y: rect.top + window.scrollY,
            buttonSize: rect.width,
            text: button.textContent
        });
    });

    return positions;
}

function addNewButton(btn) {
    const selector = document.getElementById('templateSelector');
    const selectedOption = selector.options[selector.selectedIndex];

    if (!selectedOption.value) {
        alert('请先选择一个模板');
        return;
    }

    const button = document.createElement('div');
    button.className = 'draggable-button';
    button.textContent = '•';
    button.id = generateUUID();
    // 设置初始位置在加号按钮附近
    const centerX = (window.innerWidth) / 2;
    const centerY = (window.innerHeight) / 2;

    button.style.left = `${centerX}px`;
    button.style.top = `${centerY}px`;

    document.body.appendChild(button);

    makeDraggable(button);
}

function makeDraggable(element) {
    let offsetX = 0, offsetY = 0;
    let isDragging = false;

    function onPointerDown(e) {
        isDragging = true;
        offsetX = e.clientX - element.offsetLeft;
        offsetY = e.clientY - element.offsetTop;
        element.style.cursor = 'grabbing';
        e.preventDefault();
    }

    function onPointerMove(e) {
        if (isDragging) {
            element.style.left = (e.clientX - offsetX) + 'px';
            element.style.top = (e.clientY - offsetY) + 'px';
        }
    }

    function onPointerUp() {
        isDragging = false;
        element.style.cursor = 'grab';
    }

    element.addEventListener('pointerdown', onPointerDown);
    document.addEventListener('pointermove', onPointerMove);
    document.addEventListener('pointerup', onPointerUp);
}


let isGridVisible = false;

function toggleGrid() {
    const grid = document.getElementById('gridOverlay');
    if (!grid) return;

    isGridVisible = !isGridVisible;
    grid.style.display = isGridVisible ? 'block' : 'none';
}

function showAddTemplateModal() {
    const modal = new InputModal({
        title: '请输入模板名称',
        placeholder: '例如：原神操作面板',
        confirmText: '保存',
        cancelText: '取消',
        onConfirm: function(inputValue) {
            if (!inputValue.trim()) {
                alert('模板名称不能为空');
                return;
            }
            saveTemplate(inputValue);
        },
        onCancel: function() {
            console.log('用户取消了输入');
        }
    });

    modal.open();
}

function saveTemplate(templateName) {
    try {
            const response = fetch('/play-templates', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: templateName
                })
            });
            loadTemplates(response.id)
        } catch (error) {
            console.error('请求出错:', error);
        }
}

function loadTemplates(templateId) {
    const selector = document.getElementById('templateSelector');
    selector.innerHTML = '<option value="">-- 请选择模板 --</option>'; // 清空旧内容

    fetch('/play-templates')
        .then(response => response.json())
        .then(data => {
            data.forEach(template => {
                const option = document.createElement('option');
                option.value = template.id;
                option.textContent = template.name;
                selector.appendChild(option);
            });

            // 如果传入了 templateId，则设置为选中状态
            if (templateId) {
                selector.value = templateId;
            }
        })
        .catch(error => {
            console.error('加载模板失败:', error);
        });
}


function deleteSelectedTemplate() {
    const selector = document.getElementById('templateSelector');
    const selectedOption = selector.options[selector.selectedIndex];

    if (!selectedOption.value) {
        alert('请先选择一个模板');
        return;
    }

    if (!confirm('确定要删除该模板吗？')) {
        return;
    }

    const templateId = selectedOption.value;

    fetch(`/play-templates/${templateId}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            console.log('模板删除成功');
            loadTemplates(); // 刷新下拉框
        } else {
            console.error('删除失败');
            alert('删除模板失败');
        }
    })
    .catch(error => {
        console.error('请求出错:', error);
        alert('网络错误，请重试');
    });
}

function showAddButtonModal() {
    const selector = document.getElementById('templateSelector');
    const selectedOption = selector.options[selector.selectedIndex];

    if (!selectedOption.value) {
        alert('请先选择一个模板');
        return;
    }

    const modalContent = `
        <label>请输入按钮关键字</label>
        <input type="text" id="buttonKeyword" placeholder="例如：跳跃、攻击" />

        <div style="margin-top: 10px;">
            <label><input type="radio" name="buttonSize" value="large" checked /> 大</label>
            <label><input type="radio" name="buttonSize" value="small" /> 小</label>
        </div>
    `;

    const modal = new CustomModal({
        title: '新增按钮',
        content: modalContent,
        confirmText: '确定',
        cancelText: '取消',
        onConfirm: function () {
            const keyword = document.getElementById('buttonKeyword').value.trim();
            const size = document.querySelector('input[name="buttonSize"]:checked').value;

            if (!keyword) {
                alert('关键字不能为空');
                return;
            }

            addKeywordButton(keyword, size);
        },
        onCancel: function () {
            console.log('用户取消了输入');
        }
    });

    modal.open();
}



function addKeywordButton(keyword, size = 'small') {
    const button = document.createElement('div');
    button.className = `draggable-button ${size === 'large' ? 'large-draggable' : ''}`;
    button.textContent = keyword; // 显示用户输入的关键字
    button.id = generateUUID(); // 确保你已有 generateUUID() 函数

    // 设置初始位置为屏幕中央
    const centerX = (window.innerWidth - (size === 'large' ? 120 : 80)) / 2;
    const centerY = (window.innerHeight - (size === 'large' ? 60 : 40)) / 2;

    button.style.left = `${centerX}px`;
    button.style.top = `${centerY}px`;

    document.body.appendChild(button);
    makeDraggable(button); // 确保支持拖拽
}



class CustomModal {
    constructor(options) {
        this.options = options;
        this.modal = null;
    }

    open() {
        const modal = document.createElement('div');
        modal.className = 'modal';

        const content = `
            <div class="modal-overlay">
                <div class="modal-box">
                    <div class="modal-header">
                        <h3>${this.options.title}</h3>
                    </div>
                    <div class="modal-body">
                        ${this.options.content || ''}
                    </div>
                    <div class="modal-footer">
                        <button class="btn confirm-btn">${this.options.confirmText}</button>
                        <button class="btn cancel-btn">${this.options.cancelText}</button>
                    </div>
                </div>
            </div>
        `;

        modal.innerHTML = content;
        document.body.appendChild(modal);
        this.modal = modal;

        // 绑定事件
        modal.querySelector('.confirm-btn').onclick = () => {
            this.options.onConfirm();
            this.close();
        };

        modal.querySelector('.cancel-btn').onclick = () => {
            if (this.options.onCancel) this.options.onCancel();
            this.close();
        };
    }

    close() {
        if (this.modal && this.modal.parentNode) {
            this.modal.parentNode.removeChild(this.modal);
        }
    }
}






