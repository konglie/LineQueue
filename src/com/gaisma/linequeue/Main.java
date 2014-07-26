package com.gaisma.linequeue;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Author   : ali LIM ( konglie@outlook.com )
 * Website  : http://www.konglie.web.id
 * Date     : 7/26/2014
 * Time     : 10:14 AM
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
public class Main {
	public static final Font QueueNumberFont = new Font("", Font.BOLD, 256);
	public static final Font QueueOperatorFont = new Font("", Font.BOLD, 128);
	public static final Font LargeFont = new Font("", Font.BOLD, 32);

	private static MainView view;

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){

		}

		view = new MainView();
	}

	public static MainView getView(){
		return view;
	}

	public static void ConfirmExit(){
		if( JOptionPane.showConfirmDialog(view, "Exit application ?", "Confirmation", JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION ){
			view.dispose();
			System.exit( 0 );
		}
	}

	public static JLabel createCenteredLabel(String s){
		JLabel l = new JLabel( s );
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setHorizontalTextPosition(SwingConstants.CENTER);

		return l;
	}
}
