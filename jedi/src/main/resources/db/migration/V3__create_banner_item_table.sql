CREATE TABLE banner_item (
    id BIGINT PRIMARY KEY,
    button_text VARCHAR(255),
    button_url TEXT,
    banner_id BIGINT NOT NULL,
    CONSTRAINT fk_banner FOREIGN KEY (banner_id) REFERENCES banners(id) ON DELETE CASCADE,
    CONSTRAINT fk_base_item FOREIGN KEY (id) REFERENCES base_section_item(id) ON DELETE CASCADE
);
