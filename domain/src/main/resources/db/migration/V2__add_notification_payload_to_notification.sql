-- notification_payload 컬럼 JSON 타입으로 추가 (NOT NULL)
ALTER TABLE notification
    ADD COLUMN notification_payload JSON NOT NULL AFTER receiver_member_id;

-- content 컬럼 제거
ALTER TABLE notification
DROP COLUMN content;
