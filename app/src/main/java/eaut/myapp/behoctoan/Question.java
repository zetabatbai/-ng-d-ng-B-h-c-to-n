package eaut.myapp.behoctoan;

public class Question {
    private String question;
    private int correctAnswer;
    private int[] options;
    private String questionType;

    // Constructor
    public Question(String question, int correctAnswer, int[] options, String questionType) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = options != null ? options.clone() : new int[0]; // Sao chép mảng để đảm bảo tính toàn vẹn dữ liệu
        this.questionType = questionType;
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int[] getOptions() {
        return options != null ? options.clone() : new int[0]; // Trả về bản sao của mảng để tránh bị sửa đổi bên ngoài
    }

    public String getQuestionType() {
        return questionType;
    }

    // Optional: Setter methods (if you need to modify any of the attributes later)
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setOptions(int[] options) {
        this.options = options != null ? options.clone() : new int[0];
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
