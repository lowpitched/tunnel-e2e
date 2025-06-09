-- play_template 表
CREATE TABLE play_template (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- play_button 表
CREATE TABLE play_button (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    serial_no VARCHAR(255),
    text VARCHAR(255),
    key_code VARCHAR(255),
    x VARCHAR(255),
    y VARCHAR(255),
    button_size VARCHAR(255),
    custom_style VARCHAR(255),
    template_id BIGINT,
    FOREIGN KEY (template_id) REFERENCES play_template(id)
);