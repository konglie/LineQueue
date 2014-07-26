package com.gaisma.linequeue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * Author   : ali LIM ( konglie@outlook.com )
 * Website  : http://www.konglie.web.id
 * Date     : 7/26/2014
 * Time     : 10:15 AM
 * PERIZINAN
 * Saya bukan Pengacara yang mengerti Bahasa Hukum,
 * Namun, program ini saya berikan untuk tujuan pembelajaran
 * dan bagian atau seluruh code di file ini
 * TIDAK BOLEH didistribusikan untuk tujuan komersil atau
 * berbayar, tanpa izin tertulis dari saya.
 * LICENSE
 * I am not a lawyer, but part of or all of this source code
 * MAY NOT be distributed for commercial purpose, without
 * written permission from me.
 */
public class QueueNumberView extends JFrame {
	private final int WAIT_TIME = 10 * 1000; // millisecond
	public QueueNumberView(){
		super("Queue Number");

		setMinimumSize(new Dimension(800, 200));
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		initComponents();
		initHandlers();

		pack();
		setFocusableWindowState( false );
		setLocationRelativeTo(null);
	}

	private int QUEUE_NUMBER = 0;

	private JLabel lblNumber;
	private JLabel lblOperator;
	private void initComponents(){
		lblNumber = Main.createCenteredLabel("00");
		lblNumber.setFont( Main.QueueNumberFont);

		getContentPane().add(lblNumber, BorderLayout.CENTER);

		lblOperator = Main.createCenteredLabel("--");
		lblOperator.setFont(Main.QueueOperatorFont);

		getContentPane().add(lblOperator, BorderLayout.SOUTH);
	}

	private void initHandlers(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
//				JOptionPane.showMessageDialog(QueueNumberView.this, "Close main view to exit");
			}
		});
	}

	/**
	 * Add a queue, increasing queue number
	 */
	public void enqueue(final String OperatorName){
		if(Main.getView().isAwaiting()){
			JOptionPane.showMessageDialog(Main.getView(), "Please wait for queue customer");
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				playQueueSound();
				lblNumber.setText( String.format("%02d", ++QUEUE_NUMBER) );
				lblOperator.setText( OperatorName );

				new Thread(new Runnable() {
					@Override
					public void run() {
						Main.getView().disableNextQueue();
						try{
							Thread.sleep( QueueNumberView.this.WAIT_TIME );
						} catch (Exception e){

						}
						if(Main.getView().isAwaiting()) {
							Main.getView().enableNextQueue();
						}
					}
				}).start();
			}
		});
	}

	/**
	 * play sound when queue is changed
	 * implement on your own.
	 */
	private void playQueueSound(){
		// DING DONG
	}
}
