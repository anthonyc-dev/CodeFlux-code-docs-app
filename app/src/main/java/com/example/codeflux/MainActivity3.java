package com.example.codeflux;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity3 extends AppCompatActivity {
    RecyclerView rcv;
    MyAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setTitle("Available Languages");

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.darkyellow)));

        rcv = findViewById(R.id.recview);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(dataqueue(), getApplicationContext());
        rcv.setAdapter(adapter);
    }

    public ArrayList<MyModel> dataqueue() {
        ArrayList<MyModel> holder = new ArrayList<>();

        MyModel ob1 = new MyModel();
        ob1.setHeader("C Programming");
        ob1.setDesc("Desktop Programming");
        ob1.setImgname(R.drawable.cone);
        ob1.setPdfUrl("https://cse.iitkgp.ac.in/~pallab/PDS-2011-SPRING/Lec-1c.pdf");
        holder.add(ob1);

        MyModel ob2 = new MyModel();
        ob2.setHeader("C++ Programming");
        ob2.setDesc("Desktop Programming Language");
        ob2.setImgname(R.drawable.cplusone);
        ob2.setPdfUrl("https://cplusplus.com/files/tutorial.pdf");
        holder.add(ob2);

        MyModel ob3 = new MyModel();
        ob3.setHeader("Java Programming");
        ob3.setDesc("Desktop Programming Language");
        ob3.setImgname(R.drawable.javaone);
        ob3.setPdfUrl("https://www.tutorialspoint.com/java/java_tutorial.pdf");
        holder.add(ob3);

        MyModel ob4 = new MyModel();
        ob4.setHeader("PHP Programming");
        ob4.setDesc("Web Programming Language");
        ob4.setImgname(R.drawable.phpone);
        ob4.setPdfUrl("https://assets.ctfassets.net/nkydfjx48olf/5qFMF3mvitLMahX67i7iOb/028229996c13cbc27a0538f055a41b46/php_cookbook.pdf");
        holder.add(ob4);

        MyModel ob5 = new MyModel();
        ob5.setHeader("Javascript Programming");
        ob5.setDesc("Web Programming Language");
        ob5.setImgname(R.drawable.jsone);
        ob5.setPdfUrl("https://exploringjs.com/impatient-js/downloads/impatient-js-preview-book.pdf");
        holder.add(ob5);

        MyModel ob6 = new MyModel();
        ob6.setHeader(".NET Programming");
        ob6.setDesc("Desktop/Web Programming Language");
        ob6.setImgname(R.drawable.netone);
        ob6.setPdfUrl("https://www.ssw.jku.at/Teaching/Lectures/CSharp/Tutorial/Part1.pdf");
        holder.add(ob6);

        MyModel ob7 = new MyModel();
        ob7.setHeader("C# Programming");
        ob7.setDesc("Desktop/Web Programming Language");
        ob7.setImgname(R.drawable.csharpone);
        ob7.setPdfUrl("https://ptgmedia.pearsoncmg.com/images/9781509301157/samplepages/9781509301157.pdf");
        holder.add(ob7);

//        MyModel ob8 = new MyModel();
//        ob8.setHeader("Typescript Programming");
//        ob8.setDesc("Web Programming");
//        ob8.setImgname(R.drawable.tsone);
//        ob8.setPdfUrl("https://docslib.org/doc/391585/typescript-handbook-pdf");
//        holder.add(ob8);

        MyModel ob9 = new MyModel();
        ob9.setHeader("Kotlin Programming");
        ob9.setDesc("Mobile Programming");
        ob9.setImgname(R.drawable.kotlinone);
        ob9.setPdfUrl("https://kotlinlang.org/docs/kotlin-reference.pdf");
        holder.add(ob9);

        MyModel ob10 = new MyModel();
        ob10.setHeader("Python Programming");
        ob10.setDesc("Desktop/Web based Programming");
        ob10.setImgname(R.drawable.pythonone);
        ob10.setPdfUrl("https://www.davekuhlman.org/python_book_01.pdf");
        holder.add(ob10);

        MyModel ob11 = new MyModel();
        ob11.setHeader("Node JS Programming");
        ob11.setDesc("Web based Programming");
        ob11.setImgname(R.drawable.nodejsone);
        ob11.setPdfUrl("https://www.anuragkapur.com/assets/blog/programming/node/PDF-Guide-Node-Andrew-Mead-v3.pdf");
        holder.add(ob11);

        return holder;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        MenuItem item = menu.findItem(R.id.search_menu);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
