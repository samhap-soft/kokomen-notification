package com.samhap.kokomen.notification.infrastructure;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.samhap.kokomen.notification.domain.NotificationType;
import com.samhap.kokomen.notification.domain.payload.NotificationPayload;
import java.io.IOException;

public class NotificationPayloadDeserializer extends JsonDeserializer<NotificationPayload> {

    @Override
    public NotificationPayload deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        ObjectNode node = p.readValueAsTree();

        NotificationType notificationType = NotificationType.valueOf(node.get("notification_type").asText());
        return notificationType.toNotificationPayload(objectMapper, node.toString());
    }
}
