package com.ifce.jedi.model.SecoesSite.FaqSection;

import jakarta.persistence.*;

@Entity
@Table(name = "faq_items")
public class FaqItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "faq_section_id")
    private FaqSection faqSection;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
    public FaqSection getFaqSection() { return faqSection; }
    public void setFaqSection(FaqSection faqSection) { this.faqSection = faqSection; }
}