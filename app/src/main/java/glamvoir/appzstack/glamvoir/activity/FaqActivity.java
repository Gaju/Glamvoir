package glamvoir.appzstack.glamvoir.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.ExpandableListAdapter;
import glamvoir.appzstack.glamvoir.constant.AppConstant;

/**
 * Created by jaim on 9/14/2015.
 */
public class FaqActivity extends AppCompatActivity{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Context mContext;
    private Toolbar toolbar;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FaqActivity.class);
        intent.putExtra("ParentClassName", context.getClass().getSimpleName());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_faq);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        getToolbar(toolbar);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
               /* Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
               /* Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });
    }

    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("FAQ");
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(getResources().getString(R.string.question_one));
        listDataHeader.add(getResources().getString(R.string.question_two));
        listDataHeader.add(getResources().getString(R.string.question_three));
        listDataHeader.add(getResources().getString(R.string.question_four));
        listDataHeader.add(getResources().getString(R.string.question_five));

        listDataHeader.add(getResources().getString(R.string.question_six));
        listDataHeader.add(getResources().getString(R.string.question_seven));
        listDataHeader.add(getResources().getString(R.string.question_eight));
        listDataHeader.add(getResources().getString(R.string.question_nine));
        listDataHeader.add(getResources().getString(R.string.question_ten));
        listDataHeader.add(getResources().getString(R.string.question_eleven));


        listDataHeader.add(getResources().getString(R.string.question_twelve));
        listDataHeader.add(getResources().getString(R.string.question_thirteen));
        listDataHeader.add(getResources().getString(R.string.question_fourteen));
        listDataHeader.add(getResources().getString(R.string.question_fifteen));
        listDataHeader.add(getResources().getString(R.string.question_sixteen));

        listDataHeader.add(getResources().getString(R.string.question_seventeen));
        listDataHeader.add(getResources().getString(R.string.question_ninteen));
        listDataHeader.add(getResources().getString(R.string.question_twenty));
        listDataHeader.add(getResources().getString(R.string.question_twenty_one));
        listDataHeader.add(getResources().getString(R.string.question_twenty_two));
        listDataHeader.add(getResources().getString(R.string.question_twenty_three));
        listDataHeader.add(getResources().getString(R.string.question_twenty_four));
        listDataHeader.add(getResources().getString(R.string.question_twenty_five));



        // Adding child data
        List<String> question_One = new ArrayList<String>();
        question_One.add(getResources().getString(R.string.question_one_anwer));
        List<String> question_two = new ArrayList<String>();
        question_two.add(getResources().getString(R.string.question_two_anwer));
        List<String> question_Three = new ArrayList<String>();
        question_Three.add(getResources().getString(R.string.question_three_anwer));
        List<String> question_four = new ArrayList<String>();
        question_four.add(getResources().getString(R.string.question_four_anwer));
        List<String> question_five = new ArrayList<String>();
        question_five.add(getResources().getString(R.string.question_five_anwer));

        List<String> question_six = new ArrayList<String>();
        question_six.add(getResources().getString(R.string.question_six_anwer));
        List<String> question_seven = new ArrayList<String>();
        question_seven.add(getResources().getString(R.string.question_seven_anwer));
        List<String> question_eight = new ArrayList<String>();
        question_eight.add(getResources().getString(R.string.question_eight_anwer));
        List<String> question_nine = new ArrayList<String>();
        question_nine.add(getResources().getString(R.string.question_nine_anwer));
        List<String> question_ten = new ArrayList<String>();
        question_ten.add(getResources().getString(R.string.question_ten_anwer));

        List<String> question_eleven = new ArrayList<String>();
        question_eleven.add(getResources().getString(R.string.question_eleven_anwer));
        List<String> question_twelve = new ArrayList<String>();
        question_twelve.add(getResources().getString(R.string.question_twelve_anwer));
        List<String> question_thirteen = new ArrayList<String>();
        question_thirteen.add(getResources().getString(R.string.question_thirteen_anwer));
        List<String> question_fourteen = new ArrayList<String>();
        question_fourteen.add(getResources().getString(R.string.question_fourteen_anwer));
        List<String> question_fifteen = new ArrayList<String>();
        question_fifteen.add(getResources().getString(R.string.question_fifteen_anwer));

        List<String> question_sixteen = new ArrayList<String>();
        question_sixteen.add(getResources().getString(R.string.question_sixteen_anwer));
        List<String> question_seventeen = new ArrayList<String>();
        question_seventeen.add(getResources().getString(R.string.question_seventeen_anwer));
        List<String> question_ninteen = new ArrayList<String>();
        question_ninteen.add(getResources().getString(R.string.question_ninteen_anwer));
        List<String> question_twenty = new ArrayList<String>();
        question_twenty.add(getResources().getString(R.string.question_twenty_anwer));
        List<String> question_twenty_one = new ArrayList<String>();
        question_twenty_one.add(getResources().getString(R.string.question_twenty_one_anwer));

        List<String> question_twenty_two = new ArrayList<String>();
        question_twenty_two.add(getResources().getString(R.string.question_twenty_two_anwer));
        List<String> question_twenty_three = new ArrayList<String>();
        question_twenty_three.add(getResources().getString(R.string.question_twenty_three_anwer));
        List<String> question_twenty_four = new ArrayList<String>();
        question_twenty_four.add(getResources().getString(R.string.question_twenty_four_anwer));
        List<String> question_twenty_five = new ArrayList<String>();
        question_twenty_five.add(getResources().getString(R.string.question_twenty_five_anwer));



        listDataChild.put(listDataHeader.get(0), question_One);
        listDataChild.put(listDataHeader.get(1), question_two);
        listDataChild.put(listDataHeader.get(2), question_Three);
        listDataChild.put(listDataHeader.get(3), question_four);
        listDataChild.put(listDataHeader.get(4), question_five);

        listDataChild.put(listDataHeader.get(5), question_six);
        listDataChild.put(listDataHeader.get(6), question_seven);
        listDataChild.put(listDataHeader.get(7), question_eight);
        listDataChild.put(listDataHeader.get(8), question_nine);
        listDataChild.put(listDataHeader.get(9), question_ten);

        listDataChild.put(listDataHeader.get(10), question_eleven);
        listDataChild.put(listDataHeader.get(11), question_twelve);
        listDataChild.put(listDataHeader.get(12), question_thirteen);
        listDataChild.put(listDataHeader.get(13), question_fourteen);
        listDataChild.put(listDataHeader.get(14), question_fifteen);

        listDataChild.put(listDataHeader.get(15), question_sixteen);
        listDataChild.put(listDataHeader.get(16), question_seventeen);
        listDataChild.put(listDataHeader.get(17), question_ninteen);
        listDataChild.put(listDataHeader.get(18), question_twenty);
        listDataChild.put(listDataHeader.get(19), question_twenty_one);

        listDataChild.put(listDataHeader.get(20), question_twenty_two);
        listDataChild.put(listDataHeader.get(21), question_twenty_three);
        listDataChild.put(listDataHeader.get(22), question_twenty_four);
        listDataChild.put(listDataHeader.get(23), question_twenty_five);

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName"); //getting the parent class name
        Intent newIntent = null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(FaqActivity.this, Class.forName(AppConstant.PACKAGE + className));
            newIntent.putExtra("ParentClassName", "HomeActivity");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

}
