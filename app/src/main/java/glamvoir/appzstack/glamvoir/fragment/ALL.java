package glamvoir.appzstack.glamvoir.fragment;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import glamvoir.appzstack.glamvoir.Bean.AllPostsBean;
import glamvoir.appzstack.glamvoir.Bean.ParentPostBean;
import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Custome_All_ListAdapter;
import glamvoir.appzstack.glamvoir.asynctask.SavePostAsyncTask;
import glamvoir.appzstack.glamvoir.asynctaskloader.GetAllPostLoader;
import glamvoir.appzstack.glamvoir.asynctaskloader.LoaderID;
import glamvoir.appzstack.glamvoir.config.AppConfig;
import glamvoir.appzstack.glamvoir.constant.AppConstant;
import glamvoir.appzstack.glamvoir.helpers.Utility;
import glamvoir.appzstack.glamvoir.intentservice.NetworkIntentService;
import glamvoir.appzstack.glamvoir.interfaces.AsynTaskListener;
import glamvoir.appzstack.glamvoir.model.net.request.RequestBean;
import glamvoir.appzstack.glamvoir.network.InternetStatus;


public class ALL extends Fragment {

    //ImagePagerAdapter adapter;
    private ListView mlistView;
    private RequestBean mRequestBean;

    private ArrayList<ParentPostBean> list = new ArrayList<ParentPostBean>();

    Custome_All_ListAdapter adapter;

    public ALL() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.showToast(getActivity(), "onCreate");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Utility.showToast(getActivity(), "onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Utility.showToast(getActivity(), "onCreateView");

        mRequestBean = new RequestBean();
        mRequestBean.setLoader(true);
        mRequestBean.setActivity(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        mlistView = (ListView) rootView.findViewById(R.id.lv_all);

        setAdapter();

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        //Utility.showToast(getActivity(), "onActivityCreated");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Utility.showToast(getActivity(), "onViewCreated");
    }


    protected void loadData() {
        getLoaderManager().initLoader(LoaderID.GETPOST, null, ffspCallback);
    }

    LoaderManager.LoaderCallbacks<AllPostsBean> ffspCallback =
            new LoaderManager.LoaderCallbacks<AllPostsBean>() {

                @Override
                public Loader<AllPostsBean> onCreateLoader(int id, Bundle args) {
                    return new GetAllPostLoader(mRequestBean, AppConfig.GETALLPOST, AppConstant.CATEGORY_ALL);
                }

                @Override
                public void onLoadFinished(Loader<AllPostsBean> loader, AllPostsBean data) {
                    if (loader instanceof GetAllPostLoader) {
                        ((GetAllPostLoader) loader).hideLoaderDialog();
                        if (data.getSuccessCode() == 1) {
                            //error
                        } else {
                            list.addAll(data.results);
                            getAdapter().notifyDataSetChanged();
                            // getLoaderManager().destroyLoader(LoaderID.GETPOST);
                        }
                    }
                }

                @Override
                public void onLoaderReset(Loader<AllPostsBean> loader) {
                }
            };

    private void setAdapter() {
        adapter = new Custome_All_ListAdapter(ALL.this, list);
        mlistView.setAdapter(adapter);
    }


    public Custome_All_ListAdapter getAdapter() {
        return (Custome_All_ListAdapter) adapter;
    }


    @Override
    public void onResume() {
        super.onResume();
        //Utility.showToast(getActivity(), "on resume");
        IntentFilter filter = new IntentFilter();
        filter.addAction(NetworkIntentService.BROADCAST_LIKE_ACTION);
        filter.addAction(NetworkIntentService.BROADCAST_FOLLOW_ACTION);
        getActivity().registerReceiver(getAdapter().observeLikeReceiver, filter);
        registered = true;
    }

    private boolean registered = false;

    @Override
    public void onPause() {
        super.onPause();

        if (registered) {
            getActivity().unregisterReceiver(getAdapter().observeLikeReceiver);
            registered = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Utility.showToast(getActivity(), "onDestroy frag");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Utility.showToast(getActivity(), "onDestroyView frag");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Utility.showToast(getActivity(), "onDetach frag");
    }

    @Override
    public void onStart() {
        super.onStart();
        //Utility.showToast(getActivity(), "onStart frag");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Utility.showToast(getActivity(), "onStop fragment");
    }


    public void savePost(String methodName, String mUserID, String postID, int pos) {
        if (InternetStatus.isInternetAvailable(getActivity(), true)) {
            new SavePostAsyncTask(getActivity(), new AsynTaskListener() {
                @Override
                public void success(String success, String listenerId) {

                }

                @Override
                public void error(String errorMessage, String errorCode, String listenerId) {
                    Utility.showToast(getActivity(), errorMessage);
                }

                @Override
                public void successWithresult(List<Object> sucessObject, String message, String pos) {

                    if (message.equalsIgnoreCase("0")) {
                        Utility.showToast(getActivity(), "Post saved");

                        ParentPostBean item = list.get(Integer.parseInt(pos));
                        item.setIs_saved(Integer.parseInt("1"));
                        adapter.notifyDataSetChanged();

                    } else {
                        Utility.showToast(getActivity(), message);
                    }

                }
            }, "server").execute(methodName, mUserID, postID, String.valueOf(pos));
        }
    }


}