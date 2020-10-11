package gauss_elimination;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, KeyListener {
	JPanel panel;
	JButton btnStart;
	JTextField enterField;
	JTextField[][] coeJTFields;
	JLabel[][] xJLabels;
	JButton btnRs;
	int marginBot = 20;
	int marginLeft = 40;
	int size = 30;
	int y;
	JLabel alert;
	JLabel[] rsJLabels;
	private static double[][] matrix;

	public static void main(String[] args) {
		new GUI();
	}

	public GUI() {
		setTitle("Gauss Elimination");
		setSize(450, 500);
		// pack();
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
		enterField.setBounds(130, 20, 50, 30);
		enterField.setFont(new Font("Arial", 0, 18));
		enterField.addKeyListener(this);
		panel.add(enterField);

		btnStart = new JButton("ENTER");
		btnStart.setBounds(200, 20, 80, 30);
		btnStart.addActionListener(this);
		panel.add(btnStart);

		alert = new JLabel("Hãy nhập số!");
		alert.setFont(new Font("Arial", 0, 18));
		alert.setVisible(false);
		alert.setForeground(new Color(249, 87, 36));
		panel.add(alert);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnStart)) {
			eventEnterBtn();
		}

		if (e.getSource().equals(btnRs)) {
			eventBtnResult();
		}
	}

	private void eventEnterBtn() {
		y = 100;
		String input = enterField.getText().trim();
		try {
			int number = Integer.parseInt(input);
			if (coeJTFields != null) {
				for (int i = 0; i < coeJTFields.length; i++) {
					for (int j = 0; j < coeJTFields[0].length; j++) {
						panel.remove(coeJTFields[i][j]);
						if (j < coeJTFields[0].length - 1) {
							panel.remove(xJLabels[i][j]);
						}
					}
				}
				panel.remove(btnRs);
			}

			if (rsJLabels != null) {
				for (int i = 0; i < rsJLabels.length; i++) {
					panel.remove(rsJLabels[i]);
				}
			}

			alert.setVisible(false);
			coeJTFields = new JTextField[number][number + 1];
			xJLabels = new JLabel[number][number];
			KeyAdapter keyEventFocus = new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_ENTER:
						for (int i = 0; i < coeJTFields.length; i++) {
							for (int j = 0; j < coeJTFields[0].length; j++) {
								if (e.getSource().equals(coeJTFields[i][j])) {
									coeJTFields[i][j].transferFocus();
								}
							}
						}
						break;
					case KeyEvent.VK_LEFT:
						for (int i = 0; i < coeJTFields.length; i++) {
							for (int j = 0; j < coeJTFields[0].length; j++) {
								if (e.getSource().equals(coeJTFields[i][j])) {
									coeJTFields[i][j].transferFocusBackward();
								}
							}
						}
						break;
					default:
						break;
					}
				}
			};
			for (int i = 0; i < number; i++) {
				for (int j = 0; j < number + 1; j++) {
					coeJTFields[i][j] = new JTextField();
					coeJTFields[i][j].setBounds(20 + j * size + j * marginLeft, y, size, size);
					coeJTFields[i][j].setBackground(Color.WHITE);
					coeJTFields[i][j].setFont(new Font("Arial", 0, 18));
					coeJTFields[i][j].addKeyListener(keyEventFocus);
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
			btnRs.setBounds(20, coeJTFields[number - 1][0].getY() + size + marginBot, 100, 40);
			btnRs.addActionListener(this);
			panel.add(btnRs);
			repaint();
		} catch (Exception e2) {
			alert.setBounds(300, 20, 200, 30);
			alert.setVisible(true);
		}
	}

	private void eventBtnResult() {
		int row = coeJTFields.length;
		int col = coeJTFields[0].length;
		boolean isError = false;
		double[] results;
		matrix = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				try {
					matrix[i][j] = Double.parseDouble(coeJTFields[i][j].getText());
					alert.setVisible(false);
				} catch (Exception e2) {
					isError = true;
					alert.setBounds(btnRs.getX() + btnRs.getWidth() + marginBot, btnRs.getY() + btnRs.getHeight() / 6,
							200, 30);
					alert.setVisible(true);
				}
			}
		}
		if (!isError) {
			if (rsJLabels != null) {
				for (int i = 0; i < rsJLabels.length; i++) {
					panel.remove(rsJLabels[i]);
				}
			}
			results = new Solve().solveEquations(matrix);
			rsJLabels = new JLabel[results.length];
			for (int i = 0; i < results.length; i++) {
				rsJLabels[i] = new JLabel("x" + i + "= " + results[i] + "");
				rsJLabels[i].setBounds(btnRs.getX() + btnRs.getWidth() + marginBot,
						btnRs.getY() + btnRs.getHeight() / 6 + i * marginBot, 400, 30);
				rsJLabels[i].setFont(new Font("Arial", 0, 18));
				panel.add(rsJLabels[i]);
			}

			repaint();
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(enterField)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				eventEnterBtn();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
