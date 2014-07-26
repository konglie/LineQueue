package com.gaisma.linequeue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Author   : ali LIM ( konglie@outlook.com )
 * Website  : http://www.konglie.web.id
 * Date     : 7/26/2014
 * Time     : 10:16 AM
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
public class MainView extends JFrame {
	private final QueueNumberView numberView;
	private HashMap<String, OperatorView> operators;

	private boolean IS_AWATING = false;

	public MainView(){
		super("Main View");

		setTitle( "Basic Example Line Queue Application" );
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		setMinimumSize( new Dimension(400, 160) );

		numberView = new QueueNumberView();
		operators = new HashMap<String, OperatorView>();

		initComponents();
		initHandlers();

		pack();
		setLocationRelativeTo( null );
		setVisible(true);

		numberView.setVisible( true );
		numberView.setLocation(this.getX() + this.getWidth() + 10,
				(Toolkit.getDefaultToolkit().getScreenSize().height - numberView.getHeight()) / 2
		);
	}

	private int OperatorCount = 0;
	private JButton btnOperator, btnExit;
	private void initComponents(){

		JLabel info = Main.createCenteredLabel( "This is Main View" );
		info.setFont(Main.LargeFont);
		info.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		getContentPane().add(info, BorderLayout.NORTH);

		btnOperator = new JButton( "New Operator" );
		btnExit = new JButton( "Exit" );

		JPanel pane = new JPanel(new FlowLayout());
		pane.add(btnOperator);
		pane.add(btnExit);

		getContentPane().add( pane, BorderLayout.CENTER );
		getContentPane().add( Main.createCenteredLabel("<html><center>Created by ali LIM<br/>" +
						"konglie@outlook.com</center></html>"),
				BorderLayout.SOUTH );
	}

	private void initHandlers(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				Main.ConfirmExit();
			}
		});
		btnOperator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				addOperator();
			}
		});
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.ConfirmExit();
			}
		});
	}

	public void addOperator(){
		Object oprName = "";
		int n = OperatorCount + 1;
		do {
			oprName = JOptionPane.showInputDialog(this, "Operator Name",
					"New Operator",
					JOptionPane.QUESTION_MESSAGE,
					null,
					null,
					String.format("Operator %02d", n)
			);

			if(oprName == null){
				// user cancelled the input
				return;
			}

		} while ( !validateOprName( oprName.toString() ) );

		operators.put( oprName.toString().toLowerCase(),
				new OperatorView(numberView, oprName.toString()) );
	}

	public boolean validateOprName(String s){
		s = s.trim();
		if( s.equals("")){
			JOptionPane.showMessageDialog(Main.getView(), "Operator Name is required");
			return false;
		}

		if( operators.containsKey( s.toLowerCase() ) ){
			JOptionPane.showMessageDialog(Main.getView(), "Operator Name: " + s + " already exists");
			return false;
		}

		OperatorCount++;
		return true;
	}

	public void removeOperator(String s){
		s = s.trim().toLowerCase();
		if(! operators.containsKey( s ) ){
			return;
		}

		OperatorView view = operators.get( s );
		view.dispose();
		operators.remove( s );
	}

	public void disableNextQueue(){
		IS_AWATING = true;
		for( OperatorView view : operators.values() ){
			view.waitForQueue();
		}
	}

	public void enableNextQueue(){
		IS_AWATING = false;
		for( OperatorView view : operators.values() ){
			view.nextQueueReady();
		}
	}

	public boolean isAwaiting(){
		return IS_AWATING;
	}
}
