package info.androidhive.tabsswipe.Activities.Activities.Adapter;

import info.androidhive.tabsswipe.Activities.Activities.SearchFragment;
import info.androidhive.tabsswipe.Activities.Activities.UserFragment;
import info.androidhive.tabsswipe.Activities.Activities.Ranking.RankingFragment;
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
