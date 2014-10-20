package com.uraroji.garage.android.mp3recvoice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RecMicToMp3 mRecMicToMp3 = new RecMicToMp3(
			Environment.getExternalStorageDirectory() + "/test.mp3", 8000);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final TextView statusTextView = (TextView) findViewById(R.id.StatusTextView);

		mRecMicToMp3.setHandle(new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case RecMicToMp3.MSG_REC_STARTED:
					statusTextView.setText("˜^‰¹’†");
					break;
				case RecMicToMp3.MSG_REC_STOPPED:
					statusTextView.setText("");
					break;
				case RecMicToMp3.MSG_ERROR_GET_MIN_BUFFERSIZE:
					statusTextView.setText("");
					break;
				case RecMicToMp3.MSG_ERROR_CREATE_FILE:
					statusTextView.setText("");
					break;
				case RecMicToMp3.MSG_ERROR_REC_START:
					statusTextView.setText("");
					break;
				case RecMicToMp3.MSG_ERROR_AUDIO_RECORD:
					statusTextView.setText("");
					break;
				case RecMicToMp3.MSG_ERROR_AUDIO_ENCODE:
					statusTextView.setText("");
					break;
				case RecMicToMp3.MSG_ERROR_WRITE_FILE:
					statusTextView.setText("");
					break;
				case RecMicToMp3.MSG_ERROR_CLOSE_FILE:
					statusTextView.setText("");
					break;
				default:
					break;
				}
			}
		});

		Button startButton = (Button) findViewById(R.id.StartButton);
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mRecMicToMp3.start();
			}
		});
		Button stopButton = (Button) findViewById(R.id.StopButton);
		stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mRecMicToMp3.stop();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mRecMicToMp3.stop();
	}

	private void mergeTwoFiles(String file1, String file2, String outFile) {

		try {
			FileInputStream fis1 = new FileInputStream(file1);
			FileInputStream fis2 = new FileInputStream(file2);
			SequenceInputStream sis = new SequenceInputStream(fis1, fis2);

			FileOutputStream fos = new FileOutputStream(new File(outFile));

			int temp;

			try {
				while ((temp = sis.read()) != -1) {

					fos.write(temp);

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
