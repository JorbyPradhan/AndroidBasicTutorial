package info.androidhive.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SearchResultsActivity extends Activity {

	private TextView txtQuery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);

		// get the action bar
		ActionBar actionBar = getActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);

		txtQuery = (TextView) findViewById(R.id.txtQuery);

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * Handling intent data
	 */
	private void handleIntent(Intent intent) {
		System.out.println("handleIntent : " + Intent.ACTION_SEARCH);
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			
			System.out.println("Request Query Data : " + query);
			
			/**
			 * Use this query to display search results like 
			 * 1. Getting the data from SQLite and showing in listview 
			 * 2. Making webrequest and displaying the data 
			 * For now we just display the query only
			 */
			if(Integer.parseInt(query)%2  == 0)
				txtQuery.setText("Search Query: Even " + query);
			else
				txtQuery.setText("Search Query: Odd " + query);


		}

	}
}
