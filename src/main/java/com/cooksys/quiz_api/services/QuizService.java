package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Question;

public interface QuizService {

  List<QuizResponseDto> getAllQuizzes();

  QuestionResponseDto getRandomQuestionById(Long id);

  QuizResponseDto createQuiz(QuizRequestDto quizRequestDto);

  QuizResponseDto updateQuizName(Long id, String name);
  
  QuizResponseDto addQuestion(Long id, Question question);

  QuizResponseDto deleteQuiz(Long id);

  QuestionResponseDto deleteQuestion(Long quizId, Long questionId);
}
