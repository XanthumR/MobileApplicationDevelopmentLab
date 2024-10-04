package msku.ceng.madlab.week2;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_adapter);
        final List<Animal> animals= new ArrayList<Animal>();
        animals.add(new Animal("Handsome Cat",R.mipmap.ic_launcher));
        animals.add(new Animal("Handsome Cat",R.mipmap.ic_launcher_round));
        animals.add(new Animal("Handsome Cat",R.mipmap.ic_launcher));
        animals.add(new Animal("Handsome Cat",R.mipmap.ic_launcher_round));
        animals.add(new Animal("Handsome Cat",R.mipmap.ic_launcher));
        animals.add(new Animal("Handsome Cat",R.mipmap.ic_launcher_round));

        final ListView listView = findViewById(R.id.listview);
        AnimalAdapter animalAdapter = new AnimalAdapter(this,animals);
        listView.setAdapter(animalAdapter);
    }
}