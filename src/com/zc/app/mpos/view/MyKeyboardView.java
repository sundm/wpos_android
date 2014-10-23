package com.zc.app.mpos.view;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;

public class MyKeyboardView extends KeyboardView{  
	  
    public MyKeyboardView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        // TODO Auto-generated constructor stub  
    }  
    public MyKeyboardView(Context context, AttributeSet attrs, int defStyle) {     
        super(context, attrs, defStyle);     
    }  
    @Override  
    protected boolean onLongPress(Key popupKey) {  
        // TODO Auto-generated method stub  
          
        if(popupKey.codes[0] == Keyboard.KEYCODE_DELETE){
        	Log.i("keyboardview", "onLongPress delete");
//            DefineKeyboard.mDefineKeyboardUtil.clearEditTextContent();  
            //可使用OnKeyboardActionListener中的各种方法实现该功能  
//          getOnKeyboardActionListener().onKey(Keyboard.KEYCODE_DELETE, null);    
              
        }  
          
        return super.onLongPress(popupKey);  
    }     
      
  
} 
