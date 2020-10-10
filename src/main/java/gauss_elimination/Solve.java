package gauss_elimination;

public class Solve {
	
	private static int i, j, k; 
	private static double pivot, coefficient;
	private static double[][] matrix = {{2, 1, -1, 3}, {1, -1, 2, 2}, {3, -2, 1, -1}};
	
	private static void showMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0 ; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] +" ");
			}
			System.out.println();
		}
	}
	
	private static void showArray(double[] array) {
		for (int i = 0 ; i < array.length; i++) {
			System.out.println("x["+ i + "]: " + array[i]);
		}
	}
	
	public double[] solveEquations(final double[][] matrix) {
		double[] results = new double[matrix[0].length - 1];
		int row = matrix.length;
		int col = matrix[0].length; 
		if(col > row + 1) {
			System.out.println("Phương trình vô nghiệm");
		} else {
			loop:for (i = 0; i < row; i++) {
				
				pivot = matrix[i][i];
				
				if (pivot == 0) {
					for (int j = i + 1; j < row; j++) {
						if (matrix[j][i] != 0) {
							swap(matrix, i, j);
							pivot = matrix[i][i];
						}
					}
				}
				
				for (j = 0 ; j < col; j++) {
					Thread thread1 = new Thread() {
						public void run() {
							matrix[i][j] = matrix[i][j] / pivot;
						};
					};
					thread1.run();
					thread1.stop();
				}
				
				for (k = i + 1; k < row; k++) {
					coefficient = matrix[k][i];
					for (j = 0; j < col; j++) {
						Thread thread2 = new Thread() {
							public void run() {
								matrix[k][j] = matrix[k][j] - matrix[i][j] * coefficient;
							};
						};
						thread2.run();
						thread2.stop();
					}
				}
			}
			
			results[row - 1] = matrix[row - 1][col - 1] / matrix[row - 1][row - 1];
			for (int i = row - 2; i >= 0; i--) {
				double sum = 0;
				for (int j = i + 1; j < col - 1; j++) {
					sum += matrix[i][j] * results[j]; 
				}
				results[i] = (matrix[i][col - 1] - sum) / matrix[i][i];
			}
		}
		return results;
	}

	private static void swap(double[][] matrix, int before, int after) {
		for (int i = 0; i < matrix[before].length; i++) {
			double temp = matrix[before][i];
			matrix[before][i] = matrix[after][i];
			matrix[after][i] = temp;
		}
	}
	
}
