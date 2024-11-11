package eaut.myapp.behoctoan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinalScoreActivity extends AppCompatActivity {

    private TextView finalScoreTextView;
    private Button playAgainButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_score);

        // Initialize views
        // Initialize views from the layout
        finalScoreTextView = findViewById(R.id.FinalScore);
        playAgainButton = findViewById(R.id.ButtonPlayAgain);
        backButton = findViewById(R.id.back);

        // Receive the score and total number of questions from the intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0);

        // Display the final score
        finalScoreTextView.setText("Score: " + score + "/" + totalQuestions);

        // Handle the "Play Again" button click event
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity to play again
                Intent playAgainIntent = new Intent(FinalScoreActivity.this, kiemtra.class);
                startActivity(playAgainIntent);
                finish(); // End the current activity to prevent the user from returning to it
            }
        });

        // Handle the "Back to Dashboard" button click event
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the dashboard activity
                Intent backIntent = new Intent(FinalScoreActivity.this, MainActivity2.class);
                startActivity(backIntent);
                finish(); // End the current activity to prevent the user from returning to it
            }
        });
    }
}
