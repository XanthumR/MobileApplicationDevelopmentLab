package msku.ceng.madlab.week9;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;



public class MainActivity extends AppCompatActivity {

    EditText txturl;
    ImageView imageView;
    Button button;
    private static int REQUEST_EXTERNAL_STORAGE=1;
    private static String[] PERMISSSON_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imgView);
        button = findViewById(R.id.btnDownload);
        txturl = findViewById(R.id.txtURL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permisssion = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permisssion != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSSON_STORAGE , REQUEST_EXTERNAL_STORAGE);
                }
//                String fileName = "temp.jpg";
//                String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName;
//                downloadFile(txturl.getText().toString(),imagePath);
//                preview(imagePath);
                else {
//                    DownloadTask backGroundTask = new DownloadTask();
//                    String[] urls = new String[1];
//                    urls[0] = txturl.getText().toString();
//                    backGroundTask.execute(urls);

                    Thread backgroundThread = new Thread(new DownloadRunnable(txturl.getText().toString()));
                    backgroundThread.start();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED&& grantResults[1]==PackageManager.PERMISSION_GRANTED){
//            String fileName = "temp.jpg";
//            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName;
//            downloadFile(txturl.getText().toString(),imagePath);
//            preview(imagePath);
//            DownloadTask backGroundTask = new DownloadTask();
//            String[] urls = new String[1];
//            urls[0] = txturl.getText().toString();
//            backGroundTask.execute(urls);
            Thread backgroundThread = new Thread(new DownloadRunnable(txturl.getText().toString()));
            backgroundThread.start();
        }

        else {
            Toast.makeText(this,"External storage permission is not granted",Toast.LENGTH_SHORT).show();
        }
    }

    private void preview(String imagePath) {
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        float imageWith = image.getWidth();
        float imageHeight = image.getHeight();
        int rescaledWith = 480;
        int rescaledHeight = (int) ((imageHeight* rescaledWith) / imageWith);
        Bitmap bitmap = Bitmap.createScaledBitmap(image,rescaledWith, rescaledHeight,false);
        imageView.setImageBitmap(bitmap);

    }

    private void downloadFile(String url,String imagePath){
        try {
            URL strurl = new URL(url);
            URLConnection connection = strurl.openConnection();
            connection.connect();

            InputStream inputStream = new BufferedInputStream(strurl.openStream(), 8192);
            OutputStream outputStream = new FileOutputStream(imagePath);

            byte data[] = new byte[1024];
            int count;
            while ((count = inputStream.read(data))!= -1){
                outputStream.write(data,0,count);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private Bitmap rescaleBitmap(String imagePath){
        Bitmap image = BitmapFactory.decodeFile(imagePath);
        float imageWith = image.getWidth();
        float imageHeight = image.getHeight();
        int rescaledWith = 480;
        int rescaledHeight = (int) ((imageHeight* rescaledWith) / imageWith);
        Bitmap bitmap = Bitmap.createScaledBitmap(image,rescaledWith, rescaledHeight,false);
        imageView.setImageBitmap(bitmap);
        return bitmap;
    }
    class DownloadTask extends AsyncTask<String,Integer,Bitmap>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMax(100);
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String fileName = "temp.jpg";
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName;
            downloadFile(urls[0],imagePath+"/"+fileName);

            return rescaleBitmap(imagePath+"/"+fileName);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    }

    class DownloadRunnable implements Runnable{
        String url;

        public DownloadRunnable(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            String fileName = "temp.jpg";
            String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName;
            downloadFile(url,imagePath+"/"+fileName);
            Bitmap bitmap = rescaleBitmap(imagePath+"/"+fileName);

            runOnUiThread(new UpdateBitmap(bitmap));
        }

        private class UpdateBitmap implements Runnable {
            Bitmap bitmap;
            public UpdateBitmap(Bitmap bitmap) {

                this.bitmap = bitmap;
            }

            @Override
            public void run() {

            }
        }
    }
}