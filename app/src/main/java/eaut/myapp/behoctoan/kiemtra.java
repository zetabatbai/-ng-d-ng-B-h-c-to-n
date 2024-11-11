package eaut.myapp.behoctoan;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class kiemtra extends AppCompatActivity {

    private TextView questionTextView, scoreTextView, alertTextView, timeTextView, totalQuestionsTextView;
    private Button button1, button2, button3, button4;
    private int score = 0, totalQuestions = 0;
    private List<Question> questions;
    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        // Initialize views
        questionTextView = findViewById(R.id.QuestionText);
        scoreTextView = findViewById(R.id.ScoreTextView);
        timeTextView = findViewById(R.id.TimeTextView);
        alertTextView = findViewById(R.id.AlertTextView);
        totalQuestionsTextView = findViewById(R.id.TotalQuestionsTextView);

        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button1);
        button3 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button3);

        // Generate questions
        generateQuestions();

        // Start timer
        startTimer();

        // Display the first question
        displayNextQuestion();
    }

    private void generateQuestions() {
        questions = new ArrayList<>();
        Random random = new Random();

        // Addition and subtraction within 1-10
        for (int i = 0; i < 10; i++) {
            int num1 = random.nextInt(10) + 1; // Numbers between 1 and 10
            int num2 = random.nextInt(10) + 1;

            boolean isAddition = random.nextBoolean();
            int correctAnswer = isAddition ? num1 + num2 : num1 - num2;
            String question = isAddition ? num1 + " + " + num2 : num1 + " - " + num2;

            int[] options = generateOptions(correctAnswer);
            questions.add(new Question(question, correctAnswer, options, "math"));
        }

        // Comparison questions
        for (int i = 0; i < 5; i++) {
            int num1 = random.nextInt(10) + 1;
            int num2 = random.nextInt(10) + 1;

            String question = num1 + " ? " + num2;
            int correctAnswer = (num1 > num2) ? 1 : (num1 == num2) ? 0 : -1;
            int[] options = {1, 0, -1};

            questions.add(new Question(question, correctAnswer, options, "compare"));
        }

        // Counting questions
        for (int i = 0; i < 5; i++) {
            int correctAnswer = random.nextInt(10) + 1;
            String question = "Có bao nhiêu hình?";

            int[] options = generateOptions(correctAnswer);
            questions.add(new Question(question, correctAnswer, options, "count"));
        }
    }

    private int[] generateOptions(int correctAnswer) {
        Random random = new Random();
        int[] options = new int[4];
        options[0] = correctAnswer;

        for (int i = 1; i < 4; i++) {
            int incorrectAnswer;
            do {
                incorrectAnswer = random.nextInt(19) - 9; // Adjust range for simple math (e.g., -9 to 9)
            } while (incorrectAnswer == correctAnswer || contains(options, incorrectAnswer));
            options[i] = incorrectAnswer;
        }

        shuffleArray(options);
        return options;
    }

    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) return true;
        }
        return false;
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void displayNextQuestion() {
        if (totalQuestions < questions.size()) {
            currentQuestion = questions.get(totalQuestions);

            questionTextView.setText(currentQuestion.getQuestion());

            // Ensure buttons are correctly set
            int[] options = currentQuestion.getOptions();
            if (options.length >= 4) {
                button1.setText(String.valueOf(options[0]));
                button2.setText(String.valueOf(options[1]));
                button3.setText(String.valueOf(options[2]));
                button4.setText(String.valueOf(options[3]));
            }

            totalQuestions++;
            totalQuestionsTextView.setText("Câu hỏi: " + totalQuestions + "/" + questions.size());
        } else {
            Intent intent = new Intent(kiemtra.this, FinalScoreActivity.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL_QUESTIONS", questions.size());
            startActivity(intent);
            finish();
        }
    }

    private void startTimer() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeTextView.setText(millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                Intent intent = new Intent(kiemtra.this, FinalScoreActivity.class);
                intent.putExtra("SCORE", score);
                intent.putExtra("TOTAL_QUESTIONS", questions.size());
                startActivity(intent);
                finish();
            }
        }.start();
    }

    public void optionSelect(View view) {
        Button clickedButton = (Button) view;

        int selectedAnswer;
        try {
            selectedAnswer = Integer.parseInt(clickedButton.getText().toString());
        } catch (NumberFormatException e) {
            selectedAnswer = -1; // Handle comparison answers
        }

        if (selectedAnswer == currentQuestion.getCorrectAnswer()) {
            score++;
            alertTextView.setText("Đúng rồi!");
        } else {
            alertTextView.setText("Sai rồi!");
        }

        scoreTextView.setText(score + "/" + totalQuestions);

        new Handler().postDelayed(this::displayNextQuestion, 1000);
    }
}
