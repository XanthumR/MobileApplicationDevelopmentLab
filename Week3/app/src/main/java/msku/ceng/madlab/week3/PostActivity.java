package msku.ceng.madlab.week3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PostActivity extends AppCompatActivity {

    ImageView img;
    TextView txtMessage;
    static final int CAPTURE_IMAGE= 0;
    ImageButton btnOk;
    ImageButton btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        EdgeToEdge.enable(this);
        img = findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURE_IMAGE);
            }
        });
        txtMessage =  findViewById(R.id.txtMessage);
        btnOk =findViewById(R.id.btnOk);
        btnCancel=findViewById(R.id.btnCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putParcelable("bitmap",((BitmapDrawable)img.getDrawable()).getBitmap());
                bundle.putCharSequence("msg",txtMessage.getText());
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAPTURE_IMAGE && resultCode== Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap image = (Bitmap) bundle.get("data");
            img.setImageBitmap(image);
        }
    }
}