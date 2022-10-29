package com.cooksys.quiz_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

 //==============GET MAPPING====================================================================================================================================
  
  @GetMapping
  public List<QuizResponseDto> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }
  
  @GetMapping("/{id}/random")
  public QuestionResponseDto getRandomQuestionById(@PathVariable Long id){
	  
	  return quizService.getRandomQuestionById(id);
  }

//=============POST MAPPING====================================================================================================================================
  
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public QuizResponseDto createQuiz(@RequestBody QuizRequestDto quizRequestDto){

	  return quizService.createQuiz(quizRequestDto);
  }
  
//=============PATCH MAPPING====================================================================================================================================
  
  @PatchMapping("/{id}/rename/{name}")
  public QuizResponseDto updateQuizName(@PathVariable Long id, @PathVariable String name){
	  
	  return quizService.updateQuizName(id, name);
  }
  
  @PatchMapping("/{id}/add")
  public QuizResponseDto addQuestion(@PathVariable Long id, @RequestBody Question question){
	  
	  return quizService.addQuestion(id, question);
  }
  
//=============DELETE MAPPING====================================================================================================================================
  
  @DeleteMapping("/{id}")
  public QuizResponseDto deleteQuiz(@PathVariable Long id){
	  
	  return quizService.deleteQuiz(id);
  }
  
  @DeleteMapping("/{id}/delete/{questionId}")
  public QuestionResponseDto deleteQuestion(@PathVariable Long quizId, @PathVariable Long questionId){
	  
	  return quizService.deleteQuestion(quizId, questionId);
  }
}
