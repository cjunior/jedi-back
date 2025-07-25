package com.ifce.jedi.dto.LoadLandPage;

public class FaqItemUpdateLandPageDto {
    private Long id;
    private String question;
    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public FaqItemUpdateLandPageDto() {
    }

    public FaqItemUpdateLandPageDto(Long id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }
}
