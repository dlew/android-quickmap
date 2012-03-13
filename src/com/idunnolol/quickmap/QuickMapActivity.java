package com.idunnolol.quickmap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;

public class QuickMapActivity extends Activity {

	private static final int PICK_ADDRESS = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(StructuredPostal.CONTENT_TYPE);
		startActivityForResult(intent, PICK_ADDRESS);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_ADDRESS) {
			if (resultCode == RESULT_OK && data.getData() != null) {
				Cursor c = managedQuery(data.getData(), null, null, null, null);
				if (c != null) {
					c.moveToFirst();
					String address = c.getString(c.getColumnIndexOrThrow(StructuredPostal.FORMATTED_ADDRESS));

					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("geo:0,0?q=" + address));
					startActivity(intent);
				}
			}

			finish();
		}
	}
}