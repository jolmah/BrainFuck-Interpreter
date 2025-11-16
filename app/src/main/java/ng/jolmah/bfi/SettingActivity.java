package ng.jolmah.bfi;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
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
import java.util.regex.*;
import ng.jolmah.bfi.databinding.*;
import org.json.*;

public class SettingActivity extends AppCompatActivity {
	
	private SettingBinding binding;
	private double fontSize = 0;
	
	private SharedPreferences data;
	private Intent i = new Intent();
	private com.google.android.material.bottomsheet.BottomSheetDialog bottom;
	private AlertDialog.Builder dia;
	private AlertDialog.Builder dialog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = SettingBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		data = getSharedPreferences("notes", Activity.MODE_PRIVATE);
		dia = new AlertDialog.Builder(this);
		dialog = new AlertDialog.Builder(this);
		
		binding.imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		binding.mFontSize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.setTitle("Enter fontsize");
				dialog.setCancelable(false);
				LinearLayout mylayout = new LinearLayout(SettingActivity.this);
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				
				mylayout.setLayoutParams(params); mylayout.setOrientation(LinearLayout.VERTICAL);
				
				final EditText myedittext = new EditText(SettingActivity.this);
				
				mylayout.addView(myedittext);
				dialog.setView(mylayout);
				myedittext.setText(data.getString("fontsize", ""));
				myedittext.setTextColor(0xFFA0A09C);
				mylayout.setPadding((int) 50,(int) 10,(int) 50,(int) 10);
				myedittext.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
				dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						if (myedittext.getText().toString().equals("")) {
							SketchwareUtil.showMessage(getApplicationContext(), "Input can't be empty");
						} else {
							fontSize = Integer.parseInt(myedittext.getText().toString());
							data.edit().putString("fontsize", String.valueOf((long)(fontSize))).commit();
							binding.textview4.setText(data.getString("fontsize", "").concat("px"));
							SketchwareUtil.showMessage(getApplicationContext(), "Saved");
						}
					}
				});
				dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dialog.create().show();
			}
		});
		
		binding.mShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://en.wikipedia.org/wiki/Brainfuck"));
				startActivity(i);
			}
		});
		
		binding.mHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				bottom = new com.google.android.material.bottomsheet.BottomSheetDialog(SettingActivity.this);
				View bottomV;
				bottomV = getLayoutInflater().inflate(R.layout.about,null );
				bottom.setContentView(bottomV);
				bottom.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
				final LinearLayout linear1 = (LinearLayout) bottomV.findViewById(R.id.linear1);
				linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
				bottom.show();
			}
		});
		
		binding.mContact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				bottom = new com.google.android.material.bottomsheet.BottomSheetDialog(SettingActivity.this);
				View bottomV;
				bottomV = getLayoutInflater().inflate(R.layout.contact,null );
				bottom.setContentView(bottomV);
				bottom.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
				final LinearLayout lin = (LinearLayout) bottomV.findViewById(R.id.lin);
				final LinearLayout mTel = (LinearLayout) bottomV.findViewById(R.id.mTel);
				final LinearLayout mGit = (LinearLayout) bottomV.findViewById(R.id.mGit);
				final ImageView mCal = (ImageView) bottomV.findViewById(R.id.mCal);
				lin.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
				mTel.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
				mGit.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)15, 0xFF151819));
				bottom.setCancelable(false);
				mTel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse("https://t.me/apklearner"));
						startActivity(i);
						SketchwareUtil.showMessage(getApplicationContext(), "Telegram");
						bottom.dismiss();
					}
				});
				mGit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse("https://github.com/jolmah/BrainFuck-Interpreter.git"));
						startActivity(i);
						SketchwareUtil.showMessage(getApplicationContext(), "GitHub");
						bottom.dismiss();
					}
				});
				mCal.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						bottom.dismiss();
					}
				});
				bottom.show();
			}
		});
	}
	
	private void initializeLogic() {
		_setDarkDialogTheme();
		binding.mFontSize.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF1E2122));
		binding.mShare.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF1E2122));
		binding.mHelp.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF1E2122));
		binding.mContact.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF1E2122));
		_clickAnimation(binding.mFontSize);
		_clickAnimation(binding.mShare);
		_clickAnimation(binding.mHelp);
		_clickAnimation(binding.mContact);
		if (data.getString("fontsize", "").equals("")) {
			
		} else {
			binding.textview4.setText(data.getString("fontsize", "").concat("px"));
		}
	}
	
	@Override
	public void onBackPressed() {
		i.setClass(getApplicationContext(), HomeActivity.class);
		startActivity(i);
		finish();
	}
	public void _clickAnimation(final View _view) {
		ScaleAnimation fade_in = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
		fade_in.setDuration(300);
		fade_in.setFillAfter(true);
		_view.startAnimation(fade_in);
		//aauraparti YouTube channel//
	}
	
	
	public void _setDarkDialogTheme() {
		dialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		//put this in OnCreate and replace "yourDialog" with your dialog component name.
	}
	
}