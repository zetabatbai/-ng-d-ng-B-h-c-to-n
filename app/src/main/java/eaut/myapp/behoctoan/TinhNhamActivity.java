package eaut.myapp.behoctoan;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class TinhNhamActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private ImageView imgFeedback;
    private Button[] answerButtons;
    private Button btnStop;

    private String correctAnswer;
    private int score = 0;
    private int questionsAttempted = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinh_nham);

        tvQuestion = findViewById(R.id.tv_question);
        imgFeedback = findViewById(R.id.img_feedback);
        answerButtons = new Button[] {
                findViewById(R.id.btn_answer_1),
                findViewById(R.id.btn_answer_2),
                findViewById(R.id.btn_answer_3),
                findViewById(R.id.btn_answer_4)
        };
        btnStop = findViewById(R.id.btn_stop);

        setNewQuestion();

        for (Button answerButton : answerButtons) {
            answerButton.setOnClickListener(v -> checkAnswer(((Button) v).getText().toString()));
        }

        btnStop.setOnClickListener(v -> showResults());
    }

    private void setNewQuestion() {
        // Generate a simple question and set it to tvQuestion
        int number1 = new Random().nextInt(10);
        int number2 = new Random().nextInt(10);
        tvQuestion.setText(number1 + " + " + number2 + " = ?");

        // Calculate the correct answer
        correctAnswer = String.valueOf(number1 + number2);

        // Set the answers to the buttons, including the correct one
        int correctPosition = new Random().nextInt(4);
        for (int i = 0; i < answerButtons.length; i++) {
            if (i == correctPosition) {
                answerButtons[i].setText(correctAnswer);
            } else {
                answerButtons[i].setText(String.valueOf(new Random().nextInt(20)));
            }
        }

        // Hide feedback image initially
        imgFeedback.setVisibility(View.GONE);
    }

    private void checkAnswer(String selectedAnswer) {
        MediaPlayer mediaPlayer;

        if (selectedAnswer.equals(correctAnswer)) {
            imgFeedback.setImageResource(R.drawable.ic_check); // Set checkmark image
            score++;
            mediaPlayer = MediaPlayer.create(this, R.raw.correct_sound); // Âm thanh đúng
        } else {
            imgFeedback.setImageResource(R.drawable.ic_cross); // Set X image
            mediaPlayer = MediaPlayer.create(this, R.raw.wrong_sound); // Âm thanh sai
        }

        imgFeedback.setVisibility(View.VISIBLE);
        mediaPlayer.start(); // Phát âm thanh

        // Giải phóng MediaPlayer sau khi phát xong
        mediaPlayer.setOnCompletionListener(mp -> mp.release());

        questionsAttempted++;

        // Đặt câu hỏi mới sau một khoảng thời gian
        imgFeedback.postDelayed(this::setNewQuestion, 1000);
    }

    private void showResults() {
        String resultMessage = "Điểm: " + score + "/100" +
                "\nSố câu hỏi đã làm: " + questionsAttempted + "/100";
        new AlertDialog.Builder(this)
                .setTitle("Kết quả")
                .setMessage(resultMessage)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }

}
