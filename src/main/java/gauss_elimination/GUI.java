package gauss_elimination;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;

public class GUI extends JFrame implements ActionListener{
	JPanel panel;
	JButton btnStart;
	JTextField enterField;
	Popup popup, popup2;
	PopupFactory pf;
	JTextField[][] coeJTFields;
	JLabel[][] xJLabels;
	JButton btnRs;
	int marginBot = 20;
	int marginLeft = 40;
	int size = 50;
	int y = 100;
	JLabel alert;
	private static double[][] matrix;
	public static void main(String[] args) {
		new GUI();
//		double[] rs = new Solve().solveEquations(matrix);
		
	}
	
	public GUI() {
		setTitle("Gauss Elimination");
		setSize(600, 500);
		//pack();
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        
        panel = new JPanel();
        panel.setLayout(null);
        add(panel);
        
        JLabel jLabel1 = new JLabel("Nhập số ẩn: ");
        jLabel1.setBounds(20, 20, 120, 30);
        jLabel1.setFont(new Font("Arial", 0, 18));
        panel.add(jLabel1);
        
        enterField = new JTextField();
        enterField.setBounds(150, 20, 50, 30);
        enterField.setFont(new Font("Arial", 0, 18));
        panel.add(enterField);
        
        btnStart = new JButton("Enter");
        btnStart.setBounds(220, 20, 70, 30);
        btnStart.addActionListener(this);
        panel.add(btnStart);
        
        alert = new JLabel("Hãy nhập số!");
        alert.setFont(new Font("Arial", 0, 18));
        alert.setVisible(false);
        alert.setForeground(new Color(249, 87, 36));
        panel.add(alert);
        
        setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnStart)) {
			String input = enterField.getText();
			try {
				int number = Integer.parseInt(input);
				alert.setVisible(false);
				coeJTFields = new JTextField[number][number + 1];
				xJLabels = new JLabel[number][number];
				for (int i = 0; i < number; i++) {
					for (int j = 0; j < number + 1; j++) {
						coeJTFields[i][j] = new JTextField();
						coeJTFields[i][j].setBounds(20 + j * size + j * marginLeft, y, size, size);
						coeJTFields[i][j].setBackground(Color.WHITE);
						coeJTFields[i][j].setFont(new Font("Arial", 0, 18));
						panel.add(coeJTFields[i][j]);
						
						if (j < number) {
							if (j == number - 1) {
								xJLabels[i][j] = new JLabel("x" + j + " =");
							} else {
								xJLabels[i][j] = new JLabel("x" + j + "+");
							}
							xJLabels[i][j].setBounds(coeJTFields[i][j].getX() + size, y + size / 3, 40, 20);
							xJLabels[i][j].setFont(new Font("Arial", 0, 18));
							panel.add(xJLabels[i][j]);
						}
					}
					
					y += size + marginBot; 
				}
				btnRs = new JButton("RESULT");
				btnRs.setBounds(20, coeJTFields[number-1][0].getY() + size + marginBot, 100, 40);
				btnRs.addActionListener(this);
				panel.add(btnRs);
				repaint();
			} catch (Exception e2) {
				alert.setBounds(300, 20, 200, 30);
				alert.setVisible(true);
			}
		}
		
		if (e.getSource().equals(btnRs)) {
			int row = coeJTFields.length;
			int col = coeJTFields[0].length;
			boolean isError = false;
			double[] results;
			JLabel[] rsJLabels;
			matrix = new double[row][col];
			for (int i = 0 ; i < row; i++) {
				for (int j = 0; j < col; j++) {
					try {
						matrix[i][j] = Double.parseDouble(coeJTFields[i][j].getText());
						alert.setVisible(false);
					} catch (Exception e2) {
						isError = true;
						alert.setBounds(btnRs.getX() + btnRs.getWidth() + marginBot
								, btnRs.getY() + btnRs.getHeight() / 6, 200, 30);
						alert.setVisible(true);
					}
				}
			}
			if (!isError) {
				results = new Solve().solveEquations(matrix);
				rsJLabels = new JLabel[results.length];
				for (int i = 0 ; i < results.length; i++) {
					rsJLabels[i] = new JLabel("x" + i + "= " + results[i] + "");
					rsJLabels[i].setBounds(btnRs.getX() + btnRs.getWidth() + marginBot
							, btnRs.getY() + btnRs.getHeight() / 6 + i * marginBot, 400, 30);
					rsJLabels[i].setFont(new Font("Arial", 0, 18));
					panel.add(rsJLabels[i]);
				}
				
				repaint();
			}
		}
	}
}
