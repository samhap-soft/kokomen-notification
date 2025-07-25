package com.samhap.kokomen.notification.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.samhap.kokomen.global.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class NotificationInternalControllerTest extends BaseControllerTest {

    @Test
    void 인터뷰_좋아요_알림_생성() throws Exception {
        // given
        String requestJson = """
                {
                  "receiver_member_id": 1,
                  "notification_payload": {
                    "notification_type": "INTERVIEW_LIKE",
                    "interview_id": 1,
                    "liker_member_id": 2,
                    "like_count": 5
                  }
                }
                """;

        // when & then
        mockMvc.perform(post("/internal/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent())
                .andDo(document(
                        "notification-saveNotification-likeInterview",
                        requestFields(fieldWithPath("receiver_member_id").description("알림 수신자 회원 ID"),
                                fieldWithPath("notification_payload").description("알림 페이로드"),
                                fieldWithPath("notification_payload.notification_type").description("알림 타입"),
                                fieldWithPath("notification_payload.interview_id").description("인터뷰 ID"),
                                fieldWithPath("notification_payload.liker_member_id").description("좋아요를 누른 회원 ID"),
                                fieldWithPath("notification_payload.like_count").description("좋아요 수"))
                ));
    }

    @Test
    void 인터뷰_답변_좋아요_알림_생성() throws Exception {
        // given
        String requestJson = """
                {
                  "receiver_member_id": 1,
                  "notification_payload": {
                    "notification_type": "ANSWER_LIKE",
                    "answer_id": 1,
                    "interview_id": 2,
                    "liker_member_id": 3,
                    "like_count": 5
                  }
                }
                """;

        // when & then
        mockMvc.perform(post("/internal/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent())
                .andDo(document(
                        "notification-saveNotification-likeAnswer",
                        requestFields(fieldWithPath("receiver_member_id").description("알림 수신자 회원 ID"),
                                fieldWithPath("notification_payload").description("알림 페이로드"),
                                fieldWithPath("notification_payload.notification_type").description("알림 타입"),
                                fieldWithPath("notification_payload.answer_id").description("답변 ID"),
                                fieldWithPath("notification_payload.interview_id").description("인터뷰 ID"),
                                fieldWithPath("notification_payload.liker_member_id").description("좋아요를 누른 회원 ID"),
                                fieldWithPath("notification_payload.like_count").description("좋아요 수"))
                ));
    }

    @Test
    void 인터뷰_조회수_알림_생성() throws Exception {
        // given
        String requestJson = """
                {
                  "receiver_member_id": 1,
                  "notification_payload": {
                    "notification_type": "INTERVIEW_VIEW_COUNT",
                    "interview_id": 1,
                    "view_count": 10
                  }
                }
                """;

        // when & then
        mockMvc.perform(post("/internal/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent())
                .andDo(document(
                        "notification-saveNotification-interviewViewCount",
                        requestFields(fieldWithPath("receiver_member_id").description("알림 수신자 회원 ID"),
                                fieldWithPath("notification_payload").description("알림 페이로드"),
                                fieldWithPath("notification_payload.notification_type").description("알림 타입"),
                                fieldWithPath("notification_payload.interview_id").description("인터뷰 ID"),
                                fieldWithPath("notification_payload.view_count").description("조회수"))
                ));
    }
}
