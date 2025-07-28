package com.samhap.kokomen.notification.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.samhap.kokomen.global.BaseControllerTest;
import com.samhap.kokomen.global.fixture.NotificationFixtureBuilder;
import com.samhap.kokomen.notification.domain.NotificationState;
import com.samhap.kokomen.notification.domain.NotificationType;
import com.samhap.kokomen.notification.domain.payload.AnswerLikePayload;
import com.samhap.kokomen.notification.domain.payload.InterviewLikePayload;
import com.samhap.kokomen.notification.domain.payload.InterviewViewCountPayload;
import com.samhap.kokomen.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

class NotificationApiControllerTest extends BaseControllerTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void 자신의_안_읽은_알림_조회() throws Exception {
        // given
        Long memberId = 1L;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("MEMBER_ID", memberId);
        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.READ)
                        .notificationPayload(new InterviewLikePayload(NotificationType.INTERVIEW_LIKE, 2L, 2L, 10L))
                        .build()
        );

        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.UNREAD)
                        .notificationPayload(new InterviewLikePayload(NotificationType.INTERVIEW_LIKE, 2L, 3L, 11L))
                        .build()
        );

        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.UNREAD)
                        .notificationPayload(new InterviewViewCountPayload(NotificationType.INTERVIEW_VIEW_COUNT, 2L, 100L))
                        .build()
        );

        String responseJson = """
                {
                    	"notifications": [
                    	                    		{
                    			"notification_payload": {
                    				"notification_type": "INTERVIEW_VIEW_COUNT",
                    				"interview_id": 2,
                    				"view_count": 100
                    			}
                    		},
                    		{
                    			"notification_payload": {
                    				"notification_type": "INTERVIEW_LIKE",
                    				"interview_id": 2,
                    				"liker_member_id": 3,
                    				"like_count": 11
                    			}
                    		}
                    	],
                    	"has_next": false
                }
                """;

        mockMvc.perform(get("/api/v1/notifications/me/unread")
                        .param("size", "10")
                        .param("sort", "id,desc")
                        .header("Cookie", "JSESSIONID=" + session.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.notifications[0].created_at").exists())
                .andExpect(jsonPath("$.notifications[1].created_at").exists())
                .andDo(document("notification-findMyUnreadNotifications",
                        requestHeaders(
                                headerWithName("Cookie").description("로그인 세션을 위한 JSESSIONID 쿠키")
                        ),
                        queryParameters(
                                parameterWithName("size").description("페이지 크기 (기본값: 10)"),
                                parameterWithName("sort").description("정렬 기준 (기본값: id,desc)")
                        ),
                        responseFields(
                                fieldWithPath("notifications").description("알림 목록"),
                                fieldWithPath("notifications[].notification_payload").description("알림 페이로드"),
                                fieldWithPath("notifications[].notification_payload.notification_type").description("알림 타입"),
                                fieldWithPath("notifications[].notification_payload.*").ignored(),
                                fieldWithPath("notifications[].created_at").description("알림 생성 시간"),
                                fieldWithPath("has_next").description("다음 페이지가 있는지 여부")
                        )
                ));
    }

    @Test
    void 자신의_읽은_알림_조회() throws Exception {
        // given
        Long memberId = 1L;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("MEMBER_ID", memberId);
        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.READ)
                        .notificationPayload(new InterviewLikePayload(NotificationType.INTERVIEW_LIKE, 2L, 2L, 10L))
                        .build()
        );

        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.READ)
                        .notificationPayload(new InterviewLikePayload(NotificationType.INTERVIEW_LIKE, 2L, 3L, 11L))
                        .build()
        );

        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.UNREAD)
                        .notificationPayload(new InterviewViewCountPayload(NotificationType.INTERVIEW_VIEW_COUNT, 2L, 100L))
                        .build()
        );

        String responseJson = """
                {
                    	"notifications": [
                    	    {
                    			"notification_payload": {
                    				"notification_type": "INTERVIEW_LIKE",
                    				"interview_id": 2,
                    				"liker_member_id": 3,
                    				"like_count": 11
                    			}
                    		},
                    		{
                    			"notification_payload": {
                    				"notification_type": "INTERVIEW_LIKE",
                    				"interview_id": 2,
                    				"liker_member_id": 2,
                    				"like_count": 10
                    			}
                    		}
                    	]
                }
                """;

        mockMvc.perform(get("/api/v1/notifications/me/read")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc")
                        .header("Cookie", "JSESSIONID=" + session.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.notifications[0].created_at").exists())
                .andExpect(jsonPath("$.notifications[1].created_at").exists())
                .andDo(document("notification-findMyReadNotifications",
                        requestHeaders(
                                headerWithName("Cookie").description("로그인 세션을 위한 JSESSIONID 쿠키")
                        ),
                        queryParameters(
                                parameterWithName("page").description("페이지 번호 (기본값: 0)"),
                                parameterWithName("size").description("페이지 크기 (기본값: 10)"),
                                parameterWithName("sort").description("정렬 기준 (기본값: id,desc)")
                        ),
                        responseFields(
                                fieldWithPath("notifications").description("알림 목록"),
                                fieldWithPath("notifications[].notification_payload").description("알림 페이로드"),
                                fieldWithPath("notifications[].notification_payload.notification_type").description("알림 타입"),
                                fieldWithPath("notifications[].notification_payload.*").ignored(),
                                fieldWithPath("notifications[].created_at").description("알림 생성 시간")
                        )
                ));
    }

    @Test
    void 자신의_읽은_알림_조회_중_인터뷰_좋아요_알림_조회() throws Exception {
        // given
        Long memberId = 1L;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("MEMBER_ID", memberId);
        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.READ)
                        .notificationPayload(new InterviewLikePayload(NotificationType.INTERVIEW_LIKE, 2L, 2L, 10L))
                        .build()
        );

        String responseJson = """
                {
                    	"notifications": [
                    		{
                    			"notification_payload": {
                    				"notification_type": "INTERVIEW_LIKE",
                    				"interview_id": 2,
                    				"liker_member_id": 2,
                    				"like_count": 10
                    			}
                    		}
                    	]
                }
                """;

        mockMvc.perform(get("/api/v1/notifications/me/read")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc")
                        .header("Cookie", "JSESSIONID=" + session.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.notifications[0].created_at").exists())
                .andDo(document("notification-findMyReadNotifications-interviewLike",
                        requestHeaders(
                                headerWithName("Cookie").description("로그인 세션을 위한 JSESSIONID 쿠키")
                        ),
                        queryParameters(
                                parameterWithName("page").description("페이지 번호 (기본값: 0)"),
                                parameterWithName("size").description("페이지 크기 (기본값: 10)"),
                                parameterWithName("sort").description("정렬 기준 (기본값: id,desc)")
                        ),
                        responseFields(
                                fieldWithPath("notifications").description("알림 목록"),
                                fieldWithPath("notifications[].notification_payload").description("알림 페이로드"),
                                fieldWithPath("notifications[].notification_payload.notification_type").description("알림 타입"),
                                fieldWithPath("notifications[].notification_payload.interview_id").description("인터뷰 ID"),
                                fieldWithPath("notifications[].notification_payload.liker_member_id").description("좋아요를 누른 회원 ID"),
                                fieldWithPath("notifications[].notification_payload.like_count").description("좋아요 수"),
                                fieldWithPath("notifications[].created_at").description("알림 생성 시간")
                        )
                ));
    }

    @Test
    void 자신의_읽은_알림_조회_중_답변_좋아요_알림_조회() throws Exception {
        // given
        Long memberId = 1L;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("MEMBER_ID", memberId);
        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.READ)
                        .notificationPayload(new AnswerLikePayload(NotificationType.ANSWER_LIKE, 3L, 2L, 4L, 10L))
                        .build()
        );

        String responseJson = """
                {
                    	"notifications": [
                    		{
                    			"notification_payload": {
                    				"notification_type": "ANSWER_LIKE",
                    				"answer_id": 3,
                    				"interview_id": 2,
                    				"liker_member_id": 4,
                    				"like_count": 10
                    			}
                    		}
                    	]
                }
                """;

        mockMvc.perform(get("/api/v1/notifications/me/read")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc")
                        .header("Cookie", "JSESSIONID=" + session.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.notifications[0].created_at").exists())
                .andDo(document("notification-findMyReadNotifications-answerLike",
                        requestHeaders(
                                headerWithName("Cookie").description("로그인 세션을 위한 JSESSIONID 쿠키")
                        ),
                        queryParameters(
                                parameterWithName("page").description("페이지 번호 (기본값: 0)"),
                                parameterWithName("size").description("페이지 크기 (기본값: 10)"),
                                parameterWithName("sort").description("정렬 기준 (기본값: id,desc)")
                        ),
                        responseFields(
                                fieldWithPath("notifications").description("알림 목록"),
                                fieldWithPath("notifications[].notification_payload").description("알림 페이로드"),
                                fieldWithPath("notifications[].notification_payload.notification_type").description("알림 타입"),
                                fieldWithPath("notifications[].notification_payload.answer_id").description("답변 ID"),
                                fieldWithPath("notifications[].notification_payload.interview_id").description("인터뷰 ID"),
                                fieldWithPath("notifications[].notification_payload.liker_member_id").description("좋아요를 누른 회원 ID"),
                                fieldWithPath("notifications[].notification_payload.like_count").description("좋아요 수"),
                                fieldWithPath("notifications[].created_at").description("알림 생성 시간")
                        )
                ));
    }

    @Test
    void 자신의_읽은_알림_조회_중_인터뷰_조회수_알림_조회() throws Exception {
        // given
        Long memberId = 1L;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("MEMBER_ID", memberId);
        notificationRepository.save(
                NotificationFixtureBuilder.builder()
                        .receiverMemberId(memberId)
                        .notificationState(NotificationState.READ)
                        .notificationPayload(new InterviewViewCountPayload(NotificationType.INTERVIEW_VIEW_COUNT, 2L, 100L))
                        .build()
        );

        String responseJson = """
                {
                    	"notifications": [
                    		{
                    			"notification_payload": {
                    				"notification_type": "INTERVIEW_VIEW_COUNT",
                    				"interview_id": 2,
                    				"view_count": 100
                    			}
                    		}
                    	]
                }
                """;

        mockMvc.perform(get("/api/v1/notifications/me/read")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc")
                        .header("Cookie", "JSESSIONID=" + session.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.notifications[0].created_at").exists())
                .andDo(document("notification-findMyReadNotifications-interviewViewCount",
                        requestHeaders(
                                headerWithName("Cookie").description("로그인 세션을 위한 JSESSIONID 쿠키")
                        ),
                        queryParameters(
                                parameterWithName("page").description("페이지 번호 (기본값: 0)"),
                                parameterWithName("size").description("페이지 크기 (기본값: 10)"),
                                parameterWithName("sort").description("정렬 기준 (기본값: id,desc)")
                        ),
                        responseFields(
                                fieldWithPath("notifications").description("알림 목록"),
                                fieldWithPath("notifications[].notification_payload").description("알림 페이로드"),
                                fieldWithPath("notifications[].notification_payload.notification_type").description("알림 타입"),
                                fieldWithPath("notifications[].notification_payload.interview_id").description("인터뷰 ID"),
                                fieldWithPath("notifications[].notification_payload.view_count").description("조회수"),
                                fieldWithPath("notifications[].created_at").description("알림 생성 시간")
                        )
                ));
    }
}
