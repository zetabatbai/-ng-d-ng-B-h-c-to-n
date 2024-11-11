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

public class SoSanhActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_so_sanh);

        tvQuestion = findViewById(R.id.tv_question);
        imgFeedback = findViewById(R.id.img_feedback);
        answerButtons = new Button[] {
                findViewById(R.id.btn_answer_1),
                findViewById(R.id.btn_answer_2),
                findViewById(R.id.btn_answer_3)
        };
        btnStop = findViewById(R.id.btn_stop);

        setNewQuestion();

        for (Button answerButton : answerButtons) {
            answerButton.setOnClickListener(v -> checkAnswer(((Button) v).getText().toString()));
        }

        btnStop.setOnClickListener(v -> showResults());
    }

    private void setNewQuestion() {
        // Tạo câu hỏi so sánh
        int number1 = new Random().nextInt(10);
        int number2 = new Random().nextInt(10);
        tvQuestion.setText(number1 + " ? " + number2);

        // Xác định câu trả lời đúng
        if (number1 > number2) {
            correctAnswer = ">";
        } else if (number1 < number2) {
            correctAnswer = "<";
        } else {
            correctAnswer = "=";
        }

        // Đặt đáp án vào các nút
        answerButtons[0].setText(">");
        answerButtons[1].setText("=");
        answerButtons[2].setText("<");

        // Ẩn hình ảnh phản hồi ban đầu
        imgFeedback.setVisibility(View.GONE);
    }

    private void checkAnswer(String selectedAnswer) {
        MediaPlayer mediaPlayer;

        if (selectedAnswer.equals(correctAnswer)) {
            imgFeedback.setImageResource(R.drawable.ic_check); // Đặt hình dấu tick
            score++;
            mediaPlayer = MediaPlayer.create(this, R.raw.correct_sound); // Âm thanh đúng
        } else {
            imgFeedback.setImageResource(R.drawable.ic_cross); // Đặt hình dấu X
            mediaPlayer = MediaPlayer.create(this, R.raw.wrong_sound); // Âm thanh sai
        }

        imgFeedback.setVisibility(View.VISIBLE);
        mediaPlayer.start(); // Phát âm thanh

        // Giải phóng MediaPlayer sau khi phát xong
        mediaPlayer.setOnCompletionListener(mp -> mp.release());

        questionsAttempted++;

        // Đặt câu hỏi mới sau một khoảng thời gian ngắn
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
