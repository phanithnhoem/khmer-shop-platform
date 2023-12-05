package biz.phanithnhoem.api.mail;

public class Mail<T> {
    private String sender;
    private String receiver;
    private String username;
    private String subject;
    private String template;
    private T metadata;
}
