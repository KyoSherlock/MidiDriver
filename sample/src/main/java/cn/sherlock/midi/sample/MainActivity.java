package cn.sherlock.midi.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

import cn.sherlock.com.sun.media.sound.SF2Soundbank;
import cn.sherlock.com.sun.media.sound.SoftSynthesizer;
import jp.kshoji.javax.sound.midi.InvalidMidiDataException;
import jp.kshoji.javax.sound.midi.MidiUnavailableException;
import jp.kshoji.javax.sound.midi.Receiver;
import jp.kshoji.javax.sound.midi.ShortMessage;


public class MainActivity extends Activity {

	private SoftSynthesizer synth;
	private Receiver recv;
	private boolean isPianoOn = false;
	private boolean isWoodblockOn = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			SF2Soundbank sf = new SF2Soundbank(getAssets().open("SmallTimGM6mb.sf2"));
			synth = new SoftSynthesizer();
			synth.open();
			synth.loadAllInstruments(sf);
			synth.getChannels()[0].programChange(0);
			synth.getChannels()[1].programChange(1);
			recv = synth.getReceiver();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}

		this.findViewById(R.id.piano).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (recv != null) {
					if (!isPianoOn) {
						try {
							ShortMessage msg = new ShortMessage();
							msg.setMessage(ShortMessage.NOTE_ON, 0, 60, 127);
							recv.send(msg, -1);
						} catch (InvalidMidiDataException e) {
							e.printStackTrace();
						}
					} else {
						try {
							ShortMessage msg = new ShortMessage();
							msg.setMessage(ShortMessage.NOTE_OFF, 0, 60, 127);
							recv.send(msg, -1);
						} catch (InvalidMidiDataException e) {
							e.printStackTrace();
						}
					}
					isPianoOn = !isPianoOn;
				}
			}
		});

		this.findViewById(R.id.woodblock).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (recv != null) {
					if (!isWoodblockOn) {
						try {
							ShortMessage msg = new ShortMessage();
							msg.setMessage(ShortMessage.NOTE_ON, 1, 60, 127);
							recv.send(msg, -1);
						} catch (InvalidMidiDataException e) {
							e.printStackTrace();
						}
					} else {
						try {
							ShortMessage msg = new ShortMessage();
							msg.setMessage(ShortMessage.NOTE_OFF, 1, 60, 127);
							recv.send(msg, -1);
						} catch (InvalidMidiDataException e) {
							e.printStackTrace();
						}
					}
					isWoodblockOn = !isWoodblockOn;
				}
			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (synth != null) {
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (synth != null) {
			synth.close();
		}
	}
}

