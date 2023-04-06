package final2022;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class Service2 {
	private JTextField field;
	private DefaultListModel<String> listModel; //リストの本当の中身
	private JList<String> list; //リスト用のGUI部品
	
	class RegButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String tempText = field.getText();
			listModel.addElement(tempText);
		}
	}
	
	class DelButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int index = list.getSelectedIndex();
			if (index != -1) {
				listModel.remove(index);
			} else {
				JOptionPane.showMessageDialog(list, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class QuitButtonAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String tempText = field.getText();
			listModel.remove(listModel.indexOf(tempText));
		}
	}
	
	public Component createComponents() {
		//入力欄
		field = new JTextField("initial text");
		//リスト作成
		listModel = new DefaultListModel<String>();
		
		list = new JList<String>(listModel);
		list.setVisibleRowCount(10);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//スクロールバー
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.createVerticalScrollBar();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		//登録ボタン
		JButton regButton = new JButton("Register");
		RegButtonAction regButtonListener = new RegButtonAction();
		regButton.addActionListener(regButtonListener);
		regButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//削除
		JButton delButton = new JButton("Delete");
		DelButtonAction delButtonListener = new DelButtonAction();
		delButton.addActionListener(delButtonListener);
		
		//終了
		JButton quitButton = new JButton("Quit");
		QuitButtonAction quitButtonListeener = new QuitButtonAction();
		quitButton.addActionListener(quitButtonListeener);
		
		//delButton, quitButtonをsubPane1にまとめる
		JPanel subPane1 = new JPanel();
		subPane1.setLayout(new GridLayout(1,0));
		subPane1.add(delButton);
		subPane1.add(Box.createRigidArea(new Dimension(30, 10)));
		subPane1.add(quitButton);
		
		//メインパネル作成
		JPanel mainPane = new JPanel();
		mainPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		
		//メインパネル部品追加
		mainPane.add(field);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(regButton);
		mainPane.add(Box.createRigidArea(new Dimension(10, 30)));
		mainPane.add(scrollPane);
		mainPane.add(Box.createRigidArea(new Dimension(10, 30)));
		mainPane.add(subPane1);
		
		return mainPane;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("SwingApplication");
		Service2 app = new Service2();
		Component contents = app.createComponents();
		frame.getContentPane().add(contents, BorderLayout.CENTER);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		frame.pack();
		frame.setVisible(true);
	}
}
