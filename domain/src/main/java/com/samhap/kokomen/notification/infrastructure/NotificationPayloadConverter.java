package com.samhap.kokomen.notification.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.samhap.kokomen.notification.domain.NotificationType;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;

@Converter
public class NotificationPayloadConverter implements AttributeConverter<NotificationPayload, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @Override
    public String convertToDatabaseColumn(NotificationPayload notificationPayload) {
        try {
            return objectMapper.writeValueAsString(notificationPayload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("notificationPayload를 JSON으로 변환하는데 실패하였습니다.", e);
        }
    }

    @Override
    public NotificationPayload convertToEntityAttribute(String notificationPayloadColumn) {
        try {
            JsonNode node = objectMapper.readTree(notificationPayloadColumn);
            NotificationType notificationType = NotificationType.valueOf(node.get("notification_type").asText());
            return notificationType.toNotificationPayload(objectMapper, node.toString());
        } catch (IOException e) {
            throw new IllegalStateException("DB의 notification_payload를 객체로 변환하는데 실패하였습니다.", e);
        }
    }
}
