class InputModal {
    constructor(options = {}) {
        this.options = {
            title: options.title || '请输入内容',
            placeholder: options.placeholder || '输入内容',
            confirmText: options.confirmText || '确认',
            cancelText: options.cancelText || '取消',
            onConfirm: options.onConfirm || (() => {}),
            onCancel: options.onCancel || (() => {})
        };

        this.modalElement = null;
        this.inputElement = null;
        this.init();
    }

    init() {
        // 创建 DOM 结构
        this.modalElement = document.createElement('div');
        this.modalElement.className = 'input-modal';
        this.modalElement.innerHTML = `            <div class="input-modal-overlay">
                <div class="input-modal-content">
                    <span class="input-modal-close">&times;</span>
                    <h3 class="input-modal-title">${this.options.title}</h3>
                    <input type="text" class="input-modal-input" placeholder="${this.options.placeholder}" />
                    <div class="input-modal-buttons">
                        <button class="input-modal-btn input-modal-confirm">${this.options.confirmText}</button>
                        <button class="input-modal-btn input-modal-cancel">${this.options.cancelText}</button>
                    </div>
                </div>
            </div>
        `;

        // 添加到 body 中
        document.body.appendChild(this.modalElement);

        // 绑定事件
        this.inputElement = this.modalElement.querySelector('.input-modal-input');
        this.modalElement.querySelector('.input-modal-close').addEventListener('click', () => this.close());
        this.modalElement.querySelector('.input-modal-cancel').addEventListener('click', () => this.close());
        this.modalElement.querySelector('.input-modal-confirm').addEventListener('click', () => this.confirm());

        // 点击遮罩关闭
        this.modalElement.querySelector('.input-modal-overlay').addEventListener('click', (e) => {
            if (e.target === e.currentTarget) {
                this.close();
            }
        });
    }

    open() {
        this.inputElement.value = '';
        this.modalElement.style.display = 'block';
    }

    close() {
        this.modalElement.style.display = 'none';
        this.options.onCancel();
    }

    confirm() {
        const value = this.inputElement.value.trim();
        if (value) {
            this.close();
            this.options.onConfirm(value);
        } else {
            alert('请输入内容');
        }
    }
}