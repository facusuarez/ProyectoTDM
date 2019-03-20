package info.androidhive.masterlist.adapter;

import info.androidhive.masterlist.activities.SearchFragment;
import info.androidhive.masterlist.activities.UserFragment;
import info.androidhive.masterlist.activities.Ranking.RankingFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new RankingFragment();
		case 1:
			// Games fragment activity
			return new SearchFragment();
		case 2:
			// Movies fragment activity
			return new UserFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
