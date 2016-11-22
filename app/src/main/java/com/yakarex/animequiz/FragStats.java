package com.yakarex.animequiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yakarex.animequiz.adapters.StatsPagerAdapter;

public class FragStats extends Fragment{

	ViewPager statsPager;
	StatsPagerAdapter statsPagerAdapter;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v= inflater.inflate(R.layout.frag_stats, container, false);

		statsPager= (ViewPager) v.findViewById(R.id.header_pager);
		statsPagerAdapter= new StatsPagerAdapter(getFragmentManager(), getActivity());
		statsPager.setAdapter(statsPagerAdapter);

		return v;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


	}

	@Override
	public void onResume() {
		super.onResume();


	}

}
