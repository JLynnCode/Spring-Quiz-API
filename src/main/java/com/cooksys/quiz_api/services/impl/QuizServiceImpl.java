package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;
	private final QuizMapper quizMapper;
	private final QuestionMapper questionMapper;
	private final QuestionRepository questionRepository;

//==============GET MAPPING====================================================================================================================================
	
	private Quiz getQuiz(Long id) {
		
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		return optionalQuiz.get();
	}
	
	@Override
	public List<QuizResponseDto> getAllQuizzes() {
		
		return quizMapper.entitiesToDtos(quizRepository.findAll());
	}

	@Override
	public QuestionResponseDto getRandomQuestionById(Long id) {
		
		List<Question> questions = getQuiz(id).getQuestions();
		Random r = new Random();
		
		Question question = questions.get(r.nextInt(questions.size()));
		
		return questionMapper.entityToDto(question);
	}
	
//=============POST MAPPING====================================================================================================================================
	
	@Override
	public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
		
		Quiz requestedQuiz = quizMapper.requestDtoToEntity(quizRequestDto);
				
		for (Question q : requestedQuiz.getQuestions()) {
			
			q.setQuiz(requestedQuiz);
			
			for (Answer answer : q.getAnswers()) {
				
				answer.setQuestion(q);
			}
		}
		
		Quiz quizToSave = quizRepository.saveAndFlush(requestedQuiz);
		
		return quizMapper.entityToDto(quizToSave);
	}
	
//=============PATCH MAPPING====================================================================================================================================
	
	@Override
	public QuizResponseDto updateQuizName(Long id, String name){
		
		Quiz quizToUpdate = getQuiz(id);
		quizToUpdate.setName(name);
		
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToUpdate));
	}
	
	@Override
	public QuizResponseDto addQuestion(Long id, Question question){
		
		Quiz quizToUpdate = getQuiz(id);
		
		List<Question> questions = quizToUpdate.getQuestions();
		
		questions.add(question);
		
		for(Question q : questions){
			
			q.setQuiz(quizToUpdate);
			
			for(Answer a : q.getAnswers()){
				
				a.setQuestion(q);
			}
		}
		
		quizToUpdate.setQuestions(questions);
		
		return quizMapper.entityToDto(quizRepository.saveAndFlush(quizToUpdate));
	}
	
//=============DELETE MAPPING====================================================================================================================================
	
	public QuizResponseDto deleteQuiz(Long id){
		
		Quiz quizToDelete = getQuiz(id);
		
		quizRepository.delete(getQuiz(id));
		
		return quizMapper.entityToDto(quizToDelete);
	}
	
	public QuestionResponseDto deleteQuestion(Long quizId, Long questionId){
		
		Quiz quizToUpdate = getQuiz(quizId);
		
		List<Question> questions = quizToUpdate.getQuestions();
		
		Question questionToReturn = null;
		
		for(Question q : questions){
			
			if(q.getId() == questionId){
				questions.remove(q);
				questionToReturn = q;
			}
		}
		
		quizToUpdate.setQuestions(questions);
		
		return questionMapper.entityToDto(questionRepository.saveAndFlush(questionToReturn));
	}
}
