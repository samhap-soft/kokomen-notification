CREATE TABLE notification
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    receiver_member_id BIGINT        NOT NULL,
    content            VARCHAR(1000) NOT NULL,
    notification_state ENUM('READ', 'UNREAD') NOT NULL,
    created_at         TIMESTAMP     NOT NULL
);
