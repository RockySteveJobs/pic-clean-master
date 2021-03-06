package cn.iam007.pic.clean.master.about;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.iam007.pic.clean.master.R;
import cn.iam007.pic.clean.master.base.BaseFragment;
import cn.iam007.pic.clean.master.feedback.FeedbackActivity;
import cn.iam007.pic.clean.master.service.Iam007Service;
import cn.iam007.pic.clean.master.utils.PlatformUtils;

/**
 * Created by Administrator on 2015/6/8.
 */
public class AboutFragment extends BaseFragment {

    RecyclerView mRecyclerView;
    AboutRecyclerViewAdapter mAdapter;
    View mLoadingProgressBar;

    private View mRootView = null;

    @Override
    public String getFragmentTitle() {
        return getString(R.string.about);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.activity_about, null);
            initView(mRootView);

            mLoadingProgressBar = getToolbar().findViewById(R.id.toolbar_progress_bar);
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        }
        return mRootView;
    }

    private void initView(View view) {
        // init CardView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardListView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AboutRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        initAdapter();
    }

    private void initAdapter() {
        _initAdatperHeader();
        _initAdapterLibraries();
    }

    private void _initAdatperHeader() {
        //get the packageManager to load and read some values :D
        PackageManager pm = getActivity().getPackageManager();
        //get the packageName
        String packageName = getActivity().getPackageName();
        //Try to load the applicationInfo
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (Exception ex) {
        }

        //set the Version or hide it
        String versionName = null;
        Integer versionCode = null;
        if (packageInfo != null) {
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        }

        //add this cool thing to the headerView of our listView
        mAdapter.setHeader(versionName, versionCode, null);
        mAdapter.setDescription(getString(R.string.app_description));
    }

    /**
     * 获取需要展示的使用开源库
     */
    private void _initAdapterLibraries() {
        AVQuery<AVObject> query = new AVQuery<>("Libraries");
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    ArrayList<AboutLibrary> libraries = new ArrayList<>();
                    for (AVObject object : avObjects) {
                        AboutLibrary library = toAboutLibrary(object);
                        if (library != null) {
                            libraries.add(library);
                        }
                    }

                    Object[] ls = libraries.toArray();
                    Arrays.sort(ls);
                    mAdapter.addLibraries(ls);
                    mAdapter.notifyDataSetChanged();
                } else {
                    LogUtils.d("获取关于界面信息错误！");
                }

                hideLoadingView();
            }
        });
    }

    private void hideLoadingView() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mLoadingProgressBar != null) {
                    mLoadingProgressBar.setVisibility(View.GONE);
                    mLoadingProgressBar.startAnimation(
                            AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out));
                }
            }
        });
    }

    private AboutLibrary toAboutLibrary(AVObject object) {
        AboutLibrary library = null;

        if (object != null) {
            String libraryName = object.getString("libraryName");
            String libraryDescription = object.getString("libraryDescription");
            String libraryWebsite = object.getString("libraryWebsite");
            String libraryVersion = object.getString("libraryVersion");
            String author = object.getString("author");
            String authorWebsite = object.getString("authorWebsite");
            boolean isOpenSource = object.getBoolean("isOpenSource");
            String repositoryLink = object.getString("repositoryLink");

            library = new AboutLibrary(author, libraryName, libraryDescription);
            library.setLibraryWebsite(libraryWebsite);
            library.setLibraryVersion(libraryVersion);
            library.setAuthorWebsite(authorWebsite);
            library.setOpenSource(isOpenSource);
            library.setRepositoryLink(repositoryLink);
        }

        return library;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bindService();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unbindService(mServiceConnection);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), Iam007Service.class);
        getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private Iam007Service mService = null;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((Iam007Service.Iam007Binder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.about_menu, menu);
        MenuItem item = menu.findItem(R.id.action_feedback);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                /**
                 * 打开反馈页面
                 */
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
}
