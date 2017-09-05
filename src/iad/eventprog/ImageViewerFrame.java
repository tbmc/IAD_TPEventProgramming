package iad.eventprog;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;


public class ImageViewerFrame extends JFrame implements ActionListener, FocusListener, ImageChangedListener {

	JLabel imageName;
	JTextField pathName; 
	JButton previous, next;

	ImageViewerBean imageViewer;
	
	ImageViewerFrame() {
		
		String initPath = "E:\\Workspace\\Java\\TPEventProgramming";
		imageViewer = new ImageViewerBean(initPath);
		imageViewer.addImageChangedListener(this);
		add(imageViewer, BorderLayout.CENTER);
		
		JPanel tfPanel = new JPanel(new BorderLayout());	
		
		pathName = new JTextField(initPath);
		tfPanel.add(pathName, BorderLayout.NORTH);
		
		imageName = new JLabel(" ");
		imageName.setBorder(BorderFactory.createLineBorder(Color.RED));
		tfPanel.add(imageName,BorderLayout.SOUTH);

		add(tfPanel, BorderLayout.NORTH);
		
		
		JPanel btnPanel = new JPanel();
		previous = new JButton("<-");
		btnPanel.add(previous);
	
		
		next = new JButton("->");
		btnPanel.add(next);
			
		add(btnPanel, BorderLayout.SOUTH);
		setSize(600, 400);
		
		imageViewer.first();



		previous.addActionListener(this);
		next.addActionListener(this);


		// When pathName change
		pathName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				pathNameChanged();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				pathNameChanged();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				pathNameChanged();
			}
		});

		// When focus is lost
		pathName.addFocusListener(this);


		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		imageViewer.first();
	}

	
	public static void main(String args[]) {
		ImageViewerFrame frame = new ImageViewerFrame();
		frame.setVisible(true);
		
	}

	public void pathNameChanged() {
		// System.out.println("Event");
		String path = pathName.getText();
		File file = new File(path);
		if(file.exists() && file.isDirectory()) {
			imageViewer.setPathName(path);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if(source == previous) {
			imageViewer.previous();
		}else if(source == next) {
			imageViewer.next();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {

	}

	@Override
	public void focusLost(FocusEvent e) {
		pathNameChanged();
	}

	@Override
	public void imageChanged(ImageChangedEvent event) {
		Dimension dim = event.getDim();
		imageName.setText(event.getName() + " : " + dim.getWidth() + "x" + dim.getHeight());
	}
}
