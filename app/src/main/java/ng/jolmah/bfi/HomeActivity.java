package ng.jolmah.bfi;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import ng.jolmah.bfi.databinding.*;
import org.json.*;

public class HomeActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private HomeBinding binding;
	private String Codes = "";
	private double prog = 0;
	private double fontSize = 0;
	TextViewUndoRedo urs;
	
	private SharedPreferences data;
	private Intent i = new Intent();
	private PopupWindow popup;
	private ProgressDialog pg;
	private TimerTask time;
	private Intent jajiqiqjajjajjqk;
	// ur;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = HomeBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		data = getSharedPreferences("notes", Activity.MODE_PRIVATE);
		
		binding.imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_navClick();
			}
		});
		
		binding.imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!binding.editor.getText().toString().equals("")) {
					prog = 0;
					pg = new ProgressDialog(HomeActivity.this);
					pg.setTitle("Compiling...");
					pg.setMax((int)100);
					pg.setCancelable(false);
					pg.setCanceledOnTouchOutside(false);
					pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					time = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									prog++;
									pg.setProgress((int)prog);
									if (prog == 100) {
										time.cancel();
										pg.dismiss();
										Codes = binding.editor.getText().toString().trim();
										data.edit().putString("code", Codes).commit();
										i.putExtra("output", _Interpreter(binding.editor.getText().toString().trim()));
										i.setClass(getApplicationContext(), OutputActivity.class);
										startActivity(i);
										finish();
									}
								}
							});
						}
					};
					_timer.scheduleAtFixedRate(time, (int)(0), (int)(10));
					pg.show();
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Nothing to compile >_<");
				}
			}
		});
		
		binding.mMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_menuClick();
			}
		});
		
		binding.editor.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				binding.editor.setTextIsSelectable(true);
				binding.editor.setCursorVisible(true);
				return true;
			}
		});
		
		//OnTouch
		binding.editor.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event){
				int ev = event.getAction();
				switch (ev) {
					case MotionEvent.ACTION_DOWN:
					
					binding.editor.setTextIsSelectable(true);
					binding.editor.setCursorVisible(true);
					SketchwareUtil.showMessage(getApplicationContext(), "Sorry, Keyboard disable");
					
					break;
					case MotionEvent.ACTION_UP:
					
					
					
					break;
				} return true;
			}
		});
		
		binding.imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				urs.undo();
			}
		});
		
		binding.imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				urs.redo();
			}
		});
		
		binding.t.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText(binding.editor.getText().toString().concat("["));
			}
		});
		
		binding.t2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText(binding.editor.getText().toString().concat("]"));
			}
		});
		
		binding.t4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText(binding.editor.getText().toString().concat("<"));
			}
		});
		
		binding.t5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText(binding.editor.getText().toString().concat(">"));
			}
		});
		
		binding.t6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText(binding.editor.getText().toString().concat("+"));
			}
		});
		
		binding.t7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText(binding.editor.getText().toString().concat("."));
			}
		});
		
		binding.t8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText(binding.editor.getText().toString().concat("-"));
			}
		});
	}
	
	private void initializeLogic() {
		urs = new TextViewUndoRedo(binding.editor);
		_Elevation(binding.mToolbar, 15);
		_Elevation(binding.linear5, 15);
		EditText editor = findViewById(R.id.editor);
		BrainfuckSyntaxHighlighter highlighter = new BrainfuckSyntaxHighlighter(editor);
		highlighter.attach();
		//editor.setHorizontallyScrolling(true);
		//editor.setScrollContainer(true);
		
		
		
		if (data.contains("fontsize")) {
			fontSize = Double.parseDouble(data.getString("fontsize", ""));
			binding.editor.setTextSize((float)fontSize);
		} else {
			
		}
		if (!data.getString("code", "").equals("")) {
			binding.editor.setText(data.getString("code", ""));
		} else {
			binding.editor.setText("");
		}
		if (getIntent().hasExtra("sampleCodes")) {
			binding.editor.setText(getIntent().getStringExtra("sampleCodes"));
		} else {
			binding.editor.setText(binding.editor.getText().toString());
		}
	}
	
	
	public class TextViewUndoRedo {
		
		private boolean mIsUndoOrRedo = false;
		private EditHistory mEditHistory;
		private EditTextChangeListener mChangeListener;
		private TextView mTextView;
		
		
		public TextViewUndoRedo(TextView textView) {
			mTextView = textView;
			mEditHistory = new EditHistory();
			mChangeListener = new EditTextChangeListener();
			mTextView.addTextChangedListener(mChangeListener);
		}
		
		public void disconnect() {
			mTextView.removeTextChangedListener(mChangeListener);
		}
		
		public void setMaxHistorySize(int maxHistorySize) {
			mEditHistory.setMaxHistorySize(maxHistorySize);
		}
		
		public void clearHistory() {
			mEditHistory.clear();
		}
		
		
		public boolean getCanUndo() {
			return (mEditHistory.mmPosition > 0);
		}
		
		public void undo() {
			EditItem edit = mEditHistory.getPrevious();
			if (edit == null) {
				return;
			}
			
			Editable text = mTextView.getEditableText();
			int start = edit.mmStart;
			int end = start + (edit.mmAfter != null ? edit.mmAfter.length() : 0);
			
			mIsUndoOrRedo = true;
			text.replace(start, end, edit.mmBefore);
			mIsUndoOrRedo = false;
			
			for (Object o : text.getSpans(0, text.length(), android.text.style.UnderlineSpan.class)) {
				text.removeSpan(o);
			}
			
			Selection.setSelection(text, edit.mmBefore == null ? start
			: (start + edit.mmBefore.length()));
		}
		
		public boolean getCanRedo() {
			return (mEditHistory.mmPosition < mEditHistory.mmHistory.size());
		}
		
		public void redo() {
			EditItem edit = mEditHistory.getNext();
			if (edit == null) {
				return;
			}
			
			Editable text = mTextView.getEditableText();
			int start = edit.mmStart;
			int end = start + (edit.mmBefore != null ? edit.mmBefore.length() : 0);
			
			mIsUndoOrRedo = true;
			text.replace(start, end, edit.mmAfter);
			mIsUndoOrRedo = false;
			
			for (Object o : text.getSpans(0, text.length(), android.text.style.UnderlineSpan.class)) {
				text.removeSpan(o);
			}
			
			Selection.setSelection(text, edit.mmAfter == null ? start
			: (start + edit.mmAfter.length()));
		}
		
		public void storePersistentState(android.content.SharedPreferences.Editor editor, String prefix) {
			
			editor.putString(prefix + ".hash",
			String.valueOf(mTextView.getText().toString().hashCode()));
			editor.putInt(prefix + ".maxSize", mEditHistory.mmMaxHistorySize);
			editor.putInt(prefix + ".position", mEditHistory.mmPosition);
			editor.putInt(prefix + ".size", mEditHistory.mmHistory.size());
			
			int i = 0;
			for (EditItem ei : mEditHistory.mmHistory) {
				String pre = prefix + "." + i;
				
				editor.putInt(pre + ".start", ei.mmStart);
				editor.putString(pre + ".before", ei.mmBefore.toString());
				editor.putString(pre + ".after", ei.mmAfter.toString());
				
				i++;
			}
		}
		
		public boolean restorePersistentState(SharedPreferences sp, String prefix)
		throws IllegalStateException {
			
			boolean ok = doRestorePersistentState(sp, prefix);
			if (!ok) {
				mEditHistory.clear();
			}
			
			return ok;
		}
		
		private boolean doRestorePersistentState(SharedPreferences sp, String prefix) {
			
			String hash = sp.getString(prefix + ".hash", null);
			if (hash == null) {
				return true;
			}
			
			if (Integer.valueOf(hash) != mTextView.getText().toString().hashCode()) {
				return false;
			}
			
			mEditHistory.clear();
			mEditHistory.mmMaxHistorySize = sp.getInt(prefix + ".maxSize", -1);
			
			int count = sp.getInt(prefix + ".size", -1);
			if (count == -1) {
				return false;
			}
			
			for (int i = 0; i < count; i++) {
				String pre = prefix + "." + i;
				
				int start = sp.getInt(pre + ".start", -1);
				String before = sp.getString(pre + ".before", null);
				String after = sp.getString(pre + ".after", null);
				
				if (start == -1 || before == null || after == null) {
					return false;
				}
				mEditHistory.add(new EditItem(start, before, after));
			}
			
			mEditHistory.mmPosition = sp.getInt(prefix + ".position", -1);
			if (mEditHistory.mmPosition == -1) {
				return false;
			}
			
			return true;
		}
		
		private final class EditHistory {
			
			private int mmPosition = 0;
			private int mmMaxHistorySize = -1;
			private final LinkedList<EditItem> mmHistory = new LinkedList<EditItem>();
			private void clear() {
				mmPosition = 0;
				mmHistory.clear();
			}
			
			private void add(EditItem item) {
				while (mmHistory.size() > mmPosition) {
					mmHistory.removeLast();
				}
				mmHistory.add(item);
				mmPosition++;
				
				if (mmMaxHistorySize >= 0) {
					trimHistory();
				}
			}
			
			private void setMaxHistorySize(int maxHistorySize) {
				mmMaxHistorySize = maxHistorySize;
				if (mmMaxHistorySize >= 0) {
					trimHistory();
				}
			}
			
			private void trimHistory() {
				while (mmHistory.size() > mmMaxHistorySize) {
					mmHistory.removeFirst();
					mmPosition--;
				}
				
				if (mmPosition < 0) {
					mmPosition = 0;
				}
			}
			
			private EditItem getPrevious() {
				if (mmPosition == 0) {
					return null;
				}
				mmPosition--;
				return mmHistory.get(mmPosition);
			}
			
			private EditItem getNext() {
				if (mmPosition >= mmHistory.size()) {
					return null;
				}
				
				EditItem item = mmHistory.get(mmPosition);
				mmPosition++;
				return item;
			}
		}
		
		private final class EditItem {
			private final int mmStart;
			private final CharSequence mmBefore;
			private final CharSequence mmAfter;
			
			public EditItem(int start, CharSequence before, CharSequence after) {
				mmStart = start;
				mmBefore = before;
				mmAfter = after;
			}
		}
		
		private final class EditTextChangeListener implements TextWatcher {
			
			private CharSequence mBeforeChange;
			private CharSequence mAfterChange;
			
			public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
				if (mIsUndoOrRedo) {
					return;
				}
				
				mBeforeChange = s.subSequence(start, start + count);
			}
			
			public void onTextChanged(CharSequence s, int start, int before,
			int count) {
				if (mIsUndoOrRedo) {
					return;
				}
				
				mAfterChange = s.subSequence(start, start + count);
				mEditHistory.add(new EditItem(start, mBeforeChange, mAfterChange));
			}
			
			public void afterTextChanged(Editable s) {
			}
		}
	}
	
	public void _Elevation(final View _view, final double _number) {
		
		_view.setElevation((int)_number);
	}
	
	
	public String _Interpreter(final String _s) {
		try {
			// Declare memory system locally
			int ptr = 0;
			int length = 65535;
			byte[] memory = new byte[length];
			StringBuilder _output = new StringBuilder();
			int c;
			
			for (int i = 0; i < _s.length(); i++) {
				char ch = _s.charAt(i);
				c = 0;
				
				// Only allow valid Brainfuck commands
				if ("><+-.,[]".indexOf(ch) == -1) {
					throw new Exception("Invalid character '" + ch + "' at position " + i);
				}
				
				switch (ch) {
					case '>':
					ptr = (ptr == length - 1) ? 0 : ptr + 1;
					break;
					
					case '<':
					ptr = (ptr == 0) ? length - 1 : ptr - 1;
					break;
					
					case '+':
					memory[ptr]++;
					break;
					
					case '-':
					memory[ptr]--;
					break;
					
					case '.':
					_output.append((char) (memory[ptr]));
					break;
					
					case ',':
					memory[ptr] = 0; // input ignored for now
					break;
					
					case '[':
					if (memory[ptr] == 0) {
						i++;
						while (c > 0 || _s.charAt(i) != ']') {
							if (_s.charAt(i) == '[') c++;
							else if (_s.charAt(i) == ']') c--;
							i++;
						}
					}
					break;
					
					case ']':
					if (memory[ptr] != 0) {
						i--;
						while (c > 0 || _s.charAt(i) != '[') {
							if (_s.charAt(i) == ']') c++;
							else if (_s.charAt(i) == '[') c--;
							i--;
						}
						i--;
					}
					break;
				}
			}
			
			return _output.toString();
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
			return "";
		}
		
	}
	
	
	public void _menuClick() {
		View popupV = getLayoutInflater().inflate(R.layout.menu, null);
		final PopupWindow popup = new PopupWindow(popupV, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		final LinearLayout mMenu = popupV.findViewById(R.id.mMenu);
		final LinearLayout mClear = popupV.findViewById(R.id.mClear);
		final LinearLayout mSave = popupV.findViewById(R.id.mSave);
		final LinearLayout mCopy = popupV.findViewById(R.id.mCopy);
		final LinearLayout mCut = popupV.findViewById(R.id.mCut);
		final LinearLayout mPaste = popupV.findViewById(R.id.mPaste);
		popup.setAnimationStyle(android.R.style.Animation_Dialog);
		popup.showAsDropDown(binding.mMore, 0,0);
		popup.setBackgroundDrawable(new BitmapDrawable());
		mMenu.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, 0xFF151819));
		mMenu.setElevation((int)10);
		mClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText("");
				SketchwareUtil.showMessage(getApplicationContext(), "Cleared!");
				popup.dismiss();
			}
		});
		mSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Codes = binding.editor.getText().toString();
				data.edit().putString("code", Codes).commit();
				SketchwareUtil.showMessage(getApplicationContext(), "Saved!");
				popup.dismiss();
			}
		});
		mPaste.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//EditText editor = findViewById(R.id.editor);
				
				ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				if (clipboard.hasPrimaryClip()) {
					ClipData clip = clipboard.getPrimaryClip();
					if (clip != null && clip.getItemCount() > 0) {
						String pasteData = clip.getItemAt(0).coerceToText(getApplicationContext()).toString();
						binding.editor.setText(pasteData); // replace _edittext with your EditText's name
					}
				}
				
				SketchwareUtil.showMessage(getApplicationContext(), "Paste");
				popup.dismiss();
			}
		});
		mCut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				binding.editor.setText("");
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", binding.editor.getText().toString()));
				SketchwareUtil.showMessage(getApplicationContext(), "Cut!");
				popup.dismiss();
			}
		});
		mCopy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", binding.editor.getText().toString()));
				SketchwareUtil.showMessage(getApplicationContext(), "Copy!");
				popup.dismiss();
			}
		});
	}
	
	
	public void _navClick() {
		View popupV = getLayoutInflater().inflate(R.layout.nav_bar, null);
		final PopupWindow popup = new PopupWindow(popupV, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		final ImageView mLogo = popupV.findViewById(R.id.mLogo);
		final LinearLayout mSamples = popupV.findViewById(R.id.mSamples);
		final LinearLayout mUpdate = popupV.findViewById(R.id.mUpdate);
		final LinearLayout mSetting = popupV.findViewById(R.id.mSetting);
		final LinearLayout mExit = popupV.findViewById(R.id.mExit);
		final LinearLayout nav_view = popupV.findViewById(R.id.nav_view);
		popup.setAnimationStyle(android.R.style.Animation_Dialog);
		popup.showAsDropDown(binding.imageview1, 0,0);
		popup.setBackgroundDrawable(new BitmapDrawable());
		nav_view.setElevation((int)15);
		nav_view.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFF1E2122));
		mLogo.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
		mSamples.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
		mUpdate.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
		mSetting.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
		mExit.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
		mLogo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Hello, Bro!");
			}
		});
		mSamples.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), SampleActivity.class);
				startActivity(i);
				finish();
			}
		});
		mUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "No Updates");
			}
		});
		mSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), SettingActivity.class);
				startActivity(i);
				finish();
			}
		});
		mExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Exit");
				finishAffinity();
			}
		});
	}
	
}