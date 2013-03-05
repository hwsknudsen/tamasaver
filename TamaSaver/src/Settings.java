

import com.google.analytics.tracking.android.EasyTracker;
import com.hwsknudsen.tamasaver.R;
import com.hwsknudsen.tamasaver.R.id;
import com.hwsknudsen.tamasaver.R.layout;
import com.hwsknudsen.tamasaver.R.menu;
import com.hwsknudsen.tamasaver.R.string;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Settings extends Activity implements ActionBar.TabListener  {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
	 * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
	 * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this); // Add this method.
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding tab.
		// We can also use ActionBar.Tab#select() to do this if we have a reference to the
		// Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by the adapter.
			// Also specify this Activity object, which implements the TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(
					actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
	 * sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		//private String Settings;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = null;
			if(i==0){
				fragment = new Welcome();
			}
			else if (i==1){
				fragment = new Setup();
			}
			else if(i==2){
				fragment = new Complete();
				//	            fragment = new DummySectionFragment();
				//	            Bundle args = new Bundle();
				//	            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i+1);
				//	            //Log.e("op", Integer.toString(i));
				//	            fragment.setArguments(args);
			}
			else if(i==3){
				fragment = new Complete2();
				//	            fragment = new DummySectionFragment();
				//	            Bundle args = new Bundle();
				//	            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i+1);
				//	            //Log.e("op", Integer.toString(i));
				//	            fragment.setArguments(args);
			}

			return fragment;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0: return getString(R.string.title_section1).toUpperCase();
			case 1: return getString(R.string.title_section2).toUpperCase();
			case 2: return getString(R.string.title_section3).toUpperCase();
			case 3: return getString(R.string.title_section4).toUpperCase();

			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		public DummySectionFragment() {
		}

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			Bundle args = getArguments();
			textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));


			return textView;
		}
	}

	public static class Welcome extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragmentwelcome, container, false);
		}
	}



	public static class Setup extends PreferenceFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Load the preferences from an XML resource
			addPreferencesFromResource(R.layout.prefrence);
		}
		

	}


	public static class Complete extends Fragment {

		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.explanation, container, false);
		}
	}

	public static class Complete2 extends Fragment {

		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.setupcomplete, container, false);
		}
	}



	public void bCGame(View v){
		Game.myGame(this);

		EasyTracker.getTracker().sendEvent("ui_action", "button_press", "game", null);

	}
	
	public void buttonClicked(View v) {
		//Log.e("op", "blahs");
		Intent intent = new Intent(Settings.this,Main.class);
		startActivity(intent);


		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("firstRun", false);

		// Commit the edits!
		editor.commit();
	}

}
