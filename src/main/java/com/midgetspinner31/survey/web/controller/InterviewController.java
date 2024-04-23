package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.InterviewService;
import com.midgetspinner31.survey.service.RatingService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.InterviewRequest;
import com.midgetspinner31.survey.web.request.InterviewSlotRequest;
import com.midgetspinner31.survey.web.request.RatingRequest;
import com.midgetspinner31.survey.web.response.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InterviewController {
    InterviewService interviewService;
    RatingService ratingService;

    /**
     * Создать интервью
     * (только для создателей опросов)
     */
    @PostMapping("/interviews")
    public InterviewResponse createInterview(@RequestBody InterviewRequest request) {
        return new InterviewResponse(interviewService.createInterview(request));
    }

    /**
     * Обновить интервью
     * (только для владельца интервью)
     */
    @PatchMapping("/interviews/{interviewId}")
    public InterviewResponse updateInterview(@PathVariable String interviewId,
                                             @RequestBody @Valid InterviewRequest request) {
        return new InterviewResponse(interviewService.updateInterview(interviewId, request));
    }

    /**
     * Удалить интервью
     * (только для владельца интервью)
     */
    @DeleteMapping("/interviews/{interviewId}")
    public EmptyResponse deleteInterview(@PathVariable String interviewId) {
        interviewService.deleteInterview(interviewId);
        return new EmptyResponse();
    }

    /**
     * Получить информацию о интервью
     */
    @GetMapping("/interviews/{interviewId}")
    public InterviewResponse getInterview(@PathVariable String interviewId) {
        return new InterviewResponse(interviewService.getInterview(interviewId));
    }

    /**
     * Получить список слотов интервью
     * (только для владельца интервью)
     */
    @GetMapping("/interviews/{interviewId}/slots")
    public InterviewSlotListResponse getInterviewSlots(@PathVariable String interviewId) {
        return new InterviewSlotListResponse(interviewService.getSlots(interviewId));
    }

    /**
     * Создать слоты интервью с указанным временем
     * (только для владельца интервью)
     */
    @PostMapping("/interviews/{interviewId}/slots")
    public InterviewSlotListResponse createInterviewSlots(@PathVariable String interviewId,
                                                          @RequestBody @Valid InterviewSlotRequest request) {
        return new InterviewSlotListResponse(interviewService.createSlots(interviewId, request));
    }

    /**
     * Удалить слот интервью
     * (только для владельца интервью)
     */
    @DeleteMapping("/interviews/{interviewId}/slots/{slotId}")
    public EmptyResponse deleteInterviewSlot(@PathVariable String interviewId,
                                             @PathVariable String slotId) {
        interviewService.deleteSlot(slotId);
        return new EmptyResponse();
    }

    /**
     * Получить оценку текущего пользователя по данному слоту интервью
     */
    @GetMapping("/interviews/{interviewId}/slots/{slotId}/rating")
    public RatingResponse getSlotRating(@PathVariable String interviewId,
                                        @PathVariable String slotId) {
        return new RatingResponse(ratingService.getRating(slotId));
    }

    /**
     * Оценить противоположного участника интервью (создатель -> респондент, респондент -> создатель)
     */
    @PostMapping("/interviews/{interviewId}/slots/{slotId}/rating")
    public RatingResponse rateSlot(@PathVariable String interviewId,
                                   @PathVariable String slotId,
                                   @RequestBody @Valid RatingRequest request) {
        return new RatingResponse(ratingService.rate(slotId, request));
    }

    /**
     * Получить список интервью с пагинацией
     *
     * @param offset страница
     * @param limit  количество элементов на странице
     * @param topics (опционально) темы интервью
     */
    @GetMapping("/interviews/page")
    public InterviewPageResponse getInterviewListPage(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "25") @Min(1) @Max(100) Integer limit,
            @RequestParam(name = "topic", required = false) List<String> topics) {
        return new InterviewPageResponse(interviewService.getInterviewPage(offset, limit, topics));
    }

    /**
     * Получить список интервью, принадлежащих текущему пользователю
     * (только для создателей опросов)
     */
    @GetMapping("/me/interviews")
    public InterviewListResponse getMyInterviews() {
        return new InterviewListResponse(interviewService.getInterviewsCreatedByCurrentUser());
    }

    /**
     * Получить список слотов, на которые записан текущий пользователь
     * (только для респондентов)
     */
    @GetMapping("/me/slots")
    public InterviewSlotListResponse getMySlots() {
        return new InterviewSlotListResponse(interviewService.getMySlots());
    }

    /**
     * Получить список свободных слотов интервью
     */
    @GetMapping("/interviews/{interviewId}/freeSlots")
    public InterviewSlotListResponse getFreeSlotsForInterview(@PathVariable String interviewId) {
        return new InterviewSlotListResponse(interviewService.getFreeSlotsForInterview(interviewId));
    }

    /**
     * Записаться на слот
     * (только для респондентов)
     */
    @PostMapping("/interviews/{interviewId}/slots/{slotId}/acquire")
    public InterviewSlotResponse acquireSlot(@PathVariable String interviewId, @PathVariable String slotId) {
        return new InterviewSlotResponse(interviewService.acquireSlot(slotId));
    }

    /**
     * Отменить запись на слот
     * (только для респондентов)
     */
    @PostMapping("/interviews/{interviewId}/slots/{slotId}/release")
    public InterviewSlotResponse releaseSlot(@PathVariable String interviewId, @PathVariable String slotId) {
        return new InterviewSlotResponse(interviewService.releaseSlot(slotId));
    }
}
