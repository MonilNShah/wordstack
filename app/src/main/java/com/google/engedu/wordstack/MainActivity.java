/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.wordstack;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static final int WORD_LENGTH = 5;
    public static final int LIGHT_BLUE = Color.rgb(176, 200, 255);
    public static final int LIGHT_GREEN = Color.rgb(200, 255, 200);
    private ArrayList<String> words = new ArrayList<>();
    private Random random = new Random();
    private StackedLayout stackedLayout;
    private String word1, word2;
    char tempword[]=new char[15];
    Stack  placedtiles = new Stack();
    String TAG;
    View word1LinearLayout,word2LinearLayout,word3LinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();
                if(word.length()==WORD_LENGTH)
                    words.add(word);
                /**
                 **
                 **  YOUR CODE GOES HERE
                 **
                 **/
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        LinearLayout verticalLayout = (LinearLayout) findViewById(R.id.vertical_layout);
        stackedLayout = new StackedLayout(this);
        assert verticalLayout != null;
        verticalLayout.addView(stackedLayout, 3);

        word1LinearLayout = findViewById(R.id.word1);
        word1LinearLayout.setOnTouchListener(new TouchListener());
        //word1LinearLayout.setOnDragListener(new DragListener());
        word2LinearLayout = findViewById(R.id.word2);
        word2LinearLayout.setOnTouchListener(new TouchListener());
        //word2LinearLayout.setOnDragListener(new DragListener());
    }

    private class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && !stackedLayout.empty()) {
                LetterTile tile = (LetterTile) stackedLayout.peek();
                tile.moveToViewGroup((ViewGroup) v);
                if (stackedLayout.empty()) {
                    TextView messageBox = (TextView) findViewById(R.id.message_box);
                    messageBox.setText(word1 + " " + word2);
                }
                placedtiles.push(tile);
                return true;
            }
            return false;

        }
    }

    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(LIGHT_GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign Tile to the target Layout
                    LetterTile tile = (LetterTile) event.getLocalState();
                    tile.moveToViewGroup((ViewGroup) v);
                    if (stackedLayout.empty()) {
                        TextView messageBox = (TextView) findViewById(R.id.message_box);
                        messageBox.setText(word1 + " " + word2);
                    }

                    /**
                     **
                     **  YOUR CODE GOES HERE
                     **
                     **/
                    return true;
            }
            return false;
        }
    }

    public boolean onStartGame(View view) {
        TextView messageBox = (TextView) findViewById(R.id.message_box);
        int index=random.nextInt(words.size());
        word1=words.get(index);
        index=random.nextInt(words.size());
        word2=words.get(index);
        int count1 = 0,count2=0;
        int i;
        messageBox.setText("Game started");
        //tempword=finalword.toCharArray();
        TAG="HELLO";
        for(i=0;i<word1.length()*2;i++) {
            index = random.nextInt(2);
            if (index == 0) {
                Log.i(TAG, "onStartGame: " + count1);
                char temp = word1.charAt(count1);
                TAG="CHARTAT J";
                Log.i(TAG, "onStartGame: " + temp);
                tempword[i] = temp;
                count1++;

            } else if (index == 1) {
                Log.i(TAG, "onStartGame: " + count2);
                char temp = word2.charAt(count2);
                TAG="CHARTAT J";
                Log.i(TAG, "onStartGame: " + temp);
                tempword[i] = temp;
                count2++;

            }
            if (count1 == 5 || count2 == 5) {
                Log.i(TAG, "onStartGame: " + i);
                break;
            }
        }
        i++;
        if(count1==5)
        {
            for(int j=count2;j<5;j++)
            {
                char temp = word2.charAt(j);
                TAG="CHARTAT J";
                Log.i(TAG, "onStartGame: " + temp);
                tempword[i]=temp;
                count2++;
                i++;
            }
        }
        if(count2==5)
        {
            for(int j=count1;j<5;j++)
            {
                char temp = word1.charAt(j);
                TAG="CHARTAT J";
                Log.i(TAG, "onStartGame: " + temp);
                tempword[i]=temp;
                count1++;
                i++;
            }
        }
        System.out.println(i);
        System.out.println(count1);
        System.out.println(count2);
        TAG="I";
        Log.i(TAG, "onStartGame: "+i);
        Log.i(TAG, "onStartGame: "+count1);
        Log.i(TAG, "onStartGame: "+count2);

        String finalword=new String(tempword);
        Log.i(TAG,"onStartGame:"+finalword);
        Log.i(TAG,"onStartGame:"+word1);
        Log.i(TAG,"onStartGame:"+word2);
        messageBox.setText(finalword);
        for(index=finalword.length()-1;index>=0;index--)
        {
                stackedLayout.push(new LetterTile(this,finalword.charAt(index)));
        }
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        return true;
    }

    public boolean onUndo(View view) {

  if(!placedtiles.isEmpty())
  {
        LetterTile poppedTile=(LetterTile)placedtiles.pop();
        poppedTile.moveToViewGroup(stackedLayout);
  }

        return true;
    }

//    @SuppressLint("ShowToast")
    /*public boolean onsubmit(View view)
    {
            if(word1.equals(word1LinearLayout.toString()) && word2.equals(word2LinearLayout.toString()))
            Toast.makeText(getApplicationContext(),"User Wins ",Toast.LENGTH_SHORT);
        else if(word2.equals(word1LinearLayout.toString()) && word1.equals(word2LinearLayout.toString())) {
            Toast.makeText(getApplicationContext(),"User Wins ",Toast.LENGTH_SHORT);
        }
        return true;
    }*/
}
