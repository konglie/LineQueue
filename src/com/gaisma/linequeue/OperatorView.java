package com.gaisma.linequeue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class OperatorView extends JFrame {
	private QueueNumberView numberView;
	private String OperatorName;
	public OperatorView(QueueNumberView numberView, String oprName){
		super(oprName);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize( new Dimension( 300, 100 ));
		this.numberView = numberView;
		OperatorName = oprName;

		initComponents();
		initHandlers();

		pack();
		setLocationRelativeTo(null);
		setVisible( true );
	}

	private JButton btnEnqueue, btnQueueCame;
	private void initComponents(){
		btnEnqueue = new JButton("Next Queue");
		btnEnqueue.setFont( Main.LargeFont );
		JPanel pane = new JPanel(new BorderLayout());
		pane.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10) );
		pane.add( btnEnqueue, BorderLayout.CENTER );

		btnQueueCame = new JButton("Customer is here");
		btnQueueCame.setFont( Main.LargeFont );
		pane.add(btnQueueCame, BorderLayout.SOUTH);
		btnQueueCame.setVisible( false );

		getContentPane().add( pane, BorderLayout.CENTER );
	}

	private void initHandlers(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				Main.getView().removeOperator( OperatorView.this.OperatorName );
			}
		});

		btnEnqueue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				btnQueueCame.setVisible( true );
				OperatorView.this.pack();
				OperatorView.this.numberView.enqueue(
						OperatorView.this.OperatorName
				);
			}
		});

		btnQueueCame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.getView().enableNextQueue();
			}
		});
	}

	public void waitForQueue(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				btnEnqueue.setText("waiting...");
				btnEnqueue.setEnabled( false );
			}
		});
	}

	public void nextQueueReady(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				btnQueueCame.setVisible( false );
				OperatorView.this.pack();

				btnEnqueue.setText("Next Queue");
				btnEnqueue.setEnabled( true );
			}
		});
	}
}
