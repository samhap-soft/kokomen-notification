-- notification_payload 컬럼 JSON 타입으로 추가 (NOT NULL)
ALTER TABLE notification
    ADD COLUMN notification_payload JSON NOT NULL AFTER receiver_member_id;

-- notification_type 컬럼 enum 타입으로 추가 (NOT NULL)
ALTER TABLE notification
    ADD COLUMN notification_type ENUM('INTERVIEW_LIKE', 'ANSWER_LIKE', 'INTERVIEW_VIEW_COUNT') NOT NULL AFTER notification_payload;

-- content 컬럼 제거
ALTER TABLE notification
DROP COLUMN content;
