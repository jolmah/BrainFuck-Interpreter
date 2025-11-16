package ng.jolmah.bfi;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
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
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.appbar.AppBarLayout;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import ng.jolmah.bfi.databinding.*;
import org.json.*;

public class SampleActivity extends AppCompatActivity {
	
	private SampleBinding binding;
	private String codes = "";
	
	private Intent i = new Intent();
	private SharedPreferences data;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = SampleBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		setSupportActionBar(binding.Toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		binding.Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		data = getSharedPreferences("notes", Activity.MODE_PRIVATE);
		
		binding.mSampleA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				codes = ">++++++++[<+++++++++>-]<.";
				data.edit().putString("sampleCodes", codes).commit();
				i.putExtra("sampleCodes", data.getString("sampleCodes", ""));
				i.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(i);
				SketchwareUtil.showMessage(getApplicationContext(), "Extracted");
			}
		});
		
		binding.mSample1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				codes = "+++++++++++++++++++++++++++++++++++++++++++++++++.";
				data.edit().putString("sampleCodes", codes).commit();
				i.putExtra("sampleCodes", data.getString("sampleCodes", ""));
				i.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(i);
				SketchwareUtil.showMessage(getApplicationContext(), "Extracted");
			}
		});
		
		binding.mSampleHello.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				codes = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
				data.edit().putString("sampleCodes", codes).commit();
				i.putExtra("sampleCodes", data.getString("sampleCodes", ""));
				i.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(i);
				SketchwareUtil.showMessage(getApplicationContext(), "Extracted");
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("Samples");
	}
	
	@Override
	public void onBackPressed() {
		i.setClass(getApplicationContext(), HomeActivity.class);
		startActivity(i);
		finish();
	}
}